package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentscouch.projectbostonfiles.view.viewImplementers.ApartmentActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.ApartmentActivityView;

public class ApartementActivity extends Fragment {

    /*

    private View edit_button_view;

    private TextView subleased_num_days;

    private TextView
            description_title,
            num_ratings,
            streetname_text,
            price_text,
            num_people_per_stay_num_textView;

    private FrameLayout
            book_now_button_layout,
            frameLayout,
            progress_layout;

    public static TextView description_description;

    private LinearLayout slideDotsPanel;

    private ImageView[] dots;

    private int dotsCount;

    private ScaleAnimation scaleAnimation;

    private ViewPager viewPager;

    private CustomSwipeLayout customSwipeLayout;

    private ImageView rating_imageView;

    private Animator circularReveal;

    private String fromActivity = null;

    private ScrollView scrollView;

    private ObjectAnimator anim;

    private ObjectAnimator anim2;

    private float scrollView_org_pos_y;

    private TabLayout tabLayout;

    private float tabLayout_org_pos_y;

    private Animation exit_anim_progress_layout;

    private DatabaseReference fbRef2 = null;

    private String AID = null;

    public static String
        city_or_village,
        description,
        house_number,
        locationID,
        street,
        title,
        UID;

    public static int
            price_per_night,
            numRatings;

    public static double ratingAverage;

    public static int isTwoPeopleAllowed;

    private LinearLayout subleased_nights_layout;

    private int numSubleasedNightsLeft = 123456789;

    private boolean firstVisit;

    public static String
            apartement1String = "",
            apartement2String = "",
            apartement3String = "";

    private String IBAN = "NO_IBAN_ADDED";

    String hostUID;

    // initialisation java variables used throughout the file

    */

    ApartmentActivityView view;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        view = new ApartmentActivityViewImplementer(getContext(), container);

        view.initViews(getContext(), getActivity());
        view.initData(getContext(), container, getActivity());

        /*

        Firebase.setAndroidContext(getActivity().getApplicationContext());

        // enable Firebase for this application

        firstVisit = true;

        View view = inflater.inflate(R.layout.activity_apartement, container, false);

        // set activity layout file

        subleased_nights_layout = view.findViewById(R.id.subleased_nights_layout);

        streetname_text = view.findViewById(R.id.streetname_text);

        progress_layout = view.findViewById(R.id.progress_layout);

        scrollView = view.findViewById(R.id.scrollView);

        viewPager = view.findViewById(R.id.apartement_images_imageView);

        price_text = view.findViewById(R.id.price_text);

        subleased_num_days = view.findViewById(R.id.subleased_num_days);
        TextView subleased_description = view.findViewById(R.id.subleased_description);
        description_title = view.findViewById(R.id.description_title);
        description_description = view.findViewById(R.id.description_description);
        num_ratings = view.findViewById(R.id.num_ratings);
        TextView gmaps_text = view.findViewById(R.id.gmaps_text);
        TextView book_now_textView = view.findViewById(R.id.book_now_textView);
        TextView num_people_per_stay_desc_textView = view.findViewById(R.id.num_people_per_stay_desc_textView);
        num_people_per_stay_num_textView = view.findViewById(R.id.num_people_per_stay_num_textView);

        FrameLayout gmaps_layout = view.findViewById(R.id.gmaps_layout);
        book_now_button_layout = view.findViewById(R.id.book_now_button_layout);
        frameLayout = view.findViewById(R.id.frameLayout);
        subleased_nights_layout = view.findViewById(R.id.subleased_nights_layout);
        final FrameLayout edit_frameLayout = view.findViewById(R.id.edit_frameLayout);

        slideDotsPanel = view.findViewById(R.id.slideDotsPanel);

        ToggleButton favorite_togglebutton = view.findViewById(R.id.favorite_togglebutton);

        ImageView edit_imageView = view.findViewById(R.id.edit_imageView);
        rating_imageView = view.findViewById(R.id.rating_imageView);

        edit_button_view = view.findViewById(R.id.edit_button_view);

        // connect xml views to corresponding java variables

        Typeface tp = Typeface.createFromAsset(view.getContext().getAssets(), "project_boston_typeface.ttf");

        // find typeface in project and assign typeface to Typeface object

        subleased_num_days.setTypeface(tp);
        subleased_description.setTypeface(tp);
        description_title.setTypeface(tp);
        description_description.setTypeface(tp);
        num_ratings.setTypeface(tp);
        streetname_text.setTypeface(tp);
        gmaps_text.setTypeface(tp);
        price_text.setTypeface(tp);
        book_now_textView.setTypeface(tp);
        num_people_per_stay_desc_textView.setTypeface(tp);
        num_people_per_stay_num_textView.setTypeface(tp);

        // set typeface to all views containing text

        UID = getActivity().getIntent().getStringExtra("UID");

        AID = getActivity().getIntent().getStringExtra("AID");

        // retrieve UID and AID passed from previous activity. Ensures that user-specific data is displayed

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID);

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "isAppUnlocked/isAvailableNonHosts");

        // initialise database links

        Animation enter_anim_progress_layout = AnimationUtils.loadAnimation(getActivity(), R.anim.visibility_fade_in_animation);

        exit_anim_progress_layout = AnimationUtils.loadAnimation(getActivity(), R.anim.visibility_fade_out_animation);

        enter_anim_progress_layout.setDuration(200);

        exit_anim_progress_layout.setDuration(200);

        // initialise/load animations that will be used upon opening and leaving the activity later on in the code

        scrollView_org_pos_y = scrollView.getY();

        // get y coordinate for current postion of scrollView

        tabLayout = AccountAndApartementActivity.tablayout;

        tabLayout_org_pos_y = tabLayout.getY();

        // IBAN variable is set to NO_IBAN_ADDED. If host has added IBAN, this variable will allow the user to book this apartement.
        // if IBAN is not added, user will be prevented from booking this apartement and be informed by this with the help of a toast

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("apartementImagesTemp", MODE_PRIVATE).edit();

        // create sharedPreferences editor that will be used to pass images to next activity if user starts the apartment edit activity

        if(streetname_text.getText().equals(getResources().getString(R.string.loading))){

            progress_layout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);

        } else {

            progress_layout.clearAnimation();
            progress_layout.startAnimation(exit_anim_progress_layout);
            progress_layout.setVisibility(View.INVISIBLE);

        }

        // enable loading screen. Standard text set to textViews is 'Loading...' when textViews are set and data is loaded,
        // hide loading screen

        progress_layout.startAnimation(enter_anim_progress_layout);

        // start loading animation

        AID = getActivity().getIntent().getStringExtra("AID");

        // Retrieve AID from apartment item selected in previous activity, if possible

        fromActivity = getActivity().getIntent().getStringExtra("fromActivity");

        // retrieve what activity user came from

        ViewTreeObserver vto = frameLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

        // wait until layout is  fully loaded to remove global layout listener from frameLayout

        IBAN = "NO_IBAN_ADDED";

        final DatabaseReference fbRefChange;

        fbRefChange = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID);

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                apartement1String = dataSnapshot.child("apartment1").getValue(String.class);
                apartement2String = dataSnapshot.child("apartment2").getValue(String.class);
                apartement3String = dataSnapshot.child("apartment3").getValue(String.class);

                /*

                * get all apartment images. If second and/or third
                * apartment image isn't found, an empty string is returned.
                * the layout of the activity will change accordingly to the received data

                */

        /*

                hostUID = dataSnapshot.child("UID").getValue(String.class);

                // get user UID (user is always a host in this case)

                locationID = dataSnapshot.child("locationID").getValue(String.class);

                /*

                * get location ID of city/village apartment is located in.
                * if locationID of apartment is equal to Amsterdam locationID,
                * app functionality will change accordingly

                */

        /*

                final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Users/" + UID);

                fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                        try {

                            ApartmentActivityMethods.checkIfIbanAdded(
                                    fbRef2, getActivity().getApplicationContext(), IBAN, dataSnapshot, book_now_button_layout);

                            // check if IBAN has been submitted to apartment

                        } catch (Exception e) {

                            IBAN = "NO_IBAN_ADDED";

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

        /*

                try {

                    StartupMethods.initialiseSwipeDotsLayout(
                            getActivity().getApplicationContext(), apartement1String, apartement2String, apartement3String,
                            customSwipeLayout, dots, dotsCount, viewPager, slideDotsPanel);

                    //re-initialise swipeDotsLayout after having received images from DB



                } catch (Exception e){

                    // Do nothing

                }

                ApartmentActivityMethods.loadApartmentInfo(dataSnapshot);

                // attempt to retrieve trivial apartment data, I.E. description, house number, street, etc.

                try {

                    String isTwoPeopleAllowedString = dataSnapshot.child("isTwoPeopleAllowed").getValue(String.class);

                    isTwoPeopleAllowed = Integer.valueOf(isTwoPeopleAllowedString);

                    // attempt to retrieve whether or not host has allowed bookings for two people at the same time

                }catch (Exception e){

                    isTwoPeopleAllowed = 1;

                    // if info about user allowing a maximum number of people per booking cannot be retrieved, set amount of people allowed per booking to 1

                }

                // attempt to retrieve amount of people allowed per booking.
                // If amount of people allowed per booking can not be retrieved,
                // set If amount of people allowed per booking to 0.

                try {

                    numSubleasedNightsLeft = dataSnapshot.child("subleasedNightsLeft").getValue(Integer.class);

                    // attempt to retrieve amount of subleased nights left for apartment

                    subleased_nights_layout.setVisibility(View.GONE);

                    if (numSubleasedNightsLeft != StartupMethods.NUM_NIGHTS_N_A){

                        // 12.34.56.789 number of subleased nights means 'number of nights N/A'

                        // only apartments located in Amsterdam will have a set number of subleased nights.
                        // if apartment is not located in amsterdam, make view dissapear

                        subleased_nights_layout.setVisibility(View.VISIBLE);

                        ApartmentActivityMethods.informUserApartmentOutOfSubleasedNights(
                                getActivity().getApplicationContext(), subleased_num_days, numSubleasedNightsLeft, book_now_button_layout);

                    }

                    // inform user that apartment is out of subleaseable nights and therefore cannot be booked anymore

                } catch (Exception e) {

                    // Do nothing

                }

                ApartmentActivityMethods.setRatingsTextViewGrammar(getContext(), numRatings, num_ratings, rating_imageView);

                    // change 'rating' textView to 'ratings' or 'rating', depending on number of ratings received

                try {

                    ApartmentActivityMethods.insertRetrievedInfoIntoTextViews(
                            getActivity().getApplicationContext(), description_title, streetname_text,
                            subleased_num_days, price_text, numSubleasedNightsLeft);

                    // insert retrieved info from DB into TextViews in layout

                } catch (Exception e) {

                    // Do nothing

                }

                /*

                 try catch bracket prevents above code from being instantiated
                 when host interacts with first guest booking request in StartupActivity

                */

        /*

                ApartmentActivityMethods.checkIfUserViewingOwnApartment(
                        locationID, subleased_nights_layout, book_now_button_layout, UID, edit_frameLayout);

                // check if user is viewing their own apartment

                ApartmentActivityMethods.checkIfTwoPeoplePerBookingAllowed(getActivity().getApplicationContext(), num_people_per_stay_num_textView);

                // check if two people are allowed to stay during a single booking for this apartment

//TODO

                StartupMethods.setStarsRating(ratingAverage, rating_imageView);

                // set apartment rating average

                try {

                    ApartmentActivityMethods.setExitAnimListener(
                            getActivity().getApplicationContext(), exit_anim_progress_layout, fromActivity,
                            scrollView, scrollView_org_pos_y, anim, progress_layout);

                    // animate exit activity transition or make scrollView disappear depending on activity user came from

                } catch (Exception e) {

                    // Do nothing

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        ApartmentActivityMethods.initialiseFavoriteToggleButton(getContext(), favorite_togglebutton);

        // initialise favorite toggle button

        ApartmentActivityMethods.initialiseEditButton(
                getContext(), edit_imageView, edit_button_view, AID,
                locationID, apartement1String, apartement2String, apartement3String,
                editor, title, description, price_per_night, isTwoPeopleAllowed, getActivity());

        // initialise edit button

        ApartmentActivityMethods.initialiseGoogleMapsButton(
                getContext(), street, house_number, city_or_village,
                gmaps_layout);

        // initialise Google Maps button

        ApartmentActivityMethods.initialiseBookNowButton(
                getContext(), book_now_button_layout, scrollView, tabLayout,
                anim2, scrollView_org_pos_y, tabLayout_org_pos_y, UID, AID,
                house_number, street, city_or_village, getActivity());

        // initialise 'book' button

        return view;

        */

        return view.getFragmentRootView(getContext(), container);

    }

    @Override
        public void onStart() {
        super.onStart();

        view.initLayoutOnStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        view.startActivityEnterAnimations(getContext());

    }

    /*
    public static void insertRetrievedInfoIntoTextViews (
            Context context, TextView description_title, TextView streetname_text,
            TextView subleased_num_days, TextView price_text, int numSubleasedNightsLeft,
            String apartmentTitle, String apartementDescription, String apartmentStreet, String apartmentHouseNumber,
            TextView description_description){

        try {

            String formattedPricePerNightText = "€ " + ApartementActivity.price_per_night + ",-" + " " + context.getResources().getString(R.string.per_night);

            description_title.setText(apartmentTitle);
            description_description.setText(apartementDescription);
            streetname_text.setText(apartmentStreet + " " + apartmentHouseNumber);

            // set apartment title, description, and address

            subleased_num_days.setText(String.valueOf(numSubleasedNightsLeft));
            price_text.setText(formattedPricePerNightText);

            // show loaded data in textViews

        } catch (Exception e) {

            // Do nothing

        }

    }

    */

}
