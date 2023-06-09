package com.example.two_51_64.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.two_51_64.R;
import com.example.two_51_64.User.SJK;

import java.util.List;

public class YHSC_Adapter extends RecyclerView.Adapter<YHSC_Adapter.ViewHolder> {
    List<SJK> sjks;

    public YHSC_Adapter(List<SJK> sjks) {
        this.sjks = sjks;
    }

    public interface click{
        void del(int lk);
    }

    public click getMyClick() {
        return MyClick;
    }

    public void setMyClick(click myClick) {
        MyClick = myClick;
    }

    click MyClick;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.yhsc_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SJK sjk = sjks.get(position);
        if (sjk.getSex().equals("男")){
            holder.mSrc.setImageResource(R.drawable.touxiang_2);
        }else {
            holder.mSrc.setImageResource(R.drawable.touxiang_1);
        }
        holder.mUsername.setText("用户名：" + sjk.getYhm());
        holder.mName.setText("姓名：" + sjk.getName());
        holder.mTel.setText("电话：" + sjk.getTel());
        holder.mTime.setText(sjk.getTime());
        holder.mRoot.setText(sjk.getRoot());
        holder.mWdsc.setText("已收藏");
        holder.mWdsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyClick.del(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sjks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mSrc;
        TextView mUsername;
        TextView mName;
        TextView mTel;
        TextView mTime;
        TextView mRoot;
        TextView mWdsc;

        public ViewHolder(View view) {
            super(view);
            mSrc = view.findViewById(R.id.src);
            mUsername = view.findViewById(R.id.username);
            mName = view.findViewById(R.id.name);
            mTel = view.findViewById(R.id.tel);
            mTime = view.findViewById(R.id.time);
            mRoot = view.findViewById(R.id.root);
            mWdsc = view.findViewById(R.id.wdsc);
        }
    }


}
