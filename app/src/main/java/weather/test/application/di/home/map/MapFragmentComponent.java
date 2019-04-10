package weather.test.application.di.home.map;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import weather.test.application.app.home.MapFragment;

@Subcomponent(modules = MapFragmentModule.class)
public interface MapFragmentComponent extends AndroidInjector<MapFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MapFragment> {}
}