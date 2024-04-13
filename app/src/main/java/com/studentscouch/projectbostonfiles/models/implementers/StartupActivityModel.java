package com.studentscouch.projectbostonfiles.models.implementers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import com.studentscouch.projectbostonfiles.models.interfaces.StartupActivityModelInterface;


import static android.content.Context.MODE_PRIVATE;

public class StartupActivityModel implements StartupActivityModelInterface {

    @Override
    public void informAppUserHasLoggedIn(Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences("isAccountRegistered", MODE_PRIVATE).edit();
        editor.putBoolean("registered", true);
        editor.apply();

        // inform application that user has logged in once

    }

    @Override
    public void safeCityImgsToSharedPrefs(AppCompatActivity appCompatActivity, String encoded, String encoded2) {

        SharedPreferences.Editor editor = appCompatActivity.getSharedPreferences("background_image", MODE_PRIVATE).edit();
        editor.putString("string", encoded);
        editor.putString("string2", encoded2);

        // save encoded Strings into sharedPreferences

        editor.apply();

    }

    @Override
    public void updateCheckForPushNotifications() {



    }
}
