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
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class RunDirectives {
    public static void main(String[] args) throws Exception {

        URL url = ParserTest.class.getClassLoader().getResource("directive_parser_good_tests.txt");
        File file = new File(url.getFile());
        ANTLRInputStream input = new ANTLRInputStream(new FileReader(file));

        DirectivesLexer lexer = new DirectivesLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DirectivesParser parser = new DirectivesParser(tokens);
        ParseTree tree = parser.directives();
        System.out.println("Result: " + tree.toStringTree(parser));


        DirectivesVisitor visitor = new DirectiveVisitor(new UsageRegistry());
        visitor.visit(tree);

        List<Step> result = directivesVisitor.visitDirectives(tree);
        System.out.println("Result: " + result);
    }
}
