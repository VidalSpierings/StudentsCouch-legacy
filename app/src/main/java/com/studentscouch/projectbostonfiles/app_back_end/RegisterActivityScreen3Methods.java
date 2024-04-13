package com.studentscouch.projectbostonfiles.app_back_end;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.FacebookSdk;
import com.studentscouch.projectbostonfiles.DeepCode;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;
import com.studentscouch.projectbostonfiles.ui.ConfirmRegistrationActivity;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen3;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RegisterActivityScreen3Methods {

    public static boolean isOpenend = false;
    private static SharedPreferences sharedPreferences;
    static Intent intent;

    public static void initialiseFabOnClickListener (
            final Context context, final FloatingActionButton fab, final RelativeLayout register_layout,
            final RelativeLayout log_in_layout, final ImageView profile_imageView, final AppCompatActivity appCompatActivity,
            final int choice, final Intent i, final List<View> firstToFinishViews, final List<View> fade_in_views) {

        fab.setOnClickListener(view -> setFabFunctionality(
                context, fab, register_layout, log_in_layout,
                profile_imageView, choice, i, appCompatActivity,
                firstToFinishViews, fade_in_views));

    }

    private static void setFabFunctionality(
            Context context, FloatingActionButton fab, RelativeLayout register_layout, RelativeLayout log_in_layout,
            ImageView profile_imageView, int choice, Intent i, AppCompatActivity appCompatActivity,
            List<View> firstToFinishViews, List<View> fade_in_views) {

        disableLayoutInteraction(fab, register_layout, log_in_layout);

        saveProfileImage(context, profile_imageView);

        setNextActivityAccToYesNoSelected(appCompatActivity, choice);

        RegisterActivitiesMethods.screen3ExitAnimations(
                getApplicationContext(), firstToFinishViews, intent, sharedPreferences.getString("username", ""),
                fab, fade_in_views, appCompatActivity, RegisterActivityScreen3.isFromActivity);

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseLogInButtonOnClickListener (
            final Context context, RelativeLayout log_in_layout, final View log_in_layout_view, final View register_layout_view,
            final AppCompatActivity appCompatActivity, final int REQUEST_CODE_TAKE_PICTURE) {

        log_in_layout.setOnTouchListener((v, event) -> {

            initialiseLogInButtonFunctionality(
                    context, event, log_in_layout_view, register_layout_view,
                    appCompatActivity, REQUEST_CODE_TAKE_PICTURE);

            // initialise log in button onClick functionality

            return true;
        });

    }

    private static void initialiseLogInButtonFunctionality(
            Context context, MotionEvent event, View log_in_layout_view, View register_layout_view,
            AppCompatActivity appCompatActivity, int REQUEST_CODE_TAKE_PICTURE) {

        if (!isOpenend) {

            FacebookSdk.sdkInitialize(context);

            if (event.getAction() == MotionEvent.ACTION_UP){

                RegisterActivitiesMethods.buttonPressedTwoButtons(
                        getApplicationContext(), log_in_layout_view, register_layout_view, event);

                showDialog();

                requestPermissionsOrLaunchIntent(context, appCompatActivity, REQUEST_CODE_TAKE_PICTURE);

                cameraSelectDeselectAccButtons(register_layout_view, log_in_layout_view);

                isOpenend = true;

            }

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseRegisterButton (
            final Context context, RelativeLayout register_layout, final View register_layout_view, final View log_in_layout_view,
            final int REQUEST_CODE_PICK_PICTURE, final AppCompatActivity appCompatActivity
    ) {

        register_layout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP){

                FacebookSdk.sdkInitialize(context);

                RegisterActivitiesMethods.buttonPressedTwoButtons(getApplicationContext(), register_layout_view, log_in_layout_view, event);

                // visually register button press

                launchGalleryIntent(appCompatActivity, REQUEST_CODE_PICK_PICTURE);

            }


            gallerySelectDeselectAccButtons(log_in_layout_view, register_layout_view);

            return true;
        });

    }

    private static void saveProfileImage(Context context, ImageView profile_imageView){

        Bitmap bitmap = ((BitmapDrawable) profile_imageView.getDrawable()).getBitmap();

        // retrieve bitmap object from image in profile_imageView

        String encodedImage = DeepCode.BitmapToString(bitmap);

        // convert byte array object to String object in Base64 format

        saveImage(context, encodedImage);

    }

    private static void saveImage(Context context, String encodedImage){

        sharedPreferences = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("profile_picture", encodedImage);

        editor.apply();

        // insert profile picture into sharedPreferences

    }

    private static void setNextActivityAccToYesNoSelected(AppCompatActivity appCompatActivity, int choice){

        if (choice == 1) {

            intent = new Intent(appCompatActivity, RegisterActivityYesScreen1.class);

            // if user selected to register as host, set next activity to first apartment register activity

        } else if (choice == 2) {

            intent = new Intent(appCompatActivity, ConfirmRegistrationActivity.class);

            // if user selected to register as non-host, set next activity to registration confirmation Activity

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void disableLayoutInteraction(FloatingActionButton fab, RelativeLayout register_layout, RelativeLayout log_in_layout){

        fab.setClickable(false);

        register_layout.setOnTouchListener(null);
        log_in_layout.setOnTouchListener(null);

    }

    private static void showDialog(){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    private static void requestPermissions(AppCompatActivity appCompatActivity){

        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };

        ActivityCompat.requestPermissions(appCompatActivity, PERMISSIONS, 1);

        // request Device camera and write external storage permissions

    }

    private static void launchCameraIntent(AppCompatActivity appCompatActivity, int REQUEST_CODE_TAKE_PICTURE){

        Intent i3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i3.putExtra(MediaStore.EXTRA_OUTPUT, RegisterActivityScreen3.mCapturedImageURI);
        appCompatActivity.startActivityForResult(i3, REQUEST_CODE_TAKE_PICTURE);

        // pass image Uri path to next activity

    }

    private static void requestPermissionsOrLaunchIntent(Context context, AppCompatActivity appCompatActivity, int REQUEST_CODE_TAKE_PICTURE){

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // if camera and write external storage permissions are not yet accepted, do the following

            requestPermissions(appCompatActivity);

        } else {

            // if camera and write external storage permissions are accepted, do the following


            RegisterActivityScreen3.mCapturedImageURI = DeepCode.StringToUri(getApplicationContext(), "temp.jpg");

            // create file name, image title and image Uri path

            launchCameraIntent(appCompatActivity, REQUEST_CODE_TAKE_PICTURE);

        }

        // try to open camera activity with take picture request code for onActivityResult()

    }

    private static void cameraSelectDeselectAccButtons(View register_layout_view, View log_in_layout_view){

        register_layout_view.setSelected(false);
        log_in_layout_view.setSelected(true);

        // make Camera button reveal view visible and make Gallery button reveal view invisible

    }

    private static void launchGalleryIntent(AppCompatActivity appCompatActivity, int REQUEST_CODE_PICK_PICTURE){

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_PICK_PICTURE);

        // launch gallery selection intent with pick picture request code for onActivityResult()

    }

    private static void gallerySelectDeselectAccButtons(View log_in_layout_view, View register_layout_view){

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(true);

        // make Camera button selected and make Gallery button unselected

    }

    }
