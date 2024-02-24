package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personnelcuisine.Activity.MainActivity;
import com.example.personnelcuisine.R;

public class SupportActivity extends AppCompatActivity {

    Button BtnBack;
    ImageView callBtn, messageBtn;
    TextView Txtcall, TxtEmail, TxtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initView();

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, MainActivity.class));
            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = Txtcall.getText().toString();
                openMessagingApp(phoneNumber);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = TxtAddress.getText().toString();
                openDialer(phoneNumber);
            }
        });
    }

    private void initView() {
        BtnBack = findViewById(R.id.btnBack);
        callBtn = findViewById(R.id.CallBtn);
        messageBtn = findViewById(R.id.MessageBtn);
        Txtcall = findViewById(R.id.txtCall);
        TxtEmail = findViewById(R.id.txtEmail);
        TxtAddress = findViewById(R.id.txtAddress);
    }

    private void openDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void openMessagingApp(String phoneNumber) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(intent);
    }
}
