package com.studentscouch.projectbostonfiles.models.implementers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.studentscouch.projectbostonfiles.models.interfaces.LoginActivityModelInterface;
import com.studentscouch.projectbostonfiles.observables.LogInActivityObservables;

public class LogInActivityModel implements LoginActivityModelInterface {

    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;

    private LogInActivityObservables observables;

    public LogInActivityModel(LogInActivityObservables observables){

        this.observables = observables;

    }

    @Override
    public void isUncheckedFunc(Context context) {

        initSharedPrefs(context);

        setUncheckedFunc();


    }

    @Override
    public void isCheckedFunc(Context context, EditText editText) {

        String username = editText.getText().toString();

        initSharedPrefs(context);

        saveUsername(username);

    }

    @Override
    public boolean getIsChecked(Context context) {

        SharedPreferences pref = context.getSharedPreferences("username", Context.MODE_PRIVATE);

        return pref.getBoolean("isChecked", true);
    }

    @Override
    public String getUsername(Context context) {

        SharedPreferences pref = context.getSharedPreferences("username", Context.MODE_PRIVATE);

        return pref.getString("username", "");

    }

    @SuppressLint("CommitPrefEdits")
    private void initSharedPrefs(Context context){

        SharedPreferences pref = context.getSharedPreferences("username", Context.MODE_PRIVATE);
        SharedPreferences pref2 = context.getSharedPreferences("isChecked", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor2 = pref2.edit();

    }

    private void saveUsername(String username){

        observables = new LogInActivityObservables();

        editor.putString("username", username).apply();
        editor2.putBoolean("isChecked", observables.isChecked()).apply();

        // select checkbox, this will save the users' email when they've successfully logged in

    }

    private void setUncheckedFunc(){

        editor2.putBoolean("isChecked", false).apply();

        editor.clear().apply();

        // deselect the checkbox, this will delete the users' email from sharedPreferences
        // when they've successfully logged in

    }

}
