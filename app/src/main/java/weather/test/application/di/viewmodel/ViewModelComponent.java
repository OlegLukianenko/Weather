package weather.test.application.di.viewmodel;

import dagger.Subcomponent;
import weather.test.application.viewmodel.WeatherFragmentViewModel;
import weather.test.application.viewmodel.SecondActivityViewModel;
import weather.test.application.viewmodel.SecondFragmentViewModel;
import weather.test.application.viewmodel.SplashActivityViewModel;

@Subcomponent
public interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelComponent build();
    }

    SplashActivityViewModel splashActivityViewModel();

    SecondActivityViewModel secondActivityViewModel();

    WeatherFragmentViewModel firstFragmentViewModel();

    SecondFragmentViewModel secondFragmentViewModel();
}
