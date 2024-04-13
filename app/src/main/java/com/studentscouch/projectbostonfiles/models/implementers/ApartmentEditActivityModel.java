package com.studentscouch.projectbostonfiles.models.implementers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.studentscouch.projectbostonfiles.models.interfaces.ApartmentEditActivityModelInterface;
import com.studentscouch.projectbostonfiles.observables.ApartmentEditActivityObservables;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class ApartmentEditActivityModel implements ApartmentEditActivityModelInterface {

    private ApartmentEditActivityObservables observables;
    private Bitmap[] image_resources_images;

    private SharedPreferences prefs;

    public ApartmentEditActivityModel(ApartmentEditActivityObservables observables){

        this.observables = observables;

    }

    @Override
    public void compressApartmentImages(Bitmap[] image_resources_images) {

        this.image_resources_images = image_resources_images;

        compressFirstApartmentImage();

        compressSecondApartmentImage();

        compressThirdApartmentImage();

    }

    @Override
    public CharSequence[] setItems(Bitmap[] image_resources_images) {

        this.image_resources_images = image_resources_images;
        CharSequence[] items = null;

        Log.i("imgLen", image_resources_images.length + "  jfdhjafhjfs");

        if (image_resources_images.length == 1) {

            items = new CharSequence[]{
                    "Replace image 1 (Camera)", "Replace image 1 (Gallery)", "Add image 2 (Gallery)", "Add image 2 (Camera)"};

        } else if (image_resources_images.length == 2) {

            items = new CharSequence[]{
                    "Replace image 1 (Camera)", "Replace image 1 (Gallery)", "Replace image 2 (Camera)",
                    "Replace image 2 (Gallery)", "Add image 3 (Gallery)", "Add image 3 (Camera)"};

        } else if (image_resources_images.length == 3) {

            items = new CharSequence[]{
                    "Replace image 1 (Camera)", "Replace image 1 (Gallery)", "Replace image 2 (Camera)",
                    "Replace image 2 (Gallery)", "Replace image 3 (Gallery)", "Replace image 3 (Camera)"};

        }

        // show options for replacing/adding/removing images according to amount of images that can be retrieved

        return items;

    }

    @Override
    public CharSequence[] setDeleteItemsLength2() {

        return new CharSequence[]{"Delete image 1", "Delete image 2"};

    }

    @Override
    public CharSequence[] setDeleteItemsLength3() {

        return new CharSequence[]{"Delete image 1", "Delete image 2", "Delete image 3"};

    }

    @Override
    public void deleteImg3() {

        Bitmap[] newList = new Bitmap[]{null, null};

        //observables.getImage_resources_images()[2] = null;

        newList[0] = observables.getImage_resources_images()[0];

        newList[1] = observables.getImage_resources_images()[1];

        observables.setImage_resources_images(new Bitmap[]{newList[0], newList[1]});

        // update images array list by creating new list with new correct image ordering,
        // then inserting those values back in the original array

        observables.setApartement3String("");

        Log.i("12init", "init here");

    }

    @Override
    public void deleteImg2LengthEq3() {

        Bitmap[] newList = new Bitmap[]{null, null};

        observables.getImage_resources_images()[1] = observables.getImage_resources_images()[2];

        observables.getImage_resources_images()[2] = null;

        newList[0] = observables.getImage_resources_images()[0];

        newList[1] = observables.getImage_resources_images()[1];

        observables.setImage_resources_images(new Bitmap[]{newList[0], newList[1]});

        // update images array list by creating new list with new correct image ordering,
        // then inserting those values back in the original array

        observables.setApartement2String("");
        observables.setApartement3String("");

    }

    @Override
    public void deleteImg2LengthEq2() {

        Bitmap[] newList = new Bitmap[]{null, null};

        newList[0] = observables.getImage_resources_images()[0];

        observables.setImage_resources_images(new Bitmap[]{newList[0]});

        // update images array list by creating new array list with new correct image ordering,
        // then inserting those values back in the original array

        observables.setApartement2String("");
        observables.setApartement3String("");

    }

    @Override
    public void deleteImg1LengthEq3() {

        Bitmap[] newList = new Bitmap[]{null, null};

        observables.getImage_resources_images()[0] = observables.getImage_resources_images()[1];

        observables.getImage_resources_images()[1] = observables.getImage_resources_images()[2];

        observables.getImage_resources_images()[2] = null;

        newList[0] = observables.getImage_resources_images()[0];

        newList[1] = observables.getImage_resources_images()[1];

        observables.setImage_resources_images(new Bitmap[]{newList[0], newList[1]});

        // update images array list by creating new list with new correct image ordering,
        // then inserting those values back in the original array

        observables.setApartement2String("");
        observables.setApartement3String("");

    }

    @Override
    public void deleteImg1LengthEq2() {

        Bitmap[] newList = new Bitmap[]{null, null};

        observables.getImage_resources_images()[0] = observables.getImage_resources_images()[1];

        newList[0] = observables.getImage_resources_images()[0];

        observables.setImage_resources_images(new Bitmap[]{newList[0]});

        // update images array list by creating new array list with new correct image ordering,
        // then inserting those values back in the original array

        observables.setApartement2String("");
        observables.setApartement3String("");

    }

    @Override
    public void getApartmentImages(AppCompatActivity appCompatActivity) {

        prefs = appCompatActivity.getSharedPreferences("apartementImagesTemp", MODE_PRIVATE);

        observables.setApartement1String(prefs.getString("apartement1", ""));

        // load first apartment image

        setSecondApartmentImageIfPresent();

        setThirdApartmentImageIfPresent();

    }

    private void setSecondApartmentImageIfPresent(){

        try {

            observables.setApartement2String(prefs.getString("apartement2", ""));

        } catch (Exception e){

            // Do nothing

        }

    }

    private void setThirdApartmentImageIfPresent(){

        try {

            observables.setApartement3String(prefs.getString("apartement3", ""));

        } catch (Exception e){

            // Do nothing

        }

    }

    @Override
    public void clearApartmentImages(Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences("apartementImagesTemp", MODE_PRIVATE).edit();

        editor.clear().apply();

    }

    private void compressThirdApartmentImage(){

        try {

            ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
            Bitmap bm3 = image_resources_images[2];
            bm3.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream3);
            byte[] b3 = byteArrayOutputStream3.toByteArray();

            observables.setEncodedImage3(Base64.encodeToString(b3, Base64.DEFAULT));

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void compressSecondApartmentImage(){

        try {

            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            Bitmap bm2 = image_resources_images[1];
            bm2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
            byte[] b2 = byteArrayOutputStream2.toByteArray();

            observables.setEncodedImage2(Base64.encodeToString(b2, Base64.DEFAULT));

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void compressFirstApartmentImage(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bm = image_resources_images[0];
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();

        observables.setEncodedImage(Base64.encodeToString(b, Base64.DEFAULT));

    }

}
