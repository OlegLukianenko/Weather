package weather.test.application.utils;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.util.Calendar;

public class BindingAdapters {

    @BindingAdapter("setDate")
    public static void setDate(TextView textView, Calendar calendar) {
        String date = String.format("%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        textView.setText(date);
    }

    @BindingAdapter("setDayOfWeek")
    public static void setDayOfWeek(TextView textView, String date) {
        String dayOfWeek = "";
        if(date!=null) {
            switch (Integer.parseInt(date)) {
                case Calendar.SUNDAY:
                    dayOfWeek = "SUNDAY";
                    break;
                case Calendar.MONDAY:
                    dayOfWeek = "MONDAY";
                    break;
                case Calendar.TUESDAY:
                    dayOfWeek = "TUESDAY";
                    break;
                case Calendar.WEDNESDAY:
                    dayOfWeek = "WEDNESDAY";
                    break;
                case Calendar.THURSDAY:
                    dayOfWeek = "THURSDAY";
                    break;
                case Calendar.FRIDAY:
                    dayOfWeek = "FRIDAY";
                    break;
                case Calendar.SATURDAY:
                    dayOfWeek = "SATURDAY";
                    break;
            }

            textView.setText(dayOfWeek);
        }
    }


}
