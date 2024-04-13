package com.studentscouch.projectbostonfiles;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogNoInternetConnection;
import com.studentscouch.projectbostonfiles.viewpagers_and_corresponding_files.CustomSwipeLayout;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by inter on 6-12-2016.
 */

public class StartupMethods {

    public static int MAX_NUM_CHARACTERS = 200;
    public static int MAX_NUM_CHARACTERS_MINUS_ONE = MAX_NUM_CHARACTERS - 1;
    public static String AMS_PLACE_ID = "ChIJVXealLU_xkcRja_At0z9AGY";
    public static String ROTTERDAM_PLACE_ID = "ChIJVXealLU_xkcRja_At0z9AGY";
    //public static String BRUSSELS_PLACE_ID = "ChIJZ2jHc-2kw0cRpwJzeGY6i8E";
    //public static int NUM_NIGHTS_N_A = 123456789;
    public static int IMAGE_RESIZE_RATIO = 500;

    public static String DATABASE_LINK = "https://studentscouch-d43a5.firebaseio.com/";

    //public static String Y_AXIS = "translationY";
    public static String X_AXIS = "translationX";

    private static ObjectAnimator anim;
    private static ObjectAnimator anim2;

    private static Bitmap[] image_resources;

    private static ObjectAnimator anim3;

    private static InputFilter filter;

    //methods required when starting up an activity


    // ------* Desc for what activities equal what numbers fromActivity variables

    /*

    * ApartmentActivity
    *
    * 0 = ApartmentEditActivity
    *
    * 1 = Any activity other than ApartmentEditActivity

    */

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static void changeVisibility(List<View> viewsInLayout, int visibility){

        for(View v: viewsInLayout){
            v.setVisibility(visibility);
        }

        //make all views from viewsInLayout parameter invisible to prepare for start of activty enter animation

    }

    public static void showErrorMessage(Context context, TextView error_message, String text, Typeface tp){

        /*

        * method for showing error messages in pre-logged-in environment

        * enter animation: fade in
        * animation duration: 300 milliseconds
        * text color: red (#ff0000)
        * typeface: application typeface (Century Gothic)

        */

        Animation enter_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        enter_anim.setDuration(300);

        error_message.setTypeface(tp);
        error_message.setText(text);
        error_message.setTextColor(context.getResources().getColor(R.color.error_message_color));

        error_message.startAnimation(enter_anim);
        error_message.setVisibility(View.VISIBLE);

        // make error view visible and start enter animation

    }

    public static void showErrorMessageTemporarily(final Context context, int duration, final TextView error_message, String text, Typeface tp){

        /*

         * method for showing error messages in pre-logged-in environment temporarily

         * enter/exit animation: fade in / fade out
         * animation duration: custom
         * text color: red (#ff0000)
         * typeface: application typeface (Century Gothic)

         */

        final Animation enter_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);
        final Animation exit_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        // create animation objects

        enter_anim.setDuration(300);

        // set animation duration

       initErrorTextView(error_message, tp, text, enter_anim);

        keepErrorTextVisibleForSetMilliSecs(error_message, exit_anim, duration);

    }

    private static void initErrorTextView(TextView error_message, Typeface tp, String text, Animation enter_anim){

        error_message.setTypeface(tp);

        error_message.setText(text);

        // set typeface and text

        error_message.startAnimation(enter_anim);
        error_message.setVisibility(View.VISIBLE);

        // make error view visible and start enter animation

    }

    private static void keepErrorTextVisibleForSetMilliSecs(TextView error_message, Animation exit_anim, int duration){

        Handler handler = new Handler();

        handler.postDelayed(() -> {

            error_message.startAnimation(exit_anim);
            error_message.setVisibility(View.INVISIBLE);

            // make error view invisible and start exit animation

        },
                duration);

        // use Handler class as a timer to make the error view visible for the duration that is specified to the 'duration' parameter

    }

    public static void buttonSelected(Context context, View selectedView, View deSelectedView, MotionEvent event){

        // method instantiated when user clicks a bottom layout button

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            visuallySelectedDeselectAccButtons(context, selectedView, deSelectedView);

            showButtonSelectedRevealAnim(selectedView, event);


        } else {

            /*

             * if device software version is lower than Lollipop,
             * make background of reveal view of selected view dark
             * and make the other reveal view the same color
             * as the upper layer of the button layout

             */

            selectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
            deSelectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

            // give selected reveal view selected color background and give unselected reveal view selected color background

        }

        selectDeselectAccButtons(selectedView, deSelectedView);

    }

    private static void selectDeselectAccButtons(View selectedView, View deSelectedView){

        deSelectedView.setSelected(false);
        selectedView.setSelected(true);

        // select the selected view and deselect the other view

    }

    private static void visuallySelectedDeselectAccButtons(Context context, View selectedView, View deSelectedView){

        selectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
        deSelectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

        // set darker background color of reveal view of selected view and set all other views to the unpressed color

        deSelectedView.setVisibility(View.INVISIBLE);
        selectedView.setVisibility(View.VISIBLE);

        // make selected reveal view visible and make the other reveal view invisible

    }

    private static void showButtonSelectedRevealAnim(View selectedView, MotionEvent event){

        int x = selectedView.getWidth();
        int y = selectedView.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        /*

         * get width and height of view item and set endRadius of reveal animation
         * to each set of boundaries of the axes respectively, in turn allowing the reveal
         * animation to take up the whole view item

         */

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(selectedView, (int) event.getX(), (int) event.getY(), 0, endRadius);

        /*

         * load circular reveal onClick animation.
         * starting point of circular expansion
         * are at the coordinates where the user has touched the screen
         * (hence 'event.getX() / event.getY()')

         */

        circularReveal.start();

    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    private static final String ALLOWED_CHARACTERS ="0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";

    public static String getRandomString(final int sizeOfRandomString)

    {


        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        // select letter at random index for the given length of the string

        return sb.toString();
    }



    public static Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);

            // convert string object to byte array object

            // convert byte array object to bitmap object

            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }



    public static boolean isOnline(Context c) {

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

        // return whether netinfo is non-existent or is connected or is connecting

    }

    public static void openNewInternetConnectionLostDialog (AppCompatActivity a){

        CustomDialogNoInternetConnection customDialogNoInternetConnection = new CustomDialogNoInternetConnection();

        customDialogNoInternetConnection.show(a.getSupportFragmentManager(), "test");

    }

    public static void enterAnimationTwoTextButtons (
            Context context, final List<View> viewsInLayout, List<View> fadeInViews, long durationAnimations, long animationsStartDelay,
            List<Float> orig_pos_buttons_float, List<View> buttons, FrameLayout singleButton){

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        Animation fade_in = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.visibility_fade_in_animation);

        makeAccViewsVisible(context, buttons,
                orig_pos_buttons_float, viewsInLayout,
                durationAnimations, animationsStartDelay);



        animateViewMovement(context, singleButton, durationAnimations, animationsStartDelay, orig_pos_buttons_float);

        makeAccViewsFadeIn(fadeInViews, fade_in);

    }

    private static void makeAccViewsFadeIn(List<View> fadeInViews, Animation fade_in){

        if (!(fadeInViews == null)) {

            for (View fadeInView: fadeInViews){

                fadeInView.startAnimation(fade_in);

            }

            for (int pos = 0; pos < fadeInViews.size(); pos++) {

                fadeInViews.get(pos).startAnimation(fade_in);

            }

        }

    }

    private static void animateViewMovement(Context context, FrameLayout singleButton, long durationAnimations, long animationsStartDelay, List<Float> orig_pos_buttons_float){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        if (!(singleButton == null)) {

            ObjectAnimator anim = ObjectAnimator.ofFloat(singleButton, "translationX", metrics.widthPixels - orig_pos_buttons_float.get(0), orig_pos_buttons_float.get(0));

            // ObjectAnimator object for button layout at index. Object moves from east to west, starting point of layout is offscreen in eastern coordinates

            anim.setDuration(durationAnimations);

            // set duration of animation at index to 400 milliseconds

            anim.setStartDelay(animationsStartDelay);

            // set delay for animation at index to start at 100 milliseconds for each object respectively

            anim.start();

            // start animation at index

        }

    }

    private static void makeAccViewsVisible(Context context, List<View> buttons,
                                            List<Float> orig_pos_buttons_float, List<View> viewsInLayout,
                                            long durationAnimations, long animationsStartDelay){

        if (!(buttons == null)) {

            for (int pos = 0; pos < buttons.size(); pos++) {

                makeAccViewsVisibleFunc(
                        context, pos, buttons,
                        orig_pos_buttons_float, viewsInLayout,
                        durationAnimations, animationsStartDelay);

            }

        }

    }

    private static void makeAccViewsVisibleFunc(
            Context context, int pos, List<View> buttons,
            List<Float> orig_pos_buttons_float, List<View> viewsInLayout,
            long durationAnimations, long animationsStartDelay){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(buttons.get(pos), "translationX", metrics.widthPixels - orig_pos_buttons_float.get(pos), orig_pos_buttons_float.get(pos));

        // ObjectAnimator object for button layout at index. Object moves from east to west, starting point of layout is offscreen in eastern coordinates

        anim.setDuration(durationAnimations);

        // set duration of animation at index to 400 milliseconds

        anim.setStartDelay(animationsStartDelay);

        // set delay for animation at index to start at 100 milliseconds for each object respectively

        startAnim(anim, viewsInLayout);

    }

    private static void startAnim(ObjectAnimator anim, List<View> viewsInLayout){

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

                // above method prevents views from being visible for a split second when activity starts

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
        });

        anim.start();

        // start animation at index

    }

    public static void exitAnimationMultipleAnimators(final Context context, final Intent i, List<View> animatingViews, List<Float> views_org_pos, String translation){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            animateSingleView(
                    context, pos, animatingViews,
                    translation, metrics, views_org_pos, i);

        }

        // set animation duration at 400 milliseconds

        // activity exit animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    private static void animateSingleView(
            Context context, int pos, List<View> animatingViews,
            String translation, DisplayMetrics metrics,
            List<Float> views_org_pos, Intent i){


        animatingViews.get(pos).setVisibility(View.INVISIBLE);

        initAnim(translation, pos, metrics, animatingViews, views_org_pos);

        setAnimsEndListener(context, pos, i);

        anim.start();

        animatingViews.get(pos).setVisibility(View.VISIBLE);
    }

    private static void setAnimsEndListener(Context context, int pos, Intent i){

        if (pos == 0) {

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    startNextActivity(context, i);

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

    private static void startNextActivity(Context context, Intent i){

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);

        // launch messaging activity

    }

    private static void initAnim(
            String translation, int pos, DisplayMetrics metrics,
            List<View> animatingViews, List<Float> views_org_pos){

        anim = null;

        if (translation.equals("translationY")) {

            anim = ObjectAnimator.ofFloat(animatingViews.get(pos), translation, views_org_pos.get(pos), -metrics.heightPixels - views_org_pos.get(pos));

        }

        if (translation.equals("translationX"))

        {

            anim = ObjectAnimator.ofFloat(animatingViews.get(pos), translation, views_org_pos.get(pos), -metrics.widthPixels - views_org_pos.get(pos));

        }

        Objects.requireNonNull(anim).setDuration(400);

    }

    public static void exitAnimation(
            final Context context, final Intent i, List<View> animatingViews,
            List<Float> views_org_pos, List<View> fade_out_views, String translation){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            initSingleView2(
                    context, pos, animatingViews,
                    translation, views_org_pos,
                    metrics, i);

        }

        fadeOutAccViews(context, fade_out_views);

    }

    private static void fadeOutAccViews(Context context, List<View> fade_out_views){

        if (!(fade_out_views == null)) {

            for (int pos = 0; pos < fade_out_views.size(); pos++) {

                Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

                fade_out_views.get(pos).startAnimation(fade_out);

            }

        }

        // set animation duration at 400 milliseconds

        // activity exit animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    private static void initSingleView2(
            Context context, int pos, List<View> animatingViews,
            String translation, List<Float> views_org_pos,
            DisplayMetrics metrics, Intent i){

        animatingViews.get(pos).setVisibility(View.INVISIBLE);

        initAnims2();

        changeMovementDirectionAccToAxis2(translation, pos, animatingViews, views_org_pos, metrics);

        setAnimsEndListener2(context, pos, i);

        anim2.start();

        animatingViews.get(pos).setVisibility(View.VISIBLE);

    }

    private static void initAnims2(){

        anim2 = null;

        anim2.setDuration(400);

    }

    private static void setAnimsEndListener2(Context context, int pos, Intent i){

        if (pos == 0) {

            anim2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    context.startActivity(i);

                    // launch messaging activity

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

    private static void changeMovementDirectionAccToAxis2(String translation, int pos, List<View> animatingViews, List<Float> views_org_pos, DisplayMetrics metrics){

        if (translation.equals("translationY")) {

            anim2 = ObjectAnimator.ofFloat(animatingViews.get(pos), translation, views_org_pos.get(pos), metrics.heightPixels - views_org_pos.get(pos));

        }

        if (translation.equals("translationX")) {

            anim2 = ObjectAnimator.ofFloat(animatingViews.get(pos), translation, views_org_pos.get(pos), metrics.widthPixels - views_org_pos.get(pos));

        }

    }

    public static void startCircularRevealAnimationDefaultStartRadius (Context context, final View revealView, boolean addBackgroundColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //if build version is equal to or higher than Lollipop, use circular reveal animation.

            showViewClickedAnimation(context, revealView, addBackgroundColor);

        } else {

            showViewClickedNoAnimation(context, revealView, addBackgroundColor);

        }

    }

    private static void showViewClickedNoAnimation(Context context, View revealView, boolean addBackgroundColor){

        revealView.setVisibility(View.VISIBLE);

        if (addBackgroundColor) {

            revealView.setBackgroundColor(context.getResources().getColor(R.color.introduction_button_clicked));

        }

        // if device version is lower than Lollipop, make add button reveal view visible

    }

    private static void showViewClickedAnimation(Context context, View revealView, boolean addBackgroundColor){

        int x = revealView.getWidth();
        int y = revealView.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        /*

         * get width and height of view item and set endRadius of reveal animation
         * to each set of boundaries of the axes respectively, in turn allowing the reveal
         * animation to take up the whole view item

         */

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(revealView, 0, 0, 0, endRadius);

        /*

         * load circular reveal onClick animation.
         * starting point of circular expansion
         * are at the coordinates where the user has touched the screen
         * (hence 'event.getX() / event.getY()')

         */

        makeRevealViewVisible(context, revealView, addBackgroundColor);

        circularReveal.start();

    }

    private static void makeRevealViewVisible(Context context, View revealView, boolean addBackgroundColor){

        revealView.setVisibility(View.VISIBLE);

        if (addBackgroundColor) {

            revealView.setBackgroundColor(context.getResources().getColor(R.color.introduction_button_clicked));

        }

    }

    public static void startCircularRevealAnimationCustomStartRadius (Context context, View revealView, MotionEvent event, boolean addBackground) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //if build version is equal to or higher than Lollipop, use circular reveal animation.

            showViewClickedAnimationCustomStartRadius(context, addBackground, revealView, event);

        } else {

            showViewClickedNoAnimation(context, revealView, addBackground);

        }

    }

    private static void showViewClickedAnimationCustomStartRadius(Context context, boolean addBackground, View revealView, MotionEvent event){

        int x = revealView.getWidth();
        int y = revealView.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        /*

         * get width and height of view item and set endRadius of reveal animation
         * to each set of boundaries of the axes respectively, in turn allowing the reveal
         * animation to take up the whole view item

         */

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(revealView, (int) event.getX(), (int) event.getY(), 0, endRadius);

        circularReveal.start();

        /*

         * load circular reveal onClick animation.
         * starting point of circular expansion
         * are at the coordinates where the user has touched the screen
         * (hence 'event.getX() / event.getY()')

         */

        makeRevealViewVisible(context, revealView, addBackground);

    }

    public static void startCircularRevealAnimationUserRStayEndedDialog (
            Context context, View revealView, View hidingView,
            MotionEvent event, View selectedView, View unSelectedView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //if build version is equal to or higher than Lollipop, use circular reveal animation.

            showViewClickedAnimationCustomStartRadius2(revealView, event);

        }

        selectDeselectAccViews(context, revealView, hidingView, selectedView, unSelectedView);

    }

    private static void selectDeselectAccViews(Context context, View revealView, View hidingView, View selectedView, View unSelectedView){

        visuallySelectDeselectViews(context, revealView, hidingView);

        internallySelectDeselectButtons(selectedView, unSelectedView);

    }

    private static void internallySelectDeselectButtons(View selectedView, View unSelectedView){

        selectedView.setSelected(true);
        unSelectedView.setSelected(false);

    }

    private static void visuallySelectDeselectViews(Context context, View revealView, View hidingView){

        revealView.setBackgroundColor(context.getResources().getColor(R.color.half_transparent_color));

        hidingView.setBackgroundColor(context.getResources().getColor(R.color.transparent_main_background));

        // add background color to reveal view if addBackground boolean parameter is true

        revealView.setVisibility(View.VISIBLE);

        hidingView.setVisibility(View.INVISIBLE);

    }

    private static void showViewClickedAnimationCustomStartRadius2(View revealView, MotionEvent event){

        int x = revealView.getWidth();
        int y = revealView.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        /*

         * get width and height of view item and set endRadius of reveal animation
         * to each set of boundaries of the axes respectively, in turn allowing the reveal
         * animation to take up the whole view item

         */

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(revealView, (int) event.getX(), (int) event.getY(), 0, endRadius);
        circularReveal.start();

            /*

             * load circular reveal onClick animation.
             * starting point of circular expansion
             * are at the coordinates where the user has touched the screen
             * (hence 'event.getX() / event.getY()')
             *
            */

    }

    public static void startCircularRevealAnimationMultipleButtons (Context context, View revealView, List<View> unselectedViews, MotionEvent event) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            showButtonClickedAnimation3(context, revealView, event);

            makeDeselectedButtonsInvisible(context, unselectedViews);

        } else {

            makeSelectedViewVisible(context, revealView);

            makeDeselectedButtonsInvisible(context, unselectedViews);

        }

        internallySelectDeselectAccButtons(revealView, unselectedViews);

    }

    private static void internallySelectDeselectAccButtons(View revealView, List<View> unselectedViews){

        revealView.setSelected(true);

        deselectButtons(unselectedViews);

    }

    private static void deselectButtons(List<View> unselectedViews){

        for (int pos = 0; pos < unselectedViews.size(); pos++) {

            unselectedViews.get(pos).setSelected(false);

        }

    }

    private static void makeDeselectedButtonsInvisible(Context context, List<View> unselectedViews){

        for (int pos = 0; pos < unselectedViews.size(); pos++) {

            unselectedViews.get(pos).setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

            // set background color to unselected color

            unselectedViews.get(pos).setVisibility(View.INVISIBLE);

            // make view invisible

        }

        /*

         * start the circular reveal animation,
         * this animation smoothly fills the view item with a darker colour
         * using a circular expansion starting from the coordinates at which the user has touched the screen,
         * in turn giving off an impression of having pressed a button

         */

    }

    private static void showButtonClickedAnimation3(Context context, View revealView, MotionEvent event){

        int x = revealView.getWidth();
        int y = revealView.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        /*

         * get width and height of view item and set endRadius of reveal animation
         * to each set of boundaries of the axes respectively, in turn allowing the reveal
         * animation to take up the whole view item

         */

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(revealView, (int) event.getX(), (int) event.getY(), 0, endRadius);

        /*

         * load circular reveal onClick animation.
         * starting point of circular expansion
         * are at the coordinates where the user has touched the screen
         * (hence 'event.getX() / event.getY()')

         */

        makeSelectedViewVisible(context, revealView);

        circularReveal.start();

    }

    private static void makeSelectedViewVisible(Context context, View revealView){

        revealView.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));

        revealView.setVisibility(View.VISIBLE);

    }

    public static void initialiseSwipeDotsLayout(
            final Context context, String base64StringImage1, String base64StringImage2, String base64StringImage3,
            ViewPager viewPager,
            LinearLayout slideDotsPanel
    ) {

        attemptInitImgsArray(base64StringImage1, base64StringImage2, base64StringImage3);

        CustomSwipeLayout customSwipeLayout = new CustomSwipeLayout(context.getApplicationContext(), image_resources);
        viewPager.setAdapter(customSwipeLayout);

        // initialise slideDotsPanel viewPager

        int dotsCount = customSwipeLayout.getCount();
        ImageView[] dots = new ImageView[dotsCount];

        reInitSlideDotsPanel(context, customSwipeLayout, dots, dotsCount, viewPager, slideDotsPanel);

    }

    private static void reInitSlideDotsPanel(final Context context,
                                             CustomSwipeLayout customSwipeLayout, ImageView[] dots, int dotsCount, ViewPager viewPager,
                                             LinearLayout slideDotsPanel){

        changeSlideDotsPanelOneImgPresent(context, customSwipeLayout, dotsCount, dots, slideDotsPanel);

        changeSelectedDotLocOnNewPageSelec(context, dotsCount, dots, viewPager);

    }

    private static void changeSelectedDotLocOnNewPageSelec(Context context, int dotsCount, ImageView[] dots, ViewPager viewPager){

        final int finalDotsCount = dotsCount;
        final ImageView[] finalDots = dots;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                attemptVisuallySelectPage(context, finalDots, finalDotsCount, position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private static void changeSlideDotsPanelOneImgPresent(
            Context context, CustomSwipeLayout customSwipeLayout,
            int dotsCount, ImageView[] dots,
            LinearLayout slideDotsPanel){

        if (customSwipeLayout.getCount() != 1) {

            for (int i = 0; i < dotsCount; i++){

                initAllDots(context, i, dots, slideDotsPanel);

            }

        }

    }

    private static void initAllDots(Context context, int i, ImageView[] dots, LinearLayout slideDotsPanel){

        dots[i] = new ImageView(context.getApplicationContext());
        dots[i].setImageResource(R.drawable.circle_outline_transparent);

        // create new dot

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

        // set dot dimensions to 20sp by 20sp

        params.setMargins(8, 0, 8, 0);

        // set dot margins to 8sp from the left and 8sp from the right

        slideDotsPanel.addView(dots[i], params);

        // add view to slideDotsPanel

        dots[0].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.circle_selected));

        // set dot of index of current image to selected, fill circle with gray color

    }

    private static void attemptInitImgsArray(String base64StringImage1, String base64StringImage2, String base64StringImage3){

        Bitmap firstImage = StartupMethods.StringToBitMap(base64StringImage1);

        // convert strings of retrieved images to Bitmap objects

        image_resources = null;

        // reset image resources array from data assigned from previous usage

        image_resources = new Bitmap[]{firstImage};

        // if one apartment image was received, insert one image into Bitmap array object

        attemptInitImgsArrayMultipleImgs(firstImage, base64StringImage2, base64StringImage3);

    }

    private static void attemptInitImgsArrayMultipleImgs(Bitmap firstImage, String base64StringImage2, String base64StringImage3){

        attemptInitArrayTwoImgs(base64StringImage2, firstImage);

        attemptInitArrayThreeImgs(firstImage, base64StringImage2, base64StringImage3);

    }

    private static void attemptInitArrayThreeImgs(Bitmap firstImage, String base64StringImage2, String base64StringImage3){

        try {

            if (!base64StringImage3.equals("")) {

                Bitmap secondImage = StartupMethods.StringToBitMap(base64StringImage2);
                Bitmap thirdImage = StartupMethods.StringToBitMap(base64StringImage3);

                image_resources = new Bitmap[]{firstImage, secondImage, thirdImage};

                // if three apartment images were received, insert three images into Bitmap array object

            }

        } catch (Exception e) {

            // Do nothing

        }

        // insert bitmap objects into bitmap array

    }

    private static void attemptInitArrayTwoImgs(String base64StringImage2, Bitmap firstImage){

        try {

            if (!base64StringImage2.equals("")) {

                Bitmap secondImage = StartupMethods.StringToBitMap(base64StringImage2);

                image_resources = new Bitmap[]{firstImage, secondImage};

                // if two apartment images were received, insert two images into Bitmap array object

            }

        } catch (Exception e) {

            // Do nothing

        }

    }

    private static void attemptVisuallySelectPage(Context context, ImageView[] finalDots, int finalDotsCount, int position){

        try {

            selectPage(context, finalDotsCount, position, finalDots);

        } catch (Exception e){

            // DO nothing

        }

    }

    private static void selectPage(Context context, int finalDotsCount, int position, ImageView[] finalDots){

        for(int i = 0; i < finalDotsCount; i++){

            finalDots[i].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.circle_outline_transparent));

            // set all dots to circle outline view

        }

        finalDots[position].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.circle_selected));

        // set dot at index of current image to

    }

    public static void enterAnimationNoButtons(
            Context context, List<View> viewsInLayout, List<View> fadeInViews, long durationAnimations, long animationsStartDelay,
            List<Float> orig_pos_buttons_float, List<View> firstToFinishAnimViews,
            Animator.AnimatorListener animatorListener){

        Animation fade_in = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.visibility_fade_in_animation);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        animateAllViews(
                firstToFinishAnimViews, metrics, orig_pos_buttons_float,
                durationAnimations, animationsStartDelay, animatorListener,
                fadeInViews, fade_in);

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

    }

    private static void animateAllViews(
            List<View> firstToFinishAnimViews, DisplayMetrics metrics,
            List<Float> orig_pos_buttons_float, long durationAnimations,
            long animationsStartDelay, Animator.AnimatorListener animatorListener,
            List<View> fadeInViews, Animation fade_in){

        animateViewsMovement(
                firstToFinishAnimViews, metrics, orig_pos_buttons_float,
                durationAnimations, animationsStartDelay,
                animatorListener);

        fadeInViews(fadeInViews, fade_in);

    }

    private static void fadeInViews(List<View> fadeInViews, Animation fade_in){

        for (View fadeInView: fadeInViews){

            fadeInView.startAnimation(fade_in);

        }

    }

    private static void animateViewsMovement(List<View> firstToFinishAnimViews, DisplayMetrics metrics,
                                             List<Float> orig_pos_buttons_float, long durationAnimations,
                                             long animationsStartDelay, Animator.AnimatorListener animatorListener){

        for (View firstView: firstToFinishAnimViews){

            animateSingleViewMovement(
                    firstView, metrics, orig_pos_buttons_float,
                    durationAnimations, animationsStartDelay,
                    animatorListener);

        }

    }

    private static void animateSingleViewMovement(
            View firstView, DisplayMetrics metrics,
            List<Float> orig_pos_buttons_float,
            long durationAnimations, long animationsStartDelay,
            Animator.AnimatorListener animatorListener){

        int pos = +1;

        initAnimObject2(firstView, metrics, pos, orig_pos_buttons_float);

        setAnimObjTimeRelatedInfo(durationAnimations, animationsStartDelay);

        if (animatorListener != null ) {

            anim3.addListener(animatorListener);

            // add animationListener for ObjectAnimator object at index

        }

        anim3.start();

        // start animation at index

    }

    private static void setAnimObjTimeRelatedInfo(long durationAnimations, long animationsStartDelay){

        anim3.setDuration(durationAnimations);

        // set duration of animation at index to 400 milliseconds

        anim3.setStartDelay(animationsStartDelay);

        // set delay for animation at index to start at 100 milliseconds for each object respectively

    }

    private static void initAnimObject2(View firstView, DisplayMetrics metrics, int pos, List<Float> orig_pos_buttons_float){

        anim3 = null;

        anim3 = ObjectAnimator.ofFloat(
                firstView, "translationX",
                metrics.widthPixels - orig_pos_buttons_float.get(pos),
                orig_pos_buttons_float.get(pos));

        // ObjectAnimator object for button layout at index. Object moves from east to west, starting point of layout is offscreen in eastern coordinates

    }
    
    public static String retrieveImagePath (AppCompatActivity appCompatActivity, Uri mCapturedImageURI){

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = appCompatActivity.managedQuery(mCapturedImageURI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index_data);

    }

    public static boolean isServicesOk(Context context, Activity a, int ERROR_DIALOG_REQUEST, TextView error_textView, Typeface tp){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(a);

        // create API availability object

        if (available == ConnectionResult.SUCCESS){

            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){

            showErrorDialogIsServicesOk(a, available, ERROR_DIALOG_REQUEST);

        } else {

            showGeneralErrorMessageIsServicesOk(context, error_textView, tp);

        }

        return false;

    }

    private static void showGeneralErrorMessageIsServicesOk(Context context, TextView error_textView, Typeface tp){

        error_textView.setVisibility(View.VISIBLE);
        StartupMethods.showErrorMessageTemporarily(context, 3000, error_textView, context.getResources().getString(R.string.maps_error), tp);

        // if for any other reason Play Services is not available, inform user by showing a toast

    }

    private static void showErrorDialogIsServicesOk(Activity a, int available, int ERROR_DIALOG_REQUEST){

        Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(a, available, ERROR_DIALOG_REQUEST);
        dialog.show();

        // if user could not be resolved, show Google-created dialog

    }

    public static void setStarsRating (double ratingAverage, ImageView ratingImageView){

        if (ratingAverage > 0 && ratingAverage < 1.25){

            ratingImageView.setImageResource(R.drawable.one_star);

        }

        if (ratingAverage >= 1.25 && ratingAverage < 1.75){

            ratingImageView.setImageResource(R.drawable.one_and_a_half_stars);

        }

        if (ratingAverage >= 1.75 && ratingAverage < 2.25){

            ratingImageView.setImageResource(R.drawable.two_stars);

        }

        if (ratingAverage >= 2.25 && ratingAverage < 2.75){

            ratingImageView.setImageResource(R.drawable.two_and_a_half_stars);

        }

        if (ratingAverage >= 2.75 && ratingAverage < 3.25){

            ratingImageView.setImageResource(R.drawable.three_stars);

        }

        if (ratingAverage >= 3.25 && ratingAverage < 3.75){

            ratingImageView.setImageResource(R.drawable.three_and_a_half_stars);

        }

        if (ratingAverage >= 3.75 && ratingAverage < 4.25){

            ratingImageView.setImageResource(R.drawable.four_stars);

        }

        if (ratingAverage >= 4.25 && ratingAverage < 4.75){

            ratingImageView.setImageResource(R.drawable.four_and_a_half_stars);

        }

        if (ratingAverage >= 4.75){

            ratingImageView.setImageResource(R.drawable.five_stars);

        }

        if (ratingAverage == 0){

            ratingImageView.setImageResource(0);

            // if rating average is 0, set rating average imageView bitmap equal to nothing

        }

    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize) throws FileNotFoundException {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (width_tmp / 2 >= requiredSize && height_tmp / 2 >= requiredSize) {
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    public static void setInputFilterNoMiscUTFCharacters (List<EditText> editTexts) {

        initInputFilter();

        applyInputFilterToAllEditTexts(editTexts);
    }

    private static void applyInputFilterToAllEditTexts(List<EditText> editTexts){

        for (int pos = 0; pos < editTexts.size(); pos++) {

            editTexts.get(pos).setFilters(new InputFilter[]{filter});

        }

    }

    private static void initInputFilter(){

        filter = (source, start, end, dest, dstart, dend) -> {

            for (int i = start; i < end; i++) {

                int type = Character.getType(source.charAt(i));

                //System.out.println("Type : " + type);

                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {

                    return "";

                }

                // return void String object if illegal character entered

            }

            // if user enters any UTF-8 symbols that aren't letters of the roman alphabet, return empty string as keyboard input result

            return null;
        };

    }

    public static void makeLoadingScreenVisible(Animation enterAnimation, Animation exitAnimation, FrameLayout loadingLayout) {

        loadingLayout.clearAnimation();

        if (enterAnimation != null) {

            startEnterAnim(enterAnimation, loadingLayout);

        } else if (exitAnimation != null) {

            startExitAnim(exitAnimation, loadingLayout);

        }

    }

    private static void startExitAnim(Animation exitAnimation, FrameLayout loadingLayout){

        exitAnimation.setDuration(200);

        // set duration for animation at 200 milliseconds

        loadingLayout.startAnimation(exitAnimation);

        // start animation

    }

    private static void startEnterAnim(Animation enterAnimation, FrameLayout loadingLayout){

        enterAnimation.setDuration(200);

        // set duration for animation at 200 milliseconds

        loadingLayout.startAnimation(enterAnimation);

        // start animation

    }

}
