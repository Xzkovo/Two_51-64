package com.example.two_51_64.util;

import android.content.Context;
import android.util.AttributeSet;

public class RadarChart extends com.github.mikephil.charting.charts.RadarChart {

    public RadarChart(Context context) {
        super(context);
    }

    public RadarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        this.mRenderer = new RadarChartRenderer(this, mAnimator, mViewPortHandler);
    }

    /**
     * 是否绘制顶角圆点
     * @param draw
     */
    public void setDrawAngleCircle(boolean draw) {
        ((RadarChartRenderer) this.mRenderer).setDrawAngleCircle(draw);
    }

    /**
     * 顶角圆点颜色
     * @param colors 颜色
     */
    public void setAngleCircleColor(int[] colors) {
        ((RadarChartRenderer) this.mRenderer).setAngleCircleColor(colors);
    }

    /**
     * 顶角圆点半径
     * @param radius 半径
     */
    public void setAngleCircleRadius(float radius) {
        ((RadarChartRenderer) this.mRenderer).setAngleCircleRadius(radius);
    }

}
