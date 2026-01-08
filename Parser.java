// Parser.java
// Parser descendente recursivo simple que sigue la gramática del enunciado:
// StmtList → Stmt StmtList | ε
// Stmt → ID '=' Expr ';' | 'print' '(' Expr ')' ';'
// Expr → Term { ('+' | '-') Term }
// Term → Factor { ('*' | '/') Factor }
// Factor → ID | NUM | '(' Expr ')'

public class Parser {
    private Token[] tokens;
    private int pos;
    private String error; // mensaje del primer error detectado

    public Parser(Token[] tokens) {
        this.tokens = (tokens == null) ? new Token[0] : tokens;
        this.pos = 0;
        this.error = null;
    }

    // parsea la secuencia completa. Devuelve true si no hay errores.
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

        // print ( Expr ) ;
        if (lookLex("print")) {
            consumeLex("print", "se esperaba 'print'");
            consumeLex("(", "se esperaba '(' después de 'print'");
            parseExpr();
            consumeLex(")", "falta ')' en print");
            consumeLex(";", "falta ';' al final de la sentencia print");
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
            pos++; // consumir operador
            parseTerm();
        }
    }

    private void parseTerm() {
        parseFactor();
        while (error == null && (lookLex("*") || lookLex("/"))) {
            pos++; // consumir operador
            parseFactor();
        }
    }

    private void parseFactor() {
        if (error != null) return;

        if (lookType(TipoToken.LITERAL_NUMERICO) || lookType(TipoToken.IDENTIFICADOR)) {
            pos++; // consumir NUM o ID
            return;
        }

        if (lookLex("(")) {
            pos++; // consumir '('
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
        if (pos >= tokens.length) {
            error = msg + " (fin de entrada)";
            return;
        }
        if (!tokens[pos].lexema.equals(lex)) {
            error = msg + " (llegó '" + tokens[pos].lexema + "')";
            return;
        }
        pos++;
    }
}
