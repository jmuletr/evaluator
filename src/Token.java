import java.util.ArrayList;
import java.util.List;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    // Pensa a implementar els "getters" d'aquests atributs
    private Toktype ttype;
    private int value;
    private char tk;

    public char getTk() {
        return tk;
    }

    public Toktype getTtype() {
        return ttype;
    }

    public int getValue() {
        return value;
    }

    // Constructor privat. Evita que es puguin construir objectes Token externament
    private Token() {
    }

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        return null;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        return null;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        return null;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        return "";
    }

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        Token token;
        if (o instanceof Token){
            token = (Token) o;
        }else return false;
        if (token.ttype != this.ttype) return false;
        if (token.ttype == Toktype.NUMBER) {
            if (token.value == this.value){
                return true;
            }else return false;
        }
        if (token.ttype == Toktype.OP) {
            if (token.tk == this.tk){
                return true;
            }else return false;
        }
        if (token.ttype == Toktype.PAREN) {
            if (token.tk == this.tk) {
                return true;
            }else return false;
        }
        return false;
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < expr.length(); i++) {
            String numero = "";
            while (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                numero += expr.charAt(i);
                if (i == expr.length() - 1) {
                    break;
                } else {
                    i++;
                }
            }
            if (numero.length() > 0) {
                tokens.add(tokNumber(Integer.parseInt(numero)));
            }
            if (expr.charAt(i) == '+' || expr.charAt(i) == '-' || expr.charAt(i) == '*' || expr.charAt(i) == '/' || expr.charAt(i) == '^' ) {
                tokens.add(tokOp(expr.charAt(i)));
            } else if (expr.charAt(i) == '(' || expr.charAt(i) == ')') {
                tokens.add(tokParen(expr.charAt(i)));
            }
        }
        Token[] token = new Token[tokens.size()];
        tokens.toArray(token);
        return token;
    }
}
