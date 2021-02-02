package com.joonhuiwong.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY = "allBooks";
    private static final String ALREADY_READ_BOOKS_KEY = "alreadyReadBooks";
    private static final String WANT_TO_READ_BOOKS_KEY = "wantToReadBooks";
    private static final String CURRENTLY_READING_BOOKS_KEY = "currentlyReadingBooks";
    private static final String FAVORITE_BOOKS_KEY = "favoriteBooks";

    private static Utils instance;

    private final SharedPreferences sharedPreferences;

    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (null == getAllBooks()) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()) {
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if (null == getWantToReadBooks()) {
            editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if (null == getCurrentlyReadingBooks()) {
            editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if (null == getFavoriteBooks()) {
            editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    public static Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS_KEY, null), type);
    }

    private void initData() {
        //TODO: add initial data / utilize Database & load data for all tables

        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1, "The Wolf of Wall Street", "Jordan Belfort", 420,
                "https://images-na.ssl-images-amazon.com/images/I/91arek9tBiL.jpg",
                "A circus show.",
                "Blah blah blah."));
        books.add(new Book(2, "Meow", "John", 690,
                "https://images-na.ssl-images-amazon.com/images/I/91arek9tBiL.jpg",
                "Show me the money.",
                "Blah blah blah."));

        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Serialize to Json
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit(); // apply vs commit (background vs foreground)
    }

    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (null != books) {
            for (Book b : books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    public boolean addToAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS_KEY);
                editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantToRead(Book book) {
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS_KEY);
                editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS_KEY);
                editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavorite(Book book) {
        ArrayList<Book> books = getFavoriteBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS_KEY);
                editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToLibrary(Book book) {
        ArrayList<Book> books = getAllBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALL_BOOKS_KEY);
                editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromAllBooks(Book book) {
        ArrayList<Book> books = getAllBooks();
        if (null != books) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALL_BOOKS_KEY);
                        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS_KEY);
                        editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book) {
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS_KEY);
                        editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS_KEY);
                        editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromFavorite(Book book) {
        ArrayList<Book> books = getFavoriteBooks();
        if (null != books) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS_KEY);
                        editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
