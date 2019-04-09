package weather.test.application.di.home.place;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import weather.test.application.app.home.PlaceFragment;
import weather.test.application.viewmodel.PlaceFragmentViewModel;

@Module
public class PlaceFragmentModule {
    @Provides
    PlaceFragmentViewModel provideViewModel(PlaceFragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(PlaceFragmentViewModel.class);
    }

}
