package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.studentscouch.projectbostonfiles.models.implementers.BookingActivityModel;
import com.studentscouch.projectbostonfiles.observables.BookingActivityObservables;
import com.studentscouch.projectbostonfiles.ui.BookingActivity2;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.BookingActivityViewModelInterface;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class BookingActivityViewModel implements BookingActivityViewModelInterface {

    private BookingActivityObservables observables;
    private Calendar calendar;
    private GregorianCalendar cal;

    private BookingActivityModel model;

    public BookingActivityViewModel(BookingActivityModel model, BookingActivityObservables observables){

        this.observables = observables;
        this.model = model;

    }

    @Override
    public void startNextActivity(Context context, AppCompatActivity appCompatActivity) {

        Intent i = new Intent(context, BookingActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        model.passMetaData(i);

        appCompatActivity.startActivity(i);

        // start BookingActivity2 and pass required data to activity

    }

    @Override
    public void setNumNightsVisibilityAccToNightsTillNextBooking(List<GregorianCalendar> calendarObjects, List<View> buttonLayouts, int yearInt, int monthInt, int dayInt) {

        for (int pos = 0; pos < calendarObjects.size(); pos++) {

            initNewCalObjCurrDate();

            initNewCalObjCurrDatePlusIndex(yearInt, monthInt, dayInt, pos);

            makeAccButtonsVisibleAndInvisible(calendarObjects, buttonLayouts, pos);



        }

    }

    private void makeAccButtonsVisibleAndInvisible(List<GregorianCalendar> calendarObjects, List<View> buttonLayouts, int pos){

        buttonLayouts.get(pos).setVisibility(View.VISIBLE);

        // set all buttons visible

        if (cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {

            // if selected date precedes next booking by one day, do the following

            makeButtonsVisibleInvisible(calendarObjects, buttonLayouts, pos);

        }

    }

    private void makeButtonsVisibleInvisible(List<GregorianCalendar> calendarObjects, List<View> buttonLayouts, int pos){

        for (int pos2 = 0; pos2 < calendarObjects.size(); pos2++) {

            buttonLayouts.get(pos2).setVisibility(View.INVISIBLE);

            // make all views except '1 night' view invisible

            if (pos2 <= pos) {

                buttonLayouts.get(pos2).setVisibility(View.VISIBLE);

            }


        }

    }

    private void initNewCalObjCurrDatePlusIndex(int yearInt, int monthInt, int dayInt, int pos){

        calendar = GregorianCalendar.getInstance();

        calendar.set(yearInt, monthInt - 1, dayInt);

        calendar.add(Calendar.DATE, pos + 2);

        // create instance of calendar object with selected date by user, increment day by two plus index of the current item

    }

    private void initNewCalObjCurrDate(){

        cal = (GregorianCalendar) GregorianCalendar.getInstance();

        // create new GregorianCalendar object

        cal.set(Calendar.YEAR,  observables.getBookedDatesList().get(0).get(2));
        cal.set(Calendar.MONTH, observables.getBookedDatesList().get(0).get(1) - 1);
        cal.set(Calendar.DAY_OF_MONTH, observables.getBookedDatesList().get(0).get(0));

        // set calendar object equal to date of booking at index

    }

}
