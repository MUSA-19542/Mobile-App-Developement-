package Domain;

public class CartModel {

    long id;

    long food_id;

    private String item_name;
    private int Price;
    private byte[] Image;

    int numberOrder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFood_id() {
        return food_id;
    }

    public void setFood_id(long food_id) {
        this.food_id = food_id;
    }



    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public CartModel( long food_id, String item_name, int price, byte[] image, int numberOrder) {
        this.food_id = food_id;
        this.item_name = item_name;
        Price = price;
        Image = image;
        this.numberOrder = numberOrder;
    }
}
