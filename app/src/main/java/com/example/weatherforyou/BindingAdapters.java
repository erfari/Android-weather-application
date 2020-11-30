package com.example.weatherforyou;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingAdapters {


//    @BindingAdapter({"app:minT", "app:maxT"})
//    public static void setTemp(TextView textView, String minT, String maxT){
//        String s = minT + " / " + maxT;
//        textView.setText(s);
//    };
//
//    @BindingAdapter("app:date")
//    public static void setDate(TextView textView, int date){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
//        Date d = new Date(date * 1000);
//        String s = simpleDateFormat.format(d);
//        textView.setText(s);
//    };

    @BindingAdapter("app:src")
    public static void setImage(ImageView imageView, String iconId) {
        String url = "https://openweathermap.org/img/wn/" + iconId + "@2x.png";
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }


}


////Any adapters
//@BindingAdapter("setWeatherIcon")
//fun ImageView.setStatusIcon(iconId: String) {
//        iconId.let {
//        val s = "https://openweathermap.org/img/wn/$it@2x.png"
//        Glide
//        .with(this.context)
//        .load(s)
//        .placeholder(R.drawable.ic_baseline_accessibility_new_24)
//        .into(this)
//        }
//        }
