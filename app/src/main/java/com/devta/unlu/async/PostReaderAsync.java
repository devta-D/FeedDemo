package com.devta.unlu.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.devta.unlu.BuildConfig;
import com.devta.unlu.application.UnluApplication;
import com.devta.unlu.db.FeedDbHelper;
import com.devta.unlu.rest.data.response.Posts;

import java.lang.ref.WeakReference;

/**
 * Created on : Jun, 25, 2020 at 03:52
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class PostReaderAsync extends AsyncTask<Void, Void, Posts> {

    private FeedDbHelper dbHelper;
    private WeakReference<Context> context;
    private PostReaderListener listener;

    public PostReaderAsync(Context context, PostReaderListener listener) {
        this.context = new WeakReference<>(context);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dbHelper = new FeedDbHelper(context.get());
    }

    @Override
    protected void onPostExecute(Posts posts) {
        super.onPostExecute(posts);
        if(BuildConfig.DEBUG) {
            int records = posts == null ? 0 : posts.getPosts().size();
            Log.i("PostReaderAsync",  records + " Records found");
        }
        if(listener != null)
            listener.onPostExecutionOfReadingDB(posts);
    }

    @Override
    protected Posts doInBackground(Void... voids) {
        return dbHelper.getPosts();
    }

    public interface PostReaderListener {
        void onPostExecutionOfReadingDB(Posts posts);
    }
}
