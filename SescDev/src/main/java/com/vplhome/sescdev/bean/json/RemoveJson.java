/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean.json;

import com.vplhome.database.HibernateUtil;
import com.vplhome.database.entity.CourseEntity;
import com.vplhome.database.entity.RegisterEntity;
import com.vplhome.database.entity.UserEntity;
import com.vplhome.sescdev.model.JsonProcess;
import com.vplhome.sescdev.util.Messages;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vpl
 */
@ManagedBean
@ViewScoped
public class RemoveJson extends JsonProcess {

    @Override
    public void processJson() {
        HttpServletRequest request = facesRequest();
        try {
            String entidade = (String) request.getAttribute("entidade");
            Long id = Long.parseLong(request.getAttribute("id").toString());
            switch (entidade) {
                case "usuario":
                    UserEntity.showAll("WHERE id=" + id).forEach(user ->{
                        HibernateUtil.delete(user);
                    });
                    RegisterEntity.showAll("WHERE idusuario=" + id).forEach(register -> {
                        HibernateUtil.delete(register);
                    });
                    break;
                case "curso":
                    CourseEntity.showAll("WHERE id=" + id).forEach(course ->{
                        HibernateUtil.delete(course);
                    });
                    RegisterEntity.showAll("WHERE idcurso=" + id).forEach(register -> {
                        HibernateUtil.delete(register);
                    });
                    break;
                case "registro":
                    RegisterEntity.showAll("WHERE id=" + id).forEach(register -> {
                        HibernateUtil.delete(register);
                    });
                    break;
                default:
                    break;
            }
            renderUserJson(Messages.status200());
        } catch (NumberFormatException e) {
            renderUserJson(Messages.selectMSG(Messages.CODE_NOT_MODIFIED));
        }
    }

    @Override
    public void selectAll() {
        renderUserJson(Messages.selectMSG(Messages.CODE_REDIRECT));
    }
}
