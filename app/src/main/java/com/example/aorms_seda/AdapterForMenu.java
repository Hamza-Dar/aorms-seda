package com.example.aorms_seda;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
//import com.google.firebase.database.DatabaseReference;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterForMenu extends RecyclerView.Adapter<AdapterForMenu.MyViewHolder>  {
    private ArrayList<DataListOfMenu> MenuArray;
    private ArrayList<DataListForCart> Array;
    CartItems MyCart = CartItems.get_Instance();
    Context context;
    //DatabaseReference myDatabaseReference;

    public AdapterForMenu(ArrayList<DataListOfMenu> menuArray, ArrayList<DataListForCart> Array, Context context)
    {
        this.MenuArray=menuArray;
        this.Array=Array;
        this.context=context;
    }
    @Override
    public AdapterForMenu.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.product,parent,false);
        return new AdapterForMenu.MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (MenuArray != null && holder != null) {
            final DataListOfMenu Value = MenuArray.get(position);

            Picasso.get()
                    .load(Value.getUri())
                    .error(R.drawable.loaderror)
                    .into(holder.image);
            holder.itemName.setText(Value.getName());
            holder.amount.setText(""+Value.getPrice()+"");
            DataListForCart catee = new DataListForCart(Value.getId(),Value.getUri() , Value.getName());
            holder.addToCart.setEnabled(true);
            holder.addToCart.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    DataListOfMenu item = (DataListOfMenu) MenuArray.get(position);
                    try {


                        String URI = item.getUri();
                        String id=item.getId();
                        String title = item.getName();
                        DataListForCart cartItem = new DataListForCart(id,URI , title);
                        if(!(MyCart.consists(cartItem))) {
                            Toast.makeText(context, "Item Added To Cart", Toast.LENGTH_LONG).show();
                            holder.addToCart.setBackgroundColor(R.color.red);
                            MyCart.addToCart(cartItem);
                        }
                        else
                        {
                            Toast.makeText(context, "Already Added", Toast.LENGTH_LONG).show();
                        }

                    }
                    catch(Exception A)
                    {
                        Toast.makeText(context , "Product Cannot be added at the moment! "  + A.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }



    private String toString(int price) {
        return(""+price+"");
    }

    @Override
    public int getItemCount() {
        return MenuArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView itemName ,description,amount;
        Button addToCart;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_menu);
            itemName = itemView.findViewById(R.id.txt_Title);
            description=itemView.findViewById(R.id.txt_SEO);
            addToCart = itemView.findViewById(R.id.btn_addToCart);
            amount=itemView.findViewById(R.id.txt_amount);
        }
    }
}
