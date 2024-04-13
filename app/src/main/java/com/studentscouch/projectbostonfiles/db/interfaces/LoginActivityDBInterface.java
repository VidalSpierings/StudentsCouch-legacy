package com.studentscouch.projectbostonfiles.db.interfaces;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface LoginActivityDBInterface {

    String retrieveUserCountry(DataSnapshot dataSnapshot);

    void retrieveMiscUserData(Context context, Task<AuthResult> finalTask, AppCompatActivity appCompatActivity,
                              FirebaseAuth auth,
                              List<String> monthNames, ViewGroup viewGroup,
                              FrameLayout progress_layout, ImageView header_image);

    void signInUser(Context context, AppCompatActivity appCompatActivity,
                     FirebaseAuth auth,
                     FrameLayout progress_layout, Animation exit_anim,
                     List<String> monthNames, ViewGroup viewGroup,
                     String username1, String password, ImageView header_image);

}
