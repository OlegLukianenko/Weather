package weather.test.application.di.app;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import weather.test.application.app.home.MainActivity;
import weather.test.application.app.home.SecondActivity;
import weather.test.application.app.splash.SplashActivity;
import weather.test.application.di.home.mainActivity.MainActivityComponent;
import weather.test.application.di.home.secondActivity.SecondActivityComponent;
import weather.test.application.di.splash.SplashActivityComponent;

@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSplashActivity(SplashActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(SecondActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSecondActivity(SecondActivityComponent.Builder builder);
}