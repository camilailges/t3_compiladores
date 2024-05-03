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






//#line 2 "exerc.y"
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
public final static short GE=268;
public final static short LE=269;
public final static short EQ=270;
public final static short NE=271;
public final static short AND=272;
public final static short OR=273;
public final static short UMINUS=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    4,    4,    4,    5,
    5,    3,    3,    3,    8,    9,    9,    9,    6,    6,
   11,   11,    7,   12,   12,   13,   13,   13,   13,   14,
   14,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   15,   15,   15,
};
final static short yylen[] = {                            2,
    1,    2,    2,    0,    3,    0,    1,    1,    1,    1,
    3,    7,    7,    0,    4,    1,    3,    0,    1,    0,
    4,    2,    3,    2,    0,    1,    5,    6,    4,    2,
    0,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    1,    1,    1,    1,
};
final static short yydefred[] = {                         4,
    0,    0,    7,    8,    9,    0,    2,    3,    0,    0,
    0,   10,    0,    0,    0,    5,    0,    0,    0,   11,
    0,    0,    0,    0,   22,    0,    0,    0,   25,   13,
    0,   12,    0,   21,    0,    0,    0,   23,   26,   24,
    0,    0,    0,    0,   48,    0,    0,   49,    0,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   29,    0,    0,
    0,    0,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,   43,   27,    0,    0,   15,    0,
   28,    0,   30,
};
final static short yydgoto[] = {                          1,
    2,    7,    8,   21,   13,   22,   39,   48,   71,   49,
   23,   33,   40,   91,   50,
};
final static short yysindex[] = {                         0,
    0,  -87,    0,    0,    0, -119,    0,    0, -241, -235,
 -224,    0,   15,   -2,    8,    0, -185,  -72,  -72,    0,
 -178,   44,   56,   53,    0,  -20,  -72,  -20,    0,    0,
 -149,    0,  -71,    0,   49,   74,   76,    0,    0,    0,
   59,   59,   59,   87,    0,   59,   59,    0,   64,    0,
  -34,  -18,   59,  -11,   70,   59,   59,   59,   59,   59,
   59,   59,   59,   59,   59,   59,   59,    0, -118, -118,
   81,   70,    0,  114,  114,  114,  114,   86,   86,  114,
  114,  -32,  -32,    0,    0,    0, -140,   59,    0, -118,
    0,   70,    0,
};
final static short yyrindex[] = {                         0,
    0,  136,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  110,  110,    0,
    0,    0,  117,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
    0,    0,   94,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  138,    0,    9,   16,   22,   29,  -24,   46,   36,
   42,   -4,    2,    0,    0,    0,  -58,    0,    0,    0,
    0,  139,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   91,    0,  143,  -14,    0,    0,  107,
    0,    0,   50,    0,    0,
};
final static int YYTABLESIZE=357;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   47,   47,   47,   47,   29,   47,   69,   66,   64,   66,
   65,   30,   67,   32,   67,   12,   33,   47,   47,   33,
   47,   14,   70,   66,   64,   63,   65,   62,   67,   73,
   66,   64,   15,   65,   33,   67,   40,   18,   40,   40,
   40,   63,   41,   62,   41,   41,   41,   19,   63,   38,
   62,   29,   38,   38,   40,   40,   39,   40,   17,   39,
   41,   41,   34,   41,   31,   34,   31,   38,   38,   35,
   38,   20,   35,   16,   39,   39,   36,   39,   25,   36,
   34,   34,   37,   34,   26,   37,   32,   35,   35,   32,
   35,   47,    9,   28,   36,   36,   11,   36,   46,   27,
   37,   37,   29,   37,   32,   66,   64,   34,   65,   41,
   67,   66,   64,   42,   65,   43,   67,   31,   86,   87,
   90,   89,   68,   63,   88,   62,   53,   66,   64,   63,
   65,   62,   67,   45,   18,    1,   45,   18,   35,   93,
   36,   37,    3,    4,    5,   63,   10,   62,   51,   52,
   20,   45,   54,   55,    0,   66,   64,   19,   65,   72,
   67,   24,   74,   75,   76,   77,   78,   79,   80,   81,
   82,   83,   84,   85,    3,    4,    5,    6,   16,   17,
    0,   16,   17,    0,    0,   35,    0,   36,   37,    3,
    4,    5,    0,    0,   92,    0,    0,    0,   31,    0,
   31,   31,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   47,   47,   47,
   47,   47,    0,   56,   57,   58,   59,   60,   61,    0,
    0,    0,    0,    0,    0,    0,    0,   33,   33,   56,
   57,   58,   59,   60,   61,    0,   56,   57,   58,   59,
   60,   61,    0,   40,   40,   40,   40,   40,   40,   41,
   41,   41,   41,   41,   41,    0,   38,   38,   38,   38,
   38,   38,    0,   39,   39,   39,   39,   39,   39,   34,
   34,   34,   34,   34,   34,    0,   35,   35,   35,   35,
   35,   35,    0,   36,   36,   36,   36,   36,   36,   37,
   37,   37,   37,   37,   37,   44,   45,   32,   32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   56,   57,   58,   59,   60,   61,   56,   57,   58,
   59,   60,   61,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   56,   57,   58,   59,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,  123,   47,   41,   42,   43,   42,
   45,   26,   47,   28,   47,  257,   41,   59,   60,   44,
   62,  257,   41,   42,   43,   60,   45,   62,   47,   41,
   42,   43,  257,   45,   59,   47,   41,   40,   43,   44,
   45,   60,   41,   62,   43,   44,   45,   40,   60,   41,
   62,  123,   44,  125,   59,   60,   41,   62,   44,   44,
   59,   60,   41,   62,  123,   44,  125,   59,   60,   41,
   62,  257,   44,   59,   59,   60,   41,   62,  257,   44,
   59,   60,   41,   62,   41,   44,   41,   59,   60,   44,
   62,   33,    2,   41,   59,   60,    6,   62,   40,   44,
   59,   60,  123,   62,   59,   42,   43,  257,   45,   61,
   47,   42,   43,   40,   45,   40,   47,   27,   69,   70,
  261,   41,   59,   60,   44,   62,   40,   42,   43,   60,
   45,   62,   47,   41,   41,    0,   44,   44,  257,   90,
  259,  260,  262,  263,  264,   60,  266,   62,   42,   43,
   41,   59,   46,   47,   -1,   42,   43,   41,   45,   53,
   47,   19,   56,   57,   58,   59,   60,   61,   62,   63,
   64,   65,   66,   67,  262,  263,  264,  265,   41,   41,
   -1,   44,   44,   -1,   -1,  257,   -1,  259,  260,  262,
  263,  264,   -1,   -1,   88,   -1,   -1,   -1,  257,   -1,
  259,  260,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  268,  269,  270,  271,
  272,  273,   -1,  268,  269,  270,  271,  272,  273,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,  268,
  269,  270,  271,  272,  273,   -1,  268,  269,  270,  271,
  272,  273,   -1,  268,  269,  270,  271,  272,  273,  268,
  269,  270,  271,  272,  273,   -1,  268,  269,  270,  271,
  272,  273,   -1,  268,  269,  270,  271,  272,  273,  268,
  269,  270,  271,  272,  273,   -1,  268,  269,  270,  271,
  272,  273,   -1,  268,  269,  270,  271,  272,  273,  268,
  269,  270,  271,  272,  273,  257,  258,  272,  273,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  268,  269,  270,  271,  272,  273,  268,  269,  270,
  271,  272,  273,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  268,  269,  270,  271,
};
}
final static short YYFINAL=1;
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
"DOUBLE","BOOLEAN","FUNC","VOID","RETURN","GE","LE","EQ","NE","AND","OR",
"UMINUS",
};
final static String yyrule[] = {
"$accept : Prog",
"Prog : ListaDecl",
"ListaDecl : ListaDecl DeclVar",
"ListaDecl : ListaDecl DeclFun",
"ListaDecl :",
"DeclVar : Tipo ListaIdent ';'",
"DeclVar :",
"Tipo : INT",
"Tipo : DOUBLE",
"Tipo : BOOLEAN",
"ListaIdent : IDENT",
"ListaIdent : ListaIdent ',' IDENT",
"DeclFun : FUNC Tipo IDENT '(' FormalPar ')' Bloco",
"DeclFun : FUNC VOID IDENT '(' FormalPar ')' Bloco",
"DeclFun :",
"ChamadaFunc : IDENT '(' ParamChamada ')'",
"ParamChamada : E",
"ParamChamada : ParamChamada ',' E",
"ParamChamada :",
"FormalPar : ParamList",
"FormalPar :",
"ParamList : ParamList ',' Tipo IDENT",
"ParamList : Tipo IDENT",
"Bloco : '{' ListaCmd '}'",
"ListaCmd : ListaCmd Cmd",
"ListaCmd :",
"Cmd : Bloco",
"Cmd : WHILE '(' E ')' Cmd",
"Cmd : IF '(' E ')' Cmd RestoIf",
"Cmd : IDENT '=' E ';'",
"RestoIf : ELSE Cmd",
"RestoIf :",
"E : E OR E",
"E : E AND E",
"E : E EQ E",
"E : E NE E",
"E : E '>' E",
"E : E '<' E",
"E : E GE E",
"E : E LE E",
"E : E '+' E",
"E : E '-' E",
"E : E '*' E",
"E : E '/' E",
"E : '(' E ')'",
"E : '!' E",
"E : F",
"F : IDENT",
"F : NUM",
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
