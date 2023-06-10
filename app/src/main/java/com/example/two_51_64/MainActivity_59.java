package com.example.two_51_64;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.CXJG;
import com.example.two_51_64.User.WZBH;
import com.example.two_51_64.User.WZZL;
import com.example.two_51_64.container.CXJG_59;
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

public class MainActivity_59 extends AppCompatActivity {
    private ImageView mBack;
    private EditText mEd;
    private Button mCx;
    private List<WZBH> wzbhss;
    private static List<CXJG> cxjgs;
    private List<WZZL> wzzls;
    private List<String> infoid;
    private List<Integer> wzbhs;
    public static List<CXJG> getCxjg() {
        return cxjgs;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main59);
        initView();


        new Thread(new Runnable() {
            @Override
            public void run() {
                get_pessancy_infos();
            }
        }).start();

        mCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plate = mEd.getText().toString();
                if (plate.equals("")) {
                    Toast.makeText(MainActivity_59.this, "不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (plate.length() != 6) {
                    Toast.makeText(MainActivity_59.this, "请输入正确的车牌号！", Toast.LENGTH_SHORT).show();
                    return;
                }

                get_peccancy_plate("鲁" + plate);


            }
        });
    }

    private void get_pessancy_infos() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_pessancy_infos", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            wzzls = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<WZZL>>() {
                            }.getType());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


    }

    private void get_peccancy_plate(String plate) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("plate", plate);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_peccancy_plate", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            wzbhs = new Gson().fromJson(jsonObject1.optJSONArray("id").toString(), new TypeToken<List<Integer>>() {
                            }.getType());

                            get_all_car_peccancy();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

    }

    private void get_all_car_peccancy() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_all_car_peccancy", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            wzbhss = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<WZBH>>() {
                            }.getType());

                            infoid = new ArrayList<>();
                            for (int i = 0; i < wzbhss.size(); i++) {
                                for (int j = 0; j < wzbhs.size(); j++) {
                                    if (wzbhss.get(i).getId().equals(wzbhs.get(j))) {
                                        infoid.add(wzbhss.get(i).getInfoid());
                                    }
                                }
                            }

                            XZK();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private void XZK() {
        cxjgs = new ArrayList<>();
        for (int i = 0; i < wzzls.size(); i++) {

            for (int j = 0; j < infoid.size(); j++) {

                if (wzzls.get(i).getInfoid().equals(infoid.get(j))) {
                    CXJG cxjg = new CXJG();
                    cxjg.setName(wzzls.get(i).getRoad());
                    cxjg.setBody(wzzls.get(i).getMessage());
                    cxjg.setWz(j + 1);
                    cxjg.setKf(wzzls.get(i).getDeduct());
                    cxjg.setFk(wzzls.get(i).getFine());
                    cxjgs.add(cxjg);
                }

            }

        }
        Intent intent = new Intent(MainActivity_59.this, CXJG_59.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mEd = (EditText) findViewById(R.id.ed);
        mCx = (Button) findViewById(R.id.cx);
    }


}