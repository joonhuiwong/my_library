package com.joonhuiwong.mylibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.utils.Utils;

public class AddEditBookActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID =
            "com.joonhuiwong.mylibrary.activities.EXTRA_BOOK_ID";
    public static final String EXTRA_BOOK_NAME =
            "com.joonhuiwong.mylibrary.activities.EXTRA_BOOK_NAME";
    public static final String EXTRA_AUTHOR =
            "com.joonhuiwong.mylibrary.activities.EXTRA_AUTHOR";
    public static final String EXTRA_PAGES =
            "com.joonhuiwong.mylibrary.activities.EXTRA_PAGES";
    public static final String EXTRA_SHORT_DESCRIPTION =
            "com.joonhuiwong.mylibrary.activities.EXTRA_SHORT_DESCRIPTION";
    public static final String EXTRA_LONG_DESCRIPTION =
            "com.joonhuiwong.mylibrary.activities.EXTRA_LONG_DESCRIPTION";
    public static final String EXTRA_IMAGE_URL =
            "com.joonhuiwong.mylibrary.activities.EXTRA_IMAGE_URL";
    public static final String EXTRA_IS_CURRENTLY_READING =
            "com.joonhuiwong.mylibrary.activities.EXTRA_IS_CURRENTLY_READING";
    public static final String EXTRA_IS_WANT_TO_READ =
            "com.joonhuiwong.mylibrary.activities.EXTRA_IS_WANT_TO_READ";
    public static final String EXTRA_IS_ALREADY_READ =
            "com.joonhuiwong.mylibrary.activities.EXTRA_IS_ALREADY_READ";
    public static final String EXTRA_IS_FAVORITE =
            "com.joonhuiwong.mylibrary.activities.EXTRA_IS_FAVORITE";

    private EditText editTextBookName;
    private EditText editTextAuthor;
    private EditText editTextPages;
    private EditText editTextShortDescription;
    private EditText editTextLongDescription;
    private EditText editTextImageUrl;

    //TODO: Proper Image handling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        editTextBookName = findViewById(R.id.editTextBookName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextPages = findViewById(R.id.editTextPages);
        editTextShortDescription = findViewById(R.id.editTextShortDescription);
        editTextLongDescription = findViewById(R.id.editTextLongDescription);
        editTextImageUrl = findViewById(R.id.editTextImageUrl);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_BOOK_ID)) {
            setTitle("Edit Book");
            editTextBookName.setText(intent.getStringExtra(EXTRA_BOOK_NAME));
            editTextAuthor.setText(intent.getStringExtra(EXTRA_AUTHOR));
            editTextPages.setText(String.valueOf(intent.getIntExtra(EXTRA_PAGES, -1)));
            editTextShortDescription.setText(intent.getStringExtra(EXTRA_SHORT_DESCRIPTION));
            editTextLongDescription.setText(intent.getStringExtra(EXTRA_LONG_DESCRIPTION));
            editTextImageUrl.setText(intent.getStringExtra(EXTRA_IMAGE_URL));
        }

    }

    private void saveBook() {
        String bookName = editTextBookName.getText().toString();
        String author = editTextAuthor.getText().toString();
        String pagesString = editTextPages.getText().toString();
        String shortDescription = editTextShortDescription.getText().toString();
        String longDescription = editTextLongDescription.getText().toString();

        //TODO: Add proper imageURL support - maybe with gallery or camera?
        String imageUrl = editTextImageUrl.getText().toString();

        // Validate Inputs
        if (bookName.trim().isEmpty() ||
                author.trim().isEmpty() ||
                pagesString.trim().isEmpty()) {
            Toast.makeText(this, "Please insert required fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Utils.getInstance().isDigit(pagesString)) {
            Toast.makeText(this, "Pages has to be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_BOOK_NAME, bookName);
        data.putExtra(EXTRA_AUTHOR, author);
        data.putExtra(EXTRA_PAGES, Integer.parseInt(pagesString));
        data.putExtra(EXTRA_SHORT_DESCRIPTION, shortDescription);
        data.putExtra(EXTRA_LONG_DESCRIPTION, longDescription);

        data.putExtra(EXTRA_IMAGE_URL, imageUrl);

        // Reinsert bookId for Edit/Update cases
        int id = getIntent().getIntExtra(EXTRA_BOOK_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_BOOK_ID, id);

            // Maintain existing flags
            data.putExtra(EXTRA_IS_CURRENTLY_READING, getIntent().getBooleanExtra(EXTRA_IS_CURRENTLY_READING, false));
            data.putExtra(EXTRA_IS_WANT_TO_READ, getIntent().getBooleanExtra(EXTRA_IS_WANT_TO_READ, false));
            data.putExtra(EXTRA_IS_ALREADY_READ, getIntent().getBooleanExtra(EXTRA_IS_ALREADY_READ, false));
            data.putExtra(EXTRA_IS_FAVORITE, getIntent().getBooleanExtra(EXTRA_IS_FAVORITE, false));
        }
        data.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_book:
                saveBook();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}