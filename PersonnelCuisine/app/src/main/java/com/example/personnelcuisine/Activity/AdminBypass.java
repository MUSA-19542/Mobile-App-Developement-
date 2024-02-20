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

public class AdminBypass extends AppCompatActivity {




    String name, pass, Strname, Strpass;
    Button confirmBtn;
    EditText etNamec, etPassc;
    SharedPreferences sharedpref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bypass);

        confirmBtn = findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
    }


    public void initView() {


        name = "test";
        pass = "test";


        try {
            etPassc = findViewById(R.id.etPasswordconfirm);
            etNamec = findViewById(R.id.etNameconfirm);

            Strname=etNamec.getText().toString();
            Strpass=etPassc.getText().toString();


            if (Strname.equals("") || Strpass.equals("")) {
                Toast.makeText(AdminBypass.this, "Please enter both name and password", Toast.LENGTH_LONG).show();
                return;
            }

            if (Strpass.equals(pass) && Strname.equals(name)) {
                startActivity(new Intent(AdminBypass.this, AdminActivity.class));
                finish();
            } else {
                Toast.makeText(AdminBypass.this, "Imposter! Please enter valid data", Toast.LENGTH_LONG).show();
            }


        } catch (Exception ex) {
            Toast.makeText(this, "Exception == " + ex, Toast.LENGTH_LONG).show();
        }

    }
}