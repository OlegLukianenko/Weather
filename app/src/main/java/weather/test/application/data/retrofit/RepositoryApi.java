package weather.test.application.data.retrofit;

import android.arch.lifecycle.MutableLiveData;

import weather.test.application.data.retrofit.response.LocationCity;
import weather.test.application.data.retrofit.response.Weather;

public interface RepositoryApi {

     MutableLiveData<LocationCity> getListCityFromApi();
     void getWeatherFromApi (double latitude, double longitude, MutableLiveData<Weather> weatherMutableLiveData);
}
