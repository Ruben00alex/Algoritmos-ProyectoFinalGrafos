/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.algoritmosgrafos;

/**
 *
 * @author macbook
 */
public class AlgoritmosGrafos {

    public static void main(String[] args) {
        Algoritmos algo = new Algoritmos();
        Integer[] grados = {5,3,3,3,2,2};
        //
        //        System.out.println(algo.havelHakimi(grados));
        //        algo.mostrarMatriz(algo.matrizAdyacencia(grados));
        //        for (int i = 0; i < 4; i++) {
        //            System.out.print(grados[i]+",");
        //        }
        Grafo grafo = new Grafo(grados);
        
        //grafo.conectarVerticesPorIdx(0, 1);
        System.out.println("\n");
        grafo.generarMatrizDeAdyacencia();
        System.out.println("\nGrados: ");
        for (int i = 0; i < grados.length; i++) {
            
        System.out.print(grados[i]+", ");
        }
        System.out.println("\n");
        grafo.mostrarMatriz(grafo.getMatrizAdyacencia());

    }

}
