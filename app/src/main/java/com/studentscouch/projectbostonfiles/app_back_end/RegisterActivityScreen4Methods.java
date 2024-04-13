package com.studentscouch.projectbostonfiles.app_back_end;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;

public class RegisterActivityScreen4Methods {

    private static Animation scaleAnimation;

    private static Animation
            enter_anim4,
            enter_anim5;

    public static void checkIfUnlocked(
            final Context context, FrameLayout progress_layout, Animation exit_anim_layout, final Animation enter_anim,
            final ImageView partyImageView, final TextView subtitle_textView,
            final TextView additional_info_textView, final View invisible_view, final FloatingActionButton fab, boolean isUnlocked,
            final TextView app_not_unlocked_textView) {

        if (isUnlocked){

        applicationUnlockedFunc(
                context, progress_layout, exit_anim_layout,
                partyImageView, subtitle_textView, enter_anim,
                additional_info_textView, invisible_view, fab);

        }

        if (!isUnlocked) {

        // if application is not unlocked, do the following

        applicationLockedFunc(
                context, progress_layout, exit_anim_layout,
                fab, app_not_unlocked_textView, subtitle_textView,
                enter_anim, partyImageView, additional_info_textView);

    }

    }

    private static void animateGifImage(ImageView partyImageView){

        AnimationDrawable frameAnimation = (AnimationDrawable) partyImageView.getDrawable();
        frameAnimation.setCallback(partyImageView);
        frameAnimation.setVisible(true, true);

        // make image visible, keep repeating images

        frameAnimation.start();

        // load and initialise AnimationDrawable

    }

    private static void setAnimationsStartDelay(){

        scaleAnimation.setStartOffset(1000);

        enter_anim4.setStartOffset(2000);

        enter_anim5.setStartOffset(3000);

                                    /*

                                     wait 1000, 2000 and 3000 milliseconds for scaleAnimation,
                                     enter_anim2 and enter_anim3 respectively to start

                                    */

    }

    private static void startAllAnimations(TextView additional_info_textView, View invisible_view, ImageView partyImageView){

        additional_info_textView.startAnimation(enter_anim4);

        invisible_view.startAnimation(enter_anim5);

        partyImageView.startAnimation(scaleAnimation);

        // start animations

    }

    private static void makeViewsVisible(TextView additional_info_textView, ImageView partyImageView){

        additional_info_textView.setVisibility(View.VISIBLE);

        partyImageView.setVisibility(View.VISIBLE);

        // make Title view and partyImageView visible

    }

    private static void setEnterAnimAnimationListener(
            Context context, Animation enter_anim, TextView additional_info_textView,
            View invisible_view, ImageView partyImageView, TextView subtitle_textView){

        enter_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                startNextAnims(context, additional_info_textView, invisible_view, partyImageView);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                subtitle_textView.setVisibility(View.VISIBLE);

                // make subTitle view visible

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static void startNextAnims(Context context, TextView additional_info_textView, View invisible_view, ImageView partyImageView){

        scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.resize_large);

        // load scaleAnimation

        setAnimationsStartDelay();

        startAllAnimations(additional_info_textView, invisible_view, partyImageView);

        makeViewsVisible(additional_info_textView, partyImageView);

    }

    private static void setEnterAnim5FinishedListener(FloatingActionButton fab){

        enter_anim5.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                fab.show();

                // show fab when final animation has finished animating

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static void setExitAnimListener(
            Context context, Animation exit_anim_layout,
            ImageView partyImageView, TextView subtitle_textView,
            Animation enter_anim, TextView additional_info_textView, View invisible_view,
            FloatingActionButton fab){

        exit_anim_layout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                animateGifStartNextAnims(
                        context, partyImageView, subtitle_textView,
                        additional_info_textView, enter_anim, invisible_view,
                        fab);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static void animateGifStartNextAnims(
            Context context, ImageView partyImageView, TextView subtitle_textView,
            TextView additional_info_textView, Animation enter_anim,
            View invisible_view, FloatingActionButton fab){


        // load fade in animations

        animateGifImage(partyImageView);

        // (behaviour of AnimationDrawable objects are similar to Gif images)

        subtitle_textView.startAnimation(enter_anim);

        setEnterAnimAnimationListener(
                context, enter_anim, additional_info_textView,
                invisible_view, partyImageView, subtitle_textView);

        setEnterAnim5FinishedListener(fab);
    }

    private static void startStartupActivity(Context context){

        Intent i = new Intent(context, StartupActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        context.startActivity(i);

        // start StartupActivity without standard activity transition animation

    }

    private static void setExitAnimLayoutAnimationListener(
            Context context, Animation exit_anim_layout, TextView app_not_unlocked_textView,
            TextView subtitle_textView, Animation enter_anim, ImageView partyImageView,
            TextView additional_info_textView){

        exit_anim_layout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startTextViewAnims(app_not_unlocked_textView, subtitle_textView, enter_anim);

                setTextViewsVisibility(
                        app_not_unlocked_textView, subtitle_textView,
                        partyImageView, additional_info_textView);

                setTextViewsText(context, subtitle_textView, app_not_unlocked_textView);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static void copyWebsiteLinkToClipBoard(Context context){

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("StudentsCouchwebsite", "www.studentscouch.com");
        clipboard.setPrimaryClip(clip);

        // copy studentscouch website to clipboard

    }

    private static void applicationLockedFunc(
            Context context, FrameLayout progress_layout, Animation exit_anim_layout,
            FloatingActionButton fab, TextView app_not_unlocked_textView, TextView subtitle_textView,
            Animation enter_anim, ImageView partyImageView, TextView additional_info_textView){

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_layout);

        fab.setVisibility(View.INVISIBLE);

        setExitAnimLayoutAnimationListener(
                context, exit_anim_layout, app_not_unlocked_textView,
                subtitle_textView, enter_anim, partyImageView, additional_info_textView);

        copyWebsiteLinkToClipBoard(context);

    }

    private static void applicationUnlockedFunc(
            Context context, FrameLayout progress_layout, Animation exit_anim_layout,
            ImageView partyImageView, TextView subtitle_textView, Animation enter_anim,
            TextView additional_info_textView, View invisible_view, FloatingActionButton fab){

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_layout);

        setExitAnimListener(
                context, exit_anim_layout, partyImageView,
                subtitle_textView, enter_anim, additional_info_textView,
                invisible_view, fab);

        fab.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(view -> startStartupActivity(context));

    }

    private static void startTextViewAnims(TextView app_not_unlocked_textView, TextView subtitle_textView, Animation enter_anim){

        app_not_unlocked_textView.startAnimation(enter_anim);
        subtitle_textView.startAnimation(enter_anim);

        // make app_not_unlocked_textView and subTitle_textView

    }

    private static void setTextViewsVisibility(TextView app_not_unlocked_textView, TextView subtitle_textView, ImageView partyImageView, TextView additional_info_textView){

        app_not_unlocked_textView.setVisibility(View.VISIBLE);
        subtitle_textView.setVisibility(View.VISIBLE);
        partyImageView.setVisibility(View.INVISIBLE);
        additional_info_textView.setVisibility(View.INVISIBLE);

        // make app_not_unlocked_textView and subtitle_textView visible, make party Gif and Title view invisible

    }

    private static void setTextViewsText(Context context, TextView subtitle_textView, TextView app_not_unlocked_textView){

        subtitle_textView.setText(context.getResources().getString(R.string.registration_complete));
        app_not_unlocked_textView.setText(context.getResources().getString(R.string.registration_complete1));

        // set according texts to textViews that were made visible

    }

}
