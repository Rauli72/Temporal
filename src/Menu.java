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

        System.out.println("EJEMPLO:");
        System.out.println("Blancas: Rg1, Tf1, h2, g2, f2, d4, e4, a4, b3, c2, Ab2, Ta1");
        System.out.println("Negras: Rc8, Td8, a7, b7, c7, Ae6, d5, e5, f7, g6, Ag7, h7, Th8\n");

        boolean entradaCorrecta = false;

        while (!entradaCorrecta) {

            System.out.print("Blancas: ");
            String blancas = sc.nextLine();

            System.out.print("Negras: ");
            String negras = sc.nextLine();

            tablero = new Tablero(); // Reiniciar tablero

            if (!Tablero.cargarLinea(tablero, blancas, "B")) {
                System.out.println("❌ Error en la entrada de las BLANCAS.\n");
                continue;
            }

            if (!Tablero.cargarLinea(tablero, negras, "N")) {
                System.out.println("❌ Error en la entrada de las NEGRAS.\n");
                continue;
            }

            if (!Tablero.esPiezaValida()) {
                System.out.println("❌ Error: hay piezas en la misma casilla.\n");
                continue;
            }

            boolean jaqueBlancas = Jaque.reyEnJaque(tablero, "B");
            boolean jaqueNegras = Jaque.reyEnJaque(tablero, "N");

            if (jaqueBlancas && jaqueNegras) {
                System.out.println("❌ Posición ilegal: ambos reyes están en jaque.\n");
                continue;
            }

            entradaCorrecta = true;

            System.out.println("\n========================================");
            System.out.println("            TABLERO FINAL");
            System.out.println("========================================\n");

            tablero.dibujar();

            if (jaqueBlancas) {
                System.out.println("\n⚠️  Las BLANCAS están en jaque.");
            }
            else if (jaqueNegras) {
                System.out.println("\n⚠️  Las NEGRAS están en jaque.");
            }
            else {
                System.out.println("\n✔️  Ningún rey está en jaque.");
            }
        }

        // ===== Menú principal sin while(true) =====
        boolean menuActivo = true;

        while (menuActivo) {

            System.out.println("\n===== MENÚ =====");
            System.out.println("1. Mostrar tablero");
            System.out.println("2. Comprobar jaque");
            System.out.println("3. Salir");
            System.out.print("Opción: ");

            String opcion = sc.nextLine();

            if (opcion.equals("1")) {
                tablero.dibujar();
            }
            else if (opcion.equals("2")) {
                System.out.println("Blancas en jaque: " + Jaque.reyEnJaque(tablero, "B"));
                System.out.println("Negras en jaque: " + Jaque.reyEnJaque(tablero, "N"));
            }
            else if (opcion.equals("3")) {
                System.out.println("Saliendo del programa...");
                menuActivo = false; // sale del bucle
            }
            else {
                System.out.println("❌ Opción no válida.");
            }
        }
    }
}
