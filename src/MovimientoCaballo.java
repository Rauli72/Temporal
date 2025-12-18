public class MovimientoCaballo {

    public static boolean esValido(int fi_ini, int co_ini, int fi_fin, int co_fin) {
        int df = Math.abs(fi_fin - fi_ini);
        int dc = Math.abs(co_fin - co_ini);
        // Movimiento en L
        return (df == 2 && dc == 1) || (df == 1 && dc == 2);
    }
}
