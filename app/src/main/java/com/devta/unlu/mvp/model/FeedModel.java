package com.devta.unlu.mvp.model;

import com.devta.unlu.mvp.FeedMVP;
import com.devta.unlu.rest.data.response.Posts;
import com.devta.unlu.rest.repo.FeedRepository;

import io.reactivex.Single;

/**
 * Created on : Jun, 23, 2020 at 16:55
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class FeedModel implements FeedMVP.Model {

    private FeedRepository repository;

    public FeedModel(FeedRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Posts> getPostsForPage(int pageNumber) {
        return repository.getPosts(pageNumber);
    }
}
