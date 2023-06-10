package com.example.two_51_64;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.two_51_64.Adapter.LXZS_Adapter;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.LXZS;
import com.example.two_51_64.container.GWC_63;
import com.example.two_51_64.container.XXXX_63;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_63 extends AppCompatActivity {
    private ImageView mBack;
    private GridView mGv;
    private static List<LXZS> lxzs;

    public static List<LXZS> getAdd() {
        return add;
    }

    public static void setAdd(List<LXZS> add) {
        MainActivity_63.add = add;
    }

    private static List<LXZS> add = new ArrayList<>();

    public static List<LXZS> getLxzs() {
        return lxzs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main63);
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                get_scenic_spot();
            }
        }).start();
    }

    private void get_scenic_spot() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_scenic_spot", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.body().string());
                            lxzs = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<LXZS>>() {
                            }.getType());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LXZS_Adapter adapter = new LXZS_Adapter(MainActivity_63.this, lxzs);
                                    mGv.setAdapter(adapter);
                                    adapter.setClickItem(new LXZS_Adapter.ClickItem() {
                                        @Override
                                        public void myClick(int position) {
                                            Intent intent = new Intent(MainActivity_63.this, XXXX_63.class);
                                            intent.putExtra("position", position);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void myClicks(int position) {
                                            Intent intent = new Intent(MainActivity_63.this, GWC_63.class);
                                            intent.putExtra("position",position);
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


    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mGv = (GridView) findViewById(R.id.gv);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}