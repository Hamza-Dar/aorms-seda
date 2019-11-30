package com.example.aorms_seda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class assignTableToTabletActivity extends AppCompatActivity {
    String id;
    String myTabletNo;
    TextView  tabletNo;
    TextView tableNo;
    Button assignButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Tablet");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_table_to_tablet);
        tabletNo = findViewById(R.id.tablet_to_assign);
        tableNo=findViewById(R.id.assigned_tableno);
        assignButton = findViewById(R.id.tableno_assign_button);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> message=new ArrayList<>();
        message = bundle.getStringArrayList("message");
        id=message.get(0);
        myTabletNo =message.get(1);
        tabletNo.setText(myTabletNo);

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(tableNo.getText().toString());
//                    Toast.makeText(getApplicationContext(),"Enter Number",Toast.LENGTH_LONG).show();
                    orderRef.document(id).update("Table",number);
                    Toast.makeText(getApplicationContext(),"Tablet assigned",Toast.LENGTH_LONG).show();
                    finish();
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Enter Number",Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}
