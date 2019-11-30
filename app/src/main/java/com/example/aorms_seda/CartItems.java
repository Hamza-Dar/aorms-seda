package com.example.aorms_seda;import java.util.ArrayList;

public class CartItems {

    public ArrayList<DataListForCart> cartItems;
    public ArrayList<FoodId> ids;
    public static CartItems single_instance = null;

    private CartItems() {

        cartItems = new ArrayList<>();
        ids = new ArrayList<>();
    }
    public static CartItems get_Instance() {
        if (single_instance == null)
            single_instance = new CartItems();

        return single_instance;
    }
    public String getNID(String name)
    {
        for(FoodId f : ids)
        {
            if(f.Name.equals(name))
            {
                return f.id;
            }
        }
        return name;
    }
    public ArrayList<DataListForCart> getCartItems() {
        return cartItems;
    }

    public boolean consists(DataListForCart item){
        for(int i = cartItems.size()-1; i>=0; i--){
            if(cartItems.get(i).itemName.equals(item.itemName)){
                return true;
            }
        }
        return false;
    }
    private int index(DataListForCart item){
        int index = -1;
        for(int i = cartItems.size()-1; i>=0; i--){
            if(cartItems.get(i).itemName.equals(item.itemName)){
                index = i;
                return index;
            }
        }
        return index;
    }
    public void removeFromCart(DataListForCart item){
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            cartItems.remove(indexOf);
            cartItems.notifyAll();
        }
    }
    public void addToCart(DataListForCart item){
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            int quantity = cartItems.get(indexOf).getQuantity() + 1;
            cartItems.get(indexOf).setQuantity(quantity);
        }
        else cartItems.add(item);
    }

    public void EmptyCart(){
        cartItems.clear();
    }

    public int getSize() {
        return cartItems.size();
    }

    public void decreaseQuantity(DataListForCart item) {
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            int quantity = cartItems.get(indexOf).getQuantity() - 1;
            cartItems.get(indexOf).setQuantity(quantity);
        }
    }
    public void increseQuantity(DataListForCart item) {
        if(CartItems.this.consists(item)){
            int indexOf = CartItems.this.index(item);
            int quantity = cartItems.get(indexOf).getQuantity() + 1;
            cartItems.get(indexOf).setQuantity(quantity);
        }
    }
}
