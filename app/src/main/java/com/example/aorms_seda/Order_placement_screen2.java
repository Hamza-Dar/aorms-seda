package com.example.aorms_seda;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Order_placement_screen2 extends AppCompatActivity {

    List<Food> lst_food;
    myadapter myAdapter;
    QueryDocumentSnapshot foodItem;
    ImageView cart_queue;
    Context mContext;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = getIntent();
        mContext=getApplicationContext();
        String type = in.getExtras().getString("Title");
        setContentView(R.layout.activity_order_placement_screen2);
        cart_queue= findViewById(R.id.cart_image_foodorder);                                         // adding cart logo button


       // cart wala kam idhar se karna ha
        cart_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, cartOrderPlacement.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // start the activity
                Intent x = getIntent();
                String name = x.getExtras().getString("Title");
                intent.putExtra("Title", name);
                mContext.startActivity(intent);
            }
        });

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        lst_food = new ArrayList<>();
        firestore.collection("Fooditem").whereEqualTo("Type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //lst_food.add(new Food(document.get("Type").toString(), document.get("Type").toString(), document.get("Type").toString(), R.drawable.fast));
                        String name = document.get("Name").toString();
                        lst_food.add(new Food(name, name, name, R.drawable.fast));
                    }
                    initializeRcv();
                }
            }
        });
    }

    protected void initializeRcv() {

        RecyclerView myrv = (RecyclerView) findViewById(R.id.FoodOrdersecondScreen_rcv);
        myAdapter = new myadapter(this, lst_food);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);

    }

    public class myadapter extends RecyclerView.Adapter<Order_placement_screen2.myadapter.myholder> {


        private Context mContext;
        private List<Food> mData;
        private String id;

        public myadapter(Context mContext, List<Food> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        public void updateit(List<Food> dat) {
            mData = dat;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.second_screen_cardview, parent, false);
            return new myholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final myholder holder, final int position) {
            Intent in = getIntent();
            name = in.getExtras().getString("Title");
            final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String ref = "";
            lst_food = new ArrayList<>();
            ;
            firestore.collection("Fooditem").whereEqualTo("Type", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        FoodItem item = null;
                        List<FoodItem> fitems = new ArrayList<>();
                        for (QueryDocumentSnapshot document :
                                task.getResult()) {
                            String id = document.getId().toString(), name = document.get("Name").toString(), available = document.get("Available").toString();
                            int time = Math.toIntExact((Long) document.get("Time")), price = Math.toIntExact((Long) document.get("Price"));

                            //lst_food.add(new Food(document.get("Type").toString(), document.get("Type").toString(), document.get("Type").toString(), R.drawable.fast));
                            item = new FoodItem(name, id, available, price, time);
                            fitems.add(item);
                        }
                        initIntent(holder, position, fitems);

                    }
                }
            });
//            firestore.collection("Orders").whereEqualTo("Table", 5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        List<String> list = new ArrayList<>();
//                        List<Map> items = new ArrayList<>();
//                        FoodItem item = null;
//                        for (QueryDocumentSnapshot document :
//                                task.getResult()) {
//                            items = (List<Map>) document.get("Items");
//                        }
//                        //items.get(0).get()
//                        DocumentReference ref = (DocumentReference) items.get(1).get("foodItem");
//                        id = ref.getPath().split("/")[0];
//                        //getItem(id);
//                        firestore.collection(id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document :
//                                            task.getResult()) {
//                                        String b = "adasdsa";
//
//                                    }
//                                }
//                            }
//                        });
//                    }
//                }
//            });
        }

        public void initIntent(@NonNull myholder holder, final int position, final List<FoodItem> item) {
            holder.tv_book_title.setText(mData.get(position).getTitle());

            holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ItemActivity.class);

                    FoodItem ix = null;
                    for (FoodItem i: item){
                        if(i.name.equals(mData.get(position).getTitle())){
                            ix = i;
                            break;
                        }
                    }
                    // passing data to the book activity
                    intent.putExtra("item", ix);
                    intent.putExtra("Title", name);

                    // start the activity
                    mContext.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class myholder extends RecyclerView.ViewHolder {

            TextView tv_book_title;
            ImageView img_book_thumbnail;
            CardView cardView;

            public myholder(View itemView) {
                super(itemView);
                tv_book_title = (TextView) itemView.findViewById(R.id.FoodOrderSecondMenu_foodTypeName);
                img_book_thumbnail = (ImageView) itemView.findViewById(R.id.FoodOrderSecondMenu_foodTypeImg);
                cardView = (CardView) itemView.findViewById(R.id.cardview_id);


            }
        }

    }

}



