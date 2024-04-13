package com.studentscouch.projectbostonfiles.miscellaneous_files;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.studentscouch.projectbostonfiles.Constants;

import java.net.PasswordAuthentication;
import java.util.concurrent.TimeUnit;

import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.module.Module;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.firebase.adapter.module.FirebaseModule;
import sdk.chat.firebase.push.FirebasePushModule;
import sdk.chat.firebase.ui.FirebaseUIModule;
import sdk.chat.firebase.upload.FirebaseUploadModule;
import sdk.chat.ui.module.UIModule;

public class MessagingTestActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {

          //  ChatSDKFirebase.quickStart(this, ">>>TOD0: FILL IN DATABASE PATH HERE<<<", Constants.googleMapsApiKey, true);

            ChatSDK.builder()
                    .setGoogleMaps(Constants.googleMapsApiKey)
            .setPublicChatRoomLifetimeMinutes(TimeUnit.DAYS.toMinutes(Constants.CHAT_LIFETIME_IN_DAYS))

            // chats are automatically deleted after one year
            // to save up space on DB and keep users' chat rooms tidy

            .build()
                    .addModule(
                            FirebaseModule.builder()
                                    .setFirebaseRootPath(">>>TODO: FILL IN DATABASE PATH HERE<<<")
                                    .build())
                    .addModule(UIModule.shared())
                    .addModule(FirebaseUploadModule.shared())
                    .addModule(FirebasePushModule.shared())
                    .addModule(FirebaseUIModule.builder().setProviders(EmailAuthProvider.PROVIDER_ID, PhoneAuthProvider.PROVIDER_ID).build())
            .build()
            .activate(this, "studentscouchnl@gmail.com");

        }

        catch (Exception e) {

        e.printStackTrace();

        }

    }
}
