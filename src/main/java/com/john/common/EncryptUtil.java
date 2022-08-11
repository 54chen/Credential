package com.john.common;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptUtil {
    private final static String PEPPER= "ThisISaPEPPER";
    public static String bcrypt(String password, String salt) {
        return BCrypt.hashpw(password, salt+PEPPER);
    }

    public static String genSalt(String originalString) {
        return BCrypt.gensalt();
    }

    public static boolean check(String plainText, String hashedValue) {
        return BCrypt.checkpw(plainText, hashedValue);
    }

}
