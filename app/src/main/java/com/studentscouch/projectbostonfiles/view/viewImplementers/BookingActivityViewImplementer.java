package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.dbImplementers.BookingActivitiesDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.BookingActivitiesDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.BookingActivityModel;
import com.studentscouch.projectbostonfiles.observables.BookingActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.BookingActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.BookingActivityViewModel;

import java.util.Calendar;

public class BookingActivityViewImplementer implements BookingActivityView {

    private FloatingActionButton fab;

    private TextView
            date_textView,
            dates_booked_textView;


    private Typeface tp;

    private final int DIALOG_ID = 0;

    private int
            year,
            month,
            day;

    private LinearLayout booked_dates_layout;

    private RelativeLayout activity_relativeLayout;

    private RelativeLayout relativeLayout;

    private float relativeLayout_org_pos_x;

    private View rootView;

    private FrameLayout choose_date_layout;

    private ImageView backgroundImage;

    private ViewGroup mainViewGroup;

    private BookingActivityObservables observables;

    private BookingActivitiesDBInterface db;

    private BookingActivityModel model;

    private BookingActivityViewModel viewModel;

    private Context context;

    public BookingActivityViewImplementer(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_booking, viewGroup);
        mainViewGroup = viewGroup;

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        fab = rootView.findViewById(R.id.fab);

        TextView arrival_date_textView = rootView.findViewById(R.id.arrival_date_textView);
        TextView choose_date_textView = rootView.findViewById(R.id.choose_date_textView);
        date_textView = rootView.findViewById(R.id.date_textView);
        dates_booked_textView = rootView.findViewById(R.id.dates_booked_textView);

        choose_date_layout = rootView.findViewById(R.id.choose_date_layout);
        booked_dates_layout = rootView.findViewById(R.id.booked_dates_layout);
        activity_relativeLayout = rootView.findViewById(R.id.activity_relativeLayout);
        relativeLayout = rootView.findViewById(R.id.relativeLayout);

        backgroundImage = rootView.findViewById(R.id.backgroundImage);

        // connect java variables to xml layout views

        tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

        arrival_date_textView.setTypeface(tp);
        choose_date_textView.setTypeface(tp);
        date_textView.setTypeface(tp);
        dates_booked_textView.setTypeface(tp);

        // initialise and set typeface to all textViews

    }

    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        initActivity(context);

        relativeLayout_org_pos_x = relativeLayout.getX();

        // get x coordinate of current position relativeLayout

        model.setActivityBackgroundImage(context, appCompatActivity, backgroundImage);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) - 1;
        month = calendar.get(Calendar.MONTH) + 3;
        day = calendar.get(Calendar.DAY_OF_YEAR) + 1;

        // create calendar object and set to current date of device

        fab.hide();

        StudentsCouchAnimations anims = new StudentsCouchAnimations(appCompatActivity);

        anims.animateActivityTransitionBookingActivity(context, relativeLayout, relativeLayout_org_pos_x);

        // start 'enter activity' animations

        getDataFromPreviousActivity(appCompatActivity);

        // get apartment data passed from previous activity

        initDB(context, appCompatActivity, mainViewGroup);

        // retrieve all made bookings and add bookings to bookedDatesList array

        fab.setOnClickListener(view -> startNextBookingActivityScreen(context, appCompatActivity));

    }

    private void initActivity(Context context){

        this.context = context;

        observables = new BookingActivityObservables();

        model = new BookingActivityModel(this, observables);

        viewModel = new BookingActivityViewModel(model, observables);

        db = new BookingActivitiesDBImplementer(
                this, null, observables, null, viewModel, null);

    }

    private void initDB(Context context, AppCompatActivity appCompatActivity, ViewGroup viewGroup){

        Firebase.setAndroidContext(appCompatActivity);

        // enable fireBase

        db.initDBBookingActivity(appCompatActivity, context, viewGroup);

    }

    private void startNextBookingActivityScreen(Context context, AppCompatActivity appCompatActivity){

        if (StartupMethods.isOnline(context)) {

            StudentsCouchAnimations.animateActivityTransitionBookingActivity(
                    context, appCompatActivity, relativeLayout, relativeLayout_org_pos_x, this, viewModel);

        }

        if (!StartupMethods.isOnline(context)) {

            StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

            // if internet connection can not be found, inform user by showing dialog

        }

    }

    private void getDataFromPreviousActivity(AppCompatActivity appCompatActivity){

        observables.setAID(appCompatActivity.getIntent().getStringExtra("AID"));

        observables.setCity_or_village(appCompatActivity.getIntent().getStringExtra("cityVillage"));

        observables.setStreet(appCompatActivity.getIntent().getStringExtra("street"));

        observables.setHouse_number(appCompatActivity.getIntent().getStringExtra("house_number"));

        observables.setFirstNameHost(appCompatActivity.getIntent().getStringExtra("firstNameHost"));

        observables.setLastNameHost(appCompatActivity.getIntent().getStringExtra("lastNameHost"));

        // retrieve user/apartment metadata passed in from previous activity

    }

    @Override
    public void setLayoutParamsDateTextView(Context context, TextView text, Typeface tp) {

        text.setTextSize(15);
        text.setGravity(Gravity.CENTER);
        text.setTypeface(tp);
        text.setTextColor(context.getResources().getColor(R.color.textColorTertiary));
        text.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        // assign TextSize, TextView gravity, typeface, text color, width and height to textView

    }

    @Override
    public void disableFabClickAbility() {

        fab.setClickable(false);

    }

    @Override
    public void disableHideFab() {

        fab.setClickable(false);

        fab.hide();

    }

    @Override
    public void initialiseBookingAtIndex(Context context) {

        if (observables.getBookedDatesList() != null) {

            addTextView();

        }

        makeBookingDatesInvisibleIfApplicable();

    }

    @Override
    public void makeBookedDatesTextViewInvisible() {

        dates_booked_textView.setVisibility(View.GONE);

        // if there are no available dates, hide first textView

    }

    @Override
    public void showEnableFab() {

        fab.setClickable(true);

        // make fab clickable

        fab.show();

    }

    @Override
    public void animateOnActivityRestart(Context context, AppCompatActivity appCompatActivity) {

        /*

        StudentsCouchAnimations.animateActivityTransitionBookingActivity(
                context, appCompatActivity, relativeLayout, relativeLayout_org_pos_x, observables, this, viewModel);

        */

    }

    @Override
    public void makeFabClickable() {

        fab.setClickable(true);

        // make fab clickable again. Fab might have been made unclickable when pressed to resume to next activity

    }

    @Override
    public void setChooseLayoutOnClickListener(AppCompatActivity appCompatActivity){

        choose_date_layout.setOnClickListener(view -> appCompatActivity.showDialog(DIALOG_ID));

        // show custom dialog when user presses 'choose date' button

    }

    @SuppressLint("SetTextI18n")
    private void addTextView(){

        Log.i("obsLength", observables.getBookedDatesList().size() + "00000000");

        for (int i = 0; i < observables.getBookedDatesList().size(); i++) {

            // <---create textView for every unavailable date, place them underneath one another--->

            TextView text = new TextView(context);

            text.setText(
                    observables.getBookedDatesList().get(i).get(0)
                            + "-" + observables.getBookedDatesList().get(i).get(1)
                            + "-" + observables.getBookedDatesList().get(i).get(2));

            // set date according to Dutch date ordering arrangement for every date in the bookedDatesList array

            setLayoutParamsDateTextView(context, text ,tp);

            // set TextView layout params. See method for reference

            booked_dates_layout.addView(text);

            // add textView to the layout containing all booked dates textViews

            booked_dates_layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            // set booked dates layout width and height

        }

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            model.setDate(context, date_textView, activity_relativeLayout, i, i1, i2);

            // initialise date chosen by user

        }

    };

    @Override
    public DatePickerDialog getDateFromDialog(int id) {

        DatePickerDialog dialog  = new DatePickerDialog(context, datePickerListener, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000);

        // set selected date on dialog pop up to current date minus 24 hours

        if (id == DIALOG_ID)

            return dialog;
        return null;

        // try to set date selected upon calendar pop up as close to current date as possible

    }

    private void makeBookingDatesInvisibleIfApplicable(){

        if (observables.getBookedDatesList() == null){

            dates_booked_textView.setVisibility(View.GONE);

            // if there are no available dates, hide first textView

        }

    }

}
