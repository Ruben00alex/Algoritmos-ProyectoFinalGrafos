/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmosgrafos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author macbook
 */
public class Algoritmos {

    public Algoritmos() {
    }

    public static boolean havelHakimi(Integer[] grados) {
        int tamaño = grados.length;
        ArrayList gradosArray = new ArrayList();
        for (int num : grados) {
            gradosArray.add(num);
        }

        //System.out.println(gradosArray.toString());
        while (true) {

            Collections.sort(gradosArray, Collections.reverseOrder());

            if ((int) gradosArray.get(0) == 0) {
                return true;
            }

            int k = (int) gradosArray.get(0);
            gradosArray.remove(gradosArray.get(0));

            if (k > gradosArray.size()) {
                return false;
            }

            for (int i = 0; i < k; i++) {
                gradosArray.set(i, (int) gradosArray.get(i) - 1);

                if ((int) gradosArray.get(i) < 0) {
                    return false;
                }
            }
        }

    }

    public Integer[][] matrizAdyacencia(Integer[] grados) {
        int tamaño = grados.length;
        Integer[][] matriz = new Integer[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = 0;
            }
        }

//        
//        for (int i = 0; i < tamaño; i++) {
//            for (int j = i + 1; j < tamaño; j++) {
//
//                // For each pair of vertex decrement
//                // the degree of both vertex.
//                if (grados[i] > 0 && grados[j] > 0) {
//                    grados[i]--;
//                    grados[j]--;
//                    matriz[i][j] = 1;
//                    matriz[j][i] = 1;
//                }
//            }
//        }
        for (int i = 0; i < tamaño; i++) {
            int cont = grados[i];
            System.out.println("Hilera " + (i + 1) + ": " + cont + "grados.");
            for (int j = 0; j < tamaño; j++) {

                if (i != j && cont > 0 && !gradoColumnasAlcanzados(j, grados[j], matriz, grados) && !gradosAlcanzados(i, grados[i], matriz, grados)) {
                    matriz[i][j] = 1;
                    matriz[j][i] = 1;
                    //System.out.println("cont:" + cont);
                    cont--;
                }

            }
        }
        return matriz;
    }
    
    public Integer[][] generarMatriz(Integer[] grados){
    
    
    }

    public boolean gradosAlcanzados(int fila, int gradoFila, Integer[][] matriz, Integer[] grados) {
        int suma = 0;
        for (int i = 0; i < matriz[fila].length; i++) {
            suma += matriz[fila][i];
        }
        return (grados[fila] == suma);
    }

    public boolean gradoColumnasAlcanzados(int columna, int gradoFila, Integer[][] matriz, Integer[] grados) {
        int suma = 0;
        for (int i = 0; i < matriz[columna].length; i++) {
            suma += matriz[i][columna];
        }
        return (grados[columna] == suma);
    }

    public void mostrarMatriz(Integer[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("\n");
        }

    }

}
