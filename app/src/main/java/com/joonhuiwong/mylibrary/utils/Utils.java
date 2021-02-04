package com.joonhuiwong.mylibrary.utils;

public class Utils {

    private static Utils instance;

    private Utils() {

    }

    public static Utils getInstance() {
        if (null == instance) {
            instance = new Utils();
        }
        return instance;
    }

    public boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
