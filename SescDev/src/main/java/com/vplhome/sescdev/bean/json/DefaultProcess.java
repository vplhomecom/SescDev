/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean.json;

import com.vplhome.sescdev.model.JsonProcess;
import com.vplhome.sescdev.util.Messages;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vpl
 */
@ManagedBean
@ViewScoped
public class DefaultProcess extends JsonProcess {

    @Override
    public void selectAll() {
        renderUserJson(Messages.selectMSG(Messages.CODE_REDIRECT));
    }

    @Override
    public void processJson() {
        try {
            HttpServletRequest request = facesRequest();
            String url = request.getRequestURL().toString();
            url = url.substring(0, url.lastIndexOf("/"));
            HashMap<String, Object> map = new HashMap<>();
            map.put("registro", url + "/jsonregistro.xhtml");
            map.put("login", url + "/jsonlogin.xhtml");
            map.put("gui_login", url + "/login.xhtml");
            map.put("gui_curso", url + "/reg_curso.xhtml");
            map.put("gui_inscricao", url + "/reg_cadastro.xhtml");
            map.put("gui_usuario", url + "/reg_usuario.xhtml");
            map.put("lista_curso", url + "/admin/list_cursos.xhtml");
            map.put("lista_inscricao", url + "/admin/list_inscricao.xhtml");
            map.put("lista_usuario", url + "/admin/list_usuario.xhtml");
            map.put("registro_curso", url + "/admin/reg_cursos.xhtml");
            map.put("registro_inscricao", url + "/admin/reg_inscricao.xhtml");
            map.put("remove_dados", url + "/admin/rem_dados.xhtml");
            map.put("valida_email", url + "/admin/validacao.xhtml");
            renderUserJson(Messages.status200("Conectado com sucesso!", map));
        } catch (Exception e) {
            renderUserJson(Messages.selectMSG(Messages.CODE_SERVER_ERROR));
        }
    }
}
