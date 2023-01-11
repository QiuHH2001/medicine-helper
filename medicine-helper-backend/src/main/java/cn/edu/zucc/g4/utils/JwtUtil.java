package cn.edu.zucc.g4.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "z!u@c#c$w%x^q&";
    private static final String ISSUER = "byWxq";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final Long EXPIRE_1 = 30L * 1000;
    private static final Long EXPIRE_2 = 5L * 1000 * 60;
    private static final Long EXPIRE_3 = 5L * 1000 * 60 * 60;
    private static final Long EXPIRE_4 = 30L * 1000 * 60 * 60 * 24;

    public static String create(Long id, String phone) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_4))
                .withClaim("phone", phone)
                .withClaim("id", id)
                .sign(ALGORITHM);
    }

    public static Boolean verify(String token) {
        try {
            if (null == token) {
                return false;
            }
            StringBuilder bearerToken = new StringBuilder(token);
            String verifyToken = bearerToken.substring(7);
            JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = verifier.verify(verifyToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public static Long getId(String token) {
        try {
            if (null == token) {
                return null;
            }
            StringBuilder bearerToken = new StringBuilder(token);
            String verifyToken = bearerToken.substring(7);
            JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = verifier.verify(verifyToken);
            return decodedJWT.getClaim("id").asLong();
        } catch (TokenExpiredException e) {
            return null;
        }
    }

    public static String getPhone(String token) {
        try {
            if (null == token) {
                return null;
            }
            StringBuilder bearerToken = new StringBuilder(token);
            String verifyToken = bearerToken.substring(7);
            JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = verifier.verify(verifyToken);
            return decodedJWT.getClaim("phone").asString();
        } catch (TokenExpiredException e) {
            return null;
        }
    }
}
