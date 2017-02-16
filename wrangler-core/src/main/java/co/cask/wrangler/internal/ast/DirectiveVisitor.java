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

package co.cask.wrangler.internal.ast;

import co.cask.wrangler.api.Step;
import co.cask.wrangler.internal.parser.DirectivesBaseVisitor;
import co.cask.wrangler.internal.parser.DirectivesParser;
import co.cask.wrangler.steps.XmlToJson;
import co.cask.wrangler.steps.column.Drop;
import co.cask.wrangler.steps.parser.HL7Parser;
import co.cask.wrangler.steps.parser.JsonParser;
import co.cask.wrangler.steps.row.Flatten;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * AST Visitor for parsing the directives into steps.
 */
public class DirectiveVisitor extends DirectivesBaseVisitor<List<Step>> {

  /**
   * Visit a parse tree produced by {@link DirectivesParser#directives}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<Step> visitDirectives(DirectivesParser.DirectivesContext ctx) {
    List<Step> steps = new ArrayList<>();
    for (ParseTree tree : ctx.children) {
      List<Step> childrenSteps = tree.accept(this);
      if (childrenSteps != null) {
        steps.addAll(childrenSteps);
      }
    }
    return steps;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#directive}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<Step> visitDirective(DirectivesParser.DirectiveContext ctx) {
    return ctx.getChild(0).accept(this);
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#flattenColumn}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<Step> visitFlattenColumn(DirectivesParser.FlattenColumnContext ctx) {
    int lineno = ctx.getStart().getLine();
    List<String> columns = new ArrayList<>();
    for (TerminalNode node : ctx.COLUMN_NAME()) {
      columns.add(node.getText());
    }
    String[] cols = new String[columns.size()];
    List<Step> steps = new ArrayList<>();
    steps.add(new Flatten(lineno, "", columns.toArray(cols)));
    return steps;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#parseAsCSV}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<Step> visitParseAsCSV(DirectivesParser.ParseAsCSVContext ctx) {
    String column = ctx.COLUMN_NAME().getText();
    String delimiter = ctx.QUOTED_STRING().getText();
    String emptyLines = ctx.BOOLEAN().getText();
    return null;
  }

  /**
   * {@inheritDoc}
   * <p>
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   *
   * @param ctx
   */
  @Override
  public List<Step> visitParseAsXML(DirectivesParser.ParseAsXMLContext ctx) {
    String number = ctx.INTEGER() != null ? ctx.INTEGER().getText() : null;
    int depth = Integer.MAX_VALUE;
    if (number != null && number.isEmpty()) {
      depth = Integer.parseInt(number);
    }
    List<Step> steps = new ArrayList<>();
    steps.add(new XmlToJson(ctx.getStart().getLine(), "", ctx.COLUMN_NAME().getText(), depth));
    return steps;
  }

  /**
   * {@inheritDoc}
   * <p>
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   *
   * @param ctx
   */
  @Override
  public List<Step> visitParseAsJSON(DirectivesParser.ParseAsJSONContext ctx) {
    String number = ctx.INTEGER() != null ? ctx.INTEGER().getText() : null;
    int depth = Integer.MAX_VALUE;
    if (number != null && number.isEmpty()) {
      depth = Integer.parseInt(number);
    }
    List<Step> steps = new ArrayList<>();
    steps.add(new JsonParser(ctx.getStart().getLine(), "", ctx.COLUMN_NAME().getText(), depth));
    return steps;
  }

  /**
   * {@inheritDoc}
   * <p>
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   *
   * @param ctx
   */
  @Override
  public List<Step> visitParseAsHL7(DirectivesParser.ParseAsHL7Context ctx) {
    String number = ctx.INTEGER() != null ? ctx.INTEGER().getText() : null;
    int depth = Integer.MAX_VALUE;
    if (number != null && number.isEmpty()) {
      depth = Integer.parseInt(number);
    }
    List<Step> steps = new ArrayList<>();
    steps.add(new HL7Parser(ctx.getStart().getLine(), "", ctx.COLUMN_NAME().getText(), depth));
    return steps;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#dropColumn}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<Step> visitDropColumn(DirectivesParser.DropColumnContext ctx) {
    int lineno = ctx.getStart().getLine();
    List<String> columns = new ArrayList<>();
    for (TerminalNode node : ctx.COLUMN_NAME()) {
      columns.add(node.getText());
    }
    List<Step> steps = new ArrayList<Step>();
    steps.add(new Drop(lineno, "", columns));
    return steps;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#renameColumn}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<Step> visitRenameColumn(DirectivesParser.RenameColumnContext ctx) {
    int lineno = ctx.getStart().getLine();
    List<String> columns = new ArrayList<>();
    for (TerminalNode node : ctx.COLUMN_NAME()) {
      columns.add(node.getText());
    }
    List<Step> steps = new ArrayList<Step>();
    //steps.add(new Rename(lineno, "", columns));
    return steps;
  }
}
