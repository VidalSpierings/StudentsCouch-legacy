package com.studentscouch.projectbostonfiles.db.interfaces;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface CityActivityDBInterface {

    void retrieveApartmentItems (Context context, AppCompatActivity appCompatActivity, FrameLayout no_listings_frameLayout, RecyclerView recyclerView);

}
