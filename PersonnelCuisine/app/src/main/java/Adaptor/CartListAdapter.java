package Adaptor;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personnelcuisine.R;

import java.util.ArrayList;

import Domain.CartModel;
import Helper.ManagementCart;
import Interface.ChangeNumberItemsListener;
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<CartModel> cartItems;
    private ManagementCart managementCart;
    Context context;

    public CartListAdapter(Context context, ArrayList<CartModel> cartItems) {
        this.cartItems = cartItems;
        managementCart = new ManagementCart(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartItem = cartItems.get(position);
        // Bind data to ViewHolder
        Bitmap bitmap = BitmapFactory.decodeByteArray(cartItem.getImage(), 0, cartItem.getImage().length);
        // Load Bitmap into ImageView using Glide
        Glide.with(context)
                .load(bitmap)
                .into(holder.picFood);
        holder.title.setText(cartItem.getItem_name());
        holder.feeEachItem.setText(String.valueOf(cartItem.getPrice()));
        holder.num.setText(String.valueOf(cartItem.getNumberOrder()));
        holder.totalEachItem.setText(String.valueOf(cartItem.getPrice() * cartItem.getNumberOrder()));

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cartItem.setNumberOrder(cartItem.getNumberOrder()+1);
               reset(holder,cartItem);



        }});

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cartItem.getNumberOrder()>1) {
                    cartItem.setNumberOrder(cartItem.getNumberOrder() - 1);
                    reset(holder, cartItem);
                }
                else
                {
                    managementCart.removeItem(cartItem.getFood_id(), new ChangeNumberItemsListener() {
                        @Override
                        public void changed() {
                            cartItems.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                }

            }});

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.removeItem(cartItem.getFood_id(), new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        cartItems.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    public void reset(ViewHolder holder, CartModel cartItem)
    {
        holder.num.setText(String.valueOf(cartItem.getNumberOrder()));
        holder.feeEachItem.setText(String.valueOf(cartItem.getPrice()));
        holder.totalEachItem.setText(String.valueOf(cartItem.getPrice() * cartItem.getNumberOrder()));
        managementCart.setNumberOrders(cartItem.getFood_id(),cartItem.getNumberOrder());

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, num, totalEachItem;
        ImageView plusItem, minusItem,picFood;
        Button removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            removeBtn = itemView.findViewById(R.id.Removebtn);
            picFood=itemView.findViewById(R.id.picCart);
        }
    }
}


