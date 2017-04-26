import java.util.LinkedList;
import java.util.List;

public class Evaluator {


    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        List<Token> cua = new LinkedList<>();
        LinkedList<Token> operadors = new LinkedList<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getTtype() == Token.Toktype.NUMBER) {
                cua.add(tokens[i]);
            } else if (tokens[i].getTtype() == Token.Toktype.OP) {
                if (!operadors.isEmpty()) {
                    if (CompareTo(tokens[i].getTk(), operadors.peek().getTk())) {
                        operadors.push(tokens[i]);
                    } else {
                        cua.add(operadors.pop());
                        if (!operadors.isEmpty() && !CompareTo(tokens[i].getTk(), operadors.peek().getTk())) {
                            cua.add(operadors.pop());
                        }
                        operadors.push(tokens[i]);
                    }
                } else {
                    operadors.push(tokens[i]);
                }
            } else if (tokens[i].getTk() == '(') {
                operadors.push(tokens[i]);
            } else if (tokens[i].getTk() == ')') {
                while (operadors.peek().getTk() != '(') {
                    cua.add(operadors.pop());
                }
                operadors.pop();
            }
        }
        while (!operadors.isEmpty()) {
            cua.add(operadors.pop());
        }
        Token[] operacio = new Token[cua.size()];
        cua.toArray(operacio);
        return calcRPN(operacio);
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        LinkedList<Token> lista = new LinkedList<>();
        for (int i = 0; i < list.length; i++) {
            while (list[i].getTtype() == Token.Toktype.NUMBER) {
                lista.push(list[i]);
                if (i == list.length - 1) {
                    break;
                } else {
                    i++;
                }
            }
            Token n2 = lista.pop();
            Token n1 = lista.pop();

            if (list[i].getTk() == '+') {
                lista.push(suma(n1, n2));
            } else if (list[i].getTk() == '-') {
                lista.push(resta(n1, n2));
            } else if (list[i].getTk() == '*') {
                lista.push(multiplicacio(n1, n2));
            } else if (list[i].getTk() == '/') {
                lista.push(divisio(n1, n2));
            } else if (list[i].getTk() == '^') {
                lista.push(potenci(n1, n2));
            } else if (list[i].getTk() == '_') {
                lista.push(arrel(n1, n2));
            }
        }
        return lista.poll().getValue();

    }

    private static int prioritat(char op) {
        int prioritat = 0;
        switch (op) {
            case '^':
                prioritat = 3;
                break;
            case '_':
                prioritat = 3;
                break;
            case '*':
                prioritat = 2;
                break;
            case '/':
                prioritat = 2;
                break;
            case '+':
                prioritat = 1;
                break;
            case '-':
                prioritat = 1;
                break;
        }
        return prioritat;
    }

    private static boolean CompareTo(char op1, char op2) {
        int oper1 = prioritat(op1);
        int oper2 = prioritat(op2);
        if (oper1 > oper2)return true;
        return false;
    }

    public static Token suma(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r + r2);
        return resultat;
    }

    public static Token resta(Token a, Token b) {

        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r - r2);
        return resultat;
    }

    public static Token multiplicacio(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r * r2);
        return resultat;
    }

    private static Token divisio(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r / r2);
        return resultat;
    }

    private static Token potenci(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber((int)Math.pow(r,r2));
        return resultat;
    }

    private static Token arrel(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber((int)Math.pow(r, 1.0/r2));
        return resultat;
    }


}
