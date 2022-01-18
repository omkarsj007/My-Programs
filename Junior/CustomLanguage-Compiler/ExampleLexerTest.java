import java.io.*;
import java_cup.runtime.*;

public class ExampleParserTest{

  public static void main(String[] args) throws Exception
  {
    Reader reader = null;
    
    if (args.length == 1) {
      File input = new File(args[0]);
      if (!input.canRead()) {
        System.out.println("Error: could not read ["+input+"]");
      }
      reader = new FileReader(input);
    }
    else {  
      reader = new InputStreamReader(System.in);
    }

    ExampleScanner scanner = new ExampleScanner(reader);   // create scanner

    parser parser = new parser(scanner); // create parser
    Program program = null;

    try { 
      program = (Program) parser.parse().value;  // parse
    }    
    catch (Exception e) { 
      e.printStackTrace(); 
    }

    System.out.print(program.toString(0));

  }
}
