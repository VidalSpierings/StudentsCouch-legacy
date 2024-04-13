package com.studentscouch.projectbostonfiles.app_back_end;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.ui.MessagingActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ProfileOnlyActivityMethods {

    private static Bitmap bitmap, bitmap2;
    private static String encoded;
    private static AlertDialog.Builder builder;

    public static void initialiseAddApartmentDetailsButtonFunctionality (
            Context context, AppCompatActivity appCompatActivity
    ) {

        if (StartupMethods.isOnline(context)) {

            startFirstApartmentRegistrationActivity(context, appCompatActivity);

        } else {

            StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

            // if internet connection could not be found, inform user by showing a toast

        }

    }

    private static void startFirstApartmentRegistrationActivity(Context context, AppCompatActivity appCompatActivity){

        Intent i = new Intent(appCompatActivity, RegisterActivityYesScreen1.class);
        i.putExtra("isFromProfileActivity", 1);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        // open first apartment register activity

    }

    public static void retrieveImageFromCamera(
            Intent data, ImageView profile_imageView,
            DatabaseReference fbRef, DatabaseReference fbRef3) {

        retrieveBitmapCamera(data, profile_imageView);

        convertBitmapToBase64StringCamera();

        attemptSubmitNewProfileImageToDB(fbRef, fbRef3);

        // Uri uri = getImageUri(getActivity().getBaseContext(), bitmap3);

    }

    private static void convertBitmapToBase64StringCamera(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        // convert Bitmap object to byte array object

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // convert byte array object to String object

    }

    private static void retrieveBitmapCamera(Intent data, ImageView profile_imageView){

        try {

            bitmap2 = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            profile_imageView.setImageBitmap(bitmap2);

            // retrieve image and insert into profile imageView

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private static void attemptSubmitNewProfileImageToDB(DatabaseReference fbRef, DatabaseReference fbRef3){

        try {

            fbRef.child(ConstantsDB.USER_PROFILE_IMAGE_TABLE).setValue(encoded);
            fbRef3.child(ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).setValue(encoded);

            // set new profile image to user and apartment items database subtables

        } catch (Exception e){

            // Do nothing

        }

    }

    public static void retrieveImageFromGallery(
            Context context, Intent data, ImageView profile_imageView,
            DatabaseReference fbRef, DatabaseReference fbRef3) {

        retrieveBitmapGallery(context, data);

        convertBitmapToBase64StringGallery();

        profile_imageView.setImageBitmap(bitmap);

        attemptSubmitNewProfileImageToDB(fbRef, fbRef3);

    }

    private static void convertBitmapToBase64StringGallery(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        // convert bitmap object to byte array object

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // convert byte array object to String object

    }

    private static void retrieveBitmapGallery(Context context, Intent data){

        bitmap = null;

        Uri selectedImage1 = data.getData();

        // retrieve Uri path of image chosen by user

        try {

            bitmap = StartupMethods.decodeUri(context, selectedImage1, 100);

            // convert Uri object to Bitmap object

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void startActivityForResultCamera(
            Context context, Activity activity, int MY_PERMISSION_RESPONSE,
            int REQUEST_CAMERA) {

        setPhotoDesc();

        requestPermissions(activity, MY_PERMISSION_RESPONSE);

        attemptLaunchCameraIntent(context, activity, REQUEST_CAMERA);

    }

    private static void setPhotoDesc(){

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());

        // set image title and description

    }

    private static void requestPermissions(Activity activity, int MY_PERMISSION_RESPONSE){

        String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(perms, MY_PERMISSION_RESPONSE);
        }

        // ask user for write external storage permissions

    }

    private static void attemptLaunchCameraIntent(Context context, Activity activity, int REQUEST_CAMERA){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  intent.putExtra(MediaStore.EXTRA_OUTPUT, image2);

        try {

            activity.startActivityForResult(intent, REQUEST_CAMERA);

            // start camera intent with request code for onActivityResult

        } catch (Exception e){

            Toast.makeText(context, context.getResources().getString(R.string.camera_unavailable), Toast.LENGTH_LONG).show();

            // if device camera does not allow photos to be taken, inform the user by showing a toast

        }

    }

    public static void startActivityForResultGallery(
            Context context, Activity activity, int REQUEST_GALLERY) {

        if (Build.VERSION.SDK_INT >= 23) {

            requestPermissions2(context, activity);

            attemptLaunchGalleryIntent(activity, REQUEST_GALLERY);

        }

    }

    @SuppressLint("IntentReset")
    private static void attemptLaunchGalleryIntent(Activity activity, int REQUEST_GALLERY){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);

        // start gallery intent with request code for onActivityResult

    }

    private static void requestPermissions2(Context context, Activity activity){

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            // request writing external storage permissions

        }

        // ask user for permissions

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseEditButtonOnClickListener (
            final Context context, FrameLayout edit_layout, final View edit_layout_view, final Activity activity,
            final View message_layout_view, final CharSequence[] items, final ContentValues values, final int MY_PERMISSION_RESPONSE,
            final int REQUEST_CAMERA, final int REQUEST_GALLERY, final Intent intent) {

        edit_layout.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                StartupMethods.startCircularRevealAnimationDefaultStartRadius(context, edit_layout_view, false);

                // start circular reveal animation for edit button reveal view

                initShowDialog(
                        context, activity, items, MY_PERMISSION_RESPONSE,
                        REQUEST_CAMERA, REQUEST_GALLERY, edit_layout_view,
                        message_layout_view);

            }

            return true;
        });

    }

    private static void initShowDialog(
            Context context, Activity activity, CharSequence[] items,
            int MY_PERMISSION_RESPONSE, int REQUEST_CAMERA, int REQUEST_GALLERY,
            View edit_layout_view, View message_layout_view){

        initDialogBuilder(activity);

        setDialogTitle(context);

        setDialogItems(
                context, activity, items, MY_PERMISSION_RESPONSE,
                REQUEST_CAMERA, REQUEST_GALLERY);

        setDialogOnCancelListener(edit_layout_view, message_layout_view);

        builder.show();

    }

    private static void setDialogItems(
            Context context, Activity activity, CharSequence[] items,
            int MY_PERMISSION_RESPONSE, int REQUEST_CAMERA, int REQUEST_GALLERY){

        builder.setItems(items, (dialogInterface, i) -> {

            if (items[i].equals("Camera")) {

                ProfileOnlyActivityMethods.startActivityForResultCamera(
                        context, activity, MY_PERMISSION_RESPONSE,
                        REQUEST_CAMERA);

                // initialise camera item onClickListener

            } else if (items[i].equals("Gallery")) {

                ProfileOnlyActivityMethods.startActivityForResultGallery(
                        context, activity, REQUEST_GALLERY);

            }
        });

    }

    private static void setDialogOnCancelListener(View edit_layout_view, View message_layout_view){

        builder.setOnCancelListener(dialogInterface -> {

            edit_layout_view.setVisibility(View.INVISIBLE);
            message_layout_view.setVisibility(View.INVISIBLE);

            // remove button click views upon dialog cancelled

        });

    }

    private static void initDialogBuilder(Activity activity){

        builder = new AlertDialog.Builder(activity);

    }

    private static void setDialogTitle(Context context){

        builder.setTitle(context.getResources().getString(R.string.replace_profile_image));

    }

}
