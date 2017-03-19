package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.application.miniSCADA.PLC.DataBlockReal;
import com.example.application.miniSCADA.PopupAnalogSetpoint;
import com.example.application.miniSCADA.R;

public class AnalogInput extends AnalogDisplay{
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

    public void getSetpointFromPopup(Intent intent){
        float value = Float.parseFloat(intent.getStringExtra("setpointValue"));
        setInputValue(value);
        getDisplayValue().setText(String.valueOf(inputValue));
    }

    public void createOnClickListener(final Activity activity, final Runtime runtime){
        getDisplayValue().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                runtime.setActiveElement(AnalogInput.this);
                Intent startPopup = new Intent(activity, PopupAnalogSetpoint.class);
                startPopup.putExtra("inputValue", String.valueOf(getInputValue()));

                activity.setResult(Activity.RESULT_OK,startPopup);
                activity.startActivityForResult(startPopup, 1);
            }
        });
    }
}
