package com.devta.unlu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on : Jun, 24, 2020 at 23:12
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class DevUtil {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String[] getFormattedDatetime(long millis) {
        String date = new SimpleDateFormat("MMM dd 'at' hh:mm a")
                .format(new Date(millis));
        return date.split("at");
    }

}
