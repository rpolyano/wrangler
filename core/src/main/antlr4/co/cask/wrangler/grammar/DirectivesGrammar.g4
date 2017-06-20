
//TODO: use embedded java code (semantic predicates) to handle Apache expresion !!!
grammar DirectivesGrammar;

//lexer rules
//TODO: this apache token --> http://httpd.apache.org/docs/current/mod/mod_log_config.html#formats
APACHE_LOG_TOKEN: '%';
NL: '\n';
WS: [ \t\r]+;


MATRIX: 'matrix';
WORD: [a-zA-Z\-_]+;
NUMBER: [0-9]+;
ANY_CHARS : .+;

anyString: ANY_CHARS;

/*
text
  : (anyString | WORD | NUMBER) # AnyCharText
  ;
*/


//parser rules
directives
  : NL* directive ((NL*) directive)* (NL*) EOF # DirectiveList
  ;

directive
  : command (WS*) arguments # DirectiveNoExp
  | command (WS*) arguments expression # DirectiveWithExp
  ;

expression
  : anyString # ExpressionWord
  ;

command
  : WORD # CommandWord
  ;

arguments
  : argument ((WS+) argument)* # ArgumentList
  ;

argument
  : NUMBER  # Number
  | columnName # ColumnNameArg
  ;

columnName
  : WORD # columnNameWord
  ;



/*
rename <old> <new>
parse-as-log <column> <format>
DEPRECIATED: indexsplit <source> <start> <end> <destination>



send-to-error <condition>
columns-replace <sed-expression>
write-as-csv <column>
parse-xml-to-json <column> [<depth>]
increment-variable <variable> <value> <expression>
titlecase <column>
url-encode <column>
set columns <columm>[,<column>*]
fill-null-or-empty <column> <fixed-value>
mask-shuffle <column>
decode <base32|base64|hex> <column>
set-charset <column> <charset>
diff-date <column1> <column2> <destination>
filter-row-if-true <condition>
change-column-case lower|upper
stemming <column>
parse-as-excel <column> [<sheet number | sheet name>]
parse-as-avro-file <column>
parse-as-fixed-length <column> <width>[,<width>*] [<padding-character>]
ltrim <column>
flatten <column>[,<column>*]
trim <column>
set-column <column> <expression>
quantize <source> <destination> <[range1:range2)=value>,[<range1:range2=value>]*
uppercase <column>
cleanse-column-names
xpath <column> <destination> <xpath>
keep <column>[,<column>*]
hash <column> <algorithm> [<encode=true|false>]
parse-as-protobuf <column> <schema-id> <record-name> [version]
swap <column1> <column2>
filter-row-if-matched <column> <regex>
parse-as-simple-date <column> <format>
mask-number <column> <pattern>
catalog-lookup <catalog> <column>
extract-regex-groups <column> <regex-with-groups>
parse-as-json <column> [<depth>]
lowercase <column>
filter-rows-on empty-or-null-columns <column>[,<column>*]
xpath-array <column> <destination> <xpath>
text-metric <method> <column1> <column2> <destination>
set-variable <variable> <expression>
parse-as-hl7 <column> [<depth>]
set-type <column> <type>
encode <base32|base64|hex> <column>
split-email <column>
fail <condition>
to-string <column>
format-date <column> <format>
split-to-rows <column> <separator>
split-to-columns <column> <regex>
set-record-delim <column> <delimiter> [<limit>]
write-as-json-map <column>
parse-as-xml <column>
merge <column1> <column2> <new-column> <separator>
copy <source> <destination> [<force=true|false>]
json-path <source> <destination> <json-path-expression>
table-lookup <column> <table>
rtrim <column>
parse-as ken <column> <delimiter> [<header=true|false>]
set column <column> <jexl-expression>
invoke-http <url> <column>[,<column>*] <header>[,<header>*]
parse-as-avro <column> <schema-id> <json|binary> [version]
text-distance <method> <column1> <column2> <destination>
find-and-replace <column> <sed-expression>
parse-as-date <column> [<timezone>]
split <source> <delimiter> <new-column-1> <new-column-2>
cut-character <source> <destination> <type> <range|indexes>
generate-uuid <column>
url-decode <column>
drop <column>[,<column>*]
split-url <column>
parse-as-csv <column> <delimiter> [<header=true|false>]
*/