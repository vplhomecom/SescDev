/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.security;

import com.vplhome.sescdev.util.Messages;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author vpl
 */
@WebFilter(filterName = "JsonFilter", urlPatterns = {"/jsonregistro.xhtml", "/jsonlogin.xhtml"})
public class JsonFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        try {
            String json = processStream(request);
            if (json != null && !json.equals("")) {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject.keySet().forEach(key -> {
                    request.setAttribute(key, jsonObject.get(key));
                });
            } else if (!request.getParameterMap().isEmpty()) {
                request.getParameterMap().keySet().forEach(key -> {
                    request.setAttribute(key, request.getParameterMap().get(key));
                });
            }
            chain.doFilter(request, response);
        } catch (NullPointerException | JSONException e) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(Messages.selectMSG(Messages.CODE_BAD_REQUEST));
        }
    }

    private String processStream(ServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        if (inputStream != null) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int bytesRead;
            char[] charBuffer = new char[128];
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
            return stringBuilder.toString().trim();
        }
        return null;
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

}
