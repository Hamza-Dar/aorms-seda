package com.example.aorms_seda;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        MenuArray.add(new DataListOfMenu("https://www.foodsforbetterhealth.com/wp-content/uploads/2017/01/onion-sandwitch-750x400.jpg","Sandwitch","BBQ Sauces and Grilled chicken",350));
        MenuArray.add(new DataListOfMenu("https://www.ndtv.com/cooks/images/dum.murg.ki.kacchi.biryani.jpg","Biryani","Chiken piece, Mint Raita",350));
        MenuArray.add(new DataListOfMenu("https://www.foodsforbetterhealth.com/wp-content/uploads/2017/01/onion-sandwitch-750x400.jpg","Qorma","Achari, Creamy and spicy",350));
        MenuArray.add(new DataListOfMenu("https://www.foodsforbetterhealth.com/wp-content/uploads/2017/01/onion-sandwitch-750x400.jpg","Seekh Kabaab","With Mayo, sauces and dips",350));
        MenuArray.add(new DataListOfMenu("https://www.foodsforbetterhealth.com/wp-content/uploads/2017/01/onion-sandwitch-750x400.jpg","Shami Burger","With Mayo, sauces and dips",350));
        MenuArray.add(new DataListOfMenu("https://www.foodsforbetterhealth.com/wp-content/uploads/2017/01/onion-sandwitch-750x400.jpg","Steak","With Mayo, sauces and dips",350));
        MenuArray.add(new DataListOfMenu("https://www.foodsforbetterhealth.com/wp-content/uploads/2017/01/onion-sandwitch-750x400.jpg","Molten Lava","With Mayo, sauces and dips",350));
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
