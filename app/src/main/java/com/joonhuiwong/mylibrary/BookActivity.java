package com.joonhuiwong.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtAuthorFull, txtBookNameFull, txtPagesFull, txtShortDescriptionFull, txtLongDescriptionFull;
    private Button btnAddToCurrentlyReading, btnAddToAlreadyRead, btnAddToWantToRead, btnAddToFavorites;
    private ImageView imgBookFull;

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
                Book incomingBook = Utils.getInstance().getBookById(bookId);
                if (incomingBook != null) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);

                    this.setTitle(incomingBook.getName());
                }
            }
        }
    }

    /**
     * Enable and Disable Button
     * Add the book to Already Read Book ArrayList
     *
     * @param book Book Object
     */
    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getAlreadyReadBooks();
        boolean existInAlreadyReadBooks = false;
        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existInAlreadyReadBooks = true;
                break;
            }
        }

        if (existInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(v -> {
                if (Utils.getInstance().addToAlreadyRead(book)) {
                    Toast.makeText(BookActivity.this, "Book Added to Already Read", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleWantToReadBooks(Book book) {
        ArrayList<Book> books = Utils.getWantToReadBooks();
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
                if (Utils.getInstance().addToWantToRead(book)) {
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
        ArrayList<Book> books = Utils.getCurrentlyReadingBooks();
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
                if (Utils.getInstance().addToCurrentlyReading(book)) {
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
        ArrayList<Book> books = Utils.getFavoriteBooks();
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
                if (Utils.getInstance().addToFavorite(book)) {
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, FavoriteBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookNameFull.setText(book.getName());
        txtAuthorFull.setText(book.getAuthor());
        txtPagesFull.setText(String.valueOf(book.getPages()));
        txtShortDescriptionFull.setText(book.getShortDesc());
        txtLongDescriptionFull.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(imgBookFull);
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