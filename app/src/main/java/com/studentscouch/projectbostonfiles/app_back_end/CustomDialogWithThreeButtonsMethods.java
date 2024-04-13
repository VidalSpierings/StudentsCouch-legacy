package com.studentscouch.projectbostonfiles.app_back_end;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;
import com.studentscouch.projectbostonfiles.StartupMethods;

public class CustomDialogWithThreeButtonsMethods {

    public static void setRemoveHostStatusButtonOnClickListener (
            FrameLayout remove_hoststatus_button_layout, final DatabaseReference fbRef, final androidx.appcompat.app.AppCompatDialogFragment fragment,
            final Toast status_removed) {

        remove_hoststatus_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fbRef.child(ConstantsDB.APARTMENT_STATUS_TABLE).setValue(ConstantsDB.HOST_STATUS_NOT_A_HOST_ADDED);

                fbRef.child(ConstantsDB.APARTMENT_IS_DIALOG_READ_TABLE).setValue(0);

                fragment.dismiss();

                status_removed.show();

                // inform database that user has removed host status

            }
        });

    }

    public static void set_proceed_button_onCLickListener (
            final Context context, FrameLayout proceed_button_layout, final Activity activity,
            final androidx.appcompat.app.AppCompatDialogFragment fragment) {

        proceed_button_layout.setOnClickListener(view -> launchApartmentRegistrationActivityIfOnline(context, activity, fragment));

    }

    static void launchApartmentRegistrationActivityIfOnline(
            Context context, Activity activity,
            androidx.appcompat.app.AppCompatDialogFragment fragment){

        if (StartupMethods.isOnline(context)) {

            launchApartmentRegistrationActivity(context, activity);

        } else {

            fragment.dismiss();

            // dismiss dialog

            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();

            // if no internet connection was found, inform user by showing a toast

        }

        // launch apartment register activity when user selects proceed button (no activity animation of any kind)

    }

    private static void launchApartmentRegistrationActivity(Context context, Activity activity){

        Intent i = new Intent(activity, RegisterActivityYesScreen1.class);

        i.putExtra("isFromProfileActivity", 1);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        context.startActivity(i);

        // open first apartment register activity

    }

}
