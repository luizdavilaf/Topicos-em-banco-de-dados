/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package apresentacao.main;





import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import negocio.Funcionario;
import negocio.Setor;



public class App {
    
 private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MinhaPU");
         
    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Funcionario joao = new Funcionario();
        joao.setEmail("joao@net.com");
        joao.setNome("Joao Brasil");
        joao.setSenha("1234");
        Setor administracao = new Setor();
        administracao.setDescricao("Administração");
        joao.setSetor(administracao);
        em.persist(administracao);
        em.persist(joao);
        em.getTransaction().commit();
    
        
    }
}
