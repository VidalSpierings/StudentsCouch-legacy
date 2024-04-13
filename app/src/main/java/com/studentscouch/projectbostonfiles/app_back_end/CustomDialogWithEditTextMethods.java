package com.studentscouch.projectbostonfiles.app_back_end;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.observables.StartupActivityObservables;

public class CustomDialogWithEditTextMethods {

    public static void initialiseDuplicatedIBANprevention(
            DataSnapshot dataSnapshot, long counter, boolean shouldSubmit,
            DatabaseReference fbRef, DatabaseReference fbRef2, EditText editText,
            final AppCompatDialogFragment fragment, final Toast addedToast,
            Context context) {

        long childrenCount = dataSnapshot.getChildrenCount();

        StartupActivityObservables observables = new StartupActivityObservables();

        if (!observables.isChanged5()){

            // if data hasn't been changed already, do the following

            checkForDuplicateIBANs(dataSnapshot, counter, shouldSubmit, editText);

            submitIBANifLegal(
                    context, shouldSubmit, childrenCount, counter,
                    fbRef, fbRef2, editText, fragment, observables,
                    addedToast);


        }
    }

    private static void submitIBANifLegal(
            Context context, boolean shouldSubmit, long childrenCount,
            long counter, DatabaseReference fbRef, DatabaseReference fbRef2,
            EditText editText, androidx.appcompat.app.AppCompatDialogFragment fragment,
            StartupActivityObservables observables, Toast addedToast){

        if (shouldSubmit && childrenCount == counter) {

            submitIBAN(
                    fbRef, fbRef2, editText, observables,
                    fragment, addedToast);

        } else if (!shouldSubmit) {

            IBANinUseProtocol(context, fragment);

        }

    }

    private static void IBANinUseProtocol(Context context, androidx.appcompat.app.AppCompatDialogFragment fragment){

        // if IBAN number was prevented from being submitted to DB, do the following

        Toast.makeText(context, context.getResources().getString(R.string.iban_in_use), Toast.LENGTH_LONG).show();

        // inform user that IBAN is already in use by showing a toast

        fragment.dismiss();

        // dismiss dialog

    }

    private static void submitIBAN(
            DatabaseReference fbRef, DatabaseReference fbRef2, EditText editText,
            StartupActivityObservables observables,
            androidx.appcompat.app.AppCompatDialogFragment fragment,
            Toast addedToast){

        // when app is done running to all fields in database table, do the following

        submitData(fbRef, fbRef2, editText);

        observables.setChanged5(true);

        // inform app that data has been changed once

        dismissFragmentWhenInfoSubmitted(fbRef, fragment, addedToast);

    }

    private static void dismissFragmentWhenInfoSubmitted(DatabaseReference fbRef, androidx.appcompat.app.AppCompatDialogFragment fragment, Toast addedToast){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                fragment.dismiss();

                addedToast.show();

                // inform user that IBAN is added with the help of a toast and dismiss dialog
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    private static void submitData(
            DatabaseReference fbRef, DatabaseReference fbRef2, EditText editText){

        fbRef.child(ConstantsDB.APARTMENT_IBAN_NUMBER_TABLE).setValue(editText.getText().toString());

        fbRef.child(ConstantsDB.APARTMENT_IS_IBAN_ADDED_TABLE).setValue(1);

        fbRef2.child(ConstantsDB.IBAN_KEY_MIN_UNIQUE_ID + editText.getText().toString()).setValue(editText.getText().toString());

    }

    private static void checkForDuplicateIBANs(DataSnapshot dataSnapshot, long counter, boolean shouldSubmit, EditText editText){

        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

            counter += 1;

            // initialise counter variable

            if (postSnapshot.getValue(String.class).equals(editText.getText().toString())){

                // if IBAN number already exists, do the following:

                shouldSubmit = false;

                break;

                // prevent IBAN number from submitting to servers

            }

        }

    }

    }
