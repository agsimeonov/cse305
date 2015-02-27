import java.util.Hashtable;

/* Grammar:

	program		->	cmpdstmt '.'
	cmpdstmt	->	'{' stmts '}'
	stmts		->	stmt
				 |	stmt stmts
	stmt		->	assign
				 |	ifthenelse
	assign		->	var '=' expr ';'
	ifthenelse	->	if '(' bexpr ')' cmpdstmt [ cmpdstmt ] ';'
	bexpr		->	expr '=' expr
				 |	expr '<' expr
				 |	expr '>' expr
	expr		->	term
				 |	term ('+' | '-') expr
	term		->	factor
				 |	factor ('*' | '/') term
	factor		->	int
				 |	id
				 |	'(' expr ')'

*/

class SyntaxError extends Exception {
	public String message;
	public SyntaxError(String s) {
		super(s);
		message = s;
	}
}

public class PLParser {	
	private Program p;
	public void parse() {
		p = new Program();
		p.program();
		System.out.println("done");
		System.exit(0);
	}
}

class SymbolTable {
	static public Hashtable<Character, Integer> smbltbl = new Hashtable<Character, Integer>();
}

class Program extends SymbolTable {
	private Cmpdstmt c;
	public void program() {
		try {		
			c = new Cmpdstmt(true);
			c.cmpdstmt();
			Lexer.lex(); //expects '.'
			if(Lexer.nextToken != Token.PERIOD){
				throw new SyntaxError("'.' expected at the end of the program");
			}
		}
		catch(SyntaxError e) {
			e.printStackTrace();
			System.out.println("done");
			System.exit(1);
		}
	}
}

class Cmpdstmt extends SymbolTable {
	private Stmts s;
	private boolean flag;
	
	public Cmpdstmt(boolean bool) {
		flag = bool;
	}
	
	public void cmpdstmt() throws SyntaxError {
		if(Lexer.nextToken != Token.LEFT_BRACE) {
			Lexer.lex(); //expects '{'
		}
		if((Lexer.nextToken != Token.LEFT_BRACE)) {
			throw new SyntaxError("'{' expected before a cmpdstmt");
		}
		Lexer.lex(); //expects var or if
		s = new Stmts(flag);
		s.stmts();

	}
}

class Stmts extends SymbolTable {
	private Stmt s;
	private Stmts ss;
	private boolean flag;
	
	public Stmts(boolean bool) {
		flag = bool;
	}
	
	public void stmts() throws SyntaxError {
		s = new Stmt(flag);
		s.stmt();
		Lexer.lex(); //expects '}' or var or if
		while(Lexer.nextToken != Token.RIGHT_BRACE) {
			ss = new Stmts(flag);
			ss.stmts();
		}
	}
}

class Stmt extends SymbolTable {
	private Assign a;
	private Ifthenelse i;
	private boolean flag;
	
	public Stmt(boolean bool) {
		flag = bool;
	}
	
	public void stmt() throws SyntaxError {
		switch(Lexer.nextToken) {
		case Token.ID:
			a = new Assign(flag);
			a.assign();
			break;
		case Token.KEY_IF:
			i = new Ifthenelse(flag);
			i.ifthenelse();
			break;
		default:
			throw new SyntaxError("var or if expected at the begining of each stmt");
		}
	}
}

class Assign extends SymbolTable {
	private Expr e;
	private Character var;
	private boolean flag;
	
	public Assign(boolean bool) {
		flag = bool;
	}
	
	public void assign() throws SyntaxError {
		var = Lexer.ident;
		Lexer.lex(); //expects '='
		if(Lexer.nextToken != Token.ASSIGN_OP) {
			throw new SyntaxError("'=' expected after var");
		}
		if(flag == true) {
			e = new Expr(var);
			System.out.println(var + " = " + e.expr());
		}
		else {
			e = new Expr('!');
			e.expr();
			smbltbl.remove('!');
		}
		if(Lexer.nextToken != Token.SEMICOLON) {
			throw new SyntaxError("';' expected at the end of each stmt");
		}
	}
}

class Ifthenelse extends SymbolTable {
	private Bexpr b;
	private Cmpdstmt c;
	private boolean optionAvailable = true;
	private boolean flag;
	private boolean flag2;
	
	public Ifthenelse(boolean bool) {
		flag = bool;
	}
	
	public void ifthenelse() throws SyntaxError {
		Lexer.lex(); //expects '('
		if(Lexer.nextToken != Token.LEFT_PAREN) {
			throw new SyntaxError("'(' excpected after an if");
		}
		if(flag == true) {
			b = new Bexpr();
			flag2 = b.bexpr();
		}
		else {
			b = new Bexpr();
			b.bexpr();
			flag2 = false;
		}
		if(Lexer.nextToken != Token.RIGHT_PAREN) {
			Lexer.lex(); //expects ')'
		}
		if(Lexer.nextToken != Token.RIGHT_PAREN) {
			throw new SyntaxError("')' expecter after a bexpr");
		}
		if((flag == true) && (flag2 == true)) {
			c = new Cmpdstmt(true);
		}
		else if((flag == true) && (flag2 == false)) {
			c = new Cmpdstmt(false);
		}
		else {
			c = new Cmpdstmt(false);
		}
		c.cmpdstmt();
		Lexer.lex(); //expects a '{' or ';'
		switch(Lexer.nextToken) {
		case Token.LEFT_BRACE:
			if(optionAvailable == true) {
				optionAvailable = false;
				if((flag == true) && (flag2 == true)) {
					c = new Cmpdstmt(false);
				}
				else if((flag == true) && (flag2 == false)) {
					c = new Cmpdstmt(true);
				}
				else {
					c = new Cmpdstmt(false);
				}
				c.cmpdstmt();
				Lexer.lex(); // expects a ';'
				if(Lexer.nextToken != Token.SEMICOLON) {
					throw new SyntaxError("';' expected after optional cmpdstmt in an ifthenelse");
				}
			}	
			break;
		case Token.SEMICOLON:
			optionAvailable = false;
			break;
		default:
			throw new SyntaxError("'{' or ';' expected after cmpdstmt in an ifthenelse");
		}
	}
}

class Bexpr extends SymbolTable {
	private Expr e;
	public boolean bexpr() throws SyntaxError {
		e = new Expr('#');
		int i = e.expr();
		switch(Lexer.nextToken) {
		case Token.ASSIGN_OP:
			e = new Expr('#');
			if(i == e.expr()) {
				smbltbl.put('?', 1); //where 1 is true
			}
			else {
				smbltbl.put('?', 0); //where 0 is false
			}
			break;
		case Token.LT_OP:
			e = new Expr('#');
			if(i < e.expr()) {
				smbltbl.put('?', 1); //where 1 is true
			}
			else {
				smbltbl.put('?', 0); //where 0 is false
			}
			break;
		case Token.GT_OP:
			e = new Expr('#');
			if(i > e.expr()) {
				smbltbl.put('?', 1); //where 1 is true
			}
			else {
				smbltbl.put('?', 0); //where 0 is false
			}
			break;
		default:
			throw new SyntaxError("'=', '<', or '>' expected in the middle of a bexpr");
		}
		if(smbltbl.get('?')==1) {
			smbltbl.remove('#');
			smbltbl.remove('?');
			return true;
		}
		else {
			smbltbl.remove('#');
			smbltbl.remove('?');
			return false;
		}
	}
}

class Expr extends SymbolTable {
	private Term t;
	private Expr e;
	private Character var;
	
	public Expr(Character v) {
		var = v;
	}
	
	public int expr() throws SyntaxError {
		t = new Term(var);
		int i = t.term();
		switch(Lexer.nextToken) {
		case Token.ADD_OP:
			e = new Expr(var);
			smbltbl.put(var, i+e.expr());
			break;
		case Token.SUB_OP:
			e = new Expr(var);
			smbltbl.put(var, i-e.expr());
			break;
		default:
			break;
		}
		return smbltbl.get(var);
	}
}

class Term extends SymbolTable {
	private Factor f;
	private Term t;
	private Character var;
	
	public Term(Character v) {
		var = v;
	}
	
	public int term() throws SyntaxError {
		f = new Factor(var);
		int i = f.factor();
		Lexer.lex(); //expects '+' or '-' or '*' or '/' or '=' or '<' or '>' or ';'
		switch(Lexer.nextToken) {
		case Token.MULT_OP:
			t = new Term(var);
			smbltbl.put(var, i*t.term());
			break;
		case Token.DIV_OP:
			t = new Term(var);
			smbltbl.put(var, i/t.term());
			break;
		default:
			break;
		}
		return smbltbl.get(var);
	}
}

class Factor extends SymbolTable {
	private Expr e;
	private Character var = null;
	
	public Factor(Character v) {
		var = v;
	}
	
	public int factor() throws SyntaxError {
		Lexer.lex(); //expects int or id or '(' or ')'
		switch(Lexer.nextToken){
		case Token.INT_LIT:
			if(var != null) {
				smbltbl.put(var, Lexer.intValue);
			}
			break;
		case Token.ID:
			if(smbltbl.containsKey(Lexer.ident)) {
				smbltbl.put(var, smbltbl.get(Lexer.ident));
			}
			else {
				throw new SyntaxError("unbound variable " + Lexer.ident);
			}
			break;
		case Token.LEFT_PAREN:
			e = new Expr(var);
			e.expr();
			if(Lexer.nextToken != Token.RIGHT_PAREN) {
				throw new SyntaxError("')' expected after an expr within a factor");
			}
			break;
		default:
			throw new SyntaxError("int, id, or '(' expected in the beginning of each factor");
		}
		return smbltbl.get(var);
	}
}