package com.example.two_51_64;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.two_51_64.Adapter.HJJC_Adapter;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.HJJC;
import com.example.two_51_64.User.HJJC_List;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_57 extends AppCompatActivity {
    private TextView mTitle;
    private TextView mTime;
    private TextView mZjgx;
    private List<HJJC> hjjcs;
    private PieChart mPiechart;
    private int i = 0;
    private int order = 0;
    private HJJC_Adapter adapter;
    private boolean a = false;
    private int number = 1;
    private int select = 0;
    private boolean isActive = true;
    private String[] name = {"北京", "上海", "深圳", "重庆", "雄安"};
    private List<HJJC_List> hjjc_lists = new ArrayList<>();
    private int[] colors = {Color.parseColor("#2f4554"), Color.parseColor("#61a0a8"),
            Color.parseColor("#d48265"), Color.parseColor("#91c7ae"),
            Color.parseColor("#c23531")};
    private TextView mTop;
    private ListView mLv;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 0) {
                mTime.setText(Time());
                adapter = new HJJC_Adapter(MainActivity_57.this, hjjc_lists, select, order);
                mLv.setAdapter(adapter);
                logData();
            } else if (message.what == 1) {
                mZjgx.setText("最近更新：" + number + "分钟之前");
            }
            return false;
        }
    });
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main57);

        initView();


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isActive) {
                    try {
                        hjjcs = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            get_all_sense(i);
                        }
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isActive) {
                    try {
                        i++;
                        if (i == 60) {
                            i = 0;
                            number++;
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        }).start();

        mPiechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                //点的第几个
                select = (int) highlight.getX();
                mTop.setText(name[select]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new HJJC_Adapter(MainActivity_57.this, hjjc_lists, select, order);
                        mLv.setAdapter(adapter);
                    }
                });


            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void initView() {
        mTime = (TextView) findViewById(R.id.time);
        mZjgx = (TextView) findViewById(R.id.zjgx);
        mPiechart = (PieChart) findViewById(R.id.piechart);
        mTop = (TextView) findViewById(R.id.top);
        mLv = (ListView) findViewById(R.id.lv);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void get_all_sense(int i) {
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
                            HJJC hjjc = new HJJC();
                            hjjc.setCo2(jsonObject1.getInt("co2"));
                            hjjc.setHumidity(jsonObject1.getInt("humidity"));
                            hjjc.setIllumination(jsonObject1.getInt("illumination"));
                            hjjc.setPm25(jsonObject1.getInt("pm25"));
                            hjjc.setTemperature(jsonObject1.getInt("temperature"));
                            hjjcs.add(hjjc);

                            HJJC_List hjjc_list = new HJJC_List();
                            hjjc_list.setTemperatureMax(hjjc.getTemperature());
                            hjjc_list.setTemperatureMin(hjjc.getTemperature());
                            hjjc_list.setTemperatureAve(hjjc.getTemperature());
                            hjjc_list.setHumidityMax(hjjc.getHumidity());
                            hjjc_list.setHumidityMin(hjjc.getHumidity());
                            hjjc_list.setHumidityAve(hjjc.getHumidity());
                            hjjc_list.setIlluminationMax(hjjc.getIllumination());
                            hjjc_list.setIlluminationMin(hjjc.getIllumination());
                            hjjc_list.setIlluminationAve(hjjc.getIllumination());
                            hjjc_list.setCo2Max(hjjc.getCo2());
                            hjjc_list.setCo2Min(hjjc.getCo2());
                            hjjc_list.setCo2Ave(hjjc.getCo2());
                            hjjc_list.setPm25Max(hjjc.getPm25());
                            hjjc_list.setPm25Ave(hjjc.getPm25());
                            hjjc_list.setPm25Min(hjjc.getPm25());
                            if (!a) {
                                if (i == 0) {
                                    hjjc_list.setName("北京");
                                } else if (i == 1) {
                                    hjjc_list.setName("上海");
                                } else if (i == 2) {
                                    hjjc_list.setName("深圳");
                                } else if (i == 3) {
                                    hjjc_list.setName("重庆");
                                } else if (i == 4) {
                                    hjjc_list.setName("雄安");
                                }
                                hjjc_lists.add(hjjc_list);

                            } else {
                                if (hjjc_lists.size() != 5) {
                                    hjjc_lists = new ArrayList<>();
                                    get_all_sense(order);
                                }


                                try {
                                    if (hjjc_lists.get(i).getTemperatureMax() < hjjc_list.getTemperatureMax()) {
                                        hjjc_lists.get(i).setTemperatureMax(hjjc_list.getTemperatureMax());
                                        if (hjjc_lists.get(i).getTemperatureMin() > hjjc_list.getTemperatureMin()) {
                                            hjjc_lists.get(i).setTemperatureMin(hjjc_list.getTemperatureMin());
                                        }
                                    } else {
                                        if (hjjc_lists.get(i).getTemperatureMin() > hjjc_list.getTemperatureMin()) {
                                            hjjc_lists.get(i).setTemperatureMin(hjjc_list.getTemperatureMin());
                                        }
                                    }

                                    if (hjjc_lists.get(i).getHumidityMax() < hjjc_list.getHumidityMax()) {
                                        hjjc_lists.get(i).setHumidityMax(hjjc_list.getHumidityMax());
                                        if (hjjc_lists.get(i).getHumidityMin() > hjjc_list.getHumidityMin()) {
                                            hjjc_lists.get(i).setHumidityMin(hjjc_list.getHumidityMin());
                                        }
                                    } else {
                                        if (hjjc_lists.get(i).getHumidityMin() > hjjc_list.getHumidityMin()) {
                                            hjjc_lists.get(i).setHumidityMin(hjjc_list.getHumidityMin());
                                        }
                                    }

                                    if (hjjc_lists.get(i).getIlluminationMax() < hjjc_list.getIlluminationMax()) {
                                        hjjc_lists.get(i).setIlluminationMax(hjjc_list.getIlluminationMax());
                                        if (hjjc_lists.get(i).getIlluminationMin() > hjjc_list.getIlluminationMin()) {
                                            hjjc_lists.get(i).setIlluminationMin(hjjc_list.getIlluminationMin());
                                        }
                                    } else {
                                        if (hjjc_lists.get(i).getIlluminationMin() > hjjc_list.getIlluminationMin()) {
                                            hjjc_lists.get(i).setIlluminationMin(hjjc_list.getIlluminationMin());
                                        }
                                    }

                                    if (hjjc_lists.get(i).getCo2Max() < hjjc_list.getCo2Max()) {
                                        hjjc_lists.get(i).setCo2Max(hjjc_list.getCo2Max());
                                        if (hjjc_lists.get(i).getCo2Min() > hjjc_list.getCo2Min()) {
                                            hjjc_lists.get(i).setCo2Min(hjjc_list.getCo2Min());
                                        }
                                    } else {
                                        if (hjjc_lists.get(i).getCo2Min() > hjjc_list.getCo2Min()) {
                                            hjjc_lists.get(i).setCo2Min(hjjc_list.getCo2Min());
                                        }
                                    }

                                    if (hjjc_lists.get(i).getPm25Max() < hjjc_list.getPm25Max()) {
                                        hjjc_lists.get(i).setPm25Max(hjjc_list.getPm25Max());
                                        if (hjjc_lists.get(i).getPm25Min() > hjjc_list.getPm25Min()) {
                                            hjjc_lists.get(i).setPm25Min(hjjc_list.getPm25Min());
                                        }
                                    } else {
                                        if (hjjc_lists.get(i).getPm25Min() > hjjc_list.getPm25Min()) {
                                            hjjc_lists.get(i).setPm25Min(hjjc_list.getPm25Min());
                                        }
                                    }
                                } catch (Exception e) {
                                    hjjc_lists = new ArrayList<>();
                                    get_all_sense(order);
                                }

                                hjjc_lists.get(i).setTemperatureAve(hjjc_lists.get(i).getTemperatureAve() + hjjc.getTemperature());
                                hjjc_lists.get(i).setHumidityAve(hjjc_lists.get(i).getHumidityAve() + hjjc.getHumidity());
                                hjjc_lists.get(i).setIlluminationAve(hjjc_lists.get(i).getIlluminationAve() + hjjc.getIllumination());
                                hjjc_lists.get(i).setCo2Ave(hjjc_lists.get(i).getCo2Ave() + hjjc.getCo2());
                                hjjc_lists.get(i).setPm25Ave(hjjc_lists.get(i).getPm25Ave() + hjjc.getPm25());


                            }


                            if (hjjcs.size() == 5) {
                                a = true;
                                Message message = new Message();
                                message.what = 0;
                                handler.sendMessage(message);
                            }

                            order++;

                        } catch (JSONException e) {
                            hjjc_lists = new ArrayList<>();
                            get_all_sense(order);
                        }

                    }
                });
    }

    private void logData() {
        List<PieEntry> entries = new ArrayList<>();
        int a = 0;
        for (int i = 0; i < hjjcs.size(); i++) {
            try {
                a += hjjcs.get(i).getCo2();
            } catch (Exception e) {
                return;
            }
        }

        entries.add(new PieEntry(((float) hjjcs.get(0).getCo2() / a) * 100));
        entries.add(new PieEntry(((float) hjjcs.get(1).getCo2() / a) * 100));
        entries.add(new PieEntry(((float) hjjcs.get(2).getCo2() / a) * 100));
        entries.add(new PieEntry(((float) hjjcs.get(3).getCo2() / a) * 100));
        entries.add(new PieEntry(((float) hjjcs.get(4).getCo2() / a) * 100));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(colors);
        final String strs[] = {"北京", "上海", "深圳", "重庆", "雄安"};
        pieDataSet.setValueFormatter(new ValueFormatter() {
            private int indd = -1;

            @Override
            public String getPieLabel(float value, PieEntry pieEntry) {
                indd++;
                if (indd >= strs.length) {
                    indd = 0;
                }

                return strs[indd];
            }
        });
        pieDataSet.setValueTextSize(30f);
        pieDataSet.setSliceSpace(10f);  //设置饼块与饼块之间的距离
        pieDataSet.setSelectionShift(15f);      //设置单击饼块后多出来的距离
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);   //设置连接线显示在外边
        pieDataSet.setValueLinePart1OffsetPercentage(100f);     //设置连接线距离饼图的距离，为百分数
        pieDataSet.setValueLinePart1Length(0.7f);   //定义连接线的长度
        PieData pieData = new PieData(pieDataSet);
        mPiechart.getDescription().setEnabled(false);   //不显示描述/标题
        mPiechart.getLegend().setEnabled(false);        //不显示图例
        mPiechart.setDrawHoleEnabled(false);    //不绘制空洞
        mPiechart.setRotationEnabled(false);        //不可旋转
        mPiechart.setExtraOffsets(30, 30, 30, 30);
        mPiechart.setData(pieData);
        mPiechart.invalidate();


    }

    private String Time() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm EEEE", Locale.getDefault()).format(calendar.getTime());
        // "yyyy-MM-dd HH:mm"表示按照年月日时分的格式进行格式化，"EEEE"表示星期几，Locale.getDefault()表示使用系统默认语言环境
        return currentDate;
    }


    @Override
    protected void onDestroy() {
        isActive = false;
        super.onDestroy();
    }

}