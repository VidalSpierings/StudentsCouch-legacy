package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.firebase.client.Firebase;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.dbImplementers.ApartmentEditActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.ApartmentEditActivityDBInterface;
import com.studentscouch.projectbostonfiles.observables.ApartmentEditActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.ApartmentEditActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.ApartmentEditActivityViewModel;
import com.studentscouch.projectbostonfiles.viewpagers_and_corresponding_files.CustomSwipeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ApartmentEditActivityViewImplementer implements ApartmentEditActivityView {

    private EditText
            price_per_nights_editText,
            num_people_per_stay_num_editText;

    private TextView
            description_title,
            description_description,
            currency_text,
            per_night_textView,
            num_people_per_stay_desc_textView,
            letter_count_textView,
            save_data_textView;

    private ViewPager viewPager;

    private CustomSwipeLayout customSwipeLayout;

    private LinearLayout slideDotsPanel;

    private ImageView[] dots;

    private int dotsCount;

    private View
            delete_button_view,
            add_button_view;

    private Bitmap[] newList;

    private FrameLayout main_frameLayout;

    private int

            REQUEST_REPLACE_IMAGE_1_CAMERA = 1000,
            REQUEST_REPLACE_IMAGE_1_GALLERY = 1001,
            REQUEST_REPLACE_IMAGE_2_CAMERA = 1002,
            REQUEST_REPLACE_IMAGE_2_GALLERY = 1003,
            REQUEST_REPLACE_IMAGE_3_CAMERA = 1004,
            REQUEST_REPLACE_IMAGE_3_GALLERY = 1005,
            REQUEST_ADD_IMAGE_2_CAMERA = 1006,
            REQUEST_ADD_IMAGE_2_GALLERY = 1007,
            REQUEST_ADD_IMAGE_3_CAMERA = 1008,
            REQUEST_ADD_IMAGE_3_GALLERY = 1009;

    private FrameLayout progress_layout;

    private String
            descriptionFromIntent,
            titleFromIntent;

    private FrameLayout save_data_layout;

    private ImageView
            add_imageView,
            delete_imageView;

    private ScrollView scrollView;

    private View rootView;

    private Uri selectedImage1;

    private Bitmap
            bitmap,
            bitmap3;

    private Bitmap
            apartement1,
            apartement2;

    private Animation
            exit_anim;

    private ApartmentEditActivityObservables observables;

    private ApartmentEditActivityDBInterface db;

    private ApartmentEditActivityViewModel viewModel;

    private ViewGroup viewGroup;

    public ApartmentEditActivityViewImplementer(Context context, ViewGroup viewGroup, ApartmentEditActivityObservables observables){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_apartement_edit, viewGroup);

        this.observables = observables;

        db = new ApartmentEditActivityDBImplementer(observables);

        this.viewGroup = viewGroup;

        viewModel = new ApartmentEditActivityViewModel(this, observables);

    }

    @Override
    public View getRootView() {

        return rootView;

    }

    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        linkXmlElementsToJavaVariables();

        setTypefaces(context);

    }

    private void setTypefaces(Context context){

        Typeface tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

        description_title.setTypeface(tp);
        description_description.setTypeface(tp);
        currency_text.setTypeface(tp);
        price_per_nights_editText.setTypeface(tp);
        per_night_textView.setTypeface(tp);
        num_people_per_stay_desc_textView.setTypeface(tp);
        num_people_per_stay_num_editText.setTypeface(tp);
        save_data_textView.setTypeface(tp);

        // initialise and set typeface to all textViews and editTexts

    }

    private void linkXmlElementsToJavaVariables(){

        price_per_nights_editText = rootView.findViewById(R.id.price_per_night_editText);
        num_people_per_stay_num_editText = rootView.findViewById(R.id.num_people_per_stay_num_editText);

        description_title = rootView.findViewById(R.id.description_title);
        description_description = rootView.findViewById(R.id.description_description);
        currency_text = rootView.findViewById(R.id.currency_text);
        per_night_textView = rootView.findViewById(R.id.per_night_textView);
        num_people_per_stay_desc_textView = rootView.findViewById(R.id.num_people_per_stay_desc_textView);
        letter_count_textView = rootView.findViewById(R.id.letter_count_textView);
        save_data_textView = rootView.findViewById(R.id.save_data_textView);

        slideDotsPanel = rootView.findViewById(R.id.slideDotsPanel);

        scrollView = rootView.findViewById(R.id.scrollView);

        delete_button_view = rootView.findViewById(R.id.delete_button_view);
        add_button_view = rootView.findViewById(R.id.add_buton_view);

        add_imageView = rootView.findViewById(R.id.add_imageView);
        delete_imageView = rootView.findViewById(R.id.delete_imageView);

        viewPager = rootView.findViewById(R.id.apartement_images_imageView);

        main_frameLayout = rootView.findViewById(R.id.main_frameLayout);
        save_data_layout = rootView.findViewById(R.id.save_data_layout);
        progress_layout = rootView.findViewById(R.id.progress_layout);

        viewPager = rootView.findViewById(R.id.apartement_images_imageView);

        // connect Java variables with xml layout views

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        Firebase.setAndroidContext(appCompatActivity);

        // load editor that will pass apartment images to next activity

        getInfo(appCompatActivity);

        initAnims(context);

        initActivity(context);

        initUI();

        save_data_layout.setOnClickListener(view -> viewModel.saveNewApartmentData(context, appCompatActivity, viewGroup));

        // initialise save data button, pressing button will save any made changes

        initPricePerNightNumPeopleEditTexts(context);

        // initialise price_per_night editText and add onClickListeners to surrounding textViews

        add_imageView.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                viewModel.addImageButtonFunctionality(
                        context, appCompatActivity, view, add_button_view, delete_button_view);

            }

            return true;
        });

        delete_imageView.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                viewModel.deleteButtonFunc(
                        context, appCompatActivity, add_button_view,
                        delete_button_view, main_frameLayout);

            }

            return true;
        });

    }

    private void initUI(){

        observables.setOriginal_tariff(Integer.valueOf(price_per_nights_editText.getText().toString()));

        observables.setOriginal_num_people(Integer.valueOf(price_per_nights_editText.getText().toString()));

        // convert string objects to integer objects

        // assign number of people allowed per night and price per night to according textViews

    }

    @Override
    public void initSwipeDotsLayout(Context context, String apartement1String, String apartement2String, String apartement3String, LinearLayout slideDotsPanel) {

        oneImageProtocol(context);

        oneImageProtocol2(context);

        twoImagesProtocol(context);

        threeImagesProtocol(context);

        initSwipeDotsOneImagePresent(context);

    }

    @Override
    public void initDescEditText(Context context) {

        letter_count_textView.setVisibility(View.INVISIBLE);

        description_description.setText(observables.getDescriptionDescriptionString());

        setLayoutVisibilityAccToDataFetchComplete(context);

        setDescriptionDescriptionTextWatcher();

    }

    @Override
    public void retrieveCameraImage(Context context, AppCompatActivity appCompatActivity, int REQUEST_CODE, int resultCode) {

        int RESULT_OK = -1;

        getCameraBitmap(context, appCompatActivity);

        attemptInitGlobalImagesVariableCamera(REQUEST_CODE, resultCode, RESULT_OK, bitmap3);

        reInitialiseSwipeDotsLayout(context);

    }

    @Override
    public void retrieveGalleryImage(Context context, Intent data, int REQUEST_CODE, int resultCode) {

        int RESULT_OK = -1;

        selectedImage1 = null;

        bitmap = null;

        // create new Uri and bitmap objects

        decodeBitmap(context, data);

        attemptInitGlobalImagesVariableGallery(
                context, REQUEST_CODE, resultCode, RESULT_OK, bitmap);

        reInitialiseSwipeDotsLayout(context);

    }

    @Override
    public void initObservables(){

        observables.setDesciptionTitleString(description_title.getText().toString());
        observables.setDescriptionDescriptionString(description_description.getText().toString());
        observables.setNumPeoplePerStayString(num_people_per_stay_num_editText.getText().toString());
        observables.setPrice_per_night(price_per_nights_editText.getText().toString());
        observables.setPricePerNightString(price_per_nights_editText.getText().toString());

    }

    @Override
    public void buttonClickedAnimation(Context context) {

        StartupMethods.startCircularRevealAnimationDefaultStartRadius(
                context, add_button_view, false);

        // start circular reveal animation for add button reveal view

    }

    private void initActivity(Context context){

        showActivityInfoToast(context);
        // show toast informing user to scroll down and press the save button to save made changes

        initAnims(context);

        observables.setImage_resources_images(new Bitmap[0]);

        initialiseSwipeDotsLayout(context);

        // change slideDotsPanel according to number of images retrieved

        initialiseDescriptionEditText(context);

        // initialise description editText with textWatcher that detects when user has crossed max num. Characters limit

    }

    private void getInfo(AppCompatActivity appCompatActivity){

        getDataFromPrevActivity(appCompatActivity);

        setDataToTextViews();

        getApartmentImages(appCompatActivity);

        db.getApartmentPlaceID();

    }

    private void initAnims(Context context){

        Animation enter_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_in_animation);

        // set fade in animation as enter animation

        exit_anim = AnimationUtils.loadAnimation(context, R.anim.visibility_fade_out_animation);

        // set fade out animation as exit animation

        enter_anim.setDuration(200);

        exit_anim.setDuration(200);

        // set duration for both enter and exit animation at 200 milliseconds

    }

    private void initNumPeopleEditText(Context context){

        if (Integer.parseInt(num_people_per_stay_num_editText.getText().toString()) > 2 || Integer.parseInt(num_people_per_stay_num_editText.getText().toString()) < 1) {

            num_people_per_stay_num_editText.setText(String.valueOf(observables.getOriginal_num_people()));

            // set max number of people allowed per booking to value before being edited

            Toast.makeText(context, context.getResources().getString(R.string.max_num_people), Toast.LENGTH_LONG).show();

            // inform user that maximum of people allowed to stay per booking as enforced by the platform is 2 by showing a toast

        }

        // check if user has entered a number other than 1 or 2, if so,
        // inform user that entering this number is not possible by showing a toast

    }

    private void initPerNightTextViewOnClickListener(){

        per_night_textView.setOnClickListener(view -> {

            price_per_nights_editText.requestFocus();

            // if user presses the 'per night' textView (€) request focus on price editText

            price_per_nights_editText.setSelection(price_per_nights_editText.length());

            // move editText cursor to end of text

        });

        // request focus on price per night editText upon selecting per night textView

    }

    private void initCurrencyEditText(){

        currency_text.setOnClickListener(view -> {

            price_per_nights_editText.requestFocus();

            // if user presses the currency textView (€) request focus on price editText

            price_per_nights_editText.setSelection(price_per_nights_editText.length());

            // move editText cursor to end of text

        });

        // request focus on price per night editText upon selecting currency textView

    }

    @SuppressLint("SetTextI18n")
    private void initPricePerNightEditText(Context context){

        price_per_nights_editText.setOnFocusChangeListener((view, b) -> {

            if (Integer.valueOf(price_per_nights_editText.getText().toString()) > 50 || Integer.valueOf(price_per_nights_editText.getText().toString()) < 15) {

                price_per_nights_editText.setText("" + observables.getOriginal_tariff());

                Toast.makeText(context, context.getResources().getString(R.string.price_limit), Toast.LENGTH_LONG).show();

                // if price per night was set under 15 or above 50, return original price from before changes were made

            }

        });

        // check if user has entered a number greater than 50 or lower than 15, if so,
        // set number to 0 and inform user that the entered number exceeds or preceeds allowed price per night

        setDefaultValuePriceTooHighOrLow(context);

    }

    private void setDefaultValuePriceTooHighOrLow(Context context){

        if (Integer.parseInt(price_per_nights_editText.getText().toString()) > 50 || Integer.parseInt(price_per_nights_editText.getText().toString()) < 15) {

            price_per_nights_editText.setText(String.valueOf(25));

            // set price per night to value before being edited

            Toast.makeText(context, context.getResources().getString(R.string.price_limit), Toast.LENGTH_LONG).show();

            // inform user that price per night needs to be between 15 and 50 euros by showing a toast


        }

        // if somehow original price per night was under 15 or above 50, set new price at 25

    }

    private void setDescriptionDescriptionTextWatcher(){

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                setMaxNumCharactersIndicator();

            }
        };

        description_description.addTextChangedListener(textWatcher);

        // apply textwatcher to description description editText

    }

    private void setMaxNumCharactersIndicator(){

        if (description_description.getText().length() >= StartupMethods.MAX_NUM_CHARACTERS){

            showExcessCharactersIndicator();

        }

        if (description_description.getText().length() < StartupMethods.MAX_NUM_CHARACTERS){

            hideExcessCharactersIndicator();

        }

    }

    private void hideExcessCharactersIndicator(){

        letter_count_textView.setVisibility(View.INVISIBLE);

        // if letter count limit is not overridden anymore, make letter count invisible

    }

    @SuppressLint("SetTextI18n")
    private void showExcessCharactersIndicator(){

        // if max number of allowed characters for apartment description is reached, do the following

        letter_count_textView.setVisibility(View.VISIBLE);

        // make letter count view visible

        int characterLength = description_description.getText().length();

        // get currently character length

        letter_count_textView.setText("-" + (characterLength - StartupMethods.MAX_NUM_CHARACTERS_MINUS_ONE));

        /*

         * set letter count to current characters length
         * minus maximum allowed number of characters minus one.
         * This shows user how many characters they are out of the allowed maximum

         */

    }

    private void setLayoutVisibilityAccToDataFetchComplete(Context context){

        if (description_title.equals(context.getResources().getString(R.string.loading))){

            progress_layout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);

            // while title of description is loading, make loading screen visible and make scrollView (main layout) invisible

            if (!description_title.equals(context.getResources().getString(R.string.loading))){

                MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

                // if description title is done loading, hide progress layout

            }

        }

    }

    private void initSwipeDotsOneImagePresent(Context context){

        if (customSwipeLayout.getCount() != 1) {

            for (int i = 0; i < dotsCount; i++) {

                addDots(context, i);

            }
        }

    }

    private void addDots(Context context, int i){

        dots[i] = new ImageView(context);
        dots[i].setImageResource(R.drawable.circle_outline_transparent);

        // create new imageView and set imageView to circle outline

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

        // create linearLayout params of 20sp by 20sp

        params.setMargins(8, 0, 8, 0);

        // give params 8sp margin from the left and right

        slideDotsPanel.addView(dots[i], params);

        // add dot to slideDotsPanel along with linearLayout parameters

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_selected));

        // fill dot at index of currently viewed image in a gray color

    }

    private void threeImagesProtocol(Context context){

        if (!observables.getApartement3String().equals("")){

            addApartmentImagesToArray();

            setNewDotsAndAdapter(context);

        }

    }

    private void setNewDotsAndAdapter(Context context){

        customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images());
        viewPager.setAdapter(customSwipeLayout);

        // initialise viewPager

        dotsCount = customSwipeLayout.getCount();
        dots = new ImageView[dotsCount];

        // create amount of dots according to amount of pictures in customSwipeLayout

    }

    private void addApartmentImagesToArray(){

        apartement1 = StartupMethods.StringToBitMap(observables.getApartement1String());
        apartement2 = StartupMethods.StringToBitMap(observables.getApartement2String());
        Bitmap apartement3 = StartupMethods.StringToBitMap(observables.getApartement3String());

        // convert string objects of first, second and third apartment images to Bitmap objects

        observables.setImage_resources_images(new Bitmap[]{apartement1, apartement2, apartement3});

        // insert Bitmap objects into global image resources array

    }

    private void twoImagesProtocol(Context context){

        if (!observables.getApartement2String().equals("")){

            addApartmentImagesToArray2();

            setNewAdapter(context);

        }

        // if two images are found, show these images,
        // change slideDotsPanel accordingly and set first circle of slideDotsPanel to selected

    }

    private void setNewAdapter(Context context){

        customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images() );
        viewPager.setAdapter(customSwipeLayout);

        // initialise viewPager

    }

    private void addApartmentImagesToArray2(){

        apartement1 = StartupMethods.StringToBitMap(observables.getApartement1String());
        apartement2 = StartupMethods.StringToBitMap(observables.getApartement2String());

        // convert string objects of first and second apartment images to Bitmap objects

        observables.setImage_resources_images(new Bitmap[]{apartement1, apartement2});

        // insert Bitmap objects into global image resources array

    }

    private void oneImageProtocol2(Context context){

        Log.i("isInit", "yes");

        if (!observables.getApartement1String().equals("")){

            addApartmentImagesToArray3();

            setNewDotsAndAdapter2(context);

        }

        // if an image can be found, show the image and remove slideDotsPanel

    }

    private void setNewDotsAndAdapter2(Context context){

        customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images() );
        viewPager.setAdapter(customSwipeLayout);

        // initialise viewpager

        dotsCount = customSwipeLayout.getCount();
        dots = new ImageView[dotsCount];

        // create amount of dots according to amount of pictures in customSwipeLayout

    }

    private void addApartmentImagesToArray3(){

        //observables.setImage_resources_images();

        apartement1 = StartupMethods.StringToBitMap(observables.getApartement1String());

        // convert string of first apartment image to Bitmap object

        observables.setImage_resources_images(new Bitmap[]{apartement1});

        // insert one image into image_resources_images array

    }

    private void oneImageProtocol(Context context){

        if (observables.getApartement1String().equals("")){

            observables.setImage_resources_images(
                    new Bitmap[]{BitmapFactory.decodeResource(context.getResources(), R.drawable.image_loading_error)}
            );

            // set first and only image of apartment item as an image that displays the text 'error loading images'

            customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images() );
            viewPager.setAdapter(customSwipeLayout);

            // initialise viewpager

            setViewPagerOnPageChangedListener(context);

            dotsCount = customSwipeLayout.getCount();
            dots = new ImageView[dotsCount];

            // create amount of dots according to amount of pictures in customSwipeLayout

        }

    }

    private void setViewPagerOnPageChangedListener(Context context){

        final int finalDotsCount = dotsCount;
        final ImageView[] finalDots = dots;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < finalDotsCount; i++) {

                    finalDots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_outline_transparent));

                    // create circle line items for slideDotsPanel

                }

                finalDots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_selected));

                // fill dot at index of currently viewed image in a gray color

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void getCameraBitmap(Context context, AppCompatActivity appCompatActivity){

        bitmap3 = null;

        // create new bitmap object

        String capturedImageFilePath = StartupMethods.retrieveImagePath(appCompatActivity, observables.getmCapturedImageURI());

        // retrieve image path

        final Uri uri = Uri.fromFile(new File(capturedImageFilePath));

        // get image saved on device

        attemptRetrieveCameraBitmap(context, uri);

    }

    private void attemptRetrieveCameraBitmap(Context context, Uri uri){

        try {

            bitmap3 = StartupMethods.decodeUri(context, uri, 100);

            // decode image size

        } catch (FileNotFoundException e) {

            Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            // if image could not be loaded, inform user by showing a toast
            e.printStackTrace();
        }

    }

    private void decodeBitmap(Context context, Intent data){

        getImageData(context, data);

        try {

            bitmap = StartupMethods.decodeUri(context, selectedImage1, 100);

            // convert image path Uri to bitmap object

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private void getImageData(Context context, Intent data){

        try {

            selectedImage1 = data.getData();

            // get image

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            // if image could not be loaded, inform user by showing a toast

        }

    }

    private void attemptInitGlobalImagesVariableGallery(
            Context context, int REQUEST_CODE, int resultCode, int RESULT_OK, Bitmap bitmap){

        try {

            if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_1_GALLERY && resultCode == RESULT_OK) {

                varReplaceImage1GalleryProtocol(REQUEST_CODE, resultCode, RESULT_OK, bitmap);

            }

            if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_2_GALLERY && resultCode == RESULT_OK) {

                varReplaceImage2GalleryProtocol(bitmap);

            }

            if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_3_GALLERY && resultCode == RESULT_OK) {

                varReplaceImage3GalleryProtocol(bitmap);

            }

            if (REQUEST_CODE == REQUEST_ADD_IMAGE_2_GALLERY && resultCode == RESULT_OK) {

                varAddImage2GalleryProtocol(bitmap);
            }

            if (REQUEST_CODE == REQUEST_ADD_IMAGE_3_GALLERY && resultCode == RESULT_OK) {

                varAddImage3GalleryProtocol(bitmap);

            }

            observables.setImage_resources_images(newList);

            // reassemble apartment images array

        } catch (Exception e) {

            Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            // inform user that application failed to replace/add new image

        }

    }

    @Override
    public void initialiseImagesViewPager(Context context) {

        customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images());
        viewPager.setAdapter(customSwipeLayout);

    }

    @Override
    public void changeSlideDotsPanel(Context context, LinearLayout slideDotsPanel) {

        if (observables.getImage_resources_images().length == 2
                || observables.getImage_resources_images().length == 3) {


            slideDotsPanel.removeAllViews();

            // remove current slideDotspanel arrangement to make place for new one

            dotsCount = observables.getImage_resources_images().length;
            dots = new ImageView[observables.getImage_resources_images().length];

            for (int i = 0; i < dotsCount; i++) {

                dots[i] = new ImageView(context);
                dots[i].setImageResource(R.drawable.circle_outline_transparent);

                // create new unselected dot

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

                // create new dot with 20sp by 20sp

                params.setMargins(8, 0, 8, 0);

                // create margin 8sp from the left and 8sp from the right

                slideDotsPanel.addView(dots[i], params);

                // add dot to slideDotsPanel with recently created layout params to slideDotsPanel

                dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_selected));

                // set first dot as selected

            }

        }

        // change slideDotsPanel appearance according to new number of images and their arrangements

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotsCount; i++) {

                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_outline_transparent));

                    // create circle line items for slideDotsPanel

                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_selected));

                // fill dot at index of currently viewed image in a gray color

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void makeRevealViewsInvisible() {

        add_button_view.setVisibility(View.INVISIBLE);
        delete_button_view.setVisibility(View.INVISIBLE);

        // remove selected views for add and delete button upon activity resuming

    }

    @Override
    public void retrieveInstantiatedPicture(Context context, AppCompatActivity appCompatActivity, int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_REPLACE_IMAGE_1_CAMERA && resultCode == RESULT_OK) {

            retrieveCameraImage(
                    context, appCompatActivity,
                    REQUEST_REPLACE_IMAGE_1_CAMERA, resultCode);

        } else if (requestCode == REQUEST_REPLACE_IMAGE_1_GALLERY && resultCode == RESULT_OK) {

            retrieveGalleryImage(
                    context, data, REQUEST_REPLACE_IMAGE_1_GALLERY, resultCode);

            // retrieve picked image from gallery activity

        } else if (requestCode == REQUEST_REPLACE_IMAGE_2_CAMERA && resultCode == RESULT_OK) {

            retrieveCameraImage(
                    context, appCompatActivity,
                    REQUEST_REPLACE_IMAGE_2_CAMERA, resultCode);

            // change image

        } else if (requestCode == REQUEST_REPLACE_IMAGE_2_GALLERY && resultCode == RESULT_OK) {

            retrieveGalleryImage(
                    context, data, REQUEST_REPLACE_IMAGE_2_GALLERY, resultCode);

            // retrieve picked image from gallery activity

        } else if (requestCode == REQUEST_REPLACE_IMAGE_3_CAMERA && resultCode == RESULT_OK) {

            retrieveCameraImage(
                    context, appCompatActivity,
                    REQUEST_REPLACE_IMAGE_3_CAMERA, resultCode);

            // change image

        } else if (requestCode == REQUEST_REPLACE_IMAGE_3_GALLERY && resultCode == RESULT_OK) {

            retrieveGalleryImage(
                    context, data, REQUEST_REPLACE_IMAGE_3_GALLERY, resultCode);

        } else if (requestCode == REQUEST_ADD_IMAGE_2_CAMERA && resultCode == RESULT_OK) {

            retrieveCameraImage(
                    context, appCompatActivity,
                    REQUEST_ADD_IMAGE_2_CAMERA, resultCode);

            // retrieve taken image from camera activity

        } else if (requestCode == REQUEST_ADD_IMAGE_2_GALLERY && resultCode == RESULT_OK) {

            retrieveGalleryImage(
                    context, data, REQUEST_ADD_IMAGE_2_GALLERY, resultCode);

            // retrieve picked image from gallery activity

        } else if (requestCode == REQUEST_ADD_IMAGE_3_CAMERA && resultCode == RESULT_OK) {

            retrieveCameraImage(
                    context, appCompatActivity,
                    REQUEST_ADD_IMAGE_3_CAMERA, resultCode);

            // retrieve taken image from camera activity

        } else if (requestCode == REQUEST_ADD_IMAGE_3_GALLERY && resultCode == RESULT_OK) {

            retrieveGalleryImage(
                    context, data, REQUEST_ADD_IMAGE_3_GALLERY, resultCode);

            // retrieve picked image from gallery activity

        }

        // change slideDotsPanel appearance according to new number of images and their arrangements

        // initialise ViewPager

        changeSlideDotsPanel(context, slideDotsPanel);

    }

    private void attemptInitGlobalImagesVariableCamera(int REQUEST_CODE, int resultCode, int RESULT_OK, Bitmap bitmap3){

        if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_1_CAMERA && resultCode == RESULT_OK) {

            replaceImage1CameraProtocol2(bitmap3);

        }

        if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_2_CAMERA && resultCode == RESULT_OK) {

            replaceImage2CameraProtocol2(bitmap3);

        }

        if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_3_CAMERA && resultCode == RESULT_OK) {

            replaceImage3CameraProtocol2(bitmap3);
        }

        if (REQUEST_CODE == REQUEST_ADD_IMAGE_2_CAMERA && resultCode == RESULT_OK) {

            addImage2CameraProtocol2(bitmap3);

        }

        if (REQUEST_CODE == REQUEST_ADD_IMAGE_3_CAMERA && resultCode == RESULT_OK) {

            addImage3CameraProtocol2(bitmap3);

        }

        observables.setImage_resources_images(newList);

        // reassemble apartment images array according to newly retrieved apartment images
    }

    private void addImage3CameraProtocol2(Bitmap bitmap3){

        newList = new Bitmap[]{
                observables.getImage_resources_images()[0], observables.getImage_resources_images()[1], bitmap3};

        // change total number and arrangement of images accordingly

    }

    private void addImage2CameraProtocol2(Bitmap bitmap3){

        newList = new Bitmap[]{observables.getImage_resources_images()[0], bitmap3};

        // change total number and arrangement of images accordingly

    }

    private void replaceImage3CameraProtocol2(Bitmap bitmap3){

        newList = new Bitmap[]{observables.getImage_resources_images()[0], observables.getImage_resources_images()[1], bitmap3};

        // change total number and arrangement of images accordingly

    }

    private void replaceImage2CameraProtocol2(Bitmap bitmap3){

        if (observables.getImage_resources_images().length == 2) {

            newList = new Bitmap[]{observables.getImage_resources_images()[0], bitmap3};

        }

        if (observables.getImage_resources_images().length == 3) {

            newList = new Bitmap[]{
                    observables.getImage_resources_images()[0], bitmap3, observables.getImage_resources_images()[2]};

            // change total number and arrangement of images accordingly

        }

    }

    private void replaceImage1CameraProtocol2(Bitmap bitmap3){

        if (observables.getImage_resources_images().length == 1) {

            newList = new Bitmap[]{bitmap3};

        }

        if (observables.getImage_resources_images().length == 2) {

            newList = new Bitmap[]{bitmap3, observables.getImage_resources_images()[1]};

        }

        if (observables.getImage_resources_images().length == 3) {

            newList = new Bitmap[]{bitmap3, observables.getImage_resources_images()[1], observables.getImage_resources_images()[2]};

            // change total number and arrangement of images accordingly

        }

    }

    private void varAddImage3GalleryProtocol(Bitmap bitmap){

        newList = new Bitmap[]{observables.getImage_resources_images()[0], observables.getImage_resources_images()[0], bitmap};

    }

    private void varAddImage2GalleryProtocol(Bitmap bitmap){

        newList = new Bitmap[]{observables.getImage_resources_images()[0], bitmap};

    }

    private void varReplaceImage3GalleryProtocol(Bitmap bitmap){

        newList = new Bitmap[]{observables.getImage_resources_images()[0], observables.getImage_resources_images()[1], bitmap};

    }

    private void varReplaceImage2GalleryProtocol(Bitmap bitmap){

        if (observables.getImage_resources_images().length == 2) {

            newList = new Bitmap[]{observables.getImage_resources_images()[0], bitmap};

        }

        if (observables.getImage_resources_images().length == 3) {

            newList = new Bitmap[]{observables.getImage_resources_images()[0], bitmap, observables.getImage_resources_images()[2]};

        }

    }

    private void varReplaceImage1GalleryProtocol(int REQUEST_CODE, int resultCode, int RESULT_OK, Bitmap bitmap){

        if (REQUEST_CODE == REQUEST_REPLACE_IMAGE_1_GALLERY && resultCode == RESULT_OK) {

            if (observables.getImage_resources_images().length == 1) {

                newList = new Bitmap[]{bitmap};

            }

            if (observables.getImage_resources_images().length == 2) {

                newList = new Bitmap[]{bitmap, observables.getImage_resources_images()[1]};

            }

            if (observables.getImage_resources_images().length == 3) {

                newList = new Bitmap[]{bitmap, observables.getImage_resources_images()[1], observables.getImage_resources_images()[2]};

            }

        }

    }

    private void showActivityInfoToast(Context context){

        Toast.makeText(context, context.getResources().getString(R.string.scroll_down_edit_apartement), Toast.LENGTH_SHORT).show();

    }

    private void initPricePerNightNumPeopleEditTexts (final Context context){

        initPricePerNightEditText(context);

        initCurrencyEditText();

        initPerNightTextViewOnClickListener();

        initNumPeopleEditText(context);

    }

    private void initialiseDescriptionEditText(
            Context context){

        letter_count_textView.setVisibility(View.INVISIBLE);

        description_description.setText(descriptionFromIntent);

        descInitLayoutWhenFetchingData(context);

        descInitLayoutWhenDataFetched(context);

        setDescriptionDescriptionTextWatcher();

    }

    private void descInitLayoutWhenDataFetched(Context context){

        if (!description_title.equals(context.getResources().getString(R.string.loading))){

            MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

            // if description title is done loading, hide progress layout

        }

    }

    private void descInitLayoutWhenFetchingData(Context context){

        if (description_title.equals(context.getResources().getString(R.string.loading))){

            progress_layout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);

            // while title of description is loading, make loading screen visible and make scrollView (main layout) invisible

        }

    }

    @Override
    public void initialiseSwipeDotsLayout(Context context) {

        if (observables.getApartement1String().equals("")){

            initSwipeDotsForZeroImages(context);

        }

        if (!observables.getApartement1String().equals("")){

            initSwipeDotsForOneImage(context);

        }

        // if an image can be found, show the image and remove slideDotsPanel

        if (!observables.getApartement2String().equals("")){

            initSwipeDotsLayoutForTwoImages(context);

        }

        // if two images are found, show these images,
        // change slideDotsPanel accordingly and set first circle of slideDotsPanel to selected

        if (!observables.getApartement3String().equals("")){

            initSwipeDotsForThreeImages(context);

        }

        reorganiseSlideDotsPanel(context);

    }

    @Override
    public void initialiseSwipeDotsLayout2(Context context) {

        reInitialiseSwipeDotsLayout(context);

        reorganiseSlideDotsPanel(context);

    }

    private void reorganiseSlideDotsPanel(Context context){

        slideDotsPanel.removeAllViews();

        reorganiseSlideDotsIfOnlyOneImagePresent();
        reorganiseSlideDotsIfMoreThanOneImagePresent(context);

    }

    private void reorganiseSlideDotsIfOnlyOneImagePresent(){

        if (customSwipeLayout.getCount() == 1) {

            slideDotsPanel.removeAllViews();

        }

    }

    private void reorganiseSlideDotsIfMoreThanOneImagePresent(Context context){

        if (customSwipeLayout.getCount() > 1) {

            for (int i = 0; i < dotsCount; i++) {

                dots[i] = new ImageView(context);
                dots[i].setImageResource(R.drawable.circle_outline_transparent);

                // create new imageView and set imageView to circle outline

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

                // create linearLayout params of 20sp by 20sp

                params.setMargins(8, 0, 8, 0);

                // give params 8sp margin from the left and right

                showDot(context, i, params);

            }
        }

    }

    private void showDot(Context context, int i, LinearLayout.LayoutParams params){

        slideDotsPanel.addView(dots[i], params);

        // add dot to slideDotsPanel along with linearLayout parameters

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_selected));

        // fill dot at index of currently viewed image in a gray color

    }

    private void initSwipeDotsForThreeImages(Context context){

        Bitmap apartement1 = StartupMethods.StringToBitMap(observables.getApartement1String());
        Bitmap apartement2 = StartupMethods.StringToBitMap(observables.getApartement2String());
        Bitmap apartement3 = StartupMethods.StringToBitMap(observables.getApartement3String());

        // convert string objects of first, second and third apartment images to Bitmap objects

        observables.setImage_resources_images(new Bitmap[]{apartement1, apartement2, apartement3});

        // insert Bitmap objects into global image resources array

        reInitialiseSwipeDotsLayout(context);

    }

    private void initSwipeDotsLayoutForTwoImages(Context context){

        Bitmap apartement1 = StartupMethods.StringToBitMap(observables.getApartement1String());
        Bitmap apartement2 = StartupMethods.StringToBitMap(observables.getApartement2String());

        // convert string objects of first and second apartment images to Bitmap objects

        observables.setImage_resources_images(new Bitmap[]{apartement1, apartement2});

        // insert Bitmap objects into global image resources array

        reInitialiseSwipeDotsLayout(context);

    }

    private void initSwipeDotsForOneImage(Context context){

        Bitmap apartement1 = StartupMethods.StringToBitMap(observables.getApartement1String());

        // convert string of first apartment image to Bitmap object

        observables.setImage_resources_images(new Bitmap[]{apartement1});

        // insert one image into image_resources_images array

        reInitialiseSwipeDotsLayout(context);

    }
    private void reInitialiseSwipeDotsLayout(Context context){

        customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images());
        viewPager.setAdapter(customSwipeLayout);

        // initialise viewpager

        dotsCount = customSwipeLayout.getCount();
        dots = new ImageView[dotsCount];

        // create amount of dots according to amount of pictures in customSwipeLayout

    }

    private void initSwipeDotsForZeroImages(Context context){

        observables.setImage_resources_images(
                new Bitmap[]{BitmapFactory.decodeResource(context.getResources(), R.drawable.image_loading_error)});

        // set first and only image of apartment item as an image that displays the text 'error loading images'

        customSwipeLayout = new CustomSwipeLayout(context, observables.getImage_resources_images() );
        viewPager.setAdapter(customSwipeLayout);

        // initialise viewpager

        setViewPagerOnPageChangedListener(context);

        dotsCount = customSwipeLayout.getCount();
        dots = new ImageView[dotsCount];

        // create amount of dots according to amount of pictures in customSwipeLayout

    }

    private void getApartmentImages(AppCompatActivity appCompatActivity){

        SharedPreferences prefs = appCompatActivity.getSharedPreferences("apartementImagesTemp", MODE_PRIVATE);

        observables.setApartement1String(prefs.getString("apartement1", ""));

        // load first apartment image

        try {

            observables.setApartement2String(prefs.getString("apartement2", ""));

        } catch (Exception e){

            // Do nothing

        }

        try {

            observables.setApartement3String(prefs.getString("apartement3", ""));

        } catch (Exception e){

            // Do nothing

        }

    }

    private void setDataToTextViews(){

        description_description.setText(descriptionFromIntent);
        description_title.setText(titleFromIntent);

        num_people_per_stay_num_editText.setText(observables.getIsTwoPeopleAllowed());
        price_per_nights_editText.setText(observables.getPrice_per_night());

    }

    private void getDataFromPrevActivity(AppCompatActivity appCompatActivity){

        descriptionFromIntent = appCompatActivity.getIntent().getStringExtra("description");
        titleFromIntent = appCompatActivity.getIntent().getStringExtra("title");

        observables.setAID(appCompatActivity.getIntent().getStringExtra("AID"));
        observables.setLocationID(appCompatActivity.getIntent().getStringExtra("locationID"));

        observables.setPrice_per_night(appCompatActivity.getIntent().getStringExtra("price_per_night"));
        observables.setIsTwoPeopleAllowed(appCompatActivity.getIntent().getStringExtra("max_num_people"));

        // retrieve apartment data send from previous activity

    }

}
