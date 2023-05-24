import java.util.EmptyStackException;
import java.util.Stack;
/**
 * A class for parsing arithmetic expressions
 */
public class Parser {

  /**
   * An exception that is thrown if the to-be-parsed expression is not
   * well-formed.
   */
	public static class ExpressionNotWellFormedException extends Exception {
		public ExpressionNotWellFormedException() {
		}
	}

  /**
   * Parses a given String, determines whether it is a well-formed expression,
   * and computes the expression.
   * 
   * @param expression
   *          the expression that is to be evaluated
   * @return the result of the evaluation / computation
   * @throws ExpressionNotWellFormedException
   *           if the expression is not well-formed, an exception is thrown
   */
	public static int parse(String expression) throws ExpressionNotWellFormedException {
		
		// !!!
		
		// AB HIER MEIN EIGENER CODE; DER REST WAR VORGEGEBEN
		
		// !!!
	  
		ExpressionNotWellFormedException ex = new ExpressionNotWellFormedException();
	  
		if (!isValid(expression)) { throw ex; } // ist es eine richtige expression?
		
		int erg = 0;
		String newString = expression;
		boolean aktiv = true;
		
		// idee: man muss bei klammern von innen nach außen rechnen. also gehen wir bis zur innersten klammer vor,
		// diese ist genau dort wo das erste mal ')' vorkommt. von dort aus nehmen wir den substring der klammer,
		// rechnen ihn mit eval() aus und ersetzen den substring durch das ergebnis. anschließend wieder von vorn,
		// man sucht die innerste klammer, rechnet den substring der klammer aus etc.
		
		while (true) {
			int i2 = 0, i1 = 0; // i1 = index der '(' von der innersten klammer, i2 = index der ')' von der innersten klammer
			while (true) {
				if (newString.charAt(i2) == '(') {
					i1 = i2;
				} else if (newString.charAt(i2) == ')') {
					i2 += 1;
					break;
				} else if (i2+1 == newString.length()) { // wenn das ergebnis nur noch lediglich dasteht, zB "30", ohne klammern und ohne weitere operationen
					aktiv = false;
					break;
				}
				
				i2++;
			}
			if (!aktiv) break;
			
			erg = eval(newString.substring(i1,i2));
			newString = newString.replace(newString.substring(i1,i2), Integer.toString(erg));
		}
		return Integer.parseInt(newString);
	}
	
	private static boolean isValid(String expression) {
		
		Stack<String> s = new Stack<String>(); // wir dürfen nur Stacks als Hilfsmittel verwenden
		s.push("N");
	  
		for (int i = 0; i < expression.length(); i++) {
			
			String c = String.valueOf(expression.charAt(i));
			String p = "";
			try { p = s.peek().toString(); } catch (EmptyStackException e) { return false; }
			
			if (p.equals("N")) { // neue Zahl oder neuer Ausdruck mit ner Klammer auf
				if (c.equals("(")) { // Klammer auf kommt
					s.pop();
					s.push(")"); // klammer muss später geschlossen werden
					s.push("N"); // hier muss nen regulärer Ausdruck hin
					s.push("Z"); // hier ein Rechenzeichen
					s.push("N"); // hier wieder ein regulärer ausdruck
					
				} else if (isNumber(c)) { // Zahl kommt
					s.pop();
				} else { return false; } // was anderes -> nicht richtig
				
			} else if (p.equals("Z")) { // Zeichen kommt (+, -, *, /)
				if ((c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/"))) { // ist es eins der rechenzeichen?
					s.pop();
				} else { return false; } // was anderes -> nicht richtig
				
			} else if (p.equals(")")) { // klammer zu kommt
				if (c.equals(")")) { // klammer zu muss im stack sein
					s.pop();
				} else { return false; } // sonst nicht richtig
			}
			
		}
		
		// stack muss zum schluss empty sein
		return s.isEmpty();
	}
	
	private static int eval(String s) { // ausrechenen des übergebenen Strings der form a+b oder (a+b) oder jeder anderen operation (+ - * /)
		
		if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')' ) { // klammern wegfallen lassen, also zB (a+b) wird zu a+b
			s = s.substring(1, s.length()-1);
		}
		int erg = 0;
		int mult1 = 1; int mult2 = 1;
		String s1 = ""; String s2 = "";
		String op = "";
		int n1 = 0; int n2 = 0;
		boolean zahlwechsel = false;
		
		for (int i = 0; i < s.length(); i++) {
			String c = String.valueOf(s.charAt(i));
			
			if (i == 0 && c.equals("-")) { // ist die erste zahl negativ? also zB "-1+1"
				mult1 = -1;
				continue;
			} else if (zahlwechsel && c.equals("-")) { // ist die zweite zahl negativ? also zB "1+-1"
				mult2 = -1;
				continue;
			}
			
			if ((c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/"))) { // wenn das rechenzeichen in der mitte gelesen wird
				op = c; // operator merken
				zahlwechsel = true; // wir fangen nun mit der zweiten zahl an
				continue;
			}
			
			
			if (!zahlwechsel) s1 += c; // zeichen für zeichen konvertieren vom gesamten string zur ersten zahl
			else s2 += c; // und zur zweiten zahl
			
			
		}
		n1 = Integer.parseInt(s1) * mult1; // konvertieren der zahlstrings zu int
		n2 = Integer.parseInt(s2) * mult2;
		
		if        (op.equals("+")) { // offensichtlich
			erg = n1 + n2;
		} else if (op.equals("-")) {
			erg = n1 - n2;
		} else if (op.equals("*")) {
			erg = n1 * n2;
		} else if (op.equals("/")) {
			erg = n1 / n2;
		}
		
		return erg;
	}
	
	private static boolean isNumber(String c) { // ist String c als int eine Ziffer? 0-9
		int num;
		try { num = Integer.parseInt(c); } catch (NumberFormatException n) { return false; }
		return ((0 <= num) && (num <= 9));
	}
	
	// !!!
	
	// AB HIER WIEDER VORGEGEBENER CODE
	
	// !!!

  /**
   * test cases
   */
  public static void main(String[] args) {
    {
      wellFormedCheck("((8+7)*2)", 30);
      wellFormedCheck("(4-(7-1))", -2);
      wellFormedCheck("8", 8);
      wellFormedCheck("((1+1)*(2*2))", 8);

      notWellFormedCheck(")8+)1(())");
      notWellFormedCheck("(8+())");
      notWellFormedCheck("-1");
      notWellFormedCheck("(   5    -7)");
      notWellFormedCheck("108");
      notWellFormedCheck("(8)");
    }
  }

  private static void checkAndPrint(String message, boolean correct) {
    System.out.println((correct ? "PASS:" : "FAIL:") + " " + message);
    assert (correct);
  }

  private static void notWellFormedCheck(String expression) {
    try {
      int returned = parse(expression);
      checkAndPrint("nicht wohlgeformter Ausdruck " + expression
          + " ausgewertet zu " + returned, false);
    } catch (ExpressionNotWellFormedException e) {
      checkAndPrint("Ausdruck " + expression
          + " als nicht wohlgeformt erkannt.", true);
    }
  }

  private static void wellFormedCheck(String expression, int expected) {
    try {
      int returned = parse(expression);
      checkAndPrint("Ausdruck " + expression + " ausgewertet zu " + returned
          + " (erwartet: " + expected + ")", returned == expected);
    } catch (ExpressionNotWellFormedException e) {
      checkAndPrint("Ausdruck " + expression
          + " fälschlicherweise als nicht wohlgeformt eingeschätzt.", false);
    }
  }
}