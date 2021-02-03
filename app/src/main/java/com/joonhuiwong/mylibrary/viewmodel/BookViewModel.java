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

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
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

    public LiveData<List<BookEntity>> getAllBooks() {
        return allBooks;
    }

    public LiveData<BookEntity> getBook(int bookId) {
        return repository.getBook(bookId);
    }

    public BookEntity getBookSync(int bookId) {
        return repository.getBookSync(bookId);
    }
}
