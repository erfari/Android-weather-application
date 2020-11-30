package com.example.weatherforyou.ui.fragment;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.WorkManager;

import com.example.weatherforyou.App;
import com.example.weatherforyou.R;
import com.example.weatherforyou.ui.MainActivity;

public class SettingFragment extends PreferenceFragmentCompat {


    private SwitchPreference switchPreference;
    private SwitchPreference pushPreference;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchPreference = (SwitchPreference) getPreferenceManager().findPreference("enable_night_theme");
        pushPreference = (SwitchPreference) getPreferenceManager().findPreference("push");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (switchPreference.isChecked()) {
                    Log.i("MYLOG", "unchecked");
                    getActivity().recreate();

                } else {
                    Log.i("MYLOG", "checked");
                    getActivity().recreate();
                }
                return true;

            }
        });

        pushPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (pushPreference.isChecked()) {
                    Log.i("MYLOG", "unchecked");
                    WorkManager.getInstance().cancelWorkById(App.AppInstance.getInstance().getMyWorkRequest().getId());
                } else {
                    Log.i("MYLOG", "checked");
                    WorkManager.getInstance().enqueueUniquePeriodicWork(
                            "push",
                            ExistingPeriodicWorkPolicy.REPLACE,
                            App.AppInstance.getInstance().getMyWorkRequest()
                    );
                }


                return true;
            }
        });
    }
}


//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
//public class SettingFragment extends Fragment {
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_setting, container, false);
//    }
//}