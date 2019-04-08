package weather.test.application.data.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class Weather {

    public City city;
    public List<DailyWeather> list;

    public class City {
        public String id;
        public String name;
    }

    public class DailyWeather {
        public Main main;
        public List<WeatherIcon> weather;
        public Wind wind;
        public String dt_txt;
        @SerializedName("dt")
        private long timestamp;


        public Calendar getDate() {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(timestamp * 1000);
            return date;
        }
       // public String getTempInteger() { return String.valueOf(main.temp.intValue()); }

        public class Main {
            public String temp;
            public String temp_min;
            public String temp_max;
            public String humidity;
        }

        public class WeatherIcon {
            public String icon;

            public String getIconUrl() {
                return "https://openweathermap.org/img/w/" + icon + ".png";
            }
        }

        public class Wind {
            public String speed;
        }
    }

}
