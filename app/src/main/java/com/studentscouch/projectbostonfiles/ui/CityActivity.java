package com.studentscouch.projectbostonfiles.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.studentscouch.projectbostonfiles.db.interfaces.CityActivityDBInterface;
import com.studentscouch.projectbostonfiles.view.viewImplementers.CityActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.CityActivityView;

public class CityActivity extends AppCompatActivity {

    /*

    public static String cityName;

    private static ImageView backgroundImage;

    private static RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private ArrayList<CityActivityInformation> information;

    private static ObjectAnimator anim;

    float recyclerview_org_pos_y = 0;

    private String
            UID = null,
            AID = null;

    private FrameLayout progress_layout;

    private Animation exit_anim_progress_layout;

    private FrameLayout no_listings_frameLayout;

    private int isVisible = 0;

    private static String placeID;

    static String countryName;

    static Bitmap imageBitmap;

    //protected GeoDataClient mGeoDataClient;

    public static Bitmap image2;

    private Bitmap image;

    boolean isLoaded;

    private static Context mContext;

    private static float recyclerView_org_pos_y;

    // https://stackoverflow.com/questions/38418407/how-to-use-google-places-api-to-search-for-addresses-places-android

    */

    CityActivityView view;
    CityActivityDBInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new CityActivityViewImplementer(this, null);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

        /*

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.databaseLink + "/test");

        ref.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                ref.child("test").setValue("test");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        */

        /*

        final TextView no_listings_textView = findViewById(R.id.no_listings_textView);

        no_listings_frameLayout = findViewById(R.id.no_listings_frameLayout);

        FrameLayout activity_city = findViewById(R.id.activity_city);

        backgroundImage = findViewById(R.id.backgroundImage);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setVisibility(View.INVISIBLE);

        progress_layout = findViewById(R.id.progress_layout);

        // connect Java variables to xml layout views

        Firebase.setAndroidContext(getApplicationContext());

        // enable fireBase in this activity

        recyclerView_org_pos_y = recyclerView.getY();

        // save current position of relativeLayout to local variable

        mContext = getApplicationContext();

        Animation enter_anim_progress_layout = AnimationUtils.loadAnimation(CityActivity.this, R.anim.visibility_fade_in_animation);

        exit_anim_progress_layout = AnimationUtils.loadAnimation(CityActivity.this, R.anim.visibility_fade_out_animation);

        enter_anim_progress_layout.setDuration(200);

        exit_anim_progress_layout.setDuration(200);

      //  mGeoDataClient = Places.getGeoDataClient(CityActivity.this, null);

        progress_layout.startAnimation(enter_anim_progress_layout);

        progress_layout.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        no_listings_textView.setTypeface(tp);

        // initialise typeface and set it to the no_listings_textView textView

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);

        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_lowres));

        // add transparent item divider

        recyclerView.addItemDecoration(itemDecorator);

        // add completely transparent Item decorater view inbetween every item

        if (intent != null) {

            SharedPreferences prefs = getSharedPreferences("background_image", MODE_PRIVATE);
            String bitmap = prefs.getString("string", null);
            String bitmap2 = prefs.getString("string2", null);

            // retrieve image background in Base64 encoded String format, assign to local String object

            countryName = getIntent().getStringExtra("country");
            placeID = getIntent().getStringExtra("placeID");
            cityName = intent.getStringExtra("cityName");

            // retrieve city data passed from previous activity, set city and country name

            if (placeID.equals(StartupMethods.AMS_PLACE_ID)){

                activity_city.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        // wait until layout is completely loaded and layout dimensions of all listItem are known

                        image2 = BitmapFactory.decodeResource(getResources(), R.drawable.amsterdam_png);

                        // set background image of Amsterdam saved in project as background

                    }
                });

                // if placeID is equal to Amsterdam, set a custom image

            } else if (placeID.equals(StartupMethods.ROTTERDAM_PLACE_ID)){

                activity_city.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //At this point the layout is complete and the
                        //dimensions of myView and any child views are known.

                        image2 = BitmapFactory.decodeResource(getResources(), R.drawable.rotterdam);

                        // set background image of Rotterdam saved in project as background

                    }
                });

                // if placeID is equal to Rotterdam, set a custom image

            } else {

                image2 = StartupMethods.StringToBitMap(bitmap2);

                // set header image to bitmap variable, this is the image on the second index of the placeID/city


            }

            // ----- set activity background -----

            bitmap = prefs.getString("string", null);

            image = StartupMethods.StringToBitMap(bitmap);

            backgroundImage.setImageBitmap(image);

            // load and set background image of current city

            activity_city.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //At this point the layout is complete and the
                    //dimensions of myView and any child views are known.

                    backgroundImage.setImageBitmap(image);

                    // set background image

                }
            });

        }

        final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(StartupMethods.DATABASE_LINK + "/items/" + placeID);

        // DB link to items with same PlaceID as selected apartments (Amsterdam apartments, Rotterdam apartments, etc.)

        information = new ArrayList<>();

        CityActivityMethods.retrieveApartmentItems(
                getApplicationContext(), fbRef, information, progress_layout,
                isLoaded, exit_anim_progress_layout, anim, UID, AID,
                recyclerview_org_pos_y, recyclerView, no_listings_frameLayout);

        /*

        information.add(new CityActivityInformation(

                R.drawable.apartement_photo_1,
                R.drawable.ian_200,
                25,
                "",
                "",
                2.5

        ));

        information.add(new CityActivityInformation(

                R.drawable.apartement_photo_2,
                R.drawable.ian2_200,
                37,
                "",
                "",
                5

        ));

        information.add(new CityActivityInformation(

                R.drawable.apartement_photo_3,
                R.drawable.ian3_200,
                15,
                "",
                "",
                4

        ));

        information.add(new CityActivityInformation(

                R.drawable.apartement_photo_1,
                R.drawable.ian_200,
                25,
                "",
                "",
                2.5

        ));

        information.add(new CityActivityInformation(

                R.drawable.apartement_photo_2,
                R.drawable.ian2_200,
                37,
                "",
                "",
                5

        ));

        information.add(new CityActivityInformation(

                R.drawable.apartement_photo_3,
                R.drawable.ian3_200,
                15,
                "",
                "Simon",
                4

        ));

        */

        /*

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // set status bar color if device version is equal to lollipop or above

        */

    }

    @Override
    protected void onStart() {
        super.onStart();

        view.makeProgressLayoutInvisibleIfLoadingFinished();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        view.animateActivityReEnterTransition(getApplicationContext(), this);

    }

    @Override
    public void onBackPressed() {

        view.makeBackgroundUIinvisible();

        super.onBackPressed();

        overridePendingTransition(0, 0);

        // make 'no listings available' framelayout disappear upon leaving activity using onBackPressed()

    }

}
