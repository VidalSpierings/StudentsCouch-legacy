package com.studentscouch.projectbostonfiles.ui;

import android.app.Dialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.dbImplementers.BookingActivitiesDBImplementer;
import com.studentscouch.projectbostonfiles.view.viewImplementers.BookingActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.BookingActivityView;

public class BookingActivity extends AppCompatActivity {

    /*

    private FloatingActionButton fab;

    private TextView
            date_textView,
            dates_booked_textView;


    private Typeface tp;

    private static final int DIALOG_ID = 0;

    private int
            year,
            month,
            day;

    private LinearLayout booked_dates_layout;

    private RelativeLayout activity_relativeLayout;

    public static String
        yearString,
        monthString,
        dayString;

    public static int
        selectedYearInt,
        selectedMonthInt,
        selectedDayInt;

    private ObjectAnimator anim;

    private RelativeLayout relativeLayout;

    private float relativeLayout_org_pos_x;

    public static String AID = null;

    private int numDaysStay;

    String bitmap;

    public static ArrayList<ArrayList<Integer>> bookedDatesList = new ArrayList<ArrayList<Integer>>();

    public static int isTwoPeopleAllowed = 1;

    public static boolean hasBeenUsed = false;

    public static String
            city_or_village,
            street,
            house_number;

    public static String
            firstNameHost,
            lastNameHost;

    */

    BookingActivityView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new BookingActivityViewImplementer(this, null);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

        /*

        fab = findViewById(R.id.fab);

        TextView arrival_date_textView = findViewById(R.id.arrival_date_textView);
        TextView choose_date_textView = findViewById(R.id.choose_date_textView);
        date_textView = findViewById(R.id.date_textView);
        dates_booked_textView = findViewById(R.id.dates_booked_textView);

        FrameLayout choose_date_layout = findViewById(R.id.choose_date_layout);
        booked_dates_layout = findViewById(R.id.booked_dates_layout);
        activity_relativeLayout = findViewById(R.id.activity_relativeLayout);
        relativeLayout = findViewById(R.id.relativeLayout);

        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        // connect java variables to xml layout views

        relativeLayout_org_pos_x = relativeLayout.getX();

        // get x coordinate of current position relativeLayout

        SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
        bitmap = prefs.getString("string", null);

        // get background image Base 64 String and assign to String object named 'bitmap'

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        // convert (base64) String object to bitmap object

        backgroundImage.setImageBitmap(image);

        // load and set background image of city apartment is located in

        tp = Typeface.createFromAsset(getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        arrival_date_textView.setTypeface(tp);
        choose_date_textView.setTypeface(tp);
        date_textView.setTypeface(tp);
        dates_booked_textView.setTypeface(tp);

        // initialise and set typeface to all textViews

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) - 1;
        month = calendar.get(Calendar.MONTH) + 3;
        day = calendar.get(Calendar.DAY_OF_YEAR) + 1;

        // create calendar object and set to current date of device

        fab.hide();

        Firebase.setAndroidContext(this);

        // enable fireBase

        animateActivityTransition();

        // start 'enter activity' animations

        AID = getIntent().getStringExtra("AID");

        city_or_village = getIntent().getStringExtra("cityVillage");

        street = getIntent().getStringExtra("street");

        house_number = getIntent().getStringExtra("house_number");

        firstNameHost = getIntent().getStringExtra("firstNameHost");

        lastNameHost = getIntent().getStringExtra("lastNameHost");

        // retrieve user/apartment metadata passed in from previous activity

        // get apartment data passed from previous activity

        final DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID);

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                isTwoPeopleAllowed = dataSnapshot.child("isTwoPeopleAllowed").getValue(Integer.class);

                // retrieve data on whether or not host allowed two people per booking

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve amount of people allowed per night

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID + "/bookings");

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                for(com.google.firebase.database.DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    BookingActivityMethods.retrieveBookingAtIndex(getApplicationContext(), postSnapshot);

                    // retrieve booking at index

                }

                BookingActivityMethods.initialiseBookingAtIndex(getApplicationContext(), bookedDatesList, tp, booked_dates_layout);

                // set text in TextView, layout params, etc. To date at index

                if (bookedDatesList.isEmpty()){

                    dates_booked_textView.setVisibility(View.GONE);

                    // if there are no available dates, hide first textView

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve all made bookings and add bookings to bookedDatesList array

            BookingActivityMethods.initialiseFloatingActionButton(
                    getApplicationContext(), fab, relativeLayout, relativeLayout_org_pos_x,
                    anim, dayString, monthString, yearString,
                    selectedYearInt, selectedMonthInt, selectedDayInt, isTwoPeopleAllowed,
                    AID, city_or_village, street, house_number, firstNameHost, lastNameHost, this);

            // initialise floatingActionButton

            choose_date_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showDialog(DIALOG_ID);

                }
            });

            // show custom dialog when user presses 'choose date' button

        */

        }

    @Override
    protected void onStart() {
        super.onStart();

        view.makeFabClickable();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        view.animateOnActivityRestart(getApplicationContext(), this);
        // if user returns from previous activities, initiate according animations

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);
    }


    @Override
    protected Dialog onCreateDialog(int id) {

        return view.getDateFromDialog(id);

    }

    /*

    public void animateActivityTransition(){

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", metrics.widthPixels - relativeLayout_org_pos_x, relativeLayout_org_pos_x);
        anim.setDuration(400);

        anim.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    */



}
