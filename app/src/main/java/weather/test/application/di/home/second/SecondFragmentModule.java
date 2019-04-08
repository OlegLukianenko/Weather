package weather.test.application.di.home.second;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import weather.test.application.app.home.SecondFragment;
import weather.test.application.viewmodel.SecondFragmentViewModel;

@Module
public class SecondFragmentModule {
    @Provides
    SecondFragmentViewModel provideViewModel(SecondFragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(SecondFragmentViewModel.class);
    }

}
