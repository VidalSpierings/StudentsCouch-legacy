package com.studentscouch.projectbostonfiles.ui;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.app_back_end.ReportABugActivityMethods;

public class ReportABugActivity extends AppCompatActivity {

    private FrameLayout send_email_button_layout;

    private TextView description_textView;
    private TextView send_email_textView;

    private LinearLayout send_bug_layout;

    private float send_bug_layout_org_pos_y;

    float below_bottom_layout_y;

    private View bottom_of_layout;

    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_abug);

        linkXmlElementsToJavaVariables();

        setTypefaces();

        getLayoutElementsOrigCoordinatesY();

        Intent intent = getIntent();

        ReportABugActivityMethods.animateActivityTransition(getApplicationContext(), send_bug_layout, send_bug_layout_org_pos_y);

        // animate activity enter transition

        ReportABugActivityMethods.setBackground(getApplicationContext(), intent, backgroundImage);

        // set activity background image

        send_email_button_layout.setOnClickListener(view -> {

            ReportABugActivityMethods.sendEmailToStudentsCouch(getApplicationContext(), ReportABugActivity.this);

            // open email app with StudentsCouch contact email as recipient

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void linkXmlElementsToJavaVariables(){

        description_textView = findViewById(R.id.description_textView);
        send_email_textView = findViewById(R.id.send_email_textView);

        backgroundImage = findViewById(R.id.backgroundImage);

        send_email_button_layout = findViewById(R.id.send_email_button_layout);

        bottom_of_layout = findViewById(R.id.bottom_of_layout);

        send_bug_layout = findViewById(R.id.send_bug_layout);

        // link java variables with xml layout views

    }

    private void setTypefaces(){

        final Typeface tp = Typeface.createFromAsset(getBaseContext().getAssets(),"project_boston_typeface.ttf");

        description_textView.setTypeface(tp);
        send_email_textView.setTypeface(tp);

    }

    private void getLayoutElementsOrigCoordinatesY(){

        send_bug_layout_org_pos_y = send_bug_layout.getY();

        below_bottom_layout_y = bottom_of_layout.getY();

        // get current Y coordinates of send bug and bottom_of_layout objects

    }

}
