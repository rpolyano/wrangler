/*
 *  Copyright © 2017 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package co.cask.wrangler.parser;

import co.cask.wrangler.api.CompileException;
import co.cask.wrangler.api.CompiledUnit;
import co.cask.wrangler.api.Compiler;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.tool.GrammarParserInterpreter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link RecipeCompiler}
 */
public class RecipeCompilerTest {

  private static final Compiler compiler = new RecipeCompiler();

  @Test
  public void testSuccessCompilation() throws Exception {
    try {
      Compiler compiler = new RecipeCompiler();
      CompiledUnit units = compiler.compile(
          "parse-as-csv :body ' ' true;\n"
        + "set-column :abc, :edf;\n"
        + "send-to-error exp:{ window < 10 } ;\n"
        + "parse-as-simple-date :col 'yyyy-mm-dd' :col 'test' :col2,:col4,:col9 10 exp:{test < 10};\n"
      );

      Assert.assertNotNull(units);
      Assert.assertEquals(4, units.size());
    } catch (CompileException e) {
      Assert.assertTrue(false);
    }
  }

  @Test
  public void testMacroParsing() throws Exception {
    CodePointCharStream stream = CharStreams.fromString("${macro}");
    SyntaxErrorListener errorListener = new SyntaxErrorListener();
    DirectivesLexer lexer = new DirectivesLexer(stream);
    lexer.removeErrorListeners();

    DirectivesParser parser = new DirectivesParser(new CommonTokenStream(lexer));
    parser.removeErrorListeners();
    parser.addErrorListener(errorListener);
    parser.setErrorHandler(new GrammarParserInterpreter.BailButConsumeErrorStrategy());
    parser.setBuildParseTree(true);
    parser.macro();
    Assert.assertEquals(false, errorListener.hasErrors());
  }
}