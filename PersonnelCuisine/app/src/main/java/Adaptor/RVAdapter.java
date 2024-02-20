package Adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.Activity.AdminActivity;
import com.example.personnelcuisine.Activity.FoodDataModel;
import com.example.personnelcuisine.Activity.UpdateFood;
import com.example.personnelcuisine.R;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import Helper.CDbHelper;

public class RVAdapter  extends RecyclerView.Adapter<RVAdapter.VHolder> {
    ArrayList<FoodDataModel> list;
    Context context;
    public RVAdapter(ArrayList<FoodDataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RVAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvdatalayout,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.VHolder holder, int position) {

        FoodDataModel foodmodel= list.get(position);
        holder.TxtName.setText(foodmodel.getName());
        holder.TxtPrice.setText(String.valueOf(foodmodel.getPrice()));
        holder.TxtDescription.setText(foodmodel.getDescription());
        // Convert byte array to Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodmodel.getImage(), 0, foodmodel.getImage().length);
        // Load Bitmap into ImageView using Glide
        Glide.with(context)
                .load(bitmap)
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


                Toast.makeText(context,"Update button pressed Under Developement",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateFood.class);
                long itemId=foodmodel.getId();
                intent.putExtra("item_Id", foodmodel.getId());
//
           context.startActivity(intent);
         }
        });
    }


    private void showConfirmationDialog(final FoodDataModel dataModel) {
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

    private void deleteItem(FoodDataModel dataModel) {
        CDbHelper dbHelper = new CDbHelper(context);
        dbHelper.deletefoodbyId(String.valueOf(dataModel.getId()));
        context.startActivity(new Intent(context, AdminActivity.class));
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        ImageView picFood;
        TextView TxtName,TxtPrice,TxtDescription;
        Button BtnUpdate,BtnDelete;
        public VHolder(@NonNull View itemView) {
            super(itemView);

            picFood=itemView.findViewById(R.id.PicFood);
            TxtName=itemView.findViewById(R.id.txtName);
            TxtPrice=itemView.findViewById(R.id.txtPrice);
            TxtDescription=itemView.findViewById(R.id.textDescription);
            BtnUpdate=itemView.findViewById(R.id.btnUpdate);
            BtnDelete=itemView.findViewById(R.id.btnDelete);

        }
        }
}
