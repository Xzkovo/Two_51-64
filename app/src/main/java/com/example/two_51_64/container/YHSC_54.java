package com.example.two_51_64.container;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.two_51_64.Adapter.YHSC_Adapter;
import com.example.two_51_64.DB.DBManager;
import com.example.two_51_64.R;
import com.example.two_51_64.User.SJK;

import java.util.ArrayList;
import java.util.List;

public class YHSC_54 extends AppCompatActivity {

    private ImageView mBack;
    private RecyclerView mRv;
    private List<SJK> sjks;
    private YHSC_Adapter adapter;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yhsc54);
        initView();
        DBManager manager = new DBManager(YHSC_54.this);
        boolean a = manager.isExist("yhsc");
        if (!a) {
            mLayout.setVisibility(View.VISIBLE);
            mRv.setVisibility(View.GONE);
            return;
        }
        mLayout.setVisibility(View.GONE);
        mRv.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        mRv.addItemDecoration(decoration);
        mRv.setLayoutManager(linearLayoutManager);
        //添加分割线
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //给item添加动画
        mRv.setItemAnimator(new DefaultItemAnimator());

        Cursor c = manager.queryDB("yhsc", null, null, null, null, null, "id desc", null);
        sjks = sjk(c);
        adapter = new YHSC_Adapter(sjks);
        mRv.setAdapter(adapter);
        adapter.setMyClick(new YHSC_Adapter.click() {
            @Override
            public void del(int lk) {
                Toast.makeText(YHSC_54.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                SJK sjk = sjks.get(lk);
                sjks.remove(lk);
                manager.del("yhsc", "id = ?", new String[]{sjk.getId()});
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private List<SJK> sjk(Cursor c) {
        List<SJK> sjks1 = new ArrayList<>();
        while (c.moveToNext()) {
            SJK sjk = new SJK();
            sjk.setYhm(c.getString(c.getColumnIndexOrThrow("yhm")));
            sjk.setSex(c.getString(c.getColumnIndexOrThrow("sex")));
            sjk.setName(c.getString(c.getColumnIndexOrThrow("name")));
            sjk.setTel(c.getString(c.getColumnIndexOrThrow("tel")));
            sjk.setRoot(c.getString(c.getColumnIndexOrThrow("root")));
            sjk.setTime(c.getString(c.getColumnIndexOrThrow("time")));
            sjk.setId(c.getString(c.getColumnIndexOrThrow("id")));
            sjks1.add(sjk);
        }
        return sjks1;
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLayout = (LinearLayout) findViewById(R.id.layout);
    }
}