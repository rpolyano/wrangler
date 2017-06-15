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
import co.cask.wrangler.steps.parser.KenParser;
import co.cask.wrangler.steps.transformation.LeftTrim;
import co.cask.wrangler.steps.transformation.RightTrim;
import co.cask.wrangler.steps.transformation.Trim;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by kewang on 6/14/17.
 */
public class GeneralDirectiveCompilerTest {

  @Test
  public void testGoodFileParsing() throws Exception {
    URL url = GeneralDirectiveCompilerTest.class.getClassLoader().getResource("general_directive_tests.txt");
    File file = new File(url.getFile());
    GeneralDirectiveCompiler compiler = new GeneralDirectiveCompiler();
    boolean status = compiler.compile(file);
    if (! status) {
      List<String> errors = compiler.getErrors();
      for (String error : errors) {
        System.out.println(error);
      }
    }
    Assert.assertTrue(status);
    List<Step> steps = compiler.getCompiledObjects();
    Assert.assertEquals(3, steps.size());
    Assert.assertTrue(steps.get(0) instanceof Trim);
    Assert.assertTrue(steps.get(1) instanceof LeftTrim);
    Assert.assertTrue(steps.get(2) instanceof RightTrim);
  }

  @Test
  public void testGoodStringParsing() throws Exception {
    GeneralDirectiveCompiler compiler = new GeneralDirectiveCompiler();
    boolean status = compiler.compile("\n\ntrim firstName\nltrim address\nrtrim phone\n\n");
    if (! status) {
      List<String> errors = compiler.getErrors();
      for (String error : errors) {
        System.out.println(error);
      }
    }
    Assert.assertTrue(status);
    List<Step> steps = compiler.getCompiledObjects();
    Assert.assertEquals(3, steps.size());
    Assert.assertTrue(steps.get(0) instanceof Trim);
    Assert.assertTrue(steps.get(1) instanceof LeftTrim);
    Assert.assertTrue(steps.get(2) instanceof RightTrim);
  }
}
