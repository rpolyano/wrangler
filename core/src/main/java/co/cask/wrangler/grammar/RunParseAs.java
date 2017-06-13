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
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class RunParseAs {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        DirectiveLexer lexer = new DirectiveLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DirectiveParser parser = new DirectiveParser(tokens);
        ParseTree tree = parser.parse_as();

        DirectiveBaseVisitorImpl directiveVisitor = new DirectiveBaseVisitorImpl();
        String result = directiveVisitor.visit(tree);
        System.out.println("Result: " + result);
    }
}
