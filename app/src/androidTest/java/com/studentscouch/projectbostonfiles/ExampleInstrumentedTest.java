package com.studentscouch.projectbostonfiles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.studentscouch.projectbostonfiles.ui.ConfirmRegistrationActivity;
import com.studentscouch.projectbostonfiles.ui.LoginActivity;
import com.studentscouch.projectbostonfiles.ui.MainActivity;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen2;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityScreen3;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen1;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen2;
import com.studentscouch.projectbostonfiles.ui.RegisterActivityYesScreen3;
import com.studentscouch.projectbostonfiles.ui.RegisterActivtyScreen1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;
import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.registerIdlingResources;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.Intents.intended;

import static java.util.EnumSet.allOf;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);
    @Rule public final ActivityTestRule<LoginActivity> logIn = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = getTargetContext();

        assertEquals("com.studentscouch.projectbostonfiles", appContext.getPackageName());

    }

    @Test
    public void testUserRegisterNo () throws Exception {

        main.launchActivity(new Intent());

        onView(withId(R.id.register_layout)).perform(click());

        Intents.release();

        Intents.init();

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivtyScreen1.class)));

        onView(withId(R.id.register_layout)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityScreen2.class)));

        onView(withId(R.id.firstName_edittext)).perform(replaceText("EspressoTestF")); closeSoftKeyboard();
        onView(withId(R.id.lastName_edittext)).perform(replaceText("EspressoTestL")); closeSoftKeyboard();
        onView(withId(R.id.email_edittext)).perform(replaceText("sample_email@sample.com")); closeSoftKeyboard();
        onView(withId(R.id.password_editText)).perform(replaceText("testpassword")); closeSoftKeyboard();
        onView(withId(R.id.gender_female)).perform(click());
        onView(withId(R.id.birthdate_edittext)).perform(click());
        //onView(withId(R.id.spinner)).perform(click());

        /*

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityScreen3.class)));

        onView(withId(R.id.register_layout)).perform(click());

        // ADD CHOOSING IMAGE FROM GALLERY FUNCTIONALITY!!!!!!

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), ConfirmRegistrationActivity.class)));

        onView(withId(R.id.log_in_layout)).perform(click());

        //onView(withId(R.id.fab)).perform(click());

        */

    }

    @Test
    public void testUserRegisterYes () throws Exception {

        main.launchActivity(new Intent());

        onView(withId(R.id.register_layout)).perform(click());

        Intents.init();

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivtyScreen1.class)));

        onView(withId(R.id.log_in_layout)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityScreen2.class)));

        closeSoftKeyboard();

        onView(withId(R.id.firstName_edittext)).perform(replaceText("EspressoTestF")); closeSoftKeyboard();
        onView(withId(R.id.lastName_edittext)).perform(replaceText("EspressoTestL")); closeSoftKeyboard();
        onView(withId(R.id.email_edittext)).perform(replaceText("sample_email@sample.com")); closeSoftKeyboard();
        onView(withId(R.id.password_editText)).perform(replaceText("testpassword")); closeSoftKeyboard();
        onView(withId(R.id.gender_female)).perform(click());
        onView(withId(R.id.birthdate_edittext)).perform(click());

        //onView(withId(R.id.spinner)).perform(click());

        /*

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityScreen3.class)));

        onView(withId(R.id.register_layout)).perform(click());

        // ADD CHOOSING IMAGE FROM GALLERY FUNCTIONALITY!!!!!!

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityYesScreen1.class)));

        onView(withId(R.id.register_layout)).perform(click());

        // ADD CHOOSING IMAGE FROM GALLERY FUNCTIONALITY!!!!!!

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityYesScreen2.class)));

        //onView(withId(R.id.userName_editText)).perform(click());

        onView(withId(R.id.lastName_edittext)).perform(typeText("sampleStreet"));
        onView(withId(R.id.userName_editText)).perform(typeText("55"));

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), RegisterActivityYesScreen3.class)));

        onView(withId(R.id.firstName_edittext)).perform(typeText("sampleTitle"));
        onView(withId(R.id.lastName_edittext)).perform(typeText("sampleDesc"));
        onView(withId(R.id.userName_editText)).perform(typeText("22"));
        onView(withId(R.id.one_layout)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), ConfirmRegistrationActivity.class)));

        onView(withId(R.id.log_in_layout)).perform(click());

        //onView(withId(R.id.fab)).perform(click());

        */

    }

}
