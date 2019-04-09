package weather.test.application.di;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import weather.test.application.app.home.PlaceFragment;
import weather.test.application.app.home.WeatherFragment;
import weather.test.application.di.home.place.PlaceFragmentComponent;
import weather.test.application.di.home.weather.WeatherFragmentComponent;

@Module
public abstract class FragmentBuilder {

    @Binds
    @IntoMap
    @FragmentKey(WeatherFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindWeatherFragment(WeatherFragmentComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(PlaceFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindPlacedFragment(PlaceFragmentComponent.Builder builder);
}


