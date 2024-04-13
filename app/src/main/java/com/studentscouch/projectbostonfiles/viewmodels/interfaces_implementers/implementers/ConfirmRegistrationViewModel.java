package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.view.views.ConfirmRegistrationActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.ConfirmRegistrationViewModelInterface;

import java.util.List;

public class ConfirmRegistrationViewModel implements ConfirmRegistrationViewModelInterface {

    private ConfirmRegistrationActivityView view;

    public ConfirmRegistrationViewModel(ConfirmRegistrationActivityView view){

        this.view = view;
    }

    @Override
    public void makeProgressLayoutDissapearFunc(Context context, AppCompatActivity appCompatActivity, ViewGroup viewGroup, FirebaseAuth fbAuth, FrameLayout progress_layout, int isFromActivity) {

        Animation exit_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        // load activity exit fade out animation

        exit_anim.setDuration(200);

        // set animation duration

        progress_layout.startAnimation(exit_anim);

        // make loading screen disappear

        StudentsCouchAnimations anims = new StudentsCouchAnimations(appCompatActivity);

        anims.progressLayoutExitAnimationFunc(appCompatActivity, context, view.getFab(), exit_anim, progress_layout, viewGroup, view, isFromActivity);

    }

    @Override
    public void initRegistrationViewsAccToChosenOption(List<View> apartmentNotNullViews, List<TextView> apartmentViews, List<View> userViews,
                                                       ImageView profile_picture, TextView profile_picture_textView, int isNotNowSelected, int isFromActivity,
                                                       int choice) {

        removeViewsUserInfoNull(
                userViews, apartmentViews, isNotNowSelected, isFromActivity, choice);

        // check if user came from 'add apartment details' button, if true, make visible / hide according views

        removeViewsApartmentInfoNull(
                apartmentViews, userViews, isNotNowSelected, isFromActivity, choice);

        // check if user came from 'add apartment details' button, if true, make visible / hide according views

        removeViewsApartmentInfoNotNull(
                apartmentNotNullViews, userViews, isFromActivity, profile_picture,
                profile_picture_textView);

        // if user chose to register as host and not now isn't selected, make visible / hide according views

    }

    private void removeViewsUserInfoNull(
            List<View> apartmentInfoNullViewsGone, List<TextView> apartmentInfoNullViewsVisible, int isNotNowSelected, int isFromActivity,
            int choice){

        if (isNotNowSelected == 1 || (isFromActivity == 5 && choice == 2)) {

            // if user selected 'not now' or has chosen to not register as a host, do the following

            for (int pos = 0; pos < apartmentInfoNullViewsGone.size(); pos++) {

                apartmentInfoNullViewsGone.get(pos).setVisibility(View.GONE);

            }

            for (int pos = 0; pos < apartmentInfoNullViewsVisible.size(); pos++) {

                apartmentInfoNullViewsVisible.get(pos).setVisibility(View.VISIBLE);

            }

        }

    }

    private void removeViewsApartmentInfoNull(
            List<TextView> apartmentViews, List<View> userViews, int isNotNowSelected, int isFromActivity,
            int choice) {

        if (isNotNowSelected == 1 || (isFromActivity == 5 && choice == 2)) {

            // if user selected 'not now' or has chosen to not register as a host, do the following

            for (int pos = 0; pos < apartmentViews.size(); pos++) {

                apartmentViews.get(pos).setVisibility(View.GONE);

            }

            // make apartment data views disappear

            for (int pos = 0; pos < userViews.size(); pos++) {

                userViews.get(pos).setVisibility(View.VISIBLE);

            }

            // make user data views appear

        }

    }

    private void removeViewsApartmentInfoNotNull(
            List<View> apartmentNotNullViews, List<View> userViews, int isFromActivity, ImageView profile_picture_imageView,
            TextView profile_picture_textView) {

        if (isFromActivity == 1) {

            // if user selected 'not now' or has chosen to not register as a host, do the following

            profile_picture_imageView.setVisibility(View.GONE);
            profile_picture_textView.setVisibility(View.GONE);

            for (int pos = 0; pos < apartmentNotNullViews.size(); pos++) {

                apartmentNotNullViews.get(pos).setVisibility(View.VISIBLE);

            }

            // make apartment data views disappear

            for (int pos = 0; pos < userViews.size(); pos++) {

                userViews.get(pos).setVisibility(View.GONE);

            }

            // make user data views appear

        }

    }

}
