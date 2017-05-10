package co.cask.wrangler.service.rdbms;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Tests {@link DBExplorer}
 */
public class DBExplorerTest {

  @Before
  public void setup() throws Exception {
    HikariConfig config = new HikariConfig();
    config.setMinimumIdle(1);
    config.setMaximumPoolSize(2);
    config.setConnectionTestQuery("SELECT 1");
    config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
    config.addDataSourceProperty("url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

    try (HikariDataSource ds = new HikariDataSource(config);
         Connection conn = ds.getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.executeUpdate("DROP TABLE IF EXISTS basic_pool_test");
      stmt.executeUpdate("CREATE TABLE basic_pool_test ("
                           + "id INTEGER NOT NULL IDENTITY PRIMARY KEY, "
                           + "timestamp TIMESTAMP, "
                           + "string VARCHAR(128), "
                           + "string_from_number NUMERIC "
                           + ")");
    }
  }

  @Test
  public void testMySQLConnectivity() throws Exception {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://35.188.12.7:3306/demo");
    config.setUsername("root");
    config.setPassword("somedefaultpassword");
    config.setDriverClassName("com.mysql.jdbc.Driver");
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    try (HikariDataSource ds = new HikariDataSource(config);
         Connection conn = ds.getConnection()) {
      DatabaseMetaData metaData = conn.getMetaData();
      ResultSet catalogs = metaData.getTables(null, "", "%", null);
      JsonArray array = new JsonArray();
      while(catalogs.next()) {
        JsonObject table = new JsonObject();
        table.addProperty("db", catalogs.getString(1));
        table.addProperty("name", catalogs.getString(3));
        table.addProperty("type", catalogs.getString(4));
        array.add(table);
      }
      Assert.assertTrue(true);
    }
  }
}