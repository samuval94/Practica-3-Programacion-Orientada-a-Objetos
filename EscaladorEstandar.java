public class EscaladorEstandar {
    private double[] mean, std;

    public void fit(double[][] X) {
        int n = X.length, d = X[0].length;
        mean = new double[d]; std = new double[d];
        for (int j = 0; j < d; j++) {
            double s = 0; for (int i = 0; i < n; i++) s += X[i][j];
            mean[j] = s / n;
            double var = 0; for (int i = 0; i < n; i++) { double z = X[i][j]-mean[j]; var += z*z; }
            std[j] = Math.sqrt(var / Math.max(1, n-1));
            if (std[j] == 0) std[j] = 1;
        }
    }
    public double[][] transform(double[][] X) {
        int n = X.length, d = X[0].length;
        double[][] Z = new double[n][d];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < d; j++)
                Z[i][j] = (X[i][j]-mean[j]) / std[j];
        return Z;
    }
    public double[][] fitTransform(double[][] X) { fit(X); return transform(X); }
}
