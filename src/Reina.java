public class Reina extends Pieza {

    @Override
    public boolean movimientoValido(int filaDestino, int colDestino, Casilla casilla) {

        boolean esValido = true;

        // MOVIMIENTO AL MISMO LUGAR MISMO LUGAR
        if (fila == filaDestino && columna == colDestino) {
            return false;
        }

        // MOVIMIENTO REINA ( LÓGICA )
        int df = Math.abs(filaDestino - fila);
        int dc = Math.abs(colDestino - columna);

        if (df != 0 && dc != 0 && df != dc) {
            esValido = false;
        }

        // MOVIMIENTO A UN LUGAR DONDE HAY UNA PIEZA YA SITUADA
        Pieza destino = casilla.getPieza(filaDestino, colDestino);
        if (destino != null && destino.getColor() == this.color) {
            esValido = false;
        }

        return esValido;
    }

}

