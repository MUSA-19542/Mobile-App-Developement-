package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personnelcuisine.R;

public class LoginBypass extends AppCompatActivity {
    String name, pass, Strname, Strpass;
    Button confirmBtn;
    EditText etNamec, etPassc;
    SharedPreferences sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bypass);


        confirmBtn = findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
    }


    public void initView() {

        Intent intent = getIntent();
        sharedpref = getSharedPreferences("MyPref", MODE_PRIVATE);
        name = sharedpref.getString("name", "");
        pass = sharedpref.getString("pass", "");

        Toast.makeText(LoginBypass.this, "Passed name = "+name+" Passed password = "+pass, Toast.LENGTH_LONG).show();



        try {
            etPassc = findViewById(R.id.etPasswordconfirm);
            etNamec = findViewById(R.id.etNameconfirm);


            if (intent != null) {
                Strname = etNamec.getText().toString();
                Strpass = etPassc.getText().toString();
                Toast.makeText(LoginBypass.this, "Local  = "+Strname+" Local password = "+Strpass, Toast.LENGTH_LONG).show();

            }
            if (Strname.equals("") || Strpass.equals("")) {
                Toast.makeText(LoginBypass.this, "Please enter both name and password", Toast.LENGTH_LONG).show();
                return;
            }

            if (Strpass.equals(pass) && Strname.equals(name)) {
                startActivity(new Intent(LoginBypass.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginBypass.this, "Imposter! Please enter valid data", Toast.LENGTH_LONG).show();
            }


        } catch (Exception ex) {
            Toast.makeText(this, "Exception == " + ex, Toast.LENGTH_LONG).show();
        }

    }
}