package com.example.two_51_64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_61 extends AppCompatActivity{

    private ImageView mImgzoom;
    private float beforeScale=1.0f;//之前的伸缩值
    private float nowScale;//当前的伸缩值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main61);
        initView();
        myScale();


    }

    private void myScale(){
        final ScaleGestureDetector scaleGestureDetector=new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                nowScale=detector.getScaleFactor()*beforeScale;
                //设置缩放的范围
                if (nowScale>3||nowScale<0.1){
                    beforeScale=nowScale;
                    return true;
                }
                Log.i("Scale","nowScale="+nowScale);
                Matrix matrix=new Matrix();
                matrix.setScale(nowScale,nowScale);
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.touxiang_1);//获取图片
                bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);//转化成bitmap格式
                mImgzoom.setImageBitmap(bitmap);
                beforeScale=nowScale;//保存上一次的缩放值！
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;//记得改成ture；
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
        mImgzoom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }


    private void initView() {
        mImgzoom = (ImageView) findViewById(R.id.src);
    }

}