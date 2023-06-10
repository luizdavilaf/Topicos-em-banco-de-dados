/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package apresentacao;

import negocio.Pessoa;
import java.util.ArrayList;
import java.util.List;
import persistencia.*;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) {

        post("/people", (req, res) -> createPerson(req, res));
        get("/people/:id", (req, res) -> getPerson(req, res));
        delete("/people/:id", (req, res) -> deletePerson(req, res));
        put("/people/:id", (req, res) -> editPerson(req, res));
        post("/friendship", (req, res) -> sendFriendRequest(req, res));
        get("/friendship", (req, res) -> getFriends(req, res));
        get("/friendship-requests", (req, res) -> getFriendRequests(req, res));
        delete("/friendship", (req, res) -> deleteFriendship(req, res));

    }

    private static String createPerson(Request req, Response res) {
        try {
            PessoaDAO pessoaDao = new PessoaDAO();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            String json = req.body();
            Pessoa pessoa = gson.fromJson(json, Pessoa.class);

            int id;
            try {
                id = pessoaDao.create(pessoa);
            } catch (Error e) {
                System.out.println(e);
                return "Erro ao cadastrar Pessoa, verifique os parametros e tente novamente";
            } catch (Exception e) {
                System.out.println(e);
                return "Erro ao cadastrar Pessoa, verifique os parametros e tente novamente";
            }
            res.status(200);
            return "cadastrado com o id: " + id;
        } catch (Exception e) {
            System.out.println(e);
            return "Erro no formato de algum paramêtro";
        }

    }

    private static String getPerson(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        int id = Integer.parseInt(req.params("id"));
        Pessoa pessoa = new Pessoa();
        try {
            pessoa = pessoaDao.getById(id);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao buscar Pessoa, verifique o id fornecido e tente novamente";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar Pessoa, verifique os parametros e tente novamente";
        }
        res.status(200);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(pessoa);
    }

    private static String deletePerson(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        int id = Integer.parseInt(req.params("id"));

        try {
            pessoaDao.getById(id);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao buscar Pessoa, verifique o id fornecido e tente novamente";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar Pessoa, verifique os parametros e tente novamente";
        }
        try {
            pessoaDao.deleteById(id);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao deletar Pessoa, verifique o id fornecido e tente novamente";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao deletar Pessoa, verifique os parametros e tente novamente";
        }
        res.status(200);
        return "deletado!";
    }

    private static String editPerson(Request req, Response res) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(Integer.parseInt(req.params("id")));
        PessoaDAO pessoaDao = new PessoaDAO();
        try {
            pessoa = pessoaDao.getById(pessoa.getId());
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao buscar Pessoa, verifique o id fornecido e tente novamente";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar Pessoa, verifique os parametros e tente novamente";
        }
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            String json = req.body();
            pessoa = gson.fromJson(json, Pessoa.class);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            pessoaDao.update(pessoa);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao atualizar Pessoa, verifique os parametros e tente novamente";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao atualizar Pessoa, verifique os parametros e tente novamente";
        }
        res.status(200);
        return "Atualizado com sucesso!";
    }

    private static String sendFriendRequest(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        boolean resultado = false;
        Pessoa pessoaOrigem = new Pessoa();
        Pessoa pessoaDestino = new Pessoa();
        int idOrigem = Integer.parseInt(req.queryParams("idorigem"));
        int idDestino = Integer.parseInt(req.queryParams("iddestino"));
        try {
            pessoaOrigem = pessoaDao.getById(idOrigem);
        } catch (Error e) {
            return "Pessoa de origem nao encontrada";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar os dados da pessoa de origem";
        }
        try {
            pessoaDestino = pessoaDao.getById(idDestino);
        } catch (Error e) {
            return "Pessoa de Destino nao encontrada";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar os dados da pessoa de destino";
        }
        try {
            resultado = pessoaDao.adicionarAmigo(idOrigem, idDestino);
        } catch (Exception e) {
            return "Erro ao adicionar amigo";
        }
        res.status(200);
        if (resultado == true) {
            return "Você agora é amigo de " + pessoaDestino.getNome();
        }
        return "Pedido de amizade enviado para " + pessoaDestino.getNome();
    }

    private static String getFriends(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        List<Pessoa> pessoas = new ArrayList<>();
        Pessoa pessoaOrigem = new Pessoa();
        int idOrigem = Integer.parseInt(req.queryParams("idorigem"));
        try {
            pessoaOrigem = pessoaDao.getById(idOrigem);
        } catch (Error e) {
            return "Pessoa de origem nao encontrada";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar os dados da pessoa de origem";
        }

        try {
            pessoas = pessoaDao.verListaDeAmigos(idOrigem);
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar os amigos da pessoa de origem";
        }

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            res.status(200);
            return gson.toJson(pessoas);

        } catch (Exception e) {
            return "erro ao converter pessoas";
        }

    }

    private static String getFriendRequests(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        List<Pessoa> pessoas = new ArrayList<>();
        Pessoa pessoaOrigem = new Pessoa();
        int idOrigem = Integer.parseInt(req.queryParams("idorigem"));
        try {
            pessoaOrigem = pessoaDao.getById(idOrigem);
        } catch (Error e) {
            return "Pessoa de origem nao encontrada";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar pedidos de amizade pendentes";
        }

        try {
            pessoas = pessoaDao.verPedidosDeAmizadePendentes(idOrigem);
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar pedidos de amizade pendentes";
        }

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            res.status(200);
            return gson.toJson(pessoas);

        } catch (Exception e) {
            return "erro nos parametros";
        }

    }

    private static String deleteFriendship(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        Pessoa pessoaOrigem = new Pessoa();
        Pessoa pessoaDestino = new Pessoa();
        int idOrigem = Integer.parseInt(req.queryParams("idorigem"));
        int idDestino = Integer.parseInt(req.queryParams("iddestino"));
        try {
            pessoaOrigem = pessoaDao.getById(idOrigem);
        } catch (Error e) {
            return "Pessoa de origem nao encontrada";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar os dados da pessoa de origem";
        }
        try {
            pessoaDestino = pessoaDao.getById(idDestino);
        } catch (Error e) {
            return "Pessoa de Destino nao encontrada";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao buscar os dados da pessoa de destino";
        }
        try {
            pessoaDao.removerAmigo(idOrigem, idDestino);
        } catch (Exception e) {
            return "Erro ao remover amigo";
        }
        res.status(200);

        return "Você desfez a amizade e agora não é mais amigo de: " + pessoaDestino.getNome();
    }
}
