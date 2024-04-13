package com.studentscouch.projectbostonfiles;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentscouch.projectbostonfiles.view.views.ConfirmRegistrationActivityView;

public interface StudentsCouchAnimationsInterface {

    void animateActivityTransitionBookingActivity(Context context, RelativeLayout relativeLayout, float relativeLayout_org_pos_x);

    void animateActivityExitTransitionCityActivity(Context context, RecyclerView recyclerView, float recyclerView_org_pos_y);

    void retrieveApartmentItemsEnterTransition(Context context, final RecyclerView recyclerView, Float recyclerView_org_pos_y);

    void animateCityActivityReEnterTransition(
            Context context, final RecyclerView recyclerView, Float recyclerView_org_pos_y);

    void animateConfirmRegistrationActivityReEnterAnimations (
            Context context, FrameLayout log_in_layout,
            ScrollView scrollView, Float log_in_layout_org_pos_x, Float scrollView_org_pos_x);

    void animateActivityEnterAndReEnterTransitionBookingActivity2 (
            Context context, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x);

    void progressLayoutExitAnimationFunc(AppCompatActivity appCompatActivity, Context context, FloatingActionButton fab, Animation exit_anim, FrameLayout progress_layout, ViewGroup viewGroup, ConfirmRegistrationActivityView view, int isFromActivity);
}
