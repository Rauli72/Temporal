import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Tablero tablero = new Tablero();

        System.out.println("========================================");
        System.out.println("        CONFIGURACIÓN DEL TABLERO");
        System.out.println("========================================\n");

        System.out.println("Introduzca la posición inicial de las piezas");
        System.out.println("usando NOTACIÓN ALGEBRAICA DE AJEDREZ.\n");

        System.out.println("PIEZAS:");
        System.out.println("R → Rey");
        System.out.println("D → Dama (Reina)");
        System.out.println("T → Torre");
        System.out.println("A → Alfil");
        System.out.println("C → Caballo");
        System.out.println("(Peón no lleva letra)\n");

        System.out.println("EJEMPLO FORMATO DE ENTRADA:");
        System.out.println("Blancas: Rg1, Tf1, h2, g2, f2, d4, e4, Ce5, a4, b3, c2, Ab2, Ta1");
        System.out.println("Negras: Rc8, Td8, a7, b7, c7, Ae6, d5, e5, f7, g6, Ag7, h7, Th8\n");

        System.out.println("REGLAS IMPORTANTES:");
        System.out.println("- No se permiten espacios dentro de cada pieza");
        System.out.println("- Separar piezas con coma y espacio (, )");
        System.out.println("- Columnas de la A a la H");
        System.out.println("- Filas del 1 al 8\n");

        System.out.println("A TENER EN CUENTA:");
        System.out.println("- Letras en mayúsculas son BLANCAS");
        System.out.println("- Letras en minúsculas son NEGRAS\n");

        System.out.print("Blancas: ");
        String blancas = sc.nextLine();

        System.out.print("Negras: ");
        String negras = sc.nextLine();

        if (!Tablero.cargarLinea(tablero, blancas, "B")) {
            System.out.println("Error en la entrada de las BLANCAS.");
            return;

        } else if (!Tablero.cargarLinea(tablero, negras, "N")) {
            System.out.println("Error en la entrada de las NEGRAS.");
            return;

        } else if (!Tablero.esPiezaValida()) {
            System.out.println("Error: hay piezas en la misma casilla.");
            return;
        }

        System.out.println("\nTablero cargado correctamente:\n");
        tablero.dibujar();
    }
}

