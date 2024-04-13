package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.GregorianCalendar;
import java.util.List;

public interface BookingActivityViewModelInterface {

    void startNextActivity(Context context, AppCompatActivity appCompatActivity);

    void setNumNightsVisibilityAccToNightsTillNextBooking(
            List<GregorianCalendar> calendarObjects, List<View> buttonLayouts, int yearInt, int monthInt, int dayInt);

}
