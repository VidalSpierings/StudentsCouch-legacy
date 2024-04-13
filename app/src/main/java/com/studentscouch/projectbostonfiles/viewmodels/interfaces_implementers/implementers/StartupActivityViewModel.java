package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsCountryCodes;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.models.implementers.StartupActivityModel;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;
import com.studentscouch.projectbostonfiles.ui.AccountAndApartementActivity;
import com.studentscouch.projectbostonfiles.ui.CityActivity;
import com.studentscouch.projectbostonfiles.ui.ProfileOnlyActivity;
import com.studentscouch.projectbostonfiles.ui.SettingsActivity;
import com.studentscouch.projectbostonfiles.view.views.StartupActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.StartupActivityViewModelInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class StartupActivityViewModel implements StartupActivityViewModelInterface {

    private int PLACE_PICKER_REQUEST = 273;

    private int ERROR_DIALOG_REQUEST = 8978;

    private String
            encoded,
            encoded2;

    private Bitmap[] bitmap2Array = {null};

    private String
            countryCode;

    private FetchPhotoRequest
            photoRequest,
            photoRequest2;

    private PlacesClient placesClient;

    private Place place;

    private Geocoder geocoder;

    private StartupActivityObservables observables;

    private StartupActivityModel model;

    private StartupActivityView view;

    private Intent i;

    private Intent intent;

    private Bitmap bitmap = null;

    private byte[]
            byteArray,
            byteArray2;

    public StartupActivityViewModel(StartupActivityView view, StartupActivityObservables observables){

        this.observables = observables;

        model = new StartupActivityModel();

        this.view = view;

    }

    @Override
    public void openAccountActivity(Context context, AppCompatActivity appCompatActivity, View account_layout_view, MotionEvent event, View settings_layout_views, String encoded, String encoded2) {

        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (StartupMethods.isOnline(context)) {

                registerButtonClickVisually(
                        context, settings_layout_views,
                        account_layout_view, event);

                startActivity(context, appCompatActivity);

            }

            if (!StartupMethods.isOnline(context)) {

                StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

                // if internet connection wasn't found, inform user by showing a dialog

            }
        }


    }

    private void registerButtonClickVisually(Context context, View settings_layout_views, View account_layout_view, MotionEvent event){

        List<View> unSelectedView = new ArrayList<>();
        unSelectedView.add(settings_layout_views);

        StartupMethods.startCircularRevealAnimationCustomStartRadius(context, account_layout_view, event, false);

        // visually register button click

    }

    private void startActivity(Context context, AppCompatActivity appCompatActivity){

        initIntentAccountActivity(appCompatActivity);

        context.startActivity(i);

    }

    @Override
    public void openSettingsActivity(Context context, AppCompatActivity appCompatActivity, MotionEvent motionEvent, View account_layout_view, View settings_layout_views) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

            StartupMethods.startCircularRevealAnimationCustomStartRadius(context, settings_layout_views, motionEvent, false);

            // visually register button click

            startActivity2(appCompatActivity);

        }

    }

    private void startActivity2(AppCompatActivity appCompatActivity){

        initIntentSettingsActivity(appCompatActivity);

        appCompatActivity.startActivity(i);

        // start SettingsActivity and send apartment information over to the Activity

    }

    @Override
    public void openPlacePickerDialog(Context context, AppCompatActivity appCompatActivity) {

        //FacebookSdk.sdkInitialize(context);

        if (StartupMethods.isOnline(context)) {

            attemptRequestPermissions(context, appCompatActivity);

            attemptOpenPingPlacePicker(context, appCompatActivity);

        } else {

            StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

            // inform the user that no active internet connection was found with the help of a toast

        }

    }

    @Override
    public void startAccountApartmentActivity(
            Context context, AppCompatActivity appCompatActivity,
            Animation exit_anim_item_layout, ImageView imageView,
            String encoded, String encoded2) {

        view.startExitAnims();

        // start exit animations for item textView and item image background view

        launchNewActivityOnAnimationEnd(context, appCompatActivity, exit_anim_item_layout, imageView);

    }

    @Override
    public void startAccountApartmentActivity2(AppCompatActivity appCompatActivity) {

        initIntentSettingsActivity(appCompatActivity);

        appCompatActivity.startActivity(i);

    }

    @Override
    public void retrieveSelectedLocation(Context context, AppCompatActivity appCompatActivity, Intent data, int requestCode, int resultCode) {

        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            // Make sure the request was successful

            view.makeProgressLayoutAppear();

            initVariables(context, data);

            attemptRetrieveLocation(context);

            attemptFetchPlace(context, appCompatActivity);

        }

    }

    private void convertBitmapsToBase64Strings(){

        getBitmap();

        convertBitmap();

        convertByteArraysToStrings();

    }

    private void convertByteArraysToStrings(){

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        encoded2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);

        // convert byte array objects to (Base64 encoded) String objects

    }

    private void getBitmap(){

        bitmap = StartupMethods.getResizedBitmap(bitmap, StartupMethods.IMAGE_RESIZE_RATIO);
        bitmap2Array[0] = StartupMethods.getResizedBitmap(bitmap2Array[0], StartupMethods.IMAGE_RESIZE_RATIO);

    }

    private void convertBitmap(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();

        // create Byte Array Output Stream objects

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        bitmap2Array[0].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);

        byteArray = byteArrayOutputStream.toByteArray();
        byteArray2 = byteArrayOutputStream2.toByteArray();

        // create byte array objects

    }

    private void attemptFetchPlace(Context context, AppCompatActivity appCompatActivity){

        List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

        FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(observables.getPlaceID(), fields);

        attemptFetchPlaceFunc(context, appCompatActivity, placeRequest);

    }

    private void attemptFetchPlaceFunc(Context context, AppCompatActivity appCompatActivity, FetchPlaceRequest placeRequest){

        try {

            placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {

                attemptInitFetchPhotoRequests(context);

                attemptFetchPhoto(context, appCompatActivity);

            });

        } catch (Exception e) {

            // Do nothing

        }

        // above try/catch block prevents this method from activating itself in activities other than its own

    }

    private void attemptInitFetchPhotoRequests(Context context){

        try {

            getPhotoRequests();

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.could_not_retrieve_loc), Toast.LENGTH_SHORT).show();

        }

    }

    private void getPhotoRequests(){

        PhotoMetadata photoMetadata = Objects.requireNonNull(place.getPhotoMetadatas()).get(0);
        PhotoMetadata photoMetadata2 = place.getPhotoMetadatas().get(1);
        // Create a FetchPhotoRequest.

        photoRequest = FetchPhotoRequest.builder(photoMetadata).build();
        photoRequest2 = FetchPhotoRequest.builder(photoMetadata2).build();

        // Get the photo metadata.

    }

    private void attemptFetchPhoto(Context context, AppCompatActivity appCompatActivity){

        placesClient.fetchPhoto(photoRequest).addOnSuccessListener(fetchPhotoResponse -> {

            bitmap = fetchPhotoResponse.getBitmap();

            if (observables.getCity() != null) {

                initPlaceSelectionItem(context, appCompatActivity);

            }

            if (observables.getCity() == null) {

                view.informUserErrorSelectingPlace(context);

            }

        });
    }

    private void initPlaceSelectionItem(Context context, AppCompatActivity appCompatActivity){

        if (!countryCode.equals(ConstantsCountryCodes.NETHERLANDS)
                && !countryCode.equals(ConstantsCountryCodes.BELGIUM)){

            view.informUserPlaceInIllegalCountry(context);

        } else {

            //If selected location in NL or BE, do the following

            attemptFetchSecondPhoto(context, appCompatActivity);

        }

    }

    private void attemptFetchSecondPhoto(Context context, AppCompatActivity appCompatActivity){

        try {

            placesClient.fetchPhoto(photoRequest2).addOnSuccessListener(fetchPhotoResponse -> {

                retrieveAndInitBitmap(fetchPhotoResponse);

                initItemViewOnClickListener(context, appCompatActivity);

                convertBitmapsToBase64Strings();

                model.safeCityImgsToSharedPrefs(appCompatActivity, encoded, encoded2);


            });

            // get image at index 1 for selected location from Google

        } catch (Exception e){

            // Do nothing

        }
    }

    private void retrieveAndInitBitmap(FetchPhotoResponse fetchPhotoResponse){

        bitmap2Array[0] = fetchPhotoResponse.getBitmap();

        bitmap2Array[0] = StartupMethods.getResizedBitmap(bitmap2Array[0], StartupMethods.IMAGE_RESIZE_RATIO);

    }

    private void initItemViewOnClickListener (Context context, AppCompatActivity appCompatActivity) {

        //handle the new bitmap here

        if (bitmap2Array[0] == null) {

            Toast.makeText(context, "layout frozen", Toast.LENGTH_LONG).show();

            view.freezeLayoutInteraction();

        }

        if (bitmap2Array[0] != null){

            view.getTextView().setText(observables.getCity());

            view.prepareLaunchAccountApartmentActivity(context, appCompatActivity, bitmap, bitmap2Array[0]);
        }
    }

    private void attemptRetrieveLocation(Context context){

        try {

            getMetaDataSelectedLocation();

        }catch (IOException e) {

            view.informUserSomethingWentWrong(context, e);

        } catch (IndexOutOfBoundsException e){

            observables.setCity(null);

            view.informUserSelecLocOutOfBounds(context);

        } catch (Exception e) {

            informUserUnknownErrorOccurred(context, e);

        }

    }

    private void initVariables(Context context, Intent data){

        place = PingPlacePicker.getPlace(data);

        observables.setPlaceID(Objects.requireNonNull(place).getId());

        placesClient = Places.createClient(context);

        geocoder = new Geocoder(context);

        // create addresses List array object

    }

    private void informUserUnknownErrorOccurred(Context context, Exception e){

        Toast.makeText(context, context.getResources().getString(R.string.unknown_error_occurred), Toast.LENGTH_LONG).show();

        e.printStackTrace();

    }

    private void getMetaDataSelectedLocation() throws IOException {

        List<Address> addresses = geocoder.getFromLocation(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);

        // get coordinates of selected location

        observables.setCity(addresses.get(0).getLocality());
        observables.setCountry(addresses.get(0).getCountryName());
        countryCode = addresses.get(0).getCountryCode();

        // retrieve city name, country name and country code from addresses object

    }

    private void launchNewActivityOnAnimationEnd(
            Context context, AppCompatActivity appCompatActivity,
            Animation exit_anim_item_layout, ImageView imageView){

        exit_anim_item_layout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                launchCityActivity(context, appCompatActivity, imageView);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void launchCityActivity(Context context, AppCompatActivity appCompatActivity, ImageView imageView){

        initCityActivityIntent(context);

        ActivityOptionsCompat options;

        options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, imageView, "item_imageView_shared");

        appCompatActivity.startActivity(intent, options.toBundle());

        // when animation has ended, start CityActivity

    }

    private void initCityActivityIntent(Context context){

        intent = new Intent(context, CityActivity.class);

        intent.putExtra("country", observables.getCountry());
        intent.putExtra("cityName", observables.getCity());
        intent.putExtra("placeID", observables.getPlaceID());

        // pass country name, city name, place ID to next activity

    }

    private void initIntentSettingsActivity(AppCompatActivity appCompatActivity){

        i = new Intent(appCompatActivity, SettingsActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // prevent standard activity transition from being animated

        i.putExtra("fromActivity", "0");

        i.putExtra("UID", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        i.putExtra("AID", observables.getAID());

        i.putExtra("locationID", observables.getLocationID());

        // pass User ID, Apartment ID, Place ID and what activity user came from to intent

    }

    private void initIntentAccountActivity(AppCompatActivity appCompatActivity){

        passDataAccToHostStatus(appCompatActivity);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // prevent standard activity transition animation from animating

        i.putExtra("fromActivity", "0");

        // pass what activity user is coming from to intent

    }

    private void passDataAccToHostStatus(AppCompatActivity appCompatActivity){

        if (observables.getHostStatus().equals(ConstantsDB.HOST_STATUS_USER_IS_HOST)) {

            passDataUserIsHost(appCompatActivity);

        } else {

            passDataUserIsNotHost(appCompatActivity);

        }

    }

    private void passDataUserIsNotHost(AppCompatActivity appCompatActivity){

        i = new Intent(appCompatActivity, ProfileOnlyActivity.class);
        i.putExtra("UID", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        //i.putExtra("AID", StartupActivity.AID);

        // if user is not a host, create Intent that will start ProfileOnlyActivity

    }

    private void passDataUserIsHost(AppCompatActivity appCompatActivity){

        i = new Intent(appCompatActivity, AccountAndApartementActivity.class);
        i.putExtra("UID", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        i.putExtra("AID", observables.getAID());

        // if user is a host, create Intent that will start AccountAndApartementActivity

    }

    private void attemptOpenPingPlacePicker(Context context, AppCompatActivity appCompatActivity){

        if (
                ActivityCompat.checkSelfPermission
                        (context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        MyApplication.isServicesOkLoggedInActivities(
                                appCompatActivity, context, ERROR_DIALOG_REQUEST))

        {

            openPingPlacePicker(context, appCompatActivity);

        }

    }

    private void attemptRequestPermissions(Context context, AppCompatActivity appCompatActivity){

        if (
                ActivityCompat.checkSelfPermission
                        (context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        MyApplication.isServicesOkLoggedInActivities(
                                appCompatActivity, context, ERROR_DIALOG_REQUEST)) {

            int LOCATION_PERMISSION_REQUEST_CODE = 3472;
            ActivityCompat.requestPermissions(
                    appCompatActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

            // request access location permissions

        }

    }

    private void openPingPlacePicker(Context context, AppCompatActivity appCompatActivity){

        try {

            openPlacePicker(context, appCompatActivity);

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.could_not_connect_gp), Toast.LENGTH_SHORT).show();

            e.printStackTrace();

            // if connection with Google Play Services could not be established, inform user by showing a toast

        }

    }

    private void openPlacePicker(Context context, AppCompatActivity appCompatActivity) throws Exception{

        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
        builder.setAndroidApiKey(context.getResources().getString(R.string.api_key_djjksdhoahberpow));
        Intent i = builder.build(appCompatActivity);
        appCompatActivity.startActivityForResult(i, PLACE_PICKER_REQUEST);

        // launch place picker intent with place picker request code

    }

}
