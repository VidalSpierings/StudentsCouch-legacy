package com.studentscouch.projectbostonfiles.db.dbImplementers;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsCountryCodes;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.db.interfaces.LoginActivityDBInterface;
import com.studentscouch.projectbostonfiles.observables.LogInActivityObservables;
import com.studentscouch.projectbostonfiles.ui.LoginActivity;
import com.studentscouch.projectbostonfiles.view.views.LogInActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.LogInActivityViewModel;

import java.util.List;

public class LoginActivityDBImplementer implements LoginActivityDBInterface {

    private LogInActivityObservables observables;

    private LogInActivityView view;

    private LogInActivityViewModel viewModel;

    public LoginActivityDBImplementer(LogInActivityView view, LogInActivityObservables observables){

        this.observables = observables;

        this.view = view;
        viewModel = new LogInActivityViewModel(view, observables);


    }

    @Override
    public String retrieveUserCountry(DataSnapshot dataSnapshot) {

        return dataSnapshot.getValue(String.class);

    }

    private void checkForNumWarningsFunc(Context context, DataSnapshot dataSnapshot, DatabaseReference fbRef) {

        attemptShowFinalWarningScreenIf3PlusWarnings(context, dataSnapshot);

        unblockAccountWhenSpecifiedTimePassed(dataSnapshot, fbRef);

    }

    private void unblockAccountWhenSpecifiedTimePassed(DataSnapshot dataSnapshot, DatabaseReference fbRef){

        try {

            if (Long.valueOf(ServerValue.TIMESTAMP.hashCode())
                    >= dataSnapshot.child(ConstantsDB.USER_TIME_TABLE).getValue(Long.class)
                    && dataSnapshot.child(ConstantsDB.USER_IS_UNLOCKED_AGAIN_TABLE).getValue(Integer.class) == 1){

                fbRef.child(ConstantsDB.USER_NUM_WARNINGS_TABLE).setValue(0);
                fbRef.child(ConstantsDB.USER_TIME_TABLE).setValue(0);
                fbRef.child(ConstantsDB.USER_IS_UNLOCKED_AGAIN_TABLE).setValue(0);

                // check if account is unlocked again

            }

        } catch (Exception e){

            // Do nothing

        }

    }

    private void attemptShowFinalWarningScreenIf3PlusWarnings(Context context, DataSnapshot dataSnapshot){

        try {

            if (dataSnapshot.child(ConstantsDB.USER_NUM_WARNINGS_TABLE).getValue(Integer.class) >= 3){

                Toast toast = Toast.makeText(context,
                        context.getResources().getString(R.string.account_blocked) + " " + observables.getMonth(), Toast.LENGTH_LONG);

                Intent i = new Intent(context, LoginActivity.class);

                context.startActivity(i);

                toast.show();

                // inform user that account has been blocked for receiving 3 or more warnings
                // and inform user when account will be unlocked again

            }

        } catch (Exception e) {

            // Do nothing

        }

    }

    @Override
    public void retrieveMiscUserData(
            Context context, Task<AuthResult> finalTask, AppCompatActivity appCompatActivity,
            FirebaseAuth auth,
            List<String> monthNames, ViewGroup viewGroup,
            FrameLayout progress_layout, ImageView header_image) {

        Firebase.setAndroidContext(context);

        logInSuccessfulProtocol(
                context, appCompatActivity,
                finalTask, auth,
                monthNames,
                progress_layout,
                header_image);

       logInUnSuccessfulProtocol(context, appCompatActivity, finalTask);

    }

    @Override
    public void signInUser(Context context, AppCompatActivity appCompatActivity,
                            FirebaseAuth auth, FrameLayout progress_layout,
                            Animation exit_anim, List<String> monthNames,
                            ViewGroup viewGroup, String username1, String password,
                            ImageView header_image) {

        auth.signInWithEmailAndPassword(username1, password).addOnCompleteListener(task -> signInUserFunc(
                context, appCompatActivity, task, auth,
                progress_layout, exit_anim, monthNames,
                viewGroup, header_image));

    }

    private void signInUserFunc(
            Context context, AppCompatActivity appCompatActivity,
            Task<AuthResult> task, FirebaseAuth auth,
            FrameLayout progress_layout, Animation exit_anim,
            List<String> monthNames, ViewGroup viewGroup, ImageView header_image) {

        view.makeCheckBoxUnclickable();

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

        // make loading screen invisible

        exit_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                retrieveMiscUserData(
                        context, task, appCompatActivity,
                        auth, monthNames, viewGroup, progress_layout, header_image);

                // inform about account block with a toast

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

    private void checkIfPlatformOpenForNonHostsRetrieveWarningMonthFunc(Context context, DataSnapshot dataSnapshot, AppCompatActivity appCompatActivity) {

        observables.setHosStatus(dataSnapshot.getValue(String.class));

        // retrieve users' host status

        DatabaseReference fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.IS_APP_AVAILABLE_NON_HOSTS_TABLE);

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                int isUnlocked = dataSnapshot.getValue(Integer.class);

                showToastIfUserHostAndHostNotYetOpened(appCompatActivity, context, isUnlocked);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showToastIfUserHostAndHostNotYetOpened(AppCompatActivity appCompatActivity, Context context, int isUnlocked){

        if (observables.getHosStatus().equals(ConstantsDB.HOST_STATUS_NOT_A_HOST_ADDED) && isUnlocked == 0) {

            // check if platform is available to non-hosts

            appCompatActivity.onBackPressed();

            Toast.makeText(context, context.getResources().getString(R.string.platform_not_available_nonhosts), Toast.LENGTH_LONG).show();

            // return to LoginActivity if non-host logs in while the platform isn't
            // open for non hosts yet, inform them about the platform lock with the help of a toast

        }

    }

    private void logInUnSuccessfulProtocol(Context context, AppCompatActivity appCompatActivity, Task<AuthResult> finalTask){

        if (!finalTask.isSuccessful()){

            view.makeCheckboxClickable();

            // if login was successful, do the following

            checkIfEUblockAppliesToCurrUser(context, appCompatActivity);

            view.showUnableToConnectErrors(context, finalTask);

            // attempt to retrieve error message about the reason why app couldn't log in user

        }

        // inform about account block with a toast

    }

    private void checkIfEUblockAppliesToCurrUser(Context context, AppCompatActivity appCompatActivity){

        DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.EU_BLOCK_TABLE);

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue(String.class).equals(ConstantsDB.TRUE)) {

                    retrieveUserCountryCode(context, appCompatActivity);

                    appCompatActivity.onBackPressed();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retrieveUserCountryCode(Context context, AppCompatActivity appCompatActivity){

        DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + FirebaseAuth.getInstance().getUid()
                        + ConstantsDB.USER_COUNTRY_CODE_URL_REFERENCE);

        // DB link to countryCode of user

        fbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                retrieveUserCountryCodeFunc(context, dataSnapshot, appCompatActivity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void logInSuccessfulProtocol(
            Context context, AppCompatActivity appCompatActivity,
            Task<AuthResult> finalTask, FirebaseAuth auth, List<String> monthNames,
            FrameLayout progress_layout,
            ImageView header_image){

        if (finalTask.isSuccessful()) {

            viewModel.launchStartupActivity(context, appCompatActivity, progress_layout, header_image);

            checkIfPlatformOpenForNonHostsRetrieveWarningMonth(context, appCompatActivity, auth);

            setMostRecentLogInMonth(context, auth, monthNames);

            checkForNumWarnings(context);

        }

    }

    private void checkForNumWarnings(Context context){

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + FirebaseAuth.getInstance().getUid()
                        + ConstantsDB.USER_WARNINGS_URL_REFERENCE);

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                checkForNumWarningsFunc(
                        context, dataSnapshot, fbRef);

                // check for number of warnings account has received

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setMostRecentLogInMonth(Context context, FirebaseAuth auth, List<String> monthNames){

        DatabaseReference fbRef5 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + auth.getUid()
                        + ConstantsDB.USER_WARNINGS_MONTH_URL_REFERENCE);

        fbRef5.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                viewModel.setFabClickAbilityAccToInternetConnection(context);

                //LoginActivityMethods.retrieveMonth(month, monthNames, dataSnapshot.getValue(Integer.class));

                setMonth(monthNames, dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setMonth(List<String> monthNames, DataSnapshot dataSnapshot){

        try {

            observables.setMonth(view.retrieveMonth(monthNames, dataSnapshot.getValue(Integer.class)));

        } catch (Exception e) {

            // Do nothing

        }

        // update users' most recent log-in month to DB. When user receives three or more warnings,
        // their account will be suspended starting from the first month until 6 months in advance

    }

    private void checkIfPlatformOpenForNonHostsRetrieveWarningMonth(Context context, AppCompatActivity appCompatActivity, FirebaseAuth auth){

        String id = auth.getUid();

        DatabaseReference fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE + id + ConstantsDB.STATUS2_URL_REFERENCE);

        // DB link to status on whether or not user is a host or has to fill in their apartment info

        fbRef4.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                checkIfPlatformOpenForNonHostsRetrieveWarningMonthFunc(context, dataSnapshot, appCompatActivity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retrieveUserCountryCodeFunc (Context context, DataSnapshot dataSnapshot, AppCompatActivity appCompatActivity) {

        String countryCode = dataSnapshot.getValue(String.class);

        for (int i = 0; i < ConstantsCountryCodes.COUNTRIES_IN_EUROPE.length; i++) {

            if (countryCode.equals(ConstantsCountryCodes.COUNTRIES_IN_EUROPE[i])){

                Toast.makeText(context, context.getResources().getString(R.string.eu_block_active), Toast.LENGTH_LONG).show();

                appCompatActivity.onBackPressed();

                // block usage of StudentsCouch for users in European union

            }

        }

    }

}
