package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomDialogBookingAccepted2Methods {

    private static int
            startDateDay,
            startDateMonth,
            startDateYear;

    private static String
            firstName,
            lastName;

    public static void retrieveBookingInfo(
            Context context, DataSnapshot dataSnapshot,
            int numNights, TextView description_textView, double moneyComission) {

        retrieveAllInfoFromDB(dataSnapshot);

        setTextViewContent(
                context, firstName, lastName, startDateDay, startDateMonth,
                startDateYear, numNights, description_textView, moneyComission);

    }

    @SuppressLint("SetTextI18n")
    static void setTextViewContent(Context context, String firstName, String lastName, int startDateDay,
                                   int startDateMonth, int startDateYear, int numNights,
                                   TextView description_textView, double moneyComission){

        try {

            // load booking price and round double to 2 decimals

            description_textView.
                    setText(
                            context.getResources().getString(R.string.screenshot_dialog) +
                                    firstName +
                                    " " +
                                    lastName +
                                    " " +
                                    context.getResources().getString(R.string.booking_request_completed) +
                                    " " +
                                    (startDateDay) +
                                    "-" +
                                    (startDateMonth) +
                                    "-" +
                                    (startDateYear) +
                                    " " +
                                    context.getResources().getString(R.string.booking_request_completed2) +
                                    " " +
                                    numNights + " " +
                                    context.getResources().getString(R.string.booking_request_completed3) + " " +
                                    "€ " + toMoneySum(moneyComission) + " ,- " + context.getResources().getString(R.string.booking_request_completed4)
                    );

            // set description according to specified booking. toMoneySum method rounds math result to 2 decimal points

        } catch (Exception e) {

            // Do nothing

        }

    }

    private static String toMoneySum(double value) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRENCH);

        return formatter.format(value);

    }

    private static void retrieveAllInfoFromDB(DataSnapshot dataSnapshot){

        retrieveBookerFirstLastName(dataSnapshot);

        retrieveBookingDurationInfo(dataSnapshot);

        //numNights = Integer.valueOf(dataSnapshot.child("numDaysStay").getValue(Integer.class));

        // load required data for booking

    }

    private static void retrieveBookingDurationInfo(DataSnapshot dataSnapshot){

        startDateDay = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).getValue(Integer.class);
        startDateMonth = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).getValue(Integer.class);
        startDateYear = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).getValue(Integer.class);

    }

    private static void retrieveBookerFirstLastName(DataSnapshot dataSnapshot){

        firstName = dataSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_GUEST_TABLE).getValue(String.class);
        lastName = dataSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_GUEST_TABLE).getValue(String.class);

        // retrieve first and last name of guest

    }

}
