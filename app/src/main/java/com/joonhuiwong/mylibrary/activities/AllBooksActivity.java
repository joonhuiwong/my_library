package com.joonhuiwong.mylibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.adapters.BookAdapter;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;
import com.joonhuiwong.mylibrary.viewmodel.BookViewModel;

import java.util.List;

public class AllBooksActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    public static final String ACTIVITY_NAME = "allBooks";

    private RecyclerView allBooksRecyclerView;
    private BookAdapter adapter;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllBooksActivity.this, AddBookActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        adapter = new BookAdapter(this, ACTIVITY_NAME);
        allBooksRecyclerView = findViewById(R.id.allBooksRecyclerView);

        allBooksRecyclerView.setAdapter(adapter);
        allBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                adapter.setBooks(books);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String bookName = data.getStringExtra(AddBookActivity.EXTRA_BOOK_NAME);
            String author = data.getStringExtra(AddBookActivity.EXTRA_AUTHOR);
            int pages = data.getIntExtra(AddBookActivity.EXTRA_PAGES, -1);
            String shortDescription = data.getStringExtra(AddBookActivity.EXTRA_SHORT_DESCRIPTION);
            String longDescription = data.getStringExtra(AddBookActivity.EXTRA_LONG_DESCRIPTION);
            String imageUrl = ""; //TODO: Implement this

            BookEntity book = new BookEntity(bookName, author, pages, "", shortDescription, longDescription);
            bookViewModel.insert(book);

            Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
        }
    }
}