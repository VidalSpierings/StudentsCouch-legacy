package com.studentscouch.projectbostonfiles.app_back_end;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;
import com.studentscouch.projectbostonfiles.ui.MessagingActivity;
import com.studentscouch.projectbostonfiles.ui.ProfileActivity;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ProfileActivityMethods {

    private static List<View> scrollViewList;
    private static List<Float> scrollView_org_pos;

    public static void retrieveUserInfo(
            final Context context, final Animation exit_anim_progress_layout, final Activity activity,
            final FrameLayout progress_layout, final ImageView gender_imageView, final TextView age_textView,
            final TextView name_textView, final TextView rating_right_textView, final TextView rating_left_textView,
            final ImageView profile_imageView, final ScrollView scrollView,
            final Float scrollView_org_pos_y, DatabaseReference fbRef) {

        fbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                ProfileActivityMethods.retrieveUserInfoFunctionality(
                        context, exit_anim_progress_layout, activity, progress_layout,
                        dataSnapshot, gender_imageView, age_textView, name_textView,
                        rating_right_textView, rating_left_textView, profile_imageView,
                        scrollView, scrollView_org_pos_y);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private static void retrieveUserInfoFunctionality(
            Context context, Animation exit_anim_progress_layout, Activity activity,
            FrameLayout progress_layout, DataSnapshot dataSnapshot,
            ImageView gender_imageView, TextView age_textView, TextView name_textView, TextView rating_right_textView,
            TextView rating_left_textView, ImageView profile_imageView,
            ScrollView scrollView, Float scrollView_org_pos_y)  {

        makeProgressLayoutDisappear(activity, progress_layout, exit_anim_progress_layout);

        retrieveProfileInfo(dataSnapshot, gender_imageView);

        age_textView.setText(ProfileActivity.userAge);

        // calculate age according to birth date retrieved

        Bitmap imageBitmap = StartupMethods.StringToBitMap(ProfileActivity.image);

        // convert image String (Base64 format) to Bitmap object

        showInfo(
                name_textView, rating_right_textView,
                rating_left_textView, profile_imageView,
                imageBitmap);

        attemptAnimateActivityTransition(context, scrollView, scrollView_org_pos_y);

    }

    @SuppressLint("SetTextI18n")
    private static void showInfo(
            TextView name_textView, TextView rating_right_textView,
            TextView rating_left_textView, ImageView profile_imageView,
            Bitmap imageBitmap){

        name_textView.setText(ProfileActivity.firstName + " " + ProfileActivity.lastName);
        rating_right_textView.setText(String.valueOf(ProfileActivity.rating_happy_emoji));
        rating_left_textView.setText(String.valueOf(ProfileActivity.rating_unhappy_emoji));

        profile_imageView.setImageBitmap(imageBitmap);

        // display profile user' forst and last name, happy and unhappy ratings, and set profile image

    }

    private static void attemptAnimateActivityTransition(Context context, ScrollView scrollView, float scrollView_org_pos_y){

        if (ProfileActivity.fromActivity.equals("0")){

            try {

                StudentsCouchAnimations.animateActivityTransitionProfileActivity(context, scrollView, scrollView_org_pos_y);

                // if user comes from activity other than ApartmentEditActivity

            } catch (Exception e) {

                Toast.makeText(context, "Data changed", Toast.LENGTH_LONG).show();

                // if animations could not be performed, only show a toast to user informing them that their data has been saved

            }

        }

    }

    private static void retrieveProfileInfo(DataSnapshot dataSnapshot, ImageView gender_imageView){

        String hostStatus = dataSnapshot.child(ConstantsDB.APARTMENT_HOST_STATUS_TABLE).child(ConstantsDB.APARTMENT_STATUS_TABLE).getValue(String.class);

        // retrieve status on whether or not user has their apartment register or has yet to register their apartment details

        attemptRetrieveHostStatusAndAID(Objects.requireNonNull(hostStatus), dataSnapshot);

        attemptRetrieveFirstName(dataSnapshot);

        attemptRetrieveLastName(dataSnapshot);

        attemptRetrieveNumHappyEmojiRatings(dataSnapshot);

        attemptRetrieveNumUnhappyEmojiRatings(dataSnapshot);

        attemptRetrieveAndSetGender(dataSnapshot, gender_imageView);

        attemptRetrieveDateOfBirth(dataSnapshot);

        attemptRetrieveProfileImage(dataSnapshot);

        ProfileActivity.userAge = getAge(ProfileActivity.year, ProfileActivity.month, ProfileActivity.day);

    }

    private static void attemptRetrieveDateOfBirth(DataSnapshot dataSnapshot){

        attemptRetrieveBirthYear(dataSnapshot);

        attemptRetrieveBirthMonth(dataSnapshot);

        attemptRetrieveDayOfBirth(dataSnapshot);

    }

    private static void attemptRetrieveProfileImage(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.image = dataSnapshot.child(ConstantsDB.USER_PROFILE_IMAGE_TABLE).getValue(String.class);

            // attempt to retrieve user profile picture

        } catch (Exception e){

            ProfileActivity.image = null;

            // if user profile picture could not be retrieved, make 'image' Bitmap object empty

        }

        // try getting above data, if cannot be retrieved than set to 0, null,
        // or something else that makes it clear for the user that something went wrong with
        // loading all the data

    }

    private static void attemptRetrieveDayOfBirth(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.day = dataSnapshot.child(ConstantsDB.USER_BIRTHDATE_DAY_TABLE).getValue(Integer.class);

            if (ProfileActivity.day == 0){

                ProfileActivity.day = 3;

                // if users' day of birth could not be retrieved, set day of birth as 3

            }

        } catch (Exception e) {

            ProfileActivity.day = 3;

            // if users' day of birth could not be retrieved, set day of birth as 3

        }

    }

    private static void attemptRetrieveBirthMonth(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.month = dataSnapshot.child(ConstantsDB.USER_BIRTHDATE_MONTH_TABLE).getValue(Integer.class);

            if (ProfileActivity.month == 0){

                ProfileActivity.month = 4;

                // if user birth month could not be retrieved, set birth month as 4

            }

        } catch (Exception e){

            ProfileActivity.month = 4;

            // if user birth month could not be retrieved, set birth month as 4

        }

    }

    private static void attemptRetrieveBirthYear(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.year = dataSnapshot.child(ConstantsDB.USER_BIRTHDATE_YEAR_TABLE).getValue(Integer.class);

            if (ProfileActivity.year == 0){

                ProfileActivity.year = 1998;

                // if birthdate of user in profile could not be retrieved, set birth year as 1998

            }

        } catch (Exception e){

            ProfileActivity.year = 1998;

            // if user birthdate could not be retrieved, set birth year as 1998

        }

    }

    private static void attemptRetrieveAndSetGender(DataSnapshot dataSnapshot, ImageView gender_imageView){

        try {

            ProfileActivity.gender = dataSnapshot.child(ConstantsDB.USER_GENDER_TABLE).getValue(String.class);

            // retrieve users' gender (male or female)

            setGender(gender_imageView);

        } catch (Exception e){

            gender_imageView.setImageResource(R.drawable.gender_female);

            // if gender could not be retrieved, set female gender icon

        }

    }

    public static void setGender(ImageView gender_imageView){

        if (ProfileActivity.gender.equals(ConstantsDB.GENDER_MALE)){

            gender_imageView.setImageResource(R.drawable.gender_male);

            // if user gender is male, set male gender icon

        } else if (ProfileActivity.gender.equals(ConstantsDB.GENDER_FEMALE)){

            gender_imageView.setImageResource(R.drawable.gender_female);

            // if gender of user in profile is female, set female gender icon

        } else {

            gender_imageView.setImageResource(R.drawable.gender_female);

            // if gender of user in profile is somehow different from male or female, set female gender icon

        }

    }

    private static void attemptRetrieveNumUnhappyEmojiRatings(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.rating_unhappy_emoji = dataSnapshot.child(
                    ConstantsDB.USER_EMOJI_RATING_TABLE).child(ConstantsDB.USER_UNHAPPY_EMOJIS_RATING_TABLE).getValue(Integer.class);

            // attempt to retrieve number of unhappy emoji's (negative ratings)

        } catch (Exception e){

            ProfileActivity.rating_unhappy_emoji = 0;

            // if number of unhappy rating emojis could not be retrieved, set number of unhappy rating emojis to 0

        }

    }

    private static void attemptRetrieveNumHappyEmojiRatings(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.rating_happy_emoji = dataSnapshot.child(
                    ConstantsDB.USER_EMOJI_RATING_TABLE).child(ConstantsDB.USER_HAPPY_EMOJIS_RATING_TABLE).getValue(Integer.class);

            // attempt to retrieve number of happy emoji's (positive ratings)

        } catch (Exception e){

            ProfileActivity.rating_happy_emoji = 0;

            // if number of happy rating emojis could not be retrieved, set number of happy rating emojis to 0

        }

    }

    private static void attemptRetrieveLastName(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.lastName = dataSnapshot.child(ConstantsDB.USER_LAST_NAME_TABLE).getValue(String.class);

            if (ProfileActivity.lastName.equals("") || ProfileActivity.lastName.equals(ConstantsDB.NULL)){

                ProfileActivity.lastName = ConstantsDB.ERROR;

                // if last name can not be retrieved, set first name as 'ERROR'

            }

        } catch (Exception e){

            ProfileActivity.lastName = ConstantsDB.ERROR;

            // if last name can not be retrieved, set first name as 'ERROR'

        }

    }

    private static void attemptRetrieveFirstName(DataSnapshot dataSnapshot){

        try {

            ProfileActivity.firstName = dataSnapshot.child(ConstantsDB.USER_FIRST_NAME_TABLE).getValue(String.class);

            if (ProfileActivity.firstName.equals("") || ProfileActivity.firstName.equals(ConstantsDB.NULL)){

                ProfileActivity.firstName = ConstantsDB.ERROR;

                // if first name can not be retrieved, set first name as 'ERROR'

            }

        } catch (Exception e){

            ProfileActivity.firstName = ConstantsDB.ERROR;

            // if first name can not be retrieved, set first name as 'ERROR'

        }

    }

    private static void attemptRetrieveHostStatusAndAID(String hostStatus, DataSnapshot dataSnapshot){

        if (hostStatus.equals(ConstantsDB.HOST_STATUS_USER_IS_HOST)){

            ProfileActivity.isHost = true;

            ProfileActivity.AID = dataSnapshot.child(ConstantsDB.APARTMENT_AID_TABLE).getValue(String.class);

            // get AID if user is current user profile is a host
            // inform activity that the user that is visible in this profile is a host

        } else {

            ProfileActivity.isHost = false;

            // inform activity that user is not a host

        }

    }

    public static void makeProgressLayoutDisappear(Activity activity, FrameLayout progress_layout, Animation exit_anim_progress_layout){

        exit_anim_progress_layout = AnimationUtils.loadAnimation(activity, R.anim.visibility_fade_out_animation);

        exit_anim_progress_layout.setDuration(200);

        // load fade out animation

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim_progress_layout);

    }

    private static String getAge(int year, int month, int day){

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

    public static void initialiseMessageButtonOnClickListener (
            Context context, MotionEvent motionEvent, Activity activity, View message_layout_view,
            ScrollView scrollView, Float scrollView_org_pos_y) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP){

            StartupMethods.startCircularRevealAnimationDefaultStartRadius(context, message_layout_view, false);

            // start circular reveal animation for message button reveal view

            StudentsCouchAnimations.animateActivityTransitionProfileActivity(context, scrollView, scrollView_org_pos_y);

            // animate activity exit transition animations for message button onClick

            // start activity exit animations

            initArrays(scrollView, scrollView_org_pos_y);

            Intent i = new Intent(activity, MessagingActivity.class);

            StartupMethods.exitAnimation(context, i, scrollViewList, scrollView_org_pos, null, "translationY");

        }

    }

    public static void initArrays(ScrollView scrollView, float scrollView_org_pos_y){

        scrollViewList = null;
        scrollViewList.add(scrollView);

        scrollView_org_pos = null;
        scrollView_org_pos.add(scrollView_org_pos_y);

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseEditButtonOnClickListener (
            final Context context, final Activity activity, final View edit_layout_view,
            final View message_layout_view, final CharSequence[] items, final ContentValues values, final int MY_PERMISSION_RESPONSE,
            final int REQUEST_CAMERA, final Intent intent, final int REQUEST_GALLERY, FrameLayout edit_layout
    ) {

        edit_layout.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                ProfileActivityMethods.initialiseEditButtonOnClickListenerFunctionality(
                        context, motionEvent, activity, edit_layout_view,
                        message_layout_view, items, values, MY_PERMISSION_RESPONSE,
                        REQUEST_CAMERA, intent, REQUEST_GALLERY);

            }

            return true;
        });

    }

    public static void initialiseEditButtonOnClickListenerFunctionality (
            final Context context, MotionEvent motionEvent, final Activity activity, final View edit_layout_view,
            final View message_layout_view, final CharSequence[] items, final ContentValues values, final int MY_PERMISSION_RESPONSE,
            final int REQUEST_CAMERA, final Intent intent, final int REQUEST_GALLERY) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP){

            if (StartupMethods.isOnline(activity.getApplicationContext())) {

                StartupMethods.startCircularRevealAnimationDefaultStartRadius(context, edit_layout_view, false);

                // start circular reveal animation for edit button reveal view

                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(context.getResources().getString(R.string.replace_profile_image));

                // build dialog

                setDialogItems(
                        context, activity, builder, items,
                        values, MY_PERMISSION_RESPONSE, REQUEST_CAMERA,
                        REQUEST_GALLERY, intent);

                setDialogCancelListener(builder, edit_layout_view, message_layout_view);

                builder.show();

            } else {

                Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

                // inf internet connection wasn't found, inform user by showing a toast

            }
        }

    }

    private static void setDialogItems(
            Context context, Activity activity, AlertDialog.Builder builder, CharSequence[] items,
            ContentValues values, int MY_PERMISSION_RESPONSE, int REQUEST_CAMERA, int REQUEST_GALLERY,
            Intent intent){

        builder.setItems(items, (dialogInterface, i) -> {

            initialiseCameraAndGalleryButton(
                    context, items, i, values, activity,
                    MY_PERMISSION_RESPONSE, REQUEST_CAMERA, REQUEST_GALLERY, intent);

            // initialise item click functionality for camera and gallery buttons

        });

    }

    private static void setDialogCancelListener(AlertDialog.Builder builder, View edit_layout_view, View message_layout_view){

        builder.setOnCancelListener(dialogInterface -> {

            // if user presses outside dialog and makes it disappear, do the following

            edit_layout_view.setVisibility(View.INVISIBLE);
            message_layout_view.setVisibility(View.INVISIBLE);

            // make message layout and edit layout reveal views invisible

        });

    }

    private static void initialiseCameraAndGalleryButton(
            Context context, CharSequence[] items, int i, ContentValues values2,
            Activity activity, int MY_PERMISSION_RESPONSE, int REQUEST_CAMERA, int REQUEST_GALLERY,
            Intent intent) {

        if (items[i].equals("Camera")) {

            // create 'capture image' intent

            attemptRequestWriteStoragePermissions(activity, MY_PERMISSION_RESPONSE);

            setImageContentValues();

            launchImageCaptureIntent(context, activity, REQUEST_CAMERA);

        } else if (items[i].equals("Gallery")) {

            if (Build.VERSION.SDK_INT >= 23) {

                attemptRequestStoragePermissionIfNotGranted(activity);

                launchImageCaptureIntent2(activity, REQUEST_GALLERY);

            }
        }

    }

    private static void attemptRequestStoragePermissionIfNotGranted(Activity activity){

        int permissionCheck = ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }

    }

    @SuppressLint("IntentReset")
    private static void launchImageCaptureIntent2(Activity activity, int REQUEST_GALLERY){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);

        // launch gallery activity with request code for onActivityResult()

    }

    private static void launchImageCaptureIntent(Context context, Activity activity, int REQUEST_CAMERA){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  intent.putExtra(MediaStore.EXTRA_OUTPUT, image2);

        try {

            activity.startActivityForResult(intent, REQUEST_CAMERA);

            // launch camera app with request code for onActivityResult()

        } catch (Exception e){

            Toast.makeText(activity.getApplicationContext(), context.getResources().getString(R.string.camera_unavailable), Toast.LENGTH_LONG).show();

            // Inform user when their device camera is not supported with the help of a toast

        }

    }

    private static void attemptRequestWriteStoragePermissions(Activity activity, int MY_PERMISSION_RESPONSE){

        String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(perms, MY_PERMISSION_RESPONSE);
        }

        // request permissions if user device is equal to higher than marshmellow

    }

    private static void setImageContentValues(){

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());

        // add image title and description

    }

}
