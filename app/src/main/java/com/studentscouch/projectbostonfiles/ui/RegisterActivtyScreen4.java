package com.studentscouch.projectbostonfiles.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityScreen4Methods;

public class RegisterActivtyScreen4 extends AppCompatActivity {

    private ImageView partyImageView;

    private TextView
            additional_info_textView,
            subtitle_textView,
            app_not_unlocked_textView;

    TextView error_message;

    private Animation
            enter_anim = null,
            enter_anim2 = null,
            enter_anim3 = null,
            enter_anim_layout,
            exit_anim_layout;

    private FloatingActionButton fab;

    private View invisible_view;

    int i = 0;

    private FrameLayout progress_layout;

    FirebaseAuth fbAuth;

    DatabaseReference dbRef;

    String numUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register_activty_screen4);

        linkXmlElementsToJavaVairables();

        setTypefaces();

        initDB();

        initUI();

        numUsers = "...";

        initAnims();

        startPrgsLayoutWhenPrgsLayoutLoaded();

        changeAppFuncAccToIsUnlockedStat();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);

        finishAffinity();

    }

    private void linkXmlElementsToJavaVairables(){

        additional_info_textView = findViewById(R.id.additional_info_textView);
        subtitle_textView = findViewById(R.id.subTitle_textView);
        app_not_unlocked_textView = findViewById(R.id.app_not_unlocked_textView);
        error_message = findViewById(R.id.error_message);

        progress_layout = findViewById(R.id.progress_layout);

        fab = findViewById(R.id.fab);

        partyImageView = findViewById(R.id.partyImageView);

        invisible_view = findViewById(R.id.invisible_view);

    }

    private void setTypefaces(){

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        additional_info_textView.setTypeface(tp);
        subtitle_textView.setTypeface(tp);
        app_not_unlocked_textView.setTypeface(tp);

    }

    private void initDB(){

        fbAuth = FirebaseAuth.getInstance();

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();

        Firebase.setAndroidContext(this);

    }

    private void initUI(){

        additional_info_textView.setVisibility(View.INVISIBLE);
        partyImageView.setVisibility(View.INVISIBLE);
        subtitle_textView.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);

        // link java variables to xml layout views

        partyImageView.setImageResource(R.drawable.party);

    }

    private void initAnims(){

        enter_anim_layout =  AnimationUtils.loadAnimation(RegisterActivtyScreen4.this, R.anim.visibility_fade_in_animation);
        exit_anim_layout = AnimationUtils.loadAnimation(RegisterActivtyScreen4.this, R.anim.visibility_fade_out_animation);

        // load fade in and fade out animations

        enter_anim_layout.setDuration(200);
        exit_anim_layout.setDuration(200);

        // set duration for animations at 400 milliseconds

    }

    private void startPrgsLayoutWhenPrgsLayoutLoaded(){

        progress_layout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            progress_layout.startAnimation(enter_anim_layout);

            // start loading animation when layout is fully loaded

        });

    }

    private void loadAnims(){

        enter_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_in_animation);
        enter_anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_in_animation);
        enter_anim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_in_animation);

    }

    private void changeAppFuncAccToIsUnlockedStat(){

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "isAppUnlocked/isUnlocked");

        // DB link to info on whether or not platform is unlocked

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                loadAnims();

                boolean isUnlocked = (boolean) dataSnapshot.getValue();

                RegisterActivityScreen4Methods.checkIfUnlocked(
                        getApplicationContext(), progress_layout, exit_anim_layout, enter_anim,
                        partyImageView, subtitle_textView, additional_info_textView,
                        invisible_view, fab, isUnlocked, app_not_unlocked_textView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
