package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentscouch.projectbostonfiles.miscellaneous_files.NotificationIntentService;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public interface StartupActivityView {

    View getRootView();

    void initViews(AppCompatActivity appCompatActivity, Context context);

    void initData(AppCompatActivity appCompatActivity, Context context, );

    void openNewAddApartementDetailsDialog(String UID, AppCompatActivity appCompatActivity);

    void informUserPlaceInIllegalCountry(Context context);

    void informUserSelecLocOutOfBounds(Context context);

    StartupActivityObservables getCurrentObservableInstance();

    void informUserSomethingWentWrong(Context context, IOException e);

    void prepareLaunchAccountApartmentActivity(Context context, AppCompatActivity appCompatActivity, Bitmap bitmap1, Bitmap bitmap2);

    void freezeLayoutInteraction();

    void informUserErrorSelectingPlace(Context context);

    void makeProgressLayoutAppear();

    void initLayoutForItemSelected(String city);

    TextView getTextView();

    void initCityItem(Bitmap bitmap1);

    void startExitAnims();

    void initLayout();

    void makeTextViewInvisibleWhenLoading(Context context);

}
