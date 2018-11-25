package com.example.samuel.lab2;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class contains some useful static methods
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class Utils {
    /**
     * Hides the keyboard in the given activity
     * @param activity the activity in which the keyboard should be hidden
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int countBytesInInput(InputStream in) throws IOException {
        byte[] buff = new byte[8000];

        int bytesRead = 0;

        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        while((bytesRead = in.read(buff)) != -1) {
            bao.write(buff, 0, bytesRead);
        }

        byte[] data = bao.toByteArray();

        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        return bin.available();
    }
}
