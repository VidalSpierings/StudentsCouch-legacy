package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.R;

import java.util.Objects;

public class CustomDialogNoInternetConnection extends androidx.appcompat.app.AppCompatDialogFragment {

    private TextView description_textView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_custom_dialog_no_internet_connection, null);

        linkXmlElementToJavaVariable(view);

        setTypeface();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // build dialog

        builder.setView(view);

        return builder.create();

    }

    private void linkXmlElementToJavaVariable(View view){

        description_textView = view.findViewById(R.id.descripton_textView);

        // link java variables with xml layout views

    }

    private void setTypeface(){

        Typeface tp = Typeface.createFromAsset(Objects.requireNonNull(
                getActivity()).getApplicationContext().getAssets(), "project_boston_typeface.ttf");

        description_textView.setTypeface(tp);

        // set typeFace to textView

    }

}
