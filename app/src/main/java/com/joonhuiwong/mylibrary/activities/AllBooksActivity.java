package com.joonhuiwong.mylibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.adapters.BookAdapter;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;
import com.joonhuiwong.mylibrary.viewmodel.BookViewModel;

public class AllBooksActivity extends AppCompatActivity {

    public static final int ADD_BOOK_REQUEST = 1;

    private RecyclerView allBooksRecyclerView;
    private BookAdapter adapter;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        // Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // FAB
        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(AllBooksActivity.this, AddEditBookActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST);
        });

        // Recycler View
        adapter = new BookAdapter(this);
        allBooksRecyclerView = findViewById(R.id.allBooksRecyclerView);
        allBooksRecyclerView.setAdapter(adapter);
        allBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // View Model
        bookViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, books -> adapter.setBooks(books));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK) {
            String bookName = data.getStringExtra(AddEditBookActivity.EXTRA_BOOK_NAME);
            String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
            int pages = data.getIntExtra(AddEditBookActivity.EXTRA_PAGES, -1);
            String shortDescription = data.getStringExtra(AddEditBookActivity.EXTRA_SHORT_DESCRIPTION);
            String longDescription = data.getStringExtra(AddEditBookActivity.EXTRA_LONG_DESCRIPTION);
            String imageUrl = data.getStringExtra(AddEditBookActivity.EXTRA_IMAGE_URL);

            BookEntity book = new BookEntity(bookName, author, pages, imageUrl, shortDescription, longDescription);
            bookViewModel.insert(book);

            Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.all_book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete_all_books_menu_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete all books?");
                builder.setNegativeButton("No", (dialog, which) -> {
                });
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    bookViewModel.deleteAllBooks();
                    Toast.makeText(this, "All Books Deleted.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}