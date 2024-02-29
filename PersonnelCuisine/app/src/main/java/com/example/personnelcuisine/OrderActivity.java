package com.example.personnelcuisine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personnelcuisine.Activity.MapsActivity;
import com.example.personnelcuisine.Activity.UserDataModel;

import Helper.DbHelper;

public class OrderActivity extends AppCompatActivity {

    EditText etName,etAddress,etPhone,etDescription;
    TextView Order_Price;
    String Total;

    Button BtnConfirm;
    long userId;
    UserDataModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();

         userId = getIntent().getLongExtra("user_Id", -1);
         Total=getIntent().getStringExtra("total");
         user=getuserData(userId);

            setData();
    }
    public void setData()
    {
        etName.setText(user.getName());
        etAddress.setText(user.getAddress());
        Order_Price.setText(Total);
        BtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderActivity.this, MapsActivity.class));
            }
        });
    }


    public void initView()
    {
        etName=findViewById(R.id.etName);
        etAddress=findViewById(R.id.etAddress);
        Order_Price=findViewById(R.id.Order_Price);
        etPhone=findViewById(R.id.etPhone);
        etDescription=findViewById(R.id.etDescription);
        BtnConfirm=findViewById(R.id.BtnConfirm);
    }
    private UserDataModel getuserData(long itemId) {

        DbHelper dbHelper = new DbHelper(this);

        return dbHelper.getUserById(itemId);

    }
}