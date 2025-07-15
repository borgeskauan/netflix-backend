package org.contoso.netflix.auth.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean verifyPassword(String password, String passwordHash) {
        if (password == null || passwordHash == null) {
            return false;
        }

        return passwordEncoder.matches(password, passwordHash);
    }
}
