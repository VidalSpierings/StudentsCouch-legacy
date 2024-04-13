package com.studentscouch.projectbostonfiles.app_back_end;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.RegisterActivtyScreen1;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivityMethods {

    static Intent intent;

    public static void checkIfAppNeedsUpdate(
            Context context, FrameLayout log_in_layout, FrameLayout register_layout, FloatingActionButton fab,
            RelativeLayout progress_layout, Animation exit_anim_progress_layout, DataSnapshot dataSnapshot, int entry,
            RelativeLayout progress_layout2, RelativeLayout progress_layout3, RelativeLayout main_layout) {

        enableLayoutInteractionIfOnline(
                context, log_in_layout, register_layout, fab,
                progress_layout, exit_anim_progress_layout);

        changeAppAccessibilityIfAppNeedsUpdate(
                dataSnapshot, entry, progress_layout, progress_layout2,
                progress_layout3, main_layout);

    }

    private static void changeAppAccessibilityIfAppNeedsUpdate(
            DataSnapshot dataSnapshot, int entry, RelativeLayout progress_layout,
            RelativeLayout progress_layout2, RelativeLayout progress_layout3,
            RelativeLayout main_layout){

        int accessibility = dataSnapshot.getValue(Integer.class);

        // retrieve value/info on whether or not app needs to be updated

        if (accessibility != entry) {

            changeAppAccessibilityAppNeedsUpdate(
                    progress_layout, progress_layout2, progress_layout3, main_layout);

        }

    }

    private static void enableLayoutInteractionIfOnline(
            Context context, FrameLayout log_in_layout, FrameLayout register_layout,
            FloatingActionButton fab, RelativeLayout progress_layout, Animation exit_anim_progress_layout){

        if (StartupMethods.isOnline(context)) {

            enableLayoutInteraction(log_in_layout, register_layout, fab);

            makeProgressLayoutDisappear(context, progress_layout, exit_anim_progress_layout);

        }

    }

    private static void changeAppAccessibilityAppNeedsUpdate(
            RelativeLayout progress_layout, RelativeLayout progress_layout2,
            RelativeLayout progress_layout3, RelativeLayout main_layout){

        progress_layout.removeAllViews();

        progress_layout2.removeAllViews();

        progress_layout3.setVisibility(View.VISIBLE);

        main_layout.removeAllViews();

        // if app needs to be updated, inform user by showing screen overlay and prevent user from interacting with base layout

    }

    private static void enableLayoutInteraction(FrameLayout log_in_layout, FrameLayout register_layout, FloatingActionButton fab){

        log_in_layout.setClickable(true);

        register_layout.setClickable(true);

        fab.setClickable(true);

        // make log in and register buttons and fab clickable

    }

    public static void makeProgressLayoutDisappear(Context context, RelativeLayout progress_layout, Animation exit_anim_progress_layout){

        progress_layout.clearAnimation();
        progress_layout.startAnimation(exit_anim_progress_layout);
        progress_layout.setVisibility(View.INVISIBLE);

        progress_layout.removeAllViews();

        progress_layout.setBackground(context.getResources().getDrawable(R.drawable.divider));

        // make loading screen disappear

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void setLayoutOnClickListener(final View header_image,
                                                 final TextView log_in_textView, final TextView register_textView, final TextView pb_textView,
                                                 final FrameLayout log_in_layout, final FrameLayout register_layout, final ViewGroup relativeLayout){

        relativeLayout.setOnTouchListener((v, event) -> {

            header_image.clearAnimation();
            log_in_textView.clearAnimation();
            register_textView.clearAnimation();
            pb_textView.clearAnimation();
            //  facebook_login_registration.clearAnimation();
            log_in_layout.clearAnimation();
            register_layout.clearAnimation();

            return true;
        });

    }

    public static void initialiseFloatingActionButtonOnClickListener(
            final FloatingActionButton fab, final Context context, final RelativeLayout progress_layout, final AppCompatActivity appCompatActivity,
            final FrameLayout register_layout, final FrameLayout log_in_layout, final View log_in_layout_view, final View register_layout_view, final Intent i,
            final TextView pb_textView){

        fab.setOnClickListener(v -> MainActivityMethods.floatingActionButtonOnClickListenerFunctionality(
                context, progress_layout, appCompatActivity, fab,
                register_layout, log_in_layout, log_in_layout_view, register_layout_view,
                i, pb_textView));

    }

    private static void floatingActionButtonOnClickListenerFunctionality(
            final Context context, RelativeLayout progress_layout, final AppCompatActivity appCompatActivity, FloatingActionButton fab,
            FrameLayout register_layout, FrameLayout log_in_layout, View log_in_layout_view, View register_layout_view, Intent i,
            TextView pb_textView) {

        initLayout(appCompatActivity, progress_layout, fab, register_layout, log_in_layout);

        intent = null;

        decideNextActivity(appCompatActivity, log_in_layout_view, register_layout_view);

        // open activity depending on chosen option

        animateActivityExitTransition(context, appCompatActivity, log_in_layout, register_layout, pb_textView);

        //animate views

    }

    private static void animateActivityExitTransition(
            Context context, AppCompatActivity appCompatActivity,
            FrameLayout log_in_layout, FrameLayout register_layout, TextView pb_textView){

        Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        moveObjects(context, appCompatActivity, log_in_layout, register_layout);

        //  facebook_login_registration.startAnimation(exit_anim);

        fadeOutObjects(pb_textView, fade_out);

    }

    private static void fadeOutObjects(TextView pb_textView, Animation fade_out){

        pb_textView.startAnimation(fade_out);
        pb_textView.setVisibility(View.INVISIBLE);

        //animate views

    }

    private static void setMoveObjectsCompletedListener(AppCompatActivity appCompatActivity, ObjectAnimator anim2){

        final Intent finalI = intent;
        anim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                startNextActivity(appCompatActivity, finalI);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private static void startNextActivity(AppCompatActivity appCompatActivity, Intent finalI){

        finalI.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        appCompatActivity.startActivity(finalI);

        //start activity after animation ends

    }

    private static void moveObjects(Context context, AppCompatActivity appCompatActivity, FrameLayout log_in_layout, FrameLayout register_layout){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(
                log_in_layout, "translationX",
                log_in_layout.getX(), -metrics.widthPixels - log_in_layout.getX());

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(
                register_layout, "translationX",
                register_layout.getX(), -metrics.widthPixels - register_layout.getX());

        // create animation objects

        anim.setDuration(400);
        anim2.setDuration(400);

        // set duration of animations

        //  facebook_login_registration.startAnimation(exit_anim);

        anim.start();
        anim2.start();

        // start activity animations

        setMoveObjectsCompletedListener(appCompatActivity, anim2);

    }

    private static void decideNextActivity(
            AppCompatActivity appCompatActivity, View log_in_layout_view, View register_layout_view){

        if (log_in_layout_view.isSelected()) {

            intent = new Intent(appCompatActivity, LoginActivity.class);

            // set 'LoginActivity' as next activity

        } else if (register_layout_view.isSelected()) {

            intent = new Intent(appCompatActivity, RegisterActivtyScreen1.class);

            // set 'RegisterActivityScreen1' as next activity

        }

    }

    private static void initLayout(
            AppCompatActivity appCompatActivity, RelativeLayout progress_layout,
            FloatingActionButton fab, FrameLayout register_layout, FrameLayout log_in_layout){

        progress_layout.setVisibility(View.INVISIBLE);

        appCompatActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //set notification bar color

        disableFab(fab);

        disableEditTextFields(register_layout, log_in_layout);

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void disableEditTextFields(FrameLayout register_layout, FrameLayout log_in_layout){

        register_layout.setOnTouchListener(null);
        log_in_layout.setOnTouchListener(null);

        //disable user from clicking buttons after fab is clicked

    }

    private static void disableFab(FloatingActionButton fab){

        fab.hide();

        fab.setClickable(false);

    }

    public static void initialiseRegisterButton(
            Context context, FloatingActionButton fab, View log_in_layout_view, View register_layout_view,
            RelativeLayout progress_layout, TextView error_textView, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {

            makeFabAppear(fab);

           showButtonClickAnimation(context, log_in_layout_view, register_layout_view, event);

            initLayoutIfUserOnline(context, progress_layout, error_textView);

        }

        selectDeselectAccButtonsRegSelec(log_in_layout_view, register_layout_view);

    }

    private static void selectDeselectAccButtonsRegSelec(View log_in_layout_view, View register_layout_view){

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(true);

        //register layout selected, log_in_layout deselected

    }

    private static void initLayoutIfUserOnline(Context context, RelativeLayout progress_layout, TextView error_textView){

        if (StartupMethods.isOnline(context)) {

            // if users' internet connection is reinstated after user having pressed the button, do the following

            progress_layout.setVisibility(View.INVISIBLE);

            error_textView.setText("");

            // make loading screen invisible and empty text in error_textView

        }

    }

    private static void showButtonClickAnimation(Context context, View log_in_layout_view, View register_layout_view, MotionEvent event){

        List<View> unSelectedView = new ArrayList<>();
        unSelectedView.add(log_in_layout_view);

        StartupMethods.startCircularRevealAnimationMultipleButtons(
                context, register_layout_view, unSelectedView, event);

    }

    private static void makeFabAppear(FloatingActionButton fab){

        fab.show();
        fab.clearAnimation();

    }

    public static void initialiseLogInButton(
            Context context, MotionEvent event, FloatingActionButton fab, View register_layout_view,
            View log_in_layout_view, RelativeLayout progress_layout, TextView error_textView) {

        if (event.getAction() == MotionEvent.ACTION_UP) {

            makeFabAppear(fab);

            showButtonClickAnimation2(
                    context, register_layout_view, log_in_layout_view, event);

            initLayoutIfUserOnline(context, progress_layout, error_textView);

        }

        selectDeselectAccButtonsLogSelec(register_layout_view, log_in_layout_view);

    }

    private static void selectDeselectAccButtonsLogSelec(View register_layout_view, View log_in_layout_view){

        register_layout_view.setSelected(false);
        log_in_layout_view.setSelected(true);

        // set register option to selected and log in option to deselected

    }

    private static void showButtonClickAnimation2(Context context, View register_layout_view, View log_in_layout_view, MotionEvent event){

        List<View> unSelected = new ArrayList<>();

        unSelected.add(register_layout_view);

        StartupMethods.startCircularRevealAnimationMultipleButtons(
                context, log_in_layout_view, unSelected, event);

        // start circular reveal animation for log in button reveal view

    }

    public static void initialiseFabOnClickListenerAppLocked(
            final Context context, final AppCompatActivity appCompatActivity,
            final FrameLayout log_in_layout, final FrameLayout register_layout, View log_in_layout_view, TextView error_textView,
            View register_layout_view, TextView pb_textView, Typeface tp) {

        //set notification bar color

        if (log_in_layout_view.isSelected()) {

            // if log in option is selected, do the following

            error_textView.setVisibility(View.VISIBLE);
            StartupMethods.showErrorMessageTemporarily(context, 2500, error_textView, context.getResources().getString(R.string.log_in_unavailable), tp);

            // inform user that the platform is not yet unlocked and log in is therefore unavailable, by showing a toast

        } else if (register_layout_view.isSelected()) {

            animateActivityExitTransition(context, appCompatActivity, log_in_layout, register_layout, pb_textView);

        }
    }

    public static void checkIfUnderConstruction(
            com.google.firebase.database.DataSnapshot dataSnapshot,
            RelativeLayout progress_layout, RelativeLayout progress_layout2, FloatingActionButton fab) {

        if (dataSnapshot.getValue(Integer.class) == 1) {

            // if app is under construction, do the following

            changeActivityFuncAppUnderConstruction(progress_layout, progress_layout2, fab);

        } else if (dataSnapshot.getValue(Integer.class) == 0) {

            // if app is not under construction, do the following

            changeAppFuncAppNotUnderConstruction(progress_layout, progress_layout2);

        }

    }

    private static void changeAppFuncAppNotUnderConstruction(RelativeLayout progress_layout, RelativeLayout progress_layout2){

        progress_layout.removeAllViews();

        progress_layout2.setVisibility(View.INVISIBLE);

        // empty out all views from loading screen and make 'Under construction' view invisible

    }

    private static void changeActivityFuncAppUnderConstruction(RelativeLayout progress_layout, RelativeLayout progress_layout2, FloatingActionButton fab){

        progress_layout.removeAllViews();

        progress_layout2.setVisibility(View.VISIBLE);

        fab.setOnClickListener(null);

        // make fab unclickable, empty out all views from loading screen, make 'under contruction' screen overlay visible

    }

    @SuppressLint("ClickableViewAccessibility")
    static void animateActivityEnterTransition(
            final Context context, final View header_image, Animation enter_anim,
            final TextView log_in_textView, final TextView register_textView, final AppCompatActivity appCompatActivity,
            final TextView pb_textView, final FrameLayout log_in_layout, final FrameLayout register_layout, List<View> viewsInLayout,
            RelativeLayout progress_layout, Animation enter_anim_progress_layout, FloatingActionButton fab, TextView error_textView,
            RelativeLayout relativeLayout, Typeface tp) {

        clearAnimationsOnAllLayoutElements(log_in_textView, register_textView, pb_textView, log_in_layout, register_layout);

        // clear animations for all objects in base layout

        fadeInLayoutElements(
                context, register_textView, log_in_textView,
                pb_textView, log_in_layout, register_layout);

        StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

        // make all views in base layout visible

        enableDisableLayoutInteractionAccToNetworkStatus(
                context, progress_layout, enter_anim_progress_layout,
                log_in_layout, register_layout, fab, error_textView, tp);

        setLayoutOnClickListener(
                header_image, log_in_textView, register_textView,
                pb_textView, log_in_layout, register_layout, relativeLayout);

        // if base layout is pressed while enter animations are still animating, make animations finish instantly

    }

    private static void enableDisableLayoutInteractionAccToNetworkStatus(
            Context context, RelativeLayout progress_layout, Animation enter_anim_progress_layout,
            FrameLayout log_in_layout, FrameLayout register_layout, FloatingActionButton fab,
            TextView error_textView, Typeface tp){

        if (StartupMethods.isOnline(context)) {

            // if user has active internet connection, do the following

            makeProgressLayoutVisible(progress_layout, enter_anim_progress_layout);

            enableLayoutInteraction2(log_in_layout, register_layout, fab);

        } else {

            disableLayoutInteraction(log_in_layout, register_layout, fab);

            showErrorMessageUserNotOnline(context, error_textView, tp);
        }

    }

    private static void showErrorMessageUserNotOnline(Context context, TextView error_textView, Typeface tp){

        error_textView.setVisibility(View.VISIBLE);
        StartupMethods.showErrorMessage(context,  error_textView, context.getResources().getString(R.string.you_need_to_be_online), tp);

        // inform user by toast that no active internet connection was found

    }

    private static void disableLayoutInteraction(
            FrameLayout log_in_layout,
            FrameLayout register_layout, FloatingActionButton fab){

        log_in_layout.setClickable(false);

        register_layout.setClickable(false);

        // make log in and register button unclickable

        fab.setClickable(false);

        // make log in and register buttons and fab clickable

    }

    private static void enableLayoutInteraction2(
            FrameLayout log_in_layout,
            FrameLayout register_layout, FloatingActionButton fab){

        log_in_layout.setClickable(true);

        register_layout.setClickable(true);

        fab.setClickable(true);

        // make log in and register buttons and fab clickable

    }

    private static void makeProgressLayoutVisible(RelativeLayout progress_layout, Animation enter_anim_progress_layout){

        progress_layout.startAnimation(enter_anim_progress_layout);

        progress_layout.setVisibility(View.VISIBLE);

        // make loading screen layout visible

    }

    private static void fadeInLayoutElements(
            Context context, TextView register_textView, TextView log_in_textView,
            TextView pb_textView, FrameLayout log_in_layout, FrameLayout register_layout){

        Animation enter_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        register_textView.startAnimation(enter_anim);
        log_in_textView.startAnimation(enter_anim);
        pb_textView.startAnimation(enter_anim);
        // facebook_login_registration.startAnimation(enter_anim);
        log_in_layout.startAnimation(enter_anim);
        register_layout.startAnimation(enter_anim);

        // make all views fade in according to newly created fade in 'Animation' object

    }

    private static void clearAnimationsOnAllLayoutElements(
            TextView log_in_textView, TextView register_textView,
            TextView pb_textView, FrameLayout log_in_layout, FrameLayout register_layout){

        log_in_textView.clearAnimation();
        register_textView.clearAnimation();
        //  facebook_login_registration.clearAnimation();
        pb_textView.clearAnimation();
        log_in_layout.clearAnimation();
        register_layout.clearAnimation();

        // clear animations for all objects in base layout

    }

    public static void animateActivityEnterTransitionFirstVisit (
            final Context context, final View header_image, final View layout_center, final Animation enter_anim,
            final TextView log_in_textView, final TextView register_textView, final AppCompatActivity appCompatActivity, final TextView pb_textView,
            final FrameLayout log_in_layout, final FrameLayout register_layout, final List<View> viewsInLayout, final RelativeLayout progress_layout,
            final Animation enter_anim_progress_layout, final FloatingActionButton fab, final TextView error_textView, final RelativeLayout relativeLayout2,
            final Typeface tp, RelativeLayout relativeLayout) {

        initHeaderImageAnimator(
                            context, header_image, layout_center, enter_anim,
                            log_in_textView, register_textView, appCompatActivity,
                            pb_textView, log_in_layout, register_layout, viewsInLayout,
                            progress_layout, enter_anim_progress_layout, fab,
                            error_textView, relativeLayout2, tp);

            setLayoutOnClickListener(
                    header_image, log_in_textView, register_textView,
                    pb_textView, log_in_layout, register_layout, relativeLayout);

            // if base layout is pressed while enter animations are still animating, make animations finish instantly

    }

    private static void initHeaderImageAnimator(final Context context, final View header_image, final View layout_center, final Animation enter_anim,
                                                final TextView log_in_textView, final TextView register_textView, final AppCompatActivity appCompatActivity, final TextView pb_textView,
                                                final FrameLayout log_in_layout, final FrameLayout register_layout, final List<View> viewsInLayout, final RelativeLayout progress_layout,
                                                final Animation enter_anim_progress_layout, final FloatingActionButton fab, final TextView error_textView, final RelativeLayout relativeLayout2,
                                                final Typeface tp){

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(header_image, "translationY", layout_center.getY(), header_image.getY());

        anim4.setDuration(850);

        // animation duration: 850 milliseconds

        anim4.start();

        // animate header image from original position of StartupActivity

        animateSecondEnterAnimUponHeaderAnimFin(
                context, header_image, enter_anim, log_in_textView,
                register_textView, appCompatActivity, pb_textView,
                log_in_layout, register_layout, viewsInLayout, progress_layout,
                enter_anim_progress_layout, fab, error_textView, relativeLayout2,
                tp, anim4);

    }

    private static void animateSecondEnterAnimUponHeaderAnimFin(final Context context, final View header_image, final Animation enter_anim,
                                                                final TextView log_in_textView, final TextView register_textView, final AppCompatActivity appCompatActivity, final TextView pb_textView,
                                                                final FrameLayout log_in_layout, final FrameLayout register_layout, final List<View> viewsInLayout, final RelativeLayout progress_layout,
                                                                final Animation enter_anim_progress_layout, final FloatingActionButton fab, final TextView error_textView, final RelativeLayout relativeLayout2,
                                                                final Typeface tp, ObjectAnimator anim4){

        anim4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                animateActivityEnterTransition(
                        context, header_image, enter_anim,
                        log_in_textView, register_textView, appCompatActivity, pb_textView,
                        log_in_layout, register_layout, viewsInLayout, progress_layout,
                        enter_anim_progress_layout, fab, error_textView, relativeLayout2,
                        tp);

                // fade in all views except for header image when header image / splashscreen animation is done animating

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

}
