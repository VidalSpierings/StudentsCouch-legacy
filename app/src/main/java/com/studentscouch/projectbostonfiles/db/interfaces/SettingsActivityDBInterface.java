package com.studentscouch.projectbostonfiles.db.interfaces;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

public interface SettingsActivityDBInterface {

    void retrieveHostStatus(Context context, FrameLayout become_a_host_layout, com.google.firebase.database.DataSnapshot dataSnapshot, TextView become_a_host_textView,
                            FrameLayout progress_layout, Animation exit_anim_progress_layout);

}
