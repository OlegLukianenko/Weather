package weather.test.application.di.home.map;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import weather.test.application.app.home.MapFragment;
import weather.test.application.viewmodel.MapFragmentViewModel;

@Module
public class MapFragmentModule {
    @Provides
    MapFragmentViewModel provideViewModel(MapFragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(MapFragmentViewModel.class);
    }

}