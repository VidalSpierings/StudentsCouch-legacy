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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogHostFinishedMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;
import com.studentscouch.projectbostonfiles.StartupMethods;

import java.util.Objects;

public class CustomDialogHostFinished extends androidx.appcompat.app.AppCompatDialogFragment{

    private DatabaseReference fbRef, fbRef3;

    private StartupActivityObservables observables;

    private TextView
            description_textView,
            no_button_textView,
            yes_button_textView;

    private FrameLayout
            yes_button_layout,
            no_button_layout;

    private String
            AID,
            bookingID;

    public CustomDialogHostFinished(StartupActivityObservables observables) {

        this.observables = observables;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_host_finished, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        getInfoFromActivity();

        Firebase.setAndroidContext(getActivity().getApplicationContext());

        initUI();

        initDbLinks();

        if (!observables.isChanged4()){

            // do following when data hasn't been changed already

            yes_button_layout.setOnClickListener(view1 -> {

                informDbUserStayEnded();

                checkForWarnings();

            });

        } else {

            dismiss();

            // dismiss dialog if data has already been changed

        }

        no_button_layout.setOnClickListener(view12 -> informDbUserStayEnded2());

        reInitUiAfterInfoRetrieved();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);
        yes_button_textView = view.findViewById(R.id.yes_button_textView);
        no_button_textView = view.findViewById(R.id.no_button_textView);

        yes_button_layout = view.findViewById(R.id.yes_button_layout);
        no_button_layout = view.findViewById(R.id.no_button_layout);

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                        getActivity())
                        .getApplicationContext()
                        .getAssets(), "project_boston_typeface.ttf");

        yes_button_textView.setTypeface(tp);
        description_textView.setTypeface(tp);
        no_button_textView.setTypeface(tp);

        // initialise and apply typeface to all textViews in layout

    }

    private void getInfoFromActivity(){

        Bundle args = getArguments();
        bookingID = Objects.requireNonNull(args).getString("bookingID");
        AID = args.getString("AID");

        // retrieve data required for reference to booking-specific firebase links

    }

    private void initUI(){

        description_textView.setText(getResources().getString(R.string.stay_ended_host));

        yes_button_textView.setText(getResources().getString(R.string.yes_dialog));
        no_button_textView.setText(getResources().getString(R.string.no_dialog));

    }

    private void initDbLinks(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + observables.getUID()
                        + ConstantsDB.WARNINGS_URL_REFERENCE);

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID);

        // create database links

    }

    private void informDbUserStayEnded(){

        fbRef3.child("isStayFinishedHostDialogRead").setValue(0);

        // inform DB that booking stay has ended

    }

    private void checkForWarnings(){

        @SuppressLint("ShowToast") final Toast yesToast = Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.feedback2), Toast.LENGTH_LONG);

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogHostFinishedMethods.checkForWarnings(
                        fbRef, dataSnapshot, yesToast, CustomDialogHostFinished.this);

                fbRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void informDbUserStayEnded2(){

        fbRef3.child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).setValue(0);

        dismiss();

        Toast.makeText(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                getActivity().
                        getApplication().
                        getResources().
                        getString(R.string.feedback),
                Toast.LENGTH_LONG).show();

        // inform user that ratings have been submitted with the help of a toast

    }

    @SuppressLint("SetTextI18n")
    private void reInitUiAfterInfoRetrieved(){

        description_textView.setText(
                observables.getFirstNameGuest() + " " + observables.getLastNameGuest()
                        + "'s " + getResources().getString(R.string.stay_ended_host));

        // display correct data in description textView

    }

}
