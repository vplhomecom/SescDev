/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean.json;

import com.vplhome.database.entity.CourseEntity;
import com.vplhome.database.entity.RegisterEntity;
import com.vplhome.database.entity.UserEntity;
import com.vplhome.sescdev.model.JsonProcess;
import com.vplhome.sescdev.security.JwtProcess;
import com.vplhome.sescdev.util.Messages;
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
public class RegisterJson extends JsonProcess {

    @Override
    public void processJson() {
        int tokenReponse = Messages.CODE_ACCEPTED;
        try {
            HttpServletRequest request = facesRequest();
            String token = (String) request.getAttribute(Messages.KEY_CSRF);
            if (token != null && !token.equals("")) {
                boolean isAdmin;
                try {
                    Jws<Claims> jws = JwtProcess.readJWT(token);
                    tokenReponse = JwtProcess.validationJWT(jws);
                    isAdmin = ((boolean) jws.getBody().get("admin"));
                } catch (Exception e) {
                    isAdmin = false;
                    tokenReponse = Messages.CODE_FORBIDDEN;
                }
                if (tokenReponse == Messages.CODE_ACCEPTED && isAdmin) {
                    RegisterEntity register = new RegisterEntity();
                    Enumeration<String> iterator = request.getAttributeNames();
                    while (iterator.hasMoreElements()) {
                        String key = iterator.nextElement();
                        if (!key.equals(Messages.KEY_CSRF)) {
                            switch (key) {
                                case "id":
                                    register.setId(Long.parseLong(request.getAttribute(key).toString()));
                                    break;
                                case "idusuario":
                                    register.setIdUsuario(Long.parseLong(request.getAttribute(key).toString()));
                                    break;
                                case "idcurso":
                                    register.setIdCurso(Long.parseLong(request.getAttribute(key).toString()));
                                    break;
                                case "pcg":
                                    register.setPcg((boolean) request.getAttribute(key));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    try {
                        String query = "WHERE idcurso=%d AND idusuario=%d AND pcg=%d";
                        query = String.format(query, register.getIdCurso(), register.getIdUsuario(), register.isPcg() ? 1 : 0);
                        if (RegisterEntity.showAll(query).isEmpty()) {
                            if (!UserEntity.showAll("WHERE id=" + register.getIdUsuario()).isEmpty()
                                    && !CourseEntity.showAll("WHERE id=" + register.getIdCurso()).isEmpty()) {
                                register.save();

                            } else {
                                tokenReponse = Messages.CODE_BAD_REQUEST;
                            }
                        } else {

                            tokenReponse = Messages.CODE_NOT_MODIFIED;
                        }
                    } catch (Exception e) {
                        tokenReponse = Messages.CODE_NOT_MODIFIED;
                    }
                } else {
                    tokenReponse = Messages.CODE_UNAUTHORIZED;
                }
            }
        } catch (NumberFormatException e) {
            tokenReponse = Messages.CODE_BAD_REQUEST;
        }
        renderUserJson(Messages.selectMSG(tokenReponse));
    }

    @Override
    public void selectAll() {
        String arrayJson = new String();
        List<RegisterEntity> registers = RegisterEntity.showAll();
        for (RegisterEntity register : registers) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", register.getId());
            data.put("pcg", register.isPcg());
            data.put("idcurso", register.getIdCurso());
            data.put("idusuario", register.getIdUsuario());
            if (!arrayJson.equals("")) {
                arrayJson += ",\n";
            }
            arrayJson += String.format("{%s}", Messages.createList(data));
        }

        doneRequest(arrayJson);
    }
}
