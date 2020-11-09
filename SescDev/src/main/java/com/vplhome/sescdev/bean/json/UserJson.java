/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean.json;

import com.vplhome.database.entity.UserEntity;
import com.vplhome.sescdev.functions.Email;
import com.vplhome.sescdev.model.JsonProcess;
import com.vplhome.sescdev.security.JwtProcess;
import com.vplhome.sescdev.util.Messages;
import com.vplhome.sescdev.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
public class UserJson extends JsonProcess {

    public void authenticationUser() {
        String jsonResponse;
        final int ONE_DAY = 48;
        try {
            String token = null;
            HttpServletRequest request = facesRequest();
            HashMap<String, Object> claims = new HashMap<>();
            Enumeration<String> iterator = request.getAttributeNames();
            while (iterator.hasMoreElements()) {
                String key = iterator.nextElement();
                if (!key.equals(Messages.KEY_CSRF)) {
                    claims.put(key, request.getAttribute(key));
                } else {
                    token = (String) request.getAttribute(Messages.KEY_CSRF);
                }
            }

            boolean admin = UserEntity.showAll().isEmpty();
            String url = request.getRequestURL().toString();
            url = url.substring(0, url.lastIndexOf("/"));
            url += "/admin/validacao.xhtml?%s=";
            url = String.format(url, Messages.KEY_CSRF);
            if (admin) {
                claims.put("admin", true);
                url += JwtProcess.createJWT(claims, ONE_DAY);
                Email.send((String) claims.get("email"), url);
                jsonResponse = Messages.status100();
            } else {
                if (claims.get("admin") == null) {
                    admin = false;
                } else {
                    admin = (boolean) claims.get("admin");
                    claims.remove("admin");
                }
                if (token != null && !token.equals("")) {
                    int tokenReponse;
                    boolean isAdmin;
                    try {
                        Jws<Claims> jws = JwtProcess.readJWT(token);
                        tokenReponse = JwtProcess.validationJWT(jws);
                        isAdmin = ((boolean) jws.getBody().get("admin"));
                    } catch (Exception e) {
                        isAdmin = false;
                        tokenReponse = Messages.CODE_FORBIDDEN;
                    }
                    if (tokenReponse == Messages.CODE_FORBIDDEN) {
                        jsonResponse = Messages.selectMSG(tokenReponse);
                    } else if (tokenReponse == Messages.CODE_ACCEPTED && isAdmin) {
                        claims.put("admin", admin);
                        url += JwtProcess.createJWT(claims, ONE_DAY);
                        Email.send((String) claims.get("email"), url);
                        jsonResponse = Messages.status100();
                    } else {
                        claims.put("admin", false);
                        url += JwtProcess.createJWT(claims, ONE_DAY);
                        Email.send((String) claims.get("email"), url);
                        jsonResponse = Messages.status100();
                    }
                } else {
                    claims.put("admin", false);
                    url += JwtProcess.createJWT(claims, ONE_DAY);
                    Email.send((String) claims.get("email"), url);
                    jsonResponse = Messages.status100();
                }
            }
        } catch (Exception e) {
            jsonResponse = Messages.selectMSG(Messages.CODE_FORBIDDEN);
        }
        renderUserJson(jsonResponse);
    }

    @Override
    public void processJson() {
        UserEntity user = new UserEntity();
        HttpServletRequest request = facesRequest();
        Enumeration<String> iterator = request.getAttributeNames();
        while (iterator.hasMoreElements()) {
            String key = iterator.nextElement();
            switch (key) {
                case "id":
                    user.setId(Long.parseLong(request.getAttribute(key).toString()));
                    break;
                case "cpf":
                    user.setCpf((String) request.getAttribute(key));
                    break;
                case "nome":
                    user.setNome((String) request.getAttribute(key));
                    break;
                case "sobrenome":
                    user.setSobrenome((String) request.getAttribute(key));
                    break;
                case "email":
                    user.setEmail((String) request.getAttribute(key));
                    break;
                case "telefone":
                    user.setTelefone((String) request.getAttribute(key));
                    break;
                case "senha":
                    user.setSenha(Utils.getPassword((String) request.getAttribute(key)));
                    break;
                case "admin":
                    user.setAdmin((boolean) request.getAttribute(key));
                    break;
                default:
                    break;
            }

        }
        try {
            user.save();
            renderUserJson(Messages.selectMSG(Messages.CODE_CREATED));
        } catch (Exception e) {
            renderUserJson(Messages.selectMSG(Messages.CODE_NOT_MODIFIED));
        }
    }

    @Override
    public void selectAll() {
        String arrayJson = new String();
        List<UserEntity> users = UserEntity.showAll();
        for (UserEntity user : users) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", user.getId());
            data.put("cpf", user.getCpf());
            data.put("nome", user.getNome());
            data.put("sobrenome", user.getSobrenome());
            data.put("email", user.getEmail());
            data.put("telefone", user.getTelefone());
            data.put("admin", user.isAdmin());
            if (!arrayJson.equals("")) {
                arrayJson += ",\n";
            }
            arrayJson += String.format("{%s}", Messages.createList(data));
        }
        doneRequest(arrayJson);
    }
}
