package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.dbImplementers.ConfirmRegistrationDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.ConfirmRegistrationActivityDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.ConfirmRegistrationActivityModel;
import com.studentscouch.projectbostonfiles.observables.ConfirmRegistrationObservables;
import com.studentscouch.projectbostonfiles.view.views.ConfirmRegistrationActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.ConfirmRegistrationViewModel;

import java.util.ArrayList;
import java.util.List;

public class ConfirmRegistrationViewImplementer implements ConfirmRegistrationActivityView {

    private FloatingActionButton fab;

    private ImageView
            apartementImage1_1,
            apartementImage2,
            apartementImage3;

    private ScrollView scrollView;

    private TextView
            firstName_view_textView,
            lastName_view_textView,
            email_view_textView,
            birthdate_view_textView,
            place_of_residence_view_textView,
            apartement_city_view_textView,
            street_view_textView,
            house_number_view_textView,
            title_view_textView,
            description_view_textView,
            price_per_night_view_textView,
            max_people_view_textView,
            error_textView,
            firstName_textView,
            lastName_textView,
            email_textView,
            birthdate_textView,
            place_of_residence_textView,
            profile_picture_textView,
            apartement_data_textView,
            apartement_images_textView,
            apartement_city_textView,
            street_textView,
            house_number_textView,
            title_textView,
            descriptiondescription,
            price_per_night2,
            price_per_night_currency_view_textView,
            max_people_textView,
            gender_textView,
            log_in_textView;

    private FrameLayout
            log_in_layout,
            apartement_city_view,
            street_view,
            house_number_view,
            title_view,
            description_view,
            price_per_night_view,
            max_people_view;

    private Float
            log_in_layout_org_pos_x,
            scrollView_org_pos_x;

    private List<Float> original_pos_coordinates;

    private View log_in_layout_view;

    private ObjectAnimator
            anim,
            anim2;

    private int
            isFromActivity,
            isNotNowAdded;

    private Typeface tp;

    private FirebaseAuth fbAuth;

    private FrameLayout progress_layout;

    private FrameLayout
            firstName_view,
            lastName_view,
            email_view,
            birthdate_view,
            place_of_residence_view;

    private int isNotNowSelected;

    private List<View> viewsInLayout;

    private List<TextView> apartmentViews;

    private List <View> apartmentNotNullViews;

    private List<View> userViews;

    private RelativeLayout masterLayout;

    private ImageView
            gender_imageView,
            profile_picture;

    private int numUsers;

    private int choice;

    private View rootView;

    private ViewGroup mainViewGroup;

    private ConfirmRegistrationViewModel viewModel;

    private ConfirmRegistrationActivityDBInterface db;

    private ConfirmRegistrationObservables observables;

    private ConfirmRegistrationActivityModel model;

    private Bitmap
            bitmapApartment1,
            bitmapApartment2,
            bitmapApartment3;

    public ConfirmRegistrationViewImplementer(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_confirm_registration, viewGroup);

        mainViewGroup = viewGroup;

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        linkJavaVariablesWithXmlViews();

        setTypeFaces(context);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        init(context);

        getData(appCompatActivity, context);

        viewModel.initRegistrationViewsAccToChosenOption(
                apartmentNotNullViews, apartmentViews, userViews, profile_picture,
                profile_picture_textView, isNotNowSelected, isFromActivity, choice);

        // displays either apartment or profile views or both according to what registration option user has chosen

        db.getNumHostsAndUsersFromDB();

        StudentsCouchAnimations anims = new StudentsCouchAnimations(appCompatActivity);

        anims.animateActivityTransitionConfirmRegistrationActivity(
                log_in_layout, scrollView, context, viewsInLayout,
                original_pos_coordinates, log_in_layout_org_pos_x, scrollView_org_pos_x);

        loadAllEnteredData(context);

        // load sharedPreferences for retrieving registration data

        fab.setOnClickListener(v -> {

            onFabClicked(context, appCompatActivity);

            // initialise fab. Method is responsible internally and externally processing all registration info submitted by the user

        });

        log_in_layout.setOnTouchListener((view, motionEvent) -> {

            initialiseConfirmButton(context, motionEvent, fab, log_in_layout_view);

            return true;

        });

    }

    @Override
    public void loadAllEnteredData(Context context) {

        // this method loads all the data the user has submitted during the registration process

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        // retrieve main data saved in sharedPreferences

        retrieveValuesFromSharedPrefs(context, prefs);

        setObservables(context, prefs);

        setUserProfileInfo(prefs);

        // insert retrieved strings into textViews

        initLayoutAccToRegistrationContext(context, prefs);

    }

    @Override
    public void extractStringsFromTextViews(Context context) {

        observables.setFirstName(firstName_view_textView.getText().toString().trim());
        observables.setLastName(lastName_view_textView.getText().toString().trim());
        observables.setEmail(email_view_textView.getText().toString().trim());
        observables.setBirthdate(birthdate_view_textView.getText().toString().trim());
        observables.setPlace_of_residence(place_of_residence_view_textView.getText().toString().trim());

        model.getRemainingDataFromSharedPrefs(context, choice);

    }

    @Override
    public void extractApartmentStringsFromTextViews(Context context) {

        observables.setCityVillage(apartement_city_view_textView.getText().toString().trim());
        observables.setStreet(street_view_textView.getText().toString().trim());
        observables.setHouse_number(house_number_view_textView.getText().toString().trim());
        observables.setTitle(title_view_textView.getText().toString().trim());
        observables.setDescription(description_view_textView.getText().toString().trim());
        observables.setPrice_per_night(price_per_night_view_textView.getText().toString().trim());
        observables.setIs_two_people_allowed(Integer.valueOf(max_people_view_textView.getText().toString().trim()));

        // get apartment data submitted by user during registration process

    }

    @Override
    public Bitmap getApartmentImage1_1() {

        Bitmap returnBitmap = null;

        if (observables.getApartmentImages().size() == 1 || observables.getApartmentImages().size() == 3) {

            returnBitmap =  ((BitmapDrawable) apartementImage2.getDrawable()).getBitmap();

        }

        if (observables.getApartmentImages().size() == 2) {

            returnBitmap =  ((BitmapDrawable) apartementImage1_1.getDrawable()).getBitmap();

        }

        return returnBitmap;

    }

    @Override
    public void initObjectAnimators(Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", log_in_layout.getX(), -metrics.widthPixels - log_in_layout.getX());
        anim2 = ObjectAnimator.ofFloat(scrollView, "translationX", scrollView.getX(), -metrics.widthPixels - scrollView.getX());

        // animate both views in animation object parameters from center to west

        anim.setDuration(400);
        anim2.setDuration(400);

        // set animation duration

    }

    @Override
    public ObjectAnimator getObjectAnimator1() {

        return anim;

    }

    @Override
    public ObjectAnimator getObjectAnimator2() {

        return anim2;

    }

    @Override
    public void hideFab() {

        fab.hide();

    }

    @Override
    public void enableFab() {

        fab.show();
        fab.setClickable(true);

        // show fab and make fab clickable

    }

    @Override
    public TextView getErrorTextView() {

        return error_textView;

    }

    @Override
    public FloatingActionButton getFab() {
        return fab;
    }

    @Override
    public void makeProgressLayoutDisappear(AppCompatActivity appCompatActivity, Context context) {

        Animation exit_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        // load activity exit fade out animation

        exit_anim.setDuration(200);

        // set animation duration

        initAnims(context);

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

    }

    @Override
    public void showErrorMessage(Context context) {

        StartupMethods.showErrorMessage(context, error_textView, context.getResources().getString(R.string.internet_connection_non_existent), tp);

        // show toast informing user that internet connection has not been found

    }

    @Override
    public void showProgressLayout(AppCompatActivity appCompatActivity) {

        Animation enter_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_in_animation);

        // load activity enter fade in animation

        enter_anim.setDuration(200);

        // set animation duration

        MyApplication.makeProgressLayoutAppearFrameLayout(progress_layout, enter_anim);

    }

    private void onFabClicked(final Context context, AppCompatActivity appCompatActivity) {

        disableAllButtons();

        fab.hide();

        // hide fab

        initAnims(context);

        db.submitNewInfoToDB(
                context, appCompatActivity, progress_layout,
                isNotNowAdded, isFromActivity, tp, fbAuth,
                numUsers, mainViewGroup);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void disableAllButtons(){

        fab.setClickable(false);

        // make fab unclickable

        log_in_layout.setOnTouchListener(null);

        // make 'confirm' button unclickable

    }

    private void initialiseConfirmButton (Context context, MotionEvent motionEvent, FloatingActionButton fab, View log_in_layout_view) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP){

            fab.show();
            fab.clearAnimation();

            StartupMethods.startCircularRevealAnimationCustomStartRadius(context, log_in_layout_view, motionEvent, true);

            // start circular reveal animation

        }


        log_in_layout_view.setSelected(true);

        // set 'Confirm' button as selected

    }

    private void getData(AppCompatActivity appCompatActivity, Context context){

        getRegistrationData(context);

        getDataFromPrevActivity(appCompatActivity);

    }

    private void init(Context context){

        initActivity();

        initObjectAnimators(context);

        initLayoutRelatedVariables();

        initArrays();

        fab.hide();

    }

    private void initActivity(){

        observables = new ConfirmRegistrationObservables();

        db = new ConfirmRegistrationDBImplementer(this, observables);

        viewModel = new ConfirmRegistrationViewModel(this);

        observables.setSubleasedNightsLeft(123456789);

        model = new ConfirmRegistrationActivityModel(observables);

    }

    private void initLayoutRelatedVariables(){

        log_in_layout_org_pos_x = log_in_layout.getX();

        scrollView_org_pos_x = scrollView.getX();

        // get original x positions for onRestart() activity state animation

    }

    private void initAnims(Context context){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", log_in_layout.getX(), -metrics.widthPixels - log_in_layout.getX());
        anim2 = ObjectAnimator.ofFloat(scrollView, "translationX", scrollView.getX(), -metrics.widthPixels - scrollView.getX());

        // animate both views in animation object parameters from center to west

        anim.setDuration(400);
        anim2.setDuration(400);

        // set animation duration

    }

    private void initLayoutAccToRegistrationContext(Context context, SharedPreferences prefs){

        initYesSelectedLayout(context, prefs);

        initAddHostDataLayout(context, prefs);

        initAddHostDataLayout2(context, prefs);

        initAddHostLayout3();

        initNoSelectedLayout();

        initAddApartmentDetailsLaterLayout();

    }

    private void retrieveValuesFromSharedPrefs(Context context, SharedPreferences prefs){

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        // retrieve data about whether the user has chosen to register or not to register as a host

        choice = sharedPreferences2.getInt("Choicee", 0);

        isNotNowSelected = prefs.getInt("isNotNowSelected", 0);

    }

    private void initAddApartmentDetailsLaterLayout(){

        if (isNotNowSelected == 1) {

            // Register user 'no' selected

            displayUserInfoOnly();

        }

    }

    private void initNoSelectedLayout(){

        if (isFromActivity == 2 && choice == 2) {

            // Register user 'no' selected

            displayUserInfoOnly();

        }

    }

    private void initAddHostLayout3(){

        if (isFromActivity == 2 && choice == 5) {

            // Register user 'add apartment details' selected

            displayApartmentInfoOnly();

        }

    }

    private void initAddHostDataLayout2(Context context, SharedPreferences prefs){

        if (isFromActivity == 1 && choice == 1) {

            displayApartmentInfoOnly();

            /*

             * if user chose to register as user without apartment,
             * make all objects corresponding with apartment metadata disappear

             */

            loadApartmentImagesYesSelected(prefs);

            setApartmentInfo(context, prefs);

        }

    }

    private void initYesSelectedLayout(Context context, SharedPreferences prefs){

        if (isFromActivity == 2 && choice == 1){

            loadApartmentImagesYesSelected(prefs);

            setUserProfileInfo(prefs);

            setApartmentInfo(context, prefs);

            // assign retrieved strings to corresponding textViews

        }

    }

    private void initAddHostDataLayout(Context context, SharedPreferences prefs){

        bitmapApartment2 = null;
        bitmapApartment3 = null;

        if (isFromActivity == 2) {

            // if user came from 'become host' or 'add host data' button, do the following

            displayApartmentInfoOnly();

            Bitmap bitmapApartement1 = processFirstApartmentImage(prefs);

            /*

             * convert Base64 String object of first apartment image
             * saved in sharedPreferences into bitmap object.
             * if no apartment image is found, return an empty String

             */

            processSecondApartmentImageIfPossible(prefs);

            processThirdImageIfPossible(prefs);

            model.loadApartmentImagesBecomeHostSelected(context,
                    bitmapApartement1, bitmapApartment2, bitmapApartment3,
                    apartementImage1_1, apartementImage2, apartementImage3);

            setApartmentInfo(context, prefs);

            setUserProfileInfo(prefs);

            // assign retrieved strings to corresponding textViews

        }

    }

    private void setObservables(Context context, SharedPreferences prefs){

        observables.setFirstName(prefs.getString("savedUserDataSharedFirstName", null));
        observables.setLastName(prefs.getString("savedUserDataSharedLastName", null));
        observables.setEmail(prefs.getString("savedUserDataEmail", null));
        observables.setBirthdate(prefs.getString("savedUserDataSharedBirthdate", null));
        observables.setPlace_of_residence(prefs.getString("savedUserDataShared_place_of_residence", null));
        observables.setGender(prefs.getString("savedUserDataSharedGender", "female"));

        // retrieve specific types of submitted data about the user

        if (isFromActivity == 1 && choice == 1){

            model.setApartmentObservables(context, choice);

        }

    }

    private void setTypeFaces(Context context){

        tp = Typeface.createFromAsset(context.getAssets(),"project_boston_typeface.ttf");

        firstName_textView.setTypeface(tp);
        firstName_view_textView.setTypeface(tp);
        lastName_textView.setTypeface(tp);
        lastName_view_textView.setTypeface(tp);
        email_textView.setTypeface(tp);
        email_view_textView.setTypeface(tp);
        birthdate_textView.setTypeface(tp);
        birthdate_view_textView.setTypeface(tp);
        place_of_residence_textView.setTypeface(tp);
        place_of_residence_view_textView.setTypeface(tp);
        profile_picture_textView.setTypeface(tp);
        apartement_data_textView.setTypeface(tp);
        apartement_images_textView.setTypeface(tp);
        apartement_city_textView.setTypeface(tp);
        apartement_city_view_textView.setTypeface(tp);
        street_textView.setTypeface(tp);
        street_view_textView.setTypeface(tp);
        house_number_textView.setTypeface(tp);
        house_number_view_textView.setTypeface(tp);
        title_textView.setTypeface(tp);
        title_view_textView.setTypeface(tp);
        descriptiondescription.setTypeface(tp);
        description_view_textView.setTypeface(tp);
        price_per_night2.setTypeface(tp);
        price_per_night_view_textView.setTypeface(tp);
        price_per_night_currency_view_textView.setTypeface(tp);
        max_people_textView.setTypeface(tp);
        max_people_view_textView.setTypeface(tp);
        gender_textView.setTypeface(tp);
        log_in_textView.setTypeface(tp);

    }

    private void linkJavaVariablesWithXmlViews(){

        firstName_textView = rootView.findViewById(R.id.firstName_textView);
        firstName_view_textView = rootView.findViewById(R.id.firstName_view_textView);
        lastName_textView = rootView.findViewById(R.id.lastName_textView);
        lastName_view_textView = rootView.findViewById(R.id.lastName_view_textView);
        email_textView = rootView.findViewById(R.id.email_textView);
        email_view_textView = rootView.findViewById(R.id.email_view_textView);
        birthdate_textView = rootView.findViewById(R.id.birthdate_textView);
        birthdate_view_textView = rootView.findViewById(R.id.birthdate_view_textView);
        place_of_residence_textView = rootView.findViewById(R.id.place_of_residence_textView);
        place_of_residence_view_textView = rootView.findViewById(R.id.place_of_residence_view_textView);
        profile_picture_textView = rootView.findViewById(R.id.profile_picture_textView);
        apartement_data_textView = rootView.findViewById(R.id.apartement_data_textView);
        apartement_images_textView = rootView.findViewById(R.id.apartement_images_textView);
        apartement_city_textView = rootView.findViewById(R.id.apartement_city_textView);
        apartement_city_view_textView = rootView.findViewById(R.id.apartement_city_view_textView);
        street_textView = rootView.findViewById(R.id.street_textView);
        street_view_textView = rootView.findViewById(R.id.street_view_textView);
        house_number_textView = rootView.findViewById(R.id.house_number_textView);
        house_number_view_textView = rootView.findViewById(R.id.house_number_view_textView);
        title_textView = rootView.findViewById(R.id.title_textView);
        title_view_textView = rootView.findViewById(R.id.title_view_textView);
        descriptiondescription = rootView.findViewById(R.id.description);
        description_view_textView = rootView.findViewById(R.id.description_view_textView);
        price_per_night2 = rootView.findViewById(R.id.price_per_night);
        price_per_night_view_textView = rootView.findViewById(R.id.price_per_night_view_textView);
        price_per_night_currency_view_textView = rootView.findViewById(R.id.price_per_night_currency_view_textView);
        max_people_textView = rootView.findViewById(R.id.max_people_textView);
        max_people_view_textView = rootView.findViewById(R.id.max_people_view_textView);
        gender_textView = rootView.findViewById(R.id.gender_textView);
        log_in_textView = rootView.findViewById(R.id.log_in_textView);
        error_textView = rootView.findViewById(R.id.error_textView);

        apartement_city_view = rootView.findViewById(R.id.apartement_city_view);
        street_view = rootView.findViewById(R.id.street_view);
        house_number_view = rootView.findViewById(R.id.house_number_view);
        title_view = rootView.findViewById(R.id.title_view);
        description_view = rootView.findViewById(R.id.description_view);
        price_per_night_view = rootView.findViewById(R.id.price_per_night_view);
        max_people_view = rootView.findViewById(R.id.max_people_view);

        firstName_view = rootView.findViewById(R.id.firstName_view);
        lastName_view = rootView.findViewById(R.id.lastName_view);
        email_view = rootView.findViewById(R.id.email_view);
        birthdate_view = rootView.findViewById(R.id.birthdate_view);
        place_of_residence_view = rootView.findViewById(R.id.place_of_residence_view);

        log_in_layout = rootView.findViewById(R.id.log_in_layout);

        progress_layout = rootView.findViewById(R.id.progress_layout);

        log_in_layout_view = rootView.findViewById(R.id.log_in_layout_view);

        masterLayout = rootView.findViewById(R.id.masterLayout);

        scrollView = rootView.findViewById(R.id.scrollView);

        profile_picture = rootView.findViewById(R.id.profile_picture_image);
        apartementImage1_1 = rootView.findViewById(R.id.image_1);
        apartementImage2 = rootView.findViewById(R.id.image_2);
        apartementImage3 = rootView.findViewById(R.id.image_3);

        scrollView = rootView.findViewById(R.id.scrollView);

        gender_imageView = rootView.findViewById(R.id.gender_imageView);

        fab = rootView.findViewById(R.id.fab);

    }

    private void getDataFromPrevActivity(AppCompatActivity appCompatActivity){

        Bundle extras = appCompatActivity.getIntent().getExtras();

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 5);

            // if user comes from final register activity, do nothing

        }

    }

    private void getRegistrationData(Context context){

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        SharedPreferences prefs2 = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        assignGlobalVariables(prefs, prefs2);

        model.setObservablesFromSharedPrefs(prefs);

    }

    private void assignGlobalVariables(SharedPreferences prefs, SharedPreferences prefs2){

        isNotNowSelected = prefs.getInt("isNotNowSelected", 3);

        isNotNowAdded = prefs.getInt("isNotNowSelected", 0);

        // retrieve info on whether or not user has clicked 'not now' button when having been asked about submitting data about their apartment

        choice = prefs2.getInt("Choicee", 5);

        // retrieve info on whether user has selected yes or no when having been asked to register as a host

    }

    private void initArrays(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(scrollView);
        viewsInLayout.add(log_in_layout);

        apartmentViews = new ArrayList<>();

        apartmentViews.add(apartement_data_textView);
        apartmentViews.add(apartement_images_textView);
        apartmentViews.add(apartement_city_textView);
        apartmentViews.add(apartement_city_view_textView);
        apartmentViews.add(street_textView);
        apartmentViews.add(street_view_textView);
        apartmentViews.add(house_number_textView);
        apartmentViews.add(house_number_view_textView);
        apartmentViews.add(title_textView);
        apartmentViews.add(title_view_textView);
        apartmentViews.add(descriptiondescription);
        apartmentViews.add(description_view_textView);
        apartmentViews.add(price_per_night2);
        apartmentViews.add(price_per_night_view_textView);
        apartmentViews.add(price_per_night_currency_view_textView);
        apartmentViews.add(max_people_textView);
        apartmentViews.add(max_people_view_textView);

        apartmentNotNullViews = new ArrayList<>();

        apartmentNotNullViews.add(apartement_data_textView);
        apartmentNotNullViews.add(apartement_images_textView);
        apartmentNotNullViews.add(apartement_city_textView);
        apartmentNotNullViews.add(apartement_city_view_textView);
        apartmentNotNullViews.add(street_textView);
        apartmentNotNullViews.add(street_view_textView);
        apartmentNotNullViews.add(house_number_textView);
        apartmentNotNullViews.add(house_number_view_textView);
        apartmentNotNullViews.add(title_textView);
        apartmentNotNullViews.add(title_view_textView);
        apartmentNotNullViews.add(descriptiondescription);
        apartmentNotNullViews.add(description_view_textView);
        apartmentNotNullViews.add(price_per_night2);
        apartmentNotNullViews.add(price_per_night_view_textView);
        apartmentNotNullViews.add(price_per_night_currency_view_textView);
        apartmentNotNullViews.add(max_people_textView);
        apartmentNotNullViews.add(max_people_view_textView);
        apartmentNotNullViews.add(apartement_city_view);
        apartmentNotNullViews.add(street_view);
        apartmentNotNullViews.add(house_number_view);
        apartmentNotNullViews.add(title_view);
        apartmentNotNullViews.add(description_view);
        apartmentNotNullViews.add(price_per_night_view);
        apartmentNotNullViews.add(max_people_view);

        userViews = new ArrayList<>();

        userViews.add(gender_textView);
        userViews.add(gender_imageView);
        userViews.add(firstName_textView);
        userViews.add(firstName_view_textView);
        userViews.add(lastName_textView);
        userViews.add(lastName_view_textView);
        userViews.add(email_textView);
        userViews.add(email_view_textView);
        userViews.add(birthdate_textView);
        userViews.add(birthdate_view_textView);
        userViews.add(place_of_residence_textView);
        userViews.add(place_of_residence_view_textView);
        userViews.add(profile_picture);
        userViews.add(firstName_view);
        userViews.add(lastName_view);
        userViews.add(email_view);
        userViews.add(birthdate_view);
        userViews.add(place_of_residence_view);

        original_pos_coordinates = new ArrayList<>();

        List<TextView> userProfile_textViews = new ArrayList<>();

        userProfile_textViews.add(firstName_textView);
        userProfile_textViews.add(lastName_textView);
        userProfile_textViews.add(email_textView);
        userProfile_textViews.add(birthdate_textView);
        userProfile_textViews.add(place_of_residence_textView);

        // hide fab

        original_pos_coordinates.add(log_in_layout_org_pos_x);
        original_pos_coordinates.add(scrollView_org_pos_x);

    }

    private void setGender() {

        if (observables.getGender().equals("female")){

            gender_imageView.setImageResource(R.drawable.gender_female);

            // if user selected female as gender, set female gender icon

        } else if (observables.getGender().equals("male")){

            gender_imageView.setImageResource(R.drawable.gender_male);

            // if user selected female as gender, set female gender icon

        }

    }

    private void setUserProfileInfo(SharedPreferences prefs){

        firstName_view_textView.setText(observables.getFirstName());
        lastName_view_textView.setText(observables.getLastName());
        email_view_textView.setText(observables.getEmail());
        birthdate_view_textView.setText(observables.getBirthdate());
        place_of_residence_view_textView.setText(observables.getPlace_of_residence());

        // set text-based data

        setProfilePic(prefs);

        setGender();

    }

    private void setProfilePic(SharedPreferences prefs){

        String previouslyEncodedImage = prefs.getString("profile_picture", "");
        byte[] bitmap_byte_profile_picture = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmap_byte_profile_picture, 0, bitmap_byte_profile_picture.length);
        profile_picture.setImageBitmap(bitmap);

        /*

         * insert the most recently submitted profile picture
         * into the profile picture imageView in layout of ConfirmRegistrationActivity

        */
    }

    private void setNumPeopleAllowed(){

        if (observables.getIs_two_people_allowed() == 2){

            max_people_view_textView.setText("2");

        }else if (observables.getIs_two_people_allowed() == 1){

            max_people_view_textView.setText("1");

        }

    }

    private void processSecondApartmentImageIfPossible(SharedPreferences prefs){

        try {

            String previouslyEncodedImage3 = prefs.getString("apartement2", "");
            byte[] bitmap_byte_apartment2 = Base64.decode(previouslyEncodedImage3, Base64.DEFAULT);
            bitmapApartment2 = BitmapFactory.decodeByteArray(bitmap_byte_apartment2, 0, bitmap_byte_apartment2.length);
            apartementImage2.setImageBitmap(bitmapApartment2);

            /*

             * convert Base64 String object of second apartment image
             * saved in sharedPreferences into bitmap object.
             * if no apartment image is found, return an empty String

             */

        } catch (Exception e) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("apartement2", "");
            editor.apply();

            apartementImage2.setVisibility(View.GONE);

             /*

             * if conversion of second apartment image fails, put empty String
             * in second apartment image slot and make the second apartment imageView disappear in layout

             */

        }

    }

    private void processThirdImageIfPossible(SharedPreferences prefs){

        try {

            String previouslyEncodedImage3 = prefs.getString("apartement3", "");
            byte[] bitmap_byte_apartment2 = Base64.decode(previouslyEncodedImage3, Base64.DEFAULT);
            bitmapApartment3 = BitmapFactory.decodeByteArray(bitmap_byte_apartment2, 0, bitmap_byte_apartment2.length);
            apartementImage3.setImageBitmap(bitmapApartment3);

            /*

             * convert Base64 String object of second apartment image
             * saved in sharedPreferences into bitmap object.
             * if no apartment image is found, return an empty String

             */

        } catch (Exception e) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("apartement3", "");
            editor.apply();

            apartementImage3.setVisibility(View.GONE);

            /*

             * if conversion of second apartment image fails, put empty String
             * in second apartment image slot and make the second apartment imageView disappear in layout

             */

        }

    }

    private Bitmap processFirstApartmentImage(SharedPreferences prefs){

        String previouslyEncodedImage2 = prefs.getString("apartement1", "");
        byte[] bitmap_byte_apartment1 = Base64.decode(previouslyEncodedImage2, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmap_byte_apartment1, 0, bitmap_byte_apartment1.length);

    }

    private void displayApartmentInfoOnly(){

        for (int pos = 0; pos < apartmentViews.size(); pos ++) {

            apartmentViews.get(pos).setVisibility(View.GONE);

        }

        for (int pos = 0; pos < apartmentViews.size(); pos ++) {

            apartmentViews.get(pos).setVisibility(View.VISIBLE);

        }

    }

    private void loadApartmentImagesYesSelected(SharedPreferences prefs) {

        /*

         * if user pressed 'yes' in RegisterActivityScreen1 and did not press
         * 'not now' in RegisterActivityYesScreen1, do the following

         */

        retrieveFirstApartmentImage(prefs);

        attemptRetrieveSecondApartmentImage(prefs);

        attemptRetrieveThirdApartmentImage(prefs);

        insertImagesIntoImageViews(prefs);

    }

    private void insertImagesIntoImageViews(SharedPreferences prefs){

        if (prefs.getString("apartement2", "").equals("") && prefs.getString("apartement3", "").equals("") ){

            arrangeA2nullA3null(prefs);

        }else if (!prefs.getString("apartement2", "").equals("") && prefs.getString("apartement3", "").equals("")){

            arrangeA2notNullA3null(prefs);

        }else if (prefs.getString("apartement2", "").equals("") && !prefs.getString("apartement3", "").equals("")){

            arrangeA2nullA3notNull(prefs);

        }else if (!prefs.getString("apartement1", "").equals("") && prefs.getString("apartement2", "").equals("")){

            arrangeA1notNullA2null(prefs);

        } else if (!prefs.getString("apartement1", "").equals("")
                && !prefs.getString("apartement2", "").equals("")
                && !prefs.getString("apartement3", "").equals("")){

            arrangeA1notNullA2notNullA3notNull();

        } else if (!prefs.getString("apartement1", "").equals("")
                && !prefs.getString("apartement2", "").equals("")
                && prefs.getString("apartement3", "").equals("")) {

            arrangeA1notNullA2notNullA3null();

        }

    }

    private void arrangeA1notNullA2notNullA3null(){

        apartementImage1_1.setVisibility(View.GONE);
        apartementImage2.setVisibility(View.VISIBLE);
        apartementImage3.setVisibility(View.VISIBLE);

        apartementImage2.setImageBitmap(bitmapApartment1);
        apartementImage3.setImageBitmap(bitmapApartment2);

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 2)

         */

    }

    private void arrangeA1notNullA2notNullA3notNull(){

        apartementImage1_1.setImageBitmap(bitmapApartment1);
        apartementImage2.setImageBitmap(bitmapApartment2);
        apartementImage3.setImageBitmap(bitmapApartment3);

        apartementImage1_1.setVisibility(View.VISIBLE);
        apartementImage2.setVisibility(View.VISIBLE);
        apartementImage3.setVisibility(View.VISIBLE);

        /*

         * if only first and second apartment images can be retrieved, empty third apartment slot
         * in sharedPreferences and set visibility of third imageView to gone

         */

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA1notNullA2null(SharedPreferences prefs){

        prefs.edit().remove("apartement2");

        prefs.edit().apply();

        apartementImage3.setVisibility(View.GONE);

        /*

         * if only first apartment image can be retrieved, empty second apartment slot
         * in sharedPreferences and set visibility of third imageView to gone

         */

        apartementImage2.setImageBitmap(bitmapApartment1);

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: empty

         */

        apartementImage1_1.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA2nullA3notNull(SharedPreferences prefs){

        // apartment image found at slot 1 and 3

        prefs.edit().remove("apartement3");

        prefs.edit().apply();

        /*

         * if third apartment image string is empty,
         * remove apartment images slot in sharedPreferences

         */

        apartementImage2.setImageBitmap(bitmapApartment1);

        // insert first apartment image in second imageView

        apartementImage3.setImageBitmap(bitmapApartment3);

        // insert third apartment image in second imageView

        apartementImage2.setVisibility(View.VISIBLE);

        apartementImage3.setVisibility(View.VISIBLE);

        apartementImage1_1.setVisibility(View.INVISIBLE);

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 3)

         */

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA2notNullA3null(SharedPreferences prefs){

        // apartment image found at slot 2

        prefs.edit().remove("apartement3");

        prefs.edit().apply();

        /*

         * if third apartment image string is empty,
         * remove apartment images slot in sharedPreferences

         */

        apartementImage2.setImageBitmap(bitmapApartment1);

        // insert first apartment image into second imageView

        apartementImage3.setImageBitmap(bitmapApartment2);

        // insert second apartment image in third imageView

        apartementImage2.setVisibility(View.VISIBLE);

        apartementImage3.setVisibility(View.VISIBLE);

        apartementImage1_1.setVisibility(View.INVISIBLE);


        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 2)

         */

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA2nullA3null(SharedPreferences prefs){

        // apartment images found at slot 2 and 3

        prefs.edit().remove("apartement2");
        prefs.edit().remove("apartement3");

        prefs.edit().apply();

        /*

         * if second and third apartment image strings are empty,
         * remove apartment images at their corresponding slots in sharedPreferences

         */

        apartementImage1_1.setVisibility(View.INVISIBLE);
        apartementImage2.setImageBitmap(bitmapApartment1);
        apartementImage3.setVisibility(View.INVISIBLE);

        // insert first and only apartment image into second imageView.


        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: empty

         */

    }

    private void attemptRetrieveThirdApartmentImage(SharedPreferences prefs){

        try{

            String previouslyEncodedImage4 = prefs.getString("apartement3", "");
            byte[] bitmap_byte_apartment3 = Base64.decode(previouslyEncodedImage4, Base64.DEFAULT);
            bitmapApartment3 = BitmapFactory.decodeByteArray(bitmap_byte_apartment3, 0, bitmap_byte_apartment3.length);
            apartementImage3.setImageBitmap(bitmapApartment3);

            /*

             * convert Base64 String object of third apartment image
             * saved in sharedPreferences into bitmap object.
             * if no apartment image is found, return an empty String

             */

        }catch(Exception e){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("apartement3", "");
            editor.apply();

            apartementImage3.setVisibility(View.GONE);

            /*

             * if conversion of third apartment image fails, put empty String
             * in second apartment image slot and make the third apartment imageView disappear in layout

             */

        }

    }

    private void attemptRetrieveSecondApartmentImage(SharedPreferences prefs){

        try{

            String previouslyEncodedImage3 = prefs.getString("apartement2", "");
            byte[] bitmap_byte_apartment2 = Base64.decode(previouslyEncodedImage3, Base64.DEFAULT);
            bitmapApartment2 = BitmapFactory.decodeByteArray(bitmap_byte_apartment2, 0, bitmap_byte_apartment2.length);
            apartementImage2.setImageBitmap(bitmapApartment2);

            /*

             * convert Base64 String object of second apartment image
             * saved in sharedPreferences into bitmap object.
             * if no apartment image is found, return an empty String

             */

        }catch(Exception e){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("apartement2", "");
            editor.apply();

            apartementImage2.setVisibility(View.GONE);

            /*

             * if conversion of second apartment image fails, put empty String
             * in second apartment image slot and make the second apartment imageView disappear in layout

             */

        }

    }

    private void retrieveFirstApartmentImage(SharedPreferences prefs){

        String previouslyEncodedImage2 = prefs.getString("apartement1", "");
        byte[] bitmap_byte_apartment1 = Base64.decode(previouslyEncodedImage2, Base64.DEFAULT);
        bitmapApartment1 = BitmapFactory.decodeByteArray(bitmap_byte_apartment1, 0, bitmap_byte_apartment1.length);

        /*

         * convert Base64 String object of first apartment image
         * saved in sharedPreferences into bitmap object.
         * if no apartment image is found, return an empty String

         */

    }

    private void displayUserInfoOnly(){

        for (int pos = 0; pos < apartmentViews.size(); pos++) {

            apartmentViews.get(pos).setVisibility(View.GONE);

        }

        for (int pos = 0; pos < userViews.size(); pos++) {

            userViews.get(pos).setVisibility(View.VISIBLE);

        }

    }

    private void setApartmentInfo(Context context, SharedPreferences prefs) {

        model.setApartmentData(context, prefs);

        showDataInTextViews();

        setNumPeopleAllowed();

        // if user select one person to be in the apartment at a time

    }

    private void showDataInTextViews(){

        apartement_city_view_textView.setText(observables.getCityVillage());
        street_view_textView.setText(observables.getStreet());
        house_number_view_textView.setText(observables.getPrice_per_night());
        title_view_textView.setText(observables.getTitle());
        description_view_textView.setText(observables.getDescription());
        price_per_night_view_textView.setText(observables.getHouse_number());

    }

    @Override
    public void removeOnGlobalLayoutListener(){

        fab.hide();

        ViewTreeObserver vto = masterLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                masterLayout.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this);

                checkHowManyApartmentImagesAdded();

            }
        });

    }

    @Override
    public void checkHowManyApartmentImagesAdded(){

        int height = masterLayout.getMeasuredHeight();

        if (height != 0) {

            observables.setApartmentImages(new ArrayList<>());

            if (apartementImage1_1.getVisibility() == View.VISIBLE) {

                observables.getApartmentImages().add(apartementImage1_1);

            }

            if (apartementImage2.getVisibility() == View.VISIBLE) {

                observables.getApartmentImages().add(apartementImage2);

            }

            if (apartementImage3.getVisibility() == View.VISIBLE) {

                observables.getApartmentImages().add(apartementImage3);

            }

            //Toast.makeText(getApplicationContext(), apartmentImages.size() + " ", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void animateActivityReEnterTransition(Context context, Activity activity) {

        StudentsCouchAnimations anims = new StudentsCouchAnimations(activity);

        anims.animateConfirmRegistrationActivityReEnterAnimations(context, log_in_layout, scrollView, log_in_layout_org_pos_x, scrollView_org_pos_x);

    }

}
