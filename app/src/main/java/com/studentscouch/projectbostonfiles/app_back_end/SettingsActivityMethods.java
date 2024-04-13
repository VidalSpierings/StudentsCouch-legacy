package com.studentscouch.projectbostonfiles.app_back_end;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.MainActivity;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsActivityMethods {

    @SuppressLint("StaticFieldLeak")
    static Activity activity;

    private static String value;

    SettingsActivityMethods(Activity activity){
        SettingsActivityMethods.activity =activity;
    }

    private static void deleteAccountDialog(final Context context, final String UID, final DatabaseReference fbRef2){

        final AlertDialog.Builder dial = new AlertDialog.Builder(context);

        // set 'Are you sure?' as dialog title

        // set 'Yes' as text for positive option button

        dial.setMessage(context.getResources().getString(R.string.are_you_sure))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.yes_dialog), (dialogInterface, i) -> dialogOnYesSelectedFunc(context, UID, fbRef2, dialogInterface)).setNegativeButton(context.getResources().getString(R.string.no_dialog), (dialogInterface, i) -> dialogInterface.cancel());

        showDialog(context, dial);

    }

    public static void initialiseLogOutButton (final Context context, FrameLayout log_out_layout){

        log_out_layout.setOnClickListener(view -> {

            setRememberEmailUnChecked(context);

            removeSavedEmail(context);

            FirebaseAuth.getInstance().signOut();

            // sign out user

            startNextActivityLogOut(context);

        });

    }

    public static void animateActivityTransition (
            Context context, ScrollView scrollView, Float scrollView_org_pos_y){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(scrollView, "translationY", metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);

        // create objectAnimator object

        anim.setDuration(400);

        // set animation duration

        anim.start();

        scrollView.setVisibility(View.VISIBLE);

    }

    private static void retrieveHostStatus(
            Context context, FrameLayout become_a_host_layout, com.google.firebase.database.DataSnapshot dataSnapshot, TextView become_a_host_textView,
            FrameLayout progress_layout, Animation exit_anim_progress_layout){

        // create String object

        retrieveHostStatus2(dataSnapshot);

        changeUIaccToHostStatus(context, become_a_host_layout, become_a_host_textView);

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

    }

    public static void initialiseBecomeHostButton (FrameLayout become_a_host_layout, final Context context, final AppCompatActivity appCompatActivity){

        become_a_host_layout.setOnClickListener(view -> {

            if (StartupMethods.isOnline(getApplicationContext())) {

                setYesNoSelectedMainActivity(context);

                setRegisterApartmentDetailsLaterUnSelected(context);

                startHostRegistrationStartActivity(context);

            } else {

                StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

                // if internet connection could not be found, inform user by showing a custom dialog

            }

        });

    }

    public static void retrieveUserHostStatus (
            final Context context, DatabaseReference fbRef, final FrameLayout become_a_host_layout,
            final TextView become_a_host_textView, final FrameLayout progress_layout, final Animation exit_anim_progress_layout
    ) {

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                /*

                String value;

                // create String object

                try {

                    value = dataSnapshot.getValue(String.class);

                    // attempt to retrieve

                    if (value.equals("") || value.equals("null")){

                        value = "not_a_host";

                        // if hostStatus can not be retrieved, set host status as 'not a host'

                    }

                }catch (Exception e) {

                    value = "not_a_host";

                    // if hostStatus can not be retrieved, set host status as 'not a host'

                }

                // if hostStatus cannot be retrieved, set host status to non-host

                if (value.equals("isHost")){

                    become_a_host_layout.setVisibility(View.GONE);

                    // if user is a host, hide 'become a host' button

                } else if (value.equals("info_not_added")){

                    become_a_host_textView.setText(context.getResources().getString(R.string.add_apartement_details));

                    // if user hasn't entered their apartment info yet during registration,
                    // change text in become host button to 'add apartment details'

                }

                progress_layout.clearAnimation();

                progress_layout.startAnimation(exit_anim_progress_layou              progress_layout.setVisibility(View.INVISIBLE);

                // make loading screen disappear

                */

                SettingsActivityMethods.retrieveHostStatus(
                        context, become_a_host_layout, dataSnapshot, become_a_host_textView,
                        progress_layout, exit_anim_progress_layout);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public static void initialiseDeleteAccountButton (
            final Context context, FrameLayout delete_account_layout, final String UID,
            final DatabaseReference fbRef2) {

        delete_account_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAccountDialog(context, UID, fbRef2);

                // if user wants to delete account, show delete account dialog

                SettingsActivityMethods.deleteAccountDialog(getApplicationContext(), UID, fbRef2);

            }
        });

    }

    private static void insertEmptyEmailAddress(Context context){

        SharedPreferences pref = context.getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("username", "").apply();

        // insert empty email address into 'remember email' option

    }

    private static void informAppUserNoLongerLoggedIn(Context context){

        SharedPreferences prefs = context.getSharedPreferences("isAccountRegistered", MODE_PRIVATE);

        prefs.edit().putBoolean("registered", false).apply();

    }

    private static void startMainActivity(Context context){

        Intent i2 = new Intent(context, MainActivity.class);
        i2.putExtra("isFromProfileActivity", 1);
        i2.putExtra("isFromLogOut", 0);
        i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(i2);

        /*

         * inform next activity what activity user came from,
         * inform next activity if user came from log out button,
         * clear activity stack

         */

    }

    private static void dialogOnYesSelectedFunc(Context context, String UID, DatabaseReference fbRef2, DialogInterface dialogInterface){

        fbRef2.child(UID).setValue(UID);

        // inform DB that user wants to delete their account

        Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.account_deleted), Toast.LENGTH_LONG).show();

        // inform that request to delete their account was completed successfully

        informAppUserNoLongerLoggedIn(context);

        insertEmptyEmailAddress(context);

        startMainActivity(context);

        dialogInterface.cancel();

    }

    private static void showDialog(Context context, AlertDialog.Builder dial){

        AlertDialog alert = dial.create();
        alert.setTitle(context.getResources().getString(R.string.delete_account));
        alert.show();

    }

    private static void setRememberEmailUnChecked(Context context){

        SharedPreferences pref2 = context.getSharedPreferences("isChecked", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = pref2.edit();

        editor2.putBoolean("isChecked", false);

        editor2.apply();

        // set remember email option to false

    }

    private static void removeSavedEmail(Context context){

        SharedPreferences prefs = context.getSharedPreferences("isAccountRegistered", MODE_PRIVATE);

        prefs.edit().putBoolean("registered", false).apply();

        SharedPreferences pref = context.getSharedPreferences("username", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("username", "").apply();

        // remove email from 'remember email' option

    }

    private static void startNextActivityLogOut(Context context){

        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("isFromProfileActivity", 1);
        i.putExtra("isFromLogOut", 0);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // pass what activity user is coming from and if user came from log out activity into intent,

        context.startActivity(i);

        // log user out and launch log in / register selection screen

    }

    private static void retrieveHostStatus2(DataSnapshot dataSnapshot){

        try {

            value = dataSnapshot.getValue(String.class);

            // attempt to retrieve

            if (value.equals("") || value.equals(ConstantsDB.NULL)){

                value = ConstantsDB.HOST_STATUS_NOT_A_HOST_ADDED;

                // if hostStatus can not be retrieved, set host status as 'not a host'

            }

        }catch (Exception e) {

            value = ConstantsDB.HOST_STATUS_NOT_A_HOST_ADDED;

            // if hostStatus can not be retrieved, set host status as 'not a host'

        }

        // if hostStatus cannot be retrieved, set host status to non-host

    }

    private static void changeUIaccToHostStatus(Context context, FrameLayout become_a_host_layout, TextView become_a_host_textView){

        if (value.equals(ConstantsDB.HOST_STATUS_USER_IS_HOST)){

            become_a_host_layout.setVisibility(View.GONE);

            // if user is a host, hide 'become a host' button

        } else if (value.equals(ConstantsDB.HOST_STATUS_INFO_NOT_ADDED)){

            become_a_host_textView.setText(context.getResources().getString(R.string.add_apartement_details));

            // if user hasn't entered their apartment info yet during registration,
            // change text in become host button to 'add apartment details'

        }

    }

    private static void setYesNoSelectedMainActivity(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("Choice", 1).apply();

    }

    private static void startHostRegistrationStartActivity(Context context){

        Intent i = new Intent(context, RegisterActivityYesScreen1.class);
        i.putExtra("isFromProfileActivity", 1);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(i);

        // if user clicks become a host button, start first registration activity for apartment information

    }

    private static void setRegisterApartmentDetailsLaterUnSelected(Context context){

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = prefs.edit();

        // retrieve sharedPreferences

        editor2.putInt("isNotNowSelected", 0).apply();

        // insert 'yes' for whether or not to register as a host

    }

    }

