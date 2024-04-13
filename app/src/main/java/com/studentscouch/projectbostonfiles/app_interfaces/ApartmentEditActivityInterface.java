package com.studentscouch.projectbostonfiles.app_interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public interface ApartmentEditActivityInterface {

    void addOrReplacePhotos(final Context context, final View add_button_view,
                            final AppCompatActivity appCompatActivity,
                            final View delete_button_view, final int REQUEST_REPLACE_IMAGE_1_CAMERA,
                            final int REQUEST_REPLACE_IMAGE_1_GALLERY, final int REQUEST_REPLACE_IMAGE_2_CAMERA,
                            final int REQUEST_REPLACE_IMAGE_2_GALLERY, final int REQUEST_REPLACE_IMAGE_3_CAMERA,
                            final int REQUEST_REPLACE_IMAGE_3_GALLERY, final int REQUEST_ADD_IMAGE_2_CAMERA, final int REQUEST_ADD_IMAGE_2_GALLERY,
                            final int REQUEST_ADD_IMAGE_3_CAMERA, final int REQUEST_ADD_IMAGE_3_GALLERY, final Uri mCapturedImageURI2, final Uri mCapturedImageURI3,
                            final Uri mCapturedImageURI4, final Uri mCapturedImageURI5);

    void deletePhotos ();

    void saveInfo(final Context context, FrameLayout save_data_layout, final DatabaseReference fbRef,
                  final DatabaseReference fbRef3, final TextView description_title, final TextView description_description, final EditText num_people_per_stay_num_editText,
                  final EditText price_per_nights_editText, final SharedPreferences.Editor editor, Activity activity);

}
