package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Helper.CDbHelper;
import Helper.DbHelper;

public class UpdateFood extends AppCompatActivity {
    ImageView imgFood;
    Button imgBtn,updateBtn;
    EditText etName,etPrice,etDescription;
    RadioButton rbP,rbB,rbH,rbD;
    CDbHelper cdbHelper;
    SharedPreferences sharepref;
    SharedPreferences.Editor editor;

    byte[] imageByteArray;
    String name,description;
    int Price;
    int cal;

    long foodId;

    private final int Gallery_REQ_CODE=1000;

    RadioGroup radioGroup;

  Boolean confirm ;

    String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        foodId = getIntent().getLongExtra("item_Id", -1);
        initView();
        setPrevious(foodId);

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
                    Toast.makeText(UpdateFood.this, "Please select a food type", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
            confirm=false;
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentData();
                showConfirmationDialog(UpdateFood.this);

            }
        });
    }


    public void currentData()
    { name=etName.getText().toString();
        description=etDescription.getText().toString();
        Price= Integer.parseInt(etPrice.getText().toString());


    }
    public void setPrevious(long foodId)
    {
        FoodDataModel foodmodel = getfoodData(foodId);
       etName.setText(foodmodel.getName());
       etPrice.setText(String.valueOf(foodmodel.getPrice()));
       etDescription.setText(foodmodel.getDescription());
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodmodel.getImage(), 0, foodmodel.getImage().length);
        // Load Bitmap into ImageView using Glide
        imgFood.setImageBitmap(bitmap);
        imageByteArray=foodmodel.getImage();
        Type=foodmodel.getType();

    }

    public void initView() {
        etName=findViewById(R.id.etName);
        etPrice=findViewById(R.id.etPrice);
        etDescription=findViewById(R.id.etDescription);
        imgFood=findViewById(R.id.imgFood);
        imgBtn=findViewById(R.id.imgBtn);
        updateBtn=findViewById(R.id.updateBtn);
        radioGroup = findViewById(R.id.radioGroup);


        // Adding listener to the RadioGroup

    }


    public void updateDataDatabase() {
        if (name.isEmpty() || Price==0 || description.isEmpty()) {
            Toast.makeText(UpdateFood.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {


        cdbHelper = new CDbHelper(UpdateFood.this);
        cdbHelper.updateFood(foodId,name, Price, description, Type, imageByteArray);

        Toast.makeText(UpdateFood.this, "Food data updated to  database", Toast.LENGTH_LONG).show();
        }
        catch(Exception Ex)
        {
            Toast.makeText(UpdateFood.this,"Error ="+Ex,Toast.LENGTH_LONG).show();
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

    private FoodDataModel getfoodData(long itemId) {

        CDbHelper cdbHelper = new CDbHelper(this);

        return cdbHelper.getFoodById(itemId);

    }

    private void showConfirmationDialog( Context context) {

        try {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog_confirmation, null);
        builder.setView(dialogView);

        Button btnUpdate = dialogView.findViewById(R.id.btn_delete);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Set button colors programmatically
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform update action
                    updateDataDatabase();
                    dialog.dismiss();
                    Intent intent = new Intent(UpdateFood.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        }
        catch(Exception Ex)
        {
            Toast.makeText(UpdateFood.this,"Error ="+Ex,Toast.LENGTH_LONG).show();
        }

    }

}