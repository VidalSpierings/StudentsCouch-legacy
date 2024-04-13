package com.studentscouch.projectbostonfiles.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.app_back_end.ProfileActivityMethods;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ProfileActivity extends Fragment {

    private TextView name_textView,
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

    private ImageView
            profile_imageView,
            gender_imageView;

    private FrameLayout
            progress_layout,
            message_layout,
            add_apartement_details_layout,
            edit_layout,
            report_a_bug_layout;

    private CharSequence[] items;

    private int
        REQUEST_CAMERA = 2392,
        REQUEST_GALLERY = 4930;

    private ScrollView scrollView;

    private float scrollView_org_pos_y;

    public static String fromActivity;

    public static String
            firstName = null,
            lastName = null;

    public static int
            rating_happy_emoji = 0,
            rating_unhappy_emoji = 0;

    private DatabaseReference
            fbRef = null,
            fbRef3 = null;

    public static String
            UID = null,
            gender = null,
            age = null;

    private ObjectAnimator anim2;

    private Animation exit_anim_progress_layout;

    private static int MY_PERMISSION_RESPONSE;

    private Intent intent;

    private ContentValues values;

    public static int
        year,
        month,
        day;

    public static String image;

    private Uri picUri = null;

    private Bitmap bitmap;

    public static boolean isHost = false;

    public static String
            AID,
            locationID;

    private TabLayout tabLayout;

    private float tabLayout_org_pos_y;

    public static String userAge = "";

    private String encoded;

    public ProfileActivity(){

    }

    //0x1F641
    //0x1F60A

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        initLayout(view);

        initFragmentFunc();

        add_apartement_details_layout.setOnClickListener(view1 -> addApartmentButtonClicked());

        report_a_bug_layout.setOnClickListener(view12 -> {

            StudentsCouchAnimations.animateActivityExitTransition2ProfileActivity(
                    Objects.requireNonNull(getContext()), scrollView, tabLayout, scrollView_org_pos_y,
                    tabLayout_org_pos_y, getActivity());

            // animate activity transition and launch ReportABugActivity

        });

        message_layout.setOnTouchListener((view13, motionEvent) -> {

            ProfileActivityMethods.initialiseMessageButtonOnClickListener(
                    getContext(), motionEvent, getActivity(), message_layout_view,
                    scrollView, scrollView_org_pos_y);

            return true;
        });

        items = new CharSequence[]{"Camera", "Gallery"};

        edit_layout.setOnTouchListener((view14, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                ProfileActivityMethods.initialiseEditButtonOnClickListenerFunctionality(
                        Objects.requireNonNull(getActivity()).getApplicationContext(), motionEvent, getActivity(), edit_layout_view,
                        message_layout_view, items, values, MY_PERMISSION_RESPONSE,
                        REQUEST_CAMERA, intent, REQUEST_GALLERY);

            }

            return true;
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        edit_layout_view.setVisibility(View.INVISIBLE);

        // make edit layout reveal view insvisible

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.ITEMS_TABLE_URL_REFERENCE + locationID + "/" + AID);

        int REQUEST_CODE_CROP_IMAGE = 8298;
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK){

            retrieveImageCamera(data);

        } else if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK){

            retrieveImageGallery(data);

        } else if (requestCode == REQUEST_CODE_CROP_IMAGE && resultCode == Activity.RESULT_OK){

            profile_imageView.setImageBitmap(bitmap);

            // set cropped image

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        message_layout_view.setVisibility(View.INVISIBLE);

        // make message layout reveal view invisible

    }

    @Override
    public void onResume() {
        super.onResume();

        animateActivityTransition();

    }

    // continue making activity transitions!!!!

    public void animateActivityTransition (){

        scrollView.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(scrollView, "translationY", metrics.heightPixels - scrollView_org_pos_y, scrollView_org_pos_y);
        anim.setDuration(400);

        // set animation duration at 400 milliseconds

        anim.start();

        // activity enter animation method. using DisplayMetrics class ensures that animations look the same on all screen resolutions

        scrollView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (picUri != null) {
            outState.putString("cameraImageUri", picUri.toString());
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey("cameraImageUri")) {
            picUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }

        // get profile picture upon activity having been created

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_RESPONSE){

            try {

                picUri = Objects.requireNonNull(getActivity()).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                // get Uri path of taken picture

                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);

                // pass Uri path of taken picture to next activity

                startActivityForResult(intent, REQUEST_CAMERA);

                // start 'capture new profile image with camera' Intent

            } catch (Exception e){

                // Do nothing

            }

        }

    }

    private void linkXMLElementsWithJavaVariables(View view){

        scrollView = view.findViewById(R.id.scrollView);

        message_layout_view = view.findViewById(R.id.message_layout_view);
        edit_layout_view = view.findViewById(R.id.edit_layout_view);

        your_account_textView = view.findViewById(R.id.your_account_textView);
        name_textView = view.findViewById(R.id.username_textView);
        age_textView = view.findViewById(R.id.age_textView);
        emoji_left_textView = view.findViewById(R.id.emoji_left_textView);
        rating_left_textView = view.findViewById(R.id.rating_left_textView);
        emoji_right_textView = view.findViewById(R.id.emoji_right_textView);
        rating_right_textView = view.findViewById(R.id.rating_right_textView);
        add_apartement_details_textView = view.findViewById(R.id.add_apartement_details_textView);
        report_a_bug_textView = view.findViewById(R.id.report_a_bug_textView);

        profile_imageView = view.findViewById(R.id.profile_imageView);
        gender_imageView = view.findViewById(R.id.gender_imageView);

        message_layout = view.findViewById(R.id.message_layout);
        edit_layout = view.findViewById(R.id.edit_layout);
        add_apartement_details_layout = view.findViewById(R.id.add_apartement_details_layout);
        report_a_bug_layout = view.findViewById(R.id.report_a_bug_layout);
        progress_layout = view.findViewById(R.id.progress_layout);

        // link java variables to xml layout views

    }

    private void setTypeFaces(View view){

        Typeface tp = Typeface.createFromAsset(view.getContext().getAssets(), "project_boston_typeface.ttf");

        your_account_textView.setTypeface(tp);
        name_textView.setTypeface(tp);
        age_textView.setTypeface(tp);
        rating_left_textView.setTypeface(tp);
        rating_right_textView.setTypeface(tp);
        add_apartement_details_textView.setTypeface(tp);
        report_a_bug_textView.setTypeface(tp);

        // set typefaces to all textViews

    }

    private void setEmojisAsTextOnTextViews(){

        emoji_left_textView.setText(StartupMethods.getEmojiByUnicode(0x1F641));
        emoji_right_textView.setText(StartupMethods.getEmojiByUnicode(0x1F60A));

        // set correct emoji's to corresponding emoji rating textViews

    }

    private void makeTextViewsInvisible(){

        add_apartement_details_layout.setVisibility(View.GONE);

        add_apartement_details_layout.removeAllViews();

        // remove 'add apartment details' layout for now

    }

    private void getDataFromPreviousActivity(){

        fromActivity = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("fromActivity");

        // retrieve info on what activity user came from. Different integer numbers mean different activities

        UID = getActivity().getIntent().getStringExtra("UID");

        // retrieve specified User ID

    }

    private void makeProgressLayoutAppear(){

        Animation enter_anim_progress_layout = AnimationUtils.loadAnimation(getActivity(), R.anim.visibility_fade_in_animation);

        MyApplication.makeProgressLayoutAppearFrameLayout(progress_layout, enter_anim_progress_layout);

    }

    private void initDB(Activity activity){

        Firebase.setAndroidContext(activity.getApplicationContext());

        // enable firebase to be used in this activity

    }

    private void changeFragmentFuncIfUserViewingOwnApartment(){

        if (!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(UID)){

            your_account_textView.setVisibility(View.GONE);
            edit_layout.setVisibility(View.INVISIBLE);
            add_apartement_details_layout.setVisibility(View.GONE);
            report_a_bug_layout.setVisibility(View.GONE);

            // if user visits his/her own profile, make above views dissapear

        }

    }

    private void retrieveApartmentLocationID(){

        final DatabaseReference fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.APARTMENTS_TABLE_URL_REFERENCE + AID);

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                locationID = dataSnapshot.child(ConstantsDB.APARTMENT_LOCATION_ID_TABLE).getValue(String.class);

                // retrieve Place ID for specified apartment

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addApartmentButtonClicked(){

        if (StartupMethods.isOnline(Objects.requireNonNull(getActivity()).getApplicationContext())) {

            Intent i = new Intent(getActivity(), RegisterActivityYesScreen1.class);
            i.putExtra("isFromProfileActivity", 1);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);

            // launch first apartment register activity upon user clicking add apartment details layout button

        } else {

            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            // if no internet connection was found, inform user by showing a toast

        }

    }

    private void makeProfileImageEqualWidthHeight(){

        profile_imageView.getLayoutParams().height = profile_imageView.getWidth();

        // set profile imageView height equal to profile_imageView width

    }

    private void initMiscVars(){

        tabLayout = AccountAndApartementActivity.tablayout;

        // create variable that controls tabLayout in Activity that this fragment is hosted in

    }

    private void getLayoutElementsOrigYpos() {

        scrollView_org_pos_y = scrollView.getY();

        // get current Y coordinate of scrollView

        tabLayout_org_pos_y = tabLayout.getY();

        // get current Y coordinate of tabLayout

    }

    private void retrieveUserInfoFromDB(){

        fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + ConstantsDB.USERS_TABLE_URL_REFERENCE + UID);

        // create firebase link

        ProfileActivityMethods.retrieveUserInfo(
                getContext(), exit_anim_progress_layout, getActivity(), progress_layout,
                gender_imageView, age_textView, name_textView, rating_right_textView,
                rating_left_textView, profile_imageView, scrollView,
                scrollView_org_pos_y, fbRef);

        // retrieve all info about selected user that will be displayed to currently logged in user from database

    }

    private void initLayout(View view){

        linkXMLElementsWithJavaVariables(view);

        setTypeFaces(view);

        setEmojisAsTextOnTextViews();

        makeProfileImageEqualWidthHeight();

        makeTextViewsInvisible();

        makeProgressLayoutAppear();

    }

    private void initFragmentFunc(){

        initMiscVars();

        getLayoutElementsOrigYpos();

        getDataFromPreviousActivity();

        initDB(Objects.requireNonNull(getActivity()));

        changeFragmentFuncIfUserViewingOwnApartment();

        retrieveUserInfoFromDB();

        retrieveApartmentLocationID();

    }

    private void retrieveEncodeNewProfileImage(Intent data){

        Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

        // create Bitmap object from received image in data parameter

        profile_imageView.setImageBitmap(bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        }

        byte[] byteArray = byteArrayOutputStream .toByteArray();

        // create baos object and convert to byte array object

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // convert byte array to (Base64) String object

    }

    private void submitNewPfToAccDBtables(){

        fbRef.child(ConstantsDB.USER_PROFILE_IMAGE_TABLE).setValue(encoded);
        fbRef3.child(ConstantsDB.ITEM_APARTMENT_PROFILE_IMAGE_TABLE).setValue(encoded);

        // submit base64 encoded image string as new profile picture,
        // update the according user and apartment item tables

    }

    private void attemptRetrieveNewProfileImage(Intent data){

        bitmap = null;

        Uri selectedImage1 = data.getData();

        try {

            bitmap = StartupMethods.decodeUri(Objects.requireNonNull(getActivity()).getBaseContext(), selectedImage1, 100);

            // try decoding image uri

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

    }

    private void encodeNewProfileImage(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        // create baos objet and convert to byte array object

        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // convert byte array to (Base64) String object

    }

    private void retrieveImageCamera(Intent data){

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);

            // reqeust permission to make use of device camera

        } else {

            // if camera permissions have been accepted, do the following

            retrieveEncodeNewProfileImage(data);

            submitNewPfToAccDBtables();

        }


        // Uri uri = getImageUri(getActivity().getBaseContext(), bitmap3);

    }

    private void retrieveImageGallery(Intent data){

        attemptRetrieveNewProfileImage(data);

        encodeNewProfileImage();

        profile_imageView.setImageBitmap(bitmap);

        submitNewPfToAccDBtables();

    }

}
