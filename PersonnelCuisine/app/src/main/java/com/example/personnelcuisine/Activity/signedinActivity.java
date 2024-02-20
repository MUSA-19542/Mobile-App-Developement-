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

import Helper.DbHelper;

public class signedinActivity extends AppCompatActivity {

    EditText EtEmail,EtPassword;
    String email,pass;
    Button SignIn,SignUp;
    DbHelper dbHelper;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signedin);

        dbHelper=new DbHelper(this);
        EtEmail=findViewById(R.id.EtEmail);
        EtPassword=findViewById(R.id.EtPassword);
        SignIn=findViewById(R.id.SigninBtn);
        SignUp=findViewById(R.id.btn_goto_sign_up);


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = EtEmail.getText().toString();
                pass = EtPassword.getText().toString();

                if (dbHelper.signIn(email, pass)) {
                    SharedPreferences sharepref = getSharedPreferences("MyPrefss", MODE_PRIVATE);
                  //  sharepref.getBoolean("hasPassedThroughMain", true);

                    // Create intent to start MainActivity
                    Intent intent = new Intent(signedinActivity.this, MainActivity.class);
                    editor = sharepref.edit();
                    // Put the email as an extra in the intent
                   editor.putString("email", email);
                    editor.apply();
                    finish();

                    // Start MainActivity with the intent
                    startActivity(intent);
                } else {
                    Toast.makeText(signedinActivity.this, "Name And Password is Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signedinActivity.this,signupActivity.class));
            }
        });


    }
}