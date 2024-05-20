package org.example;

import mpi.*;
import java.util.Random;
import java.util.Arrays;

public class ParallelMatrixMultiplication {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int root = 0;
        int myRank = MPI.COMM_WORLD.Rank();
        int numProcs = MPI.COMM_WORLD.Size();
        int n = 1000; // Asegúrate de que este valor sea el tamaño correcto de tus matrices

        // Crear las matrices A y B en el proceso raíz
        double[][] A = null;
        double[][] B = null;
        if (myRank == root) {
            Random random = new Random();
            A = new double[n][n];
            B = new double[n][n];
            // Llenar las matrices A y B con valores aleatorios
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = random.nextDouble();
                    B[i][j] = random.nextDouble();
                }
            }
        }

        // Aplanar las matrices A y B para la transmisión
        double[] flatA = null;
        double[] flatB = null;
        if (myRank == root) {
            flatA = Arrays.stream(A).flatMapToDouble(Arrays::stream).toArray();
            flatB = Arrays.stream(B).flatMapToDouble(Arrays::stream).toArray();
        }

        // Transmitir las matrices aplanadas
        double[] bufferA = new double[n * n];
        double[] bufferB = new double[n * n];
        MPI.COMM_WORLD.Bcast(bufferA, 0, bufferA.length, MPI.DOUBLE, root);
        MPI.COMM_WORLD.Bcast(bufferB, 0, bufferB.length, MPI.DOUBLE, root);

        // Reformar los búferes recibidos de nuevo en matrices 2D
        A = new double[n][n];
        B = new double[n][n];
        for (int i = 0; i < n; i++) {
            A[i] = Arrays.copyOfRange(bufferA, i * n, (i + 1) * n);
            B[i] = Arrays.copyOfRange(bufferB, i * n, (i + 1) * n);
        }

        // Cada proceso calculará una porción de la matriz resultante C
        double[][] C = new double[n][n];
        long startTime = System.currentTimeMillis();
        for (int i = myRank; i < n; i += numProcs) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        long endTime = System.currentTimeMillis();

        // Aplanar la matriz C para la recopilación
        double[] flatC = Arrays.stream(C).flatMapToDouble(Arrays::stream).toArray();

        // Reunir los resultados parciales de cada proceso en el proceso raíz
        double[] gatherBuffer = null;
        if (myRank == root) {
            gatherBuffer = new double[n * n * numProcs];
        }
        MPI.COMM_WORLD.Gather(flatC, 0, n * n, MPI.DOUBLE, gatherBuffer, 0, n * n, MPI.DOUBLE, root);
        
        if (myRank == root) {
            System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
        }
        multiply(A, B);
        MPI.Finalize();

    }
    public static double[][] multiply(double[][] A, double[][] B) {
        long startTime = System.nanoTime();
        int n = A.length;
        double[][] C = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // Tiempo de ejecución en nanosegundos
        System.out.println("Tiempo de ejecución: en sin paralelismo " + duration/1000000 + " ms");
        return C;
    }

}