package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import Adaptor.CartListAdapter;
import Helper.ManagementCart;
import Interface.ChangeNumberItemsListener;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;

    TextView totalTxt,emptyTxt,totalFeeTxt,taxTxt,deliveryTxt,checkoutBtn;
    private double tax;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        try{
            initView();
        initList();
        CalculateCart();
        bottomNavigation();
    }catch (Exception ex)
        {
            Toast.makeText(this,"Error = "+ex,Toast.LENGTH_LONG).show();
        }

    }

    private void bottomNavigation()
    {
        FloatingActionButton floatingActionButton=findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,MapsActivity.class));
            }
        });
    }

    private void initView()
    {
        try {


            totalFeeTxt = findViewById(R.id.totalitemsTxt);
            taxTxt = findViewById(R.id.taxTxt);
            deliveryTxt = findViewById(R.id.deliveryTxt);
            totalTxt = findViewById(R.id.totalFeeTxt);
            emptyTxt = findViewById(R.id.emptyTxt);
            scrollView = findViewById(R.id.scrollView3);
            recyclerViewList = findViewById(R.id.cartView);
           checkoutBtn=findViewById(R.id.Txtcheckout);
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error = "+ex,Toast.LENGTH_LONG).show();
        }
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);



        if (managementCart.getListCart() == null || managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            Log.d("CartListActivity", "Cart is empty");
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }

    private void CalculateCart()
    {
        double total,itemTotal,percentTax =0.02;
        double delivery=10;
        tax=Math.round((managementCart.getTotalFee()*percentTax)*100)/100;
        total=Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100;
        itemTotal=Math.round((managementCart.getTotalFee())*100)/100;

        totalFeeTxt.setText(" $ " +itemTotal);
        taxTxt.setText(" $ " +tax);
        deliveryTxt.setText(" $ " +delivery);
        totalTxt.setText(" $ " +total);


    }
}