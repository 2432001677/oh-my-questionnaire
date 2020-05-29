package cn.edu.zucc.ohmyquestionnaire.utils;

import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TokenUtil {
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 有效期24小时
    private static final String TOKEN_SECRET = "ohmq";

    public static String sign(UserLoginForm loginForm) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userName", loginForm.getUserName())
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            log.debug("verify access");
            log.debug(jwt.getClaim("userName").asString());
            log.debug("expiration at " + jwt.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
