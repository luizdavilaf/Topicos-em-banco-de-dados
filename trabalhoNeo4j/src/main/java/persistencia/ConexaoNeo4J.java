/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Query;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import static org.neo4j.driver.Values.parameters;

public class ConexaoNeo4J {

    private Driver driver;
    private Session session;

    public ConexaoNeo4J() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));

    }

    public ConexaoNeo4J(String host, String port, String username, String password) {
        this.driver = GraphDatabase.driver("bolt://" + host + ":" + port, AuthTokens.basic(username, password));

    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
