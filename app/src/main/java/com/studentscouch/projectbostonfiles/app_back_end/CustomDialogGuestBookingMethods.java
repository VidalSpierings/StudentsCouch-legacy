package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsCountryCodes;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogGuestBooking;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.StartupMethods;

import java.util.Objects;

public class CustomDialogGuestBookingMethods {

    private static Bitmap profile_image_bitmap;

    private static int startDateDay;
    private static int startDateMonth;
    private static int startDateYear;

    private static int time_of_arrival;

    private static String roomOrApartment;

    private static int numDaysStay;

    private static DatabaseReference
            fbRef5,
            fbRef6;

    public static void retrieveProfileImage (
            DataSnapshot dataSnapshot, ProgressBar progressBar, ImageView profile_imageView, Animation fade_in) {

        retrieveAndConvertProfilePicture(dataSnapshot);

        progressBar.setVisibility(View.INVISIBLE);

        // if user profile picture is done loading, make progressbar invisible

        initProfileImageImageView(profile_imageView, fade_in);

    }

    private static void initProfileImageImageView(ImageView profile_imageView, Animation fade_in){

        profile_imageView.setImageBitmap(profile_image_bitmap);

        profile_imageView.startAnimation(fade_in);

        profile_imageView.setVisibility(View.VISIBLE);

        // set image bitmap to profile_imageView, make profile_imageView visible and fade in the imageView with Animation class

    }

    private static void retrieveAndConvertProfilePicture(DataSnapshot dataSnapshot){

        String base64ImageString = dataSnapshot.getValue(String.class);

        // retrieve guests' profile picture in Base64 encoded String format

        profile_image_bitmap = StartupMethods.StringToBitMap(base64ImageString);

        // convert image String object to bitmap object

    }

    public static void checkIfApartmentInBE (
            DataSnapshot dataSnapshot, TextView subletting_apartement_textView, ImageView subletting_apartement_imageView,
            FrameLayout checkBoxApartement) {

        String countryCode = dataSnapshot.getValue(String.class);

        if (Objects.requireNonNull(countryCode).equals(ConstantsCountryCodes.BELGIUM)){

            // if apartment is located in Belgium, do the following

            subletting_apartement_textView.setVisibility(View.GONE);
            subletting_apartement_imageView.setVisibility(View.GONE);
            checkBoxApartement.setVisibility(View.GONE);

        }

    }

    private static void retrieveApartmentInfo(
            final Context context, FrameLayout yes_button_layout, final DataSnapshot dataSnapshot, final String bookingID,
            final DatabaseReference fbRef6, final int numDaysStay, final androidx.appcompat.app.AppCompatDialogFragment fragment,
            final DatabaseReference fbRef, final DatabaseReference fbRef2, final FrameLayout no_button_layout) {

        setYesButtonOnClickListener(
                context, yes_button_layout, dataSnapshot,
                fbRef6, numDaysStay, fragment, fbRef, fbRef2);

        setNoButtonOnClickListener(
                context, no_button_layout,
                fbRef, fbRef2, fragment);

    }

    private static void setNoButtonOnClickListener(
            Context context, FrameLayout no_button_layout, DatabaseReference fbRef,
            DatabaseReference fbRef2, androidx.appcompat.app.AppCompatDialogFragment fragment){

        no_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeBooking(fbRef, fbRef2);

                Toast.makeText(context, context.getResources().getString(R.string.booking_declined), Toast.LENGTH_SHORT).show();

                informAppOptionSelected2();

                fragment.dismiss();

                // inform user with the help of a toast that the booking request has been declined and dismiss dialog

            }
        });

    }

    private static void setYesButtonOnClickListener(
            Context context, FrameLayout yes_button_layout, DataSnapshot dataSnapshot,
            DatabaseReference fbRef6, int numDaysStay, androidx.appcompat.app.AppCompatDialogFragment fragment,
            DatabaseReference fbRef, DatabaseReference fbRef2){

        yes_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                retrieveIfSublettingRoomOrApartment(context, dataSnapshot, fbRef6, numDaysStay, fragment);

                if (!CustomDialogGuestBooking.checkBoxRoom.isSelected() && !CustomDialogGuestBooking.checkBoxApartement.isSelected()){

                    Toast.makeText(context, context.getResources().getString(R.string.choose_textbox), Toast.LENGTH_LONG).show();

                    // inform user when yes button was clicked but no checkbox was checked with the help of a toast

                } else {

                    noStayTypeFoundProtocols(context, fbRef, fbRef2, fragment);

                }

            }
        });

    }

    private static void noStayTypeFoundProtocols(Context context,
                                                 DatabaseReference fbRef, DatabaseReference fbRef2,
                                                 androidx.appcompat.app.AppCompatDialogFragment fragment){

        submitInfoToDBYesSelected(context, fbRef, fbRef2, fragment);

        InformAppOptionSelected();

        fragment.dismiss();

        // dismiss this dialog

    }

    private static void informAppOptionSelected2(){

        StartupActivityObservables observables = new StartupActivityObservables();

        observables.setHasBeenInitiated(true);

    }

    private static void removeBooking(DatabaseReference fbRef, DatabaseReference fbRef2){

        fbRef.removeValue();
        fbRef2.removeValue();

        // if booking request is declined, remove booking from 'bookings' DB table for guest and host

    }

    private static void submitInfoToDBYesSelected(
            Context context, DatabaseReference fbRef, DatabaseReference fbRef2,
            final androidx.appcompat.app.AppCompatDialogFragment fragment){

        Toast.makeText(context, context.getResources().getString(R.string.booking_confirmed), Toast.LENGTH_LONG).show();

        submitInfoToBookerFieldYes(fbRef);

        submitInfoToHostFieldYes(fbRef2);

        // update guest and hosts' booking table to let DB know that the booking request has been accepted

        submitIfRoomOrApartment(fbRef, fbRef2);

        fragment.dismiss();

        // dismiss this dialog

    }

    private static void InformAppOptionSelected(){

        StartupActivityObservables observables = new StartupActivityObservables();

        observables.setHasBeenInitiated(true);

        // prevent activity from re-initiating dialog from
        // within onDataChangedListener upon newly submitted data having been retrieved

    }

    private static void submitIfRoomOrApartment(DatabaseReference fbRef, DatabaseReference fbRef2){

        if (CustomDialogGuestBooking.checkBoxRoom.isSelected()){

            fbRef.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).setValue(ConstantsDB.BOOKING_STAY_TYPE_ROOM);
            fbRef2.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).setValue(ConstantsDB.BOOKING_STAY_TYPE_ROOM);

            // if user selected 'room' as stay type, submit this data to guests' and hosts' DB tables

        } else if (CustomDialogGuestBooking.checkBoxApartement.isSelected()){

            fbRef.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).setValue(ConstantsDB.BOOKING_STAY_TYPE_APARTEMENT);
            fbRef2.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).setValue(ConstantsDB.BOOKING_STAY_TYPE_APARTEMENT);

            // if user selected 'apartment' as stay type, submit this data to guests' and hosts' DB tables

        }

    }

    private static void submitInfoToHostFieldYes(DatabaseReference fbRef2){

        fbRef2.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(ConstantsDB.IS_NOT_READ);

    }

    private static void submitInfoToBookerFieldYes(DatabaseReference fbRef){

        fbRef.child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).setValue(ConstantsDB.SHOW);
        fbRef.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).setValue(ConstantsDB.DONT_SHOW);

        fbRef.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(ConstantsDB.DONT_SHOW);
        fbRef.child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(ConstantsDB.DONT_SHOW);

    }

    private static void retrieveIfSublettingRoomOrApartment(
            Context context, DataSnapshot dataSnapshot,
            DatabaseReference fbRef6, int numDaysStay,
            final androidx.appcompat.app.AppCompatDialogFragment fragment){

        retrieveRoomOrApartmentValue();

        checkIfApartmentOutOfSubleaseableNights(
                context, dataSnapshot, fbRef6,
                numDaysStay, fragment);

    }

    private static void checkIfApartmentOutOfSubleaseableNights(
            Context context, DataSnapshot dataSnapshot, DatabaseReference fbRef6,
            int numDaysStay, androidx.appcompat.app.AppCompatDialogFragment fragment){

        int subleasedNightsLeft = dataSnapshot.child(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE).getValue(Integer.class);
        //String roomOrApartment = dataSnapshot.child("bookings").child(bookingID).child("roomOrApartement").getValue(String.class);

        if (subleasedNightsLeft != 123456789 && roomOrApartment.equals(ConstantsDB.APARTMENT_IMAGE_LOOP_TABLE)) {

            // if there is no subleaseable nights limit and user choose stay type as 'apartment', do the following

            fbRef6.setValue(subleasedNightsLeft - numDaysStay);

            // subtract subleasable nights left from number of nights this booking will last

        }

        if (subleasedNightsLeft <= 0) {

            fragment.dismiss();

            Toast.makeText(context, context.getResources().getString(R.string.recent_booking_cancelled), Toast.LENGTH_LONG).show();

            // if no more subleaseable nights are left, inform user by showing a toast and dismiss this dialog

        }

    }

    private static void retrieveRoomOrApartmentValue(){

        roomOrApartment = "";

        if (CustomDialogGuestBooking.checkBoxRoom.isSelected()) {

            roomOrApartment = ConstantsDB.BOOKING_STAY_TYPE_ROOM;

        } else if (CustomDialogGuestBooking.checkBoxApartement.isSelected()) {

            roomOrApartment = ConstantsDB.APARTMENT_IMAGE_LOOP_TABLE;

        }

    }

    public static void retrieveAllApartmentInfo(
            final Context context, DataSnapshot dataSnapshot, final FrameLayout yes_button_layout, final String bookingID,
            final DatabaseReference fbRef, final DatabaseReference fbRef2,
            final FrameLayout no_button_layout, final androidx.appcompat.app.AppCompatDialogFragment fragment,
            TextView description_textView, String firstName,
            String lastName, String AID) {

        attemptRetrieveApartmentInfo(
                context, dataSnapshot, AID, yes_button_layout,
                bookingID, fragment, fbRef, fbRef2,no_button_layout);

       attemptShowRetrieveBookingMetaData(
               context, description_textView, dataSnapshot,
               firstName, lastName, fragment);
    }

    private static void attemptShowRetrieveBookingMetaData(
            Context context, TextView description_textView, DataSnapshot dataSnapshot,
            String firstName, String lastName,
            androidx.appcompat.app.AppCompatDialogFragment fragment){

        try  {

            retrieveDataBookingMetaData(dataSnapshot);

            showBookingData(context, firstName, lastName, description_textView);

        } catch (Exception e){

            Toast.makeText(context, context.getResources().getString(R.string.booking_values_not_retrieved), Toast.LENGTH_SHORT).show();

            fragment.dismiss();

            // inform user when booking dates could not be retrieved by showing a toast and dismiss dialog

        }

    }

    private static void showBookingData(Context context, String firstName, String lastName, TextView description_textView){

        if (time_of_arrival == 1) {

            setTimeOfArrivalOneText(
                    context, description_textView,
                    firstName, lastName);

        } else if (time_of_arrival == 2) {

            setTimeOfArrivalTwoText(
                    context, description_textView,
                    firstName, lastName);

        } else if (time_of_arrival == 3) {

            setTimeOfArrivalThreeText(
                    context, description_textView,
                    firstName, lastName);

        }

    }

    @SuppressLint("SetTextI18n")
    private static void setTimeOfArrivalThreeText(Context context, TextView description_textView, String firstName, String lastName){

        description_textView.
                setText(firstName +
                        " " +
                        lastName +
                        " " +
                        context.getResources().getString(R.string.wants_to_book1) +
                        " " +
                        startDateDay +
                        "-" +
                        startDateMonth +
                        "-" +
                        startDateYear +
                        " " +
                        context.getResources().getString(R.string.wants_to_book2) +
                        " " +
                        numDaysStay +
                        " " +
                        context.getResources().getString(R.string.wants_to_book3) + " " +
                        context.getResources().getString(R.string.between_five_ten) + " " +
                        context.getResources().getString(R.string.wants_to_book4));

        /*

         * inform host that a user wants to book their apartment,
         * display booking request metadata such as time of arrival (between 5PM and 10PM in this if statement),
         * first and last name of guest requesting booking and time of arrival

         */

    }

    @SuppressLint("SetTextI18n")
    static void setTimeOfArrivalTwoText(Context context, TextView description_textView, String firstName, String lastName){

        description_textView.
                setText(firstName +
                        " " +
                        lastName +
                        " " +
                        context.getResources().getString(R.string.wants_to_book1) +
                        " " +
                        startDateDay +
                        "-" +
                        startDateMonth +
                        "-" +
                        startDateYear +
                        " " +
                        context.getResources().getString(R.string.wants_to_book2) +
                        " " +
                        numDaysStay +
                        " " +
                        context.getResources().getString(R.string.wants_to_book3) + " " +
                        context.getResources().getString(R.string.between_twelve_four) + " " +
                        context.getResources().getString(R.string.wants_to_book4));

        /*

         * inform host that a user wants to book their apartment,
         * display booking request metadata such as time of arrival (between 12:00 and 4:59 PM in this if statement),
         * first and last name of guest requesting booking and time of arrival

         */

    }

    @SuppressLint("SetTextI18n")
    private static void setTimeOfArrivalOneText(Context context, TextView description_textView, String firstName, String lastName){

        description_textView.
                setText(firstName +
                        " " +
                        lastName +
                        " " +
                        context.getResources().getString(R.string.wants_to_book1) +
                        " " +
                        (startDateDay) +
                        "-" +
                        (startDateMonth) +
                        "-" +
                        (startDateYear) +
                        " " +
                        context.getResources().getString(R.string.wants_to_book2) +
                        " " +
                        numDaysStay +
                        " " +
                        context.getResources().getString(R.string.wants_to_book3) + " " +
                        context.getResources().getString(R.string.between_eight_eleven) + " " +
                        context.getResources().getString(R.string.wants_to_book4));

        /*

         * inform host that a user wants to book their apartment,
         * display booking request metadata such as time of arrival (between 8AM and 11:59 AM in this if statement),
         * first and last name of guest requesting booking and time of arrival

         */

    }

    private static void attemptRetrieveApartmentInfo(
            Context context, DataSnapshot dataSnapshot, String AID,
            FrameLayout yes_button_layout, String bookingID,
            androidx.appcompat.app.AppCompatDialogFragment fragment,
            DatabaseReference fbRef, DatabaseReference fbRef2,
            FrameLayout no_button_layout){

        try {

            numDaysStay = dataSnapshot.child(
                    ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class);

            initDBlinks(AID);

            fbRef5.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                    CustomDialogGuestBookingMethods.retrieveApartmentInfo(
                            context, yes_button_layout, dataSnapshot, bookingID,
                            fbRef6, numDaysStay, fragment, fbRef,
                            fbRef2, no_button_layout);

                    // retrieve info about apartment

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e){

            // Do nothing

        }

    }

    private static void initDBlinks(String AID){

        fbRef6 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE_URL_REFERENCE);

        fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID);

    }

    private static void retrieveDataBookingMetaData(DataSnapshot dataSnapshot){

        startDateDay = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).getValue(Integer.class);
        startDateMonth = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).getValue(Integer.class);
        startDateYear = dataSnapshot.child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).getValue(Integer.class);
        time_of_arrival = dataSnapshot.child(ConstantsDB.BOOKING_TIME_OF_ARRIVAL_TABLE).getValue(Integer.class);

        // load data required for displaying correct information in the description textView

    }

    public static void initialiseCheckBoxApartmentButton (
            final ImageView subletting_apartement_imageView, final ImageView subletting_room_imageView){

        CustomDialogGuestBooking.checkBoxApartement.setOnClickListener(view -> {

            CustomDialogGuestBooking.checkBoxApartement.setSelected(true);
            CustomDialogGuestBooking.checkBoxRoom.setSelected(false);

            subletting_apartement_imageView.setVisibility(View.VISIBLE);
            subletting_room_imageView.setVisibility(View.INVISIBLE);

            // set apartment checkbox to selected and deselect room checkbox

        });

    }

    public static void initialiseCheckBoxRoomButton (
            final ImageView subletting_apartement_imageView, final ImageView subletting_room_imageView) {

        CustomDialogGuestBooking.checkBoxRoom.setOnClickListener(view -> {

            CustomDialogGuestBooking.checkBoxApartement.setSelected(false);
            CustomDialogGuestBooking.checkBoxRoom.setSelected(true);

            subletting_apartement_imageView.setVisibility(View.INVISIBLE);
            subletting_room_imageView.setVisibility(View.VISIBLE);

            // set room checkbox to selected and deselect apartment checkbox

        });

    }

    }
