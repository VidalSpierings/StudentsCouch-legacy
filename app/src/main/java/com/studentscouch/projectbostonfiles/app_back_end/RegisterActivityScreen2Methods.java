package com.studentscouch.projectbostonfiles.app_back_end;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen3;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.OnEmailCheckListener;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen2;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class RegisterActivityScreen2Methods {

    private static int
            year3, month3, day3;

    private static String mGender;

    static Intent i;

    private static Calendar minAge;

    private static Calendar userAge;

    private static String
            dayString,
            monthString,
            yearString;

    private static String
            firstName_string,
            lastName_string,
            email_string,
            birthdate_string,
            place_of_residence_string,
            gender_boolean;

    private static void setMaleGenderIconFunctionality(
            Context context, String gender, ImageView gender_male,
            ImageView gender_female, EditText firstName_edittext, EditText lastName_edittext, EditText email_edittext,
            EditText password_edittext, EditText birthdate_edittext, EditText spinner, FloatingActionButton fab) {

        RegisterActivityScreen2.isSelected = true;

        setMaleGenderSelected(context, gender_male, gender_female);

        showHideFabAccToIfAllInfoEntered(
                context, firstName_edittext, lastName_edittext,
                email_edittext, password_edittext,
                birthdate_edittext, spinner,
                fab);

    }

    private static void showHideFabAccToIfAllInfoEntered(
            Context context, EditText firstName_edittext,
            EditText lastName_edittext, EditText email_edittext,
            EditText password_edittext, EditText birthdate_edittext,
            EditText spinner, FloatingActionButton fab){

        if (
                !firstName_edittext.getText().toString().equals("") &&
                        !lastName_edittext.getText().toString().equals("") &&
                        !email_edittext.getText().toString().equals("") &&
                        !password_edittext.getText().toString().equals("") &&
                        !birthdate_edittext.getText().toString().equals("") &&
                        !birthdate_edittext.getText().toString().equals(
                                context.getResources().getString(R.string.birthdate)) &&
                        !spinner.getText().toString().equals(
                                context.getResources().getString(R.string.place_of_residence)) &&
                        RegisterActivityScreen2.isSelected


        ) {

            // if all data is entered in, show fab

            fab.show();

        } else {

            // if data in at least one datafield isn't filled in, hide fab

            fab.hide();

        }

    }

    public static void initialiseMaleGenderIconOnClickListener(
            final Context context, final ImageView gender_male, final String gender,
            final ImageView gender_female, final EditText firstName_edittext, final EditText lastName_edittext,
            final EditText email_edittext, final EditText password_edittext, final EditText birthdate_edittext,
            final EditText spinner, final FloatingActionButton fab) {

        gender_male.setOnClickListener(view -> RegisterActivityScreen2Methods.setMaleGenderIconFunctionality(
                context, gender, gender_male,
                gender_female, firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab));

    }

    private static void setFemaleGenderIconFunctionality(
            Context context, String gender, ImageView gender_male,
            ImageView gender_female, EditText firstName_edittext, EditText lastName_edittext, EditText email_edittext,
            EditText password_edittext, EditText birthdate_edittext, EditText spinner, FloatingActionButton fab) {

        RegisterActivityScreen2.isSelected = true;

        setFemaleGenderSelected(context, gender_male, gender_female);

        showHideFabAccToIfAllInfoEntered(
                context, firstName_edittext, lastName_edittext,
                email_edittext, password_edittext,
                birthdate_edittext, spinner,
                fab);

    }

    public static void initialiseFemaleGenderIconOnClickListener(
            final Context context, final String gender, final ImageView gender_male,
            final ImageView gender_female, final EditText firstName_edittext, final EditText lastName_edittext, final EditText email_edittext,
            final EditText password_edittext, final EditText birthdate_edittext, final EditText spinner, final FloatingActionButton fab
    ) {

        gender_female.setOnClickListener(view -> RegisterActivityScreen2Methods.setFemaleGenderIconFunctionality(
                context, gender, gender_male,
                gender_female, firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab));

    }

    private static void setCityVillageEditTextClickFunctionality(
            Context context, AppCompatActivity appCompatActivity,
            int LOCATION_PERMISSION_REQUEST_CODE, int PLACE_PICKER_REQUEST,
            TextView error_message, int ERROR_DIALOG_REQUEST, Typeface tp,
            EditText firstName_editText, EditText lastNameEditText,
            EditText email_edittext, EditText password_edittext, EditText birthdate_edittext, EditText spinner,
            FloatingActionButton fab) {

        ConnectivityManager connectivityManager = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            attemptLaunchIntent(
                    context, appCompatActivity, ERROR_DIALOG_REQUEST,
                    error_message, tp, LOCATION_PERMISSION_REQUEST_CODE,
                    PLACE_PICKER_REQUEST);


        } else {

            showErrorMessage(context, error_message, tp);

        }

        initialiseEditTextTextChangeListener(
                context, spinner, firstName_editText, lastNameEditText,
                email_edittext, password_edittext, birthdate_edittext, spinner, fab);

    }

    private static void attemptLaunchIntent(Context context, AppCompatActivity appCompatActivity, int ERROR_DIALOG_REQUEST, TextView error_message, Typeface tp, int LOCATION_PERMISSION_REQUEST_CODE, int PLACE_PICKER_REQUEST){

        if (MyApplication.isServicesOkRegisterActivities(context, appCompatActivity, ERROR_DIALOG_REQUEST, error_message, tp)) {

            // if app successfully connects to google play services, do the following

            requestPermissionsOrLaunchIntent(
                    context, appCompatActivity, LOCATION_PERMISSION_REQUEST_CODE, PLACE_PICKER_REQUEST);

        }

    }

    public static void initialiseCityVillageEditTextOnClickListener(
            final Context context, final AppCompatActivity appCompatActivity, final int LOCATION_PERMISSION_REQUEST_CODE, final int PLACE_PICKER_REQUEST,
            final TextView error_message, final EditText spinner, final int ERROR_DIALOG_REQUEST, final Typeface tp,
             final EditText firstName_editText, final EditText lastName_editText, final EditText email_edittext,
            final EditText password_edittext, final EditText birthdate_edittext, final FloatingActionButton fab) {

        spinner.setOnClickListener(view -> RegisterActivityScreen2Methods.setCityVillageEditTextClickFunctionality(
                context, appCompatActivity, LOCATION_PERMISSION_REQUEST_CODE, PLACE_PICKER_REQUEST,
                error_message, ERROR_DIALOG_REQUEST, tp, firstName_editText,
                lastName_editText, email_edittext, password_edittext, birthdate_edittext, spinner, fab));

    }

    private static void setFabFunctionality(
            final Context context, final FrameLayout progress_layout, final FloatingActionButton fab,
            final EditText email_edittext, final EditText password_edittext, final Animation exit_anim_progress_layout,
            final TextView error_message, final EditText firstName_edittext, final EditText lastName_edittext, final EditText spinner,
            final EditText birthdate_edittext, final AppCompatActivity appCompatActivity, final String gender,
            final String countryCode, final List<View> animatingView, final List<Boolean> setStartingDelay,
            final Typeface tp, boolean isRegistered) {

        // third animation object (anim6) false!!!

        fab.hide();

        // make loading screen disappear

        String email = email_edittext.getText().toString();

        // retrieve user email

        checkEditTexts(
                context, progress_layout, fab, email_edittext, password_edittext,
                exit_anim_progress_layout, error_message, firstName_edittext, lastName_edittext,
                spinner, birthdate_edittext, appCompatActivity, gender, countryCode,
                animatingView, setStartingDelay, tp, isRegistered, email);

    }

    private static void checkIfEmailAlreadyExists(
            final Context context, FrameLayout progress_layout, Animation exit_anim_progress_layout, boolean isRegistered,
            final TextView error_message, final FloatingActionButton fab, final EditText firstName_edittext, final EditText lastName_edittext,
            final EditText email_edittext, final EditText password_edittext, final EditText spinner, final EditText birthdate_edittext,
            final Typeface tp, final AppCompatActivity appCompatActivity, final String gender,
            final String countryCode, final List<View> animatingView, final List<Boolean> setStartingDelay) {

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

        // turn method variable into global variable

        startNextActivityOnAnimationEnd(
                context, appCompatActivity, exit_anim_progress_layout,
                error_message, fab, firstName_edittext, lastName_edittext,
                email_edittext, password_edittext, spinner, birthdate_edittext,
                tp, isRegistered, animatingView, setStartingDelay);

    }

    private static void startNextActivityOnAnimationEnd(
            Context context, AppCompatActivity appCompatActivity, Animation exit_anim_progress_layout,
            TextView error_message, FloatingActionButton fab, EditText firstName_edittext,
            EditText lastName_edittext, EditText email_edittext, EditText password_edittext,
            EditText spinner, EditText birthdate_edittext, Typeface tp, boolean isRegistered2,
            List<View> animatingView, List<Boolean> setStartingDelay){

        exit_anim_progress_layout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                saveInfoStartNextActivity(
                        context, appCompatActivity, error_message,
                        fab, firstName_edittext, lastName_edittext,
                        email_edittext, password_edittext, spinner,
                        birthdate_edittext, tp, isRegistered2, animatingView,
                        setStartingDelay);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static void saveInfoStartNextActivity(final Context context,
                                                  final AppCompatActivity appCompatActivity,
                                                  final TextView error_message, final FloatingActionButton fab,
                                                  final EditText firstName_edittext, final EditText lastName_edittext,
                                                  final EditText email_edittext, final EditText password_edittext,
                                                  final EditText spinner, final EditText birthdate_edittext,
                                                  final Typeface tp, boolean isRegistered2,
                                                  final List<View> animatingView, final List<Boolean> setStartingDelay){

        if (isRegistered2) {

            showErrorMessageEmailAlreadyExists(context, error_message, fab, tp);

        } else {

            saveInfoStartNextActivity2(
                    context, appCompatActivity, fab, firstName_edittext,
                    lastName_edittext, email_edittext, password_edittext,
                    spinner, birthdate_edittext, animatingView, setStartingDelay);

        }

    }

    private static void saveInfoStartNextActivity2(final Context context,
                                                   final AppCompatActivity appCompatActivity,
                                                   final FloatingActionButton fab,
                                                   final EditText firstName_edittext, final EditText lastName_edittext,
                                                   final EditText email_edittext, final EditText password_edittext,
                                                   final EditText spinner, final EditText birthdate_edittext,
                                                   final List<View> animatingView, final List<Boolean> setStartingDelay){

        disableLayoutInteraction(
                fab, firstName_edittext, lastName_edittext,
                email_edittext, password_edittext, spinner,
                birthdate_edittext);

        saveAllEnteredData(
                appCompatActivity, spinner, firstName_edittext, lastName_edittext,
                email_edittext, password_edittext, birthdate_edittext);

        prepareIntent(appCompatActivity, password_edittext);

        StudentsCouchAnimations.animateActivityExitTransitionRegisterActivityScreen2(
                context, animatingView, setStartingDelay, 400,
                125, i);

        fab.hide();

    }

    private static void checkEditTexts(
            final Context context, final FrameLayout progress_layout, final FloatingActionButton fab,
            final EditText email_edittext, final EditText password_edittext, final Animation exit_anim_progress_layout,
            final TextView error_message, final EditText firstName_edittext, final EditText lastName_edittext, final EditText spinner,
            final EditText birthdate_edittext, final AppCompatActivity appCompatActivity, final String gender, final String countryCode, final List<View> animatingView, final List<Boolean> setStartingDelay,
            final Typeface tp, boolean isRegistered, String email) {

        if (StartupMethods.isOnline(context)) {

            checkEnteredDataLaunchNextActivity(
                    context, appCompatActivity,
                    progress_layout, fab, email_edittext, password_edittext,
                    exit_anim_progress_layout, error_message, firstName_edittext,
                    lastName_edittext, spinner,birthdate_edittext,
                    gender, countryCode, animatingView, setStartingDelay, tp,
                    isRegistered, email);

        }

        showErrorMessageIfUserNotOnline(context, error_message, tp);

    }

    public static void showFabWhenAllEditTextsEntered (
            final Context context, final EditText firstName_edittext, final EditText lastName_edittext, final EditText email_edittext,
            final EditText password_edittext, final EditText birthdate_edittext, final EditText spinner,
            final FloatingActionButton fab
            ) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                showHideFabAccToIfAllInfoEntered(
                        context, firstName_edittext, lastName_edittext,
                        email_edittext, password_edittext, birthdate_edittext,
                        spinner, fab);

            }
        };

        addListenerToAllEditTexts(
                firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, textWatcher);

    }

    public static void initialiseFabOnClickListener (
            final Context context, final FrameLayout progress_layout, final Animation exit_anim_progress_layout,
            final TextView error_message, final FloatingActionButton fab, final EditText firstName_edittext, final EditText lastName_edittext,
            final EditText email_edittext, final EditText password_edittext, final EditText spinner, final EditText birthdate_edittext,
            final Typeface tp, final AppCompatActivity appCompatActivity, final String gender,
            final String countryCode, final List<View> animatingView, final Animation enter_anim_progress_layout,
            final List<Boolean> setStartDelay, final FirebaseAuth fbAuth) {

        fab.setOnClickListener(view -> {

            fab.hide();

            MyApplication.makeProgressLayoutAppearFrameLayout(progress_layout, enter_anim_progress_layout);

            resumeToNextActivityIfOnline(
                    context, appCompatActivity, email_edittext, progress_layout,
                    exit_anim_progress_layout, error_message, fab, firstName_edittext,
                    lastName_edittext, password_edittext, spinner, birthdate_edittext,
                    tp, gender, countryCode, animatingView, setStartDelay, fbAuth);

        });

    }

    private static void resumeToNextActivityIfOnline(
            Context context, AppCompatActivity appCompatActivity,
            EditText email_edittext, FrameLayout progress_layout,
            Animation exit_anim_progress_layout, TextView error_message, FloatingActionButton fab,
            EditText firstName_edittext, EditText lastName_edittext, EditText password_edittext, EditText spinner,
            EditText birthdate_edittext, Typeface tp, String gender, String countryCode, List<View> animatingView,
            List<Boolean> setStartDelay, FirebaseAuth fbAuth
    ){

        String email = email_edittext.getText().toString();

        // retrieve user email

        if (StartupMethods.isOnline(context)) {

            checkDataResumeToNextActivity(
                    context, progress_layout, exit_anim_progress_layout,
                    error_message, fab, firstName_edittext, lastName_edittext,
                    email_edittext, password_edittext, spinner, birthdate_edittext,
                    tp, appCompatActivity, gender, countryCode, animatingView, setStartDelay,
                    fbAuth, email);

        } else {

            StartupMethods.showErrorMessage(context, error_message, context.getResources().getString(R.string.no_internet_connection), tp);

            // if internet connection wasn't found, inform user by showing a toast

        }

    }

    private static void isCheckEmail(final String email, FirebaseAuth fbAuth, final OnEmailCheckListener listener){
        fbAuth.fetchProvidersForEmail(email).addOnCompleteListener(task -> {

            boolean check = !Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getProviders()).isEmpty();

            listener.onSucess(check);

            // check if email entered already exists

        });

    }

    @SuppressLint("SetTextI18n")
    public static void initialiseDatePickListener(
            Context context, int i, int i1, int i2,
            EditText birthdate_edittext,
            TextView error_message, Typeface tp, FloatingActionButton fab,
            EditText firstName_editText, EditText lastNameEditText,
            EditText email_edittext, EditText password_edittext, EditText spinner) {

        initCalendarObjects(i, i1, i2);

        convertIntegerDataToString(i, i1, i2);

        birthdate_edittext.setText(dayString + "-" + monthString + "-" + yearString + " " + context.getResources().getString(R.string.dutch_date_ordering));

        // set selected date in Dutch date ordering in birthdate editText

        showFabIfUserAgeEighteenPlus(
                context, error_message, fab, tp, birthdate_edittext,
                firstName_editText, lastNameEditText, email_edittext,
                password_edittext, spinner);

    }

    private static void showFabIfUserAgeEighteenPlus(
            Context context, TextView error_message, FloatingActionButton fab,
            Typeface tp, EditText birthdate_edittext, EditText firstName_editText,
            EditText lastNameEditText, EditText email_edittext, EditText password_edittext,
            EditText spinner){

        if (minAge.before(userAge)){

            showErrorMessageUserTooYoung(context, error_message, fab, tp);

        } else {

            showFabIfAllOtherFieldsEntered(
                    context, error_message, birthdate_edittext, firstName_editText,
                    lastNameEditText, email_edittext, password_edittext, spinner, fab);

        }

    }

    public static void showFabIfInfoEntered (
            Context context, EditText firstName_edittext,
            EditText lastName_edittext, EditText email_edittext, EditText password_edittext,
            EditText birthdate_edittext, EditText spinner, FloatingActionButton fab
    ) {

        if (
                !firstName_edittext.getText().toString().equals("") &&
                        !lastName_edittext.getText().toString().equals("") &&
                        !email_edittext.getText().toString().equals("") &&
                        !password_edittext.getText().toString().equals("") &&
                        !birthdate_edittext.getText().toString().equals("") &&
                        !birthdate_edittext.getText().toString().equals(context.getResources().getString(R.string.birthdate)) &&
                        !spinner.getText().toString().equals(context.getResources().getString(R.string.place_of_residence)) &&
                        RegisterActivityScreen2.isSelected

        )
        {

            // if information is filled into all fields, show fab

            fab.show();

        }else{

            // if information is not entered into at least one editText, hide fab

            fab.hide();

        }

    }

    public static void submitSavedInfoToEditTexts(
            Context context, SharedPreferences prefs, EditText firstName_edittext, EditText lastName_edittext,
            EditText email_edittext, EditText birthdate_edittext, EditText spinner, ImageView gender_male,
            ImageView gender_female) {

        if (!Objects.equals(prefs.getString("savedUserDataSharedFirstName", ""), "")){

            getAllEnteredData(context, prefs);

            try {

                displayData(
                        context, firstName_edittext, lastName_edittext, email_edittext,
                        birthdate_edittext, spinner, gender_male, gender_female);

            } catch (Exception e){

                // Do nothing

            }

        }

        displayBirthDateLocData(context, prefs, birthdate_edittext, spinner);

    }

    private static void initialiseEditTextTextChangeListener(
            final Context context, EditText currentEditText, final EditText firstName_editText, final EditText lastName_edittext,
            final EditText email_edittext, final EditText password_edittext, final EditText birthdate_edittext, final EditText spinner,
            final FloatingActionButton fab) {

        TextWatcher showFabWhenInfoEntered = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                showFabIfInfoEntered(context, firstName_editText, lastName_edittext, email_edittext,
                        password_edittext, birthdate_edittext, spinner, fab);

            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        };

        currentEditText.addTextChangedListener(showFabWhenInfoEntered);

    }

    private static void setMaleGenderSelected(Context context, ImageView gender_male, ImageView gender_female){

        mGender = "male";
        gender_male.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        gender_female.setColorFilter(null);

        // set male gender selected and female gender deselected

    }

    private static void setFemaleGenderSelected(Context context, ImageView gender_male, ImageView gender_female){

        mGender = "female";
        gender_male.setColorFilter(null);
        gender_female.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);

        // set male gender selected and female gender deselected

    }

    private static void requestPermissionsIfNotYetDone(Context context, AppCompatActivity appCompatActivity, int LOCATION_PERMISSION_REQUEST_CODE){

        ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        Toast.makeText(context, context.getResources().getString(R.string.reselect_field), Toast.LENGTH_LONG).show();

        /*

         * request access location permissions, inform user to press editText again
         * after accepting location permissions by showing a toast

         */

    }

    private static void attemptLaunchPlacePickerIntent(Context context, AppCompatActivity appCompatActivity, int PLACE_PICKER_REQUEST){

        try {

            startPingPlacePickerIntent(context, appCompatActivity, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesNotAvailableException e) {

            Toast.makeText(context, context.getResources().getString(R.string.could_not_connect_google_play_services), Toast.LENGTH_LONG).show();

            e.printStackTrace();

            // inform user when app can not connect to Google Play Services by showing a toast

        }

    }

    private static void startPingPlacePickerIntent(Context context, AppCompatActivity appCompatActivity, int PLACE_PICKER_REQUEST)
            throws GooglePlayServicesNotAvailableException{

        PingPlacePicker.Companion.setNearbySearchEnabled(false);

        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
        builder.setAndroidApiKey(context.getResources().getString(R.string.api_key_djjksdhoahberpow));
        Intent i = builder.build(appCompatActivity);
        appCompatActivity.startActivityForResult(i, PLACE_PICKER_REQUEST);

        // start place picker intent

    }

    public static void showErrorMessage(Context context, TextView error_message, Typeface tp){

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(context, 3000, error_message, context.getResources().getString(R.string.no_internet_connection), tp);

        // inform user when internet connection was not found with the help of a toast

    }

    private static void requestPermissionsOrLaunchIntent(Context context, AppCompatActivity appCompatActivity, int LOCATION_PERMISSION_REQUEST_CODE, int PLACE_PICKER_REQUEST){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissionsIfNotYetDone(context, appCompatActivity, LOCATION_PERMISSION_REQUEST_CODE);

        } else {

            attemptLaunchPlacePickerIntent(context, appCompatActivity, PLACE_PICKER_REQUEST);

        }

    }

    private static void showErrorMessageEmailAlreadyExists(Context context, TextView error_message, FloatingActionButton fab, Typeface tp){

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(
                context, 2500, error_message,
                context.getResources().getString(R.string.email_username_exists), tp);

        fab.hide();

        // inform user that e-mail already exists with the help of a toast

    }

    private static void disableLayoutInteraction(
            FloatingActionButton fab, EditText firstName_edittext, EditText lastName_edittext,
            EditText email_edittext, EditText password_edittext, EditText spinner,
            EditText birthdate_edittext){

        fab.setClickable(false);
        firstName_edittext.setEnabled(false);
        lastName_edittext.setEnabled(false);
        email_edittext.setEnabled(false);
        password_edittext.setEnabled(false);
        spinner.setEnabled(false);
        birthdate_edittext.setEnabled(false);

        // prevent user from selecting fields after animation have finished

    }

    private static void saveAllEnteredData(
            AppCompatActivity appCompatActivity, EditText spinner, EditText firstName_edittext,
            EditText lastName_edittext, EditText email_edittext, EditText password_edittext, EditText birthdate_edittext){

        SharedPreferences.Editor editor = appCompatActivity.getSharedPreferences("savedUserData", MODE_PRIVATE).edit();

        editor.putString("savedUserDataShared_place_of_residence", spinner.getText().toString());
        editor.putString("savedUserDataSharedFirstName", firstName_edittext.getText().toString());
        editor.putString("savedUserDataSharedLastName", lastName_edittext.getText().toString());
        editor.putString("savedUserDataEmail", email_edittext.getText().toString());
        editor.putString("savedUserDataSharedPassword", password_edittext.getText().toString());
        editor.putString("savedUserDataSharedBirthdate", birthdate_edittext.getText().toString());
        editor.putInt("savedUserDataSharedBirthdateDay", day3);
        editor.putInt("savedUserDataSharedBirthdateMonth", month3);
        editor.putInt("savedUserDataSharedBirthdateYear", year3);
        editor.putString("savedUserDataSharedGender", mGender);
        editor.putString("savedUserDataSharedCountryCode", RegisterActivityScreen2.countryCode);

        // pass metadata into intent

        editor.apply();

        // save entered information in sharedPreferences

    }

    private static void prepareIntent(AppCompatActivity appCompatActivity, EditText password_edittext){

        i = new Intent(appCompatActivity, RegisterActivityScreen3.class);

        i.putExtra("username", password_edittext.getText().toString());

        i.putExtra("isFromProfileActivity", 2);

        // pass pw into intent

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private static void showErrorMessageIfUserNotOnline(Context context, TextView error_message, Typeface tp){

        if (!StartupMethods.isOnline(context)) {

            StartupMethods.showErrorMessage(context, error_message, context.getResources().getString(R.string.no_internet_connection), tp);

            // if internet connection wasn't found, inform user by showing a toast

        }

    }

    private static void checkEnteredDataLaunchNextActivity(final Context context, final AppCompatActivity appCompatActivity, final FrameLayout progress_layout, final FloatingActionButton fab,
                                                           final EditText email_edittext, final EditText password_edittext, final Animation exit_anim_progress_layout,
                                                           final TextView error_message, final EditText firstName_edittext, final EditText lastName_edittext,
                                                           final EditText spinner, final EditText birthdate_edittext,
                                                           final String gender,
                                                           final String countryCode, final List<View> animatingView,
                                                           final List<Boolean> setStartingDelay, final Typeface tp, boolean isRegistered, String email){

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password_edittext.length() >= 8) {

            // if information filled into editText is an email, do the following

            checkIfEmailAlreadyExists(
                    context, progress_layout, exit_anim_progress_layout, isRegistered,
                    error_message, fab, firstName_edittext, lastName_edittext,
                    email_edittext, password_edittext, spinner, birthdate_edittext,
                    tp, appCompatActivity, gender, countryCode, animatingView,
                    setStartingDelay);

        }else if (password_edittext.length() <= 9){

            StartupMethods.showErrorMessageTemporarily(context, 2000, error_message, context.getResources().getString(R.string.password_length), tp);

            // inform user that password needs to be at least 8 characters long by showing a toast

            progress_layout.setVisibility(View.INVISIBLE);

        } else {

            progress_layout.setVisibility(View.INVISIBLE);

            StartupMethods.showErrorMessageTemporarily(context, 2000, error_message, context.getResources().getString(R.string.email_invalid), tp);

            // if email address doesn't exist, inform user by showing a toast

        }

    }

    private static void addListenerToAllEditTexts(EditText firstName_edittext, EditText lastName_edittext, EditText email_edittext, EditText password_edittext, EditText birthdate_edittext, TextWatcher textWatcher){

        firstName_edittext.addTextChangedListener(textWatcher);
        lastName_edittext.addTextChangedListener(textWatcher);
        email_edittext.addTextChangedListener(textWatcher);
        password_edittext.addTextChangedListener(textWatcher);
        birthdate_edittext.addTextChangedListener(textWatcher);

        // apply textChangedListener to all editText in activity

    }

    private static void checkDataResumeToNextActivity(final Context context, final FrameLayout progress_layout, final Animation exit_anim_progress_layout,
                                                      final TextView error_message, final FloatingActionButton fab, final EditText firstName_edittext, final EditText lastName_edittext,
                                                      final EditText email_edittext, final EditText password_edittext, final EditText spinner, final EditText birthdate_edittext,
                                                      final Typeface tp, final AppCompatActivity appCompatActivity, final String gender,
                                                      final String countryCode, final List<View> animatingView,
                                                      final List<Boolean> setStartDelay, final FirebaseAuth fbAuth,
                                                      String email){

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password_edittext.length() >= 8) {

            // if information filled into editText is an email, do the following

            checkDuplicateEmailChangeFabFunc(context, progress_layout, exit_anim_progress_layout,
                    error_message, fab, firstName_edittext, lastName_edittext,
                    email_edittext, password_edittext, spinner, birthdate_edittext,
                    tp, appCompatActivity, gender, countryCode, animatingView, setStartDelay,
                    fbAuth);

        }else if (password_edittext.length() <= 9){

            StartupMethods.showErrorMessageTemporarily(context, 2000, error_message, context.getResources().getString(R.string.password_length), tp);

            //inform user that password needs to be at least 8 characters long by showing a toast

            progress_layout.setVisibility(View.INVISIBLE);

        } else {

            progress_layout.setVisibility(View.INVISIBLE);

            StartupMethods.showErrorMessageTemporarily(context, 2000, error_message, context.getResources().getString(R.string.email_invalid), tp);

            // if email address doesn't exist, inform user by showing a toast

        }

    }

    private static void checkDuplicateEmailChangeFabFunc(final Context context, final FrameLayout progress_layout, final Animation exit_anim_progress_layout,
                                                         final TextView error_message, final FloatingActionButton fab, final EditText firstName_edittext, final EditText lastName_edittext,
                                                         final EditText email_edittext, final EditText password_edittext, final EditText spinner, final EditText birthdate_edittext,
                                                         final Typeface tp, final AppCompatActivity appCompatActivity, final String gender,
                                                         final String countryCode, final List<View> animatingView,
                                                         final List<Boolean> setStartDelay, final FirebaseAuth fbAuth){

        isCheckEmail(email_edittext.getText().toString(), fbAuth, isRegistered -> {

            // check if email already exists

            RegisterActivityScreen2Methods.setFabFunctionality(
                    context, progress_layout, fab,
                    email_edittext, password_edittext, exit_anim_progress_layout,
                    error_message, firstName_edittext, lastName_edittext, spinner,
                    birthdate_edittext, appCompatActivity, gender, countryCode, animatingView,
                    setStartDelay, tp, isRegistered);

            // set fab behaviour

        });

    }

    private static void initCalendarObjects(int i, int i1, int i2){

        userAge = new GregorianCalendar(i, i1 + 1, i2);

        initMinimumAge();

    }

    private static void initMinimumAge(){

        minAge = new GregorianCalendar();

        // create calendar objects with age of user and minimum required age for using StudentsCouch (18)

        minAge.add(Calendar.YEAR, - 18);

        // subtract 18 years from current date for minimum date calendar object

    }

    private static void convertIntegerDataToString(int i, int i1, int i2){

        yearString = String.valueOf(i);
        monthString = String.valueOf(i1 + 1);
        dayString = String.valueOf(i2);

        // retrieve selected year, month, day in String format

        year3 = i;
        month3 = i1 + 1;
        day3 = i2;

        // insert chosen year, month and day into global variables

    }

    private static void showErrorMessageUserTooYoung(Context context, TextView error_message, FloatingActionButton fab, Typeface tp){

        error_message.setVisibility(View.VISIBLE);

        // make error message view visible

        StartupMethods.showErrorMessageTemporarily(
                context, 3000, error_message, context.getResources().getString(R.string.minimum_age), tp);

        fab.hide();

        // inform user that they are too young to use the platform by showing an error message

    }

    private static void showFabIfAllOtherFieldsEntered(
            Context context, TextView error_message, EditText birthdate_edittext,
            EditText firstName_editText, EditText lastNameEditText, EditText email_edittext,
            EditText password_edittext, EditText spinner, FloatingActionButton fab){

        error_message.setVisibility(View.INVISIBLE);

        // make error message view invisible

        initialiseEditTextTextChangeListener(
                context, birthdate_edittext, firstName_editText, lastNameEditText,
                email_edittext, password_edittext, birthdate_edittext, spinner, fab);

    }

    private static void getAllEnteredData(Context context, SharedPreferences prefs){

        firstName_string = prefs.getString("savedUserDataSharedFirstName", null);
        lastName_string = prefs.getString("savedUserDataSharedLastName", null);
        email_string = prefs.getString("savedUserDataEmail", null);
        birthdate_string = prefs.getString("savedUserDataSharedBirthdate", context.getResources().getString(R.string.birthdate));
        place_of_residence_string = prefs.getString("savedUserDataShared_place_of_residence", context.getResources().getString(R.string.place_of_residence));
        gender_boolean = prefs.getString("savedUserDataSharedGender", "");

        // retrieve data if saved (re-opening, returning from other activity, etc.)

    }

    private static void enterDataIntoTextViews(
            EditText firstName_edittext, EditText lastName_edittext,
            EditText email_edittext, EditText birthdate_edittext, EditText spinner){

        firstName_edittext.setText(firstName_string);
        lastName_edittext.setText(lastName_string);
        email_edittext.setText(email_string);
        birthdate_edittext.setText(birthdate_string);
        spinner.setText(place_of_residence_string);

        // enter retrieved data into editTexts

    }

    private static void setMaleSelected(Context context, ImageView gender_male, ImageView gender_female){

        gender_male.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        gender_female.setColorFilter(null);

        // set male to selected

    }

    private static void setFemaleSelected(Context context, ImageView gender_male, ImageView gender_female){

        gender_female.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        gender_male.setColorFilter(null);

        // set female to selected

    }

    private static void displayData(
            Context context, EditText firstName_edittext, EditText lastName_edittext,
            EditText email_edittext, EditText birthdate_edittext, EditText spinner,
            ImageView gender_male, ImageView gender_female){

        enterDataIntoTextViews(
                firstName_edittext, lastName_edittext, email_edittext, birthdate_edittext, spinner);

        if (gender_boolean.equals("male")){

            setMaleSelected(context, gender_male, gender_female);

        } else if (gender_boolean.equals("female")){

            setFemaleSelected(context, gender_male, gender_female);

        }

    }

    private static void displayBirthDateLocData(Context context, SharedPreferences prefs, EditText birthdate_edittext, EditText spinner){

        if (Objects.equals(prefs.getString("savedUserDataSharedFirstName", ""), "")) {

            birthdate_edittext.setText(context.getResources().getString(R.string.birthdate));
            spinner.setText(context.getResources().getString(R.string.place_of_residence));

            // enter retrieved data into more editTexts

        }

    }

    }
