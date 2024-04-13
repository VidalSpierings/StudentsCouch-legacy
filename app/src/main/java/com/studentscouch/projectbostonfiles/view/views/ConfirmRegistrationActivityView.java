package com.studentscouch.projectbostonfiles.view.views;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ConfirmRegistrationActivityView {

    View getRootView();

    void initViews(AppCompatActivity appCompatActivity, Context context);

    void initData(AppCompatActivity appCompatActivity, Context context);

    void loadAllEnteredData(Context context);

void extractStringsFromTextViews(Context context);

void extractApartmentStringsFromTextViews(Context context);

Bitmap getApartmentImage1_1();

void initObjectAnimators(Context context);

ObjectAnimator getObjectAnimator1();

ObjectAnimator getObjectAnimator2();

void hideFab();

void enableFab();

TextView getErrorTextView();

FloatingActionButton getFab();

void makeProgressLayoutDisappear(AppCompatActivity appCompatActivity, Context context);

void showErrorMessage(Context context);

void showProgressLayout(AppCompatActivity appCompatActivity);

void removeOnGlobalLayoutListener();

void checkHowManyApartmentImagesAdded();

    void animateActivityReEnterTransition(Context context, Activity activity);
}
