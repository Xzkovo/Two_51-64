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
import com.example.two_51_64.User.LXZS;

import java.util.List;

public class GWC_Adapter extends ArrayAdapter<LXZS> {
    public GWC_Adapter(@NonNull Context context, List<LXZS> resource) {
        super(context, 0, resource);
    }

    public interface gwcItem {
        void gwcItem(int number,int position,int money);
        void sc(int number,int position);
    }


    public gwcItem getGwcItem() {
        return gwcItem;
    }

    public void setGwcItem(gwcItem gwcItem) {
        this.gwcItem = gwcItem;
    }

    gwcItem gwcItem;


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gwc_64, parent, false);
            viewHolder.mSrc = convertView.findViewById(R.id.src);
            viewHolder.mName = convertView.findViewById(R.id.name);
            viewHolder.mBody = convertView.findViewById(R.id.body);
            viewHolder.mNumber = convertView.findViewById(R.id.number);
            viewHolder.mJia = convertView.findViewById(R.id.jia);
            viewHolder.mJian = convertView.findViewById(R.id.jian);
            viewHolder.mMoney = convertView.findViewById(R.id.money);
            viewHolder.mSc = convertView.findViewById(R.id.sc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LXZS lxzs = getItem(position);
        if (lxzs.getName().equals("故宫")) {
            viewHolder.mSrc.setImageResource(R.drawable.gugong2);
        } else {
            viewHolder.mSrc.setImageResource(R.drawable.gugong1);
        }
        viewHolder.mName.setText(lxzs.getName());
        viewHolder.mBody.setText(lxzs.getPresentation());
        viewHolder.mMoney.setText(lxzs.getPrice() + "元");
        viewHolder.mNumber.setText(lxzs.getMoney() + "");
        viewHolder.mJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gwcItem.gwcItem(1, position, Integer.parseInt(lxzs.getPrice()));
            }
        });
        viewHolder.mJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lxzs.getMoney() == 0) {
                    return;
                }
                gwcItem.gwcItem(2, position, Integer.parseInt(lxzs.getPrice()));
            }
        });
        if (!lxzs.isTf()) {
            viewHolder.mSc.setVisibility(View.GONE);
        } else {
            viewHolder.mSc.setVisibility(View.VISIBLE);
        }
        viewHolder.mSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gwcItem.sc(1,position);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        ImageView mSrc;
        TextView mName;
        TextView mBody;
        TextView mNumber;
        TextView mJia;
        TextView mJian;
        TextView mMoney;
        TextView mSc;
    }

}
