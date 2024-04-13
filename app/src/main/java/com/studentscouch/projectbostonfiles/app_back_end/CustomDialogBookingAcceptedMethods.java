package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.StripeTestActivity;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.PaymentFinishedActivity;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogBookingAccepted;

public class CustomDialogBookingAcceptedMethods {

    private static String firstName;
    private static String lastName;
    private static String stayType;

    public static void initialiseProceedButton (
            final Context context, FrameLayout proceed_button_layout, final String UID,
            final String AID, final String bookingID, final String locationName,
            final androidx.appcompat.app.AppCompatDialogFragment fragment){

        proceed_button_layout.setOnClickListener(view -> launchNextActivity(
                context, UID, AID, bookingID,
                locationName, fragment));

    }

    private static void launchNextActivity(
            Context context, String UID, String AID, String bookingID,
            String locationName, androidx.appcompat.app.AppCompatDialogFragment fragment){

        Intent i = new Intent(context, StripeTestActivity.class);

        /*

        i.putExtra("UID", UID);
        i.putExtra("AID", AID);
        i.putExtra("bookingID", bookingID);
        i.putExtra("locationName", locationName);

        */

        // pass dialog metaData to next activity

        context.startActivity(i);

        fragment.dismiss();

        // show payment activity upon user clicking proceed button

    }

    public static void setDialogDescription (
            Context context, boolean isChanged, DataSnapshot dataSnapshot, TextView description_textView,
            androidx.appcompat.app.AppCompatDialogFragment fragment, Activity activity) {

        if (!isChanged){

            // if the following statements have not been activated before, run the following lines

            retrieveBookingMetaData(dataSnapshot);

            setInfoFieldTextAccToSessionMetaData(context, activity, fragment, description_textView);

            CustomDialogBookingAccepted.isChanged = true;

            // inform application that dialog instance has been read, preventing the same dialog from popping up again after user returns to StartupActivity from PaymentFinishedActivity

        }

    }

    @SuppressLint("SetTextI18n")
    private static void setInfoFieldTextAccToSessionMetaData(
            Context context, Activity activity,
            androidx.appcompat.app.AppCompatDialogFragment fragment,
            TextView description_textView){

        if (stayType.equals(ConstantsDB.BOOKING_STAY_TYPE_ROOM)) {

            description_textView.setText(firstName + " " + lastName + " " + activity.getResources().getString(R.string.booking_accepted) + " "
                    + context.getResources().getString(R.string.room_sublet));
            //.getString(R.string.booking_accepted_room));

            // change dialog description visible to user when 'room' is select as stay type

        } else if (stayType.equals(ConstantsDB.BOOKING_STAY_TYPE_APARTEMENT)) {

            description_textView.setText(firstName + " " + lastName + " " + context.getResources().getString(R.string.booking_accepted));

            // change dialog description visible to user when 'apartment' is select as stay type

        } else {

            Toast.makeText(context, context.getResources().getString(R.string.booking_accepted_error), Toast.LENGTH_LONG).show();

            fragment.dismiss();

            // inform user by toast that booking data could not be received and dismiss the dialog

        }

        // retrieve first and last name of host and set description text accordingly

    }

    private static void retrieveBookingMetaData(DataSnapshot dataSnapshot){

        stayType = dataSnapshot.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).getValue(String.class);

        // get data on whether guest will stay in a room in the apartment or will have the whole apartment for themselves

        firstName = dataSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_HOST_TABLE).getValue(String.class);
        lastName = dataSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_HOST_TABLE).getValue(String.class);

        // get first and last name of booking host

        // try catch block prevents dialog from popping up a second time after the user has already interacted with the dialog

    }

    public static void retrieveSubleaseableNights (final Context context, DataSnapshot dataSnapshot, FrameLayout proceed_button_layout) {

        String nights = String.valueOf(dataSnapshot.getValue(Integer.class));

        if (!nights.equals(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_NA_NON_IBAN)){

            // if a subleaseable nights limit is active on the apartment, do the follwoing

            changeFragmentFuncIfNoMoreNightsAvail(context, nights, proceed_button_layout);

        }

    }

    private static void changeFragmentFuncIfNoMoreNightsAvail(Context context, String nights, FrameLayout proceed_button_layout){

        int num_nights = Integer.valueOf(nights);

        // convert subleased nights left string object ot integer object

        if (num_nights <= 0){

            changeProceedBtnFunc(context, proceed_button_layout);

        }

    }

    private static void changeProceedBtnFunc(Context context, FrameLayout proceed_button_layout){

        proceed_button_layout.setOnClickListener(view -> {

            Toast.makeText(context, context.getResources().getString(R.string.cannot_be_booked), Toast.LENGTH_LONG).show();

            // inform user when no more subleased nights are available

        });

    }

}
