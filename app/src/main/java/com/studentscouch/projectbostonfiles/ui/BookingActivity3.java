package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.BookingActivity3Methods;
import com.studentscouch.projectbostonfiles.DeepCode;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity3 extends AppCompatActivity {

    private FloatingActionButton fab;

    private RelativeLayout relativeLayout;

    private ObjectAnimator anim;

    private float relativeLayout_org_pos_x;

    private DatabaseReference
            fbRef,
            fbRef2,
            fbRef3,
            fbRef4,
            fbRef5;

    private String AID;

    private int
        yearStartInt,
        monthStartInt,
        dayStartInt,
        yearEndInt,
        monthEndInt,
        dayEndInt;

    public static int num_nights;

    private int time_of_arrival;

    String hostUID;

    private String
            firstNameGuest,
            lastNameGuest,
            firstNameHost,
            lastNameHost,
            locationName;

    public static double
            total_price,
            moneyOwedFromGuest,
            moneyOwedFromHost,
            SCearnings;

    public static String
            city_or_village,
            street,
            house_number;

    List<String> monthNames;

    public static int num_people;

    public static int price_per_night;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking3);

        // retrieve UID of host and locationID of apartment

        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        TextView price_textView = findViewById(R.id.price_textView);

        TextView your_booking_textView = findViewById(R.id.your_booking_textView);
        TextView date_of_arrival_textView = findViewById(R.id.amount_of_nights_textView2);
        TextView date_of_arrival_date_textView = findViewById(R.id.one_textView_nights);
        TextView amount_of_people_textView = findViewById(R.id.amount_of_people_textView);
        TextView amount_of_people_layout_textView = findViewById(R.id.amount_of_people_layout_textView);
        TextView amount_of_nights_textView = findViewById(R.id.amount_of_nights_textView);
        TextView amount_of_nights_layout_textView = findViewById(R.id.num_nights_textView);

        fab = findViewById(R.id.fab);

        relativeLayout = findViewById(R.id.relativeLayout);

        // connect Java variables with xml views

        setTypefaces(
                price_textView, your_booking_textView, date_of_arrival_textView,
                date_of_arrival_date_textView, amount_of_people_textView, amount_of_people_layout_textView,
                amount_of_nights_textView, amount_of_nights_layout_textView);

        getDataFromPreviousActivity();

        setBackground(backgroundImage);

        initMonthNamesArray();

        prepareDatabaseFunc();

        setFabOnClickListenerWhenUserDataLoaded();

        // retrieve first and last name of guest

        retrieveHostUIDandLocationID();

        // get number of nights selected from BookingActivity2

        calculateEndDateBooking();

        BookingActivity3Methods.calculatePrice();

        // calculate total price fro booking, divide StudentsCouch and host earnings

        String monthName = monthNames.get(monthStartInt - 1);

        // assign month name according to monthStartInt retrieved from previous activity

        date_of_arrival_date_textView.setText(dayStartInt + " " + monthName + " " + yearStartInt);

        // insert arrival date into date of arrival textView

        getTimeOfArrival();

        amount_of_nights_layout_textView.setText(String.valueOf(num_nights));

        //insert amount of nights the user has chosen to stay for the booking into amount og nights textView

        String priceRoundedTwoDecimals = DeepCode.toMoneySum(total_price);

        // above line ensures that a correct price is shown to the user, I.E. A total price of 88.888 euros becomes 88.89

        price_textView.setText("$" + priceRoundedTwoDecimals);

        //insert total price of booking for guest according to

        initNumPeopleField(amount_of_people_layout_textView);

        // set amount of people for booking according to option selected in first BookingActivty

        relativeLayout_org_pos_x = relativeLayout.getX();

        // save current position of relativeLayout to local variable

        BookingActivity3Methods.animateActivityTransition(
                getApplicationContext(), relativeLayout, relativeLayout_org_pos_x);

        // animate activity enter transition



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, 0);
    }

    private void initNumPeopleField(TextView amount_of_people_layout_textView){

        if (num_people == 2){

            amount_of_people_layout_textView.setText("2");

            // insert amount of people user has chosen to stay during the booking

        } else {

            amount_of_people_layout_textView.setText("1");

            // insert amount of people user has chosen to stay during the booking

        }

    }

    private void getTimeOfArrival(){

        SharedPreferences prefs2 = getSharedPreferences("bookingInfo", Context.MODE_PRIVATE);

        // load bookingInfo from sharedPreferences

        time_of_arrival = prefs2.getInt("time_of_arrival", 0);

        //retrieve number of nights and time of arrival chosen by user for this date from sharedPreferences

    }

    private void calculateEndDateBooking(){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, yearStartInt);
        calendar.set(Calendar.MONTH, monthStartInt - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayStartInt);

        calendar.add(Calendar.DAY_OF_MONTH, num_nights);

        // make a new calendar object and increase the number of days to the overall date according to the num_nights Integer

        yearEndInt = calendar.get(Calendar.YEAR);
        monthEndInt = calendar.get(Calendar.MONTH);
        dayEndInt = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void retrieveHostUIDandLocationID(){

        fbRef4.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                hostUID = dataSnapshot.child(ConstantsDB.UID_TABLE).getValue(String.class);
                locationName = dataSnapshot.child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).getValue(String.class);

                // retrieve host User ID and locationID of apartment for selected booking

                /*

                 * locationID is the alias for the Google Maps term 'Place ID'
                 * this a unique ID given by Google Maps for every single location in the program.

                 * this data is used in StudentsCouch to read in what city/town the apartment is located
                 * and change the apps' mechanics according to this data


                 * Example

                 * locationID Amsterdam: ChIJVXealLU_xkcRja_At0z9AGY
                 * locationID Antwerp: ChIJfYjDv472w0cRuIqogoRErz4

                 * See link below for more info
                 * https://developers.google.com/maps/documentation/javascript/examples/places-placeid-finder

                 */



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve UID of host and locationID of apartment

    }

    private void setFabOnClickListenerWhenUserDataLoaded(){

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                firstNameGuest = dataSnapshot.child(ConstantsDB.USER_FIRST_NAME_TABLE).getValue(String.class);
                lastNameGuest = dataSnapshot.child(ConstantsDB.USER_LAST_NAME_TABLE).getValue(String.class);

                //Toast.makeText(getApplicationContext(), firstNameGuest + " " + lastNameGuest, Toast.LENGTH_SHORT).show();

                // retrieve first and last name of guest for selected booking

                BookingActivity3Methods.initialiseFloatingActionButton(
                        getApplicationContext(), fab, time_of_arrival, fbRef,
                        fbRef2, firstNameGuest, lastNameGuest,
                        firstNameHost, lastNameHost, yearStartInt, monthStartInt,
                        dayStartInt, yearEndInt, monthEndInt, dayEndInt,
                        total_price, moneyOwedFromGuest, moneyOwedFromHost, SCearnings,
                        num_nights, AID, locationName, house_number,
                        street, city_or_village, fbRef5, BookingActivity3.this,
                        relativeLayout, relativeLayout_org_pos_x, anim);

                // initialise floatingActionButton

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void prepareDatabaseFunc(){

        Firebase.setAndroidContext(this);

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        // DB link to all bookings of selected apartment

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        // DB link to all bookings of user currently logged in on device

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + FirebaseAuth.getInstance().getCurrentUser().getUid());

        // DB link to user currently logged in on device

        fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + AID);

        // DB link to apartment selected by user

        fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.TRANSACTIONS_TABLE);

        // DB link to transactions table. Transactions table keeps track of all booking statuses

    }

    private void initMonthNamesArray(){

        monthNames = new ArrayList<>();

        monthNames.add(getResources().getString(R.string.january));
        monthNames.add(getResources().getString(R.string.february));
        monthNames.add(getResources().getString(R.string.march));
        monthNames.add(getResources().getString(R.string.april));
        monthNames.add(getResources().getString(R.string.may));
        monthNames.add(getResources().getString(R.string.june));
        monthNames.add(getResources().getString(R.string.july));
        monthNames.add(getResources().getString(R.string.august));
        monthNames.add(getResources().getString(R.string.september));
        monthNames.add(getResources().getString(R.string.october));
        monthNames.add(getResources().getString(R.string.november));
        monthNames.add(getResources().getString(R.string.december));

    }

    private void getDataFromPreviousActivity(){

        firstNameHost = getIntent().getStringExtra("firstNameHost");
        lastNameHost = getIntent().getStringExtra("lastNameHost");
        locationName = getIntent().getStringExtra("locationName");
        city_or_village = getIntent().getStringExtra("cityVillage");
        street = getIntent().getStringExtra("street");
        house_number = getIntent().getStringExtra("house_number");

        yearStartInt = getIntent().getIntExtra("yearInt", 0);
        monthStartInt = getIntent().getIntExtra("monthInt", 5);
        dayStartInt = getIntent().getIntExtra("dayInt", 0);

        // get date selected from first BookingActivity

        price_per_night = getIntent().getIntExtra("price_per_night", 0);

        num_nights = getIntent().getIntExtra("num_nights", 4);

        num_people = getIntent().getIntExtra("num_people", 1);

        AID = getIntent().getStringExtra("AID");

    }

    private void setTypefaces(
            TextView price_textView, TextView your_booking_textView,
            TextView date_of_arrival_textView, TextView date_of_arrival_date_textView,
            TextView amount_of_people_textView, TextView amount_of_people_layout_textView,
            TextView amount_of_nights_textView, TextView amount_of_nights_layout_textView){

        Typeface tp = Typeface.createFromAsset(getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        price_textView.setTypeface(tp);
        your_booking_textView.setTypeface(tp);
        date_of_arrival_textView.setTypeface(tp);
        date_of_arrival_date_textView.setTypeface(tp);
        amount_of_people_textView.setTypeface(tp);
        amount_of_people_layout_textView.setTypeface(tp);
        amount_of_nights_textView.setTypeface(tp);
        amount_of_nights_layout_textView.setTypeface(tp);

        // initialise and set typeface to all textViews

    }

    private void setBackground(ImageView backgroundImage){

        SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        backgroundImage.setImageBitmap(image);

        // load and set background image of city the apartment is located in

    }

}
