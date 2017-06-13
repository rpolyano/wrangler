grammar SimpleDirective;

//lexer rules
FILE_TYPE : 'csv' | 'json'| 'xml';
PARSE_AS : 'parse-as';
NL     : '\n';
WS     : [ \t\r]+ -> skip;

//parser rules
input
  : parseAs NL? EOF # InputFile
  ;

parseAs
  : PARSE_AS FILE_TYPE # ParseFileAs
  ;