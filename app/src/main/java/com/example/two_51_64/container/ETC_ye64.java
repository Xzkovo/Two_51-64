package com.example.two_51_64.container;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.Adapter.ETC_Adapter;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.R;
import com.example.two_51_64.User.CLXX;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ETC_ye64 extends AppCompatActivity {

    private ImageView mBack;
    private ListView mLv;
    private List<CLXX> clxxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_ye64);
        initView();

        get_vehicle();


    }

    private void get_vehicle() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_vehicle", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            clxxList = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<CLXX>>() {
                            }.getType());

                            for (int i = 0; i < clxxList.size(); i++) {
                                get_balance_c(i,clxxList.get(i).getPlate());
                            }






                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private void get_balance_c(int i,String plate) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("plate", plate);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_balance_c", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            clxxList.get(i).setMoney(jsonObject1.getString("balance"));


                            if (i == clxxList.size() - 1){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ETC_Adapter adapter = new ETC_Adapter(ETC_ye64.this,clxxList);
                                        mLv.setAdapter(adapter);
                                    }
                                });
                            }

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
    }
}