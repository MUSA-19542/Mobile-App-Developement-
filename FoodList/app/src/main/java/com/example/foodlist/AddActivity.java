package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText etName, etPrice, etLocation, etnumber, etDescription;
    Button Btnpic, BtnAdd,BtnRec;
    ImageView PicUser;
    DbHelper dbhelper;
    Uri uri;
    String defaulte;


    String Name, Number, Location, Price, Description;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();


        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                setData(AddActivity.this);
            }
        });
        Btnpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        BtnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this,RecycleActivity.class));
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            uri = data.getData();

            // Now you can use this URI to do whatever you need with the image
            // For example, you can display it in an ImageView
            PicUser.setImageURI(uri);
        }
    }

    public void setImage()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void initView() {
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etnumber = findViewById(R.id.etNumber);
        etLocation = findViewById(R.id.etAddress);
        etPrice = findViewById(R.id.etPrice);
        Btnpic = findViewById(R.id.btnAddImage);
        BtnAdd = findViewById(R.id.btnAdd);
        PicUser = findViewById(R.id.pic_add);
        BtnRec=findViewById(R.id.btnRec);
        defaulte=PicUser.toString();


    }

    public void getData() {

        Description = etDescription.getText().toString();
        Name = etName.getText().toString();
        Number = etnumber.getText().toString();
        Location = etLocation.getText().toString();
        Price = etPrice.getText().toString();
    }

    public Void setData(Context context) {
        // Check if any of the fields are empty
        if (Description.isEmpty()) {
            Toast.makeText(context, "Error: Please provide a description", Toast.LENGTH_SHORT).show();
        }
        if (Name.isEmpty()) {
            Toast.makeText(context, "Error: Please write your name", Toast.LENGTH_SHORT).show();
        }
        if (Number.isEmpty()) {
            Toast.makeText(context, "Error: Please provide a number", Toast.LENGTH_SHORT).show();
        }
        if (Location.isEmpty()) {
            Toast.makeText(context, "Error: Please provide a location", Toast.LENGTH_SHORT).show();
        }
        if (Price.isEmpty()) {
            Toast.makeText(context, "Error: Please provide a price", Toast.LENGTH_SHORT).show();
        }
        else if (uri == null) {
            Toast.makeText(context, "Error: Please select an image", Toast.LENGTH_SHORT).show();
        }
        else {


            dbhelper= new DbHelper(AddActivity.this);
            String imageUriString = uri.toString();
            dbhelper.addFood(new FoodModel(0,Name, Description, Number, Location, Price, imageUriString));
            setNull();
        }


        return null;
    }

    public void setNull()
    {
        etName.setText("");
        etDescription.setText("");
        etnumber.setText("");
        etLocation.setText("");
        etPrice.setText("");

    }

}