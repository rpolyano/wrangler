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

package co.cask.wrangler.dataset.schema;

import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.DataSetException;
import co.cask.cdap.api.dataset.table.Put;
import co.cask.cdap.api.dataset.table.Row;
import co.cask.cdap.api.dataset.table.Table;
import co.cask.wrangler.proto.schema.SchemaEntry;
import co.cask.wrangler.proto.schema.SchemaInfo;
import com.google.common.base.Charsets;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * This class {@link SchemaRegistry} is responsible for managing the schema registry store.
 *
 * A schema in the registry is really a collection of schema entries. Each entry is identified by the schema id and
 * a version number. Each entry within the same schema shares the same metadata, such as a name, description, type, etc.
 * An entry contains the actual schema bytes that clients can use.
 *
 * Before a schema entry can be added, the schema must be created. At that point, no entries exist.
 * When an entry is added to a schema, the registry assigns a version number to that entry.
 * Each schema will keep track of the latest entry in the schema.
 *
 * Specific schema entries can be deleted, as well as the entire schema.
 * TODO: (CDAP-14661) fix bug where latest version does not get updated when latest version is deleted
 *
 */
public final class SchemaRegistry  {
  public static final String DATASET_NAME = "schemaRegistry";
  // Table in which all the information of the schema is stored.
  private final Table table;

  // Following are the column names stored in the table for schema registry.
  private static final String NAME_COL = "name";
  private static final String DESC_COL = "description";
  private static final String CREATED_COL = "created";
  private static final String UPDATED_COL = "updated";
  private static final String TYPE_COL = "type";
  private static final String AUTO_VERSION_COL = "auto";
  private static final String ACTIVE_VERSION_COL = "current";

  public SchemaRegistry(Table table) {
    this.table = table;
  }

  /**
   * Creates a schema.
   *
   * @param info the schema information to create
   * @throws SchemaAlreadyExistsException if a schema with the same id already exists
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public void createSchema(SchemaInfo info) throws SchemaRegistryException {
    SchemaRow schemaRow = getSchemaRow(info.getId());
    if (schemaRow == null) {
      throw new SchemaAlreadyExistsException(String.format("Schema '%s' already exists", info.getId()));
    }

    schemaRow = new SchemaRow(info);

    try {
      table.put(schemaRow.toPut());
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to create schema descriptor '%s'. %s", info.getId(), e.getMessage()));
    }
  }

  /**
   * Adds a new entry to an existing schema.
   *
   * @param id the schema id
   * @param specification the specification to add
   * @return the version number of the newly added entry
   * @throws SchemaNotFoundException if the schema does not exist
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public long addEntry(String id, byte[] specification) throws SchemaRegistryException {
    SchemaRow schemaRow = getSchemaRow(id);
    if (schemaRow == null) {
      throw new SchemaNotFoundException(String.format("Schema '%s' not found.", id));
    }
    long version = schemaRow.getAuto() + 1;
    SchemaRow updated = new SchemaRow(schemaRow.getId(), schemaRow.getName(), schemaRow.getDescription(),
                                      schemaRow.getType(), schemaRow.getCreated(), System.currentTimeMillis() / 1000,
                                      version, version);
    try {
      // write updated schema row
      table.put(updated.toPut());
      // write entry
      Put entry = new Put(Bytes.toBytes(schemaRow.getId()));
      entry.add(toVersionColumn(version), specification);
      table.put(entry);
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to add entry to schema '%s'. %s", id, e.getMessage()));
    }
    return version;
  }

  /**
   * Deletes the schema and all corresponding entries.
   *
   * @param id the schema id
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public void deleteSchema(String id) throws SchemaRegistryException {
    try {
      table.delete(Bytes.toBytes(id));
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to delete schema '%s'. %s", id, e.getMessage()));
    }
  }

  /**
   * Deletes a specific entry in the schema.
   *
   * TODO: (CDAP-14661) if the 'current' version is deleted, the 'current' version must be updated.
   *
   * @param id of the schema to be deleted.
   * @param version of the schema to be deleted.
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public void deleteEntry(String id, long version) throws SchemaRegistryException {
    try {
      table.delete(Bytes.toBytes(id), toVersionColumn(version));
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to delete schema '%s'",
                      e.getMessage())
      );
    }
  }

  /**
   * Checks if schema id and version combination exists in the registry.
   *
   * @param id of the schema to be checked
   * @param version version of the schema to be checked.
   * @return true if id and version matches, else false.
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public boolean hasSchema(String id, long version) throws SchemaRegistryException {
    try {
      Row row = table.get(Bytes.toBytes(id));
      if (row.isEmpty()) {
        return false;
      }
      if (row.getColumns().keySet().contains(toVersionColumn(version))) {
        return true;
      }
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to check if schema id and version exists. '%s'",
                      e.getMessage())
      );
    }
    return false;
  }

  /**
   * Checks if the schema exists. It is not necessary that there are entries registered.
   *
   * @param id the schema to check for
   * @return whether the schema exists
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public boolean hasSchema(String id) throws SchemaRegistryException {
    try {
      Row row = table.get(Bytes.toBytes(id));
      if (row.isEmpty()) {
        return false;
      }
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to check if schema id and version exists. '%s'",
                      e.getMessage())
      );
    }
    return true;
  }

  /**
   * Given an id, returns all the versions of schema.
   *
   * @param id of schema for which all version of registered schema versions should be returned.
   * @return list of schema versions.
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public Set<Long> getVersions(String id) throws SchemaRegistryException {
    try {
      Row row = table.get(Bytes.toBytes(id));
      if (row.isEmpty()) {
        return new HashSet<>();
      }
      Set<byte[]> versions = row.getColumns().keySet();
      Set<Long> versionSet = new HashSet<>();
      for (byte[] version : versions) {
        String v = new String(version, Charsets.UTF_8);
        int idx = v.indexOf("ver:");
        if (idx != -1) {
          String number = v.substring(idx + 4);
          versionSet.add(Long.parseLong(number));
        }
      }
      return versionSet;
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to check if schema id and version exists. '%s'",
                      e.getMessage())
      );
    }
  }

  /**
   * Get a specific schema entry.
   *
   * @param id the schema id
   * @param version the entry version
   * @return the schema entry
   * @throws SchemaNotFoundException if the schema or entry does not exist
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public SchemaEntry get(String id, long version) throws SchemaRegistryException {
    try {
      SchemaRow schemaRow = getSchemaRow(id);
      if (schemaRow == null) {
        throw new SchemaNotFoundException(String.format("Schema '%s' not found.", id));
      }
      byte[] specification = table.get(Bytes.toBytes(id), toVersionColumn(version));
      if (specification == null) {
        throw new SchemaNotFoundException(String.format("Schema '%s' version '%d' not found.", id, version));
      }
      Set<Long> versions = getVersions(id);
      return new SchemaEntry(id, schemaRow.getName(), schemaRow.getDescription(), schemaRow.getType(),
                             schemaRow.getCreated(), schemaRow.getUpdated(), versions, specification, version);
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to get schema '%s' version '%d'. %s", id, version, e.getMessage()));
    }
  }

  /**
   * Get the latest schema entry.
   *
   * @param id the schema id
   * @return the schema entry
   * @throws SchemaNotFoundException if the schema does not exist or there is no latest entry
   * @throws SchemaRegistryException if there was an issue reading from or writing to the underlying storage system
   */
  public SchemaEntry get(String id) throws SchemaRegistryException {
    try {
      SchemaRow schemaRow = getSchemaRow(id);
      if (schemaRow == null) {
        throw new SchemaNotFoundException(String.format("Schema '%s' not found.", id));
      }
      Long latestVersion = schemaRow.getCurrent();
      if (latestVersion == null) {
        throw new SchemaNotFoundException(String.format("No entries for schema '%s' were found", id));
      }
      byte[] specification = table.get(Bytes.toBytes(id), toVersionColumn(latestVersion));
      Set<Long> versions = getVersions(id);
      return new SchemaEntry(id, schemaRow.getName(), schemaRow.getDescription(), schemaRow.getType(),
                             schemaRow.getCreated(), schemaRow.getUpdated(), versions, specification, latestVersion);
    } catch (DataSetException e) {
      throw new SchemaRegistryException(
        String.format("Unable to get latest version of schema '%s'. %s", id, e.getMessage()));
    }
  }

  private byte[] toVersionColumn(long version) {
    String ver = String.format("ver:%d", version);
    return ver.getBytes(Charsets.UTF_8);
  }

  @Nullable
  private SchemaRow getSchemaRow(String id) {
    Row row = table.get(Bytes.toBytes(id));
    if (row.isEmpty()) {
      return null;
    }
    return SchemaRow.from(row);
  }
}
