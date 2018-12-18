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

import co.cask.cdap.api.common.Bytes;

import java.util.Set;

/**
 * Schema Entry
 */
public final class SchemaEntry extends SchemaInfo {
  private final String specification;
  private final Set<Long> versions;
  private final long version;

  public SchemaEntry(String id, String name, String description, String type, long created,
                     long updated, Set<Long> versions, byte[] specification, long version) {
    super(id, name, description, type, created, updated);
    this.versions = versions;
    this.specification = Bytes.toHexString(specification);
    this.version = version;
  }

  public String getSpecification() {
    return specification;
  }

  public Set<Long> getVersions() {
    return versions;
  }

  public long getVersion() {
    return version;
  }
}
