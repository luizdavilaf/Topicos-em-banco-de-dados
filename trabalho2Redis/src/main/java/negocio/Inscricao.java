/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import java.util.ArrayList;
import java.util.UUID;

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
    private int total_pages;
    private int total_articles;

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

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_articles() {
        return total_articles;
    }

    public void setTotal_articles(int total_articles) {
        this.total_articles = total_articles;
    }

    @Override
    public String toString() {
        return "[" + "nome=" + nome + ", categoria=" + categoria + ", Artigos=" + total_articles + ']';
    }
    
    
}
