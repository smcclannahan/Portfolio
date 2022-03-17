package com.example.basicgitexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyCanvas myCanvas;
        super.onCreate(savedInstanceState);
        myCanvas  = new MyCanvas(this);
        myCanvas.setBackgroundColor(Color.GREEN);
        setContentView(myCanvas);
    }
}
