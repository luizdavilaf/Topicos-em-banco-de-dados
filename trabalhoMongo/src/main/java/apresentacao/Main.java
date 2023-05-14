/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package apresentacao;

import java.util.ArrayList;
import java.util.List;
import negocio.*;
import persistencia.*;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.bson.json.JsonReader;

import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.Request;
import spark.Response;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) throws Exception {
//ADICIONANDO CONTATO
//        Contato luiz = new Contato();
//        luiz.setName("Luiz");
//        Endereco novoEndereco;
//        novoEndereco = new Endereco("Rio Grande", "avenida", "cassino", 0, "");
//        luiz.setEndereco(novoEndereco);
//        luiz.getTelefones().add("99995454554545");
//        luiz.getTelefones().add("11111111111111");//        
//        ContatoDAO contatoDao = new ContatoDAO();
//        contatoDao.create(luiz);

//LISTA TODOS
//        ContatoDAO contatoDao = new ContatoDAO();
//        List<Contato> contatos = new ArrayList();
//        contatos = contatoDao.findAll();        
//        for (int i = 0; i < contatos.size(); i++) {
//            Contato get = contatos.get(i);
//            System.out.println(get.toString());            
//        }
//BUSCA POR CIDADE
//        ContatoDAO contatoDao = new ContatoDAO();
//        List<Contato> contatos = new ArrayList();
//        contatos = contatoDao.findByCity("Jardia");
//        System.out.println(contatos.size());
//        for (int i = 0; i < contatos.size(); i++) {
//            Contato get = contatos.get(i);
//            System.out.println(get.toString());            
//        }
//BUSCA POR MAIS DE UM TELEFONE
//        ContatoDAO contatoDao = new ContatoDAO();
//        List<Contato> contatos = new ArrayList();
//        contatos = contatoDao.findByMoreThanOnePhone();
//        System.out.println(contatos.size());
//        for (int i = 0; i < contatos.size(); i++) {
//            Contato get = contatos.get(i);
//            System.out.println(get.toString());            
//        }
        /* 

        <button onclick="location.href='/show-contacts'" type="button" class="btn btn-danger">

        <button onclick="location.href='/city-search'" type="button" class="btn btn-danger">
 
         <button onclick="location.href='/show-more-phones'" type="button" class="btn btn-danger"> */
        Map map = new HashMap();
        map.put("name", "Sam");
        get("/", (req, res) -> new ModelAndView(map, "index.html"), new MustacheTemplateEngine());
        get("/new-contact", (req, res) -> new ModelAndView(map, "addContact.html"), new MustacheTemplateEngine());
        post("/save-contact", (req, res) -> saveContact(req, res));

    }

    private static String saveContact(Request req, Response res) {

        res.type("application/json");
        Gson gson = new Gson();
        String json = req.body();
        Contato contato = gson.fromJson(json, Contato.class);
        ContatoDAO contatoDao = new ContatoDAO();
        try {
            contatoDao.create(contato);
        } catch (Exception e) {
            System.out.println(e);
            return "Internal server error!";
        } finally {
            res.status(200);          
            
            return "cadastrado";
        }

    }
}
