package com.example.two_51_64.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.R;
import com.example.two_51_64.User.GJCX;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class one_55 extends Fragment {

    private List<GJCX> gjcxes;
    private TextView mCar1;
    private TextView mCar2;
    private TextView mPm25;
    private TextView mTemperature;
    private TextView mHumidity;
    private TextView mCo2;
    private boolean a = true;
    private String pm25, temperature, humidity, co2;
    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 0) {
                mCar1.setText("1号公交车：" + gjcxes.get(0).getDistance());
                mCar2.setText("2号公交车：" + gjcxes.get(1).getDistance());
            } else if (message.what == 1) {
                mPm25.setText("PM2.5：" + pm25 + "ug/m3");
                mTemperature.setText("温度：" + temperature + "℃");
                mHumidity.setText("温度：" + humidity + "%");
                mCo2.setText("Co2：" + co2 + "PPM");
            }

            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_one55, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                get_bus_stop_distance();
                get_all_sense();
            }
        },0,3000);

    }

    private void get_all_sense() {
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
                            pm25 = jsonObject1.getString("pm25");
                            temperature = jsonObject1.getString("temperature");
                            humidity = jsonObject1.getString("humidity");
                            co2 = jsonObject1.getString("co2");
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private void get_bus_stop_distance() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_bus_stop_distance", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            gjcxes = new Gson().fromJson(jsonObject1.optJSONArray("中医院站").toString(), new TypeToken<List<GJCX>>() {
                            }.getType());
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }


    private void initView() {
        mCar1 = (TextView) getActivity().findViewById(R.id.car1);
        mCar2 = (TextView) getActivity().findViewById(R.id.car2);
        mPm25 = (TextView) getActivity().findViewById(R.id.pm25);
        mTemperature = (TextView) getActivity().findViewById(R.id.temperature);
        mHumidity = (TextView) getActivity().findViewById(R.id.humidity);
        mCo2 = (TextView) getActivity().findViewById(R.id.co2);
    }
}