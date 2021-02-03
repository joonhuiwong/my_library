package com.joonhuiwong.mylibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;
import com.joonhuiwong.mylibrary.viewmodel.BookViewModel;

import java.util.List;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtAuthorFull, txtBookNameFull, txtPagesFull, txtShortDescriptionFull, txtLongDescriptionFull;
    private Button btnAddToCurrentlyReading, btnAddToAlreadyRead, btnAddToWantToRead, btnAddToFavorites;
    private ImageView imgBookFull;

    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                bookViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BookViewModel.class);
                bookViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> books) {
                        BookEntity incomingBook = getIncomingBook(bookId, books);
                        if (incomingBook != null) {
                            setData(incomingBook);
                            setTitle(incomingBook.getName());

                            handleAlreadyRead(incomingBook);
                            //handleWantToReadBooks(incomingBook);
                            //handleCurrentlyReadingBooks(incomingBook);
                            //handleFavoriteBooks(incomingBook);
                        } else {
                            Toast.makeText(BookActivity.this, "Failed to Load Book for some reason. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                /*
                if (incomingBook != null) {
                    setData(incomingBook);

                    //handleAlreadyRead(incomingBook);
                    //handleWantToReadBooks(incomingBook);
                    //handleCurrentlyReadingBooks(incomingBook);
                    //handleFavoriteBooks(incomingBook);

                    this.setTitle(incomingBook.getName());
                }
                */
            }
        }
    }

    private BookEntity getIncomingBook(int bookId, List<BookEntity> books) {
        for (BookEntity b : books) {
            if (b.getId() == bookId) {
                return b;
            }
        }
        return null;
    }

    /**
     * Enable and Disable Button
     * Add the book to Already Read Book ArrayList
     *
     * @param book Book Object
     */
    private void handleAlreadyRead(BookEntity book) {
        //TODO: Convert these methods to RoomDatabase
        /*
        if (exists) {
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                    Toast.makeText(BookActivity.this, "Book Added to Want to Read", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, WantToReadBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
         */
    }

    /*
    private void handleWantToReadBooks(Book book) {
        ArrayList<Book> books = Utils.getInstance(this).getWantToReadBooks();
        boolean exists = false;
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                exists = true;
                break;
            }
        }

        if (exists) {
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                    Toast.makeText(BookActivity.this, "Book Added to Want to Read", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, WantToReadBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(Book book) {
        ArrayList<Book> books = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean exists = false;
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                exists = true;
                break;
            }
        }

        if (exists) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)) {
                    Toast.makeText(BookActivity.this, "Book Added to Currently Reading", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, CurrentlyReadingBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleFavoriteBooks(Book book) {
        ArrayList<Book> books = Utils.getInstance(this).getFavoriteBooks();
        boolean exists = false;
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                exists = true;
                break;
            }
        }

        if (exists) {
            btnAddToFavorites.setEnabled(false);
        } else {
            btnAddToFavorites.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToFavorite(book)) {
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, FavoriteBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
     */

    private void setData(BookEntity book) {
        txtBookNameFull.setText(book.getName());
        txtAuthorFull.setText(book.getAuthor());
        txtPagesFull.setText(String.valueOf(book.getPages()));
        txtShortDescriptionFull.setText(book.getShortDesc());
        txtLongDescriptionFull.setText(book.getLongDesc());

        if (book.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .asBitmap()
                    .load(R.mipmap.booklogo)
                    .into(imgBookFull);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(book.getImageUrl())
                    .into(imgBookFull);
        }
    }

    private void initViews() {
        txtAuthorFull = findViewById(R.id.txtAuthorFull);
        txtBookNameFull = findViewById(R.id.txtBookNameFull);
        txtPagesFull = findViewById(R.id.txtPagesFull);
        txtShortDescriptionFull = findViewById(R.id.txtShortDescriptionFull);
        txtLongDescriptionFull = findViewById(R.id.txtLongDescriptionFull);

        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites);

        imgBookFull = findViewById(R.id.imgBookFull);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}