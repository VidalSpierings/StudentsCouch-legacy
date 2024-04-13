package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

public interface BookingActivity2ModelInterface {

    void saveArrivalTime(
            Context context, FrameLayout eight_to_six_framelayout,
            FrameLayout twelve_to_four_fiftynine_layout, FrameLayout five_to_ten_layout);

    void saveNumPeople(Context context, FrameLayout one_layout, FrameLayout two_layout);

    void saveInfoByForLoop(Context context, List<View> allTimeArrivalLayouts, List<View> allNumNightsLayouts);

    void setBackgroundImage(AppCompatActivity appCompatActivity, ImageView backgroundImage);

    void addBookingDate(DataSnapshot postSnapshot);

}
