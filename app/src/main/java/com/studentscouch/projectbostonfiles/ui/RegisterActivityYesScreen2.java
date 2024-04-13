package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityYesScreen2Methods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivitiesMethods;
import com.studentscouch.projectbostonfiles.dialogs.PlaceSelectionDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterActivityYesScreen2 extends AppCompatActivity {

    private FloatingActionButton fab;

    private TextView
            subTitle_textView,
            error_textView,
            firstName_textView,
            lastName_textView,
            userName_textView;

    private View add_image_view;

    private EditText
            firstName_editText,
            lastName_editText,
            userName_editText;

    private ImageView
            add_image_imageView,
            viewpager,
            viewpager2,
            viewpager3;

    private List<View> viewsInLayout;

    private LinearLayout
        firstName_layout,
        lastName_layout,
        userName_layout;

    private FrameLayout
            add_image_layout,
            apartemet_images_framelayout;

    public static ObjectAnimator
            anim,
            anim2;

    private int
            REQUEST_REPLACE_1_CAMERA = 1000,
            REQUEST_REPLACE_1_GALLERY = 1002,
            REQUEST_REPLACE_2_CAMERA = 1003,
            REQUEST_REPLACE_2_GALLERY = 1004,
            REQUEST_REPLACE_3_CAMERA = 1005,
            REQUEST_REPLACE_3_GALLERY = 1006,
            REQUEST_ADD_1_CAMERA = 1007,
            REQUEST_ADD_1_GALLERY = 1008,
            REQUEST_ADD_2_CAMERA = 1009,
            REQUEST_ADD_2_GALLERY = 1010,
            REQUEST_ADD_3_CAMERA = 1011,
            REQUEST_ADD_3_GALLERY = 1012,
            REQUEST_GALLERY = 1013,
            REQUEST_CAMERA = 1014;

    private static final int ERROR_DIALOG_REQUEST = 8978;

    private static final int
            PLACE_PICKER_REQUEST_CITY_VILLAGE = 273,
            PLACE_PICKER_REQUEST_STREET = 4632;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 3472;

    public static CharSequence[] items;

    private Typeface tp;

    private Bitmap
        bitmap6,
        bitmap7,
        bitmap8;

    private String
            cityVillage = "",
            cityVillage2,
            countryName;

    public static String countryCode;
    public static String stateCode;

    String
            street2,
            original_city_name;

    public static int isFromActivity;

    private Bundle
            extras = null;

    private FrameLayout progress_layout;

    private DatabaseReference fbRef1 = null;

    public static String placeID;

    public static Uri mCapturedImageURI;

    private List<View> animatingViews;

    private List<Float> views_org_pos;

    private List<View> fading_views;

    private SharedPreferences sharedPreferences;

    private int PLACE_SELECTION_INFO_DIALOG_ID = 21763;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_yes_screen2);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        initArrays();

        initDB();

        addTextWatcherToAllEditTexts();

        initMiscTasks();

        setFirstApartmentImage();

        getDataFromPreviousActivity();

        initUI();

        StudentsCouchAnimations.onRestartAnimationsNoButtons(
                getApplicationContext(), animatingViews, views_org_pos,
                viewsInLayout, fading_views, 400);

        // start activity transition (restart and onStart anims are the same)

        RegisterActivityYesScreen2Methods.setFirstNameEditTextOnClickListener(
                getApplicationContext(), firstName_editText, this, ERROR_DIALOG_REQUEST,
                error_textView, tp, LOCATION_PERMISSION_REQUEST_CODE,
                PLACE_PICKER_REQUEST_CITY_VILLAGE
        );

        // initialise first name editText onClickListener

        RegisterActivityYesScreen2Methods.initialiseFabOnClickListener(
                getApplicationContext(), fab,
                viewpager, viewpager2, viewpager3,
                RegisterActivityYesScreen2.this, extras, isFromActivity,
                countryName,
                animatingViews, add_image_layout,
                firstName_editText, lastName_editText, userName_editText, fading_views,
                views_org_pos, error_textView, tp);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        enableLayoutInteraction();

        initUI2();

        getDataFromPreviousActivityOnStart();

        RegisterActivityYesScreen2Methods.checkIfInfoEntered(
                getApplicationContext(), firstName_editText, lastName_editText, userName_editText, fab);

        // check if info is entered in all editTexts

        RegisterActivityYesScreen2Methods.initialiseAddImageButton(
                RegisterActivityYesScreen2.this, add_image_layout, add_image_view,
                viewpager, viewpager2, viewpager3, add_image_imageView,
                REQUEST_REPLACE_1_CAMERA, REQUEST_REPLACE_1_GALLERY, REQUEST_REPLACE_2_CAMERA,
                REQUEST_REPLACE_2_GALLERY, REQUEST_REPLACE_3_CAMERA, REQUEST_REPLACE_3_GALLERY, REQUEST_ADD_1_GALLERY,
                REQUEST_ADD_2_GALLERY, REQUEST_ADD_3_GALLERY, REQUEST_ADD_1_CAMERA, REQUEST_ADD_2_CAMERA, REQUEST_ADD_3_CAMERA,
                REQUEST_CAMERA, REQUEST_GALLERY, RegisterActivityYesScreen2.this
        );

        RegisterActivityYesScreen2Methods.checkIfInfoEntered(
                getApplicationContext(), firstName_editText, lastName_editText, userName_editText, fab);

        // check if info is entered in all editTexts

    }

    @Override
    protected void onRestart() {

        super.onRestart();

        //add_image_layout.setVisibility(View.VISIBLE);

        initUI2();

        StudentsCouchAnimations.onRestartAnimationsNoButtons(
                getApplicationContext(), animatingViews, views_org_pos, viewsInLayout, fading_views, 400);

        // start onRestart Animations

    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            showFabWhenAllInfoEntered();

        }

};

    // init textWatcher

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(0, 0);

        initUI2();

        if (requestCode == PLACE_PICKER_REQUEST_CITY_VILLAGE && resultCode == RESULT_OK) {

            RegisterActivityYesScreen2Methods.retrieveSelectedCityVillage(
                    getApplicationContext(), progress_layout, data,
                    firstName_editText,
                    lastName_editText, userName_editText, fab, error_textView, tp);

        }


        if (requestCode == PLACE_PICKER_REQUEST_STREET && resultCode == RESULT_OK) {

            int timesChanged = 1;
            String street = "";
            RegisterActivityYesScreen2Methods.selectStreet(
                    getApplicationContext(),
                    progress_layout);

        }

        // register street selection to application

        retrieveImage(resultCode, requestCode, data);

    }

    private void linkXmlElementsToJavaVariables(){

        progress_layout = findViewById(R.id.progress_layout);

        lastName_textView = findViewById(R.id.lastName_textView);
        firstName_textView = findViewById(R.id.firstName_textView);
        userName_textView = findViewById(R.id.userName_textView);
        subTitle_textView = findViewById(R.id.subTitle_textView);
        error_textView = findViewById(R.id.error_message);

        add_image_view = findViewById(R.id.add_image_view);

        fab = findViewById(R.id.fab);

        firstName_editText = findViewById(R.id.firstName_edittext);
        lastName_editText = findViewById(R.id.lastName_edittext);
        userName_editText = findViewById(R.id.userName_editText);

        firstName_layout = findViewById(R.id.firstName_layout);
        lastName_layout = findViewById(R.id.lastName_layout);
        userName_layout = findViewById(R.id.userName_layout);

        add_image_layout = findViewById(R.id.add_image_layout);
        apartemet_images_framelayout = findViewById(R.id.apartement_images_frameLayout);

        add_image_imageView = findViewById(R.id.add_image_imageView);

        viewpager = findViewById(R.id.viewPager);
        viewpager2 = findViewById(R.id.viewPager2);
        viewpager3 = findViewById(R.id.viewPager3);

        // link java variables to xml layout views

    }

    private void setTypefaces(){

        tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");

        lastName_textView.setTypeface(tp);
        firstName_textView.setTypeface(tp);
        userName_textView.setTypeface(tp);
        subTitle_textView.setTypeface(tp);
        error_textView.setTypeface(tp);

        firstName_editText.setTypeface(tp);
        lastName_editText.setTypeface(tp);
        userName_editText.setTypeface(tp);

        // initialise and set typeface to all textViews in layout

    }

    private void initArrays(){

        initViewsInLayoutArray();

        initAnimatingViewsArray();

        initViewsOrgPosArray();

        initFadingViewsArray();

    }

    private void initViewsInLayoutArray(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(firstName_layout);
        viewsInLayout.add(lastName_layout);
        viewsInLayout.add(userName_layout);

        // add all view rows in base layout to ArrayList

    }

    private void initAnimatingViewsArray(){

        animatingViews = new ArrayList<>();

        animatingViews.add(firstName_layout);
        animatingViews.add(lastName_layout);
        animatingViews.add(userName_layout);

    }

    private void initViewsOrgPosArray(){

        views_org_pos = new ArrayList<>();

        views_org_pos.add(firstName_layout.getX());
        views_org_pos.add(lastName_layout.getX());
        views_org_pos.add(userName_layout.getX());

    }

    private void initFadingViewsArray(){

        fading_views = new ArrayList<>();

        fading_views.add(subTitle_textView);
        fading_views.add(apartemet_images_framelayout);
        fading_views.add(add_image_layout);

    }

    private void addTextWatcherToAllEditTexts(){

        firstName_editText.addTextChangedListener(watcher);
        lastName_editText.addTextChangedListener(watcher);
        userName_editText.addTextChangedListener(watcher);

        // add textChangedListener to all editText views in layout

    }

    private void setFirstApartmentImage(){

        String previouslyEncodedImage = sharedPreferences.getString("apartement1", "");

        // retrieve first apartment image from sharedPreferences in (Base64 encoded) String format

        if(!Objects.requireNonNull(previouslyEncodedImage).equalsIgnoreCase("") ){

            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            viewpager.setImageBitmap(bitmap);

            // set bitmap of first apartment photo if bitmap retrieved doesn't equal ""

        }

    }

    private void getDataFromPreviousActivity(){

        extras = getIntent().getExtras();

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 5);

            // retrieve info on what activity user came from. set useless integer as default value

        }

    }

    private void initUI(){

        firstName_editText.setText(getResources().getString(R.string.place_of_residence));

        // set standard unselected text for city/village selection EditText

        lastName_editText.setText("");

        // set empty street name in street name editText

        viewpager2.setImageDrawable(null);
        viewpager3.setImageDrawable(null);

        // set second and third apartment image imageViews empty

    }

    private void initMiscTasks(){

        sharedPreferences = getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        cityVillage = sharedPreferences.getString("savedUserDataShared_place_of_residence", "");

        // retrieve first city/village from sharedPreferences in String format

        /*

         * get originally entered in name of city/village
         * (selected city or village can be changed in this activity,
         * original_city_name variable saves selected city/village in the previous activity)

         */

        original_city_name = cityVillage;

        //fab.setVisibility(View.INVISIBLE);

        firstName_editText.setFocusable(false);

        lastName_editText.setFocusable(true);

        // remove ability to focus on EditText fields

        showPlaceSelectionInfoDialog();

    }

    private void showPlaceSelectionInfoDialog(){

        PlaceSelectionDialog placeSelectionDialog = new PlaceSelectionDialog(this);

        placeSelectionDialog.show();

    }

    private void initDB(){

        Firebase.setAndroidContext(this);

    }

    private void enableLayoutInteraction(){

        fab.setClickable(true);

        firstName_editText.setEnabled(true);
        lastName_editText.setEnabled(true);
        userName_editText.setEnabled(true);

        // disable editTexts from being interacted with

    }

    private void getDataFromPreviousActivityOnStart(){

        SharedPreferences prefs = this.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        if (!Objects.requireNonNull(prefs.getString("savedUserDataShared_price_per_night", "")).equals("")) {

            RegisterActivityYesScreen2Methods.retrieveApartmentInfo(
                    prefs, lastName_editText, userName_editText, viewpager2,
                    viewpager3, fab);

        }

    }

    private void initUI2(){

        add_image_view.setBackgroundColor(ContextCompat.getColor(RegisterActivityYesScreen2.this, R.color.main_background));

    }

    private void showFabWhenAllInfoEntered(){

        if (
                !firstName_editText.getText().toString().equals("") &&
                        !firstName_editText.getText().toString().equals(getResources().getString(R.string.place_of_residence)) &&
                        !lastName_editText.getText().toString().equals("") &&
                        !lastName_editText.getText().toString().equals(getResources().getString(R.string.select_street)) &&
                        !userName_editText.getText().toString().equals("")

        ) {

            // if information is entered into all fields, show fab

            fab.show();

        }

    }

    private void retrieveImage(int resultCode, int requestCode, Intent data){

        if (resultCode == RESULT_OK && requestCode == REQUEST_REPLACE_1_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager, RegisterActivityYesScreen2.this,
                    1);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_REPLACE_1_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager, false, 1);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_REPLACE_2_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager2, false, 2);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_REPLACE_2_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager2, RegisterActivityYesScreen2.this,
                    2);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_REPLACE_3_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager3, RegisterActivityYesScreen2.this,
                    3);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_REPLACE_3_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager3, false, 3);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_1_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager, RegisterActivityYesScreen2.this,
                    1);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_1_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager, false, 1);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_2_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager2, RegisterActivityYesScreen2.this,
                    2);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_2_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager2, false, 2);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_3_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager3, RegisterActivityYesScreen2.this,
                    3);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_3_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager3, false, 3);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, viewpager, false, 1);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, viewpager, RegisterActivityYesScreen2.this,
                    1);

        }

        // insert retrieved image into according imageView, save image in sharedPreferences. refer to RegisterActivitiesMethods for more info

    }

        }


