package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogWithEditTextMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDialogWithEditText extends androidx.appcompat.app.AppCompatDialogFragment {

    private EditText editText;

    private FloatingActionButton fab;

    public static String UID;

    private TextView description_textView;

    private DatabaseReference
            fbRef;
    private DatabaseReference fbRef2;

    private Toast addedToast;

    private boolean shouldSubmit = true;

    private long counter;

    @SuppressLint("ShowToast")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_with_edittext, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        initUI();

        Firebase.setAndroidContext(getActivity().getApplicationContext());

        initDbLinks();

        addEditTextOnTextChangedListener();

        addedToast = Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.iban_added), Toast.LENGTH_LONG);

        fab.setOnClickListener(view1 -> submitInfoIfIBANnotDuplicate());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // build dialog

        builder.setView(view);

        return builder.create();

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);

        editText = view.findViewById(R.id.editText);

        fab = view.findViewById(R.id.fab);

        // link java variables with xml layout views

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        editText.setTypeface(tp);
        description_textView.setTypeface(tp);

        // initialise and apply typeface to all textViews in layout

    }
    private void initUI(){

        description_textView.setText(getResources().getString(R.string.enter_iban));

        // set textView text

    }

    private void initDbLinks(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK
                + ConstantsDB.USERS_TABLE_URL_REFERENCE
                + UID);

        // DB link to specified user

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.NUMBERS_URL_REFERENCE);

        // DB link to IBAN numbers

    }

    private void showHideFabIfInfoEntered(){

        if (!editText.getText().toString().equals("")){

            fab.setClickable(true);

            fab.show();

            // show fab when text is entered into editText


        } else {

            fab.setClickable(false);

            fab.hide();

            // hide fab when no more text is entered into editText

        }

    }

    private void addEditTextOnTextChangedListener(){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // Do nothing

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                showHideFabIfInfoEntered();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void submitInfoIfIBANnotDuplicate(){

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogWithEditTextMethods.initialiseDuplicatedIBANprevention(
                        dataSnapshot, counter, shouldSubmit,
                        fbRef, fbRef2, editText, CustomDialogWithEditText.this,
                        addedToast, getContext());

                // initialise methods responsible for preventing duplicate IBAN numbers from being entered

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
