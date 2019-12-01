package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View v){
//        Intent i = new Intent(this, LandingPage.class);
//        startActivity(i);
        open();
    }
    Intent i1;
    public void openMenu(View v){
        i1 = new Intent(this, FoodOrderMainMenu.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter The Table Number");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER );
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i = Integer.valueOf(input.getText().toString());
                i1.putExtra("TableNumber", i);
                startActivity(i1);
            }
        });

        builder.show();
    }

    void open(){
        TextView em, pass;
        em =  findViewById(R.id.email_login);
        pass = findViewById(R.id.password_login);
        String Email = em.getText().toString();
        String Password = pass.getText().toString();

        FirebaseFirestore.getInstance().collection("Login")
                .whereEqualTo("Username", Email)
                .whereEqualTo("Password", Password)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful() && task.getResult()!=null){
                    if(task.getResult().getDocuments().size()>0){
                        DocumentSnapshot d =task.getResult().getDocuments().get(0);
                        String role = d.getString("Role");
                        if(role == null)
                            return;
                        switch (role) {
                            case "Kitchen Manager": {

                                Intent i = new Intent(getApplicationContext(), kitchenActivity.class);
                                startActivity(i);
                                break;
                            }
                            case "Sales": {
                                Intent i = new Intent(getApplicationContext(), ReportsActivity.class);
                                startActivity(i);
                                break;
                            }
                            case "Hall Manager":
                                startActivity(new Intent(getApplicationContext(), GuiDemoL164348.class));
                                finish();
                                break;
                            case "Inventory Manager": {
                                Intent i = new Intent(getApplicationContext(), InventoryManager.class);
                                startActivity(i);
                                break;
                            }
                            case "Everything": {
                                Intent i = new Intent(getApplicationContext(), LandingPage.class);
                                startActivity(i);
                                break;
                            }
                            default: {
                                Intent i = new Intent(getApplicationContext(), MenuOption.class);
                                startActivity(i);
                                break;
                            }
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Username Or password Incorrect", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Username Or password Incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}