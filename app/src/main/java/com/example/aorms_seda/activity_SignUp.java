package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_SignUp extends AppCompatActivity {
    private static final String KEY_TITLE = "username";
    private static final String KEY_DESCRIPTION = "password";
    private EditText editTextusername;
    private EditText editTextpassword;
    private TextView textViewData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("Login/Manager");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sign_up);



        Button btn2 = (Button) findViewById(R.id.button2);
        editTextusername= findViewById(R.id.editText4);
        editTextpassword= findViewById(R.id.editText5);
        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String username = editTextusername.getText().toString();
                final String password = editTextpassword.getText().toString();
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String title = documentSnapshot.getString(KEY_TITLE);
                                    String description = documentSnapshot.getString(KEY_DESCRIPTION);
                                   //Toast.makeText(activity_SignUp.this, username +"\n"+ password, Toast.LENGTH_SHORT).show();
                                    if (username.equals(title) && password.equals(description)){
                                        startActivity(new Intent(activity_SignUp.this, GuiDemoL164348.class));
                                        finish();
                                    } else {
                                        //Map<String, Object> note = documentSnapshot.getData();
                                        Toast.makeText(activity_SignUp.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                                    }//textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                                } else {
                                    Toast.makeText(activity_SignUp.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity_SignUp.this, "Error!", Toast.LENGTH_SHORT).show();
                              // startActivity(new Intent(activity_SignUp.this, GuiDemoL164348.class));
                                ///Log.d(TAG, e.toString());
                            }
                        });
                // TODO Auto-generated method stub
              //  startActivity(new Intent(activity_SignUp.this, GuiDemoL164348.class));
            }
        });
    }
}
