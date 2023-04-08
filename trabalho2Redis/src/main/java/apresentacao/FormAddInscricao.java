/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apresentacao;

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

import negocio.Inscricao;
import persistencia.InscricaoDAO;

/**
 *
 * @author luizd
 */
public class FormAddInscricao extends javax.swing.JFrame {

    javax.swing.JButton botaoSalvar;    
    private String origem;
    private Inscricao inscricao;

    public String getOrigem() {
        return origem;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public FormAddInscricao(String origem, Inscricao inscricaoOrigem) {
        this.origem = origem;
        this.inscricao = inscricaoOrigem;
        try {
            this.setSize(1024, 768);
            this.setTitle("Adicionar Inscricao");
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

            JTextField url = new JTextField(20);
            JPanel painelUrl = new JPanel();
            JLabel labelUrl = new JLabel("Url:");
            url.setText("Url:");
            painelUrl.add(labelUrl);
            painelUrl.add(url);

            JTextField categoria = new JTextField(20);
            JPanel painelCategoria = new JPanel();
            JLabel labelCategoria = new JLabel("Categoria:");
            categoria.setText("Categoria:");
            painelCategoria.add(labelCategoria);
            painelCategoria.add(categoria);

            if (!origem.equals("add")) {
                nome.setText(inscricaoOrigem.getNome());
                url.setText(inscricaoOrigem.getUrl());
                categoria.setText(inscricaoOrigem.getCategoria());
            } else {
                this.inscricao = new Inscricao();
            }

            painelNome.setBounds(100, 100, 300, 30);
            painelUrl.setBounds(100, 140, 300, 30);
            painelCategoria.setBounds(100, 180, 300, 30);
            this.add(painelNome);
            this.add(painelUrl);
            this.add(painelCategoria);

            botaoSalvar = new JButton();
            botaoSalvar.setBounds(100, 220, 100, 30);

            botaoSalvar.setText("Salvar");
            botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    botaoSalvarActionPerformed(evt);
                }

                private void botaoSalvarActionPerformed(ActionEvent evt) {
                    try {

                        InscricaoDAO inscricaoDAO = new InscricaoDAO();
                        inscricao = FormAddInscricao.this.getInscricao();
                        inscricao.setNome(nome.getText());
                        inscricao.setUrl(url.getText());
                        inscricao.setCategoria(categoria.getText());
                        if (FormAddInscricao.this.getOrigem().equals("add")) {
                            inscricaoDAO.adicionar(inscricao);
                        } else {
                            inscricaoDAO.atualizar(inscricao);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao salvar");
                        FormAddInscricao.this.dispose();
                        new MenuPrincipal().setVisible(true);
                    } finally {
                        FormAddInscricao.this.dispose();
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
