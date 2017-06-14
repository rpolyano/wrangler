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

package co.cask.wrangler.grammar;

import co.cask.wrangler.api.AbstractStep;
import co.cask.wrangler.api.Step;
import co.cask.wrangler.api.Usage;
import co.cask.wrangler.executor.UsageRegistry;
import org.antlr.v4.runtime.tree.ParseTree;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by kewang on 6/14/17.
 */
public class GeneralDirectiveVisitorImpl extends GeneralDirectiveBaseVisitor<List<Step>> {
  private final UsageRegistry registry;
  private String directive;
  private int line;

  public GeneralDirectiveVisitorImpl(UsageRegistry registry) {
    this.registry = registry;
  }

  @Override
  public List<Step> visitInputFile(GeneralDirectiveParser.InputFileContext ctx) {
    List<Step> steps = new ArrayList<>();
    for (ParseTree tree : ctx.children) {
      List<Step> childrenSteps = tree.accept(this);
      if (childrenSteps != null) {
        steps.addAll(childrenSteps);
      }
    }
    return steps;
  }

  @Override
  public List<Step> visitDirectiveStatment(GeneralDirectiveParser.DirectiveStatmentContext ctx) {
    //fake line number and detail
    int FAKE_LINE_NO = 0;
    String FAKE_DETAIL = "fake_detail";
    String FAKE_COL = "fake_col";
    //find class from directive statement
    Class<? extends AbstractStep> directiveClass = null;
    List<Step> steps = new ArrayList<>();
    String commandInput = ctx.DIRECTIVE_NAME().getText();
    Reflections reflections = new Reflections("co.cask.wrangler.steps");
    Set<Class<? extends AbstractStep>> stepClasses = reflections.getSubTypesOf(AbstractStep.class);
    for (Class<? extends AbstractStep> stepClass : stepClasses) {
      //if usage matches, that's the class we want to use
      Usage usage = stepClass.getAnnotation(Usage.class);
      String commandWant = usage.directive();
      if (commandInput.equals(commandWant)) {
        directiveClass = stepClass;
      }
    }
    //TODO: if not found proper class, return empty step list for now, might need to change
    if (directiveClass != null) {
      try {
        AbstractStep step = directiveClass.getConstructor(int.class, String.class, String.class)
                .newInstance(FAKE_LINE_NO, FAKE_DETAIL, FAKE_COL);
        steps.add(step);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    return steps;
  }

}
