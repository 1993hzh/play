import models.TestModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.jpa.JPA;
import play.test.WithApplication;

import javax.persistence.EntityManager;


/**
 * Created by Administrator on 2015/6/15.
 */
public class JpaTest extends WithApplication {

    private static final Logger logger = LoggerFactory.getLogger(JpaTest.class);
    private EntityManager em;
    private static final String ENTITY_MANAGER = "playdb";

    @Before
    public void create() {
        em = JPA.em(ENTITY_MANAGER);
        TestModel tm = new TestModel();
        tm.setResult(false);
        em.getTransaction().begin();
        try {
            em.persist(tm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e.getMessage());
        }
    }

    @Test
    public void read() {
        TestModel tm = (TestModel) em.createQuery("from TestModel").getSingleResult();
        Assert.assertFalse(tm.isResult());
    }

    @Test
    public void update() {
        TestModel tm = (TestModel) em.createQuery("from TestModel").getSingleResult();
        tm.setResult(true);
        em.getTransaction().begin();
        try {
            em.merge(tm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e.getMessage());
        }
        Assert.assertTrue(tm.isResult());
    }

    @Test
    public void updateFail() {
        TestModel tm = (TestModel) em.createQuery("from TestModel").getSingleResult();
        tm.setResult(true);
        em.getTransaction().begin();
        em.merge(tm);
        em.getTransaction().rollback();
        tm = (TestModel) em.createQuery("from TestModel").getSingleResult();
        Assert.assertFalse(tm.isResult());
    }

    @After
    public void delete() {
        TestModel tm = (TestModel) em.createQuery("from TestModel").getSingleResult();
        tm.setResult(true);
        em.getTransaction().begin();
        try {
            em.remove(tm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e.getMessage());
        }
    }
}
