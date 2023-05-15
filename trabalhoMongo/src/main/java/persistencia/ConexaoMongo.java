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
import javax.inject.Inject;

/**
 *
 * @author luizd
 */
public abstract class ConexaoMongo {
//    private static final MongoClient myClient = new MongoClient("localhost:27017");
//	private static MongoDatabase database = myClient.getDatabase("ProductDB");

    private static final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private static final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    private static final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
    MongoClientSettings clientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build();
    MongoClient mongoClient =  MongoClients.create(clientSettings);
    MongoDatabase db = mongoClient.getDatabase("trabalho_mongo");

    public MongoClient getMongoClient() {
        return this.mongoClient;
    }

    public MongoDatabase getDatabase() {
        return this.db;
    }

    
    @Inject
    public void setDataBase(MongoDatabase db) {
        this.db = db;
    }
    
    
   

}
