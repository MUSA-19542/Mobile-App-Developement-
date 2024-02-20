package Adaptor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.Activity.FoodDataModel;
import com.example.personnelcuisine.Activity.ShowDetailActivity;
import com.example.personnelcuisine.R;

import java.util.ArrayList;

import Domain.FoodsDomain;


public class PopularAdaptor extends RecyclerView.Adapter<PopularAdaptor.ViewHolder> {
    ArrayList<FoodDataModel> popularFood;

    public PopularAdaptor(ArrayList<FoodDataModel> categoryDomains) {
        this.popularFood = categoryDomains;
    }

    @NonNull
    @Override
    public PopularAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholer_popular,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(popularFood.get(position).getName());
        holder.fee.setText(String.valueOf(popularFood.get(position).getPrice()));

        int drawableRecourcesId = R.drawable.pizza; // Default image resource in case the byte array is null or empty

        if (popularFood.get(position).getImage() != null && popularFood.get(position).getImage().length > 0) {
            Glide.with(holder.itemView.getContext())
                    .load(popularFood.get(position).getImage()) // Load byte array directly
                    .into(holder.pic);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(drawableRecourcesId)
                    .into(holder.pic);
        }


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass only the ID
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("food_id", popularFood.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return popularFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,fee;
        ImageView pic;
        TextView addBtn;
         public ViewHolder(@NonNull View itemView)
         {
             super(itemView);
      title=itemView.findViewById(R.id.title);
      fee=itemView.findViewById(R.id.fee);
      pic=itemView.findViewById(R.id.pic);
      addBtn=itemView.findViewById(R.id.addBtn);
         }
    }
}
