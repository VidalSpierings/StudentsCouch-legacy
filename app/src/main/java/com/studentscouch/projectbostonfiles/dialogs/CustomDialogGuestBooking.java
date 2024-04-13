package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogGuestBookingMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDialogGuestBooking extends androidx.appcompat.app.AppCompatDialogFragment {

    public static String
            UID,
            bookingID,
            firstName,
            lastName,
            AID;

    private TextView description_textView;

    private DatabaseReference fbRef;

    private DatabaseReference fbRef2;

    private DatabaseReference fbRef3;

    private DatabaseReference fbRef4;

    @SuppressLint("StaticFieldLeak")
    public static FrameLayout
            checkBoxRoom,
            checkBoxApartement;

    private ImageView
            subletting_apartement_imageView,
            subletting_room_imageView,
            profile_imageView;

    private TextView
            subletting_room_textView,
            subletting_apartement_textView,
            no_button_textView,
            yes_button_textView;


    private ProgressBar progressBar;
    private FrameLayout
            no_button_layout,
            yes_button_layout;

    private Animation fade_in;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Firebase.setAndroidContext(Objects.requireNonNull(getActivity()).getApplicationContext());

        // build dialog

        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.custom_dialog_guest_bboking, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        initUI();

        getInfoFromActivity();

        fade_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.visibility_fade_in_animation);

        retrieveInfoFromDB();

        CustomDialogGuestBookingMethods.initialiseCheckBoxApartmentButton(
                subletting_apartement_imageView, subletting_room_imageView);

        CustomDialogGuestBookingMethods.initialiseCheckBoxRoomButton(
                subletting_apartement_imageView, subletting_room_imageView);

        // initialise checkboxes

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();

    }

    private void retrieveInfoFromDB(){

        initDbLinks();

        retrieveProfileImage();

        checkIfApartmentInBelgium();

        retrieveRemainingApartmentInfo();

    }

    private void retrieveRemainingApartmentInfo(){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogGuestBookingMethods.retrieveAllApartmentInfo(
                        getContext(), dataSnapshot, yes_button_layout, bookingID,
                        fbRef, fbRef2,
                        no_button_layout, CustomDialogGuestBooking.this,
                        description_textView, firstName,
                        lastName, AID);

                // retrieve all info about apartment

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkIfApartmentInBelgium(){

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogGuestBookingMethods.checkIfApartmentInBE(
                        dataSnapshot, subletting_apartement_textView, subletting_apartement_imageView, checkBoxApartement);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retrieveProfileImage(){

        fbRef4.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogGuestBookingMethods.retrieveProfileImage(dataSnapshot, progressBar, profile_imageView, fade_in);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve bookers' profile image

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);
        subletting_room_textView = view.findViewById(R.id.subletting_room_textView);
        subletting_apartement_textView = view.findViewById(R.id.subletting_apartement_textView);
        yes_button_textView = view.findViewById(R.id.yes_button_textView);
        no_button_textView = view.findViewById(R.id.no_button_textView);

        yes_button_layout = view.findViewById(R.id.yes_button_layout);
        no_button_layout = view.findViewById(R.id.no_button_layout);

        subletting_apartement_imageView = view.findViewById(R.id.subletting_apartement_imageView);
        subletting_room_imageView = view.findViewById(R.id.subletting_room_imageView);
        profile_imageView = view.findViewById(R.id.profile_imageView);

        checkBoxRoom = view.findViewById(R.id.checkBoxRoom);
        checkBoxApartement = view.findViewById(R.id.checkBoxApartement);

        progressBar = view.findViewById(R.id.progress_spinner);

        // link java variables to xml layout views

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                        getActivity()).
                        getApplicationContext().
                        getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        subletting_apartement_textView.setTypeface(tp);
        subletting_room_textView.setTypeface(tp);
        yes_button_textView.setTypeface(tp);
        no_button_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void initUI(){

        subletting_apartement_imageView.setVisibility(View.INVISIBLE);
        subletting_room_imageView.setVisibility(View.INVISIBLE);

        // set both checkboxes to unchecked

    }

    private void getInfoFromActivity(){

        Bundle args = getArguments();
        AID = Objects.requireNonNull(args).getString("AID");
        UID = args.getString("UID");
        bookingID = args.getString("bookingID");

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

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.USER_COUNTRY_CODE_URL_REFERENCE);

        // DB link to country apartment is located in

        fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.USER_PROFILE_IMAGE_TABLE_URL_REFERENCE);

        // DB link to profile image of guest

    }

}
