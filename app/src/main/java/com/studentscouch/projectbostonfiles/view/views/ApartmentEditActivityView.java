package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public interface ApartmentEditActivityView {

    View getRootView();

    void initViews(AppCompatActivity appCompatActivity, Context context);

    void initData(AppCompatActivity appCompatActivity, Context context);

    void initSwipeDotsLayout(final Context context, String apartement1String, String apartement2String, String apartement3String,
                             LinearLayout slideDotsPanel);

    void initDescEditText (Context context);

    void initObservables();

    void buttonClickedAnimation(Context context);

    void initialiseSwipeDotsLayout(Context context);

    void initialiseSwipeDotsLayout2(Context context);

    void retrieveCameraImage (Context context, AppCompatActivity appCompatActivity, int REQUEST_CODE,
                              int resultCode);

    void retrieveGalleryImage (Context context, Intent data, int REQUEST_CODE, int resultCode);

    void initialiseImagesViewPager (Context context);

    void changeSlideDotsPanel (final Context context, LinearLayout slideDotsPanel);

    void makeRevealViewsInvisible();

    void retrieveInstantiatedPicture(
            Context context, AppCompatActivity appCompatActivity,
            int requestCode, int resultCode, Intent data);
}
