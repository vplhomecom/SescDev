/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.functions;

import java.util.Base64;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author vpl
 */
public class Email {

    private static String html;
    private static final String KEY = "Base64"; //senha do email
    private static final String FROM = "sescdev@sescdev.com"; //informar um e-mail
    private static final String SUBJECT = "SescDev - Validação de e-mail";

    public static void send(String to, String url) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(KEY);
        String password = new String(decodedBytes);

        html = "<b>Para validar seu cadastro</b> ";
        html += "<a href='%s'>clique aqui!</a>";
        html = String.format(html, url);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(SUBJECT);
        message.setContent(html, "text/html");
        Transport.send(message);
    }
}
