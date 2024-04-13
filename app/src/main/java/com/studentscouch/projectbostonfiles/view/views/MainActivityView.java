package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public interface MainActivityView {

    void checkIfAppNeedsUpdate(
            Context context, FrameLayout log_in_layout, FrameLayout register_layout,
            FloatingActionButton fab, FrameLayout progress_layout, FrameLayout progress_layout2,
            FrameLayout progress_layout3, Animation exit_anim_progress_layout, int entry,
            RelativeLayout main_layout);

    void checkActivityUserCameFrom(final Context context, int isFromLogOut, final AppCompatActivity appCompatActivity, final List<View> viewsInLayout,
                                   Animation enter_anim, final RelativeLayout relativeLayout2, final View header_image, final View layout_center,
                                   final TextView log_in_textView, final TextView register_textView, final TextView pb_textView, final FrameLayout log_in_layout,
                                   final FrameLayout register_layout, final Animation enter_anim_progress_layout, final RelativeLayout progress_layout,
                                   final FloatingActionButton fab, final TextView error_textView, final Typeface tp, final ViewGroup relativeLayout,
                                   final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener);

}
