package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.ApartmentEditActivityDBInterface;
import com.studentscouch.projectbostonfiles.observables.ApartmentEditActivityObservables;


import static android.content.Context.MODE_PRIVATE;

public class ApartmentEditActivityDBImplementer implements ApartmentEditActivityDBInterface {

    private ApartmentEditActivityObservables observables;

    public ApartmentEditActivityDBImplementer(ApartmentEditActivityObservables observables){

        this.observables = observables;

    }

    @Override
    public void submitNewInfoToDB(Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences("apartementImagesTemp", MODE_PRIVATE).edit();

        DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + observables.getAID());

        DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.ITEMS_TABLE_URL_REFERENCE
                        + observables.getLocationID() + "/" + observables.getAID());

        submitInfo(fbRef, fbRef3);

        Toast.makeText(context, context.getResources().getString(R.string.data_changed), Toast.LENGTH_SHORT).show();

        // inform user that changes have been made

        editor.clear().apply();

        // remove newly submitted/changed apartment images temporarily saved in sharedPreferences

       //removeExcessApartmentImages(fbRef);

        // <----- ABOVE METHOD CURRENTLY NOT IN USE ----->

        //removeSecondThirdApartmentImages(fbRef);

    }

    /*

    private void removeSecondThirdApartmentImages(DatabaseReference fbRef){

        try {

            fbRef.child(ConstantsDB.APARTMENT_IMAGE_2_TABLE).removeValue();
            fbRef.child(ConstantsDB.APARTMENT_IMAGE_3_TABLE).removeValue();

            // if possible, remove second and third apartment images


        } catch (Exception e) {

            // Do nothing

        }

    }

    private void removeExcessApartmentImages(DatabaseReference fbRef){

        removeSecondApartmentImage(fbRef);

        removeThirdApartmentImage(fbRef);

    }

    */

    private void removeThirdApartmentImage(DatabaseReference fbRef){

        String placeHolder3 = "";

        if (placeHolder3.equals("")) {

            try {

                fbRef.child(ConstantsDB.APARTMENT_IMAGE_3_TABLE).removeValue();

            } catch (Exception e) {

                // Do nothing

            }

        }

    }

    private void removeSecondApartmentImage(DatabaseReference fbRef){

        String placeHolder2 = "";

        if (placeHolder2.equals("")) {

            try {

                fbRef.child(ConstantsDB.APARTMENT_IMAGE_2_TABLE).removeValue();

            } catch (Exception e) {

                // Do nothing

            }

        }

    }

    private void submitInfo(DatabaseReference fbRef, DatabaseReference fbRef3){

        submitInfoToApartmentTable(fbRef);

        submitInfoToItemsTable(fbRef3);

        // upload newly entered data to servers

    }

    private void submitInfoToItemsTable(DatabaseReference fbRef3){

        fbRef3.child(ConstantsDB.APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE).setValue(Integer.valueOf(observables.getNumPeoplePerStayString()));
        fbRef3.child(ConstantsDB.ITEM_APARTMENT_PRICE_TABLE).setValue(observables.getPricePerNightString());
        fbRef3.child(ConstantsDB.APARTMENT_IMAGE_1_TABLE).setValue(observables.getEncodedImage());

    }

    private void submitInfoToApartmentTable(DatabaseReference fbRef){

        fbRef.child(ConstantsDB.APARTMENT_TITLE_TABLE).setValue(observables.getDesciptionTitleString());
        fbRef.child(ConstantsDB.APARTMENT_DESCRIPTION_TABLE).setValue(observables.getDescriptionDescriptionString());
        fbRef.child(ConstantsDB.APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE).setValue(Integer.valueOf(observables.getNumPeoplePerStayString()));
        fbRef.child(ConstantsDB.APARTMENT_PRICE_PER_NIGHT_TABLE).setValue(observables.getPricePerNightString());
        fbRef.child(ConstantsDB.APARTMENT_IMAGE_1_TABLE).setValue(observables.getEncodedImage());
        fbRef.child(ConstantsDB.APARTMENT_IMAGE_2_TABLE).setValue(observables.getEncodedImage2());
        fbRef.child(ConstantsDB.APARTMENT_IMAGE_3_TABLE).setValue(observables.getEncodedImage3());

    }

    @Override
    public void getApartmentPlaceID() {

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID() + ConstantsDB.APARTMENT_LOCATION_ID_TABLE_URL_REFERENCE);

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                observables.setLocationID(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve Apartment PlaceID

    }
}
