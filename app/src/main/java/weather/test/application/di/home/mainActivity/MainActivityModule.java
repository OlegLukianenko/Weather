package weather.test.application.di.home.mainActivity;

import dagger.Module;
import weather.test.application.di.home.map.MapFragmentComponent;
import weather.test.application.di.home.weather.WeatherFragmentComponent;
import weather.test.application.di.home.place.PlaceFragmentComponent;

@Module (subcomponents = {
        WeatherFragmentComponent.class,
        PlaceFragmentComponent.class,
        MapFragmentComponent.class})
public class MainActivityModule {

}
