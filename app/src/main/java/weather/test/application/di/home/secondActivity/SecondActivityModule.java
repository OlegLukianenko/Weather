package weather.test.application.di.home.secondActivity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import weather.test.application.app.home.SecondActivity;
import weather.test.application.viewmodel.SecondActivityViewModel;

@Module
public class SecondActivityModule {

    @Provides
    SecondActivityViewModel provideSecondActivityViewModel(SecondActivity activity, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(activity, factory).get(SecondActivityViewModel.class);
    }
}
