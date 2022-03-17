package com.example.basicgitexperiments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint;
import android.view.View;

public class MyCanvas extends View {
    Paint paint;
    Rect rect;
    public MyCanvas(Context context) {
        super(context);
        paint = new Paint();
        rect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);

        canvas.drawRect(0, 0, canvas.getWidth(),canvas.getHeight(), paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(1, 1, canvas.getWidth()/2, canvas.getHeight()/2, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(1,1, canvas.getWidth()/4, paint);
    }
}
