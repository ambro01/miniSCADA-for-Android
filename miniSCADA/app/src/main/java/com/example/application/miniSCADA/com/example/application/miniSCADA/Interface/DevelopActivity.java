package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.miniSCADA.DataBlockBool;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.PlcReader;
import com.example.application.miniSCADA.R;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.DiscreteElement;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.MyButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DevelopActivity extends AppCompatActivity {

    private RelativeLayout layout;
    //private ArrayList<DiscreteElement> discreteElements;
    private Visualisation visu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String projectName = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

        layout = (RelativeLayout) findViewById(R.id.develop_root);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectName = extras.getString("projectName");
        }

        if(projectName != ""){
            visu = new Visualisation(projectName, Color.WHITE);
        }
        layout.setBackgroundColor(visu.getBackgrondColor());
    }

    public void onButtonCreate(View view){

        byte[] data = new byte[1];
        DataBlockBool statusDataBlock = new DataBlockBool(7,4,data,0);
        DataBlockBool commandOnDataBlock = new DataBlockBool(7,4,data,1);
        DataBlockBool commandOffDataBlock = new DataBlockBool(7,4,data,2);
        MyButton myButton = new MyButton(this, statusDataBlock, R.drawable.true_button, R.drawable.false_button,dptoPx(Globals.posX),dptoPx(Globals.posY), commandOnDataBlock, commandOffDataBlock);
        myButton.setTextOnFalse("Turn ON");
        myButton.setTextOnTrue("Turn OFF");
        myButton.updatePosition();
        myButton.drawObject(this.layout);
        //myButton.createOnClickListener(view);
        myButton.createOnTouchListener(layout);
        //setContentView(layout);
        visu.addElement(myButton);
    }

    public void onSaveProject(View view){

        File file = new File(getFilesDir() + "/" + visu.getName() + ".ser");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(visu);
            oos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int dptoPx(int dp){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void periodicallyReadPlc(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new PlcReader(visu.getElements()).execute("");
                    }
                });
            }
        };
        //wykonywanie taska co 1000ms
        timer.schedule(task,0,1000);
    }
}
