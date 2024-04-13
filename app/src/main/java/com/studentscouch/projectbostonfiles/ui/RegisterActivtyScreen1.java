package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivitiesMethods;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityScreen1Methods;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivtyScreen1 extends AppCompatActivity {

    private TextView
            pb_textView,
            log_in_textView,
            register_textView;

    private FloatingActionButton fab;

    View header_image;

    ViewGroup relativeLayout;

    private List<View> viewsInLayout;

    private ObjectAnimator
            anim,
            anim2;

    private FrameLayout
            log_in_layout,
            register_layout;

    private View
            register_layout_view,
            log_in_layout_view;

    View screen_center;

    View layout_center;

    private Float
            log_in_layout_orig_pos_x,
            register_layout_orig_pos_x;

    Float pb_textView_orig_pos_x;

    private Intent i;

    List<View> animatingViews;

    List<Float> orig_pos;

    List<View> fadeInViews;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activty_screen1);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        retrieveLayoutElementsOrigXpos();

        initArrays();

        initUI();

        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_in_animation);

        // load fade in animation

        StartupMethods.enterAnimationTwoTextButtons(
                getApplicationContext(), viewsInLayout, fadeInViews, 400,
                100, orig_pos, animatingViews, null);

        RegisterActivityScreen1Methods.initialiseFabOnClickListener(
                getApplicationContext(), fab, register_layout, log_in_layout,
                log_in_layout_view, register_layout_view, anim,
                anim2, pb_textView, fade_in,
                viewsInLayout, log_in_layout_orig_pos_x, register_layout_orig_pos_x);

        /*

        * initialise fab onClickListener. fab click starts and animates user to next activity,
        * passes choice for registering or not registering to next activity

        */

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {

        initFab();

        RegisterActivityScreen1Methods.initialiseYesButtonOnClickListener(
                getApplicationContext(), log_in_layout_view, fab, register_layout_view, log_in_layout);

        // initialise yes button onClickListener

        RegisterActivityScreen1Methods.initialiseNoButtonOnClickListener(
                getApplicationContext(), register_layout, fab, log_in_layout_view,
                register_layout_view);

        // initialise no button onClickListener

        unSelectAllButtons();

        RegisterActivitiesMethods.retrievePreviouslySelectedOptionRegisterScreen1(getApplicationContext(), log_in_layout_view, register_layout_view, fab);

        // if user re-navigates to this activity from proceeding activities, show previously selected option for whether or not to register as a host

        super.onStart();
    }

    public void onRestart() {

        super.onRestart();

        StudentsCouchAnimations.onRestartAnimations1(
                getApplicationContext(), log_in_layout, register_layout, log_in_layout_orig_pos_x, register_layout_orig_pos_x,
                null, null, pb_textView);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);

    }

    private void linkXmlElementsToJavaVariables(){

        fab = findViewById(R.id.fab);

        log_in_textView = findViewById(R.id.log_in_textView);
        register_textView = findViewById(R.id.register_textView);
        pb_textView = findViewById(R.id.subTitle_textView);


        header_image = findViewById(R.id.header_image);

        layout_center = findViewById(R.id.layout_center);

        log_in_layout = findViewById(R.id.log_in_layout);
        register_layout = findViewById(R.id.register_layout);

        screen_center = findViewById(R.id.screen_center);

        register_layout_view = findViewById(R.id.register_layout_view);
        log_in_layout_view = findViewById(R.id.log_in_layout_view);

        relativeLayout = findViewById(R.id.relativeLayout);

        // link xml layout elements with java variables

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");
        log_in_textView.setTypeface(tp);
        register_textView.setTypeface(tp);
        pb_textView.setTypeface(tp);

        // assign typefaces to all textViews

    }

    private void initArrays(){

        initViewsInLayoutArrays();

        initFadeInViewsArray();

        initAnimatingViewsArray();

        initOrigPosArray();

    }

    private void initViewsInLayoutArrays(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(register_textView);
        viewsInLayout.add(log_in_textView);
        viewsInLayout.add(pb_textView);
        viewsInLayout.add(log_in_layout_view);
        viewsInLayout.add(register_layout_view);
        viewsInLayout.add(log_in_layout);
        viewsInLayout.add(register_layout);

        // add all textViews to an array containing all textViews

    }

    private void initFadeInViewsArray(){

        fadeInViews = new ArrayList<>();

        fadeInViews.add(pb_textView);

    }

    private void initAnimatingViewsArray(){

        animatingViews = new ArrayList<>();

        animatingViews.add(log_in_layout);
        animatingViews.add(register_layout);

    }

    private void initOrigPosArray(){

        orig_pos = new ArrayList<>();

        orig_pos.add(log_in_layout_orig_pos_x);
        orig_pos.add(register_layout_orig_pos_x);

    }

    private void retrieveLayoutElementsOrigXpos(){

        log_in_layout_orig_pos_x = log_in_layout.getX();
        register_layout_orig_pos_x = register_layout.getX();
        pb_textView_orig_pos_x = pb_textView.getX();

        // get current x coordinates of editTexts and title textView

    }

    private void initUI(){

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        // male all views in layout invisible

        log_in_layout.setVisibility(View.INVISIBLE);
        register_layout.setVisibility(View.INVISIBLE);

        // make both log in layout and register layout invisible

        fab.hide();

    }

    private void unSelectAllButtons(){

        log_in_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivtyScreen1.this, R.color.main_background));
        register_layout_view.setBackgroundColor(ContextCompat.getColor(RegisterActivtyScreen1.this, R.color.main_background));

        // give both log in button and register button unselected background color

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(false);

        // make both log in button and register button unselected

    }

    private void initFab(){

        fab.setClickable(true);

        fab.clearAnimation();
        fab.hide();

        // hide fab

    }

}

