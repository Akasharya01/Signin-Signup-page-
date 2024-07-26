package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    //create object or databasereference to access firebase RealtimeDatabase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-4c83e-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText Fullname = findViewById(R.id.Fname);
        final EditText Email = findViewById(R.id.emailadd);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText Paasword = findViewById(R.id.regPaasword);
        final EditText Confirmpaasword = findViewById(R.id.Conpassword);
        final Button registerBtn = findViewById(R.id.regbtn);
        final TextView loginBtn = findViewById(R.id.loginbtn);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  get data from edittexts into string variables
                final String fullnameTxt = Fullname.getText().toString();
                final String emailTxt = Email.getText().toString();
                final String mobileTxt = mobile.getText().toString();
                final String PaaswordTxt = Paasword.getText().toString();
                final String ConfirmpasswordTxt = Confirmpaasword.getText().toString();

                // check if user fill all the fields before sending data to firebase

                if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || mobileTxt.isEmpty() || PaaswordTxt.isEmpty()){
                    Toast.makeText(Register.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }

                else if(PaaswordTxt.equals(ConfirmpasswordTxt)){
                    Toast.makeText(Register.this, "Passowor are not maching", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //
                            if (snapshot.hasChild(mobileTxt)){
                                Toast.makeText(Register.this, "Number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //storing data firebase Realtime database
                                // We are using phone as unique identity
                                databaseReference.child("users").child(mobileTxt).child(String.valueOf(Fullname)).setValue(fullnameTxt);
                                databaseReference.child("users").child(mobileTxt).child(String.valueOf(Email)).setValue(emailTxt);
                                databaseReference.child("users").child(mobileTxt).child(String.valueOf(Paasword)).setValue(PaaswordTxt);

                                Toast.makeText(Register.this, "User register succesful", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //storing data firebase Realtime database
                    // We are using phone as unique identity
                     databaseReference.child("users").child(mobileTxt).child(String.valueOf(Fullname)).setValue(fullnameTxt);
                    databaseReference.child("users").child(mobileTxt).child(String.valueOf(Email)).setValue(emailTxt);
                    databaseReference.child("users").child(mobileTxt).child(String.valueOf(Paasword)).setValue(PaaswordTxt);

                    Toast.makeText(Register.this, "User register succesful", Toast.LENGTH_SHORT).show();
                    finish();

                }

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
