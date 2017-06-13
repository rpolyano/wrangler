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
import co.cask.wrangler.steps.parser.CsvParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class RunSimpleDirective {
    
    @Test
    public void testParseAsCsv() throws Exception {
        URL url = ParserTest.class.getClassLoader().getResource("simple_directive_tests.txt");
        File file = new File(url.getFile());
        ANTLRInputStream input = new ANTLRInputStream(new FileReader(file));
        SimpleDirectiveLexer lexer = new SimpleDirectiveLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleDirectiveParser parser = new SimpleDirectiveParser(tokens);
        ParseTree tree = parser.input();
        SimpleDirectiveBaseVisitorImpl directiveVisitor = new SimpleDirectiveBaseVisitorImpl();
        List<Step> result = directiveVisitor.visit(tree);

        //Got all the steps from file so far

        Assert.assertEquals(1, result.size());
        Step step = result.get(0);
        Assert.assertTrue(step instanceof CsvParser);
    }
}
