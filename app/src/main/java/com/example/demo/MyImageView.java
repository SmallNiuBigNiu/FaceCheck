package com.example.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.json.Location;

public class MyImageView extends ImageView {
    private Path path = new Path();
    private Paint paint = new Paint();

    public MyImageView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }


    public void showFace(Location location) {
        path.reset();
        int left = (int) location.getLeft();
        int top = (int) location.getTop();
        int width = location.getWidth();
        int height = location.getHeight();

        path.moveTo(left, top);
        path.lineTo(left+width, top);
        path.lineTo(left+width, top+height);
        path.lineTo(left, top+height);
        path.lineTo(left, top);

        invalidate();
    }
}
