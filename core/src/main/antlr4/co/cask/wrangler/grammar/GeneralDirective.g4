//Recognize this so far: <directiveName> <columnName>
grammar GeneralDirective;

//lexer rules
NL     : '\n';
WS     : [ \t\r]+;

WORD: [a-zA-Z\-_]+;
//DIRECTIVE_NAME: [a-z\-]+;
//COLUMN_NAME: [a-zA-Z_]+;


//Want things like this:
//DIRECTIVE_NAME: [a-z\-]+;
//directiveExp: DIRECTIVE_NAME parameters

//load step classes to find THE class to use (annotation) works now
//parse-as ken works now

//added col parameter
//parser rules
input
  : NL* directive ((NL*) directive)* (NL*) EOF # InputFile
  ;

directiveName
  : WORD # DirectiveNameWord
  ;

colName
  : WORD # ColNameWord
  ;

directive
  : directiveName WS colName # DirectiveStatment
  ;