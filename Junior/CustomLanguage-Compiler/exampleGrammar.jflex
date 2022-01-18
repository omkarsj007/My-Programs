/*-***
 * This grammar is defined for the example grammar defined in the
 *project part 1 instructions
 */

/*
 * NOTE: make sure that the java cup runtime file is in the same directory as this file
 * it is also alright if the runtime location is added to to the classpath, but just
 * putting in the same file is far easier.
 */
import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%line
%column
%unicode
%class ExampleScanner

%{

Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}

Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}

%}


/*-*
 * PATTERN DEFINITIONS:
 */

tab           = \\t
newline		    = \\n
slash			    = \\
letter        = [A-Za-z]
digit         = [0-9]
id   			    = {letter}({letter}|{digit})*  
intlit	      = {digit}+
inlinecomment = {slash}{slash}.*\n
blockcomments   = {slash}\*
blockcommente   = \*{slash}
commentbody	  = ([^\*]|(\*+[^\\]))
blockcomment    = {blockcomments}{commentbody}*?{blockcommente}
whitespace    = [ \n\t\r]
escapequote     = {slash}\"
escapeapos	= {slash}\'
stringchar      = [[[^\\]&&[^\"]]&&[[^\n]&&[^\t]]]|{newline}|{tab}|{escapequote}|{slash}{slash}
stringlit       = \"{stringchar}*\"
charchar	= [[^\\]&&[^']]|{newline}|{tab}|{escapeapos}|{slash}{slash}
charlit		= '{charchar}'
floatlit	= {intlit}+\.{intlit}+


%%
/**
 * LEXICAL RULES:
 */

class              { return newSym(sym.CLASS, "class"); }
else               { return newSym(sym.ELSE, "else"); }
if                 { return newSym(sym.IF, "if"); }
fi                 { return newSym(sym.FI, "fi"); }
read               { return newSym(sym.READ, "read"); }
final		   { return newSym(sym.FINAL, "final"); }
print		           { return newSym(sym.PRINT, "print"); }
"*"                { return newSym(sym.TIMES, "*"); }
"+"                { return newSym(sym.PLUS, "+"); }
"-"                { return newSym(sym.MINUS, "-"); }
"/"                { return newSym(sym.DIVIDE, "/"); }
"="                { return newSym(sym.EQ, "="); }
";"                { return newSym(sym.SEMI, ";"); }


"||"               { return newSym(sym.OROR, "||");}
"&&"               { return newSym(sym.ANDAND, "&&");}
"("                { return newSym(sym.OPARENTH, "(");}
")"                { return newSym(sym.CPARENTH, ")");}
"["                { return newSym(sym.OBRACKET, "[");}
"]"                { return newSym(sym.CBRACKET, "]");}
"{"                { return newSym(sym.OBRACE, "{");}
"}"                { return newSym(sym.CBRACE, "}");}
"++"               { return newSym(sym.INCREMENT, "++");}
"--"		   { return newSym(sym.DECREMENT, "--");}
"=="		   { return newSym(sym.EQEQ, "==");}
"<"                { return newSym(sym.LESSTHAN, "<");}
">"                { return newSym(sym.GREATERTHAN, ">");}
"<="               { return newSym(sym.LEQUALTO, "<=");}
">="               { return newSym(sym.GEQUALTO, ">=");}
"<>"               { return newSym(sym.NOTEQ, "<>");}
"~"                { return newSym(sym.NOT, "~");}
"?"                { return newSym(sym.QUESTION, "?");}
":"                { return newSym(sym.COLON, ":");}
","                { return newSym(sym.COMMA, ",");}

while                      { return newSym(sym.WHILE, "while"); }
printline                    { return newSym(sym.PRINTLN, "printline"); }
return                     { return newSym(sym.RETURN, "return"); }
void                       { return newSym(sym.VOID, "void"); }
int                        { return newSym(sym.INT, "int"); }
float                      { return newSym(sym.FLOAT, "float"); }
bool                       { return newSym(sym.BOOL, "bool"); }
char                       { return newSym(sym.CHAR, "char"); }
true                       { return newSym(sym.TRU, "true"); }
false                      { return newSym(sym.FLS, "false"); }

{id}               { return newSym(sym.ID, yytext()); }
{intlit}           { return newSym(sym.INTLIT, new Integer(yytext())); }
{floatlit}         { return newSym(sym.FLOATLIT, new Float(yytext())); }
{charlit}      	   { return newSym(sym.CHARLIT, yytext()); }
{inlinecomment}    { /* For this stand-alone lexer, print out comments. */}
{blockcomment}	   { /* For this stand-alone lexer, print out comments. */}
{whitespace}       { /* Ignore whitespace. */ }
{stringlit}        { return newSym(sym.STR, new String(yytext())); }
.                  { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yychar); } 