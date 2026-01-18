import java.util.Scanner;

public class Funciones {
    public static boolean movIlegal = false;

    // ===== MOVIMIENTOS DE LAS PIEZAS =====
    public static void movimientosJugador(Tablero tablero, String movimiento) {

        movimiento = movimiento.toLowerCase();

        String origen = null;
        String destino = null;
        String tipo = "P"; // por defecto peón

        // ===== MOVIMIENTO =====
        // CASO PEÓN SIN ORIGEN: c4, e5
        if (movimiento.length() == 2 &&
                movimiento.charAt(0) >= 'a' && movimiento.charAt(0) <= 'h' &&
                movimiento.charAt(1) >= '1' && movimiento.charAt(1) <= '8') {

            tipo = "P";
            destino = movimiento;
        }
        else if (movimiento.length() == 4) {
            // f2f4
            origen = movimiento.substring(0, 2);
            destino = movimiento.substring(2, 4);
        }
        else {
            String obtenerTipo = String.valueOf(Character.toUpperCase(movimiento.charAt(0)));

            if (movimiento.length() == 3) {
                // Te2
                tipo = obtenerTipo;
                destino = movimiento.substring(1, 3);
            }
            else if (movimiento.length() == 5) {
                // Te7e8
                tipo = obtenerTipo;
                origen = movimiento.substring(1, 3);
                destino = movimiento.substring(3, 5);
            }
            else {
                System.out.println("Formato de movimiento inválido.");
                return;
            }
        }

        // Detectar si hay origen explícito
        boolean origenExplicito = (origen != null);

        int fila_destino = 8 - Character.getNumericValue(destino.charAt(1));
        int col_destino = destino.charAt(0) - 'a';

        // ===== CASO 1: ORIGEN EXPLÍCITO (f2f3, Te7e8) =====
        if (origenExplicito) {

            int fila_ini = 8 - Character.getNumericValue(origen.charAt(1));
            int col_ini = origen.charAt(0) - 'a';

            Casilla origenCasilla = tablero.getCasilla(fila_ini, col_ini);

            if (origenCasilla.getPieza() == null) {
                System.out.println("Movimiento ilegal: no hay pieza en el origen.");
                movIlegal = true;
                return;
            }

            Pieza p = origenCasilla.getPieza();

            if (!p.getColor().equals(Menu.colorJugador)) {
                System.out.println("Movimiento ilegal: pieza de color incorrecto.");
                movIlegal = true;
                return;
            }

            // Validación correcta del movimiento
            if (!puedeMover(p, fila_ini, col_ini, fila_destino, col_destino, tablero)) {
                System.out.println("Movimiento ilegal: la pieza no puede moverse así.");
                movIlegal = true;
                return;
            }

            // Mover la pieza
            tablero.getCasilla(fila_destino, col_destino).setPieza(p);
            origenCasilla.setPieza(null);

            System.out.println("Movimiento realizado correctamente.");
            // Volver a dibujar el tablero tras el movimiento.
            tablero.dibujar();
            return;
        }

        // ===== CASO 2: SIN ORIGEN (Te2, e4) =====
        int encontradas = 0;
        int fiVal = -1, ciVal = -1;
        Pieza piezaValida = null;

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {

                Casilla casilla = tablero.getCasilla(f, c);
                if (casilla.getPieza() == null) continue;

                Pieza p = casilla.getPieza();

                if (!p.getColor().equals(Menu.colorJugador)) continue;
                if (!p.getTipo().equals(tipo)) continue;

                if (puedeMover(p, f, c, fila_destino, col_destino, tablero)) {
                    encontradas++;
                    fiVal = f;
                    ciVal = c;
                    piezaValida = p;
                }
            }
        }

        if (encontradas == 0) {
            System.out.println("Movimiento ilegal: ninguna pieza puede llegar.");
            return;
        }

        if (encontradas > 1) {

            System.out.println("Movimiento ambiguo.");
            System.out.println("Especifica la pieza indicando origen y destino (ej: Te7e8):");

            Scanner sc = new Scanner(System.in);
            String Reintentar_mov = sc.nextLine();

            //Reintentar movimiento
            Funciones.movimientosJugador(tablero, Reintentar_mov);
        }

        // mover pieza única
        tablero.getCasilla(fila_destino, col_destino).setPieza(piezaValida);
        tablero.getCasilla(fiVal, ciVal).setPieza(null);

        System.out.println("Movimiento realizado correctamente.");
        tablero.dibujar();
    }


    // ===== VALIDACIÓN DE MOVIMIENTO =====
    private static boolean puedeMover(Pieza pieza,
                                      int fila_ini, int col_ini,
                                      int fila_fin, int col_fin,
                                      Tablero tablero) {

        String tipo = pieza.getTipo();

        if (tipo.equals("T")) {
            return Movimiento.Torre(fila_ini, col_ini, fila_fin, col_fin)
                    && Movimiento.caminoLibre(fila_ini, col_ini, fila_fin, col_fin, tablero);
        }

        if (tipo.equals("A")) {
            return Movimiento.Alfil(fila_ini, col_ini, fila_fin, col_fin)
                    && Movimiento.caminoLibre(fila_ini, col_ini, fila_fin, col_fin, tablero);
        }

        if (tipo.equals("D")) {
            return Movimiento.Reina(fila_ini, col_ini, fila_fin, col_fin)
                    && Movimiento.caminoLibre(fila_ini, col_ini, fila_fin, col_fin, tablero);
        }

        if (tipo.equals("C")) {
            return Movimiento.Caballo(fila_ini, col_ini, fila_fin, col_fin);
        }

        if (tipo.equals("R")) {
            return Movimiento.Rey(fila_ini, col_ini, fila_fin, col_fin);
        }

        if (tipo.equals("P")) {
            // Movimiento normal vertical
            if (Movimiento.PeonMovimiento(fila_ini, col_ini, fila_fin, col_fin, pieza.getColor(), tablero)) {
                return true;
            }
            // Captura diagonal
            if (Movimiento.PeonAmenaza(fila_ini, col_ini, fila_fin, col_fin, pieza.getColor())
                    && tablero.getCasilla(fila_fin, col_fin).getPieza() != null
                    && !tablero.getCasilla(fila_fin, col_fin).getPieza().getColor().equals(pieza.getColor())) {
                return true;
            }
            return false;
        }

        return false;
    }
}