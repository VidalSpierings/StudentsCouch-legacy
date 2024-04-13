package com.studentscouch.projectbostonfiles.db.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public interface ProfileActivityDBInterface {

    void retrieveUserInfo(final Context context, final Animation exit_anim_progress_layout, final Activity activity,
                          final FrameLayout progress_layout, final ImageView gender_imageView, final TextView age_textView,
                          final TextView name_textView, final TextView rating_right_textView, final TextView rating_left_textView,
                          final ImageView profile_imageView, final ScrollView scrollView,
                          final Float scrollView_org_pos_y, DatabaseReference fbRef);

}
