package com.example.aorms_seda;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class kitchenActivity extends AppCompatActivity {

    //load cooks;

    private FirebaseFirestore db;
    ArrayList<Cook>Chefs;
    String address;
    Cook chef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        db=FirebaseFirestore.getInstance();
        CollectionReference dbChefs=db.collection("Employee");
        Chefs=new ArrayList<>();
        chef=new Cook(0,null,null);

        //load chefs and their specialities

        dbChefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {

                    chef.setName((String) documentSnapshot.getData().get("Name"));
                    Log.i("Name",chef.getName());
                }
            }
        });

        //add firebase real time listener for orders;



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment SelectedFragment=null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    SelectedFragment=new HomeFragment();
                    break;
                case R.id.nav_queues:
                    SelectedFragment=new QueuesFragment();
                    break;
                case R.id.nav_notify:
                    SelectedFragment=new NotifyFragment();
                    break;
                case R.id.nav_request:
                    SelectedFragment=new RequestFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,SelectedFragment).commit();
            return true;
        }
    };
}
