package com.devta.unlu.rest.api;

import com.devta.unlu.rest.data.response.Posts;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created on : Jun, 23, 2020 at 16:29
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public interface FeedApi {

    @GET("v2/{page_ref}")
    Single<Posts> getPosts(
            @Path("page_ref") String pageRef
    );

}
