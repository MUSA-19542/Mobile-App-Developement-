package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.personnelcuisine.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
     new Handler().postDelayed(() -> {
            Intent intent;


            //<=========================================================================Assignment 3=====================================================================================>
            //Login==>Bypass
            // Check if the user has already passed through the main activity
            SharedPreferences sharepref = getSharedPreferences("MyPref", MODE_PRIVATE);
            boolean hasPassedThroughMain = sharepref.getBoolean("hasPassedThroughMain", false);

            if (hasPassedThroughMain) {
                // If already passed through, go directly to DetailActivity
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {

                // If not passed through, go to MainActivity
                intent = new Intent(SplashActivity.this, signedinActivity.class);
            }

            startActivity(intent);
            finish();
        }, 2000);

    }
}