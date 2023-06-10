package com.example.two_51_64;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.WZFX;

import com.example.two_51_64.util.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_62 extends AppCompatActivity {
    private List<WZFX> wzfxes, yes;
    private Map<String, Integer> map;
    private List<Map.Entry<String, Integer>> entrie;
    private ImageView mBack;
    private RadarChart mRadar;
    private TextView mNo1;
    private TextView mNo2;
    private TextView mNo3;
    private TextView mNo4;
    private TextView mNo5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main62);
        initView();
        get_peccancy();
    }

    private void get_peccancy() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_peccancy", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            wzfxes = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<WZFX>>() {
                            }.getType());

                            yes = new ArrayList<>();
                            for (int i = 0; i < wzfxes.size(); i++) {
                                if (!(wzfxes.get(i).getPaddr().equals(""))) {
                                    yes.add(wzfxes.get(i));
                                }
                            }


                            for (int i = 0; i < yes.size(); i++) {
                                for (int j = i + 1; j < yes.size(); j++) {
                                    if (yes.get(i).getCarnumber().equals(yes.get(j).getCarnumber())) {
                                        yes.remove(j);
                                        j--;
                                    }
                                }
                            }

                            map = new HashMap<>();
                            for (int i = 0; i < yes.size(); i++) {
                                String type = yes.get(i).getPaddr();
                                Integer count = map.get(type);
                                map.put(type, (count == null) ? 1 : count + 1);
                            }
                            entrie = new ArrayList<>(map.entrySet());

                            Collections.sort(entrie, new Comparator<Map.Entry<String, Integer>>() {
                                @Override
                                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                    return o2.getValue().compareTo(o1.getValue());
                                }
                            });

                            setData();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
    }


    private void setData() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wenzi();
            }
        });


        XAxis xAxis = mRadar.getXAxis();
        //设置x轴标签字体颜色
        xAxis.setLabelCount(4, true);
        xAxis.setAxisMaximum(4f);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(20f);

        //自定义y轴标签，x轴同理
        xAxis.setValueFormatter(new IndexAxisValueFormatter());

        YAxis yAxis = mRadar.getYAxis();
        //设置y轴的标签个数
        yAxis.setLabelCount(5, true);
        //设置y轴从0f开始
        yAxis.setAxisMinimum(0f);
        /*启用绘制Y轴顶点标签，这个是最新添加的功能
         * */
        yAxis.setDrawTopYLabelEntry(false);
        //设置字体大小
        yAxis.setTextSize(15f);
        //设置字体颜色
        yAxis.setTextColor(Color.parseColor("#979797"));

        //启用线条，如果禁用，则无任何线条
        mRadar.setDrawWeb(true);
        List<RadarEntry> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new RadarEntry((float) entrie.get(i).getValue()));
        }

        RadarDataSet set = new RadarDataSet(list, "");
        //禁用标签
        set.setDrawValues(false);
        //设置填充颜色
        set.setFillColor(Color.BLUE);
        //设置填充透明度
        set.setFillAlpha(40);
        //设置启用填充
        set.setDrawFilled(true);


        RadarData data = new RadarData(set);
        mRadar.setData(data);
        //禁用图例和图表描述
        mRadar.getDescription().setEnabled(false);
        mRadar.getLegend().setEnabled(false);
        //不可旋转
        mRadar.setRotationEnabled(false);
        //有更新就自动刷新
        mRadar.invalidate();
        //间距
        mRadar.setExtraTopOffset(20f);
        //是否绘制顶点圆点
        mRadar.setDrawAngleCircle(true);
        //顶点圆点的大小
        mRadar.setAngleCircleRadius(10f);
        //顶点圆点的颜色
        mRadar.setAngleCircleColor(new int[]{
                Color.parseColor("#36a9ce"),
                Color.parseColor("#33ff66"),
                Color.parseColor("#ef5aa1"),
                Color.parseColor("#ff0000"),
                Color.parseColor("#6600ff"),
        });

    }

    private void wenzi() {
        mNo1.setText(entrie.get(0).getKey());
        mNo2.setText(entrie.get(1).getKey());
        mNo3.setText(entrie.get(2).getKey());
        mNo4.setText(entrie.get(3).getKey());
        mNo5.setText(entrie.get(4).getKey());
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mRadar = findViewById(R.id.radar);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mNo1 = (TextView) findViewById(R.id.no1);
        mNo2 = (TextView) findViewById(R.id.no2);
        mNo3 = (TextView) findViewById(R.id.no3);
        mNo4 = (TextView) findViewById(R.id.no4);
        mNo5 = (TextView) findViewById(R.id.no5);
    }



}