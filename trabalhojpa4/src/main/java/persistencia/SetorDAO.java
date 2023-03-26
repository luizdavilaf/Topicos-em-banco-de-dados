/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import negocio.Setor;

/**
 *
 * @author luizd
 */
public class SetorDAO extends DAO {

    public void cadastraSetor(String descricao) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Setor setor = new Setor();
            setor.setDescricao(descricao);
            entityManager.persist(setor);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Setor> listaSetores() {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            List<Setor> vetSetor = entityManager.createNativeQuery("SELECT * FROM setor", Setor.class).getResultList();
            return vetSetor;
        } finally {
            entityManager.close();

        }

    }

    public Setor findById(int id) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Setor setor = entityManager.find(Setor.class, id);
            return setor;
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Setor não encontrado");
        } finally {
            entityManager.close();

        }

    }

    public void remove(Setor setor) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(setor);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalCallerException("Setor com o id nao existe");
        } finally {
            entityManager.close();
        }

    }

    public Setor update(Setor setor) {
        entityManager = null;
        System.out.println("ta antes do try");
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            setor = entityManager.merge(setor);
            entityManager.getTransaction().commit();
            return setor;

        } catch (Exception e) {
            throw new IllegalCallerException("Setor com o id nao existe");
        } finally {
            entityManager.close();

        }

    }

}
