// MiniLexer.java
// Tokenizador básico sin requerir espacios entre tokens.
// Hecho con estructuras simples para principiantes.

public class MiniLexer {

    // Clasifica un lexema en una de las 5 categorías.
    public static TipoToken clasificarToken(String lexema) {
        if (lexema.equals("print")) return TipoToken.PALABRA_CLAVE;

        if (lexema.equals("==") || lexema.equals("=") ||
            lexema.equals("+") || lexema.equals("-") ||
            lexema.equals("*") || lexema.equals("/")) {
            return TipoToken.OPERADOR;
        }

        if (lexema.equals(";") || lexema.equals("(") || lexema.equals(")")) {
            return TipoToken.DELIMITADOR;
        }

        if (lexema.matches("[0-9]+")) return TipoToken.LITERAL_NUMERICO;

        // si no coincide con nada anterior → identificador
        return TipoToken.IDENTIFICADOR;
    }

    // Tokeniza la entrada completa (acepta varias líneas concatenadas).
    public static Token[] tokenizar(String input) {
        if (input == null) input = "";
        input = input.trim();
        if (input.length() == 0) return new Token[0];

        // array temporal (principiante): suficiente para ejercicios de práctica
        String[] lexTemp = new String[1000];
        int c = 0;

        int i = 0;
        while (i < input.length()) {
            char ch = input.charAt(i);

            // ignora espacios y separadores de línea
            if (Character.isWhitespace(ch)) { i++; continue; }

            // identificador o palabra clave: letra o '_', seguido de letras/dígitos/'_'
            if (Character.isLetter(ch) || ch == '_') {
                String s = "" + ch;
                i++;
                while (i < input.length()) {
                    char d = input.charAt(i);
                    if (Character.isLetterOrDigit(d) || d == '_') {
                        s = s + d;
                        i++;
                    } else break;
                }
                lexTemp[c++] = s;
                continue;
            }

            // número (se aceptan solo enteros positivos según práctica)
            if (Character.isDigit(ch)) {
                String s = "" + ch;
                i++;
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    s = s + input.charAt(i);
                    i++;
                }
                lexTemp[c++] = s;
                continue;
            }

            // operador '==' o '='
            if (ch == '=') {
                if (i + 1 < input.length() && input.charAt(i + 1) == '=') {
                    lexTemp[c++] = "==";
                    i += 2;
                } else {
                    lexTemp[c++] = "=";
                    i += 1;
                }
                continue;
            }

            // operadores simples
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                lexTemp[c++] = "" + ch;
                i++;
                continue;
            }

            // delimitadores
            if (ch == ';' || ch == '(' || ch == ')') {
                lexTemp[c++] = "" + ch;
                i++;
                continue;
            }

            // carácter desconocido: lo guardamos para que el parser detecte error
            lexTemp[c++] = "" + ch;
            i++;
        }

        // construir Token[] del tamaño correcto
        Token[] tokens = new Token[c];
        for (int k = 0; k < c; k++) {
            String lex = lexTemp[k];
            TipoToken tipo = clasificarToken(lex);
            tokens[k] = new Token(tipo, lex);
        }
        return tokens;
    }
}
