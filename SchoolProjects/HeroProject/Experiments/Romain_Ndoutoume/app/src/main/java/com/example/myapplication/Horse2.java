package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Horse2 extends Horse {
    Bitmap bird[] = new Bitmap[7];
    public Horse2(Context context) {
        super(context);
        bird[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird2);
        bird[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird3);
        bird[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird4);
        bird[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird5);
        bird[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird6);
        bird[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird7);
        resetPosition();
    }

    @Override
    public Bitmap getBitmap() {
        return bird[horse_frame];
    }

    @Override
    public int getWidth() {
        return bird[0].getWidth();
    }

    @Override
    public int getHeight() {
        return bird[0].getHeight();
    }

    @Override
    public void resetPosition() {
        horse_x = -(200 +random.nextInt(1200));
        horse_y = random.nextInt(400);
        horse_speed = 5 + random.nextInt(13);
        //horse_frame=0;
    }
}
