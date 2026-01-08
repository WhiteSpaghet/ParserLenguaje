// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Comunicación clara con el usuario (1 punto)
        System.out.println("Programa de práctica: Nivel Léxico + Sintáctico");
        System.out.println("Escribe tu programa en varias líneas. Cuando termines escribe: FIN");
        System.out.println("Puedes escribir sin espacios, por ejemplo: int x; x=5; print(x);");
        System.out.println();

        StringBuilder sb = new StringBuilder();
        while (true) {
            String linea = sc.nextLine();
            if (linea.equals("FIN")) break;
            sb.append(linea);
            sb.append("\n");
        }

        String input = sb.toString();

        // 1) LÉXICO
        Token[] tokens = MiniLexer.tokenizar(input);

        System.out.println("\n--- Tokens (Nivel Léxico) ---");
        if (tokens.length == 0) {
            System.out.println("(No se encontraron tokens)");
        } else {
            for (int i = 0; i < tokens.length; i++) {
                System.out.println(tokens[i]);
            }
        }

        // 2) SINTÁCTICO
        System.out.println("\n--- Comprobación Sintáctica ---");
        Parser parser = new Parser(tokens);
        boolean ok = parser.parse();

        if (!ok) {
            System.out.println("Resultado: Sintaxis incorrecta.");
        } else {
            System.out.println("Resultado: Sintaxis correcta.");
        }
    }
}
