/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apresentacao.main;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import negocio.Atendimento;
import negocio.Setor;
import persistencia.AtendimentoDAO;
import persistencia.SetorDAO;

/**
 *
 * @author luizd
 */
public class MostraAtendimentos extends javax.swing.JFrame {

    javax.swing.JList<String> listaAtendimentos;
    javax.swing.JButton botaoEditar;
    javax.swing.JButton botaoMenuPrincipal;
    javax.swing.JButton botaoDeletar;
    List<Atendimento> listaFuncObj;

    public MostraAtendimentos() {
        this.setSize(1024, 768);
        this.setTitle("Mostra Atendimentos");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
        listaFuncObj = atendimentoDAO.listaAtendimentos();
        DefaultListModel<String> listaTemp = new DefaultListModel<>();
        for (int i = 0; i < listaFuncObj.size(); i++) {
            listaTemp.addElement((listaFuncObj.get(i).toString()));
        }
        listaAtendimentos = new JList<>(listaTemp);
        listaAtendimentos.setBounds(100, 100, 400, 200);

        this.add(listaAtendimentos);
        botaoEditar = new JButton();
        botaoEditar.setBounds(100, 300, 100, 30);
        botaoEditar.setHorizontalAlignment(2);
        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }

            private void botaoEditarActionPerformed(ActionEvent evt) {

                if (listaAtendimentos.getSelectedIndex() != -1) {
                    try {

                        Atendimento atendimento = listaFuncObj.get(listaAtendimentos.getSelectedIndex());

                        try {
                            MostraAtendimentos.this.setVisible(false);
                            new FormAddAtendimento("edit", atendimento).setVisible(true);
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
        botaoMenuPrincipal.setBounds(300, 300, 100, 30);
        botaoMenuPrincipal.setHorizontalAlignment(2);

        botaoMenuPrincipal.setText("Menu");
        botaoMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMenuPrincipalActionPerformed(evt);
            }

            private void botaoMenuPrincipalActionPerformed(ActionEvent evt) {
                MostraAtendimentos.this.setVisible(false);
                new MenuPrincipal().setVisible(true);

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

                if (listaAtendimentos.getSelectedIndex() != -1) {
                    try {

                        Atendimento func2 = listaFuncObj.get(listaAtendimentos.getSelectedIndex());
                        AtendimentoDAO funcdao2 = new AtendimentoDAO();
                       
                        try {
                            funcdao2.remove(func2);
                            
                        } catch (Exception e) {
                            System.out.println("Erro banco delete");
                        } finally{
                            MostraAtendimentos.this.setVisible(false);
                            new MenuPrincipal().setVisible(true);
                        }

                    } catch (Exception e) {
                        System.out.println("Erro delete pegar index");
                    }
                }
            }
        });
        this.add(botaoDeletar);

        this.setVisible(true);
    }

}
