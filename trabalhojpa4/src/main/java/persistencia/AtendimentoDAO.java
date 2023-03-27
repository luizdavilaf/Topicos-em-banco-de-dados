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
import negocio.Atendimento;
import negocio.Setor;

/**
 *
 * @author luizd
 */
public class AtendimentoDAO extends DAO {

   public void create(Atendimento atendimento) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();            
            entityManager.persist(atendimento);
            entityManager.getTransaction().commit();            
        } catch (Exception e) {
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Atendimento> listaAtendimentos() {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            List<Atendimento> vetAtendimento = entityManager.createNativeQuery("SELECT * FROM atendimento", Atendimento.class).getResultList();
            return vetAtendimento;
        } finally {
            entityManager.close();

        }

    }

    public Atendimento findById(int id) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Atendimento atendimento = entityManager.find(Atendimento.class, id);
            return atendimento;
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Atendimento não encontrado");
        } finally {
            entityManager.close();

        }

    }

    public void remove(Atendimento atendimento) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Atendimento atendimento2;
            try {
                atendimento2 = entityManager.getReference(Atendimento.class, atendimento.getId());
                atendimento2.getId();
            } catch (Exception e) {
                throw new IllegalCallerException("erro ao encontrar atendimento");
            }
            entityManager.remove(atendimento2);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalCallerException("erro ao apagar atendimento");
        } finally {
            entityManager.close();
        }

    }

    public Atendimento update(Atendimento atendimento) {
        entityManager = null;        
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            atendimento = entityManager.merge(atendimento);
            entityManager.getTransaction().commit();
            return atendimento;

        } catch (Exception e) {
            throw new IllegalCallerException("Atendimento com o id nao existe");
        } finally {
            entityManager.close();

        }

    }

}
