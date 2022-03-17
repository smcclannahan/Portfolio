package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    Bitmap background, tank;
    Rect rect;
    static int d_width, d_height;
    int horse_with;
    Handler handler;
    Runnable runnable;
    final long update_millis=30;
    ArrayList<Horse> horses, birds;
    ArrayList<Missile> missiles;
    static int tank_width, tank_height;
    Context context;
    int count =0;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank2);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        d_width = size.x;
        d_height = size.y;
        rect = new Rect(0,0, d_width, d_height);
        horses = new ArrayList<Horse>();
        birds = new ArrayList<Horse>();
        missiles = new ArrayList<>();

        for(int i=0;i<2;i++){
            Horse horse = new Horse(context);
            Horse2 horse2 = new Horse2(context);
            horses.add(horse);
            birds.add(horse2);
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        tank_width = tank.getWidth();
        tank_height = tank.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rect, null);
        for(int i=0;i<horses.size();i++){
            canvas.drawBitmap(horses.get(i).getBitmap(), horses.get(i).horse_x, horses.get(i).horse_y, null);
            horses.get(i).horse_frame++;
            if(horses.get(i).horse_frame > 6){
                horses.get(i).horse_frame=0;
            }
            horses.get(i).horse_x -= horses.get(i).horse_speed;
            if(horses.get(i).horse_x < -horses.get(i).getWidth()){
                horses.get(i).resetPosition();
            }

            canvas.drawBitmap(birds.get(i).getBitmap(), birds.get(i).horse_x, birds.get(i).horse_y, null);
            birds.get(i).horse_frame++;
            if(birds.get(i).horse_frame > 2){
                birds.get(i).horse_frame=0;
            }
            birds.get(i).horse_x += birds.get(i).horse_speed;
            if(birds.get(i).horse_x > (d_width+birds.get(i).getWidth())){
                birds.get(i).resetPosition();
            }
        }
        for(int i=0; i<missiles.size(); i++){
            if(missiles.get(i).y > -missiles.get(i).getMissileHeight()){
                missiles.get(i).y -= missiles.get(i).velocity;
                canvas.drawBitmap(missiles.get(i).missile, missiles.get(i).x, missiles.get(i).y+ tank_height, null);
                if((missiles.get(i).x >= horses.get(0).horse_x) && ((missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= horses.get(0).horse_x + horses.get(0).getWidth()) && (missiles.get(i).y >= horses.get(0).horse_y) &&
                        (missiles.get(i).y <= horses.get(0).horse_y + horses.get(0).getHeight())){
                    horses.get(0).resetPosition();
                    count++;
                    missiles.remove(i);

                }
                else if((missiles.get(i).x >= horses.get(1).horse_x) && (missiles.get(i).x + missiles.get(i).getMissileWidth()
                        <= horses.get(1).horse_x + horses.get(1).getWidth()) && (missiles.get(i).y >= horses.get(1).horse_y) &&
                        (missiles.get(i).y <= horses.get(1).horse_y + horses.get(1).getHeight())){
                    horses.get(1).resetPosition();
                    count++;
                    missiles.remove(i);

                }
                else if(missiles.get(i).x >= birds.get(0).horse_x && ((missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (birds.get(0).horse_x + birds.get(0).getWidth())) && (missiles.get(i).y >= birds.get(0).horse_y) &&
                        (missiles.get(i).y <= birds.get(0).horse_y + birds.get(0).getHeight())){
                    birds.get(0).resetPosition();
                    count++;
                    missiles.remove(i);

                }
                else if((missiles.get(i).x >= birds.get(1).horse_x) && (missiles.get(i).x + missiles.get(i).getMissileWidth()
                        <= birds.get(1).horse_x + birds.get(1).getWidth()) && (missiles.get(i).y >= birds.get(1).horse_y) &&
                        (missiles.get(i).y <= birds.get(1).horse_y + birds.get(1).getHeight())){
                    birds.get(1).resetPosition();
                    count++;
                    missiles.remove(i);

                }

            }
            else{
                missiles.remove(i);
            }
        }
        canvas.drawBitmap(tank, (d_width/2 - tank_width/2), (d_height - 160 - tank_height), null);
        handler.postDelayed(runnable, update_millis);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touch_x = event.getX();
        float touch_y = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            if(touch_x >= (d_width/2 - tank_width/2) && touch_x <= (d_width/2 + tank_width/2) && touch_y >= (d_height - 160 - tank_height)){
                Log.i("Tank","is tapped");
                if(missiles.size() < 3){
                    Missile m = new Missile(context);
                    missiles.add(m);
                }
            }
        }
        return true;
    }
}
