package com.gyl.shopping.common;


import org.apache.tomcat.util.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQk33iNdA8Iey7J6XrBsidqn6u8EDLWPHsfEUgLQ3qiTikhPKDTzZkpAfU/O0x6NvSKa7Dp0+uqWT3vnW1De0+3u8mCYdVfOdH94VG4xg5U5UrRJei8HhPiXuvKQ+6NBtebCCW5adZ4pBgOiU14cJLhVmm+dYiLo3IDD5LqrlomQIDAQAB";
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJCTfeI10Dwh7LsnpesGyJ2qfq7wQMtY8ex8RSAtDeqJOKSE8oNPNmSkB9T87THo29IprsOnT66pZPe+dbUN7T7e7yYJh1V850f3hUbjGDlTlStEl6LweE+Je68pD7o0G15sIJblp1nikGA6JTXhwkuFWab51iIujcgMPkuquWiZAgMBAAECgYA1UT9mciQWWQh9yNRmhXzssFjB2TZ8B5RIe1fe0t7D9NEf0yvAgzDzEo8U3CX5dv/CVL7vxr8bEbt7phCwsa8hJiLEOr7hLZaJzXVTbvfqb91oCZGNkqDQ3NJfGBMVgUmltEYF2Bbk3U0NDyat+Gu54tRd2OH+adJYKsD0XYeDBQJBAN5FE8E04A4FA1q8mQbVTSVJDYIEJwOrdC0r3iZ7za5CyXGk+br8pFalRePFaksRGdN32+mYhDKVNrNHspAObVMCQQCmhBsD+xiWrmpnrzeIfCW1cX8qRC3/RMkq0ACw3l6YedNFdN2Tb5WsRHmcbCI9y8mfLHiG/X1R+zHZKG67EKjjAkAmvAkGSY2mQ89i160fWLq5/bIh71FRPWbgnF15fWfJr4/lgyeWI4MMKn80g2nTrSZACQpE+jRHkGNY+OywWCNLAkEAli5nvztkfeJpDYK2b16pE/B9ZL2BTs3XMcnQFbU5VAPsTKSOgz8MmwZXOIE+kMWP3wPY4McXlC0eVGFnHUh1SQJAeAl3RPk+XbZDMYfPkStRJwocG9Ap+88mwTgR1I7uPzZ1aM84/WsQskiVMXv2SZLmMWvYtnhIKosL6IACp2AcDA==";

    public static RSAPublicKey getPublicKey() throws Exception {
        byte[] bytes = Base64.decodeBase64(PUBLIC_KEY);
        return (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
    }

    public static RSAPrivateKey getPrivateKey() throws Exception {
        byte[] bytes = Base64.decodeBase64(PRIVATE_KEY);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }
}
