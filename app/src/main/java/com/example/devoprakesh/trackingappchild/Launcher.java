package com.example.devoprakesh.trackingappchild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Launcher extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        sharedPreferences = getSharedPreferences("ChildLoginDetails",MODE_PRIVATE);

        uid = sharedPreferences.getString("UID",null);


        if(uid==null){

            Intent intent = new Intent(Launcher.this,Register.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(Launcher.this,Home.class);
            startActivity(intent);
        }


    }
}
