package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityYesScreen1Methods;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivitiesMethods;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivityYesScreen1 extends AppCompatActivity {

    TextView
            subTitle_textView,
            additional_info_textView;

    @SuppressLint("StaticFieldLeak")
    public static TextView error_textView;

    private RelativeLayout
            register_layout,
            log_in_layout;

    private View
            register_layout_view,
            log_in_layout_view,
            not_now_layout_view;

    private List<View>
            viewsInLayout;

    private Float
            register_layout_org_pos_x,
            log_in_layout_org_pos_x;

    private TextView
            not_now_textView,
            log_in_textView,
            register_textView;

    public static FloatingActionButton fab;

    private Intent i2;

    private FrameLayout not_now_layout;

    protected static final int
            REQUEST_CODE_TAKE_PICTURE = 1263,
            REQUEST_CODE_PICK_PICTURE = 9575;

    private ImageView profile_imageView;

    public static Bitmap bitmap = null;

    public static int isFromActivity;

    private Bundle extras = null;

    private RelativeLayout relativeLayout;

    private int height;

    public static Uri mCapturedImageURI;

    List<View> animatingViews;

    List<View> fadeInViews = new ArrayList<>();

    List<Float> orig_pos_coordinates = new ArrayList<>();
    List<View> buttons = new ArrayList<>();

    public static Bitmap cameraBitmap = null;

    private List<View> unselectedViews;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_yes_screen1);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        getLayoutElementsOrigPosX();

        initArrays();

        fadeInLayoutElements();

        initMiscTasks();

        initUI();

        StudentsCouchAnimations.animateActivityTransitionScreen3(
                getApplicationContext(), viewsInLayout, buttons, orig_pos_coordinates,
                fadeInViews, 400, profile_imageView);

        // animate activity enter animations

        getDataFromPreviousActivity();

        RegisterActivityYesScreen1Methods.initialiseFabOnClickListener(
                getApplicationContext(), fab, log_in_layout, register_layout,
                not_now_layout, not_now_layout_view, extras, animatingViews,
                RegisterActivityYesScreen1.this, fadeInViews);

        // initialise fab onClickListener (proceed to next activity depending on whether or not 'not now' button is selected)

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        not_now_layout_view.setSelected(false);

        getDataFromPreviousActivity();

        changeActivityFuncAccToAccUserCameFrom();

        changeUIifUserCameFromUpcomingRegisterActivityScreen();

        deselectAllButtons();

        RegisterActivityYesScreen1Methods.initialiseCameraButtonOnClickListener(
                log_in_layout, log_in_layout_view, i2, RegisterActivityYesScreen1.this, REQUEST_CODE_TAKE_PICTURE,
                fab, register_layout_view, not_now_layout_view
        );

        RegisterActivityYesScreen1Methods.initialiseGalleryButton(
                RegisterActivityYesScreen1.this, register_layout, log_in_layout_view,
                not_now_layout_view, register_layout_view, REQUEST_CODE_PICK_PICTURE
        );

        initialiseNotNowButton();

        // initiate onClickListeners for buttons. See code at end of page for reference ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ (change of sperate constructor class is made)

        makeFabClickable();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        fab.setClickable(true);

        StartupMethods.enterAnimationTwoTextButtons(
                getApplicationContext(), viewsInLayout, fadeInViews, 400,
                100, orig_pos_coordinates, buttons, null);

        profile_imageView.setVisibility(View.VISIBLE);

        // start re-enter animations

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        overridePendingTransition(0, 0);

        // prevent any default activity animations from occurring

        if (requestCode == REQUEST_CODE_PICK_PICTURE && resultCode == RESULT_OK) {

            RegisterActivitiesMethods.retrieveGalleryImage(getApplicationContext(), data, profile_imageView, true, 1);

        }

        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {

            RegisterActivitiesMethods.retrieveCameraImage(
                    getApplicationContext(), mCapturedImageURI, profile_imageView, RegisterActivityYesScreen1.this, 1);

        }


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);

        //prevent activity from animating when user presses back button
    }

    // ----- ACTIVITY METHODS -----

    @SuppressLint("ClickableViewAccessibility")
    public void initialiseNotNowButton (){

        not_now_layout.setOnTouchListener((view, event) -> {

            initUnselectedViews();

            fab.show();

            if (event.getAction() == MotionEvent.ACTION_UP){

                StartupMethods.startCircularRevealAnimationMultipleButtons(
                        getApplicationContext(), not_now_layout_view, unselectedViews, event);

            }

            not_now_layout_view.setSelected(true);

            // select not now option and deselect all other options

            return true;
        });

    }

    private void linkXmlElementsToJavaVariables(){

        additional_info_textView = findViewById(R.id.aditional_info_textView);
        subTitle_textView = findViewById(R.id.subTitle_textView);
        not_now_textView = findViewById(R.id.additional_info_textView);
        error_textView = findViewById(R.id.error_textView);
        register_textView = findViewById(R.id.register_textView);
        log_in_textView = findViewById(R.id.log_in_textView);

        profile_imageView = findViewById(R.id.profile_imageView);

        log_in_layout = findViewById(R.id.log_in_layout);
        register_layout = findViewById(R.id.register_layout);
        relativeLayout = findViewById(R.id.relativeLayout);

        log_in_layout_view = findViewById(R.id.log_in_layout_view);
        register_layout_view = findViewById(R.id.register_layout_view);
        not_now_layout_view = findViewById(R.id.not_now_layout_view);

        not_now_layout = findViewById(R.id.not_now_layout);

        fab = findViewById(R.id.fab);

        // link java variables to xml views

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");

        additional_info_textView.setTypeface(tp);
        not_now_textView.setTypeface(tp);
        subTitle_textView.setTypeface(tp);
        error_textView.setTypeface(tp);
        register_textView.setTypeface(tp);
        log_in_textView.setTypeface(tp);

        // initialise and set typeface to all textViews in layout


    }
    private void getLayoutElementsOrigPosX(){

        log_in_layout_org_pos_x = log_in_layout.getX();
        register_layout_org_pos_x = register_layout.getX();

        // get base coordinates at original position of views

    }

    private void initArrays(){

        initViewsInLayoutArray();

        initFadeInViewsArray();

        initOrigPosArray();

        initButtonsArray();

        initAnimatingViewsArray();

    }

    private void initViewsInLayoutArray(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(additional_info_textView);
        viewsInLayout.add(subTitle_textView);
        viewsInLayout.add(not_now_layout);
        viewsInLayout.add(error_textView);
        viewsInLayout.add(register_layout);
        viewsInLayout.add(log_in_layout);
        viewsInLayout.add(register_layout);

    }

    private void initFadeInViewsArray(){

        fadeInViews = new ArrayList<>();

        fadeInViews.add(not_now_layout);
        fadeInViews.add(subTitle_textView);
        fadeInViews.add(additional_info_textView);
        fadeInViews.add(profile_imageView);

    }

    private void initOrigPosArray(){

        orig_pos_coordinates = new ArrayList<>();

        orig_pos_coordinates.add(log_in_layout_org_pos_x);
        orig_pos_coordinates.add(register_layout_org_pos_x);

    }

    private void initButtonsArray(){

        buttons = new ArrayList<>();

        buttons.add(log_in_layout);
        buttons.add(register_layout);

    }

    private void initAnimatingViewsArray(){

        animatingViews = new ArrayList<>();

        animatingViews.add(log_in_layout);
        animatingViews.add(register_layout);

    }

    private void fadeInLayoutElements(){

        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_in_animation);

        not_now_layout.startAnimation(fade_in);
        subTitle_textView.startAnimation(fade_in);
        additional_info_textView.startAnimation(fade_in);
        profile_imageView.startAnimation(fade_in);

        // link views that require same animations in views arraylist

    }

    private void initUI(){

        fab.hide();

        ViewTreeObserver vto = relativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {

            height = profile_imageView.getHeight();
            profile_imageView.getLayoutParams().width = height;

        });

        // wait until layout is fully loaded, then set width of image equal to height of image

    }

    private void getDataFromPreviousActivity(){

        extras = getIntent().getExtras();

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 0);

            // retrieve info on what activity user came from

        }

    }

    private void initMiscTasks(){

        not_now_layout_view.setSelected(false);

        // set not now layout to unselected to prevent activity from continuing to the wrong proceeding activity

        FacebookSdk.sdkInitialize(getApplicationContext());

    }

    private void changeActivityFuncAccToAccUserCameFrom(){

        if(isFromActivity == 1)
        {

            not_now_layout.removeAllViews();


            /*

             * if user comes from 'become host' or 'switch to host' button
             * located in ProfileActivity, prevent the user from pressing the
             * 'not now' button by removing the main layout of the button entirely

             */

        }

    }

    private void changeUIifUserCameFromUpcomingRegisterActivityScreen(){

        if (bitmap != null){

            additional_info_textView.setText(getResources().getString(R.string.add_more_photos));
            fab.show();

            // if bitmap is set, inform user that more apartment images can be added in the next activity

        }

    }

    private void deselectAllButtons(){

        deselectButtonsUI();

        deselectButtonsInter();

        // when onStart() is instantiated, deselect all views, both visually and internally

    }

    private void deselectButtonsUI(){

        log_in_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivityYesScreen1.this, R.color.main_background));
        register_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivityYesScreen1.this, R.color.main_background));
        not_now_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivityYesScreen1.this, R.color.main_background));

        // set background color of all reveal views to the the color of an unpressed view

    }

    private void deselectButtonsInter(){

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(false);
        not_now_layout.setSelected(false);

        // deselect all views

    }

    private void makeFabClickable(){

        fab.setClickable(true);

        /*

         * make fab clickable. Fab could have previously been made un-clickable
         * by the user when clicking the Fab, resulting in the user leaving the current activity

         */

    }

    private void initUnselectedViews(){

        unselectedViews = new ArrayList<>();
        unselectedViews.add(register_layout_view);
        unselectedViews.add(log_in_layout_view);

    }

}
