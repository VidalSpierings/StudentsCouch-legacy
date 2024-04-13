package com.studentscouch.projectbostonfiles.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.studentscouch.projectbostonfiles.R;

public class SplashScreen extends AppCompatActivity {

    private boolean isAccountRegistered;
    private Intent i;
    private ImageView header_image;

    String launchType = "AppActive";

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Set Splashscreen duration for 1000 milliseconds

        SharedPreferences prefs = getSharedPreferences("isAccountRegistered", MODE_PRIVATE);

            isAccountRegistered = prefs.getBoolean("registered", false);


        Handler handler = new Handler();

        try {

            handler.postDelayed(() -> {

                if (!isAccountRegistered){

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            header_image = findViewById(R.id.header_image);

                            startNextActivityWithAnim();

                        } else {

                            startNextActivityWithoutAnim();

                        }

                    }

                if (isAccountRegistered){

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            header_image = findViewById(R.id.header_image);

                            startNextActivityWithAnim();

                        } else {

                            startNextActivityWithoutAnim();

                        }

                        // if users' device version is lower than Lollipop, do not animate scene transition animation

                    }

                // different choices for what has to happen if user has or hasn't already registered,
                // as of now these choices both have the exact same outcome


            },
                    1000);

            launchType = "AppActive";

        } catch (Exception e) {

            launchType = "AppInActive";

            // unknown / unused function

        }

    }

    private void startNextActivityWithAnim(){

        ActivityOptionsCompat options;

        options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                SplashScreen.this, header_image, getResources().getString(R.string.sharedactivitytransition_splashscreen_startupactivity));

        i = new Intent(SplashScreen.this, MainActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(i, options.toBundle());

    }

    private void startNextActivityWithoutAnim(){

        i = new Intent(SplashScreen.this, MainActivity.class);

        startActivity(i);

    }

}
