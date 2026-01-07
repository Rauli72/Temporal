public class Tablero {

    public Casilla[][] Casillas;

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

        System.out.print("   ");
        for (char c = 'A'; c <= 'H'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();

        for (int fila = 0; fila < 8; fila++) {

            System.out.print((8 - fila) + "  ");

            for (int col = 0; col < 8; col++) {

                Casilla c = Casillas[fila][col];
                String fondo = ((fila + col) % 2 == 0)
                        ? FONDO_BLANCO + TEXTO_NEGRO
                        : FONDO_NEGRO + TEXTO_BLANCO;

                if (c.getPieza() == null) {
                    System.out.print(fondo + "   " + RESET);
                } else {
                    System.out.print(fondo + " " + emojiPieza(c.getPieza()) + " " + RESET);
                }
            }

            System.out.print("  " + (8 - fila));
            System.out.println();
        }

        System.out.print("   ");
        for (char c = 'A'; c <= 'H'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();
    }

    // ===== EMOJIS =====
    private String emojiPieza(Pieza p) {

        if (p.getColor().equals("B")) {
            switch (p.getTipo()) {
                case "P":
                    return "♙";
                case "T":
                    return "♖";
                case "C":
                    return "♘";
                case "A":
                    return "♗";
                case "D":
                    return "♕";
                case "R":
                    return "♔";
            }
        } else {
            switch (p.getTipo()) {
                case "P":
                    return "♟";
                case "T":
                    return "♜";
                case "C":
                    return "♞";
                case "A":
                    return "♝";
                case "D":
                    return "♛";
                case "R":
                    return "♚";
            }
        }
        return "?";
    }
}
