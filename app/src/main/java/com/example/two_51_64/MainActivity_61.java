package com.example.two_51_64;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.Adapter.TQXX_Adapter;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.TQXX;
import com.example.two_51_64.User.Time;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_61 extends AppCompatActivity{

    private ImageView mBack;
    private TextView mTemperature;
    private TextView mWeather;
    private TextView mTime;
    private GridView mGv;
    private List<TQXX> tqxxes;
    private ImageView mRefresh;
    private Time time;
    private LineChart mLc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main61);
        initView();


        get_weather_info();
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_weather_info();
            }
        });


    }

    private void get_weather_info() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_weather_info", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            tqxxes = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<TQXX>>() {
                            }.getType());
                            String temperature = jsonObject1.getString("temperature");
                            String weather = jsonObject1.getString("weather");


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTemperature.setText(temperature + "°");
                                    mWeather.setText(weather);
                                    mTime.setText(time());
                                    TQXX_Adapter adapter = new TQXX_Adapter(MainActivity_61.this, tqxxes, time);
                                    mGv.setAdapter(adapter);
                                }
                            });

                            List<Float> zg = new ArrayList<>();
                            List<Float> zd = new ArrayList<>();
                            for (int i = 0; i < tqxxes.size(); i++) {
                                String[] arr = tqxxes.get(i).getInterval().split("~");
                                zd.add(Float.valueOf(arr[0]));
                                zg.add(Float.valueOf(arr[1]));
                            }


                            setData(zg, zd);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
    }

    private void setData(List<Float> zg, List<Float> zd) {
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < zg.size(); i++) {
            entries.add(new Entry(i, zd.get(i)));
            entries1.add(new Entry(i, zg.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(entries,"");
        LineDataSet lineDataSet1 = new LineDataSet(entries1,"");

        //设置不显示左下角和右下角标题
        mLc.getDescription().setEnabled(false);
        mLc.getLegend().setEnabled(false);

        lineDataSet.setCircleRadius(5); //设置圆点半径大小
        lineDataSet.setLineWidth(3);  //设置折线的宽度
        lineDataSet.setDrawCircleHole(false); //设置是否空心
        lineDataSet.setCircleColors(Color.BLUE); //依次设置每个远点的颜色
        lineDataSet.setColor(Color.BLUE); //依次设置个折线的颜色
        lineDataSet.setDrawValues(true); //设置显示折线上的数据
        lineDataSet.setMode(LineDataSet.Mode.LINEAR); //设置折线的类型

        lineDataSet1.setCircleRadius(5); //设置圆点半径大小
        lineDataSet1.setLineWidth(3);  //设置折线的宽度
        lineDataSet1.setDrawCircleHole(false); //设置是否空心
        lineDataSet1.setCircleColors(Color.RED); //依次设置每个远点的颜色
        lineDataSet1.setColor(Color.RED); //依次设置个折线的颜色
        lineDataSet1.setDrawValues(true); //设置显示折线上的数据
        lineDataSet1.setMode(LineDataSet.Mode.LINEAR); //设置折线的类型

        LineData lineData = new LineData(lineDataSet, lineDataSet1);
        mLc.setData(lineData);
        mLc.setExtraTopOffset(20);
        mLc.setExtraRightOffset(20);
        mLc.setExtraLeftOffset(20);
        mLc.setExtraBottomOffset(20);
        mLc.setScaleEnabled(false); //不允许放大
        mLc.invalidate();  //数据有变化就自动更新


        XAxis xAxis = mLc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);          //设置X所在的位置，可取值：TOP(默认值)：位于上方，TOP_INSIDE：位于上方并绘制在图形内部，BOTTOM：位于下方，BOTTOM_INSIDE：位于下方并绘制在图形内部，BOTH_SIDED：上下两边都显示轴
        xAxis.setDrawAxisLine(false);       //是否绘制靠近x轴的第一条线
        xAxis.setDrawGridLines(false);      //是否绘制X轴的网格线
        xAxis.setGranularity(1f);           //设置x轴每两个值之间的间隔
        xAxis.setTextSize(20);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setEnabled(false);




        mLc.getAxisRight().setEnabled(false);     //不显示右侧Y轴
        YAxis yAxis = mLc.getAxisLeft();
        yAxis.setTextSize(20);
        yAxis.setDrawGridLines(false);
        yAxis.setEnabled(true);     //是否启用
        yAxis.setDrawAxisLine(false);   //是否绘制靠近y轴的第一条线，设置xAxis.setDrawGridLines(false)才有效果
        yAxis.setGridLineWidth(1);      //设置网格线的宽度
        yAxis.setGridColor(Color.BLACK);    //设置网格线的颜色
        yAxis.setDrawLabels(false);     //不绘制左边的描述文字
        yAxis.setAxisMinimum(0);           //设置Y轴的最小值
        yAxis.setAxisMaximum(40);           //设置Y轴的最大值


    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mTemperature = (TextView) findViewById(R.id.temperature);
        mWeather = (TextView) findViewById(R.id.weather);
        mTime = (TextView) findViewById(R.id.time);
        mGv = (GridView) findViewById(R.id.gv);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRefresh = (ImageView) findViewById(R.id.Refresh);
        mLc = (LineChart) findViewById(R.id.lc);
    }

    private String time() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        List<String> temp = new ArrayList<>();

        temp.add("昨天");
        temp.add("今天");
        temp.add("明天");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        for (int i = 1; i <= 4; i++) {
            calendar.add(Calendar.DATE, 1);
            String dayOfWeek = dateFormat.format(calendar.getTime());
            if (i != 1) {
                temp.add(dayOfWeek);
            }
        }


        List<String> rq = new ArrayList<>();
        for (int i = -1; i < 5; i++) {
            Calendar cals = Calendar.getInstance();
            cals.add(Calendar.DATE, i);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
            rq.add(sdf.format(cals.getTime()));
        }

        time = new Time();
        time.setTime(temp);
        time.setRq(rq);

        return hour + ":" + minute + "刷新";


    }


}