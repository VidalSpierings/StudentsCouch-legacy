package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.CityActivityDBInterface;
import com.studentscouch.projectbostonfiles.observables.CityActivityObservables;
import com.studentscouch.projectbostonfiles.view.viewImplementers.CityActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.CityActivityView;
import com.studentscouch.projectbostonfiles.view_adapters.CityActivityInformation;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.CityActivityViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class CityActivityDBImplementer implements CityActivityDBInterface {

    private CityActivityObservables observables;

    private String
            street,
            name,
            house_number,
            price,
            UID,
            AID;

    private double rating;

    private ArrayList<CityActivityInformation> information;

    private CityActivityView view;

    public CityActivityDBImplementer(CityActivityViewImplementer view, CityActivityObservables observables){

        this.observables = observables;
        this.view = view;

    }

    @Override
    public void retrieveApartmentItems(Context context, AppCompatActivity appCompatActivity, FrameLayout no_listings_frameLayout, RecyclerView recyclerView) {

        Firebase.setAndroidContext(context);

        // enable fireBase in this activity

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.ITEMS_TABLE_URL_REFERENCE + observables.getPlaceID());

        // DB link to items with same PlaceID as selected apartments (Amsterdam apartments, Rotterdam apartments, etc.)

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveApartmentItemsFunc(context, appCompatActivity, dataSnapshot, no_listings_frameLayout, recyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retrieveApartmentItemsFunc(Context context, AppCompatActivity appCompatActivity, DataSnapshot dataSnapshot, FrameLayout no_listings_frameLayout, RecyclerView recyclerView){

        information = new ArrayList<>();

        for(com.google.firebase.database.DataSnapshot postSnapshot: dataSnapshot.getChildren()){

            try {

                retrieveApartmentInfo(postSnapshot);

                String profileId = postSnapshot.child(ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).getValue(String.class);
                String apartementImage = postSnapshot.child(ConstantsDB.APARTMENT_IMAGE_1_TABLE).getValue(String.class);

                // retrieve user profile image and header apartment image (Base64 encoded String format)

                Bitmap profileBitmap = StartupMethods.StringToBitMap(profileId);

                Bitmap iconBitmap = StartupMethods.StringToBitMap(apartementImage);

                // convert Base64 String images to Bitmap objects

                int priceInt = Integer.valueOf(price);

                // convert price string object to priceInt Integer object

                checkListItemVisibility(
                        postSnapshot, iconBitmap, profileBitmap, priceInt);

            } catch (Exception e) {

                // Do nothing

            }



        }

        observables.setLoaded(true);

        CityActivityViewModel viewModel = new CityActivityViewModel();

        view.setRecyclerViewAdapter(context, appCompatActivity, information);

        viewModel.showWindowIfNoListingsFound(information, no_listings_frameLayout, recyclerView);

    }

    private void checkListItemVisibility(
            DataSnapshot postSnapshot,
            Bitmap iconBitmap, Bitmap profileBitmap, int priceInt){

        String isVisibleString = String.valueOf(postSnapshot.child(ConstantsDB.ITEM_VISIBILITY_TABLE).getValue(Long.class));

        // retrieve whether or not apartment is visible for users other than user linked to apartment

        int isVisible = Integer.valueOf(isVisibleString);

        if (isVisible == 1){

            information.add(new CityActivityInformation(
                    iconBitmap, profileBitmap, priceInt,
                    street + " " + house_number, name, rating, UID, AID));

            Collections.shuffle(information);

            // create array of visible apartment lisitems and show them in random order

        }

    }

    private void retrieveApartmentInfo(DataSnapshot postSnapshot){

        street = postSnapshot.child(ConstantsDB.APARTMENT_STREET_TABLE).getValue(String.class);
        name = postSnapshot.child(ConstantsDB.ITEM_FIRST_NAME_HOST_TABLE).getValue(String.class);
        house_number = postSnapshot.child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).getValue(String.class);
        price = postSnapshot.child(ConstantsDB.ITEM_APARTMENT_PRICE_TABLE).getValue(String.class);
        rating = postSnapshot.child(ConstantsDB.ITEM_AVERAGE_RATING_TABLE).getValue(Double.class);
        UID = postSnapshot.child(ConstantsDB.UID_TABLE).getValue(String.class);
        AID = postSnapshot.getKey();

        // retrieve metadata for each apartment

    }

}
