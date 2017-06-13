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

grammar Directives;

options {
  language = Java;
}

@lexer::header {
  import java.util.ArrayList;
  import java.util.List;

  import co.cask.wrangler.api.DirectiveParseException;
}

directives
  : ('\n')* directive ((SEMI_COLON | ('\n')*) directive )* EOF
  ;

directive
  : SINGLE_LINE_COMMENT
  | parseSource1
  | parseSource2
  | columnOperations
  ;

parseSource1
  : ('parse-as-json'|'parse-as-hl7'|'parse-as-xml') ( COLUMN_NAME ( INTEGER )* )
  ;

parseSource2
  : ('parse-as-log' | 'parse-as-csv') (COLUMN_NAME STRING)
  ;

columnOperations
  : 'drop' (COLUMN_NAME (COLUMN_NAME)*)
  | 'rename' (COLUMN_NAME COLUMN_NAME)
  ;

KVPAIRS
  : KVPAIR (COMMA KVPAIR)*
  ;

KVPAIR
  : IDENTIFIER '=' ( IDENTIFIER | STRING | NUMBER | BOOLEAN )
  ;

LPAREN
  : '('
  ;

RPAREN
  : ')'
  ;

LBRACE
  : '{'
  ;

RBRACE
  : '}'
  ;

COMMA
  : ','
  ;

DOT
  : '.'
  ;

SEMI_COLON
  : ';'
  ;

BOOLEAN
  : 'true'
  | 'false'
  ;

COLUMN_NAME
  : [a-zA-Z_] [a-zA-Z_:0-9]*
  ;

IDENTIFIER
  : [a-zA-Z_] [a-zA-Z_0-9]*
  ;

STRING
  :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
  ;

fragment ESC_SEQ
  :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
  |   UNICODE_ESC
  |   OCTAL_ESC
  ;


fragment OCTAL_ESC
  :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
  |   '\\' ('0'..'7') ('0'..'7')
  |   '\\' ('0'..'7')
  ;


fragment UNICODE_ESC
  :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
  ;


fragment HEX_DIGIT
  : ('0'..'9'|'a'..'f'|'A'..'F')
  ;

QUOTED_STRING
  : ["] (~["\r\n] | '\\\\' | '\\"')* ["]
  | ['] (~['\r\n] | '\\\\' | '\\\'')* [']
  ;

WS
  : (' '|'\t') -> skip
  ;

INTEGER
  : INT
  ;

NUMBER
  : INT ('.' DIGIT*)?
  ;

fragment INT
  : [1-9] DIGIT*
  | '0'
  ;

fragment DIGIT
  : [0-9]
  ;

SINGLE_LINE_COMMENT
  : ('--') (.)*? [\n] -> channel(HIDDEN)
  ;
