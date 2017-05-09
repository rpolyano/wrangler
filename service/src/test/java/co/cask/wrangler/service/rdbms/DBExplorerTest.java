package co.cask.wrangler.service.rdbms;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Tests {@link DBExplorer}
 */
public class DBExplorerTest {
  private static Connection connection;

  @BeforeClass
  public static void beforeClass() throws Exception {
    Class.forName("org.hsqldb.jdbcDriver");
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl("jdbc:hsqldb:db");
    ds.setUsername("sa");
    ds.setPassword("");
    ds.setMaximumPoolSize(10);
    ds.setAutoCommit(false);
    ds.addDataSourceProperty("cachePrepStmts", "true");
    ds.addDataSourceProperty("prepStmtCacheSize", "250");
    ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    ds.setConnectionTestQuery("select ");
    connection = ds.getConnection();
    System.out.println("Starting connection ...");
  }

  @Test
  public void testTableListing() throws Exception {

  }

  @AfterClass
  public static void afterClass() throws Exception {
    if (connection != null) {
      Statement st = connection.createStatement();
      st.execute("shutdown");
      connection.close();
      System.out.println("Shutting down connection ...");
    }
  }
}