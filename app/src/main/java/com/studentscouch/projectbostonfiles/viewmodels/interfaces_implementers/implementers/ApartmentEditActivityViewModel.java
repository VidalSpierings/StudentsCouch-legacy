package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.dbImplementers.ApartmentEditActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.ApartmentEditActivityDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.ApartmentEditActivityModel;
import com.studentscouch.projectbostonfiles.observables.ApartmentEditActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.ApartmentEditActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.ApartmentEditActivityViewModelInterface;

public class ApartmentEditActivityViewModel implements ApartmentEditActivityViewModelInterface {

    private ApartmentEditActivityModel model;
    private ApartmentEditActivityDBInterface db;
    private ApartmentEditActivityView view;

    private CharSequence[] items;

    private AlertDialog.Builder builder;

    private ApartmentEditActivityObservables observables;

    public ApartmentEditActivityViewModel (ApartmentEditActivityView view, ApartmentEditActivityObservables observables) {

        this.observables = observables;

        model = new ApartmentEditActivityModel(observables);

        db = new ApartmentEditActivityDBImplementer(observables);

        this.view = view;

    }

    @Override
    public void saveNewApartmentData(Context context, AppCompatActivity appCompatActivity, ViewGroup viewGroup) {

        model.compressApartmentImages(observables.getImage_resources_images());

        view.initObservables();

        db.submitNewInfoToDB(context);

        appCompatActivity.onBackPressed();

        // return to previous activity

    }

    @Override
    public void addImageButtonFunctionality(
            Context context, AppCompatActivity appCompatActivity,
            View view, View add_button_view, View delete_button_view) {

        //view.removeOnLayoutChangeListener(listener);

        this.view.buttonClickedAnimation(context);

        items = model.setItems(observables.getImage_resources_images());

        initBuilderAndBuilderTitle(context, appCompatActivity);

        setDialogOnCancelListener(add_button_view, delete_button_view);

        setDialogItems(context, appCompatActivity, add_button_view);

        builder.show();

    }

    private void initBuilderAndBuilderTitle(Context context, AppCompatActivity appCompatActivity){

        builder = new AlertDialog.Builder(appCompatActivity);

        builder.setTitle(context.getResources().getString(R.string.add_image));

        // build AlertDialog

    }

    @Override
    public void deleteButtonFunc(Context context, AppCompatActivity appCompatActivity, View add_button_view, View delete_button_view, FrameLayout main_frameLayout) {

        if (observables.getImage_resources_images() != null) {

                StartupMethods.startCircularRevealAnimationDefaultStartRadius(context, delete_button_view, false);

                // start circular reveal animation for delete button reveal view

                initImageLengthFunc(context, main_frameLayout);

                // show options for deleting images according to amount of retrieved images

                initBuilder(context, appCompatActivity, add_button_view, delete_button_view);

                showOrHideBuilderAccToNumApartmentImages(delete_button_view);

            }



    }

    private void showOrHideBuilderAccToNumApartmentImages(View delete_button_view){

        Log.i("544length", observables.getImage_resources_images().length + "dsdf");

        if (observables.getImage_resources_images().length == 1) {

            // if only one image present, do the following

            Log.i("231init", "init here");

            delete_button_view.setVisibility(View.INVISIBLE);

            // prevent user from deleting their one and only apartment image

        } else if (observables.getImage_resources_images().length == 2) {

            // if two images present, do the following

            builder.show();

            // show deletion options to user

        } else if (observables.getImage_resources_images().length == 3) {

            // if three images present, do the following

            builder.show();

            // show deletion options to user

        }

    }

    private void initBuilder(Context context, AppCompatActivity appCompatActivity, View add_button_view, View delete_button_view){

        builder = new AlertDialog.Builder(appCompatActivity);
        builder.setTitle(context.getResources().getString(R.string.delete_dialog));

        // build dialog and set dialog title

        initBuilderOnCancelListener(add_button_view, delete_button_view);

        initBuilderItems(context, delete_button_view);

    }

    private void initBuilderOnCancelListener(View add_button_view, View delete_button_view){

        builder.setOnCancelListener(dialogInterface -> {

            add_button_view.setVisibility(View.INVISIBLE);
            delete_button_view.setVisibility(View.INVISIBLE);

        });

    }

    private void initBuilderItems(Context context, View delete_button_view){

        builder.setItems(items, (dialogInterface, i) -> {

            if (items[i].equals("Delete image 1")) {

                deleteImage1Protocol(dialogInterface, delete_button_view);

            } else if (items[i].equals("Delete image 2")) {

                deleteImage2Protocol(dialogInterface, delete_button_view);

            } else if (items[i].equals("Delete image 3")) {

                deleteImage3Protocol(dialogInterface, delete_button_view);

            }

            view.initialiseSwipeDotsLayout2(context);

            // initialise dialog options, change arrangement images accordingly upon item selection

        });

    }

    private void deleteImage2Protocol(DialogInterface dialogInterface, View delete_button_view){

        initLayoutImageDeleted(dialogInterface, delete_button_view);

        if (observables.getImage_resources_images().length == 3) {

            model.deleteImg2LengthEq3();

        } else if (observables.getImage_resources_images().length == 2) {

            model.deleteImg2LengthEq2();

        }

    }

    private void deleteImage1Protocol(DialogInterface dialogInterface, View delete_button_view){

        initLayoutImageDeleted(dialogInterface, delete_button_view);

        if (observables.getImage_resources_images().length == 3) {

            model.deleteImg1LengthEq3();

        } else if (observables.getImage_resources_images().length == 2) {

            model.deleteImg1LengthEq2();

        }

    }

    private void deleteImage3Protocol(DialogInterface dialogInterface, View delete_button_view) {

        Log.i("214length", observables.getImage_resources_images().length + "sdada");

        initLayoutImageDeleted(dialogInterface, delete_button_view);

        model.deleteImg3();

    }

    private void initLayoutImageDeleted(DialogInterface dialogInterface, View delete_button_view){

        dialogInterface.dismiss();
        delete_button_view.setVisibility(View.INVISIBLE);

        // make delete button reveal view invisible

    }

    private void initImageLengthFunc(Context context, FrameLayout main_frameLayout){

        if (observables.getImage_resources_images().length == 1) {

            Snackbar snackbar = Snackbar
                    .make(main_frameLayout, context.getResources().getString(R.string.select_add), Snackbar.LENGTH_LONG);
            snackbar.show();

            // if user wants to delete their one and only apartment image, inform the user that they can replace this photo by pressing the plus icon

        } else if (observables.getImage_resources_images().length == 2) {

            items = model.setDeleteItemsLength2();

        } else if (observables.getImage_resources_images().length == 3) {

            items = model.setDeleteItemsLength3();

        }


    }

    private void setItems(final Context context, final AppCompatActivity appCompatActivity,
                          int i, View add_button_view) {

        if (items[i].equals("Replace image 1 (Camera)")) {

            replaceImage1CameraProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Replace image 1 (Gallery)")) {

            replaceImage1GalleryProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Replace image 2 (Camera)")) {

            replaceImage2CameraProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Replace image 2 (Gallery)")) {

            replaceImage2GalleryProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Replace image 3 (Camera)")) {

            replaceImage3CameraProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Replace image 3 (Gallery)")) {

            replaceImage3GalleryProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Add image 2 (Camera)")) {

            addImage2CameraProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Add image 2 (Gallery)")) {

            launchIntentAddImage2Gallery(appCompatActivity);

        } else if (items[i].equals("Add image 3 (Camera)")) {

            addImage3CameraProtocol(context, appCompatActivity, add_button_view);

        } else if (items[i].equals("Add image 3 (Gallery)")) {

            addImage3GalleryProtocol(context, appCompatActivity, add_button_view);

        }

        // initialise dialog item selection

    }

    private void setDialogOnCancelListener(
            View add_button_view, View delete_button_view){

        builder.setOnCancelListener(dialogInterface -> {

            add_button_view.setVisibility(View.INVISIBLE);
            delete_button_view.setVisibility(View.INVISIBLE);

        });

        // if user cancels AlertDialog, make reveal views invisible

    }

    private void setDialogItems(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        builder.setItems(items, (dialogInterface, i) -> setItems(
                context, appCompatActivity,
                i, add_button_view));

    }

    private void addImage3GalleryProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        requestPermissionsIfNecessary(context, appCompatActivity);

        launchIntentAddImage3Gallery(appCompatActivity);

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void launchIntentAddImage3Gallery(AppCompatActivity appCompatActivity){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // pass image Uri path to new intent

        int REQUEST_ADD_IMAGE_3_GALLERY = 1009;
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_ADD_IMAGE_3_GALLERY);

        // start activity for result with 'add image 3 with gallery' request code

    }

    private void addImage3CameraProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        setImageSubContentsAndURI4(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, observables.getmCapturedImageURI());
        int REQUEST_ADD_IMAGE_3_CAMERA = 1008;
        appCompatActivity.startActivityForResult(intent, REQUEST_ADD_IMAGE_3_CAMERA);

        // start new intent with 'add image 3 with camera' request code

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void setImageSubContentsAndURI4(Context context){

        String fileName = "temp4.jpg";

        // set image file name

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, context.getResources().getString(R.string.temp_image_generated));

        // add title and description to image

        observables.setmCapturedImageURI(context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));

        // create Uri object with the help of previously created ContentValues object

    }

    private void launchIntentAddImage2Gallery(AppCompatActivity appCompatActivity){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // pass image Uri path to new intent

        int REQUEST_ADD_IMAGE_2_GALLERY = 1007;
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_ADD_IMAGE_2_GALLERY);

        // start activity for result with 'add image 2 by gallery' request code

    }

    private void addImage2CameraProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        setImageSubContentsAndURI4(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, observables.getmCapturedImageURI());

        // pass image Uri path to new intent

        int REQUEST_ADD_IMAGE_2_CAMERA = 1006;
        appCompatActivity.startActivityForResult(intent, REQUEST_ADD_IMAGE_2_CAMERA);

        // start new intent with 'add image 2 with camera' request code

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void replaceImage3GalleryProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        requestPermissionsIfNecessary(context, appCompatActivity);

        launchIntentReplaceImage3Gallery(appCompatActivity);

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void launchIntentReplaceImage3Gallery(AppCompatActivity appCompatActivity){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int REQUEST_REPLACE_IMAGE_3_GALLERY = 1005;
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_REPLACE_IMAGE_3_GALLERY);

        // start activity for result with 'replace image 3 with by gallery' request code

    }

    private void replaceImage3CameraProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        setImageSubContentsAndURI3(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, observables.getmCapturedImageURI());

        // pass image Uri path to new intent

        int REQUEST_REPLACE_IMAGE_3_CAMERA = 1004;
        appCompatActivity.startActivityForResult(intent, REQUEST_REPLACE_IMAGE_3_CAMERA);

        // start new intent with 'replace image 3 with camera' request code

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void setImageSubContentsAndURI3(Context context){

        String fileName = "temp3.jpg";

        // set image file name

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, context.getResources().getString(R.string.temp_image_generated));

        // add title and description to image

        observables.setmCapturedImageURI(context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));

        // create Uri object with the help of previously created ContentValues object

    }

    private void replaceImage2GalleryProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        requestPermissionsIfNecessary(context, appCompatActivity);

        launchIntentReplaceImage2Gallery(appCompatActivity);

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void launchIntentReplaceImage2Gallery(AppCompatActivity appCompatActivity){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int REQUEST_REPLACE_IMAGE_2_GALLERY = 1003;
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_REPLACE_IMAGE_2_GALLERY);

        // start activity for result with 'replace image 2 with by gallery' request code

    }

    private void replaceImage2CameraProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        setImageSubContentsAndURI2(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, observables.getmCapturedImageURI());

        // pass image Uri path to new intent

        int REQUEST_REPLACE_IMAGE_2_CAMERA = 1002;
        appCompatActivity.startActivityForResult(intent, REQUEST_REPLACE_IMAGE_2_CAMERA);

        // start new intent with 'replace image 2 with camera' request code

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void setImageSubContentsAndURI2(Context context){

        String fileName = "temp2.jpg";

        // set image file name

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, context.getResources().getString(R.string.temp_image_generated));

        // add title and description to image

        observables.setmCapturedImageURI(context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));

        // create Uri object with the help of previously created ContentValues object

    }

    private void replaceImage1GalleryProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        requestPermissionsIfNecessary(context, appCompatActivity);

        launchIntentReplace1Gallery(appCompatActivity);

        add_button_view.setVisibility(View.INVISIBLE);

        // make add button reveal view invisible

    }

    private void replaceImage1CameraProtocol(Context context, AppCompatActivity appCompatActivity, View add_button_view){

        setImageSubContentsAndURI1(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, observables.getmCapturedImageURI());

        // add image Uri path to activity

        int REQUEST_REPLACE_IMAGE_1_CAMERA = 1000;
        appCompatActivity.startActivityForResult(intent, REQUEST_REPLACE_IMAGE_1_CAMERA);

        // start activity with corresponding result code

        add_button_view.setVisibility(View.INVISIBLE);

    }

    private void requestPermissionsIfNecessary(Context context, AppCompatActivity appCompatActivity){

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            // request writing to external storage permission

        }

    }

    private void launchIntentReplace1Gallery(AppCompatActivity appCompatActivity){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int REQUEST_REPLACE_IMAGE_1_GALLERY = 1001;
        appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_REPLACE_IMAGE_1_GALLERY);

        // start activity for result with 'replace image with gallery' result code

    }

    private void setImageSubContentsAndURI1(Context context){

        String fileName = "temp.jpg";

        // set image file name

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, context.getResources().getString(R.string.temp_image_generated));

        // set image title and description

        observables.setmCapturedImageURI(context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));

        // create image Uri and insert recently created ContentValues object

    }

}
