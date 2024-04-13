package com.studentscouch.projectbostonfiles.view.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public interface ApartmentActivityView {

    void initViews(Context context, Activity activity);

    void initData(Context context, ViewGroup container, Activity activity);

    View getFragmentRootView(Context context, ViewGroup viewGroup);

    void setRatingsTextViewGrammar (Context context);

    void insertRetrievedInfoIntoTextViews(Context context);

    void checkIfUserViewingOwnApartment ();

    void checkIfTwoPeoplePerBookingAllowed (Context context);

    void informUserApartmentOutOfSubleasedNights(final Context context);

    void initSwipeDotsLayout(Context context);

    void makeSubleasedNightsLayoutDisappear();

    void setExitAnimListener(Context context, Activity activity);

    ImageView getRatingImageView();

    void initLayoutOnStart();

    void startActivityEnterAnimations(Context context);

}
