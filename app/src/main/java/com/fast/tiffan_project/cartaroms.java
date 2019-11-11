package com.fast.tiffan_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class cartaroms extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    AdapterForCart adapter;
    private CartItems MyCart=CartItems.get_Instance();
    DatabaseReference myDatabaseReference;
    private FirebaseAuth mAuth;
    Button proceed;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartaroms);
        try {
            //Intent i = getIntent();
            //MyCart = (CartItems) i.getSerializableExtra("cartObject");
            context = getApplicationContext();
            proceed =findViewById(R.id.buttonplace);
            recyclerView = (RecyclerView)findViewById(R.id.recyclerC);
            settingTheRecyclerView();
           // myDatabaseReference = FirebaseDatabase.getInstance().getReference();
            //mAuth = FirebaseAuth.getInstance();

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrderFireBase();
            }
        });
    }

    private void placeOrderFireBase() {
        Toast.makeText(context, "OrderPlaced Successfully", Toast.LENGTH_LONG).show();
        MyCart.EmptyCart();
        this.finish();
        /*
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.
        if (!phone.equals("notsaved")) {
            myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order").push();
            myDatabaseReference.setValue(MyCart);
            myDatabaseReference.child("Status").setValue("Pending");
            MyCart.EmptyCart();
            adapter.notifyDataSetChanged();
        }*/
    }
    private void settingTheRecyclerView() {
        adapter = new AdapterForCart(MyCart.getCartItems(), context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
    public void back2(View v)
    {
        Intent i = new Intent(this, menuaroms.class);
        this.finish();
        startActivity(i);
    }
}

