package com.example.weatherforyou.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherforyou.R;
import com.example.weatherforyou.data.db.entities.WeatherEntity;
import com.example.weatherforyou.databinding.FragmentWeatherBinding;
import com.example.weatherforyou.mappers.WeatherModelMapper;
import com.example.weatherforyou.model.weather.WeatherModel;
import com.example.weatherforyou.ui.adapter.DailyWeatherAdapter;
import com.example.weatherforyou.ui.adapter.HourWeatherAdapter;
import com.example.weatherforyou.ui.adapter.WeatherInfoAdapter;
import com.example.weatherforyou.viewmodel.WeatherViewModel;

public class WeatherFragment extends Fragment {

    private WeatherViewModel mViewModel;
    private RecyclerView hourWeatherRecyclerView;
    private RecyclerView dailyWeatherRecyclerView;
    private RecyclerView weatherInfoRecyclerView;

    TextView currentDescription;
    TextView currentTemperature;

    ImageView currentImage;
    ImageView backgroundImage;


    WeatherModel weatherModel;

    private View favoriteCityButton;

    public static final String PAGE_NUMBER = "page number";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        mViewModel.init(getContext());

    }

    public static WeatherFragment newInstance(int position) {

        WeatherFragment fragment = new WeatherFragment();

        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        FragmentWeatherBinding binding = FragmentWeatherBinding.inflate(inflater);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        int position = getPosition();



        TextView cityName = view.findViewById(R.id.current_city_name);

        mViewModel.getCity().observe(getViewLifecycleOwner(), city -> cityName.setText(city.getName()));

        final HourWeatherAdapter adapterHour = new HourWeatherAdapter();
        final DailyWeatherAdapter adapterDaily = new DailyWeatherAdapter();
        final WeatherInfoAdapter adapterInfo = new WeatherInfoAdapter();

        hourWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        hourWeatherRecyclerView.setAdapter(adapterHour);

        dailyWeatherRecyclerView.setAdapter(adapterDaily);

        weatherInfoRecyclerView.setAdapter(adapterInfo);

        mViewModel.update(position);

        mViewModel.getWeather().observe(getViewLifecycleOwner(), new Observer<WeatherEntity>() {
            @Override
            public void onChanged(WeatherEntity weatherEntity) {
                if (weatherEntity != null) {
                    weatherModel = new WeatherModelMapper(getContext()).toWeatherModel(weatherEntity);
                    adapterHour.setItems(weatherModel.getHourlyWeather());
                    adapterDaily.setItems(weatherModel.getDailyWeather());
                    adapterInfo.setItems(weatherModel.getWeatherInfoUI());
                    String[] s = weatherModel.getDescription().split(",",2);
                    currentDescription.setText(s[0]);
                    currentTemperature.setText(weatherModel.getTemperature());
                    String url = "https://openweathermap.org/img/wn/" + weatherModel.getIconId() + "@2x.png";
                    Glide.with(currentImage).load(url).into(currentImage);
                    switch (s[1]) {
                        case "Thunderstorm":
                        case "Drizzle":
                        case "Rain":
                            Glide.with(backgroundImage).load(R.drawable.main_screen_rain).into(backgroundImage);
                            break;
                        case "Snow":
                            Glide.with(backgroundImage).load(R.drawable.main_screen_snow).into(backgroundImage);
                            break;
                        case "Clear":
                            Glide.with(backgroundImage).load(R.drawable.main_screen_sun).into(backgroundImage);
                            break;
                        case "Clouds":
                            Glide.with(backgroundImage).load(R.drawable.main_screen_cloud).into(backgroundImage);
                            break;
                    }
                }
            }
        });

//        settingFragment.setOnClickListener(view1 -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.));
        favoriteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().setTheme(R.style.dark);
                requireActivity().recreate();
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.favoriteCityFragment);
            }
        });

    }


    private int getPosition() {
        int position = -1;
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt(PAGE_NUMBER);
            
        }

        return position;
    }

    private void init(View view) {
        hourWeatherRecyclerView = view.findViewById(R.id.hour_weather_recycler_view);
        dailyWeatherRecyclerView = view.findViewById(R.id.daily_weather_recycler_view);
        weatherInfoRecyclerView = view.findViewById(R.id.weather_info_recycler_view);
        favoriteCityButton = view.findViewById(R.id.add_city);
        currentDescription = view.findViewById(R.id.current_description);
        currentTemperature = view.findViewById(R.id.current_temp);
        currentImage = view.findViewById(R.id.current_icon);
        backgroundImage = view.findViewById(R.id.imageView);


    }


}

