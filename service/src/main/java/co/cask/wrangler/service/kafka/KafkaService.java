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

package co.cask.wrangler.service.kafka;

import co.cask.cdap.api.service.http.AbstractHttpServiceHandler;
import co.cask.cdap.api.service.http.HttpServiceRequest;
import co.cask.cdap.api.service.http.HttpServiceResponder;
import org.apache.twill.filesystem.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import static co.cask.wrangler.service.directive.DirectivesService.sendJson;

/**
 * A {@link KafkaService} is a HTTP Service handler for exploring the filesystem.
 * It provides capabilities for listing file(s) and directories. It also provides metadata.
 */
public class KafkaService extends AbstractHttpServiceHandler {
  private static final Logger LOG = LoggerFactory.getLogger(KafkaService.class);

  /**
   * Lists the content of the path specified using the {@link Location}.
   *
   * @param request HTTP Request Handler
   * @param responder HTTP Response Handler
   * @param path to the location in the filesystem
   * @throws Exception
   */
  @Path("explorer")
  @GET
  public void list(HttpServiceRequest request, HttpServiceResponder responder,
                   @QueryParam("path") String path) throws Exception {

    JSONObject response = new JSONObject();
    JSONArray values = new JSONArray();

    response.put("status", HttpURLConnection.HTTP_OK);
    response.put("message", "Success");
    response.put("count", values.length());
    response.put("values", values);
    sendJson(responder, HttpURLConnection.HTTP_OK, response.toString());
  }


  private void t() {
//    new KafkaConsumer<>()
  }

  private void q() {
  }
}
