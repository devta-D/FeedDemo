package com.devta.unlu.nointernetdialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.devta.unlu.R;

/**
 * @author Divyanshu Tayal
 */

public class NoInternetDialog extends BaseAlertDialog {

    public static final int BUTTON_RETRY = 1;
    public static final int BUTTON_OKAY = 2;

    public static class Builder{

        private NoInternetController.NoInternetParams P;

        public Builder(@NonNull Context context){
            this(context, false);
        }

        public Builder(@NonNull Context context, boolean retryAvailable){
            this(context, retryAvailable, null);
        }

        public Builder(@NonNull Context context, boolean retryAvailable, Callbacks callbacks){
            this.P = new NoInternetController.NoInternetParams(context, retryAvailable, callbacks);
        }

        public Builder setCallbacks(Callbacks callbacks){
            this.P.callbacks = callbacks;
            return this;
        }

        public Builder setRetryAvailable(boolean retryAvailable){
            this.P.retryAvailable = retryAvailable;
            return this;
        }

        public NoInternetDialog show(){
            return new NoInternetDialog(P.context, P.retryAvailable, P.callbacks);
        }

    }

    public NoInternetDialog(Context context, final boolean retryAvailable, final Callbacks callbacks) {
        super(context);

        setTitle(R.string.txt_no_internet);
        setMessage(context.getString(R.string.txt_no_internet_text));

        String buttonText = retryAvailable ? context.getString(R.string.btn_text_retry) :
                context.getString(R.string.btn_text_ok);

        //setCancelable(false);
        //setCanceledOnTouchOutside(false);

        setButton(BUTTON_POSITIVE, buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                if(callbacks != null) callbacks.onInternetDialogButtonClick(
                        retryAvailable ? BUTTON_RETRY : BUTTON_OKAY);
            }
        });

        Activity activity = (Activity)context;
        if(!activity.isFinishing() && !isShowing()) this.show();

    }

    public interface Callbacks{

        void onInternetDialogButtonClick(int buttonType);
    }
}
