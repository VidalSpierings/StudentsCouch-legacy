package com.studentscouch.projectbostonfiles.app_back_end;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen2;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivityScreen1Methods {

    private static ObjectAnimator anim3;
    private static ObjectAnimator anim4;
    static Intent i;
    private static Animator.AnimatorListener animatorListener;

    public static void initialiseFabOnClickListener(
            final Context context, final FloatingActionButton fab, final FrameLayout register_layout, final FrameLayout log_in_layout,
            final View log_in_layout_view, final View register_layout_view, final ObjectAnimator anim, final ObjectAnimator anim2,
            final TextView pb_textView, final Animation fade_in, final List<View> viewsInLayout,
            final float log_in_layout_orig_pos_x, final float register_layout_orig_pos_x

    ) {

        fab.setOnClickListener(v -> setFabOnClickFunctionality(
                context, fab, register_layout, log_in_layout,
                log_in_layout_view, register_layout_view, anim, anim2,
                pb_textView, fade_in, viewsInLayout,
                log_in_layout_orig_pos_x, register_layout_orig_pos_x));

    }

    private static void setFabOnClickFunctionality(
            final Context context, FloatingActionButton fab, FrameLayout register_layout, FrameLayout log_in_layout,
            View log_in_layout_view, View register_layout_view, ObjectAnimator anim, ObjectAnimator anim2,
            TextView pb_textView, Animation fade_in,
            final List<View> viewsInLayout, float log_in_layout_orig_pos_x, float register_layout_orig_pos_x
    ) {

        prepareAnimators(
                context, viewsInLayout, log_in_layout, register_layout,
                log_in_layout_orig_pos_x, register_layout_orig_pos_x);

        makeLayoutElementsDisappear(pb_textView, fade_in, fab);

        disableButtonClicks(fab, register_layout, log_in_layout);

        saveSelectedChoice(context, log_in_layout_view, register_layout_view);

        prepareIntent(context);

        animateActivityExitTransition(
                context, register_layout, log_in_layout,
                log_in_layout_view, register_layout_view, pb_textView);

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseYesButtonOnClickListener (
            final Context context, final View log_in_layout_view, final FloatingActionButton fab, final View register_layout_view,
            FrameLayout log_in_layout) {

        log_in_layout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP){

                makeFabAppear(fab);

                showYesButtonSelected(context, register_layout_view, log_in_layout_view, event);

            }

            yesSelectDeselectAccordingButtons(register_layout_view, log_in_layout_view);

            return true;
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseNoButtonOnClickListener (
            final Context context, FrameLayout register_layout, final FloatingActionButton fab, final View log_in_layout_view,
            final View register_layout_view) {

        register_layout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP){

                makeFabAppear(fab);

                showNoButtonSelected(context, log_in_layout_view, register_layout_view, event);

            }


            noSelectDeselectAccordingButtons(log_in_layout_view, register_layout_view);

            return true;
        });

    }

    private static void prepareAnimators(
            Context context, List<View> viewsInLayout, FrameLayout log_in_layout,
            FrameLayout register_layout, float log_in_layout_orig_pos_x, float register_layout_orig_pos_x){

        makeAllViewsVisibleOnAnimStart(viewsInLayout);

        initAnimsMovementData(context, log_in_layout, register_layout, log_in_layout_orig_pos_x, register_layout_orig_pos_x);

        setAnimsDuration();

        setAnimsStartDelay();

        addAnimListeners(animatorListener);

    }

    private static void makeAllViewsVisibleOnAnimStart(List<View> viewsInLayout){

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

                // make all views in layout visible

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

    }

    private static void addAnimListeners(Animator.AnimatorListener animatorListener){

        anim3.addListener(animatorListener);

        anim4.addListener(animatorListener);

        // add animation listeners

    }

    private static void setAnimsStartDelay(){

        anim3.setStartDelay(100);
        anim4.setStartDelay(100);

        // set start delay for animations at 100 milliseconds

    }

    private static void setAnimsDuration(){

        anim3.setDuration(400);
        anim4.setDuration(400);

        // set duration for animations at 400 milliseconds

    }

    private static void initAnimsMovementData(
            Context context, FrameLayout log_in_layout, FrameLayout register_layout,
            float log_in_layout_orig_pos_x, float register_layout_orig_pos_x){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        anim3 = ObjectAnimator.ofFloat(log_in_layout, "translationX",  metrics.widthPixels - log_in_layout_orig_pos_x, log_in_layout_orig_pos_x);
        anim4 = ObjectAnimator.ofFloat(register_layout, "translationX",  metrics.widthPixels - log_in_layout.getX(), register_layout_orig_pos_x);

        // create objectAnimator objects

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void disableButtonClicks(FloatingActionButton fab, FrameLayout register_layout, FrameLayout log_in_layout){

        fab.setClickable(false);

        register_layout.setOnTouchListener(null);
        log_in_layout.setOnTouchListener(null);

        // make log in and register buttons unclickable

    }

    private static void saveSelectedChoice(Context context, View log_in_layout_view, View register_layout_view){

        SharedPreferences sharedPreferences = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (log_in_layout_view.isSelected()){

            //fill in value with the help of SharedPreferences to later confirm in RegisterActivity3 what activity next to launch
            editor.putInt("Choicee", 1);

        } else if (register_layout_view.isSelected()){

            //fill in value with the help of SharedPreferences to later confirm in RegisterActivity3 what activity to launch next
            editor.putInt("Choicee", 2);
        }

        editor.apply();

    }

    private static void prepareIntent(Context context){

        i = new Intent(context, RegisterActivityScreen2.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private static void makeLayoutElementsDisappear(TextView pb_textView, Animation fade_in, FloatingActionButton fab){

        pb_textView.startAnimation(fade_in);

        fab.hide();

    }

    private static void animateActivityExitTransition(final Context context, FrameLayout register_layout, FrameLayout log_in_layout,
                                                      View log_in_layout_view, View register_layout_view,
                                                      TextView pb_textView){

        Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        StudentsCouchAnimations.exitAnimationRegisterActivityScreen1(
                context, register_layout, log_in_layout, log_in_layout_view,
                register_layout_view, i,
                pb_textView);

    }

    private static void makeFabAppear(FloatingActionButton fab){

        fab.show();
        fab.clearAnimation();

        // show fab

    }

    private static void showYesButtonSelected(Context context, View register_layout_view, View log_in_layout_view, MotionEvent event){

        List <View> unselectedView = new ArrayList<>();
        unselectedView.add(register_layout_view);

        StartupMethods.startCircularRevealAnimationMultipleButtons(context, log_in_layout_view, unselectedView, event);

        // register log_in_layout button click

    }

    private static void yesSelectDeselectAccordingButtons(View register_layout_view, View log_in_layout_view){

        register_layout_view.setSelected(false);
        log_in_layout_view.setSelected(true);

        // set log in button selected and set register button unselected

    }

    private static void showNoButtonSelected(Context context, View log_in_layout_view, View register_layout_view, MotionEvent event){

        List <View> unselectedView = new ArrayList<>();
        unselectedView.add(log_in_layout_view);

        StartupMethods.startCircularRevealAnimationMultipleButtons(context, register_layout_view, unselectedView, event);

        // register register_layout button click

    }

    private static void noSelectDeselectAccordingButtons(View log_in_layout_view, View register_layout_view){

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(true);

        // make log in button unselected and make register button selected

    }

}
