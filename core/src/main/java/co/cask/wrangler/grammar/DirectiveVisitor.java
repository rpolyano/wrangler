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

import co.cask.wrangler.api.Step;
import co.cask.wrangler.executor.UsageRegistry;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * AST Visitor for parsing the directives into steps.
 */
public class DirectiveVisitor extends DirectivesBaseVisitor<List<Step>> {
  private final UsageRegistry registry;
  private String directive;
  private int line;

  public DirectiveVisitor(UsageRegistry registry) {
    this.registry = registry;
  }

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
    directive = ctx.getStart().getText();
    line = ctx.getStart().getLine();
    return ctx.getChild(0).accept(this);
  }


  @Override
  public List<Step> visitParseSource1(DirectivesParser.ParseSource1Context ctx) {
    String command = ctx.getChild(0).getText();

    return super.visitParseSource1(ctx);
  }

  @Override
  public List<Step> visitParseSource2(DirectivesParser.ParseSource2Context ctx) {
    String command = ctx.getChild(0).getText();

    return super.visitParseSource2(ctx);
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
  public List<Step> visitColumnOperations(DirectivesParser.ColumnOperationsContext ctx) {
    return super.visitColumnOperations(ctx);
  }


  private List<Step> create(Step step) {
    List<Step> steps = new ArrayList<>();
    steps.add(step);
    return steps;
  }
}
