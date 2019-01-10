/*
 * Copyright © 2019 Cask Data, Inc.
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

package co.cask.wrangler.dataset;

import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.table.Scan;
import co.cask.wrangler.proto.NamespacedId;

/**
 * Utility methods around keys that have a context component.
 */
public class ContextualKeys {

  private ContextualKeys() {
    // no-op
  }

  public static Scan getScan(String context) {
    byte[] startKey = Bytes.concat(Bytes.toBytes(context.getBytes().length), Bytes.toBytes(context));
    return new Scan(startKey, Bytes.stopKeyForPrefix(startKey));
  }

  /**
   * Returns the rowkey for a contextual id
   * *
   * @param id the contextual id
   * @return the rowkey
   */
  public static byte[] getRowKey(NamespacedId id) {
    byte[] contextBytes = Bytes.toBytes(id.getNamespace());
    byte[] idBytes = Bytes.toBytes(id.getId());
    return Bytes.concat(Bytes.toBytes(contextBytes.length), contextBytes,
                        Bytes.toBytes(idBytes.length), idBytes);
  }

  /**
   * Returns a contextual id from a rowkey generated from {@link #getRowKey(NamespacedId)}.
   *
   * @param rowkey the rowkey
   * @return the decoded contextual id
   */
  public static NamespacedId fromRowKey(byte[] rowkey) {
    int contextLength = Bytes.toInt(rowkey, 0);
    String context = Bytes.toString(rowkey, Bytes.SIZEOF_INT, contextLength);
    int idLength = Bytes.toInt(rowkey, Bytes.SIZEOF_INT + contextLength);
    String id = Bytes.toString(rowkey, 2 * Bytes.SIZEOF_INT + contextLength, idLength);
    return NamespacedId.of(context, id);
  }
}
