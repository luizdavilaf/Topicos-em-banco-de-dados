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
import negocio.Paciente;
import negocio.Paciente;
import negocio.Setor;

/**
 *
 * @author luizd
 */
public class PacienteDAO extends DAO {

    public void create(Paciente paciente) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();            
            entityManager.persist(paciente);
            entityManager.getTransaction().commit();            
        } catch (Exception e) {
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Paciente> listaPacientes() {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            List<Paciente> vetPaciente = entityManager.createNativeQuery("SELECT * FROM paciente", Paciente.class).getResultList();
            return vetPaciente;
        } finally {
            entityManager.close();

        }

    }

    public Paciente findById(int id) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Paciente paciente = entityManager.find(Paciente.class, id);
            return paciente;
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Paciente não encontrado");
        } finally {
            entityManager.close();

        }

    }

    public void remove(Paciente paciente) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Paciente paciente2;
            try {
                paciente2 = entityManager.getReference(Paciente.class, paciente.getId());
                paciente2.getId();
            } catch (Exception e) {
                throw new IllegalCallerException("erro ao encontrar paciente");
            }
            entityManager.remove(paciente2);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalCallerException("erro ao apagar paciente");
        } finally {
            entityManager.close();
        }

    }

    public Paciente update(Paciente paciente) {
        entityManager = null;        
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            paciente = entityManager.merge(paciente);
            entityManager.getTransaction().commit();
            return paciente;

        } catch (Exception e) {
            throw new IllegalCallerException("Paciente com o id nao existe");
        } finally {
            entityManager.close();

        }

    }

}
