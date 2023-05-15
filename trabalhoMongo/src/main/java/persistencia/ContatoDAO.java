/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import negocio.*;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.size;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.where;
import static com.mongodb.client.model.Filters.exists;

/**
 *
 * @author luizd
 */
public class ContatoDAO extends ConexaoMongo {
    
    public void create(Contato contato) throws Exception {
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contatoCollection.insertOne(contato);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o contato");
        }
    }
    
    public List<Contato> findAll() {
        List<Contato> contatos = new ArrayList();
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contatos = contatoCollection.find().into(new ArrayList());
        } catch (Exception e) {
            throw new Exception("Erro ao buscas contatos");
        } finally {
            return contatos;
        }
    }
    
    public Contato readByName(String nome) {
        Contato contato = null;
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contato = contatoCollection.find(eq("name", nome)).first();
        } catch (Exception e) {
            throw new Exception("Erro ao buscar contato com o nome " + nome);
        } finally {
            return contato;
        }
    }
    
    public void update(Contato contato) throws Exception {
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            Bson updateNome = set("name", contato.getName());
            Bson updateEndereco = set("endereco", contato.getEndereco());
            Bson updateTelefones = set("telefones", contato.getTelefones());
            Bson updates = combine(updateNome, updateEndereco, updateTelefones);
            contatoCollection.updateOne(eq("_id", contato.getId()), updates);
        } catch (Exception e) {
            System.err.println(e);
            throw new Exception("Erro ao atualizar contato");
        } finally {
            System.out.println("atualizado");
        }
    }
    
    public void delete(Contato contato) throws Exception {
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);            
            contatoCollection.findOneAndDelete(eq("_id", contato.getId()));
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar contato");
        } finally {
            System.out.println("deletado");
        }
    }
    
    public List<Contato> findByCity(String cidade) {
        List<Contato> contatos = new ArrayList();
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contatos = contatoCollection.find(eq("endereco.cidade", cidade)).into(new ArrayList());
        } catch (Exception e) {
            throw new Exception("Erro ao buscas contatos");
        } finally {
            return contatos;
        }
    }
    
        public List<Contato> findByMoreThanOnePhone() {
        List<Contato> contatos = new ArrayList();
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contatos = contatoCollection.find(exists("telefones.1", true)).into(new ArrayList());
        } catch (Exception e) {
            throw new Exception("Erro ao buscas contatos");
        } finally {
            return contatos;
        }
    }

    public void deleteById(ObjectId _id) throws Exception {
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contatoCollection.findOneAndDelete(eq("_id", _id));
        } catch (Exception e) {
            throw new Exception("Erro ao deletar contato");
        } finally {
            System.out.println("deletado");
        }
    }

    public Contato findById(ObjectId _id) {
        Contato contato = null;
        try {
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contato = contatoCollection.find(eq("_id", _id)).first();
        } catch (Exception e) {
            throw new Exception("Erro ao buscar contato com o nome " + _id);
        } finally {
            return contato;
        }
    }
    
}
