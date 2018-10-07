package com.example.devoprakesh.trackingappchild;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackerService extends Service {

    FusedLocationProviderClient client;
    LocationRequest request;
    LocationCallback mCallbacks;
    public TrackerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();





        SharedPreferences sharedPreferences = getSharedPreferences("ChildLoginDetails",MODE_PRIVATE);

        final String Usernumber = sharedPreferences.getString("Number",null);
        mCallbacks = new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Childrens").child(Usernumber);

                Location location = locationResult.getLastLocation();
                if(location != null){

                    Log.i("Location","Location Update "+location);
                    databaseReference.child("Current").setValue(location);

                    databaseReference.child("Tracking").push().setValue(location);
                }
            }
        };

        buildNotification();
        LocationUpdateFirebase();
    }


    public void buildNotification(){

        Toast.makeText(this,"Location tacking started",Toast.LENGTH_LONG)
                .show();

    }






    public void LocationUpdateFirebase(){


        request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        client = LocationServices.getFusedLocationProviderClient(this);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if( permission == PackageManager.PERMISSION_GRANTED){

            client.requestLocationUpdates(request,mCallbacks,null );

            /*new LocationCallback(){

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference(Usernumber);

                    Location location = locationResult.getLastLocation();
                    if(location != null){

                        Log.i("Location","Location Update "+location);
                        databaseReference.child("Current").setValue(location);

                        databaseReference.child("Tracking").push().setValue(location);
                    }
                }
            },null);*/




        }
    }


    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"Stopped",Toast.LENGTH_LONG)
                .show();
        client.removeLocationUpdates(mCallbacks);
        stopSelf();

    }
}
