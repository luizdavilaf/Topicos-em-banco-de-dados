/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apresentacao;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import negocio.Artigo;

import negocio.Inscricao;
import persistencia.InscricaoDAO;

/**
 *
 * @author luizd
 */
public class MostraFeed extends javax.swing.JFrame {

    javax.swing.JButton botaoPaginaAnterior;
    javax.swing.JButton botaoProximaPagina;
    javax.swing.JButton botaoMenuPrincipal;
    private int pagina;
    private Inscricao inscricao;

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public MostraFeed(int pagina, Inscricao inscricaoOrigem) {
        this.pagina = pagina;
        int elemento = pagina * 3;
        int primeiroElemento = pagina * 3;

        this.inscricao = inscricaoOrigem;
        try {
            this.setSize(1280, 768);
            this.setTitle("Ler Feed");
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLayout(null);

            try {
                Artigo artigo = inscricaoOrigem.getArtigos().get(elemento);
                JTextArea conteudoArt1 = new JTextArea(artigo.getConteudo().toString());
                JPanel painelArtigo1 = new JPanel();
                JLabel labelArtigo1 = new JLabel(artigo.toString());
                painelArtigo1.add(labelArtigo1);
                painelArtigo1.add(conteudoArt1);
                painelArtigo1.setBounds(50, 30, 900, 180);

                conteudoArt1.setLineWrap(true);
                conteudoArt1.setWrapStyleWord(true);

                JScrollPane scrooll = new JScrollPane(conteudoArt1);

                scrooll.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                scrooll.setPreferredSize(new Dimension(900, 130));

                scrooll.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createTitledBorder(""),
                                        BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                                scrooll.getBorder()));
                painelArtigo1.add(scrooll);

                painelArtigo1.setAlignmentX(LEFT_ALIGNMENT);

                this.add(painelArtigo1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Sem artigo");
            } finally {
                elemento = elemento + 1;

                try {
                    Artigo artigo2 = inscricaoOrigem.getArtigos().get(elemento);
                    JTextArea conteudoArt2 = new JTextArea(artigo2.getConteudo().toString());
                    JPanel painelArtigo2 = new JPanel();
                    JLabel labelArtigo2 = new JLabel(artigo2.toString());

                    painelArtigo2.add(labelArtigo2);
                    painelArtigo2.add(conteudoArt2);
                    painelArtigo2.setBounds(50, 250, 900, 180);

                    conteudoArt2.setLineWrap(true);
                    conteudoArt2.setWrapStyleWord(true);

                    JScrollPane scrooll = new JScrollPane(conteudoArt2);

                    scrooll.setVerticalScrollBarPolicy(
                            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                    scrooll.setPreferredSize(new Dimension(900, 130));

                    scrooll.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createCompoundBorder(
                                            BorderFactory.createTitledBorder(""),
                                            BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                                    scrooll.getBorder()));
                    painelArtigo2.add(scrooll);

                    painelArtigo2.setAlignmentX(LEFT_ALIGNMENT);

                    this.add(painelArtigo2);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sem artigo");
                } finally {
                    elemento = elemento + 1;
                    try {
                        Artigo artigo3 = inscricaoOrigem.getArtigos().get(elemento);
                        JTextArea conteudoArt3 = new JTextArea(artigo3.getConteudo().toString());
                        JPanel painelArtigo3 = new JPanel();
                        JLabel labelArtigo3 = new JLabel(artigo3.toString());
                        painelArtigo3.add(labelArtigo3);
                        painelArtigo3.add(conteudoArt3);
                        painelArtigo3.setBounds(50, 470, 900, 180);

                        conteudoArt3.setLineWrap(true);
                        conteudoArt3.setWrapStyleWord(true);

                        JScrollPane scrooll = new JScrollPane(conteudoArt3);

                        scrooll.setVerticalScrollBarPolicy(
                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                        scrooll.setPreferredSize(new Dimension(900, 130));

                        scrooll.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createCompoundBorder(
                                                BorderFactory.createTitledBorder(""),
                                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                                        scrooll.getBorder()));
                        painelArtigo3.add(scrooll);

                        painelArtigo3.setAlignmentX(LEFT_ALIGNMENT);
                        this.add(painelArtigo3);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Sem artigo");
                    }

                }

            }

            try {
                inscricaoOrigem.getArtigos().get(primeiroElemento - 1);
                botaoPaginaAnterior = new JButton();
                botaoPaginaAnterior.setBounds(100, 670, 200, 50);

                botaoPaginaAnterior.setText("Pagina Anterior");
                botaoPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botaoPaginaAnteriorActionPerformed(evt);
                    }

                    private void botaoPaginaAnteriorActionPerformed(ActionEvent evt) {
                        MostraFeed.this.dispose();
                        new MostraFeed(pagina - 1, inscricao).setVisible(true);
                    }
                });
                this.add(botaoPaginaAnterior);
            } catch (IndexOutOfBoundsException e) {

            }

            try {
                inscricaoOrigem.getArtigos().get(primeiroElemento + 3);
                botaoProximaPagina = new JButton();
                botaoProximaPagina.setBounds(301, 670, 200, 50);

                botaoProximaPagina.setText("Proxima Pagina");
                botaoProximaPagina.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botaoProximaPaginaActionPerformed(evt);
                    }

                    private void botaoProximaPaginaActionPerformed(ActionEvent evt) {
                        MostraFeed.this.dispose();
                        new MostraFeed(pagina + 1, inscricao).setVisible(true);
                    }
                });
                this.add(botaoProximaPagina);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Sem artigo");
            }

            botaoMenuPrincipal = new JButton();
            botaoMenuPrincipal.setBounds(501, 670, 200, 50);

            botaoMenuPrincipal.setText("Menu");
            botaoMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    botaoMenuPrincipalActionPerformed(evt);
                }

                private void botaoMenuPrincipalActionPerformed(ActionEvent evt) {
                    MostraFeed.this.dispose();
                    new MenuInscricao().setVisible(true);

                }
            });
            this.add(botaoMenuPrincipal);

        } finally {
            this.setVisible(true);
        }

    }

}
