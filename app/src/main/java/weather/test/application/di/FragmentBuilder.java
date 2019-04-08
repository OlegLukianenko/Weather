package weather.test.application.di;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import weather.test.application.app.home.WeatherFragment;
import weather.test.application.app.home.SecondFragment;
import weather.test.application.di.home.weather.WeatherFragmentComponent;
import weather.test.application.di.home.second.SecondFragmentComponent;

@Module
public abstract class FragmentBuilder {

    @Binds
    @IntoMap
    @FragmentKey(WeatherFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindFirstFragment(WeatherFragmentComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(SecondFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindSecondFragment(SecondFragmentComponent.Builder builder);
}


