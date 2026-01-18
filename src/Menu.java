import java.util.Scanner;

public class Menu {
    public static String colorJugador;
    private static int contadorMenu = 0;
    private static boolean partidaActiva = true;

    private static void infoMenu() {
        System.out.println("\n========================================");
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
        System.out.println("Blancas: Rg1, Tf1, h2, g2, f2, d4, e4, a4, b3, c2, Ab2, Ta1");
        System.out.println("Negras: Rc8, Td8, a7, b7, c7, Ae6, d5, e5, f7, g6, Ag7, h7, Th8\n");

        System.out.println("REGLAS IMPORTANTES:");
        System.out.println("- No se permiten espacios dentro de cada pieza");
        System.out.println("- Separar piezas con coma y espacio (, )");
        System.out.println("- Columnas de la A a la H");
        System.out.println("- Filas del 1 al 8\n");

        System.out.println("A TENER EN CUENTA:");
        System.out.println("- Letras en mayúsculas son BLANCAS");
        System.out.println("- Letras en minúsculas son NEGRAS\n");
    }

    public static void menuJuego(Scanner sc, Tablero tablero) {
        if (Menu.contadorMenu == 0){
            infoMenu();
            contadorMenu++;
        }

        boolean todoCorrecto = false;
        tablero.resetTablero();

        while (!todoCorrecto) {
            System.out.print("Blancas: ");
            String blancas = sc.nextLine();

            System.out.print("Negras: ");
            String negras = sc.nextLine();

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

            if (!Tablero.hayReyes()) {
                System.out.println("❌ Error: debe haber exactamente un rey blanco y uno negro.\n");
                continue;
            }

            // Si llega aquí, TODO está bien
            todoCorrecto = true;
        }

        boolean jaqueBlancas = Jaque.reyEnJaque(tablero, "B");
        boolean jaqueNegras = Jaque.reyEnJaque(tablero, "N");

        if (jaqueBlancas && jaqueNegras) {
            System.out.println("❌ Posición ilegal: ambos reyes están en jaque.");
            return;
        }

        if (jaqueBlancas) {
            System.out.println("⚠️ Las ⬜ BLANCAS ⬜ están en jaque y deben mover obligatoriamente. ⚠️");
            tablero.dibujar();
            colorJugador = "B";
        }
        else if (jaqueNegras) {
            System.out.println("⚠️ Las ⬛ NEGRAS ⬛ están en jaque y deben mover obligatoriamente. ⚠️");
            tablero.dibujar();
            colorJugador = "N";
        }
        else {
            System.out.println("✔️ Ningún rey está en jaque.");
            System.out.println("El usuario decide qué bando mueve.");
            tablero.dibujar();
            System.out.println("\nEn cual bando quieres mover? [B/N]:");
            do {
                colorJugador = sc.nextLine();
                colorJugador = colorJugador.toUpperCase();
                if (!colorJugador.equals("B") && !colorJugador.equals("N")) {
                    System.out.println("Introduce un color valido");
                }
            } while (!colorJugador.equals("B") && !colorJugador.equals("N"));
        }

        // ===== Juego con menú y turnos =====
        boolean menuActivo = true;

        while (menuActivo) {
            System.out.println("\n===== MENÚ =====");
            System.out.println("Turno actual: " + colorJugador);
            System.out.println("1. Mostrar tablero");
            System.out.println("2. Mover pieza (ej: f2f3)");
            System.out.println("3. Reiniciar partida");
            System.out.println("4. Ver las instrucciones");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            String opcion = sc.nextLine();

            switch (opcion) {

                case "1":
                    tablero.dibujar();
                    break;

                case "2":
                    System.out.print("Introduce el movimiento a realizar: ");
                    String movimiento = sc.nextLine();

                    Funciones.movimientosJugador(tablero, movimiento);

                    if (Funciones.movIlegal) {
                        System.out.println("La partida se ha acabado...");
                        partidaActiva = false;
                        return;
                    }

                    jaqueBlancas = Jaque.reyEnJaque(tablero, "B");
                    jaqueNegras = Jaque.reyEnJaque(tablero, "N");

                    // Mostrar si hay jaque después del movimiento
                    if (jaqueBlancas) {
                        System.out.println("⚠️ Las ⬜ BLANCAS ⬜ están en jaque ⚠️");

                    } else if (jaqueNegras) {
                        System.out.println("⚠️ Las ⬛ NEGRAS ⬛ están en jaque ⚠️");
                    }

                    System.out.println("Reiniciando partida...\n");
                    menuActivo = false; // salir del menú
                    break;

                case "3":
                    System.out.println("Reiniciando partida...\n");
                    menuActivo = false; // salir del menú
                    break;

                case "4":
                    infoMenu();
                    break;

                case "5":
                    System.out.println("Saliendo del programa.");
                    partidaActiva = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static void main(String[] args) {
        while (partidaActiva) {
            Tablero tablero = new Tablero(); // NUEVO TABLERO
            Scanner sc = new Scanner(System.in);

            menuJuego(sc, tablero);
        }

    }
}