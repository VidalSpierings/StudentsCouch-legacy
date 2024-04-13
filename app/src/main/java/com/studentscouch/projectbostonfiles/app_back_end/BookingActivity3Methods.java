package com.studentscouch.projectbostonfiles.app_back_end;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.Constants;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.BookingActivity3;

import java.util.Objects;

public class BookingActivity3Methods {

    // generate 4 different sets of random strings
    // to minimize the chance of generating duplicate transaction ID's

    private static String transactionID;

    private static String bookingID;

    static ObjectAnimator anim;

    public static void calculatePrice () {

        int normal_price = BookingActivity3.price_per_night * BookingActivity3.num_nights;

        //Toast.makeText(context, BookingActivity3.num_nights + " " + BookingActivity3.price_per_night, Toast.LENGTH_SHORT).show();

        if (BookingActivity3.num_people == 1){

            calculatePriceForOnePerson(normal_price);

        } else if (BookingActivity3.num_people == 2){

            calculatePriceForTwoPeople(normal_price);

        }

    }

    private static void calculatePriceForTwoPeople(int normal_price){

        double price = normal_price * Constants.TWO_PEOPLE_COMMISSION_FEE_PERCENTAGE;

        // if two people are booked for a stay, increase the original price by 25%

        BookingActivity3.total_price = price + (price * Constants.GUEST_COMMISSION_FEE_PERCENTAGE);

        // if two people are booked for the stay, the original price is first multiplied by 1.25, then a fee of 0.09% is added

        BookingActivity3.moneyOwedFromGuest = (normal_price * Constants.GUEST_COMMISSION_FEE_PERCENTAGE);

        BookingActivity3.moneyOwedFromHost = (normal_price * Constants.HOST_COMMISSION_FEE_PERCENTAGE);

        // make host pay a 3% transaction fee of their net earnings. This 3% are the earnings StudentsCouch makes from guests

        BookingActivity3.SCearnings = BookingActivity3.moneyOwedFromHost + BookingActivity3.moneyOwedFromGuest;

    }

    private static void calculatePriceForOnePerson(int normal_price){

        BookingActivity3.total_price = normal_price + (normal_price * Constants.GUEST_COMMISSION_FEE_PERCENTAGE);

        // add 10% transaction fee to total price. This extra 10% are the earnings StudentsCouch makes from guests

        BookingActivity3.moneyOwedFromGuest = (normal_price * Constants.GUEST_COMMISSION_FEE_PERCENTAGE);

        BookingActivity3.moneyOwedFromHost = (normal_price * Constants.HOST_COMMISSION_FEE_PERCENTAGE);

        // make host pay a 1% transaction fee of their net earnings. This 1% are the earnings StudentsCouch makes from hosts

        BookingActivity3.SCearnings = BookingActivity3.moneyOwedFromHost + BookingActivity3.moneyOwedFromGuest;

    }

    public static void initialiseFloatingActionButton(
            final Context context, final FloatingActionButton fab, final int time_of_arrival, final DatabaseReference fbRef,
            final DatabaseReference fbRef2, final String firstNameGuest, final String lastNameGuest,
            final String firstNameHost, final String lastNameHost, final int yearStartInt, final int monthStartInt,
            final int dayStartInt, final int yearEndInt, final int monthEndInt, final int dayEndInt,
            final double total_price, final double moneyOwedFromGuest, final double moneyOwedFromHost, final double SCearnings,
            final int num_nights, final String AID, final String locationName, final String house_number,
            final String street, final String city_or_village, final DatabaseReference fbRef5, final AppCompatActivity appCompatActivity,
            final RelativeLayout relativeLayout, final Float relativeLayout_org_pos_x, final ObjectAnimator anim){

        fab.setOnClickListener(view -> {

            if (StartupMethods.isOnline(context) && time_of_arrival != 0) {

                    animateActivityExitTransition(context, relativeLayout, relativeLayout_org_pos_x, fab);

                    Toast.makeText(context, context.getResources().getString(R.string.thanks_booking), Toast.LENGTH_LONG).show();

                    // inform user that booking has been successfully submitted

                    initVariablesFabMethod();

                    submitData(
                            time_of_arrival, fbRef, fbRef2, firstNameGuest,
                            lastNameGuest, firstNameHost, lastNameHost, yearStartInt,
                            monthStartInt, dayStartInt, yearEndInt, monthEndInt,
                            dayEndInt, total_price, moneyOwedFromGuest, moneyOwedFromHost,
                            SCearnings, num_nights, AID, locationName, fbRef5,
                            transactionID, bookingID, house_number, street, city_or_village);

                    // set data required for a working booking

                    launchStartupActivity(context);

            } else if (!StartupMethods.isOnline(context)) {

                StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

                // if internet connection was not found, inform user by showing a dialog

            }

        });

    }

    private static void initVariablesFabMethod(){

        String transactionID1 = StartupMethods.getRandomString(4);

        String transactionID2 = StartupMethods.getRandomString(4);

        String transactionID3 = StartupMethods.getRandomString(4);

        String transactionID4 = StartupMethods.getRandomString(4);

        // generate 4 different sets of random strings
        // to minimize the chance of generating duplicate transaction ID's

        transactionID = transactionID1 + "_" + transactionID2 + "_" + transactionID3 + "_" + transactionID4;

        bookingID = StartupMethods.getRandomString(15);

        // paste all 4 random string together with underscores seperating the string every four letters

    }

    private static void launchStartupActivity(Context context){

        Intent i = new Intent(context, StartupActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);

        // return to startupActivity

    }

    private static void submitData(final int time_of_arrival, final DatabaseReference fbRef,
                                   final DatabaseReference fbRef2, final String firstNameGuest, final String lastNameGuest,
                                   final String firstNameHost, final String lastNameHost, final int yearStartInt, final int monthStartInt,
                                   final int dayStartInt, final int yearEndInt, final int monthEndInt, final int dayEndInt,
                                   final double total_price, final double moneyOwedFromGuest, final double moneyOwedFromHost, final double SCearnings,
                                   final int num_nights, final String AID, final String locationName,
                                   final DatabaseReference fbRef5, String transactionID,
                                   String bookingID, String house_number, String street, String city_or_village){

        submitDataToHost(
                time_of_arrival, fbRef, firstNameGuest, lastNameGuest,
                firstNameHost, lastNameHost, yearStartInt, monthStartInt,
                dayStartInt, yearEndInt, monthEndInt, dayEndInt, total_price,
                moneyOwedFromGuest, moneyOwedFromHost, SCearnings, num_nights,
                AID, locationName);

        submitDataToBooker(time_of_arrival, fbRef2, firstNameGuest, lastNameGuest,
                firstNameHost, lastNameHost, yearStartInt, monthStartInt,
                dayStartInt, yearEndInt, monthEndInt, dayEndInt, total_price,
                moneyOwedFromGuest, moneyOwedFromHost, SCearnings, num_nights,
                AID, locationName, house_number, street, city_or_village);

        submitInfoToAllBookingsTable(fbRef5, transactionID, AID, bookingID);

    }
    private static void submitInfoToAllBookingsTable(DatabaseReference fbRef5, String transactionID, String AID, String bookingID){

        fbRef5.child(transactionID).child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).setValue(
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        fbRef5.child(transactionID).child(ConstantsDB.BOOKING_IS_OFFICIAL_TABLE).setValue(ConstantsDB.NO);
        fbRef5.child(transactionID).child(ConstantsDB.APARTMENT_AID_TABLE).setValue(AID);
        fbRef5.child(transactionID).child(ConstantsDB.BOOKING_ID_TABLE).setValue(bookingID);

        // set metaData for booking transaction DB table

    }

    private static void submitDataToBooker(final int time_of_arrival, final DatabaseReference fbRef2,
                                           final String firstNameGuest, final String lastNameGuest,
                                           final String firstNameHost, final String lastNameHost, final int yearStartInt, final int monthStartInt,
                                           final int dayStartInt, final int yearEndInt, final int monthEndInt, final int dayEndInt,
                                           final double total_price, final double moneyOwedFromGuest, final double moneyOwedFromHost, final double SCearnings,
                                           final int num_nights, final String AID, final String locationName,
                                           String house_number, String street, String city_or_village){

        submitNames(fbRef2, firstNameGuest, lastNameGuest, firstNameHost, lastNameHost);

        submitBookingDurationInfo(
                fbRef2, yearStartInt, monthStartInt, dayStartInt,
                yearEndInt, monthEndInt, dayEndInt, num_nights);

        submitBookingProgressInfo(fbRef2);

        submitMonetaryInfo(fbRef2, total_price, moneyOwedFromGuest, moneyOwedFromHost, SCearnings);

        submitApartmentAddressInfo(fbRef2);

        submitMiscInfo(fbRef2, AID, locationName, time_of_arrival);

    }

    private static void submitDataToHost(final int time_of_arrival, final DatabaseReference fbRef,
                                         final String firstNameGuest, final String lastNameGuest,
                                         final String firstNameHost, final String lastNameHost, final int yearStartInt, final int monthStartInt,
                                         final int dayStartInt, final int yearEndInt, final int monthEndInt, final int dayEndInt,
                                         final double total_price, final double moneyOwedFromGuest, final double moneyOwedFromHost, final double SCearnings,
                                         final int num_nights, final String AID, final String locationName){

        submitNames(fbRef, firstNameGuest, lastNameGuest, firstNameHost, lastNameHost);

        submitBookingDurationInfo(
                fbRef, yearStartInt, monthStartInt, dayStartInt,
                yearEndInt, monthEndInt, dayEndInt, num_nights);

        submitBookingProgressInfo(fbRef);

        submitMonetaryInfo(fbRef, total_price, moneyOwedFromGuest, moneyOwedFromHost, SCearnings);

        submitApartmentAddressInfo(fbRef);

        submitMiscInfo(fbRef, AID, locationName, time_of_arrival);

    }

    private static void submitMiscInfo(DatabaseReference fbRef, String AID, String locationName, int time_of_arrival){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_BOOKER_UID_TABLE).setValue(
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        // set booking UID of the person booking the stay in DB table of booking

        fbRef.child(bookingID).child(ConstantsDB.APARTMENT_AID_TABLE).setValue(AID);

        // set AID of selected apartment in booking table

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_LOCATION_NAME_EARNINGS_TABLE).setValue(locationName);

        // set name of location in Db table for booking (I.E. Amsterdam, Utrecht, Den Haag, etc.)

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_TIME_OF_ARRIVAL_TABLE).setValue(time_of_arrival);

        // set house number, street name, name of city/village and time of arrival

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).setValue(1);

        // set data on whether or not host has read dialog informing them that the stay has ended

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_TRANSACTION_ID_TABLE).setValue(transactionID);

        // set transaction ID of booking

    }

    private static void submitApartmentAddressInfo(DatabaseReference fbRef){

        fbRef.child(bookingID).child(ConstantsDB.APARTMENT_HOUSE_NUMBER_TABLE).setValue(BookingActivity3.house_number);
        fbRef.child(bookingID).child(ConstantsDB.APARTMENT_STREET_TABLE).setValue(BookingActivity3.street);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_CITY_VILLAGE_TABLE).setValue(BookingActivity3.city_or_village);

        // set house number, street name, name of city/village

    }

    private static void submitMonetaryInfo(DatabaseReference fbRef, double total_price, double moneyOwedFromGuest, double moneyOwedFromHost, double SCearnings){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_TOTAL_PRICE_TABLE).setValue(total_price);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_MONEY_COMISSION_GUEST_TABLE).setValue(moneyOwedFromGuest);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_MONEY_COMISSION_HOST_TABLE).setValue(moneyOwedFromHost);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_TOTAL_SC_EARNINGS_TABLE).setValue(SCearnings);

        // set financial infomation about booking

    }

    private static void submitBookingProgressInfo(DatabaseReference fbRef){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_IS_ACCEPTED_TABLE).setValue(String.valueOf(0));
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_IS_ACCEPTED_2_TABLE).setValue(String.valueOf(0));
        fbRef.child(bookingID).child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(String.valueOf(1));
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_IS_DIALOG_READ_2_TABLE).setValue(String.valueOf(0));

        // set metadata about booking status

    }

    private static void submitNames(DatabaseReference fbRef, String firstNameGuest, String lastNameGuest, String firstNameHost, String lastNameHost){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_FIRST_NAME_GUEST_TABLE).setValue(firstNameGuest);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_LAST_NAME_GUEST_TABLE).setValue(lastNameGuest);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_FIRST_NAME_HOST_TABLE).setValue(firstNameHost);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_LAST_NAME_HOST_TABLE).setValue(lastNameHost);

        // set first and last names of both the guest and the host

    }

    private static void submitBookingDurationInfo(DatabaseReference fbRef, int yearStartInt, int monthStartInt, int dayStartInt, int yearEndInt, int monthEndInt, int dayEndInt, int num_nights){

        submitStartDate(fbRef, yearStartInt, monthStartInt, dayStartInt);

        submitFinishDate(fbRef, yearEndInt, monthEndInt, dayEndInt);

        submitNumNightsStay(fbRef, num_nights);

    }

    private static void submitNumNightsStay(DatabaseReference fbRef, int num_nights){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_NUM_DAYS_STAY_TABLE).setValue(num_nights);

        // set number of nights the user will be staying at the apartment

    }

    private static void submitStartDate(DatabaseReference fbRef, int yearStartInt, int monthStartInt, int dayStartInt){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_START_DATE_YEAR_TABLE).setValue(yearStartInt);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_START_DATE_MONTH_TABLE).setValue(monthStartInt + 1);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_START_DATE_DAY_TABLE).setValue(dayStartInt);

        // set starting date of booking

    }

    private static void submitFinishDate(DatabaseReference fbRef, int yearEndInt, int monthEndInt, int dayEndInt){

        fbRef.child(bookingID).child(ConstantsDB.BOOKING_FINISH_DATE_YEAR_TABLE).setValue(yearEndInt);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_FINISH_DATE_MONTH_TABLE).setValue(monthEndInt + 1);
        fbRef.child(bookingID).child(ConstantsDB.BOOKING_FINISH_DATE_DAY_TABLE).setValue(dayEndInt);

        // set ending date of booking

    }

    public static void animateActivityTransition(
            Context context, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x) {

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();

            // get screen metrics.
            // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", metrics.widthPixels - relativeLayout_org_pos_x, relativeLayout_org_pos_x);
            anim.setDuration(400);

            // set starting delay and duration of animations in milliseconds

            anim.start();

            // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

    }

    private static void animateActivityExitTransition(
            Context context, RelativeLayout relativeLayout, Float relativeLayout_org_pos_x,
            final FloatingActionButton fab){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        anim = ObjectAnimator.ofFloat(relativeLayout, "translationX", relativeLayout_org_pos_x, -metrics.widthPixels - relativeLayout_org_pos_x);
        anim.setDuration(400);

        // set starting delay and duration of animations in milliseconds

        setAnimationOnStartListener(fab);

        anim.start();

    }

    private static void setAnimationOnStartListener(FloatingActionButton fab){

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                fab.setClickable(false);

                // disable fab clicking after animation starts

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                // intent
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

}
