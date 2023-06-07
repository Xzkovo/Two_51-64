package com.example.two_51_64;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.Adapter.RZCX_Adapter;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.MyListView;
import com.example.two_51_64.User.RZCX;

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

public class MainActivity_53 extends AppCompatActivity {

    private ImageView mBack;
    private MyListView mLv;
    private List<RZCX> rzcxes;
    private RZCX_Adapter adapter;
    private int size = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main53);
        initView();
        rzcxes = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {

                    get_all_sense(i);

                }
            }
        }).start();


    }

    private void get_all_sense(int lx) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            RZCX rzcx = new RZCX();
                            rzcx.setCo2(jsonObject1.getInt("co2"));
                            rzcx.setHumidity(jsonObject1.getInt("humidity"));
                            rzcx.setIllumination(jsonObject1.getInt("illumination"));
                            rzcx.setPm25(jsonObject1.getInt("pm25"));
                            rzcx.setTemperature(jsonObject1.getInt("temperature"));
                            rzcx.setTime(Time());
                            rzcxes.add(rzcx);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               if (lx==1){
                                   if (rzcxes.size() == 4) {
                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               adapter = new RZCX_Adapter(MainActivity_53.this, rzcxes);
                                               mLv.setAdapter(adapter);
                                               mLv.setOnRefreshListener(new MyListView.OnRefreshListener() {
                                                   @Override
                                                   public void refresh() {
                                                       new Handler().postDelayed(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               for (int i = 0; i < 2; i++) {
                                                                   get_all_sense(2);
                                                               }
                                                           }
                                                       },2000);
                                                   }
                                               });
                                           }
                                       });


                                   }
                               }else {
                                   if (rzcxes.size() == size+2){
                                       mLv.finishRefresh();
                                       adapter.notifyDataSetChanged();
                                       size += 2;
                                       Toast.makeText(MainActivity_53.this, "更新两条数据", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                       });



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private String Time(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mLv = (MyListView) findViewById(R.id.lv);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}