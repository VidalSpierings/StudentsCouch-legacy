package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogBookingAcceptedMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDialogBookingAccepted extends androidx.appcompat.app.AppCompatDialogFragment {

    public static String
            AID = "",
            UID,
            bookingID;

    private static String locationName;

    private TextView description_textView;

    private FrameLayout proceed_button_layout;

    private TextView proceed_textView;

    public static boolean isChanged = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_booking_accepted, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        Firebase.setAndroidContext(getActivity().getApplicationContext());

        getDataFromActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // build dialog

        CustomDialogBookingAcceptedMethods.initialiseProceedButton(
                getContext(), proceed_button_layout, UID, AID,
                bookingID, locationName, this);

        // initialise proceed button

        retrieveInfoFromDB();

        builder.setView(view);

        return builder.create();

    }

    private void retrieveInfoFromDB(){

        if (!AID.equals("")){

            // if no AID was submitted, do the following

            setDialogDescription();

            /*

            DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                    StartupMethods.DATABASE_LINK
                            + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                            + AID
                            + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                            + "/"
                            + bookingID);

            */

            // DB link to specified bookingID for host

            retrieveSubleasedNights();

        }

    }

    private void setDialogDescription(){

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Users/" + UID + "/bookings/" + bookingID);

        // DB link to specified bookingID for guest

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogBookingAcceptedMethods.setDialogDescription(
                        getContext(), isChanged, dataSnapshot,
                        description_textView, CustomDialogBookingAccepted.this,
                        getActivity());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve info about apartment and set description text

    }

    private void retrieveSubleasedNights(){

        final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE_URL_REFERENCE);

        // DB link to number of subleasable nights left for the year for specified apartment

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogBookingAcceptedMethods.retrieveSubleaseableNights(getContext(), dataSnapshot, proceed_button_layout);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        // retrieve subleaseable nights and change dialog functionality according to received data

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        proceed_textView.setTypeface(tp);

        // initialise Typeface and apply to all textViews in layout

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);
        proceed_textView = view.findViewById(R.id.proceed_textView);

        proceed_button_layout = view.findViewById(R.id.proceed_button_layout);

        // link java variables to xml layout views

    }

    private void getDataFromActivity(){

        Bundle args = getArguments();
        AID = Objects.requireNonNull(args).getString("AID");
        UID = args.getString("UID");
        bookingID = args.getString("bookingID");
        locationName = args.getString("locationName");

        // retrieve submitted AID, UID, bookingID and name of location apartment is located in (Amsterdam, Rotterdam, Den Haag, etc.)

    }

}
