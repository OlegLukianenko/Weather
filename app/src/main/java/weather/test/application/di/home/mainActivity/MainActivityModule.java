package weather.test.application.di.home.mainActivity;

import dagger.Module;
import weather.test.application.di.home.weather.WeatherFragmentComponent;
import weather.test.application.di.home.second.SecondFragmentComponent;

@Module (subcomponents = {
        WeatherFragmentComponent.class,
        SecondFragmentComponent.class})
public class MainActivityModule {

}
