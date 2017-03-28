package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.application.miniSCADA.PLC.DataBlock;
import com.example.application.miniSCADA.PLC.DataBlockReal;
import com.example.application.miniSCADA.PLC.PlcWriter;
import com.example.application.miniSCADA.PopupAnalogSetpoint;
import com.example.application.miniSCADA.R;

import java.nio.ByteBuffer;

import Moka7.S7;

public class AnalogInput extends AnalogDisplay{
    DataBlockReal inputDataBlock;
    float inputValue;

    public AnalogInput(Activity activity, DataBlockReal dataBlockReal, int x, int y, int height, int width){
        super(activity,dataBlockReal,x,y,height,width);
        getDisplayValue().setBackgroundResource(R.drawable.input_analog);
        getDisplayValue().setText("Input");
    }

    public void setInputValue(float value){
        inputValue = value;
    }

    public float getInputValue(){
        return inputValue;
    }

    public void reCreateElement(Activity activity){
        super.reCreateElement(activity);
        getDisplayValue().setText("Input");
        getDisplayValue().setBackgroundResource(R.drawable.input_analog);
    }

    public void getSetpointFromPopup(Activity activity, Intent intent){
        float value = Float.parseFloat(intent.getStringExtra(activity.getResources().getString(R.string.extraValueFromPopupText)));
        setInputValue(value);

        setDataBlockFromFloat();
        sentDataBlockToPlc();
    }

    public void setDataBlockFromFloat(){
        byte [] data = new byte[4];
        S7.SetFloatAt(data,0,inputValue);
        inputDataBlock = new DataBlockReal(getDataBlock().getDbNumber(),getDataBlock().getPosition(), data);
    }

    public void sentDataBlockToPlc(){
        new PlcWriter(inputDataBlock).execute("");
    }

    public void createOnClickListener(final Activity activity, final Runtime runtime){
        getDisplayValue().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                runtime.setActiveElement(AnalogInput.this);
                Intent startPopup = new Intent(activity, PopupAnalogSetpoint.class);
                startPopup.putExtra(activity.getResources().getString(R.string.extraValueToPopupText), String.valueOf(getOutputValue()));

                activity.setResult(Activity.RESULT_OK,startPopup);
                activity.startActivityForResult(startPopup, 1);
            }
        });
    }
}
