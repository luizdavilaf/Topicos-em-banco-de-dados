/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import negocio.Pessoa;
import org.neo4j.driver.Query;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import static org.neo4j.driver.Values.parameters;

public class PessoaDAO {

    private ConexaoNeo4J conexaoNeo4J;

    public PessoaDAO() {
        this.conexaoNeo4J = new ConexaoNeo4J();
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
                pessoa.setId(v.get("id").asInt());
            }
        } catch (Error e) {
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
            }
        } catch (Error e) {
            throw new Error("Erro ao buscar pessoa");
        } 
        return pessoa;
    }

    public boolean update(Pessoa pessoa) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "MATCH (p:Pessoa) where ID(p) = $id set p.nome = $nome, p.cpf = $cpf, p.email = $email, p.senha = $senha, p.dataDeNascimento = $dataDeNascimento",
                    parameters("nome", pessoa.getNome(), "cpf", pessoa.getCpf(), "email", pessoa.getEmail(),
                            "senha", pessoa.getSenha(), "dataDeNascimento", pessoa.getDataDeNascimento()));
            session.run(query);
        } catch (Error e) {
            throw new Error("Erro ao atualizar pessoa");
        } 
        return true;
    }

    public boolean deleteById(int id) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query("MATCH (p:Pessoa) where ID(p) = $id delete p", parameters("id", id));
            session.run(query);
        } catch (Error e) {
            throw new Error("Erro ao remover pessoa");
        } 
        return true;

    }

    /* public boolean curtir(int idOrigem, int idDestino) {

        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "MATCH (p1:Pessoa), (p2:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino CREATE (p1)-[:CURTIU]->(p2)",
                    parameters("idOrigem", idOrigem, "idDestino", idDestino));

            session.run(query);
            // session.run(query);

        }
        return this.verificarMatch(idDestino, idOrigem);

    }

    public void descurtir(int idOrigem, int idDestino) {
        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query = new Query(
                    "MATCH (p1: Pessoa)-[a:CURTIU]->(p2:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino DELETE a",
                    parameters("idOrigem", idOrigem, "idDestino", idDestino));
            Result result = session.run(query);

        }
    }

    public boolean verificarMatch(int x, int y) {

        try (Session session = this.conexaoNeo4J.getDriver().session()) {
            Query query1 = new Query(
                    "MATCH (p1: Pessoa)-[a:CURTIU]->(p2:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino RETURN a",
                    parameters("idOrigem", x, "idDestino", y));
            Result result1 = session.run(query1);

            Query query2 = new Query(
                    "MATCH (p2: Pessoa)-[a:CURTIU]->(p1:Pessoa) Where ID(p1) = $idOrigem and ID(p2) = $idDestino RETURN a",
                    parameters("idOrigem", y, "idDestino", x));
            Result result2 = session.run(query2);
            // System.out.println(result2.hasNext());
            return result1.hasNext() && result2.hasNext();

        }
    } */
}
