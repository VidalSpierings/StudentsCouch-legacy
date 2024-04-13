package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;

public interface PaymentFinishedActivityView {

    void setDialogText (
            Context context, TextView transaction_id_textView, String transaction_id,
            TextView congratulations_description, String SAMPLE_IBAN, FloatingActionButton fab,
            String locationName, TextView congratulations_textView);

    void retrieveBookingInfo(
            DataSnapshot dataSnapshot, double total_price,
            TextView total_price_textView);

}
