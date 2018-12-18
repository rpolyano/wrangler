/*
 * Copyright © 2018 Cask Data, Inc.
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

package co.cask.wrangler.dataset.schema;

import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.table.Put;
import co.cask.cdap.api.dataset.table.Row;
import co.cask.wrangler.proto.schema.SchemaInfo;

import javax.annotation.Nullable;

/**
 * Information about a schema that is all stored in the same table row.
 */
public class SchemaRow extends SchemaInfo {
  private static final String NAME_COL = "name";
  private static final String DESC_COL = "description";
  private static final String CREATED_COL = "created";
  private static final String UPDATED_COL = "updated";
  private static final String TYPE_COL = "type";
  private static final String AUTO_VERSION_COL = "auto";
  private static final String ACTIVE_VERSION_COL = "current";
  private final long auto;
  private final Long current;


  public SchemaRow(SchemaInfo info) {
    this(info.getId(), info.getName(), info.getDescription(), info.getType(), info.getCreated(), info.getUpdated(),
         0L, null);
  }

  public SchemaRow(String id, String name, @Nullable String description, String type, long created,
                   long updated, long auto, @Nullable Long current) {
    super(id, name, description, type, created, updated);
    this.auto = auto;
    this.current = current;
  }

  public long getAuto() {
    return auto;
  }

  @Nullable
  public Long getCurrent() {
    return current;
  }

  public static SchemaRow from(Row row) {
    String id = Bytes.toString(row.getRow());
    String name = row.getString(NAME_COL);
    String description = row.getString(DESC_COL);
    String type = row.getString(TYPE_COL);
    long created = row.getLong(CREATED_COL);
    long updated = row.getLong(UPDATED_COL);
    long auto = row.getLong(AUTO_VERSION_COL);
    Long current = row.getLong(ACTIVE_VERSION_COL);
    return new SchemaRow(id, name, description, type, created, updated, auto, current);
  }

  public Put toPut() {
    Put put = new Put(id);
    put.add(NAME_COL, name);
    if (description != null) {
      put.add(DESC_COL, description);
    }
    put.add(CREATED_COL, getCreated());
    put.add(UPDATED_COL, getUpdated());
    put.add(TYPE_COL, getType());
    put.add(AUTO_VERSION_COL, 0L);
    if (current != null) {
      put.add(ACTIVE_VERSION_COL, current);
    }
    return put;
  }
}
