package com.fewok.dsl.util;

import lombok.Getter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

/**
 * Basic Access Authentication
 * wiki:https://en.wikipedia.org/wiki/Basic_access_authentication
 * @author notreami on 17/9/15.
 */
public class BasicAuth {
    private static final String MWS = "MWS";
    private static final String SPACE = " ";
    private static final String LF = "\n";
    private static final String CO = ":";
    private static final String ENCRYPT_FORMAT = "%s" + SPACE + "%s" + LF + "%s";
    private static final String AUTH_FORMAT = MWS + SPACE + "%s" + CO + "%s";
    private static final String SIGN_ALGORITHM = "HmacSHA1";
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final ZoneId TIME_ZONE = ZoneOffset.UTC;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    private BasicAuth() {

    }

    public static Data createBasicAuthData(String clientId, String clientSecret, String method, String requestURI) throws NoSuchAlgorithmException, InvalidKeyException {
        String authDate = ZonedDateTime.now(TIME_ZONE).format(TIME_FORMATTER);
        String sign = getHmacSHA1(clientSecret, method, requestURI, authDate);
        return Data.create(authDate, String.format(AUTH_FORMAT, clientId, sign));
    }

    public static boolean checkBasicAuthData(Data data, String clientSecret, String method, String requestURI) throws NoSuchAlgorithmException, InvalidKeyException {
        if (!data.getAuth().startsWith(MWS)) {
            return false;
        }
        String sign = getHmacSHA1(clientSecret, method, requestURI, data.getDate());
        return Objects.equals(sign, data.getAuth().split(CO)[1]);
    }

    private static String getHmacSHA1(String clientSecret, String method, String requestURI, String authDate) throws NoSuchAlgorithmException, InvalidKeyException {
        String sourceSign = String.format(ENCRYPT_FORMAT, method, requestURI, authDate);
        SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(CHARSET), SIGN_ALGORITHM);
        Mac mac = Mac.getInstance(SIGN_ALGORITHM);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(sourceSign.getBytes(CHARSET));
        return new String(Base64.getEncoder().encode(rawHmac), CHARSET);
    }

    public static String getClientKey(String authStr) {
        return authStr.split(SPACE)[1].split(CO)[0];
    }

    @Getter
    public static class Data {
        private String date;
        private String auth;

        private Data(String date, String auth) {
            this.date = date;
            this.auth = auth;
        }

        public static Data create(String date, String authorization) {
            return new Data(date, authorization);
        }
    }
}
