/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apresentacao.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import negocio.Atendimento;
import negocio.Funcionario;
import negocio.Paciente;
import persistencia.AtendimentoDAO;
import persistencia.FuncionarioDAO;
import persistencia.PacienteDAO;

/**
 *
 * @author luizd
 */
public class FormAddAtendimento extends javax.swing.JFrame {

    javax.swing.JComboBox<String> listaPacientees;
    javax.swing.JComboBox<String> listaFuncionarios;
    javax.swing.JButton botaoSalvar;
    List<Paciente> listaPacienteesObj;
    List<Funcionario> listaFuncObj;
    private String origem;
    private Atendimento atendimento;

    public String getOrigem() {
        return origem;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public FormAddAtendimento(String origem, Atendimento atendimentoOrigem) {
        this.origem = origem;
        this.atendimento = atendimentoOrigem;
        try {
            this.setSize(1024, 768);
            this.setTitle("Mostra Atendimento");
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLayout(null);
            PacienteDAO pacienteDAO = new PacienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            try {
                listaPacienteesObj = pacienteDAO.listaPacientes();
                listaFuncObj = funcionarioDAO.listaFuncionarios();

            } finally {

                List<String> list = new ArrayList<String>();
                List<String> listPacient = new ArrayList<String>();
                for (int i = 0; i < listaPacienteesObj.size(); i++) {
                    listPacient.add((listaPacienteesObj.get(i).getNome()));
                    
                }
                for (int i = 0; i < listaFuncObj.size(); i++) {
                    list.add((listaFuncObj.get(i).getNome()));
                }
                String[] stringArray = list.toArray(new String[0]);
                listaPacientees = new javax.swing.JComboBox<>();
                listaPacientees.setModel(new javax.swing.DefaultComboBoxModel<>(stringArray));
                listaPacientees.setBounds(100, 100, 100, 30);
                listaPacientees.setToolTipText("paciente");
                JPanel painelListaPaciente = new JPanel(new FlowLayout());
                JLabel labelListaPaciente = new JLabel("Paciente:");
                painelListaPaciente.add(labelListaPaciente);
                painelListaPaciente.add(listaPacientees);
                painelListaPaciente.setBounds(100, 140, 300, 30);
                this.add(painelListaPaciente);
                
                
                String[] stringArrayPaciente = listPacient.toArray(new String[0]);
                listaFuncionarios = new javax.swing.JComboBox<>();
                listaFuncionarios.setModel(new javax.swing.DefaultComboBoxModel<>(stringArrayPaciente));
                listaFuncionarios.setBounds(100, 100, 100, 30);
                listaFuncionarios.setToolTipText("paciente");
                JPanel painelListaFuncionario = new JPanel(new FlowLayout());
                JLabel labelListaFuncionario = new JLabel("Funcionario:");
                painelListaFuncionario.add(labelListaFuncionario);
                painelListaFuncionario.add(listaFuncionarios);
                painelListaFuncionario.setBounds(100, 180, 300, 30);
                this.add(painelListaFuncionario);
                
                JTextField data = new JTextField(20);
                JPanel painelData = new JPanel();
                JLabel labelData = new JLabel("Data:");
                
                
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
                Date hoje = new Date();               
                data.setText(dateFormat.format(hoje));
                painelData.add(labelData);
                painelData.add(data);
                painelData.setBounds(100, 100, 300, 30);
                this.add(painelData);
                
                JTextField observacoes = new JTextField(100);
                JPanel painelObservacoes = new JPanel();
                JLabel labelObservacoes = new JLabel("Observacoes:");
                observacoes.setText("Observacoes:");
                painelObservacoes.add(labelObservacoes);
                painelObservacoes.add(observacoes);
                painelData.setBounds(100, 220, 300, 30);
                this.add(painelObservacoes);
                
                
               
                if(!origem.equals("add")){
                    String dateEdit = dateFormat.format(atendimentoOrigem.getData());
                    data.setText(dateEdit);
                    observacoes.setText(atendimentoOrigem.getObservacoes());                                        
                }else{
                    this.atendimento = new Atendimento();
                }
               
                botaoSalvar = new JButton();
                botaoSalvar.setBounds(100, 300, 100, 30);
                

                botaoSalvar.setText("Salvar");
                botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botaoSalvarActionPerformed(evt);
                    }
                    @SuppressWarnings("deprecation")
                    private void botaoSalvarActionPerformed(ActionEvent evt) {
                        if (listaPacientees.getSelectedIndex() != -1) {
                            try {
                                Paciente paciente = listaPacienteesObj.get(listaPacientees.getSelectedIndex());
                                Funcionario funcionario = listaFuncObj.get(listaFuncionarios.getSelectedIndex());
                                AtendimentoDAO atendimentoDAO = new AtendimentoDAO();       
                                atendimento = FormAddAtendimento.this.getAtendimento();
                                atendimento.setPaciente(paciente);
                                atendimento.setFuncionario(funcionario);
                                
                                String dateString = data.getText();
                                Date date = formatter.parse(dateString);
                                atendimento.setData(date);                                
                                atendimento.setObservacoes(observacoes.getText());
                                if(FormAddAtendimento.this.getOrigem().equals("add")){
                                    atendimentoDAO.create(atendimento);
                                }else{
                                    atendimentoDAO.update(atendimento);
                                }
                                
                                

                            } catch (Exception e) {
                                System.out.println("Erro edit");
                            }finally{
                                FormAddAtendimento.this.setVisible(false);
                                new MenuPrincipal().setVisible(true);
                            }
                            
                        }
                    }
                });
                this.add(botaoSalvar);

            }

        } finally {
            this.setVisible(true);
        }

    }

}
