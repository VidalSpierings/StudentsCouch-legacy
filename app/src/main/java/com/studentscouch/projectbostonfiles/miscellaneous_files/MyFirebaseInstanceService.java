package com.studentscouch.projectbostonfiles.miscellaneous_files;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.studentscouch.projectbostonfiles.Constants;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.MainActivity;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MyFirebaseInstanceService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Constants constants = new Constants();

        Map<String, String> data = remoteMessage.getData();
        String newBookingRequestKey = data.get(constants.NEW_BOOKING_REQUEST_KEY);

        FirebaseMessaging.getInstance().subscribeToTopic("bookingNotifications");

        if (remoteMessage.getData().equals(newBookingRequestKey)){

            // if notification request received, use showNotification method

            showNewBookingNotification(
                    Objects.requireNonNull(
                            remoteMessage.getNotification()).getTitle(),
                    remoteMessage.getNotification().getBody());

        } else {

            showNotification2(remoteMessage.getData());

        }

        if (remoteMessage.getData().isEmpty()){

            // if notification request received, use showNotification method

            showNotification(
                    Objects.requireNonNull(
                    remoteMessage.getNotification()).getTitle(),
                    remoteMessage.getNotification().getBody());

        } else {

            showNotification2(remoteMessage.getData());

        }

    }

    private void showNotification2(Map<String,String> data) {

        String title = data.get("title");
        String body = data.get("body");

        // get title and body of notification

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
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

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Intent notificationIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // build notification

        PendingIntent intent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon);

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

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);



    }

    private void showNotification(String title, String body){

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.studentscouch.projectbostonfiles";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Desc");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);

            // create notification channel for devices equal to or higher than oreo

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Intent notificationIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent intent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon);

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

    }

    private void showNewBookingNotification(String title, String body){

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.studentscouch.projectbostonfiles";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Desc");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);

            // create notification channel for devices equal to or higher than oreo

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Intent notificationIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent intent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon);

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

    }

    /*

    const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
exports.pushNotification = functions.database.ref('/masterSheet/{pushId}').onWrite( event => {
console.log('Push notification event triggered');
    const payload = {
        notification: {
            title: 'App Name',
            body: "New message",
            sound: "default"
        },
        data: {
            title: "New Title",
            message:"New message"
        }
    };
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };
return admin.messaging().sendToTopic("notifications", payload, options);


 NODE.JS SAMPLE CODE FOR RECEIVING NOTIFICATION UPON DB NODE CHANGED!!!

 HOE HET WERKT: MET NODE.JS WORD EIGEN CODE ONTWIKKELT DIE EEN NOTIFICATIE VANUIT FIREBASE
 AFVUURT ZODRA IN EEN BEPAALDE TYPE NODE BEPAALDE DATA VERANDERT. DIT WORD ONTVANGEN IN SB
 MYFIREBASEINSTANTANCESERVICE CLASS ONDER ONMESSAGERECEIVED MIDDELS EEN 'TOPIC' FILTER. HET
 NODE.JS BESTAND ÉN KOPPELING VAN EEN NODE.JS PROGRAMMA AAN FIREBASE ZIJN DE ENIGSTE TAKEN DIE
 NOG GEDAAN MOETEN WORDEN OM DE ONTWIKKELING VAN DE NEWBOOKING NOTIFICATION AF TE MAKEN.

     */

}
