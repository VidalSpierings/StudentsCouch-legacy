package com.studentscouch.projectbostonfiles.models.implementers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.models.interfaces.BookingActivity2ModelInterface;
import com.studentscouch.projectbostonfiles.observables.BookingActivity2Observables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookingActivity2Model implements BookingActivity2ModelInterface {

    private int

    startDateDay,
            startDateMonth,
            startDateYear;

    private Calendar loopCalendar;

    private ArrayList<Integer> bookingDate;

    private BookingActivity2Observables observables;

    public BookingActivity2Model (BookingActivity2Observables observables) {

        this.observables = observables;

    }

    @Override
    public void saveArrivalTime(Context context, FrameLayout eight_to_six_framelayout, FrameLayout twelve_to_four_fiftynine_layout, FrameLayout five_to_ten_layout) {

        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = context.getSharedPreferences("bookingInfo", MODE_PRIVATE).edit();

        if (eight_to_six_framelayout.isSelected()) {

            editor.putInt("time_of_arrival", 1);

            // if user selected to arrive between 8am and 11:59, save this info in sharedPreferences

        } else if (twelve_to_four_fiftynine_layout.isSelected()) {

            editor.putInt("time_of_arrival", 2);

            // if user selected to arrive between 12 and 4:59PM, save this info in sharedPreferences

        } else if (five_to_ten_layout.isSelected()) {

            editor.putInt("time_of_arrival", 3);

            // if user selected to arrive between 5PM and 10PM, save this info in sharedPreferences

        }

    }

    @Override
    public void saveNumPeople(Context context, FrameLayout one_layout, FrameLayout two_layout) {

        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = context.getSharedPreferences("bookingInfo", MODE_PRIVATE).edit();

        if (one_layout.isSelected()){

            editor.putInt("num_people", 1);

            // if user selected to book make the booking for one person, save this info in sharedPreferences

        } else if (two_layout.isSelected()){

            editor.putInt("num_people", 2);

            // if user selected to make the booking for two people, save this info in sharedPreferences

        }

    }

    @Override
    public void saveInfoByForLoop(Context context, List<View> allTimeArrivalLayouts, List<View> allNumNightsLayouts) {

        SharedPreferences.Editor editor = context.getSharedPreferences("bookingInfo", MODE_PRIVATE).edit();

        for (int pos = 0; pos < allTimeArrivalLayouts.size(); pos++) {

            if (allTimeArrivalLayouts.get(pos).isSelected()) {

                editor.putInt("time_of_arrival", pos + 1);

            }

        }

        for (int pos = 0; pos < allNumNightsLayouts.size(); pos++) {

            if (allNumNightsLayouts.get(pos).isSelected()) {

                editor.putInt("num_nights", pos + 1);

            }

        }

        editor.apply();

    }

    @Override
    public void setBackgroundImage(AppCompatActivity appCompatActivity, ImageView backgroundImage) {

        SharedPreferences prefs = appCompatActivity.getSharedPreferences("background_image", Context.MODE_PRIVATE);

        String bitmap = prefs.getString("string", null);

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        // convert base64 String format String object to Bitmap object

        backgroundImage.setImageBitmap(image);

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
}
