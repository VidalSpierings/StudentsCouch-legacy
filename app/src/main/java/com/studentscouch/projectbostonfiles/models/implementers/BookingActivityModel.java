package com.studentscouch.projectbostonfiles.models.implementers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.models.interfaces.BookingActivityModelInterface;
import com.studentscouch.projectbostonfiles.observables.BookingActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.BookingActivityView;

import java.util.ArrayList;
import java.util.Calendar;

public class BookingActivityModel implements BookingActivityModelInterface {

    private int
            selectedDayInt,
            selectedMonthInt,
            selectedYearInt,

            startDateDay,
            startDateMonth,
            startDateYear;

    private String
            dayString,
            monthString,
            yearString;

    private Calendar loopCalendar;

    private ArrayList<Integer> bookingDate;

    private BookingActivityObservables observables;

    private BookingActivityView view;

    public BookingActivityModel(BookingActivityView view, BookingActivityObservables observables){

        this.view = view;
        this.observables = observables;

    }

    @Override
    public void setActivityBackgroundImage(Context context, AppCompatActivity appCompatActivity, ImageView backgroundImage) {

        SharedPreferences prefs = appCompatActivity.getSharedPreferences("background_image", Context.MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        // get background image Base 64 String and assign to String object named 'bitmap'

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        // convert (base64) String object to bitmap object

        backgroundImage.setImageBitmap(image);

        // load and set background image of city apartment is located in

    }

    @Override
    public void passMetaData(Intent i) {

        i.putExtra("date_of_arrival", dayString + "-" + monthString + "-" + yearString);

        i.putExtra("arrival_year", selectedYearInt);
        i.putExtra("arrival_month", selectedMonthInt);
        i.putExtra("arrival_day", selectedDayInt);

        i.putExtra("isTwoPeopleAllowed", observables.getIsTwoPeopleAllowed());

        i.putExtra("AID", observables.getAID());

        i.putExtra("cityVillage", observables.getCity_or_village());

        i.putExtra("street", observables.getStreet());

        i.putExtra("house_number", observables.getHouse_number());

        i.putExtra("firstNameHost", observables.getFirstNameHost());

        i.putExtra("lastNameHost", observables.getLastNameHost());

    }

    @Override
    public void addBookingDate(DataSnapshot postSnapshot) {

        // for loop adding every date user will be staying in the apartment for number of days the stay will last

        retrieveDateFromDB(postSnapshot);

        createNewDateLocally();

        addNewDateToNewList();

        addNewDateToAllDatesList();

    }

    private void addNewDateToAllDatesList(){

        observables.getBookedDatesList().add(bookingDate);

        // add bookingDate arrayList to bookedDatesList

    }

    private void addNewDateToNewList(){

        bookingDate = new ArrayList<>();

        bookingDate.add(loopCalendar.get(Calendar.DAY_OF_MONTH));
        bookingDate.add(loopCalendar.get(Calendar.MONTH));
        bookingDate.add(loopCalendar.get(Calendar.YEAR));

        /*

         * add loopCalendar date to bookingDate arrayList,
         * the bookingDate arrayList is added to local (nested) arrayList 'bookingDatesList'
         * for every loop in this for loop

         */

    }

    private void createNewDateLocally(){

        loopCalendar = Calendar.getInstance();

        // create new Calendar object

        loopCalendar.set(Calendar.DAY_OF_MONTH, startDateDay);
        loopCalendar.set(Calendar.MONTH, startDateMonth);
        loopCalendar.set(Calendar.YEAR, startDateYear);

        // set new Calendar object date equal to starting date of booking at index

    }

    private void retrieveDateFromDB(DataSnapshot postSnapshot){

        startDateDay = postSnapshot.child("startDateDay").getValue(Integer.class);
        startDateMonth = postSnapshot.child("startDateMonth").getValue(Integer.class);
        startDateYear = postSnapshot.child("startDateYear").getValue(Integer.class);

        // get starting Date of booking at index

    }

    @SuppressLint("SetTextI18n")
    public void setDate(
            Context context,
            TextView date_textView,
            RelativeLayout activity_relativeLayout, int i, int i1, int i2) {

        setGlobalVariablesValues(i, i1, i2);

        date_textView.setText(dayString + "-" + monthString + "-" + yearString);

        // insert selected date into date_textView according to Dutch date formatting.

        /*

         * Example:

         * selected date: 12th June 2020

         * dayString: 12
         * monthString: 6
         * yearString: 2020

         * Text in date_textView: 12-6-2020

         */

        view.showEnableFab();

        attemptSetSelectedDate(
                context, date_textView, activity_relativeLayout,
                i, i1, i2);

    }

    private void attemptSetSelectedDate(Context context,
                                        TextView date_textView,
                                        RelativeLayout activity_relativeLayout, int i, int i1, int i2){

        if (observables.getBookedDatesList() != null){

            for (int pos=0; pos < observables.getBookedDatesList().size(); pos++){

                if (
                        (observables.getBookedDatesList().get(pos).get(0) == selectedDayInt
                                && observables.getBookedDatesList().get(pos).get(1) == selectedMonthInt
                                && observables.getBookedDatesList().get(pos).get(2) == selectedYearInt)
                                ||
                                (observables.getBookedDatesList().get(pos).get(0) == selectedDayInt + 1
                                        && observables.getBookedDatesList().get(pos).get(1) == selectedMonthInt
                                        && observables.getBookedDatesList().get(pos).get(2) == selectedYearInt )

                ){

                    //
                    /*

                     * above if statement checks if user selected an unavailable date or 1 day after an unavailable date

                     * bookedDatesList.get(pos).get(0) = get day of unavailable date for booking with this position in array
                     * bookedDatesList.get(pos).get(1) = get month of unavailable date for booking with this position in array
                     * bookedDatesList.get(pos).get(2) = get year of unavailable date for booking with this position in array

                     * Example: get day, month, year of  14th July 2021

                     * bookedDatesList[[14, 7, 2021], [15-8-2022], [21-12-2019]]

                     * Int day = bookedDatesList.get(0).get(0) (outcome = 14)
                     * Int month = bookedDatesList.get(0).get(1) (outcome = 7)
                     * Int year = bookedDatesList.get(0).get(2) (outcome = 2021)

                     */

                    informUserDateAlreadyBooked(context, activity_relativeLayout, date_textView);

                    break;

                } else {

                    selectLegalDate(i, i1, i2, date_textView);

                }

            }

        }

    }

    @SuppressLint("SetTextI18n")
    private void selectLegalDate(int i, int i1, int i2, TextView date_textView){

        // if user selects available date, do the following

        setSelectedDateInfo(i, i1, i2);

        date_textView.setText(dayString + "-" + monthString + "-" + yearString);

        // set date textView to selected date according to Dutch date ordering

        view.showEnableFab();

        // if user selects date that is available, show text on top that displays date and make fab visible

    }

    private void setSelectedDateInfo(int i, int i1, int i2){

        yearString = String.valueOf(i);
        monthString = String.valueOf(i1 + 1);
        dayString = String.valueOf(i2);

        // convert selected date metadata to string

        selectedYearInt = i;
        selectedMonthInt = i1 + 1;
        selectedDayInt = i2;

        // convert selected date metadata to local variables

    }

    private void setGlobalVariablesValues(int i, int i1, int i2){

        yearString = String.valueOf(i);
        monthString = String.valueOf(i1);
        dayString = String.valueOf(i2);

        // convert number of index day, month and year to String object

        selectedYearInt = i;
        selectedMonthInt = i1;
        selectedDayInt = i2;

        // assign values of method variables to local variables

    }

    private void informUserDateAlreadyBooked(Context context, RelativeLayout activity_relativeLayout, TextView date_textView){

        Snackbar snackbar = Snackbar
                .make(activity_relativeLayout, context.getResources().getString(R.string.date_already_booked), Snackbar.LENGTH_LONG);
        snackbar.show();

        // inform user that they've selected an already booked date by showing a snackbar

        date_textView.setText("");

        // reset date preset in date_textView

        view.disableHideFab();

    }

}
