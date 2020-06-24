package com.devta.unlu.di.component;

import com.devta.unlu.di.module.FeedModule;
import com.devta.unlu.ui.activity.ActivityFeed;
import com.devta.unlu.ui.activity.ActivitySplash;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on : Jun, 23, 2020 at 17:54
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

@Singleton
@Component( modules = {FeedModule.class})
public interface AppComponent {

    void inject(ActivityFeed activityFeed);

    void inject(ActivitySplash activitySplash);

}
