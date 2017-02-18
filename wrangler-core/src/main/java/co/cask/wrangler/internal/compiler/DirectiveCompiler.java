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

package co.cask.wrangler.internal.compiler;

import co.cask.wrangler.api.Step;
import co.cask.wrangler.api.UsageRegistry;
import co.cask.wrangler.internal.parser.DirectivesLexer;
import co.cask.wrangler.internal.parser.DirectivesParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Compiles directive into steps.
 */
public final class DirectiveCompiler {
  private final List<Step> steps = new ArrayList<>();
  private final List<String> errors = new ArrayList<>();
  private final UsageRegistry registry;

  public DirectiveCompiler() {
    this.registry = new UsageRegistry();
  }

  /**
   * Compiles directives into steps.
   *
   * @param file File containing directives.
   * @return true, if successfully compiled. false, otherwise.
   * @throws IOException thrown when issue reading the directive stream.
   */
  public boolean compile(File file) throws IOException {
    return compile(new ANTLRInputStream(new FileReader(file)));
  }

  /**
   * Compiles directives into steps.
   *
   * @param directive String representation of directives.
   * @return true, if successfully compiled. false, otherwise.
   * @throws IOException thrown when issue reading the directive stream.
   */
  public boolean compile(String directive) throws IOException {
    return compile(new ANTLRInputStream(new StringReader(directive)));
  }

  /**
   * @return Compiled directives.
   */
  public List<Step> getCompiledObjects() {
    return steps;
  }

  /**
   * @return List of errors associated with parsing.
   */
  public List<String> getErrors() {
    return errors;
  }

  private boolean compile(CharStream stream) {
    DirectivesLexer lexer = new DirectivesLexer(stream);
    TokenStream tokenizer = new CommonTokenStream(lexer);
    DirectivesParser parser = new DirectivesParser(tokenizer);

    // Replace Lexer and Parser Error listeners with our own.
    ErrorListener listener = new ErrorListener();

    // Remove native listeners for errors and add our own.
    lexer.removeErrorListeners();
    parser.removeErrorListeners();
    lexer.addErrorListener(listener);
    parser.addErrorListener(listener);

    // Set to build the tree and start the parsing process.
    parser.setBuildParseTree(true);
    ParseTree tree = parser.directives();

    // Check if all parsed well.
    if (listener.hasErrors()) {
      // Extract errors
      errors.addAll(listener.getErrorList());
      return false;
    }

    // If lexer and parsing were successful, then start iterating
    // AST to convert it to steps.
    DirectiveVisitor visitor = new DirectiveVisitor(registry);
    steps.addAll(visitor.visit(tree));

    return true;
  }
}
