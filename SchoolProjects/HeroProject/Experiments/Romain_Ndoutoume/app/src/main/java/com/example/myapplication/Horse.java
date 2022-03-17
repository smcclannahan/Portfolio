package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Horse {
    Bitmap horse[] = new Bitmap[7];
    int horse_x, horse_y, horse_speed, horse_frame;
    Random random;

    public Horse(Context context) {
        horse[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse1);
        horse[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse2);
        horse[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse3);
        horse[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse4);
        horse[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse5);
        horse[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse6);
        horse[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse7);
        random = new Random();
        resetPosition();
    }
    public Bitmap getBitmap(){
        return horse[horse_frame];
    }
    public int getWidth(){
        return horse[0].getWidth();
    }
    public int getHeight(){
        return horse[0].getHeight();
    }

    public void resetPosition(){
        horse_x = GameView.d_width +random.nextInt(1200);
        horse_y = random.nextInt(300);
        horse_speed = 8 + random.nextInt(13);
        horse_frame=0;
    }

}
