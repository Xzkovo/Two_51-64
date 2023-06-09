package com.example.two_51_64;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.WZFX;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_58 extends AppCompatActivity {

    private List<WZFX> wzfxes, yes, notRepeated;
    private LineChart mLinechart;
    private ArrayList<String> arrayList = new ArrayList<>();
    private int xyl, lxl, yyl, xfl, hcksl, hcgs;
    private TextView mRq1;
    private TextView mRq2;
    private int years, months, days;
    private ImageView mSrc;
    private List<String> mRq = new ArrayList<>();
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main58);
        initView();

        arrayList.add("学院路");
        arrayList.add("联想路");
        arrayList.add("医院路");
        arrayList.add("幸福路");
        arrayList.add("环城快速路");
        arrayList.add("环城高速");

        new Thread(new Runnable() {
            @Override
            public void run() {
                get_peccancy();
            }
        }).start();

        mRq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog(1);

            }
        });
        mRq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog(2);

            }
        });

        mSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog();
            }
        });


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

                            for (int i = 0; i < yes.size(); i++) {

                                if (yes.get(i).getSite().equals("学院路")) {
                                    xyl++;
                                } else if (yes.get(i).getSite().equals("联想路")) {
                                    lxl++;
                                } else if (yes.get(i).getSite().equals("医院路")) {
                                    yyl++;
                                } else if (yes.get(i).getSite().equals("幸福路")) {
                                    xfl++;
                                } else if (yes.get(i).getSite().equals("环城快速路")) {
                                    hcksl++;
                                } else if (yes.get(i).getSite().equals("环城高速")) {
                                    hcgs++;
                                }

                            }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setData();
                                }
                            });


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

    }

    private void setData() {
        Float datas[] = {(float) xyl, (float) lxl, (float) yyl, (float) xfl, (float) hcksl, (float) hcgs};


        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < datas.length; i++) {
            entries.add(new Entry(i, datas[i]));
        }


        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setCircleRadius(5);//设置圆点半径大小
        lineDataSet.setLineWidth(3);    //设置折线的宽度
        lineDataSet.setDrawCircleHole(true);   //设置是否空心
        lineDataSet.setCircleHoleColor(Color.RED);
        lineDataSet.setCircleColors(Color.WHITE); //依次设置每个圆点的颜色
        lineDataSet.setColor(Color.RED);  //依次设置折线上的颜色
        lineDataSet.setDrawValues(true);    //设置显示折线上的数据
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);       //设置折线的类型
        LineData lineData = new LineData(lineDataSet);
        mLinechart.setExtraOffsets(30, 30, 30, 30);
        //设置不显示左下角和右下角标题
        mLinechart.getDescription().setEnabled(false);
        mLinechart.getLegend().setEnabled(false);
        mLinechart.setScaleEnabled(false);  //不允许放大
        mLinechart.invalidate();//数据有变化就自动更新
        mLinechart.setData(lineData);


        XAxis xAxis = mLinechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //设置X所在位置
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);   //设置是否绘制靠近X轴的第一条线
        xAxis.setDrawGridLines(false);  //是否绘制X轴的网格线
        xAxis.setTextSize(15);
        xAxis.setGranularity(1);
        xAxis.setTextColor(Color.parseColor("#46858d"));
        xAxis.setAxisMinimum(1f);
        xAxis.setAxisMaximum(5.6f);
        xAxis.setLabelCount(6);  //设置X轴显示数量
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return arrayList.get((int) value);
            }
        });


        mLinechart.getAxisRight().setEnabled(false);    //不显示右侧Y轴
        YAxis yAxis = mLinechart.getAxisLeft();
        yAxis.setTextSize(20);
        yAxis.setEnabled(true); //是否启用
        yAxis.setDrawAxisLine(false);   //是否绘制靠近Y轴的第一条线
        yAxis.setGridLineWidth(1);      //设置网格线的宽度
        yAxis.setGridColor(Color.BLACK);        //设置网格线的颜色
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(150);
        yAxis.setLabelCount(4, true);
    }

    private void initView() {
        mLinechart = (LineChart) findViewById(R.id.linechart);
        mRq1 = (TextView) findViewById(R.id.rq1);
        mRq2 = (TextView) findViewById(R.id.rq2);
        mSrc = (ImageView) findViewById(R.id.src);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void DatePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_58_, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        CalendarView calendarView = view.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String rq = i + "-" + i1 + "-" + i2;
                mRq.add(rq);
                for (int j = 0; j < mRq.size(); j++) {
                    System.out.println(mRq.get(j));
                }
            }
        });
        dialog.show();
    }

    private void Dialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_58, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        DatePicker datePicker = view.findViewById(R.id.date_picker_his);
        Button mQx = view.findViewById(R.id.qx);
        Button mQd = view.findViewById(R.id.qd);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    years = i;
                    months = i1;
                    days = i2;
                }
            });
        }
        mQd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (years == 0 && months == 0 && days == 0) {
                    Calendar c = Calendar.getInstance();
                    years = c.get(Calendar.YEAR);
                    months = c.get(Calendar.MONTH);
                    days = c.get(Calendar.DAY_OF_MONTH);
                }

                String temp;
                if (months < 10) {
                    temp = "0" + (months + 1);
                } else {
                    temp = (months + 1) + "";
                }

                if (i == 1) {
                    mRq1.setText(years + "-" + temp + "-" + days);
                } else if (i == 2) {
                    mRq2.setText(years + "-" + temp + "-" + days);
                }

                XZK();


                dialog.dismiss();
            }
        });


        mQx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void XZK() {
        String no1 = mRq1.getText().toString();
        String no2 = mRq2.getText().toString();

        if (no1.equals("") || no2.equals("")) {
            return;
        }
        notRepeated = new ArrayList<>();
        for (int i = 0; i < yes.size(); i++) {
            try {
                String datetime = yes.get(i).getDatetime().substring(0, 10);

                DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                if (dateFormat1.parse(no1).getTime() < dateFormat1.parse(datetime).getTime()) {
                    if (dateFormat1.parse(no2).getTime() > dateFormat1.parse(datetime).getTime()) {
                        notRepeated.add(yes.get(i));
                    }
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        xyl = 0;
        lxl = 0;
        yyl = 0;
        xfl = 0;
        hcksl = 0;
        hcgs = 0;

        for (int i = 0; i < notRepeated.size(); i++) {

            if (notRepeated.get(i).getSite().equals("学院路")) {
                xyl++;
            } else if (notRepeated.get(i).getSite().equals("联想路")) {
                lxl++;
            } else if (notRepeated.get(i).getSite().equals("医院路")) {
                yyl++;
            } else if (notRepeated.get(i).getSite().equals("幸福路")) {
                xfl++;
            } else if (notRepeated.get(i).getSite().equals("环城快速路")) {
                hcksl++;
            } else if (notRepeated.get(i).getSite().equals("环城高速")) {
                hcgs++;
            }

            setData();

        }


    }

}