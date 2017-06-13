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

import co.cask.wrangler.api.DirectiveParseException;
import co.cask.wrangler.api.Step;
import co.cask.wrangler.steps.parser.CsvParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

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
    List<Step> steps = new ArrayList<>();
    String parseAsStr = ctx.PARSE_AS().getText();
    String fileTypeStr = ctx.FILE_TYPE().getText();
    if (parseAsStr.equals("parse-as")) {
      if (fileTypeStr.equals("csv")) {
        //hard code a step really quick
        // parse-as-csv <column> <delimiter> [<header=true/false>]

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
        CsvParser.Options opt = new CsvParser.Options(delimiter, true);
        steps.add(new CsvParser(0, "fakeStr", opt, column, hasHeader));
      }
    }
    return steps;
  }
}
