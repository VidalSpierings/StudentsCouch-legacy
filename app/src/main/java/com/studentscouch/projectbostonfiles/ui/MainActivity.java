package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.app_back_end.MainActivityMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private TextView
            log_in_textView,
            register_textView,
            pb_textView,
            bug_textView2,
            bug_textView3;

    private FloatingActionButton fab;

    private View header_image;

   // LoginButton facebook_login_registration;

    private List<View> viewsInLayout;

    private ObjectAnimator
            anim,
            anim2,
            anim3;

    private Animation
            enter_anim = null,
            enter_anim_progress_layout,
            exit_anim_progress_layout;

    private FrameLayout
            log_in_layout,
            register_layout;

    private View
            register_layout_view,
            log_in_layout_view;

    View screen_center;

    private View layout_center;

    private Float
            log_in_layout_orig_pos_x;
    private Float register_layout_orig_pos_x;

    private Intent i= null;

    private RelativeLayout relativeLayout2;

    private TextView error_textView;

    private Typeface tp;

    private RelativeLayout
            progress_layout,
            progress_layout2,
            progress_layout3;

    private int entry = 1;

    private RelativeLayout main_layout;

    private int isFromLogOut = 8372146;

    List<View> allAnimatingViews;

    List<Float> allAnimatingViewsOrgPositions;

    ViewTreeObserver vto;

    public MainActivity() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        initDB();

        getLayoutElementsOriginalXposition();

        initArrays();

        initAnims();

        checkIfAppNeedsUpdate();

        checkIfUserCameFromLogOutButton();

        //get element positions X coordinate for animation instructions

        fab.hide();

        // hide fab

        MainActivityMethods.initialiseFloatingActionButtonOnClickListener(
                fab, getApplicationContext(), progress_layout, MainActivity.this,
                register_layout, log_in_layout, log_in_layout_view, register_layout_view,
                i, pb_textView);

    }

    // initialise fab button. Clicking button resumes user to next activity

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {

        SharedPreferences preferences = getSharedPreferences("savedUserData", 0);
        SharedPreferences preferences2 = getSharedPreferences("RegisterActivityScreen1YesNo", 0);
        preferences.edit().clear().apply();
        preferences2.edit().clear().apply();

        error_textView.setText("");

        register_layout.setOnTouchListener((v, event) -> {

            MainActivityMethods.initialiseRegisterButton(
                    getApplicationContext(), fab, log_in_layout_view, register_layout_view,
                    progress_layout, error_textView, event);

            // initialise register button onClickListener

            return true;
        });

        // select 'register' option. When selected, fab appears that when clicked, proceeds user to registration activities

        log_in_layout.setOnTouchListener((v, event) -> {

            MainActivityMethods.initialiseLogInButton(
                    getApplicationContext(), event, fab, register_layout_view,
                    log_in_layout_view, progress_layout, error_textView);

            return true;

        });

        // select 'log in' option. When selected, fab appears that when clicked, proceeds user to log in activity

        fab.hide();

        fab.setClickable(true);

        log_in_layout_view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.main_background));
        register_layout_view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.main_background));

        // make reveal views invisible

        log_in_layout_view.setSelected(false);
        register_layout_view.setSelected(false);

        // deselect both register and log in options

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        fab.clearAnimation();
        fab.hide();

        // make fab invisible

        Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_out_animation);

        fade_out.setDuration(400);

        // load fade out animation and set fade out animation duration

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.IS_APP_UNLOCKED_URL_REFERENCE);

        // DB link to variable/info on whether or not app is unlocked
        // (app stays locked during the period in which enough users
        // have to be collected in order for the platofrm to launch)

        ref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                boolean state = (boolean) dataSnapshot.getValue();

                if (state){

                    // if platform is locked, do the following

                    fab.setOnClickListener(view -> {

                        MainActivityMethods.initialiseFabOnClickListenerAppLocked(
                                getApplicationContext(), MainActivity.this,
                                log_in_layout, register_layout, log_in_layout_view, error_textView,
                                register_layout_view, pb_textView,
                                tp);

                        // re-initialise fab onClickListener when platform is locked
                        // prevents user from clicking fab button when app locking
                        // is changed whilst user is present in this activity

                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReferenceFromUrl(
                StartupMethods.DATABASE_LINK + ConstantsDB.IS_APP_UNDER_CONSTRUCTION_URL_REFERENCE);

        ref2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                MainActivityMethods.checkIfUnderConstruction(dataSnapshot, progress_layout, progress_layout2, fab);

                // check if platform is under construction, if true, show screen informing user that app is under contruction

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }

    public void onRestart() {

        super.onRestart();

        enter_anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.visibility_fade_in_animation);

        StudentsCouchAnimations.onRestartAnimations1(
                getApplicationContext(), log_in_layout, register_layout, log_in_layout_orig_pos_x,
                register_layout_orig_pos_x, null, null, pb_textView);

    }

    @Override
    public void onBackPressed() {

        if ((anim == null || !anim.isRunning()) && (anim3 == null || !anim3.isRunning())) {

            this.finishAffinity();
            super.onBackPressed();

            // if onBackpressed is instantiated after animations are done running, quit application

        }
    }

    private void linkXmlElementsToJavaVariables(){

        fab = findViewById(R.id.fab);

        progress_layout = findViewById(R.id.progress_layout);

        log_in_textView = findViewById(R.id.log_in_textView);
        register_textView = findViewById(R.id.register_textView);
        pb_textView = findViewById(R.id.subTitle_textView);
        error_textView = findViewById(R.id.error_textView);
        bug_textView2 = findViewById(R.id.bug_textView2);
        bug_textView3 = findViewById(R.id.bug_textView3);

        header_image = findViewById(R.id.header_image);

        // facebook_login_registration = findViewById(R.id.facebook_login_registration);

        layout_center = findViewById(R.id.layout_center);

        log_in_layout =findViewById(R.id.log_in_layout);
        register_layout = findViewById(R.id.register_layout);

        relativeLayout2 = findViewById(R.id.relativeLayout);

        screen_center = findViewById(R.id.screen_center);

        register_layout_view = findViewById(R.id.register_layout_view);
        log_in_layout_view = findViewById(R.id.log_in_layout_view);

        progress_layout2 = findViewById(R.id.progress_layout2);

        progress_layout3 = findViewById(R.id.progress_layout3);

        main_layout = findViewById(R.id.main_layout);

        //link java variables to xml views


    }

    private void setTypefaces(){

        tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");
        log_in_textView.setTypeface(tp);
        register_textView.setTypeface(tp);
        pb_textView.setTypeface(tp);
        bug_textView2.setTypeface(tp);
        bug_textView3.setTypeface(tp);

        //set typefaces

    }

    private void getLayoutElementsOriginalXposition(){

        log_in_layout_orig_pos_x = log_in_layout.getX();
        register_layout_orig_pos_x = register_layout.getX();

        // get current coordinates at X axis of above views

    }

    private void initArrays(){

        initAllAnimatingViewsOrgPositionsArray();

        initAllAnimatingViews();

        initViewsInLayoutArray();

    }

    private void initViewsInLayoutArray(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(register_textView);
        viewsInLayout.add(log_in_textView);
        viewsInLayout.add(pb_textView);
        viewsInLayout.add(log_in_layout_view);
        viewsInLayout.add(register_layout_view);
        viewsInLayout.add(log_in_layout);
        viewsInLayout.add(register_layout);

        // put all views that need to be animated into an array list

    }

    private void initAllAnimatingViewsOrgPositionsArray(){

        allAnimatingViewsOrgPositions = new ArrayList<>();

        allAnimatingViewsOrgPositions.add(log_in_layout.getX());
        allAnimatingViewsOrgPositions.add(register_layout.getX());

    }

    private void initAllAnimatingViews(){

        allAnimatingViews = new ArrayList<>();

        allAnimatingViews.add(log_in_layout);
        allAnimatingViews.add(register_layout);

    }

    private void initAnims(){

        enter_anim_progress_layout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.visibility_fade_in_animation);
        exit_anim_progress_layout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.visibility_fade_out_animation);

        enter_anim_progress_layout.setDuration(200);
        exit_anim_progress_layout.setDuration(200);

        // load animations that ought to be used in activity

    }

    private void checkIfAppNeedsUpdate(){

        Firebase fbRef = new Firebase(StartupMethods.DATABASE_LINK + ConstantsDB.NEEDS_UPDATE_TABLE);

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MainActivityMethods.checkIfAppNeedsUpdate(
                        getApplicationContext(), log_in_layout, register_layout, fab,
                        progress_layout, exit_anim_progress_layout, dataSnapshot,
                        entry, progress_layout2, progress_layout3, main_layout);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void checkIfUserCameFromLogOutButton(){

        retrieveIfUserFromLogOutButton();

        if (isFromLogOut == 0){

            changeAppFuncUserFromLogOutButton();

        } else {

            animateActivityEnterTransition();

        }

    }

    private void retrieveIfUserFromLogOutButton(){

        try {

            isFromLogOut = getIntent().getIntExtra("isFromLogOut", 1298471);

        } catch (Exception e){

            // Do nothing

        }

        // check if user came to MainActivity from log out button in ProfileActivity.
        // Retrieved integer changes activity functionality accordingly

    }

    private void changeAppFuncUserFromLogOutButton(){

        StartupMethods.changeVisibility(viewsInLayout, View.VISIBLE);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.logged_out), Toast.LENGTH_SHORT).show();

        enter_anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.visibility_fade_in_animation);

    }

    private void animateActivityEnterTransition(){

        // if user didn't come to this activity by pressing the log out button in ProfileActivity, do the following

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        // change view visibility to correctly animate activity transition

        vto = relativeLayout2.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // if layout is fully loaded, do the following

                relativeLayout2.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                MainActivityMethods.animateActivityEnterTransitionFirstVisit(
                        getApplicationContext(), header_image, layout_center, enter_anim,
                        log_in_textView, register_textView, MainActivity.this, pb_textView,
                        log_in_layout, register_layout, viewsInLayout, progress_layout,
                        enter_anim_progress_layout, fab, error_textView, relativeLayout2,
                        tp, relativeLayout2);

                // animate activity transition animation

            }
        });

    }

    private void initDB(){

        Firebase.setAndroidContext(getApplicationContext());

    }

}

//API KEY IMPLEMENTATION AND HIDING!!!!!!
// hiding method: https://www.androidauthority.com/how-to-hide-your-api-key-in-android-600583/
// API key: AIzaSyCJk2pHgQqALp6QRvd1wBkjs4r2WUXyX7k

// text length scaling in buttons

//TODO notes:

//beveiliging API key!!!!!!!
//hide or change package name!!!!!!!!!!!! (also change package name in api developer console)
// dimen.xml fixen!!!!!!!
// voorkomen dat hosts waarschuwingssyteem gebruiken om meer geld te vragen van gasten en andersom!!!! ()

//TODO investors:

// picture crop to square and max file size
// Extra: tussen laatste en één na laatste activity algemene voorwaarden activity!!!!!!! (design alpha: aantal zinnen die waarschuwingssysteem en dagenlimiet uitleggen, onder gegevens in confirmactivity) (design: wat je in je hoofd hebt. Ook simpele link weergeven (bijvoorbeeld projectboston.com/termsofservice) Waarin algemene voorwaarden beschreven staan.)
// change sharedpreferences according to dialog listitem selected RegisterYesActivityScreen2!!!!!!!!!
// amount of days that can be booked don't match up
// https://github.com/delaroy/AndroidFirbaseChat/tree/master/app/src/main/java/com/delaroystudios/firebasechat

//TODO invisible:

// save all selected data in SharedPreferences!!!!!!!
// add developer comments!!!!!!

//TODO catches:

// if somehow different charcters aref filled in that are not allowed in edittext, error catch that says that an illegal charcters has been filled in at the given editText
//RegisterActivityYesScreen3 if apartement price starts with 0, show error message 'number cannot start with 0'
// catch for when placepicker intent is cancelled!!!!!!!!
// If statement for when placepicker returns "" string!!!!!!!!

//TODO beta version

// create error messages!!!!!!! / bij alle if switches else statement toevoegen om error catches te maken!!!!!!
//overganganimatie maand viewpager datumselectie!!!!!
// message received from new and from existing person notification
// custom calendar look for booking intent!!!!
// enable apartement images to change positions!!!!!!
// prevent onRestart from animating entering activity
// add password facebook registration
// facebook login
// enter animation: current layout dissapears immediately and new layout is initialised with enter animation. onRestart initisliases enter activity
// Gallery to view multiple pictures of apartement
// fix bug where, when no selected, blank space in scrollview ConfirmRegistrationActivity at bottom is left
// opgeslagen apartementen
// isPermissionGiven veld aanpassen naar eigen table onder /messages!!!!!!!
// notifications when values changed!!!!!!!!!!!!!
// meldingen inschakelen wanneer nieuwe youtube video geuploaded

// TODO Features

// Analytics
// Adyen implementation
// age increasing function
// crop images to square (use external libary https://github.com/biokys/cropimage)
// randomiseer algoritme die items netjes sorteert
// melding en vraag naar boeking van gast naar host. Host moet dit goedkeuren
// create different image sizes for profile image
// functie die aantal verhuurdagen vermindert wanneer boeking gedaan voor mensen in gemeente amsterdam
// instructions for people subletting in Amsterdam
// functie PersonMessagingActivity die herkent of beide users hosts zijn en vice versa
// op het einde nog credits scherm, wanneer debuggen bezig is!!!!!
// correct date datatype read write to server
// evoor zorgen dat niet twee dezelfde id's ontstaan!!!!
// auth wanneer database aanvragen doen en velden die alleen users van zichzelf kunnen lezen!!!!!
// values in dialog are not read correctly anymore

//TODO notifications

//When subletting limit is at 5, 2 and 1 days left

//TODO analytics

// num average new users per month
// num average new hosts per month
// num average new normal accounts per month
// num average bookings per month
// num average bookings per user per month
// num average nights stayed over per user
// num average money spent on booking per user
// num average money earned per booking for StudentsCouch
// num average money received per host
// average apartement price
// most populair city
// amount of revenue made by StudentsCouch per month
// amount of revenue made by StudentsCouch per month from one host
// amount of revenue made per month for one host
// how many percent of users hosts, how many percent normal users

//TODO After Alpha Release / ready for showing employer

// layout params take photo apartement buttons same as take profile photo button params
// round icon backgrounds for apartement images edit
// add log codes here and there, to show that you understand how it works
// app aanpassen op privacywet
// indien mogelijk, IP-Block voor mensen die 3 of meer waarschuwingen hebben ontvangen
// if bookings for current year are not available anymore, make bookings for next year already available
// if user selects specific location within city or village, autocomplete google places result for the city or village in question
// support for devices that dont support recyclerview
// favorite apartments save!!!!!! (favorite toggle button apartment activity)
// let user know in ui and not in dialog that internet connection not active!!!!!
// notification when booking accepted!!!!!
// messaging protocol!!!!!!!
// Kijken waar implementatie google analytics mogelijk is
// B&B registratie Amsterdam
// stay logged in when password remebered
// vertaling functie aan-en uit kunnen zetten voor wanneer support wallonië en andere frans sprekende gebieden
// slideshow apartment images
// bottom line view at paymentFinishedActivity
// bug in rating where fab appears when emoji selected
// indien mogelijk database link veranderen
// huidige datums en tijd op servers, applicatie hierop aanpassen met boekingen maken, warning system suspension, etc.
// notifications for booking accepted and declined
// onclick registration animation for listitems cityactivity
// implementation different currencies
//datumnotatie bookingaccepted amerikaans / nederlands / andere nationaliteiten

// BELANGRIJK: StudentsCouch open maken in alle landen, alle wetten per land opzoeken
// more polished privacy policy

// TODO next release

// mooie animaties toevoegen indien mogelijk

// Design dialog add details: launch activity with same layout as startupactivity, material activitytransition S logo

// TODO TESTEN

// change needs update in code and when finished ALSO IN DATABASE
// CustomDialogBookingAccepted2 change standard numbers to value retrieved from database
//verwijder dummy apartement den bosch

// VERWIJDER BLOKKADES MAINACTIVITY EN REGISTERACTIVITY4

// TODO GEBLEVEN

// ZIE BOVENSTAANDE NOTES

// TODO volgende fase

// testen of studentscouch werkt op oudere apparaten
// release date final register screen text aanpassen
// create separate subLeasedNightsLeft for the following 2 years (subleasedNightsLeft2019, 2020, 2021)
// make sure local variables use same data types as on firebase. I.E. two people allowed, but in app and in firebase, use boolean data Type

// TODO UPDATEN

// version codes veranderen
// update screen integer veranderen, in app en in servers
// codes veranderen die app open maken tijdens testfase
// app testen in debug zowel als in release variant
// check if new version of app has been succesfully committed to google servers
// verwijder apartement den bosch van platform
// remove functions that requires users' account to be deleted manually from the DB, create dialog where password has to be entered in

// check if adding, removing, replacing images post-registration is now working

        /*

                EMAIL DIMITRY GUY WANNEER MARKETINGCAMPAGNE BEGONNEN!!!!!!

        */

// creeer methods files for specifieke activities en sets of activities

// test method at line
// 601, ProfileActivity
// 401, BookingActivity2
// 1230, 1660, ConfirmRegistrationActivity
// 409, CustomDialogUserStayEnded
// 721, RegisterActivityScreen3
// 1049, RegisterActivityYesScreen2
// fix 2106, RegisterActivityYesScreen2
// 2186, RegisterActivityYesScreen2
// 557, RegisterActivityYesScreen2




//                                  <---------------------------- CODE FB IMPLEMENTATION ---------------------------->




// facebook_login_registration.setReadPermissions("public_profile", "email", "user_birthday", "user_gender");

        /*

        facebook_login_registration.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage(getResources().getString(R.string.retrieving_data));
                mDialog.show();

                String accesToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        Bundle fbData = getData(object);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email, birthday, first_name, last_name, gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (AccessToken.getCurrentAccessToken() != null){

            // for when user is already logged in

        }

        exit_anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.visibility_fade_out_animation);

        exit_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                facebook_login_registration.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        */



// ABOVE CODE OUGHT TO BE IMPLEMENTED AT SOME POINT IN THE FUTURE.
// When implementing facebook login button into application at present time, security warning is displayed in facebook login page.
// 'app can't be trusted because minor amount of users'. Implement when warning is not displayed anymore
