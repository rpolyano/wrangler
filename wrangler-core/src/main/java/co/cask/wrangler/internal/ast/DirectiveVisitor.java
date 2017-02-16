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
import co.cask.wrangler.internal.parser.DirectivesParser;
import co.cask.wrangler.internal.parser.DirectivesVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

/**
 * AST Visitor for parsing the directives into steps.
 */
public class DirectiveVisitor implements DirectivesVisitor<Step> {

  /**
   * Visit a parse tree produced by {@link DirectivesParser#directives}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Step visitDirectives(DirectivesParser.DirectivesContext ctx) {
    for (ParseTree tree : ctx.children) {
      Step s = tree.accept(this);
    }
    return null;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#directive}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Step visitDirective(DirectivesParser.DirectiveContext ctx) {
    for (ParseTree tree : ctx.children) {
      Step s = tree.accept(this);
    }
    return null;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#flattenColumn}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Step visitFlattenColumn(DirectivesParser.FlattenColumnContext ctx) {
    List<TerminalNode> columns = ctx.COLUMN_NAME();
    return null;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#parseAsCSV}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Step visitParseAsCSV(DirectivesParser.ParseAsCSVContext ctx) {
    String column = ctx.COLUMN_NAME().getText();
    String delimiter = ctx.QUOTED_STRING().getText();
    String emptyLines = ctx.BOOLEAN().getText();
    return null;

  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#dropColumn}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Step visitDropColumn(DirectivesParser.DropColumnContext ctx) {
    List<TerminalNode> nodes = ctx.COLUMN_NAME();
    return null;
  }

  /**
   * Visit a parse tree produced by {@link DirectivesParser#renameColumn}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Step visitRenameColumn(DirectivesParser.RenameColumnContext ctx) {
    List<TerminalNode> nodes = ctx.COLUMN_NAME();
    return null;
  }

  @Override
  public Step visit(ParseTree parseTree) {
    return parseTree.accept(this);
  }

  @Override
  public Step visitChildren(RuleNode ruleNode) {
    return null;
  }

  @Override
  public Step visitTerminal(TerminalNode terminalNode) {
    return null;
  }

  @Override
  public Step visitErrorNode(ErrorNode errorNode) {
    return null;
  }
}
