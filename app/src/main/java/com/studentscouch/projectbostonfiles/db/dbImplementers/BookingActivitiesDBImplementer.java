package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.BookingActivitiesDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.BookingActivity2Model;
import com.studentscouch.projectbostonfiles.observables.BookingActivity2Observables;
import com.studentscouch.projectbostonfiles.observables.BookingActivityObservables;
import com.studentscouch.projectbostonfiles.view.viewImplementers.BookingActivity2ViewImplementer;
import com.studentscouch.projectbostonfiles.view.viewImplementers.BookingActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.BookingActivity2View;
import com.studentscouch.projectbostonfiles.view.views.BookingActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.BookingActivityViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BookingActivitiesDBImplementer implements BookingActivitiesDBInterface {

    private BookingActivity2Observables b2Observables;

    private BookingActivityObservables observables;

    private BookingActivityView view;

    private BookingActivity2View view2;

    private BookingActivityViewModel viewModel;

    private BookingActivity2Model bookingActivity2Model;

    private Calendar loopCalendar;

    private String
            firstNameGuest,
            lastNameGuest;

    public BookingActivitiesDBImplementer(
            BookingActivityViewImplementer bookingActivityViewImplementer, BookingActivity2ViewImplementer view2,
            BookingActivityObservables observables, BookingActivity2Observables b2Observables,
            BookingActivityViewModel viewModel, BookingActivity2Model bookingActivity2Model){

        this.observables = observables;
        this.b2Observables = b2Observables;
        view = bookingActivityViewImplementer;
        this.view2 = view2;
        this.viewModel = viewModel;
        this.bookingActivity2Model = bookingActivity2Model;

    }

    @Override
    public void initDBBookingActivity(AppCompatActivity appCompatActivity, Context context, ViewGroup viewGroup) {

        retrieveNumPeopleAllowedPerNight();

        retrieveAndInitBookings(context, appCompatActivity);

    }

    private void retrieveAndInitBookings(Context context, AppCompatActivity appCompatActivity) {

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID() + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        observables.setBookedDatesList(new ArrayList<>());

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                for(com.google.firebase.database.DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    retrieveBookingAtIndex(context, postSnapshot, appCompatActivity);

                    // retrieve booking at index

                }

                view.initialiseBookingAtIndex(context);

                // set text in TextView, layout params, etc. To date at index

                view.setChooseLayoutOnClickListener(appCompatActivity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void retrieveNumPeopleAllowedPerNight() {

        final DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + observables.getAID());

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                observables.setIsTwoPeopleAllowed(dataSnapshot.child(ConstantsDB.APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE).getValue(Integer.class));

                // retrieve data on whether or not host allowed two people per booking

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve amount of people allowed per night

    }

    @Override
    public void retrieveAllBookingDates(List<GregorianCalendar> calendarObjects, List<View> buttonLayouts) {

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + b2Observables.getAID() + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        fbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {

                    for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        b2Observables.setNumDaysStay(postSnapshot.child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class));

                        // get number of days

                        addBookingDateForEveryDayOfStay(postSnapshot);

                        changeNumNightsAvailabilityAccToBookingStartDate(calendarObjects, buttonLayouts);


                    }

                } catch (Exception e) {

                    // Do nothing

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void changeNumNightsAvailabilityAccToBookingStartDate(List<GregorianCalendar> calendarObjects, List<View> buttonLayouts){

        for (int pos = 0; pos < calendarObjects.size(); pos++) {

            Calendar calendar = GregorianCalendar.getInstance();

            calendar.set(b2Observables.getYearInt(), b2Observables.getMonthInt() - 1, b2Observables.getDayInt());

            calendar.add(Calendar.DATE, pos + 2);

            // create instance of calendar object with selected date by user, increment day by two plus index of the current item

            GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();

            // create new GregorianCalendar object

            cal.set(Calendar.YEAR, b2Observables.getBookedDatesList().get(0).get(2));
            cal.set(Calendar.MONTH, b2Observables.getBookedDatesList().get(0).get(1) - 1);
            cal.set(Calendar.DAY_OF_MONTH, b2Observables.getBookedDatesList().get(0).get(0));

            // set calendar object equal to date of booking at index

            buttonLayouts.get(pos).setVisibility(View.VISIBLE);

            // set all buttons visible

            changeNumNightsAvailabilityAccToBookingStartDateFunc(cal, calendar, pos, calendarObjects, buttonLayouts);

        }

    }

    private void changeNumNightsAvailabilityAccToBookingStartDateFunc(
            Calendar cal, Calendar calendar, int pos,
            List<GregorianCalendar> calendarObjects, List<View> buttonLayouts){

        if (cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {

            // if selected date precedes next booking by one day, do the following

            for (int pos2 = 0; pos2 < calendarObjects.size(); pos2++) {

                buttonLayouts.get(pos2).setVisibility(View.INVISIBLE);

                // make all views except '1 night' view invisible

                if (pos2 <= pos) {

                    buttonLayouts.get(pos2).setVisibility(View.VISIBLE);

                    // make available num nights visible

                }


            }

        }

    }

    private void addBookingDateForEveryDayOfStay(DataSnapshot postSnapshot){

        for (int i = 0; i < b2Observables.getNumDaysStay(); i++) {

            // for loop adding every date user will be staying in the apartment for number of days the stay will last

            int startDateDay = postSnapshot.child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).getValue(Integer.class);
            int startDateMonth = postSnapshot.child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).getValue(Integer.class);
            int startDateYear = postSnapshot.child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).getValue(Integer.class);

            // get starting Date of booking at index

            Calendar loopCalendar = Calendar.getInstance();

            // create new Calendar object

            loopCalendar.set(Calendar.DAY_OF_MONTH, startDateDay);
            loopCalendar.set(Calendar.MONTH, startDateMonth);
            loopCalendar.set(Calendar.YEAR, startDateYear);

            // set new Calendar object date equal to starting date of booking at index

            addDateToBookedDatesList(loopCalendar);

        }

    }

    private void addDateToBookedDatesList(Calendar loopCalendar){

        ArrayList<Integer> bookingDate = new ArrayList<Integer>();

        bookingDate.add(loopCalendar.get(Calendar.DAY_OF_MONTH));
        bookingDate.add(loopCalendar.get(Calendar.MONTH));
        bookingDate.add(loopCalendar.get(Calendar.YEAR));

        /*

         * add loopCalendar date to bookingDate arrayList,
         * the bookingDate arrayList is added to local (nested) arrayList 'bookingDatesList'
         * for every loop in this for loop

         */

        b2Observables.getBookedDatesList().add(bookingDate);

        // add bookingDate arrayList to bookedDatesList

    }

    @Override
    public void retrieveBookingAtIndex(Context context, DataSnapshot postSnapshot, Activity activity) {

        try {

            initAllUnavailableDates(postSnapshot);



        } catch (Exception e){

            Toast.makeText(context, context.getResources().getString(R.string.booking_values_not_retrieved), Toast.LENGTH_SHORT).show();

            activity.onBackPressed();

            activity.finish();

                // let user know when booking dates could not be retrieved



        }

    }

    @Override
    public void retrieveBookingAtIndexBookingActivity1(
            Context context, ArrayList<ArrayList<Integer>> bookedDatesList, Activity activity, ViewGroup viewGroup) {

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + observables.getAID()
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        observables.setBookedDatesList(new ArrayList<>());

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                for(com.google.firebase.database.DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    retrieveBookingAtIndex(context, postSnapshot, activity);

                    Log.i("lengthhh", observables.getBookedDatesList().size() + "0001");

                    // retrieve booking at index

                    view.initialiseBookingAtIndex(context);

                }

                Log.i("lengthhh", observables.getBookedDatesList().size() + "0002");

                // set text in TextView, layout params, etc. To date at index
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initAllUnavailableDates(DataSnapshot postSnapshot){

        int numDaysStay = Integer.valueOf(postSnapshot.child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class));

        Log.i("numStay", numDaysStay + "00000");

        for (int i = 0; i < numDaysStay; i++){

            ArrayList<Integer> bookingDate = new ArrayList<Integer>();

            int startDateDay = postSnapshot.child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).getValue(Integer.class);
            int startDateMonth = postSnapshot.child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).getValue(Integer.class);
            int startDateYear = postSnapshot.child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).getValue(Integer.class);

            // get starting date of booking, day, month and year respectively

            makeCalendarObjectFromSelectedDate(startDateDay, startDateMonth, startDateYear, i);

            createNewBookingDateAddToObservable(bookingDate);

        }

    }

    private void createNewBookingDateAddToObservable(ArrayList<Integer> bookingDate){

        int day = loopCalendar.get(Calendar.DAY_OF_MONTH);
        int month = loopCalendar.get(Calendar.MONTH);
        int year = loopCalendar.get(Calendar.YEAR);

        // save day, month and year of date in Integer objects

        bookingDate.add(day);
        bookingDate.add(month);
        bookingDate.add(year);

        addBookingDateToBookedDatesListObservable(bookingDate);

        Log.i("numSta2",  day + "000000");

    }

    private void addBookingDateToBookedDatesListObservable(ArrayList<Integer> bookingDate){

        observables.getBookedDatesList().add(bookingDate);

        // add new date to bookedDatesList array, a new unavailable date is added to this array
        // for all nights of every booking

                            /*

                            EXAMPLE:

                            made booking 1: 12-6-2019 for 3 nights
                            made booking 2: 6-4-2020 for 2 nights

                            the following dates will be added to the bookedDatesList array and thus become unavailable:

                            - 12-6-2019
                            - 13-6-2019
                            - 14-6-2019
                            - 15-6-2019

                            (Dates from booking 1)

                            - 6-4-2020
                            - 7-4-2020
                            - 8-4-2020

                            (Dates from booking 2)

                            - 6-4-2020
                            - 7-4-2020

                            */

    }

    private void makeCalendarObjectFromSelectedDate(int startDateDay, int startDateMonth, int startDateYear, int i){

        loopCalendar = Calendar.getInstance();

        loopCalendar.set(Calendar.DAY_OF_MONTH, startDateDay);
        loopCalendar.set(Calendar.MONTH, startDateMonth - 1);
        loopCalendar.set(Calendar.YEAR, startDateYear);

        loopCalendar.add(Calendar.DATE, i);

        // create calendar object and add unavailable date according to current position

    }

    private void retrieveAllBookingsFromDB(
            Context context, List<GregorianCalendar> calendarObjects, List<View> allNumNightsLayouts,
            Activity activity){

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + "Apartements/" + b2Observables.getAID() + "/bookings");

        // DB path to all bookings of apartment selected by user

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                try {

                    retrieveAllBookings(context, calendarObjects, dataSnapshot, allNumNightsLayouts, activity);



                } catch (Exception e) {

                    // Do nothing

                }

                // above try/catch block prevents above code from running when fab is clicked in BookingActivity3

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void updateLayoutAccToCurrDaysLimit(
            FloatingActionButton fab){

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + b2Observables.getAID()
                        + ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE_URL_REFERENCE);

        // DB path to amount of subleased nights left for apartment selected by user

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                updateLayoutAccToCurrDaysLimitFunc(getApplicationContext(), dataSnapshot, fab);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @Override
    public void initDBFuncBookingActivity2(Context context, List<GregorianCalendar> calendarObjects, List<View> allNumNightsLayouts,
                                                   FloatingActionButton fab,
                                                   AppCompatActivity appCompatActivity,
                                                   Activity activity, ViewGroup viewGroup){

        Firebase.setAndroidContext(appCompatActivity);

        retrieveAllBookingsFromDB(context, calendarObjects, allNumNightsLayouts, activity);

        retrievePricePerNight();

        updateLayoutAccToCurrDaysLimit(fab);

    }

    @Override
    public void retrievePricePerNight() {

        final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + b2Observables.getAID()
                        + ConstantsDB.APARTMENT_PRICE_PER_NIGHT_TABLE_URL_REFERENCE);

        // create fireBase links

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                b2Observables.setPrice_per_night(Integer.valueOf(dataSnapshot.getValue(String.class)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    private void updateLayoutAccToCurrDaysLimitFunc(
            Context context, DataSnapshot dataSnapshot,
            FloatingActionButton fab){

        String numDaysLeftString = String.valueOf(dataSnapshot.getValue(Integer.class));

        if (!numDaysLeftString.equals(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_NA_NON_IBAN) && b2Observables.getBookedDatesList().size() != 0){

            // if limited number of sublettable night per year rule does not apply to selected apartment, do the following

            fab.hide();

            // hide fab

            b2Observables.setNumDaysLeftInteger(dataSnapshot.getValue(Integer.class));

            // get amount of days left until subleased nights yearly limit is full

            view2.initButtonVisibilityAccToNumNightsLeft(context);

        }

    }

    private void retrieveAllBookings(
            Context context, List<GregorianCalendar> calendarObjects,
            DataSnapshot dataSnapshot, List<View> buttonLayouts,
            Activity activity) {

        try {

            changeUiAccToDaysTillNextApartmentBooking(dataSnapshot, calendarObjects, buttonLayouts);

        } catch (Exception e) {

            // Do nothing

        }

        try {

            createNewTextViewWithSelectedDateByUser(context);

        } catch (Exception e) {

            /*

            Toast.makeText(context, context.getResources().getString(R.string.unknown_error_close_app), Toast.LENGTH_LONG).show();

            activity.onBackPressed();

            */


        }


    }

    @SuppressLint("SetTextI18n")
    private void createNewTextViewWithSelectedDateByUser(Context context){

        for (int i = 0; i < b2Observables.getBookedDatesList().size(); i++) {

            TextView text = new TextView(context);
            text.setText(
                    b2Observables.getBookedDatesList().get(i).get(0) +
                            "-" + b2Observables.getBookedDatesList().get(i).get(1) + "-"
                            + b2Observables.getBookedDatesList().get(i).get(2));

            // insert date selected by user into new textView (assigned according to Dutch date formatting)

        }

    }

    private void changeUiAccToDaysTillNextApartmentBooking(
            DataSnapshot dataSnapshot, List<GregorianCalendar> calendarObjects, List<View> buttonLayouts){

        for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

            int numDaysStay = postSnapshot.child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).getValue(Integer.class);

            // get number of days

            addAllBookingsInternal(numDaysStay, postSnapshot);

            viewModel.setNumNightsVisibilityAccToNightsTillNextBooking(
                    calendarObjects, buttonLayouts,
                    b2Observables.getYearInt(), b2Observables.getMonthInt(), b2Observables.getDayInt());

        }

    }

    private void addAllBookingsInternal(int numDaysStay, DataSnapshot postSnapshot){

        for (int i = 0; i < numDaysStay; i++) {

            bookingActivity2Model.addBookingDate(postSnapshot);

        }

    }

    /*

    public void initDBFuncBookingActivity3(
            FloatingActionButton fab, final int time_of_arrival,
            final String firstNameHost, final String lastNameHost, final int yearStartInt, final int monthStartInt,
            final int dayStartInt, final int yearEndInt, final int monthEndInt, final int dayEndInt,
            final double total_price, final double moneyOwedFromGuest, final double moneyOwedFromHost, final double SCearnings,
            final int num_nights, final String AID, final String house_number,
            final String street, final String city_or_village, final AppCompatActivity appCompatActivity,
            final RelativeLayout relativeLayout, final Float relativeLayout_org_pos_x, final ObjectAnimator anim){

        Firebase.setAndroidContext(appCompatActivity);

        DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + AID + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        // DB link to all bookings of selected apartment

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE + FirebaseAuth.getInstance().getCurrentUser().getUid() + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE);

        final DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE + FirebaseAuth.getInstance().getCurrentUser().getUid());

        // DB link to user currently logged in on device

        final DatabaseReference fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.TRANSACTIONS_TABLE);

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                String locationName = dataSnapshot.child(ConstantsDB.APARTMENTS_TABLE).child(AID).child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).getValue(String.class);

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

    /*

                retrieveFullNameGuest(dataSnapshot);

                BookingActivity3Methods.initialiseFloatingActionButton(
                        getApplicationContext(), fab, time_of_arrival, fbRef,
                        fbRef2, firstNameGuest, lastNameGuest,
                        firstNameHost, lastNameHost, yearStartInt, monthStartInt,
                        dayStartInt, yearEndInt, monthEndInt, dayEndInt,
                        total_price, moneyOwedFromGuest, moneyOwedFromHost, SCearnings,
                        num_nights, AID, locationName, house_number,
                        street, city_or_village, fbRef5, appCompatActivity,
                        relativeLayout, relativeLayout_org_pos_x, anim);

                // initialise floatingActionButton

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve first and last name of guest

    }

    */

    /*

    private void retrieveFullNameGuest(DataSnapshot dataSnapshot){

        firstNameGuest = dataSnapshot.child(ConstantsDB.USERS_TABLE).child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ConstantsDB.USER_FIRST_NAME_TABLE).getValue(String.class);

        lastNameGuest = dataSnapshot.child(ConstantsDB.USERS_TABLE).child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ConstantsDB.USER_LAST_NAME_TABLE).getValue(String.class);

        // retrieve first and last name of guest for selected booking

    }

    */

    }
