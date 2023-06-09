package com.example.two_51_64.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.two_51_64.R;
import com.example.two_51_64.User.LSJL;

import java.util.List;

public class SSLS_Adapter extends ArrayAdapter<LSJL> {

    public SSLS_Adapter(@NonNull Context context, List<LSJL> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lsjl_item, parent, false);
            viewHolder.mNumber = convertView.findViewById(R.id.number);
            viewHolder.mSite = convertView.findViewById(R.id.site);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LSJL lsjl = getItem(position);

        viewHolder.mNumber.setText(lsjl.getNumber());
        viewHolder.mSite.setText(lsjl.getSite());


        return convertView;

    }


    static class ViewHolder {
        TextView mNumber;
        TextView mSite;
    }
}
