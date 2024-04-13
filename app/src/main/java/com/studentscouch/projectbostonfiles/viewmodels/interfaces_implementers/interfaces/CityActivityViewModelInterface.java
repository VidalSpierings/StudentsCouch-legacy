package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.studentscouch.projectbostonfiles.view_adapters.CityActivityInformation;

import java.util.ArrayList;

public interface CityActivityViewModelInterface {

    void showWindowIfNoListingsFound(
            ArrayList<CityActivityInformation> information, FrameLayout no_listings_frameLayout, RecyclerView recyclerView);

    void makeRecyclerViewInvisibleStartNextActivity(Context context, RecyclerView recyclerView);

}
