/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package apresentacao;

import com.rometools.rome.io.FeedException;

import negocio.Inscricao;
import persistencia.InscricaoDAO;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) throws IllegalArgumentException, FeedException {
        Map map = new HashMap();
        map.put("name", "Sam");
        MustacheTemplateEngine engine = new MustacheTemplateEngine();


        get("/", (req, res) -> new ModelAndView(map, "index.html"), new MustacheTemplateEngine());

        get("/new-feed", (req, res) -> new ModelAndView(map, "adicionarInscricao.html"), new MustacheTemplateEngine());

        get("/show-feeds", (req, res) -> showAll(req, res), new MustacheTemplateEngine());

        post("/save-feed", (req, res) -> {
            Inscricao inscricao = new Inscricao();
            InscricaoDAO inscricaoDAO = new InscricaoDAO();
            inscricao.setNome(req.queryParams("nome"));
            inscricao.setUrl(req.queryParams("url"));
            inscricao.setCategoria(req.queryParams("categoria"));
            try {
                inscricaoDAO.adicionar(inscricao);
            } catch (Exception e) {
                System.out.println(e);
                return "Internal server error!";
            }
            res.redirect("/");
            return "ok";
        });

    }

    private static ModelAndView showAll(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        InscricaoDAO inscricaoDAO = new InscricaoDAO();     
        try {
            model.put("feeds", inscricaoDAO.listar());
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new ModelAndView(model, "mostraFeeds.html");
    }

}