package com.example.two_51_64.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.two_51_64.R;
import com.example.two_51_64.User.YHGL;

import java.util.List;

public class YHGL_Adapter extends RecyclerView.Adapter<YHGL_Adapter.ViewHolder> {
    List<YHGL> yhgls;
    //滑动的距离
    int x = 0;

    public YHGL_Adapter(List<YHGL> yhgls) {
        this.yhgls = yhgls;
    }

    public interface Click {
        void myClick(String sex);

        void del(int i);

        void sc(int sc);

        void tz();
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YHGL yhgl = yhgls.get(position);
        if (yhgl.getSex().equals("男")) {
            holder.mSrc.setImageResource(R.drawable.touxiang_2);
        } else {
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
        holder.mShanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mHsv.smoothScrollTo(0, 0);
                click.del(position);
            }
        });
        holder.mHsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                x = i;
            }
        });
        holder.mHsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //定义状态
                int zt = 0;
                switch (motionEvent.getAction()) {
                    // 鼠标停止滑动的情况
                    case MotionEvent.ACTION_UP:
                        if (x <= 100) {
                            zt = 0;
                        } else if (x <= 200) {
                            zt = 1;
                        } else {
                            zt = 2;
                        }
                    case MotionEvent.ACTION_CANCEL:
                        if (zt == 0) {
                            holder.mHsv.smoothScrollTo(0, 0);
                        } else if (zt == 1) {
                            holder.mHsv.smoothScrollTo(200, 0);
                        } else {
                            holder.mHsv.smoothScrollTo(360, 0);
                        }
                        // TODO: 处理鼠标停止滑动的逻辑
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        if (yhgl.isTf()) {
            yhgls.get(position).setTf(true);
            holder.mSshoucang.setText("已收藏");
        } else {
            yhgls.get(position).setTf(false);
            holder.mSshoucang.setText("收藏");
        }
        holder.mSshoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.sc(position);
                if (holder.mSshoucang.getText().toString().equals("收藏")) {
                    holder.mSshoucang.setText("已收藏");
                    yhgls.get(position).setTf(true);
                }else {
                    holder.mSshoucang.setText("收藏");
                }
            }
        });
        holder.mWdsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.tz();
            }
        });


    }


    @Override
    public int getItemCount() {
        return yhgls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mSrc;
        TextView mUsername;
        TextView mName;
        TextView mTel;
        TextView mRoot;
        HorizontalScrollView mHsv;
        TextView mSshoucang;
        TextView mShanchu;
        Button mWdsc;

        //ViewHolder的构造函数
        public ViewHolder(View view) {
            super(view);
            mSrc = (ImageView) view.findViewById(R.id.src);
            mUsername = (TextView) view.findViewById(R.id.username);
            mName = (TextView) view.findViewById(R.id.name);
            mTel = (TextView) view.findViewById(R.id.tel);
            mRoot = (TextView) view.findViewById(R.id.root);
            mHsv = (HorizontalScrollView) view.findViewById(R.id.hsv);
            mSshoucang = (TextView) view.findViewById(R.id.sc);
            mShanchu = (TextView) view.findViewById(R.id.shanchu);
            mWdsc = (Button) view.findViewById(R.id.wdsc);
        }
    }


}
