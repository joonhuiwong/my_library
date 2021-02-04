package com.joonhuiwong.mylibrary.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.joonhuiwong.mylibrary.db.dao.BookDao;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BookEntity.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static BookDatabase instance; // Singleton
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("RoomDatabase.Callback", "initData: Start");
            databaseWriteExecutor.execute(() -> {
                BookDao bookDao = instance.bookDao();
                bookDao.deleteAllBooks();

                // Insert Init. Data
                bookDao.insert(new BookEntity("Test Book",
                        "John Doe",
                        420,
                        "https://images-na.ssl-images-amazon.com/images/I/91arek9tBiL.jpg",
                        "This is my short description",
                        "This is my long description."));
                bookDao.insert(new BookEntity("Where the Crawdads Sing",
                        "Delia Owens",
                        384,
                        "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4721/9781472154668.jpg",
                        "*The multi-million copy bestseller*\n" +
                                "Soon to be a major film\n" +
                                "A Number One New York Times Bestseller\n" +
                                "\n" +
                                "'Painfully beautiful' New York Times\n" +
                                "'Unforgettable . . . as engrossing as it is moving' Daily Mail\n" +
                                "'A rare achievement' The Times\n" +
                                "'I can't even express how much I love this book!' Reese Witherspoon",
                        "For years, rumors of the 'Marsh Girl' have haunted Barkley Cove, a quiet town on the North Carolina coast. So in late 1969, when handsome Chase Andrews is found dead, the locals immediately suspect Kya Clark, the so-called Marsh Girl. But Kya is not what they say. Sensitive and intelligent, she has survived for years alone in the marsh that she calls home, finding friends in the gulls and lessons in the sand. Then the time comes when she yearns to be touched and loved. When two young men from town become intrigued by her wild beauty, Kya opens herself to a new life - until the unthinkable happens."));
                bookDao.insert(new BookEntity("Normal People",
                        "Sally Rooney",
                        288,
                        "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/5713/9780571334650.jpg",
                        "Normal People is a story of mutual fascination, friendship and love. It takes us from that first conversation to the years beyond, in the company of two people who try to stay apart but find they can't.",
                        "Connell and Marianne grow up in the same small town in the west of Ireland, but the similarities end there. In school, Connell is popular and well-liked, while Marianne is a loner. But when the two strike up a conversation - awkward but electrifying - something life-changing begins."));

                Log.d("RoomDatabase.Callback", "initData: Done");
            });

        }
    };

    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d("BookDatabase", "getInstance: Start");
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class, "book_database")
                    .fallbackToDestructiveMigration() // delete database if version increased. Good or not?
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract BookDao bookDao();
}
