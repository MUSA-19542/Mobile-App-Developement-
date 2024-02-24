package Interface;

import com.example.personnelcuisine.Activity.FoodDataModel;

import java.util.List;

public interface FoodListCallback {
    void onFoodListReceived(List<FoodDataModel> foodList);
}