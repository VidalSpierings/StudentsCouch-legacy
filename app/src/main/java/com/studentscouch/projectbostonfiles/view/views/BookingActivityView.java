package com.studentscouch.projectbostonfiles.view.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public interface BookingActivityView {

    View getRootView();

    void initViews (AppCompatActivity appCompatActivity, Context context);

    void initData (AppCompatActivity appCompatActivity, Context context);

    void setLayoutParamsDateTextView(Context context, TextView text, Typeface tp);

    void disableFabClickAbility();

    void disableHideFab();

    void initialiseBookingAtIndex(Context context);

    void setChooseLayoutOnClickListener(AppCompatActivity appCompatActivity);

    void makeBookedDatesTextViewInvisible();

    void showEnableFab();

    void animateOnActivityRestart(Context context, AppCompatActivity appCompatActivity);

    void makeFabClickable();

    DatePickerDialog getDateFromDialog(int id);

}
