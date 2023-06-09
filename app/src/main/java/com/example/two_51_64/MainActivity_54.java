package com.example.two_51_64;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.two_51_64.Adapter.YHGL_Adapter;
import com.example.two_51_64.DB.DBManager;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.ROOT;
import com.example.two_51_64.User.SJK;
import com.example.two_51_64.User.YHGL;
import com.example.two_51_64.container.YHSC_54;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_54 extends AppCompatActivity {

    private ImageView mBack;
    private ListView mLv;
    private RecyclerView mRv;
    private List<YHGL> yhgls;
    private List<ROOT> roots;
    private YHGL_Adapter adapter;
    private List<SJK> sjks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main54);
        initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //设置类似listview的item黑色下划线
        DividerItemDecoration decoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        mRv.addItemDecoration(decoration);
        mRv.setLayoutManager(linearLayoutManager);
        //添加分割线
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //给item添加动画
        mRv.setItemAnimator(new DefaultItemAnimator());
        get_roots();

    }

    private void get_roots() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_roots", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            yhgls = new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<YHGL>>() {
                            }.getType());
                            get_login();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    private void get_login() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_login", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            roots = new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<ROOT>>() {
                            }.getType());

                            for (int i = 0; i < yhgls.size(); i++) {
                                for (int j = 0; j < roots.size(); j++) {
                                    if (yhgls.get(i).getId().equals(roots.get(j).getId())) {
                                        yhgls.get(i).setRoot(roots.get(j).getRoot());
                                        yhgls.get(i).setUsername(roots.get(i).getUsername());
                                        continue;
                                    }
                                }
                            }
                            DBManager manager = new DBManager(MainActivity_54.this);
                            boolean a = manager.isExist("yhsc");
                            if (a) {
                                Cursor c = manager.queryDB("yhsc", null, null, null, null, null, "id asc", null);
                                sjks = sjk(c);
                                for (int i = 0; i < sjks.size(); i++) {
                                    SJK sjk = sjks.get(i);
                                    for (int j = 0; j < yhgls.size(); j++) {
                                        YHGL yhgl = yhgls.get(j);
                                        if (sjk.getName().equals(yhgl.getName())) {
                                            yhgls.get(j).setTf(true);
                                            continue;
                                        }

                                    }
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new YHGL_Adapter(yhgls);
                                    mRv.setAdapter(adapter);
                                    adapter.setClick(new YHGL_Adapter.Click() {
                                        @Override
                                        public void myClick(String sex) {
                                            System.out.println(sex);
                                        }

                                        @Override
                                        public void del(int i) {
                                            yhgls.remove(i);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void sc(int sc) {
                                            DBManager manager = new DBManager(MainActivity_54.this);
                                            YHGL yhgl = yhgls.get(sc);
                                            if (yhgl.isTf()) {
                                                for (int i = 0; i < yhgls.size(); i++) {
                                                    for (int j = 0; j < sjks.size(); j++) {
                                                        if (yhgls.get(sc).getName().equals(sjks.get(j).getName())) {
                                                            manager.del("yhsc", "id = ?", new String[]{sjks.get(j).getId()});
                                                            Toast.makeText(MainActivity_54.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                                                                 continue;
                                                        }
                                                    }

                                                }
                                                return;
                                            }
                                            Toast.makeText(MainActivity_54.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                                            boolean a = manager.isExist("yhsc");
                                            if (!a) {
                                                String sql = "create table yhsc (" +
                                                        "id integer primary key autoincrement," +
                                                        "yhm varchar," +
                                                        "sex varchar," +
                                                        "name varchar," +
                                                        "tel varchar," +
                                                        "root varchar," +
                                                        "time varchar);";
                                                manager.createTable(sql);
                                            }
                                            ContentValues cv = new ContentValues();
                                            cv.put("yhm", yhgl.getUsername());
                                            cv.put("sex", yhgl.getSex());
                                            cv.put("name", yhgl.getName());
                                            cv.put("tel", yhgl.getTel());
                                            cv.put("root", yhgl.getRoot());
                                            cv.put("time", Time());
                                            manager.insertDB("yhsc", cv);


                                        }

                                        @Override
                                        public void tz() {
                                            Intent intent = new Intent(MainActivity_54.this, YHSC_54.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            });


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
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

    private String Time() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String currentTime = sdf.format(date);
        return currentTime;
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mLv = (ListView) findViewById(R.id.lv);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRv = (RecyclerView) findViewById(R.id.rv);
    }
}