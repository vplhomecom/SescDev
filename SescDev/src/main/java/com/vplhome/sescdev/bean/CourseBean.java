/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean;

import com.vplhome.database.entity.CourseEntity;
import com.vplhome.sescdev.model.Course;
import com.vplhome.sescdev.util.Messages;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vpl
 */
@ManagedBean
public class CourseBean extends Course {

    private ArrayList<CourseEntity> list;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute(Messages.KEY_CSRF) == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            try {
                context.getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            list = new ArrayList<>();
            List<CourseEntity> courses = CourseEntity.showAll("GROUP BY categoria");
            courses.forEach(course -> {
                list.add(course);
            });
        }
    }

    public ArrayList<String> completeCourse(String query) {
        String queryLowerCase = query.toLowerCase();
        ArrayList<String> categorie = new ArrayList<>();
        list.forEach(course -> {
            if(course.getCategoria().toLowerCase().startsWith(queryLowerCase)){
                categorie.add(course.getCategoria());
            }
        });
        return categorie;
    }

    public void save() {
        try {
            CourseEntity course = new CourseEntity();
            course.update(this);
            course.save();

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Novo curso inserido!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha!", "Erro inesperado.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
