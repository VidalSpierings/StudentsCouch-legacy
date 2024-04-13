package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public interface RegisterActivityScreen2View {

    void checkEditTexts(final Context context, final FrameLayout progress_layout, final FloatingActionButton fab,
                        final EditText email_edittext, final EditText password_edittext, final Animation exit_anim_progress_layout,
                        final TextView error_message, final EditText firstName_edittext, final EditText lastName_edittext, final EditText spinner,
                        final EditText birthdate_edittext, final AppCompatActivity appCompatActivity, final String gender, final String countryCode, final List<View> animatingView, final List<Boolean> setStartingDelay,
                        final Typeface tp, boolean isRegistered, String email);

    void initDatePicker(Context context, int i, int i1, int i2,
                        int year2, int month2, int day2, EditText birthdate_edittext,
                        TextView error_message, Typeface tp, FloatingActionButton fab,
                        EditText firstName_editText, EditText lastNameEditText,
                        EditText email_edittext, EditText password_edittext, EditText spinner);

    void enterPreviouslySubmittedInfo(Context context, SharedPreferences prefs, EditText firstName_edittext, EditText lastName_edittext,
                                      EditText email_edittext, EditText birthdate_edittext, EditText spinner, ImageView gender_male,
                                      ImageView gender_female, String gender, boolean isSelected);

}
