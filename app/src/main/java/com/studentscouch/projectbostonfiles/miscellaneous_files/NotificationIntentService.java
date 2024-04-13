package com.studentscouch.projectbostonfiles.miscellaneous_files;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class NotificationIntentService extends Service {

    private int counter=0;

    private String dbPath;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            checkForChangeThenNotif(true);
        else
            checkForChangeThenNotif(false);
    }

    private void checkForChangeThenNotif(boolean isOreoOrAbove){

        final Handler handler = new Handler();
        final int dbCheckIntervalTime = 600000;

        // set timer at 600.000 milliseconds (equal to 10 minutes)

        final Runnable r = new Runnable() {
            public void run() {

                if (isOreoOrAbove) {

                    startMyOwnForeground();
                }

                else {

                    startForeground(1, new Notification());

                }

                handler.postDelayed(this, dbCheckIntervalTime);
            }
        };

        r.run();

    }



    private void startMyOwnForeground()
    {

        //TODO: ADD CODE TO ONLY TRIGGER NOTIFICATION WHEN DB VALUE HAS CHANGED!!!!!

        final DatabaseReference fbRef9 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()
                        + "/"
                        + ConstantsDB.BOOKINGS_TABLE);

        fbRef9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    // VOEG CODE TOE OM VANUIT MYSQL OPSLAG LAATST GEBRUIKTE CIJFER VAN ELKE CHILD IN DEZE TABL TE ACHTERHALEN!!!!

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // VOEG PATH NAAR BOOKINGS TABLE TOE!!!!!!!!


        sendOffNotification();
    }

    private void sendOffNotification(){

        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("TEST DESCRIPTION")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
        }
        startForeground(2, notification);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);



        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, RestarterBroadCastReceiver.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
