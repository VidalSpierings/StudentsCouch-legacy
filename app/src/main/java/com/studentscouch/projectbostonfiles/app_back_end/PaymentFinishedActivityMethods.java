package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.PaymentFinishedActivity;

public class PaymentFinishedActivityMethods {

    /*

    public static void retrieveApartmentInfo (
            String hostUID, DataSnapshot dataSnapshot, String locationName, String transaction_id, String bookingID) {

        hostUID = dataSnapshot.child(ConstantsDB.UID_TABLE).getValue(String.class);

        locationName = dataSnapshot.child(ConstantsDB.APARTMENT_CITY_VILLAGE_TABLE).getValue(String.class);

        transaction_id = dataSnapshot.child(ConstantsDB.BOOKINGS_TABLE).child(bookingID).child(ConstantsDB.BOOKING_TRANSACTION_ID_TABLE).getValue(String.class);

        locationName = dataSnapshot.child(ConstantsDB.APARTMENT_CITY_VILLAGE_TABLE).getValue(String.class);

        // retrieve user ID of host, name of location where apartment is located (I.E. Amsterdam, Rotterdam, Den Haag, etc.)

    }

    */

    @SuppressLint("SetTextI18n")
    public static void setDialogText (
            Context context, TextView transaction_id_textView, String transaction_id, TextView congratulations_description,
            String SAMPLE_IBAN, FloatingActionButton fab, String locationName, TextView congratulations_textView) {

        transaction_id_textView.setText(transaction_id + "\n" + context.getResources().getString(R.string.transaction_id));

        congratulations_description.setText(
                context.getResources().getString(R.string.deposit_money)
                        +
                        " "
                        + SAMPLE_IBAN
                        + context.getResources().getString(R.string.deposit_money2)
                        + " " + locationName);

        congratulations_textView.setText("€" + PaymentFinishedActivity.total_price + " ,-");

        fab.setClickable(true);

        // display payment information to user

    }

    @SuppressLint("SetTextI18n")
    private static void retrieveBookingInfo(
            DataSnapshot dataSnapshot, double total_price,
            TextView total_price_textView) {

        PaymentFinishedActivity.roomOrApartement = dataSnapshot.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).getValue(String.class);

        PaymentFinishedActivity.total_price = dataSnapshot.child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_TOTAL_PRICE_TABLE).getValue(Double.class);

        PaymentFinishedActivity.transaction_id = dataSnapshot.child(ConstantsDB.BOOKING_TRANSACTION_ID_TABLE).getValue(String.class);

        // retrieve booking metadata

        total_price_textView.setText("€ " + total_price + " ,-");

        // insert total price of booking for guest in textView

    }

    public static void setFabOnClickListener (
            Context context, DatabaseReference fbRef, DatabaseReference fbRef2, DatabaseReference fbRef4,
            AppCompatActivity appCompatActivity) {

        if (StartupMethods.isOnline(context)) {

            submitInfoToDB(fbRef, fbRef2, fbRef4);

            launchStartupActivity(context, appCompatActivity);

        } if (!StartupMethods.isOnline(context)) {

            StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

            // if no internet connection was found, inform user by showing dialog

        }

    }

    private static void launchStartupActivity(Context context, AppCompatActivity appCompatActivity){

        Intent i = new Intent(appCompatActivity, StartupActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);

        // return to StartupActivity and inform database that dialog has been read upon fab button click

    }

    private static void submitInfoToDB(DatabaseReference fbRef, DatabaseReference fbRef2, DatabaseReference fbRef4){

        submitInfoFbRef1(fbRef);

        submitInfoFbRef2(fbRef2);

        submitInfoFbRef4(fbRef4);

    }

    private static void submitInfoFbRef4(DatabaseReference fbRef4){

        fbRef4.child(ConstantsDB.BOOKING_IS_OFFICIAL_TABLE).setValue(1);

        // make booking official, meaning the DB admin has to
        // manually confirm when guest has deposited money to bank account of StudentsCouch

    }

    private static void submitInfoFbRef2(DatabaseReference fbRef2){

        fbRef2.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(ConstantsDB.SHOW);
        fbRef2.child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).setValue(ConstantsDB.SHOW);

        // inform guest and hosts' user DB tables that guest has read payment requirement

    }

    private static void submitInfoFbRef1(DatabaseReference fbRef){

        fbRef.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef.child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(ConstantsDB.SHOW);
        fbRef.child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).setValue(ConstantsDB.SHOW);

    }

    /*

    public static void retrieveBookingInfo2 (
            final DatabaseReference fbRef, final String roomOrApartement, final double total_price, final String transaction_id,
            final TextView total_price_textView, final FloatingActionButton fab, final DatabaseReference fbRef2, final AppCompatActivity appCompatActivity) {

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                //PaymentFinishedActivityMethods.retrieveBookingInfo(roomOrApartement, dataSnapshot, total_price, transaction_id, total_price_textView);

                retrieveBookingInfo(dataSnapshot, total_price, total_price_textView);

                // retrieve booking info

                setFabOnClickListenerWhenBookingInfoRetrieved(
                        appCompatActivity, fbRef, fbRef2, transaction_id, fab);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    */

    /*

    static void setFabOnClickListenerWhenBookingInfoRetrieved(
            AppCompatActivity appCompatActivity, DatabaseReference fbRef,
            DatabaseReference fbRef2, String transaction_id,
            FloatingActionButton fab){

        final DatabaseReference fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.TRANSACTIONS_URL_REFERENCE + transaction_id);

        fbRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PaymentFinishedActivityMethods.setFabOnClickListener(
                                getApplicationContext(), fbRef, fbRef2, fbRef4,
                                appCompatActivity);

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // show total price and if room or apartment was selected for booking

    }

    */

}
