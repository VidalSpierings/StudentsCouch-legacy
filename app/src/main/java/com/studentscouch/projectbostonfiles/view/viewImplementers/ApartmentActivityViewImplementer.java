package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.observables.ApartmentActivityObservables;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.dbImplementers.ApartmentActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.ApartmentActivityDBInterface;
import com.studentscouch.projectbostonfiles.ui.AccountAndApartementActivity;
import com.studentscouch.projectbostonfiles.view.views.ApartmentActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.ApartmentActivityViewModel;
import com.studentscouch.projectbostonfiles.viewpagers_and_corresponding_files.CustomSwipeLayout;

public class ApartmentActivityViewImplementer implements ApartmentActivityView {

    private View edit_button_view;

    private TextView subleased_num_days;

    private TextView
            description_title,
            num_ratings,
            streetname_text,
            price_text,
            num_people_per_stay_num_textView,
            gmaps_text,
            book_now_textView,
            num_people_per_stay_desc_textView,
            subleased_description;

    private FrameLayout
            book_now_button_layout,
            frameLayout,
            edit_frameLayout,
            gmaps_layout;

    private FrameLayout progress_layout;

    private TextView description_description;

    private LinearLayout slideDotsPanel;

    private ViewPager viewPager;

    private ImageView rating_imageView;

    private String fromActivity = null;

    private ScrollView scrollView;

    private CustomSwipeLayout customSwipeLayout;

    private ImageView[] dots;

    private int dotsCount;

    private ObjectAnimator anim;

    private ObjectAnimator anim2;

    private float scrollView_org_pos_y;

    private TabLayout tabLayout;

    private float tabLayout_org_pos_y;

    private Animation
            exit_anim_progress_layout,
            enter_anim_progress_layout;

    private LinearLayout subleased_nights_layout;

    private ImageView edit_imageView;

    private ToggleButton favorite_togglebutton;

    // initialisation java variables used throughout the file

    private View rootView;

    private ApartmentActivityObservables observables;

    private ApartmentActivityViewModel viewModel;

    private ViewGroup mainViewGroup;

    private String wordForRatingsGrammer;

    private String allText;

    private Toast toastAccToNetworkStatus;

    @SuppressLint("InflateParams")
    public ApartmentActivityViewImplementer(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_apartement, null);

        mainViewGroup = viewGroup;

    }

    @Override
    public void initViews(Context context, Activity activity) {

        linkXmlElementsToJavaVariables();

        setTypefaces(context);

    }

    private void setTypefaces(Context context){

        Typeface tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

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

    }

    private void linkXmlElementsToJavaVariables(){

        subleased_nights_layout = rootView.findViewById(R.id.subleased_nights_layout);

        streetname_text = rootView.findViewById(R.id.streetname_text);

        progress_layout = rootView.findViewById(R.id.progress_layout);

        scrollView = rootView.findViewById(R.id.scrollView);

        viewPager = rootView.findViewById(R.id.apartement_images_imageView);

        price_text = rootView.findViewById(R.id.price_text);

        subleased_num_days = rootView.findViewById(R.id.subleased_num_days);
        subleased_description = rootView.findViewById(R.id.subleased_description);
        description_title = rootView.findViewById(R.id.description_title);
        description_description = rootView.findViewById(R.id.description_description);
        num_ratings = rootView.findViewById(R.id.num_ratings);
        gmaps_text = rootView.findViewById(R.id.gmaps_text);
        book_now_textView = rootView.findViewById(R.id.book_now_textView);
        num_people_per_stay_desc_textView = rootView.findViewById(R.id.num_people_per_stay_desc_textView);
        num_people_per_stay_num_textView = rootView.findViewById(R.id.num_people_per_stay_num_textView);

        gmaps_layout = rootView.findViewById(R.id.gmaps_layout);
        book_now_button_layout = rootView.findViewById(R.id.book_now_button_layout);
        frameLayout = rootView.findViewById(R.id.frameLayout);
        subleased_nights_layout = rootView.findViewById(R.id.subleased_nights_layout);
        edit_frameLayout = rootView.findViewById(R.id.edit_frameLayout);

        slideDotsPanel = rootView.findViewById(R.id.slideDotsPanel);

        favorite_togglebutton = rootView.findViewById(R.id.favorite_togglebutton);

        edit_imageView = rootView.findViewById(R.id.edit_imageView);
        rating_imageView = rootView.findViewById(R.id.rating_imageView);

        edit_button_view = rootView.findViewById(R.id.edit_button_view);

        // connect xml views to corresponding java variables

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(Context context, ViewGroup container, Activity activity) {

        initVariables(activity);

        showProgressLayoutIfNeccessary(context, activity);

        removeFrameLayoutGlobalListener();

        ApartmentActivityDBInterface db = new ApartmentActivityDBImplementer(this, observables);

        db.getDataFromDB(
                context, mainViewGroup, activity, scrollView,
                tabLayout, scrollView_org_pos_y, tabLayout_org_pos_y, book_now_button_layout);

        favorite_togglebutton.setOnCheckedChangeListener((compoundButton, isChecked) -> {

           viewModel.toggleButtonClicked(context, compoundButton);

            // upon clicking, show animation and inform user that this function will be added in a later version

        });
        // initialise favorite toggle button

        edit_imageView.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                viewModel.launchEditActivity(context, activity, edit_button_view);

            }

            return true;
        });

        // initialise edit button

        gmaps_layout.setOnClickListener(view -> viewModel.launchGmapsIntent(context));

        // initialise Google Maps button

        book_now_button_layout.setOnClickListener(view -> viewModel.launchBookNowActivity(
                context, activity, scrollView, tabLayout,
                scrollView_org_pos_y, tabLayout_org_pos_y));

        // initialise 'book' button

    }

    @Override
    public View getFragmentRootView(Context context, ViewGroup viewGroup) {

        return  rootView;

    }

    private void initVariables(Activity activity){

        initActivitySubClasses();

        retrieveDataFromPreviousActivity(activity);

        initMiscVariables();

        getLayoutElementsYcoordinates();

        // get y coordinate for current position of scrollView, coordinates will be used for animations

    }

    private void initMiscVariables(){

        tabLayout = AccountAndApartementActivity.tablayout;

    }

    private void initActivitySubClasses(){

        observables = new ApartmentActivityObservables();
        viewModel = new ApartmentActivityViewModel(observables);

    }

    private void getLayoutElementsYcoordinates(){

        scrollView_org_pos_y = scrollView.getY();

        tabLayout_org_pos_y = tabLayout.getY();

    }

    private void removeFrameLayoutGlobalListener(){

        ViewTreeObserver vto = frameLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

        // wait until layout is  fully loaded to remove global layout listener from frameLayout

    }

    private void retrieveDataFromPreviousActivity(Activity activity){

        observables.setUID(activity.getIntent().getStringExtra("UID"));

        observables.setAID(activity.getIntent().getStringExtra("AID"));

        // Retrieve AID from apartment item selected in previous activity, if possible

        fromActivity = activity.getIntent().getStringExtra("fromActivity");

    }

    private void showProgressLayoutIfNeccessary(Context context, Activity activity){

        initAnims(activity);

        if(streetname_text.getText().equals(context.getResources().getString(R.string.loading))){

            progress_layout.startAnimation(enter_anim_progress_layout);

            // start loading animation

            progress_layout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);

        } else {

            MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

        }

        // enable loading screen. Standard text set to textViews is 'Loading...' when textViews are set and data is loaded,
        // hide loading screen

    }

    private void initAnims(Activity activity){

        exit_anim_progress_layout = AnimationUtils.loadAnimation(activity, R.anim.visibility_fade_out_animation);

        enter_anim_progress_layout = AnimationUtils.loadAnimation(activity, R.anim.visibility_fade_in_animation);

        // initialise/load animations that will be used upon opening and leaving the activity later on in the code

        exit_anim_progress_layout.setDuration(200);

        enter_anim_progress_layout.setDuration(200);

    }

    @Override
    public void setRatingsTextViewGrammar(Context context) {

        // Set grammer for word refferring to the number of ratings.
        // Also hide star ratings imageView entirely if num ratings equals 0

        initStrings();

        try {

            setWordBeforeRatingGrammar(context);

            allText = observables.getNumRatings() + " " + wordForRatingsGrammer;

            // ensure correct spelling when number of ratings is equal to 0, 1, or greater than 1

            num_ratings.setText(allText);

        } catch (Exception e){

            // do nothing

        }

    }

    private void initStrings(){

        wordForRatingsGrammer = "";
        allText = "";

    }

    private void setWordBeforeRatingGrammar(Context context){

        if (observables.getNumRatings() == 1){

            wordForRatingsGrammer = context.getResources().getString(R.string.one_rating);

        }

        if (observables.getNumRatings() > 1){

            wordForRatingsGrammer = context.getResources().getString(R.string.ratings);

        }

        if (observables.getNumRatings() < 1){

            rating_imageView.setVisibility(View.GONE);

            wordForRatingsGrammer = context.getResources().getString(R.string.ratings);

        }

    }

    @Override
    public void insertRetrievedInfoIntoTextViews(Context context) {

        try {

            showInfo(context);

        } catch (Exception e) {

            // Do nothing

        }

    }

    @SuppressLint("SetTextI18n")
    private void showInfo(Context context){

        String formattedPricePerNightText =
                "$ " + observables.getPrice_per_night() + ",-" + " " + context.getResources().getString(R.string.per_night);

        description_title.setText(observables.getTitle());
        description_description.setText(observables.getDescription());
        streetname_text.setText(observables.getStreet() + " " + observables.getHouse_number());

        // set apartment title, description, and address

        subleased_num_days.setText(String.valueOf(observables.getNumSubleasedNightsLeft()));
        price_text.setText(formattedPricePerNightText);

        // show loaded data in textViews

    }

    @Override
    public void checkIfUserViewingOwnApartment() {

            alienApartmentInAmsterdam();

            ownApartmentInAmsterdam();

            alienApartmentOutsideAmsterdam();

            ownApartmentOutsideAmsterdam();

    }

    @Override
    public void checkIfTwoPeoplePerBookingAllowed(Context context) {

        num_people_per_stay_num_textView.setText(context.getResources().getString(R.string.unknown_string));

        // if amount of people allowed per booking is somehow out of bounds, set number of people allowed per booking to 'unknown'

        twoPeopleAllowedProtocol();

        onePersonAllowedProtocol();

    }

    @Override
    public void informUserApartmentOutOfSubleasedNights(Context context) {

        toastAccToNetworkStatus = null;

        int subleasedNightsInt = observables.getNumSubleasedNightsLeft();

        // assign number of remaining days to Int object

        subleased_num_days.setText(String.valueOf(observables.getNumSubleasedNightsLeft()));

        // set received number of subleased nights remaining to corresponding textView

        changeToastAccToNetworkStatus(context, subleasedNightsInt);

        showToastIfOutOfSubleasedNights(subleasedNightsInt);

        }

    private void showToastIfOutOfSubleasedNights(int subleasedNightsInt) {

        if (subleasedNightsInt <= 0) {

            Toast finalToastAccToNetworkStatus = toastAccToNetworkStatus;
            book_now_button_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finalToastAccToNetworkStatus.show();

                }
            });

            // display number of nights left. If apartment is out of available subleased nights, inform user by showing a toast

        }
    }

    @SuppressLint("ShowToast")
    private void changeToastAccToNetworkStatus(Context context, int subleasedNightsInt){

        if (subleasedNightsInt <= 0 && StartupMethods.isOnline(context)) {

            toastAccToNetworkStatus = Toast.makeText(context, context.getResources().getString(R.string.apartement_can_not_be_booked), Toast.LENGTH_SHORT);

        }

        if (subleasedNightsInt <= 0 && !StartupMethods.isOnline(context)) {

            toastAccToNetworkStatus = Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT);

        }

    }

    @Override
    public void initSwipeDotsLayout(Context context) {

        try {

            StartupMethods.initialiseSwipeDotsLayout(
                    context, observables.getApartment1String(), observables.getApartment2String(), observables.getApartment3String(),
                    viewPager, slideDotsPanel);

        } catch (Exception e){

            // Do nothing

        }

    }

    @Override
    public void makeSubleasedNightsLayoutDisappear() {

        subleased_nights_layout.setVisibility(View.GONE);

    }

    @Override
    public void setExitAnimListener(Context context, Activity activity) {

        StudentsCouchAnimations anims = new StudentsCouchAnimations(activity);

        anims.setExitAnimListenerApartmentActivity(
                context, exit_anim_progress_layout, fromActivity,
                scrollView, scrollView_org_pos_y, progress_layout);

    }

    @Override
    public ImageView getRatingImageView() {

        return rating_imageView;

    }

    private void onePersonAllowedProtocol(){

        if (observables.getIsTwoPeopleAllowed() == 1){

            num_people_per_stay_num_textView.setText("1");

            // if one person per booking are allowed to stay, change data in apartment layout accordingly

        }

    }

    private void twoPeopleAllowedProtocol(){

        if (observables.getIsTwoPeopleAllowed() == 2){

            num_people_per_stay_num_textView.setText("2");

            // if two people per booking are allowed to stay, change data in apartment layout accordingly

        }

    }

    private void alienApartmentInAmsterdam(){

        if (observables.getLocationID().equals(StartupMethods.AMS_PLACE_ID)  &&
                !FirebaseAuth.getInstance().getCurrentUser().getUid().equals(observables.getUID())){

            Log.i("optionC",  "1");

            book_now_button_layout.setVisibility(View.VISIBLE);

            edit_frameLayout.setVisibility(View.INVISIBLE);

            subleased_nights_layout.setVisibility(View.GONE);

            /*

             * if apartment is located in Amsterdam and user is not viewing their own apartment,
             * disable the ability to edit apartment and make subleased nights left view disappear

             */

        }

    }

    private void ownApartmentInAmsterdam(){

        if (observables.getLocationID().equals(StartupMethods.AMS_PLACE_ID)  &&
                FirebaseAuth.getInstance().getCurrentUser().getUid().equals(observables.getUID())){

            Log.i("optionC",  "2");

            book_now_button_layout.setVisibility(View.GONE);

            edit_frameLayout.setVisibility(View.VISIBLE);

            subleased_nights_layout.setVisibility(View.VISIBLE);

            /*

             * if user is viewing their own apartment, that is located in Amsterdam,
             * enable the user to edit the apartment and disable the option of booking a stay in the apartment

             */

        }

    }

    private void alienApartmentOutsideAmsterdam(){

        if (!observables.getLocationID().equals(StartupMethods.AMS_PLACE_ID) &&
                !FirebaseAuth.getInstance().getCurrentUser().getUid().equals(observables.getUID())) {

            Log.i("locID8",  observables.getUID() + " " + FirebaseAuth.getInstance().getCurrentUser().getUid());

            Log.i("optionC",  "3");

            book_now_button_layout.setVisibility(View.VISIBLE);

            edit_frameLayout.setVisibility(View.INVISIBLE);

            subleased_nights_layout.setVisibility(View.GONE);

            /*

             * if apartment is not located in Amsterdam and user is not viewing their own apartment,
             * disable the ability to edit apartment and make subleased nights left view disappear

             */

        }

    }

    private void ownApartmentOutsideAmsterdam(){

        if (!observables.getLocationID().equals(StartupMethods.AMS_PLACE_ID) &&
                FirebaseAuth.getInstance().getCurrentUser().getUid().equals(observables.getUID())) {

            Log.i("optionC",  "4");

            book_now_button_layout.setVisibility(View.GONE);

            edit_frameLayout.setVisibility(View.VISIBLE);

            subleased_nights_layout.setVisibility(View.GONE);


            /*

             * if apartment is not located in Amsterdam and user is viewing their own apartment,
             * enable the option to edit apartment data, make subleased nights left view visible, make book now button disappear

             */

        }

    }

    @Override
    public void initLayoutOnStart(){

        edit_button_view.setVisibility(View.INVISIBLE);

        // make edit button reveal view invisible

        ViewTreeObserver vto = frameLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

        // remove frameLayout ViewTreeObserver

    }

    @Override
    public void startActivityEnterAnimations(Context context){

        StudentsCouchAnimations.animateActivityReEnterTransitionApartmentActivity(
                context, scrollView, scrollView_org_pos_y,
                tabLayout_org_pos_y, tabLayout);

    }

}
