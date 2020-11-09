/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean.json;

import com.vplhome.database.entity.UserEntity;
import com.vplhome.sescdev.model.JsonProcess;
import com.vplhome.sescdev.security.JwtProcess;
import com.vplhome.sescdev.util.Messages;
import com.vplhome.sescdev.util.Utils;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vpl
 */
@ManagedBean
@ViewScoped
public class LoginJson extends JsonProcess {

    @Override
    public void processJson() {
        try {
            UserEntity user = new UserEntity();
            HttpServletRequest request = facesRequest();
            HashMap<String, Object> claims = new HashMap<>();
            Enumeration<String> iterator = request.getAttributeNames();
            while (iterator.hasMoreElements()) {
                String key = iterator.nextElement();
                switch (key) {
                    case "cpf":
                        user.setCpf((String) request.getAttribute(key));
                    case "email":
                        user.setEmail((String) request.getAttribute(key));
                        break;
                    case "senha":
                        user.setSenha((String) request.getAttribute(key));
                        break;
                    default:
                        break;
                }
            }
            List<UserEntity> result = Utils.getLogin(user.getCpf(), user.getEmail(), user.getSenha());
            if (!result.isEmpty()) {
                user = result.get(0);
                claims.put("cpf", user.getCpf());
                claims.put("email", user.getEmail());
                claims.put("nome", user.getNome());
                claims.put("sobrenome", user.getSobrenome());
                claims.put("admin", user.isAdmin());
                String token = JwtProcess.createJWT(claims);
                HashMap<String, Object> map = new HashMap<>();
                map.put(Messages.KEY_CSRF, token);
                renderUserJson(Messages.status200("Login efetuado com sucesso!", map));
            } else {
                renderUserJson(Messages.selectMSG(Messages.CODE_FORBIDDEN));
            }

        } catch (Exception e) {
            renderUserJson(Messages.selectMSG(Messages.CODE_BAD_REQUEST));
        }
    }

    @Override
    public void selectAll() {
        renderUserJson(Messages.selectMSG(Messages.CODE_REDIRECT));
    }
}
