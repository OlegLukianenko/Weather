package weather.test.application.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import weather.test.application.app.App;
import weather.test.application.di.app.AppComponent;
import weather.test.application.di.app.DaggerAppComponent;
import weather.test.application.di.viewmodel.Injectable;

public class InjectionHelper {

    private AppComponent appComponent;

    @Inject
    public InjectionHelper(App app) {
        appComponent = DaggerAppComponent
                .builder()
                .application(app)
                .build();
        appComponent.inject(app);

        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


    private void handleActivity(Activity activity) {

        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
            if (activity instanceof FragmentActivity) {
                ((FragmentActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                            @Override
                            public void onFragmentCreated(@NonNull FragmentManager fragmentManager,
                                                          @NonNull Fragment fragment,
                                                          Bundle savedInstanceState) {
                                if (fragment instanceof Injectable) {
                                    AndroidSupportInjection.inject(fragment);
                                }
                            }
                        }, true);

            }

        } else if (activity instanceof Injectable) {
            AndroidInjection.inject(activity);
        }
    }

}
