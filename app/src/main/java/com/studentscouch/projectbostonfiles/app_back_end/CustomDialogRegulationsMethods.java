package com.studentscouch.projectbostonfiles.app_back_end;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;

public class CustomDialogRegulationsMethods {

    public static void setDescription (Context context, TextView description_textView, TextView link_textView) {

        description_textView.setText(context.getResources().getString(R.string.subletting_rules_2));

        SpannableString content = new SpannableString(context.getResources().getString(R.string.subletting_regulations));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        link_textView.setText(content);
        link_textView.setText(content);

        // give text 'subletting regulations' blue color and underline

    }

    public static void setHyperLinkOnClickListener (final Context context, TextView link_textView, final DatabaseReference fbRef) {

        link_textView.setOnClickListener(view -> attemptShowRegulations(context, fbRef));

    }

    private static void attemptShowRegulations(Context context, DatabaseReference fbRef){

        if (StartupMethods.isOnline(context)) {

            openPDFfile(context, fbRef);

            fbRef.setValue("0");

            // inform database that user has opened regulations PDF file

        } else {

            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            // if internet connection could not be found, inform user by showing toast

        }

    }

    private static void openPDFfile(Context context, DatabaseReference fbRef){

        Uri uri = Uri.parse("NIEUWE LINK, RAADPLEEG NIEUWE WETGEVING WANNEER EMERGENCY ORDERS WEG ZIJN");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);

        // old link: https://drive.google.com/open?id=1jcr0kkpxf53AqeeTCb6bteiiyZe-ka6M"

    }

}
