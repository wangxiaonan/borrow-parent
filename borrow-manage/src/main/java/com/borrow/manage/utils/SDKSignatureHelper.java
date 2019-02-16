package com.borrow.manage.utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SDKSignatureHelper {

    private static final Logger logger = LoggerFactory.getLogger(SDKSignatureHelper.class);

    private String accessKey;
    private String secretKey;

    private static final String SIGNATURE_VERSION = "v2";

    private static final String UTF8_ENC = "UTF-8";

    private List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD");

    public SDKSignatureHelper() {

    }

    public SDKSignatureHelper(String ak, String sk) {
        accessKey = ak;
        secretKey = sk;
    }
    
    public String generateSignature(String urlPath, String method, String queryParam, String body, int expireTime) {
        if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(secretKey)) {
            logger.error("invalid AK or SK");
            throw new RuntimeException("Invalid AK or SK");
        }

        if (StringUtils.isEmpty(urlPath)) {
            logger.error("urlPath can't be empty!");
            throw new RuntimeException("Empty url path");
        }

        if (!allowedMethods.contains(method)) {
            logger.error("{} isn't an allowed method", method);
            throw new RuntimeException("invalid request method");
        }

        String signature = "";
        try {
            // |v2-{AK}-{ExpireTime}|{SK}|
            StringBuffer sbSign = new StringBuffer(String.format("|%s-%s-%d|%s|", SIGNATURE_VERSION,
                    accessKey, expireTime, secretKey));

            // {UrlPath}|
            sbSign.append(UriUtils.decode(urlPath, UTF8_ENC)).append("|");

            // {Method}|
            sbSign.append(method).append("|");

            // {QueryParam}|
            if (!StringUtils.isEmpty(queryParam)) {
                List<String> qsArray = new ArrayList<String>();
                for (String kv: queryParam.split("&")) {
                    String[] t = kv.split("=");
                    if (t.length > 1) {
                        qsArray.add(String.format("%s=%s", UriUtils.decode(t[0], UTF8_ENC), UriUtils.decode(t[1], UTF8_ENC)));
                    } else {
                        qsArray.add(String.format("%s=", UriUtils.decode(t[0], UTF8_ENC)));
                    }
                }

                Collections.sort(qsArray);
                boolean first = true;
                for (String s: qsArray) {
                    if (first) {
                        first = false;
                    } else {
                        sbSign.append("&");
                    }
                    sbSign.append(s);
                }
            }
            sbSign.append("|");

            // {body}|
            if (!StringUtils.isEmpty(body)) {
                sbSign.append(body);
            }
            sbSign.append("|");
            logger.debug("signature contents: {}", sbSign.toString());

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(sbSign.toString().getBytes("UTF-8"));

            //  v2-{AK}-{ExpireTime}-{Signature}
            signature = String.format("%s-%s-%s-%s", SIGNATURE_VERSION, accessKey, expireTime,
                    new String(Hex.encodeHex(md5.digest())));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("failed to decode url or query path");
            throw new RuntimeException("Bad encoded url path or query string");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return signature;
    }

    public boolean verifySignature(String signature,String urlPath, String method, String queryParam, String body) {
        if (StringUtils.isEmpty(signature)) {
            logger.warn("null signature");
            return false;
        }

        try {
            String[] signatureParts = signature.split("-");

            if (signatureParts.length != 4) {
                logger.warn("invalid signature format");
                return false;
            }

            if (!SIGNATURE_VERSION.equals(signatureParts[0])) {
                logger.warn("invalid signature protocol version");
                return false;
            }

            int expireTime = Integer.parseInt(signatureParts[2]);
            if (expireTime < System.currentTimeMillis() / 1000) {
                logger.warn("expired signature");
                return false;
            }

            if (signature.equals(generateSignature(urlPath, method, queryParam, body, expireTime))) {
                return true;
            }
        } catch (Exception e) {
            logger.error("failed to parse signature '{}'", signature);
            e.printStackTrace();
        }

        return false;
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
