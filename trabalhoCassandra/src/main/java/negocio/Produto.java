/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author luizd
 */
@Table(keyspace = "exemplo", name = "produto")
public class Produto {

    @PartitionKey
    private UUID id;
    private String nome;
    private BigDecimal preco;
    private String descricao;

    public Produto(String nome, BigDecimal preco, String descricao) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Produto(UUID id, String nome, BigDecimal preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Produto() {

    }

    @PartitionKey
    public UUID getId() {
        return id;
    }

    @PartitionKey
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome=" + nome + ", preco=" + preco + ", descricao=" + descricao + '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
