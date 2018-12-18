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

package co.cask.wrangler.proto.schema;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

/**
 * Information about a schema.
 */
public class SchemaInfo {
  protected final String id;
  protected final String name;
  protected final String description;
  protected final String type;
  protected final long created;
  protected final long updated;

  protected SchemaInfo(String id, String name, @Nullable String description, String type, long created, long updated) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.created = created;
    this.updated = updated;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  public String getType() {
    return type;
  }

  public long getCreated() {
    return created;
  }

  public long getUpdated() {
    return updated;
  }

  /**
   * Validate and create a SchemaInfo. Timestamps should be in seconds.
   */
  public static SchemaInfo of(String id, String name, String description, @Nullable String type,
                              long created, long updated) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("A schema id must be specified.");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("A schema name must be specified.");
    }
    if (type == null || SchemaDescriptorType.fromString(type) == null) {
      String validTypesStr = Arrays.stream(SchemaDescriptorType.values())
        .map(SchemaDescriptorType::getType)
        .collect(Collectors.joining(","));
      throw new IllegalArgumentException(String.format("Schema type must be one of %s.", validTypesStr));
    }
    return new SchemaInfo(id, name, description, type, created, updated);
  }
}
