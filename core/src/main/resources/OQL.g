//*************************************************************
// DML SQL statement
// Richie, Mar. 4, 2008
// http://www.cnblogs.com/RicCC
// Version: 0.1, ANTLR: 3.0.1
//*************************************************************
grammar OQL;
options {
	language=CSharp;
    output=AST;
    ASTLabelType=Expression;
    backtrack=false;}
tokens {
	//operators
	Eq = '=' ;
	Neq1 = '<>' ;
	Neq2 = '!=' ;
	Le1 = '<=' ;
	Le2 = '!>' ;
	Lt = '<' ;
	Ge1 = '>=' ;
	Ge2 = '!<' ;
	Gt = '>' ;	
	DIV = '/' ;
	PLUS = '+' ;
	MINUS = '-' ;
	MUL = '*' ;
	MOD = '%' ;	
	BITAND = '&' ;
	BITNOT = '~' ;
	BITOR = '|' ;
	BITXOR = '^' ;
	
	LPAREN = '(' ;
	RPAREN = ')' ;
	COLON = ':' ;
	COMMA = ',' ;
	SEMI = ';' ;
	DOT = '.' ;

	//keywords
    ALL = 'all' ;
    AND = 'and' ;
    AS = 'as' ;
    ASC = 'asc' ;
    BETWEEN = 'between' ;
    BY = 'by' ;
    CASE = 'case' ;
    CROSS = 'cross' ;
    DELETE = 'delete' ;
    DESC = 'desc' ;
    DISTINCT = 'distinct' ;
    ELSE = 'else';
    END = 'end';
    EXISTS = 'exists' ;
    FOR = 'for' ;
    FROM = 'from' ;
    GROUP = 'group' ;
    HAVING = 'having' ;
    IN = 'in' ;
    INNER = 'inner' ;
    INSERT = 'insert' ;
    INTO = 'into' ;
    IS = 'is' ;
    JOIN = 'join' ;
    LEFT = 'left' ;
    LIKE = 'like' ;
    NOT = 'not' ;
    NULL = 'null' ;
    ON = 'on' ;
    OR = 'or' ;
    ORDER = 'order' ;
    OUTER = 'outer' ;
    RIGHT = 'right' ;
    SELECT = 'select';
    SET = 'set' ;
    THEN = 'then';
    UNION = 'union' ;
    UPDATE = 'update' ;
    VALUES = 'values' ;
    WHEN = 'when' ;
    WHERE = 'where' ;
    TRUNCATE ='truncate';
    
	//system functions
	SUM = 'sum';
	AVG = 'avg';
	MAX = 'max';
	MIN = 'min';
	COUNT = 'count';
	LEN = 'len';
	
	//data type
	CHAR = 'char';
	NCHAR= 'nchar';
	VARCHAR = 'varchar2';
	NVARCHAR = 'nvarchar2';
	TEXT = 'clob';
	NTEXT = 'nclob';
	DATETIME = 'date';
	INT = 'integer';
	DECIMAL = 'number';
	BINARY = 'blob';
}
@header{using Magic.Framework.OQL.Expressions; }
@lexer::namespace { Magic.Framework.OQL}
@parser::namespace { Magic.Framework.OQL}
////////////////////////////////////////////////////////////////////////////////
//Parser rules
////////////////////////////////////////////////////////////////////////////////
start: statements ; //starting rule
statements: (statement (SEMI?) )* EOF! ;
//sql statements
statement:
	//currently I only implement the SelectStatement, because it's enough for my current project
	//the implication is everything about update, delete, insert statement may be wrong!
	selectStatement
	| updateStatement
	| deleteStatement
	| insertStatement
	;
selectStatement 
	: {bool isUnion=false;} select_1 (UNION ALL? select_2 {isUnion=true;})* orderBy?
	  ->{isUnion}? ^(UnionStmt ^(SelectStmt select_1) (^(TextNode UNION ALL?) ^(SelectStmt select_2))+ orderBy?)
	  ->{!isUnion}? ^(SelectStmt select_1 orderBy?)
	  ->
	;
//select_1 and select_1: to solve the reference conflict while rewriting the rule in selectStatement
select_1: select from? where? groupBy?;
select_2: select_1; 
subQuery: LPAREN selectStatement RPAREN -> selectStatement ;
updateStatement: update from? where?;
deleteStatement: delete where? | truncate;
insertStatement: insert (values | selectStatement ) ;
//################
//sql cluases
select
	: SELECT DISTINCT? columns
	-> ^(SelectClause SELECT DISTINCT? columns)
	;
from: FROM^ tables ;
where: WHERE condition -> ^(WhereClause WHERE condition) ;

orderBy
	: ORDER BY orderBy_1 (COMMA orderBy_1)*
	-> ^(OrderByClause ORDER BY orderBy_1+)
	;
orderBy_1 
	: {int orderMethod=0;} expressions (
	  (ASC {orderMethod=1;})
	| (DESC {orderMethod=2;}))? 
	  ->{orderMethod==0}? ^(OrderByItem expressions)
	  ->{orderMethod==1}? ^(OrderByItem expressions ASC)
	  ->{orderMethod==2}? ^(OrderByItem expressions DESC)
	  ->
	;
groupBy
	: GROUP BY expressions (COMMA expressions)* having?
	-> ^(GroupByClause GROUP BY expressions+ having?)
	;
having: HAVING^ condition;
update
	: UPDATE table SET identifier Eq expressions update_1*
	-> ^(UpdateClause ^(Table table) SET ^(Column identifier) Eq expressions update_1*)
	;
update_1
	: COMMA identifier Eq expressions
	-> COMMA ^(Column identifier) Eq expressions
	;
delete: DELETE FROM? table -> ^(DELETE FROM? ^(Table table)) ; //delete tableA from tableB, tableC ... is not allowed
truncate: TRUNCATE table -> ^(TRUNCATE ^(Table table));
insert
	: INSERT INTO table LPAREN (identifier (COMMA identifier)*)? RPAREN;
values: VALUES LPAREN (expressions (COMMA expressions)*)? RPAREN;
columns: column ( COMMA column )* -> column+;
column //I should name it selectableColumn to avoid semantic confusion
	: MUL -> ^(Column MUL) // select * from ...
	  | identifier DOT MUL -> ^(Column identifier DOT MUL) //select a.*, b.* from ...
      | expressions columnAlias? -> ^(Column expressions columnAlias?) //select a.col1, a.col2, b.col1 ... from ...
      ;
tables: table tables_0*;
tables_0: (COMMA table -> table | join -> join);
table //a single table or a subquery. table variable is not allowed
    : subQuery tableAlias? -> ^(Table subQuery tableAlias?) //subqueries
    | identifier tableAlias? 
      -> ^(Table identifier tableAlias?)
    ;
join //single joined table
    : {int joinType=1;}
    ( CROSS JOIN table
    | ((INNER {joinType=2;}) | ((LEFT {joinType=3;}) | (RIGHT {joinType=4;})) OUTER?) JOIN table (ON condition)?
    )
      ->{joinType==1}? ^(JoinedTable CROSS JOIN table)
      ->{joinType==2}? ^(JoinedTable INNER JOIN table ^(ON condition)?) 
      ->{joinType==3}? ^(JoinedTable LEFT OUTER? JOIN table ^(ON condition)?)
      ->{joinType==4}? ^(JoinedTable RIGHT OUTER? JOIN table ^(ON condition)?)
      ->
    ;
//expressions
//2008.06.21, Richie, remove the keyword "as", see 6.2
columnAlias: AS? identifier -> ^(Alias AS? identifier);
//2008.09.07, Richie, remove the keyword "as", see 6.2
tableAlias: AS? identifier -> ^(Alias identifier);
condition: subCondition ((AND ^ | OR ^) subCondition)*;
subCondition: 
	{bool prefixNot=false;bool isGroup=false;}(NOT {prefixNot=true;})? (
	  LPAREN condition RPAREN {isGroup=true;}
	  | e=predicate
	)
	-> {prefixNot && isGroup}? ^(NOT ^(Group condition))
	-> {prefixNot && !isGroup}? ^(NOT $e)
	-> {!prefixNot && isGroup}? ^(Group condition)
	-> {!prefixNot && !isGroup}? $e
	->
	;
predicate
	: expressions (comparisonOperator  expressions 
	  -> ^(Predicate expressions comparisonOperator  expressions)
	| IS (NOT)? NULL
	  -> ^(Predicate_Is expressions ^(TextNode IS (NOT)? NULL)) //prevent NOT become a condition node
	| NOT? IN LPAREN expressions_0 (COMMA expressions_1)* RPAREN
	  -> ^(IN ^(TextNode NOT? IN) expressions expressions_0 expressions_1*)
	| NOT? IN subQuery
	  -> ^(IN ^(TextNode NOT? IN) expressions subQuery)
	| NOT? BETWEEN expressions AND expressions
	  -> ^(BETWEEN ^(TextNode NOT? BETWEEN) expressions expressions ^(TextNode AND) expressions)
	)
	| EXISTS subQuery -> ^(EXISTS subQuery)
	;
//expressions_0, expressions_1: to solve the reference conflict while rewriting the rule in predicate
expressions: expression (mathOperator expression)*; //expr1 +,-,*,/,% expr2
expressions_0: expressions;
expressions_1: expressions;
expression
    : 
	    function //user defined functions and system functions, 
	    		 //stored procedures with parameters are functions, else they are dbobjects
	    | constant //systemVariables, numbers, strings, null
	    | userVariable
	    | identifier
	    | subQuery
    ;
mathOperator : PLUS | MINUS | MUL | DIV | MOD ;                     
comparisonOperator : Eq | Neq1 | Neq2 | Le1 | Le2 | Lt | Ge1 | Ge2 | Gt | LIKE ;
//################
//functions
function: systemFunction | userFunction ;
userFunction
	: identifier LPAREN (expressions (COMMA expressions_0)*)? RPAREN
	  -> ^(UserFunction identifier (expressions expressions_0*)?)
	;
systemFunction: fnCase | unitarySysFun | fnLen;
//system functions
//the case statement is treated as a special function
fnCase
    : CASE^ (
          expressions (WHEN expressions THEN expressions)+
        | (WHEN condition THEN expressions)+    // boolean expression
        )
    (ELSE expressions)? END
    ;
fnLen
	: LEN LPAREN expressions RPAREN
	  -> ^(LEN expressions)
	;
unitarySysFun: (SUM^ | AVG^ | MAX^ | MIN^ | COUNT^) unitarySysFun_0;
unitarySysFun_0
	: {int paramType=1;} LPAREN ((MUL {paramType=2;}) | (expressions {paramType=3;}))? RPAREN
	  ->{paramType==1}?
	  ->{paramType==2}? MUL
	  ->{paramType==3}? expressions
	  ->
	;

//################
//basic elements
identifier
	: QuotedIdentifier -> {new Identifier($QuotedIdentifier)}
	| NonQuotedIdentifier -> {new Identifier($NonQuotedIdentifier)} ;

datatype
	: datatypeNParam -> ^(DataType datatypeNParam)
	| datatypeOneParam -> ^(DataType datatypeOneParam)
	| datatypeNoParam -> ^(DataType datatypeNoParam)
	;
datatypeNoParam: INT | DATETIME	;
datatypeOneParam
	: (CHAR | NCHAR | VARCHAR | NVARCHAR | TEXT | NTEXT | BINARY)
	  (LPAREN Integer RPAREN)?
	;
datatypeNParam
	: (DECIMAL)
	  (LPAREN Integer (COMMA Integer)? RPAREN)?
	;
	
constant
	: e1=constant_real -> {new Constant(e1.tree)}
	| e2=constant_int -> {new Constant(e2.tree)}
	| NULL -> {new Constant($NULL)}
	| StringLiteral -> {new Constant($StringLiteral)};
constant_real: MINUS? Real -> ^(TextNode MINUS? Real);
constant_int: MINUS? Integer -> ^(TextNode MINUS? Integer);
userVariable: UserVariable -> {new UserVariable($UserVariable)} ;

////////////////////////////////////////////////////////////////////////////////
//Lexer rules
////////////////////////////////////////////////////////////////////////////////
Whitespace
    : (' ' | '\t' | '\n' | '\r'){ $channel = HIDDEN; }
    ;
SingleLineComment
    : '--'( ~('\r' | '\n') )*{ $channel = HIDDEN; }
    ;
MultiLineComment
    : '/*' ( options {greedy=false;} : . )* '*/' { $channel = HIDDEN; }
    ;

StringLiteral: '\'' (~'\'')* '\'' ( '\'' (~'\'')* '\'' )* ;
Integer :(DigitChar)+ ;
Real 
	: 
	Integer '.' Integer
	| '.' Integer
	| Integer 'e' ('+' | '-')? Integer
	;    
QuotedIdentifier
	:'"' (~'"')* '"' ('.' '"' (~'"')* '"')* //stop at the first '"'
    ; 
NonQuotedIdentifier
    options {backtrack=true;}:
      NonQuotedIdentifier_0 (DOT NonQuotedIdentifier_0)* (DOT MUL)? //#tempTable, the global temp tables beginning with ## are disallowed
    ;
NonQuotedIdentifier_0 options {backtrack=true;}
	: LetterChar (LetterChar | DigitChar | '_')*;
//UnknownSystemVariable: '@@' (LetterChar | '_') ('_' | LetterChar | DigitChar)*;
UserVariable: (('@' | ':' | '?') (LetterChar | '_') ('_' | LetterChar | DigitChar)*) | '?' ;
LetterChar:	'a'..'z' | 'A'..'Z';
DigitChar: '0'..'9';

//imaginary tokens, for AST construction
TextNode:;
SelectClause:;
UnionStmt:;
SelectStmt:;
WhereClause:;
GroupByClause:;
OrderByClause:;
OrderByItem:;
UpdateClause:;
UpdateStmt:;
DeleteClause:;
DeleteStmt:;
InsertClause:;
InsertStmt:;
Alias:;
Table:;
JoinedTable:;
Column:;
Predicate:;
Predicate_Is:;
DataType:;
Group:;
UserFunction:;
