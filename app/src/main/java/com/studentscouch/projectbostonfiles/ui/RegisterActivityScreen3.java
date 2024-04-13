package com.studentscouch.projectbostonfiles.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityScreen3Methods;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivitiesMethods;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivityScreen3 extends AppCompatActivity {

    private TextView
            subTitle_textView,
            additional_info_textView,
            error_textView,
            log_in_textView,
            register_textView;

    private RelativeLayout
        register_layout,
        log_in_layout;

    private View
        register_layout_view,
        log_in_layout_view;

    private List<View> viewsInLayout;

    private Float
        register_layout_org_pos_x,
        log_in_layout_org_pos_x;

    Float
            subTitle_textView_org_pos_x,
            additional_info_textView_org_pos_x;

    private FloatingActionButton fab;

    private Intent i;

    private int choice;

    protected static final int
            REQUEST_CODE_TAKE_PICTURE = 1263,
            REQUEST_CODE_PICK_PICTURE = 9575;

    private ImageView profile_imageView;

    private Bitmap bitmap = null;

    private RelativeLayout relativeLayout;

    public static Uri mCapturedImageURI;

    List<View> fade_in_views;

    List<Float> orig_positions;

    List<View> firstToFinishViews;

    public static int isFromActivity;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register_activity_screen3);

        linkXmlElementsToJavaVariables();

        setTypeface();

        getLayoutElementsOrigXpos();

        initArrays();

        getDataFromPreviousActivity();

        initUI();

        setProfileImgSameHeightWidthWhenLoadingComplete(relativeLayout, profile_imageView);

        retrieveChosenChoice(getApplicationContext());

        animateActivityTransition();

        RegisterActivityScreen3Methods.initialiseFabOnClickListener(
                getApplicationContext(), fab, register_layout, log_in_layout,
                profile_imageView, RegisterActivityScreen3.this, choice, i,
                firstToFinishViews, fade_in_views);

        // initialise fab onClickListener

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        fab.setClickable(true);

        RegisterActivityScreen3Methods.initialiseLogInButtonOnClickListener(
                getApplicationContext(), log_in_layout, log_in_layout_view, register_layout_view,
                RegisterActivityScreen3.this, REQUEST_CODE_TAKE_PICTURE
        );

        // initialise log in button onClickListener (launch 'take picture' intent that will capture the users' profile picture)

        RegisterActivityScreen3Methods.initialiseRegisterButton(
                getApplicationContext(), register_layout, register_layout_view, log_in_layout_view,
                REQUEST_CODE_PICK_PICTURE, RegisterActivityScreen3.this);

        // initialise register button onClickListener (launch 'pick picture' intent where user can choose their new profile picture)

        deselectAllButtons();

        initUIbitmapNotNull();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        RegisterActivityScreen3Methods.isOpenend = false;

        StudentsCouchAnimations.onRestartAnimations1(
                getApplicationContext(), log_in_layout, register_layout, log_in_layout_org_pos_x,
                register_layout_org_pos_x, profile_imageView, subTitle_textView, additional_info_textView);

        if (profile_imageView.getDrawable() != null) {

            fab.show();

        }



        // activity enter animation method

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, 0);

        // prevent standard onBackPressed() animation from occurring

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(0, 0);

        // prevent standard activity transition animation from occurring

        if (requestCode == REQUEST_CODE_PICK_PICTURE && resultCode == RESULT_OK) {

            ImageView imageView = findViewById(R.id.profile_imageView);

            RegisterActivitiesMethods.saveChosenPicture(
                    getApplicationContext(), data.getData(), fab, additional_info_textView,
                    profile_imageView, data);


            // save chosen picture in sharedPreferences

        }

        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {

            // retrieve image bitmap and process it into profile picture imageView

            RegisterActivitiesMethods.saveTakenPicture(getApplicationContext(), fab,
                    profile_imageView, additional_info_textView, error_textView, RegisterActivityScreen3.this);

        }

    }

    private void linkXmlElementsToJavaVariables(){

        subTitle_textView = findViewById(R.id.subTitle_textView);
        additional_info_textView = findViewById(R.id.additional_info_textView);
        log_in_textView = findViewById(R.id.log_in_textView);
        register_textView = findViewById(R.id.register_textView);
        error_textView = findViewById(R.id.error_textView);

        profile_imageView = findViewById(R.id.profile_imageView);

        register_layout = findViewById(R.id.register_layout);
        log_in_layout = findViewById(R.id.log_in_layout);
       relativeLayout = findViewById(R.id.relativeLayout);

        register_layout_view = findViewById(R.id.register_layout_view);
        log_in_layout_view = findViewById(R.id.log_in_layout_view);

        fab = findViewById(R.id.fab2);

        // link java variables to xml layout views


    }

    private void setProfileImgSameHeightWidthWhenLoadingComplete(RelativeLayout relativeLayout, ImageView profile_imageView){

        ViewTreeObserver vto = relativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {

            profile_imageView.getLayoutParams().width = profile_imageView.getHeight();

            // set profile image width equal to height when layout is fully loaded

        });

    }

    private void retrieveChosenChoice(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        choice = sharedPreferences.getInt("Choicee", 0);

        // retrieve choice of whether or not user selected to register as host

    }

    private void setTypeface(){

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        subTitle_textView.setTypeface(tp);
        additional_info_textView.setTypeface(tp);
        log_in_textView.setTypeface(tp);
        register_textView.setTypeface(tp);
        error_textView.setTypeface(tp);

        // initialise and set typeface to all textViews in layout

    }

    private void getLayoutElementsOrigXpos(){

        subTitle_textView_org_pos_x = subTitle_textView.getX();
        additional_info_textView_org_pos_x = additional_info_textView.getX();
        register_layout_org_pos_x = register_layout.getX();
        log_in_layout_org_pos_x = log_in_layout.getX();

        // get original x positions of views for onRestart() animation

    }

    private void initArrays(){

        initViewsInLayoutArray();

        initFadeInViewsArray();

        initFirstToFinishViewsArray();

        initOrigPositionsArray();

    }

    private void initViewsInLayoutArray(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(subTitle_textView);
        viewsInLayout.add(additional_info_textView);
        viewsInLayout.add(register_layout);
        viewsInLayout.add(log_in_layout);

        // put all views that require the same animations in array list of layout views

    }

    private void initFadeInViewsArray(){

        fade_in_views = new ArrayList<>();

        fade_in_views.add(subTitle_textView);
        fade_in_views.add(additional_info_textView);
        fade_in_views.add(profile_imageView);

        // create array of views that fade in or out during activity transition animations

    }

    private void initFirstToFinishViewsArray(){

        firstToFinishViews = new ArrayList<>();

        firstToFinishViews.add(log_in_layout);
        firstToFinishViews.add(register_layout);

        // create array of views that change coordinates during activity transition animations

    }

    private void initOrigPositionsArray(){

        orig_positions = new ArrayList<>();

        orig_positions.add(log_in_layout_org_pos_x);
        orig_positions.add(register_layout_org_pos_x);

        // create array of float coordinates of views in firstToFinishViews array

    }

    private void getDataFromPreviousActivity(){

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 5);

        }

    }

    private void initUI(){

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        fab.hide();

    }

    private void deselectAllButtons(){

        log_in_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivityScreen3.this, R.color.main_background));
        register_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivityScreen3.this, R.color.main_background));

        // set background of both buttons to normal colors

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(false);

        // set both buttons as unselected

    }

    private void initUIbitmapNotNull(){

        if (bitmap != null){

            additional_info_textView.setText(getResources().getString(R.string.looking_good));
            fab.show();

            // if bitmap that is not null is processed into imageView, inform the user that they 'look handsome' today

        }

    }

    public void animateActivityTransition(){

        // copy layoutparams attributes of relativeLayout to a set of LayourParams independent from the real relativelayoutParams

        makeLayoutElementsInvisible();

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", metrics.widthPixels - log_in_layout_org_pos_x, log_in_layout_org_pos_x);
        anim.setStartDelay(100);
        anim.setDuration(400);

        anim.addListener(animatorListener);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(register_layout, "translationX", metrics.widthPixels - register_layout_org_pos_x, register_layout_org_pos_x);
        anim2.setStartDelay(100);
        anim2.setDuration(400);

        anim2.addListener(animatorListener);

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.visibility_fade_in_animation);

        subTitle_textView.startAnimation(fade_in);
        additional_info_textView.startAnimation(fade_in);
        profile_imageView.startAnimation(fade_in);

        anim.start();
        anim2.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);
    }

    private void makeLayoutElementsInvisible(){

        log_in_layout.setVisibility(View.INVISIBLE);
        register_layout.setVisibility(View.INVISIBLE);
        subTitle_textView.setVisibility(View.INVISIBLE);
        additional_info_textView.setVisibility(View.INVISIBLE);

    }

}
