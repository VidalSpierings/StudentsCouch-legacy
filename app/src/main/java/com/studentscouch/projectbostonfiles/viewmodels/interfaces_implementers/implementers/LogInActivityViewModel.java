package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.dbImplementers.LoginActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.LoginActivityDBInterface;
import com.studentscouch.projectbostonfiles.observables.LogInActivityObservables;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.view.views.LogInActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.LogInActivityViewModelInterface;

import java.util.List;

public class LogInActivityViewModel implements LogInActivityViewModelInterface {

    private LogInActivityView view;
    private LogInActivityObservables observables;

    public LogInActivityViewModel(LogInActivityView view, LogInActivityObservables observables){

        this.observables = observables;

        this.view = view;
    }

    @Override
    public void setFabClickAbilityAccToInternetConnection(Context context) {

        FloatingActionButton floatingActionButton = view.getFloatingActionButton();

        if (StartupMethods.isOnline(context)){

            floatingActionButton.setClickable(true);

            // enable fab clicking when internet connection is found (this won't make the fab visible, only clickable)

        } else {

            floatingActionButton.setClickable(false);

            // disable fab clicking when internet connection is not found

        }

    }

    @Override
    public void launchStartupActivity(Context context, AppCompatActivity appCompatActivity, FrameLayout progress_layout, ImageView header_image) {

        progress_layout.setVisibility(View.INVISIBLE);

        ActivityOptionsCompat options;

        options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, header_image, context.getResources().getString(R.string.sharedactivitytransition_splashscreen_startupactivity));

        // use sharedActivityTransition with top image, image will animate into top image toolbar StartupActivity

        Intent i = new Intent(appCompatActivity, StartupActivity.class);

        appCompatActivity.startActivity(i, options.toBundle());

        // start new activity

    }

    @Override
    public String textFilterFunc(int start, int end, CharSequence source) {

        for (int i = start; i < end; i++) {
            int type = Character.getType(source.charAt(i));

            //System.out.println("Type : " + type);

            if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                return "";
            }

            // if illegal character entered, return empty string in place of illegal character

        }
        return null;

    }

    @Override
    public void onFabClicked(
            Context context, AppCompatActivity appCompatActivity,
            EditText editText, ViewGroup viewGroup,
            TextView error_message, final FrameLayout progress_layout, final Animation exit_anim,
            final FirebaseAuth auth, final ImageView header_image, final List<String> monthNames, Typeface tp) {

        //initMethod(context, viewGroup);

        view.updateLayoutAccToSignIn();

        view.saveOrRemoveEmail(context);

        checkIfAllFieldsEnterProceedCancelLogin(
                context, appCompatActivity, auth, progress_layout, exit_anim,
                monthNames, viewGroup, header_image, error_message, tp);

    }

    @Override
    public void onCheckBoxClickedListener(ImageView checkboxImage) {

            if (!observables.isChecked()) {

                checkboxImage.setVisibility(View.VISIBLE);

                // set checkbox to selected

                observables.setChecked(true);

            } else {

                checkboxImage.setVisibility(View.INVISIBLE);

                // set checkbox to unselected

                observables.setChecked(false);

            }

    }

    private void checkIfAllFieldsEnterProceedCancelLogin(
            Context context, AppCompatActivity appCompatActivity, FirebaseAuth auth,
            FrameLayout progress_layout, Animation exit_anim, List<String> monthNames,
            ViewGroup viewGroup, ImageView header_image, TextView error_message, Typeface tp) {

        String username1 = view.getEmailString();
        String password = view.getPasswordString();

        if (TextUtils.isEmpty(username1) || TextUtils.isEmpty(password)) {

            view.informUserToEnterInfo(context);

        } else {

            logInUserProtocol(
                    context, appCompatActivity, auth, progress_layout, exit_anim,
                    monthNames, viewGroup, username1, password, header_image);

            logInShowErrorMessageIfNoInternetConnection(context, error_message, tp);

        }
    }

    private void logInShowErrorMessageIfNoInternetConnection(Context context, TextView error_message, Typeface tp){

        if (!StartupMethods.isOnline(context)) {

            view.enableUIElements();

            StartupMethods.showErrorMessage(context, error_message, context.getResources().getString(R.string.internet_connection_non_existent), tp);

            // if internet connection was not found, inform user by showing a toast

        }

    }

    private void logInUserProtocol(
            Context context, AppCompatActivity appCompatActivity, FirebaseAuth auth,
            FrameLayout progress_layout, Animation exit_anim, List<String> monthNames,
            ViewGroup viewGroup, String username1, String password, ImageView header_image){

        if (StartupMethods.isOnline(context)) {

            // if internet connection is found, do the following

            view.showProgressLayout();

            LoginActivityDBInterface db = new LoginActivityDBImplementer(view, observables);

            auth.signInWithEmailAndPassword(username1, password).addOnCompleteListener(task -> db.signInUser(
                    context, appCompatActivity, auth, progress_layout,
                    exit_anim, monthNames, viewGroup, username1,
                    password, header_image));

        }

    }

}
