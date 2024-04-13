package com.studentscouch.projectbostonfiles.models.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

public interface ApartmentActivityModelInterface {

    void toggleButtonClicked(final Context context, CompoundButton compoundButton);

    void launchEditActivity(Context context, Activity activity, View edit_button_view);

    void launchGmapsIntent(Context context);

}
