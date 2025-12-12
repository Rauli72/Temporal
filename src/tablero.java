public class tablero {

    private casilla[][] casillas;

    public tablero() {
        this.casillas = new casilla[8][8];
        inicializar();
    }

    private void inicializar() {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                casillas[fila][col] = new casilla(fila, col);
            }
        }
    }

    public void vaciar() {
        inicializar();
    }

    public void colocarPieza(pieza pieza, int fila, int col) {
        casillas[fila][col].setPieza(pieza);
    }

    public pieza obtenerPieza(int fila, int col) {
        return casillas[fila][col].getpieza();
    }

    public casilla[][] getCasillas() {
        return casillas;
    }
}
