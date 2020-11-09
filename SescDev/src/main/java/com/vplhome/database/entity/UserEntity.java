/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.database.entity;

import com.vplhome.database.HibernateUtil;
import com.vplhome.sescdev.model.User;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author vpl
 */
@Entity
@Table(name = "usuario")
public class UserEntity extends User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public String getTelefone() {
        return super.getTelefone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column(unique = true)
    @Override
    public String getEmail() {
        return super.getEmail(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public String getSobrenome() {
        return super.getSobrenome(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public String getNome() {
        return super.getNome(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column(unique = true)
    @Override
    public String getCpf() {
        return super.getCpf(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public String getSenha() {
        return super.getSenha(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSenha(String senha) {
        super.setSenha(senha);
    }

    @Column
    @Override
    public boolean isAdmin() {
        return super.isAdmin(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    public void save() {
        if (getId() == 0) {
            HibernateUtil.insert(this);
        } else {
            UserEntity user = showAll(String.format("WHERE id=%d", getId())).get(0);
            user.update(this);
            HibernateUtil.update(user);
        }
    }

    public static List<UserEntity> showAll() {
        return showAll("");
    }

    public static List<UserEntity> showAll(String condition) {
        return (List<UserEntity>) HibernateUtil.select(UserEntity.class, condition);
    }
}
