/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.model;

import com.vplhome.sescdev.util.Messages;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vpl
 */
@ManagedBean
public abstract class JsonProcess implements Serializable {
    
    public abstract void selectAll();
    public abstract void processJson();

    protected HttpServletRequest facesRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }

    protected void renderUserJson(String jsonPattern) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/json");
            externalContext.setResponseCharacterEncoding("UTF-8");
            externalContext.getResponseOutputWriter().write(jsonPattern);
            facesContext.responseComplete();
        } catch (IOException e) {
        }
    }
    
    
    protected void doneRequest(String arrayJson) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("arrayObjectUsuario", String.format("[%s]", arrayJson));
        renderUserJson(Messages.status200("Busca realizada com sucesso!", map));
    }
}
