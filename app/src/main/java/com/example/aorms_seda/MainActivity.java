package com.demotxt.myapp.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> lstBook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstBook = new ArrayList<>();
        lstBook.add(new Book("Fast Food","Fast Food","Burgers, Pizza",R.drawable.fast));
        lstBook.add(new Book("Mexican Food","Spicy Food","Carne Adobada, Tostadas",R.drawable.mex));
        lstBook.add(new Book("Chinese Food","Chinese","Chow Mein, Kung Pao Chicken",R.drawable.chinese));
        lstBook.add(new Book("Desi Food","Desi Special","Pulao, Biryani, Quorma, Chicken Roast, Kabab",R.drawable.desi));
        lstBook.add(new Book("Italian Food","Italian Dishes","Alfredo, Puticino",R.drawable.italian));
        lstBook.add(new Book("Others","Snacks","French Fries, Cold Drinks",R.drawable.logo));
        lstBook.add(new Book("Fast Food","Fast Food","Burgers, Pizza",R.drawable.fast));
        lstBook.add(new Book("Mexican Food","Spicy Food","Carne Adobada, Tostadas",R.drawable.mex));


        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);


    }
}
