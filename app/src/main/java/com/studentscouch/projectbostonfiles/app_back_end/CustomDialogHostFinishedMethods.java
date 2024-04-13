package com.studentscouch.projectbostonfiles.app_back_end;

import android.widget.Toast;

import com.firebase.client.ServerValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

import java.util.Calendar;

public class CustomDialogHostFinishedMethods {

    private static int month;
    private static int newValue2;

    public static void checkForWarnings(
            final DatabaseReference fbRef, DataSnapshot dataSnapshot,
            Toast yesToast, androidx.appcompat.app.AppCompatDialogFragment fragment) {

        getAllWarnings(dataSnapshot);

        getCurrMonth();

        freezeUserAcc3rdWarningReceived(fbRef);

        yesToast.show();

        informActivityButtonClicked();

        fragment.dismiss();

    }

    private static void getCurrMonth(){

        Calendar calendar = Calendar.getInstance();

        month = calendar.get(Calendar.MONTH + 1);

    }

    private static void getAllWarnings(DataSnapshot dataSnapshot){

        int value = dataSnapshot.child(ConstantsDB.USER_NUM_WARNINGS_TABLE).getValue(Integer.class);

        newValue2 = value + 1;

        // calculate new number of warnings

    }

    private static void freezeUserAcc3rdWarningReceived(DatabaseReference fbRef){

        submitNewWarning(fbRef);

        setFinalWarningMetaData(fbRef);

        freezeAccFor6Months(fbRef);

    }

    private static void setFinalWarningMetaData(DatabaseReference fbRef){

        fbRef.child(ConstantsDB.USER_MONTH_TABLE).setValue(month + 6);
        fbRef.child(ConstantsDB.USER_IS_UNLOCKED_AGAIN_TABLE).setValue(1);

        /*

         * update number of months wherein user will be blocked.
         * If three or more warnings are received, the receiving users' account will be blocked for 6 months

         */

    }

    private static void submitNewWarning(DatabaseReference fbRef){

        fbRef.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(1);
        fbRef.child(ConstantsDB.USER_NUM_WARNINGS_TABLE).setValue(newValue2);

        /*

         * inform DB that user has received a new warning.
         * New dialog will be displayed the first time the user that has received the warning logs in

         */

    }

    private static void freezeAccFor6Months(DatabaseReference fbRef){

        long newValue = (long) ServerValue.TIMESTAMP.hashCode() + 60000000000L;

        // add time that has to pass before user can regain control of their account again

        fbRef.child(ConstantsDB.USER_TIME_TABLE).setValue(newValue);

    }

    private static void informActivityButtonClicked(){

        StartupActivityObservables observables = new StartupActivityObservables();

        observables.setChanged4(true);

        // Inform StartupActivity that dialog has been instantiated one time

    }

    /*

    public static void initialiseNoButton (
            final Context context, FrameLayout no_button_layout, final DatabaseReference fbRef3,
            final androidx.appcompat.app.AppCompatDialogFragment fragment){

        no_button_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                fbRef3.child(ConstantsDB.BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE).setValue(0);

                fragment.dismiss();
                Toast.makeText(context, context.getResources().getString(R.string.feedback), Toast.LENGTH_LONG).show();

                // inform user that ratings have been submitted with the help of a toast

            }

        });

    }

    */

}
