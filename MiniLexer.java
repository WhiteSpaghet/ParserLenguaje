// MiniLexer.java
// Tokenizador básico que reconoce palabras clave "print" e "int".
// Separación automática sin necesitar espacios entre tokens.

public class MiniLexer {

    // Clasifica un lexema en una de las 5 categorías.
    public static TipoToken clasificarToken(String lexema) {
        // ahora incluye "int" como palabra clave
        if (lexema.equals("print") || lexema.equals("int")) return TipoToken.PALABRA_CLAVE;

        if (lexema.equals("==") || lexema.equals("=") ||
            lexema.equals("+") || lexema.equals("-") ||
            lexema.equals("*") || lexema.equals("/")) {
            return TipoToken.OPERADOR;
        }

        if (lexema.equals(";") || lexema.equals("(") || lexema.equals(")")) {
            return TipoToken.DELIMITADOR;
        }

        if (lexema.matches("[0-9]+")) return TipoToken.LITERAL_NUMERICO;

        return TipoToken.IDENTIFICADOR;
    }

    // Tokeniza la entrada carácter a carácter (sin necesidad de espacios).
    public static Token[] tokenizar(String input) {
        if (input == null) input = "";
        input = input.trim();
        if (input.length() == 0) return new Token[0];

        String[] lexTemp = new String[1000];
        int c = 0;

        int i = 0;
        while (i < input.length()) {
            char ch = input.charAt(i);

            if (Character.isWhitespace(ch)) { i++; continue; }

            if (Character.isLetter(ch) || ch == '_') {
                String s = "" + ch;
                i++;
                while (i < input.length()) {
                    char d = input.charAt(i);
                    if (Character.isLetterOrDigit(d) || d == '_') {
                        s = s + d; i++;
                    } else break;
                }
                lexTemp[c++] = s;
                continue;
            }

            if (Character.isDigit(ch)) {
                String s = "" + ch;
                i++;
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    s = s + input.charAt(i); i++;
                }
                lexTemp[c++] = s;
                continue;
            }

            if (ch == '=') {
                if (i + 1 < input.length() && input.charAt(i + 1) == '=') {
                    lexTemp[c++] = "=="; i += 2;
                } else {
                    lexTemp[c++] = "="; i += 1;
                }
                continue;
            }

            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                lexTemp[c++] = "" + ch; i++; continue;
            }

            if (ch == ';' || ch == '(' || ch == ')') {
                lexTemp[c++] = "" + ch; i++; continue;
            }

            // carácter extraño: lo guardamos para que el parser lo explique como error
            lexTemp[c++] = "" + ch; i++;
        }

        Token[] tokens = new Token[c];
        for (int k = 0; k < c; k++) {
            String lex = lexTemp[k];
            TipoToken tipo = clasificarToken(lex);
            tokens[k] = new Token(tipo, lex);
        }
        return tokens;
    }
}
