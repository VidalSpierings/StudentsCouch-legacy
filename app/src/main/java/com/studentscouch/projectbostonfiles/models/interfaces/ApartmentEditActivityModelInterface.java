package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import com.studentscouch.projectbostonfiles.observables.ApartmentEditActivityObservables;

public interface ApartmentEditActivityModelInterface {

    void compressApartmentImages(Bitmap[] image_resources_images);

    CharSequence[] setItems(Bitmap[] image_resources_images);

    CharSequence[] setDeleteItemsLength2();

    CharSequence[] setDeleteItemsLength3();

    void deleteImg3();

    void deleteImg2LengthEq3();

    void deleteImg2LengthEq2();

    void deleteImg1LengthEq3();

    void deleteImg1LengthEq2();

    void getApartmentImages(AppCompatActivity appCompatActivity);

    void clearApartmentImages(Context context);

}
