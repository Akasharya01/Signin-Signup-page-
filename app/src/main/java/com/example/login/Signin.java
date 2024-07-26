package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-4c83e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final EditText eaddress = findViewById(R.id.eaddress);
        final EditText Paasword = findViewById(R.id.Paasword);
        final Button logbtn = findViewById(R.id.logbtn);

        logbtn.setOnClickListener(view -> {


            final String emailTxt = eaddress.getText().toString();
            final String PaaswordTxt = Paasword.getText().toString();

            if(emailTxt.isEmpty() || PaaswordTxt.isEmpty()){
                Toast.makeText(Signin.this, "Please enter email or password", Toast.LENGTH_SHORT).show();
            }
            else{

                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //check if mobile is exits in firebase database
                        if (snapshot.hasChild(emailTxt)){
                            //email is exist in firebase databse
                            //now get password
                            final String getpaasword = snapshot.child(emailTxt).child("Paasword").getValue(String.class);

                            assert getpaasword != null;
                            if (getpaasword.equals(PaaswordTxt)){
                                Toast.makeText(Signin.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signin.this, MainActivity.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        logbtn.setOnClickListener(view -> startActivity(new Intent( Signin.this, Register.class) ));
    }


}