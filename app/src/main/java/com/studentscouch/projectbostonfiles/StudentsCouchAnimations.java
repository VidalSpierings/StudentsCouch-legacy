package com.studentscouch.projectbostonfiles;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.observables.ApartmentActivityObservables;
import com.studentscouch.projectbostonfiles.ui.RegisterActivtyScreen4;
import com.studentscouch.projectbostonfiles.ui.ReportABugActivity;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.view.views.BookingActivity2View;
import com.studentscouch.projectbostonfiles.view.views.BookingActivityView;
import com.studentscouch.projectbostonfiles.view.views.ConfirmRegistrationActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.ApartmentActivityViewModel;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.BookingActivityViewModel;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.CityActivityViewModel;

import java.util.List;

public class StudentsCouchAnimations implements StudentsCouchAnimationsInterface {

    @SuppressLint("StaticFieldLeak")
    static Activity activity;
    private Intent intent;

    public StudentsCouchAnimations(Activity activity){
        StudentsCouchAnimations.activity =activity;
    }

    @Override
    public void animateActivityExitTransitionCityActivity(Context context, RecyclerView recyclerView, float recyclerView_org_pos_y){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(recyclerView, "translationY", recyclerView_org_pos_y,  metrics.heightPixels - recyclerView_org_pos_y);
        anim.setStartDelay(125);
        anim.setDuration(400);

        anim.start();

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                CityActivityViewModel viewModel = new CityActivityViewModel();

                viewModel.makeRecyclerViewInvisibleStartNextActivity(context, recyclerView);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        anim.addListener(animatorListener);


    }

    @Override
    public void animateActivityTransitionBookingActivity(Context context, RelativeLayout relativeLayout, float relativeLayout_org_pos_x){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", metrics.widthPixels - relativeLayout_org_pos_x, relativeLayout_org_pos_x);
        anim.setDuration(400);

        anim.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    @Override
    public void retrieveApartmentItemsEnterTransition(Context context, final RecyclerView recyclerView, Float recyclerView_org_pos_y){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(recyclerView, "translationY", metrics.heightPixels - recyclerView_org_pos_y, recyclerView_org_pos_y);

        anim.setStartDelay(125);

        // set delay for animation to start on 100 milliseconds for each ObjectAnimator object respectively

        anim.setDuration(400);

        // set starting delay and duration of animations in milliseconds

        anim.start();

        // activity enter animations. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                recyclerView.setVisibility(View.VISIBLE);

                // set apartment recyclerView visible upon start of enter animation

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

        anim.addListener(animatorListener);

    }

    @Override
    public void animateConfirmRegistrationActivityReEnterAnimations (
            Context context, FrameLayout log_in_layout,
            ScrollView scrollView, Float log_in_layout_org_pos_x, Float scrollView_org_pos_x) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", metrics.widthPixels - log_in_layout_org_pos_x, log_in_layout_org_pos_x);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(scrollView, "translationX", metrics.widthPixels - log_in_layout_org_pos_x, scrollView_org_pos_x);

        anim.setDuration(400);
        anim2.setDuration(400);

        // set animation duration

        anim.setStartDelay(100);

        // set delay for animations to start at 100 milliseconds for each object respectively

        anim.start();
        anim2.start();

        // activity re-enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    @Override
    public void animateCityActivityReEnterTransition(
            Context context, final RecyclerView recyclerView, Float recyclerView_org_pos_y){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(recyclerView, "translationY", metrics.heightPixels - recyclerView_org_pos_y, recyclerView_org_pos_y);
        anim.setStartDelay(125);

        // set delay for animation to start on 100 milliseconds for each ObjectAnimator object respectively

        anim.setDuration(400);

        // set starting delay and duration of animations in milliseconds

        anim.start();

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                recyclerView.setVisibility(View.VISIBLE);

                // set apartment recyclerView visible upon start of exit animation

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

        anim.addListener(animatorListener);

    }

    public static void animateActivityTransitionScreen3(
            Context context, final List<View> viewsInLayout, List<View> objectAnimatingViews, List<Float> org_pos,
            List<View> fade_in_views, int durationAnimations, ImageView profileImage){

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        // copy layoutparams attributes of relativeLayout to a set of LayoutParams independent from the real relativelayoutParams

        profileImage.setVisibility(View.INVISIBLE);

        for (int pos = 0; pos <viewsInLayout.size(); pos++) {

            viewsInLayout.get(pos).setVisibility(View.INVISIBLE);

        }

        // make all views in base layout invisible (profile imageView is also invisible here)

        // kijk of profile imageview invisible maken hier (ipv aan einde van methode) gedrag app verandert

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

                // make all views in base layout invisible

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

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int pos = 0; pos < objectAnimatingViews.size(); pos++) {

            ObjectAnimator anim = ObjectAnimator.ofFloat(objectAnimatingViews.get(pos), "translationX", metrics.widthPixels - org_pos.get(pos), org_pos.get(pos));

            // create objectAnimator objects

            anim.setDuration(durationAnimations);

            // set duration for animations at 400 milliseconds

            anim.setStartDelay(100);

            // set start delay for animations at 100 milliseconds

            anim.addListener(animatorListener);

            // add animation listeners

            anim.start();

        }

        Animation fade_in = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        for (int pos = 0; pos < fade_in_views.size(); pos++) {

            fade_in_views.get(pos).startAnimation(fade_in);

            // start fade in animation for fading in views

        }

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        // make all views in base layout invisible

    }

    public static void onRestartAnimations1 (
            Context context, View log_in_layout, View register_layout, float log_in_layout_org_pos_x,
            float register_layout_org_pos_x, ImageView profile_imageView, TextView subTitle_textView, TextView additional_info_textView){

        Animation fade_in = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", metrics.widthPixels - log_in_layout_org_pos_x, log_in_layout_org_pos_x);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(register_layout, "translationX", metrics.widthPixels - register_layout_org_pos_x, register_layout_org_pos_x);

        // create objectAnimator objects

        anim.setDuration(400);
        anim2.setDuration(400);

        // set duration for animations at 400 milliseconds

        anim.setStartDelay(100);
        anim2.setStartDelay(100);

        // set animation start delay at 100 milliseconds

        if (profile_imageView != null) {

            profile_imageView.startAnimation(fade_in);

        }

        if (subTitle_textView != null) {

            subTitle_textView.startAnimation(fade_in);

        }

        additional_info_textView.startAnimation(fade_in);

        anim.start();
        anim2.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    public static void onRestartAnimationsNoButtons(
            Context context, List<View> animatingViews, List<Float> orig_positions,
            List<View> viewsInLayout, List<View> fading_views, int durationAnimations){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            ObjectAnimator anim = ObjectAnimator.ofFloat(animatingViews.get(pos), "translationX", metrics.widthPixels - orig_positions.get(pos), orig_positions.get(pos));

            // create objectAnimator objects

            anim.setDuration(durationAnimations);

            // set duration of animations

            anim.start();

        }

        Animation fade_in = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        // load fade in animation

        for (int pos = 0; pos < fading_views.size(); pos++) {

            fading_views.get(pos).startAnimation(fade_in);

            fading_views.get(pos).setVisibility(View.VISIBLE);

            // activity exit animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        }

        StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

        // make all views in base layout visible

    }

    public static void animateActivityExitTransitionApartmentActivity(
            final Context context, Activity activity, ScrollView scrollView, TabLayout tabLayout,
            Float scrollView_org_pos_y, Float tabLayout_org_pos_y, ApartmentActivityObservables observables){

        ApartmentActivityViewModel viewModel = new ApartmentActivityViewModel(observables);

        scrollView.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(scrollView, "translationY", scrollView_org_pos_y, metrics.heightPixels - scrollView_org_pos_y);

        ObjectAnimator anim3 = ObjectAnimator.ofFloat(tabLayout, "translationY", tabLayout_org_pos_y, -metrics.heightPixels - tabLayout_org_pos_y);

        // activity enter animation. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim2.setDuration(400);
        anim3.setDuration(400);

        // set duration of animations to 400 milliseconds

        anim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                viewModel.launchBookNowActivityFunc(activity, context);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        anim2.start();
        anim3.start();

        scrollView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);

        // make scrollView and tabLayout visible

    }

    public static void animateActivityReEnterTransitionApartmentActivity(
            Context context, ScrollView scrollView,
            Float scrollView_org_pos_y, Float tabLayout_org_pos_y, TabLayout tabLayout){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(scrollView, "translationY", metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);

        // ObjectAnimator object for scrollView. Object moves from south to north, starting point of layout is offscreen in southern coordinates

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(tabLayout, "translationY", -metrics.heightPixels - tabLayout_org_pos_y, tabLayout_org_pos_y);

        // ObjectAnimator object for tabLayout. Object moves from north to south, starting point of layout is offscreen in northern coordinates

        anim.setDuration(400);

        // set duration of animation 1 to 400 milliseconds

        anim.start();
        anim2.start();

        scrollView.setVisibility(View.VISIBLE);

        // activity re-enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    public static void animateActivityTransitionCityActivity(
            Context context, final RecyclerView recyclerView, Float recyclerView_org_pos_y){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(recyclerView, "translationY", metrics.heightPixels - recyclerView_org_pos_y, recyclerView_org_pos_y);

        anim.setStartDelay(125);

        // set delay for animation to start on 100 milliseconds for each ObjectAnimator object respectively

        anim.setDuration(400);

        // set starting delay and duration of animations in milliseconds

        // activity enter animations. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                recyclerView.setVisibility(View.VISIBLE);

                // set apartment recyclerView visible upon start of enter animation

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

        anim.addListener(animatorListener);

        anim.start();

    }

    public static void animateActivityTransitionBookingActivity(
            Context context, AppCompatActivity appCompatActivity, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x,
            BookingActivityView view, BookingActivityViewModel viewModel) {

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();

            ObjectAnimator anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", relativeLayout_org_pos_x, -metrics.widthPixels - relativeLayout_org_pos_x);
            anim.setDuration(400);

            // activity exit animations. using DisplayMetrics class ensures that animations look the same on all screen resolutions

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                    view.disableFabClickAbility();

                    // disable fab clicking upon start of animation

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    viewModel.startNextActivity(context, appCompatActivity);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();

    }

    /*

    public static void animateActivityEnterTransitionMainActivity(
            final Context context, final View header_image, View layout_center, Animation enter_anim,
            final AppCompatActivity appCompatActivity, final TextView log_in_textView, final TextView register_textView, final TextView pb_textView,
            final FrameLayout log_in_layout, final FrameLayout register_layout, final List<View> viewsInLayout, final Animation enter_anim_progress_layout,
            final RelativeLayout progress_layout, final FloatingActionButton fab, final TextView error_textView, final Typeface tp,
            ViewGroup relativeLayout) {

        enter_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_in_animation);
        enter_anim.reset();
        log_in_textView.clearAnimation();
        register_textView.clearAnimation();
        //  facebook_login_registration.clearAnimation();
        pb_textView.clearAnimation();
        log_in_layout.clearAnimation();
        register_layout.clearAnimation();

        // clear animations for all objects in base layout

        register_textView.startAnimation(enter_anim);
        log_in_textView.startAnimation(enter_anim);
        pb_textView.startAnimation(enter_anim);
        // facebook_login_registration.startAnimation(enter_anim);
        log_in_layout.startAnimation(enter_anim);
        register_layout.startAnimation(enter_anim);

        // make all views fade in according to newly created fade in 'Animation' object

        StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

        // make all views in base layout visible

        if (StartupMethods.isOnline(context)) {

            // if user has active internet connection, do the following

            progress_layout.startAnimation(enter_anim_progress_layout);

            progress_layout.setVisibility(View.VISIBLE);

            // make loading screen layout visible

            log_in_layout.setClickable(true);

            register_layout.setClickable(true);

            fab.setClickable(true);

            // make log in and register buttons and fab clickable

        }

        if (!StartupMethods.isOnline(context))

        {

            log_in_layout.setClickable(false);

            register_layout.setClickable(false);

            // make log in and register button unclickable

            fab.setClickable(false);

            // make log in and register buttons and fab clickable

            error_textView.setVisibility(View.VISIBLE);
            StartupMethods.showErrorMessage(context, error_textView, context.getResources().getString(R.string.you_need_to_be_online), tp);

            // inform user by toast that no active internet connection was found

        }

        // if base layout is pressed while enter animations are still animating, make animations finish instantly

    }

    */

    public static void animateActivityTransitionProfileActivity (Context context, ScrollView scrollView, float scrollView_org_pos_y){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(scrollView, "translationY", metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);
        anim.setDuration(400);

        // set animation duration at 400 milliseconds

        anim.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        scrollView.setVisibility(View.VISIBLE);

    }

    /*

    public static void animateActivityExitTransitionProfileActivity (
            final Context context, ScrollView scrollView, Float scrollView_org_pos_y, ObjectAnimator anim2, final Activity activity){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        anim2 = ObjectAnimator.ofFloat(scrollView, "translationY", scrollView_org_pos_y, metrics.heightPixels - scrollView_org_pos_y);
        anim2.setDuration(400);

        // set animation duration at 400 milliseconds

        // activity exit animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                Intent intent = new Intent(activity, MessagingActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);

                // launch messaging activity

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        anim2.start();

        scrollView.setVisibility(View.VISIBLE);

    }

    */

    public static void animateActivityExitTransition2ProfileActivity(
            final Context context, ScrollView scrollView, TabLayout tabLayout, Float scrollView_org_pos_y,
            Float tabLayout_org_pos_y, final Activity appCompatActivity){

        scrollView.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(scrollView, "translationY", scrollView_org_pos_y, metrics.heightPixels - scrollView_org_pos_y);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(tabLayout, "translationY", tabLayout_org_pos_y, -metrics.heightPixels - tabLayout_org_pos_y);

        // create object animator objects

        anim2.setDuration(400);
        anim3.setDuration(400);

        // set animation durations at 400 milliseconds each

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                Intent intent = new Intent(appCompatActivity, ReportABugActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);

                // open ReportABugActivity when animation is finished

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        anim2.start();
        anim3.start();

        scrollView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);

    }

    public static void animateActivityExitTransition2ProfileOnlyActivity(
            final Context context, ScrollView scrollView, final AppCompatActivity appCompatActivity,
            Float scrollView_org_pos_y
    ) {

        scrollView.setVisibility(View.INVISIBLE);

        // make scrollVIew invisible

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(scrollView, "translationY", scrollView_org_pos_y, metrics.heightPixels - scrollView_org_pos_y);
        anim2.setDuration(400);

        // activity exit animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                Intent intent = new Intent(appCompatActivity, ReportABugActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

                // start ReportABugActivity when animation finished

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        anim2.start();

        scrollView.setVisibility(View.VISIBLE);

    }

    public static void animateActivityTransitionProfileOnlyActivity (
            Context context, ScrollView scrollView, Float scrollView_org_pos_y){

        scrollView.setVisibility(View.INVISIBLE);

        // make scrollVIew invisible

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(scrollView, "translationY", metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);

        // create objectAnimator object

        anim.setDuration(400);

        // set animation duration at 400 milliseconds

        anim.start();

        scrollView.setVisibility(View.VISIBLE);

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    public static void animateActivityExitTransitionRegisterActivityScreen2 (
            final Context context, List<View> animatingViews, List<Boolean> setAnimationDelay, int animationDuration,
            int animationDelayDuration, final Intent i) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            ObjectAnimator anim = ObjectAnimator.ofFloat(
                    animatingViews.get(pos), "translationX", animatingViews.get(pos).getX(),
                    -metrics.widthPixels - animatingViews.get(pos).getX());

            // create objectAnimator objects

            anim.setDuration(animationDuration);

            if (setAnimationDelay.get(pos)) {

                anim.setStartDelay(animationDelayDuration);

            }

            if (pos == 6) {

                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(i);

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

            }

            anim.start();

        }

    }

    public static void startEnterTransitionAnimationsRegisterActivityScreen2 (
            Context context, final List<View> animatingViews, List<Boolean> setStartDelay,
            List<Float> original_pos, int animationDuration, int startDelay) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        /*

        ObjectAnimator animDelay1 = ObjectAnimator.ofFloat(animatingViews.get(6), "translationX", metrics.widthPixels - original_pos.get(6), original_pos.get(6));
        ObjectAnimator animDelay2 = ObjectAnimator.ofFloat(animatingViews.get(7), "translationX", metrics.widthPixels - original_pos.get(7), original_pos.get(7));

        */

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            animatingViews.get(pos).setVisibility(View.INVISIBLE);

            ObjectAnimator anim = ObjectAnimator.ofFloat(animatingViews.get(pos), "translationX", metrics.widthPixels - original_pos.get(pos), original_pos.get(pos));

            anim.setDuration(animationDuration);

            if (setStartDelay.get(pos)) {

                anim.setStartDelay(startDelay);

                animatingViews.get(pos).setVisibility(View.INVISIBLE);

                final int finalPos = pos;
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                        animatingViews.get(finalPos).setVisibility(View.VISIBLE);

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

            }

            if (!setStartDelay.get(pos))

            {

                animatingViews.get(pos).setVisibility(View.VISIBLE);

            }

            anim.start();

        }

    }

    /*

    public static void animateActivityTransition(
            Context context, LinearLayout firstName_layout, LinearLayout lastName_layout, LinearLayout userName_layout,
            final List<View> viewsInLayout, List<View> animatingViews, List<Float> orig_pos, Animation fade_in,
            TextView subTitle_textView, FrameLayout apartemet_images_framelayout, ImageView add_image_imageView,
            List<View> fadingViews, int durationAnimations, final AppCompatActivity appCompatActivity
    ){

        // copy layoutparams attributes of relativelayout to a set of LayourParams independent from the real relativelayoutParams

        firstName_layout.setVisibility(View.INVISIBLE);
        lastName_layout.setVisibility(View.INVISIBLE);
        userName_layout.setVisibility(View.INVISIBLE);

        // make apartment data fields invisible

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

                // make all views in base layout invisible

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

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            ObjectAnimator anim = ObjectAnimator.ofFloat(animatingViews.get(pos), "translationX", metrics.widthPixels - orig_pos.get(pos), orig_pos.get(pos));

            // create objectAnimator objects

            anim.setDuration(durationAnimations);

            // set duration of animations at 400 milliseconds

            if (pos == 0) {

                anim.addListener(animatorListener);

            }

            anim.start();

            // start animations

        }

        fade_in = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        // load fade in animation

        subTitle_textView.startAnimation(fade_in);

        apartemet_images_framelayout.startAnimation(fade_in);

        add_image_imageView.startAnimation(fade_in);

        // make subtitle, apartment images layout and add image button fade in

        enterAnimationRegisterYesScreen2(
                context, animatingViews, orig_pos, fadingViews,
                400, viewsInLayout, appCompatActivity);

        StartupMethods.enterAnimationNoButtons(
                context, viewsInLayout, fadingViews, 400,
                0, orig_pos, animatingViews, null,
                animatorListener);

        StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);
    }

    */

    /*

    public static void enterAnimationRegisterYesScreen2 (
            final Context context, List<View> animatingViews, List<Float> views_org_pos, List<View> fade_in_views,
            int animationDuration, final List<View> viewsInLayout, final AppCompatActivity appCompatActivity){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        Animation fade_in = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        // load fade in animation

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

                // make all views in base layout invisible

                RegisterActivityYesScreen2Methods.openNewPlaceSelectionDialog(appCompatActivity);

                // open new dialog upon animation ending informing user that place needs to be directly selected and not indirectly
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

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            ObjectAnimator anim = ObjectAnimator.ofFloat(animatingViews.get(pos), "translationX", metrics.widthPixels - views_org_pos.get(pos), views_org_pos.get(pos));

            // create objectAnimator objects

            anim.setDuration(animationDuration);

            // set duration of animations

            anim.start();

            // start animations

            if (pos == 0) {

                anim.addListener(animatorListener);

            }

            for (int posFadeInViews = 0; posFadeInViews < fade_in_views.size(); posFadeInViews++) {

                fade_in_views.get(posFadeInViews).startAnimation(fade_in);

            }

        }

        StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

        // activity exit animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    */

    /*

    static public void animateActivityExitTransitionCityActivity(
            final Context context, final RecyclerView recyclerView, Float recyclerView_org_pos_y, ObjectAnimator anim){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        anim = ObjectAnimator.ofFloat(recyclerView, "translationY", recyclerView_org_pos_y,  metrics.heightPixels - recyclerView_org_pos_y);
        anim.setStartDelay(125);
        anim.setDuration(400);

        anim.start();

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                recyclerView.setVisibility(View.INVISIBLE);

                context.startActivity(RecyclerViewAdapter.i);

                // set item view of apartments invisible and start AccountandApratmentActivity
                // with corresponding apartment data

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        anim.addListener(animatorListener);


    }

    */

    public static void exitAnimationRegisterActivityScreen1(
            final Context context, FrameLayout register_layout, FrameLayout log_in_layout,
            View log_in_layout_view, View register_layout_view, Intent i,
            TextView pb_textView
    ) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        ObjectAnimator anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", log_in_layout.getX(), -metrics.widthPixels - log_in_layout.getX());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(register_layout, "translationX", register_layout.getX(), -metrics.widthPixels - register_layout.getX());

        // create ObjectAnimator objects

        anim.setDuration(400);
        anim2.setDuration(400);

        // set duration for animations

        pb_textView.startAnimation(fade_out);

        // make title textView fade out

        anim.start();
        anim2.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim2
                .addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(i);

                        // start RegisterActivityScreen2

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

    }

    @Override
    public void animateActivityEnterAndReEnterTransitionBookingActivity2 (
            Context context, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", metrics.widthPixels - relativeLayout_org_pos_x, relativeLayout_org_pos_x);
        anim.setDuration(400);

        anim.start();

    }

    public void animateActivityExitTransitionBookingActivity2(
            final Context context, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x,
            final FloatingActionButton fab,
            BookingActivity2View view) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", relativeLayout_org_pos_x, -metrics.widthPixels - relativeLayout_org_pos_x);
        anim.setDuration(400);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                fab.setClickable(false);

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                view.startNextActivity(context);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        anim.start();

    }

    /*

    public void animateActivityTransitionBookingActivity2 (
            Context context, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", metrics.widthPixels - relativeLayout_org_pos_x, relativeLayout_org_pos_x);
        anim.setDuration(400);

        anim.start();

    }

    */

    public void animateActivityTransitionConfirmRegistrationActivity(
            final FrameLayout log_in_layout, final ScrollView scrollView, Context context, List<View> viewsInLayout,
            List<Float> original_pos_coordinates, Float log_in_layout_org_pos_x, Float scrollView_org_pos_x){

        log_in_layout.setVisibility(View.INVISIBLE);

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                scrollView.setVisibility(View.VISIBLE);
                log_in_layout.setVisibility(View.VISIBLE);

                // upon animation starting, make scrollVIew and 'confirm' button invisible


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

        StartupMethods.enterAnimationTwoTextButtons(context, viewsInLayout, null, 400, 100, original_pos_coordinates, null, log_in_layout);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(log_in_layout, "translationX", metrics.widthPixels - log_in_layout_org_pos_x, log_in_layout_org_pos_x);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(scrollView, "translationX", metrics.widthPixels - scrollView_org_pos_x, scrollView_org_pos_x);

        anim.setDuration(400);
        anim2.setDuration(400);

        // set animation duration

        anim.setStartDelay(100);

        // set animation start delay

        anim.addListener(animatorListener);

        anim2.addListener(animatorListener);

        anim.start();
        anim2.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        scrollView.setVisibility(View.INVISIBLE);
        log_in_layout.setVisibility(View.INVISIBLE);

        // make scrollVIew and 'Confirm' button invisible

    }

    @Override
    public void progressLayoutExitAnimationFunc(AppCompatActivity appCompatActivity, Context context, FloatingActionButton fab, Animation exit_anim, FrameLayout progress_layout, ViewGroup viewGroup, ConfirmRegistrationActivityView view, int isFromActivity){

        decideNextActivity(context, isFromActivity);

        fab.hide();

        exit_anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startItemAnimMovementFunc(appCompatActivity, context, progress_layout, view, isFromActivity);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });


    }

    private void decideNextActivity(Context context, int isFromActivity){

        if (isFromActivity == 1){

            intent = new Intent(context, StartupActivity.class);

        }

        if (isFromActivity == 2) {

            intent = new Intent(context, RegisterActivtyScreen4.class);

        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // disable standard activity transition animation for this intent

    }

    private void startItemAnimMovementFunc(
            AppCompatActivity appCompatActivity, Context context, FrameLayout progress_layout, ConfirmRegistrationActivityView view, int isFromActivity){

        progress_layout.setVisibility(View.INVISIBLE);

        // make loading screen disappear

        ObjectAnimator anim = view.getObjectAnimator1();

        ObjectAnimator anim2 = view.getObjectAnimator2();

        anim.start();

        anim2.start();

        // animate 'confirm' button and scrollView layout

        anim2.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (isFromActivity == 1) {

                    appCompatActivity.finishAffinity();

                }

                if (isFromActivity == 2) {

                    context.startActivity(intent);

                    // when 'confirm' button and scrollView are finished animating (and off-screen) start activity

                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void animateActivityTransitionApartmentActivity(Context context, ScrollView scrollView, final Float scrollView_org_pos_y){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(scrollView, "translationY", metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);
        anim.setDuration(400);

        anim.start();

        scrollView.setVisibility(View.VISIBLE);

    }

    public void setExitAnimListenerApartmentActivity(
            final Context context, Animation exit_anim_progress_layout, final String fromActivity, final ScrollView scrollView,
            final Float scrollView_org_pos_y, FrameLayout progress_layout){

        try {

            exit_anim_progress_layout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    if (fromActivity.equals("0")){

                        // ------* See app Helper / StartupMethods class for description what numbers are what activities ------*

                        scrollView.setVisibility(View.VISIBLE);

                        // if user comes from ApartmentEditActivity activity, make scrollView visible

                    }

                    if (fromActivity.equals("1")){

                        StudentsCouchAnimations anims = new StudentsCouchAnimations(activity);

                        anims.animateActivityTransitionApartmentActivity(context, scrollView, scrollView_org_pos_y);

                        /*

                         * if user comes from activity other than ApartmentEditActivity activity,
                         * animate views into layout upon loading being completed

                         */

                    }


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

            // remove progress layout in case it hasn't not disappeared already

        } catch (Exception e) {

            // Do nothing

        }

        // above try/catch block prevents methods from being initiated when user has left Apartment Activity

    }

    public void animateActivityTransitionLoginActivity(
            ImageView checkboxImage, TextView automatic_log_in_textView, final List<View> viewsInLayout,
            FrameLayout checkbox3, Float automatic_log_in_textView_org_pos_x, Float checkbox3_org_pos_x,
            LinearLayout password_layout, LinearLayout username_layout, Float password_layout_org_pos_x,
            Float email_layout_org_pos) {

        int distance =

                1250;

        //(int) (6250 / getResources().getDisplayMetrics().density);
        int distance2 =

                1000;

        //(int) (5000 / getResources().getDisplayMetrics().density);

        checkboxImage.setVisibility(View.INVISIBLE);
        automatic_log_in_textView.setVisibility(View.INVISIBLE);

        // make 'remember email' checkbox and according textView invisible

        int delayTimeMil = 200;

        Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

                // make all views in layout invisible except for base layout

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                Log.i("activity_running", "anim ended");

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        ObjectAnimator anim5 = ObjectAnimator.ofFloat(automatic_log_in_textView, "translationX", distance, automatic_log_in_textView_org_pos_x);
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(checkbox3, "translationX", distance, checkbox3_org_pos_x);
        ObjectAnimator anim7 = ObjectAnimator.ofFloat(password_layout, "translationX", distance2, password_layout_org_pos_x);
        ObjectAnimator anim8 = ObjectAnimator.ofFloat(username_layout, "translationX", distance2, email_layout_org_pos);

        // initialise animation objects

        anim5.setStartDelay(delayTimeMil);
        anim6.setStartDelay(delayTimeMil);

        // set starting delay for anim5 and anim6 objects to 200 milliseconds

        anim5.setDuration(500);
        anim6.setDuration(500);
        anim7.setDuration(500);
        anim8.setDuration(500);

        // set duration of all animations to 500 milliseconds

        anim5.addListener(animationListener);
        anim6.addListener(animationListener);

        // add animationlisteners to anim5 and anim6 objects

        anim5.start();
        anim6.start();
        anim7.start();
        anim8.start();

        // start animations

    }

}
