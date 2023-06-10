/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.Pessoa;
import org.neo4j.driver.Query;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import static org.neo4j.driver.Values.parameters;
import static org.neo4j.driver.Values.value;
import org.neo4j.driver.types.Node;

public class PessoaDAO {

    private ConexaoNeo4J conexaoNeo4J;
    private Gson gson;

    public PessoaDAO() {
        this.conexaoNeo4J = new ConexaoNeo4J();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public PessoaDAO(ConexaoNeo4J conexaoNeo4J) {
        this.conexaoNeo4J = conexaoNeo4J;
    }

    public int create(Pessoa pessoa) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "CREATE (p:Pessoa {nome: $nome, cpf: $cpf, email: $email,senha: $senha,dataDeNascimento: $dataDeNascimento}) return p",
                    parameters("nome", pessoa.getNome(), "cpf", pessoa.getCpf(), "email", pessoa.getEmail(),
                            "senha", pessoa.getSenha(), "dataDeNascimento", pessoa.getDataDeNascimento()));
            Result result = session.run(query);
            if (result.hasNext()) {
                Value v = result.next().get(0);
                Node node = v.asNode();
                long nodeId = node.id();
                pessoa.setId((int) nodeId);
            }
        } catch (Exception e) {
            throw new Error("Erro ao cadastrar pessoa");
        }
        return pessoa.getId();
    }

    public Pessoa getById(int id) {
        Pessoa pessoa = new Pessoa();
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query("MATCH (p:Pessoa) where ID(p) = $id RETURN p", parameters("id", id));
            Result result = session.run(query);
            if (result.hasNext()) {
                Value v = result.next().get(0);
                pessoa.setId(id);
                pessoa.setCpf(v.get("cpf").asString());
                pessoa.setNome(v.get("nome").asString());
                pessoa.setEmail(v.get("email").asString());
                pessoa.setSenha(v.get("senha").asString());
                pessoa.setDataDeNascimento(v.get("dataDeNascimento").asLocalDate());
            } else {
                throw new Error("Pessoa não encontrada");
            }
        } catch (Exception e) {
            throw new Error("Erro ao buscar pessoa");
        }
        return pessoa;
    }

    public boolean update(Pessoa pessoa) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "MATCH (p:Pessoa) where ID(p) = $id set p.nome = $nome, p.cpf = $cpf, p.email = $email, p.senha = $senha, p.dataDeNascimento = $dataDeNascimento",
                    parameters("id", pessoa.getId(), "nome", pessoa.getNome(), "cpf", pessoa.getCpf(), "email",
                            pessoa.getEmail(),
                            "senha", pessoa.getSenha(), "dataDeNascimento", pessoa.getDataDeNascimento()));
            session.run(query);
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("Erro ao atualizar pessoa");
        }
        return true;
    }

    public boolean deleteById(int id) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            //remove somente se nao tiver relacionamentos
            //Query query = new Query("MATCH (p:Pessoa) where ID(p) = $id delete p", parameters("id", id));
            //remove a pessoa e seus relacionamentos
            Query query = new Query("MATCH (p:Pessoa) WHERE ID(p) = $id DETACH DELETE p", parameters("id", id));
            session.run(query);
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("Erro ao remover pessoa");
        }
        return true;

    }

    public boolean adicionarAmigo(int idOrigem, int idDestino) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "MATCH (p1:Pessoa), (p2:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino CREATE (p1)-[:ADICIONOU]->(p2)",
                    parameters("idOrigem", idOrigem, "idDestino", idDestino));
            session.run(query);
        }
        return this.verificarAmizade(idDestino, idOrigem);
    }

    public void removerAmigo(int idOrigem, int idDestino) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "MATCH (p1: Pessoa)-[a:ADICIONOU]->(p2:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino DELETE a",
                    parameters("idOrigem", idOrigem, "idDestino", idDestino));
            session.run(query);
        }
    }

    public boolean verificarAmizade(int id1, int id2) {

        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query1 = new Query(
                    "MATCH (p1: Pessoa)-[a:ADICIONOU]->(p2:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino RETURN a",
                    parameters("idOrigem", id1, "idDestino", id2));
            Result result1 = session.run(query1);

            Query query2 = new Query(
                    "MATCH (p2: Pessoa)-[a:ADICIONOU]->(p1:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino RETURN a",
                    parameters("idOrigem", id2, "idDestino", id1));
            Result result2 = session.run(query2);
            // System.out.println(result2.hasNext());
            return result1.hasNext() && result2.hasNext();

        }
    }

    public List<Pessoa> verPedidosDeAmizadePendentes(int id) {
        List<Pessoa> pessoas = new ArrayList<>();
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query("MATCH (p1)<-[r:ADICIONOU]-(p2) "
                    + "WHERE id(p1) = $id AND NOT exists((p1)-[:ADICIONOU]->(p2)) "
                    + "RETURN p2", parameters("id", id));
            Result result = session.run(query);
            while (result.hasNext()) {
                Pessoa pessoa = new Pessoa();
                org.neo4j.driver.Record record = result.next();

                Value p2Value = record.get("p2");
                Node node = p2Value.asNode();
                long nodeId = node.id();
                pessoa.setId((int) nodeId);
                Value cpfValue = node.get("cpf");
                pessoa.setCpf(cpfValue.asString());

                Value nomeValue = node.get("nome");
                pessoa.setNome(nomeValue.asString());

                Value emailValue = node.get("email");
                pessoa.setEmail(emailValue.asString());

                Value dataDeNascimentoValue = node.get("dataDeNascimento");
                pessoa.setDataDeNascimento(dataDeNascimentoValue.asLocalDate());

                pessoas.add(pessoa);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("Erro ao buscar pedidos de amizade pendentes");
        } finally {
            return pessoas;
        }
    }

    public List<Pessoa> verListaDeAmigos(int id) {
        List<Pessoa> pessoas = new ArrayList<>();
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query("MATCH (p1)-[r:ADICIONOU]->(p2) "
                    + "WHERE id(p1) = $id AND (p2)-[:ADICIONOU]->(p1) "
                    + "RETURN p2", parameters("id", id));
            Result result = session.run(query);
            while (result.hasNext()) {
                Pessoa pessoa = new Pessoa();
                org.neo4j.driver.Record record = result.next();

                Value p2Value = record.get("p2");
                Node node = p2Value.asNode();
                long nodeId = node.id();
                pessoa.setId((int) nodeId);
                Value cpfValue = node.get("cpf");
                pessoa.setCpf(cpfValue.asString());

                Value nomeValue = node.get("nome");
                pessoa.setNome(nomeValue.asString());

                Value emailValue = node.get("email");
                pessoa.setEmail(emailValue.asString());

                Value dataDeNascimentoValue = node.get("dataDeNascimento");
                pessoa.setDataDeNascimento(dataDeNascimentoValue.asLocalDate());

                pessoas.add(pessoa);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("Erro ao buscar amigos");
        } finally {
            // System.out.println(pessoas);
            return pessoas;
        }

    }

}
