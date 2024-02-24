package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personnelcuisine.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Adaptor.CategoryAdaptor;
import Adaptor.PopularAdaptor;
import Adaptor.SearchAdaptor;
import Domain.CategoryDomain;
import Domain.FoodsDomain;
import Helper.CDbHelper;
import Helper.DbHelper;
import Adaptor.CategoryAdaptor;

public class MainActivity extends AppCompatActivity   implements CategoryAdaptor.OnCategoryItemClickListener{
    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recycleViewCategoryList,recyclerViewPopularList;
    private TextView userNameTextView,orderNow;
    private ImageView userImageView;
    LinearLayout profileBtn,SupportBtn,SettingBtn;
    Button logout_btn,allBtn;
    SharedPreferences sharedpref;
    DbHelper dbHelper;
    long userId;

    CDbHelper cDbHelper;

    List<FoodDataModel> itemList;

    private SearchView searchView;

    RecyclerView searchRecyclerView;
    SearchAdaptor searchAdapter;
String emailP;
    @Override
    public void onCategoryItemClick(String categoryType) {
        RecycleViewPopular(categoryType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize UI elements

        cDbHelper = new CDbHelper(this);
        dbHelper = new DbHelper(this); // Initialize dbHelper
           initView();
        recyclerViewCategory();
        RecycleViewPopular();
        bottomNavigation();
        searchViewMethods();

        Intent intent = getIntent();
        sharedpref = getSharedPreferences("MyPrefss", MODE_PRIVATE);
        emailP = sharedpref.getString("email", "");
        if(emailP.isEmpty()) {
            Toast.makeText(MainActivity.this, "Null Passed To Main Activity", Toast.LENGTH_LONG).show();
        } else {
            // Initialize dbHelper before using it
            dbHelper = new DbHelper(this);
            userId = dbHelper.getUserIdByEmail(emailP);
            displayUserData(userId);
            emailP="";
        }

        logout_btn=findViewById(R.id.logoutBtn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signedinActivity.class));
            }
        });

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecycleViewPopular();
            }
        });


        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdaptor(this, new ArrayList<>());
        searchRecyclerView.setAdapter(searchAdapter);

        searchAdapter.setOnItemClickListener(new SearchAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(FoodDataModel food) {
                // Pass the item ID to DetailActivity
                Intent intent = new Intent(MainActivity.this, ShowDetailActivity.class);
                intent.putExtra("food_id", food.getId());
                startActivity(intent);
            }
        });

    }


    public void searchViewMethods() {
        // Your existing code...

        // Set up SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Hide RecyclerView when search query is empty
                    searchRecyclerView.setVisibility(View.GONE);
                } else {
                    // Show RecyclerView when search query is not empty
                    searchRecyclerView.setVisibility(View.VISIBLE);
                    // Call filter method to filter the list based on the search query
                    filterList(newText);
                }
                return true;
            }
        });
    }


    public void filterList(String newText) {
        // Retrieve all food items from the database
        itemList = cDbHelper.getAllFood();

        // Create a new list to store filtered items
        List<FoodDataModel> filteredList = new ArrayList<>();

        // Iterate through each food item in the itemList
        for (FoodDataModel item : itemList) {
            // Check if the name of the food item contains the search query (case-insensitive)
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                // If the name matches, add it to the filtered list
                filteredList.add(item);
            }
        }

        // Check if the filtered list is empty
        if (filteredList.isEmpty()) {
            // If no items match the search query, show a toast message
            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show();
        } else {
            // If there are matching items, update the RecyclerView with the filtered list
            searchAdapter.updateList(filteredList);
        }
    }


    public void initView()
    {
        userNameTextView = findViewById(R.id.UserName);
        userImageView = findViewById(R.id.Userimage);
        recycleViewCategoryList=findViewById(R.id.cartView);
        searchView=findViewById(R.id.SearchView);
        searchView.clearFocus();
       searchRecyclerView = findViewById(R.id.searchRecyclerView);
       orderNow=findViewById(R.id.OrderNow);
       allBtn=findViewById(R.id.AllBtn);
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycleViewCategoryList.setLayoutManager(linearlayoutmanager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Pizza","cat_1","P"));
        category.add(new CategoryDomain("Burger","cat_2","B"));
        category.add(new CategoryDomain("HotDog","cat_3","H"));
        category.add(new CategoryDomain("Drink","cat_4","Dr"));
        category.add(new CategoryDomain("Donut","cat_5","D"));

        // Pass MainActivity as the listener for category item clicks
        adapter = new CategoryAdaptor(category, this);
        recycleViewCategoryList.setAdapter(adapter);
    }



    private void RecycleViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        // Initialize the CDbHelper
        CDbHelper dbHelper = new CDbHelper(this);

        // Retrieve all food data from the database
        List<FoodDataModel> foodList = dbHelper.getAllFood();

        // Check if foodList is not empty before creating the adapter
        if (!foodList.isEmpty()) {
            adapter2 = new PopularAdaptor((ArrayList<FoodDataModel>) foodList);
            recyclerViewPopularList.setAdapter(adapter2);
        }
    }

    private void RecycleViewPopular(String categoryType) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        // Initialize the CDbHelper
        CDbHelper dbHelper = new CDbHelper(this);

        // Retrieve food data by category type from the database
        List<FoodDataModel> foodList = dbHelper.getFoodByType(categoryType);

        // Check if foodList is not empty before creating the adapter
        if (!foodList.isEmpty()) {
            adapter2 = new PopularAdaptor((ArrayList<FoodDataModel>) foodList);
            recyclerViewPopularList.setAdapter(adapter2);
        }
    }

    private void bottomNavigation()


    {

        SettingBtn=findViewById(R.id.settingsBtn);
        SettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents = new Intent(MainActivity.this,AdminBypass.class);
                startActivity(intents);
            }
        });

        //support Btn
        SupportBtn=findViewById(R.id.supportBtn);
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents = new Intent(MainActivity.this,SupportActivity.class);
                startActivity(intents);
            }
        });


        //Profile Btn

        profileBtn=findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intents = new Intent(MainActivity.this,ProfileActivity.class);
                intents.putExtra("user_Id", userId);
               startActivity(intents);
            }
        });


        //Home Btn
        FloatingActionButton floatingActionButton=findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,CartListActivity.class);
<<<<<<< HEAD
                intent.putExtra("User_id",userId);
=======
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
                startActivity(intent);


                }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    private void displayUserData(long userid) {
        UserDataModel user;
        // Initialize DbHelper
        DbHelper dbHelper = new DbHelper(this);

        // Get all users from the database
        List<UserDataModel> userList = dbHelper.getAllUsers();

        // Check if there are users in the list
        if (!userList.isEmpty()) {
            // Get the last user from the list


                user = userList.get(((int) userid)-1) ;


            // Set user name in the TextView
            userNameTextView.setText("Hi " + user.getName());

            // Set user image in the ImageView
            if (user.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
                userImageView.setImageBitmap(bitmap);
            }
        }
    }


    private void displayUserData() {
        UserDataModel user;
        // Initialize DbHelper
        DbHelper dbHelper = new DbHelper(this);

        // Get all users from the database
        List<UserDataModel> userList = dbHelper.getAllUsers();

        // Check if there are users in the list
        if (!userList.isEmpty()) {
            // Get the last user from the list
            user = userList.get(userList.size()-1);
            // Set user name in the TextView
            userNameTextView.setText("Hi " + user.getName());

            // Set user image in the ImageView
            if (user.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
                userImageView.setImageBitmap(bitmap);
            }
        }
    }



}