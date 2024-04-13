package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.client.Firebase;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.observables.ApartmentActivityObservables;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.interfaces.ApartmentActivityDBInterface;
import com.studentscouch.projectbostonfiles.view.views.ApartmentActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.ApartmentActivityViewModel;

import java.util.Objects;

public class ApartmentActivityDBImplementer implements ApartmentActivityDBInterface {

    private ApartmentActivityObservables observables;
    private ApartmentActivityView apartmentActivityView;
    private ApartmentActivityViewModel viewModel;

    public ApartmentActivityDBImplementer(ApartmentActivityView view, ApartmentActivityObservables observables){

        this.observables = observables;

        apartmentActivityView = view;
        viewModel = new ApartmentActivityViewModel(observables);


    }

    @Override
    public void retrieveCityVillage(DataSnapshot dataSnapshot) {

        try {

            observables.setCity_or_village(dataSnapshot.child(ConstantsDB.APARTMENT_CITY_VILLAGE_TABLE).getValue(String.class));

            if (observables.getCity_or_village().equals("") || observables.getCity_or_village().equals(ConstantsDB.NULL)){

                observables.setCity_or_village(ConstantsDB.ERROR);

            }

        }catch (Exception e){

            observables.setCity_or_village(ConstantsDB.ERROR);

        }

        // attempt to retrieve city/village. If city/village can not be retrieved, set city/village name to 'ERROR'

    }

    @Override
    public void retrieveApartmentTitle(DataSnapshot dataSnapshot) {

        try {

            observables.setTitle(dataSnapshot.child(ConstantsDB.APARTMENT_TITLE_TABLE).getValue(String.class));

            if (observables.getTitle().equals("") || observables.getTitle().equals(ConstantsDB.NULL)){

                observables.setTitle(ConstantsDB.ERROR);

                // if apartment description title can not be retrieved, set apartment description title string to 'ERROR'

            }

        }catch (Exception e){

            observables.setTitle(ConstantsDB.ERROR);

        }

        // attempt to retrieve apartment title, if apartment title can not be retrieved, set street to 'ERROR'

    }

    @Override
    public void retrieveApartmentDesc(DataSnapshot dataSnapshot) {

        try {

            observables.setDescription(dataSnapshot.child(ConstantsDB.APARTMENT_DESCRIPTION_TABLE).getValue(String.class));

            // retrieve apartment description

            if (observables.getDescription().equals("") || observables.getDescription().equals(ConstantsDB.NULL)){

                observables.setDescription(ConstantsDB.ERROR);

            }

        } catch (Exception e){

            observables.setDescription(ConstantsDB.ERROR);

        }

        // attempt to retrieve apartment description. If description can not be retrieved, set city/village name to 'ERROR'

    }

    @Override
    public void retrieveApartmentHouseNumber(DataSnapshot dataSnapshot) {

        try {

            observables.setHouse_number(dataSnapshot.child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).getValue(String.class));

            if (observables.getHouse_number().equals("") || observables.getHouse_number().equals(ConstantsDB.NULL)){

                observables.setHouse_number(ConstantsDB.ERROR);

            }

        } catch (Exception e){

            observables.setHouse_number(ConstantsDB.ERROR);

        }

        // attempt to retrieve house number. If house number can not be retrieved, set house number name to 'ERROR'

    }

    @Override
    public void retrieveApartmentLocationID(DataSnapshot dataSnapshot) {

        try {

            observables.setLocationID(dataSnapshot.child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).getValue(String.class));

            if (observables.getLocationID().equals("") || observables.getLocationID().equals(ConstantsDB.NULL)){

                observables.setLocationID(ConstantsDB.ERROR);

                // if apartment locationID can not be retrieved, set apartment locationID string to 'ERROR'

            }



        }catch (Exception e){

            observables.setLocationID(ConstantsDB.ERROR);

            // if apartment locationID can not be retrieved, set apartment locationID string to 'ERROR'

        }

        Log.i("locID2", observables.getLocationID() + "= locID");

        // attempt to retrieve Place ID. If house number can not be retrieved, set Place ID to 'ERROR'

    }

    @Override
    public void retrieveStreetName(DataSnapshot dataSnapshot) {

        try {

            observables.setStreet(dataSnapshot.child(ConstantsDB.APARTMENT_STREET_TABLE).getValue(String.class));

            if (observables.getStreet().equals("") || observables.getStreet().equals(ConstantsDB.NULL)){

                observables.setStreet(ConstantsDB.ERROR);

                // if street can not be retrieved, set street string to 'ERROR'

            }

        }catch (Exception e){

            observables.setStreet(ConstantsDB.ERROR);

            // if apartment street can not be retrieved, set apartment street string to 'ERROR'

        }

        // attempt to retrieve apartment street name, if house number can not be retrieved, set street to 'ERROR'

    }

    @Override
    public void retrievePricePerNight(DataSnapshot dataSnapshot) {

        try {

            String price_per_night_string = dataSnapshot.child(ConstantsDB.APARTMENT_PRICE_PER_NIGHT_TABLE).getValue(String.class);

            observables.setPrice_per_night(Integer.valueOf(Objects.requireNonNull(price_per_night_string)));

            // attempt to retrieve price per night for apartment

        }catch (Exception e){

            observables.setPrice_per_night(0);

            // if price per night can not be retrieved, set price per night to € 0,-

        }

        // attempt to retrieve apartment price per night. If apartment price per night can not be retrieved, inform user by showing a toast

    }

    @Override
    public void retrieveNumRatings(DataSnapshot dataSnapshot) {

        try {

            String numRatingsString = dataSnapshot.child(ConstantsDB.APARTMENT_NUM_RATINGS_TABLE).getValue(String.class);

            observables.setNumRatings(Integer.valueOf(Objects.requireNonNull(numRatingsString)));

            // attempt to retrieve ratings count

        }catch (Exception e){

            observables.setNumRatings(0);

            // if ratings count cannot be retrieved, set ratings count to 0

        }

        // attempt to retrieve total number of ratings. If ratings can not be retrieved, set total number of ratings to 0.

    }

    @Override
    public void retrieveRatingAverage(DataSnapshot dataSnapshot) {

        try {

            String ratingAverageString = dataSnapshot.child(ConstantsDB.APARTMENT_RATING_AVERAGE_TABLE).getValue(String.class);

            observables.setRatingAverage(Double.valueOf(Objects.requireNonNull(ratingAverageString)));

            // attempt to retrieve average rating

        }catch (Exception e){

            observables.setRatingAverage(0);

            // if rating average can not be retrieved, set rating average to 0 stars

        }

        // attempt to retrieve average rating. If average rating can not be retrieved, set average rating to 0 (this will make the rating disappear)

    }

    @Override
    public void retrieveIsTwoPeopleAllowed(DataSnapshot dataSnapshot) {

        try {

            int isTwoPeopleAllowedString = dataSnapshot.child(ConstantsDB.APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE).getValue(Integer.class);

            observables.setIsTwoPeopleAllowed(isTwoPeopleAllowedString);

            // attempt to retrieve whether or not host has allowed bookings for two people at the same time

        }catch (Exception e){

            observables.setIsTwoPeopleAllowed(1);

            // if info about user allowing a maximum number of people per booking cannot be retrieved, set amount of people allowed per booking to 1

        }

    }

    @Override
    public void checkIfPlatformOpenForNonHosts() {

    }

    @Override
    public void loadApartmentInfo(DataSnapshot dataSnapshot) {

        retrieveCityVillage(dataSnapshot);
        retrieveApartmentTitle(dataSnapshot);
        retrieveApartmentDesc(dataSnapshot);
        retrieveApartmentHouseNumber(dataSnapshot);
        retrieveApartmentLocationID(dataSnapshot);
        retrieveStreetName(dataSnapshot);
        retrievePricePerNight(dataSnapshot);
        retrieveNumRatings(dataSnapshot);
        retrieveRatingAverage(dataSnapshot);
        retrieveIsTwoPeopleAllowed(dataSnapshot);

    }

    @Override
    public void getDataFromDB(Context context, ViewGroup viewGroup, Activity activity, ScrollView scrollView, TabLayout tabLayout,
                              float scrollView_org_pos_y, float tabLayout_org_pos_y, FrameLayout book_now_button) {

        Firebase.setAndroidContext(activity.getApplicationContext());

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + observables.getAID());

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveDataAndInitLayout(
                        context, dataSnapshot, activity, scrollView,
                        tabLayout, scrollView_org_pos_y, tabLayout_org_pos_y, book_now_button);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void retrieveDataAndInitLayout(Context context, DataSnapshot dataSnapshot, Activity activity, ScrollView scrollView, TabLayout tabLayout,
                                           float scrollView_org_pos_y, float tabLayout_org_pos_y, FrameLayout book_now_button){

        getApartmentInfoNoTryCatch(dataSnapshot);

        checkIban(
                context, activity, scrollView, tabLayout, scrollView_org_pos_y,
                tabLayout_org_pos_y, book_now_button);

        apartmentActivityView.initSwipeDotsLayout(context);

        loadApartInfo(context, dataSnapshot);

        initLayoutText(context);

        checkApartmentDetails(context);

        StartupMethods.setStarsRating(observables.getRatingAverage(), apartmentActivityView.getRatingImageView());

        // set apartment rating average

        attemptSetExitListener(context, activity);

    }

    private void attemptSetExitListener(Context context, Activity activity){

        try {

            apartmentActivityView.setExitAnimListener(context, activity);

            // animate exit activity transition or make scrollView disappear depending on activity user came from

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void loadApartInfo(Context context, DataSnapshot dataSnapshot){

        loadApartmentInfo(dataSnapshot);

        // attempt to retrieve trivial apartment data, I.E. description, house number, street, etc.

        retrieveNumSubleasedNightsLeft(context, dataSnapshot);

    }

    private void checkApartmentDetails(Context context){

        apartmentActivityView.checkIfUserViewingOwnApartment();

        // check if user is viewing their own apartment

        apartmentActivityView.checkIfTwoPeoplePerBookingAllowed(context);

        // check if two people are allowed to stay during a single booking for this apartment

    }

    private void initLayoutText(Context context){

        apartmentActivityView.setRatingsTextViewGrammar(context);

        // change 'rating' textView to 'ratings' or 'rating', depending on number of ratings received

        try {

            apartmentActivityView.insertRetrievedInfoIntoTextViews(context);

            // insert retrieved info from DB into TextViews in layout

        } catch (Exception e) {

            // Do nothing

        }

                /*

                 try catch bracket prevents above code from being instantiated
                 when host interacts with first guest booking request in StartupActivity

                */

    }
    private void getApartmentInfoNoTryCatch(DataSnapshot dataSnapshot){

        observables.setApartment1String(dataSnapshot.child(ConstantsDB.APARTMENT_IMAGE_1_TABLE).getValue(String.class));
        observables.setApartment2String(dataSnapshot.child(ConstantsDB.APARTMENT_IMAGE_2_TABLE).getValue(String.class));
        observables.setApartment3String(dataSnapshot.child(ConstantsDB.APARTMENT_IMAGE_3_TABLE).getValue(String.class));

        /*

         * get all apartment images. If second and/or third
         * apartment image isn't found, an empty string is returned.
         * the layout of the activity will change accordingly to the received data

         */

        observables.setHostUID(dataSnapshot.child(ConstantsDB.UID_TABLE).getValue(String.class));

        // get user UID (user is always a host in this case)

    }

    private void retrieveNumSubleasedNightsLeft(Context context, DataSnapshot dataSnapshot){

        try {

            observables.setNumSubleasedNightsLeft(dataSnapshot.child(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE).getValue(Integer.class));

            // attempt to retrieve amount of subleased nights left for apartment

            apartmentActivityView.makeSubleasedNightsLayoutDisappear();

            informUserIfNoMoreNightsLeft(context);

        } catch (Exception e) {

            // Do nothing

        }

    }

    private void informUserIfNoMoreNightsLeft(Context context){

        if (observables.getNumSubleasedNightsLeft() <= 0){

            // 12.34.56.789 number of subleased nights means 'number of nights N/A'

            // only apartments located in Amsterdam will have a set number of subleased nights.
            // if apartment is not located in amsterdam, make view dissapear

            apartmentActivityView.makeSubleasedNightsLayoutDisappear();

            apartmentActivityView.informUserApartmentOutOfSubleasedNights(context);

        }

        // inform user that apartment is out of subleaseable nights and therefore cannot be booked anymore

    }

    private void checkIban(Context context, Activity activity, ScrollView scrollView, TabLayout tabLayout,
                           float scrollView_org_pos_y, float tabLayout_org_pos_y, FrameLayout book_now_button){

        final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Users/" + observables.getUID());

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                try {

                    checkIfPlatformOpenForNonHosts(
                            context, activity, scrollView, tabLayout,
                            scrollView_org_pos_y, tabLayout_org_pos_y, book_now_button);

                    checkIfIbanAdded(dataSnapshot);

                    // check if IBAN has been submitted to apartment

                } catch (Exception e) {

                    observables.setIBAN(ConstantsDB.NO_IBAN_ADDED);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        /*

         * below code snippets initialises the slideDotsPanel view every time
         * a new apartment image is loaded

         */

    }

    private void checkIfIbanAdded(DataSnapshot dataSnapshot) {

        try {

            observables.setIBAN(dataSnapshot.child(ConstantsDB.APARTMENT_IBAN_NUMBER_TABLE).getValue(String.class));

            // return user IBAN

        } catch (Exception e){

            observables.setIBAN(ConstantsDB.NO_IBAN_ADDED);

            /*

             * if IBAN can not be retrieved, return above string.
             * The activity will change to this given data accordingly

             */

        }

    }

    private void checkIfPlatformOpenForNonHosts (
            final Context context, Activity activity, ScrollView scrollView, TabLayout tabLayout,
            float scrollView_org_pos_y, float tabLayout_org_pos_y, FrameLayout book_now_button){

        viewModel = new ApartmentActivityViewModel(observables);

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "isAppUnlocked/isAvailableNonHosts");

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                setBookNowOnClickListenerIfOnline(
                        context, dataSnapshot, activity, scrollView,
                        tabLayout, scrollView_org_pos_y, tabLayout_org_pos_y,
                        book_now_button);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setBookNowOnClickListenerIfOnline(
            Context context, DataSnapshot dataSnapshot, Activity activity, ScrollView scrollView,
            TabLayout tabLayout, float scrollView_org_pos_y, float tabLayout_org_pos_y,
            FrameLayout book_now_button){

        if (!StartupMethods.isOnline(context)) {

            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            // inform the user that their device is not connected to the internet by showing a toast

        }

        if (StartupMethods.isOnline(context) && dataSnapshot.getValue(Integer.class) == 1) {

            viewModel.setBookNowButtonOnClickListener(
                    context, activity, scrollView, tabLayout, scrollView_org_pos_y,
                    tabLayout_org_pos_y, book_now_button);

        }

    }

}
