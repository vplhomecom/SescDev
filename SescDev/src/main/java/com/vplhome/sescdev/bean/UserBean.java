/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean;

import com.vplhome.database.entity.UserEntity;
import com.vplhome.sescdev.functions.Email;
import com.vplhome.sescdev.model.User;
import com.vplhome.sescdev.security.JwtProcess;
import com.vplhome.sescdev.util.Messages;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vpl
 */
@ManagedBean
public class UserBean extends User {
    
    private String token;
    private boolean superAdmin;
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute(Messages.KEY_CSRF) != null) {
            token = session.getAttribute(Messages.KEY_CSRF).toString();
            superAdmin = (boolean) JwtProcess.readJWT(token).getBody().get("admin");
        } else {
            superAdmin = false;
        }
    }
    
    public boolean isSuperAdmin() {
        return superAdmin;
    }
    
    public void save() {
        try {
            boolean admin = UserEntity.showAll().isEmpty();
            if (admin) {
                setAdmin(admin);
            }
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url = request.getRequestURL().toString();
            url = url.substring(0, url.lastIndexOf("/"));
            url += "/admin/validacao.xhtml?%s=";
            url = String.format(url, Messages.KEY_CSRF);
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("cpf", getCpf());
            claims.put("email", getEmail());
            claims.put("senha", getSenha());
            claims.put("admin", isAdmin());
            claims.put("nome", getNome());
            claims.put("sobrenome", getSobrenome());
            
            url += JwtProcess.createJWT(claims, 48);
            Email.send(getEmail(), url);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Verifique seu e-mail!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha!", "Erro inesperado.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
