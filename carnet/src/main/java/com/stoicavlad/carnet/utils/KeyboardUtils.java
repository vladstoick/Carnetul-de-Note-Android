package com.stoicavlad.carnet.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Vlad on 1/27/14.
 */
public class KeyboardUtils {
    public static void showKeyboard(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                IBinder windowToken = view.getWindowToken();

                if (windowToken != null) {
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }
    }

    public static void dismissKeyboard(Context context, View focusedView) {
        if (context != null && focusedView != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                IBinder windowToken = focusedView.getWindowToken();

                if (windowToken != null) {
                    imm.hideSoftInputFromWindow(windowToken, 0);
                }
            }
        }
    }
}
