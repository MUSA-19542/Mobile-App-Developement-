package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity {
    RvAdapter rvAdapter;
    RecyclerView rvRecycle;
    ArrayList<FoodModel> dataModelArraylist;
    FoodModel dataModel;

    FloatingActionButton AddActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        try {

            rvRecycle = findViewById(R.id.rv_data);

            // Instantiate DBHelper and get data from the database
            DbHelper dbHelper = new DbHelper(RecycleActivity.this);
            dataModelArraylist = (ArrayList<FoodModel>) dbHelper.getAllFood();

            rvAdapter = new RvAdapter(dataModelArraylist, RecycleActivity.this);
            rvRecycle.setAdapter(rvAdapter);


            Toast.makeText(this, "No of items = " + String.valueOf(rvAdapter.getItemCount()), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Exception = " + ex, Toast.LENGTH_LONG).show();
        }

        AddActivity=findViewById(R.id.rvaddBtn);
        AddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecycleActivity.this, AddActivity.class));
            }
        });
    }
}