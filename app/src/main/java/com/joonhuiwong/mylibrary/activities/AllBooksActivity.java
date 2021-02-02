package com.joonhuiwong.mylibrary.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.adapters.BookRecViewAdapter;
import com.joonhuiwong.mylibrary.utils.Utils;

public class AllBooksActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "allBooks";

    private RecyclerView allBooksRecyclerView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new BookRecViewAdapter(this, ACTIVITY_NAME);
        allBooksRecyclerView = findViewById(R.id.allBooksRecyclerView);

        allBooksRecyclerView.setAdapter(adapter);
        allBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAllBooks());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}