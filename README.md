# Práctica – Nivel Léxico y Sintáctico (Java)

## Descripción
Este proyecto implementa un **analizador léxico** y un **analizador sintáctico** sencillo en Java.

El programa:
- Lee código fuente desde la **consola** (varias líneas).
- Realiza la **tokenización automática** sin necesidad de espacios.
- Etiqueta correctamente cada token.
- Comprueba la **sintaxis** según una gramática definida.
- Muestra mensajes claros de éxito o error.

El proyecto **no incluye análisis semántico**.

## Estructura del proyecto

ParserLenguaje/
├─ TipoToken.java
├─ Token.java
├─ MiniLexer.java
├─ Parser.java
└─ Main.java

## Uso del programa

1. Ejecutar el programa.
2. Introducir el código en **varias líneas**.
3. Escribir `FIN` para finalizar la entrada y comenzar el análisis.

Ejemplo de entrada:

int x=5;
print(x);
FIN

## Salida del programa

El programa muestra primero los **tokens generados** y después el resultado del análisis sintáctico.

Ejemplo de salida correcta:

--- Tokens (Nivel Léxico) ---
Token: <PALABRA_CLAVE, "int">
Token: <IDENTIFICADOR, "x">
Token: <OPERADOR, "=">
Token: <LITERAL_NUMERICO, "5">
Token: <DELIMITADOR, ";">
Token: <PALABRA_CLAVE, "print">
Token: <DELIMITADOR, "(">
Token: <IDENTIFICADOR, "x">
Token: <DELIMITADOR, ")">
Token: <DELIMITADOR, ";">

--- Comprobación Sintáctica ---
Validación sintáctica: OK
Resultado: Sintaxis correcta.

## Errores sintácticos detectados

El analizador sintáctico detecta y muestra el **primer error** encontrado, indicando claramente la causa.

Ejemplos de errores:

### 1. Falta de punto y coma
Entrada:
x=5
FIN

Salida:
Error sintáctico: falta ';' después de la asignación

### 2. Paréntesis no balanceados
Entrada:
print(x;
FIN

Salida:
Error sintáctico: falta ')' en print

### 3. Orden incorrecto en asignación
Entrada:
= x 5;
FIN

Salida:
Error sintáctico: sentencia inválida cerca de '='

### 4. Factor inválido
Entrada:
x=+5;
FIN

Salida:
Error sintáctico: factor inválido cerca de '+'

### 5. Token inesperado
Entrada:
x=5;;;
FIN

Salida:
Error sintáctico: token inesperado ';' al final

## Compilación y ejecución

Desde la carpeta del proyecto:

### Windows
javac -d out src*.java
java -cp out Main

### Linux / macOS
javac -d out src/*.java
java -cp out Main

## Notas finales
- No es necesario escribir espacios entre tokens.
- El análisis es únicamente **léxico y sintáctico**.
- El código está pensado para un nivel **introductorio de Java**.
