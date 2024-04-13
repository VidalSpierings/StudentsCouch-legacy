package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.studentscouch.projectbostonfiles.view_adapters.CityActivityInformation;
import com.studentscouch.projectbostonfiles.view_adapters.RecyclerViewAdapter;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.CityActivityViewModelInterface;

import java.util.ArrayList;

public class CityActivityViewModel implements CityActivityViewModelInterface {

    @Override
    public void showWindowIfNoListingsFound(ArrayList<CityActivityInformation> information, FrameLayout no_listings_frameLayout, RecyclerView recyclerView) {

        if (information.isEmpty()){

            no_listings_frameLayout.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(null);

            // if no items are retrieved, don't add items to recyclerview and show layout informing user that no items could be retrieved

        }

    }

    @Override
    public void makeRecyclerViewInvisibleStartNextActivity(Context context, RecyclerView recyclerView) {

        recyclerView.setVisibility(View.INVISIBLE);

        context.startActivity(RecyclerViewAdapter.i);

        // set item view of apartments invisible and start AccountandApratmentActivity
        // with corresponding apartment data

    }
}
