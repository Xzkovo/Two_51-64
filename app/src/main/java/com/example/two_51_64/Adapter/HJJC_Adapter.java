package com.example.two_51_64.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.two_51_64.R;
import com.example.two_51_64.User.HJJC_List;

import java.util.List;

public class HJJC_Adapter extends ArrayAdapter<HJJC_List> {
    private int i;
    private int order;

    public HJJC_Adapter(@NonNull Context context, List<HJJC_List> resource, int i, int order) {
        super(context, 0, resource);
        this.i = i;
        this.order = order;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hjjc_item, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.max = convertView.findViewById(R.id.max);
            viewHolder.min = convertView.findViewById(R.id.min);
            viewHolder.ave = convertView.findViewById(R.id.ave);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HJJC_List hjjc_list = getItem(i);
        viewHolder.name.setText(hjjc_list.getName());

        if (position == 0) {
            viewHolder.name.setText("pm2.5(ug/m3)");
            viewHolder.max.setText(hjjc_list.getPm25Max() + "");
            viewHolder.min.setText(hjjc_list.getPm25Min() + "");
            viewHolder.ave.setText((hjjc_list.getPm25Ave()) / (order / 5) + "");
        } else if (position == 1) {
            viewHolder.name.setText("二氧化碳(ppm)");
            viewHolder.max.setText(hjjc_list.getCo2Max() + "");
            viewHolder.min.setText(hjjc_list.getCo2Min() + "");
            viewHolder.ave.setText((hjjc_list.getCo2Ave()) / (order / 5) + "");
        } else if (position == 2) {
            viewHolder.name.setText("光照强度(SI)");
            viewHolder.max.setText(hjjc_list.getIlluminationMax() + "");
            viewHolder.min.setText(hjjc_list.getIlluminationMin() + "");
            viewHolder.ave.setText((hjjc_list.getIlluminationAve()) / (order / 5) + "");
        } else if (position == 3) {
            viewHolder.name.setText("湿度(RH)");
            viewHolder.max.setText(hjjc_list.getHumidityMax() + "");
            viewHolder.min.setText(hjjc_list.getHumidityMin() + "");
            viewHolder.ave.setText((hjjc_list.getHumidityAve()) / (order / 5) + "");
        } else if (position == 4) {
            viewHolder.name.setText("温度(℃)");
            viewHolder.max.setText(hjjc_list.getTemperatureMax() + "");
            viewHolder.min.setText(hjjc_list.getTemperatureMin() + "");
            viewHolder.ave.setText((hjjc_list.getTemperatureAve()) / (order / 5) + "");
        }


        return convertView;
    }


    static class ViewHolder {
        TextView name;
        TextView max;
        TextView min;
        TextView ave;
    }


}
