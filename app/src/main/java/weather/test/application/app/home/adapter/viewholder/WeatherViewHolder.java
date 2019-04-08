package weather.test.application.app.home.adapter.viewholder;

import com.bumptech.glide.Glide;

import weather.test.application.R;
import weather.test.application.base.BaseViewHolder;
import weather.test.application.data.retrofit.response.Weather;
import weather.test.application.databinding.WeatherRecyclerItemBinding;

public class WeatherViewHolder extends BaseViewHolder <WeatherRecyclerItemBinding, Weather.DailyWeather>{

    public WeatherViewHolder(WeatherRecyclerItemBinding binding) {
        super(binding);
    }

    @Override
    public void bind(Weather.DailyWeather item) {
        getBinding().setData(item);

        Glide.with(getBinding().getRoot().getContext())
                .load(item.weather.get(0).getIconUrl())
                .error(R.drawable.ic_my_location)
                .into(getBinding().image);
    }
}
