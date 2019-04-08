package weather.test.application.di.home.second;


import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import weather.test.application.app.home.SecondFragment;

@Subcomponent(modules = SecondFragmentModule.class)
public interface SecondFragmentComponent extends AndroidInjector<SecondFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SecondFragment> {}
}
