/*
 * Copyright © 2017 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.wrangler.service.rdbms;

import co.cask.cdap.api.annotation.TransactionControl;
import co.cask.cdap.api.annotation.TransactionPolicy;
import co.cask.cdap.api.service.http.AbstractHttpServiceHandler;
import co.cask.cdap.api.service.http.HttpServiceRequest;
import co.cask.cdap.api.service.http.HttpServiceResponder;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import static co.cask.wrangler.service.ServiceUtils.error;
import static co.cask.wrangler.service.ServiceUtils.sendJson;

/**
 * Class description here.
 */
public class DBExplorer extends AbstractHttpServiceHandler {
  private static final Logger LOG = LoggerFactory.getLogger(DBExplorer.class);
  private static final ImmutableMap<String, String> driverMap = ImmutableMap.<String, String>builder()
    .put("mysql", "com.mysql.jdbc.Driver")
    .build();

  @TransactionPolicy(value = TransactionControl.EXPLICIT)
  @GET
  @Path("explorer/db")
  public void list(HttpServiceRequest request, HttpServiceResponder responder,
                   @QueryParam("jdbc") String jdbcUrl, @QueryParam("username") String username,
                   @QueryParam("password") String password) {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl(jdbcUrl);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setMaximumPoolSize(10);
    ds.setAutoCommit(false);
    ds.addDataSourceProperty("cachePrepStmts", "true");
    ds.addDataSourceProperty("prepStmtCacheSize", "250");
    ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    try (Connection connection = ds.getConnection()) {
      DatabaseMetaData md = connection.getMetaData();
      ResultSet rs = md.getTables(null, null, "%", null);
      JsonArray array = new JsonArray();
      while (rs.next()) {
        JsonObject table = new JsonObject();
        table.addProperty("catalog", rs.getString(0));
        table.addProperty("schema", rs.getString(1));
        table.addProperty("name", rs.getString(2));
        table.addProperty("type", rs.getString(3));
        table.addProperty("remarks", rs.getString(4));
        array.add(table);
      }
      JsonObject response = new JsonObject();
      response.addProperty("status", HttpURLConnection.HTTP_OK);
      response.addProperty("message", "Success");
      response.addProperty("count", array.size());
      response.add("values", array);
      sendJson(responder, HttpURLConnection.HTTP_OK, response.toString());
    } catch (SQLException e) {
      error(responder, e.getMessage());
    }
  }
}
