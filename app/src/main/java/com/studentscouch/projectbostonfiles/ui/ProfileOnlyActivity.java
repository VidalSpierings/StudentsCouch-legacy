package com.studentscouch.projectbostonfiles.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.app_back_end.ProfileOnlyActivityMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.Calendar;
import java.util.Objects;


public class ProfileOnlyActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    static TextView
            name_textView,
            age_textView,
            rating_left_textView,
            rating_right_textView,
            your_account_textView,
            emoji_left_textView,
            emoji_right_textView,
            add_apartement_details_textView,
            report_a_bug_textView;

    private View
            message_layout_view,
            edit_layout_view;

    @SuppressLint("StaticFieldLeak")
    static ImageView
            profile_imageView,
            gender_imageView,
            backgroundImage;

    @SuppressLint("StaticFieldLeak")
    static FrameLayout
            message_layout,
            edit_layout,
            add_apartement_details_layout,
            report_a_bug_layout;

    private FrameLayout progress_layout;

    FrameLayout profile_image;

    private CharSequence[] items;

    private int
            REQUEST_CAMERA = 2392,
            REQUEST_GALLERY = 4930;

    @SuppressLint("StaticFieldLeak")
    static ScrollView scrollView;

    static float scrollView_org_pos_y;

    private String fromActivity;

    boolean firstVisit;

    static String
            UID = null,
            firstName = null,
            lastName = null,
            gender = null;

    static int
            rating_happy_emoji = 0,
            rating_unhappy_emoji = 0;

    static DatabaseReference
            fbRef = null;

    static DatabaseReference fbRef3 = null;

    private Animation exit_anim_progress_layout;

    static int
            year,
            month,
            day;

    static String image;

    static int MY_PERMISSION_RESPONSE;

    private ObjectAnimator anim2;

    private Intent intent;

    private ContentValues values;

    private Bitmap bitmap;

    static boolean isHost = false;

    static String
            AID,
            locationID;

    private AlertDialog.Builder builder;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_only);

        linkXMLElementsToJavaVariables();

        setTypeFaces();

        setBackgroundImage();

        setEmojisText();

        assignProfileImageSameWidthHeight();

        addUiElementsIfUserViewingOwnApartment();

        initMiscVars();

        getDataFromPreviousActivity();

        makeProgressLayoutAppear();

        initDB();

        getDataFromDBFunc();

        retrieveApartmentLocationID();

        add_apartement_details_layout.setOnClickListener(view -> {

            ProfileOnlyActivityMethods.initialiseAddApartmentDetailsButtonFunctionality(
                    getApplicationContext(), ProfileOnlyActivity.this);

            // initialise add apartment details button onClickListener

        });

        report_a_bug_layout.setOnClickListener(view -> {

            StudentsCouchAnimations.animateActivityExitTransition2ProfileOnlyActivity(
                    getApplicationContext(), scrollView, ProfileOnlyActivity.this,
                    scrollView_org_pos_y);

            // animate activity transition and open ReportABugActivity

        });

        message_layout.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                StartupMethods.startCircularRevealAnimationDefaultStartRadius(
                        getApplicationContext(), message_layout_view, false);

                StudentsCouchAnimations.animateActivityExitTransition2ProfileOnlyActivity(
                        getApplicationContext(), scrollView, ProfileOnlyActivity.this,
                        scrollView_org_pos_y);

                // start activity exit transition animations
            }

            return true;
        });

        items = new CharSequence[]{"Camera", "Gallery"};

        edit_layout.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                editLayoutOnClickFunc();

            }

            return true;
        });


        ProfileOnlyActivityMethods.initialiseEditButtonOnClickListener(
                getApplicationContext(), edit_layout, edit_layout_view, ProfileOnlyActivity.this,
                message_layout_view, items, values, MY_PERMISSION_RESPONSE,
                REQUEST_CAMERA, REQUEST_GALLERY, intent);

        // initialise edit button functionality

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        edit_layout_view.setVisibility(View.INVISIBLE);

        // make edit button reveal view invisible

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "items/" + locationID + "/" + AID);

        int REQUEST_CODE_CROP_IMAGE = 8298;
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK){

            ProfileOnlyActivityMethods.retrieveImageFromCamera(
                    data, profile_imageView, fbRef, fbRef3);

            // retrieve image taken by user


        } else if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK){

            ProfileOnlyActivityMethods.retrieveImageFromGallery(
                    getApplicationContext(), data, profile_imageView,
                    fbRef, fbRef3);

            // retrieve image chosen by user from gallery

        } else if (requestCode == REQUEST_CODE_CROP_IMAGE && resultCode == Activity.RESULT_OK){

            profile_imageView.setImageBitmap(bitmap);

            // insert new image into imageView

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        StudentsCouchAnimations.animateActivityTransitionProfileOnlyActivity(
                getApplicationContext(), scrollView, scrollView_org_pos_y);

        // animate activity resume/enter transition animations

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, 0);

    }

    public static void retrieveUserInfo (
            Context context, FrameLayout progress_layout, Animation exit_anim_progress_layout, DataSnapshot dataSnapshot,
            FrameLayout add_apartement_details_layout, TextView add_apartement_details_textView,
            ImageView gender_imageView) {

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

        String hostStatus = dataSnapshot.child(ConstantsDB.APARTMENT_HOST_STATUS_TABLE).child(ConstantsDB.APARTMENT_STATUS_TABLE).getValue(String.class);

        // check users' hostatus (host, non-host or has to yet to fill in apartment details)

        if (hostStatus != null) {

            changeActivityFuncAccToHostStatus(context, dataSnapshot, hostStatus);

        }

        attemptRetrieveUserDataFunc(dataSnapshot);

    }

    public static void getDataFromDB(
            Context context, final FrameLayout progress_layout, final Animation exit_anim_progress_layout,
            final FrameLayout add_apartement_details_layout, final TextView add_apartement_details_textView,
            final ImageView gender_imageView,
            DatabaseReference fbRef, final String fromActivity,
            final TextView age_textView
    ) {

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                animateActivityEnterTransition(context, fromActivity);

                retrieveUserInfo(
                        context, progress_layout, exit_anim_progress_layout, dataSnapshot,
                        add_apartement_details_layout, add_apartement_details_textView,
                        gender_imageView);

                getAge(age_textView);

                Bitmap imageBitmap = StartupMethods.StringToBitMap(ProfileOnlyActivity.image);

                // convert base64 String image object to Bitmap object

                insertRetrievedInfoIntoTextViews(imageBitmap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    static String getAge(TextView age_textView){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(ProfileOnlyActivity.year, ProfileOnlyActivity.month, ProfileOnlyActivity.day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = age;
        String ageS = ageInt.toString();

        age_textView.setText(ageS);

        return ageS;
    }

    /*

    public static void initialiseAddApartmentDetailsButtonFunctionality (
            Context context, AppCompatActivity appCompatActivity
    ) {

        if (StartupMethods.isOnline(context)) {

            Intent i = new Intent(appCompatActivity, RegisterActivityYesScreen1.class);
            i.putExtra("isFromProfileActivity", 1);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(i);

            // open first apartment register activity

        } else {

            StartupMethods.openNewInternetConnectionLostDialog(appCompatActivity);

            // if internet connection could not be found, inform user by showing a toast

        }

    }

    */

    /*

    public void animateActivityExitTransitionProfileOnlyActivity(
            final Context context, ScrollView scrollView, Float scrollView_org_pos_y,
            final AppCompatActivity appCompatActivity){

        scrollView.setVisibility(View.INVISIBLE);

        initStartAnimatorObject(context);

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        initStartAnimatorOnAnimEndListener(context, appCompatActivity);

        anim3.start();

        scrollView.setVisibility(View.VISIBLE);

    }

    */

    private void linkXMLElementsToJavaVariables(){

        scrollView = findViewById(R.id.scrollView);

        message_layout_view = findViewById(R.id.message_layout_view);
        edit_layout_view = findViewById(R.id.edit_layout_view);

        your_account_textView = findViewById(R.id.your_account_textView);
        name_textView = findViewById(R.id.username_textView);
        age_textView = findViewById(R.id.age_textView);
        emoji_left_textView = findViewById(R.id.emoji_left_textView);
        rating_left_textView = findViewById(R.id.rating_left_textView);
        emoji_right_textView = findViewById(R.id.emoji_right_textView);
        rating_right_textView = findViewById(R.id.rating_right_textView);
        add_apartement_details_textView = findViewById(R.id.add_apartement_details_textView);
        report_a_bug_textView = findViewById(R.id.report_a_bug_textView);
        backgroundImage = findViewById(R.id.backgroundImage);

        profile_imageView = findViewById(R.id.profile_imageView);
        gender_imageView = findViewById(R.id.gender_imageView);

        profile_image = findViewById(R.id.profile_image);
        message_layout = findViewById(R.id.message_layout);
        edit_layout = findViewById(R.id.edit_layout);
        add_apartement_details_layout = findViewById(R.id.add_apartement_details_layout);
        report_a_bug_layout = findViewById(R.id.report_a_bug_layout);
        progress_layout = findViewById(R.id.progress_layout);

        // link java variables to layout views

    }

    /*

    public static String getAgeProfileOnlyActivity(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        int ageInt = age;

        return Integer.toString(ageInt);
    }

    */

    /*

    public static void retrieveImageFromCamera (Intent data, Bitmap bitmap) {

        attemptSetBitmap(data, bitmap);

        convertBitmap();

        submitNewProfileImageToDB();

    }

    */

    private void makeProgressLayoutAppear(){

        Animation enter_anim_progress_layout = AnimationUtils.loadAnimation(ProfileOnlyActivity.this, R.anim.visibility_fade_in_animation);

        enter_anim_progress_layout.setDuration(200);

        exit_anim_progress_layout = AnimationUtils.loadAnimation(ProfileOnlyActivity.this, R.anim.visibility_fade_out_animation);

        exit_anim_progress_layout.setDuration(200);

        progress_layout.startAnimation(enter_anim_progress_layout);

    }

    private void initDB(){

        Firebase.setAndroidContext(getApplicationContext().getApplicationContext());

    }

    private void addUiElementsIfUserViewingOwnApartment(){

        if (!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(UID)){

            your_account_textView.setVisibility(View.INVISIBLE);
            edit_layout.setVisibility(View.INVISIBLE);

            // if user isn't visiting their own profile page, make 'your account' textView and edit button invisible

        }

    }

    private void retrieveApartmentLocationID(){

        final DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + AID);

        // DB link to specified apartment

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                locationID = dataSnapshot.child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).getValue(String.class);

                // retrieve Place ID of apartment of user in profile

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setEmojisText(){

        emoji_left_textView.setText(StartupMethods.getEmojiByUnicode(0x1F641));
        emoji_right_textView.setText(StartupMethods.getEmojiByUnicode(0x1F60A));

        // set correct emojis to textViews

    }

    private void setBackgroundImage(){

        try {

            SharedPreferences prefs2 = getSharedPreferences("background_image", MODE_PRIVATE);
            String bitmap = prefs2.getString("string", null);

            if (bitmap != null) {

                Bitmap image = StartupMethods.StringToBitMap(bitmap);

                backgroundImage.setImageBitmap(image);

            }

            if (bitmap == null) {

                backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.amsterdam_png));

            }

            // set background image of last displayed city

        } catch (Exception e){

            // if no image is found, set background of city of amsterdam

        }

    }

    private void setTypeFaces(){

        Typeface tp = Typeface.createFromAsset(getApplication().getAssets(), "project_boston_typeface.ttf");

        your_account_textView.setTypeface(tp);
        name_textView.setTypeface(tp);
        age_textView.setTypeface(tp);
        rating_left_textView.setTypeface(tp);
        rating_right_textView.setTypeface(tp);
        add_apartement_details_textView.setTypeface(tp);
        report_a_bug_textView.setTypeface(tp);

        // initialise and apply typeface to all textViews in layout

    }

    private void getDataFromPreviousActivity(){

        fromActivity = getIntent().getStringExtra("fromActivity");

        UID = getIntent().getStringExtra("UID");

    }

    private void assignProfileImageSameWidthHeight(){

        profile_imageView.getLayoutParams().height = profile_imageView.getWidth();

    }

    private void initMiscVars(){

        firstVisit = true;

        scrollView_org_pos_y = scrollView.getY();

    }

    private void getDataFromDBFunc(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE + UID);

        // create firebase link

        getDataFromDB(
                getApplicationContext(), progress_layout, exit_anim_progress_layout,
                add_apartement_details_layout, add_apartement_details_textView,
                gender_imageView,
                fbRef, fromActivity,
                age_textView
        );

    }

    private void editLayoutOnClickFunc(){

        StartupMethods.startCircularRevealAnimationDefaultStartRadius(
                getApplicationContext(), edit_layout_view, false);

        // start circular reveal animation for edit button reveal view

        createBuilder();

        setDialogOnCancelListener();

        initDialogFunc();

        builder.show();

        // EDIT ACTIVITY IMAGE NOT LOADING/REMOVE/ADD IMAGES, ADDING BOOKING DATES TO APARTMENT NOT WORKING

    }

    private void createBuilder(){

        builder = new AlertDialog.Builder(ProfileOnlyActivity.this);
        builder.setTitle(getApplication().getResources().getString(R.string.replace_profile_image));

    }

    private void setDialogOnCancelListener(){

        builder.setOnCancelListener(dialogInterface -> {

            edit_layout_view.setVisibility(View.INVISIBLE);
            message_layout_view.setVisibility(View.INVISIBLE);

            // remove button click views upon dialog cancelled

        });

    }

    private void initDialogFunc(){

        builder.setItems(items, (dialogInterface, i) -> {

            if (items[i].equals("Camera")) {

                ProfileOnlyActivityMethods.startActivityForResultCamera(
                        getApplicationContext(), ProfileOnlyActivity.this, MY_PERMISSION_RESPONSE,
                        REQUEST_CAMERA);

                // initialise camera item onClickListener

            } else if (items[i].equals("Gallery")) {

                ProfileOnlyActivityMethods.startActivityForResultGallery(
                        getApplicationContext(), ProfileOnlyActivity.this, REQUEST_GALLERY);

            }
        });

    }

    static void changeActivityFuncAccToHostStatus(Context context, DataSnapshot dataSnapshot, String hostStatus){

        if (hostStatus.equals(ConstantsDB.HOST_STATUS_USER_IS_HOST)){

            ProfileOnlyActivity.isHost = true;

            ProfileOnlyActivity.AID = dataSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            // if user is an host, inform activity about this and retrieve users' apartment ID

        } else if (hostStatus.equals(ConstantsDB.HOST_STATUS_INFO_NOT_ADDED)) {

            add_apartement_details_layout.setVisibility(View.VISIBLE);

            ProfileOnlyActivity.isHost = false;

            // if user has not yet added apartment info, make activity display users' info as a non-host and make 'add apartment details' button visible

        } else {

            add_apartement_details_textView.setText(context.getResources().getString(R.string.become_host));

            add_apartement_details_layout.setVisibility(View.VISIBLE);

            ProfileOnlyActivity.isHost = false;

            // if user is a non-host, inform application about this. Make 'add apartment details' button visible and change text in textView inside to 'Become a host'

        }

    }

    static void attemptRetrieveFirstName(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.firstName = dataSnapshot.child(ConstantsDB.USER_FIRST_NAME_TABLE).getValue(String.class);

            // retrieve first name of user in profile

            if (Objects.requireNonNull(ProfileOnlyActivity.firstName).equals("") || ProfileOnlyActivity.firstName.equals(ConstantsDB.NULL)){

                ProfileOnlyActivity.firstName = ConstantsDB.ERROR;

                // if first name of user in profile could not be retrieved, set first name as 'ERROR'

            }

        } catch (Exception e){

            ProfileOnlyActivity.firstName = ConstantsDB.ERROR;

            // if first name of user in profile could not be retrieved, set first name as 'ERROR'

        }

    }

    static void attemptRetrieveLastName(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.lastName = dataSnapshot.child(ConstantsDB.USER_LAST_NAME_TABLE).getValue(String.class);

            // attempt to retrieve last name of user in profile

            if (Objects.requireNonNull(ProfileOnlyActivity.lastName).equals("") || ProfileOnlyActivity.lastName.equals(ConstantsDB.NULL)){

                ProfileOnlyActivity.lastName = ConstantsDB.ERROR;

                // if last name couldn't be retrieved, set last name as 'Error'

            }

        } catch (Exception e){

            ProfileOnlyActivity.lastName = ConstantsDB.ERROR;

            // if last name couldn't be retrieved, set last name as 'Error'

        }

    }

    static void attemptRetrieveNumHappyEmojiRatings(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.rating_happy_emoji = dataSnapshot.child(
                    ConstantsDB.USER_EMOJI_RATING_TABLE).child(ConstantsDB.USER_HAPPY_EMOJIS_RATING_TABLE).getValue(Integer.class);

            // attempt to retrieve number of happy emojis

        } catch (Exception e){

            ProfileOnlyActivity.rating_happy_emoji = 0;

            // if number of happy emojis could not be retrieved, set number of happy emojis to 0

        }

    }

    static void attemptRetrieveNumUnhappyEmojiRatings(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.rating_unhappy_emoji = dataSnapshot.child(
                    ConstantsDB.USER_EMOJI_RATING_TABLE).child(ConstantsDB.USER_UNHAPPY_EMOJIS_RATING_TABLE).getValue(Integer.class);

            // attempt to retrieve number of unhappy emojis

        } catch (Exception e){

            ProfileOnlyActivity.rating_unhappy_emoji = 0;

            // if number of unhappy emojis could not be retrieved

        }

    }

    static void attemptRetrieveSetGender(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.gender = dataSnapshot.child(ConstantsDB.USER_GENDER_TABLE).getValue(String.class);

            // attempt to retrieve gender of user in profile

            if (Objects.requireNonNull(ProfileOnlyActivity.gender).equals(ConstantsDB.GENDER_MALE)){

                gender_imageView.setImageResource(R.drawable.gender_male);

                // if users' gender is male, set male gender icon

            } else if (ProfileOnlyActivity.gender.equals(ConstantsDB.GENDER_FEMALE)){

                gender_imageView.setImageResource(R.drawable.gender_female);

                // if users' gender is female, set female gender icon

            } else {

                gender_imageView.setImageResource(R.drawable.gender_female);

                // if somehow a different set of strings is retrieved, set female gender icon

            }

        }catch (Exception e){

            gender_imageView.setImageResource(R.drawable.gender_female);

            // if users' gender could not be retrieved, set user gender as female

        }

    }

    static void attemptRetrieveUserBirthYear(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.year = dataSnapshot.child(ConstantsDB.USER_BIRTHDATE_YEAR_TABLE).getValue(Integer.class);

            // attempt to retrieve birth year of user in profile

            if (ProfileOnlyActivity.year == 0){

                ProfileOnlyActivity.year = 1998;

                // if users' year of birth could not be retrieved, set birth year at 1998

            }

        } catch (Exception e){

            ProfileOnlyActivity.year = 1998;

            // if users' year of birth could not be retrieved, set birth year at 1998

        }

    }

    static void attemptRetrieveBirthMonth(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.month = dataSnapshot.child(ConstantsDB.USER_BIRTHDATE_MONTH_TABLE).getValue(Integer.class);

            // attempt to retrieve birth month of user in profile

            if (ProfileOnlyActivity.month == 0){

                ProfileOnlyActivity.month = 4;

                // if users' birth month could not be retrieved, set birth month at 4

            }

        } catch (Exception e){

            ProfileOnlyActivity.month = 4;

            // if users' birth month could not be retrieved, set birth month at 4

        }

    }

    static void attemptRetrieveDayOfBirth(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.day = dataSnapshot.child(ConstantsDB.USER_BIRTHDATE_DAY_TABLE).getValue(Integer.class);

            // attempt to retrieve day of birth for user in profile

            if (ProfileOnlyActivity.day == 0){

                ProfileOnlyActivity.day = 3;

                // if day of birth could not be retrieved, set day of birth at 3

            }

        } catch (Exception e) {

            ProfileOnlyActivity.day = 3;

            // if day of birth could not be retrieved, set day of birth at 3

        }

    }

    static void attemptRetrieveProfileImage(DataSnapshot dataSnapshot){

        try {

            ProfileOnlyActivity.image = dataSnapshot.child(ConstantsDB.USER_PROFILE_IMAGE_TABLE).getValue(String.class);

            // attempt to retrieve profile picture (in Base64 String format) for user in profile

        } catch (Exception e){

            ProfileOnlyActivity.image = null;

            // if users' profile image could not be retrieved, make image String object empty

        }

    }

    static void attemptRetrieveUserDataFunc(DataSnapshot dataSnapshot){

        attemptRetrieveFirstName(dataSnapshot);

        attemptRetrieveLastName(dataSnapshot);

        attemptRetrieveNumHappyEmojiRatings(dataSnapshot);

        attemptRetrieveNumUnhappyEmojiRatings(dataSnapshot);

        attemptRetrieveSetGender(dataSnapshot);

        attemptRetrieveUserBirthDate(dataSnapshot);

        attemptRetrieveProfileImage(dataSnapshot);

    }

    static void attemptRetrieveUserBirthDate(DataSnapshot dataSnapshot){

        attemptRetrieveUserBirthYear(dataSnapshot);

        attemptRetrieveBirthMonth(dataSnapshot);

        attemptRetrieveDayOfBirth(dataSnapshot);

    }

    @SuppressLint("SetTextI18n")
    static void insertRetrievedInfoIntoTextViews(Bitmap imageBitmap){

        name_textView.setText(ProfileOnlyActivity.firstName + " " + ProfileOnlyActivity.lastName);
        rating_right_textView.setText(String.valueOf(ProfileOnlyActivity.rating_happy_emoji));
        rating_left_textView.setText(String.valueOf(ProfileOnlyActivity.rating_unhappy_emoji));

        profile_imageView.setImageBitmap(imageBitmap);

        // set retrieved full name, ratings and profile picture of user in profile

    }

    static void animateActivityEnterTransition(Context context, String fromActivity){

        if (fromActivity.equals("0")){

            // if user doesn't come from ApartmentEditActivity, start activity transition animations

            StudentsCouchAnimations.animateActivityTransitionProfileOnlyActivity(
                    context, scrollView, scrollView_org_pos_y);

            // animate activity enter transition animations

        }

    }

    /*

    private void initStartAnimatorObject(Context context){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        anim3 = ObjectAnimator.ofFloat(scrollView, "translationY", scrollView_org_pos_y, metrics.heightPixels - scrollView_org_pos_y);
        anim3.setDuration(400);

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions
    }

    */

    /*

    private void initStartAnimatorOnAnimEndListener(Context context, AppCompatActivity appCompatActivity){

        anim3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                Intent intent = new Intent(appCompatActivity, MessagingActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);

                // start messaging activity when animation finished

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    */

    /*

    static void attemptSetBitmap(Intent data, Bitmap bitmap){

        try {

            bitmap6 = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            profile_imageView.setImageBitmap(bitmap);

            // retrieve image and insert into profile imageView

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    */

    /*

    static void convertBitmap(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap6.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        // convert Bitmap object to byte array object

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // convert byte array object to String object

    }

    */
    /*

    static void submitNewProfileImageToDB(){

        try {

            fbRef.child(ConstantsDB.USER_PROFILE_IMAGE_TABLE).setValue(encoded);
            fbRef3.child(ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).setValue(encoded);

            // set new profile image to user and apartment items database subtables

        } catch (Exception e){

            // Do nothing

        }

    }

    */

    /*

    static void retrieveConvertBitmap(Context context, Intent data){

        Bitmap bitmap = null;

        Uri selectedImage1 = data.getData();

        // retrieve Uri path of image chosen by user

        try {

            bitmap = StartupMethods.decodeUri(context, selectedImage1, 100);

            // convert Uri object to Bitmap object

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    */

}
