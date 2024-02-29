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
import com.bumptech.glide.Glide;

public class UpdateForm extends AppCompatActivity {

    EditText etName, etPrice, etLocation, etnumber, etDescription;
    Button Btnpic, BtnAdd;
    ImageView PicUser;
    DbHelper dbhelper;
    Uri uri;

    Long Id;


    String Name, Number, Location, Price, Description;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);
        initView();


        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                setData(UpdateForm.this);
                Intent intent = new Intent(UpdateForm.this,AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Btnpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        long itemId = getIntent().getLongExtra("item_Id", -1);

        FoodModel food = getFoodData(itemId);

        // Populate the UI elements with the retrieved data
        if (food != null) {
            Id=itemId;
            etName.setText(food.getName());
            etDescription.setText(food.getDescription());
            etnumber.setText(food.getNumber());
            etLocation.setText(food.getLocation());
            etPrice.setText(food.getPrice());

            // Load the image using Glide or any other image loading library
            Glide.with(this).load(Uri.parse(food.getImageUri())).into(PicUser);
        }
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

            dbhelper= new DbHelper(UpdateForm.this);
            dbhelper= new DbHelper(UpdateForm.this);
            String imageUriString = uri.toString();
            dbhelper.updateFormData((new FoodModel(Id,Name, Description, Number, Location, Price, imageUriString)),UpdateForm.this);
        }


        return null;
    }

    private FoodModel getFoodData(long itemId) {

        DbHelper dbHelper = new DbHelper(this);

        return dbHelper.getFoodById(itemId);
    }

}