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
    private LiveData<List<BookEntity>> currentlyReadingBooks;
    private LiveData<List<BookEntity>> alreadyReadBooks;
    private LiveData<List<BookEntity>> wantToReadBooks;
    private LiveData<List<BookEntity>> favoriteBooks;

    public BookRepository(Application application) {
        BookDatabase database = BookDatabase.getInstance(application);
        bookDao = database.bookDao();
        allBooks = bookDao.getAllBooks();
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

    //TODO: Sync or Async?
    public LiveData<BookEntity> getBook(int bookId) {
        return bookDao.getBook(bookId);
    }

    public BookEntity getBookSync(int bookId) {
        return bookDao.getBookSync(bookId);
    }
}
