package com.fast.tiffan_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class menuaroms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterForMenu adapter;
    private CartItems MyCart = CartItems.get_Instance();
    private ArrayList<DataListOfMenu> MenuArray;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuaroms);
        //Intent i = getIntent();
        //MyCart = (CartItems) i.getSerializableExtra("cartObject");
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Menu");
       // mDatabase.keepSynced(true);
        Context context = getApplicationContext();
        MenuArray= new ArrayList<DataListOfMenu>();
        //FirebaseAuth mAuth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       // fetch();
        fillarray();
        adapter = new AdapterForMenu(MenuArray,MyCart.getCartItems(), getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
    public void fillarray()
    {
        MenuArray.add(new DataListOfMenu("@drawable/toast","Sandwitch","BBQ Sauces and Grilled chicken",350));
        MenuArray.add(new DataListOfMenu("https://www.ndtv.com/cooks/images/dum.murg.ki.kacchi.biryani.jpg","Biryani","Chiken piece, Mint Raita",350));
        MenuArray.add(new DataListOfMenu("@drawable/toast","Qorma","Achari, Creamy and spicy",350));
        MenuArray.add(new DataListOfMenu("@drawable/toast","Seekh Kabaab","With Mayo, sauces and dips",350));
        MenuArray.add(new DataListOfMenu("@drawable/toast","Shami Burger","With Mayo, sauces and dips",350));
        MenuArray.add(new DataListOfMenu("@drawable/toast","Steak","With Mayo, sauces and dips",350));
        MenuArray.add(new DataListOfMenu("@drawable/toast","Molten Lava","With Mayo, sauces and dips",350));
    }
   /* private void fetch() {
        Query query=FirebaseDatabase.getInstance().getReference().child("Menu");
        FirebaseRecyclerOptions<DataListOfMenu> options = new FirebaseRecyclerOptions.Builder<DataListOfMenu>()
                .setQuery(query, new SnapshotParser<DataListOfMenu>() {
                    @NonNull
                    @Override
                    public DataListOfMenu parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new DataListOfMenu(
                                Objects.requireNonNull(snapshot.child("URI").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("menu_title").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("menu_description").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("price").getValue()).toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DataListOfMenu, menuaroms.MenuViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, final int i, @NonNull DataListOfMenu dataListOfMenu) {
                try {
                    Picasso.get().load(dataListOfMenu.getURI()).into(menuViewHolder.image);
                    menuViewHolder.menu_title.setText(dataListOfMenu.getMenu_title());
                    menuViewHolder.menu_description.setText(dataListOfMenu.getMenu_description());
                    menuViewHolder.price.setText(dataListOfMenu.getPrice());

                    menuViewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataListOfMenu item = (DataListOfMenu) adapter.getItem(i);
                            try {

                                String URI = item.getURI();
                                String title = item.getMenu_title();
                                String price = item.getPrice();

                                Toast.makeText(getApplicationContext() , "Item Added To Cart" , Toast.LENGTH_LONG).show();
                                DataListForCart cartItem = new DataListForCart(URI , title);
                                MyCart.addToCart(cartItem);
                            }
                            catch(Exception A)
                            {
                                Toast.makeText(getApplicationContext() , "Product Cannot be added at the moment! "  + A.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                catch (Exception Ex)
                {
                    Toast.makeText(getApplicationContext() , "OnBindViewHolder  " + Ex.toString() , Toast.LENGTH_LONG).show();
                }
            }

            @NonNull
            @Override
            public menuaroms.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent , false);
                return new MenuViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView menu_title,menu_description,price;
        Button addToCart;
        public MenuViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_menu);
            menu_title = itemView.findViewById(R.id.txt_Title);
            menu_description = itemView.findViewById(R.id.txt_SEO);
            price = itemView.findViewById(R.id.txt_amount);
            addToCart = itemView.findViewById(R.id.btn_addToCart);

        }
    }*/
    public void backToManager(View v)
    {
        MyCart.EmptyCart();
        this.finish();
    }
    public void gotoCart(View v)
    {
        Intent i = new Intent(this, cartaroms.class);
        this.finish();
        startActivity(i);
    }
}
