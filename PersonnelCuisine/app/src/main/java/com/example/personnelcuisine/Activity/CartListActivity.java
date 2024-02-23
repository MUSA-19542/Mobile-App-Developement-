package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personnelcuisine.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adaptor.CartListAdapter;
import Domain.CartModel;
import Helper.ManagementCart;
import Interface.ChangeNumberItemsListener;
public class CartListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private CartListAdapter adapter;

    TextView totalItems,deliveyCost,taxTxt,totalFeeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        bottomNavigation();
       change();

    }


    public void change()
    {
        float DeliveryCost,Tax,TotalFee;
        DeliveryCost=Math.round(managementCart.getTotalFee()*0.19);
        Tax=Math.round(managementCart.getTotalFee()*0.09);
        TotalFee=Math.round(managementCart.getTotalFee()+Tax+DeliveryCost);
        totalFeeTxt.setText("$ "+String.valueOf(TotalFee));
        totalItems.setText("$ "+String.valueOf(managementCart.ItemCount()));
        deliveyCost.setText("$ "+String.valueOf(DeliveryCost));
        taxTxt.setText("$ "+String.valueOf(Tax));

    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.cartView);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        totalItems=findViewById(R.id.totalitemsTxt);
        deliveyCost=findViewById(R.id.deliveryTxt);
        taxTxt=findViewById(R.id.taxTxt);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        adapter = new CartListAdapter((Context) this, (ArrayList<CartModel>) managementCart.getListCart());
        recyclerViewList.setAdapter(adapter);
    }
}

