/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.util;

import com.vplhome.database.HibernateUtil;
import com.vplhome.database.entity.UserEntity;
import java.util.List;

/**
 *
 * @author vpl
 */
public class Utils {

    public static String getPassword(String passw) {
        String query = "SELECT PASSWORD('%s')";
        return (String) HibernateUtil.selectSQL(String.format(query, passw)).get(0);
    }

    public static List<UserEntity> getLogin(String login1, String login2, String passw) {
        String str = "WHERE (cpf='%s' OR email='%s') AND senha='%s'";
        String query = String.format(str, login1, login2, getPassword(passw));
        return UserEntity.showAll(query);
    }
}
