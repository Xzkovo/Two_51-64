package com.example.two_51_64.container;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.two_51_64.Adapter.CZJL_Adapter;
import com.example.two_51_64.DB.DBManager;
import com.example.two_51_64.R;
import com.example.two_51_64.User.CZJL;

import java.util.ArrayList;
import java.util.List;

public class ETC_czjl_64 extends AppCompatActivity {

    private ImageView mBack;
    private ListView mLv;
    private TextView mMoney;
    private List<CZJL>czjls;
    private LinearLayout mLinear;
    private int money = 0;
    private TextView mZw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_czjl64);
        initView();

        DBManager manager = new DBManager(ETC_czjl_64.this);
        boolean a = manager.isExist("czjl");
        if (!a) {
            mLinear.setVisibility(View.GONE);
            mZw.setVisibility(View.VISIBLE);
            return;
        }
        mLinear.setVisibility(View.VISIBLE);
        mZw.setVisibility(View.GONE);

        Cursor c = manager.queryDB("czjl",null,null,null,null,null,"id asc",null);
        czjls = cursorToList(c);
        manager.closeDb();;
        c.close();
        CZJL_Adapter adapter = new CZJL_Adapter(ETC_czjl_64.this,czjls);
        mLv.setAdapter(adapter);
        mMoney.setText("充值合计：" + money + "元");


    }

    private List<CZJL> cursorToList(Cursor c) {
        List<CZJL> czjls1 = new ArrayList<>();
        while (c.moveToNext()){
            CZJL czjl = new CZJL();
            czjl.setTime1(c.getString(c.getColumnIndexOrThrow("time1")));
            czjl.setXq(c.getString(c.getColumnIndexOrThrow("xingqi")));
            czjl.setPlate(c.getString(c.getColumnIndexOrThrow("plate")));
            czjl.setMoney(c.getString(c.getColumnIndexOrThrow("money")));
            czjl.setTime2(c.getString(c.getColumnIndexOrThrow("time2")));
            money += c.getInt(c.getColumnIndexOrThrow("money"));
            czjls1.add(czjl);
        }
        return czjls1;
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mLv = (ListView) findViewById(R.id.lv);
        mMoney = (TextView) findViewById(R.id.money);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mZw = (TextView) findViewById(R.id.zw);
    }
}