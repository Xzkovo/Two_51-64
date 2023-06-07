package com.example.two_51_64.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.two_51_64.R;
import com.example.two_51_64.User.RZCX;

import java.util.List;

public class RZCX_Adapter extends ArrayAdapter<RZCX> {
    List<RZCX> rzcxes;
    public RZCX_Adapter(@NonNull Context context, List<RZCX> rzcxs) {
        super(context, 0, rzcxs);
        this.rzcxes = rzcxs;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rzcx_item_53,parent,false);
            viewHolder.layout = convertView.findViewById(R.id.line);
            viewHolder.mCo2 = convertView.findViewById(R.id.co2);
            viewHolder.mHumidity = convertView.findViewById(R.id.humidity);
            viewHolder.mPm25 = convertView.findViewById(R.id.pm25);
            viewHolder.mIllumination = convertView.findViewById(R.id.illumination);
            viewHolder.mTemperature = convertView.findViewById(R.id.temperature);
            viewHolder.mTime = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RZCX rzcx = getItem(position);
        if (position == rzcxes.size() - 1){
            viewHolder.layout.setBackgroundResource(R.drawable.shangjiaxia);
        }else {
            viewHolder.layout.setBackgroundResource(R.drawable.shanghuaxian);
        }
        viewHolder.mCo2.setText(rzcx.getCo2() + " ppm");
        viewHolder.mHumidity.setText(rzcx.getHumidity() + " %RH");
        viewHolder.mPm25.setText(rzcx.getPm25() + " ug/m3℃");
        viewHolder.mIllumination.setText(rzcx.getIllumination() + " SI");
        viewHolder.mTemperature.setText(rzcx.getTemperature() + " ℃");
        viewHolder.mTime.setText(rzcx.getTime());



        return convertView;
    }



    static class ViewHolder{
        LinearLayout layout;
        TextView mCo2;
        TextView mHumidity;
        TextView mPm25;
        TextView mIllumination;
        TextView mTemperature;
        TextView mTime;
    }

}
