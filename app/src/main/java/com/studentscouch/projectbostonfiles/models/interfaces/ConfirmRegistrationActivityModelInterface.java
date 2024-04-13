package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

public interface ConfirmRegistrationActivityModelInterface {

    String convertBitmapToBase64StringLoop(List<ImageView> apartmentImages, int i);

    String convertBitmapToBase64String(Bitmap bitmap);

    void setObservablesFromSharedPrefs(SharedPreferences prefs);

    int getYesOrNoSelected(Context context);

    void getRemainingDataFromSharedPrefs(Context context, int choice);

    void setApartmentObservables(Context context, int choice);

    void setApartmentData(Context context, SharedPreferences prefs);

    void loadApartmentImagesBecomeHostSelected(Context context, Bitmap bitmapApartement1, Bitmap bitmapApartement2,
                                               Bitmap bitmapApartement3, ImageView apartementImage1_1, ImageView apartementImage2,
                                               ImageView apartementImage3);

}
