package com.example.two_51_64.container;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.MainActivity_59;
import com.example.two_51_64.R;
import com.example.two_51_64.User.CXJG;

import java.util.List;

public class CXJG_59 extends AppCompatActivity {

    private ImageView mBack;
    private TextView mName;
    private LinearLayout mLift;
    private LinearLayout mRight;
    private TextView mWz;
    private TextView mFk;
    private TextView mKf;
    private List<CXJG> cxjg = MainActivity_59.getCxjg();
    private TextView mBody;
    private int number = 0;
    private ImageView mVideo;
    private ImageView mSrc;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxjg59);
        initView();

        CXJG cxjg1 = cxjg.get(0);
        mName.setText(cxjg1.getName());
        mBody.setText(cxjg1.getBody());
        mWz.setText("违章：" + cxjg1.getWz());
        mFk.setText("罚款：" + cxjg1.getFk());
        mKf.setText("扣分：" + cxjg1.getKf());

        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car2);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, url);
        Bitmap bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        mVideo.setImageBitmap(bitmap);

        mVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CXJG_59.this, SPBF_59.class);
                intent.putExtra("number",0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        mSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CXJG_59.this, SPBF_59.class);
                intent.putExtra("number",1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number == 0) {
                    Toast.makeText(CXJG_59.this, "已经是第一个了！", Toast.LENGTH_SHORT).show();
                    return;
                }
                number--;
                CXJG cxjg1 = cxjg.get(number);
                mName.setText(cxjg1.getName());
                mBody.setText(cxjg1.getBody());
                mWz.setText("违章：" + cxjg1.getWz());
                mFk.setText("罚款：" + cxjg1.getFk());
                mKf.setText("扣分：" + cxjg1.getKf());


            }
        });
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number == cxjg.size() - 1) {
                    Toast.makeText(CXJG_59.this, "已经到最后一个了！", Toast.LENGTH_SHORT).show();
                    return;
                }
                number++;
                CXJG cxjg1 = cxjg.get(number);
                mName.setText(cxjg1.getName());
                mBody.setText(cxjg1.getBody());
                mWz.setText("违章：" + cxjg1.getWz());
                mFk.setText("罚款：" + cxjg1.getFk());
                mKf.setText("扣分：" + cxjg1.getKf());
            }
        });


    }


    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mName = (TextView) findViewById(R.id.name);
        mLift = (LinearLayout) findViewById(R.id.lift);
        mRight = (LinearLayout) findViewById(R.id.right);
        mWz = (TextView) findViewById(R.id.wz);
        mFk = (TextView) findViewById(R.id.fk);
        mKf = (TextView) findViewById(R.id.kf);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBody = (TextView) findViewById(R.id.body);
        mVideo = (ImageView) findViewById(R.id.video);
        mSrc = (ImageView) findViewById(R.id.src);
    }
}