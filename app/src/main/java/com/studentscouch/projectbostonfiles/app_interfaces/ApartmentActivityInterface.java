package com.studentscouch.projectbostonfiles.app_interfaces;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ToggleButton;

import com.google.android.material.tabs.TabLayout;

public interface ApartmentActivityInterface {

    void initGmapsButton (final Context context, final String street, final String house_number, final String city_or_village,
                             FrameLayout gmaps_layout);

    void openBookingPage (final Context context, FrameLayout book_now_button_layout, final ScrollView scrollView, final TabLayout tabLayout,
                          final ObjectAnimator anim2, final Float scrollView_org_pos_y, final Float tabLayout_org_pos_y, final String UID, final String AID,
                          final String house_number, final String street, final String city_or_village, final Activity accountProfileActivity);

    void goToEditPage(final Context context, ImageView edit_imageView, final View edit_button_view, final String AID,
                      final String locationID, final String apartement1String, final String apartement2String, final String apartement3String,
                      final SharedPreferences.Editor editor, final String title, final String description, final int price_per_night,
                      final int isTwoPeopleAllowed, final Activity activity);

    void favoriteButtonPressed (final Context context, ToggleButton favorite_togglebutton);

}
