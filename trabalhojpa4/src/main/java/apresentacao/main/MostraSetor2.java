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
import negocio.Setor;
import persistencia.SetorDAO;

/**
 *
 * @author luizd
 */
public class MostraSetor2 extends javax.swing.JFrame {

    javax.swing.JList<String> listaSetores;
    javax.swing.JButton botaoEditar;
    javax.swing.JButton botaoMenuPrincipal;
    javax.swing.JButton botaoDeletar;
    List<Setor> listaSetoresObj;

    public MostraSetor2() {
        this.setSize(1024, 768);
        this.setTitle("MostraSetor");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        SetorDAO setorDAO = new SetorDAO();
        listaSetoresObj = setorDAO.listaSetores();
        DefaultListModel<String> listaTemp = new DefaultListModel<>();
        for (int i = 0; i < listaSetoresObj.size(); i++) {
            listaTemp.addElement((listaSetoresObj.get(i).getDescricao()));
        }
        listaSetores = new JList<>(listaTemp);
        listaSetores.setBounds(100, 100, 400, 200);

        this.add(listaSetores);
        botaoEditar = new JButton();
        botaoEditar.setBounds(100, 300, 100, 30);
        botaoEditar.setHorizontalAlignment(2);

        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }

            private void botaoEditarActionPerformed(ActionEvent evt) {

                if (listaSetores.getSelectedIndex() != -1) {
                    try {

                        Setor setor = listaSetoresObj.get(listaSetores.getSelectedIndex());

                        try {
                            MostraSetor2.this.setVisible(false);
                            new FormAddSetor("edit", setor).setVisible(true);
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
                MostraSetor2.this.setVisible(false);
                new MenuPrincipal().setVisible(true);

            }
        });
        this.add(botaoMenuPrincipal);
        
        botaoDeletar = new JButton();
        botaoDeletar.setBounds(200, 300, 100, 30);
        botaoDeletar.setHorizontalAlignment(2);

        botaoDeletar.setText("Deltar");
        botaoDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }

            private void botaoEditarActionPerformed(ActionEvent evt) {

                if (listaSetores.getSelectedIndex() != -1) {
                    try {

                        Setor setor = listaSetoresObj.get(listaSetores.getSelectedIndex());

                        try {
                            MostraSetor2.this.setVisible(false);
                            new FormAddSetor("edit", setor).setVisible(true);
                        } catch (Exception e) {
                             System.out.println("Erro edit");
                        }
                       
                    } catch (Exception e) {
                        System.out.println("Erro edit");
                    }
                }
            }
        });
        this.add(botaoDeletar);

        this.setVisible(true);
    }

   

}
