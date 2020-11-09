/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean;

import com.vplhome.database.entity.UserEntity;
import com.vplhome.sescdev.security.JwtProcess;
import com.vplhome.sescdev.util.Messages;
import com.vplhome.sescdev.util.Utils;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
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
public class LoginBean implements Serializable {

    private String usuario;
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute(Messages.KEY_CSRF) != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            try {
                context.getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void logar() {
        List<UserEntity> userList = Utils.getLogin(usuario, usuario, senha);
        if (userList.isEmpty()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Falha!", "Usuário ou Senha inválidos.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            try {
                HashMap<String, Object> claims = new HashMap<>();
                UserEntity user = userList.get(0);
                claims.put("id", user.getId());
                claims.put("cpf", user.getCpf());
                claims.put("email", user.getEmail());
                claims.put("nome", user.getNome());
                claims.put("sobrenome", user.getSobrenome());
                claims.put("admin", user.isAdmin());
                String token = JwtProcess.createJWT(claims);

                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute(Messages.KEY_CSRF, token);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().redirect("");
            } catch (IOException | NumberFormatException e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha!", "Erro inesperado.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
