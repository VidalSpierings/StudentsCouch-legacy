package com.studentscouch.projectbostonfiles.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.db.ConstantsDB;

import java.util.ArrayList;

public class CustomDIalogUserStayEndedMethods {

    @SuppressLint("ClickableViewAccessibility")
    public static void setHappyEmojiOnClickListener(
            final Context context, final View unhappy_emoji_view, final FloatingActionButton fab,
            final View happy_emoji_view, final FrameLayout one_star_button_layout,
            final FrameLayout one_and_a_half_stars_button_layout, final FrameLayout two_stars_button_layout,
            final FrameLayout two_and_a_half_stars_button_layout,
            final FrameLayout three_stars_button_layout, final FrameLayout three_and_a_half_stars_button_layout, final FrameLayout four_stars_button_layout,
            final FrameLayout four_and_a_half_stars_button_layout, final FrameLayout five_stars_button_layout) {

        CustomDialogUserStayEnded.happy_emoji.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP){

                    selectDeselectAccButtonsHappyEmoji(
                            context, event, happy_emoji_view, unhappy_emoji_view);

                    showFabIfAllInfoEntered(
                            one_star_button_layout,
                            one_and_a_half_stars_button_layout,
                            two_stars_button_layout,
                            two_and_a_half_stars_button_layout,
                            three_stars_button_layout,
                            three_and_a_half_stars_button_layout,
                            four_stars_button_layout,
                            four_and_a_half_stars_button_layout,
                            five_stars_button_layout,
                            fab);

                }

                //happy emoji selected, unhappy deselected

                return true;
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setUnhappyEmojiOnClickListener(
            final Context context, final View unhappy_emoji_view,
            final FloatingActionButton fab, final FrameLayout one_star_button_layout, final FrameLayout one_and_a_half_stars_button_layout,
            final FrameLayout two_stars_button_layout, final FrameLayout two_and_a_half_stars_button_layout, final FrameLayout three_stars_button_layout,
            final FrameLayout three_and_a_half_stars_button_layout, final FrameLayout four_stars_button_layout, final FrameLayout four_and_a_half_stars_button_layout,
            final FrameLayout five_stars_button_layout, final View happy_emoji_view) {

        CustomDialogUserStayEnded.unhappy_emoji.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP){

                selectDeselectAccButtonsUnHappyEmoji(
                        context, event, happy_emoji_view, unhappy_emoji_view);

                showFabIfAllInfoEntered(
                        one_star_button_layout,
                        one_and_a_half_stars_button_layout,
                        two_stars_button_layout,
                        two_and_a_half_stars_button_layout,
                        three_stars_button_layout,
                        three_and_a_half_stars_button_layout,
                        four_stars_button_layout,
                        four_and_a_half_stars_button_layout,
                        five_stars_button_layout,
                        fab);

            }

            //unhappy emoji selected, happy emoji deselected

            return true;
        });

    }

    static void setStarRatingButtonsOnClickListeners(
            final Context context, final FloatingActionButton fab) {

        for (int pos = 0; pos < CustomDialogUserStayEnded.starRatingsButtons.size(); pos++) {

            final int finalPos = pos;
            CustomDialogUserStayEnded.starRatingsButtons.get(pos).setOnClickListener(view -> setStarRatingButtonsOnClickListener2(
                    context, finalPos, fab));

        }

    }

    private static void setStarRatingButtonsOnClickListener2(
            Context context, int pos, FloatingActionButton fab) {

        ArrayList<Double> allRatingValues = new ArrayList<>();

        allRatingValues.add(1.0);
        allRatingValues.add(1.5);
        allRatingValues.add(2.0);
        allRatingValues.add(2.5);
        allRatingValues.add(3.0);
        allRatingValues.add(3.5);
        allRatingValues.add(4.0);
        allRatingValues.add(4.5);
        allRatingValues.add(5.0);

        CustomDialogUserStayEnded.ratingValue = allRatingValues.get(pos);

        CustomDialogUserStayEnded.starRatingsButtons.get(pos).setSelected(true);

        for (int posUnselected = 0; posUnselected < CustomDialogUserStayEnded.starRatingsButtons.size(); posUnselected++) {

            CustomDialogUserStayEnded.starRatingsButtons.get(posUnselected).setBackgroundColor(context.getResources().getColor(R.color.buttonColorTertiary));

        }

        CustomDialogUserStayEnded.starRatingsButtons.get(pos).setBackgroundColor(context.getResources().getColor(R.color.buttonClickedFilter));

        if (CustomDialogUserStayEnded.starRatingsButtons.get(pos).isSelected() && (
                CustomDialogUserStayEnded.unhappy_emoji.isSelected() || CustomDialogUserStayEnded.happy_emoji.isSelected())){

            fab.show();

            // if emoji rating has already been selected, show fab

        }

        // select one star and deselect all other options

    }

    static void initialiseFloatingActionButton(
            FloatingActionButton fab, final DatabaseReference fbRef, final int numRatings, final DatabaseReference fbRef3,
            final double ratingValue, final double valueOfAllChildren, final int numChildren, final DatabaseReference fbRef2,
            final DatabaseReference fbRef6, final DatabaseReference fbRef7, final ImageView happy_emoji, final ImageView unhappy_emoji,
            final int numHappyEmojis, int numUnHappyEmojis, final DatabaseReference fbRef9,
            final Toast rating_received, final androidx.appcompat.app.AppCompatDialogFragment fragment) {

        fab.setOnClickListener(view -> {

            Log.i("numChild", "num: " + valueOfAllChildren);

            String ratingName = StartupMethods.getRandomString(20);

            // create random string for rating key in database

            submitInfoToDB(
                    fbRef, fbRef3, fbRef2, fbRef6,
                    fbRef7, ratingName, ratingValue,
                    numChildren, valueOfAllChildren,
                    numRatings);

            incrementPositiveOrNegativeEmojis(happy_emoji, unhappy_emoji, numHappyEmojis, numUnHappyEmojis, fbRef9);

            rating_received.show();

            fragment.dismiss();

            // inform user that ratings have been submitted with the help of a toast and dismiss dialog

        });

    }

    private static void incrementHappyEmojiCount(DatabaseReference fbRef9, int numHappyEmojis){

        numHappyEmojis = numHappyEmojis +1;

        fbRef9.child(ConstantsDB.USER_HAPPY_EMOJIS_RATING_TABLE).setValue(numHappyEmojis);

        // increment happy emojis if happy emoji is selected

    }

    private static void incrementUnhappyEmojiCount(DatabaseReference fbRef9, int numUnHappyEmojis) {

        numUnHappyEmojis = numUnHappyEmojis +1;

        fbRef9.child(ConstantsDB.USER_UNHAPPY_EMOJIS_RATING_TABLE).setValue(numUnHappyEmojis);

        // increment unhappy emojis if unhappy emoji is selected

    }

    private static void showFabIfAllInfoEntered(
            FrameLayout one_star_button_layout,
            FrameLayout one_and_a_half_stars_button_layout,
            FrameLayout two_stars_button_layout,
            FrameLayout two_and_a_half_stars_button_layout,
            FrameLayout three_stars_button_layout,
            FrameLayout three_and_a_half_stars_button_layout,
            FrameLayout four_stars_button_layout,
            FrameLayout four_and_a_half_stars_button_layout,
            FrameLayout five_stars_button_layout,
            FloatingActionButton fab){

        if (
                one_star_button_layout.isSelected() ||
                        one_and_a_half_stars_button_layout.isSelected() ||
                        two_stars_button_layout.isSelected() ||
                        two_and_a_half_stars_button_layout.isSelected() ||
                        three_stars_button_layout.isSelected() ||
                        three_and_a_half_stars_button_layout.isSelected() ||
                        four_stars_button_layout.isSelected() ||
                        four_and_a_half_stars_button_layout.isSelected() ||
                        five_stars_button_layout.isSelected()
        ){

            // show fab if emoji and star rating are selected

            fab.show();
        }

    }

    private static void selectDeselectAccButtonsHappyEmoji(Context context, MotionEvent event, View happy_emoji_view, View unhappy_emoji_view){

        CustomDialogUserStayEnded.unhappy_emoji.setSelected(false);
        CustomDialogUserStayEnded.happy_emoji.setSelected(true);

        // select happy emoji and deselect unhappy emoji

        StartupMethods.startCircularRevealAnimationUserRStayEndedDialog
                (context, happy_emoji_view, unhappy_emoji_view, event,
                        CustomDialogUserStayEnded.happy_emoji, CustomDialogUserStayEnded.unhappy_emoji);

    }

    private static void selectDeselectAccButtonsUnHappyEmoji(Context context, MotionEvent event, View happy_emoji_view, View unhappy_emoji_view){

        CustomDialogUserStayEnded.unhappy_emoji.setSelected(true);
        CustomDialogUserStayEnded.happy_emoji.setSelected(false);

        // select happy emoji and deselect unhappy emoji

        StartupMethods.startCircularRevealAnimationUserRStayEndedDialog
                (context, unhappy_emoji_view, happy_emoji_view, event,
                        CustomDialogUserStayEnded.unhappy_emoji, CustomDialogUserStayEnded.happy_emoji);

        /*

         * if device version code is equal to or higher than 5.0 Lollipop, show a circular reveal animation.
         * if device version code is lower than 5.0 Lollipop, make the selected views' reveal view visible and make all other selectable
         * views' reveal views invisible

         */

    }

    private static void submitInfoToDB(
            DatabaseReference fbRef,
            DatabaseReference fbRef3, DatabaseReference fbRef2,
            DatabaseReference fbRef6, DatabaseReference fbRef7,
            String ratingName, double ratingValue,
            int numChildren, double valueOfAllChildren, int numRatings){

        int newNumRatings = numRatings + 1;

        fbRef.setValue(newNumRatings);

      //  try {

            fbRef3.child(ratingName).setValue(CustomDialogUserStayEnded.ratingValue);

            // submit new rating to DB

            double newValue = (valueOfAllChildren + CustomDialogUserStayEnded.ratingValue) / (numChildren + 1);

            // calculate new rating average

        Log.i("Valnew", "val:" + valueOfAllChildren);

            fbRef2.setValue(newValue);
            fbRef6.setValue(newValue);
            fbRef7.setValue(0);

            // update DB on new average rating and inform DB that user has submitted their ratings

            /*

        } catch (Exception e) {

            // DO nothing

        }

        */

    }

    private static void incrementPositiveOrNegativeEmojis(
            ImageView happy_emoji, ImageView unhappy_emoji, int numHappyEmojis, int numUnHappyEmojis, DatabaseReference fbRef9){

        if (CustomDialogUserStayEnded.happy_emoji.isSelected()){

            incrementHappyEmojiCount(fbRef9, numHappyEmojis);

            Log.d("happy", "happy submitted");

        } else if (CustomDialogUserStayEnded.unhappy_emoji.isSelected()){

            Log.d("unhappy", "unhappy submitted");

            incrementUnhappyEmojiCount(fbRef9, numUnHappyEmojis);

        }

    }

}
