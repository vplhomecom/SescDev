/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.validations;

import com.vplhome.database.entity.UserEntity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author vpl
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (o instanceof String) {
            if (!UserEntity.showAll(String.format("WHERE email = '%s'", (String) o)).isEmpty()) {
                FacesMessage msg = new FacesMessage("Este e-mail já foi cadastrado! Por favor, informe um endereço de e-mail diferente.");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);

                throw new ValidatorException(msg);
            } else {
                String regex = "^[a-zA-Z0-9]+(([-_.][a-zA-Z0-9]{1,}){0,})+@[a-zA-Z]+[_.-]?[a-zA-Z0-9]+\\.[a-zA-Z0-9]{2,4}$";
                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher((String) o);

                if (!matcher.find()) {
                    FacesMessage msg = new FacesMessage("E-mail inválido! Por favor, informe um endereço de e-mail existente.");
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);

                    throw new ValidatorException(msg);
                }
            }
        }
    }
}
