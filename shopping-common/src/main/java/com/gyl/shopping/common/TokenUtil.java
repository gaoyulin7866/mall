package com.gyl.shopping.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * @Author: gyl
 * @Description: 生成token
 */
public class TokenUtil {
    public static String generateToken (Integer userId, String username, Integer role, String emailAddress) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        OffsetDateTime offsetDateTime = OffsetDateTime.now().plusMinutes(10);

        return JWT.create()
                .withKeyId(String.valueOf(userId))
                .withIssuer("签发者")
                .withExpiresAt(Date.from(offsetDateTime.toInstant()))
                .sign(algorithm);
    }

    public static Integer verifyToken(String token)  {
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            String id = verify.getKeyId();
            return Integer.valueOf(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.TOKEN_ERROR);
        }
    }

}
