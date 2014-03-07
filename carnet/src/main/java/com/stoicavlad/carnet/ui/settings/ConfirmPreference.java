package com.stoicavlad.carnet.ui.settings;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.stoicavlad.carnet.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * The {@link ConfirmationDialog} is a preference subclass that shows
 * a dialog with Yes and No buttons, for the purpose of confirming an action.
 *
 * This preference will persist a boolean into the SharedPreferences key
 * dictated by android:key in the XML preference.
 *
 * Derived from:
 * - http://stackoverflow.com/questions/5365310/creating-a-dialogpreference-from-xml/8818446#8818446
 * - YesNoPreference.java in Android Private SDK
 *
 * @author TeamRocket
 */
public class ConfirmPreference extends DialogPreference {
    /** The Yes/No value of the box. */
    private boolean mValue;

    /**
     * constructor
     * @param context
     * @param attrs
     */
    public ConfirmPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (callChangeListener(positiveResult)) {
            setValue(positiveResult);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getBoolean(index, false);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedBoolean(mValue) :
                (Boolean) defaultValue);
    }

    @Override
    public boolean shouldDisableDependents() {
        return !mValue || super.shouldDisableDependents();
    }

    public void setValue(boolean value) {
        mValue = value;

        persistBoolean(value);

        notifyDependencyChange(!value);
    }

    public boolean getValue() {
        return mValue;
    }

}