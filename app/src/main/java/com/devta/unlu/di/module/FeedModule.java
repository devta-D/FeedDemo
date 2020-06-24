package com.devta.unlu.di.module;

import com.devta.unlu.mvp.FeedMVP;
import com.devta.unlu.mvp.model.FeedModel;
import com.devta.unlu.mvp.presenter.FeedPresenter;
import com.devta.unlu.rest.api.FeedApi;
import com.devta.unlu.rest.repo.FeedRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created on : Jun, 23, 2020 at 17:48
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

@Module (includes = {NetworkModule.class})
public class FeedModule {

    @Provides
    public FeedApi providesApi(Retrofit retrofit) {
        return retrofit.create(FeedApi.class);
    }

    @Provides
    public FeedRepository providesRepository(FeedApi feedApi) {
        return new FeedRepository(feedApi);
    }

    @Provides
    public FeedMVP.Model providesModel(FeedRepository feedRepository) {
        return new FeedModel(feedRepository);
    }

    @Provides
    public FeedMVP.Presenter providesPresenter(FeedMVP.Model model) {
        return new FeedPresenter(model);
    }

}
