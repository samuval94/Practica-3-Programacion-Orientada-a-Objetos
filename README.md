# PRÁCTICA 3 PROGRAMACIÓN ORIENTADA A OBJETOS

## Jerónimo Echeverry Marulanda - Samuel Valencia Montoya 

## Reto: Modelos de regresión lineal.
### 
**1. Objetivo**
Desarrollar e implementar una aplicación orientada a objetos en C++ o Java que permita realizar modelos de regresión lineal múltiple, aplicando los principios fundamentales de la programación orientada a objetos para estructurar el código de manera modular y reutilizable, con el fin de entrenar, predecir y evaluar datos mediante métodos propios sin el uso de librerías externas.

__2. Descripción general del código__

El código implementa una regresión lineal múltiple en Java desde cero.
 Su objetivo es encontrar una relación lineal entre las variables de entrada (X) y una salida (y).

__2.1 Estructura general de la clase__

 Atributos principales:
- double[] weights — vector de coeficientes (uno por característica).
- double bias — término independiente (intercepto).
- double[] X_mean, X_std — medias y desviaciones estándar de cada característica de X (usadas para escalar nuevas entradas).
- double y_mean, y_std — media y desviación estándar del vector objetivo y (usadas para escalar y desescalar y y predicciones).

Métodos principales:
- dataScaling(double[][] X, double[] y) — estandariza X e y (media 0, desviación estándar 1).
- fit(double[][] X, double[] y) — entrena el modelo usando descenso de gradiente.
- predict(double[][] X) — predice valores para nuevas entradas.
- score(double[][] X, double[] y) — calcula el MSE (error cuadrático medio).
- predictSingle(double[] x) — predice para una sola muestra (asume que x ya está escalada si se usa internamente).
- rescaleParameters() — convierte weights y bias desde la escala estandarizada a la escala original.
- main — ejemplo sencillo de uso y prueba.
 
__2.2 Escalado de datos (dataScaling)__
 
Esta función normaliza los datos para que todas las variables tengan una escala similar.
 Esto es importante porque el descenso de gradiente funciona mejor si las variables están en el mismo rango.
 
 Pasos:

1. Calcula la media y desviación estándar de cada columna (característica) en X.
2. Estandariza cada valor: 
3. Hace lo mismo con el vector y. Esto evita que variables con valores grandes dominen el entrenamiento.


__2.3 Entrenamiento (fit)__

public void fit(double[][] X, double[] y)
1. Llama a dataScaling(X, y) para normalizar los datos.
2. Inicializa pesos y bias en 0.
3. Define:
	- lr (learning rate) = 0.01 → qué tanto se ajustan los pesos en cada paso.
	- epochs = 1000 → número de iteraciones del entrenamiento.
4. Por cada época:
	- Calcula las predicciones actuales (y_pred) usando predictSingle().
	- Calcula los gradientes:
		- dw: cuánto debe cambiar cada peso.
		- db: cuánto debe cambiar el bias.
	- Actualiza los pesos y el bias restando el gradiente multiplicado por la tasa de aprendizaje.
5. Finalmente, llama a rescaleParameters() para devolver los parámetros a su escala original.

__2.4 Predicción (predict)__

Esta función recibe nuevas muestras y devuelve sus valores predichos:
1. Escala las nuevas entradas X usando las medias y desviaciones del entrenamiento.
2. Calcula las predicciones escaladas con los pesos entrenados.
3. Desescala las predicciones para obtener los valores reales (multiplica por y_std y suma y_mean).
 
__2.5 Función de error (score)__

Calcula el __Error Cuadrático Medio__ (MSE):

MSE = (1 / n) * Σ (yᵢ − ŷᵢ)², mientras más pequeño el MSE, mejor el modelo.
 
__2.6 Métodos auxiliares__

- predictSingle(): calcula una sola predicción con los pesos y el bias actuales.
- rescaleParameters(): convierte los pesos y bias de la escala normalizada a la escala original.
 
 Esto se hace para que las predicciones finales estén en la misma unidad que los datos originales.
 
 
__2.7 Clase LectorCSV__

Esta clase cumple la función de leer los archivos en formato .csv y transformarlos en estructuras que el modelo tenga la capacidad de procesar.
Su principal función es separar las columnas en dos conjuntos: 
- X: contiene las variables independientes.
- Y: contiene el valor objetivo o variable dependiente.

Atributo principal: 
- String [] encabezado, almacena los nombres de las columnas que están en el archivo, permitiendo identificar cada variable.

Método principal: 
- public static ConjuntoDatos leer(String ruta), este método abre el archivo CSV, procesando cada línea. Divide las columnas y convierte los valores en tipo double. Finalmente, la última columna es designada como el valor Y, mientras que el resto hace parte de X.

 
__2.8 Clase EscaladorEstandar__

Para mejorar la estabilidad numérica y optimizar el entrenamiento del modelo, esta clase cumple la función de estandarizar las variables numéricas del conjunto de datos. Garantizando que todas posean una media de 0 y una desviación estándar de 1. 

Atributos principales: 
- double[] media: vector que almacena la media de cada columna de X.
- double[] desviacion: contiene la desviación estándar de cada característica.

Métodos principales: 
- public void ajustar(double [][] X): calcula la media y desviación estándar por columna.
- public double[][] transformar(double [][]X): aplica la transformación estándar a cada valor de X. 

 
__2.9 Clase OperacionesMatrices__

Implementa las herramientas necesarias de álgebra lineal para realizar los calculos necesarios para el modelo de regresion lineal, con el fin de no recurrir a librerías externas.

Métodos principales: 
- public static double[][] transpuesta(double[][] A): Retorna la transpuesta de una matriz A, lo cual significa intercambiar filas por columnas.
- public static double[][] multiplicar(double[][] A, double[][] B): Retorna el producto matricial entre las matrices A y B.
- public static double[][] inversa(double[][] A): Retorna la inversa de la matriz A, por medio del método de Gauss-Jordan.

 
__2.10 Ejemplo (main)__

Se crean datos simples:
X_train = [[1,2], [2,3], [3,4], [4,5]]
y_train = [3,5,7,9]
Esto corresponde a una relación lineal:
 
 y = 1x₁ + 1x₂ + 1

El modelo aprende los pesos y luego predice para nuevos valores [5,6] y [6,7], generando resultados cercanos a 11 y 13.

 
 ### 3. Funcionamiento general:
1. Estandarización de datos:
 Se normalizan las variables para que todas tengan media 0 y desviación estándar 1.
 Esto mejora la estabilidad del algoritmo.
2. Entrenamiento (método fit):
 Usa descenso de gradiente, que ajusta los pesos y el bias iterativamente para minimizar el error cuadrático medio.
3. Predicción (método predict):
 Con los pesos entrenados, calcula nuevas salidas, revirtiendo la estandarización para obtener los valores reales.
4. Evaluación (método score):
 Calcula el Error Cuadrático Medio (MSE), que mide qué tan buenas son las predicciones.
5. Prueba (en main):
 Se entrena el modelo con datos pequeños y se muestran los pesos, bias y predicciones finales.

Por lo que este código enseña los principios de cómo funciona internamente una regresión lineal:
- normaliza los datos,
- aprende los parámetros con descenso de gradiente,
- y predice resultados des escalando nuevamente las salidas.

### 4. Problemas y posibles soluciones:
4.1. Escalado de datos inconsistente (Main.java y método scaleData)

Descripción del problema:
 En el archivo Main.java, se utilizó el método scaleData() de forma independiente para escalar tanto los datos de entrenamiento (X_train) como los datos de prueba (X_test). Esto genera un inconveniente porque cada llamada al método recalcula los valores mínimos y máximos, provocando que el conjunto de prueba se escale con rangos diferentes a los del conjunto de entrenamiento

Solución propuesta: Modificar el diseño del método scaleData() para que devuelva también los valores de min y max, y almacenarlos como atributos de la clase. De esta manera, el conjunto de prueba podrá escalarse utilizando los mismos parámetros del conjunto de entrenamiento, garantizando consistencia entre ambos
 
4.2. Falta de validaciones de dimensiones (fit(), predict() y métodos auxiliares de álgebra lineal)

Descripción del problema: Los métodos multiply() y fit() no verifican que las dimensiones de las matrices y vectores sean compatibles antes de realizar las operaciones. Esto puede producir errores de tipo ArrayIndexOutOfBoundsException si los tamaños de X y y no coinciden.

Solución propuesta: Agregar comprobaciones iniciales que verifiquen la coherencia de las dimensiones antes de ejecutar operaciones matriciales. En caso de error, mostrar mensajes descriptivos que indiquen el origen del problema.
 
4.3. Evaluación del modelo sobre los mismos datos de entrenamiento (Main.java)

Descripción del problema: En el archivo Main.java, el método score() se aplica utilizando el mismo conjunto de entrenamiento con el cual se entrenó el modelo, lo que genera una evaluación optimista y no refleja el rendimiento real del modelo sobre datos nuevos.

Solución propuesta: Separar los datos en conjuntos de entrenamiento y prueba, o implementar validación cruzada para obtener una métrica más representativa del desempeño del modelo.

 
### 5. Conclusiones:

5.1. Aplicación de la Programación Orientada a Objetos: Desarrollar la regresión lineal múltiple en Java permite aplicar principios importantes de este paradigma de programación, como lo son la abstracción, encapsulamiento, modularidad, etc. 

5.2. Aprendizaje de la regresión lineal: Realizar este proyecto desde 0, permitió entender las bases de la regresión lineal y se pudo evidenciar cómo el término independiente (bias), puede ser obtenido desde la ecuación normal: w = (Xᵀ X)⁻¹ Xᵀ.

5.3. Importancia del escalado de datos: Se pudo evidenciar que escalar las variables 	a una media de 0 y una desviación de 1, permitió que la precisión mejorara, y evitó que las variables mayores dominaran el aprendizaje del modelo. 

5.4. Aplicación de álgebra lineal: De este ejercicio se pudo evidenciar que por medio de conocimientos adquiridos en otros cursos, se puede calcular la regresión lineal. Ya que para resolver la ecuación normal, se deben realizar tres operaciones matriciales fundamentales: transpuesta, multiplicación de matrices y la inversa de una matriz.

5.5. Finalmente, este proyecto consolida los conocimientos adquiridos en clase de POO. Pero no solo esto, sino funcionar en conjunto con operaciones matriciales y matemáticas que permitieron dar solución a un sistema computacional autónomo con datos reales, generando predicciones precisas sin necesidad de recurrir a medios externos.
