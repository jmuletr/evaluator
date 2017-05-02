import java.util.LinkedList;
import java.util.List;

public class Evaluator {


    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        //cream una cua i un stack
        List<Token> cua = new LinkedList<>();
        LinkedList<Token> operadors = new LinkedList<>();

        //recorrem el array de tokens
        for (int i = 0; i < tokens.length; i++) {
            //si el token es un nombre el pasam directament a la cua
            if (tokens[i].getTtype() == Token.Toktype.NUMBER) {
                cua.add(tokens[i]);
                //tractam els operadors que no son parentesi
            } else if (tokens[i].getTtype() == Token.Toktype.OP) {
                //comprovam que la llista d'operadors no estigui vuida
                if (!operadors.isEmpty()) {
                    //si l'operador te la mateixa prioritat que els que hi ha a la cua s'afegeix d'avant
                    if (CompareTo(tokens[i].getTk(), operadors.peek().getTk())) {
                        operadors.push(tokens[i]);
                        //si l'operador te mes prioritat es pasen els operadors de la cua d'operadors a la llista cua
                    } else {
                        cua.add(operadors.pop());
                        //si la cua d'operadors no es vuida es pasen els operadors que tenen menys prioritat a la llista cua
                        if (!operadors.isEmpty() && !CompareTo(tokens[i].getTk(), operadors.peek().getTk())) {
                            cua.add(operadors.pop());
                        }
                        //una vegada que la cua d'operadors es vuida o nomes conte operadors amb mes prioritat afegim l'operador actual
                        operadors.push(tokens[i]);
                    }
                    //si la llista d'operadors es vuida directament afegim l'operador a la cua
                } else {
                    operadors.push(tokens[i]);
                }
                //tractam els parentesi que son l'element mes prioritari
            } else if (tokens[i].getTk() == '(') {
                //afegeix davant de la llista d'operadors
                operadors.push(tokens[i]);
            } else if (tokens[i].getTk() == ')') {
                while (operadors.peek().getTk() != '(') {
                    //afegeix darrera la llista tots els operadors fins arrivar al parentesi
                    cua.add(operadors.pop());
                }
                //treim el parentesi de la cua d'operadors
                operadors.pop();
            }
        }
        //una vegada tractats tots els tokens pasam tots els operadors restants de la cua a la llista cua
        while (!operadors.isEmpty()) {
            cua.add(operadors.pop());
        }
        //cream un nou array de token i transformam la llista cua a array
        Token[] operacio = new Token[cua.size()];
        cua.toArray(operacio);
        //retornam el resultat de la funcio calcRPN
        return calcRPN(operacio);
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        LinkedList<Token> llista = new LinkedList<>();
        //recorrem la llista de tokens list afegint els nombres a llista fins trobar un operador
        for (int i = 0; i < list.length; i++) {
            while (list[i].getTtype() == Token.Toktype.NUMBER) {
                llista.push(list[i]);
                if (i == list.length - 1) {
                    break;
                } else {
                    i++;
                }
            }
            //asociam els nombres a 2 variables per facilitar les operacions
            Token n1, n2;
            if (list[i].getTk() != '!'){
                n2 = llista.pop();
                n1 = llista.pop();
            } else {
                n1 = llista.pop();
                n2 = null;
            }
            //comprovam l'operador i enviam els dos nombres anteriors a la funcio corresponent i afegim el resutat a la llista
            if (list[i].getTk() == '+') {
                llista.push(suma(n1, n2));
            } else if (list[i].getTk() == '-') {
                llista.push(resta(n1, n2));
            } else if (list[i].getTk() == '*') {
                llista.push(multiplicacio(n1, n2));
            } else if (list[i].getTk() == '/') {
                llista.push(divisio(n1, n2));
            } else if (list[i].getTk() == '^') {
                llista.push(potenci(n1, n2));
            } else if (list[i].getTk() == '_') {
                llista.push(arrel(n1, n2));
            } else if (list[i].getTk() == '!') {
                llista.push(factorial(n1));
            }
        }
        //retornam el resultat de l'operacio
        return llista.poll().getValue();

    }

    private static int prioritat(char op) {
        //retorna un valor que especifica la prioritat del operand
        int prioritat = 0;
        switch (op) {
            case '!':
                prioritat = 4;
                break;
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
        //compara operands depenguent de la seva prioritat
        int oper1 = prioritat(op1);
        int oper2 = prioritat(op2);
        if (oper1 > oper2)return true;
        return false;
    }

    //funcions per fer les operacions corresponents al operand
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

    private static Token factorial(Token a) {
        int r = a.getValue();
        Token resultat = Token.tokNumber(calcFactorial(r));
        return resultat;
    }

    private static int calcFactorial(int numero) {
        if (numero == 1) {
            return numero;
        }
        return numero * calcFactorial(numero - 1);
    }


}
