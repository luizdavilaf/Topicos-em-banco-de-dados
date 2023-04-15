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
import java.util.UUID;

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
        


        get("/", (req, res) -> new ModelAndView(map, "index.html"), new MustacheTemplateEngine());

        get("/new-feed", (req, res) -> new ModelAndView(map, "adicionarInscricao.html"), new MustacheTemplateEngine());

        get("/show-feeds", (req, res) -> showAll(req, res), new MustacheTemplateEngine());

        post("/save-feed", (req, res) -> saveFeed(req, res) );

        get("/delete-feed/:id", (req, res) -> deleteFeed(req, res));

        get("/feed/:id", (req, res) -> getToEditFeed(req, res), new MustacheTemplateEngine());

        post("/edit-feed", (req, res) -> editFeed(req, res));

        get("/show-articles/:id", (req, res) -> showArticlesPaginated(req, res), new MustacheTemplateEngine());


    }

    private static ModelAndView showAll(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        InscricaoDAO inscricaoDAO = new InscricaoDAO();     
        try {
            model.put("feeds", inscricaoDAO.listar());
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }

        return new ModelAndView(model, "mostraFeeds.html");
    }

    private static String saveFeed(Request req, Response res) {
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

    }

    private static String deleteFeed(Request req, Response res) {       
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        UUID idUUID = UUID.fromString(req.params("id"));     
        try {
            Inscricao inscricao = inscricaoDAO.obter(idUUID);
            inscricaoDAO.remover(inscricao);            
        } catch (Exception e) {
            System.out.println(e);
            return "Internal server error!";
        }
        res.redirect("/");
        return "ok";

    }

    private static String editFeed(Request req, Response res) {
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        UUID idUUID = UUID.fromString(req.queryParams("id"));
        try {
            Inscricao inscricao = inscricaoDAO.obter(idUUID);
            inscricao.setNome(req.queryParams("nome"));
            inscricao.setUrl(req.queryParams("url"));
            inscricao.setCategoria(req.queryParams("categoria"));
            inscricaoDAO.atualizar(inscricao);
        } catch (Exception e) {
            System.out.println(e);
            return "Internal server error!";
        }
        res.redirect("/");
        return "ok";

    }


    private static ModelAndView getToEditFeed(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        UUID idUUID = UUID.fromString(req.params("id"));
        try {
            Inscricao inscricao = inscricaoDAO.obter(idUUID);           
            model.put("feeds", inscricao);
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }
        return new ModelAndView(model, "editarInscricao.html");
    }


    private static ModelAndView showArticlesPaginated(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        UUID idUUID = UUID.fromString(req.params("id"));
        try {
            Inscricao inscricao = inscricaoDAO.obter(idUUID);
            int pagina = Integer.parseInt(req.queryParams("page"));
            int limite = Integer.parseInt(req.queryParams("limit"));
            int elemento = pagina * limite;
            int total = inscricao.getArtigos().size();
            inscricao = inscricao.lerArtigosPaginados(inscricao);
            if(pagina>0){
                model.put("previous", pagina-1 );
            }
            if (pagina < total -1) {
                model.put("next", pagina + 1);
            }
            for(int i = elemento; i < limite; i++){
                if(i < total){
                   try {
                       model.put("articles", inscricao.getArtigos().get(i));
                   } catch (Exception e) {
                    // sem artigo na posicao
                   }
                }
            }            
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        }
        return new ModelAndView(model, "mostraArtigos.html");
    }
}
