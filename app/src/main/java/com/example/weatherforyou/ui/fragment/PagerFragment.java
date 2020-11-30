package com.example.weatherforyou.ui.fragment;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherforyou.R;
import com.example.weatherforyou.repository.CityRepository;
import com.example.weatherforyou.ui.MainActivity;
import com.example.weatherforyou.ui.adapter.WeatherPagerStateAdapter;
import com.example.weatherforyou.viewmodel.PermissionViewModel;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import javax.inject.Inject;

public class PagerFragment extends Fragment {

    private ViewPager2 viewPager;

    @Inject
    CityRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager_container, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new CityRepository(getContext());

        viewPager = view.findViewById(R.id.view_pager);

        SpringDotsIndicator dotsIndicator = view.findViewById(R.id.spring_dots_indicator);

        repository.getCityCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewPager.setAdapter(new WeatherPagerStateAdapter(getChildFragmentManager(), getLifecycle(), integer));
                dotsIndicator.setViewPager2(viewPager);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        LiveData<Boolean> liveData = PermissionViewModel.getInstance().getIsLocationPermissionGranted();
        liveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    repository.calculateCityCount((LocationManager) (getActivity().getSystemService(Context.LOCATION_SERVICE)), getContext());
                } else if (!aBoolean && !PermissionViewModel.getInstance().isPermanentlyDenied) {

                    ((MainActivity)getActivity()).getPermissionManager().requestPermission();

                } else {
//                    Toast.makeText(getContext(), "Можешь не ждать, надо включать разрешение в настройках приложения", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
