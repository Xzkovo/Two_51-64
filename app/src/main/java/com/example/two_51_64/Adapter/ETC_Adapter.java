package com.example.two_51_64.Adapter;

import android.annotation.SuppressLint;
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
import com.example.two_51_64.User.CLXX;

import java.util.List;

public class ETC_Adapter extends ArrayAdapter<CLXX> {
    public ETC_Adapter(@NonNull Context context, List<CLXX> resource) {
        super(context, 0, resource);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.etc_ye_item, parent, false);
            viewHolder.mNumber = convertView.findViewById(R.id.number);
            viewHolder.mSrc = convertView.findViewById(R.id.src);
            viewHolder.mPlate = convertView.findViewById(R.id.plate);
            viewHolder.mName = convertView.findViewById(R.id.name);
            viewHolder.mMoney = convertView.findViewById(R.id.money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        CLXX clxx = getItem(position);
        viewHolder.mNumber.setText(position + 1 + "");
        String plate = clxx.getBrand();
        if (plate.equals("奔驰")) {
            viewHolder.mSrc.setImageResource(R.drawable.benchi);
        } else if (plate.equals("宝马")) {
            viewHolder.mSrc.setImageResource(R.drawable.baoma);
        } else if (plate.equals("中华")) {
            viewHolder.mSrc.setImageResource(R.drawable.zhonghua);
        } else if (plate.equals("奥迪")) {
            viewHolder.mSrc.setImageResource(R.drawable.audi);
        }

        viewHolder.mPlate.setText(clxx.getPlate());
        viewHolder.mName.setText("车主：" + clxx.getOwner());
        viewHolder.mMoney.setText("余额：" + clxx.getMoney() + "元");

        return convertView;
    }

    static class ViewHolder {
        TextView mNumber;
        ImageView mSrc;
        TextView mPlate;
        TextView mName;
        TextView mMoney;


    }


}
