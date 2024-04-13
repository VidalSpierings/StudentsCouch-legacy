package com.studentscouch.projectbostonfiles.view.views;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public interface LogInActivityView {

    View getRootView();

    void initViews(AppCompatActivity appCompatActivity, Context context);

    void initData(AppCompatActivity appCompatActivity, Context context);

    void updateLayoutAccToSignIn();

    void setEditTextsFunc();

    void setEditTextFilters();

    void setEmailEditTextTextChangedListener();

    void showUnableToConnectErrors(Context context, Task<AuthResult> task2);

    void makeCheckboxClickable();

    void initUiForSignIn();

    void logInInitialisedWithNoInternetConnectionProtocol(Context context);

    void prepareViewForLogin(Context context);

    void informUserToEnterInfo(Context context);

    void disableAppAccessIfUserCountryIllegal(Context context, AppCompatActivity appCompatActivity, String countryCode);

    String retrieveMonth(List<String> monthNames, int monthInt);

    void setCheckBoxImageVisibility(ImageView checkboxImage, boolean visibility);

    void checkIfEmailRememberTrue();

    void checkIfEmailRemembered(Context context, EditText editText, TextView error_message);

    void disableUIElements();

    void makeCheckBoxUnclickable();

    FloatingActionButton getFloatingActionButton();

    void enableUIElements();

    void showProgressLayout();

    void saveOrRemoveEmail (Context context);

    void makeErrorMessageInvisible();

    String getEmailString();

    String getPasswordString();

    void startCancelLogInAccToEditTextFields(Context context, AppCompatActivity appCompatActivity, String username1, String password);

    void initOnStartMethods(Context context);

    void initOnBackPressedData(Context context);

}
