package Helper;

import android.content.Context;
<<<<<<< HEAD
import android.widget.Toast;

import com.example.personnelcuisine.Activity.CartListActivity;

import Domain.CartModel;
import Interface.ChangeNumberItemsListener;

import java.util.List;

public class ManagementCart {

    private CartDBHelper dbHelper;

    Context context;

    public ManagementCart(Context context) {
        dbHelper = new CartDBHelper(context);
        this.context=context;
    }

    public void insertFood(CartModel item) {
        long id=item.getFood_id();
        Boolean check=dbHelper.existAlready(id);

        if(check)
        {
            dbHelper.setNumberOfOrders(id,item.getNumberOrder());
        }
        else {
            dbHelper.addItemToCart(item);
        }
        // Update UI after inserting the item

    }


    public void setNumberOrders(long id,int number)
    {
        dbHelper.setNumberOfOrders(id,number);
        // Update UI after inserting the item
        ((CartListActivity) context).change();
    }

    public void removeItem(long id, ChangeNumberItemsListener changeNumberItemsListener) {
        dbHelper.deleteCartItem(id);
        changeNumberItemsListener.changed();
        // Update UI after inserting the item
        ((CartListActivity) context).change();
    }

    public Double getTotalFee() {
        return dbHelper.getTotalFee();
    }

    public List<CartModel> getListCart() {
        return dbHelper.getAllItems();
    }

    public int ItemCount()
    {
        return dbHelper.getTotalItemCount();
    }
}


=======
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.personnelcuisine.Activity.FoodDataModel;

import java.util.ArrayList;

import Domain.FoodsDomain;
import Interface.ChangeNumberItemsListener;
import android.content.Context;
import android.widget.Toast;
import com.example.personnelcuisine.Activity.FoodDataModel;
import com.example.personnelcuisine.Activity.MainActivity;

import java.util.ArrayList;
import Interface.ChangeNumberItemsListener;

public class ManagementCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDataModel item) {
        ArrayList<FoodDataModel> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;

        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getId()==(item.getId())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CartList", listFood);
        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();
    }


    public void plusNumberedFood(ArrayList<FoodDataModel> listFood, long id, ChangeNumberItemsListener changeNumberItemsListener) {
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getId() == id) {
                listFood.get(i).setNumberInCart(listFood.get(i).getNumberInCart() + 1);
                break;
            }
        }
        tinyDB.putListObject("CartList", listFood);
        changeNumberItemsListener.changed();
    }




    public void minusNumberedFood(ArrayList<FoodDataModel> listFood, long id, ChangeNumberItemsListener changeNumberItemsListener) {
        int z=0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getId() == id) {
                if (listFood.get(i).getNumberInCart() == 1) {
                    listFood.remove(i);
                } else {
                    listFood.get(i).setNumberInCart(listFood.get(i).getNumberInCart() - 1);
                    z=i;
                }
                break;
            }
        }
       tinyDB.putListObject("CartList", listFood);
       changeNumberItemsListener.changed();
        Log.d("CartListAdapter", "Quantity decreased for food item. ID: " + id+"  Quantity = "+listFood.get(z).getNumberInCart());
    }


    public void RemoveItem(ArrayList<FoodDataModel> listFood, long id, ChangeNumberItemsListener changeNumberItemsListener)
    {
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getId() == id) {
                listFood.remove(i);
                break;
            }
            }
        tinyDB.putListObject("CartList", listFood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDataModel> listfood = getListCart();
        double Fee = 0;
        for (int i = 0; i < listfood.size(); i++) {
            Fee += ((listfood.get(i).getPrice()) * listfood.get(i).getNumberInCart());
        }
        return Fee;
    }

    public ArrayList<FoodDataModel> getListCart() {
        ArrayList<FoodDataModel> cartList = tinyDB.getListObject("CartList");
        if (cartList == null) {
            return new ArrayList<FoodDataModel>(); // Return an empty list if CartList is null
        }
        return cartList;
    }
}
>>>>>>> b9de0588a140ec3d4d65fdd941174c75a96a2b48
