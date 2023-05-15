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
import org.bson.types.ObjectId;

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

        Map map = new HashMap();
        map.put("name", "Sam");
        get("/", (req, res) -> new ModelAndView(map, "index.html"), new MustacheTemplateEngine());
        get("/new-contact", (req, res) -> new ModelAndView(map, "addContact.html"), new MustacheTemplateEngine());
        post("/save-contact", (req, res) -> saveContact(req, res));
        get("/show-contacts", (req, res) -> showAll(req, res), new MustacheTemplateEngine());
        get("/delete-contact/:id", (req, res) -> deleteContact(req, res));
        get("/contact/:id", (req, res) -> getToEditContact(req, res), new MustacheTemplateEngine());
        post("/edit-contact/:id", (req, res) -> editContact(req, res));
        get("/city-search", (req, res) -> new ModelAndView(map, "formCity.html"), new MustacheTemplateEngine());
        get("/show-contacts-by-city/", (req, res) -> findByCity(req, res), new MustacheTemplateEngine());
        get("/show-more-phones", (req, res) -> findByMoreThanOnePhone(req, res), new MustacheTemplateEngine());

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

    private static ModelAndView showAll(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        ContatoDAO contatoDao = new ContatoDAO();
        try {
            model.put("contatos", contatoDao.findAll());
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }

        return new ModelAndView(model, "mostraContatos.html");
    }

    private static String deleteContact(Request req, Response res) {
        ContatoDAO contatoDao = new ContatoDAO();
        ObjectId _id = new ObjectId(req.params("id"));
        try {
            contatoDao.deleteById(_id);
        } catch (Exception e) {
            System.out.println(e);
            return "Internal server error!";
        }
        res.redirect("/");
        return "ok";

    }

    private static ModelAndView getToEditContact(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        ContatoDAO contatoDao = new ContatoDAO();
        ObjectId _id = new ObjectId(req.params("id"));
        try {
            Contato contato = contatoDao.findById(_id);
            model.put("contatos", contato);
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }
        return new ModelAndView(model, "editarContato.html");
    }

    private static String editContact(Request req, Response res) {        
        ContatoDAO contatoDao = new ContatoDAO();
        Contato contato = null;
        Contato contatoUpdateObj = null;        
        Gson gson = new Gson();
        String contatoUpdate = req.body();
        try {
            contatoUpdateObj = gson.fromJson(contatoUpdate, Contato.class);
            ObjectId id = new ObjectId(req.params("id"));
            //System.out.println(contatoUpdateObj);
            //gson cria novo objeto com id diferente, mesmo sendo passado o id no body se nao nao fazia os  sets embaixo, pegaria o body e tacava no update 
            contato = contatoDao.findById(id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                contato.setEndereco(contatoUpdateObj.getEndereco());
                contato.setTelefones(contatoUpdateObj.getTelefones());
                contato.setName(contatoUpdateObj.getName());
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    contatoDao.update(contato);
                } catch (Exception e) {
                    System.out.println(e);
                    return "Internal server error!";
                }
            }
        }

        return "atualizado";

    }

    private static ModelAndView findByCity(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        ContatoDAO contatoDao = new ContatoDAO();
        String city = req.queryParams("cidade");
        System.out.println(city);
        try {
            model.put("contatos", contatoDao.findByCity(city));
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }

        return new ModelAndView(model, "mostraContatos.html");
    }

    private static ModelAndView findByMoreThanOnePhone(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        ContatoDAO contatoDao = new ContatoDAO();        
        try {
            model.put("contatos", contatoDao.findByMoreThanOnePhone());
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }
        return new ModelAndView(model, "mostraContatos.html");
    }

}
