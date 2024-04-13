package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public interface RegisterActivityYesScreen2View {

    void showImageOptions(ImageView viewpager, ImageView viewpager2, ImageView viewpager3);

    void checkIfInfoEnteredEverywhere(Context context, EditText firstName_editText,
                                      EditText lastName_editText, EditText userName_editText,
                                      FloatingActionButton fab);

    void retrieveApartmentInfo(SharedPreferences prefs, EditText lastName_editText, EditText userName_editText, ImageView viewpager2,
                               ImageView viewpager3, FloatingActionButton fab);

}
