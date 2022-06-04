/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkgfinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author macbook
 */
public class Grafo {

    //Atributos
    private int numVertices;
    private ArrayList<Vertice> vertices = new ArrayList<>();
    private Integer[] grados;
    private Integer[][] matrizAdyacencia;
    private ArrayList<List<Vertice>> listaAdyacencia;

    //Constructor
    public Grafo(Integer[] grados, Integer[] valores) {
        this.grados = grados;

        matrizAdyacencia = new Integer[grados.length][grados.length];
        listaAdyacencia = new ArrayList<>();
        for (int i = 0; i < grados.length; i++) {
            for (int j = 0; j < grados.length; j++) {
                matrizAdyacencia[i][j] = 0;
            }
        }

        this.numVertices = grados.length;
        generarVerticesConGrados(grados, valores);

        for (int i = 0; i < vertices.size(); i++) {
            listaAdyacencia.add(new ArrayList<Vertice>());
            listaAdyacencia.get(i).add(vertices.get(i));
        }
        generarMatrizDeAdyacencia();
        generarListaDeAdyacencia();
        mostrarLista();

    }

    /*
     * getters y setters
     */
    public Integer[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    public void setMatrizAdyacencia(Integer[][] matrizAdyacencia) {
        this.matrizAdyacencia = matrizAdyacencia;
    }

    /*
     * Manejo de grafo
     */
    public void generarVerticesConGrados(Integer[] grados, Integer[] valores) {
        for (int i = 0; i < numVertices; i++) {
            vertices.add(new Vertice(grados[i], i, i + 1, valores[i]));
            vertices.get(i).setId(i + 1);
        }

    }

    public void generarMatrizDeAdyacencia() {
        ArrayList<Vertice> verts = new ArrayList<>();
        //Generar una copia de los vertices para no afectar el ArrayList original.
        for (Vertice vert : vertices) {
            verts.add(new Vertice(vert.getGrado(), vert.getIdx(), vert.getId(), vert.getValor()));
        }

        for (int i = 0; i < (vertices.size()) - 1; i++) {
            //Ordenar descendentemente por grados:
            verts = ordenarDescendente(verts);

            int grad = verts.get(0).getGrado();//seleccionamos el grado del primer nodo a conectar

            for (int j = 0; j < grad; j++) {
                conectarVerticesPorIdx(verts.get(0).getIdx(), verts.get(j + 1).getIdx());//conectamos con los siguientes nodos en la lista
                verts.get(j + 1).setGrado(verts.get(j + 1).getGrado() - 1);//disminuimos el grado de los nodos que ya se conectaron
            }

            verts.remove(0);//removemos el primer elemento de placeholder

        }

    }

    public String listaDeAdyacenciaToString() {
        String lista = "";
        for (int i = 0; i < listaAdyacencia.size(); i++) {
            for (int j = 0; j < listaAdyacencia.get(i).size(); j++) {
                //System.out.println("listaAdyacencia.get(i).size():"+listaAdyacencia.get(i).size());
                if (j != listaAdyacencia.get(i).size() - 1) {
                    lista += listaAdyacencia.get(i).get(j).getId() + " -> ";
                } else {
                    lista += listaAdyacencia.get(i).get(j).getId()+"\n";
                }

            }
            lista += "\n";
            //System.out.println("\n");

        }
        return lista;
    }

    public void generarListaDeAdyacencia() {

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    //System.out.println("matrizAdyacencia[i][j]:"+ matrizAdyacencia[i][j]);
                    listaAdyacencia.get(i).add(vertices.get(j));

                }

            }
        }
    }

    public void mostrarLista() {
        for (int i = 0; i < listaAdyacencia.size(); i++) {
            for (int j = 0; j < listaAdyacencia.get(i).size(); j++) {
                //System.out.println("listaAdyacencia.get(i).size():"+listaAdyacencia.get(i).size());
                System.out.print(listaAdyacencia.get(i).get(j).getId() + "->");

            }
            System.out.println("\n");

        }

    }

    public ArrayList<Vertice> ordenarDescendente(ArrayList<Vertice> vertsArr) {
        ArrayList<Vertice> arr = new ArrayList<>();
        ArrayList<Vertice> arr2 = new ArrayList<>();

        //Generar una copia de los vertices para no afectar el ArrayList original.
        for (Vertice vert : vertsArr) {
            arr.add(new Vertice(vert.getGrado(), vert.getIdx(), vert.getValor(), vert.getValor()));
        }

        int tamaño = arr.size();

        for (int i = 0; i < tamaño - 1; i++) {
            int indiceMinimo = i;

            for (int j = i + 1; j < tamaño; j++) {
                if (arr.get(j).getGrado() < arr.get(indiceMinimo).getGrado()) {
                    indiceMinimo = j;
                }
            }

            Vertice temp = arr.get(indiceMinimo);
            arr.set(indiceMinimo, arr.get(i));
            arr.set(i, temp);
        }

        for (int i = tamaño; i > 0; i--) {
            arr2.add(arr.get(i - 1));
        }

        return arr2;
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

    public ArrayList inicioDFS() {
        boolean[] visitados = new boolean[matrizAdyacencia.length];
        ArrayList valores = new ArrayList();

        recursivoDFS(0, visitados, valores);

        return valores;
    }

    public void recursivoDFS(int vert, boolean visitados[], ArrayList valores) {
        visitados[vert] = true;
        valores.add(vertPorIdx(vertices.get(vert).getIdx()).getValor());

        for (int i = 0; i < matrizAdyacencia[vert].length; i++) {
            if (!visitados[i]) {
                recursivoDFS(i, visitados, valores);
            }
        }
    }

    public static boolean havelHakimi(Integer[] grados) {
        ArrayList gradosArray = new ArrayList();
        gradosArray.addAll(Arrays.asList(grados));

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

    public Integer[] getGrados() {
        return grados;
    }

    public void setGrados(Integer[] grados) {
        this.grados = grados;
    }

    
    
    
    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

    
    
    
    @Override
    public String toString() {
        String gString = "(";
        for (int i = 0; i < grados.length; i++) {
            gString += String.valueOf(vertices.get(i).getValor());
            if (i < grados.length - 1) {
                gString += ",";
            }
        }
        gString += ")";

        return gString;
    }
}
