package weather.test.application.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class MapFragmentViewModel extends ViewModel {

    @Inject
    public MapFragmentViewModel() {
    }

    private MutableLiveData<Boolean> placeIsAvailable = new MutableLiveData<>();

    public MutableLiveData<Boolean> getPlaceIsAvailable() {
        return placeIsAvailable;
    }

}
