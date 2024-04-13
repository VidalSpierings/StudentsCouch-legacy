package com.studentscouch.projectbostonfiles.db.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;

public interface ApartmentActivityDBInterface {

void retrieveCityVillage (DataSnapshot dataSnapshot);

void retrieveApartmentTitle (DataSnapshot dataSnapshot);

void retrieveApartmentDesc (DataSnapshot dataSnapshot);

void retrieveApartmentHouseNumber (DataSnapshot dataSnapshot);

void retrieveApartmentLocationID (DataSnapshot dataSnapshot);

void retrieveStreetName (DataSnapshot dataSnapshot);

void retrievePricePerNight (DataSnapshot dataSnapshot);

void retrieveNumRatings (DataSnapshot dataSnapshot);

void retrieveRatingAverage (DataSnapshot dataSnapshot);

void retrieveIsTwoPeopleAllowed (DataSnapshot dataSnapshot);

void checkIfPlatformOpenForNonHosts ();

void loadApartmentInfo(DataSnapshot dataSnapshot);


    void getDataFromDB(Context context, ViewGroup viewGroup, Activity activity, ScrollView scrollView, TabLayout tabLayout,
                       float scrollView_org_pos_y, float tabLayout_org_pos_y, FrameLayout book_now_button);

}
