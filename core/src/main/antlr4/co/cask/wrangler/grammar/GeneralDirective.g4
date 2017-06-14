grammar GeneralDirective;

//lexer rules
NL     : '\n';
WS     : [ \t\r]+ -> skip;
DIRECTIVE_NAME: [a-z\-]+;


//Want things like this:
//DIRECTIVE_NAME: [a-z\-]+;
//directiveExp: DIRECTIVE_NAME parameters
//load step classes to find THE class to use (annotation)

//parse-as ken works now


//parser rules
input
  : NL* directive ((NL*) directive)* (NL*) EOF # InputFile
  ;

directive
  : DIRECTIVE_NAME # DirectiveStatment
  ;