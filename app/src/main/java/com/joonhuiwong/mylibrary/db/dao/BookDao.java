package com.joonhuiwong.mylibrary.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joonhuiwong.mylibrary.db.entity.BookEntity;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(BookEntity book);

    @Update
    void update(BookEntity book);

    @Delete
    void delete(BookEntity book);

    @Query("DELETE FROM book_table")
    void deleteAllBooks();

    @Query("SELECT * FROM book_table ORDER BY name ASC")
    LiveData<List<BookEntity>> getAllBooks();

    @Query("SELECT * FROM book_table WHERE id = :bookId")
    LiveData<BookEntity> getBook(int bookId);

    @Query("SELECT * FROM book_table WHERE id = :bookId")
    BookEntity getBookSync(int bookId);
}
