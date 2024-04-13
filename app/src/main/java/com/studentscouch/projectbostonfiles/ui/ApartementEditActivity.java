package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.studentscouch.projectbostonfiles.models.implementers.ApartmentEditActivityModel;
import com.studentscouch.projectbostonfiles.observables.ApartmentEditActivityObservables;
import com.studentscouch.projectbostonfiles.view.viewImplementers.ApartmentEditActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.ApartmentEditActivityView;

public class ApartementEditActivity extends AppCompatActivity {

    /*

    private EditText
            price_per_nights_editText,
            num_people_per_stay_num_editText;

    private int
            original_tariff,
            original_num_people;

    private TextView
            description_title,
            description_description,
            currency_text,
            per_night_textView,
            num_people_per_stay_desc_textView,
            letter_count_textView,
            save_data_textView;

    public static ViewPager viewPager;

    public static CustomSwipeLayout customSwipeLayout;

    private LinearLayout slideDotsPanel;

    public static ImageView[] dots;

    public static int dotsCount;

    private Animator circularReveal;

    private View
        delete_button_view,
        add_button_view;

    public static CharSequence[] items;

    public static Bitmap[] newList;

    private FrameLayout main_frameLayout;

    public static int

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

    public static Bitmap[] image_resources_images = null;

    private FrameLayout progress_layout;

    private DatabaseReference

            fbRef,
            fbRef2,
            fbRef3;

    private String locationID;

    private String
            apartement1String = "",
            apartement2String = "",
            apartement3String = "";

    String
        descriptionFromIntent,
        titleFromIntent;

    private SharedPreferences.Editor editor;

    public static Uri
            mCapturedImageURI,
            mCapturedImageURI2,
            mCapturedImageURI3,
            mCapturedImageURI4,
            mCapturedImageURI5;

    public static String
            price_per_night,
            isTwoPeopleAllowed;

    */

    ApartmentEditActivityView view;
    ApartmentEditActivityModel model;
    ApartmentEditActivityObservables observables;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observables = new ApartmentEditActivityObservables();

        view = new ApartmentEditActivityViewImplementer(this, null, observables);
        model = new ApartmentEditActivityModel(observables);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

        /*

        price_per_nights_editText = findViewById(R.id.price_per_night_editText);
        num_people_per_stay_num_editText = findViewById(R.id.num_people_per_stay_num_editText);

        description_title = findViewById(R.id.description_title);
        description_description = findViewById(R.id.description_description);
        currency_text = findViewById(R.id.currency_text);
        per_night_textView = findViewById(R.id.per_night_textView);
        num_people_per_stay_desc_textView = findViewById(R.id.num_people_per_stay_desc_textView);
        letter_count_textView = findViewById(R.id.letter_count_textView);
        save_data_textView = findViewById(R.id.save_data_textView);

        slideDotsPanel = findViewById(R.id.slideDotsPanel);

        ScrollView scrollView = findViewById(R.id.scrollView);

        delete_button_view = findViewById(R.id.delete_button_view);
        add_button_view = findViewById(R.id.add_buton_view);

        final ImageView add_imageView = findViewById(R.id.add_imageView);
        ImageView delete_imageView = findViewById(R.id.delete_imageView);

        viewPager = findViewById(R.id.apartement_images_imageView);

        main_frameLayout = findViewById(R.id.main_frameLayout);
        FrameLayout save_data_layout = findViewById(R.id.save_data_layout);
        progress_layout = findViewById(R.id.progress_layout);

        viewPager = findViewById(R.id.apartement_images_imageView);

        // connect Java variables with xml layout views

        original_tariff = Integer.valueOf(price_per_nights_editText.getText().toString());

        original_num_people = Integer.valueOf(price_per_nights_editText.getText().toString());

        // convert string objects to integer objects

        // assign number of people allowed per night and price per night to according textViews

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        description_title.setTypeface(tp);
        description_description.setTypeface(tp);
        currency_text.setTypeface(tp);
        price_per_nights_editText.setTypeface(tp);
        per_night_textView.setTypeface(tp);
        num_people_per_stay_desc_textView.setTypeface(tp);
        num_people_per_stay_num_editText.setTypeface(tp);
        save_data_textView.setTypeface(tp);

        // initialise and set typeface to all textViews and editTexts

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.scroll_down_edit_apartement), Toast.LENGTH_SHORT).show();

        // show toast informing user to scroll down and press the save button to save made changes

        Firebase.setAndroidContext(this);

        SharedPreferences prefs = this.getSharedPreferences("apartementImagesTemp", Context.MODE_PRIVATE);

        editor = getSharedPreferences("apartementImagesTemp", MODE_PRIVATE).edit();

        // load editor that will pass apartment images to next activity

        descriptionFromIntent = getIntent().getStringExtra("description");
        titleFromIntent = getIntent().getStringExtra("title");

        String AID = getIntent().getStringExtra("AID");
        locationID = getIntent().getStringExtra("locationID");

        price_per_night = getIntent().getStringExtra("price_per_night");
        isTwoPeopleAllowed = getIntent().getStringExtra("max_num_people");

        // retrieve apartment data send from previous activity

        description_description.setText(descriptionFromIntent);
        description_title.setText(titleFromIntent);

        num_people_per_stay_num_editText.setText(isTwoPeopleAllowed);
        price_per_nights_editText.setText(price_per_night);

        // set description and title of apartment object to arguments passed from previous activity

        apartement1String = prefs.getString("apartement1", "");

        // load first apartment image

        try {

            apartement2String = prefs.getString("apartement2", "");

        } catch (Exception e){

            // Do nothing

        }

        try {

            apartement3String = prefs.getString("apartement3", "");

        } catch (Exception e){

            // Do nothing

        }

        // load second and third apartment images if possible

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID + "/locationID");

        fbRef2.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                locationID = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // retrieve Apartment PlaceID

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID);

        fbRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "Apartements/" + AID + "/apartement1");

        fbRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "items/" + locationID + "/" + AID);

        // initialise more FireBase links

        Animation enter_anim = AnimationUtils.loadAnimation(ApartementEditActivity.this, R.anim.visibility_fade_in_animation);

        // set fade in animation as enter animation

        Animation exit_anim = AnimationUtils.loadAnimation(ApartementEditActivity.this, R.anim.visibility_fade_out_animation);

        // set fade out animation as exit animation

        enter_anim.setDuration(200);

        exit_anim.setDuration(200);

        // set duration for both enter and exit animation at 200 milliseconds

        progress_layout.startAnimation(enter_anim);

        progress_layout.setVisibility(View.VISIBLE);

        // show loading screen

        Bitmap apartment1 = null;

        image_resources_images = new Bitmap[0];

        ApartmentEditActivityMethods.initialiseSwipeDotsLayout(
                getApplicationContext(), apartement1String, apartement2String, apartement3String,
                slideDotsPanel);

        // change slideDotsPanel according to number of images retrieved

        ApartmentEditActivityMethods.initialiseDescriptionEditText(
                getApplicationContext(), description_title, progress_layout, scrollView,
                exit_anim, description_description, letter_count_textView, descriptionFromIntent);

        // initialise description editText with textWatcher that detects when user has crossed max num. Characters limit

        ApartmentEditActivityMethods.initialiseSaveDataButton(
                getApplicationContext(), save_data_layout, fbRef,
                fbRef3, description_title, description_description, num_people_per_stay_num_editText,
                price_per_nights_editText, editor);

        // initialise save data button, pressing button will save any made changes

        ApartmentEditActivityMethods.initialisePricePerNightEditTexts(
                getApplicationContext(), price_per_nights_editText, original_tariff, currency_text,
                price_per_nights_editText, num_people_per_stay_num_editText, original_num_people);

        // initialise price_per_night editText and add onClickListeners to surrounding textViews

        add_imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

                view.removeOnLayoutChangeListener(this);

                ApartmentEditActivityMethods.setAddImageOnClickListener(
                        getApplicationContext(), add_imageView, add_button_view,
                        ApartementEditActivity.this, delete_button_view,
                        REQUEST_REPLACE_IMAGE_1_CAMERA, REQUEST_REPLACE_IMAGE_1_GALLERY,
                        REQUEST_REPLACE_IMAGE_2_CAMERA, REQUEST_REPLACE_IMAGE_2_GALLERY, REQUEST_REPLACE_IMAGE_3_CAMERA,
                        REQUEST_REPLACE_IMAGE_3_GALLERY, REQUEST_ADD_IMAGE_2_CAMERA, REQUEST_ADD_IMAGE_2_GALLERY,
                        REQUEST_ADD_IMAGE_3_CAMERA, REQUEST_ADD_IMAGE_3_GALLERY, mCapturedImageURI2,
                        mCapturedImageURI3, mCapturedImageURI4, mCapturedImageURI5);

            }
        });

        delete_imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (image_resources_images != null) {

                        StartupMethods.startCircularRevealAnimationDefaultStartRadius(getApplicationContext(), delete_button_view, false);

                        // start circular reveal animation for delete button reveal view

                            if (image_resources_images.length == 1) {

                                Snackbar snackbar = Snackbar
                                        .make(main_frameLayout, getResources().getString(R.string.select_add), Snackbar.LENGTH_LONG);
                                snackbar.show();

                                // if user wants to delete their one and only apartment image, inform the user that they can replace this photo by pressing the plus icon

                            } else if (image_resources_images.length == 2) {

                                items = new CharSequence[]{
                                        "Delete image 1", "Delete image 2"};

                            } else if (image_resources_images.length == 3) {

                                items = new CharSequence[]{
                                        "Delete image 1", "Delete image 2", "Delete image 3"};

                            }

                            // show options for deleting images according to amount of retrieved images

                        final AlertDialog.Builder builder = new AlertDialog.Builder(ApartementEditActivity.this);
                        builder.setTitle(getResources().getString(R.string.add_image));

                        // build dialog and set dialog title

                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {

                                add_button_view.setVisibility(View.INVISIBLE);
                                delete_button_view.setVisibility(View.INVISIBLE);

                            }
                        });

                        // show dialog

                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                    if (items[i].equals("Delete image 1")) {

                                        dialogInterface.dismiss();
                                        delete_button_view.setVisibility(View.INVISIBLE);

                                        // make delete button reveal view invisible

                                        if (image_resources_images.length == 3) {

                                            newList = new Bitmap[]{null, null};

                                            image_resources_images[0] = image_resources_images[1];

                                            image_resources_images[1] = image_resources_images[2];

                                            image_resources_images[2] = null;

                                            newList[0] = image_resources_images[0];

                                            newList[1] = image_resources_images[1];

                                            image_resources_images = new Bitmap[]{newList[0], newList[1]};

                                            // update images array list by creating new list with new correct image ordering, then inserting those values back in the original array

                                        } else if (image_resources_images.length == 2) {

                                            newList = new Bitmap[]{null, null};

                                            image_resources_images[0] = image_resources_images[1];

                                            image_resources_images[1] = null;

                                            newList[0] = image_resources_images[0];

                                            image_resources_images = new Bitmap[]{newList[0]};

                                            // update images array list by creating new array list with new correct image ordering, then inserting those values back in the original array

                                        }

                                        StartupMethods.initialiseSwipeDotsLayoutEdit(
                                                getApplicationContext(), image_resources_images, apartement1String,
                                                apartement2String, apartement3String, customSwipeLayout,
                                                dots, dotsCount, viewPager, slideDotsPanel);

                                        // re-initialise swipe dots layout

                                    } else if (items[i].equals("Delete image 2")) {

                                        delete_button_view.setVisibility(View.INVISIBLE);

                                        // make delete button reveal view invisible

                                        if (image_resources_images.length == 3) {

                                            newList = new Bitmap[]{null, null};

                                            image_resources_images[1] = image_resources_images[2];

                                            image_resources_images[2] = null;

                                            newList[0] = image_resources_images[0];

                                            newList[1] = image_resources_images[1];

                                            image_resources_images = new Bitmap[]{newList[0], newList[1]};

                                            // update images array list by creating new list with new correct image ordering, then inserting those values back in the original array

                                        } else if (image_resources_images.length == 2) {

                                            newList = new Bitmap[]{null, null};

                                            newList[0] = image_resources_images[0];

                                            image_resources_images = new Bitmap[]{newList[0]};

                                            // update images array list by creating new array list with new correct image ordering, then inserting those values back in the original array

                                        }

                                        StartupMethods.initialiseSwipeDotsLayoutEdit(
                                                getApplicationContext(), image_resources_images, apartement1String,
                                                apartement2String, apartement3String, customSwipeLayout,
                                                dots, dotsCount, viewPager, slideDotsPanel);

                                        // re-initialise swipe dots layout

                                    } else if (items[i].equals("Delete image 3")) {

                                        newList = new Bitmap[]{null, null};

                                        dialogInterface.dismiss();
                                        image_resources_images[2] = null;
                                        delete_button_view.setVisibility(View.INVISIBLE);

                                        newList[0] = image_resources_images[0];

                                        newList[1] = image_resources_images[1];

                                        image_resources_images = new Bitmap[]{newList[0], newList[1]};

                                        // update images array list by creating new list with new correct image ordering, then inserting those values back in the original array

                                        customSwipeLayout = new CustomSwipeLayout(getApplicationContext(), image_resources_images);
                                        viewPager.setAdapter(customSwipeLayout);

                                        // assign new swipeLayout viewPager

                                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                            @Override
                                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                            }

                                            @Override
                                            public void onPageSelected(int position) {

                                                for (int i = 0; i < dotsCount; i++) {

                                                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_outline_transparent));

                                                    // create unselected dots and add this dot for number of retrieved images

                                                }

                                                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_selected));

                                                // create selected dot and assign this dot at index in slideDotsPanel according to index of currently selected image

                                            }

                                            @Override
                                            public void onPageScrollStateChanged(int state) {

                                            }
                                        });

                                        dotsCount = customSwipeLayout.getCount();
                                        dots = new ImageView[dotsCount];

                                        // create new amount of imagesViews according to number of pages (images) in customSwipeLayout

                                        if (customSwipeLayout.getCount() == 1) {

                                            slideDotsPanel.removeAllViews();

                                            // if only one image is retrieved, remove slideDotsPanel from layout

                                        } else {

                                            slideDotsPanel.removeAllViews();

                                            // if only one image is retrieved, remove slideDotsPanel from layout

                                            for (int ii = 0; ii < dotsCount; ii++) {

                                                dots[ii] = new ImageView(getApplicationContext());
                                                dots[ii].setImageResource(R.drawable.circle_outline_transparent);

                                                // create new unselected dot

                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

                                                // create new dot with 20sp by 20sp

                                                params.setMargins(8, 0, 8, 0);

                                                // create margin 8sp from the left and 8sp from the right

                                                slideDotsPanel.addView(dots[ii], params);

                                                // add dot to slideDotsPanel with recently created layout params to slideDotsPanel

                                                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_selected));

                                                // set first dot as selected

                                            }

                                        }


                                    }

                                    // initialise dialog options, change arrangement images accordingly upon item selection

                                /*

                                customSwipeLayout = new CustomSwipeLayout(getApplicationContext(), image_resources_images);
                                viewPager.setAdapter(customSwipeLayout);

                                // initialise viewPager

                                dotsCount = customSwipeLayout.getCount();
                                dots = new ImageView[dotsCount];

                                // create new amount of imagesViews according to number of pages (images) in customSwipeLayout

                                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {

                                        for (int i = 0; i < dotsCount; i++) {

                                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_outline_transparent));

                                            // create unselected dots and add this dot for number of retrieved images

                                        }

                                        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_selected));

                                        // create selected dot and assign this dot at index in slideDotsPanel according to index of currently selected image

                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });

                                // re-instantiate slideDotsPanel after item selected

                                if (customSwipeLayout.getCount() == 1) {

                                    // Do nothing

                                } else {

                                    slideDotsPanel.removeAllViews();

                                    // if only one image is retrieved, remove slideDotsPanel from layout

                                    for (int ii = 0; ii < dotsCount; ii++) {

                                        dots[ii] = new ImageView(getApplicationContext());
                                        dots[ii].setImageResource(R.drawable.circle_outline_transparent);

                                        // create new unselected dot

                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

                                        // create new dot with 20sp by 20sp

                                        params.setMargins(8, 0, 8, 0);

                                        // create margin 8sp from the left and 8sp from the right

                                        slideDotsPanel.addView(dots[ii], params);

                                        // add dot to slideDotsPanel with recently created layout params to slideDotsPanel

                                        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_selected));

                                        // set first dot as selected

                                    }

                                }

                                */

                                // show slideDotsPanel if number of images is greater than 1

                                /*

                                }

                        });

                        StartupMethods.initialiseSwipeDotsLayoutEdit(
                                getApplicationContext(), image_resources_images, apartement1String,
                                apartement2String, apartement3String, customSwipeLayout,
                                dots, dotsCount, viewPager, slideDotsPanel);

                            if (image_resources_images.length == 1) {

                                delete_button_view.setVisibility(View.INVISIBLE);

                                // prevent user from deleting their one and only apartment image

                            } else if (image_resources_images.length == 2) {

                                builder.show();

                                // show deletion options to user

                            } else if (image_resources_images.length == 3) {

                                builder.show();

                                // show deletion options to user

                            }

                            // if only one image is added, prevent user from deleting images

                    }

                }
                return true;
            }

        });

        */

    }

    @Override
    protected void onResume() {
        super.onResume();

        view.makeRevealViewsInvisible();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        view.retrieveInstantiatedPicture(this, this, requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);

        view.makeRevealViewsInvisible();

        model.clearApartmentImages(this);
        // get rid of new changes made to apartment data

    }

}
