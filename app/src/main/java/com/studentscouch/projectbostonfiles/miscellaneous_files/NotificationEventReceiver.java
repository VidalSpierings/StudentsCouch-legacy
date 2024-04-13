package com.studentscouch.projectbostonfiles.miscellaneous_files;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.MainActivity;

import java.util.Map;
import java.util.Random;

public class NotificationEventReceiver extends BroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";

    public static void setupAlarm(Context context) {
        PendingIntent newBookingIntent = getStartPendingIntent(context);

    }

    final Handler handler = new Handler();
    final int dbCheckIntervalTime = 600000; // 1000 milliseconds == 1 second

    final Runnable r = new Runnable() {
        public void run() {
            handler.postDelayed(r, dbCheckIntervalTime);
        }
    };

    //TODO: PROBEER PROCESS VAN DB VALUE CHECKEN ZO VEEL MOGELIJK TE HERHALEN IN ACHTERGROND, BIJVOORBEELD ELKE SECONDE!!!!
    // ELKE TIEN MINUTEN HERHALEN OM POTENTIEEL MOBIEL VERBRUIK VAN GEBRUIKER ZO VEEL MOGELIJK TE MINDEREN!!!!!
    // DEZE METHODE BINNEN EEN SERVICE PLAATSEN!!!!

    // https://stackoverflow.com/questions/11434056/how-to-run-a-method-every-x-seconds

    @Override
    public void onReceive(Context context, Intent intent) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + auth.getUid());

        //BELOW IS A TEST LINE

        DatabaseReference fbRefTest = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://studentscouch-d43a5.firebaseio.com/Users/d8FpTODAdpRF7IeviaILu3L76nr1/bookings/9d5aZXZ34jg9Bq0/isAccepted");

        fbRefTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // CREATE NOTIFICATION

                showNotification2(context);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*

        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "broadcast received");
            serviceIntent = NotificationIntentService.createIntentStartNotificationService(context);

        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {

            Log.i(getClass().getSimpleName(), "broadcast deleted");
            serviceIntent = NotificationIntentService.createIntentDeleteNotification(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }

        */

    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void showNotification2(Context context) {

        String title = "title";
        String body = "body";

        // get title and body of notification

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.studentscouch.projectbostonfiles";

        // create notification channel ID and notification manager

        // (different notifications can be assigned to different channels)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("new booking request from user");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);

            // create notification channel for devices equal to or higher than oreo

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // build notification

        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_icon);

        // create notification icon Bitmap object

        notificationBuilder
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setLargeIcon(icon)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setContentText(body)
                .setContentInfo("info")
                .setContentIntent(intent);

        // set notification contents

        // can be cancelled
        // system displays notification upon begin received
        // small icon: StudentsCouch app icon
        // title: title submitted by DB
        // large icon: StudentsCouch app icon
        // Badge icon: StudentsCouch app icon
        // content text: content text submitted by DB
        // content intent (activity started when pressing notification): MainActivity

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

        // show notification

    }

}
