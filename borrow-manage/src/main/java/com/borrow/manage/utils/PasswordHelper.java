package com.borrow.manage.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;


public class PasswordHelper {
    private final static Logger logger = LoggerFactory.getLogger(PasswordHelper.class);

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 2000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 24;
    private static final String FIX_PASSHASH_SEED = "PERMISSION#ADMIN";

    public static final String PERMISSION_SESSION_KEY ="#permission_session#";

    public static String encryptPassword(String password) {
        byte[] salt = getNextSalt();
        byte[] hash = getPasswordHash(password, salt);
        return byteToString(salt) + ":" + byteToString(hash);
    }


    public static boolean verifyPassword(String password, String passwordHash) {
        if(passwordHash == null) {
            logger.warn("empty password hash");
            return false;
        }

        String[] ss = passwordHash.split(":");
        if (ss.length != 2) {
            logger.warn("invalid password hash");
            return false;
        }
        byte[] salt = stringToByte(ss[0]);
        byte[] expectedHash = stringToByte(ss[1]);
        byte[] pwdHash = getPasswordHash(password, salt);

        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    private  static byte[] getPasswordHash(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec((password + FIX_PASSHASH_SEED).toCharArray(), salt,
                ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    private static byte[] getNextSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private static String byteToString(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    private static byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }


    public static String createPermissionSession(String message) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(PERMISSION_SESSION_KEY.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKey);
            String str = Base64.encodeBase64String(sha256Hmac.doFinal(message.getBytes()));
            return str;
        } catch (Exception ignored) {
            return "";
        }
    }

}
