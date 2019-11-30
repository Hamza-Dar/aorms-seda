package com.example.aorms_seda;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import butterknife.ButterKnife;

public class menuaroms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private FirestoreRecyclerAdapter adapter;
    private Context context;
    //private AdapterForMenu adapter;
    Source source;
    LinearLayoutManager linearLayoutManager;
    private CartItems MyCart = CartItems.get_Instance();
    private ArrayList<DataListOfMenu> MenuArray;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuaroms);
        //ButterKnife.bind(this);
        firestore =  FirebaseFirestore.getInstance();
        context = getApplicationContext();
        MenuArray= new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        fetch();
    }
   private void fetch() {
   Query query = firestore.collection("Fooditem");
        FirestoreRecyclerOptions<DataListOfMenu> response;
        try {
            response = new FirestoreRecyclerOptions.Builder<DataListOfMenu>()
                    .setQuery(query, DataListOfMenu.class)
                    .build();
            adapter = new FirestoreRecyclerAdapter<DataListOfMenu, MenuViewHolder>(response) {
                @Override
                public void onBindViewHolder(final MenuViewHolder menuViewHolder,final int i, DataListOfMenu dataListOfMenu) {
                        try {
                            Picasso.get()
                                    .load(dataListOfMenu.getUri())
                                    .error(R.drawable.loaderror)
                                    .into(menuViewHolder.image);
                            menuViewHolder.menu_title.setText(dataListOfMenu.getName());
                            menuViewHolder.menu_description.setText(dataListOfMenu.getType());
                            menuViewHolder.price.setText(dataListOfMenu.getPrice().toString());
                            if (dataListOfMenu.getAvailable().equals("Yes"))
                            {
                                menuViewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DataListOfMenu item = (DataListOfMenu) adapter.getItem(i);
                                        try {

                                            String URI = item.getUri();
                                            String id = item.getId();
                                            String title = item.getName();
                                            DataListForCart cartItem = new DataListForCart(id, URI, title);
                                            if (!(MyCart.consists(cartItem))) {
                                                Toast.makeText(getApplicationContext(), "Item Added To Cart", Toast.LENGTH_LONG).show();
                                                MyCart.addToCart(cartItem);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Quantity increased", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (Exception A) {
                                            Toast.makeText(getApplicationContext(), "Product Cannot be added at the moment! " + A.toString(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                menuViewHolder.addToCart.setBackgroundColor(getResources().getColor(R.color.red));
                                menuViewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(), "Not available yet", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } catch (Exception Ex) {
                            Toast.makeText(getApplicationContext(), "OnBindViewHolder  " + Ex.toString(), Toast.LENGTH_LONG).show();
                        }
                }
                @Override
                public MenuViewHolder onCreateViewHolder(ViewGroup group, int i) {
                    View view = LayoutInflater.from(group.getContext())
                            .inflate(R.layout.product, group, false);
                    return new MenuViewHolder(view);
                }
                @Override
                public void onError(FirebaseFirestoreException e) {
                    Log.e("error", e.getMessage());
                }
            };
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
        catch(Exception A)
        {
            Toast.makeText(getApplicationContext() , "query! "  + A.toString(),
                    Toast.LENGTH_LONG).show();
        }

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
            //ButterKnife.bind(this, itemView);

        }
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
    public void backToManager(View v)
    {
        MyCart.EmptyCart();
        this.finish();
    }
    public void gotoCart(View v)
    {
        if(MyCart.getSize()!=0)
        {
            Intent i = new Intent(this, cartaroms.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Sorry, Add some items in cart", Toast.LENGTH_LONG).show();
        }

    }

}
