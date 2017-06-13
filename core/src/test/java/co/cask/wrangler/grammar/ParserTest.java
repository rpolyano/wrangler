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

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Tests the parsing of directives using wrangler.
 */
public class ParserTest {

  @Test
  public void testGoodCasesOfParsing() throws Exception {
    URL url = ParserTest.class.getClassLoader().getResource("directive_parser_good_tests.txt");
    File file = new File(url.getFile());
    DirectiveCompiler compiler = new DirectiveCompiler();
    boolean status = compiler.compile(file);
    if (! status) {
      List<String> errors = compiler.getErrors();
      for (String error : errors) {
        System.out.println(error);
      }
    }
    Assert.assertTrue(status);
    Assert.assertEquals(16, compiler.getCompiledObjects().size());
  }
}
