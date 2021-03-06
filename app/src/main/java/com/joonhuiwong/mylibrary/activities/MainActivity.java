package com.joonhuiwong.mylibrary.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks, btnAlreadyRead, btnWantToRead, btnCurrentlyReading, btnFavorite, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.VALUE, "Application Opened");
        Utils.getInstance(this).getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);

        setContentView(R.layout.activity_main);

        initViews();

        // Initialize Buttons for Page Navigation
        initButtonOnClickListener(MainActivity.this, btnAllBooks, AllBooksActivity.class);
        initButtonOnClickListener(MainActivity.this, btnAlreadyRead, AlreadyReadBookActivity.class);
        initButtonOnClickListener(MainActivity.this, btnWantToRead, WantToReadBookActivity.class);
        initButtonOnClickListener(MainActivity.this, btnCurrentlyReading, CurrentlyReadingBookActivity.class);
        initButtonOnClickListener(MainActivity.this, btnFavorite, FavoriteBookActivity.class);

        btnAbout.setOnClickListener(v -> {
            Utils.getInstance(MainActivity.this).logItemSelected("About Button");
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage("Designed by Joon Hui Wong following and inspired by free guides & courses online.\n" +
                    "This application is purely for educational purposes.");
            builder.setPositiveButton("Github", (dialog, which) -> {
                Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                intent.putExtra("url", "https://github.com/joonhuiwong");
                startActivity(intent);
            });
            builder.setCancelable(true);
            builder.create().show();
        });

    }

    /**
     * Generic Method to set OnClickListeners to navigate from one Activity to Another
     *
     * @param mContext
     * @param button
     * @param mClass
     */
    private void initButtonOnClickListener(Context mContext, Button button, Class<?> mClass) {
        button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("source", this.getClass().getSimpleName());
            Utils.getInstance(MainActivity.this).logScreenView(mClass.getSimpleName(), bundle);
            Intent intent = new Intent(mContext, mClass);
            startActivity(intent);
        });
    }

    /**
     * Initializing the Views of the Activity that are interact-able
     */
    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnWantToRead = findViewById(R.id.btnWantToRead);
        btnCurrentlyReading = findViewById(R.id.btnCurrentlyReading);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnAbout = findViewById(R.id.btnAbout);
    }
}