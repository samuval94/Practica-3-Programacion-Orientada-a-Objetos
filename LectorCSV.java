import java.io.*;
import java.util.*;

public class LectorCSV {
    public static class ConjuntoDatos {
        public final double[][] X;
        public final double[] y;
        public final String[] encabezado;
        public ConjuntoDatos(double[][] X, double[] y, String[] encabezado) { this.X = X; this.y = y; this.encabezado = encabezado; }
    }

    public static ConjuntoDatos leer(String ruta) throws IOException {
        List<double[]> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        String[] encabezado;

        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String line = lector.readLine();
            if (line == null) throw new IOException("Archivo vacío: " + ruta);
            encabezado = dividir(line);
            
            while ((line = lector.readLine()) != null) {
                line = line.trim(); 
                if (line.isEmpty()) continue;
                String[] p = dividir(line);
                int d = p.length - 1;
                double[] fila = new double[d];
                for (int j = 0; j < d; j++) fila[j] = Double.parseDouble(p[j]);
                xs.add(fila);
                ys.add(Double.parseDouble(p[d]));
            }
        }
        double[][] X = xs.toArray(new double[0][]);
        double[] y = ys.stream().mapToDouble(Double::doubleValue).toArray();
        return new ConjuntoDatos(X, y, encabezado);
    }

    private static String[] dividir(String texto) {
        String[] a = texto.split(",");
        for (int i = 0; i < a.length; i++) a[i] = a[i].trim();
        return a;
    }
}
