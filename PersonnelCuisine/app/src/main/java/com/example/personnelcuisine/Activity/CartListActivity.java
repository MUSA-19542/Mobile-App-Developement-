package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
<<<<<<< HEAD
import android.widget.ImageView;
=======
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.personnelcuisine.OrderActivity;
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
    TextView CheckOut;
    long userId;
    float DeliveryCost,Tax,TotalFee;
=======
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

>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);
<<<<<<< HEAD
        userId = getIntent().getLongExtra("User_id", -1);
        initView();
        initList();
        bottomNavigation();
       change();

    }


    public void change()
    {

        DeliveryCost=Math.round(managementCart.getTotalFee()*0.19);
        Tax=Math.round(managementCart.getTotalFee()*0.09);
        TotalFee=Math.round(managementCart.getTotalFee()+Tax+DeliveryCost);
        totalFeeTxt.setText("$ "+String.valueOf(TotalFee));
        totalItems.setText("No.  "+String.valueOf(managementCart.ItemCount()));
        deliveyCost.setText("$ "+String.valueOf(DeliveryCost));
        taxTxt.setText("$ "+String.valueOf(Tax));

    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
=======

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
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
=======
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });

<<<<<<< HEAD

        CheckOut.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent intent = new Intent(CartListActivity.this, OrderActivity.class);
         intent.putExtra("user_Id", userId);
         intent.putExtra("total", totalFeeTxt.getText());
         startActivity(intent);
     }
 });

    }

    private void initView() {

        CheckOut=findViewById(R.id.Txtcheckout);
        recyclerViewList = findViewById(R.id.cartView);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        totalItems=findViewById(R.id.totalitemsTxt);
        deliveyCost=findViewById(R.id.deliveryTxt);
        taxTxt=findViewById(R.id.taxTxt);
=======
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
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
<<<<<<< HEAD

        adapter = new CartListAdapter((Context) this, (ArrayList<CartModel>) managementCart.getListCart());
        recyclerViewList.setAdapter(adapter);
    }
}

=======
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
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
