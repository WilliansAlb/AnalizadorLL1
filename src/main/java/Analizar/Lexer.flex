package Analizador;
import java_cup.runtime.Symbol;
%%
%class Lexer
%type java_cup.runtime.Symbol
%cup
%full
%line
%column
%char
%public
L=[a-zA-Z]+
D=[0-9]+
C=[@_"-"%#&]+
espacio=[ |\t|\r|\n]+
esp = [ ]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%
("+") {return new Symbol(1,yyline,yycolumn,yytext());}
("(") {return new Symbol(2,yyline,yycolumn,yytext());}
(")") {return new Symbol(3,yyline,yycolumn,yytext());}
("*") {return new Symbol(4,yyline,yycolumn,yytext());}
{D}+ {return new Symbol(6,yyline,yycolumn,yytext());}
({L}({L}{D})*) {return new Symbol(5,yyline,yycolumn,yytext());}

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

