JAVA=java
JAVAC=javac
JFLEX=$(JAVA) -jar jflex-1.8.2/lib/jflex-full-1.8.2.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)

default: run

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
				$(JAVAC) -cp $(CP) $*.java

FILE=		ExampleScanner.java      parser.java    sym.java \
				ExampleParserTest.java \
				Asn.java Decl.java Expr.java OptionalAsn.java \
				PrintVar.java Program.java ReadVar.java Stmt.java \
				StmtList.java Token.java BinaryOp.java

dump: parserD.java $(FILE:java=class)

all: badDec.txt badInc.txt badNegation.txt badString.txt badTernaryCond.txt badTernaryTypes.txt boolToFloat.txt boolToInt.txt callNonExistFunc.txt charToFloat.txt charToInt.txt floatToInt.txt intArrayToBoolArray.txt noReturn.txt reassignFinal.txt redefMethod.txt redefVar.txt redefVarAsMethod.txt returnTypeBad.txt incompatBinary.txt fullValidProgramFI.txt

run: badDec.txt badInc.txt badNegation.txt badString.txt badTernaryCond.txt badTernaryTypes.txt boolToFloat.txt boolToInt.txt callNonExistFunc.txt charToFloat.txt charToInt.txt floatToInt.txt intArrayToBoolArray.txt noReturn.txt reassignFinal.txt redefMethod.txt redefVar.txt redefVarAsMethod.txt returnTypeBad.txt incompatBinary.txt fullValidProgramFI.txt

badDec.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest badDec.txt > badDec-output.txt
		cat -n badDec-output.txt

badInc.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest badInc.txt > badInc-output.txt
		cat -n badInc-output.txt

badNegation.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest badNegation.txt > badNegation-output.txt
		cat -n badNegation-output.txt

badString.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest badString.txt > badString-output.txt
		cat -n badString-output.txt

badTernaryCond.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest badTernaryCond.txt > badTernaryCond-output.txt
		cat -n badTernaryCond-output.txt

badTernaryTypes.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest badTernaryTypes.txt > badTernaryTypes-output.txt
		cat -n badTernaryTypes-output.txt

boolToFloat.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest boolToFloat.txt > boolToFloat-output.txt
		cat -n boolToFloat-output.txt

boolToInt.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest boolToInt.txt > boolToInt-output.txt
		cat -n boolToInt-output.txt

callNonExistFunc.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest callNonExistFunc.txt > callNonExistFunc-output.txt
		cat -n callNonExistFunc-output.txt

charToFloat.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest charToFloat.txt > charToFloat-output.txt
		cat -n charToFloat-output.txt

charToInt.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest charToInt.txt > charToInt-output.txt
		cat -n charToInt-output.txt

floatToInt.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest floatToInt.txt > floatToInt-output.txt
		cat -n floatToInt-output.txt

intArrayToBoolArray.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest intArrayToBoolArray.txt > intArrayToBoolArray-output.txt
		cat -n intArrayToBoolArray-output.txt

noReturn.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest noReturn.txt > noReturn-output.txt
		cat -n noReturn-output.txt

reassignFinal.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest reassignFinal.txt > reassignFinal-output.txt
		cat -n reassignFinal-output.txt

redefMethod.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest redefMethod.txt > redefMethod-output.txt
		cat -n redefMethod-output.txt

redefVar.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest redefVar.txt > redefVar-output.txt
		cat -n redefVar-output.txt

redefVarAsMethod.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest redefVarAsMethod.txt > redefVarAsMethod-output.txt
		cat -n redefVarAsMethod-output.txt

returnTypeBad.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest returnTypeBad.txt > returnTypeBad-output.txt
		cat -n returnTypeBad-output.txt

incompatBinary.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest incompatBinary.txt > incompatBinary-output.txt
		cat -n incompatBinary-output.txt

fullValidProgramFI.txt: alll
		$(JAVA) -cp $(CP) ExampleParserTest fullValidProgramFI.txt > fullValidProgramFI-output.txt
		cat -n fullValidProgramFI-output.txt

alll: ExampleScanner.java parser.java $(FILE:java=class)

clean:
		rm -f *.class *~ *.bak ExampleScanner.java parser.java sym.java

ExampleScanner.java: exampleGrammar.jflex
		$(JFLEX) exampleGrammar.jflex

parser.java: exampleTokens.cup
		$(CUP) -interface -progress < exampleTokens.cup

parserD.java: exampleTokens.cup
		$(CUP) -interface -dump < exampleTokens.cup
