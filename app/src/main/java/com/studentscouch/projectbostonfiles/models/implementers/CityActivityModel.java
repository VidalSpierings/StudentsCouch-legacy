package com.studentscouch.projectbostonfiles.models.implementers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.models.interfaces.CityActivityModelInterface;
import com.studentscouch.projectbostonfiles.observables.CityActivityObservables;

public class CityActivityModel implements CityActivityModelInterface {

    private CityActivityObservables observables;

    public CityActivityModel(CityActivityObservables observables){

        this.observables = observables;

    }

    @Override
    public void setActivityBackgroundFunc(Context context, AppCompatActivity appCompatActivity, ImageView backgroundImage) {

        SharedPreferences prefs = appCompatActivity.getSharedPreferences("background_image", Context.MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        // retrieve image background in Base64 encoded String format, assign to local String object

        setBackground(appCompatActivity, backgroundImage, bitmap);

    }

    @Override
    public void setHeaderImage(Context context, AppCompatActivity appCompatActivity) {

        SharedPreferences prefs = appCompatActivity.getSharedPreferences("background_image", Context.MODE_PRIVATE);
        String bitmap = prefs.getString("string2", null);

        observables.setImage2(StartupMethods.StringToBitMap(bitmap));

        // retrieve image background in Base64 encoded String format, assign to local String object

    }

    private void setBackground(AppCompatActivity appCompatActivity, ImageView backgroundImage, String bitmap){

        if (observables.getPlaceID() == (StartupMethods.AMS_PLACE_ID)){

            setLocalAmsterdamPicAsBackground(appCompatActivity, backgroundImage);

            // if placeID is equal to Amsterdam, set a custom image

        } else if (observables.getPlaceID() == (StartupMethods.ROTTERDAM_PLACE_ID)){

            setLocalRotterdamPicAsBackground(appCompatActivity, backgroundImage);

        } else {

            observables.setImage(StartupMethods.StringToBitMap(bitmap));

            // set header image to bitmap variable, this is the image on the second index of the placeID/city


        }

        backgroundImage.setImageBitmap(observables.getImage());

    }

    private void setLocalAmsterdamPicAsBackground(AppCompatActivity appCompatActivity, ImageView backgroundImage){

        backgroundImage.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            // wait until layout is completely loaded and layout dimensions of all listItem are known

            observables.setImage(BitmapFactory.decodeResource(appCompatActivity.getResources(), R.drawable.amsterdam_png));

            // set background image of Amsterdam saved in project as background

        });

        // if placeID is equal to Amsterdam, set a custom image

    }

    private void setLocalRotterdamPicAsBackground(AppCompatActivity appCompatActivity, ImageView backgroundImage){

        backgroundImage.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            //At this point the layout is complete and the
            //dimensions of myView and any child views are known.

            observables.setImage(BitmapFactory.decodeResource(appCompatActivity.getResources(), R.drawable.rotterdam));

            // set background image of Rotterdam saved in project as background

        });

        // if placeID is equal to Rotterdam, set a custom image

    }

}
