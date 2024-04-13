package com.studentscouch.projectbostonfiles;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MyApplication {

    public static String NUM_NIGHTS_LEFT_STRING_N_A = "123456789";

    public static boolean isServicesOkRegisterActivities(
            Context context, AppCompatActivity appCompatActivity, int ERROR_DIALOG_REQUEST, TextView error_message,
            Typeface tp) {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appCompatActivity);

        if (available == ConnectionResult.SUCCESS) {

            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(appCompatActivity, available, ERROR_DIALOG_REQUEST);
            dialog.show();

            // if app could not connect to Google Services, show error dialog

        } else {

            error_message.setVisibility(View.VISIBLE);
            StartupMethods.showErrorMessageTemporarily(context, 3000, error_message, context.getResources().getString(R.string.maps_error), tp);

            // in case of an unknown error, show user toast informing them that a Google Maps Error has appeared

        }
        return false;

    }

    public static boolean isServicesOkLoggedInActivities(AppCompatActivity appCompatActivity, Context context, int ERROR_DIALOG_REQUEST) {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        if (available == ConnectionResult.SUCCESS) {

            return true;

            // if connection was successful, return true

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(appCompatActivity, available, ERROR_DIALOG_REQUEST);
            dialog.show();

            // if Google Play Services isn't available, inform user by showing a google generated dialog

        } else {

            Toast.makeText(context, context.getResources().getString(R.string.maps_error), Toast.LENGTH_SHORT).show();

            // if user connection to Google Maps cannot be made, inform user by showing a toast

        }

        return false;

    }

    public static void makeProgressLayoutDisappear(FrameLayout progress_layout, Animation exit_anim_progress_layout){

        progress_layout.clearAnimation();
        progress_layout.startAnimation(exit_anim_progress_layout);
        progress_layout.setVisibility(View.INVISIBLE);

    }

    public static void makeProgressLayoutDisappearRelativeLayout(RelativeLayout progress_layout, Animation exit_anim_progress_layout){

        progress_layout.clearAnimation();
        progress_layout.startAnimation(exit_anim_progress_layout);
        progress_layout.setVisibility(View.INVISIBLE);

    }

    public static void makeProgressLayoutAppearRelativeLayout(RelativeLayout progress_layout, Animation enter_anim_progress_layout){

        progress_layout.clearAnimation();
        progress_layout.startAnimation(enter_anim_progress_layout);
        progress_layout.setVisibility(View.VISIBLE);

    }

    public static void makeProgressLayoutAppearFrameLayout(FrameLayout progress_layout, Animation enter_anim_progress_layout){

        progress_layout.clearAnimation();
        progress_layout.startAnimation(enter_anim_progress_layout);
        progress_layout.setVisibility(View.VISIBLE);

    }

}
