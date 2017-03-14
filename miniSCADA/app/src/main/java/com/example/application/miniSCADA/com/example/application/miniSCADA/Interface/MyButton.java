package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.*;

import Moka7.*;

public class MyButton extends DiscreteElement {
    private transient Button button;
    private DataBlockBool commandOnDataBlock;
    private DataBlockBool commandOffDataBlock;
    private String textOnTrue;
    private String textOnFalse;


    public MyButton(Activity activity, DataBlockBool statusDataBlock, int x, int y, int height, int width,
                    DataBlockBool commandOnDataBlock,DataBlockBool commandOffDataBlock){
        super(statusDataBlock,x,y,height,width);
        button = new Button(activity);
        this.textOnTrue = "";
        this.textOnFalse = "";
        this.commandOnDataBlock = commandOnDataBlock;
        this.commandOffDataBlock = commandOffDataBlock;
        this.button.setTextColor(Color.BLACK);
        this.button.setTextSize(14);
        this.button.setBackgroundColor(Color.WHITE);
    }

    public void reCreateButton(Activity activity){
        this.button = new Button(activity);
        this.updateTrueFalseImage(activity);
        this.button.setTextSize(14);

        updatePositionToElement();
        updateSizeToElement();
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

    public void updateTrueFalseImage(Context context){
        if (this.getStatus()) {
            button.setBackground(Globals.loadImageToDrawable(context, this.getOnTrueImage()));
            button.setText(this.textOnTrue);
        } else {
            button.setBackground(Globals.loadImageToDrawable(context, this.getOnFalseImage()));
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

    public void updatePositionToElement(){
        button.setX(this.getPositionX());
        button.setY(this.getPositionY());
    }

    public void updatePositionFromElement(){
        this.setPosition((int)button.getX(), (int)button.getY());
    }

    public void updateSizeToElement(){
        button.setHeight(this.getHeight());
        button.setWidth(this.getWidth());
    }

    public void updateSizeFromElement(){
        this.setSize(button.getHeight(), button.getWidth());
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

    public void createOnTouchListener(final RelativeLayout layout){
        button.setOnTouchListener(new View.OnTouchListener() {
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
                        button.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }
}
