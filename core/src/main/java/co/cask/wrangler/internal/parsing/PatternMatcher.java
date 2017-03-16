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

package co.cask.wrangler.internal.parsing;

import co.cask.wrangler.api.PatternMatchableStep;
import com.google.common.collect.Lists;

import java.util.List;
import javax.annotation.Nullable;

/**
 * Utility class for matching an input string against a set of {@link co.cask.wrangler.api.Step}s.
 */
public final class PatternMatcher {

  private PatternMatcher() { }

  /**
   * Finds a matching command for the provided input.
   *
   * @param input the input string
   * @return the matching command and the parsed arguments
   */
  @Nullable
  public static PatternMatch findMatch(Iterable<? extends PatternMatchableStep> steps, String input) {
    String trimmedInput = input.trim();
    for (PatternMatchableStep step : steps) {
      String pattern = getMatchPattern(step.getPattern());
      if (trimmedInput.matches(pattern)) {
        return new PatternMatch(step, trimmedInput);
      }
    }
    return null;
  }

  /**
   * Finds matching commands for the provided input.
   *
   * @param input the input string
   * @return the matching commands
   */
  public static List<PatternMatchableStep> findMatchCommands(Iterable<? extends PatternMatchableStep> steps,
                                                             String input) {
    // Note that this can be used to debug which field is missing, by performing just a match on the first word in the
    // directive (the command)
    List<PatternMatchableStep> appropriateCommands = Lists.newArrayList();
    for (PatternMatchableStep command : steps) {
      if (command.getPattern().startsWith(input)) {
        appropriateCommands.add(command);
      }
    }
    return appropriateCommands;
  }

  /**
   * Convert command pattern to regular expression that matches any input of this command
   *
   * @param pattern the command pattern
   * @return regular expression
   */
  private static String getMatchPattern(String pattern) {
    String mandatoryPart = pattern.trim().replaceAll("(\\s+?)\\[.+?\\]", "($1.+?(\\\\s|\\$))*");
    mandatoryPart = mandatoryPart.replaceAll("\\s+", "\\\\s+");
    return mandatoryPart.replaceAll("<.+?>", ".+?");
  }
}
