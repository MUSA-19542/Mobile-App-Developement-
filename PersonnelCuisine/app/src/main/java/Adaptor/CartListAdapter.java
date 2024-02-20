package Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.Activity.CartListActivity;
import com.example.personnelcuisine.Activity.FoodDataModel;
import com.example.personnelcuisine.R;

import java.util.ArrayList;

import Domain.FoodsDomain;
import Helper.ManagementCart;
import Interface.ChangeNumberItemsListener;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList <FoodDataModel> foodsDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    String name,fee;

    int numberincart,total;


    public CartListAdapter(ArrayList<FoodDataModel> foodsDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.foodsDomains = foodsDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
      return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {


        holder.title.setText(foodsDomains.get(position).getName());
      holder.feeEachItem.setText(String.valueOf(foodsDomains.get(position).getPrice()));
      holder.num.setText(String.valueOf(foodsDomains.get(position).getNumberInCart()));

      try {
          double total = foodsDomains.get(position).getPrice() * foodsDomains.get(position).getNumberInCart();
          holder.totalEachItem.setText("$ " + total);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the case where parsing fails, e.g., log the error or set a default value for total
        }



        int drawableRecourcesId = R.drawable.pizza; // Default image resource in case the byte array is null or empty

        if (foodsDomains.get(position).getImage() != null && foodsDomains.get(position).getImage().length > 0) {
            Glide.with(holder.itemView.getContext())
                    .load(foodsDomains.get(position).getImage()) // Load byte array directly
                    .into(holder.pic);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(drawableRecourcesId)
                    .into(holder.pic);
        }



        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberedFood(foodsDomains, position, new ChangeNumberItemsListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });

            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberedFood(foodsDomains, position, new ChangeNumberItemsListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });

            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.RemoveItem(foodsDomains, position, new ChangeNumberItemsListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void changed() {
                            notifyDataSetChanged();
                            changeNumberItemsListener.changed();


                }
        });
    }
    });
}

    @Override
    public int getItemCount() {
      return   foodsDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,feeEachItem;
        ImageView plusItem,pic,minusItem;
        Button removeBtn;
        TextView totalEachItem,num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleTxt);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            pic=itemView.findViewById(R.id.picCart);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            num=itemView.findViewById(R.id.numberItemTxt);
            plusItem=itemView.findViewById(R.id.plusCartBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
            removeBtn=itemView.findViewById(R.id.Removebtn);

        }
    }


}
