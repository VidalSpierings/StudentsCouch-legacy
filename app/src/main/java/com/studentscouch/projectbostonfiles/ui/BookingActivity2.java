package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.studentscouch.projectbostonfiles.view.viewImplementers.BookingActivity2ViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.BookingActivity2View;

public class BookingActivity2 extends AppCompatActivity {

    /*

    private View
            one_nights_button_view,
            two_nights_button_view,
            one_button_view,
            two_button_view,
            three_button_view,
            four_button_view,
            five_button_view,
            six_button_view,
            seven_button_view,
            eight_button_view,
            nine_button_view,
            ten_button_view,
            eleven_button_view,
            twelve_button_view,
            thirteen_button_view,
            fourteen_button_view,
            eight_to_eleven_fiftynine_button_view,
            twelve_to_four_fiftynine_button_view,
            five_to_ten_button_view;

    private FrameLayout
            one_layout_nights,
            two_layout_nights,
            three_layout,
            four_layout,
            five_layout,
            six_layout,
            seven_layout,
            eight_layout,
            nine_layout,
            ten_layout,
            eleven_layout,
            twelve_layout,
            thirteen_layout,
            fourteen_layout;

    public static FrameLayout
            one_layout,
            two_layout;

    private Animator circularReveal;

    private FloatingActionButton fab;

    public static  ArrayList<ArrayList<Integer>> bookedDatesList = new ArrayList<ArrayList<Integer>>();

    private int
            yearInt,
            monthInt,
            dayInt;

    private String arrival_date;

    private GregorianCalendar
            calendar1,
            calendar2,
            calendar3,
            calendar4,
            calendar5,
            calendar6,
            calendar7,
            calendar8,
            calendar9,
            calendar10,
            calendar11,
            calendar12,
            calendar13,
            calendar14;

    private ObjectAnimator anim = null;

    private RelativeLayout relativeLayout;

    private float relativeLayout_org_pos_x;

    private String AID;

    private DatabaseReference fbRef2;

    private String numDaysLeftString;

    private int numDaysLeftInteger;

    private int numDaysStay;

    public static int num_nights;

    public static int price_per_night;

    boolean hasBeenUsed = false;

    public static String
            city_or_village,
            house_number,
            street;

    private String
            firstNameHost,
            lastNameHost;

    public static FrameLayout
            eight_to_six_framelayout,
            twelve_to_four_fiftynine_layout,
            five_to_ten_layout;

    private TextView
            eight_to_eleven_fiftynine_textView,
            twelve_to_four_fiftynine_textView,
            five_to_ten_textView;

    public static List <View> allNumNightsLayouts;

    List <View> revealViews;

    List <GregorianCalendar> calendarObjects;

    public static List <View> allTimeArrivalLayouts;

    List <View> allNumNightsRevealViews;

    */

    BookingActivity2View view;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new BookingActivity2ViewImplementer(this, null);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

        /*

        setContentView(R.layout.activity_booking2);

        price_per_night

        AID = getIntent().getStringExtra("AID");

        TextView amount_of_people_textView = findViewById(R.id.your_booking_textView);
        TextView amount_of_nights_textView = findViewById(R.id.amount_of_people_textView);
        TextView one_textView = findViewById(R.id.one_textView);
        TextView two_textView = findViewById(R.id.two_textView);
        final TextView one_textView_nights = findViewById(R.id.one_textView_nights);
        final TextView two_textView_nights = findViewById(R.id.two_textView_nights);
        final TextView three_textView = findViewById(R.id.three_textView);
        final TextView four_textView = findViewById(R.id.four_textView);
        final TextView five_textView = findViewById(R.id.five_textView);
        final TextView six_textView = findViewById(R.id.six_textView);
        final TextView seven_textView = findViewById(R.id.seven_textView);
        final TextView eight_textView = findViewById(R.id.eight_textView);
        final TextView nine_textView = findViewById(R.id.nine_textView);
        final TextView ten_textView = findViewById(R.id.ten_textView);
        final TextView eleven_textView = findViewById(R.id.eleven_textView);
        final TextView twelve_textView = findViewById(R.id.twelve_textView);
        final TextView thirteen_textView = findViewById(R.id.thirteen_textView);
        final TextView fourteen_textView = findViewById(R.id.fourteen_textView);
        eight_to_eleven_fiftynine_textView = findViewById(R.id.eight_to_eleven_fiftynine_textView);
        twelve_to_four_fiftynine_textView = findViewById(R.id.twelve_to_four_fiftynine_textView);
        five_to_ten_textView = findViewById(R.id.five_to_ten_textView);

        one_layout_nights = findViewById(R.id.one_layout_nights);
        two_layout_nights = findViewById(R.id.two_layout_nights);
        one_layout = findViewById(R.id.one_layout);
        two_layout = findViewById(R.id.two_layout);
        three_layout = findViewById(R.id.three_layout);
        four_layout = findViewById(R.id.four_layout);
        five_layout = findViewById(R.id.five_layout);
        six_layout = findViewById(R.id.six_layout);
        seven_layout = findViewById(R.id.seven_layout);
        eight_layout = findViewById(R.id.eight_layout);
        nine_layout = findViewById(R.id.nine_layout);
        ten_layout = findViewById(R.id.ten_layout);
        eleven_layout = findViewById(R.id.eleven_layout);
        twelve_layout = findViewById(R.id.twelve_layout);
        thirteen_layout = findViewById(R.id.thirteen_layout);
        fourteen_layout = findViewById(R.id.fourteen_layout);
        eight_to_six_framelayout = findViewById(R.id.eight_to_six_framelayout);
        twelve_to_four_fiftynine_layout = findViewById(R.id.twelve_to_four_fiftynine);
        five_to_ten_layout = findViewById(R.id.five_to_ten_framelayout);

        one_nights_button_view = findViewById(R.id.one_nights_button_view);
        two_nights_button_view = findViewById(R.id.two_nights_button_view);
        one_button_view = findViewById(R.id.one_button_view);
        two_button_view = findViewById(R.id.two_button_view);
        three_button_view = findViewById(R.id.three_button_view);
        four_button_view = findViewById(R.id.four_button_view);
        five_button_view = findViewById(R.id.five_button_view);
        six_button_view = findViewById(R.id.six_button_view);
        seven_button_view = findViewById(R.id.seven_button_view);
        eight_button_view = findViewById(R.id.eight_button_view);
        nine_button_view = findViewById(R.id.nine_button_view);
        ten_button_view = findViewById(R.id.ten_button_view);
        eleven_button_view = findViewById(R.id.eleven_button_view);
        twelve_button_view= findViewById(R.id.twelve_button_view);
        thirteen_button_view = findViewById(R.id.thirteen_button_view);
        fourteen_button_view = findViewById(R.id.fourteen_button_view);
        five_to_ten_button_view = findViewById(R.id.five_to_ten_button_view);
        eight_to_eleven_fiftynine_button_view = findViewById(R.id.eight_to_eleven_fiftynine_button_view);
        twelve_to_four_fiftynine_button_view = findViewById(R.id.twelve_to_four_fiftynine_button_view);

        fab = findViewById(R.id.fab);

        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        // connect java variables to xml layout views

        SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        // retrieve base64 String format background image from sharedPreferences

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        // convert base64 String format String object to Bitmap object

        backgroundImage.setImageBitmap(image);

        //set background image of city apartment is located in

        city_or_village = getIntent().getStringExtra("cityVillage");

        street = getIntent().getStringExtra("street");

        house_number = getIntent().getStringExtra("house_number");

        firstNameHost = getIntent().getStringExtra("firstNameHost");

        lastNameHost = getIntent().getStringExtra("lastNameHost");

        int isTwoPeopleAllowed = getIntent().getIntExtra("isTwoPeopleAllowed", 1);

        // load data passed in from previous activity

        Firebase.setAndroidContext(this);

        // enable fireBase in this activity
        allNumNightsLayouts = new ArrayList<>();

        revealViews = new ArrayList<>();

        revealViews.add(one_nights_button_view);
        revealViews.add(two_nights_button_view);
        revealViews.add(three_button_view);
        revealViews.add(four_button_view);
        revealViews.add(five_button_view);
        revealViews.add(six_button_view);
        revealViews.add(seven_button_view);
        revealViews.add(eight_button_view);
        revealViews.add(nine_button_view);
        revealViews.add(ten_button_view);
        revealViews.add(eleven_button_view);
        revealViews.add(twelve_button_view);
        revealViews.add(thirteen_button_view);
        revealViews.add(fourteen_button_view);

        calendarObjects = new ArrayList<>();

        calendarObjects.add(calendar1);
        calendarObjects.add(calendar2);
        calendarObjects.add(calendar3);
        calendarObjects.add(calendar4);
        calendarObjects.add(calendar5);
        calendarObjects.add(calendar6);
        calendarObjects.add(calendar7);
        calendarObjects.add(calendar8);
        calendarObjects.add(calendar9);
        calendarObjects.add(calendar10);
        calendarObjects.add(calendar11);
        calendarObjects.add(calendar12);
        calendarObjects.add(calendar13);
        calendarObjects.add(calendar14);

        allTimeArrivalLayouts = new ArrayList<>();

        allTimeArrivalLayouts.add(eight_to_six_framelayout);
        allTimeArrivalLayouts.add(twelve_to_four_fiftynine_layout);
        allTimeArrivalLayouts.add(five_to_ten_layout);

        allNumNightsRevealViews = new ArrayList<>();

        allNumNightsRevealViews.add(one_nights_button_view);
        allNumNightsRevealViews.add(two_nights_button_view);
        allNumNightsRevealViews.add(three_button_view);
        allNumNightsRevealViews.add(four_button_view);
        allNumNightsRevealViews.add(five_button_view);
        allNumNightsRevealViews.add(six_button_view);
        allNumNightsRevealViews.add(seven_button_view);
        allNumNightsRevealViews.add(eight_button_view);
        allNumNightsRevealViews.add(nine_button_view);
        allNumNightsRevealViews.add(ten_button_view);
        allNumNightsRevealViews.add(eleven_button_view);
        allNumNightsRevealViews.add(twelve_button_view);
        allNumNightsRevealViews.add(thirteen_button_view);
        allNumNightsRevealViews.add(fourteen_button_view);

        allNumNightsLayouts.add(one_layout_nights);
        allNumNightsLayouts.add(two_layout_nights);
        allNumNightsLayouts.add(three_layout);
        allNumNightsLayouts.add(four_layout);
        allNumNightsLayouts.add(five_layout);
        allNumNightsLayouts.add(six_layout);
        allNumNightsLayouts.add(seven_layout);
        allNumNightsLayouts.add(eight_layout);
        allNumNightsLayouts.add(nine_layout);
        allNumNightsLayouts.add(ten_layout);
        allNumNightsLayouts.add(eleven_layout);
        allNumNightsLayouts.add(twelve_layout);
        allNumNightsLayouts.add(thirteen_layout);
        allNumNightsLayouts.add(fourteen_layout);

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID + "/subleasedNightsLeft");

        // DB path to amount of subleased nights left for apartment selected by user

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID + "/bookings");

        // DB path to all bookings of apartment selected by user

        final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID + "/price_per_night");

        // create fireBase links

        if (isTwoPeopleAllowed == 1){

            two_layout.setVisibility(View.GONE);

            // remove the possibility to select two people per stay if only one is allowed

        }

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                price_per_night = Integer.valueOf(dataSnapshot.getValue(String.class));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        // retrieve price per night

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                try {

                    BookingActivity2Methods.retrieveAllBookings(
                            getApplicationContext(), calendarObjects,
                            dataSnapshot, allNumNightsLayouts, yearInt, monthInt, dayInt, numDaysStay);

                } catch (Exception e) {

                    // Do nothing

                }

                // above try/catch block prevents above code from running when fab is clicked in BookingActivity3

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                numDaysLeftString = String.valueOf(dataSnapshot.getValue(Integer.class));

                if (!numDaysLeftString.equals("123456789") && bookedDatesList.size() != 0){

                    // if limited number of sublettable night per year rule does not apply to selected apartment, do the following

                    fab.hide();

                    // hide fab

                    numDaysLeftInteger = dataSnapshot.getValue(Integer.class);

                    // get amount of days left until subleased nights yearly limit is full

                    if (numDaysLeftInteger == 13){

                        fourteen_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 12){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 11){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 10){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 9){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 8){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 7){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 6){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 5){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);
                        six_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 4){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);
                        six_layout.setVisibility(View.GONE);
                        five_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 3){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);
                        six_layout.setVisibility(View.GONE);
                        five_layout.setVisibility(View.GONE);
                        four_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 2){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);
                        six_layout.setVisibility(View.GONE);
                        five_layout.setVisibility(View.GONE);
                        four_layout.setVisibility(View.GONE);
                        three_layout.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger == 1){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);
                        six_layout.setVisibility(View.GONE);
                        five_layout.setVisibility(View.GONE);
                        four_layout.setVisibility(View.GONE);
                        three_layout.setVisibility(View.GONE);
                        two_layout_nights.setVisibility(View.GONE);

                    } else if (numDaysLeftInteger <= 0){

                        fourteen_layout.setVisibility(View.GONE);
                        thirteen_layout.setVisibility(View.GONE);
                        twelve_layout.setVisibility(View.GONE);
                        eleven_layout.setVisibility(View.GONE);
                        ten_layout.setVisibility(View.GONE);
                        nine_layout.setVisibility(View.GONE);
                        eight_layout.setVisibility(View.GONE);
                        seven_layout.setVisibility(View.GONE);
                        six_layout.setVisibility(View.GONE);
                        five_layout.setVisibility(View.GONE);
                        four_layout.setVisibility(View.GONE);
                        three_layout.setVisibility(View.GONE);
                        two_layout_nights.setVisibility(View.GONE);
                        one_layout_nights.setVisibility(View.GONE);

                        fab.setClickable(false);

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.apartement_can_not_be_booked), Toast.LENGTH_LONG).show();

                    }

                    // change amount of booking nights available according to subleased nights left

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        relativeLayout = findViewById(R.id.relativeLayout);

        Typeface tp = Typeface.createFromAsset(getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        amount_of_people_textView.setTypeface(tp);
        amount_of_nights_textView.setTypeface(tp);
        one_textView.setTypeface(tp);
        two_textView.setTypeface(tp);
        one_textView_nights.setTypeface(tp);
        two_textView_nights.setTypeface(tp);
        three_textView.setTypeface(tp);
        four_textView.setTypeface(tp);
        five_textView.setTypeface(tp);
        six_textView.setTypeface(tp);
        seven_textView.setTypeface(tp);
        eight_textView.setTypeface(tp);
        nine_textView.setTypeface(tp);
        ten_textView.setTypeface(tp);
        eleven_textView.setTypeface(tp);
        twelve_textView.setTypeface(tp);
        thirteen_textView.setTypeface(tp);
        fourteen_textView.setTypeface(tp);
        eight_to_eleven_fiftynine_textView.setTypeface(tp);
        twelve_to_four_fiftynine_textView.setTypeface(tp);
        five_to_ten_textView.setTypeface(tp);

        // initialise and set typeface to all textView

        relativeLayout_org_pos_x = relativeLayout.getX();

        BookingActivity2Methods.animateActivityTransition(getApplicationContext(), relativeLayout, relativeLayout_org_pos_x);

        Bundle extras = getIntent().getExtras();
        arrival_date = extras.getString("date_of_arrival");

        yearInt = extras.getInt("arrival_year");
        monthInt = extras.getInt("arrival_month");
        dayInt = extras.getInt("arrival_day");

        // get date selected from previous activity

        fab = findViewById(R.id.fab);

        fab.hide();

        // hide fab

        /*

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (StartupMethods.isOnline(getApplicationContext())) {

                    // if device has an active internet connection, do the following

                    SharedPreferences.Editor editor = getSharedPreferences("bookingInfo", MODE_PRIVATE).edit();

                    if (one_layout.isSelected()){

                        editor.putInt("num_people", 1);

                        // if user selected to book make the booking for one person, save this info in sharedPreferences

                    } else if (two_layout.isSelected()){

                        editor.putInt("num_people", 2);

                        // if user selected to make the booking for two people, save this info in sharedPreferences

                    }

                    if (eight_to_six_framelayout.isSelected()) {

                        editor.putInt("time_of_arrival", 1);

                        // if user selected to arrive between 8am and 11:59, save this info in sharedPreferences

                    } else if (twelve_to_four_fiftynine_layout.isSelected()) {

                        editor.putInt("time_of_arrival", 2);

                        // if user selected to arrive between 12 and 4:59PM, save this info in sharedPreferences

                    } else if (five_to_ten_layout.isSelected()) {

                        editor.putInt("time_of_arrival", 3);

                        // if user selected to arrive between 5PM and 10PM, save this info in sharedPreferences

                    }

                    if (one_layout_nights.isSelected()){

                        editor.putInt("num_nights", 1);

                        // if user selected to book the apartment for one night, save this info in sharedPreferences

                    } else if (two_layout_nights.isSelected()){

                        editor.putInt("num_nights", 2);

                        // if user selected to book the apartment for two nights, save this info in sharedPreferences

                    }else if (three_layout.isSelected()){

                        editor.putInt("num_nights", 3);

                        // if user selected to book the apartment for three nights, save this info in sharedPreferences

                    }else if (four_layout.isSelected()){

                        editor.putInt("num_nights", 4);

                        // if user selected to book the apartment for four nights, save this info in sharedPreferences

                    }else if (five_layout.isSelected()){

                        editor.putInt("num_nights", 5);

                        // if user selected to book the apartment for five nights, save this info in sharedPreferences

                    }else if (six_layout.isSelected()){

                        editor.putInt("num_nights", 6);

                        // if user selected to book the apartment for six nights, save this info in sharedPreferences

                    }else if (seven_layout.isSelected()){

                        editor.putInt("num_nights", 7);

                        // if user selected to book the apartment for seven nights, save this info in sharedPreferences

                    }else if (eight_layout.isSelected()){

                        editor.putInt("num_nights", 8);

                        // if user selected to book the apartment for eight nights, save this info in sharedPreferences

                    }else if (nine_layout.isSelected()){

                        editor.putInt("num_nights", 9);

                        // if user selected to book the apartment for nine nights, save this info in sharedPreferences

                    }else if (ten_layout.isSelected()){

                        editor.putInt("num_nights", 10);

                        // if user selected to book the apartment for ten nights, save this info in sharedPreferences

                    }else if (eleven_layout.isSelected()){

                        editor.putInt("num_nights", 11);

                        // if user selected to book the apartment for eleven nights, save this info in sharedPreferences

                    }else if (twelve_layout.isSelected()){

                        editor.putInt("num_nights", 12);

                        // if user selected to book the apartment for twelve nights, save this info in sharedPreferences

                    }else if (thirteen_layout.isSelected()){

                        editor.putInt("num_nights", 13);

                        // if user selected to book the apartment for thirteen nights, save this info in sharedPreferences

                    }else if (fourteen_layout.isSelected()){

                        editor.putInt("num_nights", 14);

                        // if user selected to book the apartment for fourteen nights, save this info in sharedPreferences

                    }

                    // set extras according to number of people and number of nights selected

                    editor.apply();

                    // save booking data temporarily

                    // CONVERT NUM_NIGHTS VARIABLE TO ARRAY CONTAINING ONE VALUE!!!! (BOOKING METADATA IS NOT BEING PASSED ON PROPERLY)

                    BookingActivity2Methods.animateActivityExitTransition(
                            getApplicationContext(), relativeLayout, relativeLayout_org_pos_x, anim,
                            fab, yearInt, monthInt, dayInt,
                            price_per_night, num_nights, AID, city_or_village,
                            street, house_number, firstNameHost, lastNameHost);

                    // start BookingActivity3

                } else {

                    StartupMethods.openNewInternetConnectionLostDialog(BookingActivity2.this);

                }

            }
        });

        */

        /*

        BookingActivity2Methods.initialiseFloatingActionButton(
                getApplicationContext(), fab,this, relativeLayout,
                relativeLayout_org_pos_x, anim, yearInt, monthInt, dayInt, price_per_night, num_nights, AID,
                firstNameHost, lastNameHost);

        BookingActivity2Methods.setOnClickListenerNumNightsButtons(
                getApplicationContext(), allNumNightsLayouts, num_nights, fab,
                one_layout, two_layout, eight_to_six_framelayout, twelve_to_four_fiftynine_layout,
                five_to_ten_layout, allNumNightsRevealViews);

        eight_to_six_framelayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                eight_to_six_framelayout.setSelected(true);
                twelve_to_four_fiftynine_layout.setSelected(false);
                five_to_ten_layout.setSelected(false);

                // deselect all other time of arrival views

                if ((one_layout.isSelected() || two_layout.isSelected()) &&
                        (one_layout_nights.isSelected() ||
                        two_layout_nights.isSelected() ||
                        three_layout.isSelected() ||
                        four_layout.isSelected() ||
                        five_layout.isSelected() ||
                        six_layout.isSelected() ||
                        seven_layout.isSelected() ||
                        eight_layout.isSelected() ||
                        nine_layout.isSelected() ||
                        ten_layout.isSelected() ||
                        eleven_layout.isSelected() ||
                        twelve_layout.isSelected() ||
                        thirteen_layout.isSelected() ||
                        fourteen_layout.isSelected()
                        )){

                    /*

                     * if amount of people for booking and number of people
                     * who will be staying in the apartment during the booking have also been selected
                     * along with currently selected arrival date, do the following

                     */

        /*

                    fab.show();

                    // make fab visible

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    //if build version is equal to or higher than Lollipop, use circular reveal animation.
                    // if build version is lower than Lollipop, use background colour change method

                    int x = eight_to_six_framelayout.getWidth();
                    int y = eight_to_six_framelayout.getHeight();

                    float endRadius = (float) Math.hypot(x, y);

                    circularReveal = ViewAnimationUtils.createCircularReveal(eight_to_eleven_fiftynine_button_view, (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

                    twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.introduction_button_clicked));

                    twelve_to_four_fiftynine_button_view.setVisibility(View.INVISIBLE);
                    five_to_ten_button_view.setVisibility(View.INVISIBLE);
                    eight_to_eleven_fiftynine_button_view.setVisibility(View.VISIBLE);

                    circularReveal.start();

                } else {

                    twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.introduction_button_clicked));

                }

                return true;
            }
        });

        twelve_to_four_fiftynine_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                twelve_to_four_fiftynine_layout.setSelected(true);
                eight_to_six_framelayout.setSelected(false);
                five_to_ten_layout.setSelected(false);

                // deselect all other time of arrival views

                if ((one_layout.isSelected() || (two_layout.isSelected())) &&
                        (one_layout_nights.isSelected() ||
                        two_layout_nights.isSelected() ||
                        three_layout.isSelected() ||
                        four_layout.isSelected() ||
                        five_layout.isSelected() ||
                        six_layout.isSelected() ||
                        seven_layout.isSelected() ||
                        eight_layout.isSelected() ||
                        nine_layout.isSelected() ||
                        ten_layout.isSelected() ||
                        eleven_layout.isSelected() ||
                        twelve_layout.isSelected() ||
                        thirteen_layout.isSelected() ||
                        fourteen_layout.isSelected()))
                {

                    /*

                     * if amount of people for booking and number of people
                     * who will be staying in the apartment during the booking have also been selected
                     * along with currently selected arrival date, do the following

                     */

        /*

                    fab.show();

                    // make fab visible

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    //if build version is equal to or higher than Lollipop, use circular reveal animation.
                    // if build version is lower than Lollipop, use background colour change method

                    int x = twelve_to_four_fiftynine_layout.getWidth();
                    int y = twelve_to_four_fiftynine_layout.getHeight();

                    float endRadius = (float) Math.hypot(x, y);

                    circularReveal = ViewAnimationUtils.createCircularReveal(twelve_to_four_fiftynine_button_view, (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

                    twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.introduction_button_clicked));
                    five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));

                    twelve_to_four_fiftynine_button_view.setVisibility(View.VISIBLE);
                    five_to_ten_button_view.setVisibility(View.INVISIBLE);
                    eight_to_eleven_fiftynine_button_view.setVisibility(View.INVISIBLE);

                    circularReveal.start();

                } else {

                    twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.introduction_button_clicked));
                    five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));

                }

                return true;
            }
        });

        five_to_ten_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                eight_to_six_framelayout.setSelected(false);
                twelve_to_four_fiftynine_layout.setSelected(false);
                five_to_ten_layout.setSelected(true);

                // deselect all other time of arrival views

                if ((one_layout.isSelected() || two_layout.isSelected()) &&
                        (one_layout_nights.isSelected() ||
                        two_layout_nights.isSelected() ||
                        three_layout.isSelected() ||
                        four_layout.isSelected() ||
                        five_layout.isSelected() ||
                        six_layout.isSelected() ||
                        seven_layout.isSelected() ||
                        eight_layout.isSelected() ||
                        nine_layout.isSelected() ||
                        ten_layout.isSelected() ||
                        eleven_layout.isSelected() ||
                        twelve_layout.isSelected() ||
                        thirteen_layout.isSelected() ||
                        fourteen_layout.isSelected()
                        )){

                    /*

                     * if amount of people for booking and number of people
                     * who will be staying in the apartment during the booking have also been selected
                     * along with currently selected arrival date, do the following

                     */

        /*


                    fab.show();

                    // make fab visible

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    //if build version is equal to or higher than Lollipop, use circular reveal animation.
                    // if build version is lower than Lollipop, use background colour change method

                    int x = five_to_ten_layout.getWidth();
                    int y = five_to_ten_layout.getHeight();

                    float endRadius = (float) Math.hypot(x, y);

                    circularReveal = ViewAnimationUtils.createCircularReveal(five_to_ten_button_view, (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

                    twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.introduction_button_clicked));
                    eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));

                    twelve_to_four_fiftynine_button_view.setVisibility(View.INVISIBLE);
                    five_to_ten_button_view.setVisibility(View.VISIBLE);
                    eight_to_eleven_fiftynine_button_view.setVisibility(View.INVISIBLE);


                    circularReveal.start();

                } else {

                    twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));
                    five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.introduction_button_clicked));
                    eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(BookingActivity2.this, R.color.main_background));

                }

                return true;
            }
        });

        */

            }

    @Override
    protected void onRestart() {
        super.onRestart();

        view.animateActivityEnterAndReEnterTransition(getApplicationContext(), this);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);
    }

}
