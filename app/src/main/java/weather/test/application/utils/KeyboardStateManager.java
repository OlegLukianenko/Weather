package weather.test.application.utils;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardStateManager {

    public static void hideSoftKeyboard(@Nullable Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null && focusedView.getWindowToken() != null) {
                    inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                }
            }
        }
    }
}