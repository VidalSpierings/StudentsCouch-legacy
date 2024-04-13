package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public interface CityActivityModelInterface {

    void setActivityBackgroundFunc(Context context, AppCompatActivity appCompatActivity, ImageView backgroundImage);

    void setHeaderImage(Context context, AppCompatActivity appCompatActivity);

}
