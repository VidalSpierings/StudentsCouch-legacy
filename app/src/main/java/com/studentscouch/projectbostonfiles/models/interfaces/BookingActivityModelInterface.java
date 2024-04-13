package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;

public interface BookingActivityModelInterface {

    void setActivityBackgroundImage(Context context, AppCompatActivity appCompatActivity, ImageView backgroundImage);

    void passMetaData(Intent i);

    void addBookingDate(DataSnapshot postSnapshot);

}
