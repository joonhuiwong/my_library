package com.joonhuiwong.mylibrary.utils;

public class Utils {

    private static final String ALL_BOOKS_KEY = "allBooks";
    private static final String ALREADY_READ_BOOKS_KEY = "alreadyReadBooks";
    private static final String WANT_TO_READ_BOOKS_KEY = "wantToReadBooks";
    private static final String CURRENTLY_READING_BOOKS_KEY = "currentlyReadingBooks";
    private static final String FAVORITE_BOOKS_KEY = "favoriteBooks";

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
            int number = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
