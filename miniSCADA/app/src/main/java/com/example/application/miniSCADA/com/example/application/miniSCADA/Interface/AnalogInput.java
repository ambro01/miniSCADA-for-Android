package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;


import android.app.Activity;
import android.content.Intent;

import com.example.application.miniSCADA.R;

public class AnalogInput extends AnalogDisplay{
    float inputValue;

    public AnalogInput(Activity activity, int x, int y, int height, int width){
        super(activity,x,y,height,width);
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

    public void getSetpointFromPopup(Intent intent){
        float value = Float.parseFloat(intent.getStringExtra("setpointValue"));
        setInputValue(value);
        getDisplayValue().setText(String.valueOf(inputValue));
    }
}
