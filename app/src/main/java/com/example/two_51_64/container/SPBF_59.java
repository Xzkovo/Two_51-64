package com.example.two_51_64.container;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.two_51_64.R;
import com.example.two_51_64.User.ImageListener;

public class SPBF_59 extends AppCompatActivity {

    private VideoView mVideo;
    private ImageView mSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbf59);
        initView();

        Intent intent = getIntent();

        int i = intent.getIntExtra("number", 0);

        if (i == 0) {

            mVideo.setVisibility(View.VISIBLE);
            mSrc.setVisibility(View.GONE);
            String url = "android.resource://" + getPackageName() + "/" + R.raw.car2;
            playVideoRaw(url);


        } else if (i == 1) {
            mVideo.setVisibility(View.GONE);
            mSrc.setVisibility(View.VISIBLE);
            mSrc.setImageResource(R.drawable.weizhang10);
            mSrc.setOnTouchListener(new ImageListener(mSrc));

        }


    }


    private void playVideoRaw(String uri) {
        MediaController mediaController = new MediaController(this);
        //获取raw.mp4的uri地址
//        String uri = "android.resource://" + getPackageName() + "/" + R.raw.car1;
        mVideo.setVideoURI(Uri.parse(uri));
        //让video和mediaController建立关联
        mVideo.setMediaController(mediaController);
        mediaController.setMediaPlayer(mVideo);
        //让video获取焦点
        mVideo.requestFocus();
        //监听播放完成，
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
                //重新开始播放
//                mVideo.start();
            }
        });
        mVideo.start();
    }


    private void initView() {
        mVideo = (VideoView) findViewById(R.id.video);
        mSrc = (ImageView) findViewById(R.id.src);
    }
}