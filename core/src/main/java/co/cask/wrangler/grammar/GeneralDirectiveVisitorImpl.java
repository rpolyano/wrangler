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
public class GeneralDirectiveVisitorImpl extends GeneralDirectiveBaseVisitor<NodeValue> {
  private final UsageRegistry registry;
  private String directive;
  private int line;

  public GeneralDirectiveVisitorImpl(UsageRegistry registry) {
    this.registry = registry;
  }

  @Override
  public NodeValue visitInputFile(GeneralDirectiveParser.InputFileContext ctx) {
    List<Step> steps = new ArrayList<>();
    //for (ParseTree directiveStatementTree : ctx.children) {
    int lineo = 1;
    for (int i = 0; i < ctx.getChildCount(); i ++) {
      ParseTree directiveStatementTree = ctx.getChild(i);
      NodeValue nodeValue = directiveStatementTree.accept(this);
      if (nodeValue != null && nodeValue.isAbtractStep()) {
        AbstractStep step = nodeValue.asAbstractStep();
        step.setLineno(lineo);
        steps.add(step);
        lineo ++;
      }
    }
    //}
    return new NodeValue(steps);
  }

  @Override
  public NodeValue visitDirectiveNameWord(GeneralDirectiveParser.DirectiveNameWordContext ctx) {
    String directiveName = ctx.WORD().getText();
    return new NodeValue(directiveName);
  }

  @Override
  public NodeValue visitColNameWord(GeneralDirectiveParser.ColNameWordContext ctx) {
    String colName = ctx.WORD().getText();
    return new NodeValue(colName);
  }

  @Override
  public NodeValue visitDirectiveStatment(GeneralDirectiveParser.DirectiveStatmentContext ctx) {
    //fake line number and detail
    int FAKE_LINE_NO = 0;

    //find class from directive statement
    Class<? extends AbstractStep> directiveClass = null;
    //get directive and column name from children nodes
    String directiveWant = visit(ctx.directiveName()).asString();
    String colName = visit(ctx.colName()).asString();
    String detail = directiveWant + " " + colName;

    //TODO: might be super slow to do this every time type in a directive. Need to do this in pre-process.
    Reflections reflections = new Reflections("co.cask.wrangler.steps");
    Set<Class<? extends AbstractStep>> stepClasses = reflections.getSubTypesOf(AbstractStep.class);
    for (Class<? extends AbstractStep> stepClass : stepClasses) {
      //if usage matches, that's the class we want to use
      Usage usage = stepClass.getAnnotation(Usage.class);
      String directiveHave = usage.directive();
      if (directiveWant.equals(directiveHave)) {
        directiveClass = stepClass;
      }
    }
    //TODO: if not found proper class, return empty step list for now, need to change
    if (directiveClass != null) {
      try {
        AbstractStep step = directiveClass.getConstructor(int.class, String.class, String.class)
                .newInstance(FAKE_LINE_NO, detail, colName);
        return new NodeValue(step);
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
    //no matched step class found
    return null;
  }
}
