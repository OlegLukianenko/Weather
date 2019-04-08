package weather.test.application.app.home.adapter.viewholder;

import com.bumptech.glide.Glide;

import weather.test.application.R;
import weather.test.application.base.BaseViewHolder;
import weather.test.application.data.retrofit.response.Weather;
import weather.test.application.databinding.HorizontalWeatherRecyclerItemBinding;

public class HorizontalWeatherViewHolder extends BaseViewHolder<HorizontalWeatherRecyclerItemBinding, Weather.DailyWeather> {


    public HorizontalWeatherViewHolder(HorizontalWeatherRecyclerItemBinding binding) {
        super(binding);
    }

    @Override
    public void bind(Weather.DailyWeather data) {
        getBinding().setData(data);

        Glide.with(getBinding().getRoot().getContext())
                .load(data.weather.get(0).getIconUrl())
                .error(R.drawable.ic_my_location)
                .into(getBinding().image);
    }
}
