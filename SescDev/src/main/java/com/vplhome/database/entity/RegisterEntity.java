/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.database.entity;

import com.vplhome.database.HibernateUtil;
import com.vplhome.sescdev.model.Register;
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
@Table(name = "matricula")
public class RegisterEntity extends Register {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public boolean isPcg() {
        return super.isPcg(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public long getIdUsuario() {
        return super.getIdUsuario(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column
    @Override
    public long getIdCurso() {
        return super.getIdCurso(); //To change body of generated methods, choose Tools | Templates.
    }

    public void save() {
        HibernateUtil.insert(this);
    }

    public static List<RegisterEntity> showAll() {
        return showAll("");
    }

    public static List<RegisterEntity> showAll(String condition) {
        return (List<RegisterEntity>) HibernateUtil.select(RegisterEntity.class, condition);
    }
}
