package com.joonhuiwong.mylibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;
import com.joonhuiwong.mylibrary.utils.Utils;
import com.joonhuiwong.mylibrary.viewmodel.BookViewModel;

import java.util.List;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";
    public static final int EDIT_BOOK_REQUEST = 2;

    private TextView txtAuthorFull, txtBookNameFull, txtPagesFull, txtShortDescriptionFull, txtLongDescriptionFull;
    private Button btnAddToCurrentlyReading, btnAddToAlreadyRead, btnAddToWantToRead, btnAddToFavorites;
    private ImageView imgBookFull;

    private BookViewModel bookViewModel;

    private int bookId = -1;
    private BookEntity currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // FAB
        FloatingActionButton fabEditBook = findViewById(R.id.fab_edit_book);
        fabEditBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookActivity.this, AddEditBookActivity.class);
            intent.putExtra(AddEditBookActivity.EXTRA_BOOK_ID, currentBook.getId());

            intent.putExtra(AddEditBookActivity.EXTRA_BOOK_NAME, currentBook.getName());
            intent.putExtra(AddEditBookActivity.EXTRA_AUTHOR, currentBook.getAuthor());
            intent.putExtra(AddEditBookActivity.EXTRA_PAGES, currentBook.getPages());
            intent.putExtra(AddEditBookActivity.EXTRA_SHORT_DESCRIPTION, currentBook.getShortDesc());
            intent.putExtra(AddEditBookActivity.EXTRA_LONG_DESCRIPTION, currentBook.getLongDesc());

            intent.putExtra(AddEditBookActivity.EXTRA_IMAGE_URL, currentBook.getImageUrl());

            // Pass to maintain flags
            intent.putExtra(AddEditBookActivity.EXTRA_IS_CURRENTLY_READING, currentBook.isCurrentlyReading());
            intent.putExtra(AddEditBookActivity.EXTRA_IS_WANT_TO_READ, currentBook.isWantToRead());
            intent.putExtra(AddEditBookActivity.EXTRA_IS_ALREADY_READ, currentBook.isAlreadyRead());
            intent.putExtra(AddEditBookActivity.EXTRA_IS_FAVORITE, currentBook.isFavorite());

            startActivityForResult(intent, EDIT_BOOK_REQUEST);
        });

        initViews();

        Intent intent = getIntent();
        if (null != intent) {
            this.bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                bookViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BookViewModel.class);
                bookViewModel.getAllBooks().observe(this, books -> {
                    currentBook = getIncomingBook(bookId, books);
                    if (currentBook != null) {
                        setData(currentBook);
                        setTitle(currentBook.getName());

                        handleAlreadyRead(currentBook);
                        handleWantToReadBooks(currentBook);
                        handleCurrentlyReadingBooks(currentBook);
                        handleFavoriteBooks(currentBook);

                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, currentBook.getName());
                        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, currentBook.getId());
                        Utils.getInstance(this).logEvent(Utils.OPEN_BOOK_DETAIL_KEY, bundle);
                    }
                });
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
     * Add/Remove Flag the book object as Currently Reading (isCurrentlyReading)
     *
     * @param book BookEntity Object to flag
     */
    private void handleCurrentlyReadingBooks(BookEntity book) {
        if (book.isCurrentlyReading()) {
            btnAddToCurrentlyReading.setBackgroundColor(getResources().getColor(R.color.button_disabled_bg));
            btnAddToCurrentlyReading.setTextColor(getResources().getColor(R.color.button_disabled_text));
            btnAddToCurrentlyReading.setText(R.string.is_reading_button_label);

            btnAddToCurrentlyReading.setOnClickListener(v -> {
                book.setCurrentlyReading(false);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                handleCurrentlyReadingBooks(book);
            });
        } else {
            btnAddToCurrentlyReading.setBackgroundColor(getResources().getColor(R.color.purple_500));
            btnAddToCurrentlyReading.setTextColor(getResources().getColor(R.color.white));
            btnAddToCurrentlyReading.setText(R.string.add_to_reading_button_label);

            btnAddToCurrentlyReading.setOnClickListener(v -> {
                book.setCurrentlyReading(true);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Book Added to Currently Reading", Toast.LENGTH_SHORT).show();
                handleCurrentlyReadingBooks(book);
            });
        }
    }

    /**
     * Enable and Disable Button
     * Add/Remove Flag the book object as Already Read (isAlreadyRead)
     *
     * @param book BookEntity Object to flag
     */
    private void handleAlreadyRead(BookEntity book) {
        if (book.isAlreadyRead()) {
            btnAddToAlreadyRead.setBackgroundColor(getResources().getColor(R.color.button_disabled_bg));
            btnAddToAlreadyRead.setTextColor(getResources().getColor(R.color.button_disabled_text));
            btnAddToAlreadyRead.setText(R.string.is_already_reading_button_label);

            btnAddToAlreadyRead.setOnClickListener(v -> {
                book.setAlreadyRead(false);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                handleAlreadyRead(book);
            });
        } else {
            btnAddToAlreadyRead.setBackgroundColor(getResources().getColor(R.color.purple_500));
            btnAddToAlreadyRead.setTextColor(getResources().getColor(R.color.white));
            btnAddToAlreadyRead.setText(R.string.add_to_already_read_button_label);

            btnAddToAlreadyRead.setOnClickListener(v -> {
                book.setAlreadyRead(true);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Book added to Already Read", Toast.LENGTH_SHORT).show();
                handleAlreadyRead(book);
            });
        }
    }

    /**
     * Enable and Disable Button
     * Add/Remove Flag the book object as Wishlisted (isWantToRead)
     *
     * @param book BookEntity Object to flag
     */
    private void handleWantToReadBooks(BookEntity book) {
        if (book.isWantToRead()) {
            btnAddToWantToRead.setBackgroundColor(getResources().getColor(R.color.button_disabled_bg));
            btnAddToWantToRead.setTextColor(getResources().getColor(R.color.button_disabled_text));
            btnAddToWantToRead.setText(R.string.is_want_to_read_button_label);

            btnAddToWantToRead.setOnClickListener(v -> {
                book.setWantToRead(false);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                handleWantToReadBooks(book);
            });
        } else {
            btnAddToWantToRead.setBackgroundColor(getResources().getColor(R.color.purple_500));
            btnAddToWantToRead.setTextColor(getResources().getColor(R.color.white));
            btnAddToWantToRead.setText(R.string.add_to_wishlist_button_label);

            btnAddToWantToRead.setOnClickListener(v -> {
                book.setWantToRead(true);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Book added to Wishlist", Toast.LENGTH_SHORT).show();
                handleWantToReadBooks(book);
            });
        }
    }

    /**
     * Enable and Disable Button
     * Add/Remove Flag the book object as Favorited (isFavorite)
     *
     * @param book BookEntity Object to flag
     */
    private void handleFavoriteBooks(BookEntity book) {
        if (book.isFavorite()) {
            btnAddToFavorites.setBackgroundColor(getResources().getColor(R.color.button_disabled_bg));
            btnAddToFavorites.setTextColor(getResources().getColor(R.color.button_disabled_text));
            btnAddToFavorites.setText(R.string.is_favorite_button_label);

            btnAddToFavorites.setOnClickListener(v -> {
                book.setFavorite(false);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                handleFavoriteBooks(book);
            });
        } else {
            btnAddToFavorites.setBackgroundColor(getResources().getColor(R.color.purple_500));
            btnAddToFavorites.setTextColor(getResources().getColor(R.color.white));
            btnAddToFavorites.setText(R.string.add_to_favorites_button_label);

            btnAddToFavorites.setOnClickListener(v -> {
                book.setFavorite(true);
                bookViewModel.update(book);
                Toast.makeText(BookActivity.this, "Book Added to Favorites", Toast.LENGTH_SHORT).show();
                handleFavoriteBooks(book);
            });
        }
    }

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
                    .error(R.mipmap.booklogo)
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

    private boolean deleteBook() {
        if (bookId != -1) {
            bookViewModel.deleteBookById(bookId);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete_book:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this book?");
                builder.setNegativeButton("No", (dialog, which) -> {
                });
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    if (deleteBook()) {
                        onBackPressed();
                        Toast.makeText(this, "Book Deleted.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to delete book. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_BOOK_REQUEST && resultCode == RESULT_OK) {
            int bookId = data.getIntExtra(AddEditBookActivity.EXTRA_BOOK_ID, -1);
            if (bookId != -1) {

                String bookName = data.getStringExtra(AddEditBookActivity.EXTRA_BOOK_NAME);
                String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
                int pages = data.getIntExtra(AddEditBookActivity.EXTRA_PAGES, -1);
                String shortDescription = data.getStringExtra(AddEditBookActivity.EXTRA_SHORT_DESCRIPTION);
                String longDescription = data.getStringExtra(AddEditBookActivity.EXTRA_LONG_DESCRIPTION);
                String imageUrl = data.getStringExtra(AddEditBookActivity.EXTRA_IMAGE_URL);

                BookEntity book = new BookEntity(bookName, author, pages, imageUrl, shortDescription, longDescription);
                book.setId(bookId);
                book.setCurrentlyReading(data.getBooleanExtra(AddEditBookActivity.EXTRA_IS_CURRENTLY_READING, false));
                book.setAlreadyRead(data.getBooleanExtra(AddEditBookActivity.EXTRA_IS_ALREADY_READ, false));
                book.setWantToRead(data.getBooleanExtra(AddEditBookActivity.EXTRA_IS_WANT_TO_READ, false));
                book.setFavorite(data.getBooleanExtra(AddEditBookActivity.EXTRA_IS_FAVORITE, false));
                bookViewModel.update(book);

                Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Book cannot be updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}