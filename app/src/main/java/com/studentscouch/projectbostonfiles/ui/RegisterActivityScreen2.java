package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityScreen2Methods;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class RegisterActivityScreen2 extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private TextView
            error_message,
            country_of_residence_textView,
            email_textView,
            firstName_textView,
            lastName_textView,
            password_textView;

    private EditText
            firstName_edittext,
            lastName_edittext,
            email_edittext,
            password_edittext,
            birthdate_edittext,
            spinner;

    private FloatingActionButton fab;

    ViewGroup relativeLayout;

    private LinearLayout
            firstName_layout,
            lastName_layout,
            email_layout,
            password_layout,
            gender_birthdate_layout;

    private Float
            firstName_layout_org_pos_x,
            lastName_layout_org_pos_x,
            email_layout_org_pos_x,
            password_layout_org_pos_x,
            country_of_residence_textView_org_pos_x,
            spinner_org_pos_x,
            gender_birthdate_layout_org_pos_x;

    private ImageView
            gender_female,
            gender_male;

    private Animation
            enter_anim_progress_layout,
            exit_anim_progress_layout;

    private static final int DIALOG_ID = 234987;

    public static int
            year,
            month,
            day;

    private Typeface tp;

    private static final int PLACE_PICKER_REQUEST = 273;

    private static final int ERROR_DIALOG_REQUEST = 8978;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 3472;

    private String city = null;
    public static String countryCode = null;

    public static boolean isSelected = false;

    private String gender;

    private FirebaseAuth fbAuth;

    private FrameLayout progress_layout;

    private List<EditText> allEditTexts;

    private List<View> animatingView = null;

    private List<Boolean> setStartDelay = null;

    private List<Float> orig_pos;

    private List<Boolean> setStartDelay2 = null;

    private SharedPreferences prefs;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_screen2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        setDummyDropDownList();

        initArrays();

        StudentsCouchAnimations.startEnterTransitionAnimationsRegisterActivityScreen2(
                getApplicationContext(), animatingView, setStartDelay2, orig_pos,
                400, 125
        );

        // animate activity enter transition

        initUI();

        initMiscTasks();

        RegisterActivityScreen2Methods.initialiseMaleGenderIconOnClickListener(
                getApplicationContext(), gender_male, gender,
                gender_female, firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab);

        // initialise male icon onClickListener

        RegisterActivityScreen2Methods.initialiseFemaleGenderIconOnClickListener(
                getApplicationContext(), gender, gender_male,
                gender_female, firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab);

        // initialise female icon onClickListener

        RegisterActivityScreen2Methods.initialiseCityVillageEditTextOnClickListener(
                getApplicationContext(), RegisterActivityScreen2.this,
                LOCATION_PERMISSION_REQUEST_CODE, PLACE_PICKER_REQUEST,
                error_message, spinner, ERROR_DIALOG_REQUEST, tp,
                firstName_edittext, lastName_edittext,
                email_edittext, password_edittext, birthdate_edittext, fab);

        // initialise city/village button onClickListener

        birthdate_edittext.setOnClickListener(view -> {

            showDialog(DIALOG_ID);

            // show Google Calendar date selection dialog when clicking button

        });

        setCalendarOnPopUpStartingDate();

        initAnims();

        // load animations that need to be animated

        RegisterActivityScreen2Methods.initialiseFabOnClickListener(
                getApplicationContext(), progress_layout, exit_anim_progress_layout, error_message,
                fab, firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, spinner, birthdate_edittext, tp,
                RegisterActivityScreen2.this,
                gender, countryCode, animatingView,
                enter_anim_progress_layout, setStartDelay, fbAuth);

        // initialise onClickListener for fab

        RegisterActivityScreen2Methods.showFabWhenAllEditTextsEntered(
                getApplicationContext(), firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab);

        // when info is entered into all editTexts, make fab visible. if all info from at least one editText is removed, hide fab

        RegisterActivityScreen2Methods.submitSavedInfoToEditTexts(
                getApplicationContext(), prefs, firstName_edittext, lastName_edittext,
                email_edittext, birthdate_edittext, spinner, gender_male,
                gender_female);

        // insert apartment info saved in sharedPreferences into editTexts

    }

    @Override
    protected Dialog onCreateDialog(int id){

        if (id == DIALOG_ID)

            return new DatePickerDialog(this, datePickerListener, year, month, day);
            return null;

            // open new DatePickerDialog

        // ADD APARTMENT DETAILS FUNCTIONALITY

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            RegisterActivityScreen2Methods.initialiseDatePickListener(
                    getApplicationContext(), i, i1, i2,
                    birthdate_edittext,
                    error_message, tp, fab, firstName_edittext,
                    lastName_edittext, email_edittext, password_edittext, spinner);

            // initialise datePickListener functionality

        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        enableLayoutInteraction();

        RegisterActivityScreen2Methods.showFabIfInfoEntered(
                getApplicationContext(), firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab);

        // if info is entered in all editTexts, show fab. If all info is removed from at least one editText, hide editText

        setNoBirthDateSelectedStandardText();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);

    }

    @Override
    protected void onRestart() {

        super.onRestart();

        StudentsCouchAnimations.startEnterTransitionAnimationsRegisterActivityScreen2(
                getApplicationContext(), animatingView, setStartDelay2, orig_pos,
                400, 125
        );

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            Place place = PingPlacePicker.getPlace(data);

            Geocoder geocoder = new Geocoder(this);

            try {

                retrieveCountryCodeAndCity(geocoder, Objects.requireNonNull(place));

            } catch (IOException e) {

                e.printStackTrace();

            } catch (IndexOutOfBoundsException e){

                unSelectedCityVillage();

            }

            if (place != null && city != null) {

                // if PlaceID and city name are not empty, do the following

                displayCityVillage();

            } else {

                // if either placeID or city name are empty, do the following

                unSelectCityVillage2();

            }



        }

        if (
                !firstName_edittext.getText().toString().equals("") &&
                        !lastName_edittext.getText().toString().equals("") &&
                        !email_edittext.getText().toString().equals("") &&
                        !password_edittext.getText().toString().equals("") &&
                        !birthdate_edittext.getText().toString().equals("") &&
                        !birthdate_edittext.getText().toString().equals(getResources().getString(R.string.birthdate)) &&
                        !spinner.getText().toString().equals(getResources().getString(R.string.place_of_residence)) &&
                        isSelected

        ) {

            // if all information is entered, show fab

            fab.show();

        } else {

            // if information is not entered in at least one editText, hide fab

            fab.hide();

        }

        RegisterActivityScreen2Methods.showFabIfInfoEntered(
                getApplicationContext(), firstName_edittext, lastName_edittext, email_edittext,
                password_edittext, birthdate_edittext, spinner, fab);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

                if(requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                        (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)){

                    // If user denied location permissions, do the following

                    error_message.setVisibility(View.VISIBLE);

                    StartupMethods.showErrorMessageTemporarily(getApplicationContext(), 3000, error_message, getResources().getString(R.string.enable_location), tp);

                    // inform user that accepting permissions are required for the app to work properly after
                    // permission is denied

                }



    }

    private void linkXmlElementsToJavaVariables(){

        error_message = findViewById(R.id.error_message);
        firstName_textView = findViewById(R.id.firstName_textView);
        lastName_textView = findViewById(R.id.lastName_textView);
        email_textView = findViewById(R.id.email_textView);
        password_textView = findViewById(R.id.password_textView);
        country_of_residence_textView = findViewById(R.id.country_of_residence_textView);

        firstName_edittext = findViewById(R.id.firstName_edittext);
        lastName_edittext = findViewById(R.id.lastName_edittext);
        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_editText);
        birthdate_edittext = findViewById(R.id.birthdate_edittext);

        firstName_layout = findViewById(R.id.firstName_layout);
        lastName_layout = findViewById(R.id.lastName_layout);
        email_layout = findViewById(R.id.email_layout);
        password_layout = findViewById(R.id.password_layout);
        progress_layout = findViewById(R.id.progress_layout);

        relativeLayout = findViewById(R.id.relativeLayout);

        spinner = findViewById(R.id.spinner);
        gender_birthdate_layout = findViewById(R.id.gender_birthdate_layout);

        firstName_layout_org_pos_x = firstName_layout.getX();
        lastName_layout_org_pos_x = lastName_layout.getX();
        email_layout_org_pos_x = email_layout.getX();
        password_layout_org_pos_x = password_layout.getX();
        country_of_residence_textView_org_pos_x = country_of_residence_textView.getX();
        spinner_org_pos_x = spinner.getX();
        gender_birthdate_layout_org_pos_x = gender_birthdate_layout.getX();

        gender_male = findViewById(R.id.gender_male);
        gender_female = findViewById(R.id.gender_female);

        fab = findViewById(R.id.fab);

        // link java variables with xml layout views

    }

    private void setTypefaces(){

        tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        firstName_edittext.setTypeface(tp);
        lastName_edittext.setTypeface(tp);
        email_edittext.setTypeface(tp);
        password_edittext.setTypeface(tp);
        birthdate_edittext.setTypeface(tp);

        error_message.setTypeface(tp);
        firstName_textView.setTypeface(tp);
        lastName_textView.setTypeface(tp);
        email_textView.setTypeface(tp);
        password_textView.setTypeface(tp);
        country_of_residence_textView.setTypeface(tp);
        spinner.setTypeface(tp);

        // initialise and apply typeface to all textViews in layout

    }

    private void initArrays(){

        initViewsInLayoutArray();

        initAllEditTextsArray();

        initAnimatingViewsArray();

        initStartDelayArray();

        initStartDelay2Array();

        initOrigPosArray();

    }

    private void initViewsInLayoutArray(){

        List<View> viewsInLayout = new ArrayList<>();

        viewsInLayout.add(firstName_layout);
        viewsInLayout.add(lastName_layout);
        viewsInLayout.add(email_layout);
        viewsInLayout.add(password_layout);
        viewsInLayout.add(country_of_residence_textView);
        viewsInLayout.add(spinner);
        viewsInLayout.add(gender_birthdate_layout);

        // add views to arraylist that can animate all views at once

    }

    private void initAllEditTextsArray(){

        allEditTexts = new ArrayList<>();

        allEditTexts.add(firstName_edittext);
        allEditTexts.add(lastName_edittext);
        allEditTexts.add(email_edittext);
        allEditTexts.add(password_edittext);
        allEditTexts.add(birthdate_edittext);
        allEditTexts.add(spinner);

    }

    private void initAnimatingViewsArray(){

        animatingView = new ArrayList<>();

        animatingView.add(firstName_layout);
        animatingView.add(lastName_layout);
        animatingView.add(email_layout);
        animatingView.add(password_layout);
        animatingView.add(gender_birthdate_layout);
        animatingView.add(country_of_residence_textView);
        animatingView.add(spinner);

    }

    private void initStartDelayArray(){

        setStartDelay = new ArrayList<>();

        setStartDelay.add(true);
        setStartDelay.add(true);
        setStartDelay.add(true);
        setStartDelay.add(true);
        setStartDelay.add(true);
        setStartDelay.add(false);
        setStartDelay.add(false);

    }

    private void initStartDelay2Array(){

        setStartDelay2 = new ArrayList<>();

        setStartDelay2.add(false);
        setStartDelay2.add(false);
        setStartDelay2.add(false);
        setStartDelay2.add(false);
        setStartDelay2.add(false);
        setStartDelay2.add(true);
        setStartDelay2.add(true);

    }

    private void initOrigPosArray(){

        orig_pos = new ArrayList<>();

        orig_pos.add(firstName_layout_org_pos_x);
        orig_pos.add(lastName_layout_org_pos_x);
        orig_pos.add(email_layout_org_pos_x);
        orig_pos.add(password_layout_org_pos_x);
        orig_pos.add(gender_birthdate_layout_org_pos_x);
        orig_pos.add(country_of_residence_textView_org_pos_x);
        orig_pos.add(spinner_org_pos_x);

    }

    private void setDummyDropDownList(){

        ArrayAdapter citySequence = ArrayAdapter.createFromResource(this, R.array.city_names, android.R.layout.simple_spinner_item);

        citySequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    private void initUI(){

        fab.hide();

        error_message.setVisibility(View.INVISIBLE);

        // link xml layout elements to java variables

    }

    private void initMiscTasks(){

        fbAuth = FirebaseAuth.getInstance();

        makeCertainEditTextsUnClickable();

        isSelected = false;

        StartupMethods.setInputFilterNoMiscUTFCharacters(allEditTexts);

        // disable user from entering emojis among other alien symbols into all editTexts

        prefs = this.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

    }

    private void makeCertainEditTextsUnClickable(){

        spinner.setFocusable(false);

        birthdate_edittext.setFocusable(false);

        // make spinner (= EditText) editText object unselectable

    }

    private void initAnims(){

        enter_anim_progress_layout = AnimationUtils.loadAnimation(RegisterActivityScreen2.this, R.anim.visibility_fade_in_animation);
        exit_anim_progress_layout = AnimationUtils.loadAnimation(RegisterActivityScreen2.this, R.anim.visibility_fade_out_animation);

        // create objectAnimator objects

        enter_anim_progress_layout.setDuration(200);
        exit_anim_progress_layout.setDuration(200);

        // set animation durations

        // load animations that need to be animated

    }

    private void setCalendarOnPopUpStartingDate(){

        final Calendar calendar = Calendar.getInstance();
        year = 1999;
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // set birthdate selection dialog standard date to current month and current day of 1999

    }

    private void enableLayoutInteraction(){

        fab.setClickable(true);
        firstName_edittext.setEnabled(true);
        lastName_edittext.setEnabled(true);
        email_edittext.setEnabled(true);
        password_edittext.setEnabled(true);
        spinner.setEnabled(true);
        birthdate_edittext.setEnabled(true);

        // enable user to select all view elements in layout

    }

    private void setNoBirthDateSelectedStandardText(){

        if (birthdate_edittext.getText().toString().equals("")){

            birthdate_edittext.setText(getResources().getString(R.string.birthdate));

        }

        // set birthdate editText text to 'Select Birthdate' if no birthdate was found in sharedPreferences

    }

    private void retrieveCountryCodeAndCity(Geocoder geocoder, Place place) throws IOException {

        List<Address> addresses = geocoder.getFromLocation(
                Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);

        city = addresses.get(0).getLocality();

        countryCode = addresses.get(0).getCountryCode();

        // get city name of selected address

    }

    private void unSelectedCityVillage(){

        city = null;

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(getApplicationContext(), 3000, error_message, getResources().getString(R.string.no_place_selected), tp);

        spinner.setText(getResources().getString(R.string.place_of_residence));

        fab.hide();

        // if city name could not be retrieved, set place of residence editText text back to 'Select city/village'

    }

    private void displayCityVillage(){

        spinner.setText(city);
        fab.show();

        // insert city name into city editText and show fab

    }

    private void unSelectCityVillage2(){

        city = null;

        spinner.setText(getResources().getString(R.string.place_of_residence));

        // set city editText back to standard (unselected) text

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(getApplicationContext(), 3000, error_message, getResources().getString(R.string.no_place_selected), tp);

        // inform user that an error occurred whilst trying to select a place by showing an error message

        fab.hide();

    }

}
