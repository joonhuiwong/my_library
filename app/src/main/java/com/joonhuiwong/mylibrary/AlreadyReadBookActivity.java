package com.joonhuiwong.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlreadyReadBookActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "alreadyReadBooks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.alreadyReadBookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, ACTIVITY_NAME);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAlreadyReadBooks());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}