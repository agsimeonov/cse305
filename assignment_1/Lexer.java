import java.io.*;

public class Lexer {

  static public int nextToken;
  
  static private char ch = ' ';
  static public char ident = ' ';
  static public int intValue = 0;
  static private Buffer buffer = new Buffer(new DataInputStream(System.in));
 
  public static int lex() {

    while (Character.isWhitespace(ch))
      ch = buffer.getChar();
    if (Character.isLetter(ch)) {
      ident = Character.toLowerCase(ch);
      ch = buffer.getChar();
      if (ident == 'i' && ch == 'f') {
        ch = buffer.getChar();
        nextToken = Token.KEY_IF;
      }
      else { 
        nextToken = Token.ID;
      }
    }
    else if (Character.isDigit(ch)) {
      intValue = getNumber();
      nextToken = Token.INT_LIT;
    }
    else {
      switch (ch) {
        case ';':
          nextToken = Token.SEMICOLON;
          break;
        case ',':
          nextToken = Token.COMMA;
          break;
        case '.':
          nextToken = Token.PERIOD;
          break;
        case '+':
          nextToken = Token.ADD_OP;
          break;
        case '-':
          nextToken = Token.SUB_OP;
          break;
        case '*':
          nextToken = Token.MULT_OP;
          break;
        case '/':
          nextToken = Token.DIV_OP;
          break;
        case '=':
          nextToken = Token.ASSIGN_OP;
          break;
        case '<':
          nextToken = Token.LT_OP;
          break;
        case '>':
          nextToken = Token.GT_OP;
         break;
        case '(':
          nextToken = Token.LEFT_PAREN;
          break;
        case ')':
          nextToken = Token.RIGHT_PAREN;
          break;
        case '{':
          nextToken = Token.LEFT_BRACE;
          break;
        case '}':
          nextToken = Token.RIGHT_BRACE;
          break;
        default:
          error("Illegal character " + ch);
          break;
      }
      ch = buffer.getChar();
    }
    return nextToken;
  } // lex

  public void match(int x) {
    nextToken = lex();
    if (nextToken != x) {
      error("Invalid token " + Token.toString(nextToken) + "-- expecting " + Token.toString(x));
      System.exit(1);
    } // if
  } // match

  public static void error(String msg) {
    System.err.println(msg);
    System.exit(1);
  } // error

  private static int getNumber() {
    int num = 0;
    do {
      num = num * 10 + Character.digit(ch, 10);
      ch = buffer.getChar();
    }
    while (Character.isDigit(ch));
    return num;
  }

} // Lexer

class Buffer {
  private String line = "";
  private int position = 0;
  private int lineNo = 0;
  private DataInputStream inStream;

  public Buffer(DataInputStream i) {
    this.inStream = i;
  } // Buffer

  public char getChar() {
    position++;
    if (position >= line.length()) {
      try {
        line = inStream.readLine();
      }
      catch (Exception e) {
        System.err.println("Invalid read operation");
        System.exit(1);
      }
      if (line == null)
        System.exit(0);
      position = 0;
      lineNo++;
      // System.out.println(line);
      line = line + "\n";
    }
    return line.charAt(position);
  }

} // class Buffer

