package com.example.devoprakesh.trackingappchild;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TrackerService extends Service {
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

        buildNotification();
        loginToFirebase();
    }


    public void buildNotification(){


    }




    public void loginToFirebase(){


    }
}
