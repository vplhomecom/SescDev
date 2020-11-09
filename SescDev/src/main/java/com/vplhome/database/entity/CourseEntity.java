/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.database.entity;

import com.vplhome.database.HibernateUtil;
import com.vplhome.sescdev.model.Course;
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
@Table(name = "curso")
public class CourseEntity extends Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public long getId() {
        return super.getId();
    }

    @Column
    @Override
    public String getCategoria() {
        return super.getCategoria();
    }

    @Column(unique = true)
    @Override
    public String getTitulo() {
        return super.getTitulo();
    }

    @Column
    @Override
    public String getDescricao() {
        return super.getDescricao();
    }

    @Column
    @Override
    public String getValor() {
        return super.getValor();
    }

    @Column
    @Override
    public int getCargaHoraria() {
        return super.getCargaHoraria();
    }

    public void save() {
        if (getId() == 0) {
            HibernateUtil.insert(this);
        } else {
            CourseEntity user = showAll(String.format("WHERE id=%d", getId())).get(0);
            user.update(this);
            HibernateUtil.update(user);
        }
    }

    public static List<CourseEntity> showAll() {
        return showAll("");
    }

    public static List<CourseEntity> showAll(String condition) {
        return (List<CourseEntity>) HibernateUtil.select(CourseEntity.class,
                 condition);
    }
}
