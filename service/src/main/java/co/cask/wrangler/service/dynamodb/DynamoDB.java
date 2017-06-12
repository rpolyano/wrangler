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

package co.cask.wrangler.service.dynamodb;

import co.cask.cdap.api.annotation.UseDataSet;
import co.cask.cdap.api.data.schema.Schema;
import co.cask.cdap.api.dataset.table.Table;
import co.cask.cdap.api.service.http.AbstractHttpServiceHandler;
import co.cask.cdap.api.service.http.HttpServiceContext;
import co.cask.cdap.api.service.http.HttpServiceRequest;
import co.cask.cdap.api.service.http.HttpServiceResponder;
import co.cask.cdap.internal.io.SchemaTypeAdapter;
import co.cask.wrangler.DataPrep;
import co.cask.wrangler.RequestExtractor;
import co.cask.wrangler.ServiceUtils;
import co.cask.wrangler.dataset.connections.Connection;
import co.cask.wrangler.dataset.connections.ConnectionStore;
import co.cask.wrangler.dataset.workspace.WorkspaceDataset;
import co.cask.wrangler.service.amazon.WebCredentialProvider;
import co.cask.wrangler.service.connections.ConnectionType;
import co.cask.wrangler.service.kafka.KafkaService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import static co.cask.wrangler.ServiceUtils.error;
import static co.cask.wrangler.service.directive.DirectivesService.WORKSPACE_DATASET;

/**
 * Manages connections with DynamoDB.
 */
public class DynamoDB extends AbstractHttpServiceHandler {
  private static final Logger LOG = LoggerFactory.getLogger(KafkaService.class);
  private static final Gson gson =
    new GsonBuilder().registerTypeAdapter(Schema.class, new SchemaTypeAdapter()).create();

  @UseDataSet(WORKSPACE_DATASET)
  private WorkspaceDataset ws;

  // Data Prep store which stores all the information associated with dataprep.
  @UseDataSet(DataPrep.DATAPREP_DATASET)
  private Table table;

  // Abstraction over the table defined above for managing connections.
  private ConnectionStore store;

  /**
   * Stores the context so that it can be used later.
   *
   * @param context the HTTP service runtime context
   */
  @Override
  public void initialize(HttpServiceContext context) throws Exception {
    super.initialize(context);
    store = new ConnectionStore(table);
  }

  /**
   * Tests DynamoDB Connection.
   *
   * @param request HTTP Request handler.
   * @param responder HTTP Response handler.
   */
  @POST
  @Path("connections/s3/test")
  public void testDynamoDBConnection(HttpServiceRequest request, HttpServiceResponder responder) {
    try {
      // Extract the body of the request and transform it to the Connection object.
      RequestExtractor extractor = new RequestExtractor(request);
      Connection connection = extractor.getContent("utf-8", Connection.class);

      if (ConnectionType.fromString(connection.getType().getType()) == ConnectionType.UNDEFINED) {
        error(responder, "Invalid connection type set.");
        return;
      }

      AWSCredentials credentials = new WebCredentialProvider(connection).getCredentials();
      final AmazonDynamoDB ddb = new AmazonDynamoDBClient(credentials);
      Region region = Region.getRegion(Regions.fromName((String)connection.getProp("region")));
      ddb.setRegion(region);
      ddb.listTables();
      ServiceUtils.success(responder, "Success");
    } catch (Exception e) {
      ServiceUtils.error(responder, e.getMessage());
    }
  }

  /**
   * Lists the buckets with the S3 connection.
   *
   * @param request HTTP Request handler.
   * @param responder HTTP Response handler.
   */
  @POST
  @Path("connections/dynamodb")
  public void listTables(HttpServiceRequest request, HttpServiceResponder responder) {
    try {
      // Extract the body of the request and transform it to the Connection object.
      RequestExtractor extractor = new RequestExtractor(request);
      Connection connection = extractor.getContent("utf-8", Connection.class);

      if (ConnectionType.fromString(connection.getType().getType()) == ConnectionType.UNDEFINED) {
        error(responder, "Invalid connection type set.");
        return;
      }

      AWSCredentials credentials = new WebCredentialProvider(connection).getCredentials();
      final AmazonDynamoDB ddb = new AmazonDynamoDBClient(credentials);
      Region region = Region.getRegion(Regions.fromName((String)connection.getProp("region")));
      ddb.setRegion(region);
      ListTablesResult result = ddb.listTables();
      List<String> tables = result.getTableNames();

      JsonObject response = new JsonObject();
      JsonArray values = new JsonArray();
      for (String table : tables) {
        TableDescription descriptor = ddb.describeTable(table).getTable();
        JsonObject object = new JsonObject();
        object.addProperty("name", table);
        object.addProperty("status", descriptor.getTableStatus());
        object.addProperty("creation", descriptor.getCreationDateTime().toString());
        object.addProperty("count", descriptor.getItemCount());
        object.addProperty("size", descriptor.getTableSizeBytes());
        values.add(object);
      }
      response.addProperty("status", HttpURLConnection.HTTP_OK);
      response.addProperty("message", "Success");
      response.addProperty("count", values.size());
      response.add("values", values);
      ServiceUtils.sendJson(responder, HttpURLConnection.HTTP_OK, response.toString());
    } catch (Exception e) {
      ServiceUtils.error(responder, e.getMessage());
    }
  }

  /**
   * Reads a table into workspace.
   *
   * @param request HTTP requets handler.
   * @param responder HTTP response handler.
   * @param id Connection id for which the tables need to be listed from database.
   */
  @GET
  @Path("connections/{id}/tables/{table}/read")
  public void read(HttpServiceRequest request, final HttpServiceResponder responder,
                   @PathParam("id") final String id, @PathParam("table") final String table,
                   @QueryParam("lines") final int lines) {
    try {
      Connection connection = store.get(id);
      if (connection == null) {
        throw new IllegalArgumentException(
          String.format(
            "Invalid connection id '%s' specified or connection does not exist.", id)
        );
      }

      AWSCredentials credentials = new WebCredentialProvider(connection).getCredentials();
      final AmazonDynamoDB ddb = new AmazonDynamoDBClient(credentials);
      Region region = Region.getRegion(Regions.fromName((String)connection.getProp("region")));
      ddb.setRegion(region);
      QueryRequest dbbQuery = new QueryRequest(table);
      dbbQuery.setLimit(lines);

      QueryResult query = ddb.query(dbbQuery);
    } catch (Exception e) {
      error(responder, e.getMessage());
    } finally {
    }
  }
}
