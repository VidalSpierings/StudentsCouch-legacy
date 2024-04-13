package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.observables.ApartmentActivityObservables;
import com.studentscouch.projectbostonfiles.ui.ApartementEditActivity;
import com.studentscouch.projectbostonfiles.ui.BookingActivity;
import com.studentscouch.projectbostonfiles.ui.ProfileActivity;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.ApartmentActivityViewModelInterface;

import static android.content.Context.MODE_PRIVATE;

public class ApartmentActivityViewModel implements ApartmentActivityViewModelInterface {
    private ScaleAnimation scaleAnimation;
    private ApartmentActivityObservables observables;

    private String apartement2String;
    private String apartement3String;

    private Intent mapIntent;

    public ApartmentActivityViewModel (ApartmentActivityObservables observables){

        this.observables = observables;

    }

    @Override
    public void toggleButtonClicked (final Context context, CompoundButton compoundButton){

        prepareToggleButtonAnims();

        compoundButton.startAnimation(scaleAnimation);

        Toast.makeText(context, context.getResources().getString(R.string.function_will_be_added), Toast.LENGTH_SHORT).show();

        // upon clicking, show animation and inform user that this function will be added in a later version



    }

    @Override
    public void launchEditActivity(Context context, Activity activity, View edit_button_view) {

        final SharedPreferences.Editor editor = activity.getSharedPreferences("apartementImagesTemp", MODE_PRIVATE).edit();

        // create sharedPreferences editor that will be used to pass images to next activity if user starts the apartment edit activity

        StartupMethods.startCircularRevealAnimationDefaultStartRadius(context, edit_button_view, false);

        saveImagesToSharedPref(editor);

        startApartmentEditActivity(activity);

    }

    private void startApartmentEditActivity(Activity activity){

        Intent i = new Intent(activity, ApartementEditActivity.class);

        setToBePassedDataForIntent(i);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // disable standard activity transition

        activity.startActivity(i);

    }

    @Override
    public void launchGmapsIntent(Context context) {

        if (!StartupMethods.isOnline(context)) {

            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            // if internet connection was not found, inform user by showing a toast

        }

        if (StartupMethods.isOnline(context)) {

            mapIntent = null;

            setIntentData();

            launchIntent(context, mapIntent);

        }

        // upon clicking 'show in maps button', show location in maps by first passing the street, then the house number and
        // then the city/village in the search request

    }

    @Override
    public void setBookNowButtonOnClickListener(Context context, Activity activity,
                                                ScrollView scrollView, TabLayout tabLayout, float scrollView_org_pos_y, float tabLayout_org_pos_y,
                                                FrameLayout book_now_button) {

        book_now_button.setOnClickListener(view -> launchBookNowActivity(
                context, activity, scrollView, tabLayout,
                scrollView_org_pos_y, tabLayout_org_pos_y));

    }

    @Override
    public void launchBookNowActivity(
            Context context, Activity activity,
            ScrollView scrollView, TabLayout tabLayout, float scrollView_org_pos_y, float tabLayout_org_pos_y) {

        if (!StartupMethods.isOnline(context)) {

            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            // if internet connection was not found, inform user by showing toast

        }

        if (StartupMethods.isOnline(context)) {

            StudentsCouchAnimations.animateActivityExitTransitionApartmentActivity(
                    context, activity, scrollView, tabLayout,
                    scrollView_org_pos_y, tabLayout_org_pos_y, observables);

            // initiate exit activity animations

        }

        // if apartment is the users' apartment, hide booking and favorite button

    }

    @Override
    public void launchBookNowActivityFunc(Activity activity, Context context) {

        Intent intent = new Intent(activity, BookingActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        intent.putExtra("UID", observables.getUID());

        intent.putExtra("AID", observables.getAID());

        intent.putExtra("house_number", observables.getHouse_number());

        intent.putExtra("street", observables.getStreet());

        intent.putExtra("cityVillage", observables.getCity_or_village());

        intent.putExtra("firstNameHost", ProfileActivity.firstName);

        intent.putExtra("lastNameHost", ProfileActivity.lastName);

        context.startActivity(intent);

        // On animation end, start new activity and pass apartment data to next activity

    }

    private void launchIntent(Context context, Intent mapIntent){

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {

            context.startActivity(mapIntent);

            //  start Google Maps intent with apartment address search query

        }

    }

    private void setIntentData(){

        String data = observables.getStreet() + " " + observables.getHouse_number() + ", " + observables.getCity_or_village();

        // create apartment address

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + data);

        // create apartment address search query

        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // create Google Maps intent

    }

    private void saveImagesToSharedPref(SharedPreferences.Editor editor){

        editor.putString("apartement1", observables.getApartment1String());

        saveAdditionalImagesIfPresent(editor);

        editor.apply();

    }

    private void saveAdditionalImagesIfPresent(SharedPreferences.Editor editor){

        attemptSaveSecondImage(editor);

        attemptSaveThirdImage(editor);

    }

    private void attemptSaveThirdImage(SharedPreferences.Editor editor){

        try {

            if (!observables.getApartment3String().equals("")){

                editor.putString("apartement3", observables.getApartment3String());

                // if third apartment image string does not equal null or "", save string in sharedPreferences

            }

        } catch (Exception e){

            // Do nothing

        }

    }

    private void attemptSaveSecondImage(SharedPreferences.Editor editor){

        try {

            if (!observables.getApartment2String().equals("")){

                editor.putString("apartement2", observables.getApartment2String());

                // if second apartment image string does not equal null or "", save string in sharedPreferences

            }

        } catch (Exception e){

            //Do nothing

        }

    }

    private void setToBePassedDataForIntent(Intent i){

        i.putExtra("AID", observables.getAID());
        i.putExtra("locationID", observables.getLocationID());

        i.putExtra("title", observables.getTitle());
        i.putExtra("description", observables.getDescription());
        i.putExtra("price_per_night", String.valueOf(observables.getPrice_per_night()));
        i.putExtra("max_num_people", String.valueOf(observables.getIsTwoPeopleAllowed()));

        i.putExtra("cityVillage", observables.getCity_or_village());
        i.putExtra("house_number", observables.getHouse_number());
        i.putExtra("street", observables.getStreet());

    }

    private void prepareToggleButtonAnims(){

        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        // load enlarging animation

    }
}
