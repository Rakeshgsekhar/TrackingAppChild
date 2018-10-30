package com.example.devoprakesh.trackingappchild;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    Button startSer,stopSer;
    private static final int PERMISSION_REQUEST = 1;
    private AlertDialog.Builder alertDialogBuilder;
    private String childphnumber,unicodepassstr;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText unicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("ChildLoginDetails",MODE_PRIVATE);
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


                childphnumber = sharedPreferences.getString("Number","null");

                LayoutInflater inflater = LayoutInflater.from(Home.this);
                View promtsnumber = inflater.inflate(R.layout.stoplayout,
                        null);
                alertDialogBuilder = new AlertDialog.Builder(Home.this);

                alertDialogBuilder.setView(promtsnumber);


                unicode = promtsnumber.findViewById(R.id.stoppass);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface
                                    , int i) {

                                unicodepassstr = unicode.getText().toString();

                                DatabaseReference databaseReference = FirebaseDatabase
                                        .getInstance().getReference("Users");

                                databaseReference.child(childphnumber).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                        UserData data = dataSnapshot.getValue(UserData.class);
                                        Log.i("Unicode",unicodepassstr+"::"+data.getUnicode());
                                        if(unicodepassstr.equals(data.getUnicode())){
                                            StopSer();
                                            dialogInterface.cancel();


                                        }else{

                                            Toast.makeText(Home.this,"Invalid Code",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface
                                    , int i) {
                                dialogInterface.cancel();
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

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

        startService(new Intent(Home.this,TrackerService.class));
        /*startSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(Home.this,TrackerService.class));
            }
        });*/
    }

    public void StopSer(){
        stopService(new Intent(Home.this,TrackerService.class));
        /*stopSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(Home.this,TrackerService.class));

            }
        });*/

    }
}
