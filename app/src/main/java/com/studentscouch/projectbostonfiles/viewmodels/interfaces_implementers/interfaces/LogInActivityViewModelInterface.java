package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public interface LogInActivityViewModelInterface {

    void setFabClickAbilityAccToInternetConnection(Context context);

    void launchStartupActivity(Context context, AppCompatActivity appCompatActivity,
                               FrameLayout progress_layout, ImageView header_image);

    String textFilterFunc(int start, int end, CharSequence source);

    void onFabClicked(Context context, AppCompatActivity appCompatActivity,
                      EditText editText, ViewGroup viewGroup,
                      TextView error_message, final FrameLayout progress_layout, final Animation exit_anim,
                      final FirebaseAuth auth, final ImageView header_image, final List<String> monthNames,
                      Typeface tp);

    void onCheckBoxClickedListener(ImageView checkboxImage);
}
