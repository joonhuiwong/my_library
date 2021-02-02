package com.joonhuiwong.mylibrary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllBooksActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "allBooks";

    private RecyclerView allBooksRecyclerView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        adapter = new BookRecViewAdapter(this, ACTIVITY_NAME);
        allBooksRecyclerView = findViewById(R.id.allBooksRecyclerView);

        allBooksRecyclerView.setAdapter(adapter);
        allBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getAllBooks());
    }
}