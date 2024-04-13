package com.studentscouch.projectbostonfiles.app_back_end;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;

import static android.content.Context.MODE_PRIVATE;

public class ReportABugActivityMethods {

    private static Animator.AnimatorListener animatorListener;

    static Intent i;

    public static void animateActivityTransition(Context context, final LinearLayout send_bug_layout, float send_bug_layout_org_pos_y){

        // copy layoutparams attributes of relativelayout to a set of LayourParams independent from the real relativelayoutParams

        send_bug_layout.setVisibility(View.INVISIBLE);

        initAnimatorListener(send_bug_layout);

        startAnimation(context, send_bug_layout, send_bug_layout_org_pos_y);

        send_bug_layout.setVisibility(View.VISIBLE);

        // make send bug button visible

    }

    private static void startAnimation(Context context, LinearLayout send_bug_layout, float send_bug_layout_org_pos_y){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        // get screen metrics.
        // using DisplayMetrics class ensures that animations look the same on all screen resolutions

        ObjectAnimator anim = ObjectAnimator.ofFloat(send_bug_layout, "translationY", metrics.heightPixels - send_bug_layout_org_pos_y, send_bug_layout_org_pos_y);

        // create objectAnimator object

        anim.setDuration(400);

        // set animation duration

        anim.addListener(animatorListener);

        // add created animationListener to anim object

        anim.start();

    }

    public static void setBackground(Context context, Intent intent, ImageView backgroundImage){

        if (intent != null) {

            SharedPreferences prefs = context.getSharedPreferences("background_image", MODE_PRIVATE);
            String bitmap = prefs.getString("string", null);

            Bitmap image = StartupMethods.StringToBitMap(bitmap);

            backgroundImage.setImageBitmap(image);

            // set Activity background, which is the city/village the user last visited
            // while in the City Activity

        }

    }

    public static void sendEmailToStudentsCouch (Context context, AppCompatActivity appCompatActivity){

        initIntent(context);

        try {

            appCompatActivity.startActivity(Intent.createChooser(i, "Send mail..."));

            // set Intent chooser title

        } catch (android.content.ActivityNotFoundException ex) {

            copyProjectWebsiteToClipboard(context);
        }

        // inform user that an e-mail app was not found on their phone
        // and copy StudentsCouch email to clipboard

    }

    private static void copyProjectWebsiteToClipboard(Context context){

        Toast.makeText(context, context.getResources().getString(R.string.could_not_find_email), Toast.LENGTH_LONG).show();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("email", "studentscouchnl@gmail.com");
        clipboard.setPrimaryClip(clip);

    }

    private static void initIntent(Context context){

        i = new Intent(Intent.ACTION_SEND);
        i.setType("text/html");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"studentscouchnl@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.bug_in_app));

        // create 'send email' intent, insert recipient email address and email title

    }

    private static void initAnimatorListener(LinearLayout send_bug_layout){

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                send_bug_layout.setVisibility(View.VISIBLE);

                // make send bug button visible when activity enter animation start

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

    }
