package com.example.android.policetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button police;
    private Button dispatch;
    private Button firemen;
    private Button ambulance;

    public static boolean pFlag = false;
    public static boolean dFlag = false;
    public static boolean fFlag = false;
    public static boolean aFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        police = (Button) findViewById(R.id.police);
        dispatch = (Button) findViewById(R.id.dispatch);
        firemen = (Button) findViewById(R.id.firemen);
        ambulance = (Button) findViewById(R.id.ambulance);

        police.setOnClickListener(this);
        dispatch.setOnClickListener(this);
        firemen.setOnClickListener(this);
        ambulance.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        switch (view.getId()) {

            case R.id.police:
                pFlag = true;
                startActivity(intent);
                break;

            case R.id.dispatch:
                dFlag = true;
                startActivity(intent);
                break;

            case R.id.firemen:
                fFlag = true;
                startActivity(intent);
                break;

            case R.id.ambulance:
                aFlag = true;
                startActivity(intent);
                break;


            default:
                break;
        }
    }
}
