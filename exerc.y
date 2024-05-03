%{
  import java.io.*;
%}


%token IDENT NUM WHILE IF ELSE INT DOUBLE BOOLEAN FUNC VOID RETURN GE LE EQ NE AND OR UMINUS

%left AND OR
%left GE LE EQ NE '>' '<'
%left '+' '-'
%left '*' '/'
%nonassoc UMINUS

%%

Prog : ListaDecl
    ;

ListaDecl : ListaDecl DeclVar
          | ListaDecl DeclFun
          | // empty
          ;

DeclVar : Tipo ListaIdent ';'
        | // empty
        ;

Tipo : INT
     | DOUBLE
     | BOOLEAN
     ;

ListaIdent : IDENT
           | ListaIdent ',' IDENT
           ;

DeclFun : FUNC Tipo IDENT '(' FormalPar ')' Bloco
        | FUNC VOID IDENT '(' FormalPar ')' Bloco
        | // empty
        ;
ChamadaFunc : IDENT '(' ParamChamada ')'
            ;

ParamChamada : E
             | ParamChamada ',' E
             | // empty
             ;

FormalPar : ParamList
          | // empty
          ;

ParamList : ParamList ',' Tipo IDENT
          | Tipo IDENT
          ;

Bloco : '{' ListaCmd '}'
      ;

ListaCmd : ListaCmd Cmd
         | // empty
         ;

Cmd : Bloco
    | WHILE '(' E ')' Cmd
    | IF '(' E ')' Cmd RestoIf
    | IDENT '=' E ';'
    ;

RestoIf : ELSE Cmd
        | // empty
        ;

E : E OR E
  | E AND E
  | E EQ E
  | E NE E
  | E '>' E
  | E '<' E
  | E GE E
  | E LE E
  | E '+' E
  | E '-' E
  | E '*' E
  | E '/' E
  | '(' E ')'
  | '!' E
  | F
  ;

F : IDENT
  | NUM
  | ChamadaFunc
  ;

