package com.example.application.miniSCADA;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.MyButton;

import Moka7.*;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layout;
    public static final int posX = 50;
    public static final int posY = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = new RelativeLayout(this);
    }

    //S7Client client = new S7Client();

    public void readdb_val(View v){
        //new PlcReader(client,).execute("");
    }

    public void writeTrue(View v){
        byte[] data = new byte[1];
        S7.SetBitAt(data, 0, 0, true);
        DataBlock dataBlock = new DataBlockBool(7,4,data,0);
        new PlcWriter(dataBlock).execute("");
    }

    public void writeFalse(View v){
        byte[] data = new byte[1];
        S7.SetBitAt(data, 0, 0, false);
        DataBlock dataBlock = new DataBlockBool(7,4,data,0);
        new PlcWriter(dataBlock).execute("");
    }

    public void onClick(View view){
        byte[] data = new byte[1];
        DataBlockBool dataBlock = new DataBlockBool(7,4,data,0);
        MyButton myButton = new MyButton(this, dataBlock, R.drawable.true_button, R.drawable.false_button,dptoPx(this.posX),dptoPx(this.posY), dataBlock);
        myButton.updatePosition();
        myButton.drawObject(this.layout);
        myButton.createOnClickListener(view);
        setContentView(layout);
    }

    public int dptoPx(int dp){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
