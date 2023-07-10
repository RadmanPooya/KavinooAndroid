package com.kavinoo.kavinoo.utils;

public class TokenContainer {

    private static String token;

    public static void updateToken(String token) {
      TokenContainer.token = token;
    }

    public static String getToken() {

        return token;
    }
}
