package Helper;

import android.content.Context;
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


