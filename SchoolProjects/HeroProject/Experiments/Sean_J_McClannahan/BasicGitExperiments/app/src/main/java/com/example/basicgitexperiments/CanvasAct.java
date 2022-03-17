package com.example.basicgitexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class CanvasAct extends AppCompatActivity {
    MyCanvas myCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        myCanvas  = new MyCanvas(this);
        myCanvas.setBackgroundColor(Color.GREEN);
        setContentView(myCanvas);
    }
}
