package com.studentscouch.projectbostonfiles.view.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.db.interfaces.BookingActivitiesDBInterface;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public interface BookingActivity2View {

    View getRootView();

    void initViews(AppCompatActivity appCompatActivity, Context context);

    void initData(AppCompatActivity appCompatActivity, Context context);

    void retrieveAllBookings (Context context, Activity activity);

    void retrieveSubleasedNights (Context context, DataSnapshot dataSnapshot);

    void startNextActivity(Context context);

    void initButtonVisibilityAccToNumNightsLeft(Context context);

    void animateActivityEnterAndReEnterTransition(Context context, AppCompatActivity appCompatActivity);
}
