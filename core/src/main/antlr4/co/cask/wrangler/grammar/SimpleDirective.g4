grammar SimpleDirective;

//lexer rules
PARSE_AS : 'parse-as';
NL     : '\n';
WS     : [ \t\r]+ -> skip;
FILE_TYPE: [a-z\-]+;


//Want things like this:
//DIRECTIVE_NAME: [a-z\-]+;
//directiveExp: DIRECTIVE_NAME parameters
//load step classes to find THE class to use (annotation)

//parse-as ken works now


//parser rules
input
  : parseAs NL? EOF # InputFile
  ;

parseAs
  : PARSE_AS FILE_TYPE # ParseFileAs
  ;
