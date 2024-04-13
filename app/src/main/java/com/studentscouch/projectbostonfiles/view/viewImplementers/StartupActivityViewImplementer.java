package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.studentscouch.projectbostonfiles.Constants;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.dbImplementers.StartupActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.StartupActivityDBInterface;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogWIthThreeButtons;
import com.studentscouch.projectbostonfiles.miscellaneous_files.NotificationIntentService;
import com.studentscouch.projectbostonfiles.miscellaneous_files.RestarterBroadCastReceiver;
import com.studentscouch.projectbostonfiles.models.implementers.StartupActivityModel;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.StartupActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.StartupActivityViewModel;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StartupActivityViewImplementer implements StartupActivityView {

    private FrameLayout
            account_layout,
            settings_layout;

    private FloatingActionButton fab;

    private ImageView logo_imageView;

    private View
            account_layout_view,
            settings_layout_views;

    private Animation
            enter_anim_progress_layout,
            exit_anim_progress_layout;

    private RelativeLayout progress_layout;

    private FrameLayout item_layout;

    private ImageView item_imageView;

    private TextView item_textView;

    private Bitmap bitmap;

    /*

    protected GeoDataClient
            mGeoDataClient,
            mGeoDataClient2;

    */

    //vars

    private Animation exit_anim_item_layout;

    private View imageView_background_view;

    private TextView select_city_village_textView;

    private String
            encoded,
            encoded2;

    private StartupActivityViewModel viewModel;

    private StartupActivityModel model;

    private StartupActivityDBInterface db;

    private StartupActivityObservables observables;

    private View rootView;

    public StartupActivityViewImplementer(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_startup, viewGroup);

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        linkJavaVariablesToXmlViews();

        setTypefaces(context);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        initActivity();

        initAnims(context);

        startExitAnims();

        item_textView.setVisibility(View.VISIBLE);

        // make city name textView visible

        db.initDB(context, appCompatActivity);

        startListeningForNotifications(context, appCompatActivity);

        settings_layout.setOnTouchListener((view, motionEvent) -> {

            viewModel.openSettingsActivity(context, appCompatActivity, motionEvent, account_layout_view, settings_layout_views);

            return true;

        });

        account_layout.setOnTouchListener((view, motionEvent) -> {

            viewModel.openAccountActivity(
                    context, appCompatActivity, account_layout_view,
                    motionEvent, settings_layout_views, encoded, encoded2);

            return true;
        });

        fab.setOnClickListener(view -> viewModel.openPlacePickerDialog(context, appCompatActivity));

        // initialise floatingActionButton

        model.informAppUserHasLoggedIn(context);

        createDummyListItems();

    }

    private void startListeningForNotifications(Context context, AppCompatActivity appCompatActivity){

        Intent serviceIntent = new Intent(context, NotificationIntentService.class);

        if (!isMyServiceRunning(appCompatActivity, NotificationIntentService.class)) {

            serviceIntent.putExtra("dbPath", StartupMethods.DATABASE_LINK
                    + ConstantsDB.USERS_TABLE_URL_REFERENCE
                    + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

            appCompatActivity.startService(serviceIntent);

            //https://firebase.google.com/docs/functions/use-cases#notify_users_when_something_interesting_happens

            // setup how-to: set service aan die notificatie triggert wanneer de gebruiker die geassocieerd is
            // met de huidig actieve firebase uid token aan zijn database path wordt toegevoegd of gewijzigd in de
            // tafels van de booking statussen

        }

    }

    private boolean isMyServiceRunning(AppCompatActivity appCompatActivity, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) appCompatActivity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    @Override
    public void initLayoutForItemSelected(String city){

        item_layout.setVisibility(View.VISIBLE);

        item_layout.setClickable(true);

        select_city_village_textView.setVisibility(View.INVISIBLE);

        item_textView.setText(city);

        // make item layout and select city/village info textView invisible, assign city name to textView inside item view

    }

    @Override
    public TextView getTextView() {
        return item_textView;
    }

    @Override
    public void initCityItem(Bitmap bitmap1) {

        item_layout.setVisibility(View.VISIBLE);

        item_layout.setVisibility(View.VISIBLE);

        item_layout.setClickable(true);

        select_city_village_textView.setVisibility(View.INVISIBLE);

        item_imageView.setImageBitmap(bitmap1);

    }

    @Override
    public void openNewAddApartementDetailsDialog(String UID, AppCompatActivity appCompatActivity) {

        CustomDialogWIthThreeButtons.UID = UID;

        CustomDialogWIthThreeButtons customDialogWithEditText = new CustomDialogWIthThreeButtons();
        customDialogWithEditText.show(appCompatActivity.getSupportFragmentManager(), "test");

    }

    @Override
    public void makeProgressLayoutAppear() {

        MyApplication.makeProgressLayoutAppearRelativeLayout(progress_layout, enter_anim_progress_layout);

    }

    @Override
    public void informUserPlaceInIllegalCountry(Context context){

        Toast.makeText(context, context.getResources().getString(R.string.booking_country_not_available), Toast.LENGTH_LONG).show();

        item_layout.setVisibility(View.INVISIBLE);

        item_layout.setClickable(false);

        item_textView.setVisibility(View.VISIBLE);

        // inform user that apartments are currently only available in The Netherlands and Belgium
    }

    @Override
    public void informUserSelecLocOutOfBounds(Context context){

        Toast.makeText(context, context.getResources().getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();

        // inform user that an unidentified or illegal place has been chosen by showing a toast
        //(I.E. Europe, Alabama, Atlantic Ocean, etc.)

        item_layout.setVisibility(View.INVISIBLE);

        // make item layout invisible

    }

    @Override
    public StartupActivityObservables getCurrentObservableInstance() {

        return observables;

    }

    @Override
    public void informUserSomethingWentWrong(Context context, IOException e){

        Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();

        e.printStackTrace();
    }

    private void initActivity(){

        observables = new StartupActivityObservables();

        viewModel = new StartupActivityViewModel(this, observables);

        db = new StartupActivityDBImplementer(observables);

        model = new StartupActivityModel();

    }

    private void initAnims(Context context){

        enter_anim_progress_layout = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);
        exit_anim_progress_layout = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);
        exit_anim_item_layout = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        // load animations

        enter_anim_progress_layout.setDuration(200);
        exit_anim_progress_layout.setDuration(200);
        exit_anim_item_layout.setDuration(200);

        // set duration for animations at 200 milliseconds

    }

    private void setTypefaces(Context context){

        Typeface tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

        item_textView.setTypeface(tp);
        select_city_village_textView.setTypeface(tp);

        // set application typeface to all textViews in activity

    }

    private void linkJavaVariablesToXmlViews(){

        imageView_background_view = rootView.findViewById(R.id.imageView_background_image);
        item_layout = rootView.findViewById(R.id.item_layout);
        logo_imageView = rootView.findViewById(R.id.logo_imageView);
        item_imageView = rootView.findViewById(R.id.imageView);
        select_city_village_textView = rootView.findViewById(R.id.select_city_village_textView);
        account_layout = rootView.findViewById(R.id.account_framelayout);
        settings_layout = rootView.findViewById(R.id.settings_layout);
        progress_layout = rootView.findViewById(R.id.progress_layout);
        account_layout_view = rootView.findViewById(R.id.account_layout_view);
        settings_layout_views = rootView.findViewById(R.id.settings_layout_view);
        item_textView = rootView.findViewById(R.id.textView);

        fab = rootView.findViewById(R.id.fab);

        // inform activity which xml elements belong to what variables

    }

    @Override
    public void informUserErrorSelectingPlace(Context context){

        Toast.makeText(context, context.getResources().getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();

        item_layout.setVisibility(View.INVISIBLE);

        item_layout.setClickable(false);

        // inform user that no place has been seleced with the help of a toast

    }

    @Override
    public void prepareLaunchAccountApartmentActivity(Context context, AppCompatActivity appCompatActivity, Bitmap bitmap1, Bitmap bitmap2){

        // if no bitmap can be loaded, disable click on city items

        bitmap = bitmap1;

        convertBitmapsToBase64Strings(bitmap1, bitmap2);

            item_layout.setVisibility(View.VISIBLE);

            item_layout.setClickable(true);

            select_city_village_textView.setVisibility(View.INVISIBLE);

            item_imageView.setImageBitmap(bitmap1);

        MyApplication.makeProgressLayoutDisappearRelativeLayout(progress_layout, exit_anim_progress_layout);

        //Toast.makeText(getApplicationContext(), "Layer 1", Toast.LENGTH_SHORT).show();

        item_layout.setOnClickListener(view -> viewModel.startAccountApartmentActivity(
                context, appCompatActivity,
                exit_anim_item_layout,
                item_imageView, encoded, encoded2));

        // set item imageView background to retrieved bitmap object of selected location

    }

    private void convertBitmapsToBase64Strings(Bitmap bitmap1, Bitmap bitmap2){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();

        // create Byte Array Output Stream objects

        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byte[] byteArray2 = byteArrayOutputStream2.toByteArray();

        // create byte array objects

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        encoded2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);

        // convert byte array objects to (Base64 encoded) String objects

    }

    @Override
    public void freezeLayoutInteraction(){

        // make loading screen disappear

        MyApplication.makeProgressLayoutDisappearRelativeLayout(progress_layout, exit_anim_progress_layout);

        item_layout.setVisibility(View.INVISIBLE);

        item_layout.setClickable(false);

        select_city_village_textView.setVisibility(View.VISIBLE);

        // if no bitmap can be loaded, disable click on city items

    }

    @Override
    public void startExitAnims(){

        item_textView.startAnimation(exit_anim_item_layout);

        imageView_background_view.startAnimation(exit_anim_item_layout);

    }

    private void createDummyListItems(){

        /*

        ArrayList<StartupActivityInformation> information = new ArrayList<>();

        information.add(new StartupActivityInformation(R.drawable.eiffel_tower_small, "Amsterdam"));
        information.add(new StartupActivityInformation(R.drawable.eiffel_tower_small, "Amsterdam"));
        information.add(new StartupActivityInformation(R.drawable.eiffel_tower_small, "Amsterdam"));
        information.add(new StartupActivityInformation(R.drawable.eiffel_tower_small, "Amsterdam"));

        // create dummy array list for offline testing

        */

    }

    //register layout selected, log_in_layout deselected

    @Override
    public void makeTextViewInvisibleWhenLoading(Context context){

        //setItemTextViewInvisibleWhenLoading(context);
        setItemTextViewInvisibleIfBitmapEmpty();

    }

    @Override
    public void initLayout(){

        fab.show();
        logo_imageView.setVisibility(View.VISIBLE);
        account_layout.setVisibility(View.VISIBLE);
        account_layout_view.setVisibility(View.INVISIBLE);
        settings_layout_views.setVisibility(View.INVISIBLE);

        // deselect all option layouts

    }

    /*

    private void setItemTextViewInvisibleWhenLoading(Context context){

        if (!item_textView.getText().equals(context.getResources().getString(R.string.loading))){

            select_city_village_textView.setVisibility(View.INVISIBLE);

        }

    }

    */

    private void setItemTextViewInvisibleIfBitmapEmpty(){

        if (bitmap == null) {

            select_city_village_textView.setVisibility(View.VISIBLE);

        }

    }

}
