package com.devta.unlu.mvp;

import com.devta.unlu.rest.data.response.Posts;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created on : Jun, 23, 2020 at 16:53
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public interface FeedMVP {

    interface Model {
        Single<Posts> getPostsForPage(int pageNumber);
    }

    interface View {
        void onPostsLoaded(Posts feed);
        void onPostsError(String errorMessage);
    }

    interface Presenter {
        void setView(FeedMVP.View view);
        void loadPosts(int page);
        void onDestroy();
    }

}
