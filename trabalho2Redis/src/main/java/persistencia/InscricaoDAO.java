/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import negocio.Artigo;
import negocio.Inscricao;
import org.xml.sax.InputSource;

/**
 *
 * @author luizd
 */
public class InscricaoDAO {

    private Conexao conexao;
    private Gson gson;

    public InscricaoDAO() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    public void atualizar(Inscricao inscricao) throws FeedException {
        inscricao.getArtigos().removeAll(inscricao.getArtigos());
        inscricao = gerarArtigosPaginados(inscricao);  
        this.conexao = new Conexao();
        if (this.conexao.getConexao().exists(inscricao.getId().toString())) {
            this.conexao.getConexao().set(inscricao.getId().toString(), this.gson.toJson(inscricao));
        }
        this.conexao.fechar();
    }

    public void adicionar(Inscricao inscricao) throws FeedException {
        inscricao = gerarArtigosPaginados(inscricao);    
        

        this.conexao = new Conexao();
        if (this.conexao.getConexao().exists(inscricao.getId().toString())) {
            inscricao.setId(UUID.randomUUID());
        }
        this.conexao.getConexao().set(inscricao.getId().toString(), this.gson.toJson(inscricao));
        this.conexao.fechar();
    }

    public Inscricao obter(UUID id) {
        this.conexao = new Conexao();
        Inscricao inscricao = this.gson.fromJson(this.conexao.getConexao().get(id.toString()), Inscricao.class);
        this.conexao.fechar();
        return inscricao;
    }

    public List<Inscricao> listar() {
        this.conexao = new Conexao();
        Set<String> vetLabels = this.conexao.getConexao().keys("*");
        Iterator iterator = vetLabels.iterator();
        List<Inscricao> vetInscricao = new ArrayList<>();
        while (iterator.hasNext()) {
            Object id = iterator.next();
            String json = this.conexao.getConexao().get(id.toString());
            Inscricao inscricao = this.gson.fromJson(json, Inscricao.class);
            vetInscricao.add(inscricao);
        }
        this.conexao.fechar();
        return vetInscricao;
    }

    public void remover(Inscricao inscricao) {
        this.conexao = new Conexao();
        this.conexao.getConexao().del(inscricao.getId().toString());
        this.conexao.fechar();
    }

    private Inscricao gerarArtigosPaginados(Inscricao inscricao) throws FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new InputSource(inscricao.getUrl()));
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

        for (int i = 0; i < inscricao.getArtigos().size(); i++) {
            Artigo artigo = inscricao.getArtigos().get(i);
            artigo.setPagina((int) Math.ceil((i + 1) / 3));
        }
        inscricao.setTotal_articles(inscricao.getArtigos().size());
        inscricao.setTotal_pages((int) Math.ceil((inscricao.getArtigos().size() + 1) / 3));
        return inscricao;
    }

    public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType,
                JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), formatter);
        }
    }

}
