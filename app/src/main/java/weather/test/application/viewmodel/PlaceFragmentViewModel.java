package weather.test.application.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import weather.test.application.data.retrofit.RepositoryApi;
import weather.test.application.data.retrofit.response.City;
import weather.test.application.data.retrofit.response.LocationCity;

public class PlaceFragmentViewModel extends ViewModel {

    @Inject
    RepositoryApi repositoryApi;

    @Inject
    public PlaceFragmentViewModel() {
    }

    private MutableLiveData<Boolean> progressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<LocationCity> cityMutableLiveData = new MutableLiveData<>();

    public void sendCityRequest() {
        repositoryApi.getListCityFromApi(cityMutableLiveData);
    }

    public MutableLiveData<LocationCity> getListCityFromApi() {
        return cityMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgressBarEvent() {
        return progressBarVisibility;
    }

}
