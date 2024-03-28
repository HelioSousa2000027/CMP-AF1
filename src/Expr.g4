grammar Expr; //The code defines a grammar named Expr. This grammar is used for parsing expressions in a specific format.

options {
    language=Java; //The options section specifies that we're using Python for this grammar.
}

prog: expr EOF; //This rule states that a complete program (prog) consists of an expression (expr) followed by the end of the input (EOF).

//This section defines the various rules for parsing expressions:
expr: INT                   //Matches an integer value (e.g., 123).
    | ID    //Matches an expression enclosed in parentheses (e.g., (2 + 3)).
    | INT expr    //Matches an expression enclosed in parentheses (e.g., (2 + 3)).
    | LPAREN expr RPAREN    //Matches an expression enclosed in parentheses (e.g., (2 + 3)).
    | (PLUS|MINUS) expr          //Matches a unary plus or minus operator followed by an integer (e.g., -5).
    | expr op=(MULT|DIV) expr   //Matches two expressions separated by a multiplication or division operator (* or /).
    | expr op=(PLUS|MINUS) expr     //Matches two expressions separated by a plus or minus operator (+ or -).
    ;

// Lexer rules
INT: [0-9]+; //Matches a sequence of digits, representing an integer.
ID: [a-zA-Z][a-zA-Z0-9]*; //matches an identifier variable
//Match the corresponding arithmetic operators.
PLUS: '+'; 
MINUS: '-';
MULT: '*';
DIV: '/';
//Match parentheses.
LPAREN: '(';
RPAREN: ')';
WS: [ \t\r\n]+ -> skip;  //Matches whitespace characters (spaces, tabs, newlines) and skips them, as they're not significant for parsing.

