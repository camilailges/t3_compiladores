0. baixar e adicionar o JFlex.jar

1. gerar o léxico  (gera Yylex.java):

> java -jar JFlex.jar asdr_lex.flex

2. chmod +x byaccj.macOs

3. sudo cp byaccj.macOs /usr/local/bin

4. gerar o sintático  (gera Parser.java):

> byaccj -tv -J asdr.y

3. para compilar código java:

> javac Parser.java

4. para testar:

> java Parser <teste.txt>