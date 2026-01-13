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
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {

                Casilla casilla = tablero.getCasilla(f, c);

                if (casilla.getPieza() != null) {
                    Pieza p = casilla.getPieza();

                    if (p.getColor().equals(enemigo)) {
                        if (amenaza(p, f, c, reyFila, reyCol, tablero)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    // ---- Comprobación de PeonAmenaza ----
    private static boolean amenaza(Pieza p,
                                   int fi, int ci,
                                   int fr, int cr,
                                   Tablero tablero) {

        String tipo = p.getTipo();

        if (tipo.equals("T")) {
            return Movimiento.Torre(fi, ci, fr, cr)
                    && Movimiento.caminoLibre(fi, ci, fr, cr, tablero);
        }

        if (tipo.equals("A")) {
            return Movimiento.Alfil(fi, ci, fr, cr)
                    && Movimiento.caminoLibre(fi, ci, fr, cr, tablero);
        }

        if (tipo.equals("D")) {
            return Movimiento.Reina(fi, ci, fr, cr)
                    && Movimiento.caminoLibre(fi, ci, fr, cr, tablero);
        }

        if (tipo.equals("C")) {
            return Movimiento.Caballo(fi, ci, fr, cr);
        }

        if (tipo.equals("R")) {
            return Movimiento.Rey(fi, ci, fr, cr);
        }

        if (tipo.equals("P")) {
            return Movimiento.PeonAmenaza(fi, ci, fr, cr, p.getColor());
        }

        return false;
    }
}