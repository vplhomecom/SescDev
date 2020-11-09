/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean;

import com.vplhome.database.entity.CourseEntity;
import com.vplhome.database.entity.RegisterEntity;
import com.vplhome.sescdev.model.Register;
import com.vplhome.sescdev.security.JwtProcess;
import com.vplhome.sescdev.util.Messages;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
public class RegisterBean extends Register {

    private String token;
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
            token = (String) session.getAttribute(Messages.KEY_CSRF);
            list = new ArrayList<>();
            List<CourseEntity> courses = CourseEntity.showAll();
            courses.forEach(course -> {
                list.add(course);
            });
        }
    }

    public ArrayList<CourseEntity> getList() {
        return list;
    }

    public void save() {
        try {
            Jws<Claims> jws = JwtProcess.readJWT(token);
            setIdUsuario(Long.parseLong(jws.getBody().get("id").toString()));
            String query = "WHERE idcurso=%d AND idusuario=%d AND pcg=%d";
            query = String.format(query, getIdCurso(), getIdUsuario(), isPcg() ? 1 : 0);
            if (RegisterEntity.showAll(query).isEmpty()) {
                RegisterEntity register = new RegisterEntity();
                register.update(this);
                register.save();

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Registro confirmado!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else{
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Atenção!", "Estecadastro já existe!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha!", "Erro inesperado.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
