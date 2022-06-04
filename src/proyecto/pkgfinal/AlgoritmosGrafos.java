/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto.pkgfinal;

import java.util.Random;

/**
 *
 * @author carlo
 */
public class AlgoritmosGrafos {

    public static void main(String[] args) {
        Random rand = new Random();
        
        Integer[] grados = {5, 3, 3, 3, 2, 2};
        System.out.println(Grafo.havelHakimi(grados));
        Integer[] valores = new Integer[grados.length];
        for(int i=0;i<valores.length;i++)
            valores[i]=rand.nextInt(100);
        
        Grafo grafo = new Grafo(grados, valores);

        System.out.println("\nAdyacencia");
        grafo.generarMatrizDeAdyacencia();
        
        System.out.println("\nGrados: ");
        for (int i = 0; i < grados.length; i++) {
            System.out.print(grados[i] + ", ");
        }
        
        System.out.println("\nMatris de adyacencia");
        mostrarMatriz(grafo.getMatrizAdyacencia());
        
        System.out.println("\nDFS");
        System.out.print(grafo.inicioDFS());

    }

    public static void mostrarMatriz(Integer[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("\n");
        }

    }
    
}
