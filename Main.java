import java.util.Arrays;

public class Main {
    private static void ejecutar(String ruta) throws Exception {
        LectorCSV.ConjuntoDatos datos = LectorCSV.leer(ruta);
        double[][] X = datos.X;
        double[]   Y = datos.y;

        // Crear el modelo
        RegresionLineal modelo = new RegresionLineal(true);

        // === data_scaling (requerido) ===
        X = modelo.data_scaling(X);  // guarda parámetros para usar en predict()

        // === fit (requerido) ===
        modelo.fit(X, Y);

        System.out.println("\nArchivo: " + ruta);
        System.out.println("weights: " + Arrays.toString(modelo.getWeights())); // requerido
        System.out.println("bias:    " + modelo.getBias());                     // requerido

        // Para demo, usamos las primeras 3 filas como "X_test"
        int k = Math.min(3, X.length);
        double[][] X_test = new double[k][X[0].length];
        for (int i = 0; i < k; i++) X_test[i] = Arrays.copyOf(X[i], X[i].length);

        // === predict (requerido) ===
        double[] pred = modelo.predict(X_test);
        for (double valor : pred) System.out.println("Predicción: " + valor);

        // === score (requerido) ===
        double error = modelo.score(X, Y);  // MSE
        System.out.println("Error (MSE): " + error);
    }

    public static void main(String[] args) throws Exception {
    String ice = (args.length > 0) ? args[0] : "data/Ice_cream_selling_data.csv";
    String exm = (args.length > 1) ? args[1] : "data/student_exam_scores.csv";
    ejecutar(ice);
    ejecutar(exm);
    }
}
