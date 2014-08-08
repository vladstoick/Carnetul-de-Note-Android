package com.stoicavlad.carnet.test;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckedTextView;
import android.widget.ListView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openContextualActionModeOverflowMenu;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isChecked;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isNotChecked;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.ui.setup.SetupActivity;

import butterknife.ButterKnife;


/**
 * Created by Vlad on 08-Aug-14.
 */
public class TestSetupActivity extends ActivityInstrumentationTestCase2<SetupActivity> {
    public TestSetupActivity() {
        super("com.stoicavlad.carnet.test.ui", SetupActivity.class);
    }

    @Override
    public void setUp() throws Exception {

        super.setUp();
        getActivity().deleteDatabase("carnet.db");

        getActivity();


    }


    public void testCheckingWorks() throws Exception {
        onView(withText("OK")).perform(click());

        String[] strings = getActivity().getResources().getStringArray(R.array.materii_default);

        onView(withText(strings[1])).perform(click()).check(matches(isChecked()));
        onView(withText(strings[1])).perform(click()).check(matches(isNotChecked()));
        onView(withText(strings[1])).perform(click());
    }

    public void testNextButtonWorks() throws Exception {
        onView(withText("OK")).perform(click());
        onView(withText(R.string.action_next)).perform(click());
    }

    @Override
    public void tearDown() throws Exception {

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        settings.edit().remove("SETUP_DONE_V2").commit();
        super.tearDown();

    }
}
