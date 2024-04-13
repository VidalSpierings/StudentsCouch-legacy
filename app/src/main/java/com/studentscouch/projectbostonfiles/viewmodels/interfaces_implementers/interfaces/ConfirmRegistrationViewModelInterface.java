package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public interface ConfirmRegistrationViewModelInterface {

    void makeProgressLayoutDissapearFunc(
            Context context, AppCompatActivity appCompatActivity,
            ViewGroup viewGroup, FirebaseAuth fbAuth, FrameLayout progress_layout, int isFromActivity);

    void initRegistrationViewsAccToChosenOption(List<View> apartmentNotNullViews, List<TextView> apartmentViews, List<View> userViews,
                                                ImageView profile_picture, TextView profile_picture_textView, int isNotNowSelected, int isFromActivity,
                                                int choice);

}
