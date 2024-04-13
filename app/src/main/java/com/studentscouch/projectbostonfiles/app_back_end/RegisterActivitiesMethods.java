package com.studentscouch.projectbostonfiles.app_back_end;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studentscouch.projectbostonfiles.DeepCode;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen3;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen2;
import com.studentscouch.projectbostonfiles.StartupMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RegisterActivitiesMethods {

    static AppCompatActivity activity;
    private static Bitmap bitmap;
    private static Bitmap bitmap2;
    private static Bitmap bitmap3;
    private static Bitmap bitmap4;
    private static Uri selectedImage1;
    private static Uri uri;
    private static Animator circularReveal;
    private static String sharedPrefKey;
    static ObjectAnimator anim;

    RegisterActivitiesMethods(AppCompatActivity activity){
        RegisterActivitiesMethods.activity =activity;
    }

public static void saveTakenPicture(
        Context context, FloatingActionButton fab,
        ImageView profile_imageView, TextView additional_info_textView, TextView error_textView,
        AppCompatActivity appCompatActivity) {

    fab.show();

    retrieveImageUri(appCompatActivity);

    convertSetBitmap(context, uri, profile_imageView);

    if (bitmap3 != null){

        saveImageToSharedPrefs2(context);

        changeUIimageSubmitted(context, additional_info_textView, fab);

    }else{

        showErrorMessage2(context, error_textView);

    }

}

public static void saveChosenPicture(
        Context context,
        Uri uri, FloatingActionButton fab, TextView additional_info_textView,
        ImageView profile_imageView, Intent data) {

        saveImageToSharedPrefs3(context, data);

        setProfileImageGallery(profile_imageView);

        changeUiProfileImageSubmitted(context, additional_info_textView, fab, profile_imageView);

}

static void buttonPressedTwoButtons(
        Context context, View selectedView, View unselectedView, MotionEvent event) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        prepareAnimator(selectedView, event);

        uiSelectDeselectAccordingButtons(context, selectedView, unselectedView);

        circularReveal.start();

        /*

         * start the circular reveal animation,
         * this animation smoothly fills the view item with a darker colour
         * using a circular expansion starting from the coordinates at which the user has touched the screen,
         * in turn giving off an impression of having pressed a button

         */


    } else {

        selectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
        unselectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

    }

    /*

     * if device version code is equal to or higher than 5.0 Lollipop, show a circular reveal animation.
     * if device version code is lower than 5.0 Lollipop, make the selected views' reveal view visible and make all other selectable
     * views' reveal views invisible

     */

}

static void screen3ExitAnimations (
        final Context context, List<View> animatingViews, final Intent i, final String getStringExtraUsernameSharedPref,
        FloatingActionButton fab, List<View> fade_out_views, final AppCompatActivity appCompatActivity, final int isFromActivity) {

    DisplayMetrics metrics = context.getResources().getDisplayMetrics();

    for (int pos = 0; pos < animatingViews.size(); pos++) {

        animateActivityExitTransAllElements(
                appCompatActivity, i, isFromActivity,
                animatingViews, pos, metrics);

    }

    fab.hide();

    fadeOutLayoutElements(context, fade_out_views);

}

    static void getImageFromCamera(AppCompatActivity appCompatActivity, Context context, int request_code_take_picture) {

        try {

            RegisterActivityYesScreen2.mCapturedImageURI = DeepCode.StringToUri(context, "temp2.jpg");

            // create image Uri path

            startTakePictureIntent(appCompatActivity, request_code_take_picture);

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.camera_unavailable), Toast.LENGTH_LONG).show();

            // if device camera cannot be opened, inform user to choose gallery option by showing a toast

        }

    }

    static void deleteImage (
            Context context, ImageView add_image_view, DialogInterface dialogInterface, ImageView viewpager){

        add_image_view.setBackgroundColor(context.getResources().getColor(R.color.main_background));

        // give add image button reveal view unselected background color

        dialogInterface.dismiss();

        viewpager.setImageDrawable(null);

        viewpager.setImageBitmap(null);

        // delete third image

    }

    static void getImageFromGallery(AppCompatActivity appCompatActivity, Context context, int request_code_choose_picture) {

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            requestPermissionsIfNotYetAccepted(permissionCheck);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

                startGalleryIntent(appCompatActivity, request_code_choose_picture);

            }
        }
        // request reading storage permissions

    }

    public static void retrieveCameraImage(
            Context context, Uri mCapturedImageURI, ImageView viewPager,
            AppCompatActivity appCompatActivity, int apartmentImageIndex){

        setSharedPrefKey(apartmentImageIndex);

        try {

            convertSetSaveBitmap(
                    context, appCompatActivity, mCapturedImageURI,
                    viewPager, sharedPrefKey);

        } catch (Exception e) {

            // Do nothing

        }



    }

    private static void setSharedPrefKey(int apartmentImageIndex){

        sharedPrefKey = "";

        if (apartmentImageIndex == 1) {

            sharedPrefKey = "apartement1";

        } else if (apartmentImageIndex == 2) {

            sharedPrefKey = "apartement2";

        } else if (apartmentImageIndex == 3) {

            sharedPrefKey = "apartement3";

        }

    }

    public static void retrieveGalleryImage (
            Context context, Intent data, ImageView viewPager, boolean checkIfBitmapNull,
            int apartmentImageIndex){

        selectedImage1 = null;

        setSharedPrefKey(apartmentImageIndex);

        attemptRetrieveChosenImage(context, data);

        attemptConvertSetImage(context, viewPager);

        attemptSaveGalleryImage(context, checkIfBitmapNull);

    }

    private static void attemptSaveGalleryImage(Context context, boolean checkIfBitmapNull){

        bitmap2 = null;

        if (checkIfBitmapNull) {

            if (bitmap2 != null) {

                attemptSaveImage(context, sharedPrefKey);
            }

        } if (!checkIfBitmapNull) {

            attemptSaveImage(context, sharedPrefKey);


            // ----- ATTEMPT SAVE IMAGE WITH EMPTY CONTENTS -----

        }

    }

    public static void retrievePreviouslySelectedOptionRegisterScreen1 (
            Context context, View log_in_layout_view, View register_layout_view, FloatingActionButton fab){

        if (context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE) != null){

            SharedPreferences prefs = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

            int choice = prefs.getInt("Choice", 0);

            // retrieve sharedPreferences slot for user choosing to register or log in

            changeActivityFuncAccToYesOrNoSelected(
                    context, choice, log_in_layout_view,
                    register_layout_view, fab);

        }

    }

    private static void changeActivityFuncAccToYesOrNoSelected(
            Context context, int choice, View log_in_layout_view,
            View register_layout_view, FloatingActionButton fab){

        if (choice == 1){

            changeActivityFuncLogInSelectedPreviously(
                    context, log_in_layout_view, register_layout_view, fab);

        } else if (choice == 2){

            changeActivityFuncRegisterSelectedPreviously(
                    context, log_in_layout_view, register_layout_view, fab);

        }

    }

    private static void startNextActivity(AppCompatActivity appCompatActivity, Intent i, int isFromActivity){

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.putExtra("username", appCompatActivity.getIntent().getStringExtra("username"));

        if (isFromActivity != 20) {

            i.putExtra("isFromProfileActivity", isFromActivity);

        }

        // pass pw to next activity

        appCompatActivity.startActivity(i);

    }

    private static void animateActivityExitTransAllElements(
            AppCompatActivity appCompatActivity, Intent i, int isFromActivity,
            List<View> animatingViews, int pos, DisplayMetrics metrics){

        anim = ObjectAnimator.ofFloat
                (animatingViews.get(pos), "translationX",
                        animatingViews.get(pos).getX(),
                        -metrics.widthPixels - animatingViews.get(pos).getX());

        anim.setDuration(400);

        if (pos == 0) {

            startNextActivityOnAnimationEnd(appCompatActivity, i, isFromActivity);

        }

        anim.start();

    }

    private static void startNextActivityOnAnimationEnd(AppCompatActivity appCompatActivity, Intent i, int isFromActivity){

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                startNextActivity(appCompatActivity, i, isFromActivity);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private static void fadeOutLayoutElements(Context context, List<View> fade_out_views){

        Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        for (int pos = 0; pos < fade_out_views.size(); pos++) {

            fade_out_views.get(pos).startAnimation(fade_out);

        }

    }

    private static void startTakePictureIntent(AppCompatActivity appCompatActivity, int request_code_take_picture){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, RegisterActivityYesScreen2.mCapturedImageURI);
        appCompatActivity.startActivityForResult(intent, request_code_take_picture);

        // start camera intent with take picture for first image request code for onActivityResult

    }

    private static void requestPermissionsIfNotYetAccepted(int permissionCheck){

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            // request write external storage permission

        }

    }

    @SuppressLint("IntentReset")
    private static void startGalleryIntent(AppCompatActivity appCompatActivity, int request_code_choose_picture){

        //proceed if user accepted storage permissions

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select File"), request_code_choose_picture);

        // start gallery intent with pick picture for first image request code for onActivityResult

    }

    private static void attemptConvertSetBitmap(Context context, Uri uri, ImageView viewPager){

        bitmap = null;

        try {

            bitmap = StartupMethods.decodeUri(context, uri, 100);

            // attempt to convert Uri object to Bitmap object

        } catch (FileNotFoundException e) {

            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();

            // if Uri object could not be converted, show toast with text 'error'

            e.printStackTrace();
        }

        viewPager.setImageBitmap(bitmap);

        // insert bitmap into second apartment image imageView

    }

    private static void attemptConvertBitmap(Context context, Uri uri){

        try {

            RegisterActivityYesScreen1.cameraBitmap = StartupMethods.decodeUri(context, uri, 100);

            // attempt to convert Uri object to Bitmap object

            RegisterActivityYesScreen1.fab.show();

        } catch (FileNotFoundException e) {

            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();

            // if Uri object could not be converted, show toast with text 'error'

            e.printStackTrace();
        }

        /*

         * this if statement prevents the taken image from disappearing
         * while the user navigates to the next activities.
         * if the user navigates to previous activities from
         * this activity, the image will disappear

         */

    }

    private static void saveBitmapSharedPref(Context context, String sharedPrefKey){

        String base64Image = DeepCode.BitmapToString(bitmap);

        // convert byte array object containing bitmap to base64 String object

        SharedPreferences sharedPreferences = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(sharedPrefKey , base64Image);
        edit.apply();

    }

    public static void showErrorMessage(Context context){

        RegisterActivityYesScreen1.error_textView.setVisibility(View.VISIBLE);
        RegisterActivityYesScreen1.error_textView.setText(context.getResources().getString(R.string.something_went_wrong));

        // inform user that something went wrong with processing the bitmap
        // and/or setting bitmap into imageView

    }

    /*

    public static void attemptConvertBitmap2(Context context, Uri uri){

        try {

            RegisterActivityYesScreen1.cameraBitmap = StartupMethods.decodeUri(context, uri, 100);

            // attempt to convert Uri object to Bitmap object

            RegisterActivityYesScreen1.fab.show();

        } catch (FileNotFoundException e) {

            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();

            // if Uri object could not be converted, show toast with text 'error'

            e.printStackTrace();
        }

        /*

         * this if statement prevents the taken image from disappearing
         * while the user navigates to the next activities.
         * if the user navigates to previous activities from
         * this activity, the image will disappear

         */

    /*

    }

    */

    private static void convertSetSaveBitmap(
            Context context, AppCompatActivity appCompatActivity,
            Uri mCapturedImageURI, ImageView viewPager,
            String sharedPrefKey){

        String capturedImageFilePath = StartupMethods.retrieveImagePath(appCompatActivity, mCapturedImageURI);

        // retrieve image directory path

        final Uri uri = Uri.fromFile(new File(capturedImageFilePath));

        // convert image directory path to Uri object

        attemptConvertSetBitmap(context, uri, viewPager);

        if (bitmap != null){

            attemptConvertBitmap(context, uri);

            saveBitmapSharedPref(context, sharedPrefKey);

    }

        if (bitmap == null)

        {

            showErrorMessage(context);

        }

}

private static void attemptRetrieveChosenImage(Context context, Intent data){

    try {

        selectedImage1 = data.getData();

        // get image

    } catch (Exception e) {

        Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

        // if image could not be loaded, inform user by showing a toast

    }

}

private static void attemptConvertSetImage(Context context, ImageView viewPager){

    try {

        bitmap2 = StartupMethods.decodeUri(context, selectedImage1, 100);

        viewPager.setImageBitmap(bitmap2);

        // convert Uri object into Bitmap object, insert bitmap object into first apartment image imageView

    } catch (IOException e) {

        e.printStackTrace();

    }

}

private static void attemptSaveImage(Context context, String sharedPrefKey){

    try {

        RegisterActivityYesScreen1.bitmap = StartupMethods.decodeUri(context, selectedImage1, 100);

        String base64Image = DeepCode.BitmapToString(RegisterActivityYesScreen1.bitmap);

        // convert byte array object containing bitmap to base64 String object

        SharedPreferences sharedPreferences = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(sharedPrefKey , base64Image);
        edit.apply();

    } catch (IOException e) {

        e.printStackTrace();

    }

}
private static void changeActivityFuncLogInSelectedPreviously(
        Context context, View log_in_layout_view, View register_layout_view, FloatingActionButton fab){

    log_in_layout_view.setSelected(true);
    register_layout_view.setSelected(false);
    log_in_layout_view.setVisibility(View.VISIBLE);
    register_layout_view.setVisibility(View.INVISIBLE);
    log_in_layout_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
    register_layout_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
    fab.show();

    // select non-host, deselect host and show fab if previous choice is retrieved

}

private static void changeActivityFuncRegisterSelectedPreviously(
        Context context, View log_in_layout_view, View register_layout_view, FloatingActionButton fab){

    log_in_layout_view.setSelected(false);
    register_layout_view.setSelected(true);
    log_in_layout_view.setVisibility(View.INVISIBLE);
    register_layout_view.setVisibility(View.VISIBLE);
    log_in_layout_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
    register_layout_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
    fab.show();

    // select non-host, deselect host and show fab if previous choice is retrieved

}

private static void convertSetBitmap(Context context, Uri uri, ImageView profile_imageView){

    try {

        bitmap3 = StartupMethods.decodeUri(context, uri, 100);

        // convert image at uri path to Bitmap object

    } catch (FileNotFoundException e) {

        Toast.makeText(context, "error", Toast.LENGTH_LONG).show();

        e.printStackTrace();

        // if image file could not be found, inform user by showing a toast

    }

    setBitmap(profile_imageView);

}

public static void setBitmap(ImageView profile_imageView){

    profile_imageView.setVisibility(View.VISIBLE);

    profile_imageView.setImageBitmap(bitmap3);

    // make profile imageView visible and insert bitmap into imageView

}

private static void saveImageToSharedPrefs2(Context context){

    String encodedBase64Image = DeepCode.BitmapToString(bitmap3);

    // convert bitmap to (Base64 encoded) String object

    SharedPreferences.Editor editor = context.getSharedPreferences("savedUserData", MODE_PRIVATE).edit();
    editor.putString("profile_picture", encodedBase64Image);

    // save Base-64 encoded string of bitmap in sharedPreferences

    editor.apply();

}

private static void showErrorMessage2(Context context, TextView error_textView){

    error_textView.setVisibility(View.VISIBLE);
    error_textView.setText(context.getResources().getString(R.string.something_went_wrong));

    // inform user that something went wrong with retrieving or setting the bitmap
    // into the imageView with the help of a toast

}

private static void changeUIimageSubmitted(Context context, TextView additional_info_textView, FloatingActionButton fab){

    additional_info_textView.setText(context.getResources().getString(R.string.looking_good));

    // change additional info textView and inform user that they 'look handsome' today

    fab.show();

}

private static void retrieveImageUri(AppCompatActivity appCompatActivity){

    String capturedImageFilePath = StartupMethods.retrieveImagePath(appCompatActivity, RegisterActivityScreen3.mCapturedImageURI);

    // retrieve and convert image at Uri path to String object

    uri = Uri.fromFile(new File(capturedImageFilePath));

    // retrieve image Uri from capturedImageFilePath String

}

private static void saveImageToSharedPrefs3(Context context, Intent data){

        uri = data.getData();

    bitmap4 = null;
    try {
        bitmap4 = StartupMethods.decodeUri(context, uri, 100);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

    // convert Uri path to Bitmap object

    saveImageInSharedPrefs(context);

}

private static void saveImageInSharedPrefs(Context context){

    String encodedBase64Image = DeepCode.BitmapToString(bitmap4);

    // convert bitmap object to (Base64 encoded) String object

    SharedPreferences.Editor editor = context.getSharedPreferences("savedUserData", MODE_PRIVATE).edit();
    editor.putString("profile_picture", encodedBase64Image);

    editor.apply();

    // save Bas64-String of retrieved bitmap in sharedPreferences

}

private static void setProfileImageGallery(ImageView profile_imageView){

        profile_imageView.setImageBitmap(bitmap4);

        // convert Uri object into Bitmap object, insert bitmap object into first apartment image imageView

}

private static void changeUiProfileImageSubmitted(
        Context context, TextView additional_info_textView, FloatingActionButton fab, ImageView profile_imageView){

    additional_info_textView.setText(context.getResources().getString(R.string.looking_good));

    // change activity subTitle upon taken picture being displayed

    fab.show();

    profile_imageView.setVisibility(View.VISIBLE);

    // make profile picture imageView visible when photo was selected and proccessed

}

private static void prepareAnimator(View selectedView, MotionEvent event){

    int x = selectedView.getWidth();
    int y = selectedView.getHeight();

    float endRadius = (float) Math.hypot(x, y);

    /*

     * get width and height of view item and set endRadius of reveal animation
     * to each set of boundaries of the axes respectively, in turn allowing the reveal
     * animation to take up the whole view item

     */

    circularReveal = ViewAnimationUtils.createCircularReveal(selectedView, (int) event.getX(), (int) event.getY(), 0, endRadius);

    /*

     * load circular reveal onClick animation.
     * starting point of circular expansion
     * are at the coordinates where the user has touched the screen
     * (hence 'event.getX() / event.getY()')

     */

}

private static void uiSelectDeselectAccordingButtons(Context context, View selectedView, View unselectedView){

    selectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
    unselectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

    // make reveal view of selected button visible and make reveal view of non-selected button invisible

    unselectedView.setVisibility(View.INVISIBLE);
    selectedView.setVisibility(View.VISIBLE);

}

}
