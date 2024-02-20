package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.personnelcuisine.R;

import java.util.ArrayList;

import Adaptor.RVAdapter;
import Helper.CDbHelper;

public class RecycleViewActivity extends AppCompatActivity {
    RVAdapter rvAdapter;
    RecyclerView rvRecycle;
    ArrayList<FoodDataModel> dataModelArraylist;
    FoodDataModel dataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        rvRecycle = findViewById(R.id.rv_data);
        rvRecycle.setLayoutManager(new LinearLayoutManager(this));

        // Instantiate DBHelper and get data from the database
        CDbHelper cdbHelper = new CDbHelper(RecycleViewActivity.this);
        dataModelArraylist = (ArrayList<FoodDataModel>) cdbHelper.getAllFood();

        rvAdapter = new RVAdapter(dataModelArraylist, RecycleViewActivity.this);
        rvRecycle.setAdapter(rvAdapter);


        Toast.makeText(this, "No of items = " + String.valueOf(rvAdapter.getItemCount()), Toast.LENGTH_LONG).show();
    }
}