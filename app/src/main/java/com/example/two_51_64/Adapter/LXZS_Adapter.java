package com.example.two_51_64.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.two_51_64.R;
import com.example.two_51_64.User.LXZS;

import java.util.List;

public class LXZS_Adapter extends ArrayAdapter<LXZS> {
    public LXZS_Adapter(@NonNull Context context, List<LXZS> resource) {
        super(context, 0, resource);
    }

    public interface ClickItem {
        void myClick(int position);

        void myClicks(int position);
    }

    public ClickItem getClickItem() {
        return clickItem;
    }

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    ClickItem clickItem;


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lxzs_item1, parent, false);
            viewHolder.mName = convertView.findViewById(R.id.name);
            viewHolder.mPrice = convertView.findViewById(R.id.price);
            viewHolder.mImg = convertView.findViewById(R.id.img);
            viewHolder.mBt = convertView.findViewById(R.id.bt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LXZS lxzs = getItem(position);

        viewHolder.mName.setText(lxzs.getName());
        viewHolder.mPrice.setText("票价 ￥:" + lxzs.getPrice());
        if (lxzs.getName().equals("故宫")) {
            viewHolder.mImg.setImageResource(R.drawable.gugong2);
        } else {
            viewHolder.mImg.setImageResource(R.drawable.gugong1);
        }

        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.myClick(position);
            }
        });

        viewHolder.mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.myClicks(position);
            }
        });


        return convertView;
    }


    static class ViewHolder {
        TextView mName;
        TextView mPrice;
        ImageView mImg;
        Button mBt;

    }


}
