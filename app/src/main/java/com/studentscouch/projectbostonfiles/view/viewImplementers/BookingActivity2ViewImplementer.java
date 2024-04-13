package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.dbImplementers.BookingActivitiesDBImplementer;
import com.studentscouch.projectbostonfiles.models.implementers.BookingActivity2Model;
import com.studentscouch.projectbostonfiles.observables.BookingActivity2Observables;
import com.studentscouch.projectbostonfiles.ui.BookingActivity3;
import com.studentscouch.projectbostonfiles.view.views.BookingActivity2View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class BookingActivity2ViewImplementer implements BookingActivity2View {

    private View rootView;

    private TextView
        amount_of_people_textView,
        amount_of_nights_textView,
        one_textView,
        two_textView,
        one_textView_nights,
        two_textView_nights,
        three_textView,
        four_textView,
        five_textView,
        six_textView,
        seven_textView,
        eight_textView,
        nine_textView,
        ten_textView,
        eleven_textView,
        twelve_textView,
        thirteen_textView,
        fourteen_textView;

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

    private FrameLayout
            one_layout,
            two_layout;

    private Animator circularReveal;

    private FloatingActionButton fab;

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

    private DatabaseReference fbRef2;

    private String numDaysLeftString;

    private int isTwoPeopleAllowed;

    private String
            city_or_village,
            house_number,
            street;

    private String
            firstNameHost,
            lastNameHost;

    private FrameLayout
            eight_to_six_framelayout,
            twelve_to_four_fiftynine_layout,
            five_to_ten_layout;

    private TextView
            eight_to_eleven_fiftynine_textView,
            twelve_to_four_fiftynine_textView,
            five_to_ten_textView;

    private List <View> allNumNightsLayouts;

    private List <View> revealViews;

    private List <GregorianCalendar> calendarObjects;

    private List <View> allTimeArrivalLayouts;

    private List <View> allNumNightsRevealViews;

    private ImageView backgroundImage;

    private Calendar calendar;
    private GregorianCalendar cal;

    private BookingActivitiesDBImplementer db;

    private BookingActivity2Observables observables;

    private ViewGroup mainViewGroup;

    private BookingActivity2Model model;

    public BookingActivity2ViewImplementer(Context context, ViewGroup viewGroup){

        mainViewGroup = viewGroup;

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_booking2, viewGroup);

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        linkXmlElementsToJavaVariables();

        setTypefaces(context);

    }

    private void setTypefaces(Context context){

        Typeface tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

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

    }

    private void linkXmlElementsToJavaVariables(){

        amount_of_people_textView = rootView.findViewById(R.id.your_booking_textView);
        amount_of_nights_textView = rootView.findViewById(R.id.amount_of_people_textView);
        one_textView = rootView.findViewById(R.id.one_textView);
        two_textView = rootView.findViewById(R.id.two_textView);
        one_textView_nights = rootView.findViewById(R.id.one_textView_nights);
        two_textView_nights = rootView.findViewById(R.id.two_textView_nights);
        three_textView = rootView.findViewById(R.id.three_textView);
        four_textView = rootView.findViewById(R.id.four_textView);
        five_textView = rootView.findViewById(R.id.five_textView);
        six_textView = rootView.findViewById(R.id.six_textView);
        seven_textView = rootView.findViewById(R.id.seven_textView);
        eight_textView = rootView.findViewById(R.id.eight_textView);
        nine_textView = rootView.findViewById(R.id.nine_textView);
        ten_textView = rootView.findViewById(R.id.ten_textView);
        eleven_textView = rootView.findViewById(R.id.eleven_textView);
        twelve_textView = rootView.findViewById(R.id.twelve_textView);
        thirteen_textView = rootView.findViewById(R.id.thirteen_textView);
        fourteen_textView = rootView.findViewById(R.id.fourteen_textView);
        eight_to_eleven_fiftynine_textView = rootView.findViewById(R.id.eight_to_eleven_fiftynine_textView);
        twelve_to_four_fiftynine_textView = rootView.findViewById(R.id.twelve_to_four_fiftynine_textView);
        five_to_ten_textView = rootView.findViewById(R.id.five_to_ten_textView);

        one_layout_nights = rootView.findViewById(R.id.one_layout_nights);
        two_layout_nights = rootView.findViewById(R.id.two_layout_nights);
        one_layout = rootView.findViewById(R.id.one_layout);
        two_layout = rootView.findViewById(R.id.two_layout);
        three_layout = rootView.findViewById(R.id.three_layout);
        four_layout = rootView.findViewById(R.id.four_layout);
        five_layout = rootView.findViewById(R.id.five_layout);
        six_layout = rootView.findViewById(R.id.six_layout);
        seven_layout = rootView.findViewById(R.id.seven_layout);
        eight_layout = rootView.findViewById(R.id.eight_layout);
        nine_layout = rootView.findViewById(R.id.nine_layout);
        ten_layout = rootView.findViewById(R.id.ten_layout);
        eleven_layout = rootView.findViewById(R.id.eleven_layout);
        twelve_layout = rootView.findViewById(R.id.twelve_layout);
        thirteen_layout = rootView.findViewById(R.id.thirteen_layout);
        fourteen_layout = rootView.findViewById(R.id.fourteen_layout);
        eight_to_six_framelayout = rootView.findViewById(R.id.eight_to_six_framelayout);
        twelve_to_four_fiftynine_layout = rootView.findViewById(R.id.twelve_to_four_fiftynine);
        five_to_ten_layout = rootView.findViewById(R.id.five_to_ten_framelayout);

        one_nights_button_view = rootView.findViewById(R.id.one_nights_button_view);
        two_nights_button_view = rootView.findViewById(R.id.two_nights_button_view);
        one_button_view = rootView.findViewById(R.id.one_button_view);
        two_button_view = rootView.findViewById(R.id.two_button_view);
        three_button_view = rootView.findViewById(R.id.three_button_view);
        four_button_view = rootView.findViewById(R.id.four_button_view);
        five_button_view = rootView.findViewById(R.id.five_button_view);
        six_button_view = rootView.findViewById(R.id.six_button_view);
        seven_button_view = rootView.findViewById(R.id.seven_button_view);
        eight_button_view = rootView.findViewById(R.id.eight_button_view);
        nine_button_view = rootView.findViewById(R.id.nine_button_view);
        ten_button_view = rootView.findViewById(R.id.ten_button_view);
        eleven_button_view = rootView.findViewById(R.id.eleven_button_view);
        twelve_button_view= rootView.findViewById(R.id.twelve_button_view);
        thirteen_button_view = rootView.findViewById(R.id.thirteen_button_view);
        fourteen_button_view = rootView.findViewById(R.id.fourteen_button_view);
        five_to_ten_button_view = rootView.findViewById(R.id.five_to_ten_button_view);
        eight_to_eleven_fiftynine_button_view = rootView.findViewById(R.id.eight_to_eleven_fiftynine_button_view);
        twelve_to_four_fiftynine_button_view = rootView.findViewById(R.id.twelve_to_four_fiftynine_button_view);

        fab = rootView.findViewById(R.id.fab);

        backgroundImage = rootView.findViewById(R.id.backgroundImage);

        relativeLayout = rootView.findViewById(R.id.relativeLayout);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        initActivity();

        initArrayLists();

        getRelativeLayoutOrigPosXaxis();

        retrieveDataFromPreviousActivity(appCompatActivity);

        model.setBackgroundImage(appCompatActivity, backgroundImage);

        initNumPeopleOptions();

        fab.hide();

        animateActivityEnterAndReEnterTransition(context, appCompatActivity);

        // get date selected from previous activity

        fab.setOnClickListener(view -> fabClicked(appCompatActivity, context));

        one_layout.setOnTouchListener((view, motionEvent) -> {

            onePersonButtonSelected(context, motionEvent);

            return true;
        });

        two_layout.setOnTouchListener((view, motionEvent) -> {

            twoPeopleButtonSelected(context, motionEvent);

            return true;
        });

       setOnClickListenerNumNightsButtons(context, allNumNightsLayouts);

        eight_to_six_framelayout.setOnTouchListener((view, motionEvent) -> {

            eight_to_six_layout_clicked(context, motionEvent);

            return true;
        });

        twelve_to_four_fiftynine_layout.setOnTouchListener((view, motionEvent) -> {

            twelve_to_four_fiftynine_layout_clicked(context, motionEvent);

            return true;
        });

        five_to_ten_layout.setOnTouchListener((view, motionEvent) -> {

            five_to_ten_layout_clicked(context, motionEvent);

            return true;
        });

        retrieveAllBookings(context, appCompatActivity);

        db.initDBFuncBookingActivity2(
                context, calendarObjects, allNumNightsLayouts,
                fab, appCompatActivity, appCompatActivity, mainViewGroup);

    }

    private void initActivity(){

        observables = new BookingActivity2Observables();

        model = new BookingActivity2Model(observables);

        db = new BookingActivitiesDBImplementer(
                null,this, null, observables, null, model);

    }

    private void getRelativeLayoutOrigPosXaxis(){

        relativeLayout_org_pos_x = relativeLayout.getX();

    }

    private void retrieveDataFromPreviousActivity(AppCompatActivity appCompatActivity){

        observables.setAID(appCompatActivity.getIntent().getStringExtra("AID"));

        city_or_village = appCompatActivity.getIntent().getStringExtra("cityVillage");

        street = appCompatActivity.getIntent().getStringExtra("street");

        house_number = appCompatActivity.getIntent().getStringExtra("house_number");

        firstNameHost = appCompatActivity.getIntent().getStringExtra("firstNameHost");

        lastNameHost = appCompatActivity.getIntent().getStringExtra("lastNameHost");

        isTwoPeopleAllowed = appCompatActivity.getIntent().getIntExtra("isTwoPeopleAllowed", 1);

        // retrieve apartment info from previous activity

        retrieveBookingDataFromPreviousActivity(appCompatActivity);

    }

    private void retrieveBookingDataFromPreviousActivity(AppCompatActivity appCompatActivity){

        Bundle extras = appCompatActivity.getIntent().getExtras();

        if (extras != null) {

            observables.setYearInt(extras.getInt("arrival_year"));
            observables.setMonthInt(extras.getInt("arrival_month"));
            observables.setDayInt(extras.getInt("arrival_day"));

        }

        if (extras == null) {

            observables.setYearInt(0);
            observables.setMonthInt(0);
            observables.setDayInt(0);

        }

        // retrieve booking info from previous activity

    }

    private void initArrayLists(){

        initRevealViewsArray();

        initCalendarObjectsArray();

        initAllTimeArrivalLayouts();

        initAllNumNightsRevealViewsArray();

        initAllNumNightsLayoutsArrays();

    }

    @Override
    public void retrieveAllBookings(Context context, Activity activity) {

        db.retrieveAllBookingDates(calendarObjects, allNumNightsLayouts);

        createTextViewForEveryOccupiedDate(context);

    }

    @Override
    public void retrieveSubleasedNights(Context context, DataSnapshot dataSnapshot) {

        numDaysLeftString = String.valueOf(dataSnapshot.getValue(Integer.class));

        initNumNightsButtonsVisibilityAccordingly(context, dataSnapshot);

    }

    @Override
    public void startNextActivity(Context context) {

        Intent i = new Intent(context, BookingActivity3.class);

        passMetaData(i);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        //Toast.makeText(context, num_nights + "", Toast.LENGTH_SHORT).show();

        context.startActivity(i);

    }

    @Override
    public void initButtonVisibilityAccToNumNightsLeft(Context context) {

        if (observables.getNumDaysLeftInteger() == 13){

            fourteen_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 12){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 11){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 10){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);
            eleven_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 9){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);
            eleven_layout.setVisibility(View.GONE);
            ten_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 8){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);
            eleven_layout.setVisibility(View.GONE);
            ten_layout.setVisibility(View.GONE);
            nine_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 7){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);
            eleven_layout.setVisibility(View.GONE);
            ten_layout.setVisibility(View.GONE);
            nine_layout.setVisibility(View.GONE);
            eight_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 6){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);
            eleven_layout.setVisibility(View.GONE);
            ten_layout.setVisibility(View.GONE);
            nine_layout.setVisibility(View.GONE);
            eight_layout.setVisibility(View.GONE);
            seven_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 5){

            fourteen_layout.setVisibility(View.GONE);
            thirteen_layout.setVisibility(View.GONE);
            twelve_layout.setVisibility(View.GONE);
            eleven_layout.setVisibility(View.GONE);
            ten_layout.setVisibility(View.GONE);
            nine_layout.setVisibility(View.GONE);
            eight_layout.setVisibility(View.GONE);
            seven_layout.setVisibility(View.GONE);
            six_layout.setVisibility(View.GONE);

        } else if (observables.getNumDaysLeftInteger() == 4){

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

        } else if (observables.getNumDaysLeftInteger() == 3){

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

        } else if (observables.getNumDaysLeftInteger() == 2){

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

        } else if (observables.getNumDaysLeftInteger() == 1){

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

        } else if (observables.getNumDaysLeftInteger() <= 0){

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

            Toast.makeText(context, context.getResources().getString(R.string.apartement_can_not_be_booked), Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void animateActivityEnterAndReEnterTransition(Context context, AppCompatActivity appCompatActivity) {

        StudentsCouchAnimations anims = new StudentsCouchAnimations(appCompatActivity);

        anims.animateActivityEnterAndReEnterTransitionBookingActivity2(context, relativeLayout, relativeLayout_org_pos_x);

    }

    private void initAllNumNightsLayoutsArrays(){

        allNumNightsLayouts = new ArrayList<>();

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

    }

    private void initAllTimeArrivalLayouts(){

        allTimeArrivalLayouts = new ArrayList<>();

        allTimeArrivalLayouts.add(eight_to_six_framelayout);
        allTimeArrivalLayouts.add(twelve_to_four_fiftynine_layout);
        allTimeArrivalLayouts.add(five_to_ten_layout);

    }

    private void initAllNumNightsRevealViewsArray(){

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

    }

    private void initCalendarObjectsArray(){

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

    }

    private void initRevealViewsArray(){

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

    }

    private void passMetaData(Intent i){

        i.putExtra("yearInt", observables.getYearInt());
        i.putExtra("monthInt", observables.getMonthInt());
        i.putExtra("dayInt", observables.getDayInt());

        i.putExtra("price_per_night", observables.getPrice_per_night());

        i.putExtra("num_people", observables.getNum_people());

        i.putExtra("AID", observables.getAID());

        i.putExtra("cityVillage", city_or_village);
        i.putExtra("street", street);
        i.putExtra("house_number", house_number);

        i.putExtra("firstNameHost", firstNameHost);
        i.putExtra("lastNameHost", lastNameHost);

        passNumNightsSelected(i);

    }

    private void passNumNightsSelected(Intent i){

        for (int pos = 0; pos < allNumNightsLayouts.size(); pos++) {

            if (allNumNightsLayouts.get(pos).isSelected()) {

                i.putExtra("num_nights", pos + 1);

            }

        }

    }

    @SuppressLint("SetTextI18n")
    private void createTextViewForEveryOccupiedDate(Context context){

        try {

            for (int i = 0; i < observables.getBookedDatesList().size(); i++) {

                TextView text = new TextView(context);
                text.setText(
                        observables.getBookedDatesList().get(i).get(0) +
                                "-" + observables.getBookedDatesList().get(i).get(1) + "-"
                                + observables.getBookedDatesList().get(i).get(2));

                // insert date selected by user into new textView (assigned according to Dutch date formatting)

            }

        } catch (Exception e) {

            //activity.onBackPressed();

        }

    }

    private void initNumNightsButtonsVisibilityAccordingly(Context context, DataSnapshot dataSnapshot){

        if (!numDaysLeftString.equals(MyApplication.NUM_NIGHTS_LEFT_STRING_N_A)){

            // if limited number of sublettable nights per year rule applies to selected apartment, do the following

            fab.hide();

            // hide fab

            observables.setNumDaysLeftInteger(dataSnapshot.getValue(Integer.class));

            // get amount of days left until subleased nights yearly limit is full

            initNightsButtonsAccToMetaData();

            makeAccRevealViewsInvisible();

            makeAccNightButtonsVisibleAndInvisible();

            setNightsButtonsVisibilityAccToDaysUntilNextBooking(context);

        }

    }

    private void initNightsButtonsAccToMetaData(){

        for (int pos = 0; pos < observables.getNumDaysLeftInteger(); pos++) {

            initCalendarObjects(pos);

            if (cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                    cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {

                // if selected date precedes next booking by one day, do the following

                makeAllNightsLayoutsVisible(pos);

                break;

            }


        }

    }

    private void makeAllNightsLayoutsVisible(int pos){

        allNumNightsLayouts.get(pos).setVisibility(View.VISIBLE);

        // make all views visible

    }

    private void initCalendarObjects(int pos){

        initArrivalDatePlusTwoDaysCalObject(pos);

        initClosestBookingDateCalObject(pos);

    }

    private void initClosestBookingDateCalObject(int pos){

        cal = (GregorianCalendar) GregorianCalendar.getInstance();

        // create new GregorianCalendar object

        cal.set(Calendar.YEAR, observables.getBookedDatesList().get(pos).get(2));
        cal.set(Calendar.MONTH, observables.getBookedDatesList().get(pos).get(1) - 1);
        cal.set(Calendar.DAY_OF_MONTH, observables.getBookedDatesList().get(pos).get(0));

        // set calendar object equal to date of booking at index

    }

    private void initArrivalDatePlusTwoDaysCalObject(int pos){

        calendar = GregorianCalendar.getInstance();

        calendar.set(observables.getYearInt(), observables.getMonthInt() - 1, observables.getDayInt());

        calendar.add(Calendar.DATE, pos + 2);

    }

    private void makeAccNightButtonsVisibleAndInvisible(){

        for (int pos = 0; pos < allNumNightsLayouts.size(); pos++) {

            makeAccNightsButtonDisappear(pos);
            makeAccNightsButtonsVisible(pos);

        }

    }

    private void makeAccNightsButtonDisappear(int pos){

        allNumNightsLayouts.get(pos).setVisibility(View.GONE);

    }

    private void makeAccNightsButtonsVisible(int pos){

        for (int pos2 = 0; pos2 < pos; pos2 ++) {

            allNumNightsLayouts.get(pos2).setVisibility(View.VISIBLE);

        }

    }

    private void makeAccRevealViewsInvisible(){

        for (int pos = 0; pos > observables.getNumDaysLeftInteger(); pos--) {

            revealViews.get(pos).setVisibility(View.GONE);

        }

    }

    private void setNightsButtonsVisibilityAccToDaysUntilNextBooking(Context context){

        setThirteenDaysTillNextBookingVisibility();
        setTwelveDaysTillNextBookingVisibility();
        setElevenDaysTillNextBookingVisibility();
        setTenDaysTillNextBookingVisibility();
        setNineDaysTillNextBookingVisibility();
        setEightDaysTillNextBookingVisibility();
        setSevenDaysTillNextBookingVisbility();
        setSixDaysTillNextBookingVisibility();
        setFiveDaysTillNextBookingVisbility();
        setFourDaysTillNextBookingVisibility();
        setThreeDaysTillNextBookingVisibility();
        setTwoDaysTillNextBookingVisibility();
        setOneDayTillNextBookingVisibility();
        setNextBookingOutOfReachVisibility(context);

    }

    private void setThirteenDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 13){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);

        }

    }

    private void setTwelveDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 12){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);

        }

    }

    private void setElevenDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 11){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);

        }

    }

    private void setTenDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 10){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);

        }

    }

    private void setNextBookingOutOfReachVisibility(Context context){

        if (observables.getNumDaysLeftInteger() <= 0){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);
            allNumNightsLayouts.get(4).setVisibility(View.GONE);
            allNumNightsLayouts.get(3).setVisibility(View.GONE);
            allNumNightsLayouts.get(2).setVisibility(View.GONE);
            allNumNightsLayouts.get(1).setVisibility(View.GONE);
            allNumNightsLayouts.get(0).setVisibility(View.GONE);

            fab.setClickable(false);

            Toast.makeText(context, context.getResources().getString(R.string.apartement_can_not_be_booked), Toast.LENGTH_LONG).show();

        }

    }

    private void setOneDayTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 1){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);
            allNumNightsLayouts.get(4).setVisibility(View.GONE);
            allNumNightsLayouts.get(3).setVisibility(View.GONE);
            allNumNightsLayouts.get(2).setVisibility(View.GONE);
            allNumNightsLayouts.get(1).setVisibility(View.GONE);

        }

    }

    private void setTwoDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 2){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);
            allNumNightsLayouts.get(4).setVisibility(View.GONE);
            allNumNightsLayouts.get(3).setVisibility(View.GONE);
            allNumNightsLayouts.get(2).setVisibility(View.GONE);

        }

    }

    private void setThreeDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 3){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);
            allNumNightsLayouts.get(4).setVisibility(View.GONE);
            allNumNightsLayouts.get(3).setVisibility(View.GONE);

        }

    }

    private void setFourDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 4){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);
            allNumNightsLayouts.get(4).setVisibility(View.GONE);

        }

    }

    private void setFiveDaysTillNextBookingVisbility(){

        if (observables.getNumDaysLeftInteger() == 5){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);

        }

    }

    private void setSixDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 6){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);
            allNumNightsLayouts.get(6).setVisibility(View.GONE);
            allNumNightsLayouts.get(5).setVisibility(View.GONE);

        }

    }

    private void setSevenDaysTillNextBookingVisbility(){

        if (observables.getNumDaysLeftInteger() == 7){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);
            allNumNightsLayouts.get(7).setVisibility(View.GONE);

        }

    }

    private void setEightDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 8){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);
            allNumNightsLayouts.get(8).setVisibility(View.GONE);

        }

    }

    private void setNineDaysTillNextBookingVisibility(){

        if (observables.getNumDaysLeftInteger() == 9){

            allNumNightsLayouts.get(13).setVisibility(View.GONE);
            allNumNightsLayouts.get(12).setVisibility(View.GONE);
            allNumNightsLayouts.get(11).setVisibility(View.GONE);
            allNumNightsLayouts.get(10).setVisibility(View.GONE);
            allNumNightsLayouts.get(9).setVisibility(View.GONE);

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickListenerNumNightsButtons (
            final Context context, final List<View> allNumNightsButtons
    ) {

        for (int pos = 0; pos < allNumNightsButtons.size(); pos++) {

            final int finalPos = pos;

            allNumNightsButtons.get(pos).setOnTouchListener((view, motionEvent) -> {

                setOnClickListenerNumNightsButtonsFunctionality(context, finalPos, motionEvent);

                return true;

            });

        }

    }

   private void setOnClickListenerNumNightsButtonsFunctionality (
            Context context, int finalPos, MotionEvent motionEvent
    ) {

       initRevealViewAnimation(finalPos, motionEvent);

        selectAndDeselectAccordingButtons(context, finalPos);

       showFabIf1ButtonAllCategoriesSelected();

    }

    private void showFabIf1ButtonAllCategoriesSelected(){

        if ((one_layout.isSelected() || two_layout.isSelected())
                && (eight_to_six_framelayout.isSelected() || twelve_to_four_fiftynine_layout.isSelected() ||
                five_to_ten_layout.isSelected())){

            /*

             * if amount of people for booking and arrival date have also been selected
             * along with currently selected number of nights for stay, do the following

             */
            fab.show();

            // make fab visible

        }

    }

    private void selectAndDeselectAccordingButtons(Context context, int finalPos){

        for (int pos2 = 0; pos2 < allNumNightsLayouts.size(); pos2++) {

            deselectAllOtherButtons(context, pos2);

            if (pos2 == finalPos) {

                selectButtonAtGivenIndex(context, pos2);

                circularReveal.start();

            }

        }

    }

    private void selectButtonAtGivenIndex(Context context, int pos2){

        allNumNightsLayouts.get(pos2).setSelected(true);

        allNumNightsRevealViews.get(pos2).setBackgroundColor(context.getResources().getColor(R.color.introduction_button_clicked));

        allNumNightsRevealViews.get(pos2).setVisibility(View.VISIBLE);

    }

    private void deselectAllOtherButtons(Context context, int pos2){

        allNumNightsLayouts.get(pos2).setSelected(false);

        allNumNightsRevealViews.get(pos2).setBackgroundColor(context.getResources().getColor(R.color.main_background));

        allNumNightsRevealViews.get(pos2).setVisibility(View.INVISIBLE);

    }

    private void initRevealViewAnimation(int finalPos, MotionEvent motionEvent){

        int x = allNumNightsLayouts.get(finalPos).getWidth();
        int y = allNumNightsLayouts.get(finalPos).getHeight();

        float endRadius = (float) Math.hypot(x, y);

        circularReveal = ViewAnimationUtils.createCircularReveal(allNumNightsRevealViews.get(finalPos), (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

        // deselect all other number of nights views

    }

    private void fabClicked(AppCompatActivity appCompatActivity, Context context){

        if (StartupMethods.isOnline(context)) {

            // if device has an active internet connection, do the following

            model.saveNumPeople(context, one_layout, two_layout);

            model.saveArrivalTime(
                    context, eight_to_six_framelayout,
                    twelve_to_four_fiftynine_layout, five_to_ten_layout);

            model.saveInfoByForLoop(context, allTimeArrivalLayouts, allNumNightsLayouts);

            animateExitTransition(context, appCompatActivity);

        } else {

            StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

        }

    }

    private void animateExitTransition(Context context, AppCompatActivity appCompatActivity){

        StudentsCouchAnimations anims = new StudentsCouchAnimations(appCompatActivity);

        anims.animateActivityExitTransitionBookingActivity2(
                context, relativeLayout, relativeLayout_org_pos_x, fab, this);

        // also starts BookingActivity3

    }

    private void onePersonButtonSelected(Context context,  MotionEvent motionEvent){

        selectDeselectAccordingButtons4(context, motionEvent);

        if (    (one_layout_nights.isSelected() ||
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
                fourteen_layout.isSelected())

                &&

                (eight_to_six_framelayout.isSelected() || twelve_to_four_fiftynine_layout.isSelected() ||
                        five_to_ten_layout.isSelected())){

            /*

             * if number of nights and an arrival date have been selected along
             * with currently selected number of people staying in apartment during booking

             */

            fab.show();

            // show fab

        }

    }

    private void selectDeselectAccordingButtons4(Context context, MotionEvent motionEvent){

        selectDeselectButtonsInternal();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            StartupMethods.startCircularRevealAnimationCustomStartRadius(context, one_button_view, motionEvent, true);

        }

        selectDeselectAccordingButtonsUI4(context);

        observables.setNum_people(1);

    }

    private void selectDeselectButtonsInternal(){

        one_layout.setSelected(true);
        two_layout.setSelected(false);

        // select 'one person for this booking' button and deselect 'two people for this booking' button

    }

    private void selectDeselectAccordingButtonsUI4(Context context){

        one_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
        two_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

    }

    private void twoPeopleButtonSelected(Context context, MotionEvent motionEvent){

        //if build version is equal to or higher than Lollipop, use circular reveal animation.
        // if build version is lower than Lollipop, use background colour change method

        selectDeselectAccordingButtons5(context, motionEvent);

        if (    (one_layout_nights.isSelected() ||
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
                fourteen_layout.isSelected())

                &&

                (eight_to_six_framelayout.isSelected() || twelve_to_four_fiftynine_layout.isSelected() ||
                        five_to_ten_layout.isSelected())){

            /*

             * if an amount of nights and arrival date have been selected along
             * with currently selected number of people staying in apartment during booking

             */

            fab.show();

            // make fab visible

        }

    }

    private void selectDeselectAccordingButtons5(Context context, MotionEvent motionEvent){

        selectDeselectButtonsInternal2();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            StartupMethods.startCircularRevealAnimationCustomStartRadius(context, two_button_view, motionEvent, true);

        }

        selectDeselectAccordingButtonsUI5(context);

        observables.setNum_people(2);

    }

    private void selectDeselectButtonsInternal2(){

        one_layout.setSelected(false);
        two_layout.setSelected(true);

        // deselect 'one person for this booking' button and select 'two people for this booking' button

    }

    private void selectDeselectAccordingButtonsUI5(Context context){

        one_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
        two_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));

    }

    private void five_to_ten_layout_clicked(Context context, MotionEvent motionEvent){

        selectDeselectAccordingButtons1(context, motionEvent);

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


            fab.show();

            // make fab visible

        }
    }

    private void selectDeselectAccordingButtons1(Context context, MotionEvent motionEvent){

        selectDeselectButtons1();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //if build version is equal to or higher than Lollipop, use circular reveal animation.
            // if build version is lower than Lollipop, use background colour change method

            prepareAnimation1(motionEvent);

            selectDeselectAccordingButtonsUI1(context);


            circularReveal.start();

        } else {

            changeVisibility1();

        }

    }

    private void selectDeselectButtons1(){

        eight_to_six_framelayout.setSelected(false);
        twelve_to_four_fiftynine_layout.setSelected(false);
        five_to_ten_layout.setSelected(true);

        // deselect all other time of arrival views

    }

    private void prepareAnimation1(MotionEvent motionEvent){

        int x = five_to_ten_layout.getWidth();
        int y = five_to_ten_layout.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        circularReveal = ViewAnimationUtils.createCircularReveal(five_to_ten_button_view, (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

    }

    private void selectDeselectAccordingButtonsUI1(Context context){

        twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
        five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
        eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

        changeVisibility1();

    }

    private void changeVisibility1(){

        twelve_to_four_fiftynine_button_view.setVisibility(View.INVISIBLE);
        five_to_ten_button_view.setVisibility(View.VISIBLE);
        eight_to_eleven_fiftynine_button_view.setVisibility(View.INVISIBLE);

    }

    private void twelve_to_four_fiftynine_layout_clicked(Context context, MotionEvent motionEvent){

        selectDeselectAccordingButtons2(context, motionEvent);

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

            fab.show();

            // make fab visible

        }
    }

    private void selectDeselectAccordingButtons2(Context context, MotionEvent motionEvent){

        selectDeselectButtons2();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //if build version is equal to or higher than Lollipop, use circular reveal animation.
            // if build version is lower than Lollipop, use background colour change method

            prepareAnimations2(motionEvent);

            selectDeselectAccordingButtonsUI2(context);

            circularReveal.start();

        } else {

            changeVisibility2();

        }

    }

    private void selectDeselectButtons2(){

        twelve_to_four_fiftynine_layout.setSelected(true);
        eight_to_six_framelayout.setSelected(false);
        five_to_ten_layout.setSelected(false);

        // deselect all other time of arrival views

    }

    private void prepareAnimations2(MotionEvent motionEvent){

        int x = twelve_to_four_fiftynine_layout.getWidth();
        int y = twelve_to_four_fiftynine_layout.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        circularReveal = ViewAnimationUtils.createCircularReveal(twelve_to_four_fiftynine_button_view, (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

    }

    private void selectDeselectAccordingButtonsUI2(Context context){

        twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));
        five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
        eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));

        changeVisibility2();

    }

    private void changeVisibility2(){

        twelve_to_four_fiftynine_button_view.setVisibility(View.VISIBLE);
        five_to_ten_button_view.setVisibility(View.INVISIBLE);
        eight_to_eleven_fiftynine_button_view.setVisibility(View.INVISIBLE);

    }

    private void eight_to_six_layout_clicked(Context context, MotionEvent motionEvent){

        selectDeselectAccordingButtons3(context, motionEvent);

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

            fab.show();

            // make fab visible

        }

    }

    private void selectDeselectAccordingButtons3(Context context, MotionEvent motionEvent){

        selectDeselectButtons3();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //if build version is equal to or higher than Lollipop, use circular reveal animation.
            // if build version is lower than Lollipop, use background colour change method

            prepareAnimations3(motionEvent);

            selectDeselectAccordingButtonsUI3(context);

            circularReveal.start();

        } else {

            changeVisibility3();

        }

    }
    private void selectDeselectButtons3(){

        eight_to_six_framelayout.setSelected(true);
        twelve_to_four_fiftynine_layout.setSelected(false);
        five_to_ten_layout.setSelected(false);

        // deselect all other time of arrival views

    }

    private void prepareAnimations3(MotionEvent motionEvent){

        int x = eight_to_six_framelayout.getWidth();
        int y = eight_to_six_framelayout.getHeight();

        float endRadius = (float) Math.hypot(x, y);

        circularReveal = ViewAnimationUtils.createCircularReveal(eight_to_eleven_fiftynine_button_view, (int) motionEvent.getX(), (int) motionEvent.getY(), 0, endRadius);

    }

    private void selectDeselectAccordingButtonsUI3(Context context){

        twelve_to_four_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
        five_to_ten_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.main_background));
        eight_to_eleven_fiftynine_button_view.setBackgroundColor(ContextCompat.getColor(context, R.color.introduction_button_clicked));

        changeVisibility3();

    }

    private void changeVisibility3(){

        twelve_to_four_fiftynine_button_view.setVisibility(View.INVISIBLE);
        five_to_ten_button_view.setVisibility(View.INVISIBLE);
        eight_to_eleven_fiftynine_button_view.setVisibility(View.VISIBLE);

    }

    private void initNumPeopleOptions(){

        if (isTwoPeopleAllowed == 1){

            two_layout.setVisibility(View.GONE);

            // remove the possibility to select two people per stay if only one is allowed

        }

    }

}
