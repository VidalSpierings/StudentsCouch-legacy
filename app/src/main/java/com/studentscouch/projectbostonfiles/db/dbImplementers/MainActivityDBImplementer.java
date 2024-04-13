package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.MainActivityDBInterface;

public class MainActivityDBImplementer implements MainActivityDBInterface {

    private com.firebase.client.DataSnapshot dataSnapshot1;

    @Override
    public int retrieveIfNeedUpdate() {

        Firebase fbRef = new Firebase(StartupMethods.DATABASE_LINK + ConstantsDB.NEEDS_UPDATE_TABLE);

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                dataSnapshot1 = dataSnapshot;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return dataSnapshot1.getValue(Integer.class);

    }

    @Override
    public void checkIfUnderConstruction(DataSnapshot dataSnapshot, RelativeLayout progress_layout, RelativeLayout progress_layout2, FloatingActionButton fab) {

        if (dataSnapshot.getValue(Integer.class) == 1) {

            showConstructionScreenDisableFab(progress_layout, progress_layout2, fab);

        } else if (dataSnapshot.getValue(Integer.class) == 0) {

            hideAllLayOverScreens(progress_layout, progress_layout2);

        }

    }

    private void hideAllLayOverScreens(RelativeLayout progress_layout, RelativeLayout progress_layout2){

        // if app is not under construction, do the following

        progress_layout.removeAllViews();

        progress_layout2.setVisibility(View.INVISIBLE);

        // empty out all views from loading screen and make 'Under construction' view invisible

    }

    private void showConstructionScreenDisableFab(RelativeLayout progress_layout, RelativeLayout progress_layout2, FloatingActionButton fab){

        // if app is under construction, do the following

        progress_layout.removeAllViews();

        progress_layout2.setVisibility(View.VISIBLE);

        fab.setOnClickListener(null);

        // make fab unclickable, empty out all views from loading screen, make 'under contruction' screen overlay visible

    }

}
