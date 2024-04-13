package com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public interface ApartmentEditActivityViewModelInterface {

void saveNewApartmentData(Context context, AppCompatActivity appCompatActivity, ViewGroup viewGroup);

void addImageButtonFunctionality(Context context, final AppCompatActivity appCompatActivity, View view,
                                 View add_button_view, View delete_button_view);

void deleteButtonFunc(
        Context context, AppCompatActivity appCompatActivity,
        View add_button_view, View delete_button_view, FrameLayout main_frameLayout);

}
