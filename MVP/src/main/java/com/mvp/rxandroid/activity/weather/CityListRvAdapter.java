package com.mvp.rxandroid.activity.weather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvp.rxandroid.R;
import com.mvp.rxandroid.bean.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class CityListRvAdapter extends RecyclerView.Adapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_citylist,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        City tmp = cities.get(position);
        cityViewHolder.cityname.setText(tmp.name_cn);
    }

    List<City> cities = new ArrayList<City>();

    public void setCities(List<City> citiess)
    {
        clear();
        this.cities.addAll(citiess);
        notifyItemRangeInserted(0,citiess.size());
    }

    public void clear()
    {
        if (cities == null)
        {
            return;
        }

        int size = cities.size();
        cities.clear();
        notifyItemRangeRemoved(0,size);

    }

    public void addCity(City city)
    {
        if (cities == null)
        {
            cities = new ArrayList<City>();
        }
        cities.add(city);
        notifyItemInserted(cities.size());
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    public Object getItem(int position)
    {
        return cities == null ? null : cities.size() < position ? null : cities.get(position);
    }

    private RecycleViewListener callback;

    public void setCallback(RecycleViewListener callback) {
        this.callback = callback;
    }

    protected class CityViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener,RecyclerView.OnLongClickListener{

        @BindView(R.id.item_rv_citylist_name)
        TextView cityname;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (callback != null)
            {
                callback.OnItemClickListener(v,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (callback != null)
            {
                callback.OnItemLongClickListener(v, getAdapterPosition());
            }
            return true;
        }
    }

}
