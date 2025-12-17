public class Caballo extends Pieza {

    @Override
    public boolean movimientoValido(int filaDestino, int colDestino, Casilla casilla) {

        Boolean esValido = true;

        // MOVIMIENTO AL MISMO LUGAR MISMO LUGAR

        if (fila == filaDestino && columna == colDestino) {
            esValido = false;
        }

        // MOVIMIENTO EN L ( LÓGICA )

        int df = Math.abs(filaDestino - fila);
        int dc = Math.abs(colDestino - columna);

        if (!((df == 2 && dc == 1) || (df == 1 && dc == 2))) {
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
