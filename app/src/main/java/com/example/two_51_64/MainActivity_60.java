package com.example.two_51_64;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.two_51_64.Adapter.TJDY_Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_60 extends AppCompatActivity {
    private ImageView mBack;
    private GridView mGv;
    private GridView mGv2;
    private List<String> classification = new ArrayList<>();
    private List<String> classifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main60);
        initView();

        add();


        TJDY_Adapter adapter = new TJDY_Adapter(this, classification, 0);
        TJDY_Adapter adapter1 = new TJDY_Adapter(this, classifications, 1);

        mGv2.setAdapter(adapter);
        mGv.setAdapter(adapter1);
        adapter.setClickItem(new TJDY_Adapter.ClickItem() {
            @Override
            public void myClick(int position) {
                String temp = classification.get(position);
                classifications.add(temp);
                classification.remove(position);
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
            }
        });

        adapter1.setClickItem(new TJDY_Adapter.ClickItem() {
            @Override
            public void myClick(int position) {
                String temp = classifications.get(position);
                classification.add(temp);
                classifications.remove(position);
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
            }
        });


    }

    private void add() {
        classification.add("交通分类");
        classification.add("科技类");
        classification.add("路况类");
        classification.add("汽车类");
        classification.add("二手车类");
        classification.add("改装车");
        classification.add("违章");
        classification.add("推荐");
        classification.add("安全驾驶");
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mGv = (GridView) findViewById(R.id.gv);
        mGv2 = (GridView) findViewById(R.id.gv2);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}