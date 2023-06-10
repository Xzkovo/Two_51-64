package com.example.two_51_64.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RadarChartRenderer extends com.github.mikephil.charting.renderer.RadarChartRenderer {
    private Paint mAngleCirclePaint;
    private float mAngleCircleRadius = Utils.convertDpToPixel(5f);
    private int[] mAngleCircleColors;
    private boolean mDrawAngleCircle = false;

    public RadarChartRenderer(RadarChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        mAngleCirclePaint = new Paint();
    }

    @Override
    protected void drawWeb(Canvas c) {
        float sliceangle = mChart.getSliceAngle();

        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();
        float rotationangle = mChart.getRotationAngle();

        MPPointF center = mChart.getCenterOffsets();

        // draw the web lines that come from the center
        mWebPaint.setStrokeWidth(mChart.getWebLineWidth());
        mWebPaint.setColor(mChart.getWebColor());
        mWebPaint.setAlpha(mChart.getWebAlpha());

        final int xIncrements = 1 + mChart.getSkipWebLineCount();
        int maxEntryCount = mChart.getData().getMaxEntryCountSet().getEntryCount();

        MPPointF p = MPPointF.getInstance(0,0);
        for (int i = 0; i < maxEntryCount; i += xIncrements) {

            Utils.getPosition(
                    center,
                    mChart.getYRange() * factor,
                    sliceangle * i + rotationangle,
                    p);

            c.drawLine(center.x, center.y, p.x, p.y, mWebPaint);
        }
        MPPointF.recycleInstance(p);

        // draw the inner-web
        mWebPaint.setStrokeWidth(mChart.getWebLineWidthInner());
        mWebPaint.setColor(mChart.getWebColorInner());
        mWebPaint.setAlpha(mChart.getWebAlpha());

        int labelCount = mChart.getYAxis().mEntryCount;

        MPPointF p1out = MPPointF.getInstance(0,0);
        MPPointF p2out = MPPointF.getInstance(0,0);
        for (int j = 0; j < labelCount; j++) {

            for (int i = 0; i < mChart.getData().getEntryCount(); i++) {

                float r = (mChart.getYAxis().mEntries[j] - mChart.getYChartMin()) * factor;

                Utils.getPosition(center, r, sliceangle * i + rotationangle, p1out);
                Utils.getPosition(center, r, sliceangle * (i + 1) + rotationangle, p2out);

                c.drawLine(p1out.x, p1out.y, p2out.x, p2out.y, mWebPaint);
                // start（加上下面这几行代码，用于绘制每个顶角上的圆）
                if (mDrawAngleCircle && j == labelCount - 1) {
                    mAngleCirclePaint.setColor(mAngleCircleColors == null || mAngleCircleColors.length == 0 ? Color.BLACK : this.mAngleCircleColors[i % mAngleCircleColors.length]);
                    c.drawCircle(p2out.x, p2out.y, mAngleCircleRadius, mAngleCirclePaint);
                }
                // end
            }
        }
        MPPointF.recycleInstance(p1out);
        MPPointF.recycleInstance(p2out);
    }

    /**
     * 顶角圆点颜色
     * @param colors 颜色
     */
    public void setAngleCircleColor(int[] colors) {
        this.mAngleCircleColors = colors;
    }

    /**
     * 顶角圆点半径
     * @param radius 半径
     */
    public void setAngleCircleRadius(float radius) {
        this.mAngleCircleRadius = Utils.convertDpToPixel(radius);
    }

    /**
     * 是否绘制顶角圆点
     * @param draw
     */
    public void setDrawAngleCircle(boolean draw) {
        this.mDrawAngleCircle = draw;
    }

}
