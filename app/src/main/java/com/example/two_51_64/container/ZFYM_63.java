package com.example.two_51_64.container;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.R;
import com.example.two_51_64.User.LXZS;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class ZFYM_63 extends AppCompatActivity {

    private ImageView mBack;
    private ImageView mSrc;
    private Bitmap bitmap;
    private boolean a = true;
    private List<LXZS> lxzs = GWC_63.getList();
    private TextView mTop;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Random random = new Random();
            drawImage(url + random.nextInt(100));
            return false;
        }
    });
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfym63);
        initView();
        String temp = "";
        for (int i = 0; i < lxzs.size(); i++) {
            temp = temp.concat(lxzs.get(i).getName()).concat(",");
        }

        String moneys = GWC_63.getMoney();
        int money = Integer.parseInt(moneys.substring(4,moneys.length() - 1));



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (a) {
                        handler.sendEmptyMessage(0);
                        Thread.sleep(5000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        url = "付款项目：" + temp + "付款金额：" + money + "元";


        mSrc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mTop.setText(url);
                return false;
            }
        });

    }

    private void drawImage(String url) {
        Hashtable<EncodeHintType, String> hashtable = new Hashtable<>();
        hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300, hashtable);
            int[] pix = new int[300 * 300];
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    if (bitMatrix.get(x, y)) {
                        pix[y * 300 + x] = 0xff000000;
                    } else {
                        pix[y * 300 + x] = 0xffffffff;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pix, 0, 300, 0, 0, 300, 300);
            mSrc.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onDestroy() {
        a = false;
        super.onDestroy();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mSrc = (ImageView) findViewById(R.id.src);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTop = (TextView) findViewById(R.id.top);
    }
}