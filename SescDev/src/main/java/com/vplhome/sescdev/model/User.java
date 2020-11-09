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
public class User implements Serializable {

    private long id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String senha;
    private boolean admin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void update(User user) {
        this.setId(user.getId());
        this.setCpf(user.getCpf());
        this.setNome(user.getNome());
        this.setSobrenome(user.getSobrenome());
        this.setEmail(user.getEmail());
        this.setTelefone(user.getTelefone());
        this.setAdmin(user.isAdmin());
        if (user.getSenha() != null && !user.getSenha().equals("")) {
            this.setSenha(user.getSenha());
        }
    }
}
