/*
 * Copyright © 2016 Cask Data, Inc.
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

package co.cask.wrangler.api;

/**
 * An abstract class for {@link Step} with added debugging capabilities.
 */
public abstract class AbstractStep implements Step<Record, Record> {
  private int line;
  private String detail;

  public AbstractStep(int line, String detail) {
    this.line = line;
    this.detail = detail;
  }

  public void setLine(int line) {
    this.line = line;
  }

  @Override
  public String toString() {
    return String.format("[Step %d] - <%s>", line, detail);
  }
}

