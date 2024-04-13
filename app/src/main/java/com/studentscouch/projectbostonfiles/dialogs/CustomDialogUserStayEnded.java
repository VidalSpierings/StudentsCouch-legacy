package com.studentscouch.projectbostonfiles.dialogs;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomDialogUserStayEnded extends androidx.appcompat.app.AppCompatDialogFragment {

    private static String
                UID = null,
                firstName = null,
                lastName = null,
                AID = null;

    private FrameLayout
            one_star_button_layout,
            one_and_a_half_stars_button_layout,
            two_stars_button_layout,
            two_and_a_half_stars_button_layout,
            three_stars_button_layout,
            three_and_a_half_stars_button_layout,
            four_stars_button_layout,
            four_and_a_half_stars_button_layout,
            five_stars_button_layout;

    private TextView
            one_star_button_textView,
            one_and_a_half_stars_textView,
            two_stars_button_textView,
            two_and_a_half_stars_textView,
            three_stars_button_textView,
            three_and_a_half_stars_textView,
            four_stars_button_textView,
            four_and_a_half_stars_textView,
            five_stars_button_textView,
            description_textView;

    private DatabaseReference
            fbRef;
    private DatabaseReference
            fbRef2,
            fbRef3,
            fbRef4,
            fbRef5,
            fbRef6,
            fbRef7,
            fbRef9;

    private String bookingID;

    static double ratingValue;

    private FloatingActionButton fab;

    private int numRatings = 0;

    private double valueOfAllChildren = 0;

    private int
            numChildren = 0,
            numHappyEmojis = 0;

    private int numUnhappyEmojis = 0;

    @SuppressLint("StaticFieldLeak")
    static ImageView
            happy_emoji,
            unhappy_emoji;

    private View
            unhappy_emoji_view,
            happy_emoji_view;

    private Animator circularReveal;

    private String locationID;

    static List<View> starRatingsButtons;

    private String bookingHostUID;

    private Toast rating_received;

    @NonNull
    @SuppressLint({"ClickableViewAccessibility", "ShowToast"})
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Firebase.setAndroidContext(Objects.requireNonNull(getActivity()).getApplicationContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.custom_dialog_user_stay_ended, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        getDataFromActivity();

        initArrays();

        initUI();

        CustomDIalogUserStayEndedMethods.setHappyEmojiOnClickListener(
                getContext(), unhappy_emoji_view, fab,
                happy_emoji_view, one_star_button_layout, one_and_a_half_stars_button_layout,
                two_stars_button_layout, two_and_a_half_stars_button_layout, three_stars_button_layout,
                three_and_a_half_stars_button_layout, four_stars_button_layout, four_and_a_half_stars_button_layout,
                five_stars_button_layout);

        // initialise happy emoji onClickListener

        CustomDIalogUserStayEndedMethods.setUnhappyEmojiOnClickListener(
                getContext(), unhappy_emoji_view,
                fab, one_star_button_layout, one_and_a_half_stars_button_layout,
                two_stars_button_layout, two_and_a_half_stars_button_layout, three_stars_button_layout,
                three_and_a_half_stars_button_layout, four_stars_button_layout, four_and_a_half_stars_button_layout,
                five_stars_button_layout, happy_emoji_view);

        // initialise unhappy emoji onClickListener

        CustomDIalogUserStayEndedMethods.setStarRatingButtonsOnClickListeners(getContext(), fab);

        getInfoFromDB();

        rating_received = Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.ratings_submitted), Toast.LENGTH_LONG);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);

        unhappy_emoji_view = view.findViewById(R.id.unhappy_emoji_view);
        happy_emoji = view.findViewById(R.id.happy_emoji);

        one_star_button_textView = view.findViewById(R.id.one_star_button_textView);
        one_and_a_half_stars_textView = view.findViewById(R.id.one_and_a_half_stars_textView);
        two_stars_button_textView = view.findViewById(R.id.two_stars_button_textView);
        two_and_a_half_stars_textView = view.findViewById(R.id.two_and_a_half_stars_textView);
        three_stars_button_textView = view.findViewById(R.id.three_stars_button_textView);
        three_and_a_half_stars_textView = view.findViewById(R.id.three_and_a_half_stars_textView);
        four_stars_button_textView = view.findViewById(R.id.four_stars_button_textView);
        four_and_a_half_stars_textView = view.findViewById(R.id.four_and_a_half_stars_textView);
        five_stars_button_textView = view.findViewById(R.id.five_stars_textView);

        one_star_button_layout = view.findViewById(R.id.one_star_button_layout);
        one_and_a_half_stars_button_layout = view.findViewById(R.id.one_and_a_half_stars_button_layout);
        two_stars_button_layout = view.findViewById(R.id.two_stars_button_layout);
        two_and_a_half_stars_button_layout = view.findViewById(R.id.two_and_a_half_stars_button_layout);
        three_stars_button_layout = view.findViewById(R.id.three_stars_button_layout);
        three_and_a_half_stars_button_layout = view.findViewById(R.id.three_and_a_half_stars_button_layout);
        four_stars_button_layout = view.findViewById(R.id.four_stars_button_layout);
        four_and_a_half_stars_button_layout = view.findViewById(R.id.four_and_a_half_stars_button_layout);
        five_stars_button_layout = view.findViewById(R.id.five_stars_button_layout);

        unhappy_emoji_view = view.findViewById(R.id.unhappy_emoji_view);
        happy_emoji_view = view.findViewById(R.id.happy_emoji_view);

        happy_emoji = view.findViewById(R.id.happy_emoji);
        unhappy_emoji = view.findViewById(R.id.unhappy_emoji);

        fab = view.findViewById(R.id.fab);

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                        getActivity())
                        .getApplicationContext()
                        .getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        one_star_button_textView.setTypeface(tp);
        one_and_a_half_stars_textView.setTypeface(tp);
        two_stars_button_textView.setTypeface(tp);
        two_and_a_half_stars_textView.setTypeface(tp);
        three_stars_button_textView.setTypeface(tp);
        three_and_a_half_stars_textView.setTypeface(tp);
        four_stars_button_textView.setTypeface(tp);
        four_and_a_half_stars_textView.setTypeface(tp);
        five_stars_button_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void initArrays(){

        initButtonsLayoutArray();

        initStarRatingButtons();

    }

    private void initButtonsLayoutArray(){

        List<FrameLayout> buttonsLayout = new ArrayList<>();

        buttonsLayout.add(one_star_button_layout);
        buttonsLayout.add(one_and_a_half_stars_button_layout);
        buttonsLayout.add(two_stars_button_layout);
        buttonsLayout.add(two_and_a_half_stars_button_layout);
        buttonsLayout.add(three_stars_button_layout);
        buttonsLayout.add(three_and_a_half_stars_button_layout);
        buttonsLayout.add(four_stars_button_layout);
        buttonsLayout.add(four_and_a_half_stars_button_layout);
        buttonsLayout.add(five_stars_button_layout);

    }

    private void initStarRatingButtons(){

        starRatingsButtons = new ArrayList<>();

        starRatingsButtons.add(one_star_button_layout);
        starRatingsButtons.add(one_and_a_half_stars_button_layout);
        starRatingsButtons.add(two_stars_button_layout);
        starRatingsButtons.add(two_and_a_half_stars_button_layout);
        starRatingsButtons.add(three_stars_button_layout);
        starRatingsButtons.add(three_and_a_half_stars_button_layout);
        starRatingsButtons.add(four_stars_button_layout);
        starRatingsButtons.add(four_and_a_half_stars_button_layout);
        starRatingsButtons.add(five_stars_button_layout);

    }

    @SuppressLint("SetTextI18n")
    private void initUI(){

        description_textView.setText(
                getResources().getString(R.string.stay_ended_guest1) + " "
                        + firstName + " " + lastName + " " + getResources().getString(R.string.stay_ended_guest2) + " "
                        + firstName + " " + getResources().getString(R.string.stay_ended_guest3));

        // display correct information in description textView

    }

    private void getDataFromActivity(){

        Bundle args = getArguments();
        UID = Objects.requireNonNull(args).getString("UID");
        AID = args.getString("AID");
        firstName = args.getString("firstName");
        lastName = args.getString("lastName");
        bookingID = args.getString("bookingID");

        Log.d("uinfo", "UID: " + UID);

        // retrieve values required for reference to booking-specific firebase links

    }

    private void initDbLinks(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + "/"
                        + ConstantsDB.APARTMENT_NUM_RATINGS_TABLE);

        // DB link to number of received ratings for specified apartments

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + "/"
                        + ConstantsDB.APARTMENT_RATING_AVERAGE_TABLE);

        // DB link to rating average for specified apartment

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.ALL_RATING_URL_REFERENCE);

        // DB link to all received ratings for specified apartment

        fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.APARTMENT_LOCATION_ID_TABLE_URL_REFERENCE);

        // DB link to apartment PlaceID

        fbRef7 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID
                        + "/"
                        + ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE);

        // create Firebase links

        /*

        DatabaseReference fbRef8 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID
                        + ConstantsDB.AID_URL_REFERENCE);

        */

    }

    private void retrieveLocationID(){

        fbRef5.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                locationID = dataSnapshot.getValue(String.class);

                // retrieve PlaceID for specified apartment

                fbRef6 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                        StartupMethods.DATABASE_LINK
                                + ConstantsDB.ITEMS_TABLE_URL_REFERENCE
                                + locationID
                                + "/"
                                + AID
                                + "/"
                                + ConstantsDB.ITEM_AVERAGE_RATING_TABLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        // retrieve Place ID of apartment

    }

    private void getAllRatingsSetOnClickListener(){

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                numChildren = (int) dataSnapshot.getChildrenCount();

                for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

                    double number = ds.getValue(Double.class);

                    valueOfAllChildren = valueOfAllChildren + number;

                    // retrieve amount of ratings

                }

                CustomDIalogUserStayEndedMethods.initialiseFloatingActionButton(
                        fab, fbRef, numRatings, fbRef3,
                        ratingValue, valueOfAllChildren, numChildren, fbRef2,
                        fbRef6, fbRef7, happy_emoji, unhappy_emoji,
                        numHappyEmojis, numUnhappyEmojis,
                        fbRef9, rating_received, CustomDialogUserStayEnded.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve Place ID of apartment

    }

    private void getNumRatings(){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                numRatings = dataSnapshot.getValue(Integer.class);

                // retrieve amount of ratings
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve number of ratings apartment has receieved

    }

    private void getNumPositiveNegativeRatings(){

        fbRef4.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

               // try {

                retrieveEmojiRatingData(dataSnapshot);

                getAllRatingsSetOnClickListener();

                Log.i("numEm", "num: " + numHappyEmojis);

                    /*

                } catch (Exception e){

                    numHappyEmojis = 0;
                    numUnhappyEmojis = 0;

                    // if happy and unhappy emojis can not be retrieved, set value of both variables to 0

                }

                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve number of happy and unhappy emojis Host has received

    }

    private void retrieveEmojiRatingData(DataSnapshot dataSnapshot){

        numHappyEmojis = dataSnapshot.child(ConstantsDB.USER_HAPPY_EMOJIS_RATING_TABLE).getValue(Integer.class);
        numUnhappyEmojis = dataSnapshot.child(ConstantsDB.USER_UNHAPPY_EMOJIS_RATING_TABLE).getValue(Integer.class);

        // retrieve number of happy and unhappy emojis

    }

    private void getInfoFromDB(){

        initDbLinks();

        retrieveLocationID();

        getNumRatings();

        retrieveBookingHostUIDRelatedData();

    }

    private void retrieveHostUIDAndEmojiRatings(){

        DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + "/"
                        + ConstantsDB.UID_TABLE);

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                retrieveAllRelatedInfo(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retrieveAllRelatedInfo(DataSnapshot dataSnapshot){

        bookingHostUID = dataSnapshot.getValue(String.class);

        initHostUIDLinks();

        getNumPositiveNegativeRatings();

    }

    private void retrieveBookingHostUIDRelatedData(){

        retrieveHostUIDAndEmojiRatings();

        Log.i("Uidinf", "info: " + bookingHostUID);

    }

    private void initHostUIDLinks(){

        fbRef9 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + bookingHostUID
                        + ConstantsDB.EMOJI_RATING_URL_REFERENCE);

        fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + bookingHostUID
                        + ConstantsDB.EMOJI_RATING_URL_REFERENCE);

        // DB link to all  emoji ratings

    }

}
