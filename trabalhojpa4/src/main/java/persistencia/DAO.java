/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author luizd
 */
public abstract class DAO {
    
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MinhaPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    

    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
