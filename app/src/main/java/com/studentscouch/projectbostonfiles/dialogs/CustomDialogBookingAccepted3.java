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
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogBookingAccepted3Methods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

import java.util.Objects;

public class CustomDialogBookingAccepted3 extends androidx.appcompat.app.AppCompatDialogFragment{

    private TextView description_textView;

    private FrameLayout understood_button_layout;

    private DatabaseReference fbRef;
    private DatabaseReference fbRef2;

    private boolean triggerErrorText = false;

    private TextView understood_button_textView;

    private String
            UID,
            AID,
            bookingID;

    private StartupActivityObservables observables;

    public CustomDialogBookingAccepted3(StartupActivityObservables observables){

        this.observables = observables;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_booking_accepted_3, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        getInfoFromActivity();

        Firebase.setAndroidContext(getActivity().getApplicationContext());

        initDbLinks();

        setDescriptionAndOnClickListenersWhenInfoRetrieved();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // build new dialog

        builder.setView(view);

        return builder.create();

}

private void linkXmlElementsToJavaVariables(View view){

    description_textView = view.findViewById(R.id.description_textView);

    understood_button_layout = view.findViewById(R.id.understood_button_layout);

    understood_button_textView = view.findViewById(R.id.understood_button_textView);

    // link java variables with xml layout views

}

private void setTypefaces(){

    Typeface tp = Typeface.createFromAsset(
            Objects.requireNonNull(getActivity()).getApplicationContext().getAssets(), "project_boston_typeface.ttf");

    description_textView.setTypeface(tp);
    understood_button_textView.setTypeface(tp);

    // initialise typeface and apply to all textViews in layout

}

private void getInfoFromActivity(){

    Bundle args = getArguments();
    bookingID = Objects.requireNonNull(args).getString("bookingID");
    UID = args.getString("UID");
    AID = args.getString("AID");

    // load values passed from previous activity required for getting booking-specific firebase links

}

private void initDbLinks(){

    fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
            StartupMethods.DATABASE_LINK
                    + ConstantsDB.USERS_TABLE_URL_REFERENCE
                    + UID
                    + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                    + "/"
                    + bookingID);

    // DB link to specified booking for guest

    fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
            StartupMethods.DATABASE_LINK
                    + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                    + AID
                    + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                    + "/"
                    + bookingID);

    // DB link to specified booking for host

}

private void setDescriptionAndOnClickListenersWhenInfoRetrieved(){

    fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
        @Override
        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

            CustomDialogBookingAccepted3Methods.setDescriptionAndOnClickListener(
                    getContext(), dataSnapshot, observables, triggerErrorText,
                    description_textView, AID, understood_button_layout, fbRef,
                    fbRef2, CustomDialogBookingAccepted3.this);

        }

        // set description of dialog according to retrieved data from DB, set understood button onClicklistener

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}

}
