package com.example.two_51_64.container;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.MainActivity_63;
import com.example.two_51_64.R;
import com.example.two_51_64.User.LXZS;

public class XXXX_63 extends AppCompatActivity {

    private ImageView mBack;
    private TextView mBody;
    private RatingBar mRb;
    private TextView mTel;
    private ImageView mSrc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xxxx63);
        initView();

        Intent intent = getIntent();
        int number = intent.getIntExtra("position",0);
        LXZS lxzs = MainActivity_63.getLxzs().get(number);
        if (lxzs.getName().equals("故宫")) {
            mSrc.setImageResource(R.drawable.gugong2);
        } else {
            mSrc.setImageResource(R.drawable.gugong1);
        }
        mBody.setText(lxzs.getPresentation());
        mTel.setText(lxzs.getTel());


        mRb.setRating(Float.parseFloat(lxzs.getGrade()));
        mTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + lxzs.getTel()));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBody = (TextView) findViewById(R.id.body);
        mRb = (RatingBar) findViewById(R.id.rb);
        mTel = (TextView) findViewById(R.id.tel);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSrc = (ImageView) findViewById(R.id.src);
    }
}