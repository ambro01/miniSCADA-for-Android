package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.miniSCADA.DataBlockBool;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.PlcReader;
import com.example.application.miniSCADA.R;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.DiscreteElement;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.MyButton;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DevelopActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private RelativeLayout layout;
    //private ArrayList<DiscreteElement> discreteElements;
    private Visualisation visu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String projectName = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

        layout = (RelativeLayout) findViewById(R.id.develop_root);
        String deserialize = "";

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            projectName = extras.getString("projectName");
            deserialize = extras.getString("deserialize");
        }

        if(deserialize.equals("false")  && !projectName.isEmpty()){
            visu = new Visualisation(projectName, Color.WHITE);
            refreshBackgrondColor();
        } else if(deserialize.equals("true") && !projectName.isEmpty()){
            deserializeVisualisation(projectName);
        }

        //layout.setBackgroundColor(visu.getBackgrondColor());
    }

    public void refreshBackgrondColor(){
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
        serializeVisualisation();
    }

    public void onBackgroundColorChange(View view){
        onColorSelect(view);
    }

    public void onColorSelect(View view){
        ColorPickerDialog.newBuilder().setColor(Color.WHITE).setShowAlphaSlider(true).show(this);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        visu.setBackgrondColor(color);
        refreshBackgrondColor();
    }

    @Override
    public void onDialogDismissed(int dialogId) {
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

    public void serializeVisualisation(){
        File file = new File(getFilesDir() + "/" + visu.getName() + ".ser");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(visu);
            out.close();
            fileOut.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deserializeVisualisation(String name){
        File file = new File(getFilesDir() + "/" + name + ".ser");
        if(file.exists()) {
            try{
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                visu = (Visualisation) in.readObject();
                in.close();
                fileIn.close();

                refreshBackgrondColor();
                for(Element element : visu.getElements()){
                    MyButton myButton = (MyButton) element;
                    myButton.reCreateButton(this);
                    myButton.drawObject(this.layout);
                    myButton.createOnTouchListener(layout);
                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }


}
