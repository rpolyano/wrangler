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

import co.cask.wrangler.steps.parser.KenParser;
import org.reflections.Reflections;
import co.cask.wrangler.api.AbstractStep;
import co.cask.wrangler.api.DirectiveParseException;
import co.cask.wrangler.api.Step;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang.StringEscapeUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimpleDirectiveBaseVisitorImpl extends SimpleDirectiveBaseVisitor<List<Step>> {

  @Override
  public List<Step> visitInputFile(SimpleDirectiveParser.InputFileContext ctx) {
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
  public List<Step> visitParseFileAs (SimpleDirectiveParser.ParseFileAsContext ctx) {

    //find class based on directive parameter
    Class<? extends AbstractStep> parserClass = null;
    List<Step> steps = new ArrayList<>();
    String fileType = ctx.FILE_TYPE().getText();
    String parserClassName = fileType.substring(0, 1).toUpperCase() + fileType.substring(1) + "Parser";
    Reflections reflections = new Reflections("co.cask.wrangler");
    Set<Class<? extends AbstractStep>> stepClasses = reflections.getSubTypesOf(AbstractStep.class);
    for (Class<? extends AbstractStep> stepClass : stepClasses) {
      if (stepClass.getName().equals(parserClassName)) {
        parserClass = stepClass;
      }
    }

    if (parserClass != null) {
      String column = "body";
      String delimStr = ",";
      char delimiter = delimStr.charAt(0);
      if (delimStr.startsWith("\\")) {
        String unescapedStr = StringEscapeUtils.unescapeJava(delimStr);
        if (unescapedStr == null) {
          try {
            throw new DirectiveParseException("Invalid delimiter for CSV Parser: " + delimStr);
          } catch (DirectiveParseException e) {
            e.printStackTrace();
          }
        }
        delimiter = unescapedStr.charAt(0);
      }
      boolean hasHeader;
      String hasHeaderLinesOpt = null;
      if (hasHeaderLinesOpt == null || hasHeaderLinesOpt.equalsIgnoreCase("false")) {
        hasHeader = false;
      } else {
        hasHeader = true;
      }
      //this opt is still hardcoded for csv parser
      KenParser.Options opt = new KenParser.Options(delimiter, true);
      try {
        Step parseStep = parserClass.asSubclass(Step.class)
                .getConstructor(int.class, String.class, KenParser.Options.class, String.class, boolean.class)
                .newInstance(0, "fake_detail", opt, column, hasHeader);
        steps.add(parseStep);
        return steps;
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      return steps;
    }
    else {
      return steps;
    }
  }
}
