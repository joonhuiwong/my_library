package com.joonhuiwong.mylibrary;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.joonhuiwong.mylibrary.db.BookDatabase;
import com.joonhuiwong.mylibrary.db.dao.BookDao;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BookInstrumentedTest {

    private BookDao bookDao;
    private BookDatabase bookDatabase;

    @Before
    public void useAppContext() {
        Context context = ApplicationProvider.getApplicationContext();
        bookDatabase = Room.inMemoryDatabaseBuilder(context, BookDatabase.class).build();
        bookDao = bookDatabase.bookDao();
    }

    @After
    public void closeDb() throws IOException {
        bookDatabase.close();
    }

    @Test
    public void addBookAndRetrieveInList() throws Exception {
        BookEntity book = new BookEntity("Book Title", "Author", 350, "", "Short Description", "Long Description");
        book.setId(100);
        bookDao.insert(book);
        BookEntity b = bookDao.getBookSync(100);
        assertEquals(b.getName(), book.getName());
    }

    @Test
    public void gettingDeletedBookTest() throws Exception {
        BookEntity book = new BookEntity("Book Title", "Author", 350, "", "Short Description", "Long Description");
        book.setId(100);
        bookDao.insert(book);
        bookDao.deleteAllBooks();
        assertNull(bookDao.getBookSync(100));
    }
}