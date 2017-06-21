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
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.junit.Assert;
import org.junit.Test;

/**
 * The test in this file is ignored as we use this only in cases when someone reports an issue with the file.
 */
public class LexerTest {
  @Test
  public void testLexer() throws Exception {
    String[] directives = new String[] {
      "rename old new; rename new old",
      "flatten col1,col2,col3,col4",
      "parse-as-csv body , header=true, failonerror=false",
      "test-window (abc, window > 10)",
      "split a:b:c 'delimiter' new-column-1 new-column-2",
      "set-variable variable { window < 10 }",
      "send-to-error { a < 10 } window",
      "send-to-error { a < 10 }",
      "send-to-error !dq:isNumber(value) window",
      "send-to-error { !dq:isNumber(value) } window",
      "if(dq:isNumber(value) { set-column foo 2; set-column boo koo; set-column coo 2.6 }",
      "!udd1 abc efg lmnop",
      "set-columns a, b   , c, d,e , f"
    };

    for (int i = 0; i < directives.length; ++i) {
      CharStream stream = new ANTLRInputStream(directives[i]);
      MyDirectivesLexer lexer = new MyDirectivesLexer(stream);
      String[] ruleNames = lexer.getRuleNames();
      Token token = lexer.nextToken();
      System.out.println("\n" + directives[i]);
      while (token.getType() != Token.EOF) {
        System.out.println(
          String.format("  TOKEN:%-30s = %20s", ruleNames[token.getType() - 1] , token.getText())
        );
        token = lexer.nextToken();
      }
    }
    Assert.assertTrue(true);
  }
}
