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
import com.example.two_51_64.User.CZJL;

import java.util.List;

public class CZJL_Adapter extends ArrayAdapter<CZJL> {
    public CZJL_Adapter(@NonNull Context context, List<CZJL> resource) {
        super(context, 0, resource);
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.czjl_item, parent, false);
            viewHolder.Time1 = convertView.findViewById(R.id.time1);
            viewHolder.mXq = convertView.findViewById(R.id.xq);
            viewHolder.mPlate = convertView.findViewById(R.id.plate);
            viewHolder.mMoney = convertView.findViewById(R.id.money);
            viewHolder.mTime2 = convertView.findViewById(R.id.time2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CZJL czjl = getItem(position);
        viewHolder.Time1.setText(czjl.getTime1());
        viewHolder.mXq.setText(czjl.getXq());
        viewHolder.mPlate.setText("车牌号：" + czjl.getPlate());
        viewHolder.mMoney.setText("充值：" + czjl.getMoney());
        viewHolder.mTime2.setText(czjl.getTime2());
        return convertView;

    }


    static class ViewHolder {
        TextView Time1;
        TextView mXq;
        TextView mPlate;
        TextView mMoney;
        TextView mTime2;
    }
}
