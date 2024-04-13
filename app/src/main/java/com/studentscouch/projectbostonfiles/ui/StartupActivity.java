package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.studentscouch.projectbostonfiles.miscellaneous_files.NotificationIntentService;
import com.studentscouch.projectbostonfiles.view.viewImplementers.StartupActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.StartupActivityView;
import com.studentscouch.projectbostonfiles.dialogs.CustomDialogWithButton;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.StartupActivityViewModel;

public class StartupActivity extends AppCompatActivity implements CustomDialogWithButton.CustomDialogWithButtonListener {

    StartupActivityView view;
    StartupActivityViewModel viewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new StartupActivityViewImplementer(this, null);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, 0);

        finishAffinity();

    }

    @Override
    protected void onStart() {
        super.onStart();

        view.initLayout();
        view.makeTextViewInvisibleWhenLoading(this);

    }

    /*

    public void openNewWarningDialog(String UID){

        Bundle args = new Bundle();
        args.putString("UID", UID);

        // create metadata for intent

        CustomDialogWithButton customDialogWithButton = new CustomDialogWithButton();

        customDialogWithButton.setArguments(args);

        // pass metadata to intent

        customDialogWithButton.show(getSupportFragmentManager(), "test");

    }

    */

    @Override
    public void applyTexts(String description_text, String buttonText) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        viewModel = new StartupActivityViewModel(view, view.getCurrentObservableInstance());

        // Check which request we're responding to
        viewModel.retrieveSelectedLocation(
                getApplicationContext(), this, data, requestCode, resultCode);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        /*

        progress_layout.clearAnimation();

        progress_layout.setVisibility(View.INVISIBLE);

        */

        // make loading screen disappear

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
