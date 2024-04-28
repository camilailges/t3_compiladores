//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "asdr.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IDENT=257;
public final static short NUM=258;
public final static short WHILE=259;
public final static short IF=260;
public final static short ELSE=261;
public final static short INT=262;
public final static short DOUBLE=263;
public final static short BOOLEAN=264;
public final static short FUNC=265;
public final static short VOID=266;
public final static short RETURN=267;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    4,    4,    4,    5,
    6,    6,    3,    3,    3,    9,   11,   12,   12,   12,
    7,    7,   14,   15,   15,   16,    8,    8,   17,   17,
   17,   17,   19,   19,   18,   18,   18,   20,   20,   20,
   13,   13,   13,   13,   13,   13,   13,   21,   21,   21,
   21,   10,   10,   10,   10,
};
final static short yylen[] = {                            2,
    1,    2,    2,    0,    3,    0,    1,    1,    1,    2,
    2,    0,   11,   10,    0,    3,    5,    3,    1,    0,
    1,    0,    3,    2,    0,    4,    2,    0,    1,    5,
    4,    6,    2,    0,    3,    3,    1,    3,    3,    1,
    3,    3,    3,    3,    3,    3,    1,    3,    3,    2,
    1,    1,    1,    3,    1,
};
final static short yydefred[] = {                         0,
    7,    8,    9,    0,    0,    1,    0,    0,    0,    0,
    0,    2,    3,    0,    0,    0,    0,    0,   10,    5,
    0,    0,   11,    0,    0,   21,    0,    0,    0,    0,
    0,   23,    0,    0,   24,    0,    0,    0,    0,    0,
    0,    0,   29,    0,    0,    0,    0,    0,    0,   14,
   27,    0,    0,    0,   53,    0,    0,   51,   55,    0,
    0,    0,    0,    0,    0,    0,    0,   13,    0,   50,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   31,
    0,    0,    0,    0,    0,    0,   26,   16,    0,    0,
   54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   49,   48,   30,    0,    0,    0,    0,   32,   17,
   18,   33,
};
final static short yydgoto[] = {                          5,
    6,    7,    8,    9,   15,   19,   25,   42,   53,   58,
   59,   89,   60,   26,   32,   43,   44,   61,  109,   62,
   63,
};
final static short yysindex[] = {                       -55,
    0,    0,    0,  -69,    0,    0,  -55,  -55, -216, -194,
 -172,    0,    0,   59,   40,   74,   86, -216,    0,    0,
 -182, -182,    0, -150,  103,    0,  106,  111,   41,   43,
 -182,    0, -182, -182,    0,  -89,  -89,   97,  129,  145,
  -89,   62,    0,  -89,  -78,  -16,  -16,  -16,   71,    0,
    0,  -35,   78,  173,    0,  -16,  -16,    0,    0,   10,
   95,   31, -250,   80,  132,  146,  155,    0,  -16,    0,
  161,  -16,  -16,  -16,  -16,  -16,  -16,  -16,  -16,    0,
  -16,  -16,  -35,  -35,  -89,  -89,    0,    0,  174,  -17,
    0, -250, -250, -250, -250, -250, -250,   31,   31,   10,
   10,    0,    0,    0,  -45,  158,  -16,  -89,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                       218,
    0,    0,    0,    0,    0,    0,  218,  218,    0,    0,
    0,    0,    0,  160,    0,    0,    0,    0,    0,    0,
  179,  179,    0,    0,    0,    0,    0,  180,    0,    0,
    0,    0,  -79, -107,    0,   99,  -42,    0,    0,    0,
   99,    0,    0, -110,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,   92,
    0,   77,  -34,    0,    0,    0,    0,    0,  185,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  187,
    0,  -12,   -5,   17,   24,   46,   53,  100,  131,  120,
  141,    0,    0,    0, -111,    0,  185,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   67,  165,    0,   88,  217,    0,  216,   12,    0,  -32,
    0,  136,   35,  208,    0,    0,   16,  144,    0,  133,
   55,
};
final static int YYTABLESIZE=327;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   52,   52,   52,   52,   57,   52,   47,   47,   47,   47,
   47,   34,   47,   34,   28,    6,   56,   52,   52,   67,
   52,   83,   84,   57,   47,   47,  107,   47,   42,   42,
   42,   42,   42,   41,   42,   41,   41,   41,   41,   41,
   14,   41,   72,    6,   73,    6,   42,   42,   45,   42,
  102,  103,   49,   41,   41,   51,   41,   44,   44,   44,
   44,   44,   16,   44,   45,   45,   45,   45,   45,   72,
   45,   73,   81,   12,   13,   44,   44,   82,   44,    1,
    2,    3,   45,   45,   17,   45,   46,   46,   46,   46,
   46,   11,   46,   43,   43,   43,   43,   43,   20,   43,
  104,  105,   18,   90,   46,   46,   28,   46,   24,   24,
   70,   43,   43,   21,   43,  100,  101,   37,   24,   37,
   85,   37,   78,  112,   79,   22,   92,   93,   94,   95,
   96,   97,   40,   40,   40,   37,   40,   78,   40,   79,
   35,   90,   35,   29,   35,   34,   30,   34,   34,    6,
   40,    6,    6,   80,   31,   34,   28,   46,   35,    6,
   38,   38,   38,   33,   38,   34,   38,   38,   47,   39,
   40,   36,   86,   36,   78,   36,   79,    6,   38,    6,
    6,   39,   39,   39,   48,   39,   50,   39,   52,   36,
   64,   65,    1,    2,    3,   66,   10,   36,   37,   39,
   71,   91,   68,   78,   87,   79,    1,    2,    3,    4,
   98,   99,   69,   88,  106,  108,  110,    4,   12,   22,
   25,   54,   55,   28,   28,   20,   52,   19,   52,   52,
   52,   52,   52,   47,   23,   47,   47,   27,   35,   47,
   54,   55,  111,    0,    0,    0,    0,    0,    0,    0,
   74,    0,   75,   76,    0,   42,   77,   42,   42,    0,
    0,   42,   41,    0,   41,   41,    0,    0,   41,    0,
    0,    0,    0,    0,    0,    0,    0,   74,    0,   75,
   76,    0,    0,   77,   44,    0,   44,   44,    0,    0,
   44,   45,    0,   45,   45,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   46,    0,   46,   46,    0,    0,   46,
   43,    0,   43,   43,    0,    0,   43,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   40,   47,   41,   42,   43,   44,
   45,  123,   47,  125,  125,  123,   33,   59,   60,   52,
   62,  272,  273,   40,   59,   60,   44,   62,   41,   42,
   43,   44,   45,  123,   47,   41,   42,   43,   44,   45,
  257,   47,   60,  123,   62,  125,   59,   60,   37,   62,
   83,   84,   41,   59,   60,   44,   62,   41,   42,   43,
   44,   45,  257,   47,   41,   42,   43,   44,   45,   60,
   47,   62,   42,    7,    8,   59,   60,   47,   62,  262,
  263,  264,   59,   60,  257,   62,   41,   42,   43,   44,
   45,    4,   47,   41,   42,   43,   44,   45,   59,   47,
   85,   86,   44,   69,   59,   60,  257,   62,   21,   22,
   56,   59,   60,   40,   62,   81,   82,   41,   31,   43,
   41,   45,   43,  108,   45,   40,   72,   73,   74,   75,
   76,   77,   41,   42,   43,   59,   45,   43,   47,   45,
   41,  107,   43,   41,   45,  257,   41,  259,  260,  257,
   59,  259,  260,   59,   44,  267,  267,   61,   59,  267,
   41,   42,   43,  123,   45,  123,   47,  257,   40,  259,
  260,   41,   41,   43,   43,   45,   45,  257,   59,  259,
  260,   41,   42,   43,   40,   45,  125,   47,  267,   59,
   47,   48,  262,  263,  264,  125,  266,   33,   34,   59,
   57,   41,  125,   43,   59,   45,  262,  263,  264,  265,
   78,   79,   40,   59,   41,  261,   59,    0,   59,   41,
   41,  257,  258,  125,  267,   41,  268,   41,  270,  271,
  272,  273,  274,  268,   18,  270,  271,   22,   31,  274,
  257,  258,  107,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  268,   -1,  270,  271,   -1,  268,  274,  270,  271,   -1,
   -1,  274,  268,   -1,  270,  271,   -1,   -1,  274,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  268,   -1,  270,
  271,   -1,   -1,  274,  268,   -1,  270,  271,   -1,   -1,
  274,  268,   -1,  270,  271,   -1,   -1,  274,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  268,   -1,  270,  271,   -1,   -1,  274,
  268,   -1,  270,  271,   -1,   -1,  274,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"IDENT","NUM","WHILE","IF","ELSE","INT",
"DOUBLE","BOOLEAN","FUNC","VOID","RETURN","\">=\"","\"=>\"","\"!=\"","\"==\"",
"\"&&\"","\"||\"","\"<=\"",
};
final static String yyrule[] = {
"$accept : Prog",
"Prog : ListaDecl",
"ListaDecl : DeclVar ListaDecl",
"ListaDecl : DeclFun ListaDecl",
"ListaDecl :",
"DeclVar : Tipo ListaIdent ';'",
"DeclVar :",
"Tipo : INT",
"Tipo : DOUBLE",
"Tipo : BOOLEAN",
"ListaIdent : IDENT RestoListaIdent",
"RestoListaIdent : ',' ListaIdent",
"RestoListaIdent :",
"DeclFun : FUNC Tipo IDENT '(' FormalPar ')' '{' DeclVar ListaCmd Retorno '}'",
"DeclFun : FUNC VOID IDENT '(' FormalPar ')' '{' DeclVar ListaCmd '}'",
"DeclFun :",
"Retorno : RETURN F ';'",
"ChamadaFunc : IDENT '(' ParamChamada ')' ';'",
"ParamChamada : L ',' ParamChamada",
"ParamChamada : L",
"ParamChamada :",
"FormalPar : ParamList",
"FormalPar :",
"ParamList : Tipo IDENT RestoParamList",
"RestoParamList : ',' ParamList",
"RestoParamList :",
"Bloco : '{' ListaCmd '}' ';'",
"ListaCmd : Cmd ListaCmd",
"ListaCmd :",
"Cmd : Bloco",
"Cmd : WHILE '(' E ')' Cmd",
"Cmd : IDENT '=' E ';'",
"Cmd : IF '(' E ')' Cmd RestoIf",
"RestoIf : ELSE Cmd",
"RestoIf :",
"E : E '+' T",
"E : E '-' T",
"E : T",
"T : T '*' L",
"T : T '/' L",
"T : L",
"L : L '>' R",
"L : L '<' R",
"L : L \"<=\" R",
"L : L \">=\" R",
"L : L \"!=\" R",
"L : L \"==\" R",
"L : R",
"R : R \"||\" F",
"R : R \"&&\" F",
"R : '!' R",
"R : F",
"F : IDENT",
"F : NUM",
"F : '(' E ')'",
"F : ChamadaFunc",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
