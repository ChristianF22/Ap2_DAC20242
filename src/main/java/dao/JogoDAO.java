package dao;

import javax.persistence.*;
import entidades.Jogo;
import java.util.List;

public class JogoDAO {

    private EntityManagerFactory entityManagerFactory;

    public JogoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("integracaoPU");
    }

    public void salvar(Jogo jogo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(jogo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); 
            }
            e.printStackTrace(); 
        } finally {
            entityManager.close();
        }
    }

    public void editar(Jogo jogo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(jogo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void excluir(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Jogo jogo = entityManager.find(Jogo.class, id);
            if (jogo != null) {
                entityManager.remove(jogo);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public List<Jogo> listar() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Jogo> jogos = null;
        try {
            jogos = entityManager.createQuery("SELECT j FROM Jogo j", Jogo.class).getResultList();
        } finally {
            entityManager.close();
        }
        return jogos;
    }

    public Jogo buscarPorId(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Jogo jogo = null;
        try {
            jogo = entityManager.find(Jogo.class, id);
        } finally {
            entityManager.close();
        }
        return jogo;
    }

    public void fechar() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
