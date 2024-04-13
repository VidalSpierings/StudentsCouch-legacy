package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.CustomDialogWithThreeButtonsMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Objects;

public class CustomDialogWIthThreeButtons extends androidx.appcompat.app.AppCompatDialogFragment {

    private DatabaseReference fbRef;

    public static String UID;

    private FrameLayout
            remove_hoststatus_button_layout,
            maybe_later_button_layout,
            proceed_button_layout;

    private TextView
            maybe_later_textView,
            description_textView,
            proceed_textView,
            remove_hoststatus_textView;

    private boolean firstVisit;

    private Toast status_removed;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Firebase.setAndroidContext(Objects.requireNonNull(getActivity()).getApplicationContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_dialog_with_three_buttons, null);

        linkXmlElementsToJavaVariables(view);

        initUI();

        setTypefaces();

        initMiscTasks();

        Firebase.setAndroidContext(getActivity());

        initDbLink();

        setOnClickListenersWhenDbConnectionEstablished(status_removed);

        maybe_later_button_layout.setOnClickListener(view1 -> {

            dismiss();

            // dismiss dialog

        });

        // initialise maybe later button

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();

    }

    @Override
    public void onResume() {

        if (firstVisit){

            firstVisit = false;

            // inform activity that first visit has been made

        }

        if (!firstVisit){

            maybe_later_button_layout.setClickable(true);
            remove_hoststatus_button_layout.setClickable(true);
            proceed_button_layout.setClickable(true);

            // upon second visit, set buttons clickable



        }

        // if user restarts activity, make above views selectable

        super.onResume();
    }

    private void linkXmlElementsToJavaVariables(View view){

        remove_hoststatus_button_layout = view.findViewById(R.id.remove_hoststatus_button_layout);
        maybe_later_button_layout = view.findViewById(R.id.maybe_later_button_layout);
        proceed_button_layout = view.findViewById(R.id.proceed_button_layout);

        description_textView = view.findViewById(R.id.descripton_textView);
        maybe_later_textView = view.findViewById(R.id.maybe_later_button_textView);
        remove_hoststatus_textView = view.findViewById(R.id.remove_hoststatus_textView);
        proceed_textView = view.findViewById(R.id.proceedtextView);

        // link java variables to xml layout views

    }

    private void initUI(){

        maybe_later_textView.setText(getResources().getString(R.string.later));
        remove_hoststatus_textView.setText(getResources().getString(R.string.remove_hoststatus));
        proceed_textView.setText(getResources().getString(R.string.proceed));
        description_textView.setText(getResources().getString(R.string.add_hoststatus_text));

        // set button texts

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(
                Objects.requireNonNull(
                        getActivity())
                        .getApplicationContext()
                        .getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        maybe_later_textView.setTypeface(tp);
        proceed_textView.setTypeface(tp);
        remove_hoststatus_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void initDbLink(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.HOST_STATUS_URL_REFERENCE);

        // create database link

    }

    private void setOnClickListenersWhenDbConnectionEstablished(Toast status_removed){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                CustomDialogWithThreeButtonsMethods.setRemoveHostStatusButtonOnClickListener(
                        remove_hoststatus_button_layout, fbRef, CustomDialogWIthThreeButtons.this, status_removed);

                // initialise remove host status button

                CustomDialogWithThreeButtonsMethods.set_proceed_button_onCLickListener(
                        getContext(), proceed_button_layout, getActivity(), CustomDialogWIthThreeButtons.this);

                /*

                 * initialise proceed button.
                 * Clicking button opens RegisterActivityYesScreen1
                 * and puts user through apartment details submission process

                 */

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @SuppressLint("ShowToast")
    private void initMiscTasks(){

        status_removed = Toast.makeText(
                Objects.requireNonNull(
                        getActivity())
                        .getApplicationContext(), getResources().getString(R.string.host_status_removed), Toast.LENGTH_SHORT);

        // create (not show) toast informing user that their host status has been removed

        firstVisit = true;

        // inform app that this is the users first visit

    }

}
