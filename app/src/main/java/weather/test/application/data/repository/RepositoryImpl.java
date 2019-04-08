package weather.test.application.data.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weather.test.application.data.retrofit.RepositoryApi;
import weather.test.application.data.retrofit.ServerApi;
import weather.test.application.data.retrofit.response.LocationCity;
import weather.test.application.data.retrofit.response.Weather;

import static android.content.ContentValues.TAG;

public class RepositoryImpl implements RepositoryApi {

    @Inject
    ServerApi serverApi;

    @Inject
    public RepositoryImpl() {
    }

    final MutableLiveData<LocationCity> data = new MutableLiveData<>();


    @Override
    public MutableLiveData<LocationCity> getListCityFromApi() {
        Call<LocationCity> messages = serverApi.getListCityFromApi();
        messages.enqueue(new Callback<LocationCity>() {
            @Override
            public void onResponse(Call<LocationCity> call, Response<LocationCity> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LocationCity> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
        return data;
    }

    @Override
    public void getWeatherFromApi(double latitude, double longitude, MutableLiveData<Weather> weatherMutableLiveData) {
        Call<Weather> messages = serverApi.getForecast(latitude, longitude, "metric", "3e3d80dd41d9da1fd540323b71f18c8a");
        messages.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                weatherMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}
