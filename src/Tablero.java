public class Tablero {

    private Casilla[][] Casillas;
    public static String[] piBlancas;
    public static String[] piNegras;

    // ANSI
    private static final String RESET = "\u001B[0m";
    private static final String FONDO_NEGRO = "\u001B[40m";
    private static final String FONDO_BLANCO = "\u001B[47m";
    private static final String TEXTO_NEGRO = "\u001B[30m";
    private static final String TEXTO_BLANCO = "\u001B[37m";

    // ===== CONTADORES =====
    private static int reyesBlancos = 0;
    private static int reyesNegros = 0;
    private static int peonesBlancos = 0;
    private static int peonesNegros = 0;

    // ===== CONSTRUCTOR =====
    public Tablero() {
        Casillas = new Casilla[8][8];
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Casillas[f][c] = new Casilla(f, c);
            }
        }
    }

    // ===== REINICIAR CONTADORES =====
    private static void resetContadores(String color) {
        if (color.equals("B")) {
            reyesBlancos = 0;
            peonesBlancos = 0;
        } else {
            reyesNegros = 0;
            peonesNegros = 0;
        }
    }

    // ===== COLOCAR PIEZA =====
    public void colocarPieza(String tipo, String color, String pos) {

        int col = columna(pos.charAt(0));
        int fila = 8 - Character.getNumericValue(pos.charAt(1));

        if (fila < 0 || fila > 7 || col < 0 || col > 7) {
            return;
        }

        Casillas[fila][col].setPieza(new Pieza(tipo, color));
    }

    // ===== CONVERTIR COLUMNA =====
    private int columna(char c) {
        switch (Character.toUpperCase(c)) {
            case 'A': return 0;
            case 'B': return 1;
            case 'C': return 2;
            case 'D': return 3;
            case 'E': return 4;
            case 'F': return 5;
            case 'G': return 6;
            case 'H': return 7;
            default: return -1;
        }
    }

    // ===== DIBUJAR TABLERO =====
    public void dibujar() {

        System.out.print("    ");
        for (char c = 'A'; c <= 'H'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();

        for (int f = 0; f < 8; f++) {

            System.out.printf("%2d  ", 8 - f);

            for (int c = 0; c < 8; c++) {

                Casilla casilla = Casillas[f][c];
                String fondo = ((f + c) % 2 == 0)
                        ? FONDO_BLANCO + TEXTO_NEGRO
                        : FONDO_NEGRO + TEXTO_BLANCO;

                String contenido = " ";
                if (casilla.getPieza() != null) {
                    contenido = letraPieza(casilla.getPieza());
                }

                System.out.print(fondo + " " + contenido + " " + RESET);
            }

            System.out.printf(" %2d%n", 8 - f);
        }

        System.out.print("    ");
        for (char c = 'A'; c <= 'H'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();
    }

    // ===== REPRESENTACIÓN DE PIEZAS =====
    private String letraPieza(Pieza p) {
        if (p.getColor().equals("B")) {
            return p.getTipo();
        } else {
            return p.getTipo().toLowerCase();
        }
    }

    // ===== CARGAR LINEA =====
    public static boolean cargarLinea(Tablero tablero, String linea, String color) {

        resetContadores(color);

        if (linea == null || linea.isEmpty()) {
            return false;
        }

        String[] piezas = linea.trim().split(", ");

        if (color.equals("B")) {
            piBlancas = piezas;
        } else {
            piNegras = piezas;
        }

        for (String pieza : piezas) {

            if (pieza.contains(" ")) {
                return false;
            }

            String tipo;
            String pos;

            if (pieza.length() == 2) {
                tipo = "P";
                pos = pieza;
            } else if (pieza.length() == 3) {
                tipo = String.valueOf(pieza.charAt(0));
                pos = pieza.substring(1);
            } else {
                return false;
            }

            if (!esTipoValido(tipo, color, pos)) {
                return false;
            }

            if (!esCasillaValida(pos)) {
                return false;
            }

            tablero.colocarPieza(tipo, color, pos.toUpperCase());
        }

        return true;
    }

    // ===== VALIDAR TIPO =====
    private static boolean esTipoValido(String tipo, String color, String pos) {

        if (!(tipo.equals("P") || tipo.equals("T") || tipo.equals("C")
                || tipo.equals("A") || tipo.equals("D") || tipo.equals("R"))) {
            return false;
        }

        // ===== VALIDAR REYES =====
        if (tipo.equals("R")) {
            if (color.equals("B")) {
                reyesBlancos++;
                if (reyesBlancos > 1) return false;
            } else {
                reyesNegros++;
                if (reyesNegros > 1) return false;
            }
            return true;
        }

        // ===== VALIDAR PEONES =====
        if (tipo.equals("P")) {

            int fila = Character.getNumericValue(pos.charAt(1));

            if (color.equals("B")) {
                peonesBlancos++;
                if (peonesBlancos > 8) return false;

                // Peón blanco en fila 1 o 2
                if (fila < 2) return false;

            } else {
                peonesNegros++;
                if (peonesNegros > 8) return false;

                // Peón negro en fila 7 u 8
                if (fila > 7) return false;
            }
            return true;
        }

        // ===== OTRAS PIEZAS =====
        return true;
    }

    // ===== VALIDAR QUE EXISTAN AMBOS REYES =====
    public static boolean hayReyes() {
        return reyesBlancos == 1 && reyesNegros == 1;
    }


    // ===== VALIDAR CASILLA =====
    private static boolean esCasillaValida(String casilla) {

        if (casilla.length() != 2) {
            return false;
        }

        char col = Character.toUpperCase(casilla.charAt(0));
        char fila = casilla.charAt(1);

        return col >= 'A' && col <= 'H' && fila >= '1' && fila <= '8';
    }

    // ===== VALIDAR COLISIONES =====
    public static boolean esPiezaValida() {

        if (piBlancas == null || piNegras == null) {
            return false;
        }

        for (String b : piBlancas) {
            String pb = extraerPosicion(b);
            for (String n : piNegras) {
                String pn = extraerPosicion(n);
                if (pb.equals(pn)) {
                    return false;
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