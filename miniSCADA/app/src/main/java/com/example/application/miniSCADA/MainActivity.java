package com.example.application.miniSCADA;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.DiscreteObject;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.MyButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Moka7.*;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layout;
    private ArrayList<DiscreteObject> discreteObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = new RelativeLayout(this);
        discreteObjects = new ArrayList<DiscreteObject>();

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new PlcReader(discreteObjects).execute("");
                    }
                });
            }
        };
        //wykonywanie taska co 1000ms
        timer.schedule(task,0,1000);
    }

    public void onClick(View view){
        byte[] data = new byte[1];
        DataBlockBool statusDataBlock = new DataBlockBool(7,4,data,0);
        DataBlockBool commandOnDataBlock = new DataBlockBool(7,4,data,1);
        DataBlockBool commandOffDataBlock = new DataBlockBool(7,4,data,2);
        MyButton myButton = new MyButton(this, statusDataBlock, R.drawable.true_button, R.drawable.false_button,dptoPx(Globals.posX),dptoPx(Globals.posY), commandOnDataBlock, commandOffDataBlock);
        myButton.setTextOnFalse("Turn ON");
        myButton.setTextOnTrue("Turn OFF");
        myButton.updatePosition();
        myButton.drawObject(this.layout);
        myButton.createOnClickListener(view);
        myButton.createOnTouchListener(view,layout);
        setContentView(layout);
        discreteObjects.add(myButton);

        TextView myText = new TextView(this);
        myText.setX(50);
        myText.setY(500);
        myText.setHeight(100);
        myText.setWidth(300);

        layout.addView(myText);
    }

    public int dptoPx(int dp){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
