package com.joonhuiwong.mylibrary.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.joonhuiwong.mylibrary.db.BookDatabase;
import com.joonhuiwong.mylibrary.db.dao.BookDao;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;

import java.util.List;

public class BookRepository {

    private final BookDao bookDao;
    private final LiveData<List<BookEntity>> allBooks;
    private final LiveData<List<BookEntity>> currentlyReadingBooks;
    private final LiveData<List<BookEntity>> alreadyReadBooks;
    private final LiveData<List<BookEntity>> wantToReadBooks;
    private final LiveData<List<BookEntity>> favoriteBooks;

    public BookRepository(Application application) {
        BookDatabase database = BookDatabase.getInstance(application);
        bookDao = database.bookDao();
        allBooks = bookDao.getAllBooks();
        currentlyReadingBooks = bookDao.getAllCurrentlyReadingBooks();
        alreadyReadBooks = bookDao.getAllAlreadyReadBooks();
        wantToReadBooks = bookDao.getAllWantToReadBooks();
        favoriteBooks = bookDao.getAllFavoriteBooks();
    }

    public void insert(BookEntity book) {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.insert(book));
    }

    public void update(BookEntity book) {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.update(book));
    }

    public void delete(BookEntity book) {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.delete(book));
    }

    public void deleteAllBooks() {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.deleteAllBooks());
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return allBooks;
    }

    public LiveData<List<BookEntity>> getCurrentlyReadingBooks() {
        return currentlyReadingBooks;
    }

    public LiveData<List<BookEntity>> getAlreadyReadBooks() {
        return alreadyReadBooks;
    }

    public LiveData<List<BookEntity>> getWantToReadBooks() {
        return wantToReadBooks;
    }

    public LiveData<List<BookEntity>> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void deleteBookById(int bookId) {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.deleteBookById(bookId));
    }

    public LiveData<BookEntity> getBook(int bookId) {
        return bookDao.getBook(bookId);
    }

    public BookEntity getBookSync(int bookId) {
        return bookDao.getBookSync(bookId);
    }


}
