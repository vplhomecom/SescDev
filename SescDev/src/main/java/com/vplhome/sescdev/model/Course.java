/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.model;

import java.io.Serializable;

/**
 *
 * @author vpl
 */
public class Course implements Serializable {

    private long id;
    private String categoria;
    private String titulo;
    private String descricao;
    private String valor;
    private int cargaHoraria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void update(Course course) {
        this.setId(course.getId());
        this.setCategoria(course.getCategoria());
        this.setTitulo(course.getTitulo());
        this.setDescricao(course.getDescricao());
        this.setValor(course.getValor());
        this.setCargaHoraria(course.getCargaHoraria());
    }
}
