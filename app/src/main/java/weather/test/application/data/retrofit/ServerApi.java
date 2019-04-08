package weather.test.application.data.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weather.test.application.data.retrofit.response.LocationCity;
import weather.test.application.data.retrofit.response.Weather;

public interface ServerApi {

    @GET ("https://api.meetup.com/2/cities/?country=ua&page=200&format=json&sign=true&key=253777f6721f535d5c7174423b273d")
    Call<LocationCity> getListCityFromApi();


    @GET("forecast/hourly")
    Call<Weather> getForecast(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("units") String units,
            @Query("appid") String appid
    );
}