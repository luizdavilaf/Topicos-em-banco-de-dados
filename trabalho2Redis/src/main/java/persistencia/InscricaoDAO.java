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
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import negocio.Inscricao;

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

    public void atualizar(Inscricao inscricao) {
        this.conexao = new Conexao();
        if (this.conexao.getConexao().exists(inscricao.getId().toString())) {
            this.conexao.getConexao().set(inscricao.getId().toString(), this.gson.toJson(inscricao));
        }
        this.conexao.fechar();
    }

    public void adicionar(Inscricao inscricao) {
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
