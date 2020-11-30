package com.example.weatherforyou.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weatherforyou.App;
import com.example.weatherforyou.R;
import com.example.weatherforyou.data.db.entities.WeatherEntity;
import com.example.weatherforyou.repository.OpenWeatherRepository;

import java.util.concurrent.TimeUnit;

public class PushWorker extends Worker {

    private static final String PUSH_ID = "push";

    public PushWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MYLOG", "doWork: start");

//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("MYLOG", "doWork: end");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "push")
                .setSmallIcon(R.drawable.ic_cloud_24)
                .setContentTitle("Push").setContentText("This is push notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, builder.build());


        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.d("MYLOG", "doWork: stop");
        super.onStopped();

    }
}
