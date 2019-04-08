package weather.test.application.di.home.weather;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import weather.test.application.app.home.WeatherFragment;
import weather.test.application.app.home.adapter.HorizontalWeatherRecyclerAdapter;
import weather.test.application.app.home.adapter.WeatherRecyclerAdapter;
import weather.test.application.viewmodel.WeatherFragmentViewModel;

@Module
public class WeatherFragmentModule {

    @Provides
    WeatherFragmentViewModel provideViewModel(WeatherFragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(WeatherFragmentViewModel.class);
    }

    @Provides
    WeatherRecyclerAdapter provideWeatherAdapter(WeatherRecyclerAdapter.WeatherItemListListener listener) {
        return new WeatherRecyclerAdapter(listener);
    }

    @Provides
    HorizontalWeatherRecyclerAdapter provideHorizontalWeatherRecyclerAdapter() {
        return new HorizontalWeatherRecyclerAdapter();
    }

    @Provides
    WeatherRecyclerAdapter.WeatherItemListListener provideWeatherListListener(WeatherFragment fragment) {
        return fragment;
    }
}
