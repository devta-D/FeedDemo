package com.devta.unlu.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.devta.unlu.BuildConfig;
import com.devta.unlu.application.UnluApplication;
import com.devta.unlu.db.FeedDbHelper;
import com.devta.unlu.rest.data.response.Posts;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created on : Jun, 25, 2020 at 03:40
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class PostWriterAsync extends AsyncTask<Posts, Void, Void> {

    public static final int TYPE_INSERT = -1;
    public static final int TYPE_REFRESH = -2;

    private FeedDbHelper feedDbHelper;
    private WeakReference<Context> context;
    private int actionType;

    public PostWriterAsync(Context context, int actionType) {
        this.context = new WeakReference<>(context);
        this.actionType = actionType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        feedDbHelper = new FeedDbHelper(context.get());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(BuildConfig.DEBUG)
            Log.i("PostWriterAsync",
                    "Data Inserted " + "Type: "
                            + (actionType == TYPE_INSERT ? "INSERT" : "REFRESH"));
    }

    @Override
    protected Void doInBackground(Posts... posts) {
        for(Posts p : posts) {
            if(actionType == TYPE_INSERT) {
                feedDbHelper.insertPosts(p);
            }else {
                feedDbHelper.refresh(p);
            }
        }
        return null;
    }
}
