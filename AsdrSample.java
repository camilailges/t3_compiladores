//Camila Ilges, Eduardo Pretto, João Pedro Feijó

import java.io.*;

public class AsdrSample {

  private static final int BASE_TOKEN_NUM = 301;
  public static final int IDENT  = 301;
  public static final int NUM 	 = 302;
  public static final int WHILE  = 303;
  public static final int IF	 = 304;
  public static final int FI	 = 305;
  public static final int ELSE = 306;
  public static final int INT = 307;
  public static final int DOUBLE = 308;
  public static final int BOOLEAN = 309;
  public static final int FUNC = 310;
  public static final int VOID = 311;


    public static final String tokenList[] = 
      {"IDENT",
		 "NUM", 
		 "WHILE", 
		 "IF", 
		 "FI",
		 "ELSE",
       "INT",
       "DOUBLE",
       "BOOLEAN",
       "FUNC", 
       "VOID"
      };
                                      
  /* referencia ao objeto Scanner gerado pelo JFLEX */
  private Yylex lexer;

  public ParserVal yylval;

  private static int laToken;
  private boolean debug;

  
  /* construtor da classe */
  public AsdrSample (Reader r) {
      lexer = new Yylex (r, this);
  }

// Nova gramática

// Prog -->  ListaDecl

  private void Prog() {
      ListaDecl();
   }

   
// ListaDecl -->  DeclVar  ListaDecl
//             |  DeclFun  ListaDecl
//             |  /* vazio */

   private void ListaDecl() {
      if (laToken == INT || laToken == DOUBLE || laToken == BOOLEAN) {
         if (debug) System.out.println("ListaDecl --> DeclVar ListaDecl");
         DeclVar();
         ListaDecl();
      }
      else if (laToken == FUNC){
         DeclFunc();
         ListaDecl();
      }
      else{
         
      }
   }

   
// DeclVar --> Tipo ListaIdent ';'
//           | /* vazio */


   private void DeclVar(){
      if (laToken ==INT || laToken == DOUBLE || laToken == BOOLEAN){
         if (debug) System.out.println("DeclVar --> Tipo ListaIdent ;");
         Tipo();
         ListaIdent();
         verifica(';');
      } else{

      }
   }

   
// Tipo --> int | double | boolean

   private void Tipo(){
      if (laToken == INT){
         if (debug) System.out.println("Tipo --> int");
         verifica(INT);
      } else if (laToken == DOUBLE){
         if(debug) System.out.println("ipo --> double");
         verifica(DOUBLE);
      } else if (laToken == BOOLEAN){
         if(debug) System.out.println("Tipo --> boolean");
         verifica(BOOLEAN);
      } else{
         yyerror("Esperado int, double ou boolean");
      }
   }

   
// ListaIdent --> IDENT , ListaIdent  
//              | IDENT   

//NOVO:
// ListaIdent --> IDENT RestoListaIdent

//RestoListaIdent --> , ListaIdent
//                  | /* vazio */

   private void ListaIdent(){
      if (laToken == IDENT){
         if(debug) System.out.println("ListaIdent --> IDENT , RestoListaIdent");
         verifica(IDENT);
         RestoListaIdent();
      }
   }

   private void RestoListaIdent(){
      if(laToken == ','){
         if (debug) System.out.println("RestoListaIdent --> , ListaIdent");
         verifica(',');
         ListaIdent();
      } else {

      }
   }

   
// DeclFun --> FUNC tipoOuVoid IDENT '(' FormalPar ')' '{' DeclVar ListaCmd '}'
//           | /* vazio */

   private void DeclFunc(){
      if (laToken == FUNC){
         if (debug) System.out.println("DeclFunc --> FUNC TipoOuVoid IDENT ( FormalPar ) { DeclVar ListaCmd }");
         verifica(FUNC);
         TipoOuVoid();
         verifica(IDENT);
         verifica('(');
         FormalPar();
         verifica(')');
         verifica('{');
         DeclVar();
         ListaCmd();
         verifica('}');
      } else {

      }
   }

   
// TipoOuVoid --> Tipo | VOID

   private void TipoOuVoid(){
      if (laToken == INT || laToken == DOUBLE || laToken == BOOLEAN){
         if(debug) System.out.println("TipoOuVoid --> Tipo");
         Tipo();
      } else if (laToken == VOID){ //precisa colocar void no lex ou nos tokens?
         if(debug) System.out.println("TipoOuVoid --> VOID");
         verifica(VOID);
      } else{
         yyerror("Esperado int, double, boolean ou void");
      }
   }

   
// FormalPar -> paramList | /* vazio */


   private void FormalPar(){
      if (laToken == INT || laToken == DOUBLE || laToken == BOOLEAN){
         if(debug) System.out.println("FormalPar --> paramList");
         ParamList();
      } else{

      }
   }

   
// paramList --> Tipo IDENT , ParamList
//             | Tipo IDENT 

//NOVO:

//paramList --> Tipo IDENT RestoParamList

//RestoParamList --> ParamList
//                 | /* vazio */

   private void ParamList(){
      if (laToken == INT || laToken == DOUBLE || laToken == BOOLEAN){
         if(debug) System.out.println("paramList --> Tipo IDENT , ParamList");
         Tipo(); //como verifico a segunda derivação
         verifica(IDENT);
         RestoParamList();
      }
   }

   private void RestoParamList(){
      if (laToken == ','){
         if (debug) System.out.println("RestoParamList --> , ParamList");
         verifica(',');
         ParamList();
      } else {
         
      }
   }

   
// Bloco --> { ListaCmd }

  private void Bloco() {
      if (debug) System.out.println("Bloco --> { Cmd }");
      //if (laToken == '{') {
         verifica('{');
         ListaCmd();
         verifica('}');
      //}
  }

  
// ListaCmd --> Cmd ListaCmd
//   |    /* vazio */

  private void ListaCmd() {
      if (laToken == '{' || laToken == WHILE || laToken == IDENT || laToken == IF) {
         if (debug) System.out.println("ListaCmd --> Cmd ListaCmd");
         Cmd();
         ListaCmd();
      } else {
         if (debug) System.out.println("ListaCmd --> vazio");
      }
  }

  // Cmd --> Bloco
  //     | while ( E ) Cmd
  //     | IDENT = E ;
  //     | if ( E ) Cmd RestoIf

  private void Cmd() {
      if (laToken == '{') {
         if (debug) System.out.println("Cmd --> Bloco");
         Bloco();
	   }    
      else if (laToken == WHILE) {
         if (debug) System.out.println("Cmd --> WHILE ( E ) Cmd");
         verifica(WHILE);    // laToken = this.yylex(); 
  		   verifica('(');
  		   E();
         verifica(')');
         Cmd();
	   }
      else if (laToken == IDENT ) {
         if (debug) System.out.println("Cmd --> IDENT = E ;");
            verifica(IDENT);  
            verifica('='); 
            E();
		      verifica(';');
	   }
    else if (laToken == IF) {
         if (debug) System.out.println("Cmd --> if (E) Cmd RestoIF");
         verifica(IF);
         verifica('(');
  		   E();
         verifica(')');
         Cmd();
         RestoIF();
	   }
 	else yyerror("Esperado {, if, while ou identificador");
   }


   
// RestoIf -> else Cmd
//       |    /* vazio */


   private void RestoIF() {
       if (laToken == ELSE) {
         if (debug) System.out.println("RestoIF --> else Cmd");
         verifica(ELSE);
         Cmd();
	   }
      else {
         if (debug) System.out.println("RestoIF --> vazio");
      }
     }  

     // E --> E + T
      //     | E - T
      //     | T
     
     
  private void E() {
      T();
      El();
  }

   private void El() {
      if(laToken == '+' || laToken == '-') {
         int op = laToken;
         verifica(op);
         T();
         El();
      }
   }

   // T --> T * F
   //     | T / F
   //     | F    

   private void T() {
      F();
      Tl();
   }

   private void Tl() {
      if (laToken == '*' || laToken == '/') {
         int op = laToken;
         verifica(op);
         F();
         Tl();
      }
   }

   
    
// F -->  IDENT
//     | NUM
//     | ( E )

   private void F() {
      if (laToken == IDENT) {
         if (debug) System.out.println("F --> IDENT");
         verifica(IDENT);
      }
      else if (laToken == NUM) {
         if (debug) System.out.println("F --> NUM");
         verifica(NUM);
      }
      else if (laToken == '(') {
         if (debug) System.out.println("F --> ( E )");
         verifica('(');
         E();
         verifica(')');
      }
      else {
         yyerror("Esperado operando identificador ou numero ou (");
      }
   }


  private void verifica(int expected) {
      if (laToken == expected)
         laToken = this.yylex();
      else {
         String expStr, laStr;       

		expStr = ((expected < BASE_TOKEN_NUM )
                ? ""+(char)expected
			     : tokenList[expected-BASE_TOKEN_NUM]);
         
		laStr = ((laToken < BASE_TOKEN_NUM )
                ? Character.toString(laToken)
                : tokenList[laToken-BASE_TOKEN_NUM]);

          yyerror( "esperado token: " + expStr +
                   " na entrada: " + laStr);
     }
   }

   /* metodo de acesso ao Scanner gerado pelo JFLEX */
   private int yylex() {
       int retVal = -1;
       try {
           yylval = new ParserVal(0); //zera o valor do token
           retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
       } catch (IOException e) {
           System.err.println("IO Error:" + e);
          }
       return retVal; //retorna o token para o Parser 
   }

  /* metodo de manipulacao de erros de sintaxe */
  public void yyerror (String error) {
     System.err.println("Erro: " + error);
     System.err.println("Entrada rejeitada");
     System.out.println("\n\nFalhou!!!");
     System.exit(1);
     
  }

  public void setDebug(boolean trace) {
      debug = true;
  }


  /**
   * Runs the scanner on input files.
   *
   * This main method is the debugging routine for the scanner.
   * It prints debugging information about each returned token to
   * System.out until the end of file is reached, or an error occured.
   *
   * @param args   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String[] args) {
     AsdrSample parser = null;
     try {
         if (args.length == 0)
            parser = new AsdrSample(new InputStreamReader(System.in));
         else 
            parser = new  AsdrSample( new java.io.FileReader(args[0]));

          parser.setDebug(false);
          laToken = parser.yylex();          

          parser.Prog();
     
          if (laToken== Yylex.YYEOF)
             System.out.println("\n\nSucesso!");
          else     
             System.out.println("\n\nFalhou - esperado EOF.");               

        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+args[0]+"\"");
        }
//        catch (java.io.IOException e) {
//          System.out.println("IO error scanning file \""+args[0]+"\"");
//          System.out.println(e);
//        }
//        catch (Exception e) {
//          System.out.println("Unexpected exception:");
//          e.printStackTrace();
//      }
    
  }
  
}

