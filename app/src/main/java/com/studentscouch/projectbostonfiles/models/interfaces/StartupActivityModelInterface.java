package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public interface StartupActivityModelInterface {

    void informAppUserHasLoggedIn(Context context);

    void safeCityImgsToSharedPrefs(AppCompatActivity appCompatActivity, String encoded, String encoded2);

    void updateCheckForPushNotifications();

}
