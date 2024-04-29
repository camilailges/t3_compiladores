%%

%byaccj


%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

NL  = \n | \r | \r\n

%%

"$TRACE_ON"  { yyparser.setDebug(true);  }
"$TRACE_OFF" { yyparser.setDebug(false); }

IF { return Parser.IF;}
ELSE { return Parser.ELSE;}
ENDIF { return Parser.endif;}
IDENT { return Parser.ident;}
WHILE { return Parser.while;}
INT { return Parser.int;}
DOUBLE { return Parser.double;}
BOOLEAN { return Parser.boolean;}
FUNC { return Parser.func;}
VOID { return Parser.void;}
RETURN { return Parser.return;}

[0-9]+ { return Parser.num;}
[a-zA-Z][a-zA-Z0-9]* { return Parser.ident;}

%token IDENT, NUM, WHILE, IF, ELSE, INT, DOUBLE, BOOLEAN, FUNC, VOID, RETURN


"<" |
">" |
">="| 
"=>"| 
"!="|
"=="|
"!" |
"&&"|
"||"|
"{" |
"}" |
"=" |
"(" |
")" |
";" |
"*" |
"/" |
"+" |
"-"     { return (int) yycharat(0); }

[ \t]+ { }
{NL}+  { } 

.    { System.err.println("Error: unexpected character '"+yytext()+"' na linha "+yyline); return YYEOF; }






