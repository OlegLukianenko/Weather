package weather.test.application.app.home.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import weather.test.application.R;
import weather.test.application.app.home.adapter.viewholder.WeatherViewHolder;
import weather.test.application.base.BaseRecyclerAdapter;
import weather.test.application.data.retrofit.response.Weather;
import weather.test.application.databinding.WeatherRecyclerItemBinding;

public class WeatherRecyclerAdapter  extends BaseRecyclerAdapter<WeatherRecyclerItemBinding, Weather.DailyWeather, WeatherViewHolder> {


    public WeatherItemListListener weatherItemListListener;

    @Inject
    public WeatherRecyclerAdapter(WeatherItemListListener listener) {
        this.weatherItemListListener = listener;
    }

    @Override
    protected WeatherRecyclerItemBinding getBinding(LayoutInflater inflater, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater, R.layout.weather_recycler_item, parent, false);
    }

    @Override
    protected WeatherViewHolder getViewHolder(WeatherRecyclerItemBinding binding) {
        binding.setAdapter(this);
        return new WeatherViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public interface WeatherItemListListener {
        void onWeatherItemClick(Weather.DailyWeather dailyWeather);
    }
}