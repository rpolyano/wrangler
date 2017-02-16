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
  : directive (SEMI_COLON directive )* EOF
  ;

directive
  : dropColumn
  | renameColumn
  | flattenColumn
  | parseAsHL7
  | parseAsXML
  | parseAsJSON
  | parseAsCSV
  ;

// flatten(column, ...) or flatten column [, columns]*
flattenColumn
  : 'flatten' LPAREN COLUMN_NAME (',' COLUMN_NAME)* RPAREN
  | 'flatten' COLUMN_NAME (',' COLUMN_NAME)*
  ;

// parse-as-csv(column, ",", true) or parse-as csv column "," true
parseAsCSV
  : 'parse-as-csv' LPAREN COLUMN_NAME ',' QUOTED_STRING ',' BOOLEAN RPAREN
  | PARSE_AS 'csv' COLUMN_NAME QUOTED_STRING  BOOLEAN
  ;

// parse-as-xml(column [, depth]) or parse-as xml column [depth]
parseAsXML
  : 'parse-as-xml' LPAREN COLUMN_NAME ( ',' INTEGER ) RPAREN
  | PARSE_AS 'xml' COLUMN_NAME ( ',' INTEGER )
  ;

// parse-as-json(column [, depth]) or parse-as json column [depth]
parseAsJSON
  : 'parse-as-xml' LPAREN COLUMN_NAME ( ',' INTEGER ) RPAREN
  | PARSE_AS 'json' COLUMN_NAME ( ',' INTEGER )
  ;

// parse-as-hl7(column, depth)
parseAsHL7
  : 'parse-as-hl7' LPAREN COLUMN_NAME ( ',' INTEGER ) RPAREN
  | PARSE_AS 'hl7' COLUMN_NAME ( ',' INTEGER )
  ;

// drop(column1, column2, ...)
dropColumn
  : 'drop' LPAREN COLUMN_NAME (',' COLUMN_NAME)* RPAREN
  | 'drop' COLUMN_NAME (',' COLUMN_NAME)*
  ;

// rename(column, column, ...)
renameColumn
  : 'rename' LPAREN COLUMN_NAME (',' COLUMN_NAME)* RPAREN
  | 'rename' COLUMN_NAME (',' COLUMN_NAME)*
  ;


fragment KVPAIR
  : IDENTIFIER '=' ( IDENTIFIER | QUOTED_STRING | NUMBER | BOOLEAN )
  ;

PARSE_AS : 'parse-as';
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
COMMA : ',';
DOT : '.';
SEMI_COLON : ';';

BOOLEAN
  : 'true'
  | 'false'
  ;

COLUMN_NAME
  : [a-zA-Z_] [a-zA-Z_0-9]*
  ;

IDENTIFIER
  : [a-zA-Z_] [a-zA-Z_0-9]*
  ;

QUOTED_STRING
  : ["] (~["\r\n] | '\\\\' | '\\"')* ["]
  | ['] (~['\r\n] | '\\\\' | '\\\'')* [']
  ;

SPACE
  : [ \t\r\n\u000C] -> skip
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

MULTI_LINE_COMMENT
  : ('/*') (.)*? ('*/' | EOF) -> channel(HIDDEN)
  ;