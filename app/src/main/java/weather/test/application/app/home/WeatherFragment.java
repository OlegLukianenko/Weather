package weather.test.application.app.home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import weather.test.application.R;
import weather.test.application.app.home.adapter.HorizontalWeatherRecyclerAdapter;
import weather.test.application.app.home.adapter.WeatherRecyclerAdapter;
import weather.test.application.base.BaseFragment;
import weather.test.application.data.retrofit.response.Weather;
import weather.test.application.databinding.FragmentWeatherBinding;
import weather.test.application.di.viewmodel.Injectable;
import weather.test.application.viewmodel.WeatherFragmentViewModel;

public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> implements
        Injectable, WeatherRecyclerAdapter.WeatherItemListListener {

    @Inject
    WeatherFragmentViewModel viewModel;

    @Inject
    WeatherRecyclerAdapter weatherRecyclerAdapter;

    @Inject
    HorizontalWeatherRecyclerAdapter horizontalWeatherRecyclerAdapter;

    @Inject
    protected SharedPreferences sharedPreferences;

    Map<String, List<Weather.DailyWeather>> linkedHashMap;

    @Override
    protected int getLayout() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        float latitude = sharedPreferences.getFloat("latitude", 0.0f);
        float longitude = sharedPreferences.getFloat("longitude", 0.0f);
        viewModel.sendWeatherRequest(latitude, longitude);

        initSubscribers();
        initWeatherAdapter();
    }

    private void initWeatherAdapter() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycleView.setAdapter(weatherRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recycleViewHorizontal.setLayoutManager(linearLayoutManager);
        binding.recycleViewHorizontal.setAdapter(horizontalWeatherRecyclerAdapter);
    }

    private void initSubscribers() {

        viewModel.getDailyWeatherMutable().observe(this, dailyWeatherResponseWrap -> {
            Glide.with(this)
                    .load("https://openweathermap.org/img/w/" + dailyWeatherResponseWrap.weather.get(0).icon + ".png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.iconWeather);
        });

        viewModel.getWeatherResponseMutable().observe(this, weatherResponseWrap ->
        {
            linkedHashMap = new LinkedHashMap<>();
            List<Weather.DailyWeather> listWeathers = new ArrayList<>();

            String predDay = "";

            List<Weather.DailyWeather> listDailyWeathers = new ArrayList<>();
            for (Weather.DailyWeather day : weatherResponseWrap.list) {

                if (!predDay.isEmpty()) {
                    if (predDay.equals(String.format("%d", day.getDate().get(Calendar.DAY_OF_WEEK)))) {
                        listWeathers.add(day);
                    } else {
                        linkedHashMap.put(predDay, listWeathers);
                        listWeathers = new ArrayList<>();
                    }
                }
                predDay = String.format("%d", day.getDate().get(Calendar.DAY_OF_WEEK));

                if (day.getDate().get(Calendar.HOUR_OF_DAY) == 15) {
                    @SuppressLint("DefaultLocale") String date = String.format("%d",
                            day.getDate().get(Calendar.DAY_OF_WEEK),
                            day.getDate().get(Calendar.HOUR_OF_DAY),
                            day.getDate().get(Calendar.MINUTE)
                    );
                    day.dt_txt = date;
                    listDailyWeathers.add(day);
                }
            }
            horizontalWeatherRecyclerAdapter.setItems(linkedHashMap.get(linkedHashMap.keySet().iterator().next()));
            weatherRecyclerAdapter.setItems(listDailyWeathers);
            binding.textViewPlace.setText(weatherResponseWrap.city.name);
            viewModel.getDailyWeatherMutable().postValue(listDailyWeathers.get(0));

        });
    }

    @Override
    public void onWeatherItemClick(Weather.DailyWeather dailyWeather) {
        viewModel.getDailyWeatherMutable().postValue(dailyWeather);
        horizontalWeatherRecyclerAdapter.setItems(linkedHashMap.get(dailyWeather.dt_txt));
    }
}
