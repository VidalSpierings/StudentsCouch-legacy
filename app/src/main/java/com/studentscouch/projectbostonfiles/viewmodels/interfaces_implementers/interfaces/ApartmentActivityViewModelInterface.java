package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.google.android.material.tabs.TabLayout;

public interface ApartmentActivityViewModelInterface {

    void toggleButtonClicked(final Context context, CompoundButton compoundButton);

    void launchEditActivity(Context context, Activity activity, View edit_button_view);

    void launchGmapsIntent(final Context context);

    void setBookNowButtonOnClickListener(Context context, Activity activity,
                                         ScrollView scrollView, TabLayout tabLayout, float scrollView_org_pos_y, float tabLayout_org_pos_y,
                                         FrameLayout book_now_button);

    void launchBookNowActivity(Context context, Activity activity,
                               ScrollView scrollView, TabLayout tabLayout, float scrollView_org_pos_y, float tabLayout_org_pos_y);

    void launchBookNowActivityFunc(Activity activity, Context context);

}
