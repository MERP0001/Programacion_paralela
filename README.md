# ParallelMatrixMultiplication README

## Descripción

Este programa realiza la multiplicación de matrices de manera paralela utilizando la biblioteca MPI en Java. El programa divide la tarea de multiplicación de matrices entre múltiples procesos para aprovechar el procesamiento paralelo y reducir el tiempo de ejecución.

```

## Instrucciones para Compilar y Ejecutar el Programa

1. **Revisar el SDK y la biblioteca MPI:**
   - Asegúrate de tener un SDK de Java y la biblioteca MPI instalada en tu dispositivo.
   - Si no tienes la biblioteca MPI-Java, abre la opción de `Project Structure` en tu entorno de desarrollo integrado (IDE), haz clic en el botón `+`, y agrega la biblioteca MPI.

2. **Configuración del Entorno:**
   - Abre la configuración de `Run` en tu IDE.
   - Aplica la opción `AV` y agrega los siguientes parámetros:
     ```
     -jar $MPJ_HOME\lib\starter.jar -np 4
     ```
   - Define `MPJ_HOME` en tu entorno, por ejemplo:
     ```
     MPJ_HOME=C:\mpj-v0_44
     ```

3. **Compilar y Ejecutar:**
   - Compila el programa utilizando tu IDE.
   - Ejecuta el programa de manera normal.
    ![imagen de ejemplo](images/mi_imagen.png)


## Ejemplos de Entrada y Salida

### Entrada:
Las matrices A y B se inicializan con valores aleatorios y tienen un tamaño de 1000x1000.

### Salida:
El programa imprime el tiempo de ejecución del cálculo paralelo y secuencial en milisegundos. Por ejemplo:

### Resultados con Diferentes Cantidades de Procesos

![Descripción de la imagen](images/mi_imagen.png)

### Gráf

Este README proporciona una guía completa para compilar, ejecutar y analizar el rendimiento del programa de multiplicación de matrices paralelas en Java utilizando MPI.
