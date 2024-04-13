package com.studentscouch.projectbostonfiles.db.interfaces;

import com.google.firebase.auth.FirebaseAuth;
import com.studentscouch.projectbostonfiles.viewmodels.interfaces_implementers.interfaces.OnEmailCheckListener;

public interface RegisterActivityScreen2DBInterface {

    void isCheckEmail(final String email, FirebaseAuth fbAuth, final OnEmailCheckListener listener);

}
