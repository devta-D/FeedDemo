package com.devta.unlu.nointernetdialog;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author Divyanshu Tayal
 */

public class NoInternetController {

    public static class NoInternetParams{

        NoInternetDialog.Callbacks callbacks;
        boolean retryAvailable;
        final Context context;

        public NoInternetParams(@NonNull Context context, boolean retryAvailable,
                                NoInternetDialog.Callbacks callbacks) {
            this.context = context;
            this.retryAvailable = retryAvailable;
            this.callbacks = callbacks;
        }
    }

}
