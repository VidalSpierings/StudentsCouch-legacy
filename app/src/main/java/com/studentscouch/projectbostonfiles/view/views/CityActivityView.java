package com.studentscouch.projectbostonfiles.view.views;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.db.interfaces.CityActivityDBInterface;
import com.studentscouch.projectbostonfiles.view_adapters.CityActivityInformation;

import java.util.ArrayList;

public interface CityActivityView {

    View getRootView();

    void initViews(AppCompatActivity appCompatActivity, Context context);

    void initData(AppCompatActivity appCompatActivity, Context context);

    void initDB(AppCompatActivity appCompatActivity, Context context);

    void retrieveAllApartmentItems(AppCompatActivity appCompatActivity, Context context);

    void animateActivityExitTransition(Context context, Activity activity);

    void makeProgressLayoutInvisibleIfLoadingFinished();

    void animateActivityReEnterTransition(Context context, Activity activity);

    void makeBackgroundUIinvisible();

    void setRecyclerViewAdapter(Context context, AppCompatActivity appCompatActivity, ArrayList<CityActivityInformation> information);

    Drawable getBackGroundDrawable();

    void animateActivityExitTransition(Context context, AppCompatActivity appCompatActivity, Intent intent);
}
