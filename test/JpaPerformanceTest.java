import models.TestModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.db.jpa.JPA;
import play.test.WithApplication;

import javax.persistence.EntityManager;
import java.util.Random;

/**
 * Created by Administrator.
 * 2015/6/21 20:40
 */
public class JpaPerformanceTest extends WithApplication {

    public static final Integer TIMES = 9999;
    public static final Integer SEARCH_TIMES = 500;

    private static final String ENTITY_MANAGER = "playdb";

    private EntityManager em;

    @Before
    public void insert() {
        em = JPA.em(ENTITY_MANAGER);
        try {
            em.getTransaction().begin();
            long before = System.currentTimeMillis();
            for (int i = 1; i <= TIMES; i++) {
                TestModel tm = new TestModel();
                tm.setNum(i);
                em.persist(tm);
            }
            long after = System.currentTimeMillis();
            Logger.debug("Insert " + TIMES + " times costs: " + (after - before));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Test
    public void list() {
        try {
            em.getTransaction().begin();
            Random random = new Random();
            long before = System.currentTimeMillis();
            for (int i = 0; i < SEARCH_TIMES; i++) {
                int num = random.nextInt(TIMES) + 1;
                TestModel tm = em.find(TestModel.class, num);
            }
            long after = System.currentTimeMillis();
            Logger.debug("Query " + SEARCH_TIMES + " times costs: " + (after - before));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @After
    public void delete() {
        try {
            em.getTransaction().begin();
            long before = System.currentTimeMillis();
            em.createQuery("from TestModel").getResultList().stream().forEach(em::remove);
            long after = System.currentTimeMillis();
            Logger.debug("Delete costs: " + (after - before));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

}
