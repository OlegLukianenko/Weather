package weather.test.application.di.viewmodel;

import dagger.Subcomponent;
import weather.test.application.viewmodel.MapFragmentViewModel;
import weather.test.application.viewmodel.WeatherFragmentViewModel;
import weather.test.application.viewmodel.SecondActivityViewModel;
import weather.test.application.viewmodel.PlaceFragmentViewModel;
import weather.test.application.viewmodel.SplashActivityViewModel;

@Subcomponent
public interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelComponent build();
    }

    SplashActivityViewModel splashActivityViewModel();

    SecondActivityViewModel secondActivityViewModel();

    WeatherFragmentViewModel weatherFragmentViewModel();

    PlaceFragmentViewModel placeFragmentViewModel();

    MapFragmentViewModel mapFragmentViewModel();
}
