/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package apresentacao;

import com.rometools.rome.io.FeedException;
import java.util.ArrayList;

import negocio.Inscricao;
import persistencia.InscricaoDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import negocio.Artigo;

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

        post("/save-feed", (req, res) -> saveFeed(req, res));

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
        ArrayList<Artigo> artigos = new ArrayList<>();;
        try {

            InscricaoDAO inscricaoDAO = new InscricaoDAO();
            UUID idUUID = UUID.fromString(req.params("id"));
            Inscricao inscricao = inscricaoDAO.obter(idUUID);

            int pagina = Integer.parseInt(req.queryParams("page"));
            int limite = Integer.parseInt(req.queryParams("limit"));
            int elemento = pagina * limite;
            int ultimoElemento = elemento + limite;
            model.put("id", req.params("id"));
            
            System.out.println(pagina);
            System.out.println(limite);
            

            try {
                inscricao = inscricao.lerArtigosPaginados(inscricao);
                int total = inscricao.getArtigos().size();
                

                if (pagina > 0) {
                    model.put("previous", pagina - 1);
                } else {
                    model.put("previous", 0);
                }
                if (pagina < total - 1) {
                    model.put("next", pagina + 1);
                } else {
                    model.put("next", pagina);
                }
               
                for (int i = elemento; i < ultimoElemento; i++) {                    
                    if (i < total) {
                        try {
                            //System.out.println(inscricao.getArtigos().get(i).toString());
                            artigos.add(inscricao.getArtigos().get(i));
                        } catch (Exception e) {
                            System.out.println("sem artigo");
                        }
                    }
                }
            } finally {
                model.put("articles", artigos);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            res.redirect("/");
        } finally {
            
            return new ModelAndView(model, "mostraArtigos.html");
        }

    }
}
