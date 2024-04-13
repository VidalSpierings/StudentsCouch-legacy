package com.studentscouch.projectbostonfiles.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.RegisterActivityYesScreen3Methods;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivityYesScreen3 extends AppCompatActivity {

    private FloatingActionButton fab;

    private TextView
            additional_info_textView,
            error_message,
            letter_count_textView;

    private TextView
            firstName_textView,
            lastName_textView,
            userName_textView,
            password_textView,
            currency_textView;

    private TextView
            one_textView,
            two_textView;

    private EditText
            firstName_editText,
            lastName_editText,
            userName_editText;

    private View
            one_layout_view,
            two_layout_view;

    private FrameLayout
            one_layout,
            two_layout;

    private LinearLayout
            userName_layout,
            lastName_layout,
            firstName_layout,
            password_layout;

    private List<View> viewsInLayout;

    private Float
            firstName_layout_org_pos_x,
            lastName_layout_org_pos_x,
            userName_layout_org_pos_x,
            password_layout_org_pos_x;

    private Bundle extras = null;

    private String
            countryName,
            countryCode;

    private List <View> animatingViews;

    private List<Float> orig_pos;

    private List <View> fadingView;

    private Typeface tp;

    private TextWatcher textWatcher;

    public static int isTwoPeopleAllowed = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_yes_screen3);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_scroll), Toast.LENGTH_LONG).show();

        linkXmlElementsWithJavaVariables();

        setTypefaces();

        getOrigPosLayoutElements();

        initArrays();

        makeExcessCharactersCountViewInvisible();

        // animate activity enter transition animations

        getInfoFromPreviousActivity();

        RegisterActivityYesScreen3Methods.initialiseFabOnClickListener(
                getApplicationContext(), fab, userName_editText, error_message,
                lastName_editText, firstName_editText,
                one_layout, two_layout, countryName, countryCode,
                extras, animatingViews,
                tp, RegisterActivityYesScreen3.this, fadingView
        );

        // initialise fab onClickListener

        initTextWatcher();

        applyTextWatcherToAllEditTexts();

        StudentsCouchAnimations.onRestartAnimationsNoButtons(
                getApplicationContext(), animatingViews, orig_pos, viewsInLayout, fadingView, 400);

    }





    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        enableLayoutInteraction();

        RegisterActivityYesScreen3Methods.initialiseOneButtonSelected(
                getApplicationContext(), one_layout, one_layout_view, two_layout_view,
                firstName_editText, lastName_editText, userName_editText, fab);

        RegisterActivityYesScreen3Methods.initialiseFabOnClickListener(
                getApplicationContext(), fab, userName_editText, error_message, lastName_editText,
                firstName_editText, one_layout,
                two_layout, countryName, countryCode, extras,
                animatingViews, tp, RegisterActivityYesScreen3.this,
                fadingView);

        // initialise fab onClickListener (start next activity and pass all submitted data to it)

        RegisterActivityYesScreen3Methods.initTwoButtonSelected(
                two_layout, firstName_editText, lastName_editText,
                userName_editText, one_layout_view, two_layout_view, fab);

        // register '2 people' button click

        RegisterActivityYesScreen3Methods.checkIfInfoEntered(
                getApplicationContext(), firstName_editText, lastName_editText, userName_editText,
                fab, one_layout_view, two_layout_view);

        // check if info is entered in all editTexts

        retrievePreviouslyEnteredInfo();

    }

    protected void onRestart() {

        super.onRestart();

        StudentsCouchAnimations.onRestartAnimationsNoButtons(
                getApplicationContext(), animatingViews, orig_pos, viewsInLayout, fadingView, 400);

        // activity re-enter animation method

    }

    private void linkXmlElementsWithJavaVariables(){

        lastName_textView = findViewById(R.id.lastName_textView);
        firstName_textView = findViewById(R.id.firstName_textView);
        userName_textView = findViewById(R.id.userName_textView);
        password_textView = findViewById(R.id.password_textView);
        additional_info_textView = findViewById(R.id.additional_info_textView);
        one_textView = findViewById(R.id.one_textView);
        two_textView = findViewById(R.id.two_textView);
        letter_count_textView = findViewById(R.id.letter_count_textView);
        currency_textView = findViewById(R.id.currency_textView);
        error_message = findViewById(R.id.error_message);

        fab = findViewById(R.id.fab);

        firstName_editText = findViewById(R.id.firstName_edittext);
        lastName_editText = findViewById(R.id.lastName_edittext);
        userName_editText = findViewById(R.id.userName_editText);

        userName_layout = findViewById(R.id.userName_layout);
        lastName_layout = findViewById(R.id.lastName_layout);
        firstName_layout = findViewById(R.id.firstName_layout);
        password_layout = findViewById(R.id.password_layout);

        one_layout = findViewById(R.id.log_in_layout);
        two_layout = findViewById(R.id.two_layout_view);

        one_layout_view = findViewById(R.id.one_layout_view);
        two_layout_view = findViewById(R.id.log_in_layout_view);

        // Lik java variables with xml layout views

    }

    private void setTypefaces(){

        tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");

        lastName_textView.setTypeface(tp);
        firstName_textView.setTypeface(tp);
        userName_textView.setTypeface(tp);
        password_textView.setTypeface(tp);
        additional_info_textView.setTypeface(tp);
        one_textView.setTypeface(tp);
        two_textView.setTypeface(tp);
        currency_textView.setTypeface(tp);

        firstName_editText.setTypeface(tp);
        lastName_editText.setTypeface(tp);
        userName_editText.setTypeface(tp);

        // initialise and set typeface to all textViews and editTexts in layout

    }

    private void initArrays(){

        initViewsInLayoutArray();

        initAnimatingViewsArray();

        initOrigPosArray();

        initFadingViewArray();

    }
    private void initViewsInLayoutArray(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(firstName_layout);
        viewsInLayout.add(lastName_layout);
        viewsInLayout.add(userName_layout);
        viewsInLayout.add(password_layout);

    }

    private void initAnimatingViewsArray(){

        animatingViews = new ArrayList<>();

        animatingViews.add(firstName_layout);
        animatingViews.add(lastName_layout);
        animatingViews.add(userName_layout);
        animatingViews.add(password_layout);

    }

    private void initOrigPosArray(){

        orig_pos = new ArrayList<>();

        orig_pos.add(firstName_layout_org_pos_x);
        orig_pos.add(lastName_layout_org_pos_x);
        orig_pos.add(userName_layout_org_pos_x);
        orig_pos.add(password_layout_org_pos_x);

    }

    private void initFadingViewArray(){

        fadingView = new ArrayList<>();

        fadingView.add(additional_info_textView);

    }

    private void getOrigPosLayoutElements(){

        firstName_layout_org_pos_x = firstName_layout.getX();
        lastName_layout_org_pos_x = lastName_layout.getX();
        userName_layout_org_pos_x = userName_layout.getX();
        password_layout_org_pos_x = password_layout.getX();

        // get current x axis coordinates of all editText fields

    }

    private void makeExcessCharactersCountViewInvisible(){

        letter_count_textView.setVisibility(View.INVISIBLE);

        // make textView indicating how many characters too much have been entered in in description invisble

    }

    private void getInfoFromPreviousActivity(){

        extras = getIntent().getExtras();

        countryCode = getIntent().getStringExtra("countryCode");
        countryName = getIntent().getStringExtra("countryName");

        // retrieve country code and country name from previous activity

    }

    private void initTextWatcher(){

        textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                initExcessCharacterCountView();

                RegisterActivityYesScreen3Methods.checkIfInfoEntered(
                        getApplicationContext(), firstName_editText, lastName_editText, userName_editText,
                        fab, one_layout_view, two_layout_view);

                // check if info is entered in all editTexts

            }
        };

    }

    @SuppressLint("SetTextI18n")
    private void showUserExcessCharacterCount(){

        letter_count_textView.setVisibility(View.VISIBLE);

        int characterLength = lastName_editText.getText().length();

        // retrieve length of characters entered in by user

        letter_count_textView.setText("-" + (characterLength - StartupMethods.MAX_NUM_CHARACTERS_MINUS_ONE));

        // inform user that entered text is too long with the help of a toast

    }

    private void makeExcessCharacterCountViewInvisible(){

        letter_count_textView.setVisibility(View.INVISIBLE);

        // inform user that entered text is too long with the help of a toast

    }

    private void initExcessCharacterCountView(){

        if (lastName_editText.getText().length() >= StartupMethods.MAX_NUM_CHARACTERS_MINUS_ONE){

            showUserExcessCharacterCount();

        } else if (lastName_editText.getText().length() < StartupMethods.MAX_NUM_CHARACTERS_MINUS_ONE){

            makeExcessCharacterCountViewInvisible();
        }

    }

    private void applyTextWatcherToAllEditTexts(){

        firstName_editText.addTextChangedListener(textWatcher);
        lastName_editText.addTextChangedListener(textWatcher);
        userName_editText.addTextChangedListener(textWatcher);

        /*

         * add textWatcher to all editTexts. TextWatcher enables function where fab appears
         * when info is entered into all editTexts,
         * if all info is removed from at least one editText, textWatcher makes fab disappear

         */

    }

    private void enableLayoutInteraction(){

        fab.setClickable(true);

        firstName_editText.setEnabled(true);
        lastName_editText.setEnabled(true);
        userName_editText.setEnabled(true);

        // allow editTexts to be used

    }

    private void retrievePreviouslyEnteredInfo(){

        SharedPreferences prefs = this.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        if (prefs.getString("savedUserDataShared_house_number", null) != ""){

            // check if apartment info was submitted and saved in sharedPrefs before user continued to next activity

            RegisterActivityYesScreen3Methods.retrievePreviouslyEnteredInfo(
                    getApplicationContext(), prefs, firstName_editText, lastName_editText, userName_editText,
                    one_layout_view, two_layout_view, fab);

        }

    }

}
