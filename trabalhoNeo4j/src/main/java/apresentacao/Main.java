/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package apresentacao;

import java.util.ArrayList;
import java.util.List;
import negocio.*;
import persistencia.*;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("name", "Sam");
        
        post("/people", (req, res) -> createPerson(req, res)); 
        get("/people/:id", (req, res) -> getPerson(req, res));       
        delete("/people/:id", (req, res) -> deletePerson(req, res));       
        put("/people/:id", (req, res) -> editPerson(req, res));        
        
    }



    private static String createPerson(Request req, Response res) {        
        Gson gson = new Gson();
        String json = req.body();
        Pessoa pessoa = gson.fromJson(json, Pessoa.class);
        PessoaDAO pessoaDao = new PessoaDAO();
        int id;
        try {
            id = pessoaDao.create(pessoa);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao cadastrar Pessoa, verifique os parametros e tente novamente";
        } 
        res.status(200);
        return "cadastrado com o id: " + id;
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
        }
        res.status(200);
        return pessoa.toString();
    }


    private static String deletePerson(Request req, Response res) {
        PessoaDAO pessoaDao = new PessoaDAO();
        int id = Integer.parseInt(req.params("id"));
        try {
            pessoaDao.deleteById(id);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao deletar contato, verifique o id fornecido e tente novamente";
        } 
        res.status(200);
        return "deletado!";
    }

    private static String editPerson(Request req, Response res) {
        Gson gson = new Gson();
        String json = req.body();
        Pessoa pessoa = gson.fromJson(json, Pessoa.class);
        PessoaDAO pessoaDao = new PessoaDAO();
        pessoa.setId(Integer.parseInt(req.params("id")));
        try {
            pessoaDao.update(pessoa);
        } catch (Error e) {
            System.out.println(e);
            return "Erro ao atualizar Pessoa, verifique os parametros e tente novamente";
        } 
        res.status(200);
        return "Atualizado com sucesso!";
    }
}
