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

    final public MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
    final public MutableLiveData<Weather.DailyWeather> dailyWeatherMutableLiveData = new MutableLiveData<>();

    public void sendWeatherRequest(double latitude, double longitude) {
        repositoryApi.getWeatherFromApi(latitude, longitude, weatherMutableLiveData);
    }

    public MutableLiveData<Weather> getWeatherResponseMutable() {
        return weatherMutableLiveData;
    }


    public MutableLiveData<Weather.DailyWeather> getDailyWeatherMutable() {
        return dailyWeatherMutableLiveData;
    }

}
