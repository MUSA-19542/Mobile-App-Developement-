package com.example.foodlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.VHolder> {

    ArrayList<FoodModel> list;
    Context context;


    public RvAdapter(ArrayList<FoodModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    String item_id;
    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvdatalayout,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {

        FoodModel foodmodel= list.get(position);
        holder.TxtName.setText(foodmodel.getName());
        holder.TxtPrice.setText("$ "+foodmodel.getPrice());
        Glide.with(context)
                .load(Uri.parse(foodmodel.getImageUri()))
                .into(holder.picFood);

        holder.BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Delete button pressed",Toast.LENGTH_SHORT).show();
                showConfirmationDialog(foodmodel);
            }
        });

        holder.BtnUpdate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                Toast.makeText(context,"Update button pressed",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateForm.class);
                long itemId=foodmodel.getId();
                intent.putExtra("item_Id", foodmodel.getId());

                context.startActivity(intent);
            }
        });


        holder.BtnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDetail.class);
                intent.putExtra("item_Id", foodmodel.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{

        ImageView picFood;
        TextView TxtName,TxtPrice;
        Button BtnDescription,BtnUpdate,BtnDelete;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            picFood=itemView.findViewById(R.id.PicFood);
            TxtName=itemView.findViewById(R.id.txtName);
            TxtPrice=itemView.findViewById(R.id.textPrice);
            BtnDescription=itemView.findViewById(R.id.btnDescription);
            BtnUpdate=itemView.findViewById(R.id.btnUpdate);
            BtnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }


    private void showConfirmationDialog(final FoodModel dataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);

        Button btnDelete = dialogView.findViewById(R.id.btn_delete);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Set button colors programmatically
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform delete action
                deleteItem(dataModel);
                dialog.dismiss();
            }


        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void deleteItem(FoodModel dataModel) {
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.deletetaskbyId(String.valueOf(dataModel.getId()));
        context.startActivity(new Intent(context,AddActivity.class));
    }
}
