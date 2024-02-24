package Adaptor;

<<<<<<< HEAD
import static android.app.ProgressDialog.show;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
=======
import android.annotation.SuppressLint;
import android.content.Context;
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
=======
import android.widget.CheckBox;
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
<<<<<<< HEAD
=======
import com.example.personnelcuisine.Activity.CartListActivity;
import com.example.personnelcuisine.Activity.FoodDataModel;
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
import com.example.personnelcuisine.R;

import java.util.ArrayList;

<<<<<<< HEAD
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
=======
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
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
<<<<<<< HEAD
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
=======
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


>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
               cartItem.setNumberOrder(cartItem.getNumberOrder()+1);
               reset(holder,cartItem);



        }});
=======
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
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD

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


=======
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
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
