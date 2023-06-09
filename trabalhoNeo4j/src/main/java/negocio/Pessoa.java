/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;

/**
 *
 * @author luizd
 */
public class Pessoa {
    @Expose
    private int id;
    @Expose
    private String cpf;
    @Expose
    private String nome;
    @Expose
    private String email;    
    private String senha;
    @Expose
    private LocalDate dataDeNascimento;

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate data_de_nascimento) {
        this.dataDeNascimento = data_de_nascimento;
    }

    @Override
    public String toString() {
        return "Pessoa [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", email=" + email + ", data_de_nascimento="
                + dataDeNascimento + "]";
    }

}
