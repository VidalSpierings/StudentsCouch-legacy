package com.studentscouch.projectbostonfiles.db.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

import java.util.ArrayList;

public interface StartupActivityDBInterface {

    void initDB(Context context, AppCompatActivity appCompatActivity);

    void checkIfEUBlockActive(final Context context, Activity activity);

    void retrieveUserHostStatus ();

    String retrieveUserCountryCode();

    void checkIfMostRecentWarningInfoRead(AppCompatActivity appCompatActivity);

    void checkIfIbanAdded(AppCompatActivity appCompatActivity);

    void retrieveStatusOfAllUserBookings(AppCompatActivity appCompatActivity);

    void retrieveUnlockedCountries(final Context context);

    void retrieveBookingsRelatedInfo(Context context, AppCompatActivity appCompatActivity);

}
