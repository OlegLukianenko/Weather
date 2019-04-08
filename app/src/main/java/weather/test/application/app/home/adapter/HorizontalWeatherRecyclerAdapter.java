package weather.test.application.app.home.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import weather.test.application.R;
import weather.test.application.app.home.adapter.viewholder.HorizontalWeatherViewHolder;
import weather.test.application.base.BaseRecyclerAdapter;
import weather.test.application.data.retrofit.response.Weather;
import weather.test.application.databinding.HorizontalWeatherRecyclerItemBinding;

public class HorizontalWeatherRecyclerAdapter extends BaseRecyclerAdapter<HorizontalWeatherRecyclerItemBinding, Weather.DailyWeather, HorizontalWeatherViewHolder> {

    @Override
    protected HorizontalWeatherRecyclerItemBinding getBinding(LayoutInflater inflater, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater, R.layout.horizontal_weather_recycler_item, parent, false);
    }

    @Override
    protected HorizontalWeatherViewHolder getViewHolder(HorizontalWeatherRecyclerItemBinding binding) {
        binding.setAdapter(this);
        return new HorizontalWeatherViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
