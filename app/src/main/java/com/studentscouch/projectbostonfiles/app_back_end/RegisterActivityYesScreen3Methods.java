package com.studentscouch.projectbostonfiles.app_back_end;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.Constants;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.ConfirmRegistrationActivity;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen3;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RegisterActivityYesScreen3Methods {

    @SuppressLint("StaticFieldLeak")
    static Activity activity;
    private static Animator.AnimatorListener animatorListener;
    static Intent i;
    static ObjectAnimator anim;
    private static int isFromActivity;
    private static String
            price_per_night,
            title,
            description;

    private static int choice;

    RegisterActivityYesScreen3Methods(Activity activity){
        RegisterActivityYesScreen3Methods.activity =activity;
    }

    public static void animateActivityTransition(
            Context context, List<View> animatingViews, final List<View> viewsInLayout, List<View> fadingView,
            final List<Float> orig_pos){

        // copy layoutparams attributes of relativelayout to a set of LayourParams independent from the real relativelayoutParams

        for (View animatingView : animatingViews) {

            animatingView.setVisibility(View.INVISIBLE);

        }

        // make info rows invisible

        initAnimatorListener(viewsInLayout);

        StartupMethods.enterAnimationNoButtons(
                context, viewsInLayout, fadingView, 400,
                0, orig_pos, animatingViews,
                animatorListener);

    }

    private static void initAnimatorListener(List<View> viewsInLayout){

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                StartupMethods.changeVisibility(viewsInLayout, View.INVISIBLE);

                // make all views in base layout invisible

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

    }

    private static void startNextActivity(
            final Context context, EditText firstName_editText, EditText lastName_editText,
            EditText userName_editText, FloatingActionButton fab,
            FrameLayout one_layout, FrameLayout two_layout, String countryName, String countryCode,
            Bundle extras, final List<View> animatingViews,
            final AppCompatActivity appCompatActivity, List<View> fadingView){

        changeAppFuncIfUserFromAddApartmentDetailsButton(context, extras);

        animateActivityTransitionStartNextActivity(
                context, appCompatActivity, animatingViews,
                countryCode, countryName, isFromActivity, fadingView);

        saveEnteredApartmentData(context, firstName_editText, lastName_editText, userName_editText);

        disableLayoutInteraction(
                fab, firstName_editText, lastName_editText,
                userName_editText, one_layout, two_layout);

    }

    public static void checkIfInfoEntered (
            Context context, EditText firstName_editText, EditText lastName_editText, EditText userName_editText,
            FloatingActionButton fab, View one_layout_view, View two_layout_view){

        if (
                !firstName_editText.getText().toString().equals("") &&
                        !lastName_editText.getText().toString().equals("") &&
                        !userName_editText.getText().toString().equals("") &&
                        (one_layout_view.isSelected() || two_layout_view.isSelected())
        )
        {

            // if information is entered in all fields, show fab

            fab.show();

        } else {

            // if information is not entered in at least one of the fields, hide fab

            fab.hide();

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initTwoButtonSelected(
            final FrameLayout two_layout, final EditText firstName_editText, final EditText lastName_editText,
            final EditText userName_editText, final View one_layout_view, final View two_layout_view,
            final FloatingActionButton fab){

        two_layout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP){

                RegisterActivityYesScreen3.isTwoPeopleAllowed = 2;

                removeFocusFromAllEditTexts(userName_editText, lastName_editText, firstName_editText);

                StartupMethods.buttonSelected(getApplicationContext(), two_layout_view, one_layout_view, event);

                // visually select two people button

                showHideFabIfAllButtonsPressed(
                        firstName_editText, lastName_editText, userName_editText,
                        one_layout_view, two_layout_view, fab);

            }

            return true;
        });

    }

    public static void retrievePreviouslyEnteredInfo(
            Context context, SharedPreferences prefs, EditText firstName_editText, EditText lastName_editText,
            EditText userName_editText, View one_layout_view, View two_layout_view, FloatingActionButton fab) {

        try {

            retrieveApartmentInfoFromSharedPreferences(prefs);

            changeActivityFuncAccToNumPeopleSelected(
                    one_layout_view, two_layout_view, userName_editText, lastName_editText, firstName_editText);

            showEditTextsApartmentInfo(firstName_editText, lastName_editText, userName_editText);

            showFabIfAllInfoPresent(prefs, fab);

        }catch (Exception e){

            // Do nothing

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void initialiseOneButtonSelected(
            Context context, FrameLayout one_layout, View one_layout_view, View two_layout_view,
            EditText firstName_editText, EditText lastName_editText, EditText userName_editText,
            FloatingActionButton fab){

        one_layout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP){

                RegisterActivityYesScreen3.isTwoPeopleAllowed = 1;

                removeFocusFromAllEditTexts(userName_editText, lastName_editText, firstName_editText);

                StartupMethods.buttonSelected(getApplicationContext(), one_layout_view, two_layout_view, event);

                RegisterActivityYesScreen3Methods.checkIfInfoEntered(
                        context, firstName_editText, lastName_editText, userName_editText,
                        fab, one_layout_view, two_layout_view);

                // check if info is entered in all editTexts

            }

            return true;
        });

    }

    public static void initialiseFabOnClickListener(
            final Context context, final FloatingActionButton fab, final EditText userName_editText, final TextView error_message,
            final EditText lastName_editText, final EditText firstName_editText,
            final FrameLayout one_layout, final FrameLayout two_layout, final String countryName, final String countryCode,
            final Bundle extras, final List<View> animatingViews,
            final Typeface tp, final AppCompatActivity appCompatActivity, final List<View> fadingView) {

        fab.setOnClickListener(view -> startNextActivityIfNoEnterInfoIllegalActions(
                context, appCompatActivity, userName_editText, lastName_editText,
                firstName_editText, one_layout, two_layout, countryName, countryCode,
                extras, animatingViews, error_message, fab, tp, fadingView));

    }

    private static void prepareIntent(AppCompatActivity appCompatActivity, String countryCode, String countryName, int isFromActivity){

        i = new Intent(appCompatActivity, ConfirmRegistrationActivity.class);

        i.putExtra("username", appCompatActivity.getIntent().getStringExtra("username"));

        i.putExtra("countryCode", countryCode);
        i.putExtra("countryName", countryName);

        i.putExtra("isFromProfileActivity", isFromActivity);

        // pass info on what activity user is coming from to next activity

        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private static void setOnAnimEndListener(AppCompatActivity appCompatActivity, List<View> animatingViews){

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                animateAllViewsStartNewActivity(appCompatActivity, animatingViews);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private static void animateAllViewsStartNewActivity(AppCompatActivity appCompatActivity, List<View> animatingViews){

        appCompatActivity.startActivity(i);

        // upon final animation being done animating, start new activity

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            animatingViews.get(pos).setVisibility(View.INVISIBLE);

        }

    }

    private static void startNewActivityOnFirstItem(
            AppCompatActivity appCompatActivity, int pos, String countryCode,
            String countryName, int isFromActivity, List<View> animatingViews){

        if (pos == 0) {

            prepareIntent(appCompatActivity, countryCode, countryName, isFromActivity);

            setOnAnimEndListener(appCompatActivity, animatingViews);

        }

    }

    private static void fadeOutLayoutElements(List<View> fadingView){

        Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visibility_fade_out_animation);

        fade_out.setDuration(400);

        fadingView.get(0).startAnimation(fade_out);

    }

    private static void animateActivityTransitionStartNextActivity(Context context, AppCompatActivity appCompatActivity, List<View> animatingViews, String countryCode, String countryName, int isFromActivity, List<View> fadingView){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int pos = 0; pos < animatingViews.size(); pos++) {

            anim = ObjectAnimator.ofFloat(
                    animatingViews.get(pos), "translationX", animatingViews.get(pos).getX(),
                    -metrics.widthPixels -animatingViews.get(pos).getX());

            startNewActivityOnFirstItem(appCompatActivity, pos, countryCode, countryName, isFromActivity, animatingViews);

            fadeOutLayoutElements(fadingView);

            anim.start();

        }

    }

    private static void getActivityUserCameFrom(Bundle extras){

        if (extras != null) {

            isFromActivity = extras.getInt("isFromProfileActivity", 5);

            // retrieve info on what activity user came from, if this info could not be retrieved retrieve useless '5' integer

        }

    }

    private static void changeAppFuncIfUserFromAddApartmentDetailsButtonFunc(Context context){

        if(isFromActivity == 1)

        {

            SharedPreferences sharedPreferences2 = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor2 = sharedPreferences2.edit();

            editor2.putInt("Choice", 2);

            // insert 'yes' as choice for register as host option to
            // enable according functions in the registration process

            editor2.apply();

        }

    }

    private static void saveEnteredApartmentData(Context context, EditText firstName_editText, EditText lastName_editText, EditText userName_editText){

        SharedPreferences sharedPreferences = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("savedUserDataSharedTitle", firstName_editText.getText().toString());
        editor.putString("savedUserDataSharedDescription", lastName_editText.getText().toString());
        editor.putString("savedUserDataShared_house_number", userName_editText.getText().toString());
        editor.putInt("savedUserDataIsTwoPeopleAllowed", RegisterActivityYesScreen3.isTwoPeopleAllowed);

        // save entered info

        editor.apply();

    }

    @SuppressLint("ClickableViewAccessibility")
    private static void disableLayoutInteraction(
            FloatingActionButton fab, EditText firstName_editText,
            EditText lastName_editText, EditText userName_editText,
            FrameLayout one_layout, FrameLayout two_layout){

        fab.hide();

        fab.setClickable(false);

        firstName_editText.setEnabled(false);
        lastName_editText.setEnabled(false);
        userName_editText.setEnabled(false);

        // disable all editText fields from being interacted with

        one_layout.setOnTouchListener(null);
        two_layout.setOnTouchListener(null);

        // make one person and two people buttons unclickable

    }

    private static void changeAppFuncIfUserFromAddApartmentDetailsButton(Context context, Bundle extras){

        getActivityUserCameFrom(extras);

        changeAppFuncIfUserFromAddApartmentDetailsButtonFunc(context);

    }

    private static void showHideFabIfAllButtonsPressed(
            EditText firstName_editText, EditText lastName_editText,
            EditText userName_editText, View one_layout_view, View two_layout_view,
            FloatingActionButton fab){

        if (
                !firstName_editText.getText().toString().equals("") &&
                        !lastName_editText.getText().toString().equals("") &&
                        !userName_editText.getText().toString().equals("") &&
                        (one_layout_view.isSelected() || two_layout_view.isSelected())
        )
        {

            // if information is entered in all editTexts, show fab

            fab.show();

        } else {

            // if information is not entered in at least one of the editTexts, hide fab

            fab.hide();

        }

    }

    private static void removeFocusFromAllEditTexts(EditText userName_editText, EditText lastName_editText, EditText firstName_editText){

        userName_editText.clearFocus();
        lastName_editText.clearFocus();
        firstName_editText.clearFocus();

        //remove focus from all editText fields

    }

    private static void retrieveApartmentInfoFromSharedPreferences(SharedPreferences prefs){

        price_per_night = prefs.getString("savedUserDataShared_house_number", "");

        title = prefs.getString("savedUserDataSharedTitle", "");

        description = prefs.getString("savedUserDataSharedDescription", "");

        choice = prefs.getInt("savedUserDataIsTwoPeopleAllowed", 0);

        // retrieve title, description, number of people allowed per booking, price per booking

    }

    private static void changeActivityFuncAccToNumPeopleSelected(
            View one_layout_view, View two_layout_view, EditText userName_editText,
            EditText lastName_editText, EditText firstName_editText){

        if (choice == 1) {

            // if one person selected, do the following

            selectDeselectAccButtonsOneLayoutSelec(one_layout_view, two_layout_view);

            removeFocusFromAllEditTexts(userName_editText, lastName_editText, firstName_editText);

            selectDeselectAccButtonsOneLayoutSelecVisual(one_layout_view, two_layout_view);

        } else if (choice == 2){

            // if two people selected, do the following

            selectDeselectAccButtonsTwoLayoutSelec(two_layout_view, one_layout_view);

            removeFocusFromAllEditTexts(userName_editText, lastName_editText, firstName_editText);

            selectDeselectAccButtonsTwoLayoutSelecVisual(one_layout_view, two_layout_view);

        }

    }

    private static void selectDeselectAccButtonsOneLayoutSelec(View one_layout_view, View two_layout_view){

        two_layout_view.setSelected(false);
        one_layout_view.setSelected(true);

        // make 'one person' button selected and make 'two people' view unselected

    }

    private static void selectDeselectAccButtonsOneLayoutSelecVisual(View one_layout_view, View two_layout_view){

        one_layout_view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.introduction_button_clicked));
        two_layout_view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.main_background));

        // give 'one person' selected dark background, give 'two people' button unselected normal background

        one_layout_view.setVisibility(View.VISIBLE);
        two_layout_view.setVisibility(View.INVISIBLE);

        // make 'one person' reveal view visible, make 'two people' reveal view invisible

    }

    private static void selectDeselectAccButtonsTwoLayoutSelec(View two_layout_view, View one_layout_view){

        two_layout_view.setSelected(true);
        one_layout_view.setSelected(false);

        // make 'two people' button selected and make 'one person' button unselected

    }

    private static void selectDeselectAccButtonsTwoLayoutSelecVisual(View one_layout_view, View two_layout_view){

        one_layout_view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.main_background));
        two_layout_view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.introduction_button_clicked));

        // give 'one person' layout

        one_layout_view.setVisibility(View.INVISIBLE);
        two_layout_view.setVisibility(View.VISIBLE);

        // make 'one person' reveal view invisible and make 'two people' reveal view visible

    }

    private static void showEditTextsApartmentInfo(EditText firstName_editText, EditText lastName_editText, EditText userName_editText){

        firstName_editText.setText(title);
        lastName_editText.setText(description);
        userName_editText.setText(price_per_night);

        // insert pre-entered title, description, price per night

    }

    private static void showFabIfAllInfoPresent(SharedPreferences prefs, FloatingActionButton fab){

        if (prefs.getString("savedUserDataShared_house_number", null) != "" &&
                prefs.getString("savedUserDataSharedTitle", "") != "" &&
                prefs.getString("savedUserDataSharedDescription", "") != "" &&
                prefs.getString("savedUserDataIsTwoPeopleAllowed", "") != ""){

            // if previously entered information is found in sharedPreferences, show fab

            fab.show();

        }

    }

    private static void resetPricePerNightIfTooHigh(Context context, TextView error_message, EditText userName_editText, FloatingActionButton fab, Typeface tp){

        StartupMethods.showErrorMessageTemporarily(
                getApplicationContext(), 3000, error_message,
                context.getResources().getString(R.string.price_limit), tp);

        userName_editText.setText("");

        fab.hide();

        // if price entered is under 15 or above 50, inform user that this is not possible with the help of a toast

    }

    private static void informUserDescTooManyCharacters(Context context, TextView error_message, FloatingActionButton fab, Typeface tp){

        StartupMethods.showErrorMessageTemporarily(
                getApplicationContext(), 3000, error_message,
                context.getResources().getString(R.string.description_too_long), tp);

        fab.hide();

        // inform user that listing description is too long with the help of a toast

    }

    private static void startNextActivityIfNoEnterInfoIllegalActions(
            Context context, AppCompatActivity appCompatActivity, EditText userName_editText,
            EditText lastName_editText, EditText firstName_editText, FrameLayout one_layout,
            FrameLayout two_layout, String countryName, String countryCode, Bundle extras,
            List<View> animatingViews, TextView error_message, FloatingActionButton fab,
            Typeface tp, List<View> fadingView){

        if (Integer.parseInt(
                userName_editText.getText().toString()) > Constants.MAX_BOOKING_COST_US_DOLLARS
                || Integer.parseInt(userName_editText.getText().toString()) < Constants.MIN_BOOKING_COST_US_DOLLARS) {

            resetPricePerNightIfTooHigh(context, error_message, userName_editText, fab, tp);

        } else if (lastName_editText.getText().length() >= StartupMethods.MAX_NUM_CHARACTERS){

            informUserDescTooManyCharacters(context, error_message, fab, tp);

        } else {

            RegisterActivityYesScreen3Methods.startNextActivity(
                    getApplicationContext(), firstName_editText, lastName_editText,
                    userName_editText, fab, one_layout, two_layout,
                    countryName, countryCode, extras,
                    animatingViews, appCompatActivity, fadingView);

        }

    }

}
