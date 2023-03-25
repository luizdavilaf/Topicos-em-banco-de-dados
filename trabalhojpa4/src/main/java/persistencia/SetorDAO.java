/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import negocio.Setor;

/**
 *
 * @author luizd
 */
public class SetorDAO extends DAO{

    

    public void cadastraSetor(String descricao) {
        entityManager.getTransaction().begin();
        Setor setor = new Setor();
        setor.setDescricao(descricao);
        entityManager.persist(setor);
        entityManager.getTransaction().commit();       
        
    }

    public List<Setor> listaSetores() {
        entityManager.getTransaction().begin();
        List<Setor> vetSetor = entityManager.createNativeQuery("SELECT * FROM setor", Setor.class).getResultList();        
        return vetSetor;
    }

}
