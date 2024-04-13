package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

public class CustomDialogBookingAccepted3Methods {

    private static String house_number;
    private static String street;
    private static String locationName;

    private static String startDateDay;
    private static String startDateMonth;
    private static String startDateYear;

    private static String firstNameHost;
    private static String lastNameHost;

    static String bookerUID;

    private static String firstNameGuest;
    private static String lastNameGuest;

    private static int numNights;

    private static int resourceString;

    private static int time_of_arrival;

    public static void setDescriptionAndOnClickListener(
            Context context, DataSnapshot dataSnapshot, StartupActivityObservables observables, boolean triggerErrorText,
            TextView description_textView, final String AID, FrameLayout understood_button_layout,
            final DatabaseReference fbRef, final DatabaseReference fbRef2, final androidx.appcompat.app.AppCompatDialogFragment fragment) {

        getInfoFromDB(dataSnapshot);

        attemptRetrieveArrivalTime(
                context, dataSnapshot,
                triggerErrorText, description_textView, AID);

        understood_button_layout.setOnClickListener(view -> submitData(observables, AID, fbRef, fbRef2, fragment));

    }

    private static void attemptRetrieveArrivalTime(
            Context context, DataSnapshot dataSnapshot, boolean triggerErrorText,
            TextView description_textView, String AID){

        attemptRetrieveTimeOfArrival(dataSnapshot);

        try {

            showInfoIfBookingRetrieved(context, triggerErrorText, description_textView, AID);

        } catch (Exception e){

            // Do nothing

        }

    }

    private static void getInfoFromDB(DataSnapshot dataSnapshot){

        getGuestAndHostInfo(dataSnapshot);

        getAddressInfo(dataSnapshot);

        getBookingInfo(dataSnapshot);

    }

    private static void getBookingInfo(DataSnapshot dataSnapshot){

        startDateDay = String.valueOf(dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).getValue(Long.class));
        startDateMonth = String.valueOf(dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).getValue(Long.class));
        startDateYear = String.valueOf(dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).getValue(Long.class));

        numNights = dataSnapshot.child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class);

        // retrieve booking metadata

    }

    private static void getAddressInfo(DataSnapshot dataSnapshot){

        house_number = dataSnapshot.child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).getValue(String.class);
        street = dataSnapshot.child(ConstantsDB.APARTMENT_STREET_TABLE).getValue(String.class);
        locationName = dataSnapshot.child(ConstantsDB.BOOKING_CITY_VILLAGE_TABLE).getValue(String.class);

    }

    private static void getGuestAndHostInfo(DataSnapshot dataSnapshot){

        firstNameHost = dataSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_HOST_TABLE).getValue(String.class);
        lastNameHost = dataSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_HOST_TABLE).getValue(String.class);

        String bookerUID = dataSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

        firstNameGuest = dataSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_GUEST_TABLE).getValue(String.class);
        lastNameGuest = dataSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_GUEST_TABLE).getValue(String.class);

    }

    private static void submitData(StartupActivityObservables observables, String AID, DatabaseReference fbRef, DatabaseReference fbRef2, androidx.appcompat.app.AppCompatDialogFragment fragment){

        if (AID != null){

            submitInfoUserIsHost(fbRef2);

            Log.d("AIDref", "AID IS NOT NULL: " + AID);

        }

        if (AID == null || AID == ""){

            Log.d("AIDref", "AID IS NULL");

            submitInfoUserIsNotHost(fbRef);

        }

        if (AID != null && AID.equals(ConstantsDB.INVALID_APARTMENT)) {

            submitInfoTestApartment(fbRef);

        } else {

            submitInfoMiscApartment(fbRef2);

        }

        fragment.dismiss();

        // upon user confirming booking, inform database about this decision

    }

    private static void submitInfoMiscApartment(DatabaseReference fbRef2){

        fbRef2.child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(ConstantsDB.DONT_SHOW);

        // set booking status as inactive

    }

    private static void submitInfoTestApartment(DatabaseReference fbRef){

        // if apartment is a test apartment that is not in use anymore, do the following

        fbRef.child("isAccepted").setValue(ConstantsDB.DONT_SHOW);
        fbRef.child("isAccepted2").setValue(ConstantsDB.DONT_SHOW);
        fbRef.child("isDialogRead").setValue(ConstantsDB.DONT_SHOW);
        fbRef.child("isDialogRead2").setValue(ConstantsDB.DONT_SHOW);

        // set booking status as inactive

    }

    private static void submitInfoUserIsNotHost(DatabaseReference fbRef){

        fbRef.child("isAccepted").setValue(ConstantsDB.DONT_SHOW);
        fbRef.child("isAccepted2").setValue(ConstantsDB.DONT_SHOW);
        fbRef.child("isDialogRead").setValue(ConstantsDB.DONT_SHOW);
        fbRef.child("isDialogRead2").setValue(ConstantsDB.DONT_SHOW);

        // set booking status as inactive

    }

    private static void submitInfoUserIsHost(DatabaseReference fbRef2){

        fbRef2.child("isAccepted").setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child("isAccepted2").setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child("isDialogRead").setValue(ConstantsDB.DONT_SHOW);
        fbRef2.child("isDialogRead2").setValue(ConstantsDB.DONT_SHOW);

        // set booking status as inactive

    }

    private static void setHostOrGuestAndTime(
            Context context, String hostOrGuest, int time_of_arrival, TextView description_textView,
            String firstNameGuest, String lastNameGuest, String startDateDay, String startDateMonth,
            String startDateYear, String numNights, String firstNameHost, String lastNameHost,
            String AID) {

        setTimeOfArrivalString(time_of_arrival);

        setInfoFieldTextAccToSessionMetadata(
                context, hostOrGuest, description_textView, firstNameGuest,
                lastNameGuest, startDateDay, startDateMonth, startDateYear,
                numNights, firstNameHost, lastNameHost, AID);

    }

    private static void setTimeOfArrivalString(int time_of_arrival){

        resourceString = 0;

        if (time_of_arrival == ConstantsDB.EIGHT_TO_ELEVEN_VAL) {

            resourceString = R.string.between_eight_eleven;

        } else if (time_of_arrival == ConstantsDB.TWELVE_TO_FOUR_VAL) {

            resourceString = R.string.between_twelve_four;

        } else if (time_of_arrival == ConstantsDB.FIVE_TO_TEN_VAL) {

            resourceString = R.string.between_five_ten;

        }

    }

    @SuppressLint("SetTextI18n")
    private static void setInfoFieldTextAccToSessionMetadata(Context context, String hostOrGuest, TextView description_textView,
                                                             String firstNameGuest, String lastNameGuest, String startDateDay, String startDateMonth,
                                                             String startDateYear, String numNights, String firstNameHost, String lastNameHost,
                                                             String AID) {

        if (hostOrGuest.equals(ConstantsDB.BOOKING_GUEST) && !AID.equals(ConstantsDB.INVALID_APARTMENT)) {

            description_textView.setText(
                    context.getResources().getString(R.string.bookingaccepted3_text_screenshot) + "\n\n" + " " +
                            context.getResources().getString(R.string.bookingaccepted3_text1_host) + " " +
                            firstNameGuest + " " + lastNameGuest + " " +
                            context.getResources().getString(R.string.bookingaccepted3_text2) + " " +
                            startDateDay + "-" + startDateMonth + "-" + startDateYear + ". " +
                            context.getResources().getString(R.string.bookingaccepted3_text3) + " " +
                            numNights + " " + context.getResources().getString(R.string.bookingaccepted3_text4) + " " +
                            context.getResources().getString(resourceString) +
                            context.getResources().getString(R.string.tax_and_service_costs_info));

        } else if (hostOrGuest.equals(ConstantsDB.BOOKING_HOST)) {

            description_textView.setText(
                    context.getResources().getString(R.string.bookingaccepted3_text_screenshot) + "\n\n" +
                            context.getResources().getString(R.string.bookingaccepted3_text1_guest) + " " +
                            firstNameHost + " " + lastNameHost + " " +
                            context.getResources().getString(R.string.bookingaccepted3_text3_guest) + " " +
                            startDateDay + "-" + startDateMonth + "-" + startDateYear +
                            context.getResources().getString(R.string.bookingaccepted3_text3_host) + " " +
                            numNights + " " + context.getResources().getString(R.string.bookingaccepted3_text4_host) + " " +
                            context.getResources().getString(resourceString) + " " +
                            context.getResources().getString(R.string.at) + " " + street + " " + house_number + ", " + locationName

            );

        }

    }

    private static void attemptRetrieveTimeOfArrival(DataSnapshot dataSnapshot){

        try {

            time_of_arrival = dataSnapshot.child(ConstantsDB.BOOKING_TIME_OF_ARRIVAL_TABLE).getValue(Integer.class);

            // retrieve time of arrival for booking

        } catch (Exception ignored) {


        }

        // retrieving values required for showing correct information in description textView

    }

    private static void showInfoIfBookingRetrieved(Context context, boolean triggerErrorText, TextView description_textView, String AID){

        if (triggerErrorText) {

            description_textView.setText(context.getResources().getString(R.string.could_not_load_data));

            /*

             * if error text variable is triggered, inform user that booking information
             * could not be retrieved by changing the text in the dialog

             */

        } else {

            setHostOrGuestAndTime(context, ConstantsDB.BOOKING_HOST, time_of_arrival, description_textView,
                    firstNameGuest, lastNameGuest, startDateDay, startDateMonth,
                    startDateYear, String.valueOf(numNights), firstNameHost, lastNameHost,
                    AID);

        }

        // show retrieved data in description textView

    }

}
