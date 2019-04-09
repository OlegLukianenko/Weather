package weather.test.application.di.home.place;


import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import weather.test.application.app.home.PlaceFragment;

@Subcomponent(modules = PlaceFragmentModule.class)
public interface PlaceFragmentComponent extends AndroidInjector<PlaceFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PlaceFragment> {}
}
