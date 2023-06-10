package com.example.two_51_64.container;

import android.content.ContentValues;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.DB.DBManager;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.R;
import com.example.two_51_64.User.ETC;

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

public class ETC_cz64 extends AppCompatActivity {

    private ImageView mBack;
    private List<ETC> plate = new ArrayList<>();
    private EditText mClbh;
    private TextView mCph;
    private TextView mShi;
    private TextView mWushi;
    private TextView mYibai;
    private EditText mMoney;
    private Button mCz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_cz64);
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 7; i++) {
                    get_plate(i);
                }

            }
        }).start();


        mClbh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 当文本发生改变之后调用此方法
                String temp = mClbh.getText().toString();
                if (temp.equals("")) {
                    mCph.setText("");
                    return;
                }
                int number = Integer.parseInt(temp);
                for (int i = 0; i < plate.size(); i++) {
                    if (number == plate.get(i).getNumber()) {
                        mCph.setText(plate.get(i).getPlate());
                        return;
                    }
                    mCph.setText("");
                }


            }
        });


        mCz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plate = mCph.getText().toString();
                String money = mMoney.getText().toString();
                if (plate.equals("")) {
                    Toast.makeText(ETC_cz64.this, "车牌号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (money.equals("")) {
                    Toast.makeText(ETC_cz64.this, "充值金额不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                set_balance(plate, money);


            }
        });

    }

    private void set_balance(String plate, String money) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("plate", plate);
            jsonObject.put("balance", money);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("set_balance", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            String temp = jsonObject1.getString("RESULT");
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (temp.equals("S")) {
                                      Toast.makeText(ETC_cz64.this, "充值成功！", Toast.LENGTH_SHORT).show();
                                      DB(plate,money);
                                  } else {
                                      Toast.makeText(ETC_cz64.this, "充值失败！", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
    }

    private void DB(String plate,String money) {
        DBManager manager = new DBManager(ETC_cz64.this);
        boolean a = manager.isExist("czjl");
        if (!a){
            String sql = "create table czjl (" +
                    "id integer primary key autoincrement," +
                    "time1 varchar," +
                    "xingqi varchar," +
                    "plate varchar," +
                    "money varchar," +
                    "time2 text);";
            manager.createTable(sql);
        }
        ContentValues cv = new ContentValues();
        cv.put("time1",Times());
        cv.put("xingqi",xingqi());
        cv.put("plate",plate);
        cv.put("money",money);
        cv.put("time2",Time());
        manager.insertDB("czjl",cv);


    }

    private String xingqi(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String[] weekDays = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String today = weekDays[dayOfWeek - 1];
        return today;    // 输出类似 "周三" 的字符串
    }

    private String Time(){
        // 获取当前时间
        Date date = new Date();

        // 设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // 格式化时间并输出
        String now = sdf.format(date);
        return now;
    }

    private String Times(){
        // 获取当前时间
        Date date = new Date();

        // 设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        // 格式化时间并输出
        String now = sdf.format(date);
        return now;
    }

    private void get_plate(int number) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("number", number);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_plate", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            ETC etc = new ETC();
                            etc.setNumber(number);
                            etc.setPlate(jsonObject1.getString("plate"));
                            plate.add(etc);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mClbh = (EditText) findViewById(R.id.clbh);
        mCph = (TextView) findViewById(R.id.cph);
        mShi = (TextView) findViewById(R.id.shi);
        mWushi = (TextView) findViewById(R.id.wushi);
        mYibai = (TextView) findViewById(R.id.yibai);
        mMoney = (EditText) findViewById(R.id.money);
        mShi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoney.append("10");
            }
        });
        mWushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoney.append("50");
            }
        });
        mYibai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoney.append("100");
            }
        });
        mCz = (Button) findViewById(R.id.cz);
    }
}