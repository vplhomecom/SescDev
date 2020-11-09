/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.util;

import java.util.HashMap;

/**
 *
 * @author vpl
 */
public class Messages {

    public static final int CODE_CONTINUE = 100;
    public static final int CODE_OK = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_ACCEPTED = 202;
    public static final int CODE_NOT_MODIFIED = 304;
    public static final int CODE_REDIRECT = 308;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_SERVER_ERROR = 500;
    public static final String KEY_CSRF = "_csrf";

    public static String createBody(boolean success, String body) {
        return "{\n\t\"success\": " + success + "," + body + "\n}";
    }

    public static String statusDefault(int code, String msg, HashMap<String, Object> map) {
        return statusDefault(false, code, msg, map);
    }

    public static String statusDefault(boolean success, int code, String msg, HashMap<String, Object> map) {
        String json = createList(map, 1);
        json += json.equals("") ? "" : ",";
        json += "\n\t\"status\": {";
        json += "\n\t\t\"code\": %d,";
        json += "\n\t\t\"message\": \"%s\"";
        json += "\n\t}";
        return createBody(success, String.format(json, code, msg));
    }

    public static String status100() {
        return status100("Aguardando validação!", null);
    }

    public static String status100(String msg, HashMap<String, Object> map) {
        return statusDefault(true, CODE_CONTINUE, msg, map);
    }

    public static String status200() {
        return status200("OK!", null);
    }

    public static String status200(String msg, HashMap<String, Object> map) {
        return statusDefault(true, CODE_OK, msg, map);
    }

    public static String status201() {
        return status201("Entidade criada!", null);
    }

    public static String status201(String msg, HashMap<String, Object> map) {
        return statusDefault(true, CODE_CREATED, msg, null);
    }

    public static String status202() {
        return status202("Requisição aceita!", null);
    }

    public static String status202(String msg, HashMap<String, Object> map) {
        return statusDefault(true, CODE_ACCEPTED, msg, map);
    }

    public static String status304() {
        return status304("Conteúdo não pode ser modificado!", null);
    }

    public static String status304(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_NOT_MODIFIED, msg, map);
    }

    public static String status308() {
        return status308("Destino inacessível!", null);
    }

    public static String status308(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_REDIRECT, msg, map);
    }

    public static String status400() {
        return status400("Erro na solicitação!", null);
    }

    public static String status400(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_BAD_REQUEST, msg, map);
    }

    public static String status401() {
        return status401("Credencial expirada!", null);
    }

    public static String status401(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_UNAUTHORIZED, msg, map);
    }

    public static String status403() {
        return status403("Credencial inválida!", null);
    }

    public static String status403(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_FORBIDDEN, msg, map);
    }

    public static String status404() {
        return status404("A página solicitada não existe!", null);
    }

    public static String status404(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_NOT_FOUND, msg, map);
    }

    public static String status500() {
        return status500("Erro inesperado no servidor!", null);
    }

    public static String status500(String msg, HashMap<String, Object> map) {
        return statusDefault(CODE_SERVER_ERROR, msg, map);
    }

    public static String selectMSG(int value) {
        switch (value) {
            case CODE_CONTINUE:
                return status100();
            case CODE_OK:
                return status200();
            case CODE_CREATED:
                return status201();
            case CODE_ACCEPTED:
                return status202();
            case CODE_NOT_MODIFIED:
                return status304();
            case CODE_BAD_REQUEST:
                return status400();
            case CODE_UNAUTHORIZED:
                return status401();
            case CODE_FORBIDDEN:
                return status403();
            case CODE_NOT_FOUND:
                return status404();
            case CODE_SERVER_ERROR:
                return status500();
            default:
                return status308();
        }
    }

    public static String createList(HashMap<String, Object> map) {
        return createList(map, 2);
    }
    
    public static String createList(HashMap<String, Object> map, int tabs) {
        String tab = new String();
        for(int i = 0; i < tabs; i++){
            tab += "\t";
        }
        String jsonList = new String();
        if (map != null) {
            for (String key : map.keySet()) {
                if (!jsonList.equals("")) {
                    jsonList += ",";
                }
                Object value = map.get(key);
                if (value instanceof String) {
                    String str = String.valueOf(value);
                    if (str.startsWith("[") || str.startsWith("{")) {
                        jsonList += String.format("\n%s\"%s\": %s", tab, key, str);
                    } else {
                        jsonList += String.format("\n%s\"%s\": \"%s\"", tab, key, str);
                    }
                } else {
                    jsonList += String.format("\n%s\"%s\": %s", tab, key, String.valueOf(value));

                }
            }
        }
        return jsonList;
    }
}
