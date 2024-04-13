package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.pager_adapaters.ApartementProfilePagerAdapter;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;

public class AccountAndApartementActivity extends AppCompatActivity {

    public static TabLayout tablayout = null;
    // Variable made static so it can be controlled from child fragments, for animation purposes

    // Variable made static so it can be controlled from child fragments, for animation purposes

    private LinearLayout linearLayout;

    // initialisation java variables used throughout the file

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_and_apartement);

        linearLayout = findViewById(R.id.linearLayout);

        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        tablayout = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.viewpager);

        // connect java variables with xml layout views

        initialiseTabLayout(getApplicationContext(), tablayout);

        // initialise activity tabLayout
        
        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        for (int i = 0; i < tablayout.getTabCount(); i++) {
            @SuppressLint("InflateParams")
            TextView tv = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout,null);
            tv.setTypeface(tp);
            tablayout.getTabAt(i).setCustomView(tv);
        }

        // get typeface and apply it to every instance of a created textView. created textViews display unavailable booking dates

        SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
        String bitmap = prefs.getString("string", null);

        Bitmap image = StartupMethods.StringToBitMap(bitmap);

        backgroundImage.setImageBitmap(image);

        // get background image of city apartment is located in and set it to background of Activity

        final ApartementProfilePagerAdapter adapter = new ApartementProfilePagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        adapter.AddFragment(new ProfileActivity(), getResources().getString(R.string.profile));
        adapter.AddFragment(new ApartementActivity(), getResources().getString(R.string.apartement));

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        //initialise viewPager

        String fromActivity = getIntent().getStringExtra("fromActivity");

        // assignment returns string

        switch (fromActivity) {

            case "0":

                viewPager.setCurrentItem(0);

                break;
            case "1":

                viewPager.setCurrentItem(1);

                break;
            case "2":

                viewPager.setCurrentItem(1);

                break;
        }

        // 0 = ProfileEditActivity, 1 = ApartmentEditActivity, 2 = CityActivity (item click)

        // if user selected profile in StartupActivity, set pager to profile tab
        // if user selected apartment in CityActivity RecyclerView, set pager to apartement tab

        /*

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                // set page of viewpager to [age selected by user

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        */

        // more tabLayout initialisation

    }

    @Override
    protected void onStart() {
        super.onStart();

        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                animateActivityTransition();

            }
        });

        // start activity enter animation when layout is fully loaded

    }

    @Override
    protected void onRestart() {
        super.onRestart();

                // if returning from another activity, animate return activity
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);

        // disable onBackPressed standard activity transition animation

        }

        public void animateActivityTransition (){

            DisplayMetrics metrics = getResources().getDisplayMetrics();

            // get screen metrics.
            // using DisplayMetrics class ensures that animations look the same on all screen resolutions

            float tabLayout_org_pos_y = 0;
            ObjectAnimator anim = ObjectAnimator.ofFloat(tablayout, "translationY", -metrics.heightPixels - tabLayout_org_pos_y, tabLayout_org_pos_y);

            // ObjectAnimator object for tablayout layout. Object moves from north to south, starting point of layout is offscreen in northern coordinates

            anim.setDuration(400);

            // set animation duration

            anim.start();

            // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        }

    private void initialiseTabLayout (Context context, TabLayout tablayout){

        tablayout.addTab(tablayout.newTab().setText(context.getResources().getString(R.string.profile)));
        tablayout.addTab(tablayout.newTab().setText(context.getResources().getString(R.string.apartement)));

        // initialisation tabLayout. 'profile' on the left, 'apartment' right.

        tablayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tablayout.setTabTextColors(context.getResources().getColor(R.color.textColorTertiary), context.getResources().getColor(R.color.textColorTertiary));
        tablayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.textColorTertiary));

        // background and tab scroll colors are modified for design continuity

        setTabsOnClickListeners(tablayout);

    }

    private void setTabsOnClickListeners(TabLayout tablayout){

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
