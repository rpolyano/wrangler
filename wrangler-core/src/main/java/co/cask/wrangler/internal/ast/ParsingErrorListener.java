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

package co.cask.wrangler.internal.ast;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

/**
 * Directive parsing error listener.
 */
public final class ParsingErrorListener implements ANTLRErrorListener {

  private boolean fail = false;

  public boolean isFail() {
    return fail;
  }

  public void setFail(boolean fail) {
    this.fail = fail;
  }


  public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int row,
                          int column, String message, RecognitionException arg5) {
    System.out.println(row + ":" + column + " : " + message);
    setFail(true);
  }

  public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2,
                                       int arg3, int arg4, ATNConfigSet arg5) {
    System.out.println("r" + arg1.toString() + " : " + arg1.toLexerString());
    System.out.println("r" + arg5.toString());
    setFail(true);
  }

  public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2,
                                          int arg3, BitSet arg4, ATNConfigSet arg5) {
    System.out.println(arg1.toString() + " : " + arg1.toLexerString());
    System.out.print(arg5.toString());
    setFail(true);
  }

  public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3,
                              boolean arg4, BitSet arg5, ATNConfigSet arg6) {
    setFail(true);
  }
}
