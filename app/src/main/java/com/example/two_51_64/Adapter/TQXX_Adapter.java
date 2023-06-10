package com.example.two_51_64.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.two_51_64.R;
import com.example.two_51_64.User.TQXX;
import com.example.two_51_64.User.Time;

import java.util.List;

public class TQXX_Adapter extends ArrayAdapter<TQXX> {

    Time time;

    public TQXX_Adapter(@NonNull Context context, List<TQXX> resource, Time time) {
        super(context, 0, resource);
        this.time = time;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tqxx_item, parent, false);
            viewHolder.mDay = convertView.findViewById(R.id.day);
            viewHolder.mTime = convertView.findViewById(R.id.time);
            viewHolder.mWeather = convertView.findViewById(R.id.weather);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mDay.setText(time.getTime().get(position));
        viewHolder.mTime.setText(time.getRq().get(position));
        TQXX tqxx = getItem(position);
        String weather = tqxx.getWeather();
        if (weather.equals("晴")) {
            viewHolder.mWeather.setImageResource(R.drawable.qing);
        } else if (weather.equals("阴")) {
            viewHolder.mWeather.setImageResource(R.drawable.yintian);
        } else if (weather.equals("小雨")) {
            viewHolder.mWeather.setImageResource(R.drawable.xiaoyu);
        }


        return convertView;
    }


    static class ViewHolder {
        TextView mDay;
        TextView mTime;
        ImageView mWeather;
    }


}
