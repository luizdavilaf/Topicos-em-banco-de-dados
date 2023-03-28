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
import negocio.Funcionario;
//import negocio.Funcionario;

/**
 *
 * @author luizd
 */
public class FuncionarioDAO extends DAO {

    public void create(Funcionario funcionario) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();            
            entityManager.persist(funcionario);
            entityManager.getTransaction().commit();            
        } catch (Exception e) {
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Funcionario> listaFuncionarios() {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            List<Funcionario> vetFuncionario = entityManager.createNativeQuery("SELECT * FROM funcionario", Funcionario.class).getResultList();
            return vetFuncionario;
        } finally {
            entityManager.close();

        }

    }

    public Funcionario findById(int id) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Funcionario funcionario = entityManager.find(Funcionario.class, id);
            return funcionario;
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Funcionario não encontrado");
        } finally {
            entityManager.close();

        }

    }

    public void remove(Funcionario funcionario) {
        entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Funcionario funcionario2;
            try {
                funcionario2 = entityManager.getReference(Funcionario.class, funcionario.getId());
                funcionario2.getId();
            } catch (Exception e) {
                throw new IllegalCallerException("erro ao encontrar funcionario");
            }
            entityManager.remove(funcionario2);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalCallerException("erro ao apagar funcionario");
        } finally {
            entityManager.close();
        }

    }

    public Funcionario update(Funcionario funcionario) {
        entityManager = null;        
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            funcionario = entityManager.merge(funcionario);
            entityManager.getTransaction().commit();
            return funcionario;

        } catch (Exception e) {
            throw new IllegalCallerException("Funcionario com o id nao existe");
        } finally {
            entityManager.close();

        }

    }

}
