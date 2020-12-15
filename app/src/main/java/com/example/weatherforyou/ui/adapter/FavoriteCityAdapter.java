package com.example.weatherforyou.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforyou.App;
import com.example.weatherforyou.R;
import com.example.weatherforyou.data.db.entities.CityEntity;
import com.example.weatherforyou.data.db.entities.DailyEntity;
import com.example.weatherforyou.data.db.entities.HourlyEntity;
import com.example.weatherforyou.data.db.entities.InfoEntity;
import com.example.weatherforyou.data.db.entities.WeatherEntity;
import com.example.weatherforyou.databinding.ItemCityInfoBinding;
import com.example.weatherforyou.model.city.FavoriteCity;
import com.example.weatherforyou.model.city.FavoriteCityImpl;
import com.example.weatherforyou.repository.CityRepository;
import com.example.weatherforyou.ui.adapter.listeners.ItemClickListener;
import com.example.weatherforyou.ui.adapter.listeners.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FavoriteCityAdapter extends RecyclerView.Adapter<FavoriteCityAdapter.FavoriteCityViewHolder> implements ItemTouchHelperAdapter {

    private List<FavoriteCity> items = new ArrayList<>();

    private CityRepository cityRepository;

    private Context context;

    private ItemClickListener<FavoriteCity> itemClickListener;

    public FavoriteCityAdapter(Context context, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.context = context;
        this.cityRepository = new CityRepository(context);
    }

    public void setItems(List<FavoriteCity> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemCityInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_city_info, parent, false);

        return new FavoriteCityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteCityViewHolder holder, int position) {
        holder.bind(items.get(position), context, itemClickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        Log.i("MYLOG", fromPosition + " " + toPosition);
        App.AppInstance.getInstance().getDatabase().getCityDao().clearTable();

        List<CityEntity> cityEntities = new ArrayList<>();
        for (FavoriteCity city : items) {
            cityEntities.add(new CityEntity(city.getCityName(), city.getLat(), city.getLon(), 123));
        }
        App.AppInstance.getInstance().getDatabase().getCityDao().insertAll(cityEntities);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        FavoriteCity favoriteCity = items.get(position);
        App.AppInstance.getInstance().getDatabase().getCityDao().delete(new CityEntity(favoriteCity.getCityName(), favoriteCity.getLat(), favoriteCity.getLon(), 123));
        App.AppInstance.getInstance().getDatabase().getWeatherDao().delete(new WeatherEntity(0.0, 0.0, favoriteCity.getCityName(), "", 0.0, 0, "", new InfoEntity("","","","","","",0,0), new ArrayList<DailyEntity>(), new ArrayList<HourlyEntity>()));

        items.remove(position);
        notifyItemRemoved(position);
    }

    class FavoriteCityViewHolder extends RecyclerView.ViewHolder {

        ItemCityInfoBinding weatherBinding;

        public FavoriteCityViewHolder(ItemCityInfoBinding binding) {
            super(binding.getRoot());
            weatherBinding = binding;

        }

        public void bind(FavoriteCity favoriteCity, Context context, ItemClickListener<FavoriteCity> cityItemClickListener) {

            weatherBinding.setFavoritecity(favoriteCity);

            ConstraintLayout constraintLayout = itemView.findViewById(R.id.city_container);


//            constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                    cityItemClickListener.onClick(favoriteCity);
//                    return false;
//                }
//            });
            constraintLayout.setBackground(new ColorDrawable(favoriteCity.backColor()));


        }
    }
}




