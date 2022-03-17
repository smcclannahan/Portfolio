package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Missile {
    int x, y;
    int velocity;
    Bitmap missile;

    public Missile(Context context) {
        missile = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire2);
        x=GameView.d_width/2 - getMissileWidth()/2;
        y=GameView.d_height/2 - GameView.tank_height - getMissileHeight()/2;
        velocity=50;

    }
    public int getMissileWidth(){
        return missile.getWidth();
    }
    public int getMissileHeight(){
        return missile.getHeight();
    }
}
