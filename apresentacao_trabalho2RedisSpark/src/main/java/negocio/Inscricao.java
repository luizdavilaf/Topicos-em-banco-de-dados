/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

/**
 *
 * @author luizd
 */
public class Inscricao {
    
    private UUID id;
    private String nome;
    private String url;    
    private String categoria;
    private ArrayList<Artigo> artigos;


    public Inscricao() {
        this.id = UUID.randomUUID();
        this.artigos = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(ArrayList<Artigo> artigos) {
        this.artigos = artigos;
    }



    @Override
    public String toString() {
        return "[" + "nome:" + nome + ", categoria:" + categoria + ", URL:" + url + ']';
    }


    public Inscricao lerArtigosPaginados(Inscricao inscricao) throws FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new InputSource(inscricao.getUrl()));
        inscricao.getArtigos().removeAll(artigos);
        Iterator itr = feed.getEntries().iterator();
        while (itr.hasNext()) {
            Artigo artigo = new Artigo();
            SyndEntry syndEntry = (SyndEntry) itr.next();
            artigo.setAutor(syndEntry.getAuthor());
            artigo.setLink(syndEntry.getLink());
            List<SyndContent> conteudos = syndEntry.getContents();
            String result = "";
            for (SyndContent conteudo : conteudos) {
                result = result + conteudo.getValue();
            }
            String conteudoFinal = result.replaceAll("\\n", "");
            conteudoFinal = conteudoFinal.replaceAll("<[^>]*>", "");
            artigo.setConteudo(conteudoFinal);
            artigo.setTitulo(syndEntry.getTitle());
            artigo.setData(syndEntry.getPublishedDate());
            inscricao.getArtigos().add(artigo);
        }
        inscricao.getArtigos().sort(Comparator.comparing(Artigo::getData).reversed());
        return inscricao;
    }
    
    
}
