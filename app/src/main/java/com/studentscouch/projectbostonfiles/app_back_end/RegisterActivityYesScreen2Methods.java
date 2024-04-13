package com.studentscouch.projectbostonfiles.app_back_end;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.studentscouch.projectbostonfiles.Constants;
import com.studentscouch.projectbostonfiles.DeepCode;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsCountryCodes;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen3;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterActivityYesScreen2Methods {

    @SuppressLint("StaticFieldLeak")
    static Activity activity;
    private static Geocoder geocoder;
    private static Place place;
    private static List<Address> addresses;
    private static String
            cityVillage,
            countryName;

    private static Animation exit_anim;

    private static String
            previouslyEncodedImage,
            previouslyEncodedImage2,
            house_number,
            street;

    private static Bitmap
            bitmap,
            bitmap2,
            bitmap6,
            bitmap7,
            bitmap8;

    static Intent intent;

    private static int isFromActivity;

    private static List<Bitmap> bitmapImages;

    private static AlertDialog.Builder builder;

    private static SharedPreferences.Editor editor;

    RegisterActivityYesScreen2Methods(Activity activity){

        RegisterActivityYesScreen2Methods.activity = activity;

    }

    public static void selectStreet(
            final Context context,
            final FrameLayout progress_layout) {

        Animation enter_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        // create objectAnimator objects

        enter_anim.setDuration(200);

        // set duration for animations at 200 milliseconds

        StartupMethods.makeLoadingScreenVisible(enter_anim, null, progress_layout);

        // make loading screen visible

        //Place place = PlacePicker.getPlace(context, data);
        //Geocoder geocoder = new Geocoder(context);

        // retrieve place selected by user

    }

    public static void retrieveSelectedCityVillage(
            final Context context, final FrameLayout progress_layout, Intent data,
            final EditText firstName_editText, EditText lastName_editText, EditText userName_editText,
            final FloatingActionButton fab, final TextView error_textView, final Typeface tp) {

        makeLoadingScreenVisible(context, progress_layout);

        initGeoCoder(context, data);

        try {

            retrieveSelectedLocation(
                    context, firstName_editText, lastName_editText, userName_editText,
                    fab, error_textView, tp, progress_layout);

        } catch (IOException e) {
            e.printStackTrace();


        } catch (IndexOutOfBoundsException e) {

            showErrorMessageEmptyLocData(context, error_textView, firstName_editText, fab, tp);

        }

    }

    private static void showErrorMessageEmptyLocData(
            Context context, TextView error_textView,
            EditText firstName_editText, FloatingActionButton fab, Typeface tp){

        emptyOutLocationData();

        showErrorMessagePlaceOutOfBounds(context, error_textView, firstName_editText, tp);

        fab.hide();

        // if a place like 'europe' or 'the atlantic ocean' is selected,
        // inform user that something went wrong with selecting the place with the help of a toast

    }

    private static void showOptions(ImageView viewpager, ImageView viewpager2, ImageView viewpager3) {

        if (viewpager.getDrawable() != null  && viewpager2.getDrawable() == null && viewpager3.getDrawable() == null ){

            initDialogItemsImg1NotNullImg2NullImg3Null();

        }else if (viewpager.getDrawable() != null && viewpager2.getDrawable() != null && viewpager3.getDrawable() == null){

            initDialogItemsImg1NotNullImg2NotNullImg3Null();

        }else if (viewpager.getDrawable() != null && viewpager2.getDrawable() != null && viewpager3.getDrawable() != null){

            initDialogItemsAllImgsNotNull();

        } else if (viewpager.getDrawable() == null && viewpager2.getDrawable() == null && viewpager3.getDrawable() == null){

            initDialogItemsAllImgsNull();

        }else if (viewpager.getDrawable() != null && viewpager2.getDrawable() == null && viewpager3.getDrawable() != null){

            initDialogItemsImg1NotNullImg2NullImg3NotNull();
        }

    }

    public static void checkIfInfoEntered (
            Context context, EditText firstName_editText, EditText lastName_editText, EditText userName_editText,
            FloatingActionButton fab){

        if (
                !firstName_editText.getText().toString().equals("") &&
                        !firstName_editText.getText().toString().equals(context.getResources().getString(R.string.place_of_residence)) &&
                        !lastName_editText.getText().toString().equals("") &&
                        !lastName_editText.getText().toString().equals(context.getResources().getString(R.string.select_street)) &&
                        !userName_editText.getText().toString().equals("")

        )
        {

            // if info in all fields is entered, show fab

            fab.show();

        } else {

            // if info in at least one of the fields isn't entered in, do the following

            fab.hide();

        }

    }

    public static void retrieveApartmentInfo (
            SharedPreferences prefs, EditText lastName_editText, EditText userName_editText, ImageView viewpager2,
            ImageView viewpager3, FloatingActionButton fab) {

        if (!prefs.getString("savedUserDataShared_price_per_night", null).equals("")){

            try {

                attemptRetrieveApartmentInfoFromSharedPrefs(
                        prefs, lastName_editText, userName_editText,
                        viewpager2, viewpager3, fab);

            }catch (Exception e){

                // Do nothing

            }
        }

    }

    public static void setFirstNameEditTextOnClickListener(
            final Context context, EditText firstName_editText, final AppCompatActivity appCompatActivity,
            final int ERROR_DIALOG_REQUEST, final TextView error_textView, final Typeface tp, final int LOCATION_PERMISSION_REQUEST_CODE,
            final int PLACE_PICKER_REQUEST_CITY_VILLAGE

    ) {

        firstName_editText.setOnClickListener(view -> {

            ConnectivityManager connectivityManager = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                // if device is connected to the internet, do the following

                if ((StartupMethods.isServicesOk(context, appCompatActivity, ERROR_DIALOG_REQUEST, error_textView, tp)) &&
                        networkInfo != null && networkInfo.isConnected()){

                    // if connection with Google Play services can be established without failure, do the following

                    requestPermissionsOpenPlacePickerIntent(
                            context, appCompatActivity, LOCATION_PERMISSION_REQUEST_CODE,
                            PLACE_PICKER_REQUEST_CITY_VILLAGE);

                } else if (networkInfo == null || !networkInfo.isConnected()) {

                    showErrorMessageNoInternetConnection(context, error_textView, tp);

                }
        });

    }

    public static void initialiseFabOnClickListener(
            final Context context, final FloatingActionButton fab,
            final ImageView viewpager, final ImageView viewpager2, final ImageView viewpager3,
            final AppCompatActivity appCompatActivity, final Bundle extras,
            final int isFromActivity, final String countryName, final List<View> animatingViews,
            final FrameLayout add_image_layout, final EditText firstName_editText, final EditText lastName_editText,
            final EditText userName_editText, final List<View> fading_views, final List<Float> views_org_pos,
            final TextView error_textView, final Typeface tp
    ) {

        fab.setOnClickListener(view -> initialiseFabFunctionality(
                context, fab, viewpager,
                viewpager2, viewpager3, appCompatActivity, extras,
                isFromActivity, countryName, animatingViews, add_image_layout, firstName_editText,
                lastName_editText, userName_editText, fading_views, views_org_pos,
                error_textView, tp));

    }

    private static void initialiseFabFunctionality(
            final Context context, final FloatingActionButton fab,
            final ImageView viewpager, final ImageView viewpager2, final ImageView viewpager3,
            final AppCompatActivity appCompatActivity, final Bundle extras, int isFromActivity,
            final String countryName, final List<View> animatingViews,
            final FrameLayout add_image_layout, final EditText firstName_editText,
            final EditText lastName_editText, final EditText userName_editText,
            final List<View> fading_views, final List<Float> views_org_pos,
            final TextView error_textView, Typeface tp
    ) {

        attemptRetrieveAllImages(viewpager, viewpager2, viewpager3);

        startNextActivity(context, appCompatActivity, isFromActivity, animatingViews, views_org_pos, extras);

        showErrorMessageNoImagesPresent(context, error_textView, tp);

        disableLayoutInteraction(fab, add_image_layout, firstName_editText, lastName_editText, userName_editText);

        saveAllSubmittedData(context, firstName_editText, lastName_editText, userName_editText);

        initUI(fab);

        fadeOutAllViews(context, fading_views);

    }

    /*

    public static void openNewPlaceSelectionDialog(AppCompatActivity appCompatActivity){

        PlaceSelectionDialog placeSelectionDialog = new PlaceSelectionDialog();

        placeSelectionDialog.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    */

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseAddImageButton (
            final Context context, FrameLayout add_image_layout, final View add_image_view,
            final ImageView viewpager, final ImageView viewpager2, final ImageView viewpager3,
            final ImageView add_image_imageView, final int REQUEST_REPLACE_1_CAMERA,
            final int REQUEST_REPLACE_1_GALLERY,
            final int REQUEST_REPLACE_2_CAMERA, final int REQUEST_REPLACE_2_GALLERY,
            final int REQUEST_REPLACE_3_CAMERA, final int REQUEST_REPLACE_3_GALLERY,
            final int REQUEST_ADD_1_GALLERY, final int REQUEST_ADD_2_GALLERY,
            final int REQUEST_ADD_3_GALLERY, final int REQUEST_ADD_1_CAMERA,
            final int REQUEST_ADD_2_CAMERA, final int REQUEST_ADD_3_CAMERA,
            final int REQUEST_CAMERA, final int REQUEST_GALLERY,
            final AppCompatActivity appCompatActivity) {

        add_image_layout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP) {

               showDialog(
                       context, appCompatActivity, add_image_view, viewpager,
                       viewpager2, viewpager3, add_image_imageView,
                       REQUEST_REPLACE_1_CAMERA, REQUEST_REPLACE_1_GALLERY,
                       REQUEST_REPLACE_2_CAMERA, REQUEST_REPLACE_2_GALLERY,
                       REQUEST_REPLACE_3_CAMERA, REQUEST_REPLACE_3_GALLERY,
                       REQUEST_ADD_1_GALLERY, REQUEST_ADD_2_GALLERY,
                       REQUEST_ADD_3_GALLERY, REQUEST_ADD_1_CAMERA,
                       REQUEST_ADD_2_CAMERA, REQUEST_ADD_3_CAMERA,
                       REQUEST_CAMERA, REQUEST_GALLERY);

            }

            return true;
        });

    }

    private static void showDialog(
            Context context, AppCompatActivity appCompatActivity, View add_image_view,
            ImageView viewpager, ImageView viewpager2, ImageView viewpager3, ImageView add_image_imageView,
            int REQUEST_REPLACE_1_CAMERA,
            int REQUEST_REPLACE_1_GALLERY, int REQUEST_REPLACE_2_CAMERA,
            int REQUEST_REPLACE_2_GALLERY, int REQUEST_REPLACE_3_CAMERA,
            int REQUEST_REPLACE_3_GALLERY, int REQUEST_ADD_1_GALLERY,
            int REQUEST_ADD_2_GALLERY, int REQUEST_ADD_3_GALLERY, int REQUEST_ADD_1_CAMERA,
            int REQUEST_ADD_2_CAMERA, int REQUEST_ADD_3_CAMERA,
            int REQUEST_CAMERA, int REQUEST_GALLERY){

        add_image_view.setVisibility(View.VISIBLE);

        //StartupMethods.startCircularRevealAnimationDefaultStartRadius(context, add_image_view);

        // reveal add image button reveal view

        RegisterActivityYesScreen2Methods.showOptions(viewpager, viewpager2, viewpager3);

        // show item options according to number of retrieved images

        initDialog(
                context, appCompatActivity, add_image_imageView,
                viewpager, viewpager2, viewpager3, add_image_view,
                REQUEST_REPLACE_1_CAMERA, REQUEST_REPLACE_1_GALLERY, REQUEST_REPLACE_2_CAMERA,
                REQUEST_REPLACE_2_GALLERY, REQUEST_REPLACE_3_CAMERA, REQUEST_REPLACE_3_GALLERY,
                REQUEST_ADD_1_GALLERY, REQUEST_ADD_2_GALLERY, REQUEST_ADD_3_GALLERY,
                REQUEST_ADD_1_CAMERA, REQUEST_ADD_2_CAMERA, REQUEST_ADD_3_CAMERA,
                REQUEST_CAMERA, REQUEST_GALLERY);

        builder.show();

    }

    private static void makeLoadingScreenVisible(Context context, FrameLayout progress_layout){

        Animation enter_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        // create objectAnimator objects

        enter_anim.setDuration(200);

        // set duration for animations at 200 milliseconds

        // If user selected a city or village, do the following

        StartupMethods.makeLoadingScreenVisible(enter_anim, null, progress_layout);

    }

    private static void initGeoCoder(Context context, Intent data){

        place = PingPlacePicker.getPlace(data);
        geocoder = new Geocoder(context);

        // retrieve place selected by user

        place = PingPlacePicker.getPlace(data);
        RegisterActivityYesScreen2.placeID = Objects.requireNonNull(place).getId();
        geocoder = new Geocoder(context);

        // retrieve Place ID from place selected by user

    }

    private static void initAddressesArray() throws IOException {

        addresses = geocoder.getFromLocation(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);

    }

    private static void getInfoFromLocation(){

        RegisterActivityYesScreen2.countryCode = "NULL";

        RegisterActivityYesScreen2.stateCode = "NULL";

        try {

            cityVillage = addresses.get(0).getLocality();
            RegisterActivityYesScreen2.countryCode = addresses.get(0).getCountryCode();
            RegisterActivityYesScreen2.stateCode = addresses.get(0).getAdminArea();
            countryName = addresses.get(0).getCountryName();

        } catch (Exception e) {

            Log.d("STAT", "ERRORRRRR: ");

        }

    }

    private static void enableLayoutInteraction(EditText firstName_editText, EditText lastName_editText, EditText userName_editText){

        firstName_editText.setEnabled(true);
        lastName_editText.setEnabled(true);
        userName_editText.setEnabled(true);

        // enable editText fields to be interacted with

    }

    private static void showFabIfAllInfoEntered(
            Context context, EditText firstName_editText,
            EditText lastName_editText, EditText userName_editText,
            FloatingActionButton fab){

        if (
                !firstName_editText.getText().toString().equals("") &&
                        !firstName_editText.toString().equals(context.getResources().getString(R.string.place_of_residence)) &&
                        !lastName_editText.getText().toString().equals("") &&
                        !lastName_editText.getText().toString().equals(context.getResources().getString(R.string.select_street)) &&
                        !userName_editText.getText().toString().equals("")

        ) {

            // if information is entered into all fields, show fab

            fab.show();

        } else {

            // if information is not entered into at least one field, hide fab

            fab.hide();

        }

    }

    private static void initExitAnim(Context context){

        exit_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        exit_anim.setDuration(200);

        // load fade out animation and set duration animation at 200 milliseconds

    }

    private static void showErrorMessageHostingUnAvailInSelecCountry(Context context, TextView error_textView, Typeface tp){

        StartupMethods.showErrorMessage(context.getApplicationContext(), error_textView, context.getResources().getString(R.string.hosting_not_available), tp);

        // inform user that apartments can not be hosted in their chosen country by showing an error message

        error_textView.setVisibility(View.VISIBLE);

        // make error textView visible

    }

    private static void changeActivityFuncAccToSelecCountryFunc(
            Context context, EditText firstName_editText,
            TextView error_textView, Typeface tp, FloatingActionButton fab){

        try {

            if (!RegisterActivityYesScreen2.countryCode.equals(ConstantsCountryCodes.UNITED_STATES) ||
                    !RegisterActivityYesScreen2.stateCode.equals(ConstantsCountryCodes.NEW_JERSEY)) {

                firstName_editText.setText(context.getResources().getString(R.string.place_of_residence));

                // set standard unselected text in city/village editText

                showErrorMessageHostingUnAvailInSelecCountry(context, error_textView, tp);

                fab.hide();

            } else {

                error_textView.setVisibility(View.GONE);

                // make error textView disappear

            }

        } catch (Exception e) {

            firstName_editText.setText(context.getResources().getString(R.string.place_of_residence));

            // set standard unselected text in city/village editText

            showErrorMessageHostingUnAvailInSelecCountry(context, error_textView, tp);

            fab.hide();

        }

    }

   private static void changeActivityFuncAccToSelecCountry(
           Context context, EditText firstName_editText, TextView error_textView,
           Typeface tp, FloatingActionButton fab, FrameLayout progress_layout){

        DatabaseReference fbRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.UNLOCKED_COUNTRIES_URL_REFERENCE);

        // DB link to countries where StudentsCouch apartments can be hosted

       fbRef1.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                Log.d("CODE", "sabdjhbdfsehjb: ");

                changeActivityFuncAccToSelecCountryFunc(
                        context, firstName_editText, error_textView, tp, fab);

                initExitAnim(context);

                MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private static void retrieveSelectedLocation(
            Context context, EditText firstName_editText, EditText lastName_editText,
            EditText userName_editText, FloatingActionButton fab, TextView error_textView,
            Typeface tp, FrameLayout progress_layout) throws IOException{

        initAddressesArray();

        getInfoFromLocation();

        firstName_editText.setText(cityVillage);

        enableLayoutInteraction(firstName_editText, lastName_editText, userName_editText);

        showFabIfAllInfoEntered(context, firstName_editText, lastName_editText, userName_editText, fab);

        changeActivityFuncAccToSelecCountry(context, firstName_editText, error_textView, tp, fab, progress_layout);

    }

    private static void emptyOutLocationData(){

        RegisterActivityYesScreen2.countryCode = "NULL";

        RegisterActivityYesScreen2.stateCode = "NULL";

        RegisterActivityYesScreen2Methods.countryName = null;

        RegisterActivityYesScreen2Methods.cityVillage = null;

        // empty country code, country name and city/village Strings

    }

    private static void showErrorMessagePlaceOutOfBounds(Context context, TextView error_textView, EditText firstName_editText, Typeface tp){

        error_textView.setVisibility(View.VISIBLE);

        // make error textView visible

        StartupMethods.showErrorMessageTemporarily(
                context.getApplicationContext(), 3000,
                error_textView, context.getResources().getString(R.string.no_place_selected), tp);

        // if there was an error whilst trying to select a place, inform user by showing an error message

        firstName_editText.setText(context.getResources().getString(R.string.place_of_residence));

        // set standard unselected text for select city/village editText

    }

    private static void initDialogItemsImg1NotNullImg2NullImg3Null(){

        // if only one image is found, show the following options

        RegisterActivityYesScreen2.items = new CharSequence[]{
                "Add image 2 (Camera)", "Add image 2 (Gallery)",
                "Replace image 1 (Camera)", "Replace image 1 (Gallery)", "Cancel"};

    }
    private static void initDialogItemsImg1NotNullImg2NotNullImg3Null(){

        // if two images are found, do the following options

        RegisterActivityYesScreen2.items = new CharSequence[]{
                "Replace image 1 (Camera)", "Replace image 1 (Gallery)",
                "Replace image 2 (Camera)", "Replace image 2 (Gallery)",
                "Add image 3 (Camera)", "Add image 3 (Gallery)",
                "Delete image 1",  "Delete image 2",
                "Cancel"};

    }

    private static void initDialogItemsAllImgsNotNull(){

        // if three images were found, show the following options

        RegisterActivityYesScreen2.items = new CharSequence[]{
                "Replace image 1 (Camera)", "Replace image 1 (Gallery)",
                "Replace image 2 (Camera)", "Replace image 2 (Gallery)",
                "Replace image 3 (Camera)", "Replace image 3 (Gallery)",
                "Delete image 1",  "Delete image 2", "Delete image 3",
                "Cancel"};

    }

    private static void initDialogItemsAllImgsNull(){

        // if no images were found, show the following options

        RegisterActivityYesScreen2.items = new CharSequence[]{"Camera", "Gallery", "Cancel"};

    }

    private static void initDialogItemsImg1NotNullImg2NullImg3NotNull(){

        // if image 1 and 3 were found but not image 2, show the following options

        RegisterActivityYesScreen2.items = new CharSequence[]{
                "Replace image 1 (Camera)", "Replace image 1 (Gallery)",
                "Replace image 2 (Camera)", "Replace image 2 (Gallery)",
                "Replace image 3 (Camera)", "Replace image 3 (Gallery)",
                "Delete image 1",  "Delete image 2", "Delete image 3",
                "Cancel"};

    }

    private static void retrieveApartmentDataFromSharedPreferences(SharedPreferences prefs){

        previouslyEncodedImage = prefs.getString("apartement2", null);
        previouslyEncodedImage2 = prefs.getString("apartement3", null);

        house_number = prefs.getString("savedUserDataShared_price_per_night", "");
        street = prefs.getString("savedUserDataSharedStreet", "");

        // retrieve apartment house number and street name

    }

    private static void convertBase64StringImagesToBitmaps(){

        bitmap = StartupMethods.StringToBitMap(previouslyEncodedImage);
        bitmap2 = StartupMethods.StringToBitMap(previouslyEncodedImage2);

        // convert (Base64 encoded) String of second apartment image to Bitmap object

    }

    private static void showRetrievedInfoInUI(EditText lastName_editText, EditText userName_editText, ImageView viewpager2, ImageView viewpager3){

        lastName_editText.setText(street);
        userName_editText.setText(house_number);

        // insert apartment house number and street name into according textViews

        viewpager2.setImageBitmap(bitmap);
        viewpager3.setImageBitmap(bitmap2);

        // insert second and third apartment images into according imageViews

    }

    private static void showFabIfAllInfoEnteredRetrieveApartmentInfo(SharedPreferences prefs, FloatingActionButton fab){

        if (!prefs.getString("savedUserDataSharedTitle", null).equals("") &&
                !prefs.getString("savedUserDataShared_price_per_night", "").equals("") &&
                !prefs.getString("savedUserDataSharedStreet", "").equals("")){

            fab.show();

            /*

             * if apartment location name (Amsterdam, Rotterdam, Den Haag, etc.),
             * house number and street name have been entered in, show fab

             */

        }

    }

    private static void attemptRetrieveApartmentInfoFromSharedPrefs(
            SharedPreferences prefs, EditText lastName_editText, EditText userName_editText,
            ImageView viewpager2, ImageView viewpager3, FloatingActionButton fab){

        try {

            retrieveApartmentDataFromSharedPreferences(prefs);

            convertBase64StringImagesToBitmaps();

            showRetrievedInfoInUI(lastName_editText, userName_editText, viewpager2, viewpager3);

            showFabIfAllInfoEnteredRetrieveApartmentInfo(prefs, fab);

        }catch (Exception e){

            // Do nothing

        }

    }

    private static void startPlacePickerIntent(Context context, AppCompatActivity appCompatActivity, int PLACE_PICKER_REQUEST_CITY_VILLAGE) throws GooglePlayServicesNotAvailableException{

        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();

        builder.setAndroidApiKey(context.getResources().getString(R.string.api_key_djjksdhoahberpow));

        Intent i = builder.build(appCompatActivity);

        i.putExtra("countryName", countryName);

        i.putExtra("countryCode", RegisterActivityYesScreen2.countryCode);

        appCompatActivity.startActivityForResult(i, PLACE_PICKER_REQUEST_CITY_VILLAGE);

        // start google place selection dialog with select city/village request code

    }

    private static void attemptStartPlacePickerIntent(Context context, AppCompatActivity appCompatActivity, int PLACE_PICKER_REQUEST_CITY_VILLAGE){

        try {

            startPlacePickerIntent(context, appCompatActivity, PLACE_PICKER_REQUEST_CITY_VILLAGE);

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            // inform user that app could not connect to google play services

        }

    }

    private static void requestPermissionsOpenPlacePickerIntent(Context context, AppCompatActivity appCompatActivity, int LOCATION_PERMISSION_REQUEST_CODE, int PLACE_PICKER_REQUEST_CITY_VILLAGE){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

            // request access location permissions

        } else {

            attemptStartPlacePickerIntent(context, appCompatActivity, PLACE_PICKER_REQUEST_CITY_VILLAGE);

        }

    }

    private static void showErrorMessageNoInternetConnection(Context context, TextView error_textView, Typeface tp){

        error_textView.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(
                context, 3000, error_textView, context.getResources().getString(R.string.no_internet_connection), tp);

        // if internet connection was not found, inform user by showing a toast

    }

    private static void attemptRetrieveAllImages(ImageView viewpager, ImageView viewpager2, ImageView viewpager3){

        attemptRetrieveFirstImage(viewpager);

        attemptRetrieveSecondImage(viewpager2);

        attemptRetrieveThirdImage(viewpager3);

    }

    private static void attemptRetrieveFirstImage(ImageView viewpager){

        try {

            bitmap6 = ((BitmapDrawable) viewpager.getDrawable()).getBitmap();

            // attempt to retrieve first apartment image

        }catch(Exception e){

            bitmap6 = null;

            // if first apartment image could not be retrieved, make bitmap object empty

        }

    }

    private static void attemptRetrieveSecondImage(ImageView viewpager2){

        try {

            bitmap7 = ((BitmapDrawable) viewpager2.getDrawable()).getBitmap();

            // attempt to retrieve second apartment image

        } catch (Exception e) {

            bitmap7 = null;

            // if second apartment image could not be retrieved, make bitmap object empty

        }

    }

    private static void attemptRetrieveThirdImage(ImageView viewpager3){

        try {

            bitmap8 = ((BitmapDrawable) viewpager3.getDrawable()).getBitmap();

            // attempt to retrieve third apartment image

        } catch (Exception e) {

            bitmap8 = null;

            // if third apartment image could not be retrieved, make bitmap object empty

        }

    }
    private static void startNextActivity(Context context, AppCompatActivity appCompatActivity, int isFromActivity, List<View> animatingViews, List<Float> views_org_pos, Bundle extras){

        retrieveInfoFromPreviousActivity(extras);

        initIntent(appCompatActivity);

        StartupMethods.exitAnimationMultipleAnimators(
                context, intent, animatingViews, views_org_pos,
                StartupMethods.X_AXIS);

    }

    private static void initIntent(AppCompatActivity appCompatActivity){

        intent = new Intent(appCompatActivity, RegisterActivityYesScreen3.class);

        intent.putExtra("username", appCompatActivity.getIntent().getStringExtra("username"));

        intent.putExtra("isFromProfileActivity", isFromActivity);

        // if user came from activity 1, pass this data into next intent

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        intent.putExtra("countryCode", RegisterActivityYesScreen2.countryCode);
        intent.putExtra("countryName", countryName);

        // pass country code and country name to next activity

    }

    private static void retrieveInfoFromPreviousActivity(Bundle extras){

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 5);

            // retrieve info on what activity user came from. set useless integer as default value

        }

    }

    private static void showErrorMessageNoImagesPresentFunc(Context context, TextView error_textView, Typeface tp){

        error_textView.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(context, 3000, error_textView , context.getResources().getString(R.string.one_picture), tp);

        // inform user that they have to add at least one picture in order to continue by (temporarily) showing an error message

    }

    private static void showErrorMessageNoImagesPresent(Context context, TextView error_textView, Typeface tp){

        if (bitmap6 == null && bitmap7 == null && bitmap8 == null){

            // if no images were found, do the following

            showErrorMessageNoImagesPresentFunc(context, error_textView, tp);

        }

        // if only first apartment image was submitted, do the following

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void disableLayoutInteraction(FloatingActionButton fab, FrameLayout add_image_layout, EditText firstName_editText, EditText lastName_editText, EditText userName_editText){

        fab.setClickable(false);

        add_image_layout.setOnTouchListener(null);

        firstName_editText.setEnabled(false);
        lastName_editText.setEnabled(false);
        userName_editText.setEnabled(false);

        // disable all editTexts from being interacted with

    }

    private static void downgradeImageQualityFirstImage(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap6.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

    }

    private static void initBitmapImagesArray(){

        bitmapImages = new ArrayList<>();

        bitmapImages.add(bitmap6);
        bitmapImages.add(bitmap7);
        bitmapImages.add(bitmap8);

    }

    private static void saveAllSubmittedData(Context context, EditText firstName_editText, EditText lastName_editText, EditText userName_editText){

        downgradeImageQualityFirstImage();

        initBitmapImagesArray();

        SharedPreferences sharedPreferences = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        saveAllRetrievedApartmentImages();

        saveAllOtherInfo(firstName_editText, lastName_editText, userName_editText);

        editor.apply();

    }

    private static void saveAllRetrievedApartmentImages(){

        for (int pos = 0; pos < bitmapImages.size(); pos++){

            if (bitmapImages.get(pos) != null) {

                String base64String = DeepCode.BitmapToString(bitmapImages.get(pos));

                editor.putString("apartment" + (pos + 1), base64String);

            }

        }

        // insert retrieved apartment images into sharedPreferences

    }

    private static void saveAllOtherInfo(EditText firstName_editText, EditText lastName_editText, EditText userName_editText){

        editor.putString("savedUserDataShared_city_or_village", firstName_editText.getText().toString());
        editor.putString("savedUserDataSharedStreet", lastName_editText.getText().toString());
        editor.putString("savedUserDataShared_price_per_night", userName_editText.getText().toString());
        editor.putString("savedUserDataPlaceID", RegisterActivityYesScreen2.placeID);
        editor.putString("savedUserDataSharedCountryCode", RegisterActivityYesScreen2.countryCode);

        // insert apartment title, street name, price per night and placeID into sharedPreferences

    }

    private static void fadeOutAllViews(Context context, List<View> fading_views){

        Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        // load fade out animation

        for (int pos = 0; pos < fading_views.size(); pos++) {

            fading_views.get(pos).startAnimation(fade_out);

        }

        // make fading views fade out of layout

    }

    private static void initUI(FloatingActionButton fab){

        fab.hide();

    }

    private static void initDialogBuilder(Context context){

        builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog);

    }

    private static void setDialogTitle(Context context){

        builder.setTitle(context.getResources().getString(R.string.add_image));

    }

    private static void setDialogItems(
            Context context, AppCompatActivity appCompatActivity, ImageView add_image_imageView,
            ImageView viewpager, ImageView viewpager2, ImageView viewpager3, View add_image_view,
            int REQUEST_REPLACE_1_CAMERA, int REQUEST_REPLACE_1_GALLERY, int REQUEST_REPLACE_2_CAMERA,
            int REQUEST_REPLACE_2_GALLERY, int REQUEST_REPLACE_3_CAMERA, int REQUEST_REPLACE_3_GALLERY,
            int REQUEST_ADD_1_GALLERY, int REQUEST_ADD_2_GALLERY, int REQUEST_ADD_3_GALLERY,
            int REQUEST_ADD_1_CAMERA, int REQUEST_ADD_2_CAMERA, int REQUEST_ADD_3_CAMERA,
            int REQUEST_CAMERA, int REQUEST_GALLERY){

        builder.setItems(RegisterActivityYesScreen2.items, (dialogInterface, i) -> {

            initDeleteImagesItems(context, i, add_image_imageView, dialogInterface, viewpager, viewpager2, viewpager3);

            initReplaceGalleryImagesItems(
                    context, appCompatActivity, i, REQUEST_REPLACE_1_GALLERY,
                    REQUEST_REPLACE_2_GALLERY, REQUEST_REPLACE_3_GALLERY);

            initReplaceCameraImagesItems(context, appCompatActivity, i, REQUEST_REPLACE_1_CAMERA, REQUEST_REPLACE_2_CAMERA, REQUEST_REPLACE_3_CAMERA);

            initAddGalleryImagesItems(context, appCompatActivity, i, REQUEST_ADD_1_GALLERY, REQUEST_ADD_2_GALLERY, REQUEST_ADD_3_GALLERY);

            initAddCameraImagesItems(context, appCompatActivity, i, REQUEST_ADD_1_CAMERA, REQUEST_ADD_2_CAMERA, REQUEST_ADD_3_CAMERA);

            initGenericItems(context, appCompatActivity, i, REQUEST_CAMERA, REQUEST_GALLERY);

            initCancelItem(context, i, add_image_imageView, dialogInterface);

                        /*

                        switch (RegisterActivityYesScreen2.items[i].toString()) {

                            case ("Delete image 1"): {

                                RegisterActivitiesMethods.deleteImage(context, add_image_imageView, dialogInterface, viewpager);

                            } case ("Delete image 2"): {

                                RegisterActivitiesMethods.deleteImage(context, add_image_imageView, dialogInterface, viewpager2);

                            } case ("Delete image 3"): {

                                RegisterActivitiesMethods.deleteImage(context, add_image_imageView, dialogInterface, viewpager3);

                            } case ("Replace image 1 (Camera)"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_REPLACE_1_CAMERA);

                            } case ("Replace image 1 (Gallery)"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_REPLACE_1_GALLERY);

                            } case ("Replace image 2 (Camera)"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_REPLACE_2_CAMERA);

                            } case ("Replace image 2 (Gallery)"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_REPLACE_2_GALLERY);

                            } case ("Replace image 3 (Camera)"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_REPLACE_3_CAMERA);

                            } case ("Replace image 3 (Gallery)"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_REPLACE_3_GALLERY);

                            } case ("Add image 1 (Gallery)"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_ADD_1_GALLERY);

                            } case ("Add image 2 (Gallery)"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_ADD_2_GALLERY);

                            } case ("Add image 3 (Gallery)"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_ADD_3_GALLERY);

                            } case ("Add image 1(Camera)"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_ADD_1_CAMERA);

                            } case ("Add image 2 (Camera)"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_ADD_2_CAMERA);

                            } case ("Add image 3 (Camera)"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_ADD_3_CAMERA);

                            } case("Camera"): {

                                RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, RegisterActivityYesScreen2.mCapturedImageURI, REQUEST_CAMERA);

                            } case ("Gallery"): {

                                RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_GALLERY);

                            } case ("Cancel"): {

                                // AFTER SWITCHING TO STATIC VARIABLE (INSTEAD OF LOCALLY ENTERED PARAMETER VARIABLE) BUG APPEARS FOR HAVING TO CHANGE THEME!!!!!!!!!!!

                                add_image_view.setBackgroundColor(context.getResources().getColor(R.color.main_background));

                                dialogInterface.dismiss();

                            }

                        }

                        */

        });

    }

    private static void setDialogOnCancelListener(){

        builder.setOnCancelListener(dialogInterface -> {

            //add_image_view.setVisibility(View.INVISIBLE);

        });

    }

    private static void initDialog(Context context, AppCompatActivity appCompatActivity, ImageView add_image_imageView,
                                   ImageView viewpager, ImageView viewpager2, ImageView viewpager3, View add_image_view,
                                   int REQUEST_REPLACE_1_CAMERA, int REQUEST_REPLACE_1_GALLERY, int REQUEST_REPLACE_2_CAMERA,
                                   int REQUEST_REPLACE_2_GALLERY, int REQUEST_REPLACE_3_CAMERA, int REQUEST_REPLACE_3_GALLERY,
                                   int REQUEST_ADD_1_GALLERY, int REQUEST_ADD_2_GALLERY, int REQUEST_ADD_3_GALLERY,
                                   int REQUEST_ADD_1_CAMERA, int REQUEST_ADD_2_CAMERA, int REQUEST_ADD_3_CAMERA,
                                   int REQUEST_CAMERA, int REQUEST_GALLERY){

        initDialogBuilder(context);

        setDialogTitle(context);

        setDialogItems(
                context, appCompatActivity, add_image_imageView,
                viewpager, viewpager2, viewpager3, add_image_view,
                REQUEST_REPLACE_1_CAMERA, REQUEST_REPLACE_1_GALLERY, REQUEST_REPLACE_2_CAMERA,
                REQUEST_REPLACE_2_GALLERY, REQUEST_REPLACE_3_CAMERA, REQUEST_REPLACE_3_GALLERY,
                REQUEST_ADD_1_GALLERY, REQUEST_ADD_2_GALLERY, REQUEST_ADD_3_GALLERY,
                REQUEST_ADD_1_CAMERA, REQUEST_ADD_2_CAMERA, REQUEST_ADD_3_CAMERA,
                REQUEST_CAMERA, REQUEST_GALLERY);

        setDialogOnCancelListener();

    }

    private static void initDeleteImagesItems(Context context, int i, ImageView add_image_imageView, DialogInterface dialogInterface, ImageView viewpager, ImageView viewpager2, ImageView viewpager3){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Delete image 1")) {

            RegisterActivitiesMethods.deleteImage(context, add_image_imageView, dialogInterface, viewpager);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Delete image 2")) {

            RegisterActivitiesMethods.deleteImage(context, add_image_imageView, dialogInterface, viewpager2);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Delete image 3")) {

            RegisterActivitiesMethods.deleteImage(context, add_image_imageView, dialogInterface, viewpager3);

        }

    }

    private static void initReplaceGalleryImagesItems(Context context, AppCompatActivity appCompatActivity, int i, int REQUEST_REPLACE_1_GALLERY, int REQUEST_REPLACE_2_GALLERY, int REQUEST_REPLACE_3_GALLERY){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Replace image 1 (Gallery)")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_REPLACE_1_GALLERY);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Replace image 2 (Gallery)")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_REPLACE_2_GALLERY);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Replace image 3 (Gallery)")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_REPLACE_3_GALLERY);

        }

    }

    private static void initReplaceCameraImagesItems(Context context, AppCompatActivity appCompatActivity, int i, int REQUEST_REPLACE_1_CAMERA, int REQUEST_REPLACE_2_CAMERA, int REQUEST_REPLACE_3_CAMERA){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Replace image 1 (Camera)")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_REPLACE_1_CAMERA);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Replace image 2 (Camera)")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_REPLACE_2_CAMERA);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Replace image 3 (Camera)")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_REPLACE_3_CAMERA);

        }

    }

    private static void initAddGalleryImagesItems(Context context, AppCompatActivity appCompatActivity, int i, int REQUEST_ADD_1_GALLERY, int REQUEST_ADD_2_GALLERY, int REQUEST_ADD_3_GALLERY){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Add image 1 (Gallery)")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_ADD_1_GALLERY);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Add image 2 (Gallery)")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_ADD_2_GALLERY);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Add image 3 (Gallery)")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_ADD_3_GALLERY);

        }

    }

    private static void initAddCameraImagesItems(Context context, AppCompatActivity appCompatActivity, int i, int REQUEST_ADD_1_CAMERA, int REQUEST_ADD_2_CAMERA, int REQUEST_ADD_3_CAMERA){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Add image 1(Camera)")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_ADD_1_CAMERA);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Add image 2 (Camera)")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_ADD_2_CAMERA);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Add image 3 (Camera)")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_ADD_3_CAMERA);

        }

    }

    private static void initGenericItems(Context context, AppCompatActivity appCompatActivity, int i, int REQUEST_CAMERA, int REQUEST_GALLERY){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Camera")) {

            RegisterActivitiesMethods.getImageFromCamera(appCompatActivity, context, REQUEST_CAMERA);

        }

        if (RegisterActivityYesScreen2.items[i].toString().equals("Gallery")) {

            RegisterActivitiesMethods.getImageFromGallery(appCompatActivity, context, REQUEST_GALLERY);

        }

    }

    private static void initCancelItem(Context context, int i, ImageView add_image_view, DialogInterface dialogInterface){

        if (RegisterActivityYesScreen2.items[i].toString().equals("Cancel")) {

            add_image_view.setBackgroundColor(context.getResources().getColor(R.color.main_background));

            dialogInterface.dismiss();

        }

    }

}
