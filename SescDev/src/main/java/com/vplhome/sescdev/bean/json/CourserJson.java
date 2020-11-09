/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.bean.json;

import com.vplhome.database.entity.CourseEntity;
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
public class CourserJson extends JsonProcess {

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
                    CourseEntity course = new CourseEntity();
                    Enumeration<String> iterator = request.getAttributeNames();
                    while (iterator.hasMoreElements()) {
                        String key = iterator.nextElement();
                        if (!key.equals(Messages.KEY_CSRF)) {
                            switch (key) {
                                case "id":
                                    course.setId(Long.parseLong(request.getAttribute(key).toString()));
                                    break;
                                case "categoria":
                                    course.setCategoria((String) request.getAttribute(key));
                                    break;
                                case "titulo":
                                    course.setTitulo((String) request.getAttribute(key));
                                    break;
                                case "descricao":
                                    course.setDescricao((String) request.getAttribute(key));
                                    break;
                                case "valor":
                                    course.setValor((String) request.getAttribute(key));
                                    break;
                                case "cargahoraria":
                                    course.setCargaHoraria((int) request.getAttribute(key));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    try {
                        course.save();
                    } catch (Exception e) {
                        tokenReponse = Messages.CODE_NOT_MODIFIED;
                    }
                }
            }
        } catch (NumberFormatException e) {
            tokenReponse = Messages.CODE_FORBIDDEN;
        }
        renderUserJson(Messages.selectMSG(tokenReponse));
    }

    @Override
    public void selectAll() {
        String arrayJson = new String();
        List<CourseEntity> courses = CourseEntity.showAll();
        for (CourseEntity course : courses) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", course.getId());
            data.put("categoria", course.getCategoria());
            data.put("titulo", course.getTitulo());
            data.put("descricao", course.getDescricao());
            data.put("valor", course.getValor());
            data.put("cargahoraria", course.getCargaHoraria());
            if (!arrayJson.equals("")) {
                arrayJson += ",\n";
            }
            arrayJson += String.format("{%s}", Messages.createList(data));
        }

        doneRequest(arrayJson);
    }
}
