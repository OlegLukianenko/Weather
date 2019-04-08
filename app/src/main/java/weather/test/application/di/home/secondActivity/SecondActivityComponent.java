package weather.test.application.di.home.secondActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import weather.test.application.app.home.SecondActivity;

@Subcomponent(modules = {SecondActivityModule.class})

public interface SecondActivityComponent extends AndroidInjector<SecondActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SecondActivity> {
    }
}