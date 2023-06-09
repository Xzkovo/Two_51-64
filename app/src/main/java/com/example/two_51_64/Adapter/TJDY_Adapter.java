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

import java.util.List;

public class TJDY_Adapter extends ArrayAdapter<String> {
    int i;
    public TJDY_Adapter(@NonNull Context context, List<String> resource, int i) {
        super(context, 0, resource);
        this.i = i;
    }
    public interface ClickItem{
        void myClick(int position);
    }

    public ClickItem getClickItem() {
        return clickItem;
    }

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    private ClickItem clickItem;



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pxgn_item, parent, false);
            viewHolder.mBody = convertView.findViewById(R.id.body);
            viewHolder.mTop = convertView.findViewById(R.id.top);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String classification = getItem(position);
        viewHolder.mBody.setText(classification);
        viewHolder.mTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.myClick(position);
            }
        });

        if (i == 0){
            viewHolder.mTop.setImageResource(R.drawable.add2);
        }else if (i == 1){
            viewHolder.mTop.setImageResource(R.drawable.plus);
        }


        return convertView;
    }


    static class ViewHolder {
        TextView mBody;
        ImageView mTop;

    }


}
