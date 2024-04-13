package com.studentscouch.projectbostonfiles.models.interfaces;

import android.content.Context;
import android.widget.EditText;

public interface LoginActivityModelInterface {

    void isUncheckedFunc(Context context);

    void isCheckedFunc(Context context, EditText editText);

    String getUsername(Context context);

    boolean getIsChecked(Context context);
}
