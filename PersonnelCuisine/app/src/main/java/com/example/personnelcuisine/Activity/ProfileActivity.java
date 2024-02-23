package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personnelcuisine.R;

import Helper.DbHelper;

public class ProfileActivity extends AppCompatActivity {
    ImageView picuser;
    TextView TxtName,TxtEmail,TxtAddress;
    Button homeBtn,updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();

        long userId = getIntent().getLongExtra("user_Id", -1);
        UserDataModel user=getuserData(userId);
        TxtName.setText(user.getName());
        TxtEmail.setText(user.getEmail());
        TxtAddress.setText(user.getAddress());
        if (user.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            picuser.setImageBitmap(bitmap);
        }

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,updateuser.class);
                intent.putExtra("id",userId);
                startActivity(intent);
            }
        });


    }

    public void initView() {
        picuser=findViewById(R.id.picuser);
        TxtName=findViewById(R.id.txtName);
        TxtEmail=findViewById(R.id.txtEmail);
        TxtAddress=findViewById(R.id.txtAddress);
        homeBtn=findViewById(R.id.HomeBtn);
        updateBtn=findViewById(R.id.UpdateBtn);

    }

    private UserDataModel getuserData(long itemId) {

        DbHelper dbHelper = new DbHelper(this);

        return dbHelper.getUserById(itemId);

    }
}