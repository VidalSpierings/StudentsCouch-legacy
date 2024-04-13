package com.studentscouch.projectbostonfiles.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.studentscouch.projectbostonfiles.view.viewImplementers.LogInActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.LogInActivityView;

public class LoginActivity extends AppCompatActivity {

    /*

    private EditText
            editText,
            editText2;

    private FloatingActionButton floatingActionButton;

    private ImageView header_image;

    private TextView
            automatic_log_in_textView,
            error_message;

    ObjectAnimator
            anim,
            anim2;

    private LinearLayout
            username_layout,
            password_layout;

    private List<View> viewsInLayout;

    public static boolean isChecked;

    private FrameLayout checkbox3;

    RelativeLayout relativeLayout;

    private ImageView checkboxImage;

    View screen_center;

    private Typeface tp;

    private float
            automatic_log_in_textView_org_pos_x,
            checkbox3_org_pos_x;

    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener authStateListener;

    ProgressBar loading_spinner;

    private FrameLayout progress_layout;

    private Animation
            enter_anim,
            exit_anim;

    private Task<AuthResult> task2;

    private DatabaseReference
            fbRef,
            fbRef2,
            fbRef3,
            fbRef4;

    private String month = "";

    private Toast toast;

    int checked = 2342;

    private String hosStatus;

    List<String> monthNames;

    List<Float> orig_pos_views;

    List<View> editTextLayouts;

    public static String staticMonth = "";

    Float
            password_layout_org_pos_x,
            email_layout_org_pos_x;

    */

    LogInActivityView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_login);

        view = new LogInActivityViewImplementer(this, null);

        setContentView(view.getRootView());

        view.initViews(this, this);
        view.initData(this, this);

        /*

        Firebase.setAndroidContext(this);

        loading_spinner = findViewById(R.id.loading_spinner);

        floatingActionButton = findViewById(R.id.fab);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        checkbox3 = findViewById(R.id.checkBox3);

        checkboxImage = findViewById(R.id.checkboxImage);

        screen_center = findViewById(R.id.screen_center);

        relativeLayout = findViewById(R.id.relativeLayout);

        username_layout = findViewById(R.id.username_layout);
        password_layout = findViewById(R.id.password_layout);
        progress_layout = findViewById(R.id.progress_layout);

        TextView username_textView = findViewById(R.id.username_textView);
        TextView password_textView = findViewById(R.id.password_textView);
        automatic_log_in_textView = findViewById(R.id.automatic_log_in_textView);
        error_message = findViewById(R.id.error_message);

        header_image = findViewById(R.id.header_image);

        // link java variables with xml layout views

        tp = Typeface.createFromAsset(getBaseContext().getAssets(), "project_boston_typeface.ttf");

        username_textView.setTypeface(tp);
        editText.setTypeface(tp);
        editText2.setTypeface(tp);
        password_textView.setTypeface(tp);
        automatic_log_in_textView.setTypeface(tp);

        // initialise and apply typeface to all textViews in layout

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(username_layout);
        viewsInLayout.add(password_layout);
        viewsInLayout.add(automatic_log_in_textView);
        viewsInLayout.add(checkbox3);

        // create and array of views that can be animated

        automatic_log_in_textView_org_pos_x = automatic_log_in_textView.getX();
        checkbox3_org_pos_x = checkbox3.getX();
        email_layout_org_pos_x = editText.getX();
        password_layout_org_pos_x = password_layout.getX();

        orig_pos_views = new ArrayList<>();

        orig_pos_views.add(automatic_log_in_textView_org_pos_x);
        orig_pos_views.add(checkbox3_org_pos_x);
        orig_pos_views.add(password_layout.getX());
        orig_pos_views.add(username_layout.getX());

        editTextLayouts = new ArrayList<>();

        editTextLayouts.add(username_layout);
        editTextLayouts.add(password_layout);

        error_message.setVisibility(View.INVISIBLE);

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        floatingActionButton.hide();

        auth = FirebaseAuth.getInstance();

        enter_anim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.visibility_fade_in_animation);

        // load enter animation

        enter_anim.setDuration(200);

        // set animation duration at 200 milliseconds

        exit_anim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.visibility_fade_out_animation);

        // load exit animation

        exit_anim.setDuration(200);

        // set animation duration at 200 milliseconds

        LoginActivityMethods.animateActivityTransitionLoginActivity(
                checkboxImage, automatic_log_in_textView, viewsInLayout, checkbox3,
                automatic_log_in_textView_org_pos_x, checkbox3_org_pos_x, password_layout, username_layout,
                password_layout_org_pos_x, email_layout_org_pos_x);

        monthNames = new ArrayList<>();

        monthNames.add(getResources().getString(R.string.january));
        monthNames.add(getResources().getString(R.string.february));
        monthNames.add(getResources().getString(R.string.march));
        monthNames.add(getResources().getString(R.string.april));
        monthNames.add(getResources().getString(R.string.may));
        monthNames.add(getResources().getString(R.string.june));
        monthNames.add(getResources().getString(R.string.july));
        monthNames.add(getResources().getString(R.string.august));
        monthNames.add(getResources().getString(R.string.september));
        monthNames.add(getResources().getString(R.string.october));
        monthNames.add(getResources().getString(R.string.november));
        monthNames.add(getResources().getString(R.string.december));

        LoginActivityMethods.setEditTexttextFilters(editText, editText2);

        // prevent user from entering emojis and other unwanted symbols into editText fields

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){

                    // Do nothing

                }

            }
        };

        /*

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            FingerprintManager fingerprintManager = (FingerprintManager) getBaseContext().getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (fingerprintManager.isHardwareDetected()) {

            }
        }

        */

        /*

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editText2.getText().toString().equals("")) {

                    floatingActionButton.show();

                    // show fab when both editTexts have text entered in them

                } else {

                    floatingActionButton.hide();

                    // hide fab when no text is entered in one of two or neither editTexts

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!editText.getText().toString().equals("") && !editText2.getText().toString().equals("")) {

                    floatingActionButton.show();

                    // show fab when both editTexts have text entered in them

                } else {

                    floatingActionButton.hide();

                    // hide fab when no text is entered in one of two or neither editTexts

                }

            }
        });

        LoginActivityMethods.setEmailEditTextTextChangedListener(editText, editText2, floatingActionButton);

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editText.getText().toString().equals("")) {

                    floatingActionButton.show();

                    // show fab when both editTexts have text entered in them

                } else {

                    floatingActionButton.hide();

                    // hide fab when no text is entered in one of two or neither editTexts

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!editText2.getText().toString().equals("") && !editText.getText().toString().equals("")) {

                    floatingActionButton.show();
                    // show fab when both editTexts have text entered in them

                } else {

                    floatingActionButton.hide();

                    // hide fab when no text is entered in one of two or neither editTexts

                }

            }
        });

        LoginActivityMethods.initialiseFloatingActionButton(
                getApplicationContext(), floatingActionButton, checkbox3, editText,
                editText2, error_message, tp, enter_anim, progress_layout,
                exit_anim, auth, task2, LoginActivity.this, fbRef4
                , fbRef3, fbRef2, header_image, hosStatus,
                month, monthNames, toast);

        */

}

    @Override
    protected void onStart() {
        super.onStart();

        view.initOnStartMethods(this);

    }

    @Override
    public void onBackPressed() {

        view.initOnBackPressedData(this);

        super.onBackPressed();

        overridePendingTransition(0, 0);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        /*

        LoginActivityMethods.animateActivityTransitionLoginActivity(
                checkboxImage, automatic_log_in_textView, viewsInLayout, checkbox3,
                automatic_log_in_textView_org_pos_x, checkbox3_org_pos_x, password_layout, username_layout,
                password_layout_org_pos_x, email_layout_org_pos_x);

        */

    }
}
