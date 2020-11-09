/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.sescdev.security;

import com.vplhome.sescdev.util.Messages;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author vpl
 */
public class JwtProcess {

    private static final String SECRET_KEY = "dnBsLURldlNlc2M=";

    public static String createJWT(Map<String, Object> map) {
        return createJWT(map, 1);
    }

    public static String createJWT(Map<String, Object> map,int time) {
        long currentTime = System.currentTimeMillis();
        String jws = Jwts.builder()
                .setIssuer("JWT")
                .setSubject("SescDev")
                .addClaims(map)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(currentTime)))
                .setExpiration(Date.from(Instant.ofEpochSecond(currentTime + (time * 1800000))))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(SECRET_KEY)
                )
                .compact();

        return jws;
    }

    public static Jws<Claims> readJWT(String token) {
        return Jwts.parser()
                .setSigningKey(TextCodec.BASE64.decode(SECRET_KEY))
                .parseClaimsJws(token);
    }

    public static int validationJWT(String token) {
        return validationJWT(readJWT(token));
    }

    public static int validationJWT(Jws<Claims> jws) {
        try {
            long exp = (long) jws.getBody().get("exp");
            long currentTime = System.currentTimeMillis();
            return (exp > currentTime) ? Messages.CODE_ACCEPTED : Messages.CODE_UNAUTHORIZED;
        } catch (JwtException e) {
            return Messages.CODE_FORBIDDEN;
        }
    }
}
