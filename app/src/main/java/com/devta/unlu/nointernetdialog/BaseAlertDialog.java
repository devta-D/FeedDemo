package com.devta.unlu.nointernetdialog;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

import androidx.appcompat.app.AlertDialog;

/**
 * @author Divyanshu Tayal
 */

public abstract class BaseAlertDialog extends AlertDialog {

    public BaseAlertDialog(Context context) {
        super(context);
    }

    @Override
    public void show() {
        Activity activity = (Activity)((ContextThemeWrapper) getContext()).getBaseContext();
        if(activity != null && !activity.isFinishing()) super.show();
    }

    @Override
    public void dismiss() {
        Activity activity = (Activity)((ContextThemeWrapper) getContext()).getBaseContext();
        if(activity != null && !activity.isFinishing()) super.dismiss();
    }

}
