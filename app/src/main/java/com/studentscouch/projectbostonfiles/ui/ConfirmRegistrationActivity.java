package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.studentscouch.projectbostonfiles.db.dbImplementers.ConfirmRegistrationDBImplementer;
import com.studentscouch.projectbostonfiles.view.viewImplementers.ConfirmRegistrationViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.ConfirmRegistrationActivityView;

public class ConfirmRegistrationActivity extends AppCompatActivity {

    /*

    private FloatingActionButton fab;

    private String
            firstName,
            lastName,
            password,
            birthdate,
            place_of_residence;

    private String
            street,
            house_number,
            title,
            description;

    private int price_per_night;

    private boolean
            isTwoPeopleAllowed,
            isClicked;

    private String gender;

    private ImageView
            apartementImage1_1,
            apartementImage2,
            apartementImage3;

    public static List <ImageView> apartmentImages;

    private ScrollView scrollView;

    private TextView
            firstName_view_textView,
            lastName_view_textView,
            email_view_textView,
            birthdate_view_textView,
            place_of_residence_view_textView,
            apartement_city_view_textView,
            street_view_textView,
            house_number_view_textView,
            title_view_textView,
            description_view_textView,
            price_per_night_view_textView,
            max_people_view_textView,
            error_textView;

    private FrameLayout log_in_layout;

    private Float
            log_in_layout_org_pos_x,
            scrollView_org_pos_x;

    private List<Float> original_pos_coordinates;

    private View log_in_layout_view;

    private Animator circularReveal;

    private ObjectAnimator
            anim,
            anim2;

    private ScrollView scrollview;

    private Intent i;

    private int
            isFromActivity,
            isNotNowAdded;

    private Typeface tp;

    private FirebaseAuth fbAuth;

    private FrameLayout progress_layout;

    private Animation
            enter_anim,
            exit_anim;

    private int numApartmentImages;

    private String
            cityRegulationsRead = "NA";

    private int subleasedNightsLeft = 123456789;

    private String
            apartementImage2_petscop,
            apartementImage3_petscrop;

    private DatabaseReference
            fbRef1 = null,
            fbRef2 = null,
            fbRef3 = null,
            fbRef4 = null,
            fbRef5 = null;

    public static int
        day,
        month,
        year;

    private int
            numUsers,
            numHosts;

    private String countryCode;

    private FrameLayout
            firstName_view,
            lastName_view,
            email_view,
            birthdate_view,
            place_of_residence_view;

    private int isNotNowSelected;

    private List<View> viewsInLayout;

    private List<View> apartmentViews;

    private List <View> apartmentNotNullViews;

    private List<View> userViews;

    RelativeLayout masterLayout;

    public static AppCompatActivity appCompatActivity = null;

    */

    ConfirmRegistrationDBImplementer db;

    ConfirmRegistrationActivityView view;

    @Override
    protected void onStart() {
        super.onStart();

        view.removeOnGlobalLayoutListener();
        view.checkHowManyApartmentImagesAdded();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new ConfirmRegistrationViewImplementer(this, null);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

        /*
        setContentView(R.layout.activity_confirm_registration);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "UserData");

        TextView firstName_textView = findViewById(R.id.firstName_textView);
        firstName_view_textView = findViewById(R.id.firstName_view_textView);
        TextView lastName_textView = findViewById(R.id.lastName_textView);
        lastName_view_textView = findViewById(R.id.lastName_view_textView);
        TextView email_textView = findViewById(R.id.email_textView);
        email_view_textView = findViewById(R.id.email_view_textView);
        TextView birthdate_textView = findViewById(R.id.birthdate_textView);
        birthdate_view_textView = findViewById(R.id.birthdate_view_textView);
        TextView place_of_residence_textView = findViewById(R.id.place_of_residence_textView);
        place_of_residence_view_textView = findViewById(R.id.place_of_residence_view_textView);
        TextView profile_picture_textView = findViewById(R.id.profile_picture_textView);
        TextView apartement_data_textView = findViewById(R.id.apartement_data_textView);
        TextView apartement_images_textView = findViewById(R.id.apartement_images_textView);
        final TextView apartement_city_textView = findViewById(R.id.apartement_city_textView);
        apartement_city_view_textView = findViewById(R.id.apartement_city_view_textView);
        TextView street_textView = findViewById(R.id.street_textView);
        street_view_textView = findViewById(R.id.street_view_textView);
        TextView house_number_textView = findViewById(R.id.house_number_textView);
        house_number_view_textView = findViewById(R.id.house_number_view_textView);
        TextView title_textView = findViewById(R.id.title_textView);
        title_view_textView = findViewById(R.id.title_view_textView);
        TextView descriptiondescription = findViewById(R.id.description);
        description_view_textView = findViewById(R.id.description_view_textView);
        TextView price_per_night2 = findViewById(R.id.price_per_night);
        price_per_night_view_textView = findViewById(R.id.price_per_night_view_textView);
        TextView price_per_night_currency_view_textView = findViewById(R.id.price_per_night_currency_view_textView);
        TextView max_people_textView = findViewById(R.id.max_people_textView);
        max_people_view_textView = findViewById(R.id.max_people_view_textView);
        TextView gender_textView = findViewById(R.id.gender_textView);
        TextView log_in_textView = findViewById(R.id.log_in_textView);
        error_textView = findViewById(R.id.error_textView);

        FrameLayout apartement_city_view = findViewById(R.id.apartement_city_view);
        FrameLayout street_view = findViewById(R.id.street_view);
        final FrameLayout house_number_view = findViewById(R.id.house_number_view);
        FrameLayout title_view = findViewById(R.id.title_view);
        FrameLayout description_view = findViewById(R.id.description_view);
        FrameLayout price_per_night_view = findViewById(R.id.price_per_night_view);
        FrameLayout max_people_view = findViewById(R.id.max_people_view);

        firstName_view = findViewById(R.id.firstName_view);
        lastName_view = findViewById(R.id.lastName_view);
        email_view = findViewById(R.id.email_view);
        birthdate_view = findViewById(R.id.birthdate_view);
        place_of_residence_view = findViewById(R.id.place_of_residence_view);

        log_in_layout = findViewById(R.id.log_in_layout);

        progress_layout = findViewById(R.id.progress_layout);

        log_in_layout_view = findViewById(R.id.log_in_layout_view);

        masterLayout = findViewById(R.id.masterLayout);

        scrollView = findViewById(R.id.scrollView);

        ImageView profile_picture = findViewById(R.id.profile_picture_image);
        apartementImage1_1 = findViewById(R.id.image_1);
        apartementImage2 = findViewById(R.id.image_2);
        apartementImage3 = findViewById(R.id.image_3);

        scrollView = findViewById(R.id.scrollView);

        ImageView gender_imageView = findViewById(R.id.gender_imageView);

        fab = findViewById(R.id.fab);

        // connect Java variables to xml layout views

        // Load amount of users and hosts, the values of which will be incremented
        // when the user confirms his/her registration

        tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");

        firstName_textView.setTypeface(tp);
        firstName_view_textView.setTypeface(tp);
        lastName_textView.setTypeface(tp);
        lastName_view_textView.setTypeface(tp);
        email_textView.setTypeface(tp);
        email_view_textView.setTypeface(tp);
        birthdate_textView.setTypeface(tp);
        birthdate_view_textView.setTypeface(tp);
        place_of_residence_textView.setTypeface(tp);
        place_of_residence_view_textView.setTypeface(tp);
        profile_picture_textView.setTypeface(tp);
        apartement_data_textView.setTypeface(tp);
        apartement_images_textView.setTypeface(tp);
        apartement_city_textView.setTypeface(tp);
        apartement_city_view_textView.setTypeface(tp);
        street_textView.setTypeface(tp);
        street_view_textView.setTypeface(tp);
        house_number_textView.setTypeface(tp);
        house_number_view_textView.setTypeface(tp);
        title_textView.setTypeface(tp);
        title_view_textView.setTypeface(tp);
        descriptiondescription.setTypeface(tp);
        description_view_textView.setTypeface(tp);
        price_per_night2.setTypeface(tp);
        price_per_night_view_textView.setTypeface(tp);
        price_per_night_currency_view_textView.setTypeface(tp);
        max_people_textView.setTypeface(tp);
        max_people_view_textView.setTypeface(tp);
        gender_textView.setTypeface(tp);
        log_in_textView.setTypeface(tp);

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(scrollView);
        viewsInLayout.add(log_in_layout);

        fab.hide();

        // hide fab

        isClicked = false;


        // initialise and set typeface to all textViews in layout

        /*

        if (!getIntent().getStringExtra("countryCode").equals("") && !getIntent().getStringExtra("countryName").equals("")){

            countryCode = getIntent().getStringExtra("countryCode");
            countryName = getIntent().getStringExtra("countryName");

        }

        */

        /*

        appCompatActivity = ConfirmRegistrationActivity.this;

        apartmentImages = new ArrayList<>();

        apartmentViews = new ArrayList<>();

        apartmentViews.add(apartement_data_textView);
        apartmentViews.add(apartement_images_textView);
        apartmentViews.add(apartement_city_textView);
        apartmentViews.add(apartement_city_view_textView);
        apartmentViews.add(street_textView);
        apartmentViews.add(street_view_textView);
        apartmentViews.add(house_number_textView);
        apartmentViews.add(house_number_view_textView);
        apartmentViews.add(title_textView);
        apartmentViews.add(title_view_textView);
        apartmentViews.add(descriptiondescription);
        apartmentViews.add(description_view_textView);
        apartmentViews.add(price_per_night2);
        apartmentViews.add(price_per_night_view_textView);
        apartmentViews.add(price_per_night_currency_view_textView);
        apartmentViews.add(max_people_textView);
        apartmentViews.add(max_people_view_textView);

        apartmentNotNullViews = new ArrayList<>();

        apartmentNotNullViews.add(apartement_data_textView);
        apartmentNotNullViews.add(apartement_images_textView);
        apartmentNotNullViews.add(apartement_city_textView);
        apartmentNotNullViews.add(apartement_city_view_textView);
        apartmentNotNullViews.add(street_textView);
        apartmentNotNullViews.add(street_view_textView);
        apartmentNotNullViews.add(house_number_textView);
        apartmentNotNullViews.add(house_number_view_textView);
        apartmentNotNullViews.add(title_textView);
        apartmentNotNullViews.add(title_view_textView);
        apartmentNotNullViews.add(descriptiondescription);
        apartmentNotNullViews.add(description_view_textView);
        apartmentNotNullViews.add(price_per_night2);
        apartmentNotNullViews.add(price_per_night_view_textView);
        apartmentNotNullViews.add(price_per_night_currency_view_textView);
        apartmentNotNullViews.add(max_people_textView);
        apartmentNotNullViews.add(max_people_view_textView);
        apartmentNotNullViews.add(apartement_city_view);
        apartmentNotNullViews.add(street_view);
        apartmentNotNullViews.add(house_number_view);
        apartmentNotNullViews.add(title_view);
        apartmentNotNullViews.add(description_view);
        apartmentNotNullViews.add(price_per_night_view);
        apartmentNotNullViews.add(max_people_view);

        userViews = new ArrayList<>();

        userViews.add(gender_textView);
        userViews.add(gender_imageView);
        userViews.add(firstName_textView);
        userViews.add(firstName_view_textView);
        userViews.add(lastName_textView);
        userViews.add(lastName_view_textView);
        userViews.add(email_textView);
        userViews.add(email_view_textView);
        userViews.add(birthdate_textView);
        userViews.add(birthdate_view_textView);
        userViews.add(place_of_residence_textView);
        userViews.add(place_of_residence_view_textView);
        userViews.add(profile_picture);
        userViews.add(firstName_view);
        userViews.add(lastName_view);
        userViews.add(email_view);
        userViews.add(birthdate_view);
        userViews.add(place_of_residence_view);

        isNotNowSelected = prefs.getInt("isNotNowSelected", 3);

        // retrieve info on whether or not user has selected the 'not now' button upon having been asked to enter information about their apartment

        SharedPreferences prefs2 = getApplicationContext().getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        int choice = prefs2.getInt("Choicee", 5);

        // retrieve info on whether user has selected yes or no when having been asked to register as a host

        ConfirmRegistrationActivityMethods.removeViewsUserInfoNull(
                userViews, apartmentViews, isNotNowSelected, isFromActivity, choice);

        // check if user came from 'add apartment details' button, if true, make visible / hide according views

        ConfirmRegistrationActivityMethods.removeViewsApartmentInfoNull(
                userViews, apartmentViews, isNotNowSelected, isFromActivity, choice);

        // check if user came from 'add apartment details' button, if true, make visible / hide according views

        ConfirmRegistrationActivityMethods.removeViewsApartmentInfoNotNull(
                apartmentNotNullViews, userViews, isFromActivity, profile_picture,
                profile_picture_textView);

        // if user chose to register as host and not now isn't selected, make visible / hide according views

        fbRef5.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                numUsers = Integer.valueOf(dataSnapshot.child("numUsers").getValue(Integer.class));
                numHosts = Integer.valueOf(dataSnapshot.child("numHosts").getValue(Integer.class));

                // retrieve current number of users and hosts on the platform

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        fbRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Users");

        // DB link to users table

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements");

        // DB link to Apartments

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "items");

        // DB link to items table (contains subtables with Place ID, containing subTables with user apartments)

        fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "emails");

        // DB link to all user emails

        enter_anim = AnimationUtils.loadAnimation(ConfirmRegistrationActivity.this, R.anim.visibility_fade_in_animation);

        // load activity enter fade in animation

        enter_anim.setDuration(200);

        // set animation duration

        exit_anim = AnimationUtils.loadAnimation(ConfirmRegistrationActivity.this, R.anim.visibility_fade_out_animation);

        // load activity exit fade out animation

        exit_anim.setDuration(200);

        // set animation duration

        fbAuth = FirebaseAuth.getInstance();

        // assign firebase Auth object to global variable

        log_in_layout_org_pos_x = log_in_layout.getX();

        scrollView_org_pos_x = scrollView.getX();

        // get original x positions for onRestart() activity state animation

        original_pos_coordinates = new ArrayList<>();

        original_pos_coordinates.add(log_in_layout_org_pos_x);
        original_pos_coordinates.add(scrollView_org_pos_x);

        Bundle extras = getIntent().getExtras();

        ConfirmRegistrationActivityMethods.animateActivityTransition(
                log_in_layout, scrollView, getApplicationContext(), viewsInLayout,
                original_pos_coordinates, log_in_layout_org_pos_x, scrollView_org_pos_x);

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 5);

            // if user comes from final register activity, do nothing

        }

        try {

            countryCode = getIntent().getStringExtra("countryCode");



        } catch (Exception e) {

            // Do nothing

        }

        StartupMethods.getAllSavedUserDataRegistrationApartement(
                getApplicationContext(), firstName_view_textView, lastName_view_textView,
                email_view_textView,
                birthdate_view_textView, place_of_residence_view_textView, gender_imageView,
                profile_picture, apartementImage1_1, apartementImage2, apartementImage3,
                title_view_textView, street_view_textView, price_per_night_view_textView, apartement_city_view_textView,
                description_view_textView, house_number_view_textView, max_people_view_textView,
                apartement_data_textView, apartement_images_textView, apartement_city_textView, apartement_city_view,
                street_textView, street_view, house_number_textView, house_number_view,
                title_textView, title_view, price_per_night2, price_per_night_view,
                max_people_textView, max_people_view, descriptiondescription, description_view, isFromActivity,
                apartement_data_textView, apartement_city_textView, apartement_city_view_textView, street_view_textView,
                house_number_view_textView, title_view_textView, descriptiondescription, description_view_textView,
                price_per_night2, price_per_night_view_textView, price_per_night_currency_view_textView, max_people_textView,
                max_people_view_textView, gender_textView, gender_imageView, firstName_textView, firstName_view_textView,
                lastName_textView, lastName_view_textView, email_textView, email_view_textView,
                birthdate_textView, birthdate_view_textView, place_of_residence_textView, place_of_residence_view_textView,
                profile_picture_textView, firstName_view, lastName_view, email_view, birthdate_view, place_of_residence_view

        );

        // load sharedPreferences for retrieving registration data

        isNotNowAdded = prefs.getInt("isNotNowSelected", 0);

        // retrieve info on whether or not user has clicked 'not now' button when having been asked about submitting data about their apartment

        year = prefs.getInt("savedUserDataSharedBirthdateYear", 1998);

        month = prefs.getInt("savedUserDataSharedBirthdateMonth", 3);

        day = prefs.getInt("savedUserDataSharedBirthdateDay", 7);

        // retrieve user birthdate

        final String gender = prefs.getString("savedUserDataSharedGender", "female");

        // retrieve gender selected by user

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfirmRegistrationActivityMethods.initialiseFloatingActionButton(
                        getApplicationContext(), fab, log_in_layout, i,
                        scrollView, anim, anim2, progress_layout,
                        isNotNowAdded, isFromActivity, enter_anim, error_textView,
                        firstName_view_textView, lastName_view_textView, email_view_textView, birthdate_view_textView,
                        place_of_residence_view_textView, tp, fbAuth, fbRef1,
                        fbRef4, fbRef5, ConfirmRegistrationActivity.this, numUsers, exit_anim,
                        gender, numApartmentImages, apartementImage2_petscop, apartementImage3_petscrop,
                        apartement_city_textView, house_number_view_textView, title_view_textView, description_view_textView, price_per_night_view_textView,
                        max_people_view_textView, subleasedNightsLeft, cityRegulationsRead, numHosts,
                        fbRef2, fbRef3, countryCode,
                        apartementImage2, apartementImage3, apartementImage1_1, apartement_city_view_textView,
                        street_view_textView, firstName);

                // initialise fab. Method is responsible internally and externally processing all registration info submitted by the user

            }

        });

        log_in_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                ConfirmRegistrationActivityMethods.initialiseConfirmButton(getApplicationContext(), motionEvent, fab, log_in_layout_view);

                return true;

            }
        });

        */

    }

    public void onRestart() {

        super.onRestart();

        //   int marginTop = (int) (1750 / getResources().getDisplayMetrics().density);

        view.enableFab();

        view.animateActivityReEnterTransition(getApplicationContext(), this);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);
    }




}
