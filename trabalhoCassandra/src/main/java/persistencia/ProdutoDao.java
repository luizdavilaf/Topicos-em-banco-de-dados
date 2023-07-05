/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.datastax.driver.core.Session;
import negocio.Produto;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.core.PreparedStatement;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.UUID;

/**
 *
 * @author luizd
 */
public class ProdutoDao {

    public ProdutoDao() {

    }

    public String salvarProduto(Produto produto) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect("exemplo");
            Mapper<Produto> pessoaMapper = new MappingManager(session).mapper(Produto.class);

            pessoaMapper.save(produto);
        } catch (Exception e) {
            System.out.println(e);
            if (cluster != null) {
                cluster.close();
            }
            return "Erro ao salvar produto";
        } finally {
            if (cluster != null) {
                cluster.close();
            }

        }
        return "Salvo";
    }

    public String atualizarProduto(Produto produto) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect("exemplo");
            String query = "UPDATE produto SET nome = ?, preco = ?, descricao = ? WHERE id = ?";
            PreparedStatement preparedStatement = session.prepare(query);
            BoundStatement boundStatement = preparedStatement.bind(produto.getNome(), produto.getPreco(), produto.getDescricao(), produto.getId());
            session.execute(boundStatement);
        } catch (Exception e) {
            System.out.println(e);
            if (cluster != null) {
                cluster.close();
            }
            return "Erro ao atualizar produto";
        } finally {
            if (cluster != null) {
                cluster.close();
            }

        }
        return "Salvo";
    }

    public String deletarProduto(UUID id) {
        Cluster cluster = null;
        Produto produto = new Produto();
        try {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect("exemplo");            
            Mapper<Produto> pessoaMapper = new MappingManager(session).mapper(Produto.class);
            pessoaMapper.delete(id);
        } catch (Exception e) {
            System.out.println(e);
            if (cluster != null) {
                cluster.close();
            }
            return "Erro ao excluir";
        } finally {
            if (cluster != null) {
                cluster.close();
            }

        }
        return "Produto excluido";
    }

    public Produto getProduto(UUID id) {
        Cluster cluster = null;
        Produto produto = new Produto();
        try {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect("exemplo");
            String query = "SELECT * FROM produto WHERE id = ?";
            PreparedStatement preparedStatement = session.prepare(query);
            BoundStatement boundStatement = preparedStatement.bind(id);
            ResultSet resultSet = session.execute(boundStatement);
            Row row = resultSet.one();
            if (row != null) {
                produto.setNome(row.getString("nome"));
                produto.setDescricao(row.getString("descricao"));
                produto.setPreco(row.getDecimal("preco"));
                produto.setId(id);

            } else {
                System.out.println("Produto não encontrada.");
            }

            cluster.close();
        } catch (Exception e) {
            System.out.println(e);
            if (cluster != null) {
                cluster.close();
            }
            return produto;
        } finally {
            if (cluster != null) {
                cluster.close();
            }

        }
        return produto;
    }

}
