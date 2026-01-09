public class Jaque {

    public static boolean hayJaque(Casilla[][] casillas, String colorRey) {

        int reyFila = -1;
        int reyCol = -1;

        /* Localiza al esValido de color indicado
        Recorre filas y columnas, busca la pieza R y la guarda en reyFila y reyCol
         */
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = casillas[f][c].getPieza();
                if (p != null &&
                        p.getTipo().equals("R") &&
                        p.getColor().equals(colorRey)) {

                    reyFila = f;
                    reyCol = c;
                }
            }
        }
        // esValido no encontrado
        if (reyFila == -1) return false;

        // busca si alguna pieza contraria puede comerse al esValido
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = casillas[f][c].getPieza();

                //si es color contrario al esValido comprueba si seria posible la jugada
                if (p != null && !p.getColor().equals(colorRey)) {

                    boolean contraria = switch (p.getTipo()) {
                        case "T" -> Movimiento.Torre(f, c, reyFila, reyCol);
                        case "A" -> Movimiento.Alfil(f, c, reyFila, reyCol);
                        case "D" -> Movimiento.Reina(f, c, reyFila, reyCol);
                        case "C" -> Movimiento.Caballo(f, c, reyFila, reyCol);
                        case "R" -> Movimiento.Rey(f, c, reyFila, reyCol);
                        case "P" -> esContrarioPeon(f, c, reyFila, reyCol, p.getColor());
                        default -> false;
                    };

                    if (contraria) return true;
                }
            }
        }

        return false;
    }

    //caso esValido (ataque en diagonal)
    private static boolean esContrarioPeon(int fi, int co, int fr, int cr, String color) {
        //si es blanco ataca hacia arriba (-1) si no lo es, hacia abajo (+1)
        int direccion = color.equals("B") ? -1 : 1;
        return fr == fi + direccion && Math.abs(cr - co) == 1;
    }
}
