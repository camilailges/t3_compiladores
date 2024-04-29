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
WHILE { return Parser.WHILE;}
INT { return Parser.INT;}
DOUBLE { return Parser.DOUBLE;}
BOOLEAN { return Parser.BOOLEAN;}
FUNC { return Parser.FUNC;}
VOID { return Parser.VOID;}
RETURN { return Parser.RETURN;}

[0-9]+ { return Parser.num;}
[a-zA-Z][a-zA-Z0-9]* { return Parser.ident;}


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






