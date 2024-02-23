package com.example.personnelcuisine.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.personnelcuisine.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Helper.DbHelper;

public class updateuser extends AppCompatActivity {
    Button UpdateBtn;
    EditText EtName, EtEmail, EtAddress, EtPassword;
    CheckBox togglePassword;
    ImageView imageGallery;
    SharedPreferences sharepref;
    SharedPreferences.Editor editor;
    DbHelper dbHelper;

    byte[] imageByteArray;


    String name,email,pass,address;
    long userId;

    private final int Gallery_REQ_CODE=1000;
    Button imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
         userId = getIntent().getLongExtra("id", -1);

        initView();
        setPrevious(userId);

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentData();
                updatedataDataBase();
                Intent intent = new Intent(updateuser.this, signedinActivity.class);
                startActivity(intent);
                finish();


            }
        });



        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGalley = new Intent(Intent.ACTION_PICK);
                iGalley.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGalley,Gallery_REQ_CODE);

            }
        });
        togglePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Check OFF
                if (isChecked) {
                    EtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                //Check on
                else {
                    EtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }


    public void currentData()
    {
    name=EtName.getText().toString();
    email=EtEmail.getText().toString();
    address=EtAddress.getText().toString();
    pass=EtPassword.getText().toString();

    }
    public void updatedataDataBase() {
        try {
            if (name.isEmpty() || pass.isEmpty() || address.isEmpty() || email.isEmpty()) {
                Toast.makeText(updateuser.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper = new DbHelper(updateuser.this);
            dbHelper.updateUser(userId,name, email, pass, address, imageByteArray);

            Toast.makeText(updateuser.this, "User data added to database", Toast.LENGTH_LONG).show();

            // Start new activity with intent
            Intent intent = new Intent(updateuser.this, MainActivity.class);
            startActivity(intent);


             } catch (Exception ex) {
            Toast.makeText(updateuser.this, "Error: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    public void setPrevious(long userId)
    {
        UserDataModel user=getuserData(userId);
        EtName.setText(user.getName());
        EtAddress.setText(user.getAddress());
        EtEmail.setText(user.getEmail());
        EtPassword.setText(user.getPassword());

        if (user.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            imageGallery.setImageBitmap(bitmap);
        }

        imageByteArray=user.getImage();
    }

    public void initView() {
        UpdateBtn = findViewById(R.id.signupBtn);
        EtName = findViewById(R.id.etName);
        EtEmail = findViewById(R.id.etEmail);
        EtAddress = findViewById(R.id.etAddress);
        EtPassword = findViewById(R.id.etPassword);
        togglePassword = findViewById(R.id.togglepassvisbility);
        imageGallery= findViewById(R.id.imgUser);
        imgButton=findViewById(R.id.imgBtn);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Gallery_REQ_CODE) {
                // Get the URI of the selected image
                Uri imageUri = data.getData();

                try {
                    // Convert the selected image URI to a Bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageByteArray = stream.toByteArray();

                    // Set the image in the ImageView
                    imageGallery.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private UserDataModel getuserData(long itemId) {

        DbHelper dbHelper = new DbHelper(this);

        return dbHelper.getUserById(itemId);

    }
}