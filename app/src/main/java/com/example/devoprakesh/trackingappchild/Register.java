package com.example.devoprakesh.trackingappchild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    Button sendotp,btnverify;
    String phonenumber,strotp;
    EditText regphno,regOTP;

    String verifynumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

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

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                phonenumber = regphno.getText().toString();

                setVisibileElements();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phonenumber,
                        60,
                        TimeUnit.SECONDS,
                        Register.this,
                        mCallbacks
                );


            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.i("onVerification ","Verified");
                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                mVerificationId = s;
                mResendToken = forceResendingToken;

                //regOTP.setText(s);

            }
        };





    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Log.i("UserSignin -> ","Sucess");
                            FirebaseUser user = task.getResult().getUser();
                            editor.putString("Number",user.getPhoneNumber());
                            editor.apply();

                            Intent intent = new Intent(Register.this,UserDetails.class);
                            startActivity(intent);
                            finish();
                        }else{

                            Log.i("User Phone Error - >",""+task.getException());
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        verifynumber = sharedPreferences.getString("Number",null);
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null && verifynumber.equals(user.getPhoneNumber())){

            Intent intent = new Intent(Register.this,Home.class);
            startActivity(intent);
            finish();
        }
    }


    public void setVisibileElements(){

        btnverify.setVisibility(View.VISIBLE);
        regOTP.setVisibility(View.VISIBLE);
        sendotp.setVisibility(View.INVISIBLE);
        regphno.setVisibility(View.INVISIBLE);
    }
}
