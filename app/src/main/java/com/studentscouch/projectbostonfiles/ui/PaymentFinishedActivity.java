package com.studentscouch.projectbostonfiles.ui;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.app_back_end.PaymentFinishedActivityMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

public class PaymentFinishedActivity extends AppCompatActivity {

    private TextView
            total_price_textView,
            congratulations_description,
            transaction_id_textView;

    private FloatingActionButton fab;

    FrameLayout
            progress_layout;

    private String
            locationName,
            UID = "",
            AID = "",
            bookingID;

    private DatabaseReference
            fbRef;
    private DatabaseReference fbRef2;

    private DatabaseReference fbRef3;

    public static String roomOrApartement;

    private String numDaysLeft = "";

    private String hostUID;

    public static String transaction_id;

    private String SAMPLE_IBAN = "NL46 RABO 0313 6684 18";

    public static Double total_price;

    LinearLayout subleased_nights_layout;

    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_finished);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        setBackground();

        // allow Firebase to be used in this activity

        getInfoFromPreviousActivity();

        fab.setClickable(false);

        initDB();

        setDBlinks();

        retrieveApartmentInfoAndSetTextFieldData();

        retrieveBookingInfoSetFabOnClickListener();

        /*

        tripadvisor_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://www.tripadvisor.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        */

    }

    private void retrieveBookingInfoSetFabOnClickListener(){

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveBookingInfo(dataSnapshot);

                final DatabaseReference fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.TRANSACTIONS_URL_REFERENCE + transaction_id);

                fbRef4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        fab.setOnClickListener(view -> PaymentFinishedActivityMethods.setFabOnClickListener(
                                getApplicationContext(), fbRef, fbRef2, fbRef4,
                                PaymentFinishedActivity.this));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // show total price and if room or apartment was selected for booking
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retrieveBookingInfo(DataSnapshot dataSnapshot){

        roomOrApartement = dataSnapshot.child(ConstantsDB.BOOKING_ROOM_OR_APARTMENT_TABLE).getValue(String.class);

        total_price = dataSnapshot.child(ConstantsDB.BOOKING_MONEY_2_TABLE).child(ConstantsDB.BOOKING_TOTAL_PRICE_TABLE).getValue(Double.class);

        transaction_id = dataSnapshot.child(ConstantsDB.BOOKING_TRANSACTION_ID_TABLE).getValue(String.class);

        //PaymentFinishedActivityMethods.retrieveBookingInfo(roomOrApartement, dataSnapshot, total_price, transaction_id, total_price_textView);

        // retrieve booking info

    }

    private void linkXmlElementsToJavaVariables(){

        total_price_textView = findViewById(R.id.congratulations_textView);
        congratulations_description = findViewById(R.id.congratulations_description);
        transaction_id_textView = findViewById(R.id.transaction_id_textView);

        subleased_nights_layout = findViewById(R.id.subleased_nights_layout);

        fab = findViewById(R.id.fab);

        progress_layout = findViewById(R.id.progress_layout);

        backgroundImage = findViewById(R.id.backgroundImage);

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        total_price_textView.setTypeface(tp);
        congratulations_description.setTypeface(tp);
        transaction_id_textView.setTypeface(tp);

        // initialise typeface and apply to all textViews in layout

    }

    private void setBackground(){

        SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        // retrieve background image from sharedpreferences

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        // convert image String object in Base64 format to bitmap object

        backgroundImage.setImageBitmap(image);

    }

    private void getInfoFromPreviousActivity(){

        UID = getIntent().getStringExtra("UID");
        AID = getIntent().getStringExtra("AID");
        bookingID = getIntent().getStringExtra("bookingID");

        // retrieve specified apartment ID, User ID and booking ID

    }

    private void initDB(){

        Firebase.setAndroidContext(this);

    }

    private void setDBlinks(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.USERS_TABLE_URL_REFERENCE
                        + UID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID);

        // DB link to specified booking for guest

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK
                        + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE
                        + AID
                        + ConstantsDB.BOOKINGS_TABLE_URL_REFERENCE
                        + "/"
                        + bookingID);

        // DB link to specified booking for host

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + AID);

        // DB link specified apartment

    }
    private void retrieveApartmentInfo(DataSnapshot dataSnapshot){

        hostUID = dataSnapshot.child(ConstantsDB.UID_TABLE).getValue(String.class);

        locationName = dataSnapshot.child(ConstantsDB.APARTMENT_CITY_VILLAGE_TABLE).getValue(String.class);

        transaction_id = dataSnapshot.child(
                ConstantsDB.BOOKINGS_TABLE)
                .child(bookingID)
                .child(ConstantsDB.BOOKING_TRANSACTION_ID_TABLE)
                .getValue(String.class);

        // retrieve apartment info

    }

    private void retrieveApartmentInfoAndSetTextFieldData(){

        fbRef3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                retrieveApartmentInfo(dataSnapshot);

                final DatabaseReference fbRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE + hostUID);

                // DB link to user table of host

                fbRef4.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                        PaymentFinishedActivityMethods.setDialogText(
                                getApplicationContext(), transaction_id_textView, transaction_id, congratulations_description,
                                SAMPLE_IBAN, fab, locationName, total_price_textView);

                        // display payment information to user

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                try {

                    numDaysLeft = dataSnapshot.child(ConstantsDB.APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE).getValue(String.class);

                } catch (Exception e){

                    // Do nothing

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
