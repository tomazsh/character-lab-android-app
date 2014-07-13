package org.characterlab.android.views;

import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class BarGraph extends View {

    private ArrayList<Bar> points = new ArrayList<Bar>();

    private Paint paint = new Paint();
    private Rect barRect;
    private Path path = new Path();
    private Bitmap fullImage;

    private int indexSelected = -1;
    private OnBarClickedListener listener;

    public BarGraph(Context context) {
        super(context);
    }

    public BarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBars(ArrayList<Bar> points) {
        this.points = points;
        postInvalidate();
        removeCallbacks(animator);
        post(animator);
    }

    public ArrayList<Bar> getBars() {
        return this.points;
    }

    public void onDraw(Canvas ca) {

        fullImage = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fullImage);
        canvas.drawColor(Color.TRANSPARENT);

        float padding = 7;
        int selectPadding = 4;
        float leftPadding = 20;
        float usableWidth = getWidth() - leftPadding;

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setAlpha(50);
        paint.setAntiAlias(true);

        canvas.drawLine(leftPadding, 0, leftPadding, getHeight(), paint);

        float barHeight = (getHeight() - ((padding * 2) * points.size())) / points.size();
        float perUnitWidth = usableWidth / 7;

        barRect = new Rect();
        path.reset();

        int count = 0;
        for (Bar barToDraw : points) {
            float currentValueBarHeight = barHeight * 0.70f;
            float currentAvgValueBarHeight = barHeight * 0.30f;

            int left = (int) leftPadding;
            int top = (int) ((padding * 2) * count + padding + barHeight * count);
            int right = left + (int) (perUnitWidth * (barToDraw.getCurrentValue()));
            int bottom = (int) (top + currentValueBarHeight);

            barRect.set(left, top, right, bottom);

            Path barPath = new Path();
            barPath.addRect(new RectF(barRect.left - selectPadding, barRect.top - selectPadding, barRect.right + selectPadding, barRect.bottom + selectPadding), Path.Direction.CW);
            barToDraw.setPath(barPath);
            barToDraw.setRegion(new Region(barRect.left - selectPadding, barRect.top - selectPadding, barRect.right + selectPadding, barRect.bottom + selectPadding));
            //this.paint.setColor(barToDraw.getColor());
            this.paint.setColor(Color.parseColor("#FFFF00"));
            this.paint.setAlpha(125);
            canvas.drawRect(barRect, this.paint);

            // avg bar start

            Rect avgBarRect = new Rect();
            left = barRect.left;
            top = barRect.bottom;
            right = left + (int) (perUnitWidth * (barToDraw.getCurrentAvgValue()));
            bottom = (int) (top + currentAvgValueBarHeight);

            avgBarRect.set(left, top, right, bottom);
            this.paint.setColor(Color.parseColor("#33CCFF"));
            this.paint.setAlpha(125);
            canvas.drawRect(avgBarRect, this.paint);

            // avg bar end

            this.paint.setColor(Color.DKGRAY);
            this.paint.setTextSize(sp2px(getContext(), 9));
            String traitTitleAndValueStr = barToDraw.getName() + "  (" + (int) barToDraw.getValue() + ")";
            canvas.drawText(traitTitleAndValueStr, (int) (leftPadding + 10), barRect.top + (currentValueBarHeight / 2) + 7, this.paint);
            this.paint.setColor(barToDraw.getColor());

            if (indexSelected == count && listener != null) {
                this.paint.setColor(Color.parseColor("#33B5E5"));
                this.paint.setAlpha(100);
                canvas.drawPath(barToDraw.getPath(), this.paint);
                this.paint.setAlpha(255);
            }
            count++;
        }
        ca.drawBitmap(fullImage, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();

        int count = 0;
        for (Bar bar : points) {
            Region region = new Region();
            region.setPath(bar.getPath(), bar.getRegion());
            if (region.contains(point.x, point.y) && event.getAction() == MotionEvent.ACTION_DOWN) {
                indexSelected = count;
                break;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (region.contains(point.x, point.y) && listener != null) {
                    listener.onClick(bar.getName());
                    indexSelected = -1; break;
                }
                indexSelected = -1;
            }
            count++;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
            postInvalidate();
        }

        return true;
    }

    public void setOnBarClickedListener(OnBarClickedListener listener) {
        this.listener = listener;
    }

    public interface OnBarClickedListener {
        void onClick(String name);
    }

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            float changeValue = BarGraph.this.getContext().getResources().getDisplayMetrics().density * 0.05f;
            for (Bar bar : points) {
                if (Math.abs(bar.getValue() - bar.getCurrentValue()) < changeValue) {
                    bar.setCurrentValue(bar.getValue());
                }

                if (Math.abs(bar.getAvgValue() - bar.getCurrentAvgValue()) < changeValue) {
                    bar.setCurrentAvgValue(bar.getAvgValue());
                }

                if (bar.getCurrentValue() < bar.getValue()) {
                    bar.setCurrentValue(bar.getCurrentValue() + changeValue);
                    needNewFrame = true;
                } else if (bar.getCurrentValue() > bar.getValue()) {
                    bar.setCurrentValue(bar.getValue());
                    needNewFrame = true;
                }

                if (bar.getCurrentAvgValue() < bar.getAvgValue()) {
                    bar.setCurrentAvgValue(bar.getCurrentAvgValue() + changeValue);
                    needNewFrame = true;
                } else if (bar.getCurrentAvgValue() > bar.getAvgValue()) {
                    bar.setCurrentAvgValue(bar.getAvgValue());
                    needNewFrame = true;
                }

            }
            if (needNewFrame) {
                postDelayed(this, 20);
            }
            invalidate();
        }
    };

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
