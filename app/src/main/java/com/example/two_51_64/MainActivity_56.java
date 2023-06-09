package com.example.two_51_64;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.two_51_64.Adapter.SSLS_Adapter;
import com.example.two_51_64.DB.DBManager;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.LSJL;
import com.example.two_51_64.User.SSJT;
import com.example.two_51_64.container.X_SSJT_56;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_56 extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private ImageView mChange;
    private TextView mTitle;
    private NavigationView mNav;
    private static SSJT ssjt;
    private EditText mEd;
    private TextView mTranslate;
    private List<LSJL> lsjls;
    private ListView mLv;
    private TextView mDel;
    private SSLS_Adapter adapter;
    private ImageView mBack;

    public static SSJT getSsjt() {
        return ssjt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main56);

        initView();

        db();

        mTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEd.getText().toString().equals("")) {
                    Toast.makeText(MainActivity_56.this, "不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                get_bus_data(mEd.getText().toString());

            }
        });

        mDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager manager = new DBManager(MainActivity_56.this);
                manager.delTable("ssls");
                lsjls.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void db() {
        DBManager manager = new DBManager(MainActivity_56.this);
        boolean a = manager.isExist("ssls");
        if (!a) {
            return;
        }
        Cursor c = manager.queryDB("ssls", null, null, null, null, null, "id asc", null);
        lsjls = cursorToList(c);
        manager.closeDb();
        c.close();
        adapter = new SSLS_Adapter(MainActivity_56.this, lsjls);
        mLv.setAdapter(adapter);

    }


    private List<LSJL> cursorToList(Cursor c) {
        List<LSJL> lsjls1 = new ArrayList<>();
        while (c.moveToNext()) {
            LSJL lsjl = new LSJL();
            lsjl.setNumber(c.getString(c.getColumnIndexOrThrow("number")));
            lsjl.setSite(c.getString(c.getColumnIndexOrThrow("site")));
            lsjls1.add(lsjl);
        }
        return lsjls1;
    }


    private void get_bus_data(String Busid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("Busid", Busid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_bus_data", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            ssjt = new Gson().fromJson(jsonObject1.toString(), SSJT.class);
                            if (ssjt.getROWS_DETAIL().size() == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity_56.this, "未查询到此路公交车！", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                return;
                            }

                            Intent intent = new Intent(MainActivity_56.this, X_SSJT_56.class);
                            intent.putExtra("title", mEd.getText().toString());
                            startActivity(intent);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    DBManager manager = new DBManager(MainActivity_56.this);
                                    boolean a = manager.isExist("ssls");
                                    if (!a) {
                                        String sql = "create table ssls (" +
                                                "id integer primary key autoincrement," +
                                                "number varchar," +
                                                "site varchar);";
                                        manager.createTable(sql);
                                    }

                                    ContentValues cv = new ContentValues();
                                    cv.put("number", mEd.getText().toString() + "路");
                                    cv.put("site", "(" + ssjt.getROWS_DETAIL().get(0).getSite().get(0) + "-" + ssjt.getROWS_DETAIL().get(0).getSite().get(ssjt.getROWS_DETAIL().get(0).getSite().size() - 1) + ")");
                                    manager.insertDB("ssls", cv);


                                }
                            }).start();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private void initView() {
        mEd = (EditText) findViewById(R.id.ed);
        mTranslate = (TextView) findViewById(R.id.translate);
        mLv = (ListView) findViewById(R.id.lv);
        mDel = (TextView) findViewById(R.id.del);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}