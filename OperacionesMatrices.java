public final class OperacionesMatrices {
    private OperacionesMatrices() {}

    public static double[][] transpuesta(double[][] A) {
        int n = A.length, m = A[0].length;
        double[][] T = new double[m][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) T[j][i] = A[i][j];
        return T;
    }

    public static double[][] multiplicar(double[][] A, double[][] B) {
        int n = A.length, m = A[0].length, p = B[0].length;
        double[][] C = new double[n][p];
        for (int i = 0; i < n; i++)
            for (int k = 0; k < m; k++) {
                double aik = A[i][k];
                for (int j = 0; j < p; j++) C[i][j] += aik * B[k][j];
            }
        return C;
    }

    public static double[][] inversa(double[][] A) {
        int n = A.length;
        double[][] aug = new double[n][2*n];
        for (int i = 0; i < n; i++) { System.arraycopy(A[i],0,aug[i],0,n); aug[i][n+i]=1; }

        for (int c = 0; c < n; c++) {
            int pivote = c;
            for (int r = c+1; r < n; r++) if (Math.abs(aug[r][c]) > Math.abs(aug[pivote][c])) pivote = r;
            double[] tmp = aug[c]; aug[c] = aug[pivote]; aug[pivote] = tmp;

            double pivot = aug[c][c];
            if (Math.abs(pivot) < 1e-12) throw new RuntimeException("Matriz no invertible");
            for (int j = 0; j < 2*n; j++) aug[c][j] /= pivot;

            for (int r = 0; r < n; r++) if (r != c) {
                double f = aug[r][c];
                for (int j = 0; j < 2*n; j++) aug[r][j] -= f * aug[c][j];
            }
        }
        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++) System.arraycopy(aug[i], n, inv[i], 0, n);
        return inv;
    }
}
