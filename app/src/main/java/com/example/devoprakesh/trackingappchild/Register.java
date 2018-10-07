package com.example.devoprakesh.trackingappchild;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button sendotp,btnverify;
    String phonenumber,strotp;
    EditText regphno,regOTP;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("ChildLoginDetails",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sendotp = findViewById(R.id.submitno);

        btnverify = findViewById(R.id.optverify);

        regphno = findViewById(R.id.regphonenumber);

        regOTP = findViewById(R.id.regotp);

        mAuth = FirebaseAuth.getInstance();






    }
}
