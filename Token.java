// Token.java
// Clase mínima para guardar tipo y lexema
public class Token {
    public TipoToken tipo;
    public String lexema;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "Token: <" + tipo + ", \"" + lexema + "\">";
    }
}
