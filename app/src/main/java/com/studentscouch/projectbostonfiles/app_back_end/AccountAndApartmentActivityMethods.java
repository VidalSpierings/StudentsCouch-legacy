package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import com.google.android.material.tabs.TabLayout;

import com.studentscouch.projectbostonfiles.R;

public class AccountAndApartmentActivityMethods {

    @SuppressLint("StaticFieldLeak")
    static Activity activity;

    AccountAndApartmentActivityMethods(Activity activity){
        AccountAndApartmentActivityMethods.activity = activity;
    }

    public static void initialiseTabLayout (Context context, TabLayout tablayout, Float tabLayout_org_pos_y){

        tablayout.addTab(tablayout.newTab().setText(context.getResources().getString(R.string.profile)));
        tablayout.addTab(tablayout.newTab().setText(context.getResources().getString(R.string.apartement)));

        // initialisation tabLayout. 'profile' on the left, 'apartment' right.

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabTextColors(context.getResources().getColor(R.color.textColorTertiary), context.getResources().getColor(R.color.textColorTertiary));
        tablayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.textColorTertiary));

        // background and tab scroll colors are modified for design continuity

        tabLayout_org_pos_y = tablayout.getY();

        // get original Y position from tabLayout. Is used later in code to ensure that
        // tabLayout animates back to correct position in onRestart() amongst other possible methods

    }

}
