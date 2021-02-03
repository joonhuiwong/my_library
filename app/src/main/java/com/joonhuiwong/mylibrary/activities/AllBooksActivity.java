package com.joonhuiwong.mylibrary.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.adapters.BookAdapter;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;
import com.joonhuiwong.mylibrary.viewmodel.BookViewModel;

import java.util.List;

public class AllBooksActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "allBooks";

    private RecyclerView allBooksRecyclerView;
    private BookAdapter adapter;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new BookAdapter(this, ACTIVITY_NAME);
        allBooksRecyclerView = findViewById(R.id.allBooksRecyclerView);

        allBooksRecyclerView.setAdapter(adapter);
        allBooksRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

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
}