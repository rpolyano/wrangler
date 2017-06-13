grammar Directive;

//lexer rules
FILE_TYPE : 'csv' | 'json'| 'xml';
ACTION : 'parse-as';

//parser rules
parse_as
  : ACTION FILE_TYPE EOF # ParseAs;