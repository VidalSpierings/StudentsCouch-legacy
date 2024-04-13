package com.studentscouch.projectbostonfiles.db.interfaces;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface ConfirmRegistrationActivityDBInterface {

    void createNewUser(Context context);

    void registerUserYes(Context context, AppCompatActivity appCompatActivity,
                         FirebaseAuth fbAuth, FrameLayout progress_layout, Typeface tp, int isFromActivity);

    void addApartmentInfoToDB(AppCompatActivity appCompatActivity, Context context, FirebaseAuth fbAuth);

    void registerUserYesInfoNotRegistered(final Context context, final FirebaseAuth fbAuth, final FrameLayout progress_layout, final int numUsers,
                                          AppCompatActivity appCompatActivity, ViewGroup viewGroup, int isFromActivity);

    void registerUserNo(final Context context, final FirebaseAuth fbAuth,
                        final FrameLayout progress_layout, final int numUsers,
                        int isDialogRead,
                        AppCompatActivity appCompatActivity, ViewGroup viewGroup, int isFromActivity);

    void getNumHostsAndUsersFromDB();



    void submitNewInfoToDB(final Context context, AppCompatActivity appCompatActivity,
                           final FrameLayout progress_layout, final int isNotNowAdded,
                           final int isFromActivity,  Typeface tp, FirebaseAuth fbAuth,
                           int numUsers, ViewGroup viewGroup);

}
