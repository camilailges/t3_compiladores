%{
  import java.io.*;
%}
   

%token IDENT, NUM, WHILE, IF, ELSE, INT, DOUBLE, BOOLEAN, FUNC, VOID, RETURN

%left '+' '-'
%left '*' '/'
%nonassoc '<' '>' '>=' '=>' '!=' '=='
%left '&&' '||'

%%

Prog : ListaDecl
    ;

ListaDecl :  DeclVar  ListaDecl
          |  DeclFun  ListaDecl
          |  // vazio
          ;


DeclVar : Tipo ListaIdent ';'
        ;


Tipo : INT
    | DOUBLE
    | BOOLEAN
    ;

ListaIdent : IDENT RestoListaIdent
           ;


RestoListaIdent : ',' ListaIdent
                | // vazio
                ;


DeclFun : FUNC Tipo IDENT '(' FormalPar ')' '{' DeclVar ListaCmd Retorno '}'
        | FUNC VOID IDENT '(' FormalPar ')' '{' DeclVar ListaCmd '}'
        ;


Retorno : RETURN F ';'
        ;

ChamadaFunc : IDENT '(' ParamChamada ')' ';'


ParamChamada : L ',' ParamChamada
             | L
             | // vazio
             ;


FormalPar : ParamList
          | // vazio
          ;


ParamList : Tipo IDENT RestoParamList
          ;


RestoParamList : ',' ParamList
               | // vazio
               ;



Bloco : '{' ListaCmd '}' ';'
      ;



ListaCmd : Cmd ListaCmd
         | // vazio
         ;


Cmd : Bloco
    | WHILE '(' E ')' Cmd
    | IDENT '=' E ';'
    | IF '(' E ')' Cmd RestoIf
    ;



RestoIf : ELSE Cmd
       | // vazio
       ;


E : E '+' T
    | E '-' T
    | T
    ;

T : T '*' L
  | T '/' L
  | L
  ;

L : L '>' R
  | L '<' R
  | L '<=' R
  | L '>=' R
  | L '!=' R
  | L '==' R
  | R
  ;

R : R '||' F
  | R '&&' F
  | F
  ;

F : IDENT
  | NUM
  | '(' E ')'
  | ChamadaFunc
  ;

//T : T * F
//  | T / F
//  | F
//  ;