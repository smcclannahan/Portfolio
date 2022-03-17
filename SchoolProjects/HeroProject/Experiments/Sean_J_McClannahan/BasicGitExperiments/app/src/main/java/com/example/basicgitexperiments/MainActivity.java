package com.example.basicgitexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;
    MyCanvas myCanvas;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCanvas  = new MyCanvas(this);
        random = new Random();
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.buttonMessage);
        b2 = (Button) findViewById(R.id.clearMessage);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                        Toast.makeText(getApplicationContext(),Integer.toString(random.nextInt(101)), Toast.LENGTH_LONG).show();

            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View view){
                    android.content.Intent i = new android.content.Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
        });



    }
}



