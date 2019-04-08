package weather.test.application.di.home.weather;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import weather.test.application.app.home.WeatherFragment;

@Subcomponent(modules = WeatherFragmentModule.class)
public interface WeatherFragmentComponent extends AndroidInjector<WeatherFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<WeatherFragment> {}
}

