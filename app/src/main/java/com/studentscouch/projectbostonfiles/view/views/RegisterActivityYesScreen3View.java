package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public interface RegisterActivityYesScreen3View {

    void checkIfInfoEnteredEditTexts(Context context, List<EditText> allEditTexts, View one_layout_view, View two_layout_view, FloatingActionButton fab);

    void retrievePreviouslyEnteredInfo(Context context, SharedPreferences prefs, EditText firstName_editText, EditText lastName_editText,
                                       EditText userName_editText, View one_layout_view, View two_layout_view, FloatingActionButton fab);

}
