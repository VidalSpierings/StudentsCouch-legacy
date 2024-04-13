package com.studentscouch.projectbostonfiles.view.viewImplementers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.studentscouch.projectbostonfiles.MyApplication;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.StudentsCouchAnimations;
import com.studentscouch.projectbostonfiles.db.ConstantsCountryCodes;
import com.studentscouch.projectbostonfiles.db.dbImplementers.LoginActivityDBImplementer;
import com.studentscouch.projectbostonfiles.db.interfaces.LoginActivityDBInterface;
import com.studentscouch.projectbostonfiles.models.implementers.LogInActivityModel;
import com.studentscouch.projectbostonfiles.observables.LogInActivityObservables;
import com.studentscouch.projectbostonfiles.view.views.LogInActivityView;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.implementers.LogInActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class LogInActivityViewImplementer implements LogInActivityView {

    private EditText
            editText,
            editText2;

    private FloatingActionButton floatingActionButton;

    private ImageView header_image;

    private TextView
            automatic_log_in_textView,
            error_message,
            username_textView,
            password_textView;

    private LinearLayout
            username_layout,
            password_layout;

    private List<View> viewsInLayout;

    private FrameLayout checkbox3;

    private ImageView checkboxImage;

    private Typeface tp;

    private float
            automatic_log_in_textView_org_pos_x,
            checkbox3_org_pos_x;

    private FirebaseAuth auth;

    private FrameLayout progress_layout;

    private Animation
            enter_anim,
            exit_anim;

    private List<String> monthNames;

    private Float
            password_layout_org_pos_x,
            email_layout_org_pos_x;

    private String username;

    private View rootView;

    private LoginActivityDBInterface db;

    private LogInActivityViewModel viewModel;

    private LogInActivityModel model;

    private ViewGroup mainViewGroup;

    private LogInActivityObservables observables;

    public LogInActivityViewImplementer(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_login, viewGroup);

        mainViewGroup = viewGroup;

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initViews(AppCompatActivity appCompatActivity, Context context) {

        linkXMLElementsToJavaVariables();

        setTypefaces(context);

    }

    @Override
    public void initData(AppCompatActivity appCompatActivity, Context context) {

        initMiscVariables(this);

        initArrays(context);

        initUiVisibility();

        StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

        initAnims(appCompatActivity);

        initEditTexts();

        startActivityEnterTransition(appCompatActivity);

        floatingActionButton.setOnClickListener(view -> viewModel.onFabClicked(
                context, appCompatActivity, editText,
                mainViewGroup, error_message, progress_layout,
                exit_anim, auth, header_image, monthNames, tp));

    }

    @Override
    public void updateLayoutAccToSignIn() {

        floatingActionButton.hide();

        // hide fab

        disableViewClickAbility();

        setErrorMessageTypeface();

    }

    private void initEditTexts(){

        setEditTextFilters();

        setEditTextsFunc();

    }

    public void setEditTextsFunc() {

        setEmailEditTextTextChangedListener();

        setPasswordEditTextOnTextChangedListener();

    }

    @Override
    public void setEditTextFilters() {

        InputFilter filter = (source, start, end, dest, dstart, dend) -> viewModel.textFilterFunc(start, end, source);

        applyTextFilters(filter);

        // prevent user from entering emojis and other unwanted symbols into editText fields

    }

    @Override
    public void setEmailEditTextTextChangedListener() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                onTextChangedFunc();

            }

            @Override
            public void afterTextChanged(Editable s) {

                afterTextChangedFunc();

            }
        });

    }

    @Override
    public void informUserToEnterInfo(Context context) {

// if either editTexts are somehow empty, do the following

        floatingActionButton.hide();

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(context, 2000, error_message, context.getResources().getString(R.string.email_or_password_not_filled_in), tp);

        // inform user that either one of the editTexts are not filled in

        editText.setEnabled(true);

        editText2.setEnabled(true);

        // enable editTexts to be interacted with

    }

    @Override
    public void disableAppAccessIfUserCountryIllegal(Context context, AppCompatActivity appCompatActivity, String countryCode) {

        for (int i = 0; i < ConstantsCountryCodes.COUNTRIES_IN_EUROPE.length; i++) {

            if (countryCode.equals(ConstantsCountryCodes.COUNTRIES_IN_EUROPE[i])){

                Toast.makeText(context, context.getResources().getString(R.string.eu_block_active), Toast.LENGTH_LONG).show();

                appCompatActivity.onBackPressed();

                // block usage of StudentsCouch for users in European union

            }

        }
}

    @Override
    public String retrieveMonth(List<String> monthNames, int monthInt) {

        return monthNames.get(monthInt);

    }

    @Override
    public void setCheckBoxImageVisibility(ImageView checkboxImage, boolean visibility) {

        if (visibility) {

            checkboxImage.setVisibility(View.VISIBLE);

            // set checkbox to selected

        } else {

            checkboxImage.setVisibility(View.INVISIBLE);

            // set checkbox to unselected

        }

    }

    @Override
    public void checkIfEmailRememberTrue() {

        if (!username.equals("")){

            editText.setText(username);

            checkboxImage.setVisibility(View.VISIBLE);

            // set email to email box if email was saved

        } else {

            checkboxImage.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void checkIfEmailRemembered(Context context, EditText editText, TextView error_message) {

        if (observables.isChecked()){

            model.isCheckedFunc(context, editText);

        }

        else if (!observables.isChecked()){

            model.isUncheckedFunc(context);

        }

        else {

            error_message.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void showUnableToConnectErrors(Context context, Task<AuthResult> task2) {

        try {

            throw task2.getException();

        } catch(FirebaseAuthInvalidCredentialsException e) {

            invalidCredentialsErrorProtocol(context);

        } catch(FirebaseAuthUserCollisionException e) {

            wrongEmailOrPasswordErrorProtocol(context);

        } catch(Exception e) {

            unIdentifiedErrorProtocol(context);

        }

        initUiForShowingError();

    }

    @Override
    public void makeCheckboxClickable() {



    }

    @Override
    public void initUiForSignIn() {

        checkbox3.setClickable(false);

        // disable 'remember email' checkbox from being interacted with

        MyApplication.makeProgressLayoutDisappear(progress_layout, exit_anim);

    }

    @Override
    public void logInInitialisedWithNoInternetConnectionProtocol(Context context){

        if (!StartupMethods.isOnline(context))

        {

            floatingActionButton.show();

            enableUIElements();

            StartupMethods.showErrorMessage(context, error_message, context.getResources().getString(R.string.internet_connection_non_existent), tp);

            // if internet connection was not found, inform user by showing a toast

        }

    }

    @Override
    public void disableUIElements(){

        checkbox3.setClickable(false);

        editText.setEnabled(false);

        editText2.setEnabled(false);

        // disable editTexts and checkBox from being interacted with

        floatingActionButton.hide();

    }

    @Override
    public void makeCheckBoxUnclickable() {

        checkbox3.setClickable(false);

    }

    @Override
    public FloatingActionButton getFloatingActionButton() {

        return floatingActionButton;

    }

    @Override
    public void makeErrorMessageInvisible() {

        error_message.setVisibility(View.VISIBLE);

    }

    @Override
    public String getEmailString() {

        return editText.getText().toString();

    }

    @Override
    public String getPasswordString() {

        return editText2.getText().toString();

    }

    private void applyTextFilters(InputFilter filter){

        editText.setFilters(new InputFilter[]{filter});
        editText2.setFilters(new InputFilter[]{filter});

    }

    private void initUiForShowingError(){

        progress_layout.setVisibility(View.INVISIBLE);

        error_message.setVisibility(View.VISIBLE);

    }

    private void setPasswordEditTextOnTextChangedListener(){

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                onTextChangedFunc2();

            }

            @Override
            public void afterTextChanged(Editable s) {

                afterTextChangedFunc2();

            }
        });

    }

    private void afterTextChangedFunc2(){

        if (!editText2.getText().toString().equals("") && !editText.getText().toString().equals("")) {

            floatingActionButton.show();
            // show fab when both editTexts have text entered in them

        } else {

            floatingActionButton.hide();

            // hide fab when no text is entered in one of two or neither editTexts

        }

    }

    private void onTextChangedFunc2(){

        if (!editText.getText().toString().equals("")) {

            floatingActionButton.show();

            // show fab when both editTexts have text entered in them

        } else {

            floatingActionButton.hide();

            // hide fab when no text is entered in one of two or neither editTexts

        }

    }

    private void afterTextChangedFunc(){

        if (!editText.getText().toString().equals("") && !editText2.getText().toString().equals("")) {

            floatingActionButton.show();

            // show fab when both editTexts have text entered in them

        } else {

            floatingActionButton.hide();

            // hide fab when no text is entered in one of two or neither editTexts

        }

    }

    private void onTextChangedFunc(){

        if (!editText2.getText().toString().equals("")) {

            floatingActionButton.show();

            // show fab when both editTexts have text entered in them

        } else {

            floatingActionButton.hide();

            // hide fab when no text is entered in one of two or neither editTexts

        }

    }

    private void startActivityEnterTransition(Activity activity){

        StudentsCouchAnimations anims = new StudentsCouchAnimations(activity);

        anims.animateActivityTransitionLoginActivity(
                checkboxImage, automatic_log_in_textView, viewsInLayout, checkbox3,
                automatic_log_in_textView_org_pos_x, checkbox3_org_pos_x, password_layout, username_layout,
                password_layout_org_pos_x, email_layout_org_pos_x);

    }

    private void initMiscVariables(LogInActivityView viewImplementer){

        observables = new LogInActivityObservables();

        model = new LogInActivityModel(observables);

        db = new LoginActivityDBImplementer(viewImplementer, observables);

        auth = FirebaseAuth.getInstance();

        viewModel = new LogInActivityViewModel(viewImplementer, observables);

    }

    private void initAnims(AppCompatActivity appCompatActivity){

        enter_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_in_animation);

        // load enter animation

        enter_anim.setDuration(200);

        // set animation duration at 200 milliseconds

        exit_anim = AnimationUtils.loadAnimation(appCompatActivity, R.anim.visibility_fade_out_animation);

        // load exit animation

        exit_anim.setDuration(200);

        // set animation duration at 200 milliseconds

    }

    private void initArrays(Context context){

        initViewsInLayoutArrayList();

        initOrigPosViewsArrayList();

        initEditTextLayoutsArrayList();

        initMonthNamesArrayList(context);

    }

    private void initMonthNamesArrayList(Context context){

        monthNames = new ArrayList<>();

        monthNames.add(context.getResources().getString(R.string.january));
        monthNames.add(context.getResources().getString(R.string.february));
        monthNames.add(context.getResources().getString(R.string.march));
        monthNames.add(context.getResources().getString(R.string.april));
        monthNames.add(context.getResources().getString(R.string.may));
        monthNames.add(context.getResources().getString(R.string.june));
        monthNames.add(context.getResources().getString(R.string.july));
        monthNames.add(context.getResources().getString(R.string.august));
        monthNames.add(context.getResources().getString(R.string.september));
        monthNames.add(context.getResources().getString(R.string.october));
        monthNames.add(context.getResources().getString(R.string.november));
        monthNames.add(context.getResources().getString(R.string.december));

    }

    private void initUiVisibility(){

        error_message.setVisibility(View.INVISIBLE);

        floatingActionButton.hide();

    }

    private void initEditTextLayoutsArrayList(){

        List<View> editTextLayouts = new ArrayList<>();

        editTextLayouts.add(username_layout);
        editTextLayouts.add(password_layout);

    }

    private void initOrigPosViewsArrayList(){

        automatic_log_in_textView_org_pos_x = automatic_log_in_textView.getX();
        checkbox3_org_pos_x = checkbox3.getX();
        email_layout_org_pos_x = editText.getX();
        password_layout_org_pos_x = password_layout.getX();

        List<Float> orig_pos_views = new ArrayList<>();

        orig_pos_views.add(automatic_log_in_textView_org_pos_x);
        orig_pos_views.add(checkbox3_org_pos_x);
        orig_pos_views.add(password_layout.getX());
        orig_pos_views.add(username_layout.getX());

    }

    private void initViewsInLayoutArrayList(){

        viewsInLayout = new ArrayList<>();

        viewsInLayout.add(username_layout);
        viewsInLayout.add(password_layout);
        viewsInLayout.add(automatic_log_in_textView);
        viewsInLayout.add(checkbox3);

        // create and array of views that can be animated

    }

    private void setTypefaces(Context context){

        tp = Typeface.createFromAsset(context.getAssets(), "project_boston_typeface.ttf");

        username_textView.setTypeface(tp);
        editText.setTypeface(tp);
        editText2.setTypeface(tp);
        password_textView.setTypeface(tp);
        automatic_log_in_textView.setTypeface(tp);

        // initialise and apply typeface to all textViews in layout

    }

    private void linkXMLElementsToJavaVariables(){

        floatingActionButton = rootView.findViewById(R.id.fab);

        editText = rootView.findViewById(R.id.editText);
        editText2 = rootView.findViewById(R.id.editText2);

        checkbox3 = rootView.findViewById(R.id.checkBox3);

        checkboxImage = rootView.findViewById(R.id.checkboxImage);

        username_layout = rootView.findViewById(R.id.username_layout);
        password_layout = rootView.findViewById(R.id.password_layout);
        progress_layout = rootView.findViewById(R.id.progress_layout);

        username_textView = rootView.findViewById(R.id.username_textView);
        password_textView = rootView.findViewById(R.id.password_textView);
        automatic_log_in_textView = rootView.findViewById(R.id.automatic_log_in_textView);
        error_message = rootView.findViewById(R.id.error_message);

        header_image = rootView.findViewById(R.id.header_image);

        // link java variables with xml layout views

    }

    @Override
    public void startCancelLogInAccToEditTextFields(Context context, AppCompatActivity appCompatActivity, String username1, String password){

        if (TextUtils.isEmpty(username1) || TextUtils.isEmpty(password)){

            informUserToEnterInfo(context);

        } else {

            textViewsNotEmptyLogInUser(context, username1, password, appCompatActivity);

            logInInitialisedWithNoInternetConnectionProtocol(context);

        }

    }

    private void textViewsNotEmptyLogInUser(Context context, String username1, String password, AppCompatActivity appCompatActivity){

        if (StartupMethods.isOnline(context)){

            // if internet connection is found, do the following

            MyApplication.makeProgressLayoutAppearFrameLayout(progress_layout, enter_anim);

            // make loading screen invisible

            auth.signInWithEmailAndPassword(username1, password).addOnCompleteListener(task -> db.signInUser(
                    context, appCompatActivity, auth, progress_layout,
                    exit_anim, monthNames, mainViewGroup, username1,
                    password, header_image));

        }

    }

    private void unIdentifiedErrorProtocol(Context context){

        StartupMethods.showErrorMessageTemporarily(context, 2500, error_message, context.getResources().getString(R.string.something_went_wrong), tp);

        // inform user that something went wrong whilst trying to log in

        editText.setEnabled(true);

        editText2.setEnabled(true);

        // enable user to interact with editTexts

    }

    private void wrongEmailOrPasswordErrorProtocol(Context context){

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(context, 2500, error_message, context.getResources().getString(R.string.wrong_email_password), tp);

        // inform user that the entered email or password is incorrect

        editText.setEnabled(true);

        editText2.setEnabled(true);

        // enable user to interact with editTexts

    }

    private void invalidCredentialsErrorProtocol(Context context){

        linkXMLElementsToJavaVariables();

        error_message.setVisibility(View.VISIBLE);

        StartupMethods.showErrorMessageTemporarily(context, 2500, error_message, context.getResources().getString(R.string.wrong_email_password), tp);

        // inform user that the entered email or password is incorrect

        editText.setEnabled(true);

        editText2.setEnabled(true);

        // enable user to interact with editTexts

    }

    @Override
    public void enableUIElements() {

            editText.setEnabled(true);

            editText2.setEnabled(true);

            checkbox3.setClickable(true);

            // enable editTexts to be interacted with

        floatingActionButton.show();

    }

    @Override
    public void showProgressLayout() {

        MyApplication.makeProgressLayoutAppearFrameLayout(progress_layout, enter_anim);

        // make loading screen invisible

    }

    @Override
    public void prepareViewForLogin(Context context) {

        saveOrRemoveEmail(context);

        // save or delete email to/from sharedPreferences depending on whether the user chose to have their email remembered

        disableUIElements();

        // disable editTexts and checkBox from being interacted with

        floatingActionButton.hide();

    }

    private void disableViewClickAbility(){

        checkbox3.setClickable(false);

        editText.setEnabled(false);

        editText2.setEnabled(false);

        // disable editText from being interacted with

    }

    private void setErrorMessageTypeface(){

        error_message.setVisibility(View.INVISIBLE);

        error_message.setTypeface(tp);

        // set error message typeface

    }

    @Override
    public void saveOrRemoveEmail (Context context) {

        if (observables.isChecked()){

            model.isCheckedFunc(context, editText);

        }

        else if (!observables.isChecked()){

            model.isUncheckedFunc(context);

        }

        error_message.setVisibility(View.INVISIBLE);

    }

    @Override
    public void initOnStartMethods(Context context){

        checkbox3.setOnClickListener(view -> viewModel.onCheckBoxClickedListener(checkboxImage));

        retrieveDataFromPreviousActivity(context);

        checkIfEmailRememberTrue();

        // check if user selected to remember email, if true, insert email string into editText from sharedPreferences

    }

    @Override
    public void initOnBackPressedData(Context context) {

        floatingActionButton.hide();

        checkIfEmailRemembered(context, editText, error_message);

    }

    private void retrieveDataFromPreviousActivity(Context context){

        username = model.getUsername(context);
        observables.setChecked(model.getIsChecked(context));

        // retrieve pw and if user checked to have their email address remembered

    }

}
