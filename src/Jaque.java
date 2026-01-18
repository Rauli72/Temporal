public class Jaque {

    public static boolean reyEnJaque(Tablero tablero, String colorRey) {

        int reyFila = -1;
        int reyCol = -1;

        // 1. Buscar el rey
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {

                Casilla casilla = tablero.getCasilla(f, c);

                if (casilla.getPieza() != null) {
                    Pieza p = casilla.getPieza();

                    if (p.getTipo().equals("R") && p.getColor().equals(colorRey)) {
                        reyFila = f;
                        reyCol = c;
                    }
                }
            }
        }

        if (reyFila == -1) return false;

        // 2. Determinar color enemigo
        String enemigo;

        if (colorRey.equals("B")) {
            enemigo = "N";
        } else if (colorRey.equals("N")) {
            enemigo = "B";
        } else {
            return false;
        }


        // 3. Comprobar amenazas
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {

                Casilla casilla = tablero.getCasilla(fila, col);

                if (casilla.getPieza() != null) {
                    Pieza pieza = casilla.getPieza();

                    if (pieza.getColor().equals(enemigo)) {
                        if (amenazaJaque(pieza, fila, col, reyFila, reyCol, tablero)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    // ---- Comprobación de Amenaza ----
    private static boolean amenazaJaque(Pieza pieza,
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

        if (tipo.equals("P")) {
            return Movimiento.PeonAmenaza(fila_ini, col_ini, fila_fin, col_fin, pieza.getColor());
        }

        return false;
    }
}