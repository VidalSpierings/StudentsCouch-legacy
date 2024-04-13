package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public interface StartupActivityViewModelInterface {

    void openAccountActivity(Context context, AppCompatActivity appCompatActivity, View account_layout_view,
                             MotionEvent event, View settings_layout_views, String encoded, String encoded2);

    void openSettingsActivity(Context context, AppCompatActivity appCompatActivity, MotionEvent motionEvent,
                              View account_layout_view, View settings_layout_views);

    void openPlacePickerDialog(Context context, AppCompatActivity appCompatActivity);

    void startAccountApartmentActivity(
            Context context, AppCompatActivity appCompatActivity,
            Animation exit_anim_item_layout, ImageView imageView,
            String encoded, String encoded2);

    void startAccountApartmentActivity2(AppCompatActivity appCompatActivity);

    void retrieveSelectedLocation(Context context, AppCompatActivity appCompatActivity, Intent data, int requestCode, int resultCode);

}
