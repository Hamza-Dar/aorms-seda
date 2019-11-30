package com.example.aorms_seda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
//import com.bumptech.glide.Glide;
//import com.google.firebase.database.DatabaseReference;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterForCart  extends RecyclerView.Adapter<AdapterForCart.MyViewHolder>  {
    private ArrayList<DataListForCart> Array;
    CartItems MyCart = CartItems.get_Instance();
    Context context;
    //DatabaseReference myDatabaseReference;

    public AdapterForCart(ArrayList<DataListForCart> Array, Context context)
    {
        this.Array=Array;
        this.context=context;
    }
    @Override
    public AdapterForCart.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.cart_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterForCart.MyViewHolder holder, final int position) {
        if (Array != null && holder != null) {
            final DataListForCart Value = Array.get(position);
            //Glide.with(context)
            //        .load(Value.getURI())
            //        .into(holder.image);
            Picasso.get()
                    .load(Value.getURI())
                    .error(R.drawable.loaderror)
                    .into(holder.image);

            holder.itemName.setText(Value.getItemName());
            holder.quantity.setText(String.valueOf(Value.getQuantity()));

            holder.increaseQuanity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity= Value.getQuantity();
                    quantity=quantity+1;
                    MyCart.increseQuantity(Value);
                    Value.setQuantity(quantity);
                    Array.set(position,Value);
                    notifyItemChanged(position);// check it out
                }
            });
            holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        int quantity= Value.getQuantity();
                    if(quantity>1) {
                        quantity = quantity - 1;
                        MyCart.decreaseQuantity(Value);
                        Value.setQuantity(quantity);
                        Array.set(position, Value);
                        notifyItemChanged(position);// check it out
                    }
                }
            });
            holder.removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!Array.isEmpty())
                    {
                        DataListForCart item = Array.get(position);
                        Array.remove(position);
                        notifyItemRemoved(position);
                        MyCart.removeFromCart(item);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView itemName ,quantity;
        ImageView increaseQuanity,decreaseQuantity,removeItem;


        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_cart);
            itemName = itemView.findViewById(R.id.item_name);
            decreaseQuantity = itemView.findViewById(R.id.button_subtract);
            increaseQuanity = itemView.findViewById(R.id.button_add);

            quantity = itemView.findViewById(R.id.item_size);
            removeItem = itemView.findViewById(R.id.button_close);

        }
    }
}
