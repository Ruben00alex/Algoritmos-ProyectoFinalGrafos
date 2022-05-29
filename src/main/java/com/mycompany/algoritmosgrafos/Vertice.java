/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmosgrafos;

/**
 *
 * @author macbook
 */
public class Vertice {

    private Integer grado;
    private int idx;
    private int id;

    public Vertice(Integer grado, int idx) {
        this.grado = grado;
        this.idx = idx;
        this.id = id + 1;
    }
    
    
    public Vertice(Integer grado, int idx, int id) {
        this.grado = grado;
        this.idx = idx;
        this.id = id;
        //System.out.println("id inside: " +this.id);
    }


    public Vertice(int idx) {
        this.idx = idx;
        this.id = idx + 1;
    }

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
