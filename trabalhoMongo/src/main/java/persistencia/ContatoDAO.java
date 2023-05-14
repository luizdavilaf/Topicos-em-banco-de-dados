/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import negocio.*;

/**
 *
 * @author luizd
 */
public class ContatoDAO extends ConexaoMongo {    
    
    
    public void create(Contato contato) throws Exception{      
        try {           
            db = getDatabase();
            MongoCollection<Contato> contatoCollection = db.getCollection("Contato", Contato.class);
            contatoCollection.insertOne(contato);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o contato");
        }
        
    }

}
