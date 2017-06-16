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
import co.cask.wrangler.api.Usage;
import org.junit.Test;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Created by kewang on 6/14/17.
 */
public class SyntaxPrinter {
  @Test
  public void getAllSyntax() {
    Reflections reflections = new Reflections("co.cask.wrangler.steps");
    Set<Class<? extends AbstractStep>> stepClasses = reflections.getSubTypesOf(AbstractStep.class);
    for (Class<? extends AbstractStep> stepClass : stepClasses) {
      //if usage matches, that's the class we want to use
      Usage usage = stepClass.getAnnotation(Usage.class);
      //String directiveHave = usage.directive();
      String usageStr = usage.usage();
      System.out.println(usageStr);
    }
  }
}
