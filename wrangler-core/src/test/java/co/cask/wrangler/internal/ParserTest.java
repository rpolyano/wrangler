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

package co.cask.wrangler.internal;

import co.cask.wrangler.api.Step;
import co.cask.wrangler.internal.ast.DirectiveVisitor;
import co.cask.wrangler.internal.ast.DirectiveParsingError;
import co.cask.wrangler.internal.parser.DirectivesLexer;
import co.cask.wrangler.internal.parser.DirectivesParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests the parsing of directives using wrangler.
 */
public class ParserTest {

  @Test
  public void testBasicParsing() throws Exception {
    List<RecognitionException> errors = new ArrayList<>();
    String directive = "parse-as xml x3";
    CharStream inputCharStream = new ANTLRInputStream(new StringReader(directive));
    DirectivesLexer lexer = new DirectivesLexer(inputCharStream);
    TokenStream inputTokenStream = new CommonTokenStream(lexer);
    DirectivesParser parser = new DirectivesParser(inputTokenStream);

    lexer.removeErrorListeners();
    lexer.addErrorListener(DirectiveParsingError.INSTANCE);
    parser.removeErrorListeners();
    parser.addErrorListener(DirectiveParsingError.INSTANCE);

    parser.setBuildParseTree(true);
    ParseTree tree = parser.directives();

    DirectiveVisitor visitor = new DirectiveVisitor();
    List<Step> steps = visitor.visit(tree);

    Assert.assertTrue(steps.size() >= 0);
  }
}
