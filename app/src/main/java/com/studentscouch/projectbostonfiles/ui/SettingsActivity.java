package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.SettingsActivityMethods;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private FrameLayout
            become_a_host_layout,
            delete_account_layout,
            change_password_layout,
            log_out_layout;

    private TextView
            become_a_host_textView,
            change_password_textView,
            delete_account_textView,
            log_out_textView;

    private ScrollView scrollView;

    private float scrollView_org_pos_y;

    private DatabaseReference fbRef;

    private FrameLayout progress_layout;

    private Animation
            enter_anim_progress_layout,
            exit_anim_progress_layout;

    private String UID;

    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        linkXmlElementsToJavaVariables();

        setBackgroundImage();

        initAnims();

        makeProgressLayoutAppear();

        retrieveDataFromPreviousActivity();

        Firebase.setAndroidContext(this);

        getOriginalYpositionLayoutElements();

        setTypefaces();

        animateActivityTransition();

        SettingsActivityMethods.animateActivityTransition(getApplicationContext(), scrollView, scrollView_org_pos_y);

        // make loading screen appear

        retrieveUserHostStatusFunc();

        become_a_host_layout.setOnClickListener(view -> {

            if (StartupMethods.isOnline(getApplicationContext())) {

                deselectRegisterApartmentDetailsLaterRegProtocol();

                setMainActivityYesNoSelected();

                launchFirstApartmentRegistrationActivity();

            }

            if(!StartupMethods.isOnline(getApplicationContext()))

            {

                StartupMethods.openNewInternetConnectionLostDialog(SettingsActivity.this);

                // if internet connection could not be found, inform user by showing a custom dialog

            }

        });

        SettingsActivityMethods.initialiseBecomeHostButton(become_a_host_layout, getApplicationContext(), this);

        log_out_layout.setOnClickListener(view -> {

            setRememberEmailFalse();

           setUserRegisteredFalse();

           emptyUsernamePreferences();

            FirebaseAuth.getInstance().signOut();

            // sign out user

            launchMainActivity();

            SettingsActivityMethods.initialiseLogOutButton(getApplicationContext(), log_out_layout);

        });

        setDeleteAccountButtonListenerFunc();

        change_password_layout.setOnClickListener(
                view -> fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ConstantsDB.ERROR));

        become_a_host_layout.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, 0);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        animateActivityTransition();

    }

    public void animateActivityTransition (){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(
                scrollView, "translationY",
                metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);

        // create objectAnimator object

        anim.setDuration(400);

        // set animation duration

        anim.start();

        scrollView.setVisibility(View.VISIBLE);

    }

    private void linkXmlElementsToJavaVariables(){

        backgroundImage = findViewById(R.id.backgroundImage);

        become_a_host_layout = findViewById(R.id.become_a_host_layout);
        change_password_layout = findViewById(R.id.change_password_layout);
        delete_account_layout = findViewById(R.id.delete_password_layout);
        log_out_layout = findViewById(R.id.log_out_layout);
        progress_layout = findViewById(R.id.progress_layout);

        become_a_host_textView = findViewById(R.id.become_a_host_textView);
        change_password_textView = findViewById(R.id.change_password_textView);
        delete_account_textView = findViewById(R.id.delete_password_textView);
        log_out_textView = findViewById(R.id.log_out_textView);

        scrollView = findViewById(R.id.scrollView);

        // link java variables to xml layout views

    }

    private void setBackgroundImage(){

        SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        backgroundImage.setImageBitmap(image);

    }
    private void initAnims(){

        enter_anim_progress_layout = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.visibility_fade_in_animation);
        exit_anim_progress_layout = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.visibility_fade_out_animation);

        // load fade in and fade out animations

        enter_anim_progress_layout.setDuration(200);
        exit_anim_progress_layout.setDuration(200);

        // load animations that will most likely be performed

    }

    private void makeProgressLayoutAppear(){

        progress_layout.startAnimation(enter_anim_progress_layout);
        progress_layout.setVisibility(View.VISIBLE);

    }

    private void retrieveDataFromPreviousActivity(){

        UID = Objects.requireNonNull(getIntent().getExtras()).getString("UID");

        if (Objects.requireNonNull(UID).equals("")){

            UID = ConstantsDB.UNKNOWN_UID;

        }

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        become_a_host_textView.setTypeface(tp);
        change_password_textView.setTypeface(tp);
        delete_account_textView.setTypeface(tp);
        log_out_textView.setTypeface(tp);

        // initalise and set typeface to all textViews in layout

    }

    private void getOriginalYpositionLayoutElements(){

        scrollView_org_pos_y = scrollView.getY();

    }

    private void retrieveUserHostStatusFunc(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + FirebaseAuth.getInstance().getUid()
                        + ConstantsDB.HOST_STATUS_URL_REFERENCE);

        // DB link to hostStatus of user (host, non-host or has yet to enter apartment details)

        SettingsActivityMethods.retrieveUserHostStatus(
                getApplicationContext(), fbRef, become_a_host_layout, become_a_host_textView,
                progress_layout, exit_anim_progress_layout
        );

        // retrieve host status of current user

    }

    private void setMainActivityYesNoSelected(){

        SharedPreferences sharedPreferences = getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("Choice", 1);

        editor.apply();

    }

    private void deselectRegisterApartmentDetailsLaterRegProtocol(){

        SharedPreferences prefs = getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = prefs.edit();

        // retrieve sharedPreferences

        editor2.putInt("isNotNowSelected", 0).apply();

        // insert 'yes' for whether or not to register as a host

    }

    private void launchFirstApartmentRegistrationActivity(){

        Intent i = new Intent(SettingsActivity.this, RegisterActivityYesScreen1.class);
        i.putExtra("isFromProfileActivity", 1);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);

        // if user clicks become a host button, start first registration activity for apartment information
    }

    private void setUserRegisteredFalse(){

        SharedPreferences prefs = getSharedPreferences("isAccountRegistered", MODE_PRIVATE);

        prefs.edit().putBoolean("registered", false).apply();

    }

    private void emptyUsernamePreferences(){

        SharedPreferences pref = getSharedPreferences("username", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("username", "").apply();

    }

    private void setRememberEmailFalse(){

        SharedPreferences pref2 = getSharedPreferences("isChecked", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = pref2.edit();

        editor2.putBoolean("isChecked", false);

        editor2.apply();

        // set remember email option to false

    }

    private void launchMainActivity(){

        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        i.putExtra("isFromProfileActivity", 1);
        i.putExtra("isFromLogOut", 0);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // pass what activity user is coming from and if user came from log out activity into intent,

        startActivity(i);

    }

    private void setDeleteAccountButtonListenerFunc(){

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.WANTS_TO_DELETE_URL_REFERENCE);

        // create firebase links

        SettingsActivityMethods.initialiseDeleteAccountButton(
                getApplicationContext(), delete_account_layout, UID, fbRef2);

        // initialise delete button onClickListener

    }

}
