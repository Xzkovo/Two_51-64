package com.example.two_51_64;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.two_51_64.container.ETC_cz64;
import com.example.two_51_64.container.ETC_czjl_64;
import com.example.two_51_64.container.ETC_ye64;

public class MainActivity_64 extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private ImageView mCz;
    private ImageView mYe;
    private ImageView mJl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main64);
        initView();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mCz = (ImageView) findViewById(R.id.Cz);
        mYe = (ImageView) findViewById(R.id.Ye);
        mJl = (ImageView) findViewById(R.id.Jl);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCz.setOnClickListener(this);
        mYe.setOnClickListener(this);
        mJl.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Cz:
                Intent intent = new Intent(MainActivity_64.this, ETC_cz64.class);
                startActivity(intent);
                break;
            case R.id.Ye:
                Intent intent1 = new Intent(MainActivity_64.this, ETC_ye64.class);
                startActivity(intent1);
                break;
            case R.id.Jl:
                Intent intent2 = new Intent(MainActivity_64.this, ETC_czjl_64.class);
                startActivity(intent2);
                break;
        }
    }
}