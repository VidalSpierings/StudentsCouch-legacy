package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsCountryCodes;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.StartupActivityDBInterface;
import com.studentscouch.projectbostonfiles.dialogs.CustomDIalogBookingAccepted2;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogBookingAccepted;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogBookingAccepted3;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogGuestBooking;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogHostFinished;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogRegulations;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogUserStayEnded;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogWithButton;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogWithEditText;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class StartupActivityDBImplementer implements StartupActivityDBInterface {

    private StartupActivityObservables observables;
    private static Bundle args;

    private int
            endDay,
            endMonth,
            endYear;

    private int
            cDay,
            cMonth,
            cYear;

    private String bookingAID;

    private String currentUserDbPath;

    public StartupActivityDBImplementer(StartupActivityObservables observables){

        this.observables = observables;
        observables.setHasBeenInitiated(false);
        observables.setChangedAccepted3(false);
        observables.setCHangedAccepted2(false);
        observables.setChanged(false);

    }

    @Override
    public void initDB(Context context, AppCompatActivity appCompatActivity) {

        Firebase.setAndroidContext(context);

        retrieveInfoFromDB(context, appCompatActivity);

        checkUserProfileStatus(context, appCompatActivity);

    }

    @Override
    public void checkIfEUBlockActive(final Context context, Activity activity) {

        final DatabaseReference fbRef11 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.BLOCKED_COUNTRIES_URL_REFERENCE);

        // DB link to countries where StudentsCouch is unavailable

        fbRef11.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                checkIfBlockActiveAndUserInEU(context, activity, dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void retrieveUserHostStatus() {

        String UID2 = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID2
                        + ConstantsDB.HOST_STATUS_URL_REFERENCE
                        + ConstantsDB.STATUS_URL_REFERENCE);

        // DB link to hostStatus of logged in user

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                retrieveUserHostStatusFunctionality(dataSnapshot, fbRef);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @Override
    public String retrieveUserCountryCode() {

        final String[] userCountryCode = new String[1];

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()
                        + ConstantsDB.USER_WARNINGS_URL_REFERENCE);

        // DB link to country of residence of currently logged in user

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                userCountryCode[0] = retrieveCountryCode(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userCountryCode[0];

    }

    @Override
    public void checkIfMostRecentWarningInfoRead(AppCompatActivity appCompatActivity) {

        DatabaseReference fbRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + observables.getUID2()
                        + ConstantsDB.USER_WARNINGS_IS_DIALOG_READ_URL_REFERENCE);

        // DB link to status on whether or not user has read the latest 'warning received' dialog

        fbRef1.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                checkIfOpenWarningDialog(appCompatActivity, dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkIfOpenWarningDialog(AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot){

        try {

            int isRead = dataSnapshot.getValue(Integer.class);

            if (isRead == 1 && !observables.isLoaded()){

                // open new warning dialog if user has received one

                openNewWarningDialog(appCompatActivity);

                observables.setLoaded(true);

                // isLoaded prevents the dialog from appearing again when data is changed and
                // onDataChangeListener is triggered

            }

        } catch (Exception e){

            // Do nothing

        }

    }

    @Override
    public void checkIfIbanAdded(AppCompatActivity appCompatActivity) {

        final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + observables.getUID2());

        // DB link to currently logged in Users' user table

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                checkIfIbanAddedFunc(appCompatActivity, dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void retrieveStatusOfAllUserBookings(AppCompatActivity appCompatActivity) {

        String UID2 = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Log.i("userUID", UID2);

        final DatabaseReference fbRef20 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID2
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        // DB link to made bookings by user

        showBookingsIfNew(appCompatActivity, fbRef20);

        checkIfAnyBookingsFinishedGuest(appCompatActivity, UID2);

    }

    private void checkIfAnyBookingsFinishedGuest(AppCompatActivity appCompatActivity, String UID2){

        try {

            openGuestFinishedDialogIfApplicable(appCompatActivity, UID2);

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void openGuestFinishedDialogIfApplicable(AppCompatActivity appCompatActivity, String UID2){

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID2
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                checkForAllBookingsIsFinished(appCompatActivity, dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkForAllBookingsIsFinished(AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot){

        for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

            attemptOpenGuestFinishedDialogIfApplicableFunc(appCompatActivity, postSnapshot);

        }

    }

    private void attemptOpenGuestFinishedDialogIfApplicableFunc(AppCompatActivity appCompatActivity, DataSnapshot postSnapshot){

        try {

            getBookingEndDate(postSnapshot);

            getCurrentDate();

            openGuestFinishedDialogIfApplicable2(appCompatActivity, postSnapshot);

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void openGuestFinishedDialogIfApplicable2(AppCompatActivity appCompatActivity, DataSnapshot postSnapshot){

        int longAssVariable = postSnapshot.child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).getValue(Integer.class);

        String bookingID6 = postSnapshot.getKey();

        if (endDay == cDay && endMonth == cMonth && endYear == cYear && longAssVariable == 1) {

            String firstName2 = postSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_HOST_TABLE).getValue(String.class);
            String lastName2 = postSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_HOST_TABLE).getValue(String.class);
            String AID3 = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);
            String UID3 = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

            openNewGuestStayFinishedDialog(appCompatActivity, UID3, firstName2, lastName2, AID3, bookingID6);

        }

    }

    private void getCurrentDate(){

        Calendar calender = Calendar.getInstance();
        cDay = calender.get(Calendar.DAY_OF_MONTH);
        cMonth = calender.get(Calendar.MONTH) + 1;
        cYear = calender.get(Calendar.YEAR);

    }

    private void getBookingEndDate(DataSnapshot postSnapshot){

        endDay = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_DAY_TABLE).getValue(Integer.class);
        endMonth = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_MONTH_TABLE).getValue(Integer.class);
        endYear = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_YEAR_TABLE).getValue(Integer.class);

        // compare to current date on phone user

    }

    private void showBookingsIfNew(AppCompatActivity appCompatActivity, DatabaseReference fbRef20){

        fbRef20.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    showBookingIfNew(postSnapshot, appCompatActivity);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // ------retrieve info on all bookings made to users' apartment, open according booking accepted dialogs if neccessary -----

    }

    @Override
    public void retrieveUnlockedCountries(final Context context) {

        final DatabaseReference fbRef8 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.UNLOCKED_COUNTRIES_URL_REFERENCE);

        fbRef8.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveUnlockedCountriesFunctionality(context, dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void retrieveBookingsRelatedInfo(Context context, AppCompatActivity appCompatActivity) {

        retrieveUserCountryCode();

        Log.i("countryCode", retrieveUserCountryCode() + " countryCode");

        final DatabaseReference fbRef9 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        // DB link to User table of currently logged in user

        fbRef9.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveBookingsRelatedInfoFunctionality(context, dataSnapshot, appCompatActivity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkUserProfileStatus(Context context, AppCompatActivity appCompatActivity){


        checkIfEUBlockActive(context, appCompatActivity);

        // check if application is available for users living in european union

        checkIfMostRecentWarningInfoRead(appCompatActivity);

        // ----- Check if user has read latest warning received dialog -----

        checkIfIbanAdded(appCompatActivity);

        checkIfNeedToOpenRegulationsDialog(appCompatActivity);

    }

    private void retrieveInfoFromDB(Context context, AppCompatActivity appCompatActivity){

        setUserUID();

        retrieveUserHostStatus();

        // ----- Retrieve host status of currently logged in user -----

        retrieveUnlockedCountries(context);

        retrieveUserCountryCode();

        // ----- Retrieve country code of currently logged in user -----

        retrieveBookingsRelatedInfo(context, appCompatActivity);

        // ----- Retrieve different snippets of info about user if user is a host

        retrieveStatusOfAllUserBookings(appCompatActivity);

    }

    private void setUserUID(){

        observables.setUID2(FirebaseAuth.getInstance().getUid());

    }

    private void openNewWarningDialog(AppCompatActivity appCompatActivity) {

            Bundle args = new Bundle();
            args.putString("UID", observables.getUID2());

            // create metadata for intent

            CustomDialogWithButton customDialogWithButton = new CustomDialogWithButton();

            customDialogWithButton.setArguments(args);

            // pass metadata to intent

            customDialogWithButton.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private void checkIfBlockActiveAndUserInEU(Context context, Activity activity, DataSnapshot dataSnapshot){

        String isEuBlockActive = dataSnapshot.child(ConstantsDB.EU_BLOCK_TABLE2).getValue(String.class);

        String userCountryCode = retrieveUserCountryCode();

        try {

            informUserWhenEuBlockActive(context, activity, isEuBlockActive, userCountryCode);

            } catch (Exception e) {

            // Do something

        }

        }

    private void informUserWhenEuBlockActive(Context context, Activity activity, String isEuBlockActive, String userCountryCode){

            for (int i = 0; i < ConstantsCountryCodes.COUNTRIES_IN_EUROPE.length; i++) {

                if (isEuBlockActive.equals(ConstantsDB.TRUE) && userCountryCode.equals(ConstantsCountryCodes.COUNTRIES_IN_EUROPE[i])){

                    Toast.makeText(context, context.getResources().getString(R.string.eu_block_active), Toast.LENGTH_LONG).show();

                    activity.finishAffinity();

                    // block usage of StudentsCouch for users in European union

                }

            }

        }

    private void retrieveUserHostStatusFunctionality(DataSnapshot dataSnapshot, DatabaseReference fbRef234) {

        observables.setHostStatus(dataSnapshot.getValue(String.class));

        fbRef234.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                observables.setIsUnlocked(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String retrieveCountryCode(DataSnapshot dataSnapshot) {

        return dataSnapshot.getValue(String.class);

        // retrieve country code of country of residence of currently logged in user

    }

    private void retrieveUnlockedCountriesFunctionality(final Context context, DataSnapshot dataSnapshot
    ) {

        observables.setUnlockedCountriesArray(new ArrayList<>());

        // create arrayList of countries

        for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

            attemptRetrieveUnlockedCountry(context, postSnapshot);

        }

        observables.setUnlockedCountries(dataSnapshot.child(ConstantsCountryCodes.NETHERLANDS).getValue(String.class));

        // put Netherlands country code in unlockedCountries variable

    }

    private void attemptRetrieveUnlockedCountry(Context context, DataSnapshot postSnapshot){

        try {

            String unlockedCountry = postSnapshot.getValue(String.class);

            observables.getUnlockedCountriesArray().add(unlockedCountry);

            // add country where StudentsCouch apartments can be hosted

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.unlocked_countries_unavailable), Toast.LENGTH_LONG).show();

            // if unlocked countries can not be added to array, inform user that something
            // is wrong with receiving server data by showing a toast

        }

    }

    private void retrieveBookingsRelatedInfoFunctionality(
            final Context context,
            DataSnapshot dataSnapshot, final AppCompatActivity appCompatActivity) {

        userIsHostProtocol(context, dataSnapshot, appCompatActivity);

        //protocol is initiated if user is host

        userNotHostProtocol(dataSnapshot);

        // above protocol is initiated if user is not a host

    }

    private void userIsHostProtocol(final Context context,
                                   DataSnapshot dataSnapshot, final AppCompatActivity appCompatActivity){

        if (dataSnapshot.child(
                ConstantsDB.APARTMENT_HOST_STATUS_TABLE)
                .child(ConstantsDB.APARTMENT_STATUS_TABLE)
                .getValue(String.class).equals(ConstantsDB.HOST_STATUS_USER_IS_HOST)) {

            // if user is host, do the following

            setInfo(dataSnapshot);

            checkForAndShowNewBookings(appCompatActivity);

            retrieveUserPlaceID();

            // Retrieve PlaceID of apartment of currently logged in user

            retrieveAllBookingsProgress(context, appCompatActivity);

        }

        Log.i("AID", observables.getAID() + "EMPTY2");

    }

    private void retrieveAllBookingsProgress(Context context, AppCompatActivity appCompatActivity){

        if (!observables.getAID().equals("") && (!observables.getAID().equals(ConstantsDB.NULL))) {

            // if an AID can be retrieved, do the following

            retrieveBookingProgressStatusOfAllBookings(context, appCompatActivity);

            /*

             * booking data is set and bookings are accepted
             * through a series of dialogs displayed to both the booker and the host,
             * depending on the answer of the opposite person a new dialog
             * will or will not be shown to the current user.
             * the above method checks the progress of this booking dialog protocol
             * for the logged in user

             */

        }

    }

    private void setInfo(DataSnapshot dataSnapshot){

        observables.setIsHost(true);

        // inform activity that user is a host

        observables.setAID(dataSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class));

        // retrieve users' apartment ID

    }

    private void userNotHostProtocol(DataSnapshot dataSnapshot){

        if (!dataSnapshot.child(
                ConstantsDB.APARTMENT_HOST_STATUS_TABLE)
                .child(ConstantsDB.APARTMENT_STATUS_TABLE)
                .getValue(String.class).equals(ConstantsDB.HOST_STATUS_USER_IS_HOST))

        {

            observables.setAID("");

            observables.setIsHost(false);

            // inform app that user is specified as a non-host

        }

    }

    private void checkIfNeedToOpenRegulationsDialog(AppCompatActivity appCompatActivity){

        DatabaseReference fbRef6 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + observables.getUID2()
                        + ConstantsDB.AID_URL_REFERENCE);

        fbRef6.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                attemptOpenRegulationsDialogIfNecessary(appCompatActivity, dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*

         * retrieve info on whether or not user has read the subletting regulations document,
         * if not, open subletting regulations info dialog

         */

    }

    private void attemptOpenRegulationsDialogIfNecessary(AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot){

        try {

            observables.setAID(dataSnapshot.getValue(String.class));

            openNewRegulationsDialogIfApplicableFunctionality(appCompatActivity);

        } catch (Exception e){

            // Do nothing

        }

    }

    private void retrieveBookingProgressStatusOfAllBookings(Context context, AppCompatActivity appCompatActivity){

        try {

            checkIfAnyBookingsFinished(appCompatActivity);

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.error_unknown), Toast.LENGTH_LONG).show();

            // if failed to retrieve bookings, inform user by showing a toast

        }

    }

    private void checkIfAnyBookingsFinished(AppCompatActivity appCompatActivity){

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID()
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        final DatabaseReference finalFbRef3 = fbRef2;

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                checkIfAnyBookingsFinished(dataSnapshot, appCompatActivity, finalFbRef3, this);

                retrieveBookingProgressStatusOfAllBookingsFunctionality(appCompatActivity, dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve info on status of all made bookings to users' apartment, open according booking accepted dialogs if neccessary

    }

    private void retrieveUserPlaceID(){

        final DatabaseReference fbRef10 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID()
                        + ConstantsDB.APARTMENT_LOCATION_ID_TABLE_URL_REFERENCE);

        // DB link to PlaceID of users' PlaceID

        fbRef10.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrievePlaceID(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkForAndShowNewBookings(AppCompatActivity appCompatActivity){

        DatabaseReference fbRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID()
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        // DB link to list of all bookings from users' apartment

        fbRef1.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    showBookingIfNew(postSnapshot, appCompatActivity);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Retrieve info about status of all bookings made to users' apartment

    }

    private void showBookingIfNew(DataSnapshot postSnapshot, AppCompatActivity appCompatActivity) {

        String isAccepted = "0";
        String isAccepted2 = "0";

        // initialise booking metadata values

        String bookingID = postSnapshot.getKey();

        String bookerUID = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

        // retrieve booking ID and User ID of hosts' apartment

        bookingAID = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);


         try {

        isAccepted = postSnapshot.child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).getValue(String.class);

        // retrieve first booking status variable



         } catch (Exception e){

         // Do nothing

         }

        try {

            isAccepted2 = postSnapshot.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).getValue(String.class);

            // retrieve second booking status variable



        } catch (Exception e) {

            // Do nothing

        }

        Log.i("isAvv", "value: " + isAccepted2);

        openNewBookingDialog3IfNecessary(appCompatActivity, isAccepted2, bookingID, bookerUID);

        openNewBookingDialogIfNecessary(appCompatActivity, isAccepted, postSnapshot);

        attemptPerformMiscTasks(postSnapshot);

    }

    private void openNewBookingDialogIfNecessary(AppCompatActivity appCompatActivity, String isAccepted, DataSnapshot postSnapshot){

        try {

            if (isAccepted != null && isAccepted.equals(ConstantsDB.SHOW)) {

                attemptOpenNewBookingAcceptedDialog(appCompatActivity, postSnapshot);

            }

        } catch (Exception ignored) {



        }

    }

    private void openNewBookingDialog3IfNecessary(AppCompatActivity appCompatActivity, String isAccepted2, String bookingID, String bookerUID){

        try {

            if (isAccepted2.equals(ConstantsDB.SHOW_SEPCIAL) && !observables.isChangedAccepted3()) {

                // if booking status of isAccepted variable is at '2' , do the following

                openNewBookingAccepted3Dialog(bookingID, bookerUID, appCompatActivity);

                observables.setChangedAccepted3(true);

                // open third booking accepted dialog

            }

            // compare to current date on phone user

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void attemptPerformMiscTasks(DataSnapshot postSnapshot){

        try {

            setEndDate(postSnapshot);

            // compare to current date on phone user

            checkIfHostBookingFinishedDialogToBeShown(postSnapshot);



        } catch (Exception e) {

            // Do nothing

        }

    }

    private void attemptOpenNewBookingAcceptedDialog(AppCompatActivity appCompatActivity, DataSnapshot postSnapshot){

        try {

            String bookingId = postSnapshot.getKey();

            String AID3 = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            String UID3 = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

            // retrieve bookingID, Apartment ID of host booking, User ID of host booking

            openNewBookingAcceptedDialogIfApplicable(appCompatActivity, UID3, bookingId, AID3);

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void openNewBookingAcceptedDialogIfApplicable(
            AppCompatActivity appCompatActivity, String UID3, String bookingId, String AID3){

        if (!observables.isHasBeenInitiated()) {

            openNewBookingAcceptedDialog(UID3, bookingId, AID3, appCompatActivity);

            observables.setHasBeenInitiated(true);

        }

    }

    private void checkIfHostBookingFinishedDialogToBeShown(DataSnapshot postSnapshot){

        Calendar calender2 = Calendar.getInstance();
        int cDay = calender2.get(Calendar.DAY_OF_MONTH);
        int cMonth = calender2.get(Calendar.MONTH) + 1;
        int cYear = calender2.get(Calendar.YEAR);

        // create calendar object for current date

        int isHostStayFinishedDialogRead = postSnapshot.child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).getValue(Integer.class);

        // retrieve info on whether host has read the 'stay finished' dialog

        String bookingID6 = postSnapshot.getKey();

        if (
                observables.getEndDay() == cDay
                        && observables.getEndMonth() == cMonth
                        && observables.getEndYear() == cYear
                        && isHostStayFinishedDialogRead == 1) {

            observables.setFirstNameHost(postSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_HOST_TABLE).getValue(String.class));
            observables.setLastNameHost(postSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_HOST_TABLE).getValue(String.class));

            String AID3 = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            String UID3 = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

            // retrieve first and last name of host, Apartment ID, User ID of booker

        }

    }

    private void setEndDate(DataSnapshot postSnapshot){

        observables.setEndDay(postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_DAY_TABLE).getValue(Integer.class));
        observables.setEndMonth(postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_MONTH_TABLE).getValue(Integer.class));
        observables.setEndYear(postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_YEAR_TABLE).getValue(Integer.class));

    }

    private void retrievePlaceID(DataSnapshot dataSnapshot) {

        observables.setLocationID(dataSnapshot.getValue(String.class));

        // retrieve PlaceID of users' apartment

    }

    private void retrieveBookingProgressStatusOfAllBookingsFunctionality(AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot) {

        for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

            bookingAID = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            Log.d("book2", "aid: " + bookingAID);

            retrieveIsAccepted1Bookings(postSnapshot, appCompatActivity);

            retrieveIsAccepted2Bookings(postSnapshot, appCompatActivity);

            // Retrieve pending bookings. According to 'isAccepted' status,
            // user gets shown different dialog relating to how far the user has proceeded
            // in the process of fully accepting the booking and making their stay official

            checkIfAnyUserBookingsFinished();

            // check if user has reached the end date of any bookings, if so, show booking rating dialog

        }

        // loop through all bookings

        // (Unused variable)

    }

    private static void checkIfAnyUserBookingsFinished(){

        /*

        try {

            int endDay = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_DAY_TABLE).getValue(Integer.class);
            int endMonth = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_MONTH_TABLE).getValue(Integer.class);
            int endYear = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_YEAR_TABLE).getValue(Integer.class);

            // compare to current date on phone user

            Calendar calander = Calendar.getInstance();
            int cDay = calander.get(Calendar.DAY_OF_MONTH);
            int cMonth = calander.get(Calendar.MONTH) + 1;
            int cYear = calander.get(Calendar.YEAR);

            // create calendar object for current date

            int isStayFinishedHostDialogRead = postSnapshot.child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).getValue(Integer.class);

            // retrieve info on whether host has read the 'stay finished' dialog

            if (endDay == cDay && endMonth == cMonth && endYear == cYear && isStayFinishedHostDialogRead == 1) {

                String firstName2 = postSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_HOST_TABLE).getValue(String.class);
                String lastName2 = postSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_HOST_TABLE).getValue(String.class);

                String AID3 = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

                String UID3 = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

                // retrieve first and last name of host, Apartment ID, User ID of booker

            }

        } catch (Exception e) {

            // Do nothing

        }

        */

    }

    private void retrieveIsAccepted1Bookings(DataSnapshot postSnapshot, AppCompatActivity appCompatActivity){

        String isAccepted = "";

        try {

            isAccepted = postSnapshot.child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).getValue(String.class);

            // retrieve first booking status variable

        } catch (Exception e){

            // Do nothing

        }

        if (isAccepted == ConstantsDB.BOOKING_ACCEPTED_PROGRESS_1) {

            openNewBookingAcceptedDialogFunctionality(postSnapshot, appCompatActivity);

        }

    }

    private void retrieveIsAccepted2Bookings(DataSnapshot postSnapshot, AppCompatActivity appCompatActivity){

        String bookingID = postSnapshot.getKey();

        String bookerUID = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

        // retrieve booking ID and User ID of hosts' apartment

        String isAccepted2 = "";

        try {

            isAccepted2 = postSnapshot.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).getValue(String.class);

            // retrieve second booking status variable

        } catch (Exception e) {

            // Do nothing

        }

        if (isAccepted2.equals(ConstantsDB.SHOW_SEPCIAL) && !observables.isChangedAccepted3()) {

            // if booking status of isAccepted variable is at '2' , do the following

            openNewBookingAccepted3Dialog(bookingID, bookerUID, appCompatActivity);

            observables.setChangedAccepted3(true);

            // open third booking accepted dialog

        }

    }

    private void checkIfAnyBookingsFinished(
            DataSnapshot dataSnapshot, AppCompatActivity appCompatActivity,
            DatabaseReference fbRef2, ValueEventListener eventListener
    ) {

        for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

            // postSnapShot keys recognized, does not recognize variables

            attemptCheckIfNeedOpenNewBookingProgressDialogs(
                    appCompatActivity, fbRef2, postSnapshot, eventListener);

        }

    }

    private void attemptCheckIfNeedOpenNewBookingProgressDialogs(
            AppCompatActivity appCompatActivity, DatabaseReference fbRef2,
            DataSnapshot postSnapshot, ValueEventListener eventListener){

        try {

            setGuestFullName(postSnapshot);

            int endDay = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_DAY_TABLE).getValue(Integer.class);
            int endMonth = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_MONTH_TABLE).getValue(Integer.class);
            int endYear = postSnapshot.child(ConstantsDB.BOOKING_FINISH_DATE_YEAR_TABLE).getValue(Integer.class);

            // retrieve ending date for booking

            String bookerUID = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);
            String AID = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            // retrieve User ID and AID of host booking

            openNewHostStayFinishedDialogIfNecessary(
                    appCompatActivity, postSnapshot,
                    endDay, endMonth, endYear, AID);

            openNewBookingAccepted2DialogIfNecessary(appCompatActivity, postSnapshot, bookerUID, AID);

            openNewGuestDialogIfNecessary(appCompatActivity, postSnapshot, fbRef2, eventListener);



        } catch (Exception e) {

            // Do nothing

        }



    }

    private void openNewBookingAccepted2DialogIfNecessary(AppCompatActivity appCompatActivity, DataSnapshot postSnapshot, String bookerUID, String AID){

        String isAccepted = postSnapshot.child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).getValue(String.class);

        if (isAccepted.equals(ConstantsDB.SHOW) && !observables.isCHangedAccepted2()) {

            openNewBookingAccepted2Dialog(postSnapshot.getKey(), bookerUID, AID, appCompatActivity);

            observables.setCHangedAccepted2(true);

            // open new 'booking accepted for second time' dialog

        }

    }

    private void openNewGuestDialogIfNecessary(AppCompatActivity appCompatActivity, DataSnapshot postSnapshot, DatabaseReference fbRef2, ValueEventListener eventListener){

        String isDialogRead = postSnapshot.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).getValue(String.class);

        // retrieve info on whether or not first booking dialog has been read by user

        String locationName = postSnapshot.child(ConstantsDB.BOOKING_CITY_VILLAGE_TABLE).getValue(String.class);

        // retrieve location name of apartment (Amsterdam, Rotterdam, Den Haag, etc.)

        if (isDialogRead.equals(ConstantsDB.SHOW)) {

            openNewGuestDialogFunctionality(postSnapshot, fbRef2, eventListener, locationName, appCompatActivity);

        }

    }

    private void setGuestFullName(DataSnapshot postSnapshot){

        observables.setFirstNameGuest(postSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_GUEST_TABLE).getValue(String.class));

        observables.setLastNameGuest(postSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_GUEST_TABLE).getValue(String.class));

        // retrieve first and last name of guest booking

    }

    private void openNewHostStayFinishedDialogIfNecessary(
            AppCompatActivity appCompatActivity, DataSnapshot postSnapshot,
            int endDay, int endMonth, int endYear,
            String AID){

        String bookingID = postSnapshot.getKey();

        int longAssVariable = postSnapshot.child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).getValue(Integer.class);

        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);

        if (endDay == cDay && endMonth == cMonth && endYear == cYear && longAssVariable == 1) {

            // if end date of at least one booking is or has been reached,
            // show stay finished for host dialog

            observables.setUID(postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class));
            openNewHostStayFinishedDialog(AID, bookingID, appCompatActivity);

            // retrieve User ID of host and open new 'host stay finished' dialog

            // host stay finished dialog = dialog shown to host when ending date for booking has been reached or preceded

        }

    }

    private void checkIfIbanAddedFunc(AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot){

        int isAdded = 3;

        try {

            isAdded = dataSnapshot.child(ConstantsDB.APARTMENT_IS_IBAN_ADDED_TABLE).getValue(Integer.class);

            // attempt to retrieve info on whether or not user has added IBAN number

        } catch (Exception e) {

            // Do nothing

        }

        openIBANdialogIfNecessary(appCompatActivity, isAdded);

    }

    private void openIBANdialogIfNecessary(AppCompatActivity appCompatActivity, int isAdded){

        if (isAdded == 0 && !observables.isChanged() && observables.getHostStatus().equals(ConstantsDB.HOST_STATUS_USER_IS_HOST)) {

            openNewAddIbanDialog(appCompatActivity);
            observables.setChanged(true);

            // if user hasn't added IBAN for their apartment yet, open new add IBAN dialog.
            // isChanged prevents the dialog from appearing again when data is changed and
            // onDataChangeListener is triggered

        }

    }

    private void openNewRegulationsDialog(AppCompatActivity appCompatActivity) {

        Bundle args = new Bundle();
        args.putString("AID", observables.getAID());

        // create metadata for intent

        CustomDialogRegulations customDialogRegulations = new CustomDialogRegulations();

        customDialogRegulations.setArguments(args);

        // pass metadata to intent

        customDialogRegulations.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private void openNewRegulationsDialogIfApplicableFunctionality(
            AppCompatActivity appCompatActivity) {

        DatabaseReference fbRef6 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID()
                        + ConstantsDB.IS_CITY_REGULATIONS_DIALOG_READ_URL_REFERENCE);

        fbRef6.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (!observables.getAID().equals("")) {

                    openNewRegulationsDialogIfApplicableFunctionality2(appCompatActivity, dataSnapshot);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openNewRegulationsDialogIfApplicableFunctionality2(AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot){

        try {

            if (dataSnapshot.getValue(String.class).equals(ConstantsDB.IS_NOT_READ)) {

                openNewRegulationsDialog(appCompatActivity);

                // if user registered apartment in Amsterdam and if user hasn't clicked ok on the dialog before,
                // show user dialog about subletting regulations in Amsterdam

            }

        } catch (Exception e) {

            // Do nothing

        }

    }

    private static void openNewGuestBookingDialog(
            String UID, String bookingID, String firstName, String lastName,
            String AID, String locationName, AppCompatActivity appCompatActivity) {

        initToBePassedValues(firstName, lastName, UID, bookingID, AID, locationName);

        CustomDialogGuestBooking customDialogGuestBooking = new CustomDialogGuestBooking();
        customDialogGuestBooking.setArguments(args);

        // pass metadata to intent

        customDialogGuestBooking.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private void openNewGuestStayFinishedDialog(AppCompatActivity appCompatActivity, String UID, String firstName, String lastName, String AID, String bookingID){

        Bundle args = new Bundle();
        args.putString("firstName", firstName);
        args.putString("lastName", lastName);
        args.putString("UID", UID);
        args.putString("AID", AID);
        args.putString("bookingID", bookingID);

        CustomDialogUserStayEnded customDialogUserStayEnded = new CustomDialogUserStayEnded();

        customDialogUserStayEnded.setArguments(args);

        customDialogUserStayEnded.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private static void initToBePassedValues(String firstName, String lastName, String UID, String bookingID, String AID, String locationName){

        CustomDialogGuestBooking.firstName = firstName;
        CustomDialogGuestBooking.lastName = lastName;
        CustomDialogGuestBooking.UID = UID;
        CustomDialogGuestBooking.bookingID = bookingID;
        CustomDialogGuestBooking.AID = AID;

        setArgs(UID, bookingID, AID, locationName);

    }

    private static void setArgs(String UID, String bookingID, String AID, String locationName){

        args = new Bundle();
        args.putString("UID", UID);
        args.putString("bookingID", bookingID);
        args.putString("AID", AID);
        args.putString("locationName", locationName);

        // create metadata for intent

    }

    private static void openNewGuestDialogFunctionality(
            DataSnapshot postSnapshot, DatabaseReference fbRef2, ValueEventListener eventListener,
            String locationName, AppCompatActivity appCompatActivity
    ) {

        String firstName2 = postSnapshot.child(ConstantsDB.BOOKING_FIRST_NAME_GUEST_TABLE).getValue(String.class);
        String lastName2 = postSnapshot.child(ConstantsDB.BOOKING_LAST_NAME_GUEST_TABLE).getValue(String.class);
        String bookingId = postSnapshot.getKey();

        // retrieve booking ID and first and last name of guest booking

        String AID3 = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);
        String UID3 = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

        // retrieve Apartment ID and User ID of booker

        openNewGuestBookingDialog(
                UID3, bookingId, firstName2, lastName2,
                AID3, locationName, appCompatActivity);

        // open new guest dialog apartment

        fbRef2.removeEventListener(eventListener);

        // remove functionality for fbRef2 firebase reference

    }

    private void openNewBookingAcceptedDialogFunctionality(
            DataSnapshot postSnapshot, AppCompatActivity appCompatActivity
    ) {

        try {

            String bookingId = postSnapshot.getKey();

            String AID3 = postSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            String UID3 = postSnapshot.child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).getValue(String.class);

            // retrieve bookingID, Apartment ID of host booking, User ID of host booking

            openNewBookingAcceptedDialog(UID3, bookingId, AID3, appCompatActivity);

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void openNewHostStayFinishedDialog(
            String AID, String bookingID,
            AppCompatActivity appCompatActivity) {

        setArgs2(bookingID, AID);

        CustomDialogHostFinished customDialogHostFinished = new CustomDialogHostFinished(observables);

        customDialogHostFinished.setArguments(args);

        // pass metadata to intent

        customDialogHostFinished.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private void setArgs2(String bookingID, String AID){

        args = new Bundle();
        args.putString("bookingID", bookingID);
        args.putString("UID", observables.getUID());
        args.putString("AID", AID);

        // create metadata for intent

    }

    private void openNewAddIbanDialog(AppCompatActivity appCompatActivity) {

        CustomDialogWithEditText.UID = observables.getUID2();

        try {

            CustomDialogWithEditText customDialogWithEditText = new CustomDialogWithEditText();
            customDialogWithEditText.show(appCompatActivity.getSupportFragmentManager(), "test");

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void openNewBookingAcceptedDialog(String UID, String bookingID, String AID, AppCompatActivity appCompatActivity) {

        setArgs3(UID, bookingID, AID);

        CustomDialogBookingAccepted customDialogBookingAccepted = new CustomDialogBookingAccepted();
        customDialogBookingAccepted.setArguments(args);

        // pass metadata to intent

        customDialogBookingAccepted.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private static void setArgs3(String UID, String bookingID, String AID){

        args = new Bundle();
        args.putString("UID", UID);
        args.putString("bookingID", bookingID);
        args.putString("AID", AID);

        // create metadata for intent

    }

    private static void openNewBookingAccepted2Dialog(String bookingId, String UID, String AID, AppCompatActivity appCompatActivity) {

        setArgs4(bookingId, UID, AID);

        CustomDIalogBookingAccepted2 customDIalogBookingAccepted2 = new CustomDIalogBookingAccepted2();

        customDIalogBookingAccepted2.setArguments(args);

        // pass metadata to intent

        customDIalogBookingAccepted2.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private static void setArgs4(String bookingId, String UID, String AID){

        args = new Bundle();
        args.putString("bookingID", bookingId);
        args.putString("UID", UID);
        args.putString("AID", AID);

        // create metadata for intent

    }

    private void openNewBookingAccepted3Dialog(String bookingID, String UID, AppCompatActivity appCompatActivity) {

        setArgs5(bookingID, UID);

        CustomDialogBookingAccepted3 customDialogBookingAccepted3 = new CustomDialogBookingAccepted3(observables);

        customDialogBookingAccepted3.setArguments(args);

        // pass metadata to intent

        customDialogBookingAccepted3.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    private void setArgs5(String bookingID, String UID){

        args = new Bundle();
        args.putString("bookingID", bookingID);
        args.putString("UID", UID);

        decideIfIsBookingHostAID();

    }

    private void decideIfIsBookingHostAID(){

        Log.d("bookaid", "aid: " + bookingAID);

        if (bookingAID.equals(observables.getAID())) {

            args.putString("AID", observables.getAID());

            // create metadata for intent

        }

        if (!bookingAID.equals(observables.getAID())){

            args.putString("AID", null);

            // create metadata for intent

        }

    }

    }
