package com.example.aorms_seda;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;

public class cartaroms extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    AdapterForCart adapter;
    CartItems MyCart=CartItems.get_Instance();
    ///DatabaseReference myDatabaseReference;
    private FirebaseAuth mAuth;
    Button proceed;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] tables = new String[] { "T1", "T2",
                "T3", "T4", "T5"};
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
            final Spinner spinner = (Spinner) findViewById(R.id.spinner2);
            /*spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(),tables[i] , Toast.LENGTH_SHORT).show();
                }
            });*/
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, tables);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

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
    }
    private void settingTheRecyclerView() {
        adapter = new AdapterForCart(MyCart.getCartItems(), context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
    public void back2(View v)
    {
        Intent i = new Intent(this,menuaroms.class);
        this.finish();
    }
}


