package Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.R;

import java.util.ArrayList;

import Domain.CategoryDomain;


public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    ArrayList<CategoryDomain>categoryDomains;

    private OnCategoryItemClickListener listener;

    public CategoryAdaptor(ArrayList<CategoryDomain> categoryDomains, OnCategoryItemClickListener listener) {
        this.categoryDomains = categoryDomains;
        this.listener = listener;
    }
    public interface OnCategoryItemClickListener {
        void onCategoryItemClick(String categoryType);
    }
    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        String categoryType = categoryDomains.get(position).getType();
        String picUrl = "";
        switch (position) {
            case 0:
                picUrl = "cat_1";
                holder.mainlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background1));
                break;
            case 1:
                picUrl = "cat_2";
                holder.mainlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background2));
                break;
            case 2:
                picUrl = "cat_3";
                holder.mainlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background3));
                break;
            case 3:
                picUrl = "cat_4";
                holder.mainlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background4));
                break;
            case 4:
                picUrl = "cat_5";
                holder.mainlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background5));
                break;
        }




        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryType = categoryDomains.get(holder.getAdapterPosition()).getType();
                listener.onCategoryItemClick(categoryType);
            }
        });


        int drawableRecourcesId=holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableRecourcesId).into(holder.categoryPic);

    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainlayout;
         public ViewHolder(@NonNull View itemView)
         {
             super(itemView);
             categoryName=itemView.findViewById(R.id.categoryNames);
             categoryPic=itemView.findViewById(R.id.categoryPic);
             mainlayout=itemView.findViewById(R.id.mainLayout);



         }
    }
}
