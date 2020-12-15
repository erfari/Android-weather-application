package com.example.weatherforyou.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.example.weatherforyou.App;
import com.example.weatherforyou.R;
import com.example.weatherforyou.data.db.entities.DailyEntity;
import com.example.weatherforyou.data.db.entities.WeatherEntity;

import java.util.Calendar;
import java.util.List;

public class MyWidget extends AppWidgetProvider {


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("INFO", "OnEnabled    "+context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("INFO", "OnUpdate    "+context);


        SharedPreferences sp = context.getSharedPreferences(
                WidgetConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);


        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, sp, id);

        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             SharedPreferences sp, int id) {


        Log.d("INFO", "UpdateWidget" + id+context);
        RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        String city = sp.getString(WidgetConfigActivity.WIDGET_TEXT + id, null);
        if (city == null) return;
        Log.d("INFO", "WidgetId  = " + id + "   " + "City = " + city);
        LiveData<WeatherEntity> weather = App.AppInstance.getInstance().getDatabase().getWeatherDao().getBy(city);
        widgetView.setTextViewText(R.id.city_name, city);
        Observer<WeatherEntity> weatherObserver = new Observer<WeatherEntity>() {
            @Override
            public void onChanged(WeatherEntity weatherEntity) {
                Log.d("INFO", "OnChanged");
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                Log.d("INFO", "day of week  = "  + day);

                List<DailyEntity> weather = weatherEntity.getDailyWeather();

                String minTem = Long.toString(Math.round(weather.get(0).getMinTemp()));
                String maxTem = Long.toString(Math.round(weather.get(0).getMaxTemp()));
                if (Math.round(weather.get(0).getMinTemp()) > 0) {
                    minTem = "+" + minTem;
                    maxTem = "+" + maxTem;
                }

                String url = "https://openweathermap.org/img/wn/" + weather.get(0).getIconIdWithUrl() + "@2x.png";
                Log.d("INFO", "city name" + "   " + minTem + "     " + maxTem + "      " + "     " + url);
                updateViews(minTem, maxTem, url, widgetView, context, appWidgetManager, id);


            }
        };

        weather.observeForever(weatherObserver);
    }

    static void updateViews(String minTem, String maxTem, String url, RemoteViews widgetView, Context context, AppWidgetManager appWidgetManager,
                            int id) {
        Log.d("INFO", "UpdateViews" + id);
        widgetView.setTextViewText(R.id.max_temperature, maxTem + "°C");
        widgetView.setTextViewText(R.id.min_temperature, minTem + "°C");
        AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, R.id.icon, widgetView, id);
        Glide.with(context).asBitmap().load(url).into(appWidgetTarget);
        appWidgetManager.updateAppWidget(id, widgetView);
    }
}

