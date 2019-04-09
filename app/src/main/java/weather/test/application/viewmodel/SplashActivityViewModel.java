package weather.test.application.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import javax.inject.Inject;
import weather.test.application.data.retrofit.RepositoryApi;
import weather.test.application.data.retrofit.response.LocationCity;

public class SplashActivityViewModel extends ViewModel {

    @Inject
    RepositoryApi repositoryApi;

    private MutableLiveData<Boolean> progressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<Boolean> internetIsAvailable = new MutableLiveData<>();

    @Inject
    public SplashActivityViewModel() {
    }

    public MutableLiveData<Boolean> getInternetIsAvailable() {
        return internetIsAvailable;
    }

    public MutableLiveData<Boolean> getProgressBarEvent() {
        return progressBarVisibility;
    }


}
