package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDialogWithButton extends androidx.appcompat.app.AppCompatDialogFragment {

    // (new warning received dialog)

    private TextView description_textView;

    private FrameLayout button_layout;

    private DatabaseReference fbRef;

    private int numwarnings = 0;

    private TextView button_textView;

    private String UID;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Firebase.setAndroidContext(Objects.requireNonNull(getActivity()).getApplicationContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_with_button, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        getInfoFromPreviousActivity();

        initUI();

        makeButtonUnClickable();

        initDbLink();

        retrieveInfoFromDB();

        button_layout.setOnClickListener(view1 -> {

            fbRef.child("isDialogRead").setValue(0);

            dismiss();

            // inform database that user has read dialog

        });

        // when user presses 'ok' button, inform DB that user has read dialog text and prevent application from showing Dialog again

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        /*

        try {

            CustomDialogWithButtonListener listener = (CustomDialogWithButtonListener) context;

        } catch (ClassCastException e){

            // Do nothing

        }

        */

    }

    public interface CustomDialogWithButtonListener{

        void applyTexts(String description_text, String buttonText);

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);
        button_textView = view.findViewById(R.id.button_textView);

        button_layout = view.findViewById(R.id.button_layout);

        // link java variables with xml layout views

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                        getActivity())
                        .getApplicationContext()
                        .getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        button_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void getInfoFromPreviousActivity(){

        Bundle args = getArguments();
        UID = Objects.requireNonNull(args).getString("UID");

        // retrieve user ID required for obtaining correct firebase data path/link

    }

    private void initUI(){

        description_textView.setText(getResources().getString(R.string.warning_received1));

        button_textView.setText(getResources().getString(R.string.ok));

    }

    private void makeButtonUnClickable(){

        button_layout.setClickable(false);

    }

    private void initDbLink(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.WARNINGS_URL_REFERENCE);

    }

    private void retrieveInfo(DataSnapshot dataSnapshot){

        numwarnings = dataSnapshot.child("numWarnings").getValue(Integer.class);
        int isDialogRead = dataSnapshot.child("isDialogRead").getValue(Integer.class);

    }

    @SuppressLint("SetTextI18n")
    private void setDescTextAndButtonOnClickListener(){

        description_textView.setText(
                Objects.requireNonNull(
                        getActivity())
                        .getResources()
                        .getString(
                                R.string.warning_received1)
                        + " "
                        + numwarnings
                        + " "
                        + getActivity().getResources().getString(R.string.warning_received2));

        button_layout.setClickable(true);

        // display correct information in description textView

    }

    private void retrieveInfoFromDB(){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveInfo(dataSnapshot);

                setDescTextAndButtonOnClickListener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        // retrieve info on whether or not user has received a new warning

    }

}
