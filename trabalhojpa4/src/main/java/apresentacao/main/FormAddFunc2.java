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
import negocio.Funcionario;
import negocio.Setor;
import persistencia.FuncionarioDAO;
import persistencia.SetorDAO;

/**
 *
 * @author luizd
 */
public class FormAddFunc2 extends javax.swing.JFrame {

    javax.swing.JComboBox<String> listaSetores;
    javax.swing.JButton botaoSalvar;
    List<Setor> listaSetoresObj;
    private String origem;

    public String getOrigem() {
        return origem;
    }

    public FormAddFunc2(String origem, Funcionario funcionarioOrigem) {
        this.origem = origem;
        try {
            this.setSize(1024, 768);
            this.setTitle("MostraSetor");
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLayout(null);
            SetorDAO setorDAO = new SetorDAO();
            try {
                listaSetoresObj = setorDAO.listaSetores();

            } finally {

                List<String> list = new ArrayList<String>();
                for (int i = 0; i < listaSetoresObj.size(); i++) {
                    list.add((listaSetoresObj.get(i).getDescricao()));
                }
                String[] stringArray = list.toArray(new String[0]);
                listaSetores = new javax.swing.JComboBox<>();

                listaSetores.setModel(new javax.swing.DefaultComboBoxModel<>(stringArray));

                listaSetores.setBounds(100, 100, 100, 30);
                listaSetores.setToolTipText("setor");
                JPanel painelListaSetor = new JPanel(new FlowLayout());
                JLabel labelListaSetor = new JLabel("Setor:");
                JTextField nome = new JTextField(20);
                JPanel painelNome = new JPanel();
                JLabel labelNome = new JLabel("Nome:");
                nome.setText("nome");
                painelNome.add(labelNome);
                painelNome.add(nome);
                JTextField email = new JTextField(20);
                JPanel painelEmail = new JPanel();
                JLabel labelEmail = new JLabel("Email:");
                email.setText("Email:");
                painelEmail.add(labelEmail);
                painelEmail.add(email);
                JTextField senha = new JTextField(20);
                JPanel painelSenha = new JPanel();
                JLabel labelSenha = new JLabel("Senha:");
                senha.setText("Senha:");
                painelSenha.add(labelSenha);
                painelSenha.add(senha);
                JTextField funcao = new JTextField(20);
                JPanel painelFuncao = new JPanel();
                JLabel labelFuncao = new JLabel("Funcao:");
                funcao.setText("Funcao:");
                if(!origem.equals("add")){
                    nome.setText(funcionarioOrigem.getNome());
                    email.setText(funcionarioOrigem.getEmail());
                    senha.setText(funcionarioOrigem.getSenha());
                    funcao.setText(funcionarioOrigem.getFuncao());
                }
                painelFuncao.add(labelFuncao);
                painelFuncao.add(funcao);
                
                
                painelNome.setBounds(100, 100, 300, 30);
                painelListaSetor.setBounds(100, 140, 300, 30);
                painelEmail.setBounds(100, 180, 300, 30);
                painelSenha.setBounds(100, 220, 300, 30);
                painelFuncao.setBounds(100, 260, 300, 30);
                this.add(painelNome);
                this.add(painelEmail);
                this.add(painelSenha);
                this.add(painelFuncao);

                painelListaSetor.add(labelListaSetor);
                painelListaSetor.add(listaSetores);
                this.add(painelListaSetor);

                botaoSalvar = new JButton();
                botaoSalvar.setBounds(100, 300, 100, 30);
                

                botaoSalvar.setText("Salvar");
                botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botaoSalvarActionPerformed(evt);
                    }
                    private void botaoSalvarActionPerformed(ActionEvent evt) {
                        if (listaSetores.getSelectedIndex() != -1) {
                            try {
                                Setor setor = listaSetoresObj.get(listaSetores.getSelectedIndex());
                                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                                Funcionario funcionario = new Funcionario();
                                funcionario.setSetor(setor);
                                funcionario.setNome(nome.getText());
                                funcionario.setSenha(senha.getText());
                                funcionario.setFuncao(funcao.getText());
                                funcionario.setEmail(email.getText());
                                if(FormAddFunc2.this.getOrigem().equals("add")){
                                    funcionarioDAO.create(funcionario);
                                }else{
                                    funcionarioDAO.update(funcionario);
                                }
                                
                                

                            } catch (Exception e) {
                                System.out.println("Erro edit");
                            }finally{
                                FormAddFunc2.this.setVisible(false);
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
