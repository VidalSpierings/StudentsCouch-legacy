package com.studentscouch.projectbostonfiles.ui;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.view_adapters.RecyclerViewAdapter3;
import com.studentscouch.projectbostonfiles.view_adapters.MessageActivityInformation;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {

    ArrayList<MessageActivityInformation> information;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        information = new ArrayList<>();

        information.add(new MessageActivityInformation(R.drawable.ian, R.drawable.circle_selected_purple));
        information.add(new MessageActivityInformation(R.drawable.ian2, R.drawable.divider_mini));
        information.add(new MessageActivityInformation(R.drawable.ian3, R.drawable.divider_mini));
        information.add(new MessageActivityInformation(R.drawable.ian3, R.drawable.circle_selected_pink));
        information.add(new MessageActivityInformation(R.drawable.ian2, R.drawable.divider_mini));
        information.add(new MessageActivityInformation(R.drawable.ian, R.drawable.divider_mini));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);

        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_lowres));

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        RecyclerViewAdapter3 adapter = new RecyclerViewAdapter3(getApplicationContext(), information);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerView.addItemDecoration(itemDecorator);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, 0);

    }
}
