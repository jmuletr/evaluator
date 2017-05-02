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

    //geters de les variables
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
        Token tokenNum = new Token();
        tokenNum.ttype = Toktype.NUMBER;
        tokenNum.value = value;
        return tokenNum;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        Token tokenChar = new Token();
        tokenChar.ttype = Toktype.OP;
        tokenChar.tk = c;
        return tokenChar;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        Token tokenPar = new Token();
        tokenPar.ttype = Toktype.PAREN;
        tokenPar.tk = c;
        return tokenPar;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        return "" + this.ttype + " " + this.value + " " + this.tk;
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
        //array de tokens
        List<Token> tokens = new ArrayList<>();
        boolean negatiu = false;
        //recorrem tots els valors del string
        for (int i = 0; i < expr.length(); i++) {
            //variable per guardar un numero temporalment
            String numero = "";
            //mentres el caracter del string que tractam siguin numeros seguits vol dir que es un nombre de mes de un digit
            while (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                //afegim els nombres a la variable numero, si em recorregut tot el string aturam el bucle si no augmentam i
                numero += expr.charAt(i);
                if (i == expr.length() - 1) {
                    break;
                } else {
                    i++;
                }
            }
            //si el caracter que em tractat es un nombre cream el token corresponent
            if (numero.length() > 0) {
                if (negatiu){
                    tokens.add(tokNumber(Integer.parseInt(numero) * -1));
                    negatiu = false;
                }else tokens.add(tokNumber(Integer.parseInt(numero)));

            }
            if ((expr.charAt(i) == '-'&& i == 0)){
                negatiu = true;
            }
            //si es un operador comprovam que sigui un dels operadors valids i cream el token
            else if (!negatiu && (expr.charAt(i) == '+' || expr.charAt(i) == '-' || expr.charAt(i) == '*' || expr.charAt(i) == '/' || expr.charAt(i) == '^' || expr.charAt(i) == '_')) {
                tokens.add(tokOp(expr.charAt(i)));
                //per acabar tractam els parentesi creant el seu token corresponent
            } else if (expr.charAt(i) == '(' || expr.charAt(i) == ')') {
                tokens.add(tokParen(expr.charAt(i)));
                if (i + 1 != expr.length()){
                    if (expr.charAt(i+1) == '-'){
                        negatiu = true;
                    }
                }
            }
        }
        //una vegada tractat tot el string convertim la llista de tokens que hem generat a array i la retornam
        Token[] token = new Token[tokens.size()];
        tokens.toArray(token);
        return token;
    }
}
