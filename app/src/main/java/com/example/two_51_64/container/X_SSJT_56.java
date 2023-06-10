package com.example.two_51_64.container;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.two_51_64.MainActivity_56;
import com.example.two_51_64.R;
import com.example.two_51_64.User.SSJT;

import java.util.ArrayList;
import java.util.List;

public class X_SSJT_56 extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mBlack;
    private SSJT.ROWSDETAILBean rowsdetailBean;
    private TextView mSite;
    private TextView mStart;
    private TextView mEnd;
    private TextView mMileage;
    private TextView mPrice;
    private LinearLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xssjt56);

        initView();

        mTitle.setText(getIntent().getStringExtra("title") + "路");

        rowsdetailBean = MainActivity_56.getSsjt().getROWS_DETAIL().get(0);
        mSite.setText(rowsdetailBean.getSite().get(0) + "-" + rowsdetailBean.getSite().get(rowsdetailBean.getSite().size() - 1));
        mStart.setText(rowsdetailBean.getStart());
        mEnd.setText(rowsdetailBean.getEnd());
        mMileage.setText(rowsdetailBean.getSite().size() + "站/" + rowsdetailBean.getMileage() + "公里");
        mPrice.setText("票价：最高票价" + rowsdetailBean.getPrice() + "元");

        List<String> site = new ArrayList<>();
        site = rowsdetailBean.getSite();
        mLayout.removeAllViews();
        for (int i = 0; i < site.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.ssjt_item, null);
            TextView number = view.findViewById(R.id.number);
            TextView sites = view.findViewById(R.id.site);
            View views = view.findViewById(R.id.view);
            if (i == 0) {
                number.setBackgroundResource(R.drawable.yuan44);
                sites.setTextColor(Color.RED);
            } else {
                number.setBackgroundResource(R.drawable.yuan33);
                sites.setTextColor(Color.BLACK);
            }
            if (i == site.size() - 1){
                views.setVisibility(View.GONE);
            }else {
                views.setVisibility(View.VISIBLE);
            }

            number.setText(i + 1 + "");
            sites.setText(site.get(i));
            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < mLayout.getChildCount(); j++) {
                        View view1 = mLayout.getChildAt(j);
                        TextView number = view1.findViewById(R.id.number);
                        TextView sites = view1.findViewById(R.id.site);
                        if (finalI == j){
                            number.setBackgroundResource(R.drawable.yuan44);
                            sites.setTextColor(Color.RED);
                        }else {
                            number.setBackgroundResource(R.drawable.yuan33);
                            sites.setTextColor(Color.BLACK);
                        }
                    }
                }
            });
            mLayout.addView(view,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        }

    }

    private void initView() {
        mSite = (TextView) findViewById(R.id.site);
        mStart = (TextView) findViewById(R.id.start);
        mEnd = (TextView) findViewById(R.id.end);
        mMileage = (TextView) findViewById(R.id.mileage);
        mPrice = (TextView) findViewById(R.id.price);
        mTitle = (TextView) findViewById(R.id.title);
        mBlack = (ImageView) findViewById(R.id.black);
        mBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mLayout = (LinearLayout) findViewById(R.id.layout);
    }


}