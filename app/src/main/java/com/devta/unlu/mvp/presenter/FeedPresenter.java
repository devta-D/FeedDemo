package com.devta.unlu.mvp.presenter;

import androidx.core.util.Preconditions;

import com.devta.unlu.application.UnluApplication;
import com.devta.unlu.mvp.FeedMVP;
import com.devta.unlu.rest.data.response.Posts;
import com.devta.unlu.util.DevUtil;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on : Jun, 23, 2020 at 16:56
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class FeedPresenter implements FeedMVP.Presenter {

    private FeedMVP.Model model;
    private FeedMVP.View view;

    private Disposable postsDisposable;

    public FeedPresenter(FeedMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(FeedMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadPosts(final int page) {
        postsDisposable = model.getPostsForPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Posts>(){
                    @Override
                    public void onSuccess(Posts posts) {
                        if(view == null) return;
                        view.onPostsLoaded(posts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(view == null) return;
                        view.onPostsError(e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if(postsDisposable != null && !postsDisposable.isDisposed()) {
            postsDisposable.dispose();
            postsDisposable = null;
        }
    }
}
