// Parser.java
// Parser descendente sencillo. Gramática soportada:
// StmtList → Stmt StmtList | ε
// Stmt → 'int' ID ('=' Expr)? ';'    <-- añadido (declaración con/ sin inicialización)
//       | ID '=' Expr ';'
//       | 'print' '(' Expr ')' ';'
// Expr/Term/Factor como antes.

public class Parser {
    private Token[] tokens;
    private int pos;
    private String error;

    public Parser(Token[] tokens) {
        this.tokens = (tokens == null) ? new Token[0] : tokens;
        this.pos = 0;
        this.error = null;
    }

    public boolean parse() {
        parseStmtList();

        if (error != null) {
            System.out.println("Error sintáctico: " + error);
            return false;
        }

        if (pos < tokens.length) {
            System.out.println("Error sintáctico: token inesperado '" + tokens[pos].lexema + "' al final");
            return false;
        }

        System.out.println("Validación sintáctica: OK");
        return true;
    }

    private void parseStmtList() {
        while (pos < tokens.length && error == null) {
            parseStmt();
        }
    }

    private void parseStmt() {
        if (error != null) return;

        // Declaración: int ID ( = Expr )? ;
        if (lookLex("int")) {
            pos++; // consumir 'int'
            if (!lookType(TipoToken.IDENTIFICADOR)) {
                error = "se esperaba IDENTIFICADOR después de 'int' (llegó '" + currentLexema() + "')";
                return;
            }
            pos++; // consumir ID

            // si hay inicialización: =
            if (lookLex("=")) {
                pos++; // consumir =
                parseExpr();
            }

            consumeLex(";", "falta ';' al final de la declaración");
            return;
        }

        // print ( Expr ) ;
        if (lookLex("print")) {
            pos++;
            consumeLex("(", "se esperaba '(' después de 'print'");
            parseExpr();
            consumeLex(")", "falta ')' en print");
            consumeLex(";", "falta ';' al final del print");
            return;
        }

        // ID = Expr ;
        if (lookType(TipoToken.IDENTIFICADOR)) {
            pos++; // consumir ID
            consumeLex("=", "se esperaba '=' después del identificador en la asignación");
            parseExpr();
            consumeLex(";", "falta ';' después de la asignación");
            return;
        }

        if (pos < tokens.length) {
            error = "sentencia inválida cerca de '" + tokens[pos].lexema + "'";
        } else {
            error = "entrada incompleta";
        }
    }

    private void parseExpr() {
        parseTerm();
        while (error == null && (lookLex("+") || lookLex("-"))) {
            pos++;
            parseTerm();
        }
    }

    private void parseTerm() {
        parseFactor();
        while (error == null && (lookLex("*") || lookLex("/"))) {
            pos++;
            parseFactor();
        }
    }

    private void parseFactor() {
        if (error != null) return;

        if (lookType(TipoToken.LITERAL_NUMERICO) || lookType(TipoToken.IDENTIFICADOR)) {
            pos++;
            return;
        }

        if (lookLex("(")) {
            pos++;
            parseExpr();
            consumeLex(")", "Paréntesis no balanceados: falta ')'");
            return;
        }

        if (pos < tokens.length) {
            error = "factor inválido cerca de '" + tokens[pos].lexema + "'";
        } else {
            error = "factor inválido: fin de entrada";
        }
    }

    // helpers
    private boolean lookLex(String lex) {
        return pos < tokens.length && tokens[pos].lexema.equals(lex);
    }

    private boolean lookType(TipoToken tipo) {
        return pos < tokens.length && tokens[pos].tipo == tipo;
    }

    private void consumeLex(String lex, String msg) {
        if (error != null) return;
        if (pos >= tokens.length) { error = msg + " (fin de entrada)"; return; }
        if (!tokens[pos].lexema.equals(lex)) { error = msg + " (llegó '" + tokens[pos].lexema + "')"; return; }
        pos++;
    }

    private String currentLexema() {
        if (pos < tokens.length) return tokens[pos].lexema;
        return "<EOF>";
    }
}
