package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.dbImplementers.CityActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.CityActivityDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.CityActivityModel;
import com.studentscouch.projectbostonfiles.observables.CityActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.CityActivityView;
import com.studentscouch.projectbostonfiles.view_adapters.CityActivityInformation;
import com.studentscouch.projectbostonfiles.view_adapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class CityActivityViewImplementer implements CityActivityView {

    private ImageView backgroundImage;

    private RecyclerView recyclerView;

    private FrameLayout progress_layout;

    private FrameLayout no_listings_frameLayout;

    private float recyclerView_org_pos_y;

    private View rootView;

    private CityActivityObservables observables;

    private CityActivityModel model;

    public CityActivityViewImplementer(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_city, viewGroup);

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        final TextView no_listings_textView = rootView.findViewById(R.id.no_listings_textView);

        no_listings_frameLayout = rootView.findViewById(R.id.no_listings_frameLayout);

        backgroundImage = rootView.findViewById(R.id.backgroundImage);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setVisibility(View.INVISIBLE);

        progress_layout = rootView.findViewById(R.id.progress_layout);

        // connect Java variables to xml layout views

        Typeface tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

        no_listings_textView.setTypeface(tp);

        // initialise typeface and set it to the no_listings_textView textView

    }

    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        initActivity();

        makeProgressLayoutDissappear(appCompatActivity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appCompatActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            appCompatActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        recyclerView_org_pos_y = recyclerView.getY();

        // save current position of relativeLayout to local variable

        addRecyclerViewItemDivider(context);

        // add completely transparent Item decorater view inbetween every item

        setCityImages(appCompatActivity, context);

        initDB(appCompatActivity, context);

        updateScreenLoadingFinished(context, appCompatActivity);

    }

    @Override
    public void initDB(AppCompatActivity appCompatActivity, Context context) {

        retrieveAllApartmentItems(appCompatActivity, context);

        // set status bar color if device version is equal to lollipop or above

    }

    private void makeProgressLayoutDissappear(AppCompatActivity appCompatActivity){

        Animation enter_anim_progress_layout = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_in_animation);

        enter_anim_progress_layout.setDuration(200);

        //  mGeoDataClient = Places.getGeoDataClient(CityActivity.this, null);

        MyApplication.makeProgressLayoutAppearFrameLayout(progress_layout, enter_anim_progress_layout);

    }

    private void initActivity(){

        observables = new CityActivityObservables();

        model = new CityActivityModel(observables);

    }

    private void setCityImages(AppCompatActivity appCompatActivity, Context context){

        Intent intent = appCompatActivity.getIntent();

        if (intent != null) {

            getDataFromPreviousActivity(appCompatActivity, intent);

            model.setHeaderImage(context, appCompatActivity);

            model.setActivityBackgroundFunc(context, appCompatActivity, backgroundImage);

        }

    }

    private void getDataFromPreviousActivity(AppCompatActivity appCompatActivity, Intent intent){

        observables.setCountryName(appCompatActivity.getIntent().getStringExtra("country"));
        observables.setPlaceID(appCompatActivity.getIntent().getStringExtra("placeID"));
        observables.setCityName(intent.getStringExtra("cityName"));

    }

    private void addRecyclerViewItemDivider(Context context){

        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);

        itemDecorator.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_lowres));

        // add transparent item divider

        recyclerView.addItemDecoration(itemDecorator);

    }

    @Override
    public void retrieveAllApartmentItems(AppCompatActivity appCompatActivity, Context context) {

        CityActivityDBInterface db = new CityActivityDBImplementer(this, observables);

        db.retrieveApartmentItems(context, appCompatActivity, no_listings_frameLayout, recyclerView);

    }

    @Override
    public void animateActivityExitTransition(Context context, Activity activity) {

        StudentsCouchAnimations anims = new StudentsCouchAnimations(activity);

        anims.animateActivityExitTransitionCityActivity(context, recyclerView, recyclerView_org_pos_y);

    }

    private void updateScreenLoadingFinished(
            Context context, AppCompatActivity appCompatActivity) {

        Animation exit_anim_progress_layout = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        exit_anim_progress_layout.setDuration(200);

        final Float finalRecyclerview_org_pos_y = (float) 0;
        exit_anim_progress_layout.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                StudentsCouchAnimations.animateActivityTransitionCityActivity(context, recyclerView, finalRecyclerview_org_pos_y);

                // when loading screen disappear animation is done loading, animate activity enter animations

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //recyclerView.setVisibility(View.VISIBLE);

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

        // make loading screen disappear

    }

    @Override
    public void makeProgressLayoutInvisibleIfLoadingFinished(){

        if (observables.isLoaded()){

            progress_layout.setVisibility(View.INVISIBLE);

            // hide loading screen when all data is loaded

        }

    }

    @Override
    public void animateActivityReEnterTransition(Context context, Activity activity) {

        StudentsCouchAnimations anims = new StudentsCouchAnimations(activity);

        anims.animateCityActivityReEnterTransition(context, recyclerView, recyclerView_org_pos_y);

    }

    @Override
    public void makeBackgroundUIinvisible() {

        no_listings_frameLayout.setVisibility(View.INVISIBLE);

        recyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setRecyclerViewAdapter(Context context, AppCompatActivity appCompatActivity, ArrayList<CityActivityInformation> information) {

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, appCompatActivity, information, observables, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

    }

    @Override
    public Drawable getBackGroundDrawable() {

        return backgroundImage.getDrawable();

    }

    @Override
    public void animateActivityExitTransition(Context context, AppCompatActivity appCompatActivity, Intent intent) {

        animateActivityExitTransitionCityActivity(context, appCompatActivity, intent);

    }

    private void animateActivityExitTransitionCityActivity(Context context, AppCompatActivity appCompatActivity, Intent intent){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(recyclerView, "translationY", recyclerView_org_pos_y,  metrics.heightPixels - recyclerView_org_pos_y);
        anim.setStartDelay(125);
        anim.setDuration(400);

        anim.start();

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                recyclerView.setVisibility(View.INVISIBLE);

                appCompatActivity.startActivity(intent);

                // set item view of apartments invisible and start AccountAndApartmentActivity
                // with corresponding apartment data

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        anim.addListener(animatorListener);


    }

}
