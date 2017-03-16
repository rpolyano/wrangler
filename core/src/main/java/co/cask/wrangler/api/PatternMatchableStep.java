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

package co.cask.wrangler.api;

import co.cask.common.cli.Arguments;

/**
 * A {@link Step} which can be matched via a pattern it defines.
 * This should be merged with {@link AbstractStep} once all steps define their own pattern.
 */
public abstract class PatternMatchableStep implements Step<Record, Record> {
  private int lineno;
  private String detail;

  public final void configure(int lineno, String detail) {
    this.lineno = lineno;
    this.detail = detail;
  }

  @Override
  public String toString() {
    return String.format("[Step %d] - <%s>", lineno, detail);
  }

  /**
   * Initialize the step with the arguments being passed for it.
   *
   * @param arguments consists of the parameters defined in the step's pattern
   */
  public abstract void initialize(Arguments arguments) throws DirectiveParseException;

  /**
   * Return the pattern representing the step. Wrap the argument name in '<>' to indicate that it is a required
   * argument. Wrap the argument in '[]' to indicate that it is an optional argument.
   * Example: "directiveName <requiredArg> [optionalArg]"
   */
  public abstract String getPattern();
}
