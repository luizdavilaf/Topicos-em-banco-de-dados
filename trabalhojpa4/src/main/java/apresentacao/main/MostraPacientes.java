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
import negocio.Paciente;
import negocio.Setor;
import persistencia.PacienteDAO;
import persistencia.SetorDAO;

/**
 *
 * @author luizd
 */
public class MostraPacientes extends javax.swing.JFrame {

    javax.swing.JList<String> listaPacientes;
    javax.swing.JButton botaoEditar;
    javax.swing.JButton botaoMenuPrincipal;
    javax.swing.JButton botaoDeletar;
    List<Paciente> listaFuncObj;

    public MostraPacientes() {
        this.setSize(1024, 768);
        this.setTitle("Mostra Pacientes");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        PacienteDAO pacienteDAO = new PacienteDAO();
        listaFuncObj = pacienteDAO.listaPacientes();
        DefaultListModel<String> listaTemp = new DefaultListModel<>();
        for (int i = 0; i < listaFuncObj.size(); i++) {
            listaTemp.addElement((listaFuncObj.get(i).getNome()));
        }
        listaPacientes = new JList<>(listaTemp);
        listaPacientes.setBounds(100, 100, 400, 200);

        this.add(listaPacientes);
        botaoEditar = new JButton();
        botaoEditar.setBounds(100, 300, 100, 30);
        botaoEditar.setHorizontalAlignment(2);
        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }

            private void botaoEditarActionPerformed(ActionEvent evt) {

                if (listaPacientes.getSelectedIndex() != -1) {
                    try {

                        Paciente paciente = listaFuncObj.get(listaPacientes.getSelectedIndex());

                        try {
                            MostraPacientes.this.setVisible(false);
                            new FormAddPac("edit", paciente).setVisible(true);
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
                MostraPacientes.this.setVisible(false);
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

                if (listaPacientes.getSelectedIndex() != -1) {
                    try {

                        Paciente func2 = listaFuncObj.get(listaPacientes.getSelectedIndex());
                        PacienteDAO funcdao2 = new PacienteDAO();
                       
                        try {
                            funcdao2.remove(func2);
                            
                        } catch (Exception e) {
                            System.out.println("Erro banco delete");
                        } finally{
                            MostraPacientes.this.setVisible(false);
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
