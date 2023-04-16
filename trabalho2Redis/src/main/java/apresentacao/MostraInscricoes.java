/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apresentacao;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import negocio.Inscricao;
import persistencia.InscricaoDAO;

/**
 *
 * @author luizd
 */
public class MostraInscricoes extends javax.swing.JFrame {

    javax.swing.JList<String> listaInscricoes;
    javax.swing.JButton botaoEditar;
    javax.swing.JButton botaoLerFeed;
    javax.swing.JButton botaoMenuPrincipal;
    javax.swing.JButton botaoDeletar;
    List<Inscricao> listaInscricoesObjeto;

    public MostraInscricoes() {
        this.setSize(1024, 768);
        this.setTitle("Mostra Inscri��es");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        listaInscricoesObjeto = inscricaoDAO.listar();
        DefaultListModel<String> listaTemp = new DefaultListModel<>();
        for (int i = 0; i < listaInscricoesObjeto.size(); i++) {
            listaTemp.addElement((listaInscricoesObjeto.get(i).toString()));
        }
        listaInscricoes = new JList<>(listaTemp);
        listaInscricoes.setBounds(100, 100, 400, 200);

        this.add(listaInscricoes);
        botaoEditar = new JButton();
        botaoEditar.setBounds(100, 300, 100, 30);
        botaoEditar.setHorizontalAlignment(2);

        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }

            private void botaoEditarActionPerformed(ActionEvent evt) {

                if (listaInscricoes.getSelectedIndex() != -1) {
                    try {

                        Inscricao inscricao = listaInscricoesObjeto.get(listaInscricoes.getSelectedIndex());

                        try {
                            MostraInscricoes.this.setVisible(false);
                            new FormAddInscricao("edit", inscricao).setVisible(true);
                        } catch (Exception e) {
                            System.out.println("Erro edit");
                        }

                    } catch (Exception e) {
                        System.out.println("Erro edit");
                    }
                }
            }
        });
        this.add(botaoEditar);

        botaoMenuPrincipal = new JButton();
        botaoMenuPrincipal.setBounds(400, 300, 100, 30);
        botaoMenuPrincipal.setHorizontalAlignment(2);

        botaoMenuPrincipal.setText("Menu");
        botaoMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMenuPrincipalActionPerformed(evt);
            }

            private void botaoMenuPrincipalActionPerformed(ActionEvent evt) {
                MostraInscricoes.this.dispose();
                new MenuInscricao().setVisible(true);

            }
        });
        this.add(botaoMenuPrincipal);

        botaoDeletar = new JButton();
        botaoDeletar.setBounds(200, 300, 100, 30);
        botaoDeletar.setHorizontalAlignment(2);

        botaoDeletar.setText("Deletar");
        botaoDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }

            private void botaoEditarActionPerformed(ActionEvent evt) {

                if (listaInscricoes.getSelectedIndex() != -1) {
                    try {

                        Inscricao insc2 = listaInscricoesObjeto.get(listaInscricoes.getSelectedIndex());
                        InscricaoDAO inscDAO2 = new InscricaoDAO();

                        try {
                            inscDAO2.remover(insc2);

                        } catch (Exception e) {
                            System.out.println("Erro banco delete");
                        } finally {
                            MostraInscricoes.this.dispose();
                            new MenuInscricao().setVisible(true);
                        }

                    } catch (Exception e) {
                        System.out.println("Erro delete pegar index");
                    }
                }
            }
        });
        this.add(botaoDeletar);

        botaoLerFeed = new JButton();
        botaoLerFeed.setBounds(300, 300, 100, 30);
        botaoLerFeed.setHorizontalAlignment(2);

        botaoLerFeed.setText("Ler Feed");
        botaoLerFeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLerFeedActionPerformed(evt);
            }

            private void botaoLerFeedActionPerformed(ActionEvent evt) {

                if (listaInscricoes.getSelectedIndex() != -1) {
                    try {

                        Inscricao inscricao = listaInscricoesObjeto.get(listaInscricoes.getSelectedIndex());
                        inscricao = inscricao.lerArtigosPaginados(inscricao);
                        MostraInscricoes.this.dispose();
                        new MostraFeed(0, inscricao, 3).setVisible(true);

                    } catch (Exception e) {
                        System.out.println("Erro ao ler feed");
                    }
                }
            }
        });
        this.add(botaoLerFeed);

        this.setVisible(true);
    }

}
