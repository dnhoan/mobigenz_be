package com.api.mobigenz_be.Utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtils extends BCryptPasswordEncoder {

    public static String encrypt(String origin) {
        return BCrypt.hashpw(origin, BCrypt.gensalt());
    }

    public static boolean check(String origin, String encrypted) {
        return BCrypt.checkpw(origin, encrypted);
    }


}
