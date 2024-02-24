package Adaptor;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personnelcuisine.Activity.FoodDataModel;
import com.example.personnelcuisine.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdaptor extends RecyclerView.Adapter<SearchAdaptor.ViewHolder> {
    private Context context;
    private List<FoodDataModel> foodList;

    public interface OnItemClickListener {
        void onItemClick(FoodDataModel food);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SearchAdaptor(Context context, List<FoodDataModel> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    public void updateList(List<FoodDataModel> filteredList) {
        foodList.clear(); // Clear the existing list
        foodList.addAll(filteredList); // Add all items from the filtered list
        notifyDataSetChanged(); // Notify the adapter of the data set change
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDataModel food = foodList.get(position);
        holder.foodNameTextView.setText(food.getName());
        holder.foodPriceTextView.setText("$ "+String.valueOf(food.getPrice()));

        if (food.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);
            holder.foodImageView.setImageBitmap(bitmap);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(foodList.get(position));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodNameTextView;
        TextView foodPriceTextView;
        TextView foodDescriptionTextView;
        ImageView foodImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.txtName);
            foodPriceTextView = itemView.findViewById(R.id.txtPrice);

            foodImageView = itemView.findViewById(R.id.PicFood);
        }
    }
}
