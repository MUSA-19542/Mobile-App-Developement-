package com.example.personnelcuisine.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
<<<<<<< HEAD
import android.database.Cursor;
=======
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
<<<<<<< HEAD
import android.provider.OpenableColumns;
=======
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.personnelcuisine.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Helper.DbHelper;

public class signupActivity extends AppCompatActivity {
    Button SignBtn;
    EditText EtName, EtEmail, EtAddress, EtPassword;
    CheckBox togglePassword;
    ImageView imageGallery;
    SharedPreferences sharepref;
    SharedPreferences.Editor editor;
    DbHelper dbHelper;

    byte[] imageByteArray;


    String name,email,pass,address;

    private final int Gallery_REQ_CODE=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sharepref = getSharedPreferences("MyPref", MODE_PRIVATE);

        initView();

         imageGallery= findViewById(R.id.imgUser);
        Button imgButton=findViewById(R.id.imgBtn);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGalley = new Intent(Intent.ACTION_PICK);
                iGalley.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGalley,Gallery_REQ_CODE);

            }
        });



        //Password Visibility Code
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


        try {
            // Shared preferences initialization
            sharepref = getSharedPreferences("MyPref", MODE_PRIVATE);

            editor = sharepref.edit();
            SignBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     name = EtName.getText().toString();
                     pass = EtPassword.getText().toString();
                     address = EtAddress.getText().toString();
                     email = EtEmail.getText().toString();

                    // Save data to SharedPreferences
                    editor.putString("name", name);
                    editor.putString("pass", pass);
                    editor.putString("address", address);
                    editor.putString("email", email);
                    editor.putBoolean("hasPassedThroughMain", true);
                    editor.apply();
                    setDataDatabase();

                    // Get the byte array representation of the selected image


                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    public void initView() {
        SignBtn = findViewById(R.id.signupBtn);
        EtName = findViewById(R.id.etName);
        EtEmail = findViewById(R.id.etEmail);
        EtAddress = findViewById(R.id.etAddress);
        EtPassword = findViewById(R.id.etPassword);
        togglePassword = findViewById(R.id.togglepassvisbility);

        sharepref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }

    public void setDataDatabase() {
        try {
            if (name.isEmpty() || pass.isEmpty() || address.isEmpty() || email.isEmpty()) {
                Toast.makeText(signupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper = new DbHelper(signupActivity.this);
            dbHelper.addUser(name, email, pass, address, imageByteArray);

            Toast.makeText(signupActivity.this, "User data added to database", Toast.LENGTH_LONG).show();

            // Start new activity with intent
            Intent intent = new Intent(signupActivity.this, LoginBypass.class);
            startActivity(intent);
            // Set the name and pass values in SharedPreferences before starting the activity
            editor.putString("name", name);
            editor.putString("pass", pass);
            editor.apply();
            finish();
        } catch (Exception ex) {
            Toast.makeText(signupActivity.this, "Error: " + ex, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Gallery_REQ_CODE) {
                // Get the URI of the selected image
                Uri imageUri = data.getData();

                try {
<<<<<<< HEAD
                    // Get the file size of the selected image
                    int fileSize = getFileSize(imageUri);

                    // Check if the file size is less than 2 MB
                    if (fileSize < 2 * 1024 * 1024) { // 2 MB in bytes
                        // Convert the selected image URI to a Bitmap
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                        // Convert the Bitmap to a byte array
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imageByteArray = stream.toByteArray();

                        // Set the image in the ImageView
                        imageGallery.setImageBitmap(bitmap);
                    } else {
                        // Show a toast indicating that the selected image is too large
                        Toast.makeText(this, "Image size exceeds 2 MB limit. Please select a smaller image.", Toast.LENGTH_SHORT).show();
                    }
=======
                    // Convert the selected image URI to a Bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageByteArray = stream.toByteArray();

                    // Set the image in the ImageView
                    imageGallery.setImageBitmap(bitmap);

>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

<<<<<<< HEAD
    // Method to get the file size of an image URI
    private int getFileSize(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        cursor.moveToFirst();
        int fileSize = cursor.getInt(sizeIndex);
        cursor.close();
        return fileSize;
    }


=======
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
}
