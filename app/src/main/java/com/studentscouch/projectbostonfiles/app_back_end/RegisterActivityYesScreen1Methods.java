package com.studentscouch.projectbostonfiles.app_back_end;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.studentscouch.projectbostonfiles.DeepCode;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen2;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.ConfirmRegistrationActivity;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RegisterActivityYesScreen1Methods {

    static Intent i;

    @SuppressLint("StaticFieldLeak")
    static Activity activity;

    private static List<View> unselectedViews;

    private static Intent i3;

    RegisterActivityYesScreen1Methods(Activity activity){

        RegisterActivityYesScreen1Methods.activity = activity;

    }

    private static void fabClickedNotNowButtonSelected(Context context){

        SharedPreferences.Editor editor = context.getSharedPreferences("savedUserData", MODE_PRIVATE).edit();

        editor.putInt("isNotNowSelected", 1);

        editor.apply();

        // direct user to registration confirmation activity and inform app
        // that user has chosen to not register their apartment details yet

    }

    private static void fab_clicked_not_now_button_unselected(
            Context context, Bundle extras,
            int isFromActivity) {

        if (extras != null) {

            RegisterActivityYesScreen1.isFromActivity = extras.getInt("isFromProfileActivity", 0);

            // determine whether user is coming from previous register screen
            // or has clicked the 'become host' button in their profile

            // in this case, user came from previous register activity

        }


        if(isFromActivity == 1)

        {

            saveYesOptionSelected(context);

            i.putExtra("isFromProfileActivity", 1);

            // inform app that user clicked 'become host' button to come to this activity

        }

    }

    public static void initialiseFabOnClickListener(
            final Context context, final FloatingActionButton fab, final RelativeLayout log_in_layout,
            final RelativeLayout register_layout, final FrameLayout not_now_layout,
            final View not_now_layout_view, final Bundle extras,
            final List<View> animatingViews, final AppCompatActivity appCompatActivity, final List<View> fadeInViews) {

        fab.setOnClickListener(view -> {

            disableLayoutInteraction(fab, log_in_layout, register_layout, not_now_layout);

            chooseNextActivityAccToNotNowSelec(context, extras, not_now_layout_view);

            RegisterActivitiesMethods.screen3ExitAnimations(
                    getApplicationContext(), animatingViews, i, appCompatActivity.getIntent().getStringExtra("username"),
                    fab, fadeInViews, appCompatActivity, RegisterActivityYesScreen1.isFromActivity);


            // animate activity exit transition animations

        });

    }

   private static void initialiseCameraButtonFunctionality(
           final Context context, final View log_in_layout_view,
           Intent i2, final AppCompatActivity appCompatActivity, final int REQUEST_CODE_TAKE_PICTURE,
           final FloatingActionButton fab, final View register_layout_view, final View not_now_layout_view,
           MotionEvent event
   ) {

        cameraInitUnselectedViews(register_layout_view, not_now_layout_view);

        RegisterActivityYesScreen1.mCapturedImageURI = DeepCode.StringToUri(getApplicationContext(), "temp2.jpg");

        // convert String object to Uri object

        if (event.getAction() == MotionEvent.ACTION_UP){

            StartupMethods.startCircularRevealAnimationMultipleButtons(getApplicationContext(), log_in_layout_view, unselectedViews, event);

            /*

             * if device version code is equal to or higher than 5.0 Lollipop, show a circular reveal animation.
             * if device version code is lower than 5.0 Lollipop, make the selected views' reveal view visible and make all other selectable
             * views' reveal views invisible

             */

            initIntent();

            prepareBuilder();

            try {

                appCompatActivity.startActivityForResult(i3, REQUEST_CODE_TAKE_PICTURE);

                // start camera intent with the according request code for onActivityResult()



            } catch (Exception e){

                showErrorMessageCantUseCamera(context, log_in_layout_view, fab);

            }

        }

        cameraSelectDeselectAccButtons(register_layout_view, log_in_layout_view, not_now_layout_view);

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseGalleryButton (
            final AppCompatActivity appCompatActivity, RelativeLayout register_layout, final View log_in_layout_view,
            final View not_now_layout_view, final View register_layout_view, final int REQUEST_CODE_PICK_PICTURE
    ) {

        register_layout.setOnTouchListener((v, event) -> {

            galleryAddUnselectedViews(log_in_layout_view, not_now_layout_view);

            if (event.getAction() == MotionEvent.ACTION_UP) {

                StartupMethods.startCircularRevealAnimationMultipleButtons(getApplicationContext(), register_layout_view, unselectedViews, event);

                // register button click

                launchGalleryIntent(appCompatActivity, REQUEST_CODE_PICK_PICTURE);

            }

            return true;
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseCameraButtonOnClickListener (
            final RelativeLayout log_in_layout,
            final View log_in_layout_view, final Intent i2, final AppCompatActivity appCompatActivity,
            final int REQUEST_CODE_TAKE_PICTURE, final FloatingActionButton fab, final View register_layout_view,
            final View not_now_layout_view){

        log_in_layout.setOnTouchListener((v, event) -> {

            initialiseCameraButtonFunctionality(
                    getApplicationContext(),
                    log_in_layout_view, i2, appCompatActivity, REQUEST_CODE_TAKE_PICTURE,
                    fab, register_layout_view, not_now_layout_view, event);

            // initialise camera / take image button

            return true;
        });

    }

    private static void saveYesOptionSelected(Context context){

        SharedPreferences prefs2 = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = prefs2.edit();

        editor2.putInt("Choicee", 1).apply();

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void disableLayoutInteraction(
            FloatingActionButton fab, RelativeLayout log_in_layout,
            RelativeLayout register_layout, FrameLayout not_now_layout){

        fab.setClickable(false);

        log_in_layout.setOnTouchListener(null);
        register_layout.setOnTouchListener(null);
        not_now_layout.setOnTouchListener(null);

        // disable user from clicking on any buttons after fab is pressed

    }

    private static void chooseNextActivityAccToNotNowSelec(Context context, Bundle extras, View not_now_layout_view){

        if (not_now_layout_view.isSelected()){

            i = new Intent(getApplicationContext(), ConfirmRegistrationActivity.class);

            RegisterActivityYesScreen1Methods.fabClickedNotNowButtonSelected(context);

        }

        if (!not_now_layout_view.isSelected()) {

            i = new Intent(getApplicationContext(), RegisterActivityYesScreen2.class);

            RegisterActivityYesScreen1Methods.fab_clicked_not_now_button_unselected(
                    getApplicationContext(), extras,
                    RegisterActivityYesScreen1.isFromActivity);

        }

    }

    private static void cameraInitUnselectedViews(View register_layout_view, View not_now_layout_view){

        unselectedViews = new ArrayList<>();

        unselectedViews.add(register_layout_view);
        unselectedViews.add(not_now_layout_view);

    }

    private static void initIntent(){

        i3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i3.putExtra(MediaStore.EXTRA_OUTPUT, RegisterActivityYesScreen1.mCapturedImageURI);

        // initialise intent and pass URI of captured image to next activity

    }

    private static void showErrorMessageCantUseCamera(Context context, View log_in_layout_view, FloatingActionButton fab){

        log_in_layout_view.setVisibility(View.INVISIBLE);

        Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.camera_unavailable), Toast.LENGTH_LONG).show();

        // inform user that their camera can not be used by showing a toast

        fab.hide();

        // hide fab

    }

    private static void prepareBuilder(){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // above code informs the virtual machine about unwanted numerous internal processes, I.E. Cache overflow and network status updates

    }

    private static void cameraSelectDeselectAccButtons(View register_layout_view, View log_in_layout_view, View not_now_layout_view){

        register_layout_view.setSelected(false);
        log_in_layout_view.setSelected(true);
        not_now_layout_view.setSelected(false);

        // select the selected view and deselect all other clickable views

    }

    private static void launchGalleryIntent(AppCompatActivity appCompatActivity, int REQUEST_CODE_PICK_PICTURE){

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_PICK_PICTURE);

        // launch gallery selection intent with pick picture request code for onActivityResult()

    }

    private static void galleryAddUnselectedViews(View log_in_layout_view, View not_now_layout_view){

        unselectedViews = new ArrayList<>();

        unselectedViews.add(log_in_layout_view);
        unselectedViews.add(not_now_layout_view);

    }

}
