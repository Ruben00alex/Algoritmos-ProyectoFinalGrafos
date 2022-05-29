/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmosgrafos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbook
 */
public class Grafo {

    private int numVertices;
    private ArrayList<Vertice> vertices = new ArrayList<>();
    private Integer[][] matrizAdyacencia;
    private List<Integer> listaAdyacencia;

    public Grafo(Integer[] grados) {
        matrizAdyacencia = new Integer[grados.length][grados.length];

        for (int i = 0; i < grados.length; i++) {
            for (int j = 0; j < grados.length; j++) {

                matrizAdyacencia[i][j] = 0;
            }
        }

        this.numVertices = grados.length;
        generarVerticesConGrados(grados);
    }

    public void nuevoVertice(Vertice nuevoVert) {
        vertices.add(nuevoVert);
    }

    public void generarVerticesConGrados(Integer[] grados) {
        for (int i = 0; i < numVertices; i++) {
            // System.out.println("id:"+ (i+1));
            vertices.add(new Vertice(grados[i], i, i + 1));
            vertices.get(i).setId(i + 1);
//            vertices.get(i).setGrado(grados[i]);

        }

    }

    public void generarVertices() {
        for (int i = 0; i < numVertices; i++) {
            vertices.add(new Vertice(i));
        }

    }

    public void generarMatrizDeAdyacencia() {
        ArrayList<Vertice> verts = new ArrayList<>();
        //Generar una copia de los vertices para no afectar el ArrayList original.
        for (Vertice vert : vertices) {
            verts.add(new Vertice(vert.getGrado(), vert.getIdx(), vert.getId()));
        }
      
        for (int H = 0; H < 4; H++) {
            //Ordenar descendentemente por grados:
            verts = ordenarDescendente(verts);

            System.out.println("\nInicio:");
            
            
            for (int i = 0; i < verts.size(); i++) {
                System.out.print("+" + verts.get(i).getIdx());
            }
            
            System.out.println("\n");
            for (int i = 0; i < verts.size(); i++) {
                System.out.print("+" + verts.get(i).getGrado());
            }
            System.out.println("\n");
            int k = verts.get(0).getGrado();//seleccionamos el grado del primer nodo a conectar

            for (int i = 0; i < k; i++) {
              // System.out.println("vertice1: "+ verts.get(i).getIdx()+"vertice2: "+verts.get(i+1).getIdx());
                conectarVerticesPorIdx(verts.get(0).getIdx(), verts.get(i + 1).getIdx());//conectamos con los siguientes nodos en la lista
                //System.out.println("\n");
                //mostrarMatriz(matrizAdyacencia);
                verts.get(i+1).setGrado(verts.get(i+1).getGrado() - 1);//disminuimos el grado de los nodos que ya se conectaron
            }

            verts.remove(0);//removemos el primer elemento
            
            
            for (int i = 0; i < verts.size(); i++) {
                System.out.print("" + verts.get(i).getGrado());
            }

        }

    }

    public ArrayList<Vertice> ordenarDescendente(ArrayList<Vertice> vertsArr) {
        ArrayList<Vertice> arr = new ArrayList<>();
        ArrayList<Vertice> arr2 = new ArrayList<>();

        //Generar una copia de los vertices para no afectar el ArrayList original.
        for (Vertice vert : vertsArr) {
            arr.add(new Vertice(vert.getGrado(), vert.getIdx()));
        }

        int tamaño = arr.size();

        for (int i = 0; i < tamaño - 1; i++) {
            int indiceMinimo = i;
            //System.out.println("89: arr.get(j).grado: " + arr.get(i).getGrado() + "arr.get(indiceMinimo).getGrado():" + arr.get(indiceMinimo).getGrado());
            for (int j = i + 1; j < tamaño; j++) {

                if (arr.get(j).getGrado() < arr.get(indiceMinimo).getGrado()) {
                    indiceMinimo = j;
                }

                //System.out.println("arr " + arr.get(i));
            }
            Vertice temp = arr.get(indiceMinimo);
            arr.set(indiceMinimo, arr.get(i));
            arr.set(i, temp);
        }

        for (int i = tamaño; i > 0; i--) {
            //System.out.println("tamaño" + i);
            arr2.add(arr.get(i - 1));
        }
        return arr2;
    }

    public Integer[] extraerSecuenciaDeGrados(ArrayList<Vertice> vertices) {
        Integer[] grados = new Integer[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            grados[i] = vertices.get(i).getGrado();
        }
        return grados;
    }

    public void conectarVertices(int id1, int id2) {
        
        //Utilizar como : conectarVertices(Vertice vert1 =vertPorIdx(1) , Vertice vert2 = vertPorIdx(2))
        matrizAdyacencia[id1 - 1][id2 - 1] = 1;
        matrizAdyacencia[id2 - 1][id1 - 1] = 1;
    }

    public void conectarVerticesPorIdx(int idx1, int idx2) {
        //Utilizar como : conectarVertices(Vertice vert1 =vertPorIdx(1) , Vertice vert2 = vertPorIdx(2))
        matrizAdyacencia[idx1][idx2] = 1;
        matrizAdyacencia[idx2][idx1] = 1;
    }

    public Vertice vertPorIdx(int idx) {
        for (Vertice vert : vertices) {
            if (vert.getIdx() == idx) {
                return vert;
            }
        }
        return null;
    }

    public void mostrarMatriz(Integer[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("\n");
        }

    }

    public Integer[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    public void setMatrizAdyacencia(Integer[][] matrizAdyacencia) {
        this.matrizAdyacencia = matrizAdyacencia;
    }

}
