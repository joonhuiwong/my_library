package com.joonhuiwong.mylibrary.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.joonhuiwong.mylibrary.db.entity.BookEntity;
import com.joonhuiwong.mylibrary.db.repository.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private final BookRepository repository;
    private final LiveData<List<BookEntity>> allBooks;
    private final LiveData<List<BookEntity>> currentlyReadingBooks;
    private final LiveData<List<BookEntity>> alreadyReadBooks;
    private final LiveData<List<BookEntity>> wantToReadBooks;
    private final LiveData<List<BookEntity>> favoriteBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
        currentlyReadingBooks = repository.getCurrentlyReadingBooks();
        alreadyReadBooks = repository.getAlreadyReadBooks();
        wantToReadBooks = repository.getWantToReadBooks();
        favoriteBooks = repository.getFavoriteBooks();
    }

    public void insert(BookEntity book) {
        repository.insert(book);
    }

    public void update(BookEntity book) {
        repository.update(book);
    }

    public void delete(BookEntity book) {
        repository.delete(book);
    }

    public void deleteAllBooks() {
        repository.deleteAllBooks();
    }

    public void deleteBookById(int bookId) {
        repository.deleteBookById(bookId);
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

}
