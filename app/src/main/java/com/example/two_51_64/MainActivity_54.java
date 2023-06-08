package com.example.two_51_64;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.two_51_64.Adapter.YHGL_Adapter;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.ROOT;
import com.example.two_51_64.User.YHGL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main54);
        initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //设置类似listview的item黑色下划线
        DividerItemDecoration decoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        mRv.addItemDecoration(decoration);
        mRv.setLayoutManager(linearLayoutManager);
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
                                    });
                                }
                            });


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
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