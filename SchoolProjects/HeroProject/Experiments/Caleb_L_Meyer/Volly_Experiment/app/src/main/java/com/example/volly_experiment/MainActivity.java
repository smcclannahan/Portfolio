package com.example.volly_experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bI, bJ, bS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bI = (Button) findViewById(R.id.button3);
        bJ = (Button) findViewById(R.id.button2);
        bS = (Button) findViewById(R.id.button);

        bI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ImageRequestActivity.class);
                startActivity(i);
            }
        });

        bJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, JSONRequestActivity.class);
                startActivity(i);
            }
        });

        bS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StringRequestActivity.class);
                startActivity(i);
            }
        });
    }
}
