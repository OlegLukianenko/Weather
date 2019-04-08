package weather.test.application.di.splash;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import weather.test.application.app.splash.SplashActivity;

@Subcomponent(modules = SplashModule.class)
public interface SplashActivityComponent extends AndroidInjector<SplashActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SplashActivity> {}

}


