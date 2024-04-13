package com.studentscouch.projectbostonfiles.db.interfaces;

import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public interface MainActivityDBInterface {

    int retrieveIfNeedUpdate();

    void checkIfUnderConstruction(
            com.google.firebase.database.DataSnapshot dataSnapshot, RelativeLayout progress_layout,
            RelativeLayout progress_layout2, FloatingActionButton fab);



}
