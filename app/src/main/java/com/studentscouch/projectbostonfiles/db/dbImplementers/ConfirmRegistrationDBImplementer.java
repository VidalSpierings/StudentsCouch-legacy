package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.ConfirmRegistrationActivityDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.ConfirmRegistrationActivityModel;
import com.studentscouch.projectbostonfiles.observables.ConfirmRegistrationObservables;
import com.studentscouch.projectbostonfiles.ui.RegisterActivtyScreen4;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.view.viewImplementers.ConfirmRegistrationViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.ConfirmRegistrationActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.ConfirmRegistrationViewModel;

import java.util.Objects;

public class ConfirmRegistrationDBImplementer implements ConfirmRegistrationActivityDBInterface {

    private int DIALOG_READ = 1;

    private ConfirmRegistrationObservables observables;

    private String
            cityRegulationsRead;

    private int numUsers;

    private int numHosts;

    private int choice;

    private Animation exit_anim;

    private DatabaseReference
            fbRef1,
            fbRef2,
            fbRef3,
            fbRef4,
            fbRef5;

    private ConfirmRegistrationActivityView view;

    private ConfirmRegistrationViewModel viewModel;

    private ConfirmRegistrationActivityModel model;

    private Intent intent;

    public ConfirmRegistrationDBImplementer(ConfirmRegistrationActivityView view, ConfirmRegistrationObservables observables){

        this.observables = observables;

        this.view = view;
        viewModel = new ConfirmRegistrationViewModel(view);
        model = new ConfirmRegistrationActivityModel(observables);


    }

    @Override
    public void createNewUser(Context context) {

        initDBlinks();

        submitAllInfoToDB();

    }

    @Override
    public void registerUserYes(
            Context context, AppCompatActivity appCompatActivity,
            FirebaseAuth fbAuth, FrameLayout progress_layout, Typeface tp, int isFromActivity) {

        // for all comments check above user register method

        initExitAnim(appCompatActivity);

        view.extractStringsFromTextViews(context);

        observables.setHostStatus(ConstantsDB.HOST_STATUS_USER_IS_HOST);

        if (observables.getLocationID() == (StartupMethods.AMS_PLACE_ID)){

            observables.setSubleasedNightsLeft(30);

            // if apartment is located in Amsterdam, set subleaseable days limit to 30 nights

        }

        createUserYes(context, fbAuth, appCompatActivity, progress_layout, tp, isFromActivity);

    }

    private void createUserYes(Context context, FirebaseAuth fbAuth, AppCompatActivity appCompatActivity, FrameLayout progress_layout, Typeface tp, int isFromActivity){

        final SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        fbAuth.createUserWithEmailAndPassword(
                observables.getEmail(),
                appCompatActivity.getIntent().getStringExtra("username")).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {

                    ObjectAnimator anim = view.getObjectAnimator1();
                    ObjectAnimator anim2 = view.getObjectAnimator2();

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        registrationCompletedProtocol(context, appCompatActivity, prefs, progress_layout, task, tp, anim, anim2, isFromActivity);

                    }
                });



    }

    private void registrationCompletedProtocol(
            Context context, AppCompatActivity appCompatActivity, SharedPreferences prefs, FrameLayout progress_layout,
            Task<AuthResult> task, Typeface tp, ObjectAnimator anim, ObjectAnimator anim2, int isFromActivity){

        observables.setAID(StartupMethods.getRandomString(100));

        // create apartment ID and email DB path using random character generation method

        if (task.isSuccessful()){

            initIntentAndAnims(
                    context, appCompatActivity, isFromActivity,
                    progress_layout, anim, anim2);

            registrationSuccessfulProtocol(context, prefs, progress_layout);

        } else {

            registrationUnsuccessfulProtocol(context, appCompatActivity, progress_layout, task, tp);

        }

    }

    private void initIntentAndAnims(
            Context context, AppCompatActivity appCompatActivity, int isFromActivity,
            FrameLayout progress_layout, ObjectAnimator anim, ObjectAnimator anim2){

        decideNextActivity(context, isFromActivity);

        intent = new Intent(context, RegisterActivtyScreen4.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        view.hideFab();

        setExitAnimationListener(context, appCompatActivity, progress_layout, intent, anim, anim2);

    }

    private void decideNextActivity(Context context, int isFromActivity){

        if (isFromActivity == 1) {

            intent = new Intent(context, StartupActivity.class);

        }

        if (isFromActivity == 2){

            intent = new Intent(context, RegisterActivtyScreen4.class);

        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // disable standard acitivty transition animation for this intent

    }

    private void setExitAnimationListener(
            Context context, AppCompatActivity appCompatActivity,
            FrameLayout progress_layout, Intent i,
            ObjectAnimator anim, ObjectAnimator anim2){

        exit_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        // load activity exit fade out animation

        exit_anim.setDuration(200);

        // set animation duration

        startObjectAnimatorsOnAnimationFinished(
                context, i, progress_layout, anim, anim2);

    }

    private void startObjectAnimatorsOnAnimationFinished(
            Context context, Intent i, FrameLayout progress_layout,
            ObjectAnimator anim, ObjectAnimator anim2){

        exit_anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                progress_layout.setVisibility(View.INVISIBLE);

                // make loading screen disappear

                anim.start();

                anim2.start();

                // animate scrollView and 'Confirm' activity

                addOnAnimationEndListener(context, i, anim2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void addOnAnimationEndListener(Context context, Intent i, ObjectAnimator anim2){

        anim2.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                context.startActivity(i);

                // when 'confirm' button is done animating (and off-screen), start new activity

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void registrationUnsuccessfulProtocol(
            Context context, AppCompatActivity appCompatActivity, FrameLayout progress_layout,
            Task<AuthResult> task, Typeface tp) {

        // if user was not successfully created, do the following

        view.enableFab();

        Toast.makeText(context, context.getResources().getString(R.string.could_not_create_user), Toast.LENGTH_LONG).show();

        // inform user that their registration could not be completed by showing a toast

        TextView error_textView = view.getErrorTextView();

        attemptProvideErrorDetails(context, appCompatActivity, task, error_textView, tp);

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

        // make loading screen disappear

    }

    private void attemptProvideErrorDetails(
            Context context, AppCompatActivity appCompatActivity, Task<AuthResult> task,
            TextView error_textView, Typeface tp){

        try {

            throw Objects.requireNonNull(task.getException());

        } catch(FirebaseAuthUserCollisionException e) {

            error_textView.setVisibility(View.VISIBLE);

            StartupMethods.showErrorMessageTemporarily(
                    context, 2500, error_textView,
                    context.getResources().getString(R.string.email_username_exists), tp);

            appCompatActivity.onBackPressed();

            // inform user that e-mail is already in use and return to previous activity

        } catch(Exception e) {

            StartupMethods.showErrorMessageTemporarily(
                    context, 2500, error_textView,
                    context.getResources().getString(R.string.something_went_wrong), tp);

            // inform user that something went wrong and return to previous activity

        }

    }

    private void registrationSuccessfulProtocol(Context context, SharedPreferences prefs, FrameLayout progress_layout){

        // if new user was successfully created on servers, do the following

        fbRef5.child(ConstantsDB.NUM_USERS_TABLE).setValue(numUsers + 1);
        fbRef5.child(ConstantsDB.NUM_HOSTS_TABLE).setValue(numHosts + 1);

        prefs.edit().clear().apply();

        // clear all info about user registration saved in sharedpreferences

        createNewUser(context);

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

        // make loading screen disappear

    }

    private void initDBlinks(){

        fbRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE);

        // DB link to users table

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE);

        // DB link to Apartments

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.ITEMS_TABLE);

        // DB link to items table (contains subtables with Place ID, containing subTables with user apartments)

        fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.EMAILS_TABLE);

        // DB link to all user emails

    }

    private void submitAllInfoToDB(){

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        // assign firebase Auth object to global variable

        submitUserInfo(fbRef1, fbAuth, DIALOG_READ);

        submitApartmentInfo(fbRef2, fbAuth);

        // submit users' apartment data to apartments DB table

        submitApartmentItemInfo(fbRef3, fbAuth);

        // submit users' apartment data to apartment items DB table

        submitInfoToEmailsField(fbRef4);

        // submit users' email to 'emails' DB table where the email addresses of all users are organized

    }

    private void submitApartmentImages(){

        for (int i = 0; i < observables.getApartmentImages().size(); i++) {

            try {

                String encodedImage = model.convertBitmapToBase64StringLoop(observables.getApartmentImages(), i);

                String apartmentImageTable = ConstantsDB.APARTMENT_IMAGE_LOOP_TABLE + (i + 1);

                submitImagesToApartmentDBTables(apartmentImageTable, encodedImage);

            } catch (Exception e) {

                // Do nothing

            }

        }

    }

    private void submitImagesToApartmentDBTables(String apartmentImageTable, String encodedImage){

        fbRef2.child(observables.getAID()).child(apartmentImageTable).setValue(encodedImage);

        fbRef3.child(observables.getLocationID()).child(observables.getAID()).child(apartmentImageTable).setValue(encodedImage);

        // assign apartment images at index to according apartment image database table

    }

    private void initExitAnim(AppCompatActivity appCompatActivity){

        exit_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        // load activity exit fade out animation

        exit_anim.setDuration(200);

    }

    private void addApartementDetailsToDB2(
            final Context context, AppCompatActivity appCompatActivity,
            FirebaseAuth fbAuth, final FrameLayout progress_layout, ViewGroup viewGroup,
            int isFromActivity
    ) {

        // for all comments check above user register method

        setSubleasedNightsLeftIfApartmentInAmsterdam(context);

        addApartmentInfoToDB(appCompatActivity, context, fbAuth);

        viewModel.makeProgressLayoutDissapearFunc(context, appCompatActivity, viewGroup, fbAuth, progress_layout, isFromActivity);

    }

    private void setSubleasedNightsLeftIfApartmentInAmsterdam(Context context){

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        // set standard number of apartment images at 1

        final String locationID = prefs.getString("savedUserDataPlaceID", "");

        if (locationID.equals(StartupMethods.AMS_PLACE_ID)){

            observables.setSubleasedNightsLeft(30);

            // if user has registered apartment in Amsterdam, set subleased nights
            // and set value that will make a dialog about the cities' regulations appear to the user

        }

    }

    @Override
    public void registerUserYesInfoNotRegistered(final Context context, final FirebaseAuth fbAuth, final FrameLayout progress_layout, final int numUsers,
                                                 AppCompatActivity appCompatActivity, ViewGroup viewGroup, int isFromActivity) {

        observables.setHostStatus(ConstantsDB.HOST_STATUS_INFO_NOT_ADDED);

        int DIALOG_NOT_READ = 0;
        registerUserNo(context, fbAuth, progress_layout, numUsers, DIALOG_NOT_READ, appCompatActivity, viewGroup, isFromActivity);

    }

    @Override
    public void registerUserNo(final Context context, final FirebaseAuth fbAuth,
                               final FrameLayout progress_layout, final int numUsers,
                               int isDialogRead,
                               AppCompatActivity appCompatActivity, ViewGroup viewGroup, int isFromActivity) {

        // set animation duration
        view.hideFab();

        createNewUserNo(
                context, fbAuth, fbRef1,
                fbRef4, fbRef5, progress_layout, numUsers,
                exit_anim, isDialogRead,
                appCompatActivity, viewGroup, isFromActivity);

    }

    @Override
    public void getNumHostsAndUsersFromDB() {

        fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "UserData");

        fbRef5.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                numUsers = dataSnapshot.child(ConstantsDB.NUM_USERS_TABLE).getValue(Integer.class);
                numHosts = dataSnapshot.child(ConstantsDB.NUM_HOSTS_TABLE).getValue(Integer.class);

                // retrieve current number of users and hosts on the platform

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @Override
    public void submitNewInfoToDB(
            Context context,  AppCompatActivity appCompatActivity, FrameLayout progress_layout,
            int isNotNowAdded, int isFromActivity,
            Typeface tp, FirebaseAuth fbAuth,
            int numUsers, ViewGroup viewGroup) {

        prepareMethod(appCompatActivity, context);

        if (StartupMethods.isOnline(context)){

            fbAuth = FirebaseAuth.getInstance();

            view.showProgressLayout(appCompatActivity);

            createAllDBTableReferenceLinks();

            startSpecifiedRegisterProtocol(
                    context, appCompatActivity, fbAuth, progress_layout,
                    isNotNowAdded, isFromActivity, tp, viewGroup);

        }else {

            ConfirmRegistrationActivityView view = new ConfirmRegistrationViewImplementer(context, viewGroup);

            view.showErrorMessage(context);

        }

    }

    private void startSpecifiedRegisterProtocol(
            Context context, AppCompatActivity appCompatActivity, FirebaseAuth fbAuth,
            FrameLayout progress_layout, int isNotNowAdded, int isFromActivity,
            Typeface tp, ViewGroup viewGroup){

        if (isNotNowAdded == 0 && choice == 1 && isFromActivity != 1){

            registerUserYes(context, appCompatActivity, fbAuth, progress_layout, tp, isFromActivity);

            //Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();

            // register user as a host

        } else if (isFromActivity == 1 && choice == 1){

            addApartementDetailsToDB2(context, appCompatActivity, fbAuth, progress_layout, viewGroup, isFromActivity);

            //Toast.makeText(context, "add apartment details", Toast.LENGTH_SHORT).show();

            // if user clicked 'become host' button in previous activity, add new apartment data to user

        }  else if (isNotNowAdded == 1 && isFromActivity == 2){

            registerUserYesInfoNotRegistered(context, fbAuth, progress_layout, numUsers, appCompatActivity, viewGroup, isFromActivity);

            // register user as new host with apartment data not entered in yet

        } else if (choice == 2) {

            observables.setHostStatus(ConstantsDB.HOST_STATUS_NOT_A_HOST_ADDED);

            registerUserNo(
                    context, fbAuth,
                    progress_layout, numUsers,
                    DIALOG_READ, appCompatActivity, viewGroup, isFromActivity);

            //Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();

            // register user as new user without apartment

        }

    }

    private void prepareMethod(AppCompatActivity appCompatActivity, Context context){

        choice = model.getYesOrNoSelected(context);

        exit_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        // load activity exit fade out animation
    }

    private void createAllDBTableReferenceLinks(){

        fbRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE);

        // DB link to users table

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE);

        // DB link to Apartments

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.ITEMS_TABLE);

        // DB link to items table (contains subtables with Place ID, containing subTables with user apartments)

        fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.EMAILS_TABLE);

        // DB link to all user emails

        fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USER_DATA_TABLE);

        // DB link to app users statistics

    }

    /*

    private void initAnimations(ObjectAnimator anim, ObjectAnimator anim2){

        anim = view.getObjectAnimator1();

        anim2 = view.getObjectAnimator2();

        anim.setDuration(400);
        anim2.setDuration(400);

        // set animation duration

    }

    */

    private void createNewUserNo(final Context context,
                              final FirebaseAuth fbAuth, final DatabaseReference fbRef1, final DatabaseReference fbRef4,
                              final DatabaseReference fbRef5,
                              final FrameLayout progress_layout, final int numUsers, final Animation exit_anim,
                              int isDialogRead, AppCompatActivity appCompatActivity, ViewGroup viewGroup, int isFromActivity){

        view.extractStringsFromTextViews(context);

        fbAuth.createUserWithEmailAndPassword(
                observables.getEmail(),
                appCompatActivity
                        .getIntent()
                        .getStringExtra("username"))
                        .addOnCompleteListener(task -> {

            resetProfileData(context);

            submitUserInfo(fbRef1, fbAuth, isDialogRead);

            // submit user data to 'users' DB table

            submitInfoToEmailsField(fbRef4);

            // submit user email to 'email' DB table

            incrementNumUsersCount(fbRef5, numUsers);

            // add user email to 'all emails' data field

            MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

            // make loading screen disappear when registration has finished

            StudentsCouchAnimations anims = new StudentsCouchAnimations(appCompatActivity);

            anims.progressLayoutExitAnimationFunc(
                    appCompatActivity, context, view.getFab(), exit_anim, progress_layout,
                    viewGroup, view, isFromActivity);



        });

    }

    private void resetProfileData(Context context){

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        prefs.edit().clear().apply();

    }

    @Override
    public void addApartmentInfoToDB(AppCompatActivity appCompatActivity, Context context, FirebaseAuth fbAuth){

        cityRegulationsRead = "1";

        // set city regulations info dialog as unread

        view.extractApartmentStringsFromTextViews(context);

        observables.setAID(StartupMethods.getRandomString(100));

        // create unique ID for users' apartment

        incrementNumHostsCount(fbRef5, numHosts);

        // update statistics on how many hosts have registered themselves on the platform

        addUserInfo(fbRef1, fbAuth);

        // submit user data to users' Db table under 'users' Db table

        addAllApartmentData(appCompatActivity, context, fbAuth);

    }

    private void addAllApartmentData(AppCompatActivity appCompatActivity, Context context, FirebaseAuth fbAuth){

        addApartmentImages(fbRef2, fbRef3);

        addApartmentInfo(fbRef2, fbAuth);

        addApartmentItem(fbRef3,fbAuth);

        retrieveAndSetItemProfilePicture(appCompatActivity, context, fbRef3);

        Toast.makeText(context, context.getResources().getString(R.string.info_added_apartment), Toast.LENGTH_LONG).show();

        //appCompatActivity.finish();


    }

    private void incrementNumHostsCount(DatabaseReference fbRef5, int numHosts){

        fbRef5.child(ConstantsDB.NUM_HOSTS_TABLE).setValue(numHosts + 1);

    }

    private void incrementNumUsersCount(DatabaseReference fbRef5, int numUsers){

        fbRef5.child(ConstantsDB.NUM_USERS_TABLE).setValue(numUsers + 1);

    }

    private void addApartmentImages(DatabaseReference fbRef2, DatabaseReference fbRef3){

        for (int i = 0; i < observables.getApartmentImages().size(); i++) {

            String encodedImage = model.convertBitmapToBase64StringLoop(observables.getApartmentImages(), i);

            // convert byte array object to Base64 encoded String

            String apartmentImageTable = ConstantsDB.APARTMENT_IMAGE_LOOP_TABLE + (i + 1);

            fbRef2.child(observables.getAID()).child(apartmentImageTable).setValue(encodedImage);

            fbRef3.child(observables.getLocationID()).child(observables.getAID()).child(apartmentImageTable).setValue(encodedImage);

            // assign apartment images at index to according apartment image database table

        }

    }

    private void retrieveAndSetItemProfilePicture(AppCompatActivity appCompatActivity, Context context, DatabaseReference fbRef3){

        DatabaseReference fbRef =  FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + FirebaseAuth.getInstance().getUid() + ConstantsDB.USER_PROFILE_IMAGE_TABLE_URL_REFERENCE);

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fbRef3.child(observables.getLocationID()).child(observables.getAID()).child(
                        ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).setValue(dataSnapshot.getValue(String.class));

                view.makeProgressLayoutDisappear(appCompatActivity, context);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // set profile image for apartment item to user's current profile picture

    }

    private void addApartmentItem(DatabaseReference fbRef3, FirebaseAuth fbAuth){

        Bitmap bitmap10 = view.getApartmentImage1_1();

        // get first, second apartment images from second apartment image imageView

        String encodedImage10 = model.convertBitmapToBase64String(bitmap10);
        // convert byte array object to Base64 encoded String

        submitApartmentItemToDB(fbRef3, fbAuth);

    }

    private void submitApartmentItemToDB(DatabaseReference fbRef3, FirebaseAuth fbAuth){

        String AID = observables.getAID();

        String locationID = observables.getLocationID();

        fbRef3.child(locationID).child(AID).child(ConstantsDB.UID_TABLE).setValue(fbAuth.getUid());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).setValue(observables.getHouse_number());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_FIRST_NAME_HOST_TABLE).setValue(observables.getFirstName());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_APARTMENT_PRICE_TABLE).setValue(observables.getPrice_per_night());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_AVERAGE_RATING_TABLE).setValue(0);
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).setValue(observables.getProfile_picture());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.APARTMENT_STREET_TABLE).setValue(observables.getStreet());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_VISIBILITY_TABLE).setValue(1);

        // submit users' apartment data to apartment items DB table

    }

    private void addUserInfo (DatabaseReference fbRef1, FirebaseAuth fbAuth){

        fbRef1.child(Objects.requireNonNull(fbAuth.getUid())).child(ConstantsDB.APARTMENT_HOST_STATUS_TABLE).child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(0);
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_AID_TABLE).setValue(observables.getAID());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_COUNTRY_CODE_TABLE).setValue(observables.getCountryCode());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_IS_IBAN_ADDED_TABLE).setValue(0);

        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_HOST_STATUS_TABLE).child(
                ConstantsDB.APARTMENT_STATUS_TABLE).setValue(ConstantsDB.HOST_STATUS_USER_IS_HOST);

        // submit user data to users' Db table under 'users' Db table

    }

    private void addApartmentInfo(DatabaseReference fbRef2, FirebaseAuth fbAuth){

        String AID = observables.getAID();

        fbRef2.child(AID).child(ConstantsDB.APARTMENT_IS_CITY_REGULATIONS_DIALOG_READ_TABLE).setValue(cityRegulationsRead);
        fbRef2.child(AID).child(ConstantsDB.UID_TABLE).setValue(fbAuth.getUid());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_CITY_VILLAGE_TABLE).setValue(observables.getCityVillage());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_STREET_TABLE).setValue(observables.getStreet());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).setValue(observables.getHouse_number());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_TITLE_TABLE).setValue(observables.getTitle());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_DESCRIPTION_TABLE).setValue(observables.getDescription());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_PRICE_PER_NIGHT_TABLE).setValue(observables.getPrice_per_night());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE).setValue(observables.getIs_two_people_allowed());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE).setValue(observables.getSubleasedNightsLeft());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_COUNTRY_CODE_TABLE).setValue(observables.getCountryCode());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).setValue(observables.getLocationID());

        // submit users' apartment data to apartments Db table

        showInfoInLogCat(fbAuth);

    }

    private void showInfoInLogCat(FirebaseAuth fbAuth){

        Log.i("accreg", "isCityRegulationsDialogRead: " + cityRegulationsRead);
        Log.i("accreg", "UID: " + fbAuth.getUid());
        Log.i("accreg", "city of village: " + observables.getCityVillage());
        Log.i("accreg", "street: " + observables.getStreet());
        Log.i("accreg", "house number: " + observables.getPrice_per_night());
        Log.i("accreg", "title: " + observables.getTitle());
        Log.i("accreg", "description: " + observables.getDescription());
        Log.i("accreg", "price per night: " + observables.getHouse_number());
        Log.i("accreg", "is two people allowed: " + observables.getIs_two_people_allowed());
        Log.i("accreg", "subleased nights: " + observables.getSubleasedNightsLeft());
        Log.i("accreg", "country code: " + observables.getCountryCode());
        Log.i("accreg", "location id: " + observables.getLocationID());

    }

    private void submitUserInfo(DatabaseReference fbRef1, FirebaseAuth fbAuth, int isDialogRead){

        fbRef1.child(Objects.requireNonNull(fbAuth.getUid())).child(ConstantsDB.USER_FIRST_NAME_TABLE).setValue(observables.getFirstName());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_LAST_NAME_TABLE).setValue(observables.getLastName());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_EMAIL_TABLE).setValue(observables.getEmail());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_BIRTHDATE_TABLE).setValue(observables.getBirthdate());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_BIRTHDATE_YEAR_TABLE).setValue(observables.getYear());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_BIRTHDATE_MONTH_TABLE).setValue(observables.getMonth());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_BIRTHDATE_DAY_TABLE).setValue(observables.getDay());

        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_EMOJI_RATING_TABLE).child(
                ConstantsDB.USER_HAPPY_EMOJIS_RATING_TABLE).setValue(0);
        fbRef1.child(fbAuth.getUid()).child(
                ConstantsDB.USER_EMOJI_RATING_TABLE).child(ConstantsDB.USER_UNHAPPY_EMOJIS_RATING_TABLE).setValue(0);

        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_PLACE_OF_RESIDENCE_TABLE).setValue(observables.getPlace_of_residence());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_PROFILE_IMAGE_TABLE).setValue(observables.getProfile_picture());

        // submit data to user DB table of current user

        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_GENDER_TABLE).setValue(observables.getGender());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_HOST_STATUS_TABLE).child(ConstantsDB.APARTMENT_STATUS_TABLE).setValue(observables.getHostStatus());
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_WARNINGS_TABLE).child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(0);
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.USER_WARNINGS_TABLE).child(ConstantsDB.USER_NUM_WARNINGS_TABLE).setValue(0);
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_IS_IBAN_ADDED_TABLE).setValue(0);
        fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_COUNTRY_CODE_TABLE).setValue(observables.getCountryCode());

        showInfoInLogCat2(isDialogRead);


        if (observables.getAID() != null){

            fbRef1.child(fbAuth.getUid()).child(ConstantsDB.APARTMENT_AID_TABLE).setValue(observables.getAID());

        }

    }

    private void showInfoInLogCat2(int isDialogRead){

        Log.i("accreg", "firstname: " + observables.getFirstName());
        Log.i("accreg", "lastname: " + observables.getLastName());
        Log.i("accreg", "email: " + observables.getEmail());
        Log.i("accreg", "birthdate: " + observables.getBirthdate());
        Log.i("accreg", "birthdateYear: " + observables.getYear());
        Log.i("accreg", "birthdateMonth: " + observables.getMonth());
        Log.i("accreg", "birthdateDay: " + observables.getDay());
        Log.i("accreg", "place of residence: " + observables.getPlace_of_residence());
        Log.i("accreg", "gender: " + observables.getGender());
        Log.i("accreg", "host status: " + observables.getHostStatus());
        Log.i("accreg", "is dialog read: " + isDialogRead);
        Log.i("accreg", "country code: " + observables.getCountryCode());

    }

    private void submitApartmentInfo(DatabaseReference fbRef2, FirebaseAuth fbAuth){

        cityRegulationsRead = "1";

        // set city regulations info dialog to 'unread'

        String AID = observables.getAID();

        fbRef2.child(AID).child(ConstantsDB.APARTMENT_IS_CITY_REGULATIONS_DIALOG_READ_TABLE).setValue(cityRegulationsRead);
        fbRef2.child(AID).child(ConstantsDB.UID_TABLE).setValue(fbAuth.getUid());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_CITY_VILLAGE_TABLE).setValue(observables.getCityVillage());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_STREET_TABLE).setValue(observables.getStreet());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).setValue(observables.getPrice_per_night());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_TITLE_TABLE).setValue(observables.getTitle());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_DESCRIPTION_TABLE).setValue(observables.getDescription());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_PRICE_PER_NIGHT_TABLE).setValue(observables.getHouse_number());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE).setValue(observables.getIs_two_people_allowed());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_IS_IBAN_ADDED_TABLE).setValue(0);
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE).setValue(observables.getSubleasedNightsLeft());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_COUNTRY_CODE_TABLE).setValue(observables.getCountryCode());
        fbRef2.child(AID).child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).setValue(observables.getLocationID());

        submitApartmentImages();

        showInfoInLogCat3(fbAuth);

    }

    private void showInfoInLogCat3(FirebaseAuth fbAuth){

        Log.i("accreg", "isCityRegulationsDialogRead: " + cityRegulationsRead);
        Log.i("accreg", "UID: " + fbAuth.getUid());
        Log.i("accreg", "city of village: " + observables.getCityVillage());
        Log.i("accreg", "street: " + observables.getStreet());
        Log.i("accreg", "house number: " + observables.getPrice_per_night());
        Log.i("accreg", "title: " + observables.getTitle());
        Log.i("accreg", "description: " + observables.getDescription());
        Log.i("accreg", "price per night: " + observables.getHouse_number());
        Log.i("accreg", "is two people allowed: " + observables.getIs_two_people_allowed());
        Log.i("accreg", "subleased nights: " + observables.getSubleasedNightsLeft());
        Log.i("accreg", "country code: " + observables.getCountryCode());
        Log.i("accreg", "location id: " + observables.getLocationID());

        // submit users' apartment data to apartments DB table

    }

    private void submitApartmentItemInfo(DatabaseReference fbRef3, FirebaseAuth fbAuth){

        String locationID = observables.getLocationID();

        String AID = observables.getAID();

        fbRef3.child(locationID).child(AID).child(ConstantsDB.UID_TABLE).setValue(fbAuth.getUid());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_APARTMENT_PRICE_TABLE).setValue(observables.getHouse_number());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_FIRST_NAME_HOST_TABLE).setValue(observables.getFirstName());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).setValue(observables.getPrice_per_night());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_AVERAGE_RATING_TABLE).setValue(0);
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).setValue(observables.getProfile_image());
        fbRef3.child(locationID).child(AID).child(ConstantsDB.ITEM_VISIBILITY_TABLE).setValue(1);
        fbRef3.child(locationID).child(AID).child(ConstantsDB.APARTMENT_STREET_TABLE).setValue(observables.getStreet());

    }

    private void submitInfoToEmailsField(DatabaseReference fbRef4){

        String emailPath = StartupMethods.getRandomString(40);

        fbRef4.child(emailPath).setValue(observables.getEmail());

    }

}
