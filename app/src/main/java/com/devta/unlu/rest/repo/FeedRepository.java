package com.devta.unlu.rest.repo;

import com.devta.unlu.rest.api.FeedApi;
import com.devta.unlu.rest.data.response.Posts;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created on : Jun, 23, 2020 at 16:40
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class FeedRepository {

    private final FeedApi feedApi;

    public FeedRepository(FeedApi feedApi) {
        this.feedApi = feedApi;
    }

    public Single<Posts> getPosts(int pageNumber) {
        String pageReference = null;
        switch (pageNumber) {
            case 1: pageReference = "59b3f0b0100000e30b236b7e";
                break;
            case 2: pageReference = "59ac28a9100000ce0bf9c236";
                break;
            case 3: pageReference = "59ac293b100000d60bf9c239";
                break;
        }
        return feedApi.getPosts(pageReference)
                .flatMap(new Function<Posts, SingleSource<? extends Posts>>() {
                    @Override
                    public SingleSource<? extends Posts>
                    apply(Posts posts) throws Exception {
                        return Single.just(posts);
                    }
                });
    }
}
