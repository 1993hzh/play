import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.DB;
import play.test.WithApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator.
 * 2015/6/15 20:18
 */
public class DataBaseTest extends WithApplication {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseTest.class);

    private static final String DATA_SOURCE = "playdb";

    @Before
    public void init() {
        logger.info("Start");
//
    }

    @After
    public void close() {
        logger.info("End");
//        database.shutdown();
    }

    @Test
    public void test() throws SQLException {
        DataSource ds = DB.getDataSource(DATA_SOURCE);
        Connection connection = ds.getConnection();
        Assert.assertFalse(connection.isClosed());
    }

}
