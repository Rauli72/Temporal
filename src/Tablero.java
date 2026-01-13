public class Tablero {

    private Casilla[][] Casillas;
    private static String[] piBlancas;
    private static String[] piNegras;

    // ANSI
    private static final String RESET = "\u001B[0m";
    private static final String FONDO_NEGRO = "\u001B[40m";
    private static final String FONDO_BLANCO = "\u001B[47m";
    private static final String TEXTO_NEGRO = "\u001B[30m";
    private static final String TEXTO_BLANCO = "\u001B[37m";

    // ===== CONSTRUCTOR =====
    public Tablero() {
        Casillas = new Casilla[8][8];

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Casillas[fila][col] = new Casilla(fila, col);
            }
        }
    }

    // ===== COLOCAR PIEZA =====
    public void colocarPieza(String tipo, String color, String pos) {
        int col = columna(pos.charAt(0));
        int fila = 8 - Character.getNumericValue(pos.charAt(1));

        if (fila < 0 || fila > 7 || col < 0 || col > 7) return;

        Casillas[fila][col].setPieza(new Pieza(tipo, color));
    }

    // ===== CONVERTIR COLUMNA (SWITCH) =====
    private int columna(char c) {
        switch (Character.toUpperCase(c)) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            default:
                return -1;
        }
    }

    // ===== DIBUJAR TABLERO =====
    public void dibujar() {

        System.out.print("    ");
        for (char c = 'A'; c <= 'H'; c++) { //Letras arriba A-H
            System.out.print(" " + c + " ");
        }
        System.out.println();

        for (int fila = 0; fila < 8; fila++) {

            System.out.printf("%2d  ", (8 - fila)); // Números lateral izq del 1-8

            for (int col = 0; col < 8; col++) {

                Casilla c = Casillas[fila][col];
                String fondo = ((fila + col) % 2 == 0)
                        ? FONDO_BLANCO + TEXTO_NEGRO
                        : FONDO_NEGRO + TEXTO_BLANCO;

                String contenido;
                if (c.getPieza() == null) {
                    contenido = " ";
                } else {
                    contenido = letraPieza(c.getPieza());
                }

                System.out.print(fondo + " " + contenido + " " + RESET);
            }

            System.out.printf(" %2d%n", (8 - fila)); // Números lateral der del 1-8
        }

        System.out.print("    ");
        for (char c = 'A'; c <= 'H'; c++) { //Letras abajo A-H
            System.out.print(" " + c + " ");
        }
        System.out.println();
    }


    // ===== PIEZAS =====
    private String letraPieza(Pieza p) {

        String t = p.getTipo();

        if (p.getColor().equals("B")) {
            return t;           // Blancas en mayúscula
        } else {
            return t.toLowerCase(); // Negras en minúscula
        }
    }


    // ===== COMPROBACIÓN DE LA CONSTRUCCIÓN DEL TABLERO =====
    public static boolean cargarLinea(Tablero tablero, String linea, String color) {

        if (linea == null || linea.isEmpty()) return false;

        String contenido = linea.trim();
        if (contenido.isEmpty()) return false;

        String[] piezas = contenido.split(", ");

        if (color.equals("B")) {
            piBlancas = piezas;
        } else {
            piNegras = piezas;
        }

        for (String pieza : piezas) {

            if (pieza.contains(" ")) return false;

            String tipo;
            String pos;

            if (pieza.length() == 2) {          // Peón
                tipo = "P";
                pos = pieza;
            } else if (pieza.length() == 3) {   // R, T, A, C, D
                tipo = String.valueOf(pieza.charAt(0));
                pos = pieza.substring(1);
            } else {
                return false;
            }

            if (!esTipoValido(tipo)) return false;
            if (!esCasillaValida(pos)) return false;

            tablero.colocarPieza(tipo, color, pos.toUpperCase());
        }

        return true;
    }


    // ===== VALIDAR TIPO =====
    private static boolean esTipoValido(String tipo){
        if (tipo.equals("P") || tipo.equals("T") || tipo.equals("C") ||
                tipo.equals("A") || tipo.equals("D") || tipo.equals("R")) {
            return true;
        }
        return false;
    }

    // ===== VALIDAR POSICIÓN =====
    private static boolean esCasillaValida(String casilla) {
        if (casilla.length() != 2) return false;

        char col = Character.toUpperCase(casilla.charAt(0));
        char fila = casilla.charAt(1);

        if (col >= 'A' && col <= 'H' && fila >= '1' && fila <= '8'){
            return true;
        } else {
            return false;
        }
    }

    // ===== VALIDAR PIEZAS =====
    public static boolean esPiezaValida() {

        if (piBlancas == null || piNegras == null) {
            return false;
        }

        for (String blanca : piBlancas) {
            String posBlanca = extraerPosicion(blanca);

            for (String negra : piNegras) {
                String posNegra = extraerPosicion(negra);

                if (posBlanca.equals(posNegra)) {
                    return false; // Dos piezas en la misma casilla
                }
            }
        }
        return true;
    }

    private static String extraerPosicion(String pieza) {
        if (pieza.length() == 2) {
            return pieza.toUpperCase();
        }
        return pieza.substring(1).toUpperCase();
    }


    // ===== GET CASILLA =====
    public Casilla getCasilla(int fila, int col) {
        return Casillas[fila][col];
    }
}