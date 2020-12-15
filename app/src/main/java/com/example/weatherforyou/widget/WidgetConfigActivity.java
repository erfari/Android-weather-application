package com.example.weatherforyou.widget;

import android.app.Application;
import android.os.Bundle;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.example.weatherforyou.App;
import com.example.weatherforyou.R;

import java.util.List;

public class WidgetConfigActivity extends AppCompatActivity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;
    List<String> favouriteCities;

    final String LOG_TAG = "myLogs";

    public final static String WIDGET_PREF = "widget_pref";
    public final static String WIDGET_TEXT = "widget_text_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate config");
        setContentView(R.layout.activity_widget_config);

        // извлекаем ID конфигурируемого виджета
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        // отрицательный ответ
        setResult(RESULT_CANCELED, resultValue);
        RadioGroup llMain = findViewById(R.id.rgCity);
        favouriteCities = App.AppInstance.getInstance().getDatabase().getWeatherDao().getCityList();
        int i = 1;

        for (String city : favouriteCities) {
            Log.d("INFO", "City = "+ city);
            RadioButton btnNew = new RadioButton(this);
            btnNew.setText(city);
            btnNew.setId(i++);
            llMain.addView(btnNew);

        }
    }


    public void onClick(View v) {
        int chosenCityId = ((RadioGroup) findViewById(R.id.rgCity))
                .getCheckedRadioButtonId();
        Log.d("INFO", "Id = "+ chosenCityId);
        String chosenCity = favouriteCities.get(chosenCityId-1);
        Log.d("INFO", "Id = "+ chosenCity);


        SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(WIDGET_TEXT + widgetID, chosenCity);
        editor.commit();
        Log.d(LOG_TAG, "UpdateCommand " + widgetID);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        MyWidget.updateWidget(getBaseContext().getApplicationContext(), appWidgetManager, sp, widgetID);

        setResult(RESULT_OK, resultValue);

        Log.d(LOG_TAG, "finish config " + widgetID);
        finish();
    }
}