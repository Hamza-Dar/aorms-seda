package com.example.aorms_seda;
import java.util.ArrayList;

public class CartItems {

    private ArrayList<DataListForCart> CartItems;

    private static CartItems single_instance = null;

    private CartItems() {

        CartItems = new ArrayList<>();
    }
    public static CartItems get_Instance() {
        if (single_instance == null)
            single_instance = new CartItems();

        return single_instance;
    }
    public ArrayList<DataListForCart> getCartItems() {
        return CartItems;
    }

    public boolean consists(DataListForCart item){
        for(int i = CartItems.size()-1; i>=0; i--){
            if(CartItems.get(i).itemName.equals(item.itemName)){
                return true;
            }
        }
        return false;
    }
    private int index(DataListForCart item){
        int index = -1;
        for(int i = CartItems.size()-1; i>=0; i--){
            if(CartItems.get(i).itemName.equals(item.itemName)){
                index = i;
                return index;
            }
        }
        return index;
    }
    public void removeFromCart(DataListForCart item){
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            CartItems.remove(indexOf);
            CartItems.notifyAll();
        }
    }
    public void addToCart(DataListForCart item){
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            int quantity = CartItems.get(indexOf).getQuantity() + 1;
            CartItems.get(indexOf).setQuantity(quantity);
        }
        else CartItems.add(item);
    }

    public void EmptyCart(){
        CartItems.clear();
    }

    public int getSize() {
        return CartItems.size();
    }

    public void decreaseQuantity(DataListForCart item) {
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            int quantity = CartItems.get(indexOf).getQuantity() - 1;
            CartItems.get(indexOf).setQuantity(quantity);
        }
    }
    public void increseQuantity(DataListForCart item) {
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            int quantity = CartItems.get(indexOf).getQuantity() + 1;
            CartItems.get(indexOf).setQuantity(quantity);
        }
    }
}
