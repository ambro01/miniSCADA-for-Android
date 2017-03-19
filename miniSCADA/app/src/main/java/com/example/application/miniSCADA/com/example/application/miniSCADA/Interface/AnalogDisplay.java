package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;


import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.miniSCADA.PLC.DataBlockReal;
import com.example.application.miniSCADA.PopupAnalog;
import com.example.application.miniSCADA.R;

public class AnalogDisplay extends Element{
    private transient TextView displayValue;
    private DataBlockReal outputDataBlock;

    public AnalogDisplay(Activity activity, int x, int y, int height, int width){
        super(x,y,height,width);
        displayValue = new TextView(activity);
        defaultSettings();
    }

    public void defaultSettings(){
        displayValue.setBackgroundResource(R.drawable.display_analog);
        displayValue.setTextSize(16);
        displayValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        displayValue.setText("Display");
    }

    public TextView getDisplayValue(){
        return displayValue;
    }

    public DataBlockReal getDataBlock(){
        return outputDataBlock;
    }

    public DataBlockReal getOutputDataBlock(){
        return outputDataBlock;
    }

    public void setOutputDataBlock(DataBlockReal dataBlock){
        outputDataBlock = dataBlock;
    }

    public void updatePositionToElement(){
        displayValue.setX(this.getPositionX());
        displayValue.setY(this.getPositionY());
    }

    public void updatePositionFromElement(){
        this.setPosition((int)displayValue.getX(), (int)displayValue.getY());
    }

    public void updateSizeToElement(){
        displayValue.setMinimumHeight(this.getHeight());
        displayValue.setMinimumWidth(this.getWidth());
    }

    public void updateSizeFromElement(){
        this.setSize(displayValue.getHeight(), displayValue.getWidth());
    }

    public void drawObject(RelativeLayout layout){
        layout.addView(displayValue);
    }

    public void reCreateElement(Activity activity){
        displayValue = new TextView(activity);
        defaultSettings();

        updatePositionToElement();
        updateSizeToElement();
    }

    public void createOnTouchListener(final RelativeLayout layout){
        displayValue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lparams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        setOldPosition(me.getRawX() - lparams.leftMargin, me.getRawY() - lparams.topMargin);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        params.leftMargin = (int)(me.getRawX() - getOldXposition());
                        params.topMargin = (int)(me.getRawY() - getOldYposition());
                        params.rightMargin = -250;
                        params.bottomMargin = -250;
                        displayValue.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }

    public void createOnLongClickListener(final Activity activity, final Develop develop){
        displayValue.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                develop.setActiveElement(AnalogDisplay.this);
                Intent startPopup = new Intent(activity, PopupAnalog.class);
                activity.startActivityForResult(startPopup,1);
                return true;
            }
        });
    }

    public void activeOnLongClickListener(Activity activity,Develop develop){
        createOnLongClickListener(activity, develop);
        displayValue.setOnTouchListener(null);
    }

    public void activeOnTouchListener(RelativeLayout layout){
        createOnTouchListener(layout);
        displayValue.setOnLongClickListener(null);
    }

    public void createDataFromPopup(Intent intent){
        int dbNumber;
        int wordNumber;

        dbNumber = Integer.parseInt(intent.getStringExtra("dbNumber_status"));
        wordNumber = Integer.parseInt(intent.getStringExtra("wordNumber_status"));
        setOutputDataBlock(new DataBlockReal(dbNumber,wordNumber, new byte[1]));
    }
}
