package weather.test.application.viewmodel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import javax.inject.Inject;

import weather.test.application.data.retrofit.RepositoryApi;
import weather.test.application.data.retrofit.response.Weather;


public class WeatherFragmentViewModel extends ViewModel {

    @Inject
    RepositoryApi repositoryApi;

    @Inject
    public WeatherFragmentViewModel() {
    }

    private MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Weather.DailyWeather> dailyWeatherMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBarVisibility = new MutableLiveData<>();

    public void sendWeatherRequest(double latitude, double longitude) {
        repositoryApi.getWeatherFromApi(latitude, longitude, weatherMutableLiveData);
    }

    public MutableLiveData<Weather> getWeatherResponseMutable() {
        return weatherMutableLiveData;
    }


    public MutableLiveData<Weather.DailyWeather> getDailyWeatherMutable() {
        return dailyWeatherMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgressBarEvent() {
        return progressBarVisibility;
    }

}
