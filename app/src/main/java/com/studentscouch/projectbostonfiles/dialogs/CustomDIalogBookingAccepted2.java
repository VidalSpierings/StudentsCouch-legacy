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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogBookingAccepted2Methods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDIalogBookingAccepted2 extends androidx.appcompat.app.AppCompatDialogFragment {

    private TextView
            description_textView,
            ok_textView;

    private FrameLayout
            ok_button_layout;

    private String
            UID,
            AID,
            bookingID;

    private DatabaseReference
            fbRef,
            fbRef2,
            fbRef4;

    private Double moneyComission;

    private int subleasedNightsLeftOld = 123456789;

    private Integer numNights;

    private int subLeasedNightsLeft;

    private DatabaseReference
            fbRef11,
            fbRef6;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.custom_dialog_accepted_2, null);

        linkXmlElementsToJavaVariables(view);

        Firebase.setAndroidContext(getActivity().getApplicationContext());

        getDataFromActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setTypefaces();

        retrieveAllInfoFromDB();

        // ----- retrieve number of subleased nights left for the year for apartment -----

        builder.setView(view);

        return builder.create();

    }

    private void retrieveAllInfoFromDB(){

        attemptRetrieveOriginalNumSubleasedNightsLeft();

        initDbLinks();

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveBookingInfoHost(dataSnapshot);

                retrieveBookingInfoBooker();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        // inform database that user has read dialog

    }

    private void retrieveBookingInfoHost(DataSnapshot dataSnapshot){

        moneyComission = dataSnapshot.child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_MONEY_COMISSION_HOST_TABLE).getValue(Double.class);

        // retrieve amount StudentsCouch earnings from booking from hosts' side

        numNights = dataSnapshot.child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class);

        // retrieve number of nights the booking last

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                         getActivity())
                        .getApplicationContext()
                        .getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        ok_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void getDataFromActivity(){

        Bundle args = getArguments();
        bookingID = Objects.requireNonNull(args).getString("bookingID");
        UID = args.getString("UID");
        AID = args.getString("AID");

        // retrieve data required for correct firebase links for booking in question

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);
        ok_textView = view.findViewById(R.id.ok_textView);

        ok_button_layout = view.findViewById(R.id.ok_button_layout);

    }
    private void attemptRetrieveOriginalNumSubleasedNightsLeft(){

        try {

            fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                    StartupMethods.DATABASE_LINK
                            + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                            + AID
                            + ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE_URL_REFERENCE);

            fbRef4.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                    try {

                        subleasedNightsLeftOld = dataSnapshot.getValue(Integer.class);

                        // retrieve amount of subleased nights left

                    } catch (Exception e){

                        subleasedNightsLeftOld = 123456789;

                        // subleased nights N/A

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e){

            subleasedNightsLeftOld = 123456789;

            // if attempt to perform action failed, assume that apartment has no subleaseseable nights limit

        }

    }

    private void initDbLinks(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID);

        // DB link to the specified booking from guest

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID);

        // DB link to the specified booking from host

    }

    private void retrieveBookingInfoBooker(){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveData(dataSnapshot);

                initDbLinks2();

                CustomDialogBookingAccepted2Methods.retrieveBookingInfo(
                        getContext(), dataSnapshot,
                        numNights, description_textView,
                        moneyComission);

                enableOkButtonFunctionality();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void enableOkButtonFunctionality(){

        fbRef11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                subLeasedNightsLeft = dataSnapshot.child(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE).getValue(Integer.class);

                // retrieve subleaseable nights left for current year

                final String locationID = dataSnapshot.child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).getValue(String.class);

                cancelBookingIfOutOfSubleasedNights();

                ok_button_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        informDbUserAcceptedBooking();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void cancelBookingIfOutOfSubleasedNights(){

        if (numNights < 0) {

            fbRef6.child(bookingID).child(ConstantsDB.UID_TABLE).setValue(UID);
            fbRef6.child(bookingID).child(ConstantsDB.APARTMENT_AID_TABLE).setValue(AID);

            dismiss();

            Toast.makeText(Objects.requireNonNull(
                    getActivity()).getApplicationContext(),
                    getResources().getString(
                    R.string.recent_booking_cancelled),
                    Toast.LENGTH_LONG).show();

        }

    }

    private void retrieveData(DataSnapshot dataSnapshot){

        /*

        String transaction_id = dataSnapshot.child(ConstantsDB.BOOKING_TRANSACTION_ID_TABLE).getValue(String.class);

        // retrieve transaction ID that will be updated upon user taking certain actions within the dialog

        String firstName = dataSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_GUEST_TABLE).getValue(String.class);
        String lastName = dataSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_GUEST_TABLE).getValue(String.class);

        // retrieve first and last name of guest

        int startDateDay = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).getValue(Integer.class);
        int startDateMonth = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).getValue(Integer.class);
        int startDateYear = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).getValue(Integer.class);

        int finishDateDay = dataSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_DAY_TABLE).getValue(Integer.class);
        int finishDateMonth = dataSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_MONTH_TABLE).getValue(Integer.class);
        int finishDateYear = dataSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_YEAR_TABLE).getValue(Integer.class);

        // retrieve starting and finishing dates of booking

        */

        numNights = dataSnapshot.child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class);

        // load required data for booking

    }

    private void initDbLinks2(){

        fbRef11 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID);

        // DB link to specified apartment under 'apartments' table

        fbRef6 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.CANCELLED_BOOKINGS_URL_REFERENCE);

        // DB link to created bookings with cancelled status

    }

    private void informDbUserAcceptedBooking(){

        fbRef.child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(
                ConstantsDB.DONT_SHOW);

        fbRef2.child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(
                ConstantsDB.DONT_SHOW);

        dismiss();

        // update database that user has agreed to
        // description displayed in dialog and dismiss dialog

        if (subleasedNightsLeftOld != 123456789) {

            fbRef4.setValue(subleasedNightsLeftOld - numNights);

        }

    }

}
