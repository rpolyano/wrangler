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

package co.cask.wrangler.grammar.examples;

import co.cask.wrangler.api.AbstractStep;
import co.cask.wrangler.api.Step;

import java.util.List;

/**
 * Used to wrap data of different types, so they can be returned by antlr visitors
 * TODO: might have smarter way to solve this
 */

public class NodeValue {
  private Object value;

  public NodeValue(Object object) {
    this.value = object;
    if (!(isString() || isSteps() || isAbtractStep()))
      throw new IllegalArgumentException();
  }

  public boolean isString() {
    return value instanceof String;
  }
  public String asString() {
    return (String) value;
  }

  public boolean isSteps() { return value instanceof List; }
  public List<Step> asSteps() {
    return (List<Step>) value;
  }

  public boolean isAbtractStep() { return value instanceof AbstractStep; }
  public AbstractStep asAbstractStep() { return (AbstractStep) value; }


  public Object getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NodeValue nodeValue = (NodeValue) o;

    return value != null ? value.equals(nodeValue.value) : nodeValue.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
