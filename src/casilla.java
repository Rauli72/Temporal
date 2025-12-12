public class casilla {
    private int fila;
    private int columna;
    private pieza pieza;

    public casilla(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }

    public pieza getPieza() {
        return pieza;
    }

    public void setPieza(pieza pieza) {
        this.pieza = pieza;
    }
}