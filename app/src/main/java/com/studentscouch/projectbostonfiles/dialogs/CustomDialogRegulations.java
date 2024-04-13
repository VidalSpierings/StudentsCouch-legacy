package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogRegulationsMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDialogRegulations extends androidx.appcompat.app.AppCompatDialogFragment {

    private String
            AID;

    private DatabaseReference
            fbRef,
            fbRef2;

    private TextView
            description_textView,
            link_textView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Firebase.setAndroidContext(Objects.requireNonNull(getActivity()).getApplicationContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_regulations, null);

        linkXmlElementsToJavaVariables(view);

        getDataFromActivity();

        setTypefaces();

        setDescriptionComponentLoadingText();

        initDbLinks();

        setDescAndHyperLinkOnClickListenerOnDataRetrieved();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();

    }

    private void linkXmlElementsToJavaVariables(View view){

        description_textView = view.findViewById(R.id.descripton_textView);
        link_textView = view.findViewById(R.id.link_textView);

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                        getActivity())
                        .getApplicationContext()
                        .getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        link_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void getDataFromActivity(){

        Bundle args = getArguments();
        AID = Objects.requireNonNull(args).getString("AID");

    }

    private void setDescriptionComponentLoadingText(){

        description_textView.setText(getResources().getString(R.string.loading));

        // display correct information for specific city in description textView

    }

    private void initDbLinks(){

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID);

        // DB link to specified apartment

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.IS_CITY_REGULATIONS_DIALOG_READ_URL_REFERENCE);

        // DB link to subletting regulations info dialog

    }

    private void setDescAndHyperLinkOnClickListenerOnDataRetrieved(){

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogRegulationsMethods.setDescription(Objects.requireNonNull(getContext()), description_textView, link_textView);

                // set description of dialog

                CustomDialogRegulationsMethods.setHyperLinkOnClickListener(getContext(), link_textView, fbRef);

                // initialise hyperclick onClickListener. Clicking hyperlink takes user to subletting regulations document
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
