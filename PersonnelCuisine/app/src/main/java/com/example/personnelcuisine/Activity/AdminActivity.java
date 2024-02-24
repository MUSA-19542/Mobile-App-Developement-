package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.personnelcuisine.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Helper.CDbHelper;

public class AdminActivity extends AppCompatActivity {
ImageView imgFood;
Button imgBtn,addBtn,recycleBtn;
EditText etName,etPrice,etDescription;
RadioButton rbP,rbB,rbH,rbD;
    CDbHelper cdbHelper;
    SharedPreferences sharepref;
    SharedPreferences.Editor editor;

    byte[] imageByteArray;
    String name,description;
    int Price;
    int cal;

    private final int Gallery_REQ_CODE=1000;

    RadioGroup radioGroup;



   String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();

        recycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,RecycleViewActivity.class));
            }
        });


        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGalley = new Intent(Intent.ACTION_PICK);
                iGalley.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGalley,Gallery_REQ_CODE);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbB) {
                    Type = "B";
                } else if (checkedId == R.id.rbD) {
                    Type = "D";
                } else if (checkedId == R.id.rbH) {
                    Type = "H";
                } else if (checkedId == R.id.rbP) {
                    Type = "P";
                }
                else if (checkedId == R.id.rbDr) {
                    Type = "Dr";
                }
                if (Type == null) {
                    Toast.makeText(AdminActivity.this, "Please select a food type", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    addBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name=etName.getText().toString();
            description=etDescription.getText().toString();
            Price= Integer.parseInt(etPrice.getText().toString());

            setDataDatabase();

            etName.setText("");
            etDescription.setText("");
            etPrice.setText("");

        }
    });
    }




    public void initView() {
        etName=findViewById(R.id.etName);
        etPrice=findViewById(R.id.etPrice);
        etDescription=findViewById(R.id.etDescription);
        imgFood=findViewById(R.id.imgFood);
        imgBtn=findViewById(R.id.imgBtn);
        addBtn=findViewById(R.id.addBtn);
      radioGroup = findViewById(R.id.radioGroup);
      recycleBtn=findViewById(R.id.recycleBtn);

        // Adding listener to the RadioGroup

    }

    public void setDataDatabase() {
        if (name.isEmpty() || Price==0 || description.isEmpty()) {
            Toast.makeText(AdminActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        cdbHelper = new CDbHelper(AdminActivity.this);
        cdbHelper.addFood(name, Price, description, Type, imageByteArray);

        Toast.makeText(AdminActivity.this, "Food data added to database", Toast.LENGTH_LONG).show();



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
                    imgFood.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}