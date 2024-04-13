package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.R;

import java.util.Objects;

public class PlaceSelectionDialog extends androidx.appcompat.app.AppCompatDialog {

    private TextView
            understoodTextView,
            description_textView;

    private FrameLayout understoodButtonLayout;

    public PlaceSelectionDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection_dialog);

        linkXmlElementsToJavaVariables();

        setTypefaces(getContext());

        understoodButtonLayout.setOnClickListener(view1 -> {

            dismiss();

            // if user has pressed the 'understood' button, dismiss this dialog

        });

    }

    /*

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_place_selection_dialog, null);

        linkXmlElementsToJavaVariables(view);

        setTypefaces();

        understoodButtonLayout.setOnClickListener(view1 -> {

            dismiss();

            // if user has pressed the 'understood' button, dismiss this dialog

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // build dialog

        builder.setView(view);

        return builder.create();

    }

    */

    private void linkXmlElementsToJavaVariables(){

        understoodTextView = findViewById(R.id.ok_textView);
        description_textView = findViewById(R.id.descripton_textView);
        understoodButtonLayout = findViewById(R.id.ok_button_layout);

        // link xml layout elements to java variables

    }

    private void setTypefaces(Context context){

        Typeface tp = Typeface.createFromAsset(context.getAssets(),"project_boston_typeface.ttf");
        understoodTextView.setTypeface(tp);
        description_textView.setTypeface(tp);

        // assign typeface to textViews

    }

}
