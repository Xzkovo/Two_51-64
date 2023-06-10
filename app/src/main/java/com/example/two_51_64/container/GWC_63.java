package com.example.two_51_64.container;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.two_51_64.Adapter.GWC_Adapter;
import com.example.two_51_64.MainActivity_63;
import com.example.two_51_64.R;
import com.example.two_51_64.User.LXZS;

import java.util.ArrayList;
import java.util.List;

public class GWC_63 extends AppCompatActivity {

    private ImageView mBack;
    private boolean temp = false;
    private TextView mJt;
    private TextView mMt;
    private ListView mLv;
    private static TextView mMoney;
    private int moneys = 0;
    private Button mZf;
    private Button mQk;
    private GWC_Adapter adapter;
    private static List<LXZS> lxzs = new ArrayList<>();
    private TextView mGl;

    public static List<LXZS> getList() {
        return lxzs;
    }

    public static String getMoney() {
        return mMoney.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwc63);
        System.out.println(1);
        initView();
        Intent intent = getIntent();
        int number = intent.getIntExtra("position", 0);
        LXZS lxzs1 = MainActivity_63.getLxzs().get(number);
        lxzs1.setMoney(1);
        lxzs = MainActivity_63.getAdd();
        lxzs.add(lxzs1);
        MainActivity_63.setAdd(lxzs);
        for (int i = 0; i < lxzs.size();i++){
            if (!lxzs.get(i).isTf()){
                lxzs.get(i).setTf(false);
            }
        }

        adapter = new GWC_Adapter(GWC_63.this, lxzs);
        mLv.setAdapter(adapter);
        adapter.setGwcItem(new GWC_Adapter.gwcItem() {
            @Override
            public void gwcItem(int number, int position, int money) {
                if (number == 1) {
                    lxzs.get(position).setMoney(lxzs.get(position).getMoney() + 1);
                    adapter.notifyDataSetChanged();
                    moneys += money;
                    mMoney.setText("总金额：" + moneys + "元");
                } else if (number == 2) {
                    lxzs.get(position).setMoney(lxzs.get(position).getMoney() - 1);
                    adapter.notifyDataSetChanged();
                    moneys -= money;
                    mMoney.setText("总金额：" + moneys + "元");
                }
            }

            @Override
            public void sc(int number, int position) {
                if (number == 1) {
                    int temp = Integer.parseInt(lxzs.get(position).getPrice());
                    moneys -= temp;
                    mMoney.setText("总金额：" + moneys + "元");
                    lxzs.remove(position);
                    MainActivity_63.setAdd(lxzs);
                    for (int i = 0; i < lxzs.size();i++){
                        System.out.println(lxzs.get(i).isTf());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

        });

        day();

        for (int i = 0; i < lxzs.size(); i++) {
            for (int j = 0; j < lxzs.get(i).getMoney(); j++) {
                moneys += Integer.parseInt(lxzs.get(i).getPrice());
            }
        }
        mMoney.setText("总金额：" + moneys + "元");
        mQk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(lxzs.size());
                for (int i = 0; i < lxzs.size(); i++) {
                    lxzs.remove(i);
                    i--;
                }
                mMoney.setText("总金额：0元");
                adapter.notifyDataSetChanged();
            }
        });
        mGl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!temp) {
                    for (int i = 0; i < lxzs.size(); i++) {
                        lxzs.get(i).setTf(true);
                    }
                    temp = true;
                } else {
                    for (int i = 0; i < lxzs.size(); i++) {
                        lxzs.get(i).setTf(false);
                    }
                    temp = false;
                }

                adapter.notifyDataSetChanged();
            }
        });
        mZf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moneys == 0){
                    Toast.makeText(GWC_63.this, "最少要购买一张才可以支付！", Toast.LENGTH_SHORT).show();
                return;
                }
                Intent intent1 = new Intent(GWC_63.this,ZFYM_63.class);
                startActivity(intent1);


            }
        });

    }

    private void day() {
        mJt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mJt.setBackgroundResource(R.drawable.xiahuaxian);
                mJt.setTextColor(Color.BLACK);
                mMt.setBackgroundResource(R.drawable.wuxiahuaxian);
                mMt.setTextColor(Color.parseColor("#9A9A9A"));
            }
        });
        mMt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMt.setBackgroundResource(R.drawable.xiahuaxian);
                mMt.setTextColor(Color.BLACK);
                mJt.setBackgroundResource(R.drawable.wuxiahuaxian);
                mJt.setTextColor(Color.parseColor("#9A9A9A"));
            }
        });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mJt = (TextView) findViewById(R.id.jt);
        mMt = (TextView) findViewById(R.id.mt);
        mLv = (ListView) findViewById(R.id.lv);
        mMoney = (TextView) findViewById(R.id.money);
        mZf = (Button) findViewById(R.id.zf);
        mQk = (Button) findViewById(R.id.qk);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < lxzs.size(); i++) {
                    lxzs.get(i).setTf(false);
                }
                MainActivity_63.setAdd(lxzs);

                for (int i = 0; i < lxzs.size();i++){
                    System.out.println("=" + i + "=" + lxzs.get(i).isTf());
                }
                temp = false;
                finish();
            }
        });
        mGl = (TextView) findViewById(R.id.gl);
    }


}