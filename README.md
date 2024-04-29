0. baixar e adicionar o JFlex.jar

1. gerar o léxico  (gera Yylex.java):

> java -jar JFlex.jar lexico.flex

2. chmod +x byaccj.macOs

3. sudo cp byaccj.macOs /usr/local/bin

4. gerar o sintático  (gera Parser.java):

> byaccj.macos -tv -J exerc.y

3. para compilar código java:

> javac Parser.java

4. para testar:

> java Parser prog1.txt