/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apresentacao.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import negocio.Paciente;
import negocio.Setor;
import persistencia.PacienteDAO;
import persistencia.SetorDAO;

/**
 *
 * @author luizd
 */
public class FormAddPac extends javax.swing.JFrame {

    //javax.swing.JComboBox<String> listaSetores;
    javax.swing.JButton botaoSalvar;
    List<Setor> listaSetoresObj;
    private String origem;
    private Paciente paciente;

    public String getOrigem() {
        return origem;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public FormAddPac(String origem, Paciente pacienteOrigem) {
        this.origem = origem;
        this.paciente = pacienteOrigem;
        try {
            this.setSize(1024, 768);
            this.setTitle("Adicionar Paciente");
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLayout(null);

            JTextField nome = new JTextField(20);
            JPanel painelNome = new JPanel();
            JLabel labelNome = new JLabel("Nome:");
            nome.setText("nome");
            painelNome.add(labelNome);
            painelNome.add(nome);
            
            JTextField cpf = new JTextField(20);
            JPanel painelCpf = new JPanel();
            JLabel labelCpf = new JLabel("Cpf:");
            cpf.setText("Cpf:");
            painelCpf.add(labelCpf);
            painelCpf.add(cpf);
            
            if (!origem.equals("add")) {
                nome.setText(pacienteOrigem.getNome());
                cpf.setText(pacienteOrigem.getCpf());               
            } else {
                this.paciente = new Paciente();
            }
   

            painelNome.setBounds(100, 100, 300, 30);

            painelCpf.setBounds(100, 140, 300, 30);
            
            this.add(painelNome);
            this.add(painelCpf);
            

            botaoSalvar = new JButton();
            botaoSalvar.setBounds(100, 180, 100, 30);

            botaoSalvar.setText("Salvar");
            botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    botaoSalvarActionPerformed(evt);
                }

                private void botaoSalvarActionPerformed(ActionEvent evt) {
                    try {
                            
                            PacienteDAO pacienteDAO = new PacienteDAO();
                            paciente = FormAddPac.this.getPaciente();                           
                            paciente.setNome(nome.getText());                           
                            paciente.setCpf(cpf.getText());
                            if (FormAddPac.this.getOrigem().equals("add")) {
                                pacienteDAO.create(paciente);
                            } else {
                                pacienteDAO.update(paciente);
                            }

                        } catch (Exception e) {
                            System.out.println("Erro edit");
                        } finally {
                            FormAddPac.this.setVisible(false);
                            new MenuPrincipal().setVisible(true);
                        }
                }
            });
            this.add(botaoSalvar);

        } finally {
            this.setVisible(true);
        }

    }

}
