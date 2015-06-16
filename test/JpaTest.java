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

    @Before
    public void create() {
        em = JPA.em("default");
        TestModel tm = new TestModel();
        tm.setResult(false);
        em.getTransaction().begin();
        try {
            em.persist(tm);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e.getMessage());
        }
        em.getTransaction().commit();
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
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e.getMessage());
        }
        em.getTransaction().commit();
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
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e.getMessage());
        }
        em.getTransaction().commit();
    }
}
