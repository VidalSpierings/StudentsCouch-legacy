package com.studentscouch.projectbostonfiles.db.interfaces;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.view.views.BookingActivityView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public interface BookingActivitiesDBInterface {

    void initDBBookingActivity(AppCompatActivity appCompatActivity, Context context, ViewGroup viewGroup);

    void retrieveNumPeopleAllowedPerNight();

    void retrieveAllBookingDates (List<GregorianCalendar> calendarObjects, List<View> buttonLayouts);

    void retrieveBookingAtIndex(Context context, DataSnapshot postSnapshot, Activity activity);

    void retrieveBookingAtIndexBookingActivity1(Context context, ArrayList<ArrayList<Integer>> bookedDatesList, Activity activity, ViewGroup viewGroup);

    void initDBFuncBookingActivity2(Context context, List<GregorianCalendar> calendarObjects, List<View> allNumNightsLayouts,
                                    FloatingActionButton fab,
                                    AppCompatActivity appCompatActivity,
                                    Activity activity, ViewGroup viewGroup);

    void retrievePricePerNight();

}