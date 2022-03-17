package com.example.basicgitexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button buttonMSG = (Button) findViewById(R.id.buttonMSG);

    int msgCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });


        buttonMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvMessage = (TextView) findViewById(R.id.tvMessage);
               msgCounter++;
              if (msgCounter%2 ==0)
                   tvMessage.setText("This is message 1");
               else
                    tvMessage.setText("This is message 2");
            }
        });



    }

    public void openActivity2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
