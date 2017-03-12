package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.*;

import Moka7.*;

public class MyButton extends DiscreteObject{
    private Button button;
    private DataBlockBool commandOnDataBlock;
    private DataBlockBool commandOffDataBlock;
    private String textOnTrue;
    private String textOnFalse;
    private float oldXvalue;
    private float oldYvalue;

    public MyButton(Activity activity, DataBlockBool statusDataBlock, int onTrueImageId, int onFalseImageId, int x, int y, DataBlockBool commandOnDataBlock,DataBlockBool commandOffDataBlock){
        super(statusDataBlock,onTrueImageId,onFalseImageId,x,y);
        button = new Button(activity);
        this.updateImage();
        this.textOnTrue = "";
        this.textOnFalse = "";
        this.commandOnDataBlock = commandOnDataBlock;
        this.commandOffDataBlock = commandOffDataBlock;
        this.button.setTextColor(Color.BLACK);
        this.button.setTextSize(12);
    }

    public Button getButton(){
        return button;
    }

    public void setTextOnTrue(String text){
        this.textOnTrue = text;
    }

    public void setTextOnFalse(String text){
        this.textOnFalse = text;
    }

    public void updateImage(){
        if (this.getStatus()) {
            button.setBackgroundResource(this.getOnTrueImageId());
            button.setText(this.textOnTrue);
        } else {
            button.setBackgroundResource(this.getOnFalseImageId());
            button.setText(this.textOnFalse);
        }
    }

    public void executeCommand(){
        if(this.getStatus()){
            S7.SetBitAt(this.commandOffDataBlock.getData(), 0, this.commandOffDataBlock.getBitPosition(), true);
            new PlcWriter(this.commandOffDataBlock).execute("");
        } else {
            S7.SetBitAt(this.commandOnDataBlock.getData(), 0, this.commandOnDataBlock.getBitPosition(), true);
            new PlcWriter(this.commandOnDataBlock).execute("");
        }
    }

    public void updateSize(){
        button.setHeight(this.getHeight());
        button.setWidth(this.getWidth());
    }

    public void updatePosition(){
        button.setX(this.getPositionX());
        button.setY(this.getPositionY());
    }

    public void drawObject(RelativeLayout layout){
        layout.addView(this.button);
    }

    public void createOnClickListener(View view){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                executeCommand();
            }
        });
    }
/*
    public void createOnLongClickListener(View view){
        button.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(button);
                button.startDrag(clipData, shadowBuilder, button, 0);
                return true;
            }
        });
    }

    public void createOnDragListener(View v){
        button.setOnDragListener(new View.OnDragListener(){
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        int posX = (int) event.getX();
                        int posY = (int) event.getY();
                        setPosition(posX,posY);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return true;
            }
        });
    }
    */

    public void createOnTouchListener(final View view, final RelativeLayout layout){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lparams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        oldXvalue = me.getRawX() - lparams.leftMargin;
                        oldYvalue = me.getRawY() - lparams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        params.leftMargin = (int)(me.getRawX() - oldXvalue);
                        params.topMargin = (int)(me.getRawY() - oldYvalue);
                        params.rightMargin = -250;
                        params.bottomMargin = -250;
                        button.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }
}
