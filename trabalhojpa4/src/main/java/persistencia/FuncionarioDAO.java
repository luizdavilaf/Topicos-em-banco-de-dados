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
public class FuncionarioDAO extends DAO {

    public void create(String descricao) {
        entityManager.getTransaction().begin();
        Setor setor = new Setor();
        setor.setDescricao(descricao);
        entityManager.persist(setor);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Setor> listAll() {
        entityManager.getTransaction().begin();
        List<Setor> vetSetor = entityManager.createNativeQuery("SELECT * FROM setor", Setor.class).getResultList();
        return vetSetor;
    }

    public Setor findById(int id) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.find(Setor.class, id);
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Setor não encontrado");
        }

    }

    public void removeById(int id) {
        Setor setor;
        try {
            entityManager.getTransaction().begin();
            setor = entityManager.getReference(Setor.class, id);
            setor.getId();
        } catch (Exception e) {
            throw new IllegalCallerException("Setor com o id nao existe");
        }
        entityManager.remove(setor);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(Setor setor) {
        try {
            entityManager.getTransaction().begin();
            setor = entityManager.merge(setor);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalCallerException("Setor com o id nao existe");
        }
        entityManager.close();
    }

}
