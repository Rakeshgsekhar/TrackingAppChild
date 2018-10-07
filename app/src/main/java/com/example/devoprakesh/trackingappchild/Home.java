package com.example.devoprakesh.trackingappchild;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    Button startSer,stopSer;
    private static final int PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startSer = findViewById(R.id.startServicebtn);
        stopSer = findViewById(R.id.stopservicebtn);

        LocationManager locationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(Home.this,
                    "Please enable location service GPS",Toast.LENGTH_LONG).show();
            finish();
        }

        startSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartServ();
            }
        });

        stopSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopSer();
            }
        });

        int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED){


        }else{


            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull
            int[] grantResults) {


        if(requestCode == PERMISSION_REQUEST && grantResults.length == 2
                && grantResults[0]==PackageManager.PERMISSION_GRANTED){


        }else{

            finish();
        }
    }

    public void StartServ(){

        startSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(Home.this,TrackerService.class));
            }
        });
    }

    public void StopSer(){

        stopSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(Home.this,TrackerService.class));

            }
        });
    }
}
