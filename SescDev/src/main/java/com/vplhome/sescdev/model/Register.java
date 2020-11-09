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
public class Register implements Serializable {

    private long id;
    private long idCurso;
    private long idUsuario;
    private boolean pcg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(long idCurso) {
        this.idCurso = idCurso;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isPcg() {
        return pcg;
    }

    public void setPcg(boolean pcg) {
        this.pcg = pcg;
    }

    public void update(Register register) {
        this.setId(register.getId());
        this.setPcg(register.isPcg());
        this.setIdCurso(register.getIdCurso());
        this.setIdUsuario(register.getIdUsuario());
    }
}
