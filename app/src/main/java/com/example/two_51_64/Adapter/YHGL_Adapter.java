package com.example.two_51_64.Adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.two_51_64.R;
import com.example.two_51_64.User.YHGL;

import java.util.List;

public class YHGL_Adapter extends RecyclerView.Adapter<YHGL_Adapter.ViewHolder> {
    List<YHGL> yhgls;

    public YHGL_Adapter(List<YHGL> yhgls) {
        this.yhgls = yhgls;
    }

    public interface Click{
        void myClick(String sex);
    }

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    Click click;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.yhgl_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YHGL yhgl = yhgls.get(position);
        if (yhgl.getSex().equals("男")){
            holder.mSrc.setImageResource(R.drawable.touxiang_2);
        }else {
            holder.mSrc.setImageResource(R.drawable.touxiang_1);
        }
        holder.mUsername.setText("用户名：" + yhgl.getUsername());
        holder.mName.setText("姓名：" + yhgl.getName());
        holder.mTel.setText("电话：" + yhgl.getTel());
        holder.mRoot.setText(yhgl.getRoot());
        holder.mSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.myClick(yhgl.getSex());
            }
        });
    }



    @Override
    public int getItemCount() {
        return yhgls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mSrc;
        TextView mUsername;
        TextView mName;
        TextView mTel;
        TextView mRoot;

        //ViewHolder的构造函数
        public ViewHolder(View view) {
            super(view);
            mSrc = (ImageView) view.findViewById(R.id.src);
            mUsername = (TextView) view.findViewById(R.id.username);
            mName = (TextView) view.findViewById(R.id.name);
            mTel = (TextView) view.findViewById(R.id.tel);
            mRoot = (TextView) view.findViewById(R.id.root);
        }
    }


}
