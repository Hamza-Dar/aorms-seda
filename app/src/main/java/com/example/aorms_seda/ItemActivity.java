package com.example.aorms_seda;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView ingredientRecyclerView;
    private List<Ingredient> ItemActivity_IngredientsList;
    private IngredientAdapter adapter;
    Button increaseQuantityBtn;
    Button decreaseQuantityBtn;
    Button addOrderItemBtn;
    TextView quantity_edit_txt;
    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent in = getIntent();

        FoodItem foodItem = (FoodItem) in.getExtras().get("item");
        TextView iname = findViewById(R.id.ItemActivity_contact_name);
        TextView iprice = findViewById(R.id.ItemActivity_item_price);
        iprice.setText(Integer.toString(foodItem.price));

        iname.setText(foodItem.name);

        quantity_edit_txt = (TextView) findViewById(R.id.quantity_value);

        increaseQuantityBtn = (Button) findViewById(R.id.quantity_increase);
        decreaseQuantityBtn = (Button) findViewById(R.id.quantity_decrease);
        addOrderItemBtn = (Button) findViewById(R.id.addOrderItemBtn);

        increaseQuantityBtn.setOnClickListener(this);
        decreaseQuantityBtn.setOnClickListener(this);
        addOrderItemBtn.setOnClickListener(this);

    }

//    private List<Ingredient> getDummyData(){
//        List<Ingredient> temp = new ArrayList<Ingredient>();
//        Ingredient ingredient;
//        String name;
//        for (int i=0; i<7; i++){
//            name = "Ingredient " + (i+1);
//            ingredient = new Ingredient(name, "1", 2, 2);
//            temp.add(ingredient);
//            Log.e("DATA", "getDummyData: "+ name);
//        }
//
//        return temp;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quantity_increase: {
                quantity++;
                quantity_edit_txt.setText(Integer.toString(quantity));
                break;
            }
            case R.id.quantity_decrease: {
                quantity--;
                quantity = Math.max(0, quantity);
                quantity_edit_txt.setText(Integer.toString(quantity));
                break;
            }
            case R.id.addOrderItemBtn: {
                FoodItem currentItem = (FoodItem) getIntent().getSerializableExtra("item");

                OrderItem orderItem = new OrderItem(currentItem, quantity );

                OrderedItemsQueue queue = OrderedItemsQueue.Singleton();
                queue.addOrderItem(orderItem);
//                Intent x = getIntent();
//                String name = x.getExtras().getString("Title");
//                Intent intent = new Intent(ItemActivity.this, FoodOrderMainMenu.class);
//                intent.putExtra("Title", name);
//                startActivity(intent);
                finish();
                break;
            }
        }

    }
}